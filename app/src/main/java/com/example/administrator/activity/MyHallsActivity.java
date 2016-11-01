package com.example.administrator.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.ab.view.ToDoItem;
import com.example.administrator.fragment.CalendarFragment;
import com.example.administrator.list.Utils;
import com.example.administrator.myapplication.R;
import com.example.administrator.net.XUtilsHelper;
import com.example.administrator.utils.IvListener;
import com.example.administrator.utils.LoadingDialog;
import com.example.administrator.utils.LocalStorage;
import com.lidroid.xutils.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import rx.Observable;
import rx.Subscriber;

public class MyHallsActivity extends Activity implements OnClickListener,
		CalendarFragment.CallBackValue {

	public static void actionStart(Context context, String SchID,
			String orderName, String orderTel, String orderAddress,
			String dinner, String vice, String orderTime) {
		Intent intent = new Intent(context, MyHallsActivity.class);
		Bundle bundle = new Bundle();
		bundle.putString("SchID", SchID);
		bundle.putString("orderName", orderName);
		bundle.putString("orderTel", orderTel);
		bundle.putString("orderAddress", orderAddress);
		bundle.putString("dinner", dinner);
		bundle.putString("vice", vice);
		bundle.putString("orderTime", orderTime);
		intent.putExtras(bundle);
		context.startActivity(intent);
	}

	private Button dateBt, userInfo_back, yuding;
	private SimpleDateFormat sfDate;
	private ToDoItem toDoItema;
	private TextView halladdress, hallname ;
	private EditText minortable, maintable, bookname, booktel, bookaddress;
	private LoadingDialog dialog = null;
	private TextView tv;
private LinearLayout ll;
	private Bundle bun = null;
	private float dy, uy, zy;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.myhall);
		dateBt = (Button) findViewById(R.id.dateBt);
		userInfo_back = (Button) findViewById(R.id.btn_back);
		yuding = (Button) findViewById(R.id.yuding);
		tv = (TextView) findViewById(R.id.tv_title);
		tv.setText("新增档期");
		halladdress = (TextView) findViewById(R.id.halladdress);
		hallname = (TextView) findViewById(R.id.hallname);
