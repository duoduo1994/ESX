package com.example.esycab;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationClientOption.AMapLocationMode;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMap.OnCameraChangeListener;
import com.amap.api.maps.AMap.OnMapClickListener;
import com.amap.api.maps.AMap.OnMapLongClickListener;
import com.amap.api.maps.AMap.OnMapTouchListener;
import com.amap.api.maps.AMap.OnMarkerClickListener;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.CoordinateConverter;
import com.amap.api.maps.CoordinateConverter.CoordType;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.RoutePara;
import com.example.amap.navi.GPSNaviActivity;
import com.example.esycab.entity.NTH_Halls;
import com.example.esycab.network.SmartFruitsRestClient;
import com.example.esycab.utils.ActivityCollector;
import com.example.esycab.utils.IvListener;
import com.example.esycab.utils.Load;
import com.example.esycab.utils.LoadingDialog;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class GaodeMapActivity extends Activity implements LocationSource,
		AMapLocationListener, OnMapClickListener, OnMapLongClickListener,
		OnCameraChangeListener, OnMapTouchListener, OnMarkerClickListener {

	public static void actionStart(Context context, NTH_Halls nth_H) {
		Bundle extras = new Bundle();
		Intent intent = new Intent(context, GaodeMapActivity.class);
		extras.putSerializable("param1", nth_H);
		intent.putExtras(extras);
		context.startActivity(intent);
	}

	public static void actionStart(Context context, String districtID) {
		Bundle extras = new Bundle();
		Intent intent = new Intent(context, GaodeMapActivity.class);
		extras.putString("districtID", districtID);
		intent.putExtras(extras);
		context.startActivity(intent);
	}

	private AMapLocation loca = null;
	private AMap aMap;
	private MapView mapView;
	private OnLocationChangedListener mListener;
	private AMapLocationClient mlocationClient;
	private AMapLocationClientOption mLocationOption;

	private Marker marker1, marker2;
	private CoordinateConverter converter;

	private TextView tv;
	private Button iv;

	private Bundle bundleNth;
	private List<NTH_Halls> list;
	private NTH_Halls nth_Halls = null, getNth_Halls = null;
	private String districtID = null;

	private LoadingDialog dialog = null;

	private View showview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
			// 透明状态栏
			getWindow().addFlags(
					WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON);
			// 透明导航栏
			// getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
		} else {
			this.requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏
			this.getWindow().setFlags(
					WindowManager.LayoutParams.FLAG_FULLSCREEN,
					WindowManager.LayoutParams.FLAG_FULLSCREEN);// 去掉信息栏
		}
		setContentView(R.layout.gaodemap);
		ActivityCollector.addActivity(this);

		mapView = (MapView) findViewById(R.id.map);
		mapView.onCreate(savedInstanceState);// 此方法必须重写
		showview = findViewById(R.id.showview);

		init();

		bundleNth = getIntent().getExtras();
		getNth_Halls = (NTH_Halls) bundleNth.getSerializable("param1");

		districtID = bundleNth.getString("districtID");

		initNTHOverlay();

		tv = (TextView) findViewById(R.id.tv_title);
		tv.setText("喜事堂");
		iv = (Button) findViewById(R.id.btn_back);
		iv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (System.currentTimeMillis() - HomeActivity.dianJiShiJian > 500) {

					HomeActivity.dianJiShiJian = System.currentTimeMillis();
					// 结束当前界面，并返回上一页面
					GaodeMapActivity.this.finish();
				}
			}
		});

		aMap.setOnMapClickListener(this);// 对amap添加单击地图事件监听器
		aMap.setOnMapLongClickListener(this);// 对amap添加长按地图事件监听器
		aMap.setOnCameraChangeListener(this);// 对amap添加移动地图事件监听器
		aMap.setOnMapTouchListener(this);// 对amap添加触摸地图事件监听器
		aMap.setOnMarkerClickListener(this);
		setMoreListener();
	}

	private ImageView iv_more;

	private void setMoreListener() {
		iv_more = (ImageView) findViewById(R.id.iv_more);
		iv_more.setOnClickListener(new IvListener(iv_more,
				GaodeMapActivity.this, 0));
	}

	private void checkBaiduMap(final LatLng lng) {
		show("您未安装百度地图，是否前往浏览器进行导航！", "提示：", "是", "否", 0, lng);
	}

	private TextView juTiXinXi, tiShi;
	private Dialog d;
	private Button p1;
	private Button n;
	private View v;

	private void showDialog() {
		show("您尚未安装高德地图app或app版本过低，点击确认安装？", "提示", "确认", "取消", 1, null);
	}

	private void show(String tishi, String biaoti, String b1, String b2,
			final int a, final LatLng lng) {
		if (GaodeMapActivity.this.isFinishing()) {
		} else {
			d = new Dialog(GaodeMapActivity.this, R.style.loading_dialog);
			v = LayoutInflater.from(GaodeMapActivity.this).inflate(
					R.layout.dialog, null);// 窗口布局
			d.setContentView(v);// 把设定好的窗口布局放到dialog中
			d.setCanceledOnTouchOutside(true);// 设定点击窗口空白处取消会话
			p1 = (Button) v.findViewById(R.id.p);
			n = (Button) v.findViewById(R.id.n);
			juTiXinXi = (TextView) v.findViewById(R.id.banben_xinxi);
			tiShi = (TextView) v.findViewById(R.id.banben_gengxin);
//			fuZhi = (TextView) v.findViewById(R.id.fuzhi_jiantieban);
			juTiXinXi.setText(tishi);
			tiShi.setText(biaoti);
			p1.setText(b1);
			n.setText(b2);
			p1.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					if (a == 1) {
						AMapUtils.getLatestAMapApp(getApplicationContext());
					} else {
						try {
							String url = String
									.format("http://api.map.baidu.com/direction?origin=latlng:"
											+ lng.latitude
											+ ","
											+ lng.longitude
											+ "|name:我的位置&destination=latlng:"
											+ endNTH_Halls.getLatitude()
											+ ","
											+ endNTH_Halls.getLongitude()
											+ "|name:"
											+ endNTH_Halls.getName()
											+ "&mode=driving&region=宁波&output=html&src=宁波甬尚鲜|乡村喜宴#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end");

							Intent intent = new Intent(Intent.ACTION_VIEW);
							intent.setData(Uri.parse(url));
							startActivity(intent);
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
					d.dismiss();
				}
			});
			n.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					d.dismiss();
				}
			});
			d.show();
		}
	}

	/**
	 * 初始化
	 */
	private void init() {
		if (aMap == null) {
			aMap = mapView.getMap();

			aMap.setLocationSource(this);// 设置定位监听
			aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
			aMap.getUiSettings().setScaleControlsEnabled(true);
			// 设置中心点和缩放比例
			aMap.moveCamera(CameraUpdateFactory.zoomTo(13));
			aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
			// 设置定位的类型为定位模式 ，可以由定位、跟随或地图根据面向方向旋转几种
			aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
		}

	}

	private int ciShu = 0;
	private boolean isFalse = true;

	private void initNTHOverlay() {
		// TODO Auto-generated method stub
		dialog = new LoadingDialog(GaodeMapActivity.this, "数据加载中，请稍候...");
		dialog.showDialog();
		RequestParams hall = new RequestParams();
		if (getNth_Halls != null) {
			hall.add("ID", getNth_Halls.getDistrictID());
		} else if (!TextUtils.isEmpty(districtID)) {
			hall.add("ID", districtID);
		}

		SmartFruitsRestClient.post("HallsHandler.ashx?Action=getVillage", hall,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
						dialog.closeDialog();
						ciShu = 0;
						isFalse = true;
						String result = new String(arg2);
						System.out.println(result);
						list = new ArrayList<NTH_Halls>();
						try {
							JSONObject json = new JSONObject(result.trim());
							JSONArray jsArray = json.getJSONArray("乡村");
							if (jsArray != null) {
								int size = jsArray.length();
								list.clear();
								JSONObject tJson = null;
								for (int i = 0; i < size; i++) {
									nth_Halls = new NTH_Halls();
									tJson = jsArray.getJSONObject(i);
									if (!TextUtils.isEmpty(tJson
											.getString("WgsLongitude"))
											&& !TextUtils.isEmpty(tJson
													.getString("WgsLatitude"))) {
										String name = tJson.getString("Name");
										nth_Halls.setName(name);

										String pkHallID = tJson
												.getString("HallID");
										nth_Halls.setPkHallID(pkHallID);

										String contact = tJson
												.getString("Principal");
										nth_Halls.setContact(contact);

										String contactTel = tJson
												.getString("PrincipalTel");
										nth_Halls.setContactTel(contactTel);

										String fkAuctionCdrID = tJson
												.getString("FeastDt");
										nth_Halls
												.setFkAuctionCdrID(fkAuctionCdrID);
										String detailAdr = tJson
												.getString("DetailAddr");
										nth_Halls.setDetailAdr(detailAdr);

										String longitude = tJson
												.getString("Longitude");
										nth_Halls.setLongitude(longitude);

										String latitude = tJson
												.getString("Latitude");
										nth_Halls.setLatitude(latitude);

										String wgsLongitude = tJson
												.getString("WgsLongitude");
										nth_Halls.setWgsLongitude(wgsLongitude);
										String wgslatitude = tJson
												.getString("WgsLatitude");
										nth_Halls.setWgsLatitude(wgslatitude);

										String districtID = tJson
												.getString("DistrictID");
										nth_Halls.setDistrictID(districtID);
										String imageUrl = tJson
												.getString("ImageUrl");
										nth_Halls.setImageUrl(imageUrl);
										list.add(nth_Halls);
									}
								}
								System.out.println("list.size()" + list.size());
								listMap();
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}

					@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
							Throwable arg3) {
						dialog.closeDialog();
						if (ciShu >= 3) {
							isFalse = false;
							Toast.makeText(GaodeMapActivity.this,
									"数据加载失败，请重新加载~", Toast.LENGTH_SHORT).show();
						}
						if (isFalse) {
							initNTHOverlay();
							ciShu++;
						}
					}

				});
	}

	/***
	 * 将数据显示在地图上
	 * 
	 * @param lng
	 */

	private void listMap() {

		if (getNth_Halls != null) {
			for (int i = 0; i < list.size(); i++) {
				System.out.println(list.get(i).getWgsLatitude() + "  v  "
						+ list.get(i).getWgsLongitude());
				// 绘制marker
				aMap.addMarker(new MarkerOptions()
						.position(
								latLngConverter(list.get(i).getWgsLatitude(),
										list.get(i).getWgsLongitude()))
						.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
								.decodeResource(getResources(),
										R.drawable.icon_openmap_mark)))
						.draggable(false));

				if (list.get(i).getWgsLatitude()
						.equals(getNth_Halls.getWgsLatitude())
						&& list.get(i).getWgsLongitude()
								.equals(getNth_Halls.getWgsLongitude())) {
					aMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
							latLngConverter(list.get(i).getWgsLatitude(), list
									.get(i).getWgsLongitude()), 13));
					aMap.addMarker(new MarkerOptions()
							.position(
									latLngConverter(list.get(i)
											.getWgsLatitude(), list.get(i)
											.getWgsLongitude()))
							.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
									.decodeResource(getResources(),
											R.drawable.icon_openmap_focuse_mark))));
					showview.setVisibility(View.VISIBLE);
					addView(list.get(i));

				}
			}
		} else if (!TextUtils.isEmpty(districtID)) {
			for (int i = 0; i < list.size(); i++) {
				System.out.println(list.get(i).getWgsLatitude() + "  v  "
						+ list.get(i).getWgsLongitude());
				// 绘制marker
				aMap.addMarker(new MarkerOptions()
						.position(
								latLngConverter(list.get(i).getWgsLatitude(),
										list.get(i).getWgsLongitude()))
						.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
								.decodeResource(getResources(),
										R.drawable.icon_openmap_mark)))
						.draggable(false));
			}
			aMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
					latLngConverter(list.get(0).getWgsLatitude(), list.get(0)
							.getWgsLongitude()), 13));
			aMap.addMarker(new MarkerOptions().position(
					latLngConverter(list.get(0).getWgsLatitude(), list.get(0)
							.getWgsLongitude())).icon(
					BitmapDescriptorFactory.fromBitmap(BitmapFactory
							.decodeResource(getResources(),
									R.drawable.icon_openmap_focuse_mark))));
			showview.setVisibility(View.VISIBLE);
			addView(list.get(0));

		}

	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onResume() {
		super.onResume();
		mapView.onResume();
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onPause() {
		super.onPause();
		mapView.onPause();
		deactivate();
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		mapView.onSaveInstanceState(outState);
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onDestroy() {
		super.onDestroy();
		mapView.onDestroy();
		if (null != mlocationClient) {
			mlocationClient.onDestroy();
		}
	}

	private boolean isFirst = false;

	/**
	 * 定位成功后回调函数
	 */
	@Override
	public void onLocationChanged(AMapLocation amapLocation) {
		if (mListener != null && amapLocation != null) {
			if (amapLocation != null && amapLocation.getErrorCode() == 0) {
				if (isFirst) {
					mListener.onLocationChanged(amapLocation);// 显示系统小蓝点
				}
				loca = amapLocation;
			} else {
				String errText = "定位失败," + amapLocation.getErrorCode() + ": "
						+ amapLocation.getErrorInfo();
				Log.e("AmapErr", errText);
			}
		}
	}

	private boolean isFirstActivate = false;

	/**
	 * 激活定位
	 */
	@Override
	public void activate(OnLocationChangedListener listener) {
		if (isFirstActivate) {
			isFirst = true;
		}
		isFirstActivate = true;
		mListener = listener;
		if (mlocationClient == null) {
			mlocationClient = new AMapLocationClient(this);
			mLocationOption = new AMapLocationClientOption();
			// 设置定位监听
			mlocationClient.setLocationListener(this);
			// 设置为高精度定位模式
			mLocationOption.setLocationMode(AMapLocationMode.Hight_Accuracy);
			// 设置定位参数
			mlocationClient.setLocationOption(mLocationOption);
			// 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
			// 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
			// 在定位结束后，在合适的生命周期调用onDestroy()方法
			// 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
			mlocationClient.startLocation();
		}
	}

	/**
	 * 停止定位
	 */
	@Override
	public void deactivate() {
		mListener = null;
		if (mlocationClient != null) {
			mlocationClient.stopLocation();
			mlocationClient.onDestroy();
		}
		mlocationClient = null;
	}

	@Override
	public void onMapClick(LatLng arg0) {
		// TODO Auto-generated method stub
		// showview.setVisibility(View.GONE);
	}

	@Override
	public void onMapLongClick(LatLng arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onCameraChange(CameraPosition arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onCameraChangeFinish(CameraPosition arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTouch(MotionEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onMarkerClick(Marker arg0) {
		// TODO Auto-generated method stub
		isFirst = false;
		showview.setVisibility(View.VISIBLE);
		if (aMap != null) {

			for (int i = 0; i < list.size(); i++) {
				if (arg0.getPosition().equals(
						latLngConverter(list.get(i).getWgsLatitude(),
								list.get(i).getWgsLongitude()))) {
					arg0.setIcon(BitmapDescriptorFactory
							.fromBitmap(BitmapFactory.decodeResource(
									getResources(),
									R.drawable.icon_openmap_focuse_mark)));
					addView(list.get(i));
				} else {
					aMap.addMarker(new MarkerOptions().position(
							latLngConverter(list.get(i).getWgsLatitude(), list
									.get(i).getWgsLongitude())).icon(
							BitmapDescriptorFactory.fromBitmap(BitmapFactory
									.decodeResource(getResources(),
											R.drawable.icon_openmap_mark))));
				}
			}

		}
		return false;
	}

	private TextView tv1, tv2, tv3, tv4, tv5, tv6;
	private ImageView im;
	private NTH_Halls endNTH_Halls = null;

	private void addView(final NTH_Halls nth_Halls) {
		endNTH_Halls = nth_Halls;
		System.out.println(nth_Halls.getName() + nth_Halls.getDetailAdr()
				+ nth_Halls.getContactTel());
		tv1 = (TextView) findViewById(R.id.maphallname);
		tv2 = (TextView) findViewById(R.id.maphalladdress);
		tv3 = (TextView) findViewById(R.id.maphalltel);
		tv4 = (TextView) findViewById(R.id.mapgotohall);
		tv5 = (TextView) findViewById(R.id.mapgotocompany);
		tv6 = (TextView) findViewById(R.id.myhallusername);
		im = (ImageView) findViewById(R.id.maphallimage);
		tv1.setText(nth_Halls.getName());
		tv2.setText(nth_Halls.getDetailAdr());
		tv3.setText(nth_Halls.getContactTel());
		tv6.setText(nth_Halls.getContact());
		Load.imageLoader.displayImage((nth_Halls.getImageUrl()), im,
				Load.options);

		tv4.setClickable(true);
		tv4.setFocusable(true);
		tv4.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (TextUtils.isEmpty(nth_Halls.getWgsLatitude())
						|| TextUtils.isEmpty(nth_Halls.getWgsLongitude())) {
					Toast.makeText(getApplicationContext(), "请选择您要去喜事堂",
							Toast.LENGTH_SHORT).show();
					return;
				}

				if (TextUtils.isEmpty(String.valueOf(loca.getLatitude()))
						|| TextUtils.isEmpty(String.valueOf(loca.getLongitude()))) {
					Toast.makeText(getApplicationContext(), "定位失败，无法进行导航",
							Toast.LENGTH_SHORT).show();
					return;
				}
				showPopwindow(nth_Halls);

			}
		});

		tv5.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				nth_Halls.setWgsLatitude("29.70890");
				nth_Halls.setWgsLongitude("121.85672");
				nth_Halls.setLatitude("29.712089");
				nth_Halls.setLongitude("121.867174");
				nth_Halls.setName("宁波甬尚鲜");
				if (TextUtils.isEmpty(String.valueOf(loca.getLatitude()))
						|| TextUtils.isEmpty(String.valueOf(loca.getLongitude()))) {
					Toast.makeText(getApplicationContext(), "定位失败，无法导航",
							Toast.LENGTH_SHORT).show();
					return;
				}
				showPopwindow(nth_Halls);

			}
		});
	}
	
	/**
	 * popwindow
	 * @param nth_Halls
	 */
	public void showPopwindow(final NTH_Halls nth_Halls) {

		final LatLng lng = latLngConverter(nth_Halls.getWgsLatitude(),
				nth_Halls.getWgsLongitude());

		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.selectmap_dialog, null);
		// 下面是两种方法得到宽度和高度 getWindow().getDecorView().getWidth()

		final PopupWindow window = new PopupWindow(view,
				WindowManager.LayoutParams.MATCH_PARENT,
				WindowManager.LayoutParams.WRAP_CONTENT);

		// 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
		window.setFocusable(true);

		// 实例化一个ColorDrawable颜色为半透明
		ColorDrawable dw = new ColorDrawable(0x00000000);
		window.setBackgroundDrawable(dw);

		// 设置popWindow的显示和消失动画
		window.setAnimationStyle(R.style.mypopwindow_anim_style);

		WindowManager manager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		@SuppressWarnings("deprecation")
		// 获取xoff
		int xpos = manager.getDefaultDisplay().getWidth() / 2
				- window.getWidth() / 2;
		// 在底部显示
		window.showAtLocation(
				GaodeMapActivity.this.findViewById(R.id.btn_back),
				Gravity.BOTTOM, xpos, 0);

		// 这里检验popWindow里的button是否可以点击

		view.findViewById(R.id.gaodemap).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
