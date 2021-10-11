
public class Animal {
	public int id;
	public int herd;
	public Sensor sensor;
	public History history;
	public String sex;
	public String milkProduction;
	
	public Animal() {}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setHerd(int herd) {
		this.herd = herd;
	}
	
	public void setSensor(Sensor sensor) {
		this.sensor = sensor;
	}
	
	public void setHistory(History history) {
		this.history = history;
	}
	
	public void setSex(String sex) {
		this.sex = sex;
	}
	
	public void setMilkProduction(String milkProduction) {
		this.milkProduction = milkProduction;
	}
	
	public int getId() {
		return this.id;
	}
	
	public int getHerd() {
		return this.herd;
	}
	
	public Sensor getSensor() {
		return this.sensor;
	}
	
	public History getHistory() {
		return this.history;
	}
	
	public String getSex() {
		return this.sex;
	}
	
	public String getMilkProduction() {
		return this.milkProduction;
	}
	
}
