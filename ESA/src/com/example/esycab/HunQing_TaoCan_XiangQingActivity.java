package com.example.esycab;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.esycab.entity.AnLi;
import com.example.esycab.network.SmartFruitsRestClient;
import com.example.esycab.utils.ActivityCollector;
import com.example.esycab.utils.IvListener;
import com.eyoucab.list.CommonAdapter;
import com.eyoucab.list.ViewHolder;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.os.Bundle;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.app.Activity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class HunQing_TaoCan_XiangQingActivity extends Activity {
	private TextView tv, xx;
	private String ID;
	private ListView lv;

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
		setContentView(R.layout.activity_hun_qing__tao_can__xiang_qing);
		ActivityCollector.addActivity(this);
		//
		tv = (TextView) findViewById(R.id.tv_hunqing_title);
		lal = new ArrayList<AnLi>();

		ID = getIntent().getStringExtra("taocanID");
		xx = (TextView) findViewById(R.id.taocan_shiren_xq);
		lv = (ListView) findViewById(R.id.taocan_xiang_qing_lv);
		findViewById(R.id.btn_hunqing_back).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						if (System.currentTimeMillis()
								- HomeActivity.dianJiShiJian > 500) {

							HomeActivity.dianJiShiJian = System
									.currentTimeMillis();
							HunQing_TaoCan_XiangQingActivity.this.finish();
						}
					}
				});

		if (null == ID) {
			ID = getIntent().getStringExtra("taocanXX");
			tv.setText("另加项目表");
			chuLi();
		} else {
			tv.setText("套餐详情");
			getMsg();
		}

		setMoreListener();
	}

	private ImageView iv_more;

	private void setMoreListener() {
		iv_more = (ImageView) findViewById(R.id.iv_more);
		iv_more.setOnClickListener(new IvListener(iv_more,
				HunQing_TaoCan_XiangQingActivity.this, 0));
	}

	private AnLi anli;
	private List<AnLi> lal;
	private CommonAdapter<AnLi> caal;

	private void chuLi() {
		xx.setVisibility(View.VISIBLE);
		lv.setVisibility(View.GONE);
		System.out.println(ID);
		ID = ID.replace("@n", "\n\n");
		xx.setBackgroundResource(R.drawable.lingjia_xiangmu);
		xx.setText(ID);
	}

	private void getMsg() {
		RequestParams p = new RequestParams();
		p.put("WeddingItemID", ID);
		SmartFruitsRestClient.post(
				"weddingHandler.ashx?Action=GetSetMealDetails", p,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
						// TODO Auto-generated method stub
						String result = new String(arg2);
						System.out.println(result);
						try {
							JSONArray json = new JSONArray(result.trim());
							JSONObject tJson;
							if (null != json) {
								// System.out.println(configlist.length());
								for (int i = 0; i < json.length(); i++) {
									tJson = json.getJSONObject(i);
									anli = new AnLi();
									anli.setImgUrl(tJson.getString("Name"));
									String a = tJson.getString("Cont");
									if (a.contains("@n")) {
										a = a.replace("@n", "\n");
										System.out.println(a);
									} else {
										a = a + "\n";
									}
									anli.setDetail(a);
									lal.add(anli);
								}

							}
							caal = new CommonAdapter<AnLi>(
									HunQing_TaoCan_XiangQingActivity.this, lal,
									R.layout.item_taocan_xiangqing) {

								@Override
								public void convert(ViewHolder helper, AnLi item) {
									// TODO Auto-generated method stub
									helper.setText(R.id.taocan_xq_da,
											item.getImgUrl());
									helper.setText(R.id.taocan_xq_xiao,
											item.getDetail());
								}
							};
							lv.setAdapter(caal);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							System.out.println("G" + e.getMessage());
							// Toast.makeText(HunQing_TaoCan_XiangQingActivity.this,
							// e.getMessage(),
							// Toast.LENGTH_SHORT).show();
						}
					}

					@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
							Throwable arg3) {
						// TODO Auto-generated method stub
						System.out.println(arg3 + "asdfgdhgsa");
						if (ciShu >= 3) {
							isFalse = false;
							Toast.makeText(
									HunQing_TaoCan_XiangQingActivity.this,
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

	private boolean isFalse = true;
	private int ciShu = 0;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (System.currentTimeMillis() - HomeActivity.dianJiShiJian > 500) {

				HomeActivity.dianJiShiJian = System.currentTimeMillis();
				HunQing_TaoCan_XiangQingActivity.this.finish();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
