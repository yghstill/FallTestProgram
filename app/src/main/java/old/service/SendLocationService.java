package old.service;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.conn.ConnectTimeoutException;

import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import internet.old.ConnectService;
import internet.old.ConnectServiceImp;
import loading.BaseApplication;

public class SendLocationService extends Service {
	private static final int FLAG_SEND_SUCCESS = 1;
	private static final String MSG_SEND_ERROR = "位置上传出错。";
	private static final String MSG_SEND_SUCCESS = "位置上传成功。";
	public static final String MSG_SERVER_ERROR = "请求服务器错误。";
	public static final String MSG_SERVER_TIMEOUT = "请求服务器超时。";
	public static final String MSG_RESPONCE_TIMEOUT = "服务器响应超时。";
	private BaseApplication mApplication;
	private ConnectService connectService = new ConnectServiceImp();

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		mApplication = (BaseApplication) getApplication();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					// 获取系统时间
					String currentTime = DateFormat.format(
							"yyyy-MM-dd hh:mm:ss", new Date()).toString();
					SimpleDateFormat sdf = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss", Locale.getDefault());
					currentTime = sdf.format(new Date());

					if (mApplication.getLatitude() != 0) {
						Log.e("状态值：：：：：", mApplication.getStatus()+"-----");
						Log.e("位置：：：：：", mApplication.getPosition()+"-----");
						Log.e("经纬：：：：：", mApplication.getLatitude()+"---"+mApplication.getLongtitude());
						connectService.sendMessage(
								mApplication.getLoginUserName(), currentTime,
								mApplication.getPosition(),
								mApplication.getLatitude(),
								mApplication.getLongtitude(), mApplication.getStatus());
						mHandler.sendEmptyMessage(FLAG_SEND_SUCCESS);
					}
//					Thread.sleep(600000);
				} catch (ConnectTimeoutException e) {
					e.printStackTrace();
					Message msg = new Message();
					Bundle data = new Bundle();
					data.putSerializable("ErrorMsg", MSG_SERVER_TIMEOUT);
					msg.setData(data);
					mHandler.sendMessage(msg);
				} catch (SocketTimeoutException e) {
					e.printStackTrace();
					Message msg = new Message();
					Bundle data = new Bundle();
					data.putSerializable("ErrorMsg", MSG_RESPONCE_TIMEOUT);
					msg.setData(data);
					mHandler.sendMessage(msg);
				}  catch (Exception e) {
					e.printStackTrace();
					Message msg = new Message();
					Bundle data = new Bundle();
					data.putSerializable("ErrorMsg", MSG_SEND_ERROR);
					msg.setData(data);
					mHandler.sendMessage(msg);
				}

			}
		}).start();

		stopSelf();
		return super.onStartCommand(intent, flags, startId);
	}

	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			int flag = msg.what;
			switch (flag) {
				case 0:
					String errorMsg = (String) msg.getData().getSerializable(
							"ErrorMsg");
					showTip(errorMsg);
					break;
				case FLAG_SEND_SUCCESS:
					if(mApplication.getStatus()==1){
						mApplication.setStatus(0);
					}
					showTip("跌倒位置发送成功！！！");
					break;

				default:
					break;
			}
		}
	};

	private void showTip(String str) {
		Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
		// if (str == FLAG_SEND_SUCCESS) {
		// }

	}

}
