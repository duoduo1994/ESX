package com.example.esycab;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.esycab.network.SmartFruitsRestClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.os.Bundle;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class JianChaGengXinActivity extends Activity {
	private TextView banBenDQ, banBenZX;
	private SharedPreferences sharedPreferences;
	private Editor editor;
	private boolean isFirst = true;
	private Button gengXin;
	private TextView tv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
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
		setContentView(R.layout.activity_jian_cha_geng_xin);
		// RelativeLayout rl;
		// if(!HomeActivity.quan){
		// rl = (RelativeLayout) findViewById(R.id.g_eng_d_uo);
		// android.widget.LinearLayout.LayoutParams l =
		// (android.widget.LinearLayout.LayoutParams) rl.getLayoutParams();
		// l.topMargin = (int) (HomeActivity.a12);
		// rl.setLayoutParams(l);
		// }
		tv = (TextView) findViewById(R.id.tv_title);
		tv.setText("检查更新");
		findViewById(R.id.btn_back).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (System.currentTimeMillis() - HomeActivity.dianJiShiJian > 500) {

					HomeActivity.dianJiShiJian = System.currentTimeMillis();
					JianChaGengXinActivity.this.finish();
				}
			}
		});
		gengXin = (Button) findViewById(R.id.gengduo_jiancha_gengxin_anniu);
		gengXin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (gengXin.getText().toString().equals("更新")) {
					sharedPreferences = getSharedPreferences("banbens",
							MODE_PRIVATE);
					editor = sharedPreferences.edit();
					editor.clear();
					editor.commit();
					startActivity(new Intent(JianChaGengXinActivity.this,
							GenXingActivity.class));
				} else {
					getBanBen();
				}
			}
		});
		banBenDQ = (TextView) findViewById(R.id.gengduo_jiancha_gengxin_dq);
		banBenZX = (TextView) findViewById(R.id.gengduo_jiancha_gengxin_zx);

		PackageManager packageManager = this.getPackageManager();
		try {
			sharedPreferences = getSharedPreferences("banbens", MODE_PRIVATE);
			isFirst = sharedPreferences.getBoolean("first", true);

			if (isFirst) {
				System.out.println("GHJUK");
				PackageInfo packageInfo = packageManager.getPackageInfo(
						this.getPackageName(), 0);
				a = packageInfo.versionName;

				getBanBen();
				isFirst = false;

			} else {
				a = sharedPreferences.getString("DQ", null);
				bb = sharedPreferences.getString("ZX", null);
				banBenDQ.setText(a);
				banBenZX.setText(bb);
			}
			// Toast.makeText(HomeActivity.this, a, Toast.LENGTH_LONG).show();
		} catch (NameNotFoundException e1) {
			// TODO Auto-generated catch block
			System.out.println(e1.getMessage());
		}
	}

	private String a;

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
							// System.out.println(j.toString());
							// System.out.println(j.length());
							bb = j.getString("Ver");
							// System.out.println(bb + "HJ");
							sharedPreferences = getSharedPreferences("banbens",
									MODE_PRIVATE);
							banBenDQ.setText(a);
							banBenZX.setText(bb);
							editor = sharedPreferences.edit();// 获取编辑器
							editor.putBoolean("first", isFirst);
							editor.putString("DQ", a);
							editor.putString("ZX", bb);
							editor.commit();// 提交修改
							if (!a.equals(bb)) {
								gengXin.setText("更新");
							} else {
								Toast.makeText(JianChaGengXinActivity.this,
										"当前版本为最新版本", Toast.LENGTH_SHORT).show();
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							System.out.println(e.getMessage());
							// Toast.makeText(JianChaGengXinActivity.this,
							// e.getMessage(),
							// Toast.LENGTH_SHORT).show();
						}
					}

					@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
							Throwable arg3) {
						// TODO Auto-generated method stub
						System.out.println(arg3 + "asdfgdhgsa");
						if (ciShu >= 3) {
							isFalse = false;
							Toast.makeText(JianChaGengXinActivity.this,
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

	private String bb;
	private boolean isFalse = true;
	private int ciShu = 0;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (System.currentTimeMillis() - HomeActivity.dianJiShiJian > 500) {

				HomeActivity.dianJiShiJian = System.currentTimeMillis();
				JianChaGengXinActivity.this.finish();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
