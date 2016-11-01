package com.example.administrator.activity;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.administrator.myapplication.R;
import com.example.administrator.net.XUtilsHelper;
import com.example.administrator.utils.ACache;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.view.annotation.ViewInject;


import android.annotation.SuppressLint;
import android.app.ListActivity;
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
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import android.widget.TextView;
import android.widget.Toast;

import rx.Observable;
import rx.Subscriber;

public class winner extends ListActivity {

	private TextView winners;

	private Button button;
//	private	List<String> l_name ;
	private	List<String> l_tel_one ;
	private	List<String> l_tel_two ;
	private	List<String> l_tel_three ;

	private	int flag = -1;
	private	String id = "";
	private ACache mCache;

	private Button button_suaxin;
	private LinearLayout zhongjiangll;
	private ImageView wodezi;
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.zhongjiang);
		/*-----------------------设置背景--------------------------------*/
		zhongjiangll=(LinearLayout) findViewById(R.id.zhongjiangll);
		BitmapDrawable b = setImage(winner.this, R.mipmap.ppp);
		zhongjiangll.setBackground(b);
		wodezi=(ImageView) findViewById(R.id.wodezi);
		BitmapDrawable c = setImage(winner.this, R.mipmap.wodezi);
		wodezi.setBackground(c);
		/*-----------------------设置背景--------------------------------*/
//		 l_name =new ArrayList<String>();
		 l_tel_one = new ArrayList<String>();
		 l_tel_two = new ArrayList<String>();
		 l_tel_three = new ArrayList<String>();



		

		Intent intent = getIntent();
		flag = intent.getIntExtra("flag", -1);
		mCache = ACache.get(this);
		button_suaxin= (Button) findViewById(R.id.button_suaxin);
		if (flag == 2) {

			id = intent.getStringExtra("id");
			button_suaxin.setVisibility(View.GONE);
		} else {

			if (null != mCache.getAsString("抽奖验证码")) {
				id = mCache.getAsString("抽奖验证码");
				System.out.println(id + "哈哈");
			}

		}
		winners= (TextView) findViewById(R.id.Winners);
		button= (Button) findViewById(R.id.button_back);
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (flag == 1) {
					winner.this.finish();
				} else {
					Intent intent = new Intent();
					intent.putExtra("id", id);
					setResult(1, intent);
					winner.this.finish();
				}
			}
		});

		init();
		button_suaxin= (Button) findViewById(R.id.button_suaxin);
		button_suaxin.setOnClickListener(
				new OnClickEvent(1000) {
					@Override
					public void singleClick(View v) {
						init();
						l_tel_one.clear();
						l_tel_two.clear();
						l_tel_three.clear();

					}
				});

	}

	private	ArrayAdapter<String> a = null;
	private	ArrayAdapter<String> b = null;
	private	ArrayAdapter<String> c = null;

	private void init() {



		XUtilsHelper xUtilsHelper1 = new XUtilsHelper(this,"HandlerDraw.ashx?Action=GetAll",1);
		RequestParams requestParams = new RequestParams();
		requestParams.addBodyParameter("DrowID", id);
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
				JSONObject tJson;
						try {
							JSONObject json = new JSONObject(s.trim());
							JSONArray win_tel = json.getJSONArray("结果");
							System.out.println(win_tel);

							for (int i = 0; i < win_tel.length(); i++) {
								tJson = win_tel.getJSONObject(i);
								String name = tJson.getString("NameS");
								String wintel = tJson.getString("WinnerTel");

								if (name.equals("一等奖")) {
									if (flag == 2) {
										l_tel_one.add(wintel);
									} else {
										l_tel_one.add(wintel.substring(0, 3)
												+ "****"
												+ wintel.substring(7, 11));
									}

								} else if (name.equals("二等奖")) {
									if (flag == 2) {
										l_tel_two.add(wintel);

									} else {
										l_tel_two.add(wintel.substring(0, 3)
												+ "****"
												+ wintel.substring(7, 11));
									}

								} else if (name.equals("三等奖")) {
									if (flag == 2) {
										l_tel_three.add(wintel);
									} else {
										l_tel_three.add(wintel.substring(0, 3)
												+ "****"
												+ wintel.substring(7, 11));
									}
								}

								if (flag == 2) {
									winners.setText(name + ":" + wintel);
								} else {
									winners.setText(name + ":"
											+ wintel.substring(0, 3) + "****"
											+ wintel.substring(7, 11));
								}

							}

							if (l_tel_one.size() != 0) {

								System.out.println(1);
								if (null == a) {
									a = new ArrayAdapter<String>(winner.this,
											R.layout.wins_item, l_tel_one);
									adapter.addSection("一等奖   ", a);

								} else {

									a.notifyDataSetChanged();
								}

							} else {
								l_tel_one.add("  ");
								if (null == a) {
									a = new ArrayAdapter<String>(winner.this,
											R.layout.wins_item, l_tel_one);
									System.out.println(5);
									adapter.addSection("一等奖   ", a);
								} else {

									a.notifyDataSetChanged();
								}

							}

							if (l_tel_two.size() != 0) {

								if (null == b) {
									b = new ArrayAdapter<String>(winner.this,
											R.layout.wins_item, l_tel_two);
									System.out.println(5);
									adapter.addSection("二等奖   ", b);
								} else {

									b.notifyDataSetChanged();
								}

							} else {
								l_tel_two.add("  ");
								if (null == b) {
									b = new ArrayAdapter<String>(winner.this,
											R.layout.wins_item, l_tel_two);
									System.out.println(5);
									adapter.addSection("二等奖   ", b);
								} else {

									b.notifyDataSetChanged();
								}

							}

							if (l_tel_three.size() != 0) {
								if (null == c) {
									c = new ArrayAdapter<String>(winner.this,
											R.layout.wins_item, l_tel_three);
									System.out.println(5);
									adapter.addSection("三等奖   ", c);
								} else {

									c.notifyDataSetChanged();
								}

							} else {
								l_tel_three.add("  ");
								if (null == c) {
									c = new ArrayAdapter<String>(winner.this,
											R.layout.wins_item, l_tel_three);
									System.out.println(5);
									adapter.addSection("三等奖   ", c);
								} else {
									System.out.println(123);
									c.notifyDataSetChanged();
								}

							}
							setListAdapter(adapter);

						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							System.out.println("S" + e.getMessage());
						}
			}
		});




















