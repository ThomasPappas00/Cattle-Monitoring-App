
public class Gate {
	public int id;
	public Location location;
	public boolean isOpen;
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setLocation(Location location) {
		this.location = location;
	}
	
	public void setIsOpen(boolean isOpen) {
		this.isOpen = isOpen;
	}
	
	public int getId() {
		return this.id;
	}
	
	public Location getLocation() {
		return this.location;
	}
	
	public boolean getIsOpen() {
		return this.isOpen;
	}
}
