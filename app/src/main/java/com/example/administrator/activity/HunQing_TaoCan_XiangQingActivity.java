package com.example.administrator.activity;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.administrator.entity.AnLi;
import com.example.administrator.list.CommonAdapter;
import com.example.administrator.list.ViewHolder;
import com.example.administrator.myapplication.R;
import com.example.administrator.net.XUtilsHelper;
import com.example.administrator.utils.ActivityCollector;
import com.example.administrator.utils.IvListener;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.view.annotation.ViewInject;


import android.content.Context;
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

import rx.Observable;
import rx.Subscriber;

public class HunQing_TaoCan_XiangQingActivity extends BaseActivity {
	@ViewInject(R.id.tv_hunqing_title)
	private TextView tv ;
	@ViewInject(R.id.taocan_shiren_xq)
	private TextView xx;
	private String ID;
	@ViewInject(R.id.taocan_xiang_qing_lv)
	private ListView lv;
	@Override
	protected int setContentView() {
		return R.layout.activity_hun_qing__tao_can__xiang_qing;
	}
	@Override
	protected void initView() {


		ActivityCollector.addActivity(this);
		//

		lal = new ArrayList<AnLi>();

		ID = getIntent().getStringExtra("taocanID");


		findViewById(R.id.btn_hunqing_back).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						finishA();
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


	@ViewInject(R.id.iv_more)
	private ImageView iv_more;

	private void setMoreListener() {

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
		xx.setBackgroundResource(R.mipmap.lingjia_xiangmu);
		xx.setText(ID);
	}


	protected void toast(Context context , String info){
		Toast.makeText(context,info,Toast.LENGTH_SHORT).show();
	}

	private void getMsg() {
		XUtilsHelper xUtilsHelper1 = new XUtilsHelper(this,"weddingHandler.ashx?Action=GetSetMealDetails",1);
		RequestParams requestParams = new RequestParams();
		requestParams.addBodyParameter("WeddingItemID", ID);
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
					toast(HunQing_TaoCan_XiangQingActivity.this,
							getString(R.string.conn_failed ));

				}
				;
				if (isFalse) {
					getMsg();
					ciShu++;
				}


			}

			@Override
			public void onNext(String s) {
				try {
							JSONArray json = new JSONArray(s.trim());
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
		});

	}

	private boolean isFalse = true;
	private int ciShu = 0;




}
