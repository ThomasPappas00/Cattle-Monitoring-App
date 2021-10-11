
public class MilkPump {
	public int id;
	public Location loc;
	public boolean avail;
	public int animalId;
	public int capacity;
	
	public MilkPump() {}
	
	public int getId() {
		return this.id;
	}
	
	public Location getLoc() {
		return this.loc;
	}
	
	public boolean getAvail() {
		return this.avail;
	}
	
	public int getAnimalId() {
		return this.animalId;
	}
	
	public int getCapacity() {
		return this.capacity;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setLoc(Location loc) {
		this.loc = loc;
	}
	
	public void setAvail(boolean avail) {
		this.avail = avail;
	}
	
	public void setAnimalId(int animalId) {
		this.animalId = animalId;
	}
	
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
}