//						Toast.makeText(getApplicationContext(), "高德Map",
//								Toast.LENGTH_SHORT).show();
						try {
							window.dismiss();
							RoutePara routePara = new RoutePara();
							// 注意点：以下参数为必填项，查看其jar中的代码，发现少一参数就给你报非法参数

							routePara.setStartPoint(new LatLng(loca
									.getLatitude(), loca.getLongitude()));
							routePara.setEndPoint(latLngConverter(
									endNTH_Halls.getWgsLatitude(),
									endNTH_Halls.getWgsLongitude()));
							routePara.setEndName(endNTH_Halls.getName());
							routePara.setStartName("我的位置");
							// 调起高德地图导航
							AMapUtils.openAMapDrivingRoute(routePara,
									getApplicationContext());
						} catch (com.amap.api.maps.AMapException e) {
							// 如果没安装会进入异常，调起下载页面
							showDialog();
						}
					}
				});

		view.findViewById(R.id.baidumap).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						window.dismiss();
						LatLng lng = lngConverter(loca.getLatitude(),
								loca.getLongitude());
						// 调起百度地图导航
						try {
							String url = String
									.format("intent://map/direction?origin=latlng:"
											+ lng.latitude
											+ ","
											+ lng.longitude
											+ "|name:我的位置&destination=latlng:"
											+ endNTH_Halls.getLatitude()
											+ ","
											+ endNTH_Halls.getLongitude()
											+ "|name:"
											+ endNTH_Halls.getName()
											+ "&mode=driving&src=乡村喜宴|导航#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end");
							startActivity(Intent.getIntent(url)); // 启动
						} catch (Exception e) {

							checkBaiduMap(lng);

						}
					}
				});

		view.findViewById(R.id.esycabmap).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
