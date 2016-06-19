package internet.old;

import android.content.Context;

public interface ConnectService {
	public void sendMessage(String username, String nowtime, String poision,
							double mLatitude, double mLongtitude, int status) throws Exception;

	public void myposition(String username, String myposition, double latitude,
						   double longtitude) throws Exception;

	public void getMyposition(Context context, String username) throws Exception;

}
