package old.me;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.ygh.falltestprogram.R;

import tools.PlayTsSound;


public class About_Us extends AppCompatActivity {
	private Context context = About_Us.this;
	private Toolbar toolbar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about_us);
		findViewById();
	}

	private void findViewById() {
		toolbar = (Toolbar) findViewById(R.id.toolbar);
		toolbar.setTitle("关于我们");
		setSupportActionBar(toolbar);
		toolbar.setNavigationIcon(R.mipmap.back1);
		TextView tvablout = (TextView) findViewById(R.id.ablout);
		TextView version = (TextView) findViewById(R.id.etversion);
		version.setText("版本:" + getResources().getString(R.string.et_version));
		tvablout.setText(ToDBC(getResources().getString(R.string.ablout)));
		toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				new PlayTsSound(About_Us.this, R.raw.back);
				finish();
			}
		});
	}



	/**
	 * 半角转换为全角
	 *
	 * @param input
	 * @return
	 */
	public static String ToDBC(String input) {
		char[] c = input.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == 12288) {
				c[i] = (char) 32;
				continue;
			}
			if (c[i] > 65280 && c[i] < 65375)
				c[i] = (char) (c[i] - 65248);
		}
		return new String(c);
	}

}