//		RequestParams params = new RequestParams();
//		params.put("DrowID", id);
//		SmartFruitsRestClient.post("HandlerDraw.ashx?Action=GetAll", params,
//				new AsyncHttpResponseHandler() {
//
//					@Override
//					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
//							Throwable arg3) {
//						// TODO Auto-generated method stub
//					}
//
//					@Override
//					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
//						String result = new String(arg2);
//						System.out.println(result);
//
//						JSONObject tJson;
//						try {
//							JSONObject json = new JSONObject(result.trim());
//							JSONArray win_tel = json.getJSONArray("结果");
//							System.out.println(win_tel);
//
//							for (int i = 0; i < win_tel.length(); i++) {
//								tJson = win_tel.getJSONObject(i);
//								String name = tJson.getString("NameS");
//								String wintel = tJson.getString("WinnerTel");
//
//								if (name.equals("一等奖")) {
//									if (flag == 2) {
//										l_tel_one.add(wintel);
//									} else {
//										l_tel_one.add(wintel.substring(0, 3)
//												+ "****"
//												+ wintel.substring(7, 11));
//									}
//
//								} else if (name.equals("二等奖")) {
//									if (flag == 2) {
//										l_tel_two.add(wintel);
//
//									} else {
//										l_tel_two.add(wintel.substring(0, 3)
//												+ "****"
//												+ wintel.substring(7, 11));
//									}
//
//								} else if (name.equals("三等奖")) {
//									if (flag == 2) {
//										l_tel_three.add(wintel);
//									} else {
//										l_tel_three.add(wintel.substring(0, 3)
//												+ "****"
//												+ wintel.substring(7, 11));
//									}
//								}
//
//								if (flag == 2) {
//									winners.setText(name + ":" + wintel);
//								} else {
//									winners.setText(name + ":"
//											+ wintel.substring(0, 3) + "****"
//											+ wintel.substring(7, 11));
//								}
//
//							}
//
//							if (l_tel_one.size() != 0) {
//
//								System.out.println(1);
//								if (null == a) {
//									a = new ArrayAdapter<String>(winner.this,
//											R.layout.wins_item, l_tel_one);
//									adapter.addSection("一等奖   ", a);
//
//								} else {
//
//									a.notifyDataSetChanged();
//								}
//
//							} else {
//								l_tel_one.add("  ");
//								if (null == a) {
//									a = new ArrayAdapter<String>(winner.this,
//											R.layout.wins_item, l_tel_one);
//									System.out.println(5);
//									adapter.addSection("一等奖   ", a);
//								} else {
//
//									a.notifyDataSetChanged();
//								}
//							}
//							if (l_tel_two.size() != 0) {
//
//								if (null == b) {
//									b = new ArrayAdapter<String>(winner.this,
//											R.layout.wins_item, l_tel_two);
//									System.out.println(5);
//									adapter.addSection("二等奖   ", b);
//								} else {
//
//									b.notifyDataSetChanged();
//								}
//							} else {
//								l_tel_two.add("  ");
//								if (null == b) {
//									b = new ArrayAdapter<String>(winner.this,
//											R.layout.wins_item, l_tel_two);
//									System.out.println(5);
//									adapter.addSection("二等奖   ", b);
//								} else {
//
//									b.notifyDataSetChanged();
//								}
//							}
//							if (l_tel_three.size() != 0) {
//								if (null == c) {
//									c = new ArrayAdapter<String>(winner.this,
//											R.layout.wins_item, l_tel_three);
//									System.out.println(5);
//									adapter.addSection("三等奖   ", c);
//								} else {
//
//									c.notifyDataSetChanged();
//								}
//							} else {
//								l_tel_three.add("  ");
//								if (null == c) {
//									c = new ArrayAdapter<String>(winner.this,
//											R.layout.wins_item, l_tel_three);
//									System.out.println(5);
//									adapter.addSection("三等奖   ", c);
//								} else {
//									System.out.println(123);
//									c.notifyDataSetChanged();
//								}
//							}
//							setListAdapter(adapter);
//						} catch (JSONException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//							System.out.println("S" + e.getMessage());
//						}
//					};
//				});
	}

	SectionedAdapter adapter = new SectionedAdapter() {
		protected View getHeaderView(String caption, int index,
				View convertView, ViewGroup parent) {
			TextView result = (TextView) convertView;

			if (convertView == null) {
				result = (TextView) getLayoutInflater().inflate(
						R.layout.header, null);
			}

			result.setText(caption);

			return (result);
		}
	};
	
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
				if (flag == 1) {
					winner.this.finish();
				} else {
					Intent intent = new Intent();
					intent.putExtra("id", id);
					setResult(1, intent);
					winner.this.finish();
				}
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
