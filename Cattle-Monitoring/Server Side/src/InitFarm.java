
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;



public class InitFarm {

	public static void main(String[] args) throws ParseException, JsonProcessingException, SQLException, ClassNotFoundException {		
		
		String connectionUrl =                 
				"jdbc:sqlserver://localhost:1433;"
                + "databaseName=CattleDB;"
                + "integratedSecurity=true;";
		
		Connection connection = DriverManager.getConnection(connectionUrl); //make connection with local Microsoft SQL Database
		System.out.println("Farm generator connected to the SQL Database");	
		int numOfAnimals = 20;
		
		createDBTables(connection);
		populateDBTables(connection, numOfAnimals);		
		
		System.out.println("A farm with " + numOfAnimals + " animals and 3 milking pumps is created");
	}
	
	public static void initLocation(Location loc, int herd) {
		String x = "21.68" + String.valueOf(herd) + String.valueOf(ThreadLocalRandom.current().nextInt(0, 9 + 1)) + String.valueOf(ThreadLocalRandom.current().nextInt(0, 9 + 1)); 
		String y = "39.73" + String.valueOf(herd) + String.valueOf(ThreadLocalRandom.current().nextInt(0, 9 + 1)) + String.valueOf(ThreadLocalRandom.current().nextInt(0, 9 + 1)); 
		loc.setX(x);
		loc.setY(y);
	}
	