//		addschedule = (TextView) findViewById(R.id.addschedule);
		ll = (LinearLayout) findViewById(R.id.wo_de_xi_shi_tang);
		ll.setOnTouchListener(new OnTouchListener() {

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
		minortable = (EditText) findViewById(R.id.minortable);
		maintable = (EditText) findViewById(R.id.maintable);
		bookname = (EditText) findViewById(R.id.bookname);
		booktel = (EditText) findViewById(R.id.booktel);
		bookaddress = (EditText) findViewById(R.id.bookaddress);
		bun = getIntent().getExtras();
		initHallsData();

		maintable.addTextChangedListener(watchermaintable);
		minortable.addTextChangedListener(watcherminortable);

		// 处理事件日期点击事件
		dateBt.setOnClickListener(this);
		yuding.setOnClickListener(this);
		userInfo_back.setOnClickListener(this);
		setMoreListener();
	}

	private ImageView iv_more;

	private void setMoreListener() {
		iv_more = (ImageView) findViewById(R.id.iv_more);
		iv_more.setOnClickListener(new IvListener(iv_more,
				MyHallsActivity.this, 0));
	}

	private TextWatcher watchermaintable = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {
			// TODO Auto-generated method stub
			if (maintable.getText().toString().trim().startsWith("0")) {
				Toast.makeText(getApplicationContext(), "主餐桌数的首位不能为0，请重新输入！",
						Toast.LENGTH_SHORT).show();
				maintable.setText("");
			}
		}

		@Override
		public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {
			// TODO Auto-generated method stub

		}

		@Override
		public void afterTextChanged(Editable arg0) {
			// TODO Auto-generated method stub

		}
	};

	private TextWatcher watcherminortable = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {
			// TODO Auto-generated method stub
			if (minortable.getText().toString().trim().startsWith("0")) {
				Toast.makeText(getApplicationContext(), "副餐桌数的首位不能为0，请重新输入！",
						Toast.LENGTH_SHORT).show();
				minortable.setText("");
			}
		}

		@Override
		public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {
			// TODO Auto-generated method stub

		}

		@Override
		public void afterTextChanged(Editable arg0) {
			// TODO Auto-generated method stub

		}
	};

	static String result, updateResult;
	private String hallID;
	private Date now;
	private int ciShu = 0;
	private boolean isFalse = true;

	private void initHallsData() {
		// TODO Auto-generated method stub

		now = new Date();
		sfDate = new SimpleDateFormat("yyyy/MM/dd", Locale.CHINA);
		Bundle extras = getIntent().getExtras();
		if (extras == null || extras.get("") == null) {
			// 创建task为空的ToDoItem对象
			toDoItema = new ToDoItem("");
			// 新建待办事项，将界面中事件时间设置为当前时间
			dateBt.setText(sfDate.format(now));
			toDoItema.setTime(now); // 设置ToDoItem事件时间
		}
		XUtilsHelper xUtilsHelper1 = new XUtilsHelper(this,"HallsHandler.ashx?Action=getHallUTel",1);
		RequestParams requestParams = new RequestParams();
		requestParams.addBodyParameter("UserTel", LocalStorage.get("UserTel").toString());
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
							Toast.makeText(MyHallsActivity.this,
									"数据加载失败，请重新加载~", Toast.LENGTH_SHORT).show();
						}

						if (isFalse) {
							initHallsData();
							ciShu++;
						}
			}

			@Override
			public void onNext(String s) {
				result = new String(s);
						System.out.println("喜事堂档期：" + result);
						try {
							JSONObject jsonObject = new JSONObject(result
									.trim());
							JSONArray useArray = jsonObject
									.getJSONArray("占用情况");
							JSONArray infoArray = jsonObject
									.getJSONArray("喜事堂详细");
							if (infoArray != null) {
								JSONObject infoObject = infoArray
										.getJSONObject(0);
								System.out.println(infoObject.getString("Name")
										+ infoObject.getString("DetailAddr"));
								hallname.setText(infoObject.getString("Name"));
								halladdress.setText(infoObject
										.getString("DetailAddr"));
								hallID = infoObject.getString("HallID");

								System.out.println(infoObject.toString());
							} else {
								Toast.makeText(getApplicationContext(),
										"数据加载失败！", Toast.LENGTH_SHORT).show();
							}

						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						ciShu = 0;
						isFalse = true;

			}
		});

