import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class Simulator {

	public static void main(String[] args) throws SQLException, InterruptedException, JsonMappingException, JsonProcessingException {
		String connectionUrl =                 
				"jdbc:sqlserver://localhost:1433;"
                + "databaseName=CattleDB;"
                + "integratedSecurity=true;";
		
		Connection connection = DriverManager.getConnection(connectionUrl); //make connection with local Microsoft SQL Database
		System.out.println("Simulator connected to the SQL Database");
		Statement stmt = connection.createStatement();
		ObjectMapper mapper = new ObjectMapper();
		
		
		while(true) {									//update all sensors every 10 seconds
			String sql = "SELECT * from SENSOR";	
			ResultSet rs1 = stmt.executeQuery(sql);

			while (rs1.next()) {                        //update each sensor
				Location loc = new Location();
				Sensor sen = new Sensor();

				sen.setId(rs1.getInt("id"));				
				String jsonLocation = rs1.getNString("location");
				ObjectNode node = new ObjectMapper().readValue(jsonLocation, ObjectNode.class);
				String xString = node.get("x").toString().substring(1, 9);
				String yString = node.get("y").toString().substring(1, 9);
				loc.setX(xString);
				loc.setY(yString);
				sen.setLocation(loc);
				sen.setTemp(rs1.getString("temp"));
				sen.setHeartRate(rs1.getInt("heartRate"));
				sen.setNormalRumination(rs1.getBoolean("normalRumination"));
				sen.setSecure(rs1.getBoolean("secure"));
				sen.setAttention(rs1.getBoolean("attention"));
				sen.setTimestamp(rs1.getString("timestamp"));			

				
				
				boolean stay_still = visitMilkPumps(sen.getId(),loc,connection);
				if(!stay_still)
					updateLocation(loc);
				updateSensor(sen,loc);
				checkForAttention(sen);
				
				
				
				
				String update = "UPDATE SENSOR "
						+ "SET location = ?, temp = ?, heartRate = ?, normalRumination = ?, secure = ?, attention = ?, timestamp = ? WHERE id = ?";
				PreparedStatement ps1 = connection.prepareStatement(update);
				ps1.setNString(1, mapper.writeValueAsString(sen.getLocation()));
				ps1.setString(2,sen.getTemp());
				ps1.setInt(3, sen.getHeartRate());
				ps1.setBoolean(4, sen.getNormalRumination());
				ps1.setBoolean(5,sen.getSecure());
				ps1.setBoolean(6,sen.getAttention());
				ps1.setString(7, sen.getTimestamp());
				ps1.setInt(8, sen.getId());
				ps1.executeUpdate();	
			}
			TimeUnit.SECONDS.sleep(10);			
		}	
	}
	
	private static void updateLocation(Location loc) {

		String old_x = Character.toString(loc.getX().charAt(7));
		String old_y = Character.toString(loc.getY().charAt(7));
		String new_x = null;
		String new_y = null;	
		
		Random generator1 = new Random();
		int randomX = generator1.nextInt( 3 ) - 1;
		switch(randomX) {
			case -1:
				if(old_x.equals("0"))
					new_x = String.valueOf(Integer.parseInt(old_x) + 1);
				else 
					new_x = String.valueOf(Integer.parseInt(old_x) - 1);
				break;
			case 0:
				new_x = old_x;
				break;
			case 1:
				if(old_x.equals("9"))
					new_x = String.valueOf(Integer.parseInt(old_x) - 1);
				else
					new_x = String.valueOf(Integer.parseInt(old_x) + 1);
				break;
		}
		
		Random generator2 = new Random();
		int randomY = generator2.nextInt( 3 ) - 1;
		switch(randomY) {
			case -1:
				if(old_y.equals("0"))
					new_y = String.valueOf(Integer.parseInt(old_y) + 1);
				else 
					new_y = String.valueOf(Integer.parseInt(old_y) - 1);
				break;
			case 0:
				new_y = old_y;
				break;
			case 1:
				if(old_y.equals("9"))
					new_y = String.valueOf(Integer.parseInt(old_y) - 1);
				else
					new_y = String.valueOf(Integer.parseInt(old_y) + 1);
				break;
		}
		
		String xloc = loc.getX().substring(0,7) + new_x;
		String yloc = loc.getY().substring(0,7) + new_y;
	
		loc.setX(xloc);
		loc.setY(yloc);
	}
	
	private static void updateSensor(Sensor sensor,Location loc) {
		sensor.setLocation(loc);
		String old_temp = sensor.getTemp();
		int old_hr = sensor.getHeartRate();
		DecimalFormat df = new DecimalFormat("#.00");
		Random generator1 = new Random();
		int randomT = generator1.nextInt( 3 ) - 1;
		switch(randomT) {
			case -1:
				sensor.setTemp(df.format(Double.parseDouble(old_temp) - 0.05));
				break;
			case 0:
				break;
			case 1:
				sensor.setTemp(df.format(Double.parseDouble(old_temp) + 0.05));
				break;
		}
		Random generator2 = new Random();
		int randomHR = generator2.nextInt( 3 ) - 1;
		switch(randomHR) {
			case -1:
				sensor.setHeartRate(old_hr - 1);
				break;
			case 0:
				break;
			case 1:
				sensor.setHeartRate(old_hr + 1);
				break;
		}
		double random1 = Math.random();
		if(random1<0.9)
			sensor.setNormalRumination(true);
		else
			sensor.setNormalRumination(false);
		double random2 = Math.random();
		if(random2<0.9)
			sensor.setSecure(true);
		else
			sensor.setSecure(false);
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
		LocalDateTime now = LocalDateTime.now();  
	    sensor.setTimestamp(dtf.format(now));	
	}
	
	public static void checkForAttention(Sensor s) {
		double temp = Double.parseDouble(s.getTemp());
		int hr = s.getHeartRate();
		
		if(temp<38.0||temp>39.5||hr<40||hr>87)
			s.setAttention(true);
		else
			s.setAttention(false);
	}
	
	public static void initLocation(Location loc, int herd) {
		String x = "21.68" + String.valueOf(herd) + String.valueOf(ThreadLocalRandom.current().nextInt(0, 9 + 1)) + String.valueOf(ThreadLocalRandom.current().nextInt(0, 9 + 1)); 
		String y = "39.73" + String.valueOf(herd) + String.valueOf(ThreadLocalRandom.current().nextInt(0, 9 + 1)) + String.valueOf(ThreadLocalRandom.current().nextInt(0, 9 + 1)); 
		loc.setX(x);
		loc.setY(y);
	}
	
	
	public static boolean visitMilkPumps(int sensor_id, Location loc, Connection connection) throws SQLException, JsonMappingException, JsonProcessingException {
		String sql = "SELECT animal_id FROM WEARS WHERE sensor_id = ?";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setInt(1, sensor_id);
		ResultSet rs = ps.executeQuery();
		int animal_id = 0;
		if(rs.next())		
			animal_id = rs.getInt(1);	
		String sql1 = "SELECT animalId FROM MILKPUMP";
		Statement stmt = connection.createStatement();
		ResultSet rs1 = stmt.executeQuery(sql1);
		if(rs1.next() && rs1.getInt(1)!=animal_id &&  rs1.next()  && rs1.getInt(1)!=animal_id &&  rs1.next()  && rs1.getInt(1)!=animal_id) {
			String sql2 = "SELECT TOP 1 id,capacity FROM MILKPUMP WHERE avail = 1";
			Statement stmt2 = connection.createStatement();
			ResultSet rs2 = stmt2.executeQuery(sql2);
			if(rs2.next()) {
				int pump_id = rs2.getInt("id");
				int capacity = rs2.getInt("capacity");
				double random = Math.random();
				String sql3 = "SELECT sex FROM ANIMAL WHERE id = ?";
				PreparedStatement ps3 = connection.prepareStatement(sql3);
				ps3.setInt(1, animal_id);
				ResultSet rs3 = ps3.executeQuery();
				rs3.next();
				
				if(random < 0.7 && rs3.getString("sex").equals("F") && capacity < 100) {
					String sql4 = "UPDATE MILKPUMP SET avail = 0, animalId = ?, capacity = capacity + 10 WHERE id = ?";
					PreparedStatement ps4 = connection.prepareStatement(sql4);
					ps4.setInt(1, animal_id);
					ps4.setInt(2, pump_id);
					ps4.executeUpdate();
					String sql5 = "SELECT location FROM MILKPUMP WHERE id = ?";
					PreparedStatement ps5 = connection.prepareStatement(sql5);
					ps5.setInt(1, pump_id);
					ResultSet rs5 = ps5.executeQuery();
					rs5.next();
					String jsonLoc = rs5.getNString("location");
					ObjectNode node = new ObjectMapper().readValue(jsonLoc, ObjectNode.class);
					String xString = node.get("x").toString().substring(1, 9);
					String yString = node.get("y").toString().substring(1, 9);
					loc.setX(xString);
					loc.setY(yString);
					return true;
				}
				else {
					return false;
				}	
			}
			else {
				return false;
			}
		}
		else {
			String sql6 = "SELECT capacity FROM MILKPUMP WHERE animalId = ?";
			PreparedStatement ps6 = connection.prepareStatement(sql6);
			ps6.setInt(1, animal_id);
			ResultSet rs6 = ps6.executeQuery();
			rs6.next();
			int capacity = rs6.getInt("capacity");
			if(capacity < 100) {
				String sql7 = "UPDATE MILKPUMP SET capacity = capacity + 10 WHERE animalId = ?";
				PreparedStatement ps7 = connection.prepareStatement(sql7);
				ps7.setInt(1, animal_id);
				ps7.executeUpdate();
				return true;
			}
			else {
				String sql8 = "UPDATE MILKPUMP SET avail = 1, animalId = 0, capacity = 0 WHERE animalId = ?";
				PreparedStatement ps8 = connection.prepareStatement(sql8);			
				ps8.setInt(1, animal_id);
				ps8.executeUpdate();
				String sql9 = "SELECT herd FROM ANIMAL WHERE id = ?";
				PreparedStatement ps9 = connection.prepareStatement(sql9);			
				ps9.setInt(1, animal_id);
				ResultSet rs9 = ps9.executeQuery();
				rs9.next();
				int herd = rs9.getInt("herd");
				initLocation(loc,herd);
				return false;
			}
			
		}
	}
}
