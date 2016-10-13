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
import com.example.esycab.utils.Diolg;
import com.example.esycab.utils.IvListener;
import com.eyoucab.list.CommonAdapter;
import com.eyoucab.list.ViewHolder;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ShiRen_DingZhiActivity extends Activity {
	private LinearLayout yuYue;
	private TextView t1, t2, biaoTi, t, meiyong,kp;
	private Button b1;
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
		setContentView(R.layout.activity_hun_qing__tao_can_xiang_qing);
		ActivityCollector.addActivity(this);
		mCache = ACache.get(this);
		yuYue = (LinearLayout) findViewById(R.id.taocan_xiangqing);
		biaoTi = (TextView) findViewById(R.id.tv_hunqing_title);
		biaoTi.setText("私人定制");
		t = (TextView) findViewById(R.id.hunqing_taocan_xiangq);
		kp = (TextView) findViewById(R.id.kepianzhanshi);
		kp.setText("客片展示");
		t.setText("项目价格表");
		t1 = (TextView) findViewById(R.id.tongyong_jiage);
		t1.setText("订制价格");
		meiyong = (TextView) findViewById(R.id.woyaoyuyue_meiyong);
		meiyong.setVisibility(View.GONE);
		t1.setVisibility(View.GONE);
		t2 = (TextView) findViewById(R.id.unit_price);
		t2.setText("");
		t2.setVisibility(View.GONE);
		b1 = (Button) findViewById(R.id.jiarudingdan);
		b1.setText("我要预定");
		b1.setBackgroundColor(Color.parseColor("#e27386"));
		b1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				new Diolg(ShiRen_DingZhiActivity.this, "立即拨打", "返回",
						"联系电话18058516999", "提示", 7);
			}
		});
		lv = (ListView) findViewById(R.id.hunqing_taocanxiangqing_tup);
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
							ShiRen_DingZhiActivity.this.finish();
						}
					}
				});
		if (null != mCache.getAsString("私人定制")) {
			String result = mCache.getAsString("私人定制");
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
				ShiRen_DingZhiActivity.this, 0));
	}

	private boolean isFalse = true;
	private int ciShu = 0;

	private void getMsg() {
		RequestParams reqParams = new RequestParams();
		SmartFruitsRestClient.post("weddingHandler.ashx?Action=PersonalTailor",
				reqParams, new AsyncHttpResponseHandler() {

					@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
							Throwable arg3) {
						// TODO Auto-generated method stub
						System.out.println(arg3 + "asdfgdhgsa");
						if (ciShu >= 3) {
							isFalse = false;
							Toast.makeText(ShiRen_DingZhiActivity.this,
									getString(R.string.conn_failed),
									Toast.LENGTH_SHORT).show();
						}
						;
						if (isFalse) {
							getMsg();
							ciShu++;
						}
					}

					@Override
					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
						// TODO Auto-generated method stub
						String result = new String(arg2);
						System.out.println("!#*@()DJ#()J" + result);

						tryc(result);

					}
				});
	}

	private List<CaiDan> lcd;
	private CaiDan cd;
	private List<String> lsu;

	private void tryc(String result) {
		lcd = new ArrayList<CaiDan>();
		try {
			JSONArray json = new JSONArray(result.trim());
			for (int i = 0; i < json.length(); i++) {
				JSONObject t = json.getJSONObject(i);
				mCache.put("私人定制", result, 60 * 60 * 24);
				cd = new CaiDan();
				cd.setName(t.getString("name"));
				cd.setMaterial(t.getString("Cont"));
				JSONArray tuPian = t.getJSONArray("Img");
				lsu = new ArrayList<String>();
				for (int j = 0; j < tuPian.length(); j++) {
					lsu.add((String) tuPian.get(j));
				}
				cd.setLs(lsu);
				lcd.add(cd);
			}
			cas = new CommonAdapter<String>(ShiRen_DingZhiActivity.this, lsu,
					R.layout.item_hunq_zuop) {

				@Override
				public void convert(ViewHolder helper, String item) {
					// TODO Auto-generated method stub
					helper.loadImage(R.id.hunqing_zuop, item);
				}
			};
			lv.setAdapter(cas);
			yuYue.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Intent i = new Intent(ShiRen_DingZhiActivity.this,
							HunQing_TaoCan_XiangQingActivity.class);
					i.putExtra("taocanXX", lcd.get(0).getMaterial());
					startActivity(i);
				}
			});
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
	}

	private CommonAdapter<String> cas;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (System.currentTimeMillis() - HomeActivity.dianJiShiJian > 500) {
				HomeActivity.dianJiShiJian = System.currentTimeMillis();
				// 结束当前界面
				ShiRen_DingZhiActivity.this.finish();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
