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
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

public class TaoCan_LieBiaoActivity extends Activity {

	private ListView lieBiao;
	private ACache mCache;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON);
            //透明导航栏
          //  getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
		}else{
			this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
			this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏
		}
		setContentView(R.layout.activity_tao_can__lie_biao);
		ActivityCollector.addActivity(this);
//		if(!HomeActivity.quan){
//			rl = (RelativeLayout) findViewById(R.id.g_eng_d_uo);
//			android.widget.LinearLayout.LayoutParams l = (android.widget.LinearLayout.LayoutParams) rl.getLayoutParams();
//			l.topMargin = (int) (HomeActivity.a12);
//			rl.setLayoutParams(l);
//		}
		mCache = ACache.get(this);
		findViewById(R.id.taocan_liebiao_work_back).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						if(System.currentTimeMillis()-HomeActivity.dianJiShiJian>500){
							HomeActivity.dianJiShiJian = System.currentTimeMillis();
						// 结束当前界面
						TaoCan_LieBiaoActivity.this.finish();
						}
					}
				});
		lieBiao = (ListView) findViewById(R.id.hunqing_taocan_liebiao);
		lieBiao.setSelector(new ColorDrawable(Color.parseColor("#ffffff")));
		if (null != mCache.getAsString("婚庆套餐列表")) {
			String result = mCache.getAsString("婚庆套餐列表");
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
		iv_more.setOnClickListener(new IvListener(iv_more, TaoCan_LieBiaoActivity.this,0));
	}

	private void getMsg() {
		RequestParams p = new RequestParams();
		SmartFruitsRestClient.get("weddingHandler.ashx?Action=GetSetMealList",
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
							Toast.makeText(TaoCan_LieBiaoActivity.this,
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
		lcd = new ArrayList<CaiDan>();
		try {
			JSONArray json = new JSONArray(result.trim());
			System.out.println(json.length());
			if (json.length() >= 1) {
				mCache.put("婚庆套餐列表", result, 60 * 60 * 24);
			}
		//	System.out.println(json.getJSONObject(4));
		//	System.out.println(json.getJSONObject(3));
			for (int i = 0; i < json.length(); i++) {
				tJson = json.getJSONObject(i);
				System.out.println(i+"_____________"+tJson);
				cd = new CaiDan();
				cd.setName(tJson.getString("Name"));
				cd.setIsPass(tJson.getString("WeddingItemID"));
				cd.setPlace(tJson.getString("Price"));
				cd.setImageUrl(tJson.getString("Icon"));
				ls = new ArrayList<String>();
				for (int j = 0; j < tJson.getJSONArray("ImageUrl").length(); j++) {
					System.out.println(tJson.getJSONArray("ImageUrl").get(j));
					ls.add((String) tJson.getJSONArray("ImageUrl").get(j));
				}
				cd.setLs(ls);
				lcd.add(cd);
			}
			cacd = new CommonAdapter<CaiDan>(TaoCan_LieBiaoActivity.this, lcd,
					R.layout.item_taocan) {

				@Override
				public void convert(ViewHolder helper, CaiDan item) {
					// TODO Auto-generated method stub
					helper.setText(R.id.hunqing_taocan_item_mingzi,
							item.getName());
					helper.setText(R.id.hunqing_taocan_item_jiage,
							"￥"+item.getPlace());
					 helper.loadImage(R.id.hunqing_taocan_item_tupian,
					 item.getImageUrl());

				}
			};
			lieBiao.setAdapter(cacd);
			lieBiao.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					   Intent mIntent = new Intent(TaoCan_LieBiaoActivity.this,HunQing_TaoCanXiangQingActivity.class);  
				        Bundle mBundle = new Bundle();  
				        mBundle.putSerializable("taocanxiangq", lcd.get(arg2));  
				        mIntent.putExtras(mBundle);  
				        startActivity(mIntent);  
				}
			});
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			System.out.println("H" + e.getMessage());
		}
	}

	private JSONObject tJson;
	private List<CaiDan> lcd;
	private CaiDan cd;
	private List<String> ls;
	private CommonAdapter<CaiDan> cacd;
	private boolean isFalse = true;
	private int ciShu = 0;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if(System.currentTimeMillis()-HomeActivity.dianJiShiJian>500){
				HomeActivity.dianJiShiJian = System.currentTimeMillis();
			// 结束当前界面
			TaoCan_LieBiaoActivity.this.finish();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
