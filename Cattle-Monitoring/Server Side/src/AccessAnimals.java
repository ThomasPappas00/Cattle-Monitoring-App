
import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(name = "accessAnimals", urlPatterns = {"/accessAnimals/*"})
public class AccessAnimals extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    public AccessAnimals() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String herd = request.getParameter("herd");
		String jsonCommand = null;
		if (herd == null) {
			 jsonCommand = "{\"command\":\"getallanimals\",\"id\":" + "0" +"}";
		}
		else {
			jsonCommand =  "{\"command\":\"getherd\",\"id\":" + herd +"}";
		}
		
		//serve client 
		ClientHandler ch = new ClientHandler();
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
