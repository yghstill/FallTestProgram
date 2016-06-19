package old.position;


public class PositionBean {

	private String nowtime=null;
	private String position=null;
	private int status;
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getNowtime() {
		return nowtime;
	}
	public void setNowtime(String nowtime) {
		this.nowtime = nowtime;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
}
