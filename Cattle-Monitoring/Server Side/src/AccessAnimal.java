

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(name = "accessAnimal", urlPatterns = {"/accessAnimal/*"})
public class AccessAnimal extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public AccessAnimal() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		
		System.out.println("Delere request on animal " + id);
		//serve client 
		ClientHandler ch = new ClientHandler();
		String jsonCommand = "{\"command\":\"getanimal\",\"id\":" + id +"}";
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
		
		//serve request
		ClientHandler ch = new ClientHandler();
		String jsonOut = null;		
		String jsonCommand = "{\"command\":\"deleteanimal\",\"id\":" + id + "}";	
		try {
			jsonOut = ch.serveDelete(jsonCommand);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} 
		response.setContentType("application/json");
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods","GET, POST, DELETE");
		response.getOutputStream().println(jsonOut); //send appropriate JSON to client 
	}

}
