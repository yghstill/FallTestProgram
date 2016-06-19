package old.position;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ygh.falltestprogram.R;
import com.google.android.gms.common.api.GoogleApiClient;

import org.apache.http.conn.ConnectTimeoutException;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import internet.old.ServiceRulesException;
import internet.old.history_positionImp;
import loading.BaseApplication;
import tools.UnderLineLinearLayout;


public class PositionHistory extends FragmentActivity implements
        OnClickListener {
    private static final int FLAG_HIS_SUCCESS = 1;
    private static final String MSG_REQUEST_ERROR = "请求位置信息出错。";
    public static final String MSG_SERVER_ERROR = "请求服务器错误。";
    public static final String MSG_SERVER_TIMEOUT = "请求服务器超时。";
    public static final String MSG_RESPONCE_TIMEOUT = "服务器响应超时。";
    private Context context = PositionHistory.this;
    private TextView txt_title;
    private static ProgressDialog dialog;
    private internet.old.positionService positionService = new history_positionImp();
    private BaseApplication mApplication;
    private List<PositionBean> list = new ArrayList<PositionBean>();

    private UnderLineLinearLayout mUnderLineLinearLayout;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.position_history);
        mApplication = (BaseApplication) getApplication();
        BaseApplication.getInstance().addActivity(this);
        initData();
        findViewById();
        setOnListener();
    }

    private void findViewById() {
        mUnderLineLinearLayout = (UnderLineLinearLayout) findViewById(R.id.underline_layout);
        txt_title = (TextView) findViewById(R.id.txt_title);
        txt_title.setText("我的足迹");
    }

    private void setOnListener() {

    }


    private void initData() {
        /**
         * loading...
         */
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                try {

                    list = positionService.sendMessage(mApplication
                            .getLoginUserName());
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
                ((TextView) v.findViewById(R.id.tx_action_status)).setText("发生跌倒");
            }
            mUnderLineLinearLayout.addView(v);
        }
    }

    private Handler mHandler = new Handler() {
        @SuppressLint("HandlerLeak")
        @Override
        public void handleMessage(Message msg) {
            if (dialog != null) {
                dialog.dismiss();
            }

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
