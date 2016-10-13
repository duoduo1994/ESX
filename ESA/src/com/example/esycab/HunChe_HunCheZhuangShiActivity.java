package com.example.esycab;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.esycab.Fragment.FragmentHome;
import com.example.esycab.network.SmartFruitsRestClient;
import com.example.esycab.utils.ACache;
import com.example.esycab.utils.ActivityCollector;
import com.example.esycab.utils.IvListener;
import com.example.esycab.utils.Load;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.os.Bundle;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.app.Activity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class HunChe_HunCheZhuangShiActivity extends Activity {
	private TextView tv;
	private List<String> imageUrl = new ArrayList<String>();
	private List<String> imageUrl1 = new ArrayList<String>();
	private LinearLayout hun_che_zhuangshi, vxing;

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
		setContentView(R.layout.activity_hun_che__hun_che_zhuang_shi);
		ActivityCollector.addActivity(this);
		// RelativeLayout rl;
		// if(!HomeActivity.quan){
		// rl = (RelativeLayout) findViewById(R.id.g_eng_d_uo);
		// android.widget.LinearLayout.LayoutParams l =
		// (android.widget.LinearLayout.LayoutParams) rl.getLayoutParams();
		// l.topMargin = (int) (HomeActivity.a12);
		// rl.setLayoutParams(l);
		// }
		mCache = ACache.get(this);
		tv = (TextView) findViewById(R.id.tv_title);
		tv.setText("婚车装饰");
		Load.getLoad(this);
		hun_che_zhuangshi = (LinearLayout) findViewById(R.id.chedui_xingxin_zhanshi);
		vxing = (LinearLayout) findViewById(R.id.chedui_vxin_zhanshi);
		if (null != mCache.getAsString("婚车装饰")) {
			String result = mCache.getAsString("婚车装饰");
			System.out.println(result);
			trcy(result);
		} else {
			System.out.println(147852);
			getCarList();
		}

		findViewById(R.id.btn_back).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (System.currentTimeMillis() - HomeActivity.dianJiShiJian > 500) {

					HomeActivity.dianJiShiJian = System.currentTimeMillis();
					HunChe_HunCheZhuangShiActivity.this.finish();
				}
			}
		});
		setMoreListener();
	}

	private ImageView iv_more;

	private void setMoreListener() {
		iv_more = (ImageView) findViewById(R.id.iv_more);
		iv_more.setOnClickListener(new IvListener(iv_more,
				HunChe_HunCheZhuangShiActivity.this, 0));
	}

	private boolean isFalse = true;
	private int ciShu = 0;
	private ACache mCache;

	private void getCarList() {

		RequestParams paramsa = new RequestParams();

		SmartFruitsRestClient.post("WedingCarHandler.ashx?Action=getDecorate",
				paramsa, new AsyncHttpResponseHandler() {

					@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
							Throwable arg3) {
						// TODO Auto-generated method stub

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
						System.out.println(result);

						if (result != null) {

							mCache.put("婚车装饰", result, 60 * 60 * 24);

						}
						trcy(result);

					}

				});

	}


	private void trcy(String result) {
		JSONObject json = null;
		JSONObject json1 = null;
		String json2 = null;
		try {
			JSONArray cars = new JSONArray(result.trim());
			json = cars.getJSONObject(0);
			json1 = cars.getJSONObject(1);
			System.out.println("HHHH" + json.getString("Url") + "\n" + json1);
			json2 = (String) json.getJSONArray("Url").get(0);
			System.out.println(json2 + "+++++++++++");
			for (int i = 0; i < json.getJSONArray("Url").length(); i++) {
				imageUrl.add((String) json.getJSONArray("Url").get(i));
				System.out.println((String) json.getJSONArray("Url").get(i));
			}
			System.out.println(imageUrl.size() + "__________");
			for (int j = 0; j < json1.getJSONArray("Url").length(); j++) {
				imageUrl1.add((String) json1.getJSONArray("Url").get(j));

			}

			for (int i = 0; i < imageUrl.size(); i++) {
				ImageView imageView = new ImageView(
						HunChe_HunCheZhuangShiActivity.this);
				Load.imageLoader.displayImage((imageUrl.get(i)), imageView,
						Load.options);

				LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
						new ViewGroup.LayoutParams(FragmentHome.width,
								FragmentHome.height / 4));
				layoutParams.topMargin = 20;
				hun_che_zhuangshi.addView(imageView, layoutParams);
			}
			for (int i = 0; i < imageUrl1.size(); i++) {
				ImageView imageView = new ImageView(
						HunChe_HunCheZhuangShiActivity.this);
				Load.imageLoader.displayImage((imageUrl1.get(i)), imageView,
						Load.options);

				LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
						new ViewGroup.LayoutParams(FragmentHome.width,
								FragmentHome.height / 4));
				layoutParams.topMargin = 20;
				vxing.addView(imageView, layoutParams);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}

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
