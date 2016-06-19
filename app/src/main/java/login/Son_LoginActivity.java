package login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.ygh.falltestprogram.R;
import com.example.ygh.falltestprogram.Son_MainActivity;

import org.apache.http.conn.ConnectTimeoutException;

import java.lang.ref.WeakReference;
import java.net.SocketTimeoutException;

import internet.old.ServiceRulesException;
import internet.old.UserService;
import internet.old.UserServiceImpl;
import loading.Loading;

/**
 * Created by Y-GH on 2016/6/7.
 */
public class Son_LoginActivity extends AppCompatActivity implements
        CompoundButton.OnCheckedChangeListener {
    private EditText username, password, relation;
    private ImageView mBtnClearUid, mBtnClearPsw, mBtnClearRelation, back;
    private ToggleButton mTgBtnShowPsw;
    private Button login;
    private TextView resign;

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
//    private String httpurl = "http://182.254.137.75:8080/MyService/login.action";

    private UserService userService = new UserServiceImpl();
    private static final int FLAG_LOGIN_SUCCESS = 1;
    private static final String MSG_LOGIN_ERROR = "登录出错,请检查网络。";
    private static final String MSG_LOGIN_SUCCESS = "登录成功。";
    public static final String MSG_LOGIN_FAILED = "用户名或密码错误。";
    public static final String MSG_SERVER_ERROR = "请求服务器错误。";
    public static final String MSG_SERVER_TIMEOUT = "请求服务器超时。";
    public static final String MSG_RESPONCE_TIMEOUT = "服务器响应超时。";
    private static ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_son);
        initView();
        initUID();
        setOnListener();
    }

    private void initView() {
        login = (Button) findViewById(R.id.btn_login);
        username = (EditText) findViewById(R.id.edit_uid);
        password = (EditText) findViewById(R.id.edit_psw);
        relation = (EditText) findViewById(R.id.edit_relation);
        mBtnClearUid = (ImageView) findViewById(R.id.img_login_clear_uid);
        mBtnClearPsw = (ImageView) findViewById(R.id.img_login_clear_psw);
        mBtnClearRelation = (ImageView) findViewById(R.id.img_login_clear_relation);
        mTgBtnShowPsw = (ToggleButton) findViewById(R.id.tgbtn_show_psw);
        resign = (TextView) findViewById(R.id.tv_quick_sign_up);
        back = (ImageView) findViewById(R.id.ic_back);

    }

    private void setOnListener() {

        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final String loginName = username.getText().toString();
                final String loginPassword = password.getText().toString();
                /**
                 * loading...
                 */

                if (dialog == null) {
                    dialog = new ProgressDialog(Son_LoginActivity.this);
                }
                dialog.setMessage("登录中...");
                dialog.setCancelable(false);
                dialog.show();

                /**
                 * 子线程
                 */
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            userService.userLogin(Son_LoginActivity.this, loginName, loginPassword);
                            handler.sendEmptyMessage(FLAG_LOGIN_SUCCESS);
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
                            data.putSerializable("ErrorMsg", MSG_LOGIN_ERROR);
                            msg.setData(data);
                            handler.sendMessage(msg);
                        }
                    }
                });
                thread.start();

            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Son_LoginActivity.this, Loading.class);
                startActivity(intent);
                finish();
            }
        });

        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (username.getText().toString().length() > 0) {
                    mBtnClearUid.setVisibility(View.VISIBLE);
                    if (password.getText().toString().length() > 0 && relation.getText().toString().length() > 0) {
                        login.setEnabled(true);
                    } else {
                        login.setEnabled(false);
                    }
                } else {
                    login.setEnabled(false);
                    mBtnClearUid.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (password.getText().toString().length() > 0) {
                    mBtnClearPsw.setVisibility(View.VISIBLE);
                    if (username.getText().toString().length() > 0 && relation.getText().toString().length() > 0) {
                        login.setEnabled(true);
                    } else {
                        login.setEnabled(false);
                    }
                } else {
                    login.setEnabled(false);
                    mBtnClearPsw.setVisibility(View.INVISIBLE);
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

        relation.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (relation.getText().toString().length() > 0) {
                    mBtnClearRelation.setVisibility(View.VISIBLE);
                    if (username.getText().toString().length() > 0 && password.getText().toString().length() > 0) {
                        login.setEnabled(true);
                    } else {
                        login.setEnabled(false);
                    }
                } else {
                    login.setEnabled(false);
                    mBtnClearRelation.setVisibility(View.INVISIBLE);
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

        resign.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Son_LoginActivity.this,
                        ResignActivity.class);
                startActivity(intent);
            }
        });
        mBtnClearUid.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                clearText(username);
            }
        });
        mBtnClearPsw.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                clearText(password);
            }
        });
        mBtnClearRelation.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                clearText(relation);
            }
        });

        mTgBtnShowPsw.setOnCheckedChangeListener(this);
    }

    /**
     * 初始化记住的用户名
     */

    private void initUID() {
        pref = getSharedPreferences("son_user", MODE_PRIVATE);
        String Username = pref.getString("username", "");
        String relationStr = pref.getString("relation", "");
        username.setText(Username);
        relation.setText(relationStr);
    }


    /**
     * 清空控件文本
     */
    private void clearText(EditText edit) {
        edit.setText("");
    }

    /**
     * 是否显示密码
     */
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            // 显示密码
            password.setTransformationMethod(HideReturnsTransformationMethod
                    .getInstance());
        } else {
            // 隐藏密码
            password.setTransformationMethod(PasswordTransformationMethod
                    .getInstance());
        }
    }

    private void showTip(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
        if (str == MSG_LOGIN_SUCCESS) {
            editor = pref.edit();
            editor.putString("username", username.getText().toString());
            editor.putString("password", password.getText().toString());
            editor.putString("relation", relation.getText().toString());
            editor.putBoolean("islogin", true);
            editor.commit();

            Intent intent = new Intent(Son_LoginActivity.this, Son_MainActivity.class);
            startActivity(intent);
            finish();
        }

    }

    private static class IHandler extends Handler {

        private final WeakReference<FragmentActivity> mActivity;

        public IHandler(Son_LoginActivity activity) {
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
                    ((Son_LoginActivity) mActivity.get()).showTip(errorMsg);
                    break;
                case FLAG_LOGIN_SUCCESS:
                    ((Son_LoginActivity) mActivity.get())
                            .showTip(MSG_LOGIN_SUCCESS);
                    break;

                default:
                    break;
            }
        }
    }

    private IHandler handler = new IHandler(this);

}
