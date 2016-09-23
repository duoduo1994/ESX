package com.example.administrator.activity;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.administrator.myapplication.R;
import com.example.administrator.net.XUtilsHelper;
import com.example.administrator.utils.ACache;
import com.example.administrator.utils.LocalStorage;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.view.annotation.ViewInject;


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
import android.widget.Toast;

import rx.Observable;
import rx.Subscriber;

public class ChouJiangYanZheng extends BaseActivity {
@ViewInject(R.id.edit)
	private EditText edit;
	@ViewInject(R.id.st)
	private Button st;
	@ViewInject(R.id.win)
	private Button win;
	@ViewInject(R.id.back)
	private Button back;
	@ViewInject(R.id.cjyz)
	private LinearLayout cjzy;
	boolean flag = false;
	float dy, uy, zy;
	private String yz;
	private ACache mCache;

	private TextView juTiXinXi;
	private TextView tiShi;
	private Dialog d;
	private Button p1;
	private Button n;
	private View v;
	@Override
	protected int setContentView() {
		return R.layout.choujiangyanzheng;
	}
	@Override
	protected void initView() {
		LocalStorage.initContext(this);


		/*-----------------------设置背景--------------------------------*/

		BitmapDrawable b = setImage(ChouJiangYanZheng.this, R.mipmap.pp6);
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

				XUtilsHelper xUtilsHelper1 = new XUtilsHelper(ChouJiangYanZheng.this,"HandlerDraw.ashx?Action=SignUp");
				RequestParams requestParams = new RequestParams();
				requestParams.addBodyParameter("DrowID", yz);
				requestParams.addBodyParameter("JoinTel", UserTel);
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

					}

					@Override
					public void onNext(String s) {
						JSONObject json;
						try {
							json = new JSONObject(s.trim());
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





//				RequestParams params = new RequestParams();
//				params.put("DrowID", yz);
//				params.put("JoinTel", UserTel);
//
//				SmartFruitsRestClient.post("HandlerDraw.ashx?Action=SignUp",
//						params, new AsyncHttpResponseHandler() {
//
//							@Override
//							public void onFailure(int arg0, Header[] arg1,
//									byte[] arg2, Throwable arg3) {
//								// TODO Auto-generated method stub
//
//							}
//
//							@Override
//							public void onSuccess(int arg0, Header[] arg1,
//									byte[] arg2) {
//								String result = new String(arg2);
//
//								System.out.println(result);
//								JSONObject json;
//								try {
//									json = new JSONObject(result.trim());
//									String res = json.getString("结果");
//									System.out.println(res);
//
//									if (res != null) {
//										if (ChouJiangYanZheng.this
//												.isFinishing()) {
//										} else {
//											d = new Dialog(
//													ChouJiangYanZheng.this,
//													R.style.loading_dialog);
//											v = LayoutInflater.from(
//													ChouJiangYanZheng.this)
//													.inflate(R.layout.dialog,
//															null);// 窗口布局
//											d.setContentView(v);// 把设定好的窗口布局放到dialog中
//											d.setCanceledOnTouchOutside(true);// 设定点击窗口空白处取消会话
//											p1 = (Button) v
//													.findViewById(R.id.p);
//											n = (Button) v.findViewById(R.id.n);
//											juTiXinXi = (TextView) v
//													.findViewById(R.id.banben_xinxi);
//											tiShi = (TextView) v
//													.findViewById(R.id.banben_gengxin);
//											juTiXinXi.setText(res);
//											tiShi.setText("消息");
//											p1.setText("确定");
//											n.setText("返回");
//											p1.setOnClickListener(new OnClickListener() {
//												@Override
//												public void onClick(View arg0) {
//													// TODO Auto-generated
//													// method stub
//													d.dismiss();
//												}
//											});
//											n.setOnClickListener(new OnClickListener() {
//												@Override
//												public void onClick(View arg0) {
//													// TODO Auto-generated
//													// method stub
//													// System.out.println(a);
//													d.dismiss();
//												}
//											});
//											d.show();
//										}
//
//									}
//								} catch (JSONException e) {
//									// TODO Auto-generated catch block
//									e.printStackTrace();
//								}
//
//							}
//
//						});

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





}
