package com.example.administrator.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ImageView;

import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.list.Utils;
import com.example.administrator.myapplication.R;
import com.example.administrator.net.XUtilsHelper;
import com.example.administrator.utils.ACache;
import com.example.administrator.utils.ActivityCollector;
import com.example.administrator.utils.IvListener;
import com.example.administrator.utils.LocalStorage;
import com.example.administrator.utils.LoginCheckAlertDialogUtils;
import com.example.administrator.utils.ProConst;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.view.annotation.ViewInject;

import rx.Observable;
import rx.Subscriber;

/**
 * 加盟页
 * 
 * @author Administrator
 * 
 */
public class JoinInActivity extends BaseActivity implements ProConst {
	@ViewInject(R.id.btn_back)
	private Button iv;// 返回按钮
	@ViewInject(R.id.et_join_name)
	private TextView et_join_name;// 真实姓名，联系电话，菜系选择，菜系选择后的id值,日期获取
	@ViewInject(R.id.et_join_tel)
	private TextView et_join_tel;
	@ViewInject(R.id.caixi_xuanze)
	private TextView caixi_xuanze;
	@ViewInject(R.id.caixi_xuanze_id)
	private TextView caixi_xuanze_id;
	@ViewInject(R.id.et_join_time)
	private TextView et_join_time;

	@ViewInject(R.id.time)
	private Button time;// 申请日期，选择按钮，下一页按钮
	@ViewInject(R.id.xuanze)
	private Button xuanze;
	@ViewInject(R.id.next)
	private Button next;




	private ACache mCache;
@ViewInject(R.id.check)
	private CheckBox btnCheck;// 我同意按钮
	@ViewInject(R.id.cook_contract)
	private TextView btnContract;// 加盟按钮
	private boolean b = false;// 判断我同意按钮是否选中
	@ViewInject(R.id.tv_title)
	private TextView tv;
	@ViewInject(R.id.fanwei)
	private Spinner fanwei;// 服务范围，团队个人，接单能力
	@ViewInject(R.id.tuandui)
	private Spinner tuandui;
	@ViewInject(R.id.jiedan)
	private Spinner jiedan;

