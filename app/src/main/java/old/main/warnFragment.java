package old.main;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ygh.falltestprogram.R;

import loading.BaseApplication;
import old.service.JbSoundService;
import old.service.SendLocationService;
import tools.AlertDialog;
import tools.PlayTsSound;


public class warnFragment extends FragmentActivity {

	private ImageView btn_warn_exit;
	private TextView millness;
	private TextView mways;
	private TextView mphone;
	private SharedPreferences pref;
	private SharedPreferences pref1;
	private String mcellphone1;
	private Context context = warnFragment.this;
	private BaseApplication mApplication;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sos_warn_fragment);
		initView();
		final Window win = getWindow();
		mApplication = (BaseApplication) getApplication();
		win.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
//	            | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD  
				| WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
				| WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
		Intent startsoundIntent = new Intent(warnFragment.this,
				JbSoundService.class);
		startService(startsoundIntent);

		mApplication.setStatus(0);
		Intent locationintent1 = new Intent(warnFragment.this,
				SendLocationService.class);
		startService(locationintent1);
//		new SendMessage().sendMessage(context);
//		Toast.makeText(context, "已经发送求救短信。", Toast.LENGTH_SHORT).show();

	}

	private void initView() {
		pref = getSharedPreferences("service", MODE_PRIVATE);
		pref1 = getSharedPreferences("user", MODE_PRIVATE);
		mcellphone1 = pref1.getString("cellphone1", "");
		mphone = (TextView) findViewById(R.id.txt_cellphone);
		millness = (TextView) findViewById(R.id.txt_illness);
		mways = (TextView) findViewById(R.id.txt_way);
		mphone.setText(" " + mcellphone1 + "\n (点击拨打)");
		if (pref.getString("illness", "") != null) {
			millness.setText(pref.getString("illness", ""));
		}
		if (pref.getString("saveway", "") != null) {
			mways.setText(pref.getString("saveway", ""));
		}
		mphone.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (!mcellphone1.equals("")) {
					Intent intent = new Intent(Intent.ACTION_CALL, // 创建一个意图
							Uri.parse("tel:" + mcellphone1));
					if (ActivityCompat.checkSelfPermission(warnFragment.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
						// TODO: Consider calling
						//    ActivityCompat#requestPermissions
						// here to request the missing permissions, and then overriding
						//   public void onRequestPermissionsResult(int requestCode, String[] permissions,
						//                                          int[] grantResults)
						// to handle the case where the user grants the permission. See the documentation
						// for ActivityCompat#requestPermissions for more details.
						return;
					}
					startActivity(intent);
				}
			}
		});
		btn_warn_exit=(ImageView)findViewById(R.id.btn_warn_exit);
		btn_warn_exit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				new PlayTsSound(context, R.raw.quit);
				Intent intent=new Intent(warnFragment.this, JbSoundService.class);
				stopService(intent);
//				//重启服务
//				Intent intent1=new Intent(warnFragment.this,FallDecisionService.class);
//				stopService(intent1);
//				startService(intent1);
				finish();
			}
		});
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			new AlertDialog(context).builder().setTitle("警告")
					.setMsg("确定要关闭本页面吗？")
					.setPositiveButton("取消", new OnClickListener() {
						@Override
						public void onClick(View v) {
							new PlayTsSound(context, R.raw.cancel);
						}
					}).setNegativeButton("确定", 0, new OnClickListener() {
				@Override
				public void onClick(View v) {
					new PlayTsSound(context, R.raw.sure);
					Intent intent=new Intent(warnFragment.this, JbSoundService.class);
					stopService(intent);
//							//重启服务
//							Intent intent1=new Intent(warnFragment.this,FallDecisionService.class);
//							startService(intent1);
					finish();
				}
			}).show();
			// return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
