package com.example.administrator.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.activity.CanJu_Activity;
import com.example.administrator.activity.CaseActivity;
import com.example.administrator.activity.ChouJiangShouYe;
import com.example.administrator.activity.CookActivity;
import com.example.administrator.activity.HomeActivity;
import com.example.administrator.activity.HunCheActivity;
import com.example.administrator.activity.HunQing;
import com.example.administrator.activity.JiaMengActivity;
import com.example.administrator.activity.ReserveActivity;
import com.example.administrator.activity.WeddinghallActivity;
import com.example.administrator.activity.XiYan;
import com.example.administrator.myapplication.R;
import com.example.administrator.utils.ElasticScrollView;
import com.example.administrator.utils.LoginCheckAlertDialogUtils;
import com.example.administrator.utils.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class FragmentHome extends Fragment {
	// 首页轮播
	private ViewPager viewPager;
	/** 存储首页轮播的界面 */
	private ArrayList<ImageView> ali;
	/** 首页轮播的界面的资源 */
	private int[] resId = {R.mipmap.shouye6, R.mipmap.shouye1,
			R.mipmap.shouye2, R.mipmap.shouye3, R.mipmap.shouye4,
			R.mipmap.shouye5, R.mipmap.shouye6, R.mipmap.shouye1 };
	// 底部小店图片
	private ImageView[] tips;
	private View view;
	public static int width;
	public static int height;
	private ImageView iv1, iv2, iv3, iv4, iv5, iv6, iv7, iv8, iv9, iv10;
	private List<String> ls;
	private int index1 = 1;
	private Handler handler1 = new Handler();
	private Runnable runnable = new Runnable() {
		@Override
		public void run() {
			if (index1 != ls.size() / 2) {
				index1 += 1;
				lv.smoothScrollToPositionFromTop(index1, 0);

			} else {
				index1 = 1;
				lv.setSelection(index1);
			}
			handler1.postDelayed(runnable, 2000);
		}
	};
	private ListView lv;
	private LayoutParams mParams;
	private MyAdapter m;
	private boolean isF = true;

//	@Override
//	public void onHiddenChanged(boolean hidden) {
//		// TODO Auto-generated method stub
//		super.onHiddenChanged(hidden);
//		if (hidden) {// 隐藏
//			handler1.removeCallbacks(runnable);
//		} else {// 显示
//			lv.setSelection(index1);
//			handler1.removeCallbacks(runnable);
//			handler1.postDelayed(runnable, 2000);
//		}
//	}

	private class MyAdapter extends BaseAdapter {
		LayoutInflater li;

		MyAdapter() {
			li = LayoutInflater.from(HomeActivity._instance);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return ls.size() / 2;
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			if (arg1 == null) {
				arg1 = li.inflate(R.layout.item_shouye_toutiao, null);
				LinearLayout ll = (LinearLayout) arg1
						.findViewById(R.id.shouye_xinxi_lv);
				LinearLayout.LayoutParams pa = (LinearLayout.LayoutParams) ll
						.getLayoutParams();
				pa.height = (int) (height * 0.1);
				ll.setLayoutParams(pa);
				v = new ViewHolder();
				v.t1 = (TextView) arg1.findViewById(R.id.shouye_xibao_t);
				v.t2 = (TextView) arg1.findViewById(R.id.shouye_dingdan_t);
				arg1.setTag(v);
			} else {
				v = (ViewHolder) arg1.getTag();
			}
			v.t1.setText(ls.get(arg0 * 2));
			v.t2.setText(ls.get(arg0 * 2 + 1));
			return arg1;
		}
	}

	private ViewHolder v = null;
	ElasticScrollView elasticScrollView;

	private class ViewHolder {
		TextView t1, t2;
	}

//	@Override
//	public void onResume() {
//		// TODO Auto-generated method stub
//		super.onResume();
//		if (isF) {
//
//		} else {
//			lv.setSelection(index1);
//			handler1.removeCallbacks(runnable);
//			handler1.postDelayed(runnable, 2000);
//		}
//	}
//
//	@Override
//	public void onStop() {
//		// TODO Auto-generated method stub
//		super.onStop();
//		handler1.removeCallbacks(runnable);
//	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		view = LayoutInflater.from(getActivity()).inflate(
				R.layout.sss, null);
		elasticScrollView = (ElasticScrollView) view
				.findViewById(R.id.elasticScrollView1);
		View ll = LayoutInflater.from(getActivity()).inflate(
				R.layout.item_shouye_haoduo, null);
		elasticScrollView.addChild(ll, 1);
		System.out.println("ghyujik");
		ImageView button = (ImageView) ll.findViewById(R.id.dianjidianhuass);// 拨打电话
		ImageView buttons = (ImageView) ll
				.findViewById(R.id.dianjishoujidianhua);// 拨打电话
		width = HomeActivity._instance.getResources().getDisplayMetrics().widthPixels;
		height = HomeActivity._instance.getResources().getDisplayMetrics().heightPixels;
		lv = (ListView) ll.findViewById(R.id.shouye_lv);
		LinearLayout.LayoutParams p = (LinearLayout.LayoutParams) lv
				.getLayoutParams();
		System.out.println("U" + p.height);
		p.height = (int) (height * 0.1);
		lv.setLayoutParams(p);
		// // // System.out.println(width);
		iv1 = (ImageView) ll.findViewById(R.id.shouye_tu1);
		mParams = iv1.getLayoutParams();
		mParams.width = (width) / 7;
		mParams.height = (width) / 5;
		// mParams.height = (int) (height * 0.5);
		iv1.setLayoutParams(mParams);
		iv2 = (ImageView) ll.findViewById(R.id.shouye_tu2);
		iv3 = (ImageView) ll.findViewById(R.id.shouye_tu3);
		iv4 = (ImageView) ll.findViewById(R.id.shouye_tu4);
		iv5 = (ImageView) ll.findViewById(R.id.shouye_tu5);
		iv2.setLayoutParams(mParams);
		iv3.setLayoutParams(mParams);
		iv4.setLayoutParams(mParams);
		iv5.setLayoutParams(mParams);
		iv6 = (ImageView) ll.findViewById(R.id.shouye_tu6);
		iv7 = (ImageView) ll.findViewById(R.id.shouye_tu7);
		iv8 = (ImageView) ll.findViewById(R.id.shouye_tu8);
		iv9 = (ImageView) ll.findViewById(R.id.shouye_tu9);
		iv10 = (ImageView) ll.findViewById(R.id.shouye_tu10);
		iv6.setLayoutParams(mParams);
		iv7.setLayoutParams(mParams);
		iv8.setLayoutParams(mParams);
		iv9.setLayoutParams(mParams);
		iv10.setLayoutParams(mParams);
		iv1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