	private List<String> leib = new ArrayList<String>();// 团队个人
	private List<String> fwfw = new ArrayList<String>();// 服务范围
	private List<String> jd = new ArrayList<String>();// 接单能力
	private String[] series;// 全部菜系
	boolean[] bol;// 判断什么菜系选中了
	@ViewInject(R.id.chushi_jiameng_scrollview)
	private LinearLayout ll;
	private float dy, uy;
	private String tuan_geren, fuwufanwei, jdnl;// 团队个人，服务范围名字，接单能力名字
	public static Activity a5;
	@ViewInject(R.id.et_join)
	private TextView et_join;// 出生日期

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		if (!new LoginCheckAlertDialogUtils(JoinInActivity.this).showDialog()) {
		}
	}
	@Override
	protected int setContentView() {
		return R.layout.join_cook;
	}
	@Override
	protected void initView() {
		ActivityCollector.addActivity(this);
		// if(!HomeActivity.quan){
		// rl = (RelativeLayout) findViewById(R.id.g_eng_d_uo);
		// android.widget.LinearLayout.LayoutParams l =
		// (android.widget.LinearLayout.LayoutParams) rl.getLayoutParams();
		// l.topMargin = (int) (HomeActivity.a12);
		// rl.setLayoutParams(l);
		// }

		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);// 竖屏
		mCache = ACache.get(this);
		a5 = JoinInActivity.this;

		tv.setText("厨师加盟");
		LocalStorage.initContext(this);



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




		String UserTel = LocalStorage.get("UserTel").toString();
		et_join_tel.setText(UserTel);
		/*-------------------------合同----------------------------------------*/

		btnContract.setOnClickListener(new OnClickEvent(1000) {
			@Override
			public void singleClick(View v) {

				if (null != mCache.getAsString("厨师劳动合同")) {
					String result = mCache.getAsString("厨师劳动合同");
					new AlertDialog.Builder(JoinInActivity.this).setTitle("合同")
							.setMessage(result).setPositiveButton("确定", null)
							.show();
					System.out.println(1);
				} else {
					contract();
					System.out.println(2);
				}
				btnContract.setTextColor(Color.parseColor("#006600"));
			}
		});
		/*------------------------团队，个人-------------------------------------------*/


		leib.add("个人");
		leib.add("团队");
		tuandui.setAdapter(new ArrayAdapter<String>(JoinInActivity.this,
				android.R.layout.simple_dropdown_item_1line, leib));
		tuandui.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				tuan_geren = String.valueOf(arg2);
				System.out.println(tuan_geren);
				if ("0".equals(tuan_geren)) {
					et_join.setText("出生年月:");
				} else {
					et_join.setText("创办时间:");
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
		/*-----------------------服务范围--------------------------------------------*/

		if (null != mCache.getAsString("加盟服务范围")) {
			String result = mCache.getAsString("加盟服务范围");
			tcc(result);
		} else {
			district();
		}

		fanwei.setAdapter(new ArrayAdapter<String>(JoinInActivity.this,
				android.R.layout.simple_dropdown_item_1line, fwfw));

		/*------------------------菜系选择-------------------------------------------*/



		xuanze.setOnClickListener(new OnClickEvent(1000) {
			@Override
			public void singleClick(View v) {
				if (null != mCache.getAsString("加盟菜系选择")) {
					String result = mCache.getAsString("加盟菜系选择");
					cook(result);
				} else {
					cook_style();
				}

			}
		});
		/*---------------------------接单能力----------------------------------------*/

		if (null != mCache.getAsString("加盟接单能力")) {
			String result = mCache.getAsString("加盟接单能力");
			tc(result);
		} else {
			JieDan();
		}

		jiedan.setAdapter(new ArrayAdapter<String>(JoinInActivity.this,
				android.R.layout.simple_dropdown_item_1line, jd));

		/*------------------------------时间选择---------------------------------------*/
		OnClickListener dateBtnListener = new BtnOnClickListener(
				DATE_DIALOG);



		time.setOnClickListener(dateBtnListener);


		/*------------------------------返回-------------------------------------*/

		iv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (System.currentTimeMillis() - HomeActivity.dianJiShiJian > 500) {

					HomeActivity.dianJiShiJian = System.currentTimeMillis();
					InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					inputMethodManager.hideSoftInputFromWindow(
							JoinInActivity.this.getCurrentFocus()
									.getWindowToken(),
							InputMethodManager.HIDE_NOT_ALWAYS);
					// 结束当前界面
					JoinInActivity.this.finish();
				}
			}
		});

		/*-----------------------------我同意按钮--------------------------------------*/

		// btnCheck.setChecked(true);
		btnCheck.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) { // 当复选按钮被选中
					b = true;
				} else {
					b = false;
				}

			}
		});
		/*------------------------------下一页-------------------------------------*/

		next.setOnClickListener(reservation);

		setMoreListener();
	}

	@ViewInject(R.id.iv_more)
	private ImageView iv_more;

	private void setMoreListener() {

		iv_more.setOnClickListener(new IvListener(iv_more, JoinInActivity.this,
				0));
	}

	private final int DATE_DIALOG = 1;

	protected Dialog onCreateDialog(int id) {
		// 用来获取日期和时间的
		Calendar calendar = Calendar.getInstance();

		Dialog dialog = null;
		switch (id) {
		case DATE_DIALOG:

			time = (Button) findViewById(R.id.time);
			DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {
				@Override
				public void onDateSet(DatePicker datePicker, int year,
						int month, int dayOfMonth) {
					TextView editText = (TextView) findViewById(R.id.et_join_time);
					// Calendar月份是从0开始,所以month要加1
					if (month < 9) {
						if (dayOfMonth < 10) {
							editText.setText(year + "/" + "0" + (month + 1)
									+ "/0" + dayOfMonth);
						} else {
							editText.setText(year + "/" + "0" + (month + 1)
									+ "/" + dayOfMonth);
						}
					} else {
						if (dayOfMonth < 10) {
							editText.setText(year + "/" + (month + 1) + "/0"
									+ dayOfMonth);
						} else {
							editText.setText(year + "/" + (month + 1) + "/"
									+ dayOfMonth);
						}

					}

				}
			};
			dialog = new DatePickerDialog(this, R.style.AppTheme_Dialog,
					dateListener, calendar.get(Calendar.YEAR),
					calendar.get(Calendar.MONTH),
					calendar.get(Calendar.DAY_OF_MONTH));

			time.clearFocus();
			break;
		default:
			break;
		}
		return dialog;
	}

	/*
	 * 成员内部类,此处为提高可重用性，也可以换成匿名内部类
	 */
	private class BtnOnClickListener implements OnClickListener {

		private int dialogId = 0; // 默认为0则不显示对话框

		public BtnOnClickListener(int dialogId) {
			this.dialogId = dialogId;
		}

		@Override
		public void onClick(View view) {
			showDialog(dialogId);
		}

	}

	/*
	 * 合同
	 */
	private void contract() {


		XUtilsHelper xUtilsHelper1 = new XUtilsHelper(this,"JoinHandler.ashx?Action=GetCookContract",1);
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
				if (ciShu >= 3) {
							isFalse = false;
					toast(JoinInActivity.this,
							getString(R.string.conn_failed));

						}
						;
						if (isFalse) {
							contract();
							ciShu++;
						}
			}

			@Override
			public void onNext(String result) {
				int i = result.indexOf(":");
						result = result.substring(i + 1);
						System.out.println(result);

						if (!TextUtils.isEmpty(result)) {
							mCache.put("厨师劳动合同", result, 60 * 60 * 24);
						}

						new AlertDialog.Builder(JoinInActivity.this)
								.setTitle("合同").setMessage(result)
								.setPositiveButton("确定", null).show();
//

			}
		});









