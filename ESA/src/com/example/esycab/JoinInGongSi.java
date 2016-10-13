package com.example.esycab;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.esycab.network.SmartFruitsRestClient;
import com.example.esycab.utils.ACache;
import com.example.esycab.utils.ActivityCollector;
import com.example.esycab.utils.IvListener;
import com.example.esycab.utils.LocalStorage;
import com.example.esycab.utils.LoginCheckAlertDialogUtils;
import com.eyoucab.list.Utils;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class JoinInGongSi extends Activity {
	private Spinner et_join_in;// 行业所属
	private Button et_join_time, gongsi_back;// 时间，返回
	private CheckBox btnCheck;// 我同意按钮
	private ACache mCache;// 缓存
	private int mYear;// 获取当前年月日
	private int mMonth;
	private int mDay;
	private Button reserva_tion_join;// 申请加盟按钮
	private EditText et_join_name, et_join_company, et_join_tel;// 姓名，公司名称，联系电话
	private boolean b = false;// 判断是否勾选我同意
	private List<String> ld = new ArrayList<String>();// 行业所属ID
	private List<String> ln = new ArrayList<String>();// 行业所属名字
	private int hyID;// 行业所属所选中的ID
	private String hyName;// 行业所属所选中的名字
	private TextView tv, company_contract;// 合同按钮
	private LinearLayout ll;
	private float dy, uy, zy;

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		if (!new LoginCheckAlertDialogUtils(JoinInGongSi.this,0).showDialog()) {
			System.out.println(132456789);
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
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
		setContentView(R.layout.join_gongsi);
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		ActivityCollector.addActivity(this);

		LocalStorage.initContext(this);
		ll = (LinearLayout) findViewById(R.id.gongsi_jiameng_scrollview);
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

		mCache = ACache.get(this);
		et_join_name = (EditText) findViewById(R.id.et_join_name);

		et_join_company = (EditText) findViewById(R.id.et_join_company);

		et_join_tel = (EditText) findViewById(R.id.et_join_tel);
		et_join_in = (Spinner) findViewById(R.id.et_join_in);
		tv = (TextView) findViewById(R.id.tv_title);
		tv.setText("公司加盟");
		String UserTel = LocalStorage.get("UserTel").toString();
		et_join_tel.setText(UserTel);
		/*-------------------------------------合同-----------------------------------------------*/
		company_contract = (TextView) findViewById(R.id.company_contract);
		company_contract.setOnClickListener(new OnClickEvent(1000) {
			@Override
			public void singleClick(View v) {
				contract();
				company_contract.setTextColor(Color.parseColor("#006600"));
			}
		});

		/*---------------------------------我同意按钮---------------------------------------------------*/
		btnCheck = (CheckBox) findViewById(R.id.check);
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

		/*---------------------------------日期选择---------------------------------------------------*/
		et_join_time = (Button) findViewById(R.id.et_join_time);
		final Calendar c = Calendar.getInstance();
		mYear = c.get(Calendar.YEAR); // 获取当前年份
		mMonth = c.get(Calendar.MONTH);// 获取当前月份
		mDay = c.get(Calendar.DAY_OF_MONTH);// 获取当前月份的日期号码
		Calendar calendar = Calendar.getInstance();
		String nowString = calendar.get(Calendar.YEAR) + "/"
				+ (calendar.get(Calendar.MONTH) + 1) + "/"
				+ calendar.get(Calendar.DAY_OF_MONTH);
		et_join_time.setText(nowString);

		/*-------------------------------行业所属-----------------------------------------------------*/
		et_join_in = (Spinner) findViewById(R.id.et_join_in);
		if (null != mCache.getAsString("加盟行业所属")) {
			String result = mCache.getAsString("加盟行业所属");
			trcy(result);
		} else {
			getMsg();
		}

		/*---------------------------申请加盟---------------------------------------------------------*/
		reserva_tion_join = (Button) findViewById(R.id.reserva_tion_join);
		reserva_tion_join.setOnClickListener(reservation);

		/*-------------------------------返回-----------------------------------------------------*/
		findViewById(R.id.btn_back).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (System.currentTimeMillis() - HomeActivity.dianJiShiJian > 500) {

					HomeActivity.dianJiShiJian = System.currentTimeMillis();
					finish();
				}

			}
		});
		setMoreListener();
	}

	private ImageView iv_more;

	private void setMoreListener() {
		iv_more = (ImageView) findViewById(R.id.iv_more);
		iv_more.setOnClickListener(new IvListener(iv_more, JoinInGongSi.this, 0));
	}

	boolean isFalse = true;
	int ciShu = 0;// 申请次数

	/*
	 * 加载行业所属类别
	 */
	private void getMsg() {
		RequestParams p = new RequestParams();
		SmartFruitsRestClient.get("JoinHandler.ashx?Action=GetCompanyCgy", p,
				new AsyncHttpResponseHandler() {

					@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
							Throwable arg3) {
						if (ciShu >= 3) {
							isFalse = false;
							Toast.makeText(JoinInGongSi.this,
									getString(R.string.conn_failed),
									Toast.LENGTH_SHORT).show();
						}
						;
						if (isFalse) {
							getMsg();
							ciShu++;
						}
					}

					@Override
					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
						String result = new String(arg2);
						System.out.println(result + "行业");
						if (!TextUtils.isEmpty(result)) {
							mCache.put("加盟行业所属", result, 60 * 60 * 24);// 60*60*24以秒为单位，每天删除
						}
						trcy(result);
					}

				});

	}

	/*
	 * 解析行业所属类别
	 */
	private void trcy(String result) {
		JSONObject json;
		JSONObject tJson = null;
		try {
			json = new JSONObject(result.trim());
			JSONArray hy = json.getJSONArray("行业");

			for (int i = 0; i < hy.length(); i++) {
				tJson = hy.getJSONObject(i);
				ld.add(tJson.getString("ID"));
				ln.add(tJson.getString("Name"));

			}

			et_join_in.setAdapter(new ArrayAdapter<String>(JoinInGongSi.this,
					android.R.layout.simple_dropdown_item_1line, ln));

			et_join_in.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {

					hyID = arg2;
					hyName = ln.get(arg2);
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub

				}
			});

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	TextView juTiXinXi, tiShi, fuZhi;
	Dialog d;
	Button p1;
	Button n;
	View v;
	String nameID;
	/*
	 * 申请加盟功能实现
	 */
	OnClickListener reservation = new OnClickEvent(1000) {
		@Override
		public void singleClick(View arg0) {
			// TODO Auto-generated method stub

			add();
		}

	};

	private void add() {

		String RealName = et_join_name.getText().toString().trim();

		String UserTel = LocalStorage.get("UserTel").toString();

		String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(RealName);

		String Tel = et_join_tel.getText().toString();

		String Gongsi = et_join_company.getText().toString().trim();
		String reg = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？0123456789]";
		Pattern p2 = Pattern.compile(reg);
		Matcher m2 = p2.matcher(Gongsi);

		String FeastDatetime = et_join_time.getText().toString();
		if (m.find()) {
			Toast.makeText(JoinInGongSi.this, "姓名不允许输入特殊符号！", Toast.LENGTH_LONG)
					.show();
			return;
		} else if (m2.find()) {
			Toast.makeText(JoinInGongSi.this, "公司名字只能输入中文和英文！",
					Toast.LENGTH_LONG).show();
			return;
		}
		if (RealName == null || "".equals(RealName)) {
			Toast.makeText(JoinInGongSi.this, "请输入您的姓名", Toast.LENGTH_LONG)
					.show();
			return;
		} else if (!Utils.isMobile(Tel)) {
			Toast.makeText(JoinInGongSi.this, "输入手机号码有误，请重新输入",
					Toast.LENGTH_LONG).show();
			return;
		} else if (!Tel.equals(UserTel)) {
			Toast.makeText(JoinInGongSi.this, "您填写的手机号有误,请填写登入账号",
					Toast.LENGTH_LONG).show();
			return;
		} else if (Gongsi == null || "".equals(Gongsi)) {
			Toast.makeText(JoinInGongSi.this, "请输入公司名字", Toast.LENGTH_LONG)
					.show();
			return;
		} else if (!b) {
			Toast.makeText(JoinInGongSi.this, "请勾选我同意", Toast.LENGTH_LONG)
					.show();
			return;
		} else

		if (ld.size() == 0) {
			Toast.makeText(JoinInGongSi.this, "请选择行业所属", Toast.LENGTH_LONG)
					.show();
			return;
		}
		nameID = ld.get(hyID);
		System.out.println(nameID + "," + FeastDatetime + "," + Gongsi + ","
				+ RealName + "," + Tel + "," + hyName);
		RequestParams params = new RequestParams();
		params.put("dateTime", FeastDatetime);
		params.put("Company", Gongsi);
		params.put("JoinselectID", nameID);
		params.put("User", RealName);
		params.put("Tel", Tel);

		SmartFruitsRestClient.post("JoinHandler.ashx?Action=CompanyJoin",
				params, new AsyncHttpResponseHandler() {

					@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
							Throwable arg3) {
						// TODO Auto-generated method stub

						if (ciShu >= 3) {
							isFalse = false;
							Toast.makeText(JoinInGongSi.this,
									getString(R.string.conn_failed),
									Toast.LENGTH_SHORT).show();
						}
						;
						if (isFalse) {
							add();
							ciShu++;
						}

					}

					@Override
					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
						String result = new String(arg2);

						JSONObject json;
						JSONObject tJson = null;

						try {
							json = new JSONObject(result.trim());
							final String res = json.getString("提示");
							if (res != null) {
								if (JoinInGongSi.this.isFinishing()) {
								} else {
									d = new Dialog(JoinInGongSi.this,
											R.style.loading_dialog);
									v = LayoutInflater.from(JoinInGongSi.this)
											.inflate(R.layout.dialog, null);// 窗口布局
									d.setContentView(v);// 把设定好的窗口布局放到dialog中
									d.setCanceledOnTouchOutside(true);// 设定点击窗口空白处取消会话
									p1 = (Button) v.findViewById(R.id.p);
									n = (Button) v.findViewById(R.id.n);
									juTiXinXi = (TextView) v
											.findViewById(R.id.banben_xinxi);
									tiShi = (TextView) v
											.findViewById(R.id.banben_gengxin);
									fuZhi = (TextView) v
											.findViewById(R.id.fuzhi_jiantieban);
									juTiXinXi.setText(res);
									tiShi.setText("消息");
									p1.setText("确定");
									n.setText("返回");
									n.setVisibility(View.GONE);
									p1.setOnClickListener(new OnClickListener() {
										@Override
										public void onClick(View arg0) {
											// TODO Auto-generated method stub

											if (res.contains("成功")) {

												JoinInGongSi.this.finish();
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
							System.out.println(e + ",程序出现错误了！！！！！！！！！！！");
						}

					}

				});

	}

	/*
	 * 合同管理
	 */
	private void contract() {
		String dizhi = "JoinHandler.ashx?Action=GetWiddingContract";
		if (hyName.contains("婚车")) {
			dizhi = "JoinHandler.ashx?Action=GetWiddingCarContract";
		} else if (hyName.contains("婚庆")) {
			dizhi = "JoinHandler.ashx?Action=GetWiddingContract";
		} else if (hyName.contains("餐具")) {
			dizhi = "JoinHandler.ashx?Action=GetWaresContract";
		} else if (hyName.contains("菜品")) {
			new AlertDialog.Builder(JoinInGongSi.this)
			.setTitle("合同").setMessage("暂无合同")
			.setPositiveButton("确定", null).show();
			return;
		}

		RequestParams reqParams = new RequestParams();
		SmartFruitsRestClient.post(dizhi, reqParams,
				new AsyncHttpResponseHandler() {

					@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
							Throwable arg3) {
						if (ciShu >= 3) {
							isFalse = false;
							Toast.makeText(JoinInGongSi.this,
									getString(R.string.conn_failed),
									Toast.LENGTH_SHORT).show();
						}
						;
						if (isFalse) {
							contract();
							ciShu++;
						}

						System.out.println("错误");

					}

					@Override
					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
						String result = new String(arg2);

						int i = result.indexOf(":");
						result = result.substring(i + 1);
						System.out.println(result);
						new AlertDialog.Builder(JoinInGongSi.this)
								.setTitle("合同").setMessage(result)
								.setPositiveButton("确定", null).show();

					}
				});

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (System.currentTimeMillis() - HomeActivity.dianJiShiJian > 500) {

				HomeActivity.dianJiShiJian = System.currentTimeMillis();
				finish();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
