package com.example.administrator.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.list.ListViewAdapter;
import com.example.administrator.myapplication.R;
import com.example.administrator.net.XUtilsHelper;
import com.example.administrator.utils.LoadingDialog;
import com.example.administrator.utils.LocalStorage;
import com.lidroid.xutils.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;

public class MyHallSchList extends Activity implements OnClickListener {

	private TextView tv;
	private Button userInfo_back;
	private ListView listView;
	private List<Map<String, Object>> listItems;
	private ListViewAdapter listViewAdapter;
	private ImageView addsch, cai,myhalllist_back;
	private LoadingDialog dialog = null;
	public static Activity myHallSchList;

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
		setContentView(R.layout.myhallschlist);
		RelativeLayout rl;
		LocalStorage.initContext(this);
		myHallSchList = MyHallSchList.this;
		userInfo_back = (Button) findViewById(R.id.btn_back);
		addsch = (ImageView) findViewById(R.id.addsch);
		addsch.setVisibility(View.VISIBLE);
		cai = (ImageView) findViewById(R.id.iv_more);
		cai.setVisibility(View.GONE);
		tv = (TextView) findViewById(R.id.tv_title);
		tv.setText("档期列表");
		
		myhalllist_back=(ImageView) findViewById(R.id.myhalllist_back);
		listView = (ListView) findViewById(R.id.list_sch);

		initData();

