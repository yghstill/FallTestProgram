package old.service;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import tools.AlertDialog;

public class AlramClockReceiver extends BroadcastReceiver {
	// private SharedPreferences pref1;

	@Override
	public void onReceive(final Context context, Intent intent) {
		// pref1 = context.getSharedPreferences("service",
		// context.MODE_PRIVATE);
		Intent startsoundIntent = new Intent(context, WarningSoundService.class);
		context.startService(startsoundIntent);
		String medicine = intent.getStringExtra("medicine");
		Log.e("=====yao----", medicine);
		new AlertDialog(context).builder().setTypeSYS(true)
				.setCancelable(false).setTitle("提示")
				.setMsg("您该吃" + medicine + "药了，请及时服用！")
				.setPositiveButton("确定", new OnClickListener() {
					@Override
					public void onClick(View v) {
						// 停止提示音
						Intent stopIntent = new Intent(context,
								WarningSoundService.class);
						context.stopService(stopIntent);
					}
				}).show();

	}
}
