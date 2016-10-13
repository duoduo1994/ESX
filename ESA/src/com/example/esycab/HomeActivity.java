package com.example.esycab;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import cn.jpush.android.api.JPushInterface;

import com.example.esycab.Fragment.FragmentAddress;
import com.example.esycab.Fragment.FragmentHome;
import com.example.esycab.Fragment.FragmentNotify;
import com.example.esycab.network.SmartFruitsRestClient;
import com.example.esycab.utils.LocalStorage;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * 首页
 * 
 * @author Administrator
 * 
 */
public class HomeActivity extends FragmentActivity {
	private FragmentHome home;// 首页
	private FragmentAddress me;// 个人中心
	private FragmentNotify shopping;// 订单
	private RadioGroup myTabRg;
	public static HomeActivity _instance;
	private boolean isFirst = true;
	private String a;
	private SharedPreferences sharedPreferences;
	private Editor editor;
	static Activity homethis;
	public static boolean quan = false;
	public static int a12;
	public static String strUniqueId;
	public static long dianJiShiJian;

	@SuppressLint("InlinedApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
		// this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		// WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏
		// getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
		// getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
		if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
			// 透明状态栏
			getWindow().addFlags(
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			// 透明导航栏
			// getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
		} else {
			quan = true;
			this.requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏
			this.getWindow().setFlags(
					WindowManager.LayoutParams.FLAG_FULLSCREEN,
					WindowManager.LayoutParams.FLAG_FULLSCREEN);// 去掉信息栏
		}
		setContentView(R.layout.activity_main);
		LocalStorage.initContext(_instance);
		dianJiShiJian = System.currentTimeMillis();
		strUniqueId = JPushInterface.getRegistrationID(HomeActivity.this);
		System.out.println(strUniqueId);
		// Toast.makeText(HomeActivity.this, "识别码为"+strUniqueId, 1).show();
		a12 = getStatusBarHeight();
		homethis = HomeActivity.this;
		// manager = (NotificationManager)
		// getSystemService(Context.NOTIFICATION_SERVICE);
		PackageManager packageManager = this.getPackageManager();
		home = new FragmentHome();
		shopping = new FragmentNotify();
		me = new FragmentAddress();
		try {
			sharedPreferences = getSharedPreferences("banben", MODE_PRIVATE);
			isFirst = sharedPreferences.getBoolean("first", true);
			System.out.println("GHJUK");

			PackageInfo packageInfo = packageManager.getPackageInfo(
					this.getPackageName(), 0);
			a = packageInfo.versionName;
			// Toast.makeText(HomeActivity.this, a, 3000).show();
			getBanBen();
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
		myTabRg = (RadioGroup) findViewById(R.id.tab_menu);
		// 监听RadioGroup选中项改变事件
		myTabRg.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				switch (checkedId) {
				case R.id.iv_menu_0://
					if(!home.isAdded()){
						getSupportFragmentManager().beginTransaction().hide(me).hide(shopping).add(R.id.main_content, home).commit();
					}else{
						getSupportFragmentManager().beginTransaction().show(home).hide(me).hide(shopping).commit();
					}
					break;

				case R.id.iv_menu_1:
					if(!shopping.isAdded()){
						getSupportFragmentManager().beginTransaction().hide(me).hide(home).add(R.id.main_content, shopping).add(R.id.main_content, me).commit();
					}else{
						getSupportFragmentManager().beginTransaction().show(shopping).hide(me).hide(home).commit();
						
					}
					break;
				case R.id.iv_menu_2:
					if(!me.isAdded()){
						getSupportFragmentManager().beginTransaction().hide(home).hide(shopping).add(R.id.main_content, shopping).add(R.id.main_content, me).commit();
					}else{
						
						getSupportFragmentManager().beginTransaction().hide(home).hide(shopping).show(me).commit();
					}
					break;
				}
			}
		});

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

	private void getBanBen() {
		RequestParams p = new RequestParams();
		p.put("Type", 0);
		SmartFruitsRestClient.get(
				"IssueHandler.ashx?Action=GetCurrentIssueInfo", p,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
						// TODO Auto-generated method stub
						String result = new String(arg2);
						System.out.println(result);
						try {
							JSONArray json = new JSONArray(result.trim());
							JSONObject j = (JSONObject) json.get(0);
							System.out.println(j.toString());
							System.out.println(j.length());
							bb = j.getString("Ver");
							System.out.println(bb.replace(".", "") + "HJ"
									+ a.replace(".", ""));
							int a1 = Integer.parseInt(bb.replace(".", ""))
									- Integer.parseInt(a.replace(".", ""));
							System.out.println(a1 + "^^^^^^^^^^^^^^^^^^");
							if (a1 < 0) {
								System.out.println(bb.equals(a));
							} else {
								if (HomeActivity.this.isFinishing()) {
								} else {
									d = new Dialog(HomeActivity.this,
											R.style.loading_dialog);
									v = LayoutInflater.from(HomeActivity.this)
											.inflate(R.layout.dialog, null);// 窗口布局
									d.setContentView(v);// 把设定好的窗口布局放到dialog中
									d.setCanceledOnTouchOutside(true);// 设定点击窗口空白处取消会话
									p1 = (Button) v.findViewById(R.id.p);
									n = (Button) v.findViewById(R.id.n);
									p1.setOnClickListener(new OnClickListener() {

										@Override
										public void onClick(View arg0) {
											d.dismiss();
											startActivity(new Intent(
													HomeActivity.this,
													GenXingActivity.class));
										}
									});
									n.setOnClickListener(new OnClickListener() {

										@Override
										public void onClick(View arg0) {
											// TODO Auto-generated method stub
											// System.out.println(a);
											d.dismiss();
										}
									});
									d.show();
								}
							}
						} catch (JSONException e) {
							System.out.println(e.getMessage());
							// Toast.makeText(HomeActivity.this, e.getMessage(),
							// Toast.LENGTH_SHORT).show();
						}
					}

					@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
							Throwable arg3) {
						System.out.println(arg3 + "asdfgdhgsa");
						if (ciShu >= 3) {
							isFalse = false;
							Toast.makeText(HomeActivity.this,
									getString(R.string.conn_failed),
									Toast.LENGTH_SHORT).show();
						}
						;
						if (isFalse) {
							getBanBen();
							ciShu++;
						}

					}
				});
	}

	//
	// public void getDingDanWei(final int a ) {
	// RequestParams p = new RequestParams();
	// if (LocalStorage.get("UserTel").toString().length() != 11) {
	//
	// } else {
	// System.out.println(LocalStorage.get("UserTel").toString()
	// + "_________");
	// p.put("fkCusTel", LocalStorage.get("UserTel").toString());
	// SmartFruitsRestClient.get("BookHandler.ashx?Action=bookAllInfo", p,
	// new AsyncHttpResponseHandler() {
	//
	// @Override
	// public void onSuccess(int arg0, Header[] arg1,
	// byte[] arg2) {
	// String result = new String(arg2);
	// System.out.println("RFY" + result);
	// try {
	// JSONObject json = new JSONObject(result.trim());
	// JSONArray j = json.getJSONArray("预定信息列表");
	// JSONObject tj;
	// if (j.length() != 0) {
	// for (int i = 0; i < j.length(); i++) {
	// tj = j.getJSONObject(i);
	// if
	// (tj.getString("OrdStatus").equals("3")||tj.getString("OrdStatus").equals("2")||tj.getString("OrdStatus").equals("1"))
	// {
	// SimpleDateFormat df = new SimpleDateFormat(
	// "yyyy/MM/dd HH:mm:ss");// 设置日期格式
	// dat = df.format(new Date());// new
	// // Date()为获取当前系统时间
	// dat1 = tj.getString("BookDt");
	// zhuangTai = tj.getString("OrdStatus");
	// if(a == 1){
	// return;
	// }else{
	//
	// System.out.println(zhuangTai + "______"
	// + dat1);
	// handler.postDelayed(runnable, 5000);}
	// }
	// }
	//
	// }
	// } catch (JSONException e) {
	// e.printStackTrace();
	// }
	//
	// }
	//
	// @Override
	// public void onFailure(int arg0, Header[] arg1,
	// byte[] arg2, Throwable arg3) {
	// System.out.println(arg3 + "asdfgdhgsa");
	// if (ciShu >= 3) {
	// isFalse = false;
	// }
	// ;
	// if (isFalse) {
	// ciShu++;
	// }
	//
	// }
	// });
	// }
	// }

	// public void tui() {
	// String[] t = dat1.split("/");
	// int nian1 = Integer.parseInt(t[0]);
	// int yue1 = Integer.parseInt(t[1]);
	// int ri1 = Integer.parseInt(t[2].split(" ")[0]);
	// String[] t1 = dat.split("/");
	// int nian2 = Integer.parseInt(t1[0]);
	// int yue2 = Integer.parseInt(t1[1]);
	// int ri2 = Integer.parseInt(t1[2].split(" ")[0]);
	// if (nian1 == nian2 && yue1 == yue2 && ri1 == ri2) {
	//
	// } else {
	// PendingIntent pendingIntent = PendingIntent.getActivity(
	// HomeActivity.this, 0, new Intent(HomeActivity.this,
	// WoDeDingDanActivity.class), 0);
	// // 下面需兼容Android 2.x版本是的处理方式
	// // Notification notify1 = new Notification(R.drawable.message,
	// // "TickerText:" + "您有新短消息，请注意查收！", System.currentTimeMillis());
	// Notification notify1 = new Notification();
	// notify1.icon = R.drawable.logo;
	// notify1.tickerText = "EMA:您有未支付的订单，为了方便使用，去支付订单吧！";
	// notify1.when = System.currentTimeMillis();
	// notify1.setLatestEventInfo(this, "提示", "乡村喜宴", pendingIntent);
	// notify1.number = 1;
	// notify1.flags |= Notification.FLAG_AUTO_CANCEL; //
	// FLAG_AUTO_CANCEL表明当通知被用户点击时，通知将被清除。
	// // 通过通知管理器来发起通知。如果id不同，则每click，在statu那里增加一个提示
	// manager.notify(NOTIFICATION_FLAG, notify1);
	// }
	// }

	// private boolean isYi = true;
	// Handler handler = new Handler();
	// Runnable runnable = new Runnable() {
	// @Override
	// public void run() {
	// if (isYi) {
	// isYi = false;
	// tui();
	// }
	// handler.postDelayed(this, 5000);
	// }
	// };
	private long exitTime = 0;
	// private static final int NOTIFICATION_FLAG = 1;
	private boolean isFalse = true;
	private int ciShu = 0;

	// private String dat;
	// private String dat1;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			if ((System.currentTimeMillis() - exitTime) > 2000) {
				Toast.makeText(getApplicationContext(), "再按一次退出程序",
						Toast.LENGTH_SHORT).show();
				exitTime = System.currentTimeMillis();
			} else {
				sharedPreferences = getSharedPreferences("banben", MODE_PRIVATE);
				editor = sharedPreferences.edit();
				editor.clear();
				editor.commit();
				finish();
				System.exit(0);
				System.gc();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
