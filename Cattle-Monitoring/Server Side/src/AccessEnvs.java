

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "accessEnvs", urlPatterns = {"/accessEnvs/*"})
public class AccessEnvs extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public AccessEnvs() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String jsonOut = null;
		
		String connectionUrl =                 
				"jdbc:sqlserver://localhost:1433;"
                + "databaseName=CattleDB;"
                + "integratedSecurity=true;";
		String sql = "DECLARE @ReturnJSON nvarchar(max) " + 
				"SET @ReturnJSON = (SELECT id, name, normal FROM ENVS FOR JSON AUTO); " + 
				"SELECT @ReturnJSON AS Result";
		
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			Connection connection = DriverManager.getConnection(connectionUrl);
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if(rs.next()) {
				jsonOut = rs.getNString("Result");
			}
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		jsonOut = "{\"reply\":\"getallenvs\",\"body\":" + jsonOut + "}";

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods","GET, POST, DELETE");
		PrintWriter out = response.getWriter();
		out.println(jsonOut);
	}


}
