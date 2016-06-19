package old.service;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.view.View.OnClickListener;

import loading.BaseApplication;
import tools.AlertDialog;

public class DistanceReceiver extends BroadcastReceiver {
	private BaseApplication mApplication;
	private SharedPreferences pref1;
	@Override
	public void onReceive(final Context context, Intent arg1) {
		// TODO Auto-generated method stub
		mApplication=(BaseApplication) context.getApplicationContext();
		pref1 = context.getSharedPreferences("service", context.MODE_PRIVATE);
		new AlertDialog(context).builder().setTitle("警告！！！").setTypeSYS(true)
				.setCancelable(false).setMsg("您已经离家超过"+pref1.getInt("maxdistance", 5000)+"米了，已将信息发送到紧急联系人，请不要走太远！")
				.setNegativeButton("确定", 0, new OnClickListener() {
					@Override
					public void onClick(View v) {

						mApplication.setDistanceshow(true);
					}
				}).show();
	}

}
