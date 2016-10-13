package com.example.esycab;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.esycab.entity.AnLi;
import com.example.esycab.network.SmartFruitsRestClient;
import com.example.esycab.utils.ACache;
import com.example.esycab.utils.ActivityCollector;
import com.example.esycab.utils.LocalStorage;
import com.eyoucab.list.CommonAdapter;
import com.eyoucab.list.ViewHolder;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class CanjuItenActivity extends FragmentActivity implements
		OnClickListener {
	private Button wedding_back;
	private TextView tv_title, tv_canju_price;
	private List<AnLi> list = new ArrayList<AnLi>();
	private ListView lv;
	private String starLevel;
	private CommonAdapter<AnLi> caxm;
	private ImageView star1, star2;

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
		setContentView(R.layout.activity_canju_item);
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
		LocalStorage.initContext(this);
		tv_canju_price = (TextView) findViewById(R.id.tv_canju_price);
		star1 = (ImageView) findViewById(R.id.iv_star_level4);
		star2 = (ImageView) findViewById(R.id.iv_star_level5);
		starLevel = getIntent().getStringExtra("sl");
		tv_canju_price.setText("40");
		if (starLevel.equals("4")) {
			tv_canju_price.setText("80");
			star1.setVisibility(View.VISIBLE);
		} else if (starLevel.equals("5")) {
			tv_canju_price.setText("120");
			star1.setVisibility(View.VISIBLE);
			star2.setVisibility(View.VISIBLE);
		}
		;
		lv = (ListView) findViewById(R.id.lv_canju_item);
		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_title.setText("餐具套餐");
		wedding_back = (Button) findViewById(R.id.btn_back);
		wedding_back.setOnClickListener(this);
		setAdapter();
		lv.setAdapter(caxm);
		if (null != mCache.getAsString("餐具详情")) {
			String result = mCache.getAsString("餐具详情");
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
		iv_more.setVisibility(View.GONE);
		// iv_more.setOnClickListener(new IvListener(iv_more,
		// CanjuItenActivity.this,0));
	}

	private void setAdapter() {
		// TODO Auto-generated method stub
		caxm = new CommonAdapter<AnLi>(CanjuItenActivity.this, list,
				R.layout.item_hunqinggongsi) {
			@Override
			public void convert(ViewHolder helper, AnLi item) {
				// TODO Auto-generated method stub
				helper.setText(R.id.hunqing_gongsi, item.getName());
				// helper.setTextColor(R.id.anliss, Color.BLACK,
				// Color.parseColor("#696969"), index);
				// helper.setBackG(R.id.item_ziti_bg, Color.WHITE,
				// Color.parseColor("#f5f5f5"), index);
				helper.setVis(R.id.beijing);
				helper.setVis(R.id.tiao_zhuan);
				helper.loadImage(R.id.shipei_tupian, item.getImgUrl());
			}
		};
	}
	private void getMsg() {
		RequestParams p = new RequestParams();
		SmartFruitsRestClient.get("WaresHandle.ashx?Action=GetDetail", p,
				new AsyncHttpResponseHandler() {

					@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
							Throwable arg3) {
						// TODO Auto-generated method stub
						System.out.println(arg3 + "!@U*JXIE*&DU");
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

	private ACache mCache;
	private JSONObject js;
	private JSONObject tJson = null;
	private AnLi al;

	private void tryc(String result) {
		try {
			js = new JSONObject(result.trim());
			JSONArray arry = js.getJSONArray("餐具详情");
			System.out.println(arry.length() + "!");
			if (arry.length() >= 1) {
				mCache.put("餐具套餐", result, 60 * 60 * 24);
			}
			for (int i = 0; i < arry.length(); i++) {
				tJson = arry.getJSONObject(i);
				al = new AnLi();
				al.setName(tJson.getString("Name"));
				al.setDetail(tJson.getString("StarLevel"));
				al.setFeastAdr(tJson.getString("Price"));
				al.setImgUrl(tJson.getString("ImageUrl"));
				if (al.getDetail().equals(starLevel)) {
					System.out.println(1234);
					list.add(al);
				}
				caxm.notifyDataSetChanged();
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (System.currentTimeMillis() - HomeActivity.dianJiShiJian > 500) {
				HomeActivity.dianJiShiJian = System.currentTimeMillis();
				CanjuItenActivity.this.finish();

			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.btn_back:
			if (System.currentTimeMillis() - HomeActivity.dianJiShiJian > 500) {
				HomeActivity.dianJiShiJian = System.currentTimeMillis();
				CanjuItenActivity.this.finish();
			}
			break;

		default:
			break;
		}
	}
}
