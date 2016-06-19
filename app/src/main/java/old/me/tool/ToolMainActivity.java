package old.me.tool;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.example.ygh.falltestprogram.R;

import tools.PlayTsSound;
import tools.Utils;

public class ToolMainActivity extends AppCompatActivity implements OnClickListener{
	private TextView btnlight;
	private TextView btnbigjing;
	private TextView btnmath;
	private Context context=ToolMainActivity.this;
	private Toolbar toolbar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_tool);
		initView();
	}

	private void initView() {
		toolbar = (Toolbar) findViewById(R.id.toolbar);
		toolbar.setTitle("家里情况");
		setSupportActionBar(toolbar);
		toolbar.setNavigationIcon(R.mipmap.back1);

		toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				new PlayTsSound(context, R.raw.back);
				Utils.finish(ToolMainActivity.this);
			}
		});

		btnlight=(TextView) findViewById(R.id.light_btn);
		btnbigjing=(TextView) findViewById(R.id.bigjing_btn);
		btnmath=(TextView) findViewById(R.id.math_btn);
		btnlight.setOnClickListener(this);
		btnbigjing.setOnClickListener(this);
		btnmath.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.light_btn:
			new PlayTsSound(context, R.raw.light);
			Utils.start_Activity(ToolMainActivity.this, LightActivity.class);
			break;
		case R.id.bigjing_btn:
			new PlayTsSound(context, R.raw.bigjing);
			Utils.start_Activity(ToolMainActivity.this, BigCamearActivity.class);
			break;
		case R.id.math_btn:
			new PlayTsSound(context, R.raw.calculatr);
			Utils.start_Activity(ToolMainActivity.this, CalculatorActivity.class);
			break;
		default:
			break;
		}
		
	}

}
