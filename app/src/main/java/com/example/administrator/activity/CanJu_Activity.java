package com.example.administrator.activity;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import android.content.Context;
import android.os.Bundle;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.content.Intent;
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
import android.widget.Toast;

import com.example.administrator.entity.AnLi;
import com.example.administrator.fragment.CanJuFragment;
import com.example.administrator.myapplication.R;
import com.example.administrator.net.XUtilsHelper;
import com.example.administrator.utils.ACache;
import com.example.administrator.utils.ActivityCollector;
import com.example.administrator.utils.Diolg;
import com.example.administrator.utils.IvListener;
import com.example.administrator.utils.LocalStorage;
import com.example.administrator.utils.LoginCheckAlertDialogUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.view.annotation.ViewInject;

import rx.Observable;
import rx.Subscriber;

public class CanJu_Activity extends FragmentActivity implements
		OnPageChangeListener, OnClickListener {
	private Button wedding_back;
	private Button	san;
	private Button si;
	private Button wu;
	private Button tv;
	private int jiaGe3 = 0, jiaGe4 = 0, jiaGe5 = 0;
	private TextView t1;
	private TextView t2;
	private TextView t3;
	private Button b1;
	private TextView tv_title;
	private List<Integer> li;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_can_ju_);
		ActivityCollector.addActivity(this);
		LocalStorage.initContext(this);
		mCache = ACache.get(this);
		li = new ArrayList<Integer>();

		t3 = (TextView) findViewById(R.id.woyaoyuyue_meiyong);
		t3.setText("元/桌");
		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_title.setText("餐具套餐");
		t1 = (TextView) findViewById(R.id.tongyong_jiage);
		t1.setText("租赁费");
		t2 = (TextView) findViewById(R.id.unit_price);

		b1 = (Button)findViewById(R.id.jiarudingdan);
		b1.setText("加入订单");
		b1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (!new LoginCheckAlertDialogUtils(CanJu_Activity.this)
						.showDialog()) {
					shangChuan();
				}

			}
		});



		san = (Button) findViewById(R.id.sanxingji);
		si = (Button) findViewById(R.id.sixingji);
		wu = (Button) findViewById(R.id.wuxingji);
		pagers = (ViewPager) findViewById(R.id.vp);
		wedding_back = (Button) findViewById(R.id.btn_back);
		wedding_back.setOnClickListener(this);
		tabs = (LinearLayout) findViewById(R.id.tabs);

		pagers.setOnPageChangeListener(this);
		if (null != mCache.getAsString("餐具套餐")) {
			String result = mCache.getAsString("餐具套餐");
			tryc(result);
		} else {
			System.out.println(147852);
			getMsg();
		}
		setMoreListener();
	}

	private ImageView iv_more;

	private void setMoreListener() {
		iv_more = (ImageView) findViewById(R.id.iv_more);
		iv_more.setOnClickListener(new IvListener(iv_more, CanJu_Activity.this,
				0));
	}

	private boolean isFalse = true;
	private int ciShu = 0;

	private void shangChuan() {
		String tel = (String) LocalStorage.get("UserTel").toString();


		XUtilsHelper xUtilsHelper1 = new XUtilsHelper(this,"weddingHandler.ashx?Action=GetCase",1);
		RequestParams requestParams = new RequestParams();
		requestParams.addBodyParameter("StarLevel", String.valueOf(level));
		requestParams.addBodyParameter("fkCusTel", tel);
		requestParams.addBodyParameter("CustPhyAddr", HomeActivity.strUniqueId);

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

				if (ciShu >= 3) {
							isFalse = false;

					toast(CanJu_Activity.this,getString(R.string.conn_failed) + e);

						}
						;

						if (isFalse) {
							shangChuan();
							ciShu++;
						}
			}

			@Override
			public void onNext(String s) {
				try {
							JSONObject json = new JSONObject(s.trim());
							// Toast.makeText(CanJu_Activity.this, result,
							// Toast.LENGTH_LONG).show();
							if ("你还没有订单".equals(json.getString("提示"))) {
								Intent i = new Intent(CanJu_Activity.this,
										ReserveActivity.class);
								i.putExtra("jiaRu", 21);
								startActivity(i);
							} else if ("添加成功".equals(json.getString("提示"))) {
								new Diolg(CanJu_Activity.this, "确定", "null",
										"添加成功", "提示", 0);
							} else if ("添加失败".equals(json.getString("提示"))) {
								new Diolg(CanJu_Activity.this, "确定", "null",
										"添加失败", "提示", 0);
							} else if ("意外错误".equals(json.getString("提示"))) {
								new Diolg(CanJu_Activity.this, "确定", "null",
										"意外错误", "提示", 0);
							} else if ("你有一笔要付钱的订单".equals(json.getString("提示"))) {
								new Diolg(CanJu_Activity.this, "确定", "null",
										"你有一笔要付钱的订单", "提示", 0);
							} else if ("替换成功".equals(json.getString("提示"))) {
								new Diolg(CanJu_Activity.this, "确定", "null",
										"替换成功", "提示", 0);
							} else if ("替换失败".equals(json.getString("提示"))) {
								new Diolg(CanJu_Activity.this, "确定", "null",
										"替换失败", "提示", 0);
							} else if ("绝不可能出现在这里".equals(json.getString("提示"))) {
								new Diolg(CanJu_Activity.this, "确定", "返回",
										"绝不可能出现在这里", "提示", 0);
							} else if ("你的账户已在另一台手机登录".equals(json
									.getString("提示"))) {
								new Diolg(CanJu_Activity.this, "确定", "返回",
										"你的账户已在另一台手机登录", "提示", 0);
							} else {
								Intent in = new Intent(CanJu_Activity.this,
										MainActivity.class);
								in.putExtra("dengRu", 45);
								startActivity(in);
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

			}
		});



