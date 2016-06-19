package old.me;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.ygh.falltestprogram.R;

import loading.BaseApplication;
import tools.PlayTsSound;
import tools.Utils;

public class Install extends AppCompatActivity implements OnClickListener {

    private TextView mTvCacheSize;
    private SeekBar mSeekBarBrightness;
    private View mViewNightMode;
    private ToggleButton mTgLight;
    private EditText mdistance;
    private Context context = Install.this;
    private SharedPreferences pref1;
    private SharedPreferences.Editor editor;
    private Button save;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_set);
        pref1 = getSharedPreferences("service", MODE_PRIVATE);
        findViewById();
        setOnListener();
        BaseApplication.getInstance().addActivity(this);
        editor = pref1.edit();
    }

    private void findViewById() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("相关设置");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.back1);

        mdistance = (EditText) findViewById(R.id.set_distance);
        save = (Button) findViewById(R.id.btn_save);
        mdistance.setText(pref1.getInt("maxdistance", 5000) + "");
    }

    private void setOnListener() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new PlayTsSound(Install.this, R.raw.back);
                finish();
            }
        });

        findViewById(R.id.me_btn_mmchange).setOnClickListener(this);
        save.setOnClickListener(this);
        mdistance.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                initmsucess();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.me_btn_mmchange:
                new PlayTsSound(Install.this, R.raw.mymima);

                break;
            case R.id.btn_save:
                    editor.putInt("maxdistance",
                            Integer.valueOf(mdistance.getText().toString())
                                    .intValue());
                    editor.commit();
                Utils.finish(Install.this);
                break;
        }
    }

    /**
     * 按钮状态监听
     */
    private void initmsucess() {
        if (mdistance.getText().toString().length() > 0) {
            save.setEnabled(true);
        } else {
            save.setEnabled(false);
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

}