		userInfo_back.setOnClickListener(this);
		addsch.setOnClickListener(this);
		}

	/**
	 * 初始化商品信息
	 */

	private int ciShu = 0;
	private boolean isFalse = true;

	public void initData() {
		dialog = new LoadingDialog(MyHallSchList.this, "数据加载中，请稍候...");
		dialog.showDialog();
		XUtilsHelper xUtilsHelper1 = new XUtilsHelper(this,"HallsHandler.ashx?Action=getHallDtToTel",1);
		RequestParams requestParams = new RequestParams();
		requestParams.addBodyParameter("UserTel", LocalStorage.get("UserTel").toString());
		Observable.create(new Observable.OnSubscribe<String>() {
			@Override
			public void call(Subscriber<? super String> subscriber) {
				xUtilsHelper1.sendPost(requestParams,subscriber);
			}
		}).subscribe(new Subscriber<String>() {
			@Override
			public void onCompleted() {

			}

			@Override
			public void onError(Throwable e) {
				dialog.closeDialog();
						if (ciShu >= 3) {
							isFalse = false;
							Toast.makeText(MyHallSchList.this, "数据加载失败，请重新加载~",
									Toast.LENGTH_SHORT).show();
						}

						if (isFalse) {
							initData();
							ciShu++;
						}
			}

			@Override
			public void onNext(String s) {
				dialog.closeDialog();
				ciShu = 0;
				isFalse = true;
				String result = new String(s);
				System.out.println(result);
						if (!TextUtils.isEmpty(result.trim())) {
							try {
								JSONObject jsonObject = new JSONObject(result
										.trim());
								JSONArray jsonArray = jsonObject.getJSONArray("占用情况");
								if(!jsonArray.isNull(0)){
									List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();
									for (int i = 0; i < jsonArray.length(); i++) {
										Map<String, Object> map = new HashMap<String, Object>();
										JSONObject listItem = jsonArray
												.getJSONObject(i);
										map.put("SchID",
												listItem.getString("SchID"));
										map.put("orderName",
												listItem.getString("UserName"));
										map.put("orderTel",
												listItem.getString("Tel"));
										map.put("orderAddress",
												listItem.getString("address"));
										map.put("dinner",
												listItem.getString("Dinner"));
										map.put("vice", listItem.getString("Vice"));

										SimpleDateFormat format = new SimpleDateFormat(
												"yyyy/MM/dd HH:mm:ss");
										SimpleDateFormat formatTo = new SimpleDateFormat(
												"yyyy/MM/dd");
										if (formatTo
												.format(format.parse(listItem
														.getString("SolarDtFrom")))
												.equals(formatTo.format(format.parse(listItem
														.getString("SolarDtTo"))))) {
											map.put("orderTime",
													formatTo.format(format.parse(listItem
															.getString("SolarDtFrom"))));
										} else {
											map.put("orderTime",
													formatTo.format(format.parse(listItem
															.getString("SolarDtFrom")))
															+ "——"
															+ formatTo.format(format.parse(listItem
																	.getString("SolarDtTo"))));
										}

										listItems.add(map);
									}
									listViewAdapter = new ListViewAdapter(
											MyHallSchList.this, listItems,1); // 创建适配器
									listView.setAdapter(listViewAdapter);
								}
								else{
									listView.setVisibility(View.GONE);
									myhalllist_back.setVisibility(View.VISIBLE);
								}

							} catch (Exception e) {
								// TODO: handle exception
								Toast.makeText(getApplicationContext(), "数据解析错误！", Toast.LENGTH_SHORT).show();
							}
			}

			}
		});

//		RequestParams params = new RequestParams();
//		params.add("UserTel", LocalStorage.get("UserTel").toString());
//		SmartFruitsRestClient.post("HallsHandler.ashx?Action=getHallDtToTel",
//				params, new AsyncHttpResponseHandler() {
//
//					@Override
//					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
//						// TODO Auto-generated method stub
//						dialog.closeDialog();
//						ciShu = 0;
//						isFalse = true;
//						String result = new String(arg2);
//						System.out.println(result);
//						if (!TextUtils.isEmpty(result.trim())) {
//							try {
//								JSONObject jsonObject = new JSONObject(result
//										.trim());
//								JSONArray jsonArray = jsonObject.getJSONArray("占用情况");
//								if(!jsonArray.isNull(0)){
//									List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();
//									for (int i = 0; i < jsonArray.length(); i++) {
//										Map<String, Object> map = new HashMap<String, Object>();
//										JSONObject listItem = jsonArray
//												.getJSONObject(i);
//										map.put("SchID",
//												listItem.getString("SchID"));
//										map.put("orderName",
//												listItem.getString("UserName"));
//										map.put("orderTel",
//												listItem.getString("Tel"));
//										map.put("orderAddress",
//												listItem.getString("address"));
//										map.put("dinner",
//												listItem.getString("Dinner"));
//										map.put("vice", listItem.getString("Vice"));
//
//										SimpleDateFormat format = new SimpleDateFormat(
//												"yyyy/MM/dd HH:mm:ss");
//										SimpleDateFormat formatTo = new SimpleDateFormat(
//												"yyyy/MM/dd");
//										if (formatTo
//												.format(format.parse(listItem
//														.getString("SolarDtFrom")))
//												.equals(formatTo.format(format.parse(listItem
//														.getString("SolarDtTo"))))) {
//											map.put("orderTime",
//													formatTo.format(format.parse(listItem
//															.getString("SolarDtFrom"))));
//										} else {
//											map.put("orderTime",
//													formatTo.format(format.parse(listItem
//															.getString("SolarDtFrom")))
//															+ "——"
//															+ formatTo.format(format.parse(listItem
//																	.getString("SolarDtTo"))));
//										}
//
//										listItems.add(map);
//									}
//									listViewAdapter = new ListViewAdapter(
//											MyHallSchList.this, listItems,1); // 创建适配器
//									listView.setAdapter(listViewAdapter);
//								}
//								else{
//									listView.setVisibility(View.GONE);
//									myhalllist_back.setVisibility(View.VISIBLE);
//								}
//
//							} catch (Exception e) {
//								// TODO: handle exception
//								Toast.makeText(getApplicationContext(), "数据解析错误！", Toast.LENGTH_SHORT).show();
//							}
//						}
//					}
//
//					@Override
//					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
//							Throwable arg3) {
//						// TODO Auto-generated method stub
//						dialog.closeDialog();
//						if (ciShu >= 3) {
//							isFalse = false;
//							Toast.makeText(MyHallSchList.this, "数据加载失败，请重新加载~",
//									Toast.LENGTH_SHORT).show();
//						}
//
//						if (isFalse) {
//							initData();
//							ciShu++;
//						}
//					}
//				});
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		
		case R.id.btn_back:
			if(System.currentTimeMillis()- HomeActivity.dianJiShiJian>500){
				HomeActivity.dianJiShiJian = System.currentTimeMillis();
				finish();
			}
			
			break;

		case R.id.addsch:
			Intent intent = new Intent(MyHallSchList.this,
					MyHallsActivity.class);
			startActivity(intent);
			finish();
			break;
		default:
			break;
		}
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if(System.currentTimeMillis()- HomeActivity.dianJiShiJian>500){
				HomeActivity.dianJiShiJian = System.currentTimeMillis();
				finish();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
