package com.example.administrator.activity;

import java.util.ArrayList;
import java.util.List;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.provider.Settings;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.entity.AnLi;
import com.example.administrator.entity.CaiDan;
import com.example.administrator.list.CommonAdapter;
import com.example.administrator.list.ViewHolder;
import com.example.administrator.myapplication.R;
import com.example.administrator.net.XUtilsHelper;
import com.example.administrator.utils.ACache;
import com.example.administrator.utils.ActivityCollector;
import com.example.administrator.utils.IvListener;
import com.example.administrator.utils.Load;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.view.annotation.ViewInject;

import rx.Observable;
import rx.Subscriber;

public class CaseActivity extends BaseActivity implements OnItemClickListener {
	@ViewInject(R.id.btn_back)
	private Button iv;
	@ViewInject(R.id.anli)
	private ListView lv;
	@ViewInject(R.id.anli_tupian)
	private ListView l1;
	// TextView miaos,jiage;
	@ViewInject(R.id.tv_title)
	private TextView tv_title;

	@Override
	protected void initView() {
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
		Load.getLoad(this);
		tv_title.setText("实例");
		// miaos = (TextView) findViewById(R.id.anli_miaoshu);
		// jiage = (TextView) findViewById(R.id.shili_jiage);

		iv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (System.currentTimeMillis() - HomeActivity.dianJiShiJian > 500) {
					HomeActivity.dianJiShiJian = System.currentTimeMillis();
					CaseActivity.this.finish();
					ActivityCollector.finishAll();
				}
				// 结束当前界面

			}
		});
		if (null != mCache.getAsString("案例")) {
			String result = mCache.getAsString("案例");
			tryc(result);
		} else {
			System.out.println(147852);
			initDatas();
		}
		setMoreListener();
	}
	@Override
	protected int setContentView() {
		return R.layout.activity_cases;
	}
@ViewInject(R.id.iv_more)
	private ImageView iv_more;
	private void setMoreListener() {
		iv_more.setOnClickListener(new IvListener(iv_more, CaseActivity.this, 0));
	}

	private List<String> ls;
	private List<String> ls1 = new ArrayList<String>();
	private CommonAdapter<CaiDan> cas;
	private CommonAdapter<String> cast;
	private CaiDan cd;
	private List<CaiDan> lcj;
	private JSONObject tJson = null;

	private void tryc(String result) {
		lcj = new ArrayList<CaiDan>();
		try {
			JSONArray json = new JSONArray(result.trim());
			if (null != json) {
				// System.out.println(configlist.length());
				mCache.put("案例", result, 60 * 60 * 24);
				for (int i = 0; i < json.length(); i++) {
					tJson = json.getJSONObject(i);
					cd = new CaiDan();
					ls = new ArrayList<String>();
					cd.setName(tJson.getString("Name"));
					cd.setPlace(tJson.getString("Price"));
					for (int j = 0; j < tJson.getJSONArray("ImageUrl").length(); j++) {
						ls.add((String) tJson.getJSONArray("ImageUrl").get(j));
					}

					cd.setMaterial(tJson.getString("Remark"));
					cd.setLs(ls);
					lcj.add(cd);
				}

			}
			System.out.println(lcj.size());
			for (int i = 0; i < lcj.get(0).getLs().size(); i++) {
				ls1.add(lcj.get(0).getLs().get(i));
				System.out.println(lcj.get(0).getLs().get(i)+",");
			}

			// miaos.setText(cd.getMaterial());
			// jiage.setText("总价为"+cd.getPlace()+"元");
			cas = new CommonAdapter<CaiDan>(CaseActivity.this, lcj,
					R.layout.item_zitis) {

				@Override
				public void convert(ViewHolder helper, CaiDan item) {
					// TODO Auto-generated method stub
					helper.setText(R.id.anliss, item.getName());
					helper.setTextColor(R.id.anliss, Color.BLACK,
							Color.parseColor("#696969"), index);
					helper.setBackG(R.id.item_ziti_bg, Color.WHITE,
							Color.parseColor("#eeeeee"), index);
				}

				@Override
				public void convert(ViewHolder helper, AnLi item) {

				}
			};
			lv.setAdapter(cas);
			cast = new CommonAdapter<String>(CaseActivity.this, ls1,
					R.layout.item_zuopins) {

				@Override
				public void convert(ViewHolder helper, String item) {
					// TODO Auto-generated method stub
					helper.loadImage(R.id.zuopin123, item);
				}

				@Override
				public void convert(ViewHolder helper, AnLi item) {

				}
			};
			l1.setAdapter(cast);
			lv.setOnItemClickListener(this);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			System.out.println("G" + e.getMessage());
			// Toast.makeText(CaseActivity.this,
			// e.getMessage(),
			// Toast.LENGTH_SHORT).show();
		}
	}

	private ACache mCache;

	private void initDatas() {

		XUtilsHelper xUtilsHelper1 = new XUtilsHelper(this,"weddingHandler.ashx?Action=GetCase",1);
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
				initDatas();
				if (ciShu >= 3) {
							isFalse = false;
					toast(CaseActivity.this,getString(R.string.conn_failed) + e);
						}
						;

						if (isFalse) {
							initDatas();
							ciShu++;
						}
			}

			@Override
			public void onNext(String s) {
						tryc(s);

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
				CaseActivity.this.finish();
				ActivityCollector.finishAll();
			}
		}
		return super.onKeyDown(keyCode, event);
	}


	private int index = 0;

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		index = arg2;
		ls1.clear();
		cas.notifyDataSetChanged();

		for (int i = 0; i < lcj.get(arg2).getLs().size(); i++) {
			ls1.add(lcj.get(arg2).getLs().get(i));
		}
		// miaos.setText(lcj.get(arg2).getMaterial());
		// jiage.setText("总价为"+lcj.get(arg2).getPlace()+"元");
		cast.notifyDataSetChanged();
	}
}
