package loading;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import java.util.ArrayList;

public class BaseApplication extends Application {
	private ArrayList<Activity> mList = new ArrayList<Activity>();
	private static BaseApplication instance;
	private String login_username = "";
	private boolean poision_flag = false;
	private boolean islogin = false;
	private boolean isopenDD = false;
	//	private boolean isMsgclick=false;
	private boolean distanceshow=false;
	private boolean isclockset=false;
	private boolean isupdateData=false;
	private ArrayList<String> clocklist = new ArrayList<String>();
	private ArrayList<String> cgqlist = new ArrayList<String>();


	private String realname = "";
	private String cellphone1 = "";
	private String cellphone2 = "";
	private String cellphone3 = "";
	private int id = 0;
	private static Context context;
	private int mdistance;
	private String position;
	private double latitude=0;
	private double longtitude=0;
	private int status=0;


	@Override
	public void onCreate() {
		super.onCreate();
		context=getApplicationContext();
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * 存放位置信息
	 * @return
	 */

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongtitude() {
		return longtitude;
	}

	public void setLongtitude(double longtitude) {
		this.longtitude = longtitude;
	}

	//吃药提醒用来暂时存放时间列表
	public ArrayList<String> getclockList() {
		return clocklist;
	}

	public void setclockList(String mString) {
		clocklist.add(mString);
	}
   //传感器
//	public ArrayList<String> getcgqList() {
//		return cgqlist;
//	}
//
//	public void setcgqList(String mString) {
//		cgqlist.add(mString);
//	}

	public void deleteList(){
		clocklist.clear();
	}


	public static Context getContext(){
		return context;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLoginUserName() {
		return login_username;
	}

	public void setLoginUserName(String login_username) {
		this.login_username = login_username;
	}

	public String getrealname() {
		return realname;
	}

	public void setrealname(String realname) {
		this.realname = realname;
	}

	public String getcellphone1() {
		return cellphone1;
	}

	public void setcellphone1(String cellphone1) {
		this.cellphone1 = cellphone1;
	}

	public String getcellphone2() {
		return cellphone2;
	}

	public void setcellphone2(String cellphone2) {
		this.cellphone2 = cellphone2;
	}

	public String getcellphone3() {
		return cellphone3;
	}

	public void setcellphone3(String cellphone3) {
		this.cellphone3 = cellphone3;
	}

	public boolean getpoision_flag() {
		return poision_flag;
	}

	public void setpoision_flag(boolean poision_flag) {
		this.poision_flag = poision_flag;
	}

	public boolean getislogin() {
		return islogin;
	}

	public void setislogin(boolean islogin) {
		this.islogin = islogin;
	}

	public boolean getisIsopenDD() {
		return isopenDD;
	}

	public void setIsopenDD(boolean isopenDD) {
		this.isopenDD = isopenDD;
	}

//	public boolean getisMsgclick() {
//		return isMsgclick;
//	}
//
//	public void setMsgclick(boolean isMsgclick) {
//		this.isMsgclick = isMsgclick;
//	}

	public boolean isDistanceshow() {
		return distanceshow;
	}

	public void setDistanceshow(boolean distanceshow) {
		this.distanceshow = distanceshow;
	}
	public boolean getIsclockset() {
		return isclockset;
	}

	public void setIsclockset(boolean isclockset) {
		this.isclockset = isclockset;
	}


	public boolean getIsupdateData() {
		return isupdateData;
	}

	public void setIsupdateData(boolean isupdateData) {
		this.isupdateData = isupdateData;
	}

	public int getMdistance() {
		return mdistance;
	}

	public void setMdistance(int mdistance) {
		this.mdistance = mdistance;
	}

	public synchronized static BaseApplication getInstance() {
		if (null == instance) {
			instance = new BaseApplication();
		}
		return instance;
	}

	public void addActivity(Activity activity) {
		mList.add(activity);
	}

	public void exit() {
		try {
			for (Activity activity : mList) {
				if (activity != null)
					activity.finish();
			}
//			Intent intent = new Intent(this,DistanceService.class);
//			stopService(intent);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.exit(0);
		}
	}

}