//		RequestParams p = new RequestParams();
//		p.put("StarLevel", level);
//		p.put("fkCusTel", tel);
//
//		p.put("CustPhyAddr", HomeActivity.strUniqueId);
//		SmartFruitsRestClient.get(
//				"BookHandler.ashx?Action=AddIntoOrder&Type=Type_TableWare", p,
//				new AsyncHttpResponseHandler() {
//
//					@Override
//					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
//						// TODO Auto-generated method stub
//						String result = new String(arg2);
//						System.out.println(result);
//						try {
//							JSONObject json = new JSONObject(result.trim());
//							// Toast.makeText(CanJu_Activity.this, result,
//							// Toast.LENGTH_LONG).show();
//							if ("你还没有订单".equals(json.getString("提示"))) {
//								Intent i = new Intent(CanJu_Activity.this,
//										ReserveActivity.class);
//								i.putExtra("jiaRu", 21);
//								startActivity(i);
//							} else if ("添加成功".equals(json.getString("提示"))) {
//								new Diolg(CanJu_Activity.this, "确定", "null",
//										"添加成功", "提示", 0);
//							} else if ("添加失败".equals(json.getString("提示"))) {
//								new Diolg(CanJu_Activity.this, "确定", "null",
//										"添加失败", "提示", 0);
//							} else if ("意外错误".equals(json.getString("提示"))) {
//								new Diolg(CanJu_Activity.this, "确定", "null",
//										"意外错误", "提示", 0);
//							} else if ("你有一笔要付钱的订单".equals(json.getString("提示"))) {
//								new Diolg(CanJu_Activity.this, "确定", "null",
//										"你有一笔要付钱的订单", "提示", 0);
//							} else if ("替换成功".equals(json.getString("提示"))) {
//								new Diolg(CanJu_Activity.this, "确定", "null",
//										"替换成功", "提示", 0);
//							} else if ("替换失败".equals(json.getString("提示"))) {
//								new Diolg(CanJu_Activity.this, "确定", "null",
//										"替换失败", "提示", 0);
//							} else if ("绝不可能出现在这里".equals(json.getString("提示"))) {
//								new Diolg(CanJu_Activity.this, "确定", "返回",
//										"绝不可能出现在这里", "提示", 0);
//							} else if ("你的账户已在另一台手机登录".equals(json
//									.getString("提示"))) {
//								new Diolg(CanJu_Activity.this, "确定", "返回",
//										"你的账户已在另一台手机登录", "提示", 0);
//							} else {
//								Intent in = new Intent(CanJu_Activity.this,
//										MainActivity.class);
//								in.putExtra("dengRu", 45);
//								startActivity(in);
//							}
//						} catch (JSONException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//
//					}
//
//					@Override
//					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
//							Throwable arg3) {
//						// TODO Auto-generated method stub
//						System.out.println(arg3 + "asdfgdhgsa");
//						if (ciShu >= 3) {
//							isFalse = false;
//							Toast.makeText(CanJu_Activity.this,
//									getString(R.string.conn_failed) + arg3,
//									Toast.LENGTH_SHORT).show();
//						}
//						;
//
//						if (isFalse) {
//							shangChuan();
//							ciShu++;
//						}
//
//					}
//				});
	}


	protected void toast(Context context , String info){
		Toast.makeText(context,info,Toast.LENGTH_SHORT).show();
	}

	private int level = 3;

	private void getMsg() {

		XUtilsHelper xUtilsHelper1 = new XUtilsHelper(this,"WaresHandle.ashx?Action=GetDetail",1);
		RequestParams requestParams = new RequestParams();


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
				getMsg();
				if (ciShu >= 3) {
							isFalse = false;
							Toast.makeText(CanJu_Activity.this,
									getString(R.string.conn_failed) + e,
									Toast.LENGTH_SHORT).show();
						}
						;

						if (isFalse) {
							getMsg();
							ciShu++;
						}
				li.add(jiaGe3);
				li.add(jiaGe4);
				li.add(jiaGe5);
			}

			@Override
			public void onNext(String s) {
				isFalse = true;
				ciShu = 0;
				tryc(s);

			}
		});








