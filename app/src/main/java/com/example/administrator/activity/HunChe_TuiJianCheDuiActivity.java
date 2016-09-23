package com.example.administrator.activity;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.administrator.myapplication.R;
import com.example.administrator.net.XUtilsHelper;
import com.example.administrator.utils.ACache;
import com.example.administrator.utils.ActivityCollector;
import com.example.administrator.utils.IvListener;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.content.Context;
import android.os.Bundle;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.app.Activity;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import rx.Observable;
import rx.Subscriber;

public class HunChe_TuiJianCheDuiActivity extends BaseActivity {
	@ViewInject(R.id.tv_title)
	private TextView tv;
	@ViewInject(R.id.hun_che_xilie)
	private ListView hun_che_xilie;
	private MyAdapter adapter;
	private ACache mCache;
	@Override
	protected int setContentView() {
		return R.layout.activity_hun_che__tui_jian_che_dui;
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

		tv.setText("车队系列");
		mCache = ACache.get(this);

		adapter = new MyAdapter();

		if (null != mCache.getAsString("推荐车队")) {
			String result = mCache.getAsString("推荐车队");
			trcy(result);
		} else {
			System.out.println(147852);
			getCarList();
		}

		hun_che_xilie.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent = new Intent(HunChe_TuiJianCheDuiActivity.this,
						HunCheZhanShi.class);
				String img = imageUrl.get(arg2);
				intent.putExtra("img", img);
				intent.putExtra("carname", car.get(arg2));
				startActivity(intent);

			}

		});

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
				HunChe_TuiJianCheDuiActivity.this, 0));
	}

	protected void toast(Context context , String info){
		Toast.makeText(context,info,Toast.LENGTH_SHORT).show();
	}

	private boolean isFalse = true;
	private int ciShu = 0;

	private void getCarList() {
		XUtilsHelper xUtilsHelper1 = new XUtilsHelper(this,"WedingCarHandler.ashx?Action=getCarList");
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
					toast(HunChe_TuiJianCheDuiActivity.this,
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

							mCache.put("推荐车队", s, 60 * 60 * 24);

						}
				System.out.print(s+"asdkak");
						trcy(s);

			}
		});






//

	}
	private List<String> imageUrl;
	private List<String> car;
	private void trcy(String result) {
		imageUrl = new ArrayList<String>();
		car = new ArrayList<String>();
		JSONObject json = null;
		try {
			JSONArray cars = new JSONArray(result.trim());

			for (int i = 0; i < cars.length(); i++) {
				json = new JSONObject(cars.get(i).toString());

				String carName = json.getString("Name");
				String carUrl = json.getString("Url");

				car.add(carName);
				imageUrl.add(carUrl);
			}
			hun_che_xilie.setAdapter(adapter);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private class MyAdapter extends BaseAdapter {
		LayoutInflater ll;

		MyAdapter() {
			ll = LayoutInflater.from(HunChe_TuiJianCheDuiActivity.this);
		}

		@Override
		public int getCount() {

			return car.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		/*
		 * 重写view
		 */
		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			ViewHolder v = null;
			if (arg1 == null) {
				arg1 = ll.inflate(
						R.layout.activity_hun_che__tui_jian_che_dui_tem, null);
				v = new ViewHolder();

				v.txtName = (TextView) arg1.findViewById(R.id.car_name);
				arg1.setTag(v);

			} else {
				v = (ViewHolder) arg1.getTag();
			}

			v.txtName.setText(car.get(arg0));

			return arg1;
		}

	}

	private class ViewHolder {
		TextView txtName;

	}





}
