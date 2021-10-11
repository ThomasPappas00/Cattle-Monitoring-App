
public class Sensor {
	public int id;
	public Location location;
	public String temp;
	public int heartRate;
	public boolean normalRumination;
	public boolean secure;
	public boolean attention;
	public String timestamp;
	
	public Sensor() {}

	public void setId(int id) {
		this.id = id;
	}
	
	public void setLocation(Location location) {
		this.location = location;
	}
	
	public void setTemp(String temp) {
		this.temp = temp;
	}
	
	
	public void setHeartRate(int heartRate) {
		this.heartRate = heartRate;
	}
	
	public void setNormalRumination(boolean normal) {
		this.normalRumination = normal;
	}
	
	public void setSecure(boolean secure) {
		this.secure = secure;
	}
	
	public void setAttention(boolean attention) {
		this.attention = attention;
	}
	
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	
	public int getId() {
		return this.id;
	}
	
	public Location getLocation() {
		return this.location;
	}
	
	public String getTemp() {
		return this.temp;
	}
		
	public int getHeartRate() {
		return this.heartRate;
	}
	
	public boolean getNormalRumination() {
		return this.normalRumination;
	}
	
	public boolean getSecure() {
		return this.secure;
	}
	
	public boolean getAttention() {
		return this.attention;
	}
	
	public String getTimestamp() {
		return this.timestamp;
	}
	
}