//		RequestParams p = new RequestParams();
//		SmartFruitsRestClient.get("WaresHandle.ashx?Action=GetDetail", p,
//				new AsyncHttpResponseHandler() {
//
//					@Override
//					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
//							Throwable arg3) {
//						// TODO Auto-generated method stub
//						if (ciShu >= 3) {
//							isFalse = false;
//							Toast.makeText(CanJu_Activity.this,
//									getString(R.string.conn_failed) + arg3,
//									Toast.LENGTH_SHORT).show();
//						}
//						;
//
//						if (isFalse) {
//							getMsg();
//							ciShu++;
//						}
//						System.out.println(arg3 + "!@U*JXIE*&DU");
//						li.add(jiaGe3);
//						li.add(jiaGe4);
//						li.add(jiaGe5);
//					}
//
//					@Override
//					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
//						// TODO Auto-generated method stub
//						isFalse = true;
//						ciShu = 0;
//						String result = new String(arg2);
//						System.out.println("返回的json数据：" + result);
//						tryc(result);
//
//					}
//
//				});
	}

	private ACache mCache;
	private JSONObject js;
	private JSONObject tJson = null;
	private List<AnLi> list3, list4, list5;
	private AnLi al;

	private void tryc(String result) {
		System.out.println("$%$$$$$$$$$$$$$$$$" + result);
		list3 = new ArrayList<AnLi>();
		list4 = new ArrayList<AnLi>();
		list5 = new ArrayList<AnLi>();
		try {
			js = new JSONObject(result.trim());
			JSONArray arry = js.getJSONArray("餐具详情");
			System.out.println(arry.length() + "!!!!!!!");
			if (arry.length() >= 1) {
				System.out.println("YYYYYYYYYYYYYYYYYY");
				mCache.put("餐具套餐", result, 60 * 10);
			}
			System.out.println("" + mCache.getAsString("餐具详情"));
			for (int i = 0; i < arry.length(); i++) {
				tJson = arry.getJSONObject(i);
				al = new AnLi();
				al.setName(tJson.getString("Name"));
				al.setDetail(tJson.getString("StarLevel"));
				al.setFeastAdr(tJson.getString("Price"));
				al.setImgUrl(tJson.getString("ImageUrl"));
				if (al.getDetail().equals("3")) {
					jiaGe3 += Integer.parseInt(al.getFeastAdr());
					list3.add(al);
				} else if (al.getDetail().equals("4")) {
					jiaGe4 += Integer.parseInt(al.getFeastAdr());
					list4.add(al);
				} else if (al.getDetail().equals("5")) {
					jiaGe5 += Integer.parseInt(al.getFeastAdr());
					list5.add(al);
				}
			}
			san.setOnClickListener(this);
			si.setOnClickListener(this);
			wu.setOnClickListener(this);
			System.out.println(jiaGe3 + "++++++++++++++");
			System.out.println(jiaGe4 + "++++++++++++++++");
			System.out.println(jiaGe5 + "+++++++++++++");
			li.add(jiaGe3);
			li.add(jiaGe4);
			li.add(jiaGe5);
			t2.setText(li.get(0) + "");
			fragments = new ArrayList<CanJuFragment>();
			fragments.add(new CanJuFragment(list3));
			fragments.add(new CanJuFragment(list4));
			fragments.add(new CanJuFragment(list5));
			pagers.setAdapter(new MyAdapter(getSupportFragmentManager()));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (System.currentTimeMillis() - HomeActivity.dianJiShiJian > 500) {
				HomeActivity.dianJiShiJian = System.currentTimeMillis();
				CanJu_Activity.this.finish();
				ActivityCollector.finishAll();

			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	private ViewPager pagers;
	private int index = 0, before = 0;

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.btn_back:
			if (System.currentTimeMillis() - HomeActivity.dianJiShiJian > 500) {
				HomeActivity.dianJiShiJian = System.currentTimeMillis();
				ActivityCollector.finishAll();
				CanJu_Activity.this.finish();
			}

			break;
		case R.id.sanxingji:
			index = 0;
			t2.setText(li.get(0) + "");
			break;
		case R.id.sixingji:
			index = 1;
			t2.setText(li.get(1) + "");
			break;
		case R.id.wuxingji:
			index = 2;
			t2.setText(li.get(2) + "");
			break;
		default:
			break;
		}
		pagers.setCurrentItem(index);// 设置ViewPager的点击事件
	}

	private List<CanJuFragment> fragments;// 适配器， Fragment配合viewPager

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
		t2.setText(li.get(arg0) + "");
		level = arg0 + 3;
	}

}
