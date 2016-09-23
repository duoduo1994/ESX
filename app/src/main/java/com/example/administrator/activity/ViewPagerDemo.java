package com.example.administrator.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.fragment.Tab;
import com.example.administrator.list.son;
import com.example.administrator.myapplication.R;
import com.example.administrator.utils.ACache;
import com.example.administrator.utils.ActivityCollector;
import com.example.administrator.utils.IvListener;


public class ViewPagerDemo extends FragmentActivity {

	private ViewPager viewPager;

	private ACache mCache;
	private List<son> l1 = new ArrayList<son>();
	private List<son> l2 = new ArrayList<son>();
	// private List<son> l3 = new ArrayList<son>();
	private int isZiXuan;
	private TextView tv;
	@Override
	public void onCreate(Bundle savedInstanceState) {
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
		setContentView(R.layout.activity_view_pager_demo);
		ActivityCollector.addActivity(this);
		initView();
		isZiXuan = getIntent().getIntExtra("iszixuan", 0);
		mCache = ACache.get(this);
		tv = (TextView) findViewById(R.id.tv_title);
		tv.setText("套餐详情");
		findViewById(R.id.btn_back).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(System.currentTimeMillis()-HomeActivity.dianJiShiJian>500){
					HomeActivity.dianJiShiJian = System.currentTimeMillis();
					System.out.println("#############");
					ViewPagerDemo.this.finish();
				}
				
					
					return;
			}
		});
		if (null != mCache.getAsString("喜宴套餐详情")) {
			son n1;
			son n2;

			if (isZiXuan == 3) {// zixuan

				for (int i = 0; i < FeastSetActivity.lists.size(); i++) {
					n1 = new son();
					n2 = new son();
					// n3 = new son();
					if (FeastSetActivity.lists.get(i).isiCho()) {
						if ("32".equals(FeastSetActivity.lists.get(i).getId())) {
							n1.setName(FeastSetActivity.lists.get(i).getName());
							n1.setImageUrl(FeastSetActivity.lists.get(i)
									.getImageUrl());
							l1.add(n1);
						}
						if ("33".equals(FeastSetActivity.lists.get(i).getId())) {
							n2.setName(FeastSetActivity.lists.get(i).getName());
							n2.setImageUrl(FeastSetActivity.lists.get(i)
									.getImageUrl());
							l2.add(n2);
						}
					}
				}
			} else if (isZiXuan == 2) {// tihuan
				for (int i = 0; i < FeastSetActivity.lists.size(); i++) {
					System.out.println(FeastSetActivity.lists.get(i));
					n1 = new son();
					n2 = new son();
					// n3 = new son();
					if (FeastSetActivity.lists.get(i).isTiHuan()) {
					}

					if ("32".equals(FeastSetActivity.lists.get(i).getId())) {
						n1.setName(FeastSetActivity.lists.get(i).getName());
						n1.setImageUrl(FeastSetActivity.lists.get(i)
								.getImageUrl());
						l1.add(n1);
					}
					if ("33".equals(FeastSetActivity.lists.get(i).getId())) {
						n2.setName(FeastSetActivity.lists.get(i).getName());
						n2.setImageUrl(FeastSetActivity.lists.get(i)
								.getImageUrl());
						l2.add(n2);
					}

				}
			} else if (isZiXuan == 1) {
				String result = mCache.getAsString("喜宴套餐详情");

				try {
					JSONObject json = new JSONObject(result.trim());
					JSONArray configlist = json.getJSONArray("菜单");

					if (configlist != null) {
						int size = configlist.length();
						JSONObject tJson = null;

						for (int i = 0; i < size; i++) {
							n1 = new son();
							n2 = new son();
							// n3 = new son();
							tJson = configlist.getJSONObject(i);
							String secondId = tJson.getString("DishCgy");

							String lessonpId = tJson.getString("DishName");

							String ImageUrl = tJson.getString("ImageUrl");

							if ("32".equals(secondId)) {
								n1.setName(lessonpId);
								n1.setImageUrl(ImageUrl);
								l1.add(n1);
							}
							if ("33".equals(secondId)) {
								n2.setName(lessonpId);
								n2.setImageUrl(ImageUrl);
								l2.add(n2);
							}
						}
					}

				} catch (JSONException e) {
					e.printStackTrace();

				}

			}
		}

		setMoreListener();
	}

	private ImageView iv_more;

	private void setMoreListener() {
		iv_more = (ImageView) findViewById(R.id.iv_more);
		iv_more.setOnClickListener(new IvListener(iv_more, ViewPagerDemo.this,
				0));
	}



	private void initView() {

		viewPager = (ViewPager) findViewById(R.id.viewpager);
		fragments = new ArrayList<Fragment>();
		fragments.add(new Tab("冷菜", l1,ViewPagerDemo.this));
		fragments.add(new Tab("热菜", l2,ViewPagerDemo.this));
		// fragments.add(new Tab1("酒水", l3));
		viewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));

	}

	private List<Fragment> fragments;

	private class MyAdapter extends FragmentPagerAdapter {

		public MyAdapter(FragmentManager fm) {
			super(fm);

		}

		@Override
		public Fragment getItem(int arg0) {

			return fragments.get(arg0);
		}

		@Override
		public int getCount() {

			return fragments.size();
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if(System.currentTimeMillis()-HomeActivity.dianJiShiJian>500){
				HomeActivity.dianJiShiJian = System.currentTimeMillis();
				System.out.println("#############");
				ViewPagerDemo.this.finish();
			}
			
				return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
