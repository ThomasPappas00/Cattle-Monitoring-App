

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "accessSensors", urlPatterns = {"/accessSensors/*"})
public class AccessSensors extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public AccessSensors() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//serve client 
		String jsonCommand = "{\"command\":\"getallsensors\",\"id\":" + "0" +"}";
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
