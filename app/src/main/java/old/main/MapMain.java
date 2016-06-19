package old.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.example.ygh.falltestprogram.R;

import tools.PlayTsSound;

/**
 * Created by Y-GH on 2016/6/12.
 */
public class MapMain extends AppCompatActivity implements View.OnClickListener {
    private Toolbar toolbar;
    private ImageView huijia, chaoshi, yaodain, cesuo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.old_map_main);
        initView();
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("地图");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.back1);

        huijia = (ImageView) findViewById(R.id.huijia);
        chaoshi = (ImageView) findViewById(R.id.chaoshi);
        yaodain = (ImageView) findViewById(R.id.yaodian);
        cesuo = (ImageView) findViewById(R.id.cesuo);
        huijia.setOnClickListener(this);
        chaoshi.setOnClickListener(this);
        yaodain.setOnClickListener(this);
        cesuo.setOnClickListener(this);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new PlayTsSound(MapMain.this, R.raw.back);
                finish();
            }
        });


    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.huijia:
                intent = new Intent(MapMain.this, BaidumapActivity.class);
                intent.putExtra("action", 1);
                startActivity(intent);

                break;
            case R.id.chaoshi:
                intent = new Intent(MapMain.this, BaidumapActivity.class);
                intent.putExtra("action", 2);
                startActivity(intent);
                break;
            case R.id.yaodian:
                intent = new Intent(MapMain.this, BaidumapActivity.class);
                intent.putExtra("action", 3);
                startActivity(intent);
                break;
            case R.id.cesuo:
                intent = new Intent(MapMain.this, BaidumapActivity.class);
                intent.putExtra("action", 4);
                startActivity(intent);
                break;
        }

    }
}
