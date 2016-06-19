package old.me.tool;

import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.ygh.falltestprogram.R;

import java.util.List;

import old.me.About_Us;
import tools.PlayTsSound;

public class LightActivity extends AppCompatActivity {
	private Button mButton;
	private boolean flag = false;
	private Camera camera;
	private Toolbar toolbar;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.light_tool);
		mButton = (Button) findViewById(R.id.btn_light);
		camera = Camera.open();
		mButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (flag) {
					flag = false;
					turnLightOff(camera);
					mButton.setBackgroundResource(R.drawable.shou_off);
				} else {
					flag = true;
					turnLightOn(camera);
					mButton.setBackgroundResource(R.drawable.shou_on);
				}

			}
		});

		toolbar = (Toolbar) findViewById(R.id.toolbar);
		toolbar.setTitle("手电筒");
		setSupportActionBar(toolbar);
		toolbar.setNavigationIcon(R.mipmap.back1);

		toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				new PlayTsSound(LightActivity.this, R.raw.back);
				finish();
			}
		});
	}

//	@Override
//	public boolean onKeyDown(int keyCode, KeyEvent event) {
//		camera.stopPreview();
//		camera.release();
//		camera = null;
//		return super.onKeyDown(keyCode, event);
//	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		camera.stopPreview();
		camera.release();
		camera = null;
	}

	private static void turnLightOn(Camera mCamera) {
		if (mCamera == null) {
			return;
		}
		Parameters parameters = mCamera.getParameters();
		if (parameters == null) {
			return;
		}
		List<String> flashModes = parameters.getSupportedFlashModes();
		// Check if camera flash exists
		if (flashModes == null) {
			// Use the screen as a flashlight (next best thing)
			return;
		}
		String flashMode = parameters.getFlashMode();
		if (!Parameters.FLASH_MODE_TORCH.equals(flashMode)) {
			// Turn on the flash
			if (flashModes.contains(Parameters.FLASH_MODE_TORCH)) {
				parameters.setFlashMode(Parameters.FLASH_MODE_TORCH);
				mCamera.setParameters(parameters);
				mCamera.startPreview();
			} else {
			}
		}
	}

	private static void turnLightOff(Camera mCamera) {
		if (mCamera == null) {
			return;
		}
		Parameters parameters = mCamera.getParameters();
		if (parameters == null) {
			return;
		}
		List<String> flashModes = parameters.getSupportedFlashModes();
		String flashMode = parameters.getFlashMode();
		// Check if camera flash exists
		if (flashModes == null) {
			return;
		}
		if (!Parameters.FLASH_MODE_OFF.equals(flashMode)) {
			// Turn off the flash
			if (flashModes.contains(Parameters.FLASH_MODE_OFF)) {
				parameters.setFlashMode(Parameters.FLASH_MODE_OFF);
				mCamera.setParameters(parameters);
				mCamera.stopPreview();
			} else {
				Log.e("__TAG___", "FLASH_MODE_OFF not supported");
			}
		}
	}

}
