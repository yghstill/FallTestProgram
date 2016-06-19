package old.me;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ygh.falltestprogram.R;

import org.apache.http.conn.ConnectTimeoutException;

import java.lang.ref.WeakReference;
import java.net.SocketTimeoutException;

import internet.old.ServiceRulesException;
import internet.old.UserService;
import internet.old.UserServiceImpl;
import loading.BaseApplication;

//import internet.UserServiceImpl;

/**
 * Created by Y-GH on 2016/6/10.
 */
public class MyMessage extends AppCompatActivity implements OnClickListener {
    private EditText mrealname;
    private EditText mcellphone1;
    private EditText mcellphone2;
    private EditText mcellphone3;
    private Button msuccess;
    private ImageView mBtnClearname;
    private ImageView mBtnClearphone1;
    private ImageView mBtnClearphone2;
    private ImageView mBtnClearphone3;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private BaseApplication mApplication;
    private Context context = MyMessage.this;
    private static ProgressDialog dialog;
    private UserService userService = new UserServiceImpl();
    private static final int FLAG_CHANGE_SUCCESS = 1;
    private static final String MSG_CHANGE_SUCCESS = "修改成功。";
    public static final String MSG_CHANGE_FAILED = "修改失败。";
    public static final String MSG_SERVER_TIMEOUT = "请求服务器超时。";
    public static final String MSG_RESPONCE_TIMEOUT = "服务器响应超时。";
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApplication = (BaseApplication) getApplication();
        setContentView(R.layout.mymessage);
        BaseApplication.getInstance().addActivity(this);
        initView();
        findViewById();
        setOnListener();
            initUID();
    }

    private void initView() {
        mrealname = (EditText) findViewById(R.id.msg_username);
        mcellphone1 = (EditText) findViewById(R.id.msg_cellphone1);
        mcellphone2 = (EditText) findViewById(R.id.msg_cellphone2);
        mcellphone3 = (EditText) findViewById(R.id.msg_cellphone3);
        mBtnClearname = (ImageView) findViewById(R.id.img_msg_username);
        mBtnClearphone1 = (ImageView) findViewById(R.id.img_msg_cellphone1);
        mBtnClearphone2 = (ImageView) findViewById(R.id.img_msg_cellphone2);
        mBtnClearphone3 = (ImageView) findViewById(R.id.img_msg_cellphone3);
        msuccess = (Button) findViewById(R.id.btn_mymessage);
    }

    private void initUID() {
        pref = getSharedPreferences("user", MODE_PRIVATE);
        mrealname.setText(pref.getString("realname", ""));
        mcellphone1.setText(pref.getString("cellphone1", ""));
        mcellphone2.setText(pref.getString("cellphone2", ""));
        mcellphone3.setText(pref.getString("cellphone3", ""));
    }


    private void findViewById() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("个人信息");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.back1);

    }

    private void setOnListener() {
        mBtnClearname.setOnClickListener(this);
        mBtnClearphone1.setOnClickListener(this);
        mBtnClearphone2.setOnClickListener(this);
        mBtnClearphone3.setOnClickListener(this);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mrealname.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (mrealname.getText().toString().length() > 0) {
                    mBtnClearname.setVisibility(View.VISIBLE);
                    initmsucess();
                } else {
                    msuccess.setEnabled(false);
                    mBtnClearname.setVisibility(View.INVISIBLE);
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

        mcellphone1.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (mcellphone1.getText().toString().length() > 0) {
                    mBtnClearphone1.setVisibility(View.VISIBLE);
                    initmsucess();
                } else {
                    msuccess.setEnabled(false);
                    mBtnClearphone1.setVisibility(View.INVISIBLE);
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

        mcellphone2.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (mcellphone2.getText().toString().length() > 0) {
                    mBtnClearphone2.setVisibility(View.VISIBLE);
                    initmsucess();
                } else {
                    msuccess.setEnabled(false);
                    mBtnClearphone2.setVisibility(View.INVISIBLE);
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

        mcellphone3.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (mcellphone3.getText().toString().length() > 0) {
                    mBtnClearphone3.setVisibility(View.VISIBLE);
                    initmsucess();
                } else {
                    msuccess.setEnabled(false);
                    mBtnClearphone3.setVisibility(View.INVISIBLE);
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

        /**
         * @author Y-GH 网上查询
         */
        msuccess.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                final String realname = mrealname.getText().toString();
                final String cellphone1 = mcellphone1.getText().toString();
                final String cellphone2 = mcellphone2.getText().toString();
                final String cellphone3 = mcellphone3.getText().toString();
                /**
                 * loading...
                 */
                if (dialog == null) {
                    dialog = new ProgressDialog(MyMessage.this);
                }
                dialog.setMessage("修改中...");
                dialog.setCancelable(false);
                dialog.show();

                /**
                 * 子线程
                 */
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        try {
                            String loginName = mApplication.getLoginUserName();
                            userService.updateuser(loginName, realname,
                                    cellphone1, cellphone2, cellphone3);
                            handler.sendEmptyMessage(FLAG_CHANGE_SUCCESS);
                            editor = pref.edit();
                            editor.putString("realname", realname);
                            editor.putString("cellphone1", cellphone1);
                            editor.putString("cellphone2", cellphone2);
                            editor.putString("cellphone3", cellphone3);
                            editor.commit();
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
                            data.putSerializable("ErrorMsg",
                                    MSG_RESPONCE_TIMEOUT);
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
                            data.putSerializable("ErrorMsg", MSG_CHANGE_FAILED);
                            msg.setData(data);
                            handler.sendMessage(msg);
                        }
                    }
                });
                thread.start();
            }
        });
    }

    private void initmsucess() {
        // TODO Auto-generated method stub
        if (mrealname.getText().toString().length() > 0
                & mcellphone1.getText().toString().length() > 0
                & mcellphone2.getText().toString().length() > 0
                & mcellphone3.getText().toString().length() > 0) {
            msuccess.setEnabled(true);
        } else {
            msuccess.setEnabled(false);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_msg_username:
                mrealname.setText("");
                break;
            case R.id.img_msg_cellphone1:
                mcellphone1.setText("");
                break;
            case R.id.img_msg_cellphone2:
                mcellphone2.setText("");
                break;
            case R.id.img_msg_cellphone3:
                mcellphone3.setText("");
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private static class IHandler extends Handler {

        private final WeakReference<FragmentActivity> mActivity;

        public IHandler(MyMessage activity) {
            mActivity = new WeakReference<FragmentActivity>(activity);
        }

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
                    ((MyMessage) mActivity.get()).showTip(errorMsg);
                    break;
                case FLAG_CHANGE_SUCCESS:
                    ((MyMessage) mActivity.get()).showTip(MSG_CHANGE_SUCCESS);
                    break;

                default:
                    break;
            }
        }
    }

    private void showTip(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
        if (str == MSG_CHANGE_SUCCESS) {
        }

    }

    private IHandler handler = new IHandler(this);
}