//				if (!new LoginCheckAlertDialogUtils(HomeActivity._instance)
//						.showDialog()) {
					isF = false;
					Intent intentc = new Intent(HomeActivity._instance,
							ReserveActivity.class);
					startActivity(intentc);
				}

	//		}
		});
		iv2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				isF = false;
				Intent intent = new Intent(HomeActivity._instance, XiYan.class);
				startActivity(intent);

			}
		});
		iv3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				isF = false;
				Intent intenta = new Intent(HomeActivity._instance,
						CanJu_Activity.class);
				startActivity(intenta);

			}
		});
		iv4.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				isF = false;
				Intent intentb = new Intent(HomeActivity._instance,
						CookActivity.class);
				startActivity(intentb);

			}
		});
		iv5.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				isF = false;
//				if (!new LoginCheckAlertDialogUtils(HomeActivity._instance)
//						.showDialog()) {
					Intent intentg = new Intent(HomeActivity._instance,
							ChouJiangShouYe.class);
					startActivity(intentg);
	//			}
			}
		});
		iv6.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				handler1.removeCallbacks(runnable);
				isF = false;
				Intent intentd = new Intent(HomeActivity._instance,
						WeddinghallActivity.class);
				startActivity(intentd);

			}
		});
		iv7.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				isF = false;
				handler1.removeCallbacks(runnable);
				Intent intente = new Intent(HomeActivity._instance,
						HunQing.class);
				startActivity(intente);

			}
		});
		iv8.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				isF = false;
				Intent intentn = new Intent(HomeActivity._instance,
						HunCheActivity.class);
				startActivity(intentn);
				// 结束当前界面
			}
		});
		iv9.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				isF = false;
				Intent intenth = new Intent(HomeActivity._instance,
						CaseActivity.class);
				startActivity(intenth);

			}
		});
		iv10.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				isF = false;
				Intent intentx = new Intent(HomeActivity._instance,
						JiaMengActivity.class);
				startActivity(intentx);
				// 结束当前界面
			}
		});
		buttons.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
//				new Diolg(HomeActivity._instance, "确定", "取消", "是否拨打客服电话", "提示",
//						2);

			}
		});
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
//				new Diolg(HomeActivity._instance, "确定", "取消", "是否拨打客服电话", "提示",
//						3);

			}
		});
