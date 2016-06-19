package old.service;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.baidu.location.LocationClient;

import internet.old.ConnectService;
import internet.old.ConnectServiceImp;
import loading.BaseApplication;
import old.main.DiedaoFragment;
import tools.FallDecision;

public class FallDecisionService extends Service {
	private SensorManager sensorManager;
	private static final int MSG_SEND_SUCCESS = 1;
	private float xValue = 0;
	private float yValue = 0;
	private float zValue = 0;
	private BaseApplication mApplication;
	private ConnectService connectService = new ConnectServiceImp();
	private String mlocation;
	private double mLatitude;
	private double mLongtitude;
	// private MyLocationListener mLocationListener;
	private LocationClient mLocationClient;
	private static final String MSG_SEND_ERROR = "位置上传出错。";
	public static final String MSG_SERVER_ERROR = "请求服务器错误。";
	public static final String MSG_SERVER_TIMEOUT = "请求服务器超时。";
	public static final String MSG_RESPONCE_TIMEOUT = "服务器响应超时。";
	private Intent locationintent;

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		mApplication = (BaseApplication) getApplication();
		locationintent = new Intent(FallDecisionService.this,
				SendLocationService.class);
		super.onCreate();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		Sensor sensor = sensorManager
				.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		sensorManager.registerListener(listener, sensor,
				SensorManager.SENSOR_DELAY_GAME);
		final FallDecision decision = new FallDecision();
		new Thread(new Runnable() {

			@Override
			public void run() {

				// mHandler.sendEmptyMessage(MSG_SEND_SUCCESS);
				int flag = 1;
				int count = 0;
				try {
					while (true) {
						while (flag == 1) {
							Thread.sleep(40);
							flag = decision.Suspection1(xValue, yValue, zValue);
							// Log.e("--加速度信息---", "-x-" + xValue + "-y-" +
							// yValue
							// + "-z-" + zValue);
							count++;
							if (count == 15000) {
								mApplication.setStatus(0);
								startService(locationintent);
								count = 0;
								Log.e("falldecisonservice", "已经发送地理位置信息");
							}

						}
						if (flag == 0) {
							Log.e("--加速度信息---", "--发信息---");
							mApplication.setStatus(1);
							Intent locationintent1 = new Intent(FallDecisionService.this,
									SendLocationService.class);
							startService(locationintent1);
							mHandler.sendEmptyMessage(MSG_SEND_SUCCESS);
						}

					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}).start();
//		stopSelf();
		return super.onStartCommand(intent, flags, startId);
	}

	private SensorEventListener listener = new SensorEventListener() {
		@Override
		public void onSensorChanged(SensorEvent event) {
			// 加速度可能是负值，所以取它们的绝对值
			xValue = Math.abs(event.values[0]);
			yValue = Math.abs(event.values[1]);
			zValue = Math.abs(event.values[2]);
			/**
			 * 测试用
			 */
			// if (xValue > 19 || yValue > 19 & zValue > 19) {
			// // 认为用户摇动了手机
			// Toast.makeText(BaseApplication.getContext(), "检测到加速度信息",
			// Toast.LENGTH_SHORT).show();
			// // Intent intent = new Intent("com.example.warning.MSG");
			// // sendBroadcast(intent);
			// Intent intent = new Intent(FallDecisionService.this,
			// DiedaoFragment.class);
			// intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			// startActivity(intent);
			// Intent startsoundIntent = new Intent(FallDecisionService.this,
			// WarningSoundService.class);
			// startService(startsoundIntent);
			//
			// }
		}

		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {

		}
	};

	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			int flag = msg.what;
			switch (flag) {
				case MSG_SEND_SUCCESS:
					Toast.makeText(BaseApplication.getContext(), "检测到加速度信息",
							Toast.LENGTH_SHORT).show();
					// Intent intent = new Intent("com.example.warning.MSG");
					// sendBroadcast(intent);
					Intent intent = new Intent(FallDecisionService.this,
							DiedaoFragment.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(intent);
					// Intent startsoundIntent = new
					// Intent(FallDecisionService.this,
					// WarningSoundService.class);
					// startService(startsoundIntent);
//			case 2:
//				Toast.makeText(BaseApplication.getContext(), "已向服务器发送跌倒位置信息！",
//						Toast.LENGTH_SHORT).show();
//				break;

				default:
					break;
			}
		}
	};

	// private void getlocation() {
	// mLocationClient = new LocationClient(this);
	// mLocationListener = new MyLocationListener();
	// mLocationClient.registerLocationListener(mLocationListener);
	// LocationClientOption option = new LocationClientOption();
	// option.setCoorType("bd09ll");
	// option.setIsNeedAddress(true);
	// option.setOpenGps(true);
	// option.setScanSpan(500);// 设置发起定位请求的间隔时间为5000ms
	// mLocationClient.setLocOption(option);
	// mLocationClient.start(); // 开始定位
	// }
	//
	// private class MyLocationListener implements BDLocationListener {
	// @Override
	// public void onReceiveLocation(BDLocation location) {
	// mlocation = location.getAddrStr();
	// mLatitude = location.getLatitude();
	// mLongtitude = location.getLongitude();
	// }
	// }

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (sensorManager != null) {
			sensorManager.unregisterListener(listener);
		}
	}

}