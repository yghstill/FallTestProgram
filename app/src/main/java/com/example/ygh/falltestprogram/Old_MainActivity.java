package com.example.ygh.falltestprogram;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import loading.BaseApplication;
import old.main.HeartRateMonitor;
import old.main.MapMain;
import old.main.contast.ContactsActivity;
import old.main.medicine.MedicineActivity;
import old.main.sosFragment;
import old.me.About_Us;
import old.me.Install;
import old.me.LogoutDialogFragment;
import old.me.MyMessage;
import old.me.SetMessage;
import old.me.tool.ToolMainActivity;
import tools.PlayTsSound;
import tools.Utils;

public class Old_MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    private ImageView mchiyao;
    private ImageView mheart;
    private ImageView mmap;
    private ImageView mcontast;
    private ImageView msosImage;
    private TextView realname,distance,medicine,tempture;
    private ImageView mhead;
    private Context context = Old_MainActivity.this;
    private SharedPreferences pref;
    private SharedPreferences pref1;
    private static final int DISTANCE = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_old);
        BaseApplication.getInstance().addActivity(this);
        pref1 = getSharedPreferences("service", MODE_PRIVATE);
        initView();
        initEvents();

    }

    private void initEvents() {
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                while (true) {
                    mHandler.sendEmptyMessage(DISTANCE);
                    try {
                        Thread.sleep(60000);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }

            }
        });
        thread.start();
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);  //个性图标得到解放~嘿嘿
        View headerView = navigationView.getHeaderView(0);

        //初始化头像
        pref=getSharedPreferences("user",MODE_PRIVATE);
        ImageView head = (ImageView) headerView.findViewById(R.id.image_head);
        if(pref.getInt("sex",0)==0){
            head.setImageResource(R.mipmap.me_man_head);
        }else {
            head.setImageResource(R.mipmap.me_woman_head);
        }
        Log.e("===========>","-----"+pref.getInt("sex",0));

        //初始化控件
        distance= (TextView) findViewById(R.id.distance);
        medicine= (TextView) findViewById(R.id.medicine);
        mchiyao = (ImageView) findViewById(R.id.btn_msg_select);
        mheart = (ImageView) findViewById(R.id.btn_tool_select);
        mmap = (ImageView) findViewById(R.id.btn_map_select);
        mcontast = (ImageView) findViewById(R.id.btn_contast_select);
        msosImage = (ImageView) findViewById(R.id.btn_sos_select);
        realname= (TextView) headerView.findViewById(R.id.realname);
        realname.setText(pref.getString("realname",""));
        Log.e("===========>","-----"+pref.getString("realname",""));
        mchiyao.setOnClickListener(this);
        mheart.setOnClickListener(this);
        mmap.setOnClickListener(this);
        mcontast.setOnClickListener(this);
        msosImage.setOnClickListener(this);

    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int flag = msg.what;
            switch (flag) {
                case DISTANCE:
                    distance.setText("离家距离：" + pref1.getInt("distance",1) + "米");
                    break;

                default:
                    break;
            }
        }
    };


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_msg_select:
                new PlayTsSound(context, R.raw.medicine);
                Utils.start_Activity(Old_MainActivity.this, MedicineActivity.class);
                break;
            case R.id.btn_tool_select:
                new PlayTsSound(context, R.raw.heart);
                Utils.start_Activity(Old_MainActivity.this, HeartRateMonitor.class);
                break;
            case R.id.btn_map_select:
                new PlayTsSound(context, R.raw.map);
                Utils.start_Activity(Old_MainActivity.this, MapMain.class);
                break;
            case R.id.btn_contast_select:
                new PlayTsSound(context, R.raw.constast);
                Utils.start_Activity(Old_MainActivity.this, ContactsActivity.class);
                break;
            case R.id.btn_sos_select:

                new PlayTsSound(context, R.raw.sos);
                Utils.start_Activity(Old_MainActivity.this, sosFragment.class);
                break;
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("提示");
            builder.setMessage("确定要退出吗？");
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    //这里添加点击确定后的逻辑
                    BaseApplication.getInstance().exit();
                }
            });
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    //这里添加点击确定后的逻辑
                }
            });
            builder.create().show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_mymsg) {
            Intent intent = new Intent(Old_MainActivity.this, MyMessage.class);
            startActivity(intent);

        } else if (id == R.id.nav_jjmsg) {
            Intent intent = new Intent(Old_MainActivity.this, SetMessage.class);
            startActivity(intent);

        } else if (id == R.id.nav_tool) {
            Intent intent = new Intent(Old_MainActivity.this, ToolMainActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_set) {
            Intent intent = new Intent(Old_MainActivity.this, Install.class);
            startActivity(intent);

        } else if (id == R.id.nav_fankui) {
//            Intent intent = new Intent(Old_MainActivity.this, MyMessage.class);
//            startActivity(intent);

        } else if (id == R.id.nav_about) {
            Intent intent = new Intent(Old_MainActivity.this, About_Us.class);
            startActivity(intent);

        } else if (id == R.id.nav_exit) {
            new PlayTsSound(context, R.raw.logout);
            LogoutDialogFragment fragment = new LogoutDialogFragment();
            fragment.show(getFragmentManager(), null);
//            LogoutDialogFragment fragment = new LogoutDialogFragment();
//            FragmentManager fragmentManager =getFragmentManager();
//            FragmentTransaction transaction = fragmentManager.beginTransaction();
//            transaction.add(R.id.logout,fragment,null);
//            fragment.show(transaction,null);

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