//		RequestParams reqParams = new RequestParams();
//		SmartFruitsRestClient.post("JoinHandler.ashx?Action=GetCookContract",
//				reqParams, new AsyncHttpResponseHandler() {
//
//					@Override
//					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
//							Throwable arg3) {
//						if (ciShu >= 3) {
//							isFalse = false;
//							Toast.makeText(JoinInActivity.this,
//									getString(R.string.conn_failed),
//									Toast.LENGTH_SHORT).show();
//						}
//						;
//						if (isFalse) {
//							contract();
//							ciShu++;
//						}
//
//						System.out.println("错误");
//
//					}
//
//					@Override
//					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
//						String result = new String(arg2);
//
//						int i = result.indexOf(":");
//						result = result.substring(i + 1);
//						System.out.println(result);
//
//						if (!TextUtils.isEmpty(result)) {
//							mCache.put("厨师劳动合同", result, 60 * 60 * 24);
//						}
//
//						new AlertDialog.Builder(JoinInActivity.this)
//								.setTitle("合同").setMessage(result)
//								.setPositiveButton("确定", null).show();
//
//					}
//				});

	}

	/*
	 * 下一页，数据传输
	 */
	private boolean isFalse = true;
	private int ciShu = 0;// 次数

	private OnClickListener reservation = new OnClickListener() {
		@Override
		public void onClick(View arg0) {
			String RealName = et_join_name.getText().toString().trim();
			String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
			Pattern p = Pattern.compile(regEx);
			Matcher m = p.matcher(RealName);

			String UserTel = LocalStorage.get("UserTel").toString();
			// Toast.makeText(JoinInActivity.this, "登入账号为" + UserTel,
			// Toast.LENGTH_LONG).show();

			String Tel = et_join_tel.getText().toString();
			String FeastDatetime = et_join_time.getText().toString();

			String caixiID = caixi_xuanze_id.getText().toString();
			String caixiName = caixi_xuanze.getText().toString();

			if (m.find()) {
				toast(JoinInActivity.this, "姓名不允许输入特殊符号！");

				return;
			}
			if (RealName == null || "".equals(RealName)) {
				toast(JoinInActivity.this, "请输入您的姓名");

				return;
			} else if (!Utils.isMobile(Tel)) {
				toast(JoinInActivity.this, "输入手机号码有误，请重新输入");

				return;
			} else if (!Tel.equals(UserTel)) {
				toast(JoinInActivity.this, "您填写的手机号有误,请填写登入账号");

				return;
			} else if (caixiName == null || "".equals(caixiName)) {
				toast(JoinInActivity.this, "请选择擅长菜系");

				return;

			} else if (!b) {
				toast(JoinInActivity.this, "请勾选我同意");

				return;
			} else if (fwfw.size() == 0) {
				Toast.makeText(JoinInActivity.this, "请选择服务范围",
						Toast.LENGTH_LONG).show();
				return;
			} else if (jd.size() == 0) {
				toast(JoinInActivity.this, "请选择接单能力");

				return;
			} else if ("".equals(FeastDatetime)) {
				toast(JoinInActivity.this, "请输入日期");

				return;

			}

			System.out.println(FeastDatetime + "," + tuan_geren + ","
					+ fuwufanwei + "," + RealName + "," + Tel + "," + caixiID
					+ "," + jdnl);
			Intent intent = new Intent();
			Bundle bundle = new Bundle();
			bundle.putString("FeastDatetime", FeastDatetime);
			bundle.putString("tuan_geren", tuan_geren);
			bundle.putString("fuwufanwei", fuwufanwei);
			bundle.putString("RealName", RealName);
			bundle.putString("Tel", Tel);
			bundle.putString("caixiID", caixiID);
			bundle.putString("jdnl", jdnl);

			intent.putExtras(bundle);
			intent.setClass(JoinInActivity.this, ChuShiJiaMeng.class);
			startActivity(intent);

		}

	};

	/*
	 * 获取服务范围区
	 */
	private void district() {



		XUtilsHelper xUtilsHelper1 = new XUtilsHelper(this,"HallsHandler.ashx?Action=getCounty",1);
		RequestParams requestParams = new RequestParams();
		requestParams.addBodyParameter("CityID", "1");
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
					toast(JoinInActivity.this,
							getString(R.string.conn_failed));

						}
						;
						if (isFalse) {
							district();
							ciShu++;
						}
			}

			@Override
			public void onNext(String result) {
				if (!TextUtils.isEmpty(result)) {
							mCache.put("加盟服务范围", result, 60 * 60 * 24);// 60*60*24以秒为单位，每天删除
						}
						tcc(result);

			}
		});












