package com.example.ygh.falltestprogram;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import children.main.Distance;
import children.main.Health;
import children.main.Home;
import children.main.Position;
import children.main.State;
import children.me.Install;
import children.me.LogoutDialogFragment;
import children.me.MyMessage;
import children.me.PositionHistory;
import internet.NormalPostRequest;
import loading.BaseApplication;
import old.me.About_Us;
import tools.Utils;

public class Son_MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private TextView bj_position, bj_state, bj_distance, bj_health, bj_home;
    private TextView position, state, distance, yundong, medicine, heart;
    private TextView bj_position_right, position_right, state_right, distance_right,
            yundong_right, medicine_right, heart_right, bj_home_right;
//    private ListView cgq_list;
    private TextView home_smoke,home_temp,home_mains,home_light;
    private SwipyRefreshLayout swipyRefreshLayout;
    private SharedPreferences pref;
    private SharedPreferences pref1;
    private TextView realname;
//    private ArrayAdapter<String> madapter;
    private BaseApplication mApplication;
    private static final int DELEAY = 0;

    private String mposition = "最近所在的位置：";
    private String mstate = "的行为状态：";
    private String mdistance = "当前离家距离：";
    private String mhealth = "健康情况：";
    private String mhome = "家中实时情况：";
    private String httpurl = "http://192.168.1.111:8080/MyService/childrengetall.action";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_son);
        BaseApplication.getInstance().addActivity(this);
        pref = getSharedPreferences("user", MODE_PRIVATE);
        pref1 = getSharedPreferences("son_user", MODE_PRIVATE);
        initView();
        initfindView();
        initData();

    }

    private void initfindView() {
        bj_position = (TextView) findViewById(R.id.bj_position);
        bj_state = (TextView) findViewById(R.id.bj_state);
        bj_distance = (TextView) findViewById(R.id.bj_distance);
        bj_health = (TextView) findViewById(R.id.bj_health);
        bj_home = (TextView) findViewById(R.id.bj_home);
        //内容
        position = (TextView) findViewById(R.id.position);
        state = (TextView) findViewById(R.id.state);
        distance = (TextView) findViewById(R.id.distance);
        yundong = (TextView) findViewById(R.id.yundong);
        medicine = (TextView) findViewById(R.id.medicine);
        heart = (TextView) findViewById(R.id.heart);
        home_smoke= (TextView) findViewById(R.id.home_smoke);
        home_temp= (TextView) findViewById(R.id.home_temp);
        home_light= (TextView) findViewById(R.id.home_light);
        home_mains= (TextView) findViewById(R.id.home_mains);
//        cgq_list = (ListView) findViewById(R.id.cgq_list);
        //详情
        bj_position_right = (TextView) findViewById(R.id.bj_position_right);
        position_right = (TextView) findViewById(R.id.position_right);
        state_right = (TextView) findViewById(R.id.state_right);
        distance_right = (TextView) findViewById(R.id.distance_right);
        yundong_right = (TextView) findViewById(R.id.yundong_right);
        medicine_right = (TextView) findViewById(R.id.medicine_right);
        heart_right = (TextView) findViewById(R.id.heart_right);
        bj_home_right = (TextView) findViewById(R.id.bj_home_right);
        //详情的点击事件
        bj_position_right.setOnClickListener(this);  //更多位置
        position_right.setOnClickListener(this);
        state_right.setOnClickListener(this);
        distance_right.setOnClickListener(this);
        yundong_right.setOnClickListener(this);
        medicine_right.setOnClickListener(this);
        heart_right.setOnClickListener(this);
        bj_home_right.setOnClickListener(this);
        swipyRefreshLayout = (SwipyRefreshLayout) findViewById(R.id.swipyrefreshlayout);
        swipyRefreshLayout.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(SwipyRefreshLayoutDirection direction) {
                networkRequest();
                mHandler.sendEmptyMessageDelayed(DELEAY, 2000);

            }
        });
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
        ImageView head = (ImageView) headerView.findViewById(R.id.image_head);
        if (pref.getInt("sex", 0) == 0) {
            head.setImageResource(R.mipmap.me_man_head);
        } else {
            head.setImageResource(R.mipmap.me_woman_head);
        }
        realname = (TextView) headerView.findViewById(R.id.realname);
        realname.setText(pref.getString("realname", ""));
    }


    private void initData() {
        String relation = pref1.getString("relation", "");
        bj_position.setText(relation + mposition);
        bj_state.setText(relation + mstate);
        bj_home.setText(relation + mhome);
        bj_health.setText(relation + mhealth);
        bj_distance.setText(relation + mdistance);
//        mApplication = (BaseApplication) getApplication();
//        madapter = new ArrayAdapter<String>(Son_MainActivity.this,
//                android.R.layout.simple_list_item_1, mApplication.getcgqList());

        networkRequest();

    }
    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {

            switch (msg.what) {
                case DELEAY:
                    swipyRefreshLayout.setRefreshing(false);
                    break;
            }
        };
    };

    private void networkRequest() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("username", pref1.getString("username", ""));
        /**
         * 访问网络
         */

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        Request<JSONObject> request = new NormalPostRequest(httpurl,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String result = null;
                        try {
                            result = response.getString("result");
                            if (result.equals("success")) {
                                position.setText(response.getString("poision"));
                                state.setText(response.getString("state"));
                                distance.setText(response.getString("distance")+"米");
                                yundong.setText("运动状态："+response.getString("sport"));
                                medicine.setText("吃药情况："+response.getString("medicine"));
                                heart.setText("上次测的心率是："+response.getString("heart"));
                                home_light.setText("光照状态："+response.getString("home_light"));
                                home_smoke.setText("烟雾状态："+response.getString("home_smoke"));
                                home_temp.setText("温度状态："+response.getString("home_temp"));
                                home_mains.setText("电源状态："+response.getString("home_mains"));


                            } else {
//                            Toast.makeText(Old_LoginActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Son_MainActivity.this, "连接服务器错误", Toast.LENGTH_SHORT).show();
            }
        }, params);
        requestQueue.add(request);
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
            Intent intent = new Intent(Son_MainActivity.this, MyMessage.class);
            startActivity(intent);

        } else if (id == R.id.nav_position) {
            Intent intent = new Intent(Son_MainActivity.this, PositionHistory.class);
            startActivity(intent);

        } else if (id == R.id.nav_set) {
            Intent intent = new Intent(Son_MainActivity.this, Install.class);
            startActivity(intent);

        } else if (id == R.id.nav_health) {
//            Intent intent = new Intent(Son_MainActivity.this, MyMessage.class);
//            startActivity(intent);

        } else if (id == R.id.nav_fankui) {
//            Intent intent = new Intent(Son_MainActivity.this, MyMessage.class);
//            startActivity(intent);

        } else if (id == R.id.nav_about) {
            Intent intent = new Intent(Son_MainActivity.this, About_Us.class);
            startActivity(intent);

        } else if (id == R.id.nav_exit) {
            LogoutDialogFragment fragment = new LogoutDialogFragment();
            fragment.show(getFragmentManager(), null);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bj_position_right:
                Utils.start_Activity(Son_MainActivity.this, PositionHistory.class);
                break;
            case R.id.bj_home_right:
                Utils.start_Activity(Son_MainActivity.this, Home.class);
                break;
            case R.id.position_right:
                Utils.start_Activity(Son_MainActivity.this, Position.class);
                break;
            case R.id.state_right:
                Utils.start_Activity(Son_MainActivity.this, State.class);
                break;
            case R.id.distance_right:
                Utils.start_Activity(Son_MainActivity.this, Distance.class);
                break;
            case R.id.yundong_right:
                Utils.start_Activity(Son_MainActivity.this, Health.class);
                break;
            case R.id.medicine_right:
                Utils.start_Activity(Son_MainActivity.this, Health.class);
                break;
            case R.id.heart_right:
                Utils.start_Activity(Son_MainActivity.this, Health.class);
                break;
        }
    }
}
