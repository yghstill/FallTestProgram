package login;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.example.ygh.falltestprogram.R;

/**
 * Created by Y-GH on 2016/6/7.
 */
public class ResignActivity extends AppCompatActivity implements View.OnClickListener {
    private Toolbar toolbar;
    private EditText account, password, sure_password, realname, phone1, phone2, phone3;
    RadioGroup mode;
    private int msex = 0;
    private ImageView mBtnClearusername, mBtnClearrealname, mBtnClearpassword, mBtnClearqrpsw;
    private ImageView mBtnClearphone1;
    private ImageView mBtnClearphone2;
    private ImageView mBtnClearphone3;
    private Button resign;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resign_all);
        initView();
        setOnListener();


    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("注册");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.back1);

        //控件
        account = (EditText) findViewById(R.id.msg_resign_username);
        password = (EditText) findViewById(R.id.msg_resign_password);
        sure_password = (EditText) findViewById(R.id.msg_resign_qrpsw);
        realname = (EditText) findViewById(R.id.msg_resign_realname);
        phone1 = (EditText) findViewById(R.id.msg_resign_cellphone1);
        phone2 = (EditText) findViewById(R.id.msg_resign_cellphone2);
        phone3 = (EditText) findViewById(R.id.msg_resign_cellphone3);
        mode = (RadioGroup) findViewById(R.id.mode);
        mBtnClearusername = (ImageView) findViewById(R.id.img_resign_username);
        mBtnClearpassword = (ImageView) findViewById(R.id.img_resign_password);
        mBtnClearqrpsw = (ImageView) findViewById(R.id.img_resign_qrpsw);
        mBtnClearrealname = (ImageView) findViewById(R.id.img_resign_realname);
        mBtnClearphone1 = (ImageView) findViewById(R.id.img_resign_cellphone1);
        mBtnClearphone2 = (ImageView) findViewById(R.id.img_resign_cellphone2);
        mBtnClearphone3 = (ImageView) findViewById(R.id.img_resign_cellphone3);
        resign = (Button) findViewById(R.id.btn_resign);

    }

    private void setOnListener() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //按钮监听
        resign.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                final String loginName = account.getText().toString();
                final String loginPassword = password.getText().toString();
                final String surePassword = sure_password.getText().toString();
                final String realName = realname.getText().toString();
                final String cellphone1 = phone1.getText().toString();
                final String cellphone2 = phone2.getText().toString();
                final String cellphone3 = phone3.getText().toString();

            }
        });

        mBtnClearusername.setOnClickListener(this);
        mBtnClearpassword.setOnClickListener(this);
        mBtnClearqrpsw.setOnClickListener(this);
        mBtnClearrealname.setOnClickListener(this);
        mBtnClearphone1.setOnClickListener(this);
        mBtnClearphone2.setOnClickListener(this);
        mBtnClearphone3.setOnClickListener(this);
        mode.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub
                switch (checkedId) {
                    case R.id.man_select:
                        msex = 0;
                        break;
                    case R.id.women_select:
                        msex = 1;
                        break;
                }
            }
        });

        // 用户名 输入监听
        account.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (account.getText().toString().length() > 0) {
                    mBtnClearusername.setVisibility(View.VISIBLE);
                    initmsucess();
                } else {
                    resign.setEnabled(false);
                    mBtnClearusername.setVisibility(View.INVISIBLE);
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

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (password.getText().toString().length() > 0) {
                    mBtnClearpassword.setVisibility(View.VISIBLE);
                    findViewById(R.id.qrpsw_layout).setVisibility(View.VISIBLE);
                    initmsucess();
                } else {
                    resign.setEnabled(false);
                    findViewById(R.id.qrpsw_layout).setVisibility(View.GONE);
                    mBtnClearpassword.setVisibility(View.INVISIBLE);
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

        sure_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (sure_password.getText().toString().length() > 0) {
                    mBtnClearqrpsw.setVisibility(View.VISIBLE);
                    initmsucess();
                } else {
                    resign.setEnabled(false);
                    mBtnClearqrpsw.setVisibility(View.INVISIBLE);
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
        // 真是姓名输入监听
        realname.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (realname.getText().toString().length() > 0) {
                    mBtnClearrealname.setVisibility(View.VISIBLE);
                    initmsucess();
                } else {
                    resign.setEnabled(false);
                    mBtnClearrealname.setVisibility(View.INVISIBLE);
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

        phone1.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (phone1.getText().toString().length() > 0) {
                    mBtnClearphone1.setVisibility(View.VISIBLE);
                    initmsucess();
                } else {
                    resign.setEnabled(false);
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

        phone2.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (phone2.getText().toString().length() > 0) {
                    mBtnClearphone2.setVisibility(View.VISIBLE);
                    initmsucess();
                } else {
                    resign.setEnabled(false);
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

        phone3.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (phone3.getText().toString().length() > 0) {
                    mBtnClearphone3.setVisibility(View.VISIBLE);
                    initmsucess();
                } else {
                    resign.setEnabled(false);
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
    }

    /**
     * 按钮状态监听
     */
    private void initmsucess() {
        if (account.getText().toString().length() > 0
                & password.getText().toString().length() > 0
                & sure_password.getText().toString().length() > 0
                & realname.getText().toString().length() > 0
                & phone1.getText().toString().length() > 0
                & phone2.getText().toString().length() > 0
                & phone3.getText().toString().length() > 0) {
            resign.setEnabled(true);
        } else {
            resign.setEnabled(false);
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.img_resign_username:
                account.setText("");
                break;
            case R.id.img_resign_password:
                password.setText("");
                break;
            case R.id.img_resign_qrpsw:
                sure_password.setText("");
                break;
            case R.id.img_resign_realname:
                realname.setText("");
                break;
            case R.id.img_resign_cellphone1:
                phone1.setText("");
                break;
            case R.id.img_resign_cellphone2:
                phone2.setText("");
                break;
            case R.id.img_resign_cellphone3:
                phone3.setText("");
                break;

        }

    }
}
