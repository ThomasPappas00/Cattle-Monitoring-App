import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class ClientHandler {
	final String connectionUrl =                 
			"jdbc:sqlserver://localhost:1433;"
            + "databaseName=CattleDB;"
            + "integratedSecurity=true;";
	
	public ClientHandler() {}
	
	public String serveGet(String jsonIn) throws SQLException, ClassNotFoundException, JsonMappingException, JsonProcessingException {
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		Connection connection =  DriverManager.getConnection(connectionUrl);
		String jsonOut = null; 
			
		ObjectNode node = new ObjectMapper().readValue(jsonIn, ObjectNode.class);
		String command = node.get("command").asText();
		int index = Integer.parseInt(node.get("id").toString());
		
		switch(command) {
			case "getanimal":
				jsonOut = getAnimal(connection, index);	
				jsonOut = "{\"reply\":\"getanimal\",\"body\":"+  jsonOut + "}" ;
				break;
			case "getherd":
				jsonOut = getHerd(connection, index);
				break;
			case "getallanimals":
				jsonOut = getAllAnimals(connection);
				break;
			case "getsensor":
				jsonOut = getSensor(connection, index);
				jsonOut = "{\"reply\":\"getsensor\",\"body\":"+  jsonOut + "}" ;
				break;
			case "getallsensors":
				jsonOut = getAllSensors(connection);
				break;
			case "getmilkpump":
				jsonOut = getMilkPump(connection,index);
				jsonOut = "{\"reply\":\"getmilkpump\",\"body\":"+  jsonOut + "}" ;
				break;
			case "getallmilkpumps":
				jsonOut = getAllMilkPumps(connection);
				break;
			case "getgate":
				jsonOut = getGate(connection,index);
				jsonOut = "{\"reply\":\"getgate\",\"body\":"+  jsonOut + "}" ;
				return jsonOut;
			case "getallgates":
				jsonOut = getAllGates(connection);
				return jsonOut;
		} 	
		connection.close();
		return jsonOut;
	}
	
	public String servePost(String jsonIn) throws ClassNotFoundException, SQLException, JsonMappingException, JsonProcessingException {
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		Connection connection =  DriverManager.getConnection(connectionUrl);
		String jsonOut = null;

		ObjectNode node = new ObjectMapper().readValue(jsonIn, ObjectNode.class);	
		String command = node.get("command").asText();
		switch(command) {
			case "postsensor":
				jsonOut = postSensor(connection,jsonIn);
				break;
			case "postanimal":
				jsonOut = postAnimal(connection,jsonIn);
				break;		
			case "postgate":
				jsonOut = postGate(connection,jsonIn);
				break;
		}
		connection.close();
		return jsonOut;
	}
	
	public String serveDelete(String jsonIn) throws ClassNotFoundException, JsonMappingException, JsonProcessingException, SQLException {
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		Connection connection =  DriverManager.getConnection(connectionUrl);
		String jsonOut = null;

		ObjectNode node = new ObjectMapper().readValue(jsonIn, ObjectNode.class);	
		String command = node.get("command").asText();
		int id = node.get("id").asInt();
		switch(command) {
			case "deletesensor":
				String sql = "DELETE FROM SENSOR WHERE id = ?";
				PreparedStatement ps = connection.prepareStatement(sql);
				ps.setInt(1, id);
				ps.executeUpdate();
				String sql1 = "DELETE FROM WEARS WHERE sensor_id = ?";
				PreparedStatement ps1 = connection.prepareStatement(sql1);
				ps1.setInt(1, id);
				ps1.executeUpdate();
				jsonOut = "{\"reply\":\"deletesensor\",\"body\":\"Sensor (" + id + ") deleted successfully\"}";
				break;
			case "deleteanimal":
				String sql2 = "DELETE FROM ANIMAL WHERE id = ?";
				PreparedStatement ps2 = connection.prepareStatement(sql2);
				ps2.setInt(1, id);
				ps2.executeUpdate();
				String sql3 = "DELETE FROM WEARS WHERE animal_id = ?";
				PreparedStatement ps3 = connection.prepareStatement(sql3);
				ps3.setInt(1, id);
				ps3.executeUpdate();
				jsonOut = "{\"reply\":\"deleteanimal\",\"body\":\"Animal (" + id + ") deleted successfully\"}";
				break;		
		}	
		connection.close();
		return jsonOut;	
	}
	
	
	public static String getAnimal(Connection connection, int animal_id) throws SQLException, JsonMappingException, JsonProcessingException {
		int sensor_id = 0;
		Animal a = new Animal();
		Sensor s = new Sensor();
		ObjectMapper mapper = new ObjectMapper();
		
		String sql = "SELECT sensor_id FROM WEARS WHERE animal_id = ?";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setInt(1, animal_id);
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			sensor_id = rs.getInt("sensor_id");
		}
		
		
		String sql1 = "SELECT * FROM SENSOR WHERE id = ?";
		PreparedStatement ps1 = connection.prepareStatement(sql1);
		ps1.setInt(1, sensor_id);
		ResultSet rs1 = ps1.executeQuery();
		while(rs1.next()) {
			s.setId(rs1.getInt("id"));	
			s.setLocation(mapper.readValue(rs1.getNString("location"),Location.class));
			s.setTemp(rs1.getString("temp"));
			s.setHeartRate(rs1.getInt("heartRate"));
			s.setNormalRumination(rs1.getBoolean("normalRumination"));
			s.setSecure(rs1.getBoolean("secure"));
			s.setAttention(rs1.getBoolean("attention"));
			s.setTimestamp(rs1.getString("timestamp"));
		}
		
		String sql2 = "SELECT * FROM ANIMAL WHERE id = ?";
		PreparedStatement ps2 = connection.prepareStatement(sql2);
		ps2.setInt(1, animal_id);
		ResultSet rs2 = ps2.executeQuery();
		a.setSensor(s);
		while(rs2.next()) {
			a.setId(rs2.getInt("id"));
			a.setHerd(rs2.getInt("herd"));
			a.setHistory( mapper.readValue(rs2.getNString("history"), History.class));
			a.setSex(rs2.getString("sex"));
			a.setMilkProduction(rs2.getString("milkProduction"));
		}

		String reply = mapper.writeValueAsString(a);
		return reply;
	}

	public static String getHerd(Connection connection,int herd) throws SQLException, JsonMappingException, JsonProcessingException {
		int animal_id = 0;
		String reply = "";
		String sql = "SELECT id FROM ANIMAL WHERE herd = ?";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setInt(1, herd);
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			animal_id = rs.getInt("id");
			reply = reply + getAnimal(connection, animal_id) + ",";
		}
		String closedReply = "{\"reply\":\"getherd\",\"body\":["+  reply.substring(0, reply.length() - 1) + "]}" ;		
		return closedReply;
	}
	
	public static String getAllAnimals(Connection connection) throws SQLException, JsonMappingException, JsonProcessingException {
		int animal_id = 0;
		String reply = "";
		String sql = "SELECT id FROM ANIMAL";
		Statement stmt = connection.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		while(rs.next()) {
			animal_id = rs.getInt("id");
			reply = reply + getAnimal(connection, animal_id) + ",";
		}
		String closedReply =  "{\"reply\":\"getallanimals\",\"body\":["+  reply.substring(0, reply.length() - 1) + "]}" ;			
		return closedReply;
	}

	public static String getSensor(Connection connection, int sensor_id) throws SQLException, JsonMappingException, JsonProcessingException {
		Sensor s = new Sensor();
		ObjectMapper mapper = new ObjectMapper();
		String sql = "SELECT * FROM SENSOR WHERE id = ?";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setInt(1, sensor_id);
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			s.setId(rs.getInt("id"));				
			s.setLocation(mapper.readValue(rs.getNString("location"),Location.class));
			s.setTemp(rs.getString("temp"));
			s.setHeartRate(rs.getInt("heartRate"));
			s.setNormalRumination(rs.getBoolean("normalRumination"));
			s.setSecure(rs.getBoolean("secure"));
			s.setAttention(rs.getBoolean("attention"));
			s.setTimestamp(rs.getString("timestamp"));
		}
		
		String reply = mapper.writeValueAsString(s);
		return reply;
	}

	public static String getAllSensors(Connection connection) throws SQLException, JsonMappingException, JsonProcessingException {
		int sensor_id = 0;
		String reply = "";
		String sql = "SELECT id FROM SENSOR";
		Statement stmt = connection.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		while(rs.next()) {
			sensor_id = rs.getInt("id");
			reply = reply + getSensor(connection, sensor_id) + ",";
		}
		String closedReply = "{\"reply\":\"getallsensors\",\"body\":["+  reply.substring(0, reply.length() - 1) + "]}" ;	
		return closedReply;
	}
	
	public static String postSensor(Connection connection, String jsonIn) throws JsonMappingException, JsonProcessingException, SQLException {
		String jsonOut = null;
		Location loc = new Location();
		Sensor sen = new Sensor();
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode node = new ObjectMapper().readValue(jsonIn, ObjectNode.class);	
		String body = node.get("body").toString();
		
		ObjectNode node1 = new ObjectMapper().readValue(body, ObjectNode.class);
		int sensor_id = node1.get("sensor_id").asInt();
		int herd = node1.get("herd").asInt();
		initLocation(loc,herd);
		initSensor(sen,sensor_id,loc);
		
		String sql = "INSERT INTO SENSOR VALUES (?,?,?,?,?,?,?,?)";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setInt(1, sen.getId());
		ps.setNString(2, mapper.writeValueAsString(sen.getLocation()));
		ps.setString(3, sen.getTemp());
		ps.setInt(4, sen.getHeartRate());
		ps.setBoolean(5, sen.getNormalRumination());
		ps.setBoolean(6, sen.getSecure());
		ps.setBoolean(7, sen.getAttention());
		ps.setString(8, sen.getTimestamp());
		try {
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			jsonOut = "{\"reply\":\"postsensor\",\"body\":\"This sensor (" + sen.getId() + ") is already submitted\"}";
			return jsonOut;			
		}
		
		jsonOut = "{\"reply\":\"postsensor\",\"body\":\"Sensor (" + sen.getId() + ") submitted successfully\"}";
		return jsonOut;
	}
	
	public static String postGate(Connection connection, String jsonIn) throws JsonMappingException, JsonProcessingException, SQLException {
		ObjectNode node = new ObjectMapper().readValue(jsonIn, ObjectNode.class);	
		String body = node.get("body").toString();
		
		ObjectNode node1 = new ObjectMapper().readValue(body, ObjectNode.class);
		int gate_id = node1.get("gate_id").asInt();
		boolean isOpen = node1.get("isOpen").asBoolean();
		
		String sql = "UPDATE GATE SET isOpen = ? WHERE id = ?";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setBoolean(1, isOpen);
		ps.setInt(2, gate_id);
		ps.executeUpdate();
		
		return null;
	}
	
	public static void initLocation(Location loc, int herd) {
		String x = "21.68" + String.valueOf(herd) + String.valueOf(ThreadLocalRandom.current().nextInt(0, 9 + 1)) + String.valueOf(ThreadLocalRandom.current().nextInt(0, 9 + 1)); 
		String y = "39.73" + String.valueOf(herd) + String.valueOf(ThreadLocalRandom.current().nextInt(0, 9 + 1)) + String.valueOf(ThreadLocalRandom.current().nextInt(0, 9 + 1)); 
		loc.setX(x);
		loc.setY(y);
	}
	
	public static void initSensor(Sensor sensor,int id,Location loc) {
		sensor.setId(id);
		sensor.setLocation(loc);
		double random_temp = ThreadLocalRandom.current().nextDouble(37.8, 39.7);
		DecimalFormat df = new DecimalFormat("#.00");
		sensor.setTemp(df.format(random_temp));
		sensor.setHeartRate(ThreadLocalRandom.current().nextInt(40, 90 + 1));
		double random1 = Math.random();
		if(random1<0.9)
			sensor.setNormalRumination(true);
		else
			sensor.setNormalRumination(false);
		double random2 = Math.random();
		if(random2<0.99)
			sensor.setSecure(true);
		else
			sensor.setSecure(false);
		sensor.setAttention(false);
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
		LocalDateTime now = LocalDateTime.now();  
	    sensor.setTimestamp(dtf.format(now));		
	}
	
	public static String postAnimal(Connection connection, String jsonIn) throws JsonMappingException, JsonProcessingException, SQLException   {
		History h = new History();
		ObjectMapper mapper = new ObjectMapper();
		String jsonOut = null;
		ObjectNode node = new ObjectMapper().readValue(jsonIn, ObjectNode.class);	
		String animal = node.get("body").toString();
		
		ObjectNode node1 = new ObjectMapper().readValue(animal, ObjectNode.class);	
		int animal_id = node1.get("id").asInt();
		int herd = node1.get("herd").asInt();
		int sensor_id = node1.get("sensor_id").asInt();		
		String historyJson = node1.get("history").toString();
		String sex = node1.get("sex").asText();
		String milkProduction = node1.get("milkProduction").asText();
		
		ObjectNode node2 = new ObjectMapper().readValue(historyJson, ObjectNode.class);	
		h.setBirthDate(node2.get("birth_date").asText());
		h.setFather(node2.get("father_id").asInt());
		h.setMother(node2.get("mother_id").asInt());
		h.setIBR(node2.get("IBR_vacc").asBoolean());
		h.setBVD(node2.get("BVD_vacc").asBoolean());
		h.setPI3(node2.get("PI3_vacc").asBoolean());
		h.setBRSV(node2.get("BRSV_vacc").asBoolean());
		h.setSick(node2.get("sick").asBoolean());
		h.setGivenBirth(node2.get("given_birth").asBoolean());
		
		String sql = "INSERT INTO WEARS VALUES (?,?)";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setInt(1, animal_id);
		ps.setInt(2, sensor_id);
		try {
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			jsonOut = "{\"reply\":\"postanimal\",\"body\":\"Animal (" + animal_id + ") is already submitted or another animal wears this sensor\"}";
			return jsonOut;			
		}
		
		String sql1 = "INSERT INTO ANIMAL VALUES (?,?,?,?,?)";
		PreparedStatement ps1 = connection.prepareStatement(sql1);
		ps1.setInt(1, animal_id);
		ps1.setInt(2, herd);
		ps1.setNString(3, mapper.writeValueAsString(h));
		ps1.setString(4, sex);
		ps1.setString(5, milkProduction);
		ps1.executeUpdate();
		
		jsonOut = "{\"reply\":\"postanimal\",\"body\":\"Animal (" + animal_id + ") successfully submitted\"}";
		return jsonOut;
	}
	
	public static String getMilkPump(Connection connection, int id) throws SQLException, JsonMappingException, JsonProcessingException {
		MilkPump mp = new MilkPump();
		ObjectMapper mapper = new ObjectMapper();
		String sql = "SELECT * FROM MILKPUMP WHERE id = ?";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setInt(1, id);
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			mp.setId(rs.getInt("id"));				
			mp.setLoc(mapper.readValue(rs.getNString("location"),Location.class));
			mp.setAvail(rs.getBoolean("avail"));
			mp.setAnimalId(rs.getInt("animalId"));
			mp.setCapacity(rs.getInt("capacity"));
		}
		
		String reply = mapper.writeValueAsString(mp);
		return reply;
	}
	
	
	public static String getAllMilkPumps(Connection connection) throws JsonMappingException, JsonProcessingException, SQLException {
		String reply = "";
		for(int i=1;i<4;i++) {
			reply = reply + getMilkPump(connection, i) + ",";
		}
		String closedReply = "{\"reply\":\"getallmilkpumps\",\"body\":["+  reply.substring(0, reply.length() - 1) + "]}" ;	
		return closedReply;
	}
	
	public static String getGate(Connection connection, int id) throws SQLException, JsonMappingException, JsonProcessingException {
		Gate g = new Gate();
		ObjectMapper mapper = new ObjectMapper();
		String sql = "SELECT * FROM GATE WHERE id = ?";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setInt(1, id);
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			g.setId(rs.getInt("id"));
			g.setLocation(mapper.readValue(rs.getNString("location"),Location.class));
			g.setIsOpen(rs.getBoolean("isOpen"));
		}
		String reply = mapper.writeValueAsString(g);
		return reply;
	}
	
	public static String getAllGates(Connection connection) throws JsonMappingException, JsonProcessingException, SQLException {
		String reply = "";
		for(int i=1;i<5;i++) {
			reply = reply + getGate(connection, i) + ",";
		}
		String closedReply = "{\"reply\":\"getallgates\",\"body\":["+  reply.substring(0, reply.length() - 1) + "]}" ;	
		return closedReply;
	}
}
