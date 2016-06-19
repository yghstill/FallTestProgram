package old.service;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Builder;
import android.util.Log;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.example.ygh.falltestprogram.Old_MainActivity;
import com.example.ygh.falltestprogram.R;

import loading.BaseApplication;

public class DistanceService extends Service {
	private SharedPreferences pref;
	private SharedPreferences pref1;
	private SharedPreferences.Editor editor;
	private BaseApplication mApplication;
	private static final double EARTH_RADIUS = 6378137.0;// 地球半径
	private double mLatitude;
	private double mLongtitude;
	private double nowLatitude;
	private double nowLongtitude;
	private MyLocationListener mLocationListener;
	private LocationClient mLocationClient;
	private int mdistance;
	private static final int FLAG_SUCCESS = 1;
	private Context context = DistanceService.this;

	/** Notification构造器 */
	private NotificationCompat.Builder mBuilder;
	/** Notification的ID */
	private static int notifyId = 100;
	public static NotificationManager mNotificationManager;

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressLint("CommitPrefEdits")
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		mApplication = (BaseApplication) getApplication();
		pref = getSharedPreferences("user", MODE_PRIVATE);
		pref1 = getSharedPreferences("service", MODE_PRIVATE);
		editor = pref1.edit();
		// mNotificationManager = (NotificationManager)
		// getSystemService(NOTIFICATION_SERVICE);
		initpoision();

	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		new Thread(new Runnable() {
			@Override
			public void run() {

					try {
						if (pref.getFloat("mLatitude", 0) == 0&&(pref.getString("myposition", "")!=null)) {
							mdistance = 0;
						} else {
							mLatitude = pref.getFloat("mLatitude", 0);
							mLongtitude = pref.getFloat("mLongtitude", 0);

							if (nowLongtitude != 0&&isNetworkAvailable(mApplication.getApplicationContext())) {
								mdistance = (int) getDistance(mLongtitude,
										mLatitude, nowLongtitude, nowLatitude);
							} else {
								Log.e("--位置服务没有开启网络", "无网络");
								mdistance = 0;
							}

							// showCzNotify(mdistance);
							editor.putInt("distance", mdistance);
							editor.commit();
						}

						mHandler.sendEmptyMessage(FLAG_SUCCESS);
						Thread.sleep(180000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

			}
		}).start();

		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		clearNotify();
	}

	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {

			switch (msg.what) {
				case FLAG_SUCCESS:
					mApplication.setMdistance(mdistance);
					if (mdistance > pref1.getInt("maxdistance", 5000)
							& !mApplication.isDistanceshow()) {
						Intent intent = new Intent("com.example.warning.DISTANCE");
						sendBroadcast(intent);
						mApplication.setDistanceshow(true);
						new SendMessage().senddisMessage(context);
						Toast.makeText(context, "已经发送提示短信。", Toast.LENGTH_SHORT)
								.show();

					} else {
						mApplication.setDistanceshow(false);
					}
			}
		}
	};

	// 返回单位是米
	public static double getDistance(double longitude1, double latitude1,
									 double longitude2, double latitude2) {
		double Lat1 = rad(latitude1);
		double Lat2 = rad(latitude2);
		double a = Lat1 - Lat2;
		double b = rad(longitude1) - rad(longitude2);
		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
				+ Math.cos(Lat1) * Math.cos(Lat2)
				* Math.pow(Math.sin(b / 2), 2)));
		s = s * EARTH_RADIUS;
		s = Math.round(s * 10000) / 10000;
		return s;
	}

	private static double rad(double d) {
		return d * Math.PI / 180.0;
	}

	private void initpoision() {

		mLocationClient = new LocationClient(this);
		mLocationListener = new MyLocationListener();
		mLocationClient.registerLocationListener(mLocationListener);
		LocationClientOption option = new LocationClientOption();
		option.setCoorType("bd09ll");
		option.setIsNeedAddress(true);
		option.setOpenGps(true);
		option.setScanSpan(59000);// 设置发起定位请求的间隔时间为5000ms
		mLocationClient.setLocOption(option);
		mLocationClient.start();
	}

	private class MyLocationListener implements BDLocationListener {
		@Override
		public void onReceiveLocation(BDLocation location) {
			nowLatitude = location.getLatitude();
			nowLongtitude = location.getLongitude();
		}
	}

	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (cm == null) {
		} else {
			// 如果仅仅是用来判断网络连接
			// 则可以使用 cm.getActiveNetworkInfo().isAvailable();
			NetworkInfo[] info = cm.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * 通知栏
	 */
	protected void showCzNotify(int distancd) {
		// TODO Auto-generated method stub
		mBuilder = new Builder(context);
		// PendingIntent 跳转动作
		Intent intent = new Intent(this, Old_MainActivity.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
				intent, 0);
		mBuilder.setSmallIcon(R.drawable.ic_menu_gallery)
				// .setTicker("监听服务已开启")
				.setContentTitle("监听服务正在运行")
				.setContentText(" 状态：正常       离家距离：" + distancd)// mApplication.getMdistance());
				.setContentIntent(pendingIntent);
		Notification mNotification = mBuilder.build();

		// 设置通知 消息 图标
		mNotification.icon = R.drawable.ic_menu_gallery;
		// 在通知栏上点击此通知后自动清除此通知
		mNotification.flags = Notification.FLAG_ONGOING_EVENT;// FLAG_ONGOING_EVENT
		// 在顶部常驻，可以调用下面的清除方法去除
		// FLAG_AUTO_CANCEL
		// 点击和清理可以去调
		// 设置显示通知时的默认的发声、震动、Light效果
		mNotification.defaults = Notification.DEFAULT_SOUND;
		// 设置发出消息的内容
		// mNotification.tickerText = "监听服务已开启";
		// 设置发出通知的时间
		mNotification.when = System.currentTimeMillis();
		mNotificationManager.notify(notifyId, mNotification);
	}

	public static void clearNotify() {
		mNotificationManager.cancel(notifyId);// 删除一个特定的通知ID对应的通知
	}

}
