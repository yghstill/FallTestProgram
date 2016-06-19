package old.service;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

import internet.old.ConnectService;
import internet.old.ConnectServiceImp;
import loading.BaseApplication;

public class LocationService extends Service {
    private SharedPreferences pref;
    private SharedPreferences pref1;
    private SharedPreferences.Editor editor;
    private String mlocation;
    private double mLatitude;
    private double mLongtitude;
    private MyLocationListener mLocationListener;
    private LocationClient mLocationClient;
    private BaseApplication mApplication;
    private ConnectService connectService = new ConnectServiceImp();

    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @SuppressLint("CommitPrefEdits")
    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        pref = getSharedPreferences("service", MODE_PRIVATE);
        pref1 = getSharedPreferences("user", MODE_PRIVATE);
        editor = pref.edit();
        mApplication = (BaseApplication) getApplication();
        mApplication.setpoision_flag(true);
        mLocationClient = new LocationClient(this);
        mLocationListener = new MyLocationListener();
        mLocationClient.registerLocationListener(mLocationListener);
        LocationClientOption option = new LocationClientOption();
        option.setCoorType("bd09ll");
        option.setIsNeedAddress(true);
        option.setOpenGps(true);
        option.setScanSpan(10000);// 设置发起定位请求的间隔时间为5000ms
        mLocationClient.setLocOption(option);
        mLocationClient.start(); // 开始定位
        Log.e("--服务信息----", "@@@@@@@@@@@");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // TODO Auto-generated method stub

        new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub

                try {
                    while (mApplication.getpoision_flag() == true) {

                        if ((mLatitude != 0) && (mLongtitude != 0)) {
                            String realname = pref1.getString("realname", "");
                            String mtxt = realname + "在" + mlocation.substring(6)
                                    + "发生跌倒或者一些事故，请尽快救助！http://182.254.137.75:8080/MyService/";//(http://182.254.137.75:8080/MyService/)
                            //（查询位置网址：http://182.254.137.75:8080/MyService/）
                            Log.e("--服务信息----", mtxt);
                            editor.putString("DDmessage", mtxt);
                            editor.commit();

                            mApplication.setPosition(mlocation);
                            mApplication.setLatitude(mLatitude);
                            mApplication.setLongtitude(mLongtitude);
                        }
                        Thread.sleep(60000);
                    }

                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }

        }).start();
        return super.onStartCommand(intent, flags, startId);
    }

    private class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            mlocation = location.getAddrStr();
            mLatitude = location.getLatitude();
            mLongtitude = location.getLongitude();
        }
    }

}

