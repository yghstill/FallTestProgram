package old.me;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ygh.falltestprogram.R;

import loading.BaseApplication;


public class IllnessDialogFragment extends DialogFragment {
	private FragmentActivity mActivity;
	private EditText medit01;
	private EditText medit02;
	private Button msure;
	private Button mcancel;
	private View inflate;
	private SharedPreferences pref;
	private SharedPreferences.Editor editor;
	private BaseApplication mApplication;
	@Override
	public View onCreateView(LayoutInflater inflater,
							 @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		mActivity = this.getActivity();
		inflate = inflater.inflate(R.layout.fragment_dialog_illness, null);
		mApplication = (BaseApplication) mActivity.getApplication();
		initView();
		getDialog().getWindow().setBackgroundDrawable(
				new ColorDrawable(Color.TRANSPARENT));
		getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		setCancelable(false);
		return inflate;
	}

	private void initView() {
		// TODO Auto-generated method stub
		medit01 = (EditText) inflate.findViewById(R.id.bq_edt);
		medit02 = (EditText) inflate.findViewById(R.id.way_edt);
		msure = (Button) inflate.findViewById(R.id.btn_neg_illness);
		mcancel = (Button) inflate.findViewById(R.id.btn_pos_illness);
		pref = mActivity.getSharedPreferences("service", Context.MODE_PRIVATE);
		editor = pref.edit();
		msure.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String mtxt1 = medit01.getText().toString();
				String mtxt2 = medit02.getText().toString();
				editor.putString("illness", mtxt1);
				editor.putString("saveway", mtxt2);
				editor.commit();
				Intent intent=new Intent(mActivity,SetMessage.class);
				mApplication.setIsclockset(true);
//				intent.putExtra("isShow", true);
//				intent.putExtra("mtxt1", mtxt1);
//				intent.putExtra("mtxt2", mtxt2);
				Toast.makeText(mActivity, "设置成功！", Toast.LENGTH_SHORT).show();
				startActivity(intent);
				mActivity.finish();
			}
		});

		mcancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(mActivity,SetMessage.class);
//				intent.putExtra("isShow", true);
				mApplication.setIsclockset(true);
				startActivity(intent);
				mActivity.finish();
			}
		});
	}

}
