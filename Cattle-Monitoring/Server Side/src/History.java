
public class History {
	public String birth_date;
	public int father_id;
	public int mother_id;
	public boolean IBR_vacc;
	public boolean BVD_vacc;
	public boolean PI3_vacc;
	public boolean BRSV_vacc;
	public boolean sick;
	public boolean given_birth;
	
	public History() {}
	
	public void setBirthDate(String bd) {
		this.birth_date = bd;
	}
	
	public void setFather(int i) {
		this.father_id = i;
	}
	
	public void setMother(int i) {
		this.mother_id = i;
	}
	
	public void setIBR(boolean b) {
		this.IBR_vacc = b;
	}
	
	public void setBVD(boolean b) {
		this.BVD_vacc = b;
	}
	
	public void setPI3(boolean b) {
		this.PI3_vacc = b;
	}
	
	public void setBRSV(boolean b) {
		this.BRSV_vacc = b;
	}
	
	public void setSick(boolean b) {
		this.sick = b;
	}
	
	public void setGivenBirth(boolean b) {
		this.given_birth = b;
	}
	
}