//		RequestParams r = new RequestParams();
//		r.put("CityID", "1");
//		SmartFruitsRestClient.post("HallsHandler.ashx?Action=getCounty", r,
//				new AsyncHttpResponseHandler() {
//
//					@Override
//					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
//							Throwable arg3) {
//						if (ciShu >= 3) {
//							isFalse = false;
//							Toast.makeText(JoinInActivity.this,
//									getString(R.string.conn_failed),
//									Toast.LENGTH_SHORT).show();
//						}
//						;
//						if (isFalse) {
//							district();
//							ciShu++;
//						}
//
//					}
//
//					@Override
//					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
//						String result = new String(arg2);
//						System.out.println(result);
//						if (!TextUtils.isEmpty(result)) {
//							mCache.put("加盟服务范围", result, 60 * 60 * 24);// 60*60*24以秒为单位，每天删除
//						}
//						tcc(result);
//
//					}
//
//				});

	}

	private void tcc(String result) {

		JSONObject tJson = null;
		try {
			JSONObject json = new JSONObject(result.trim());

			JSONArray cities = json.getJSONArray("区");

			for (int i = 0; i < cities.length(); i++) {
				tJson = cities.getJSONObject(i);
				fwfw.add(tJson.getString("CountyName"));

			}
			fanwei.setAdapter(new ArrayAdapter<String>(JoinInActivity.this,
					android.R.layout.simple_dropdown_item_1line, fwfw));
			fanwei.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {

					fuwufanwei = String.valueOf(arg2 + 1);
					System.out.println(fuwufanwei + "范围");
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub

				}
			});

		} catch (JSONException e) {
			System.out.println(123);
			e.printStackTrace();
		}

	}

	/*
	 * 获取接单能力
	 */
	private void JieDan() {



		XUtilsHelper xUtilsHelper1 = new XUtilsHelper(this,"JoinHandler.ashx?Action=GetMaxOrderNums",1);
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

						if (ciShu >= 3) {
							isFalse = false;
							toast(JoinInActivity.this,
									getString(R.string.conn_failed));

						}
						;
						if (isFalse) {
							JieDan();
							ciShu++;
						}

			}

			@Override
			public void onNext(String result) {

				if (!TextUtils.isEmpty(result)) {
							mCache.put("加盟接单能力", result, 60 * 60 * 24);// 60*60*24以秒为单位，每天删除
						}
						tc(result);
			}
		});







