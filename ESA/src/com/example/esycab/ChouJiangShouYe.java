package com.example.esycab;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.esycab.network.SmartFruitsRestClient;
import com.example.esycab.utils.ActivityCollector;
import com.example.esycab.utils.Diolg;
import com.example.esycab.utils.LoadingDialog;
import com.example.esycab.utils.LocalStorage;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

public class ChouJiangShouYe extends Activity {

	private Button faqichoujiang, baoming, shouye_back;
	private String cjryzm = "";
	private String strUniqueId ;
	private String editTel, editPass;
	private LinearLayout choujiangshouyell;
	private LoadingDialog ld;

	@SuppressLint("NewApi")
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
		setContentView(R.layout.choujiangshouye);
		/*----------------设置背景--------------------------------------------*/
		choujiangshouyell = (LinearLayout) findViewById(R.id.choujiangshouyell);
		BitmapDrawable b = setImage(ChouJiangShouYe.this, R.drawable.cbg);
		choujiangshouyell.setBackground(b);
		/*----------------设置背景--------------------------------------------*/
		LocalStorage.initContext(this);
		editTel = (String) LocalStorage.get("UserTel");
		editPass = (String) LocalStorage.get("UserPass");
		faqichoujiang = (Button) findViewById(R.id.faqichoujiang);
		faqichoujiang.setOnClickListener(new OnClickEvent(2000) {
			@Override
			public void singleClick(View v) {
				ld = new LoadingDialog(ChouJiangShouYe.this, "正在加载，请稍后");
				ld.showDialog();
				judge();
			}
		});
		baoming = (Button) findViewById(R.id.baoming);
		baoming.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				Intent intent = new Intent();
				intent.setClass(ChouJiangShouYe.this, ChouJiangYanZheng.class);
				startActivity(intent);

			}
		});

		shouye_back = (Button) findViewById(R.id.shouye_back);
		shouye_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				ChouJiangShouYe.this.finish();
				ActivityCollector.finishAll();
			}
		});

	}

	private void judge() {
		strUniqueId	= HomeActivity.strUniqueId;
		String UserTel = (String) LocalStorage.get("UserTel");
		System.out.println(UserTel);
		RequestParams params = new RequestParams();
		params.put("CreatorTel", UserTel);
		params.put("CustPhyAddr", strUniqueId);
		SmartFruitsRestClient.post("HandlerDraw.ashx?Action=Create", params,
				new AsyncHttpResponseHandler() {

					@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
							Throwable arg3) {
						// TODO Auto-generated method stub
						new Diolg(ChouJiangShouYe.this, "确定", "null",
								"数据获取失败，请重试", "提示", 0);
						ld.closeDialog();
					}

					@Override
					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
						String result = new String(arg2);
						System.out.println(result);
						ld.closeDialog();
						try {
							JSONObject json = new JSONObject(result.trim());
							final String str = json.getString("抽奖验证码");
							System.out.println(str);
							final boolean isNum = str.matches("[0-9]+");
							if (isNum) {
								cjryzm = str;
								Intent intent = new Intent();
								intent.putExtra("flag", 2);
								intent.putExtra("id", cjryzm);
								intent.setClass(ChouJiangShouYe.this,
										ChouJiang.class);
								startActivityForResult(intent, 1);

							} else if (str.contains("没有登录")) {
								LocalStorage.clear();
								LocalStorage.set("LoginStatus", "tuichu");
								LocalStorage.set("UserPass", editPass);
								LocalStorage.set("UserTel", editTel);

								Intent intent = new Intent(
										ChouJiangShouYe.this,
										MainActivity.class);
								ChouJiangShouYe.this.startActivity(intent);
							}

						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							System.out.println(2);
						}

					}

				});
	}

	private BitmapDrawable setImage(Context context, int i) {
		BitmapFactory.Options opt = new BitmapFactory.Options();

		opt.inPreferredConfig = Bitmap.Config.RGB_565;

		opt.inPurgeable = true;

		opt.inInputShareable = true;

		// 获取资源图片

		InputStream is = context.getResources().openRawResource(i);

		Bitmap bitmap = BitmapFactory.decodeStream(is, null, opt);

		try {
			is.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return new BitmapDrawable(context.getResources(), bitmap);

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {

			if (System.currentTimeMillis() - HomeActivity.dianJiShiJian > 500) {
				HomeActivity.dianJiShiJian = System.currentTimeMillis();
				ChouJiangShouYe.this.finish();
				ActivityCollector.finishAll();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