//		getMsg();// zhufuyu
		// // ren
		// // dan shu
		final Handler handler = new Handler() {
			public void handleMessage(Message message) {

//				getMsg();
			}
		};
		elasticScrollView.setonRefreshListener(new ElasticScrollView.OnRefreshListener() {

			@Override
			public void onRefresh() {
//				handler1.removeCallbacks(runnable);
//				Thread thread = new Thread(new Runnable() {
//
//					@Override
//					public void run() {
//						try {
//							Thread.sleep(2000);
//						} catch (InterruptedException e) {
//							e.printStackTrace();
//						}
//						Message message = handler.obtainMessage(0, "new Text");
//						handler.sendMessage(message);
//					}
//				});
//				thread.start();
			}
		});
		initView(view);
		return view;
	}

	protected void OnReceiveData() {
		elasticScrollView.onRefreshComplete();
	}

//	private void getMsg1() {
//		RequestParams reqParams = new RequestParams();
//		SmartFruitsRestClient.post(
//				"MainPageMsgHandler.ashx?Action=GetNearOrder", reqParams,
//				new AsyncHttpResponseHandler() {
//
//					@Override
//					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
//							Throwable arg3) {
//						// TODO Auto-generated method stub
//						System.out.println(arg3 + "asdfgdhgsa");
//						if (ciShu >= 3) {
//							isFalse = false;
//							Toast.makeText(HomeActivity._instance,
//									"网络连接失败，请稍候再试！", Toast.LENGTH_SHORT).show();
//						}
//						;
//						if (isFalse) {
//							getMsg1();
//							ciShu++;
//						}
//					}
//
//					@Override
//					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
//						// TODO Auto-generated method stub
//						String result = new String(arg2);
//						System.out.println("!#*@()DJ#()J" + result);
//
//						tryc1(result);
//						return;
//
//					}
//				});
//	}

//	private void getMsg2() {
//		RequestParams reqParams = new RequestParams();
//		SmartFruitsRestClient.post(
//				"MainPageMsgHandler.ashx?Action=GetAllAndFinishOrder",
//				reqParams, new AsyncHttpResponseHandler() {
//
//					@Override
//					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
//							Throwable arg3) {
//						// TODO Auto-generated method stub
//						System.out.println(arg3 + "asdfgdhgsa");
//						if (ciShu >= 3) {
//							isFalse = false;
//							Toast.makeText(HomeActivity._instance,
//									"网络连接失败，请稍候再试！", Toast.LENGTH_SHORT).show();
//						}
//						;
//						if (isFalse) {
//							getMsg2();
//							ciShu++;
//						}
//					}
//
//					@Override
//					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
//						// TODO Auto-generated method stub
//						String result = new String(arg2);
//						System.out.println("!#*@()DJ#()J" + result);
//
//						tryc2(result);
//						return;
//
//					}
//				});
//	}

//	private void getMsg() {
//		if (null == lal) {
//			lal = new ArrayList<AnLi>();
//		} else {
//			lal.clear();
//		}
//		if (null == lal1) {
//			lal1 = new ArrayList<AnLi>();
//		} else {
//			lal1.clear();
//		}
//		if (null == lal2) {
//			lal2 = new ArrayList<AnLi>();
//		} else {
//			lal2.clear();
//		}
//		if (null == ls) {
//			ls = new ArrayList<String>();
//		} else {
//			ls.clear();
//		}
//		RequestParams reqParams = new RequestParams();
//		SmartFruitsRestClient.post("MainPageMsgHandler.ashx?Action=GetMeg",
//				reqParams, new AsyncHttpResponseHandler() {
//
//					@Override
//					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
//							Throwable arg3) {
//						// TODO Auto-generated method stub
//						System.out.println(arg3 + "asdfgdhgsa");
//						if (ciShu >= 3) {
//							isFalse = false;
//							elasticScrollView.onRefreshComplete();
//							Toast.makeText(HomeActivity._instance,
//									"网络连接失败，请稍候再试！", Toast.LENGTH_SHORT).show();
//						}
//						;
//						if (isFalse) {
//							getMsg();
//							ciShu++;
//						}
//					}
//
//					@Override
//					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
//						// TODO Auto-generated method stub
//						String result = new String(arg2);
//						System.out.println("!#*@()DJ#()J" + result);
//
//						tryc(result);
//						return;
//
//					}
//				});
//	}

//	private AnLi anli;
//	private List<AnLi> lal;// zhu fu yu
//	private List<AnLi> lal1;// ren

