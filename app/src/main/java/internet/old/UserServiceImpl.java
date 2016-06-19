package internet.old;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import login.Old_LoginActivity;
import old.me.MyMessage;


public class UserServiceImpl implements UserService {
	private static final String TAG = "UserServiceImpl";
	private SharedPreferences pref;
	private SharedPreferences.Editor editor;
	// MainActivity person=new MainActivity();

	@SuppressWarnings("static-access")
	@Override
	public void userLogin(Context context,String loginName, String loginPassword)
			throws Exception {
		Log.d(TAG, loginName);
		Log.d(TAG, loginPassword);

		// Thread.sleep(3000);

		// ����http����
		HttpClient client = new DefaultHttpClient();

		/**
		 * post/json��ֵ
		 */
		// String uri =
		// "http://10.138.108.131:8080/Test_MyService/login.action?username="
		// + loginName + "&password=" + loginPassword;
		// HttpGet get = new HttpGet(uri);
		// ��Ӧ
		// HttpResponse response = client.execute(get);
//		String uri = "http://192.168.191.1:8080/MyService/login.action";
//		String uri = "http://192.168.1.107:8080/MyService/login.action";
		String uri = "http://182.254.137.75:8080/MyService/login.action";
		HttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, 8000);
		HttpConnectionParams.setSoTimeout(httpParams, 8000);
		HttpPost post = new HttpPost(uri);
		List<NameValuePair> pair = new ArrayList<NameValuePair>();
		try {
			JSONObject obj = new JSONObject();
			obj.put("username", loginName);
			obj.put("password", loginPassword);
			// JSONObject json = new JSONObject();
			// Gson gson = new Gson();
			// String str = gson.toJson(obj);
			pair.add(new BasicNameValuePair("json", obj.toString()));
			post.setEntity(new UrlEncodedFormEntity(pair, HTTP.UTF_8));
			post.setParams(httpParams);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		HttpResponse response = client.execute(post);
		if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			// String result = EntityUtils.toString(response.getEntity(),
			// "UTF-8");
			// System.out.println(result);

			String responseStr = EntityUtils.toString(response.getEntity());
			JSONObject json = new JSONObject(responseStr).getJSONObject("json");
			String result = json.getString("result");
			Log.d(TAG, result);
			if (result.equals("success")) {
				String realname = json.getString("realname");
				int sex=json.getInt("sex");
				String cellphone1 = json.getString("cellphone1");
				String cellphone2 = json.getString("cellphone2");
				String cellphone3 = json.getString("cellphone3");
//				String myposition = json.getString("myposition");
//				double latitude = json.getDouble("latitude");
//				double longtitude = json.getDouble("longtitude");
				Log.e("---��¼��Ϣ--", "--"+json.toString());
				pref = context.getSharedPreferences("user", context.MODE_PRIVATE);
				editor=pref.edit();
				editor.putString("realname", realname);
				editor.putInt("sex", sex);
				editor.putString("cellphone1", cellphone1);
				editor.putString("cellphone2", cellphone2);
				editor.putString("cellphone3", cellphone3);
				editor.putString("myposition", "");
				editor.putFloat("mLatitude", 0);
				editor.putFloat("mLongtitude", 0);
				editor.commit();
				
			} else {
				// return false;
				throw new ServiceRulesException(Old_LoginActivity.MSG_LOGIN_FAILED);
			}

		} else {
			throw new ServiceRulesException(Old_LoginActivity.MSG_SERVER_ERROR);
		}

		// int statusCode = response.getStatusLine().getStatusCode();
		// if (statusCode != HttpStatus.SC_OK) {
		// throw new ServiceRulesException(MainActivity.MSG_SERVER_ERROR);
		// }
		// String result = EntityUtils.toString(response.getEntity(), "UTF-8");
		//
		// if (result.equals("success")) {
		//
		// } else {
		// throw new ServiceRulesException(MainActivity.MSG_LOGIN_FAILED);
		// }

	}

	@Override
	public void userResign(String loginName, String loginPassword,
			String surePassword, String realName,int sex, String cellphone1,
			String cellphone2, String cellphone3) throws Exception {

		// if(loginPassword==surePassword){
		// }else{
		// throw new ServiceRulesException(ResignActivity.MSG_RESIGN_FAILED2);
		// }

		// Thread.sleep(3000);
		// ����http����
		/**
		 * GET ��ֵ
		 */
		// String uri =
		// "http://10.138.106.30:8080/MyService/regist.action?username="
		// + loginName
		// + "&password="
		// + loginPassword
		// + "&realName="
		// + realName
		// + "&cellphone1="
		// + cellphone1
		// + "&cellphone2="
		// + cellphone2 + "&cellphone3=" + cellphone3;
		// http://10.138.108.131:8080/Test_MyService/login.action
		String uri = "http://182.254.137.75:8080/MyService/regist.action";
		HttpClient client = new DefaultHttpClient();
		HttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, 8000); 
		HttpConnectionParams.setSoTimeout(httpParams, 8000);
		HttpPost post = new HttpPost(uri);
		List<NameValuePair> pair = new ArrayList<NameValuePair>();
		try {
			JSONObject obj = new JSONObject();
			obj.put("username", loginName);
			obj.put("password", loginPassword);
			obj.put("realname", realName);
			obj.put("sex", sex); 
			obj.put("cellphone1", cellphone1);
			obj.put("cellphone2", cellphone2);
			obj.put("cellphone3", cellphone3);
			// Gson gson = new Gson();
			// String str = gson.toJson(obj);
			pair.add(new BasicNameValuePair("json", obj.toString()));
			post.setEntity(new UrlEncodedFormEntity(pair, HTTP.UTF_8));
			post.setParams(httpParams);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		HttpResponse response = client.execute(post);
		if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			String responseStr = EntityUtils.toString(response.getEntity());
			JSONObject json = new JSONObject(responseStr).getJSONObject("json");
			String result = json.getString("result");

			if (result.equals("success")) {
			} else {
//				throw new ServiceRulesException(
//						ResignActivity.MSG_RESIGN_FAILED);
			}
		}



	}
	
	@Override
	public void updateuser(String loginName, String realname,
			String cellphone1, String cellphone2, String cellphone3)
			throws Exception {
		// TODO Auto-generated method stub
//		String uri = "http://192.168.191.1:8080/MyService/updateuser.action";
		String uri = "http://182.254.137.75:8080/MyService/updateuser.action";
		HttpClient client = new DefaultHttpClient();
		HttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, 15000); 
		HttpConnectionParams.setSoTimeout(httpParams, 15000);
		HttpPost post = new HttpPost(uri);
		List<NameValuePair> pair = new ArrayList<NameValuePair>();
		try {
			JSONObject obj = new JSONObject();
			obj.put("username", loginName);
			obj.put("realname", realname);
			obj.put("cellphone1", cellphone1);
			obj.put("cellphone2", cellphone2);
			obj.put("cellphone3", cellphone3);
			// Gson gson = new Gson();
			// String str = gson.toJson(obj);
			pair.add(new BasicNameValuePair("json", obj.toString()));
			post.setEntity(new UrlEncodedFormEntity(pair, HTTP.UTF_8));
			post.setParams(httpParams);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		HttpResponse response = client.execute(post);
		if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			String responseStr = EntityUtils.toString(response.getEntity());
			JSONObject json = new JSONObject(responseStr).getJSONObject("json");
			String result = json.getString("result");

			if (result.equals("success")) {
			} else {
				throw new ServiceRulesException(
						MyMessage.MSG_CHANGE_FAILED);
			}
		}

	}
}
