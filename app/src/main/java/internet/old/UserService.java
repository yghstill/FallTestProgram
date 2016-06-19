package internet.old;

import android.content.Context;

public interface UserService {

	public void userLogin(Context context, String loginName, String loginPassword)
			throws Exception;

	public void userResign(String loginName, String loginPassword,
						   String surePassword, String realName, int esx, String cellphone1,
						   String cellphone2, String cellphone3) throws Exception;

//	public JSONObject selectUser(String loginName, String realname,
//			String cellphone1, String cellphone2, String cellphone3)
//			throws Exception;

	public void updateuser(String loginName, String realname,
						   String cellphone1, String cellphone2, String cellphone3)
			throws Exception;

}