//		RequestParams r = new RequestParams();
//
//		SmartFruitsRestClient.post("JoinHandler.ashx?Action=GetMaxOrderNums",
//				r, new AsyncHttpResponseHandler() {
//
//					@Override
//					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
//							Throwable arg3) {
//
//						if (ciShu >= 3) {
//							isFalse = false;
//							Toast.makeText(JoinInActivity.this,
//									getString(R.string.conn_failed),
//									Toast.LENGTH_SHORT).show();
//						}
//						;
//						if (isFalse) {
//							JieDan();
//							ciShu++;
//						}
//
//					}
//
//					@Override
//					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
//						String result = new String(arg2);
//						System.out.println(result);
//						if (!TextUtils.isEmpty(result)) {
//							mCache.put("加盟接单能力", result, 60 * 60 * 24);// 60*60*24以秒为单位，每天删除
//						}
//						tc(result);
//					}
//				});

	}

	private void tc(String result) {

		JSONObject tJson = null;
		try {
			JSONObject json = new JSONObject(result.trim());

			JSONArray cities = json.getJSONArray("接单能力");

			for (int i = 0; i < cities.length(); i++) {
				tJson = cities.getJSONObject(i);
				jd.add(tJson.getString("Name"));

			}
			jiedan.setAdapter(new ArrayAdapter<String>(JoinInActivity.this,
					android.R.layout.simple_dropdown_item_1line, jd));
			jiedan.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {

					jdnl = String.valueOf(arg2 + 1);
					System.out.println(jdnl + "接单能力");
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub

				}
			});

		} catch (JSONException e) {
			System.out.println(123);
			e.printStackTrace();
		}

	}

	/*
	 * 获取菜系
	 */
	private void cook_style() {


		XUtilsHelper xUtilsHelper1 = new XUtilsHelper(this,"BookHandler.ashx?Action=bookPage",1);
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
				if (ciShu >= 3) {
							isFalse = false;
					toast(JoinInActivity.this,
							getString(R.string.conn_failed));

						}
						;
						if (isFalse) {
							cook_style();
							ciShu++;
						}
			}

			@Override
			public void onNext(String result) {
				if (!TextUtils.isEmpty(result)) {
							mCache.put("加盟菜系选择", result, 60 * 60 * 24);// 60*60*24以秒为单位，每天删除
						}

						cook(result);

			}
		});









//		RequestParams p = new RequestParams();
//		SmartFruitsRestClient.get("BookHandler.ashx?Action=bookPage", p,
//				new AsyncHttpResponseHandler() {
//
//					@Override
//					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
//						// TODO Auto-generated method stub
//						String result = new String(arg2);
//						System.out.println(result);
//
//						if (!TextUtils.isEmpty(result)) {
//							mCache.put("加盟菜系选择", result, 60 * 60 * 24);// 60*60*24以秒为单位，每天删除
//						}
//
//						cook(result);
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
//							Toast.makeText(JoinInActivity.this,
//									getString(R.string.conn_failed),
//									Toast.LENGTH_SHORT).show();
//						}
//						;
//						if (isFalse) {
//							cook_style();
//							ciShu++;
//						}
//
//					}
//				});

	}

	/*
	 * 菜系解析
	 */
	private void cook(String result) {
		JSONObject json;
		JSONObject tJson = null;
		try {
			json = new JSONObject(result.trim());
			JSONArray configlists = json.getJSONArray("series");
			series = new String[configlists.length()];
			bol = new boolean[configlists.length()];

			for (int i = 0; i < configlists.length(); i++) {
				tJson = configlists.getJSONObject(i);
				series[i] = tJson.getString("Name");
				bol[i] = false;
			}

			Dialog alertDialog = new AlertDialog.Builder(JoinInActivity.this)
					.setTitle("请选择擅长菜系")

					.setMultiChoiceItems(series, bol,
							new DialogInterface.OnMultiChoiceClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which, boolean isChecked) {
									bol[which] = isChecked;
								}
							})
					.setPositiveButton("确认",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									StringBuilder stringBuilder = new StringBuilder();
									StringBuilder id = new StringBuilder();
									for (int i = 0; i < bol.length; i++) {
										if (bol[i] == true) {
											stringBuilder.append(series[i]
													+ " ");

											String is = String.valueOf(i + 1);
											id.append(is + "H");
										}
									}
									String str = stringBuilder.toString();
									String id_str = id.toString();
									if (str.length() != 0) {
										str = str.substring(0, str.length() - 1);

										id_str = id_str.substring(0,
												id_str.length() - 1);
									}
									caixi_xuanze.setText(str);
									caixi_xuanze_id.setText(id_str);

									// Toast.makeText(JoinInActivity.this, str,
									// Toast.LENGTH_SHORT).show();

								}
							})
					.setNegativeButton("取消",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
								}
							}).create();
			alertDialog.show();

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}




	protected void toast(Context context , String info){
		Toast.makeText(context,info,Toast.LENGTH_SHORT).show();
	}


}
