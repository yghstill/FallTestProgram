package children.me;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ygh.falltestprogram.R;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

import org.apache.http.conn.ConnectTimeoutException;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import internet.old.ServiceRulesException;
import internet.old.history_positionImp;
import loading.BaseApplication;
import old.position.PositionBean;
import tools.UnderLineLinearLayout;


public class PositionHistory extends AppCompatActivity implements
        OnClickListener {
    private static final int FLAG_HIS_SUCCESS = 1;
    private static final String MSG_REQUEST_ERROR = "请求位置信息出错。";
    public static final String MSG_SERVER_ERROR = "请求服务器错误。";
    public static final String MSG_SERVER_TIMEOUT = "请求服务器超时。";
    public static final String MSG_RESPONCE_TIMEOUT = "服务器响应超时。";
    private Context context = PositionHistory.this;
    private internet.old.positionService positionService = new history_positionImp();
    private List<PositionBean> list = new ArrayList<PositionBean>();
    private SwipyRefreshLayout swipyRefreshLayout;
    private UnderLineLinearLayout mUnderLineLinearLayout;
    private Toolbar toolbar;
    private SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.position_history);
        BaseApplication.getInstance().addActivity(this);
        pref = getSharedPreferences("son_user", MODE_PRIVATE);
        initData();
        findViewById();
        setOnListener();
        initPosition();
    }

    private void findViewById() {
        mUnderLineLinearLayout = (UnderLineLinearLayout) findViewById(R.id.underline_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("我的足迹");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.back1);
    }

    private void setOnListener() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    private void initData() {
        swipyRefreshLayout = (SwipyRefreshLayout) findViewById(R.id.swipyrefreshlayout);
        swipyRefreshLayout.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(SwipyRefreshLayoutDirection direction) {
                initPosition();
                swipyRefreshLayout.setRefreshing(false);

            }
        });


    }

    private void initPosition(){
        /**
         * loading...
         */
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                try {

                    list = positionService.sendMessage(pref.getString("username",""));
                    Thread.sleep(1000);
                    mHandler.sendEmptyMessage(FLAG_HIS_SUCCESS);

                } catch (ConnectTimeoutException e) {
                    e.printStackTrace();
                    Message msg = new Message();
                    Bundle data = new Bundle();
                    data.putSerializable("ErrorMsg", MSG_SERVER_TIMEOUT);
                    msg.setData(data);
                    mHandler.sendMessage(msg);
                } catch (SocketTimeoutException e) {
                    e.printStackTrace();
                    Message msg = new Message();
                    Bundle data = new Bundle();
                    data.putSerializable("ErrorMsg", MSG_RESPONCE_TIMEOUT);
                    msg.setData(data);
                    mHandler.sendMessage(msg);
                } catch (ServiceRulesException e) {
                    e.printStackTrace();
                    Message msg = new Message();
                    Bundle data = new Bundle();
                    data.putSerializable("ErrorMsg", e.getMessage());
                    msg.setData(data);
                    mHandler.sendMessage(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                    Message msg = new Message();
                    Bundle data = new Bundle();
                    data.putSerializable("ErrorMsg", MSG_REQUEST_ERROR);
                    msg.setData(data);
                    mHandler.sendMessage(msg);
                }
            }
        });
        thread.start();

    }

    private void addItems() {
        // TODO Auto-generated method stub
        for (int i = 0; i < list.size(); i++) {
            View v = LayoutInflater.from(this).inflate(
                    R.layout.list_item_position, mUnderLineLinearLayout, false);
            ((TextView) v.findViewById(R.id.tx_action)).setText(list.get(i)
                    .getPosition());
            ((TextView) v.findViewById(R.id.tx_action_time)).setText(list
                    .get(i).getNowtime());
            if (list.get(i).getStatus() == 0) {
                ((TextView) v.findViewById(R.id.tx_action_status)).setText("状态正常");
            } else {
                ((TextView) v.findViewById(R.id.tx_action_status)).setText("状态异常");
            }
            mUnderLineLinearLayout.addView(v);
        }
    }

    private Handler mHandler = new Handler() {
        @SuppressLint("HandlerLeak")
        @Override
        public void handleMessage(Message msg) {

            int flag = msg.what;
            switch (flag) {
                case 0:
                    String errorMsg = (String) msg.getData().getSerializable(
                            "ErrorMsg");
                    showTip(errorMsg);
                    break;
                case FLAG_HIS_SUCCESS:
                    // showTip(MSG_SEND_SUCCESS);
                    findViewById(R.id.phloadView).setVisibility(View.GONE);
                    addItems();
                    break;

                default:
                    break;
            }
        }
    };

    private void showTip(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
//            case R.id.img_back:
//                new PlayTsSound(context, R.raw.back);
//                Utils.finish(PositionHistory.this);
//                break;
//            case R.id.txt_left:
//                new PlayTsSound(context, R.raw.back);
//                Utils.finish(PositionHistory.this);
//                break;
        }
    }


}
