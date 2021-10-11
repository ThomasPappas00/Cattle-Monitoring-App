import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(name = "accessSensor", urlPatterns = {"/accessSensor/*"})
public class AccessSensor extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public AccessSensor() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		
		//serve client 
		ClientHandler ch = new ClientHandler();
		String jsonCommand = "{\"command\":\"getsensor\",\"id\":" + id +"}";
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


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//get JSON from client 
		String jsonIn = "";
		Scanner scanner = new Scanner(request.getInputStream());
		while(scanner.hasNextLine()){
			jsonIn += scanner.nextLine();
			jsonIn += "\n";
		}
		scanner.close();
		System.out.println(jsonIn);
		
		//serve request
		ClientHandler ch = new ClientHandler();
		String jsonOut = null;			
		try {
			jsonOut = ch.servePost(jsonIn);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} 
		System.out.println(jsonOut);
		response.setContentType("application/json");
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods","GET, POST, DELETE");
		response.getOutputStream().println(jsonOut); //send appropriate JSON to client 
	}
	
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		
		System.out.println("Delete request on sensor " + id);
		//serve request
		ClientHandler ch = new ClientHandler();
		String jsonOut = null;		
		String jsonCommand = "{\"command\":\"deletesensor\",\"id\":" + id + "}";
		try {
			jsonOut = ch.serveDelete(jsonCommand);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} 
		response.setContentType("application/json");
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods","GET, POST, DELETE, OPTIONS");
		response.addHeader("Access-Control-Allow-Credentials","true");		
		response.addHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization");
		response.getOutputStream().println(jsonOut); //send appropriate JSON to client 
	}

}