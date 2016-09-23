package com.example.administrator.activity;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.administrator.fragment.FragmentHome;
import com.example.administrator.myapplication.R;
import com.example.administrator.net.XUtilsHelper;
import com.example.administrator.utils.ACache;
import com.example.administrator.utils.ActivityCollector;
import com.example.administrator.utils.IvListener;
import com.example.administrator.utils.Load;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.content.Context;
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
import android.widget.Toast;

import rx.Observable;
import rx.Subscriber;

public class HunChe_HunCheZhuangShiActivity extends BaseActivity {
	@ViewInject(R.id.tv_title)
	private TextView tv;
	private List<String> imageUrl = new ArrayList<String>();
	private List<String> imageUrl1 = new ArrayList<String>();
	@ViewInject(R.id.chedui_xingxin_zhanshi)
	private LinearLayout hun_che_zhuangshi;
	@ViewInject(R.id.chedui_vxin_zhanshi)
	private LinearLayout vxing;
	@Override
	protected int setContentView() {
		return R.layout.activity_hun_che__hun_che_zhuang_shi;
	}
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

		tv.setText("婚车装饰");
		Load.getLoad(this);


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
				finishA();
			}
		});
		setMoreListener();
	}


	@ViewInject(R.id.iv_more)
	private ImageView iv_more;

	private void setMoreListener() {

		iv_more.setOnClickListener(new IvListener(iv_more,
				HunChe_HunCheZhuangShiActivity.this, 0));
	}
	protected void toast(Context context , String info){
		Toast.makeText(context,info,Toast.LENGTH_SHORT).show();
	}

	private boolean isFalse = true;
	private int ciShu = 0;
	private ACache mCache;

	private void getCarList() {




		XUtilsHelper xUtilsHelper1 = new XUtilsHelper(this,"WedingCarHandler.ashx?Action=getDecorate");
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
				getCarList();
				if (ciShu >= 3) {
					isFalse = false;
					toast(HunChe_HunCheZhuangShiActivity.this,
							getString(R.string.conn_failed) + e);

				}
				;

				if (isFalse) {
					getCarList();
					ciShu++;
				}
			}

			@Override
			public void onNext(String s) {
				if (s != null) {

					mCache.put("婚车装饰", s, 60 * 60 * 24);

				}

				trcy(s);

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




}