//		RequestParams params = new RequestParams();
//		params.add("UserTel", LocalStorage.get("UserTel").toString());
//		SmartFruitsRestClient.post("HallsHandler.ashx?Action=getHallUTel",
//				params, new AsyncHttpResponseHandler() {
//
//					@Override
//					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
//						// TODO Auto-generated method stub
//						result = new String(arg2);
//						System.out.println("喜事堂档期：" + result);
//						try {
//							JSONObject jsonObject = new JSONObject(result
//									.trim());
//							JSONArray useArray = jsonObject
//									.getJSONArray("占用情况");
//							JSONArray infoArray = jsonObject
//									.getJSONArray("喜事堂详细");
//							if (infoArray != null) {
//								JSONObject infoObject = infoArray
//										.getJSONObject(0);
//								System.out.println(infoObject.getString("Name")
//										+ infoObject.getString("DetailAddr"));
//								hallname.setText(infoObject.getString("Name"));
//								halladdress.setText(infoObject
//										.getString("DetailAddr"));
//								hallID = infoObject.getString("HallID");
//
//								System.out.println(infoObject.toString());
//							} else {
//								Toast.makeText(getApplicationContext(),
//										"数据加载失败！", Toast.LENGTH_SHORT).show();
//							}
//
//						} catch (JSONException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//						ciShu = 0;
//						isFalse = true;
//					}
//
//					@Override
//					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
//							Throwable arg3) {
//						// TODO Auto-generated method stub
//
//						if (ciShu >= 3) {
//							isFalse = false;
//							Toast.makeText(MyHallsActivity.this,
//									"数据加载失败，请重新加载~", Toast.LENGTH_SHORT).show();
//						}
//
//						if (isFalse) {
//							initHallsData();
//							ciShu++;
//						}
//					}
//				});

		if (bun != null) {
			tv.setText("修改档期");
			bookname.setText(bun.getString("orderName"));
			booktel.setText(bun.getString("orderTel"));
			bookaddress.setText(bun.getString("orderAddress"));
			maintable.setText(bun.getString("dinner"));
			minortable.setText(bun.getString("vice"));
			dateBt.setText(bun.getString("orderTime"));
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) { // 按下的如果是BACK，同时没有重复

			if (System.currentTimeMillis() - HomeActivity.dianJiShiJian > 500) {
				HomeActivity.dianJiShiJian = System.currentTimeMillis();
				Intent intent = new Intent(MyHallsActivity.this,
						MyHallSchList.class);
				startActivity(intent);
				finish();
			}
			return true;
		}

		return super.onKeyDown(keyCode, event);
	}

	static String orderDate = null;

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.btn_back:

			if (System.currentTimeMillis() - HomeActivity.dianJiShiJian > 500) {
				HomeActivity.dianJiShiJian = System.currentTimeMillis();
				Intent intent = new Intent(MyHallsActivity.this,
						MyHallSchList.class);
				startActivity(intent);
				finish();
			}
			break;

		case R.id.dateBt:
			if (bun != null) {
				orderDate = bun.getString("orderTime").trim();

			} else {
				orderDate = null;
			}

			DialogFragment calendarFragment = new CalendarFragment(
					toDoItema.getTime(), null, MyHallsActivity.result, 1,
					orderDate) {

				@Override
				public void onDateSet(DatePicker view, int year,
						int monthOfYear, int dayOfMonth) {
					super.onDateSet(view, year, monthOfYear, dayOfMonth);
					toDoItema.setTime(year, monthOfYear, dayOfMonth);
					Date date = toDoItema.getTime();

				}

			};
			calendarFragment.show(getFragmentManager(), "calendarPcker");
			break;
		case R.id.yuding:

			if (TextUtils.isEmpty(bookname.getText().toString().trim())
					|| TextUtils.isEmpty(booktel.getText().toString().trim())
					|| TextUtils.isEmpty(maintable.getText().toString().trim())
					|| TextUtils
							.isEmpty(minortable.getText().toString().trim())
					|| TextUtils.isEmpty(bookaddress.getText().toString()
							.trim())) {
				Toast.makeText(getApplicationContext(), "请补充完整信息后再进行操作~",
						Toast.LENGTH_SHORT).show();
				return;
			}
			if (!Utils.isMobile(booktel.getText().toString())) {
				Toast.makeText(getApplicationContext(),
						getString(R.string.register_mobile_format_error),
						Toast.LENGTH_LONG).show();
				return;
			}
			if (!Utils.isBookName(bookname.getText().toString().trim())) {
				Toast.makeText(getApplicationContext(), "请输入正确的名称！如张三、John",
						Toast.LENGTH_LONG).show();
				return;
			}

			Date date = new Date();
			if (TextUtils.isEmpty(startDay) || TextUtils.isEmpty(endDay)) {
				if (bun == null) {
					Toast.makeText(getApplicationContext(), "请选择预定日期再进行操作~",
							Toast.LENGTH_SHORT).show();
					return;
				}
			} else {
				SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
				try {
					if (sf.parse(startDay).getTime() < date.getTime()) {
						Toast.makeText(getApplicationContext(),
								"请选择今日之后的日期再进行操作~", Toast.LENGTH_SHORT).show();
						return;
					}
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println(startDay + endDay);
			}

			d = new Dialog(MyHallsActivity.this, R.style.loading_dialog);
			v = LayoutInflater.from(MyHallsActivity.this).inflate(
					R.layout.dialog, null);// 窗口布局
			d.setContentView(v);// 把设定好的窗口布局放到dialog中
			d.setCanceledOnTouchOutside(true);// 设定点击窗口空白处取消会话
			p1 = (Button) v.findViewById(R.id.p);
			n = (Button) v.findViewById(R.id.n);
			juTiXinXi = (TextView) v.findViewById(R.id.banben_xinxi);
			tiShi = (TextView) v.findViewById(R.id.banben_gengxin);
			fuZhi = (TextView) v.findViewById(R.id.fuzhi_jiantieban);
			juTiXinXi.setGravity(Gravity.LEFT);
			if (bun != null) {
				if (!TextUtils.isEmpty(startDay) && !TextUtils.isEmpty(endDay)) {
					juTiXinXi.setText("您确定要添加此预定信息吗？\n姓名："
							+ bookname.getText().toString().trim() + "\n电话："
							+ booktel.getText().toString().trim() + "\n主桌："
							+ maintable.getText().toString().trim() + "\n副桌："
							+ minortable.getText().toString().trim() + "\n地址："
							+ bookaddress.getText().toString().trim() + "\n档期："
							+ startDay + "——" + endDay);
				} else {
					juTiXinXi.setText("您确定要更改此预定信息吗？\n姓名："
							+ bookname.getText().toString().trim() + "\n电话："
							+ booktel.getText().toString().trim() + "\n主桌："
							+ maintable.getText().toString().trim() + "\n副桌："
							+ minortable.getText().toString().trim() + "\n地址："
							+ bookaddress.getText().toString().trim() + "\n档期："
							+ bun.getString("orderTime"));
				}

				tiShi.setText("提示");
				p1.setText("更改");
				n.setText("取消");
				p1.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						uploadData();
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
			} else {

				juTiXinXi.setText("您确定要添加此预定信息吗？\n姓名："
						+ bookname.getText().toString().trim() + "\n电话："
						+ booktel.getText().toString().trim() + "\n主桌："
						+ maintable.getText().toString().trim() + "\n副桌："
						+ minortable.getText().toString().trim() + "\n地址："
						+ bookaddress.getText().toString().trim() + "\n档期："
						+ startDay + "——" + endDay);
				tiShi.setText("提示");
				p1.setText("添加");
				n.setText("取消");
				p1.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						uploadData();
						d.dismiss();
					}
				});
				n.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						d.dismiss();
					}
				});

			}
			d.show();

			break;
		default:
			break;
		}
	}

	private TextView juTiXinXi, tiShi, fuZhi;
	private Dialog d;
	private Button p1;
	private Button n;
	private View v;

	public void uploadData() {
		try {

			dialog = new LoadingDialog(MyHallsActivity.this, "正在更新数据...");
			dialog.showDialog();

			RequestParams params = new RequestParams();
			params.addBodyParameter("UserName", bookname.getText().toString());
			params.addBodyParameter("HallStatus", "3");
			params.addBodyParameter("Tel", booktel.getText().toString());
			params.addBodyParameter("Dinner", maintable.getText().toString());
			params.addBodyParameter("Vice", minortable.getText().toString());
			params.addBodyParameter("address", bookaddress.getText().toString());
			if (bun != null) {
				if (!TextUtils.isEmpty(startDay) && !TextUtils.isEmpty(endDay)) {
					params.addBodyParameter("SolarDtFrom", startDay);
					params.addBodyParameter("SolarDtTo", endDay);
				}
				params.addBodyParameter("SchID", bun.getString("SchID"));
				XUtilsHelper xUtilsHelper1 = new XUtilsHelper(this, "HallsHandler.ashx?Action=HallsSchUP", 1);
				Observable.create(new Observable.OnSubscribe<String>() {
					@Override
					public void call(Subscriber<? super String> subscriber) {
						xUtilsHelper1.sendPost(params, subscriber);
					}
				}).subscribe(new Subscriber<String>() {
					@Override
					public void onCompleted() {

					}

					@Override
					public void onError(Throwable e) {
						if (ciShu >= 3) {
									isFalse = false;
									Toast.makeText(MyHallsActivity.this,
											"亲，您的网络不太好哦~", Toast.LENGTH_SHORT)
											.show();
								}
								;

								if (isFalse) {
									uploadData();
									ciShu++;
								}
					}

					@Override
					public void onNext(String s) {

						String re = new String(s);
								System.out.println(re);
								dialog.closeDialog();
								try {
									JSONObject tishi = new JSONObject(re.trim());
									if (tishi.getString("提示").equals("占用修改失败")) {
										Toast.makeText(getApplicationContext(),
												"档期更改失败，请重新更改~",
												Toast.LENGTH_SHORT).show();
									} else {
										Toast.makeText(getApplicationContext(),
												"喜事堂更改成功~", Toast.LENGTH_SHORT)
												.show();
										Intent intent = new Intent(
												MyHallsActivity.this,
												MyHallSchList.class);
										startActivity(intent);
										finish();
									}
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								isFalse = true;
								ciShu = 0;
					}
				});

			}else {
				params.addBodyParameter("HallID", hallID);
				params.addBodyParameter("SolarDtFrom", startDay);
				params.addBodyParameter("SolarDtTo", endDay);

				XUtilsHelper xUtilsHelper1 = new XUtilsHelper(this,"HallsHandler.ashx?Action=HallsSch",1);
				Observable.create(new Observable.OnSubscribe<String>() {
					@Override
					public void call(Subscriber<? super String> subscriber) {
						xUtilsHelper1.sendPost(params,subscriber);
					}
				}).subscribe(new Subscriber<String>() {
					@Override
					public void onCompleted() {

					}

					@Override
					public void onError(Throwable e) {
						if (ciShu >= 3) {
									isFalse = false;
									Toast.makeText(MyHallsActivity.this,
											"亲，您的网络不太好哦~", Toast.LENGTH_SHORT)
											.show();
								}
								;

								if (isFalse) {
									uploadData();
									ciShu++;
								}
					}

					@Override
					public void onNext(String s) {
						String re = new String(s);
								System.out.println(re);
								dialog.closeDialog();
								try {
									JSONObject tishi = new JSONObject(re.trim());
									if (tishi.getString("提示").equals("占用失败")) {
										Toast.makeText(getApplicationContext(),
												"喜事堂预定失败，请重新预定~",
												Toast.LENGTH_SHORT).show();
									} else {
										Toast.makeText(getApplicationContext(),
												"喜事堂预定成功~", Toast.LENGTH_SHORT)
												.show();
										Intent intent = new Intent(
												MyHallsActivity.this,
												MyHallSchList.class);
										startActivity(intent);
										finish();
									}
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								isFalse = true;
								ciShu = 0;

					}
				});







			}


//			RequestParams params = new RequestParams();
//			params.add("UserName", bookname.getText().toString());
//			params.add("HallStatus", "3");
//			params.add("Tel", booktel.getText().toString());
//			params.add("Dinner", maintable.getText().toString());
//			params.add("Vice", minortable.getText().toString());
//			params.add("address", bookaddress.getText().toString());
//			if (bun != null) {
//				if (!TextUtils.isEmpty(startDay) && !TextUtils.isEmpty(endDay)) {
//					params.add("SolarDtFrom", startDay);
//					params.add("SolarDtTo", endDay);
//				}

//				params.add("SchID", bun.getString("SchID"));

//				SmartFruitsRestClient.post(
//						"HallsHandler.ashx?Action=HallsSchUP", params,
//						new AsyncHttpResponseHandler() {
//
//							@Override
//							public void onSuccess(int arg0, Header[] arg1,
//									byte[] arg2) {
//								// TODO Auto-generated method stub
//								String re = new String(arg2);
//								System.out.println(re);
//								dialog.closeDialog();
//								try {
//									JSONObject tishi = new JSONObject(re.trim());
//									if (tishi.getString("提示").equals("占用修改失败")) {
//										Toast.makeText(getApplicationContext(),
//												"档期更改失败，请重新更改~",
//												Toast.LENGTH_SHORT).show();
//									} else {
//										Toast.makeText(getApplicationContext(),
//												"喜事堂更改成功~", Toast.LENGTH_SHORT)
//												.show();
//										Intent intent = new Intent(
//												MyHallsActivity.this,
//												MyHallSchList.class);
//										startActivity(intent);
//										finish();
//									}
//								} catch (JSONException e) {
//									// TODO Auto-generated catch block
//									e.printStackTrace();
//								}
//								isFalse = true;
//								ciShu = 0;
//
//							}
//
//							@Override
//							public void onFailure(int arg0, Header[] arg1,
//									byte[] arg2, Throwable arg3) {
//								// TODO Auto-generated method stub
//
//								if (ciShu >= 3) {
//									isFalse = false;
//									Toast.makeText(MyHallsActivity.this,
//											"亲，您的网络不太好哦~", Toast.LENGTH_SHORT)
//											.show();
//								}
//								;
//
//								if (isFalse) {
//									uploadData();
//									ciShu++;
//								}
//
//							}
//						});
//			} else {
//				params.add("HallID", hallID);
//				params.add("SolarDtFrom", startDay);
//				params.add("SolarDtTo", endDay);
//				SmartFruitsRestClient.post("HallsHandler.ashx?Action=HallsSch",
//						params, new AsyncHttpResponseHandler() {
//
//							@Override
//							public void onSuccess(int arg0, Header[] arg1,
//									byte[] arg2) {
//								// TODO Auto-generated method stub
//								String re = new String(arg2);
//								System.out.println(re);
//								dialog.closeDialog();
//								try {
//									JSONObject tishi = new JSONObject(re.trim());
//									if (tishi.getString("提示").equals("占用失败")) {
//										Toast.makeText(getApplicationContext(),
//												"喜事堂预定失败，请重新预定~",
//												Toast.LENGTH_SHORT).show();
//									} else {
//										Toast.makeText(getApplicationContext(),
//												"喜事堂预定成功~", Toast.LENGTH_SHORT)
//												.show();
//										Intent intent = new Intent(
//												MyHallsActivity.this,
//												MyHallSchList.class);
//										startActivity(intent);
//										finish();
//									}
//								} catch (JSONException e) {
//									// TODO Auto-generated catch block
//									e.printStackTrace();
//								}
//								isFalse = true;
//								ciShu = 0;
//
//							}
//
//							@Override
//							public void onFailure(int arg0, Header[] arg1,
//									byte[] arg2, Throwable arg3) {
//								// TODO Auto-generated method stub
//
//								if (ciShu >= 3) {
//									isFalse = false;
//									Toast.makeText(MyHallsActivity.this,
//											"亲，您的网络不太好哦~", Toast.LENGTH_SHORT)
//											.show();
//								}
//								;
//
//								if (isFalse) {
//									uploadData();
//									ciShu++;
//								}
//
//							}
//						});
//			}
//
		} catch (Exception e) {
		// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	String startDay;
	String endDay;

	@Override
	public void SendMessageValue(String startDay, String endDay) {
		// TODO Auto-generated method stub
		this.startDay = startDay;
		this.endDay = endDay;
		System.out.println(endDay + startDay);
		if (!TextUtils.isEmpty(startDay) && !TextUtils.isEmpty(endDay)) {

			if (startDay.equals(endDay)) {
				dateBt.setText(startDay);
			} else {
				dateBt.setText(startDay + "——" + endDay);
			}

		}

	}

}
