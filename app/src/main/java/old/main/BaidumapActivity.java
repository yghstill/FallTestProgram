package old.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.overlayutil.PoiOverlay;
import com.baidu.mapapi.overlayutil.WalkingRouteOverlay;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchOption;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRoutePlanOption;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.example.ygh.falltestprogram.R;

import loading.BaseApplication;
import tools.MyOrientationListener;

public class BaidumapActivity extends AppCompatActivity {
	// private static final int FLAG_SEND_SUCCESS = 1;
	// private static final String MSG_SEND_ERROR = "位置上传出错。";
	// private static final String MSG_SEND_SUCCESS = "位置上传成功。";
	public static final String MSG_SERVER_ERROR = "请求服务器错误。";
	public static final String MSG_SERVER_TIMEOUT = "请求服务器超时。";
	public static final String MSG_RESPONCE_TIMEOUT = "服务器响应超时。";

	private MapView mMapView;
	private BaiduMap mBaiduMap;
	private TextView Location;
	private Context context = BaidumapActivity.this;

	// 定位相关
	private LocationClient mLocationClient;
	private MyLocationListener mLocationListener;
	private boolean isFirstIn = true;
	private double mLatitude;
	private double mLongtitude;
	private BDLocation location;
	// 自定义定位图标
	private BitmapDescriptor mIconLocation;
	private MyOrientationListener myOrientationListener;
	private float mCurrentX;
	private LocationMode mLocationMode;

	private BaseApplication mApplication;
	private SharedPreferences pref;
	private SharedPreferences.Editor editor;
	private ImageView goHome;
	private ImageView cesuo;
	private ImageView chaoshi;
	private ImageView yaodian;
	private RoutePlanSearch routePlanSearch;
	private PoiSearch poiSearch;
	private Toolbar toolbar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SDKInitializer.initialize(getApplicationContext());//此方法要在setContentView之前
		setContentView(R.layout.mapview);
		mApplication = (BaseApplication) getApplication();
		// setHasOptionsMenu(true);
		initView();
		// 初始化定位
		initLocation();

		Intent intent=getIntent();
		if(intent.getExtras().getInt("action")==1){
			walkSearch();  //回家
		}else if(intent.getExtras().getInt("action")==2){
			nearbySearch(1,"厕所");
		}else if(intent.getExtras().getInt("action")==3){
			nearbySearch(1,"超市");
		}else if(intent.getExtras().getInt("action")==4){
			nearbySearch(1,"药店");
		}
	}

	private void initView() {
		toolbar = (Toolbar) findViewById(R.id.toolbar);
		toolbar.setTitle("地图详情");
		setSupportActionBar(toolbar);
		toolbar.setNavigationIcon(R.mipmap.back1);

		toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		// 定位相关
		Location = (TextView) findViewById(R.id.location);
		mMapView = (MapView) findViewById(R.id.id_bmapView);
		mBaiduMap = mMapView.getMap();
		MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(15.0f);
		mBaiduMap.setMapStatus(msu);
		routePlanSearch = RoutePlanSearch.newInstance();
		routePlanSearch
				.setOnGetRoutePlanResultListener(routePlanResultListener);

		poiSearch = PoiSearch.newInstance();
		// 检索
		poiSearch.setOnGetPoiSearchResultListener(poiSearchListener);

	}