//	private void tryc1(String result) {
//		try {
//			JSONArray json = new JSONArray(result.trim());
//			JSONObject tJson;
//			if (0 != json.length()) {
//				System.out.println(json.length());
//				for (int i = 0; i < json.length(); i++) {
//					tJson = json.getJSONObject(i);
//					anli = new AnLi();
//					anli.setImgUrl(tJson.getString("RealName"));
//					anli.setDetail(tJson.getString("Name"));
//					if (tJson.getString("Sex").trim().equals("0")) {
//						anli.setSex("女士");
//					} else {
//						anli.setSex("先生");
//					}
//					lal1.add(anli);
//				}
//			}
//
//			getMsg2();
//		} catch (JSONException e) {
//			// TODO Auto-generated catch block
//			System.out.println("G" + e.getMessage());
//			// Toast.makeText(getActivity(),
//			// e.getMessage(),
//			// Toast.LENGTH_SHORT).show();
//		}
//	}

//	private List<AnLi> lal2;// xia dan

//	private void tryc2(String result) {
//		try {
//			JSONArray json = new JSONArray(result.trim());
//			JSONObject tJson;
//			if (0 != json.length()) {
//				System.out.println(json.length());
//				for (int i = 0; i < json.length(); i++) {
//					tJson = json.getJSONObject(i);
//					anli = new AnLi();
//					anli.setImgUrl(tJson.getString("TotalCount"));
//					anli.setDetail(tJson.getString("FinishCount"));
//					lal2.add(anli);
//				}
//			}
//			for (int i = 0; i < lal1.size(); i++) {
//				System.out.println(lal1.get(i).getSex());
//			}
//			if (lal1.size() != 0) {
//				for (int i = 0; i < lal.size(); i++) {
//					System.out.println(lal1.size() + "%$#");
//					if (lal1.get((lal1.size() - 1)).getDetail()
//							.equals(lal.get(i).getImgUrl())) {
//						System.out.println(lal1.size() + "@#$%");
//						ls.add("恭喜" + lal1.get((lal1.size() - 1)).getImgUrl()
//								+ lal1.get(lal1.size() - 1).getSex()
//								+ "下单成功，祝福他们" + lal.get(i).getDetail());
//						ls.add("已接受" + lal2.get(0).getImgUrl() + "个喜宴订单，已完成"
//								+ lal2.get(0).getDetail() + "个订单");
//					}
//				}
//				for (int i = 0; i < lal1.size(); i++) {
//					for (int j = 0; j < lal.size(); j++) {
//						if (lal1.get(i).getDetail()
//								.equals(lal.get(j).getImgUrl())) {
//							ls.add("恭喜" + lal1.get(i).getImgUrl()
//									+ lal1.get(i).getSex() + "下单成功，祝福他们"
//									+ lal.get(j).getDetail());
//							ls.add("已接受" + lal2.get(0).getImgUrl()
//									+ "个喜宴订单，已完成" + lal2.get(0).getDetail()
//									+ "个订单");
//						}
//					}
//				}
//				for (int i = 0; i < lal.size(); i++) {
//					if (lal1.get(0).getDetail().equals(lal.get(i).getImgUrl())) {
//						ls.add("恭喜" + lal1.get(0).getImgUrl()
//								+ lal1.get(0).getSex() + "下单成功，祝福他们"
//								+ lal.get(i).getDetail());
//						ls.add("已接受" + lal2.get(0).getImgUrl() + "个喜宴订单，已完成"
//								+ lal2.get(0).getDetail() + "个订单");
//					}
//				}
//				if (ls.size() > 0) {
//					if (null == m) {
//						m = new MyAdapter();
//						lv.setAdapter(m);
//						lv.setSelection(1);
//						elasticScrollView.onRefreshComplete();
//						lv.setOnTouchListener(new OnTouchListener() {
//
//							@Override
//							public boolean onTouch(View v, MotionEvent event) {
//								// TODO Auto-generated method stub
//								return true;
//							}
//						});
//						handler1.postDelayed(runnable, 2000);
//					} else {
//						elasticScrollView.onRefreshComplete();
//						m.notifyDataSetChanged();
//						index1 = 1;
//						lv.setSelection(1);
//						handler1.removeCallbacks(runnable);
//						handler1.postDelayed(runnable, 2000);
//					}
//				} else {
//					elasticScrollView.onRefreshComplete();
//				}
//
//			}
//
//		} catch (JSONException e) {
//			// TODO Auto-generated catch block
//			System.out.println("G" + e.getMessage());
//			// 注释的地方
//			// Toast.makeText(getActivity(),
//			// e.getMessage(),
//			// Toast.LENGTH_SHORT).show();
//		}
//	}

