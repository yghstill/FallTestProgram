package loading;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.ygh.falltestprogram.Old_MainActivity;
import com.example.ygh.falltestprogram.R;
import com.example.ygh.falltestprogram.Son_MainActivity;

import login.Old_LoginActivity;
import login.Son_LoginActivity;
import old.service.DistanceService;
import old.service.FallDecisionService;
import old.service.LocationService;

/**
 * Created by Y-GH on 2016/6/7.
 */
public class Loading extends FragmentActivity {
    private ImageView login_old,login_son;
    private SharedPreferences pref1;
    private SharedPreferences pref2;
    private SharedPreferences.Editor editor;
    private static final int GOTO_OLD_MAIN_ACTIVITY = 0;
    private static final int GOTO_SON_MAIN_ACTIVITY = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pref1 = getSharedPreferences("old_user", MODE_PRIVATE);
        pref2 = getSharedPreferences("son_user", MODE_PRIVATE);
        if(pref1.getBoolean("islogin", false)==true){
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            setContentView(R.layout.loading_old_welcome);
            initOld();
        }else if(pref2.getBoolean("islogin", false)==true){
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            setContentView(R.layout.loading_son_welcome);
            initChildren();
        }else {
            setContentView(R.layout.loading_first);
            initView();
        }


    }

    private void initChildren() {
        mHandler.sendEmptyMessageDelayed(GOTO_SON_MAIN_ACTIVITY, 3000);// 3秒跳转

    }

    private void initOld() {
        mHandler.sendEmptyMessageDelayed(GOTO_OLD_MAIN_ACTIVITY, 3000);// 3秒跳转
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {

            switch (msg.what) {
                case GOTO_OLD_MAIN_ACTIVITY:
                    Intent startIntent1 = new Intent(Loading.this,
                            LocationService.class);
                    startService(startIntent1);
                    Log.e("-1--初始化LocationService----", "--" + "!!!!!!!!");
                    Intent startIntent2 = new Intent(Loading.this,
                            DistanceService.class);
                    startService(startIntent2);
                    Log.e("-2--初始化DistanceService----", "--" + "!!!!!!!!");
                    Intent startIntent3 = new Intent(Loading.this,
                            FallDecisionService.class);
                    startService(startIntent3);
                    Log.e("-3--初始化FallDecisionService----", "--" + "!!!!!!!!");

                    Intent intent = new Intent(Loading.this, Old_MainActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                case GOTO_SON_MAIN_ACTIVITY:
                    Intent intent2 = new Intent(Loading.this, Son_MainActivity.class);
                    startActivity(intent2);
                    finish();
                    break;
            }
        };
    };

    private void initView() {
        login_old= (ImageView) findViewById(R.id.login_old);
        login_son= (ImageView) findViewById(R.id.login_son);
        login_old.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Loading.this, Old_LoginActivity.class);
                startActivity(intent);
                finish();

            }
        });
        login_son.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Loading.this, Son_LoginActivity.class);
                startActivity(intent);
                finish();

            }
        });
    }
}
