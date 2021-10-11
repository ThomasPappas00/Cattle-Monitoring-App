

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(name = "accessGate", urlPatterns = {"/accessGate/*"})
public class AccessGate extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public AccessGate() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		
		//serve client 
		ClientHandler ch = new ClientHandler();
		String jsonCommand = "{\"command\":\"getgate\",\"id\":" + id +"}";
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

}
