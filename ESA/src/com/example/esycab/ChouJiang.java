package com.example.esycab;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.esycab.network.SmartFruitsRestClient;
import com.example.esycab.utils.LocalStorage;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

/**
 * 实现对listView的循环滚动
 * 
 * @author gerry
 * 
 */
public class ChouJiang extends Activity {
	private TextView num1, num2, num3, num8, num9, num10, num11, yzm;

	private Button start, winner, stop, winner_back, btn_stop;
	private List<String> l;

	private String tel;
	private String t1, t2, t3,  t8, t9, t10, t11;
	private String id;
	private RadioButton one, two, three;
	private String Grade;
	private String strUniqueId = HomeActivity.strUniqueId;
	private String startres, results;
	private ImageView pp4;

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
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);// 竖屏
		setContentView(R.layout.choujiang);
		/*-----------------------设置背景--------------------------------*/
		pp4 = (ImageView) findViewById(R.id.pp4);
		BitmapDrawable b = setImage(ChouJiang.this, R.drawable.pp4);
		pp4.setBackground(b);
		/*-----------------------设置背景--------------------------------*/

		Intent intent = getIntent();
		id = intent.getStringExtra("id");
		LocalStorage.initContext(this);
		System.out.println(id + "验证马");

		System.out.println(id + "choujiang");
		yzm = (TextView) findViewById(R.id.yzm);

		if ("".equals(id) || id == null) {
			System.out.println("hasbdabjksfka" + id);
			yzm.setText("请前往首页获取验证码");

		} else {
			System.out.println("asdasd" + id);
			yzm.setText(id);
		}
		one = (RadioButton) findViewById(R.id.one);
		two = (RadioButton) findViewById(R.id.two);
		three = (RadioButton) findViewById(R.id.three);
		three.setChecked(true);

		num1 = (TextView) findViewById(R.id.num1);
		num2 = (TextView) findViewById(R.id.num2);
		num3 = (TextView) findViewById(R.id.num3);
		num8 = (TextView) findViewById(R.id.num8);
		num9 = (TextView) findViewById(R.id.num9);
		num10 = (TextView) findViewById(R.id.num10);
		num11 = (TextView) findViewById(R.id.num11);
		stop = (Button) findViewById(R.id.stop);
		stop.setOnClickListener(new OnClickEvent(1000) {
			@Override
			public void singleClick(View arg0) {

				if (ChouJiang.this.isFinishing()) {
				} else {
					d = new Dialog(ChouJiang.this, R.style.loading_dialog);
					v = LayoutInflater.from(ChouJiang.this).inflate(
							R.layout.dialog, null);// 窗口布局
					d.setContentView(v);// 把设定好的窗口布局放到dialog中
					d.setCanceledOnTouchOutside(true);// 设定点击窗口空白处取消会话
					p1 = (Button) v.findViewById(R.id.p);
					n = (Button) v.findViewById(R.id.n);
					juTiXinXi = (TextView) v.findViewById(R.id.banben_xinxi);
					tiShi = (TextView) v.findViewById(R.id.banben_gengxin);
					juTiXinXi.setText("是否结束本轮抽奖");
					tiShi.setText("消息");
					p1.setText("确定");
					n.setText("返回");
					p1.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View arg0) {

							stop();
							d.dismiss();
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
		});
		l = new ArrayList<String>();
		for (int i = 0; i < 10; i++) {
			l.add(String.valueOf(i));
		}

		winner = (Button) findViewById(R.id.winner);
		winner.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				Intent intent = new Intent();
				intent.putExtra("flag", 2);
				intent.putExtra("id", id);
				intent.setClass(ChouJiang.this, winner.class);
				startActivityForResult(intent, 1);

			}
		});

		winner_back = (Button) findViewById(R.id.winner_back);
		winner_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				ChouJiang.this.finish();

			}
		});

		btn_stop = (Button) findViewById(R.id.btn_stop);
		btn_stop.setOnClickListener(new OnClickEvent(1000) {
			@Override
			public void singleClick(View arg0) {
				btn_stop.setVisibility(View.GONE);
				start.setVisibility(View.VISIBLE);
				if (one.isChecked()) {
					Grade = "一等奖";
				} else if (two.isChecked()) {
					Grade = "二等奖";
				} else {
					Grade = "三等奖";
				}
				String UserTel = (String) LocalStorage.get("UserTel");
				RequestParams params = new RequestParams();
				params.put("DrowID", id);
				params.put("DrawLevel", Grade);
				params.put("CreatorTel", UserTel);
				params.put("CustPhyAddr", strUniqueId);

				System.out.println(Grade + "," + id + "," + UserTel + ","
						+ strUniqueId);

				SmartFruitsRestClient.post("HandlerDraw.ashx?Action=SelectOne",
						params, new AsyncHttpResponseHandler() {

							@Override
							public void onFailure(int arg0, Header[] arg1,
									byte[] arg2, Throwable arg3) {
								// TODO Auto-generated method stub
							}

							@Override
							public void onSuccess(int arg0, Header[] arg1,
									byte[] arg2) {
								try {

									String result = new String(arg2);
									JSONObject json = new JSONObject(result
											.trim());
									String res = json.getString("结果");
									System.out.println(res);
									boolean isNum = res.matches("[0-9]+");
									if (isNum) {
										tel = res;
										t1 = tel.substring(0, 1);
										t2 = tel.substring(1, 2);
										t3 = tel.substring(2, 3);
										t8 = tel.substring(7, 8);
										t9 = tel.substring(8, 9);
										t10 = tel.substring(9, 10);
										t11 = tel.substring(10, 11);
										num1.setText("" + t1);
										num2.setText("" + t2);
										num3.setText("" + t3);
										num8.setText("" + t8);
										num9.setText("" + t9);
										num10.setText("" + t10);
										num11.setText("" + t11);
										handler.removeCallbacks(runnable);

									} else {
										handler.removeCallbacks(runnable);
										num1.setText("0");
										num2.setText("0");
										num3.setText("0");
										num8.setText("0");
										num9.setText("0");
										num10.setText("0");
										num11.setText("0");
										if (res != null) {
											if (ChouJiang.this.isFinishing()) {
											} else {
												d = new Dialog(ChouJiang.this,
														R.style.loading_dialog);
												v = LayoutInflater
														.from(ChouJiang.this)
														.inflate(
																R.layout.dialog,
																null);// 窗口布局
												d.setContentView(v);// 把设定好的窗口布局放到dialog中
												d.setCanceledOnTouchOutside(true);// 设定点击窗口空白处取消会话
												p1 = (Button) v
														.findViewById(R.id.p);
												n = (Button) v
														.findViewById(R.id.n);
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
													public void onClick(
															View arg0) {
														// TODO Auto-generated
														// method stub
														d.dismiss();
													}
												});
												n.setOnClickListener(new OnClickListener() {
													@Override
													public void onClick(
															View arg0) {
														// TODO Auto-generated
														// method stub
														// System.out.println(a);
														d.dismiss();
													}
												});
												d.show();
											}

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

		start = (Button) findViewById(R.id.start);
		start.setOnClickListener(new OnClickEvent(1000) {
			@Override
			public void singleClick(View arg0) {
				System.out.println(1);
				RequestParams param = new RequestParams();
				param.put("DrowID", id);
				String UserTel = (String) LocalStorage.get("UserTel");
				param.put("CreatorTel", UserTel);
				param.put("CustPhyAddr", strUniqueId);
				System.out.println(strUniqueId);
				SmartFruitsRestClient.post(
						"HandlerDraw.ashx?Action=GetApplyCount", param,
						new AsyncHttpResponseHandler() {

							@Override
							public void onFailure(int arg0, Header[] arg1,
									byte[] arg2, Throwable arg3) {
								// TODO Auto-generated method stub

							}

							@Override
							public void onSuccess(int arg0, Header[] arg1,
									byte[] arg2) {
								String result = new String(arg2);
								System.out.println(result + "ghjds");
								JSONObject json;
								try {
									json = new JSONObject(result.trim());
									if (result.contains("报名人数")) {
										startres = json.getString("报名人数");
										results = "报名人数不足";

									} else if (result.contains("提示")) {
										startres = json.getString("提示");
										results = startres;
									}
									System.out.println(startres + "sfhjajh");

									if ("0".equals(startres)
											|| "你的账户已在另一台手机登录".equals(startres)) {
										if (startres != null) {
											if (ChouJiang.this.isFinishing()) {
											} else {
												d = new Dialog(ChouJiang.this,
														R.style.loading_dialog);
												v = LayoutInflater
														.from(ChouJiang.this)
														.inflate(
																R.layout.dialog,
																null);// 窗口布局
												d.setContentView(v);// 把设定好的窗口布局放到dialog中
												d.setCanceledOnTouchOutside(true);// 设定点击窗口空白处取消会话
												p1 = (Button) v
														.findViewById(R.id.p);
												n = (Button) v
														.findViewById(R.id.n);
												juTiXinXi = (TextView) v
														.findViewById(R.id.banben_xinxi);
												tiShi = (TextView) v
														.findViewById(R.id.banben_gengxin);
												juTiXinXi.setText(results);
												tiShi.setText("消息");
												p1.setText("确定");
												n.setText("返回");
												p1.setOnClickListener(new OnClickListener() {
													@Override
													public void onClick(
															View arg0) {
														// TODO Auto-generated
														// method stub
														d.dismiss();
													}
												});
												n.setOnClickListener(new OnClickListener() {
													@Override
													public void onClick(
															View arg0) {
														// TODO Auto-generated
														// method stub
														// System.out.println(a);
														d.dismiss();
													}
												});
												d.show();
											}

										}
									} else {
										start.setVisibility(View.GONE);
										btn_stop.setVisibility(View.VISIBLE);
										handler.postDelayed(runnable, 100);
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

	private Handler handler = new Handler();
	private Runnable runnable = new Runnable() {
		@Override
		public void run() {
			int n1 = (int) (Math.random() * l.size() - 1);
			int n2 = (int) (Math.random() * l.size() - 1);
			int n3 = (int) (Math.random() * l.size() - 1);
			int n8 = (int) (Math.random() * l.size() - 1);
			int n9 = (int) (Math.random() * l.size() - 1);
			int n10 = (int) (Math.random() * l.size() - 1);
			int n11 = (int) (Math.random() * l.size() - 1);
			num1.setText("" + n1);
			num2.setText("" + n2);
			num3.setText("" + n3);
			num8.setText("" + n8);
			num9.setText("" + n9);
			num10.setText("" + n10);
			num11.setText("" + n11);
			handler.postDelayed(this, 100);
		}
	};
	private TextView juTiXinXi, tiShi;
	private Dialog d;
	private Button p1;
	private Button n;
	private View v;

	private void stop() {
		String UserTel = (String) LocalStorage.get("UserTel");
		RequestParams params = new RequestParams();
		params.put("DrowID", id);
		params.put("CreatorTel", UserTel);
		params.put("CustPhyAddr", strUniqueId);
		SmartFruitsRestClient.post("HandlerDraw.ashx?Action=Stop", params,
				new AsyncHttpResponseHandler() {

					@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
							Throwable arg3) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
						String result = new String(arg2);
						JSONObject json;
						try {
							json = new JSONObject(result.trim());
							final String str = json.getString("结果");
							System.out.println(str);

							if (str != null) {
								if (ChouJiang.this.isFinishing()) {
								} else {
									d = new Dialog(ChouJiang.this,
											R.style.loading_dialog);
									v = LayoutInflater.from(ChouJiang.this)
											.inflate(R.layout.dialog, null);// 窗口布局
									d.setContentView(v);// 把设定好的窗口布局放到dialog中
									d.setCanceledOnTouchOutside(true);// 设定点击窗口空白处取消会话
									p1 = (Button) v.findViewById(R.id.p);
									n = (Button) v.findViewById(R.id.n);
									juTiXinXi = (TextView) v
											.findViewById(R.id.banben_xinxi);
									tiShi = (TextView) v
											.findViewById(R.id.banben_gengxin);
								
									juTiXinXi.setText(str);
									tiShi.setText("消息");
									p1.setText("确定");
									n.setText("返回");
									p1.setOnClickListener(new OnClickListener() {

										@Override
										public void onClick(View arg0) {
											// TODO Auto-generated method stub

											if (str.contains("结束")) {

												ChouJiang.this.finish();
											}

											d.dismiss();
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
							// TODO Auto-generated catch block
							e.printStackTrace();
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

				ChouJiang.this.finish();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 1 && resultCode == 1) {
			id = data.getStringExtra("id");
		}
	}

}
