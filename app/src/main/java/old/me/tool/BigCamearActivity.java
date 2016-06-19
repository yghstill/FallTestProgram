package old.me.tool;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.PreviewCallback;
import android.hardware.Camera.ShutterCallback;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

import com.example.ygh.falltestprogram.R;

import java.io.IOException;
import java.util.List;

import tools.PlayTsSound;

public class BigCamearActivity extends AppCompatActivity implements SurfaceHolder.Callback, PreviewCallback{
	private static final String tag="StandardCamera";
	private boolean isPreview = false;
	private SurfaceView mPreviewSV = null; 
	private SurfaceHolder mySurfaceHolder = null;
	private Camera myCamera = null;
	private Bitmap mBitmap = null;
	private AutoFocusCallback myAutoFocusCallback = null;
	boolean flag = true;
	private SeekBar mZoomBar = null;
	private Toolbar toolbar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bigcamear);

		initView();
		mySurfaceHolder = mPreviewSV.getHolder();
		mySurfaceHolder.setFormat(PixelFormat.TRANSLUCENT);
		mySurfaceHolder.addCallback(this);
		mySurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);


		myAutoFocusCallback = new AutoFocusCallback() {

			public void onAutoFocus(boolean success, Camera camera) {
				// TODO Auto-generated method stub
				if(success)
				{
					Log.i(tag, "myAutoFocusCallback: success...");


				}
				else
				{
					Log.i(tag, "myAutoFocusCallback: 婢惰精瑙︽禍锟�?.");

				}


			}
		};
		
		//娣诲姞ZoomBar
		mZoomBar = (SeekBar)findViewById(R.id.seekbar_zoom);
		mZoomBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				Parameters p = myCamera.getParameters();
				p.setZoom(progress);
				myCamera.setParameters(p);
			}
		});

	}
	
	public void initView(){
		toolbar = (Toolbar) findViewById(R.id.toolbar);
		toolbar.setTitle("家里情况");
		setSupportActionBar(toolbar);
		toolbar.setNavigationIcon(R.mipmap.back1);

		toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				new PlayTsSound(BigCamearActivity.this, R.raw.back);
				finish();
			}
		});
		mPreviewSV = (SurfaceView)findViewById(R.id.previewSV);
		WindowManager wm = (WindowManager)getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
        LayoutParams lpSV = mPreviewSV.getLayoutParams();
        lpSV.width = display.getWidth();
        lpSV.height = (int) ((float)display.getHeight()*0.77);
        mPreviewSV.setLayoutParams(lpSV);


	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width,int height) 
	{
		// TODO Auto-generated method stub		
		Log.i(tag, "SurfaceHolder.Callback:surfaceChanged!");
		initCamera();

	}


	public void surfaceCreated(SurfaceHolder holder) 
	{
		// TODO Auto-generated method stub		
		myCamera = Camera.open();
		try {
			myCamera.setPreviewDisplay(mySurfaceHolder);
			Log.i(tag, "SurfaceHolder.Callback: surfaceCreated!");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			if(null != myCamera){
				myCamera.release();
				myCamera = null;
			}
			e.printStackTrace();
		}



	}


	public void surfaceDestroyed(SurfaceHolder holder) 

	{
		// TODO Auto-generated method stub
		Log.i(tag, "SurfaceHolder.Callback閿涙瓔urface Destroyed");
		if(null != myCamera)
		{
			myCamera.setPreviewCallback(null); 

			myCamera.stopPreview(); 
			isPreview = false; 
			myCamera.release();
			myCamera = null;     
		}

	}

	public void initCamera(){
		if(isPreview){
			myCamera.stopPreview();
		}
		if(null != myCamera){			
			Parameters myParam = myCamera.getParameters();


			myParam.setPictureFormat(PixelFormat.JPEG);


			myParam.setPictureSize(1280, 960);  //
			myParam.setPreviewSize(960, 720);	//		
			//myParam.set("rotation", 90);  			
			myCamera.setDisplayOrientation(90);  
			List<String> focuseMode = (myParam.getSupportedFocusModes());
			for(int i=0; i<focuseMode.size(); i++){
				Log.i(tag, focuseMode.get(i));
				if(focuseMode.get(i).contains("continuous")){
					myParam.setFocusMode(Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);
				}
				else{
					myParam.setFocusMode(Parameters.FOCUS_MODE_AUTO);

				}
			}
			//璁剧疆mZoomBar鐨勬渶澶у��
			mZoomBar.setMax(myParam.getMaxZoom());
//			myCamera.setParameters(myParam);			
			myCamera.startPreview();
			myCamera.autoFocus(myAutoFocusCallback);
			isPreview = true;
		}
	}

	ShutterCallback myShutterCallback = new ShutterCallback() 
	{

		public void onShutter() {
			// TODO Auto-generated method stub
			Log.i(tag, "myShutterCallback:onShutter...");

		}
	};
	PictureCallback myRawCallback = new PictureCallback() 
	{

		public void onPictureTaken(byte[] data, Camera camera) {
			// TODO Auto-generated method stub
			Log.i(tag, "myRawCallback:onPictureTaken...");

		}
	};

	@Override
	public void onBackPressed()
	{
		// TODO Auto-generated method stub
		super.onBackPressed();
		BigCamearActivity.this.finish();
	}



	class UpdateThread implements Runnable{

		public void run() {
			// TODO Auto-generated method stub
			while(flag){
				if(myCamera!=null && isPreview)
					myCamera.autoFocus(myAutoFocusCallback); //閼奉亜濮╅懕姘卞妽
				myCamera.setOneShotPreviewCallback(BigCamearActivity.this); //onPreviewFrame闁插奔绱伴幒銉ュ綀閸掔増鏆熼幑锟�?			myCamera.stopPreview(); //閸嬫粍顒涙０鍕瀲
				flag = false;

				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}



	public void onPreviewFrame(byte[] data, Camera camera) {
		// TODO Auto-generated method stub

	}

}