	public static void initHistory(History history, int animal_id) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");  
		Date date_min = formatter.parse("01/01/2013");
		Date date_max = formatter.parse("12/12/2020");
		Date randomDate = new Date(ThreadLocalRandom.current().nextLong(date_min.getTime(), date_max.getTime()));
		history.setBirthDate(formatter.format(randomDate));
		history.setFather(ThreadLocalRandom.current().nextInt(1, 30 + 1));
		history.setMother(ThreadLocalRandom.current().nextInt(31, 150 + 1));
		double random1 = Math.random();
		if(random1<0.8) {
			history.setIBR(true);
			history.setBVD(true);
			history.setPI3(true);
			history.setBRSV(true);
		}
		else {
			history.setIBR(false);
			history.setBVD(false);
			history.setPI3(false);
			history.setBRSV(false);
		}
		double random2 = Math.random();
		if(random2<0.1)
			history.setSick(true);
		else
			history.setSick(false);
		double random3 = Math.random();
		if(random3<0.1)
			history.setGivenBirth(true);
		else
			history.setGivenBirth(false);	
	}
	
	public static void updateHistory(History history,boolean vacc1,boolean vacc2, boolean vacc3, boolean vacc4, boolean sick,boolean given_birth) {
		history.setIBR(vacc1);
		history.setBVD(vacc2);
		history.setPI3(vacc3);
		history.setBRSV(vacc4);
		history.setSick(sick);
		history.setGivenBirth(given_birth);
	}

	public static void initSensor(Sensor sensor,int id,Location loc) {
		sensor.setId(id);
		sensor.setLocation(loc);
		double random_temp = ThreadLocalRandom.current().nextDouble(37.9, 39.6);
		DecimalFormat df = new DecimalFormat("#.00");
		sensor.setTemp(df.format(random_temp));
		sensor.setHeartRate(ThreadLocalRandom.current().nextInt(38, 89 + 1));
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
	
	public static void initAnimal(Animal animal, int id, int herd, Sensor sensor, History history) {
		animal.setId(id);
		animal.setHerd(herd);
		animal.setSensor(sensor);
		animal.setHistory(history);
		double random1 = Math.random();
		if(random1>0.1) {
			animal.setSex("F");
			animal.setMilkProduction(String.valueOf(ThreadLocalRandom.current().nextInt(4, 60 + 1)) + " litres/day");
		}
		else {
			animal.setSex("M");
			animal.setMilkProduction("no milk production");
		}
	}
	
	public static void updateAnimal(Animal animal, Sensor sensor, History history) {
		animal.setSensor(sensor);
		animal.setHistory(history);		
	}
	
	public static void createDBTables(Connection connection) throws SQLException {
		Statement st = connection.createStatement();
		String sql = "DROP TABLE IF EXISTS ANIMAL";
		st.execute(sql);
		
		String sql1 =  "CREATE TABLE ANIMAL "
				+ "(id INTEGER, "
				+ "herd INTEGER, "
				+ "history NVARCHAR(400), "
				+ "sex VARCHAR, "
				+ "milkProduction VARCHAR(20), "
				+ "PRIMARY KEY(id))";
		st.execute(sql1);
		
		String sql2 = "DROP TABLE IF EXISTS SENSOR";
		st.execute(sql2);
		
		String sql3 = "CREATE TABLE SENSOR "
				+ "(id INTEGER, "
				+ "location NVARCHAR(200), "
				+ "temp VARCHAR(10), "
				+ "heartRate INTEGER, "
				+ "normalRumination BIT, "
				+ "secure BIT, "
				+ "attention BIT, "
				+ "timestamp VARCHAR(20), "
				+ "PRIMARY KEY(id))";
		st.execute(sql3);
		
		String sql4 = "DROP TABLE IF EXISTS WEARS";
		st.execute(sql4);
		
		String sql5 = "CREATE TABLE WEARS"
				+ "(animal_id INTEGER, "
				+ "sensor_id INTEGER, "
				+ "PRIMARY KEY (animal_id,sensor_id))";
		st.execute(sql5); 
		
		String sql6 = "DROP TABLE IF EXISTS MILKPUMP";
		st.execute(sql6);
		
		String sql7 = "CREATE TABLE MILKPUMP"
				+ "(id INTEGER, "
				+ "location NVARCHAR(200), "
				+ "avail BIT, "
				+ "animalId INTEGER, "
				+ "capacity INTEGER, "
				+ "PRIMARY KEY (id))";
		st.execute(sql7);
		
		String sql8 = "DROP TABLE IF EXISTS GATE";
		st.execute(sql8);
		
		String sql9 = "CREATE TABLE GATE"
				+ "(id INTEGER, "
				+ "location NVARCHAR(200), "
				+ "isOpen BIT, "
				+ "PRIMARY KEY (id))";
		st.execute(sql9);
		
		String sql10 = "DROP TABLE IF EXISTS ENVS";
		st.execute(sql10);
		
		String sql11 = "CREATE TABLE ENVS"
				+ "(id INTEGER, "
				+ "name VARCHAR(30), "
				+ "normal BIT, "
				+ "PRIMARY KEY(id))";
		st.execute(sql11);
	}

	public static void populateDBTables(Connection connection, int sum) throws ParseException, SQLException, JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		for(int i=0;i<sum;i++) {
			History h1 = new History();
			Location loc1 = new Location();
			Sensor s1 = new Sensor();
			Animal a1 = new Animal();
			int cow_id = i+1;
			int herd_id = i%3 + 1;
			int sen_id = i+11;
			initHistory(h1,cow_id);
			initLocation(loc1,herd_id);
			initSensor(s1,sen_id,loc1);
			initAnimal(a1,cow_id,herd_id,s1,h1);
			
			String sql = "INSERT INTO ANIMAL (id,herd,history,sex,milkProduction)"
					+ "VALUES (?,?,?,?,?)";
			PreparedStatement ps = connection.prepareStatement(sql);
			int animal_id = a1.getId();
			ps.setInt(1, animal_id);
			ps.setInt(2, a1.getHerd());
			ps.setNString(3,mapper.writeValueAsString(a1.getHistory()));
			ps.setString(4, a1.getSex());
			ps.setString(5, a1.getMilkProduction());
			ps.executeUpdate();
			
			String sql1 = "INSERT INTO SENSOR (id,location,temp,heartRate,normalRumination,secure,attention,timestamp)"
					+ "VALUES (?,?,?,?,?,?,?,?)";
			PreparedStatement ps1 = connection.prepareStatement(sql1);
			int sensor_id = s1.getId();
			ps1.setInt(1, sensor_id);
			ps1.setNString(2, mapper.writeValueAsString(s1.getLocation()));
			ps1.setString(3,s1.getTemp());
			ps1.setInt(4, s1.getHeartRate());
			ps1.setBoolean(5, s1.getNormalRumination());
			ps1.setBoolean(6,s1.getSecure());
			ps1.setBoolean(7,s1.getAttention());
			ps1.setString(8, s1.getTimestamp());
			ps1.executeUpdate();
			
			String sql2 = "INSERT INTO WEARS (animal_id,sensor_id) VALUES(?,?)";
			PreparedStatement ps2 = connection.prepareStatement(sql2);
			ps2.setInt(1, animal_id);
			ps2.setInt(2, sensor_id);
			ps2.executeUpdate();
		}
		
		Location pump1Loc = new Location("21.68668","39.72941");
		Location pump2Loc = new Location("21.68673","39.72935");
		Location pump3Loc = new Location("21.68676","39.72931");
		ArrayList<Location> pumpsLocations = new ArrayList<>();
		pumpsLocations.add(pump1Loc);
		pumpsLocations.add(pump2Loc);
		pumpsLocations.add(pump3Loc);
		for(int i=1;i<4;i++) {
			String sql3 = "INSERT INTO MILKPUMP (id,location,avail,animalId,capacity) VALUES(?,?,?,?,?)";
			PreparedStatement ps3 = connection.prepareStatement(sql3);
			ps3.setInt(1, i);
			ps3.setNString(2,  mapper.writeValueAsString(pumpsLocations.get(i-1)));
			ps3.setBoolean(3, true);
			ps3.setInt(4, 0);
			ps3.setInt(5, 0);
			ps3.executeUpdate();
		}
		
		Location gate1Loc = new Location("21.689625791412766","39.73043203955618");
		Location gate2Loc = new Location("21.690081074234033","39.729759569550666");
		Location gate3Loc = new Location("21.68531483485483","39.724363641091145");
		Location gate4Loc = new Location("21.675050691412963","39.73697079442264");
		ArrayList<Location> gatesLocations = new ArrayList<>();
		gatesLocations.add(gate1Loc);
		gatesLocations.add(gate2Loc);
		gatesLocations.add(gate3Loc);
		gatesLocations.add(gate4Loc);
		for(int i=1;i<5;i++) {
			String sql4 = "INSERT INTO GATE (id,location,isOpen) VALUES(?,?,?)";
			PreparedStatement ps4 = connection.prepareStatement(sql4);
			ps4.setInt(1, i);
			ps4.setNString(2,  mapper.writeValueAsString(gatesLocations.get(i-1)));
			ps4.setBoolean(3, false);
			ps4.executeUpdate();
		}
		
		ArrayList<String> envNames = new ArrayList<>();
		envNames.add("Επίπεδα θερμοκρασίας");
		envNames.add("Επίπεδα υγρασίας");
		envNames.add("Επίπεδα τοξικών αερίων");
		envNames.add("Εντοπισμός καπνού");
		for(int i=1;i<4;i++) {
			String sql5 = "INSERT INTO ENVS (id,name,normal) VALUES(?,?,?)";
			PreparedStatement ps5 = connection.prepareStatement(sql5);
			ps5.setInt(1, i);
			ps5.setString(2,  mapper.writeValueAsString(envNames.get(i-1)));
			ps5.setBoolean(3, true);
			ps5.executeUpdate();
		}
		String sql5 = "INSERT INTO ENVS (id,name,normal) VALUES(?,?,?)";
		PreparedStatement ps5 = connection.prepareStatement(sql5);
		ps5.setInt(1, 4);
		ps5.setString(2,  mapper.writeValueAsString(envNames.get(4-1)));
		ps5.setBoolean(3, false);
		ps5.executeUpdate();
	}
}


