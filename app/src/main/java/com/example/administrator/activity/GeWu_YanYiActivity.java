package com.example.administrator.activity;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.administrator.entity.AnLi;
import com.example.administrator.entity.CaiDan;
import com.example.administrator.list.CommonAdapter;
import com.example.administrator.list.ViewHolder;
import com.example.administrator.myapplication.R;
import com.example.administrator.net.XUtilsHelper;
import com.example.administrator.utils.ACache;
import com.example.administrator.utils.ActivityCollector;
import com.example.administrator.utils.IvListener;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.view.annotation.ViewInject;


import android.app.Activity;
import android.content.Context;
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

import rx.Observable;
import rx.Subscriber;

public class GeWu_YanYiActivity extends BaseActivity {
	@ViewInject(R.id.tv_hunqing_title)
	private TextView tv;
	@ViewInject(R.id.qing_dian_li_yi_liebiao)
	private ListView lv;
	private ACache mCache;
	@Override
	protected int setContentView() {
		return R.layout.activity_qing_dian__li_yi;
	}
	@Override
	protected void initView() {


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

		tv.setText("歌舞演艺");


		findViewById(R.id.btn_hunqing_back).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						finishA();
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


	@ViewInject(R.id.iv_more)
	private ImageView iv_more;

	private void setMoreListener() {

		iv_more.setOnClickListener(new IvListener(iv_more,
				GeWu_YanYiActivity.this, 0));
	}
	protected void toast(Context context , String info){
		Toast.makeText(context,info,Toast.LENGTH_SHORT).show();
	}

	private CaiDan anli;
	private CommonAdapter<CaiDan> caal;
	private List<CaiDan> lal;
	private List<String> ls;

	private void getMsg() {

		XUtilsHelper xUtilsHelper1 = new XUtilsHelper(this,"weddingHandler.ashx?Action=SongAndDance");
		RequestParams requestParams = new RequestParams();

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

				if (ciShu >= 3) {
					isFalse = false;
					toast(GeWu_YanYiActivity.this,
							getString(R.string.conn_failed));

				}
				;
				if (isFalse) {
					getMsg();
					ciShu++;
				}


			}

			@Override
			public void onNext(String s) {
				tryc(s);

			}
		});











//
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

				@Override
				public void convert(ViewHolder helper, AnLi item) {

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




}
