import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(name = "accessMilkPump", urlPatterns = {"/accessMilkPump/*"})
public class AccessMilkPump extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public AccessMilkPump() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		
		//serve client 
		ClientHandler ch = new ClientHandler();
		String jsonCommand = "{\"command\":\"getmilkpump\",\"id\":" + id +"}";
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
