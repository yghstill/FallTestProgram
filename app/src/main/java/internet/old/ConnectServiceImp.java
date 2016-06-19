package internet.old;

import android.content.Context;
import android.content.SharedPreferences;

import org.apache.http.HttpResponse;
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

import old.main.BaidumapActivity;
import old.position.MyPosition;
import old.position.PositionHistory;


public class ConnectServiceImp implements ConnectService {
	private SharedPreferences pref;
	private SharedPreferences.Editor editor;
	@Override
	public void sendMessage(String username, String nowtime, String poision,
			double Latitude, double Longtitude,int status) throws Exception {
		// TODO Auto-generated method stub
		HttpClient client = new DefaultHttpClient();
		String uri = "http://182.254.137.75:8080/MyService/poision.action";
//		String uri = "http://192.168.1.107:8080/MyService/poision.action";
		HttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, 10000);
		HttpConnectionParams.setSoTimeout(httpParams, 10000);
		HttpPost post = new HttpPost(uri);
		List<NameValuePair> pair = new ArrayList<NameValuePair>();
		try {
			JSONObject obj = new JSONObject();
			obj.put("username", username);
			obj.put("nowtime", nowtime);
			obj.put("poision", poision);
			obj.put("latitude", Latitude);
			obj.put("longtitude", Longtitude);
			obj.put("status", status);
			pair.add(new BasicNameValuePair("json", obj.toString()));
			post.setEntity(new UrlEncodedFormEntity(pair, HTTP.UTF_8));
			post.setParams(httpParams);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		HttpResponse response = client.execute(post);
		if (response.getStatusLine().getStatusCode() == 200) {
			String responseStr = EntityUtils.toString(response.getEntity());
			JSONObject json = new JSONObject(responseStr).getJSONObject("json");
			String result = json.getString("result");

			if (result.equals("success")) {
			} else {
				throw new ServiceRulesException(
						BaidumapActivity.MSG_SERVER_ERROR);
			}
		}

	}

	@Override
	public void myposition(String username, String myposition, double latitude,
			double longtitude) throws Exception {
		// TODO Auto-generated method stub
		HttpClient client = new DefaultHttpClient();
		String uri = "http://182.254.137.75:8080/MyService/Mypoision.action";
//		String uri = "http://192.168.1.107:8080/MyService/Mypoision.action";
		HttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, 8000);
		HttpConnectionParams.setSoTimeout(httpParams, 8000);
		HttpPost post = new HttpPost(uri);
		List<NameValuePair> pair = new ArrayList<NameValuePair>();
		try {
			JSONObject obj = new JSONObject();
			obj.put("username", username);
			obj.put("poision", myposition);
			obj.put("latitude", latitude);
			obj.put("longtitude", longtitude);
			pair.add(new BasicNameValuePair("json", obj.toString()));
			post.setEntity(new UrlEncodedFormEntity(pair, HTTP.UTF_8));
			post.setParams(httpParams);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		HttpResponse response = client.execute(post);
		if (response.getStatusLine().getStatusCode() == 200) {
			String responseStr = EntityUtils.toString(response.getEntity());
			JSONObject json = new JSONObject(responseStr).getJSONObject("json");
			String result = json.getString("result");

			if (result.equals("success")) {
			} else {
				throw new ServiceRulesException(
						MyPosition.MSG_SERVER_ERROR);
			}
		}
		
		
	}

	@Override
	public void getMyposition(Context context,String username) throws Exception {
		HttpClient client = new DefaultHttpClient();
		String uri = "http://182.254.137.75:8080/MyService/Reqmyposition.action";
//		String uri = "http://192.168.1.107:8080/MyService/Mypoision.action";
		HttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, 8000);
		HttpConnectionParams.setSoTimeout(httpParams, 8000);
		HttpPost post = new HttpPost(uri);
		List<NameValuePair> pair = new ArrayList<NameValuePair>();
		try {
			JSONObject obj = new JSONObject();
			obj.put("username", username);
			pair.add(new BasicNameValuePair("json", obj.toString()));
			post.setEntity(new UrlEncodedFormEntity(pair, HTTP.UTF_8));
			post.setParams(httpParams);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		HttpResponse response = client.execute(post);
		if (response.getStatusLine().getStatusCode() == 200) {
			String responseStr = EntityUtils.toString(response.getEntity());
			JSONObject json = new JSONObject(responseStr).getJSONObject("json");
			String result = json.getString("result");

			if (result.equals("success")) {
				String myposition = json.getString("myposition");
				double latitude = json.getDouble("latitude");
				double longtitude = json.getDouble("longtitude");
				pref = context.getSharedPreferences("user", context.MODE_PRIVATE);
				editor=pref.edit();
				editor.putString("myposition", myposition);
				editor.putFloat("mLatitude", (float) latitude);
				editor.putFloat("mLongtitude", (float) longtitude);
				editor.commit();
			} else {
				throw new ServiceRulesException(
						PositionHistory.MSG_SERVER_ERROR);
			}
		}
		
		
	}
		
		
}
