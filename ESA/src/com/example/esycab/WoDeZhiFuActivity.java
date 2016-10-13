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
import com.example.esycab.utils.LocalStorage;
import com.example.listview.SwipeMenuListView;
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
import android.widget.TextView;
import android.widget.Toast;

public class WoDeZhiFuActivity extends Activity {
	private TextView tv;
	private SwipeMenuListView lv;

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
		setContentView(R.layout.wo_de_zhifu);
		ActivityCollector.addActivity(this);
		// if(!HomeActivity.quan){
		// rl = (RelativeLayout) findViewById(R.id.g_eng_d_uo);
		// android.widget.LinearLayout.LayoutParams l =
		// (android.widget.LinearLayout.LayoutParams) rl.getLayoutParams();
		// l.topMargin = (int) (HomeActivity.a12);
		// rl.setLayoutParams(l);
		// }
		tv = (TextView) findViewById(R.id.tv_title);
		tv.setText("我的支付");
//		mCache = ACache.get(this);
		LocalStorage.initContext(this);
		lv = (SwipeMenuListView) findViewById(R.id.wo_de_zhifu_lv);
		findViewById(R.id.btn_back).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (System.currentTimeMillis() - HomeActivity.dianJiShiJian > 500) {
					HomeActivity.dianJiShiJian = System.currentTimeMillis();
					WoDeZhiFuActivity.this.finish();
				}
			}
		});
		getMsg();
		setMoreListener();
	}

	private ImageView iv_more;

	private void setMoreListener() {
		iv_more = (ImageView) findViewById(R.id.iv_more);
		iv_more.setOnClickListener(new IvListener(iv_more,
				WoDeZhiFuActivity.this, 0));
	}

	private boolean isFalse = true;
	private int ciShu = 0;

	private void getMsg() {
		RequestParams p = new RequestParams();
		p.put("PayerTel", LocalStorage.get("UserTel").toString());
		SmartFruitsRestClient.get("PayHisHandler.ashx?Action=MyPayHis", p,
				new AsyncHttpResponseHandler() {

					@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
							Throwable arg3) {
						// TODO Auto-generated method stub
						if (ciShu >= 3) {
							isFalse = false;
							Toast.makeText(WoDeZhiFuActivity.this,
									getString(R.string.conn_failed) + arg3,
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
						System.out.println("返回的json数据：" + result);
						tryc(result);

					}

				});
	}

	private JSONArray js;
	private JSONObject tJson;
	private AnLi al;
	private List<AnLi> lal;
	private CommonAdapter<AnLi> caal;

	private void tryc(String result) {
		lal = new ArrayList<AnLi>();
		try {
			js = new JSONArray(result.trim());

			for (int i = 0; i < js.length(); i++) {
				tJson = js.getJSONObject(i);
				al = new AnLi();
				al.setName(tJson.getString("PayDt").replace(" ", "\n"));
				double qian = Double.parseDouble(tJson.getString("Amount"));
				al.setDetail(qian + "");
				al.setFeastAdr(tJson.getString("Name"));
				al.setImgUrl(tJson.getString("ImageUrl"));
				lal.add(al);
			}
			caal = new CommonAdapter<AnLi>(WoDeZhiFuActivity.this, lal,
					R.layout.item_wo_de_zhifu) {

				@Override
				public void convert(ViewHolder helper, AnLi item) {
					// TODO Auto-generated method stub
					helper.setText(R.id.wo_de_zhifu_shijian, item.getName());
					helper.setText(R.id.wo_de_zhifu_mingzi, item.getFeastAdr());
					helper.setText(R.id.wo_de_zhifu_jine,
							 item.getDetail()+"元");
				}
			};
			lv.setAdapter(caal);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

//	private ACache mCache;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (System.currentTimeMillis() - HomeActivity.dianJiShiJian > 500) {
				HomeActivity.dianJiShiJian = System.currentTimeMillis();
				WoDeZhiFuActivity.this.finish();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
