package tools;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.ygh.falltestprogram.R;

import org.apache.http.message.BasicNameValuePair;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
	public static void showLongToast(Context context, String pMsg) {
		Toast.makeText(context, pMsg, Toast.LENGTH_LONG).show();
	}

	public static void showShortToast(Context context, String pMsg) {
		Toast.makeText(context, pMsg, Toast.LENGTH_SHORT).show();
	}


	/**
	 * 结束Activity
	 * 
	 * @param activity
	 */
	public static void finish(Activity activity) {
		activity.finish();
		activity.overridePendingTransition(R.anim.push_right_in,
				R.anim.push_right_out);
	}

	/**
	 * 启动Activity
	 * 
	 * @param activity
	 * @param cls
	 * @param name
	 */
	public static void start_Activity(Activity activity, Class<?> cls,
			BasicNameValuePair... name) {
		Intent intent = new Intent();
		intent.setClass(activity, cls);
		if (name != null)
			for (int i = 0; i < name.length; i++) {
				intent.putExtra(name[i].getName(), name[i].getValue());
			}
		activity.startActivity(intent);
		activity.overridePendingTransition(R.anim.push_left_in,
				R.anim.push_left_out);

	}

	/**
	 * �ж��Ƿ�������
	 */
	public static boolean isNetworkAvailable(Context context) {
		if (context.checkCallingOrSelfPermission(Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
			return false;
		} else {
			ConnectivityManager connectivity = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);

			if (connectivity == null) {
				Log.w("Utility", "couldn't get connectivity manager");
			} else {
				NetworkInfo[] info = connectivity.getAllNetworkInfo();
				if (info != null) {
					for (int i = 0; i < info.length; i++) {
						if (info[i].isAvailable()) {
							Log.d("Utility", "network is available");
							return true;
						}
					}
				}
			}
		}
		Log.d("Utility", "network is not available");
		return false;
	}



	/**
	 * �Ƴ�SharedPreference
	 * 
	 * @param context
	 * @param key
	 */
	public static final void RemoveValue(Context context, String key) {
		Editor editor = getSharedPreference(context).edit();
		editor.remove(key);
		boolean result = editor.commit();
		if (!result) {
			Log.e("�Ƴ�Shared", "save " + key + " failed");
		}
	}

	private static final SharedPreferences getSharedPreference(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context);
	}

	/**
	 * ��ȡSharedPreference ֵ
	 * 
	 * @param context
	 * @param key
	 * @return
	 */
	public static final String getValue(Context context, String key) {
		return getSharedPreference(context).getString(key, "");
	}

	public static final Boolean getBooleanValue(Context context, String key) {
		return getSharedPreference(context).getBoolean(key, false);
	}

	public static final void putBooleanValue(Context context, String key,
			boolean bl) {
		Editor edit = getSharedPreference(context).edit();
		edit.putBoolean(key, bl);
		edit.commit();
	}

	public static final int getIntValue(Context context, String key) {
		return getSharedPreference(context).getInt(key, 0);
	}

	public static final long getLongValue(Context context, String key,
			long default_data) {
		return getSharedPreference(context).getLong(key, default_data);
	}

	public static final boolean putLongValue(Context context, String key,
			Long value) {
		Editor editor = getSharedPreference(context).edit();
		editor.putLong(key, value);
		return editor.commit();
	}

	public static final Boolean hasValue(Context context, String key) {
		return getSharedPreference(context).contains(key);
	}

	/**
	 * ����SharedPreference ֵ
	 * 
	 * @param context
	 * @param key
	 * @param value
	 */
	public static final boolean putValue(Context context, String key,
			String value) {
		value = value == null ? "" : value;
		Editor editor = getSharedPreference(context).edit();
		editor.putString(key, value);
		boolean result = editor.commit();
		if (!result) {
			return false;
		}
		return true;
	}

	/**
	 * ����SharedPreference ֵ
	 * 
	 * @param context
	 * @param key
	 * @param value
	 */
	public static final boolean putIntValue(Context context, String key,
			int value) {
		Editor editor = getSharedPreference(context).edit();
		editor.putInt(key, value);
		boolean result = editor.commit();
		if (!result) {
			return false;
		}
		return true;
	}

	public static Date stringToDate(String str) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		Date date = null;
		try {
			// Fri Feb 24 00:00:00 CST 2012
			date = format.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * ��֤����
	 * 
	 * @param email
	 * @return
	 */
	public static boolean isEmail(String email) {
		String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(email);

		return m.matches();
	}

	/**
	 * ��֤�ֻ���
	 * 
	 * @param mobiles
	 * @return
	 */
	public static boolean isMobileNO(String mobiles) {
		Pattern p = Pattern
				.compile("^((13[0-9])|(15[^4,\\D])|(17[^4,\\D])|(18[0-9]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}

	/**
	 * ��֤�Ƿ�������
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumber(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher match = pattern.matcher(str);
		if (match.matches() == false) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * ��ȡ�汾��
	 * 
	 * @return ��ǰӦ�õİ汾��
	 */
	public static String getVersion(Context context) {
		try {
			PackageManager manager = context.getPackageManager();
			PackageInfo info = manager.getPackageInfo(context.getPackageName(),
					0);
			String version = info.versionName;
			return version;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	private static float sDensity = 0;

	/**
	 * DPת��Ϊ����
	 * 
	 * @param context
	 * @param nDip
	 * @return
	 */
	public static int dipToPixel(Context context, int nDip) {
		if (sDensity == 0) {
			final WindowManager wm = (WindowManager) context
					.getSystemService(Context.WINDOW_SERVICE);
			DisplayMetrics dm = new DisplayMetrics();
			wm.getDefaultDisplay().getMetrics(dm);
			sDensity = dm.density;
		}
		return (int) (sDensity * nDip);
	}

}
