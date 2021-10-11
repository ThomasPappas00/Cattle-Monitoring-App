

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



@WebServlet(name = "accessGates", urlPatterns = {"/accessGates/*"})
public class AccessGates extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public AccessGates() {
        super();

    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//serve client 
		ClientHandler ch = new ClientHandler();
		String jsonCommand = "{\"command\":\"getallgates\",\"id\":" + 0 +"}";
		String jsonOut = null;
		try {
			jsonOut = ch.serveGet(jsonCommand);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} 
		response.setContentType("application/json");
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods","GET, POST, DELETE");
		response.getOutputStream().println(jsonOut); //send appropriate JSON to client 
	}
	


}
