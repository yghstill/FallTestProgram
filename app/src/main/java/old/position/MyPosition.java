package old.position;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.example.ygh.falltestprogram.R;

import org.apache.http.conn.ConnectTimeoutException;

import java.lang.ref.WeakReference;
import java.net.SocketTimeoutException;

import internet.old.ConnectService;
import internet.old.ConnectServiceImp;
import internet.old.ServiceRulesException;
import loading.BaseApplication;
import tools.AlertDialog;
import tools.Utils;

/**
 * @author Y-GH
 */
public class MyPosition extends FragmentActivity implements OnClickListener {
    // private MKSearch mSearch = null;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private BaseApplication mApplication;
    private TextView myposition;
    private Button mbutset;
    private TextView txt_title;
    private TextView txt_right;
    private Context context = MyPosition.this;
    private static ProgressDialog dialog;
    private String mlocation;
    private double mLatitude;
    private double mLongtitude;
    private float clatitude;
    private float clongtitude;
    private MyLocationListener mLocationListener;
    private LocationClient mLocationClient;
    private ConnectService connectService = new ConnectServiceImp();
    String mposition = null;
    private static final int FLAG_SEND_SUCCESS = 1;
    private static final String MSG_SEND_ERROR = "位置上传出错。";
    private static final String MSG_SEND_SUCCESS = "家的位置设置成功。";
    public static final String MSG_SERVER_ERROR = "请求服务器错误。";
    public static final String MSG_SERVER_TIMEOUT = "请求服务器超时。";
    public static final String MSG_RESPONCE_TIMEOUT = "服务器响应超时。";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApplication = (BaseApplication) getApplication();
        setContentView(R.layout.myposition);
        BaseApplication.getInstance().addActivity(this);
        initView();
        initUID();
        if (mposition.equals("")) {
            initpoision();
        }
        setOnListener();

    }

    private void initUID() {
        pref = getSharedPreferences("user", MODE_PRIVATE);
        mposition = pref.getString("myposition", "");
        myposition.setText(mposition);

    }

    private void initView() {
        myposition = (TextView) findViewById(R.id.change_myposition);
        mbutset = (Button) findViewById(R.id.btn_setposition);
        txt_title = (TextView) findViewById(R.id.txt_title);
        txt_title.setText("家的位置");

    }

    private void initpoision() {

        mLocationClient = new LocationClient(this);
        mLocationListener = new MyLocationListener();
        mLocationClient.registerLocationListener(mLocationListener);
        LocationClientOption option = new LocationClientOption();
        option.setCoorType("bd09ll");
        option.setIsNeedAddress(true);
        option.setOpenGps(true);
        option.setScanSpan(500);// 设置发起定位请求的间隔时间为5000ms
        mLocationClient.setLocOption(option);
        mLocationClient.start();
        myposition.setText(mlocation);
    }

    private void setOnListener() {
        txt_right.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub

                Utils.finish(MyPosition.this);
            }
        });
        mbutset.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                initpoision();
                clatitude = (float) mLatitude;
                clongtitude = (float) mLongtitude;
                new AlertDialog(context).builder().setCancelable(false)
                        .setTitle("提示").setMsg("现在的位置在：" + mlocation)
                        .setPositiveButton("取消", new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mposition = pref.getString("myposition", "");
                                myposition.setText(mposition);
                            }
                        }).setNegativeButton("确定", 0, new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        newthread();
                        /**
                         * loading...
                         */
                        if (dialog == null) {
                            dialog = new ProgressDialog(MyPosition.this);
                        }
                        dialog.setMessage("设置中...");
                        dialog.setCancelable(false);
                        dialog.show();
                    }
                }).show();
                mLocationClient.stop();
            }
        });
    }


    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
//            case R.id.img_back:
//                new PlayTsSound(context, R.raw.back);
//                Utils.finish(MyPosition.this);
//                break;
//            case R.id.txt_left:
//                new PlayTsSound(context, R.raw.back);
//                Utils.finish(MyPosition.this);
//                break;
        }
    }

    private class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            mlocation = location.getAddrStr();
            mLatitude = location.getLatitude();
            mLongtitude = location.getLongitude();
        }
    }

    private void newthread() {
        // TODO Auto-generated method stub
        new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                try {
                    connectService.myposition(mApplication.getLoginUserName(),
                            mlocation, mLatitude, mLongtitude);
                    handler.sendEmptyMessage(FLAG_SEND_SUCCESS);
                } catch (ConnectTimeoutException e) {
                    e.printStackTrace();
                    Message msg = new Message();
                    Bundle data = new Bundle();
                    data.putSerializable("ErrorMsg", MSG_SERVER_TIMEOUT);
                    msg.setData(data);
                    handler.sendMessage(msg);
                } catch (SocketTimeoutException e) {
                    e.printStackTrace();
                    Message msg = new Message();
                    Bundle data = new Bundle();
                    data.putSerializable("ErrorMsg", MSG_RESPONCE_TIMEOUT);
                    msg.setData(data);
                    handler.sendMessage(msg);
                } catch (ServiceRulesException e) {
                    e.printStackTrace();
                    Message msg = new Message();
                    Bundle data = new Bundle();
                    data.putSerializable("ErrorMsg", e.getMessage());
                    msg.setData(data);
                    handler.sendMessage(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                    Message msg = new Message();
                    Bundle data = new Bundle();
                    data.putSerializable("ErrorMsg", MSG_SEND_ERROR);
                    msg.setData(data);
                    handler.sendMessage(msg);
                }
            }

        }).start();
    }

    private static class IHandler extends Handler {

        private final WeakReference<FragmentActivity> mActivity;

        public IHandler(MyPosition activity) {
            mActivity = new WeakReference<FragmentActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {

            if (dialog != null) {
                dialog.cancel();
                dialog = null;
            }

            int flag = msg.what;
            switch (flag) {
                case 0:
                    String errorMsg = (String) msg.getData().getSerializable(
                            "ErrorMsg");
                    ((MyPosition) mActivity.get()).showTip(errorMsg);
                    break;
                case FLAG_SEND_SUCCESS:
                    ((MyPosition) mActivity.get()).showTip(MSG_SEND_SUCCESS);
                    break;

                default:
                    break;
            }
        }
    }

    private void showTip(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
        if (str == MSG_SEND_SUCCESS) {
            mbutset.setEnabled(false);
            editor = pref.edit();
            editor.putString("myposition", mlocation);
            editor.putFloat("mLatitude", clatitude);
            editor.putFloat("mLongtitude", clongtitude);
            editor.commit();
        }

    }

    private IHandler handler = new IHandler(this);

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
