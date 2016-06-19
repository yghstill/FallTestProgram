package old.service;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.telephony.SmsManager;
import android.util.Log;

public class SendMessage {
	private SharedPreferences pref;
	private SharedPreferences pref1;
	private IntentFilter sendFilter;
//	private SendStatusReceiver sendStatusReceiver;

	@SuppressWarnings("static-access")
	public void sendMessage(Context context) {
		pref = context.getSharedPreferences("service", context.MODE_PRIVATE);
		pref1 = context.getSharedPreferences("user", context.MODE_PRIVATE);
		String mMessage = pref.getString("DDmessage", "");
		String mcellphone1 = pref1.getString("cellphone1", "");
		String mcellphone2 = pref1.getString("cellphone2", "");
		String mcellphone3 = pref1.getString("cellphone3", "");
		sendFilter=new IntentFilter();
        sendFilter.addAction("SENT_SMS_ACTION");
//        sendStatusReceiver=new SendStatusReceiver();
//        context.registerReceiver(sendStatusReceiver,sendFilter);
        SmsManager smsManager=SmsManager.getDefault();
		Intent sentIntent=new Intent("SENT_SMS_ACTION");
		PendingIntent pi=PendingIntent.getBroadcast(context,0,sentIntent,0);
		smsManager.sendTextMessage(mcellphone1, null, mMessage, pi, null);
		smsManager.sendTextMessage(mcellphone2, null, mMessage, null, null);
		smsManager.sendTextMessage(mcellphone3, null, mMessage, null, null);
		Log.e("��Ϣ ���ݣ�", mMessage);
//		context.unregisterReceiver(sendStatusReceiver);
	}
	
	//���;��뾯����Ϣ
	@SuppressWarnings("static-access")
	public void senddisMessage(Context context) {
		pref = context.getSharedPreferences("service", context.MODE_PRIVATE);
		pref1 = context.getSharedPreferences("user", Context.MODE_PRIVATE);
		String realname = pref1.getString("realname", "");
		String mMessage = realname+"�Ѿ���ҳ���"+pref.getInt("maxdistance", 5000)+"�ף����ע��";
		String mcellphone1 = pref1.getString("cellphone1", "");
		String mcellphone2 = pref1.getString("cellphone2", "");
		String mcellphone3 = pref1.getString("cellphone3", "");
		sendFilter=new IntentFilter();
        sendFilter.addAction("SENT_SMS_ACTION");
//        sendStatusReceiver=new SendStatusReceiver();
//        context.registerReceiver(sendStatusReceiver,sendFilter);
        SmsManager smsManager=SmsManager.getDefault();
		Intent sentIntent=new Intent("SENT_SMS_ACTION");
		PendingIntent pi=PendingIntent.getBroadcast(context,0,sentIntent,0);
		smsManager.sendTextMessage(mcellphone1, null, mMessage, pi, null);
		smsManager.sendTextMessage(mcellphone2, null, mMessage, null, null);
		smsManager.sendTextMessage(mcellphone3, null, mMessage, null, null);
		
	}

//	private class SendStatusReceiver extends BroadcastReceiver {
//		private static final int RESULT_OK = 1;
//
//		@Override
//		public void onReceive(Context context, Intent intent) {
//			if (getResultCode() == RESULT_OK) {
//				Toast.makeText(context, "�Ѿ�������ȶ��š�", Toast.LENGTH_LONG)
//						.show();
//			} else {
//				Toast.makeText(context, "����ʧ�ܣ������ֻ�������", Toast.LENGTH_LONG)
//						.show();
//			}
//		}
//	}

}