//	private void tryc(String result) {
//		try {
//			JSONArray json = new JSONArray(result.trim());
//			JSONObject tJson;
//			if (0 != json.length()) {
//				System.out.println(json.length());
//				for (int i = 0; i < json.length(); i++) {
//					tJson = json.getJSONObject(i);
//					anli = new AnLi();
//					anli.setImgUrl(tJson.getString("Cgy"));
//					anli.setDetail(tJson.getString("Content"));
//					lal.add(anli);
//				}
//			}else{
//				elasticScrollView.onRefreshComplete();
//			}
//			getMsg1();
//		} catch (JSONException e) {
//			// TODO Auto-generated catch block
//			System.out.println("G" + e.getMessage());
//			// Toast.makeText(getActivity(),
//			// e.getMessage(),
//			// Toast.LENGTH_SHORT).show();
//		}
//	}

	private boolean isFalse = true;
	private int ciShu = 0;

	private void initView(View view) {
		viewPager = (ViewPager) view.findViewById(R.id.viewPager_menu);
		// 设置播放方式为顺序播放
		ali = new ArrayList<ImageView>();
		for (int i = 0; i < resId.length; i++) {
			iv = new ImageView(HomeActivity._instance);
			iv.setImageResource(resId[i]);
			iv.setScaleType(ScaleType.FIT_XY);
			ali.add(iv);
		}
		vpa = new ViewPagerAdapter(ali,
				HomeActivity._instance);
		viewPager.setAdapter(vpa);
		viewPager.setCurrentItem(1);
		startTask();
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				// System.out.println("!@DSEWDED");
				pageIndex = arg0;
			}

			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {
			}

			/* state: 0空闲，1是滑行中，2加载完毕 */
			@Override
			public void onPageScrollStateChanged(int state) {
				// TODO Auto-generated method stub
				// System.out.println("state:" + state);
				if (state == 0 && !isTaskRun) {
					setCurrentItem();
					startTask();
				} else if (state == 1 && isTaskRun)
					stopTask();
			}
		});
	}

	/**
	 * 停止定时任务
	 */
	private void stopTask() {
		// TODO Auto-generated method stub
		isTaskRun = false;
		mTimer.cancel();
	}

	/**
	 * 处理Page的切换逻辑
	 */
	private void setCurrentItem() {
		if (pageIndex == 0) {
			pageIndex = 6;
			viewPager.setCurrentItem(pageIndex, false);
		} else if (pageIndex == 7) {
			pageIndex = 1;
			viewPager.setCurrentItem(pageIndex, false);
		} else {

			viewPager.setCurrentItem(pageIndex, true);// 取消动画
		}
		setImageBackground(pageIndex - 1);
	}

	private Timer mTimer;
	private TimerTask mTask;
	private int pageIndex = 1;
	private boolean isTaskRun;
	private ViewPagerAdapter vpa;
	private ImageView iv;

	/**
	 * 开启定时任务
	 */
	private void startTask() {
		// TODO Auto-generated method stub
		isTaskRun = true;
		mTimer = new Timer();
		mTask = new TimerTask() {
			@Override
			public void run() {
				pageIndex++;
				mHandler.sendEmptyMessage(0);
			}
		};
		mTimer.schedule(mTask, 2 * 1000, 2 * 1000);// 这里设置自动切换的时间，单位是毫秒，2*1000表示2秒
	}

	// 处理EmptyMessage(0)
	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			setCurrentItem();
		}
	};

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		ViewGroup group = (ViewGroup) view.findViewById(R.id.viewGroup);
		tips = new ImageView[6];
		for (int i = 0; i < tips.length; i++) {
			ImageView imageView = new ImageView(HomeActivity._instance);
			imageView.setLayoutParams(new LayoutParams(20, 20));
			if (i == 0) {
				imageView.setBackgroundResource(R.mipmap.red_point);
			} else {
				imageView.setBackgroundResource(R.mipmap.dot_unselected);
			}
			tips[i] = imageView;

			LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
					new LayoutParams(LayoutParams.WRAP_CONTENT,
							LayoutParams.WRAP_CONTENT));
			layoutParams.leftMargin = 10;// 设置点点点view的左边距
			layoutParams.rightMargin = 10;// 设置点点点view的右边距
			group.addView(imageView, layoutParams);
		}

	}

	private void setImageBackground(int selectItems) {
		for (int i = 0; i < tips.length; i++) {
			if (i == selectItems) {
				tips[i].setBackgroundResource(R.mipmap.red_point);
			} else {
				tips[i].setBackgroundResource(R.mipmap.dot_unselected);
			}
		}
	}

}
