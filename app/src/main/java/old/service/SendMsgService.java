package old.service;


import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class SendMsgService extends Service {

	private Context context=SendMsgService.this;;

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		context = SendMsgService.this;
		super.onCreate();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		Log.e("---启动发消息服务---", "--开始发---");
		new SendMessage().sendMessage(context);
		Toast.makeText(context, "已经发送求救短信。", Toast.LENGTH_SHORT).show();
		// Intent intent = new Intent(this,SendMsgService.class);
		// stopService(intent);

//		Log.e("---开启警报服务---", "---开启---");
//		Intent Soundintent = new Intent("com.example.warning.SOUND");
//		sendBroadcast(Soundintent);
		Log.e("----发送完成---", "--！！！---");
		stopSelf();
		Log.e("----关闭发消息服务", "---关闭---");

		// new Thread(new Runnable(){
		//
		// @Override
		// public void run() {
		// // TODO Auto-generated method stub
		//

		return super.onStartCommand(intent, flags, startId);
	}


	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();

	}

}