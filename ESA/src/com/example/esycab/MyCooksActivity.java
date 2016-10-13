package com.example.esycab;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.http.Header;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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

import com.example.esycab.Fragment.CalendarFragment;
import com.example.esycab.Fragment.CalendarFragment.CallBackValue;
import com.example.esycab.ab.view.ToDoItem;
import com.example.esycab.network.SmartFruitsRestClient;
import com.example.esycab.utils.IvListener;
import com.example.esycab.utils.LoadingDialog;
import com.example.esycab.utils.LocalStorage;
import com.eyoucab.list.Utils;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class MyCooksActivity extends Activity implements OnClickListener,
		CallBackValue {

	public static void actionStart(Context context, String SchID,
			String orderName, String orderTel, String orderAddress,
			String orderTime) {
		Intent intent = new Intent(context, MyCooksActivity.class);
		Bundle bundle = new Bundle();
		bundle.putString("SchID", SchID);
		bundle.putString("orderName", orderName);
		bundle.putString("orderTel", orderTel);
		bundle.putString("orderAddress", orderAddress);
		bundle.putString("orderTime", orderTime);
		intent.putExtras(bundle);
		context.startActivity(intent);
	}

	private Button datecookBt, userInfo_back, cookyuding;
	private SimpleDateFormat sfDate;
	private ToDoItem toDoItema;
//	private TextView addcookschedule;
	private EditText cookname, cooktel, cookaddress;
	private LoadingDialog dialog = null;
	private TextView tv;
	private LinearLayout ll;
	private float dy, uy;
	private Bundle bun = null;
	private ImageView iv_more;

	private void setMoreListener() {
		iv_more = (ImageView) findViewById(R.id.iv_more);
		iv_more.setOnClickListener(new IvListener(iv_more,
				MyCooksActivity.this, 0));
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mycook);
		LocalStorage.initContext(this);
		datecookBt = (Button) findViewById(R.id.datecookBt);
		userInfo_back = (Button) findViewById(R.id.btn_back);
		cookyuding = (Button) findViewById(R.id.cookyuding);
		tv = (TextView) findViewById(R.id.tv_title);
		tv.setText("新增档期");
//		addcookschedule = (TextView) findViewById(R.id.addcookschedule);
		ll = (LinearLayout) findViewById(R.id.chushidangqi);
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
		cookname = (EditText) findViewById(R.id.cookname);
		cooktel = (EditText) findViewById(R.id.cooktel);
		cookaddress = (EditText) findViewById(R.id.cookaddress);
		bun = getIntent().getExtras();
		initHallsData();

		// 处理事件日期点击事件
		datecookBt.setOnClickListener(this);
		cookyuding.setOnClickListener(this);
		userInfo_back.setOnClickListener(this);
		setMoreListener();
	}

	public static String result, updateResult;
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
			datecookBt.setText(sfDate.format(now));
			toDoItema.setTime(now); // 设置ToDoItem事件时间
		}

		RequestParams params = new RequestParams();
		params.add("Tel", LocalStorage.get("UserTel").toString());
		SmartFruitsRestClient.post("CooksHandler.ashx?Action=getOccupy",
				params, new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
						// TODO Auto-generated method stub
						result = new String(arg2);
						System.out.println("厨师档期：" + result);

						ciShu = 0;
						isFalse = true;
					}

					@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
							Throwable arg3) {
						// TODO Auto-generated method stub

						if (ciShu >= 3) {
							isFalse = false;
							Toast.makeText(MyCooksActivity.this,
									"数据加载失败，请重新加载~", Toast.LENGTH_SHORT).show();
						}

						if (isFalse) {
							initHallsData();
							ciShu++;
						}
					}
				});

		if (bun != null) {
			tv.setText("修改档期");
			cookname.setText(bun.getString("orderName"));
			cooktel.setText(bun.getString("orderTel"));
			cookaddress.setText(bun.getString("orderAddress"));
			datecookBt.setText(bun.getString("orderTime"));
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) { // 按下的如果是BACK，同时没有重复
			if (System.currentTimeMillis() - HomeActivity.dianJiShiJian > 500) {

				HomeActivity.dianJiShiJian = System.currentTimeMillis();
				Intent intent = new Intent(MyCooksActivity.this,
						MyCookSchList.class);
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
				Intent intent = new Intent(MyCooksActivity.this,
						MyCookSchList.class);
				startActivity(intent);
				finish();
			}
			break;

		case R.id.datecookBt:
			if (bun != null) {
				orderDate = bun.getString("orderTime").trim();

			} else {
				orderDate = null;
			}

			DialogFragment calendarFragment = new CalendarFragment(
					toDoItema.getTime(), null, MyCooksActivity.result, 2,
					orderDate) {

				@Override
				public void onDateSet(DatePicker view, int year,
						int monthOfYear, int dayOfMonth) {
					super.onDateSet(view, year, monthOfYear, dayOfMonth);
					toDoItema.setTime(year, monthOfYear, dayOfMonth);
//					Date date = toDoItema.getTime();

				}

			};
			calendarFragment.show(getFragmentManager(), "calendarPcker");
			break;
		case R.id.cookyuding:

			if (TextUtils.isEmpty(cookname.getText().toString().trim())
					|| TextUtils.isEmpty(cooktel.getText().toString().trim())
					|| TextUtils.isEmpty(cookaddress.getText().toString()
							.trim())) {
				Toast.makeText(getApplicationContext(), "请补充完整信息后再进行操作~",
						Toast.LENGTH_SHORT).show();
				return;
			}
			if (!Utils.isMobile(cooktel.getText().toString())) {
				Toast.makeText(getApplicationContext(),
						getString(R.string.register_mobile_format_error),
						Toast.LENGTH_LONG).show();
				return;
			}
			if (!Utils.isBookName(cookname.getText().toString().trim())) {
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

			d = new Dialog(MyCooksActivity.this, R.style.loading_dialog);
			v = LayoutInflater.from(MyCooksActivity.this).inflate(
					R.layout.dialog, null);// 窗口布局
			d.setContentView(v);// 把设定好的窗口布局放到dialog中
			d.setCanceledOnTouchOutside(true);// 设定点击窗口空白处取消会话
			p1 = (Button) v.findViewById(R.id.p);
			n = (Button) v.findViewById(R.id.n);
			juTiXinXi = (TextView) v.findViewById(R.id.banben_xinxi);
			tiShi = (TextView) v.findViewById(R.id.banben_gengxin);
//			fuZhi = (TextView) v.findViewById(R.id.fuzhi_jiantieban);
			juTiXinXi.setGravity(Gravity.LEFT);
			if (bun != null) {
				if (!TextUtils.isEmpty(startDay) && !TextUtils.isEmpty(endDay)) {
					juTiXinXi.setText("您确定要添加此预定信息吗？\n姓名："
							+ cookname.getText().toString().trim() + "\n电话："
							+ cooktel.getText().toString().trim() + "\n地址："
							+ cookaddress.getText().toString().trim() + "\n档期："
							+ startDay + "——" + endDay);
				} else {
					juTiXinXi.setText("您确定要更改此预定信息吗？\n姓名："
							+ cookname.getText().toString().trim() + "\n电话："
							+ cooktel.getText().toString().trim() + "\n地址："
							+ cookaddress.getText().toString().trim() + "\n档期："
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
						+ cookname.getText().toString().trim() + "\n电话："
						+ cooktel.getText().toString().trim() + "\n地址："
						+ cookaddress.getText().toString().trim() + "\n档期："
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
						// System.out.println(a);
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

	private TextView juTiXinXi, tiShi;
	private Dialog d;
	private Button p1;
	private Button n;
	private View v;

	public void uploadData() {
		try {
			dialog = new LoadingDialog(MyCooksActivity.this, "正在更新数据...");
			dialog.showDialog();
			RequestParams params = new RequestParams();
			params.add("UserTel", LocalStorage.get("UserTel").toString());
			params.add("OccupyStatus", "3");
			params.add("HallName", cookaddress.getText().toString().trim());
			params.add("ROrderName", cookname.getText().toString().trim());
			params.add("ROrderNameTel", cooktel.getText().toString().trim());
			if (bun != null) {
				if (!TextUtils.isEmpty(startDay) && !TextUtils.isEmpty(endDay)) {
					params.add("SolarDtFrom", startDay);
					params.add("SolarDtTo", endDay);
				}
				System.out.println(LocalStorage.get("UserTel").toString());
				System.out.println(cookaddress.getText().toString().trim());
				System.out.println(cookname.getText().toString().trim());
				System.out.println(cooktel.getText().toString().trim());
				System.out.println(bun.getString("SchID"));
				System.out.println(startDay + endDay);
				params.add("SchID", bun.getString("SchID"));
				SmartFruitsRestClient.post(
						"CooksHandler.ashx?Action=CooksSchUP", params,
						new AsyncHttpResponseHandler() {

							@Override
							public void onSuccess(int arg0, Header[] arg1,
									byte[] arg2) {
								// TODO Auto-generated method stub
								String re = new String(arg2);
								System.out.println(re);
								dialog.closeDialog();

								try {
									JSONObject tishi = new JSONObject(re.trim());
									if (tishi.getString("提示").equals("修改失败")) {
										Toast.makeText(getApplicationContext(),
												"档期更改失败，请重新更改~",
												Toast.LENGTH_SHORT).show();
									} else {
										Toast.makeText(getApplicationContext(),
												"更改成功~", Toast.LENGTH_SHORT)
												.show();
										Intent intent = new Intent(
												MyCooksActivity.this,
												MyCookSchList.class);
										startActivity(intent);
										finish();
									}
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								isFalse = true;
								ciShu = 0;

							}

							@Override
							public void onFailure(int arg0, Header[] arg1,
									byte[] arg2, Throwable arg3) {
								// TODO Auto-generated method stub

								if (ciShu >= 3) {
									isFalse = false;
									Toast.makeText(MyCooksActivity.this,
											"亲，您的网络不太好哦~", Toast.LENGTH_SHORT)
											.show();
								}
								;

								if (isFalse) {
									uploadData();
									ciShu++;
								}

							}
						});
			} else {

				params.add("SolarDtFrom", startDay);
				params.add("SolarDtTo", endDay);
				SmartFruitsRestClient.post("CooksHandler.ashx?Action=CooksSch",
						params, new AsyncHttpResponseHandler() {

							@Override
							public void onSuccess(int arg0, Header[] arg1,
									byte[] arg2) {
								// TODO Auto-generated method stub
								String re = new String(arg2);
								System.out.println(re);
								dialog.closeDialog();
								try {
									JSONObject tishi = new JSONObject(re.trim());
									if (tishi.getString("提示").equals("占用失败")) {
										Toast.makeText(getApplicationContext(),
												"新增档期失败，请重新添加~",
												Toast.LENGTH_SHORT).show();
									} else {
										Toast.makeText(getApplicationContext(),
												"新增档期成功~", Toast.LENGTH_SHORT)
												.show();
										Intent intent = new Intent(
												MyCooksActivity.this,
												MyCookSchList.class);
										startActivity(intent);
										finish();

									}
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								isFalse = true;
								ciShu = 0;

							}

							@Override
							public void onFailure(int arg0, Header[] arg1,
									byte[] arg2, Throwable arg3) {
								// TODO Auto-generated method stub

								if (ciShu >= 3) {
									isFalse = false;
									Toast.makeText(MyCooksActivity.this,
											"亲，您的网络不太好哦~", Toast.LENGTH_SHORT)
											.show();
								}
								;

								if (isFalse) {
									uploadData();
									ciShu++;
								}

							}
						});
			}

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
				datecookBt.setText(startDay);
			} else {
				datecookBt.setText(startDay + "——" + endDay);
			}

		}

	}

}
