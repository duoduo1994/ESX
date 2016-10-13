package com.example.esycab;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.esycab.network.SmartFruitsRestClient;
import com.example.esycab.utils.ACache;
import com.example.esycab.utils.LocalStorage;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ChouJiangYanZheng extends Activity {

	private EditText edit;
	private Button st, win, back;
	private LinearLayout cjzy;
	boolean flag = false;
	float dy, uy, zy;
	private String yz;
	private ACache mCache;

	private TextView juTiXinXi, tiShi;
	private Dialog d;
	private Button p1;
	private Button n;
	private View v;

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
		setContentView(R.layout.choujiangyanzheng);

		LocalStorage.initContext(this);

		edit = (EditText) findViewById(R.id.edit);
		st = (Button) findViewById(R.id.st);
		/*-----------------------设置背景--------------------------------*/
		cjzy = (LinearLayout) findViewById(R.id.cjyz);
		BitmapDrawable b = setImage(ChouJiangYanZheng.this, R.drawable.pp6);
		cjzy.setBackground(b);
		/*-----------------------设置背景--------------------------------*/

		cjzy.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				switch (arg1.getAction()) {
				case MotionEvent.ACTION_DOWN:
					dy = arg1.getY();
					break;
				case MotionEvent.ACTION_UP:
					uy = arg1.getY();
					if ((uy - dy) <= 10 || (uy - dy) >= -10) {
						InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
						return imm.hideSoftInputFromWindow(getCurrentFocus()
								.getWindowToken(), 0);
					}
					break;
				default:
					break;
				}
				return true;
			}
		});

		mCache = ACache.get(this);
		win = (Button) findViewById(R.id.win);
		win.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				yz = edit.getText().toString();
				if (!yz.equals("")) {
					mCache.put("抽奖验证码", yz, 60 * 60 * 6);
				}

				Intent intent = new Intent();
				intent.putExtra("flag", 1);
				intent.setClass(ChouJiangYanZheng.this, winner.class);
				startActivityForResult(intent, 1);

			}
		});

		back = (Button) findViewById(R.id.back);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				ChouJiangYanZheng.this.finish();

			}
		});

		st.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				yz = edit.getText().toString();
				if (!yz.equals("")) {
					mCache.put("抽奖验证码", yz, 60 * 60 * 6);
				}

				String UserTel = (String) LocalStorage.get("UserTel");
				RequestParams params = new RequestParams();
				params.put("DrowID", yz);
				params.put("JoinTel", UserTel);

				SmartFruitsRestClient.post("HandlerDraw.ashx?Action=SignUp",
						params, new AsyncHttpResponseHandler() {

							@Override
							public void onFailure(int arg0, Header[] arg1,
									byte[] arg2, Throwable arg3) {
								// TODO Auto-generated method stub

							}

							@Override
							public void onSuccess(int arg0, Header[] arg1,
									byte[] arg2) {
								String result = new String(arg2);

								System.out.println(result);
								JSONObject json;
								try {
									json = new JSONObject(result.trim());
									String res = json.getString("结果");
									System.out.println(res);

									if (res != null) {
										if (ChouJiangYanZheng.this
												.isFinishing()) {
										} else {
											d = new Dialog(
													ChouJiangYanZheng.this,
													R.style.loading_dialog);
											v = LayoutInflater.from(
													ChouJiangYanZheng.this)
													.inflate(R.layout.dialog,
															null);// 窗口布局
											d.setContentView(v);// 把设定好的窗口布局放到dialog中
											d.setCanceledOnTouchOutside(true);// 设定点击窗口空白处取消会话
											p1 = (Button) v
													.findViewById(R.id.p);
											n = (Button) v.findViewById(R.id.n);
											juTiXinXi = (TextView) v
													.findViewById(R.id.banben_xinxi);
											tiShi = (TextView) v
													.findViewById(R.id.banben_gengxin);
											juTiXinXi.setText(res);
											tiShi.setText("消息");
											p1.setText("确定");
											n.setText("返回");
											p1.setOnClickListener(new OnClickListener() {
												@Override
												public void onClick(View arg0) {
													// TODO Auto-generated
													// method stub
													d.dismiss();
												}
											});
											n.setOnClickListener(new OnClickListener() {
												@Override
												public void onClick(View arg0) {
													// TODO Auto-generated
													// method stub
													// System.out.println(a);
													d.dismiss();
												}
											});
											d.show();
										}

									}
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

							}

						});

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
				ChouJiangYanZheng.this.finish();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