//						Toast.makeText(getApplicationContext(), "esycabmap",
//								Toast.LENGTH_SHORT).show();
						Bundle extras = new Bundle();
						Intent intent = new Intent(GaodeMapActivity.this,
								GPSNaviActivity.class);
						extras.putDouble("localati", loca.getLatitude());
						extras.putDouble("localong", loca.getLongitude());
						extras.putDouble("latitude", lng.latitude);
						extras.putDouble("longitude", lng.longitude);
						intent.putExtras(extras);
						startActivity(intent);
					}
				});

		view.findViewById(R.id.mapdismiss).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						window.dismiss();
					}
				});

		// popWindow消失监听方法
		window.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
				System.out.println("popWindow消失");
				window.dismiss();
			}
		});

	}

	/**
	 * String To Double
	 */
	public Double stringToDouble(String string) {
		Double doub = Double.parseDouble(string);
		return doub;
	}

	/**
	 * 将高德坐标转换为百度坐标
	 * 
	 * @param lat
	 * @param log
	 * @return
	 */

	public LatLng lngConverter(Double lat, Double log) {

		LatLng sourceLatLng = new LatLng(lat, log);
		converter = new CoordinateConverter(GaodeMapActivity.this);
		// CoordType.GPS 待转换坐标类型
		converter.from(CoordType.BAIDU);
		converter.coord(sourceLatLng);
		// 执行转换操作
		LatLng desLatLng = converter.convert();
		System.out.println(desLatLng.latitude + "    " + desLatLng.longitude);

		return desLatLng;
	}

	/**
	 * 将WGS84坐标转为高德坐标
	 * 
	 * @param lat
	 * @param log
	 * @return
	 */
	public LatLng latLngConverter(String lat, String log) {

		Double latitude = Double.parseDouble(lat);
		Double longitude = Double.parseDouble(log);
		// sourceLatLng待转换坐标点
		LatLng sourceLatLng = new LatLng(latitude, longitude);
		converter = new CoordinateConverter(GaodeMapActivity.this);
		// CoordType.GPS 待转换坐标类型
		converter.from(CoordType.GPS);
		converter.coord(sourceLatLng);
		// 执行转换操作
		LatLng desLatLng = converter.convert();
		System.out.println(desLatLng.latitude + "    " + desLatLng.longitude);
		return desLatLng;
	}

}