package com.example.esycab;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.esycab.network.SmartFruitsRestClient;
import com.example.esycab.utils.ACache;
import com.example.esycab.utils.ActivityCollector;
import com.example.esycab.utils.IvListener;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.os.Bundle;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.app.Activity;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class HunChe_TuiJianCheDuiActivity extends Activity {
	private TextView tv;
	
	private ListView hun_che_xilie;
	private MyAdapter adapter;
	private ACache mCache;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
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
		setContentView(R.layout.activity_hun_che__tui_jian_che_dui);
		ActivityCollector.addActivity(this);
		// RelativeLayout rl;
		// if(!HomeActivity.quan){
		// rl = (RelativeLayout) findViewById(R.id.g_eng_d_uo);
		// android.widget.LinearLayout.LayoutParams l =
		// (android.widget.LinearLayout.LayoutParams) rl.getLayoutParams();
		// l.topMargin = (int) (HomeActivity.a12);
		// rl.setLayoutParams(l);
		// }
		tv = (TextView) findViewById(R.id.tv_title);
		tv.setText("车队系列");
		mCache = ACache.get(this);
		hun_che_xilie = (ListView) findViewById(R.id.hun_che_xilie);
		adapter = new MyAdapter();

		if (null != mCache.getAsString("推荐车队")) {
			String result = mCache.getAsString("推荐车队");
			trcy(result);
		} else {
			System.out.println(147852);
			getCarList();
		}

		hun_che_xilie.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent = new Intent(HunChe_TuiJianCheDuiActivity.this,
						HunCheZhanShi.class);
				String img = imageUrl.get(arg2);
				intent.putExtra("img", img);
				intent.putExtra("carname", car.get(arg2));
				startActivity(intent);

			}

		});

		findViewById(R.id.btn_back).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (System.currentTimeMillis() - HomeActivity.dianJiShiJian > 500) {

					HomeActivity.dianJiShiJian = System.currentTimeMillis();
					HunChe_TuiJianCheDuiActivity.this.finish();
				}
			}
		});
		setMoreListener();
	}

	private ImageView iv_more;

	private void setMoreListener() {
		iv_more = (ImageView) findViewById(R.id.iv_more);
		iv_more.setOnClickListener(new IvListener(iv_more,
				HunChe_TuiJianCheDuiActivity.this, 0));
	}

	private boolean isFalse = true;
	private int ciShu = 0;

	private void getCarList() {

		RequestParams paramsa = new RequestParams();

		SmartFruitsRestClient.post("WedingCarHandler.ashx?Action=getCarList",
				paramsa, new AsyncHttpResponseHandler() {

					@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
							Throwable arg3) {

						if (ciShu >= 3) {
							isFalse = false;

						}
						;
						if (isFalse) {
							getCarList();
							ciShu++;
						}

					}

					@Override
					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
						String result = new String(arg2);

						if (result != null) {

							mCache.put("推荐车队", result, 60 * 60 * 24);

						}
						trcy(result);

					}

				});

	}
	private List<String> imageUrl;
	private List<String> car;
	private void trcy(String result) {
		imageUrl = new ArrayList<String>();
		car = new ArrayList<String>();
		JSONObject json = null;
		try {
			JSONArray cars = new JSONArray(result.trim());

			for (int i = 0; i < cars.length(); i++) {
				json = new JSONObject(cars.get(i).toString());

				String carName = json.getString("Name");
				String carUrl = json.getString("Url");

				car.add(carName);
				imageUrl.add(carUrl);
			}
			hun_che_xilie.setAdapter(adapter);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private class MyAdapter extends BaseAdapter {
		LayoutInflater ll;

		MyAdapter() {
			ll = LayoutInflater.from(HunChe_TuiJianCheDuiActivity.this);
		}

		@Override
		public int getCount() {

			return car.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		/*
		 * 重写view
		 */
		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			ViewHolder v = null;
			if (arg1 == null) {
				arg1 = ll.inflate(
						R.layout.activity_hun_che__tui_jian_che_dui_tem, null);
				v = new ViewHolder();

				v.txtName = (TextView) arg1.findViewById(R.id.car_name);
				arg1.setTag(v);

			} else {
				v = (ViewHolder) arg1.getTag();
			}

			v.txtName.setText(car.get(arg0));

			return arg1;
		}

	}

	private class ViewHolder {
		TextView txtName;

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (System.currentTimeMillis() - HomeActivity.dianJiShiJian > 500) {

				HomeActivity.dianJiShiJian = System.currentTimeMillis();
				finish();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
