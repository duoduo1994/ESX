package com.example.administrator.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.list.ListViewAdapter;
import com.example.administrator.myapplication.R;
import com.example.administrator.utils.LoadingDialog;
import com.example.administrator.utils.LocalStorage;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyCookSchList extends Activity implements OnClickListener {

	private TextView tv;
	private Button userInfo_back;
	private ListView listView;
	private	List<Map<String, Object>> listItems;
	private ListViewAdapter listViewAdapter;
	private ImageView addsch, cai, myhalllist_back;
	private LoadingDialog dialog = null;
	public static Activity myCookSchList;

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
		LocalStorage.initContext(this);
		myCookSchList = MyCookSchList.this;
		userInfo_back = (Button) findViewById(R.id.btn_back);
		addsch = (ImageView) findViewById(R.id.addsch);
		addsch.setVisibility(View.VISIBLE);
		cai = (ImageView) findViewById(R.id.iv_more);
		cai.setVisibility(View.GONE);
		tv = (TextView) findViewById(R.id.tv_title);
		tv.setText("档期列表");

		myhalllist_back = (ImageView) findViewById(R.id.myhalllist_back);
		listView = (ListView) findViewById(R.id.list_sch);
		initData();

		userInfo_back.setOnClickListener(this);
		addsch.setOnClickListener(this);
	}

	/**
	 * 初始化信息
	 */

	private int ciShu = 0;
	private boolean isFalse = true;

	public void initData() {
		dialog = new LoadingDialog(MyCookSchList.this, "数据加载中，请稍候...");
		dialog.showDialog();
//		RequestParams params = new RequestParams();
//		params.add("Tel", LocalStorage.get("UserTel").toString());
//		SmartFruitsRestClient.post("CooksHandler.ashx?Action=getCooksDtToCld",
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
//
//						try {
//							JSONObject jsonObject = new JSONObject(result
//									.trim());
//							JSONArray orderArray = jsonObject
//									.getJSONArray("订单档期");
//							if(!orderArray.isNull(0)){
//								resolveData(orderArray);
//							}
//							else{
//								listView.setVisibility(View.GONE);
//								myhalllist_back.setVisibility(View.VISIBLE);
//							}
//
//						} catch (Exception e) {
//							// TODO: handle exception
//							Toast.makeText(getApplicationContext(), "数据解析错误！", Toast.LENGTH_SHORT).show();
//						}
//
//					}
//
//					@Override
//					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
//							Throwable arg3) {
//						// TODO Auto-generated method stub
//						dialog.closeDialog();
//						if (ciShu >= 3) {
//							isFalse = false;
//							Toast.makeText(MyCookSchList.this, "数据加载失败，请重新加载~",
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

	public void resolveData(JSONArray jsonArray) {
		listItems = new ArrayList<Map<String, Object>>();
		try {

			for (int i = 0; i < jsonArray.length(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				JSONObject listItem = jsonArray.getJSONObject(i);
				map.put("SchID", listItem.getString("SchID"));
				map.put("orderName", listItem.getString("ROrderName"));
				map.put("orderTel", listItem.getString("ROrderNameTel"));
				map.put("orderAddress", listItem.getString("HallName"));
				map.put("orderID", listItem.getString("OrderID"));

				SimpleDateFormat format = new SimpleDateFormat(
						"yyyy/MM/dd HH:mm:ss");
				SimpleDateFormat formatTo = new SimpleDateFormat("yyyy/MM/dd");
				if (formatTo.format(
						format.parse(listItem.getString("SolarDtFrom")))
						.equals(formatTo.format(format.parse(listItem
								.getString("SolarDtTo"))))) {
					map.put("orderTime", formatTo.format(format.parse(listItem
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
			listViewAdapter = new ListViewAdapter(MyCookSchList.this,
					listItems, 2); // 创建适配器
			listView.setAdapter(listViewAdapter);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.btn_back:
			if (System.currentTimeMillis() - HomeActivity.dianJiShiJian > 500) {

				HomeActivity.dianJiShiJian = System.currentTimeMillis();
				finish();
			}
			break;

		case R.id.addsch:
			Intent intent = new Intent(MyCookSchList.this,
					MyCooksActivity.class);
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
			if (System.currentTimeMillis() - HomeActivity.dianJiShiJian > 500) {
				HomeActivity.dianJiShiJian = System.currentTimeMillis();
				MyCookSchList.this.finish();

			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