//	@Override
//	public void onClick(View v) {
//		switch (v.getId()) {
//			case R.id.huijia:
//				walkSearch();
//				break;
//			case R.id.cesuo:
//				nearbySearch(1,"厕所");
//				break;
//			case R.id.chaoshi:
//				nearbySearch(1,"超市");
//				break;
//			case R.id.yaodian:
//				nearbySearch(1,"药店");
//				break;
//			default:
//				break;
//		}
//
//	}

	private void walkSearch() {
		WalkingRoutePlanOption walkOption = new WalkingRoutePlanOption();
		//初始起始点
		LatLng latLng1 = new LatLng(mLatitude, mLongtitude);
		walkOption.from(PlanNode.withLocation(latLng1));
		//初始终点
		pref = getSharedPreferences("user", MODE_PRIVATE);
		float l1= pref.getFloat("mLatitude", 0);
		float l2=pref.getFloat("mLongtitude", 0);
		LatLng latLng2 = new LatLng(l1, l2);
		walkOption.to(PlanNode.withLocation(latLng2));
		routePlanSearch.walkingSearch(walkOption);
	}


	OnGetRoutePlanResultListener routePlanResultListener = new OnGetRoutePlanResultListener() {

		/**
		 * 回家
		 */
		@Override
		public void onGetWalkingRouteResult(
				WalkingRouteResult walkingRouteResult) {
			mBaiduMap.clear();
			if (walkingRouteResult == null
					|| walkingRouteResult.error != SearchResult.ERRORNO.NO_ERROR) {
				Toast.makeText(BaidumapActivity.this, "加载出错，请检查网络！",
						Toast.LENGTH_SHORT).show();
			}
			if (walkingRouteResult.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
				// TODO
				return;
			}
			if (walkingRouteResult.error == SearchResult.ERRORNO.NO_ERROR) {
				WalkingRouteOverlay walkingRouteOverlay = new WalkingRouteOverlay(
						mBaiduMap);
				walkingRouteOverlay.setData(walkingRouteResult.getRouteLines()
						.get(0));
				mBaiduMap.setOnMarkerClickListener(walkingRouteOverlay);
				walkingRouteOverlay.addToMap();
				walkingRouteOverlay.zoomToSpan();
			}
		}

		@Override
		public void onGetDrivingRouteResult(DrivingRouteResult arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onGetTransitRouteResult(TransitRouteResult arg0) {
			// TODO Auto-generated method stub

		}
	};

	OnGetPoiSearchResultListener poiSearchListener = new OnGetPoiSearchResultListener() {
		@Override
		public void onGetPoiResult(PoiResult poiResult) {
			if (poiResult == null
					|| poiResult.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {
				Toast.makeText(BaidumapActivity.this, "加载出错，请检查网络！",
						Toast.LENGTH_LONG).show();
				return;
			}

			if (poiResult.error == SearchResult.ERRORNO.NO_ERROR) {
				mBaiduMap.clear();
				MyPoiOverlay poiOverlay = new MyPoiOverlay(mBaiduMap);
				poiOverlay.setData(poiResult);// 璁剧疆POI鏁版嵁
				mBaiduMap.setOnMarkerClickListener(poiOverlay);
				poiOverlay.addToMap();
				poiOverlay.zoomToSpan();

			}
		}

		@Override
		public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {
			if (poiDetailResult.error != SearchResult.ERRORNO.NO_ERROR) {
				Toast.makeText(BaidumapActivity.this, "检索出错！",
						Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(
						BaidumapActivity.this,
						poiDetailResult.getName() + ": "
								+ poiDetailResult.getAddress(),
						Toast.LENGTH_LONG).show();
			}
		}
	};


	class MyPoiOverlay extends PoiOverlay {

		public MyPoiOverlay(BaiduMap arg0) {
			super(arg0);
		}

		@Override
		public boolean onPoiClick(int arg0) {
			super.onPoiClick(arg0);
			PoiInfo poiInfo = getPoiResult().getAllPoi().get(arg0);
			poiSearch.searchPoiDetail(new PoiDetailSearchOption()
					.poiUid(poiInfo.uid));
			return true;
		}

	}

	private void nearbySearch(int page,String edit) {
		PoiNearbySearchOption nearbySearchOption = new PoiNearbySearchOption();
		nearbySearchOption.location(new LatLng(mLatitude, mLongtitude));
		nearbySearchOption.keyword(edit);
		nearbySearchOption.radius(1000);// 妫�绱㈠崐寰勶紝鍗曚綅鏄背
		nearbySearchOption.pageNum(page);
		poiSearch.searchNearby(nearbySearchOption);// 鍙戣捣闄勮繎妫�绱㈣姹�
	}


	private void initLocation() {

		mLocationMode = LocationMode.NORMAL;
		mLocationClient = new LocationClient(context);
		mLocationListener = new MyLocationListener();
		mLocationClient.registerLocationListener(mLocationListener);

		LocationClientOption option = new LocationClientOption();
		option.setCoorType("bd09ll");
		option.setIsNeedAddress(true);
		option.setOpenGps(true);
		option.setScanSpan(1000);
		mLocationClient.setLocOption(option);
		// 初始化图标
		mIconLocation = BitmapDescriptorFactory
				.fromResource(R.drawable.navi_map_gps_locked);
		myOrientationListener = new MyOrientationListener(context);

		myOrientationListener
				.setOnOrientationListener(new MyOrientationListener.OnOrientationListener() {
					@Override
					public void onOrientationChanged(float x) {
						mCurrentX = x;
					}
				});

		mLocationClient.start(); // 开始定位
	}

	@Override
	public void onResume() {
		super.onResume();
		// 在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
		mMapView.onResume();
	}

	@Override
	public void onStart() {
		super.onStart();
		// 开启定位
		mBaiduMap.setMyLocationEnabled(true);
		if (!mLocationClient.isStarted())
			mLocationClient.start();
		// 开启方向传感器
		myOrientationListener.start();
	}

	@Override
	public void onPause() {
		super.onPause();
		// 在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
		mMapView.onPause();
	}

	@Override
	public void onStop() {
		super.onStop();

		// 停止定位
		mBaiduMap.setMyLocationEnabled(false);
		mLocationClient.stop();
		// 停止方向传感器
		myOrientationListener.stop();

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		// 在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
		mMapView.onDestroy();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.baidumap_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.id_map_common:
				mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
				break;

			case R.id.id_map_site:
				mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
				break;

			case R.id.id_map_traffic:
				if (mBaiduMap.isTrafficEnabled()) {
					mBaiduMap.setTrafficEnabled(false);
					item.setTitle("实时交通(off)");
				} else {
					mBaiduMap.setTrafficEnabled(true);
					item.setTitle("实时交通(on)");
				}
				break;
			case R.id.id_map_location:
				centerToMyLocation();
				break;
			case R.id.id_map_mode_common:
				mLocationMode = LocationMode.NORMAL;
				break;
			case R.id.id_map_mode_following:
				mLocationMode = LocationMode.FOLLOWING;
				break;
			case R.id.id_map_mode_compass:
				mLocationMode = LocationMode.COMPASS;
				break;
			default:
				break;
		}

		// return super.onOptionsItemSelected(item);
		return true;
	}

	/**
	 * 定位到我的位置
	 */
	private void centerToMyLocation() {
		LatLng latLng = new LatLng(mLatitude, mLongtitude);
		MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(latLng);
		mBaiduMap.animateMapStatus(msu);
	}

	private class MyLocationListener implements BDLocationListener {
		@Override
		public void onReceiveLocation(BDLocation location) {

			MyLocationData data = new MyLocationData.Builder()//
					.direction(mCurrentX)//
					.accuracy(location.getRadius())//
					.latitude(location.getLatitude())//
					.longitude(location.getLongitude())//
					.build();
			mBaiduMap.setMyLocationData(data);
			// 设置自定义图标
			MyLocationConfiguration config = new MyLocationConfiguration(
					mLocationMode, true, mIconLocation);
			mBaiduMap.setMyLocationConfigeration(config);

			// 更新经纬度
			mLatitude = location.getLatitude();
			mLongtitude = location.getLongitude();

			Location.setText("我的位置:" + location.getAddrStr());
			if (isFirstIn) {
				LatLng latLng = new LatLng(location.getLatitude(),
						location.getLongitude());
				MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(latLng);
				mBaiduMap.animateMapStatus(msu);
				isFirstIn = false;
				mLocationMode = LocationMode.FOLLOWING;
//				Toast.makeText(context, location.getAddrStr(),
//						Toast.LENGTH_SHORT).show();
			}


		}


	}

}