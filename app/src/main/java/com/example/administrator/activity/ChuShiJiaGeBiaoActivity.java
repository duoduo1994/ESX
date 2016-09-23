package com.example.administrator.activity;

import java.util.ArrayList;
import java.util.List;


import android.os.Bundle;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.fragment.ChuShiJiaGeFragment;
import com.example.administrator.myapplication.R;
import com.example.administrator.utils.ActivityCollector;
import com.example.administrator.utils.IvListener;
import com.lidroid.xutils.view.annotation.ViewInject;

public class ChuShiJiaGeBiaoActivity extends FragmentActivity implements
		OnPageChangeListener, OnClickListener {
	@ViewInject(R.id.btn_back)
	private Button wedding_back;
	@ViewInject(R.id.chushi_sanxingji)
	private Button san;
	@ViewInject(R.id.chushi_sixingji)
	private Button si;
	@ViewInject(R.id.chushi_wuxingji)
	private Button wu;
	private Button	tv;
	@ViewInject(R.id.tv_title)
	private TextView tv_title;
	@ViewInject(R.id.iv_more)
	private ImageView iv_more;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_chu_shi_jia_ge_biao);
		ActivityCollector.addActivity(this);
		// RelativeLayout rl;
		// if(!HomeActivity.quan){
		// rl = (RelativeLayout) findViewById(R.id.g_eng_d_uo);
		// android.widget.LinearLayout.LayoutParams l =
		// (android.widget.LinearLayout.LayoutParams) rl.getLayoutParams();
		// l.topMargin = (int) (HomeActivity.a12);
		// rl.setLayoutParams(l);
		// }

		iv_more.setOnClickListener(new IvListener(iv_more,
				ChuShiJiaGeBiaoActivity.this, 0));





		tv_title.setText("厨师价格表");

		wedding_back.setOnClickListener(this);

		san.setOnClickListener(this);
		si.setOnClickListener(this);
		wu.setOnClickListener(this);
		fragments = new ArrayList<ChuShiJiaGeFragment>();
		fragments.add(new ChuShiJiaGeFragment(3));
		fragments.add(new ChuShiJiaGeFragment(4));
		fragments.add(new ChuShiJiaGeFragment(5));
		pagers.setAdapter(new MyAdapter(getSupportFragmentManager()));
		pagers.setOnPageChangeListener(this);

	}


@ViewInject(R.id.vp)
	private ViewPager pagers;
	private int index = 0, before = 0;

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.btn_back:
			if (System.currentTimeMillis() - HomeActivity.dianJiShiJian > 500) {
				HomeActivity.dianJiShiJian = System.currentTimeMillis();
				ChuShiJiaGeBiaoActivity.this.finish();
			}
			break;
		case R.id.chushi_sanxingji:
			index = 0;
			break;
		case R.id.chushi_sixingji:
			index = 1;
			break;
		case R.id.chushi_wuxingji:
			index = 2;
			break;
		default:
			break;
		}
		pagers.setCurrentItem(index);// 设置ViewPager的点击事件
	}

	private List<ChuShiJiaGeFragment> fragments;// 适配器， Fragment配合viewPager

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
@ViewInject(R.id.tabs)
	private LinearLayout tabs;

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
		tv = (Button) tabs.getChildAt(before);
		tv.setTextColor(Color.BLACK);

		tv = (Button) tabs.getChildAt(arg0);
		tv.setTextColor(Color.parseColor("#7bb618"));
		before = arg0;

	}

	@Override
	public void onPageSelected(int arg0) {
		// TODO Auto-generated method stub
	}

}
