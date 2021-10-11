import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class ClientDummie {

	public static void main(String[] args) throws IOException {

		//sendGetA(0);
		//sendGetA(2);
		//sendPostA();
		
		//sendDeleteA(4);
		//sendGetHerd(3);
		//sendGetAA();
		//sendGetS(14);
		//sendGetS();
		//sendPostS();
		
		sendDeleteS(14);
		//sendGetAS();
		
	}
	
	@SuppressWarnings("unused")
	private static void sendGetA(int id) throws IOException {
		
		String reqUrl = "http://localhost/CattleMonitoring/accessAnimal";
		
		String query = "";
		if(id!=0) {
			query = String.format("/id?id=%d", id);
		}
		
		HttpURLConnection connection = (HttpURLConnection) new URL(reqUrl + query).openConnection();
		
		
		
		System.out.println("YEP");
		
		connection.setRequestMethod("GET");	
		connection.getDoInput();
		
		int responseCode = connection.getResponseCode();
		if(responseCode == 200){
			String jsonIn = "";
			Scanner scanner = new Scanner(connection.getInputStream());
			while(scanner.hasNextLine()){
				jsonIn += scanner.nextLine();
				jsonIn += "\n";
			}
			scanner.close();

			System.out.println(jsonIn);
		}
		else if(responseCode == 401){
			System.out.println("Wrong password.");
		}
	}
	
	@SuppressWarnings("unused")
	private static String sendPostA() throws IOException {
		HttpURLConnection connection = (HttpURLConnection) new URL("http://localhost/CattleMonitoring/accessAnimal").openConnection();
		
		String jsonOut = "{\"command\":\"postanimal\",\"animal\":{\"id\":11,\"herd\":2,\"sensor_id\":21,\"history\":{\"birth_date\":\"16/10/2015\",\"father_id\":15,\"mother_id\":76,\"IBR_vacc\":true,\"BVD_vacc\":true,\"PI3_vacc\":true,\"BRSV_vacc\":true,\"sick\":false,\"given_birth\":true},\"sex\":\"F\",\"milkProduction\":\"26 litres/day\"}} ";
		connection.setRequestMethod("POST");	
		connection.setDoOutput(true);
	    OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());
	    wr.write(jsonOut);
	    wr.flush();
		
		int responseCode = connection.getResponseCode();
		if(responseCode == 200){
			String response = "";
			Scanner scanner = new Scanner(connection.getInputStream());
			while(scanner.hasNextLine()){
				response += scanner.nextLine();
				response += "\n";
			}
			scanner.close();

			return response;
		}
		else if(responseCode == 401){
			System.out.println("Wrong password.");
		}
		return null;
	}
	
	@SuppressWarnings("unused")
	private static void sendDeleteA(int id) throws IOException {
		String reqUrl = "http://localhost/CattleMonitoring/accessAnimal";
		String query = String.format("/id?id=%d", id);
		
		
		HttpURLConnection connection = (HttpURLConnection) new URL(reqUrl + query).openConnection();
		
		
		
		System.out.println("YEP");
		
		connection.setRequestMethod("DELETE");	
		connection.getDoInput();

		
		int responseCode = connection.getResponseCode();
		if(responseCode == 200){
			String jsonIn = "";
			Scanner scanner = new Scanner(connection.getInputStream());
			while(scanner.hasNextLine()){
				jsonIn += scanner.nextLine();
				jsonIn += "\n";
			}
			scanner.close();

			System.out.println(jsonIn);
		}
		else if(responseCode == 401){
			System.out.println("Wrong password.");
		}
	}
	
	@SuppressWarnings("unused")
	private static void sendGetS(int id) throws IOException {
		String reqUrl = "http://localhost/CattleMonitoring/accessSensor";
		String query = String.format("/id?id=%d", id);
			
		HttpURLConnection connection = (HttpURLConnection) new URL(reqUrl + query).openConnection();
	
		
		System.out.println("YEP");
		
		connection.setRequestMethod("GET");	
		connection.getDoInput();
		
		int responseCode = connection.getResponseCode();
		if(responseCode == 200){
			String jsonIn = "";
			Scanner scanner = new Scanner(connection.getInputStream());
			while(scanner.hasNextLine()){
				jsonIn += scanner.nextLine();
				jsonIn += "\n";
			}
			scanner.close();

			System.out.println(jsonIn);
		}
		else if(responseCode == 401){
			System.out.println("Wrong password.");
		}
	}
	
	@SuppressWarnings("unused")
	private static String sendPostS() throws IOException {
		HttpURLConnection connection = (HttpURLConnection) new URL("http://localhost/CattleMonitoring/accessSensor").openConnection();

		String jsonOut = "{\"command\":\"postsensor\",\"body\":{\"sensor_id\":21,\"herd\":2}}";	
		connection.setRequestMethod("POST");	
		connection.setDoOutput(true);
		
	    OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());
	    wr.write(jsonOut);
	    wr.flush();
		
		int responseCode = connection.getResponseCode();
		if(responseCode == 200){
			String response = "";
			Scanner scanner = new Scanner(connection.getInputStream());
			while(scanner.hasNextLine()){
				response += scanner.nextLine();
				response += "\n";
			}
			scanner.close();
			System.out.println(response);
			return response;
		}
		else if(responseCode == 401){
			System.out.println("Wrong password.");
		}
		return null;
	}
	
	@SuppressWarnings("unused")
	private static void sendDeleteS(int id) throws IOException {
		String reqUrl = "http://localhost/CattleMonitoring/accessSensor";
		String query = String.format("/id?id=%d", id);
		
		
		HttpURLConnection connection = (HttpURLConnection) new URL(reqUrl + query).openConnection();
		
		
		
		System.out.println("YEP");
		
		connection.setRequestMethod("DELETE");	
		connection.getDoInput();

		
		int responseCode = connection.getResponseCode();
		if(responseCode == 200){
			String jsonIn = "";
			Scanner scanner = new Scanner(connection.getInputStream());
			while(scanner.hasNextLine()){
				jsonIn += scanner.nextLine();
				jsonIn += "\n";
			}
			scanner.close();

			System.out.println(jsonIn);
		}
		else if(responseCode == 401){
			System.out.println("Wrong password.");
		}
	}
	
	@SuppressWarnings("unused")
	private static void sendGetHerd(int id) throws IOException {
		
		String reqUrl = "http://localhost/CattleMonitoring/accessAnimals";
		
		String query = "";
		if(id!=0) {
			query = String.format("/herd?herd=%d", id);
		}
		
		HttpURLConnection connection = (HttpURLConnection) new URL(reqUrl + query).openConnection();
		
		
		
		System.out.println("YEP");
		
		connection.setRequestMethod("GET");	
		connection.getDoInput();
		
		int responseCode = connection.getResponseCode();
		if(responseCode == 200){
			String jsonIn = "";
			Scanner scanner = new Scanner(connection.getInputStream());
			while(scanner.hasNextLine()){
				jsonIn += scanner.nextLine();
				jsonIn += "\n";
			}
			scanner.close();

			System.out.println(jsonIn);
		}
		else if(responseCode == 401){
			System.out.println("Wrong password.");
		}
	}
	
	@SuppressWarnings("unused")
	private static void sendGetAA() throws IOException {
		
		String reqUrl = "http://localhost/CattleMonitoring/accessAnimals";
		
		HttpURLConnection connection = (HttpURLConnection) new URL(reqUrl).openConnection();
		
		
		
		System.out.println("YEP");
		
		connection.setRequestMethod("GET");	
		connection.getDoInput();
		
		int responseCode = connection.getResponseCode();
		if(responseCode == 200){
			String jsonIn = "";
			Scanner scanner = new Scanner(connection.getInputStream());
			while(scanner.hasNextLine()){
				jsonIn += scanner.nextLine();
				jsonIn += "\n";
			}
			scanner.close();

			System.out.println(jsonIn);
		}
		else if(responseCode == 401){
			System.out.println("Wrong password.");
		}
	}
	
	@SuppressWarnings("unused")
	private static void sendGetAS() throws IOException {
		
		String reqUrl = "http://localhost/CattleMonitoring/accessSensors";
		
		HttpURLConnection connection = (HttpURLConnection) new URL(reqUrl).openConnection();
		
		
		
		System.out.println("YEP");
		
		connection.setRequestMethod("GET");	
		connection.getDoInput();
		
		int responseCode = connection.getResponseCode();
		if(responseCode == 200){
			String jsonIn = "";
			Scanner scanner = new Scanner(connection.getInputStream());
			while(scanner.hasNextLine()){
				jsonIn += scanner.nextLine();
				jsonIn += "\n";
			}
			scanner.close();

			System.out.println(jsonIn);
		}
		else if(responseCode == 401){
			System.out.println("Wrong password.");
		}
	}
	

}
