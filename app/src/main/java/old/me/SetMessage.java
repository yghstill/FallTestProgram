package old.me;


import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ygh.falltestprogram.R;

import loading.BaseApplication;
import tools.PlayTsSound;
import tools.Utils;

public class SetMessage extends AppCompatActivity {
	private TextView btn_sos;
	private EditText edt_sos;
	private TextView mjj_txt;
	private TextView mdd_txt;
	private TextView btn_jj;
	private TextView btn_dd;
	private Button reset_illness;
	private BaseApplication mApplication;
	private SharedPreferences pref;
	private SharedPreferences.Editor editor;
	private Context context = SetMessage.this;
	private Toolbar toolbar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.set_message);
		mApplication = (BaseApplication) getApplication();
		BaseApplication.getInstance().addActivity(this);
		initview();
		initEvent();
		mApplication.setIsclockset(false);
	}

	private void initEvent() {

			if (pref.getString("illness", "").equals("")) {
				if(!mApplication.getIsclockset()){
					IllnessDialogFragment fragment = new IllnessDialogFragment();
					fragment.show(getSupportFragmentManager(), null);
				}

			}
	}

	private void initview() {
		toolbar = (Toolbar) findViewById(R.id.toolbar);
		toolbar.setTitle("家里情况");
		setSupportActionBar(toolbar);
		toolbar.setNavigationIcon(R.mipmap.back1);

		toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				new PlayTsSound(context, R.raw.back);
				ischangemsg();
			}
		});


		// 栏1
		btn_sos = (TextView) findViewById(R.id.sos_txt_msg);
		edt_sos = (EditText) findViewById(R.id.sos_msg_edt);
		// 栏2
		btn_jj = (TextView) findViewById(R.id.qj_txt_msg);
		mjj_txt = (TextView) findViewById(R.id.qj_msg_txt);
		// 栏3
		btn_dd = (TextView) findViewById(R.id.dd_txt_msg);
		mdd_txt = (TextView) findViewById(R.id.dd_msg_txt);
		reset_illness=(Button) findViewById(R.id.reset_illness);
		//初始化数据
		pref = getSharedPreferences("service", MODE_PRIVATE);
		editor = pref.edit();
		if (!pref.getString("illness", "").equals("")) {
			edt_sos.setText("本人的病情是：" + pref.getString("illness", "")
					+ "。\n救助措施：" + pref.getString("saveway", ""));
			editor.putString("SOSshowmessage", edt_sos.getText().toString());
		}
		mjj_txt.setText("" + pref.getString("JJmessage", ""));
		mdd_txt.setText("" + pref.getString("DDmessage", ""));
		//点击监听事件
		btn_sos.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (edt_sos.getVisibility() == View.VISIBLE)
					edt_sos.setVisibility(View.GONE);
				else
					edt_sos.setVisibility(View.VISIBLE);
			}
		});
		btn_jj.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (mjj_txt.getVisibility() == View.VISIBLE)
					mjj_txt.setVisibility(View.GONE);
				else
					mjj_txt.setVisibility(View.VISIBLE);
			}
		});
		btn_dd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (mdd_txt.getVisibility() == View.VISIBLE)
					mdd_txt.setVisibility(View.GONE);
				else
					mdd_txt.setVisibility(View.VISIBLE);
			}
		});
		reset_illness.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				IllnessDialogFragment fragment = new IllnessDialogFragment();
				fragment.show(getSupportFragmentManager(), null);
			}
		});
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			ischangemsg();
		}
		return super.onKeyDown(keyCode, event);
	}

	private void ischangemsg() {
		String txtlocal = pref.getString("SOSshowmessage", "");
		if (!edt_sos.getText().toString().equals(txtlocal)) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("提示");
			builder.setMessage("要保存修改吗？");
			builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					//这里添加点击确定后的逻辑
					String saveTxt = edt_sos.getText().toString();
					editor.putString("SOSshowmessage", saveTxt);
					editor.commit();
					Utils.finish(SetMessage.this);
				}
			});
			builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					//这里添加点击确定后的逻辑
					Utils.finish(SetMessage.this);
				}
			});
			builder.create().show();
		} else {
			Utils.finish(SetMessage.this);
		}

	}

}
