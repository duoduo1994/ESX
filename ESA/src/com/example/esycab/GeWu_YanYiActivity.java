package com.example.esycab;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.esycab.entity.CaiDan;
import com.example.esycab.network.SmartFruitsRestClient;
import com.example.esycab.utils.ACache;
import com.example.esycab.utils.ActivityCollector;
import com.example.esycab.utils.IvListener;
import com.eyoucab.list.CommonAdapter;
import com.eyoucab.list.ViewHolder;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class GeWu_YanYiActivity extends Activity {
	private TextView tv;
	private ListView lv;
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
		setContentView(R.layout.activity_qing_dian__li_yi);
		ActivityCollector.addActivity(this);
		mCache = ACache.get(this);
		// RelativeLayout rl;
		// if(!HomeActivity.quan){
		// rl = (RelativeLayout) findViewById(R.id.g_eng_d_uo);
		// android.widget.LinearLayout.LayoutParams l =
		// (android.widget.LinearLayout.LayoutParams) rl.getLayoutParams();
		// l.topMargin = (int) (HomeActivity.a12);
		// rl.setLayoutParams(l);
		// }
		tv = (TextView) findViewById(R.id.tv_hunqing_title);
		tv.setText("歌舞演艺");

		lv = (ListView) findViewById(R.id.qing_dian_li_yi_liebiao);
		findViewById(R.id.btn_hunqing_back).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						if (System.currentTimeMillis()
								- HomeActivity.dianJiShiJian > 500) {

							HomeActivity.dianJiShiJian = System
									.currentTimeMillis();
							// 结束当前界面
							GeWu_YanYiActivity.this.finish();
						}
					}
				});
		lal = new ArrayList<CaiDan>();
		if (null != mCache.getAsString("歌舞演艺")) {
			String result = mCache.getAsString("歌舞演艺");
			tryc(result);
		} else {
			System.out.println(147852);
			getMsg();
		}
		setMoreListener();
	}

	private ImageView iv_more;

	private void setMoreListener() {
		iv_more = (ImageView) findViewById(R.id.iv_more);
		iv_more.setOnClickListener(new IvListener(iv_more,
				GeWu_YanYiActivity.this, 0));
	}

	private CaiDan anli;
	private CommonAdapter<CaiDan> caal;
	private List<CaiDan> lal;
	private List<String> ls;

	private void getMsg() {
		RequestParams p = new RequestParams();
		SmartFruitsRestClient.post("weddingHandler.ashx?Action=SongAndDance",
				p, new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
						// TODO Auto-generated method stub
						String result = new String(arg2);
						System.out.println(result);
						tryc(result);
					}

					@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
							Throwable arg3) {
						// TODO Auto-generated method stub
						System.out.println(arg3 + "asdfgdhgsa");
						if (ciShu >= 3) {
							isFalse = false;
							Toast.makeText(GeWu_YanYiActivity.this,
									getString(R.string.conn_failed),
									Toast.LENGTH_SHORT).show();
						}
						;

						if (isFalse) {
							getMsg();
							ciShu++;
						}

					}
				});
	}

	private void tryc(String result) {
		try {
			JSONArray json = new JSONArray(result.trim());
			JSONObject tJson;
			if (null != json) {
				// System.out.println(configlist.length());
				if (json.length() >= 1) {
					mCache.put("歌舞演艺", result, 60 * 60 * 24);
				}
				for (int i = 0; i < json.length(); i++) {
					tJson = json.getJSONObject(i);

					anli = new CaiDan();
					ls = new ArrayList<String>();
					anli.setIsPass("WeddingItemID");
					anli.setImageUrl(tJson.getString("Icon"));
					anli.setName(tJson.getString("Name"));
					anli.setPlace(tJson.getString("Price"));
					for (int j = 0; j < tJson.getJSONArray("ImageUrl").length(); j++) {
						ls.add((String) tJson.getJSONArray("ImageUrl").get(j));
					}
					anli.setLs(ls);
					lal.add(anli);
				}

			}
			caal = new CommonAdapter<CaiDan>(GeWu_YanYiActivity.this, lal,
					R.layout.item_hunqinggongsi) {

				@Override
				public void convert(ViewHolder helper, CaiDan item) {
					// TODO Auto-generated method stub
					helper.setVis(R.id.beijing);
					helper.setText(R.id.hunqing_gongsi, item.getName());
					helper.loadImage(R.id.shipei_tupian, item.getImageUrl());
				}
			};
			lv.setAdapter(caal);
			lv.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					Intent mIntent = new Intent(GeWu_YanYiActivity.this,
							HunQing_TaoCanXiangQingActivity.class);
					Bundle mBundle = new Bundle();
					mBundle.putSerializable("qingdianliyixq", lal.get(arg2));
					mIntent.putExtras(mBundle);

					startActivity(mIntent);
				}
			});
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			System.out.println("G" + e.getMessage());
			// Toast.makeText(GeWu_YanYiActivity.this,
			// e.getMessage(),
			// Toast.LENGTH_SHORT).show();
		}
	}

	private boolean isFalse = true;
	private int ciShu = 0;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (System.currentTimeMillis() - HomeActivity.dianJiShiJian > 500) {

				HomeActivity.dianJiShiJian = System.currentTimeMillis();
				// 结束当前界面
				GeWu_YanYiActivity.this.finish();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
