package com.example.administrator.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.example.administrator.fragment.FragmentAddress;
import com.example.administrator.fragment.FragmentHome;
import com.example.administrator.fragment.FragmentNotify;
import com.example.administrator.myapplication.R;

import cn.jpush.android.api.JPushInterface;

/**
 * 首页
 * 
 * @author Administrator
 * 
 */
public class HomeActivity extends FragmentActivity {
	private FragmentHome home;// 首页
//	private FragmentAddress me;// 个人中心
//	private FragmentNotify shopping;// 订单
//	private RadioGroup myTabRg;
	public static HomeActivity _instance;
	private boolean isFirst = true;
	private String a;
	private SharedPreferences sharedPreferences;
	private Editor editor;

	public static boolean quan = false;
	public static int a12;
	public static String strUniqueId;
	public static long dianJiShiJian;



	@SuppressLint("InlinedApi")

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_mains);
//		LocalStorage.initContext(_instance);
		dianJiShiJian = System.currentTimeMillis();
		// Toast.makeText(HomeActivity.this, "识别码为"+strUniqueId, 1).show();
		a12 = getStatusBarHeight();

		// manager = (NotificationManager)
		// getSystemService(Context.NOTIFICATION_SERVICE);
		PackageManager packageManager = this.getPackageManager();
		home = new FragmentHome();
//		shopping = new FragmentNotify();
//		me = new FragmentAddress();
		try {
			sharedPreferences = getSharedPreferences("banben", MODE_PRIVATE);
			isFirst = sharedPreferences.getBoolean("first", true);

			PackageInfo packageInfo = packageManager.getPackageInfo(
					this.getPackageName(), 0);
			a = packageInfo.versionName;
			// Toast.makeText(HomeActivity.this, a, 3000).show();
//			getBanBen();
			if (isFirst) {
				isFirst = false;
				editor = sharedPreferences.edit();// 获取编辑器
				editor.putBoolean("first", isFirst);
				editor.commit();// 提交修改
			}
			// Toast.makeText(HomeActivity.this, a, Toast.LENGTH_LONG).show();
		} catch (NameNotFoundException e1) {
			// TODO Auto-generated catch block
			System.out.println(e1.getMessage());
		}
		// getDingDanWei(0);
		_instance = this;
		home = new FragmentHome();
		// 替换选择项对应的界面
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.main_content, home).commit();
//		myTabRg = (RadioGroup) findViewById(R.id.tab_menu);
		// 监听RadioGroup选中项改变事件
//		myTabRg.setOnCheckedChangeListener(new OnCheckedChangeListener() {
//			@Override
//			public void onCheckedChanged(RadioGroup group, int checkedId) {
//				// TODO Auto-generated method stub
//				switch (checkedId) {
//				case R.id.iv_menu_0://
//					if(!home.isAdded()){
//						getSupportFragmentManager().beginTransaction().hide(me).hide(shopping).add(R.id.main_content, home).commit();
//
//					}else{
//						getSupportFragmentManager().beginTransaction().show(home).hide(me).hide(shopping).commit();
//					}
//					break;
//
//				case R.id.iv_menu_1:
//					if(!shopping.isAdded()){
//						getSupportFragmentManager().beginTransaction().hide(me).hide(home).add(R.id.main_content, shopping).add(R.id.main_content, me).commit();
//					}else{
//						getSupportFragmentManager().beginTransaction().show(shopping).hide(me).hide(home).commit();
//
//					}
//					break;
//				case R.id.iv_menu_2:
//					if(!me.isAdded()){
//						getSupportFragmentManager().beginTransaction().hide(home).hide(shopping).add(R.id.main_content, shopping).add(R.id.main_content, me).commit();
//					}else{
//
//						getSupportFragmentManager().beginTransaction().hide(home).hide(shopping).show(me).commit();
//					}
//					break;
//				}
//			}
//		});

		return;
	}

	private Dialog d;
	private String bb;
	private Button p1;
	private Button n;
	private View v;

	private int getStatusBarHeight() {
		int result = 0;
		int resourceId = getResources().getIdentifier("status_bar_height",
				"dimen", "android");
		if (resourceId > 0) {
			result = getResources().getDimensionPixelSize(resourceId);
		}
		return result;
	}








	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
				sharedPreferences = getSharedPreferences("banben", MODE_PRIVATE);
				editor = sharedPreferences.edit();
				editor.clear();
				editor.commit();
				finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
