package children.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.ygh.falltestprogram.R;

/**
 * Created by Y-GH on 2016/6/13.
 */
public class Health extends AppCompatActivity implements View.OnClickListener{
    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.son_main_health);
        initView();
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("健康情况");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.back1);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onClick(View v) {


    }
}
