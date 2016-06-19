package old.main.medicine;

public class MedicineBean {
	private String medicinename;
	private String clocktime;
	
	public MedicineBean(String medicinename,String clocktime){
		this.medicinename=medicinename;
		this.clocktime=clocktime;
		
	}
	
	public String getMedicinename() {
		return medicinename;
	}
	public void setMedicinename(String medicinename) {
		this.medicinename = medicinename;
	}
	public String getClocktime() {
		return clocktime;
	}
	public void setClocktime(String clocktime) {
		this.clocktime = clocktime;
	}
	
	

}
