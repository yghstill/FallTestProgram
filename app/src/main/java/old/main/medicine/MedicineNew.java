package old.main.medicine;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ygh.falltestprogram.R;

import java.util.Calendar;
import java.util.TimeZone;

import loading.BaseApplication;
import old.service.AlramClockReceiver;


public class MedicineNew extends AppCompatActivity implements OnClickListener {
    private EditText mmededtEdit, mjiliang;
    private TextView maddbtn;
    private ListView mtimelist;
    private ImageView mdelmedicine, mdeljiliang;
    private BaseApplication mApplication;
    private int mHour = -1;
    private int mMinute = -1;
    ArrayAdapter<String> madapter;
    private Context context = MedicineNew.this;
    public static final long DAY = 1000L * 60 * 60 * 24;
    private SharedPreferences pref1;
    private SharedPreferences.Editor editor;
    private Button btn_save;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.medicine_set_view);
        mApplication = (BaseApplication) getApplication();
        initView();
        if (mApplication.getIsclockset()) {
            initclockdata();
        }
        if (mApplication.getIsupdateData()) {
            initdata();
        }

    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("添加药物");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.back1);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mmededtEdit.getText().toString().equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MedicineNew.this);
                    builder.setTitle("提示");
                    builder.setMessage("您还没有保存，确定要退出吗？");
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            //这里添加点击确定后的逻辑
                            mApplication.setIsclockset(false);
                            finish();
                        }
                    });
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            //这里添加点击确定后的逻辑
                        }
                    });
                    builder.create().show();
                } else {
                    mApplication.setIsclockset(false);
                    finish();
                }
            }
        });

        madapter = new ArrayAdapter<String>(MedicineNew.this,
                android.R.layout.simple_list_item_1, mApplication.getclockList());
        pref1 = getSharedPreferences("service", MODE_PRIVATE);
        editor = pref1.edit();
        mmededtEdit = (EditText) findViewById(R.id.contast_edt_medicine);
        mjiliang = (EditText) findViewById(R.id.contast_edt_jiliang);
        mdelmedicine = (ImageView) findViewById(R.id.img_del_medicine);
        mdeljiliang = (ImageView) findViewById(R.id.img_del_jiliang);
        maddbtn = (TextView) findViewById(R.id.btn_add_med_time);
        mtimelist = (ListView) findViewById(R.id.med_time_list_view);
        btn_save = (Button) findViewById(R.id.btn_save);
        // 设置点击监听
        maddbtn.setOnClickListener(this);
        mdelmedicine.setOnClickListener(this);
        mmededtEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (mmededtEdit.getText().toString().length() > 0) {
                    mdelmedicine.setVisibility(View.VISIBLE);
                    initmsucess();
                } else {
                    mdelmedicine.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable arg0) {
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
            }
        });

        mjiliang.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (mjiliang.getText().toString().length() > 0) {
                    mdeljiliang.setVisibility(View.VISIBLE);
                    initmsucess();
                } else {
                    mdeljiliang.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable arg0) {
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
            }
        });

        btn_save.setOnClickListener(new OnClickListener() {

                                        @Override
                                        public void onClick(View arg0) {
                                            String savename = mmededtEdit.getText().toString() + "(" + mjiliang.getText().toString() + ")";
                                            String timeclock = "";
                                            for (int i = 0; i < mApplication.getclockList().size(); i++) {
                                                String[] list = mApplication.getclockList().get(i).trim().split(":");
                                                mHour = Integer.parseInt(list[0]);
                                                mMinute = Integer.parseInt(list[1]);

                                                initClock();
                                                timeclock = timeclock + mApplication.getclockList().get(i) + ";";
                                            }
                                            Intent intent = new Intent(MedicineNew.this,
                                                    MedicineActivity.class);
                                            intent.putExtra("medicinename", savename);
                                            intent.putExtra("time", timeclock);
                                            mApplication.setIsclockset(true);
                                            mApplication.deleteList();
                                            if (!mApplication.getIsupdateData()) {
//						setResult(RESULT_OK, intent);
                                                startActivity(intent);
                                                Log.e("------回----", "result——ok");
                                                finish();
                                            } else {
                                                mApplication.setIsupdateData(false);
//						setResult(10, intent);
                                                startActivity(intent);
                                                finish();
                                            }


                                        }
                                    }

        );


    }

    /**
     * 按钮状态监听
     */
    private void initmsucess() {
        if (mjiliang.getText().toString().length() > 0
                & mmededtEdit.getText().toString().length() > 0) {
            btn_save.setEnabled(true);
        } else {
            btn_save.setEnabled(false);
        }
    }

    private void initdata() {
        Intent intent = getIntent();
        mmededtEdit.setText(intent.getStringExtra("medicine"));
    }

    private void initclockdata() {
        Intent intent = getIntent();
        mmededtEdit.setText(pref1.getString("medicine", ""));
        mHour = intent.getExtras().getInt("mHour");
        mMinute = intent.getExtras().getInt("mMinute");
        Log.d("监听的时间为", "--" + mHour + "--" + mMinute);
        mApplication.setclockList(mHour + ":" + mMinute);
        mtimelist.setAdapter(madapter);
        mApplication.setIsclockset(false);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add_med_time:
                editor.putString("medicine", mmededtEdit.getText().toString());
                editor.commit();
                DateDialogFragment fragment = new DateDialogFragment();
                fragment.show(getSupportFragmentManager(), null);
                break;
            case R.id.img_del_medicine:
                mmededtEdit.setText("");
                break;

            default:
                break;
        }

    }

    private void initClock() {
        Intent intent = new Intent(MedicineNew.this,
                AlramClockReceiver.class);
        intent.putExtra("medicine", mmededtEdit.getText().toString());
        int id = pref1.getInt("clockid", 0);
        editor.putInt("clockid", id + 1);
//			editor.putString("medinice"+id, mmededtEdit.getText().toString());
        editor.commit();
        PendingIntent sender = PendingIntent.getBroadcast(
                MedicineNew.this, id, intent, 0);

        long firstTime = SystemClock.elapsedRealtime(); // 开机之后到现在的运行时间(包括睡眠时间)
        long systemTime = System.currentTimeMillis();

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+8")); // 设置时区
        // ，，这里时区需要设置一下，不然会有8个小时的时间差
        calendar.set(Calendar.MINUTE, mMinute);
        calendar.set(Calendar.HOUR_OF_DAY, mHour);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        long selectTime = calendar.getTimeInMillis();

        // 如果当前时间大于设置的时间，那么就从第二天的设定时间开始
        if (systemTime > selectTime) {
            Toast.makeText(MedicineNew.this, "设置的时间小于当前时间",
                    Toast.LENGTH_SHORT).show();
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            selectTime = calendar.getTimeInMillis();
        }

        // 计算现在时间到设定时间的时间差
        long time = selectTime - systemTime;
        firstTime += time;

        // 进行闹铃注册
        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        manager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                firstTime, DAY, sender);

        Log.e("AlarmManager", "time ==== " + time + ", selectTime ===== "
                + selectTime + ", systemTime ==== " + systemTime
                + ", firstTime === " + firstTime);

//			Toast.makeText(MedicineNew.this, "设置闹铃成功! ", Toast.LENGTH_LONG)
//					.show();
//		}
    }
}
