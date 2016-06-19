package old.main.medicine;

import android.annotation.SuppressLint;
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
import android.widget.TimePicker;

import com.example.ygh.falltestprogram.R;

import java.util.Calendar;
import java.util.TimeZone;

import loading.BaseApplication;

public class DateDialogFragment extends DialogFragment implements
		OnClickListener {
	private FragmentActivity mActivity;
	private TimePicker mTimePicker;
	private int mHour = -1;
	private int mMinute = -1;
	public static final long DAY = 1000L * 60 * 60 * 24;
	private SharedPreferences pref;
	private SharedPreferences.Editor editor;
	private BaseApplication mApplication;

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		mActivity = getActivity();
		View inflate = inflater.inflate(R.layout.fragment_dialog_date, null);
		mApplication = (BaseApplication) mActivity.getApplication();
		initDate();
		inflate.findViewById(R.id.btn_neg_date).setOnClickListener(this);
		inflate.findViewById(R.id.btn_pos_date).setOnClickListener(this);
		mTimePicker = (TimePicker) inflate.findViewById(R.id.timePicker);
		mTimePicker.setCurrentHour(mHour);
		mTimePicker.setCurrentMinute(mMinute);
		mTimePicker
				.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {

					@Override
					public void onTimeChanged(TimePicker view, int hourOfDay,
							int minute) {

						mHour = hourOfDay;
						mMinute = minute;
					}
				});
		getDialog().getWindow().setBackgroundDrawable(
				new ColorDrawable(Color.TRANSPARENT));
		getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		return inflate;

	}
	
	private void initDate(){
		pref=mActivity.getSharedPreferences("service", Context.MODE_PRIVATE);
		editor=pref.edit();
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		if(mHour == -1 && mMinute == -1) {
			mHour = calendar.get(Calendar.HOUR_OF_DAY);
			mMinute = calendar.get(Calendar.MINUTE);
		}
	}

	@Override
	public void onClick(View v) {
		
//		dismiss();
		switch (v.getId()) {
		case R.id.btn_neg_date: // ȷ��
			mApplication.setIsclockset(true);
			Intent intent=new Intent(mActivity,MedicineNew.class);
			intent.putExtra("mHour", mHour);
			intent.putExtra("mMinute", mMinute);
			startActivity(intent);
			mActivity.finish();
			break;
		case R.id.btn_pos_date: // ȡ��
			mActivity.finish();
			break;

		default:
			mActivity.finish();
			break;
		}
	}

}
