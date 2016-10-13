package com.example.esycab;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.view.Display;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import cn.jpush.android.api.JPushInterface;

import com.example.esycab.network.SmartFruitsRestClient;
import com.example.esycab.utils.LocalStorage;
import com.example.esycab.utils.MD5Util;
import com.example.esycab.utils.ProConst;
import com.example.esycab.utils.SmartConfig;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * 开始页
 * 
 * @author Administrator
 * 
 */
public class StartActivity extends Activity implements ProConst {
	public static ExecutorService exec = Executors.newSingleThreadExecutor();
	private final int SPLASH_DISPLAY_LENGHT = 2000; // 延迟2秒
	private SharedPreferences preferences;
	private Editor editor;
	public static int displayWidth = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
          //  getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
		}else{
			this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
			this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏
		}
		setContentView(R.layout.activity_pageing);
		
//		TelephonyManager tm = (TelephonyManager) getBaseContext()
//				.getSystemService(Context.TELEPHONY_SERVICE);

//		strUniqueId=JPushInterface.getRegistrationID(StartActivity.this);
//		Toast.makeText(StartActivity.this, "识别码为"+strUniqueId, 1).show();
//		tmDevice = "" + tm.getDeviceId();
//		tmSerial = "" + tm.getSimSerialNumber();
//		androidId = ""
//				+ android.provider.Settings.Secure.getString(
//						getContentResolver(),
//						android.provider.Settings.Secure.ANDROID_ID);

//		deviceUuid = new UUID(androidId.hashCode(),
//				((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
//		strUniqueId = deviceUuid.toString().toUpperCase();
		//System.out.println(strUniqueId);
		preferences = getSharedPreferences("phone", Context.MODE_PRIVATE);

		// 初始化配置文件
		SmartConfig smcConfig = SmartConfig.getInstance();
		smcConfig.propertyInit(this);
		LocalStorage.initContext(this);
		LocalStorage.set("progress", "");
		if (isConnect(this) == false) {
			Toast.makeText(StartActivity.this, "设备无连接，请连接网络", 4000).show();
		} else {

		}
		Display mDisplay = getWindowManager().getDefaultDisplay();
		displayWidth = mDisplay.getWidth();
		// System.out.println("111111密码：  " + UserPass + "22222手机号  ：" +
		// UserTel);
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				System.out.println("HGB"+LocalStorage.get("LoginStatus"));
				if (preferences.getBoolean("firststart", true)) {
					editor = preferences.edit();
					// 将登录标志位设置为false，下次登录时不在显示首次登录界面
					editor.putBoolean("firststart", false);
					editor.commit();
					Intent intent = new Intent();
					intent.setClass(StartActivity.this,
							AndyViewPagerActivity.class);
					StartActivity.this.startActivity(intent);
					StartActivity.this.finish();
				} else {
					Intent intent = new Intent();
					intent.setClass(StartActivity.this, HomeActivity.class);
					StartActivity.this.startActivity(intent);
					StartActivity.this.finish();

					// }
					// } else {
					// // 1.判断LocalStorage.get("")是否有值
					// // 如有：用保存的用户密码重新连接服务器登录操作，如果成功，直接跳到主页，失败，登录页面
					// // 如没有：跳到登录页面
					// System.out.println("密码：  " + UserPass + "手机号  ：" +
					// UserTel);
					//
					// if ("".equals(UserTel) && "".equals(UserPass)) {
					// System.out.println("跳转页面!!");
					// // 没有跳到登陆
					// Intent intent = new Intent();
					// intent.setClass(StartActivity.this, MainActivity.class);
					// StartActivity.this.startActivity(intent);
					// StartActivity.this.finish();
					// } else {
					// // 将此值作为登陆
					// RequestParams params = new RequestParams();
					// String passEnt = MD5Util.string2MD5(UserPass);
					// // 将数值发送到服务
					// params.add("UserTel", UserTel);
					// params.add("UserPass", passEnt);
					// SmartFruitsRestClient.post(
					// "LoginCheckHandler.ashx?Action=LoginCheck",
					// params, new AsyncHttpResponseHandler() {
					// @Override
					// public void onSuccess(int arg0,
					// Header[] arg1, byte[] arg2) {
					// try {
					// Intent intent = new Intent();
					// intent.setClass(StartActivity.this,
					// HomeActivity.class);
					// // intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					// startActivity(intent);
					// finish();
					//
					// } catch (Exception e) {
					// // TODO: handle exception
					// }
					//
					// }
					//
					// @Override
					// public void onFailure(int arg0,
					// Header[] arg1, byte[] arg2,
					// Throwable arg3) {
					//
					// }
					//
					// });
					// }
				}

			}
		}, SPLASH_DISPLAY_LENGHT);

	}
	
	public static void dengRu(){
		String tel = (String) LocalStorage.get("UserTel").toString();
		String passEnt = MD5Util.string2MD5((String) LocalStorage.get("UserPass").toString());
		RequestParams p = new RequestParams();
		p.put("UserPass",passEnt );
		p.put("UserTel", tel);
		p.put("CustPhyAddr", HomeActivity.strUniqueId);
		SmartFruitsRestClient.get(
				"LoginCheckHandler.ashx?Action=UserLogin", p,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
						// TODO Auto-generated method stub
						String result = new String(arg2);
						System.out.println("RFY"+result);
							LocalStorage.set("LoginStatus", "login");
						
					}
					@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
							Throwable arg3) {
						// TODO Auto-generated method stub
						System.out.println(arg3 + "asdfgdhgsa");
						if (ciShu >= 3) {
							isFalse = false;
						}
						;

						if (isFalse) {
							tuiChu();
							ciShu++;
						}

				}
				}
				);
	}
	public static void tuiChu(){
		String tel = (String) LocalStorage.get("UserTel").toString();
//		String passEnt = MD5Util.string2MD5((String) LocalStorage.get("UserPass").toString());
		RequestParams p = new RequestParams();
		//p.put("UserPass",passEnt );
		p.put("UserTel", tel);
		p.put("CustPhyAddr", HomeActivity.strUniqueId);
		SmartFruitsRestClient.get(
				"LoginCheckHandler.ashx?Action=UserExit", p,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
						// TODO Auto-generated method stub
						String ut=LocalStorage.get("UserTel").toString();
//						String up=LocalStorage.get("UserPass").toString();
						LocalStorage.clear();
						LocalStorage.set("LoginStatus", "tuichu");
//						LocalStorage.set("UserPass",up );
						LocalStorage.set("UserTel", ut);
						String result = new String(arg2);
						System.out.println("RFY"+result);
						try {
							JSONObject json = new JSONObject(result.trim());
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
					@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
							Throwable arg3) {
						// TODO Auto-generated method stub
						if (ciShu >= 3) {
							isFalse = false;
						}
						;

						if (isFalse) {
							tuiChu();
							ciShu++;
						}

				}
				}
				);
	}
	static boolean isFalse = true;
	static int ciShu = 0;
	/*
	 * 判断网络连接是否已开 true 已打开 false 未打开
	 */
	private boolean isConnect(StartActivity startActivity) {
		ConnectivityManager conManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo networkInfo = conManager.getActiveNetworkInfo();

		if (networkInfo != null) { // 注意，这个判断一定要的哦，要不然会出错

			return networkInfo.isAvailable();

		}

		return false;

	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		JPushInterface.onPause(this);
		super.onPause();
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		JPushInterface.onResume(this);
		super.onResume();
	}
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
