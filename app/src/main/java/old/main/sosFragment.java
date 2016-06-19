package old.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.example.ygh.falltestprogram.R;

import old.service.SendMsgService;
import old.service.WarningSoundService;
import tools.PlayTsSound;


public class sosFragment extends FragmentActivity {
	// private View layout;
	private ImageView mtime;
	private ImageView mcancel;
	private ImageView msure;
	private static final int INIT_TIME = 0;
	private boolean isclickcancel = false;
	private Context context=sosFragment.this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sos_fragment);
		mtime = (ImageView) findViewById(R.id.sos_time_count);
		mcancel = (ImageView) findViewById(R.id.sos_btn_cancel);
		msure = (ImageView) findViewById(R.id.sos_btn_sure);
		setOnListener();
		Intent startsoundIntent = new Intent(sosFragment.this,
				WarningSoundService.class);
		startService(startsoundIntent);
		mHandler.sendEmptyMessage(INIT_TIME);

	}

	private void setOnListener() {
		mcancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				isclickcancel=true;
				//停止提示音
				Intent stopIntent = new Intent(sosFragment.this,WarningSoundService.class);
				stopService(stopIntent);
				new PlayTsSound(context, R.raw.cancel);
				finish();
			}
		});
		msure.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				new PlayTsSound(context, R.raw.sure);
				isclickcancel=true;
//				Intent startsoundIntent = new Intent(sosFragment.this,
//						JbSoundService.class);
//				startService(startsoundIntent);
				//发送短信
				Intent startIntent = new Intent(sosFragment.this,
						SendMsgService.class);
				startService(startIntent);
				//停止提示音
				Intent stopIntent = new Intent(sosFragment.this,WarningSoundService.class);
				stopService(stopIntent);
				Intent intent=new Intent(sosFragment.this,warnFragment.class);
				startActivity(intent);
				finish();
//				warnFragment fragment=new warnFragment();
//				fragment.show(getSupportFragmentManager(), null);

			}
		});

	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		isclickcancel=true;
		finish();
	}

	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {

			switch (msg.what) {
				case INIT_TIME:
					if (!isclickcancel) {
						mHandler.sendEmptyMessageDelayed(1, 1000);
						break;
					} else {
						Log.e("--停止计时--", "----！！！！！！");
						break;
					}
				case 1:
					if (!isclickcancel) {
						mtime.setImageResource(R.drawable.sos_time_1_4);
						mHandler.sendEmptyMessageDelayed(2, 1000);
						break;
					} else {
						Log.e("--停止计时--", "----！！！！！！");
						break;
					}
				case 2:
					if (!isclickcancel) {
						mtime.setImageResource(R.drawable.sos_time_1_3);
						mHandler.sendEmptyMessageDelayed(3, 1000);
						break;
					} else {
						Log.e("--停止计时--", "----！！！！！！");
						break;
					}
				case 3:
					if (!isclickcancel) {
						mtime.setImageResource(R.drawable.sos_time_1_2);
						mHandler.sendEmptyMessageDelayed(4, 1000);
						break;
					} else {
						Log.e("--停止计时--", "----！！！！！！");
						break;
					}
				case 4:
					if (!isclickcancel) {
						mtime.setImageResource(R.drawable.sos_time_1_1);
						mHandler.sendEmptyMessageDelayed(5, 1000);
						break;
					} else {
						Log.e("--停止计时--", "----！！！！！！");
						break;
					}
				case 5:
					if (!isclickcancel) {
						mHandler.sendEmptyMessageDelayed(6, 1000);
						break;
					} else {
						Log.e("--停止计时--", "----！！！！！！");
						break;
					}

				case 6:
//				Intent startsoundIntent = new Intent(sosFragment.this,
//						JbSoundService.class);
//				startService(startsoundIntent);
					//发送短信
					Intent startIntent = new Intent(sosFragment.this,
							SendMsgService.class);
					startService(startIntent);
					//停止提示音
					Intent stopIntent = new Intent(sosFragment.this,WarningSoundService.class);
					stopService(stopIntent);
					Intent intent=new Intent(sosFragment.this,warnFragment.class);
					startActivity(intent);
					finish();
				default:
					break;
			}
		};
	};

}
