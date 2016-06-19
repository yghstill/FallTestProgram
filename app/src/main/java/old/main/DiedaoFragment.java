package old.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.ygh.falltestprogram.R;

import old.service.FallDecisionService;
import old.service.SendMsgService;
import old.service.WarningSoundService;
import tools.AlertDialog;
import tools.PlayTsSound;

public class DiedaoFragment extends FragmentActivity {
	private ImageView mtimeView;
	private ImageView mcancel;
	private ImageView msure;
	private static final int INIT_TIME = 0;
	private boolean isclickcancel = false;
	private Context context = DiedaoFragment.this;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.diedaofragment);
		initView();
		final Window win = getWindow();
		win.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
//	            | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD  
				| WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
				| WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

		Intent startsoundIntent = new Intent(DiedaoFragment.this,
				WarningSoundService.class);
		startService(startsoundIntent);
		mHandler.sendEmptyMessage(INIT_TIME);
	}

	private void initView() {
		mtimeView = (ImageView) findViewById(R.id.sos_time_diedao);
		mcancel = (ImageView) findViewById(R.id.diedao_btn_cancel);
		msure = (ImageView) findViewById(R.id.diedao_btn_sure);
		mcancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				new PlayTsSound(context, R.raw.cancel);
				isclickcancel = true;
				Intent stopIntent = new Intent(DiedaoFragment.this,
						WarningSoundService.class);
				stopService(stopIntent);
				// 重启服务
				Intent intent1 = new Intent(DiedaoFragment.this,
						FallDecisionService.class);
				stopService(intent1);
				startService(intent1);
				finish();

			}
		});

		msure.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// Intent startsoundIntent = new Intent(sosFragment.this,
				// JbSoundService.class);
				// startService(startsoundIntent);
				new PlayTsSound(context, R.raw.sure);
				isclickcancel = true;
				Intent startIntent = new Intent(DiedaoFragment.this,
						SendMsgService.class);
				startService(startIntent);
				// 停止提示音
				Intent stopIntent = new Intent(DiedaoFragment.this,
						WarningSoundService.class);
				stopService(stopIntent);
				// 重启服务
				Intent intent1 = new Intent(DiedaoFragment.this,
						FallDecisionService.class);
				stopService(intent1);
				startService(intent1);
				// 跳转
				Intent intent = new Intent(DiedaoFragment.this,
						warnFragment.class);
				startActivity(intent);
				finish();
				// warnFragment fragment=new warnFragment();
				// fragment.show(getSupportFragmentManager(), null);
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
					isclickcancel = true;
					Intent stopIntent = new Intent(DiedaoFragment.this,
							WarningSoundService.class);
					stopService(stopIntent);
					// 重启服务
					Intent intent1 = new Intent(DiedaoFragment.this,
							FallDecisionService.class);
					stopService(intent1);
					startService(intent1);
					finish();
				}
			}).show();
			// return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		isclickcancel = true;
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
						mtimeView.setImageResource(R.drawable.sos_19);
						mHandler.sendEmptyMessageDelayed(2, 1000);
						break;
					} else {
						Log.e("--停止计时--", "----！！！！！！");
						break;
					}
				case 2:
					if (!isclickcancel) {
						mtimeView.setImageResource(R.drawable.sos_18);
						mHandler.sendEmptyMessageDelayed(3, 1000);
						break;
					} else {
						Log.e("--停止计时--", "----！！！！！！");
						break;
					}
				case 3:
					if (!isclickcancel) {
						mtimeView.setImageResource(R.drawable.sos_17);
						mHandler.sendEmptyMessageDelayed(4, 1000);
						break;
					} else {
						Log.e("--停止计时--", "----！！！！！！");
						break;
					}
				case 4:
					if (!isclickcancel) {
						mtimeView.setImageResource(R.drawable.sos_16);
						mHandler.sendEmptyMessageDelayed(5, 1000);
						break;
					} else {
						Log.e("--停止计时--", "----！！！！！！");
						break;
					}
				case 5:
					if (!isclickcancel) {
						mtimeView.setImageResource(R.drawable.sos_15);
						mHandler.sendEmptyMessageDelayed(6, 1000);
						break;
					} else {
						Log.e("--停止计时--", "----！！！！！！");
						break;
					}
				case 6:
					if (!isclickcancel) {
						mtimeView.setImageResource(R.drawable.sos_14);
						mHandler.sendEmptyMessageDelayed(7, 1000);
						break;
					} else {
						Log.e("--停止计时--", "----！！！！！！");
						break;
					}
				case 7:
					if (!isclickcancel) {
						mtimeView.setImageResource(R.drawable.sos_13);
						mHandler.sendEmptyMessageDelayed(8, 1000);
						break;
					} else {
						Log.e("--停止计时--", "----！！！！！！");
						break;
					}
				case 8:
					if (!isclickcancel) {
						mtimeView.setImageResource(R.drawable.sos_12);
						mHandler.sendEmptyMessageDelayed(9, 1000);
						break;
					} else {
						Log.e("--停止计时--", "----！！！！！！");
						break;
					}
				case 9:
					if (!isclickcancel) {
						mtimeView.setImageResource(R.drawable.sos_11);
						mHandler.sendEmptyMessageDelayed(10, 1000);
						break;
					} else {
						Log.e("--停止计时--", "----！！！！！！");
						break;
					}
				case 10:
					if (!isclickcancel) {
						mtimeView.setImageResource(R.drawable.sos_10);
						mHandler.sendEmptyMessageDelayed(11, 1000);
						break;
					} else {
						Log.e("--停止计时--", "----！！！！！！");
						break;
					}
				case 11:
					if (!isclickcancel) {
						mtimeView.setImageResource(R.drawable.sos_9);
						mHandler.sendEmptyMessageDelayed(12, 1000);
						break;
					} else {
						Log.e("--停止计时--", "----！！！！！！");
						break;
					}
				case 12:
					if (!isclickcancel) {
						mtimeView.setImageResource(R.drawable.sos_8);
						mHandler.sendEmptyMessageDelayed(13, 1000);
						break;
					} else {
						Log.e("--停止计时--", "----！！！！！！");
						break;
					}
				case 13:
					if (!isclickcancel) {
						mtimeView.setImageResource(R.drawable.sos_7);
						mHandler.sendEmptyMessageDelayed(14, 1000);
						break;
					} else {
						Log.e("--停止计时--", "----！！！！！！");
						break;
					}
				case 14:
					if (!isclickcancel) {
						mtimeView.setImageResource(R.drawable.sos_6);
						mHandler.sendEmptyMessageDelayed(15, 1000);
						break;
					} else {
						Log.e("--停止计时--", "----！！！！！！");
						break;
					}
				case 15:
					if (!isclickcancel) {
						mtimeView.setImageResource(R.drawable.sos_5);
						mHandler.sendEmptyMessageDelayed(16, 1000);
						break;
					} else {
						Log.e("--停止计时--", "----！！！！！！");
						break;
					}
				case 16:
					if (!isclickcancel) {
						mtimeView.setImageResource(R.drawable.sos_4);
						mHandler.sendEmptyMessageDelayed(17, 1000);
						break;
					} else {
						Log.e("--停止计时--", "----！！！！！！");
						break;
					}
				case 17:
					if (!isclickcancel) {
						mtimeView.setImageResource(R.drawable.sos_3);
						mHandler.sendEmptyMessageDelayed(18, 1000);
						break;
					} else {
						Log.e("--停止计时--", "----！！！！！！");
						break;
					}
				case 18:
					if (!isclickcancel) {
						mtimeView.setImageResource(R.drawable.sos_2);
						mHandler.sendEmptyMessageDelayed(19, 1000);
						break;
					} else {
						Log.e("--停止计时--", "----！！！！！！");
						break;
					}
				case 19:
					if (!isclickcancel) {
						mtimeView.setImageResource(R.drawable.sos_1);
						mHandler.sendEmptyMessageDelayed(20, 1000);
						break;
					} else {
						Log.e("--停止计时--", "----！！！！！！");
						break;
					}
				case 20:
					// Intent startsoundIntent = new Intent(sosFragment.this,
					// JbSoundService.class);
					// startService(startsoundIntent);
					// 发消息
					Intent startIntent = new Intent(DiedaoFragment.this,
							SendMsgService.class);
					startService(startIntent);
					// 停止提示音
					Intent stopIntent = new Intent(DiedaoFragment.this,
							WarningSoundService.class);
					stopService(stopIntent);
					// 重启服务
					Intent intent1 = new Intent(DiedaoFragment.this,
							FallDecisionService.class);
					stopService(intent1);
					startService(intent1);
					Intent intent = new Intent(DiedaoFragment.this,
							warnFragment.class);
					startActivity(intent);
					finish();
					break;
				default:
					break;
			}
		};
	};

}