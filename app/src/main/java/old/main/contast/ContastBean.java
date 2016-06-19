package old.main.contast;

import android.graphics.drawable.Drawable;

public class ContastBean {
	private String name;
	private String cellphone;
	private Drawable imagehead;
	
	public ContastBean(String name,String cellphone, Drawable imagehead){
		this.name=name;
		this.cellphone=cellphone;
		this.imagehead=imagehead;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCellphone() {
		return cellphone;
	}
	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}
	public Drawable getimagehead() {
		return imagehead;
	}
	public void setimagehead(Drawable imagehead) {
		this.imagehead = imagehead;
	}
	
	

}
