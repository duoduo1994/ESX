package com.example.esycab;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.esycab.Fragment.CalendarFragment;
import com.example.esycab.ab.view.ToDoItem;
import com.example.esycab.entity.AnLi;
import com.example.esycab.network.SmartFruitsRestClient;
import com.example.esycab.utils.ACache;
import com.example.esycab.utils.ActivityCollector;
import com.example.esycab.utils.Diolg;
import com.example.esycab.utils.IvListener;
import com.example.esycab.utils.LocalStorage;
import com.example.esycab.utils.LoginCheckAlertDialogUtils;
import com.example.esycab.utils.ProConst;
import com.eyoucab.list.Utils;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * 预定页
 * 
 * @author Administrator
 * 
 */
public class ReserveActivity extends Activity implements ProConst, TextWatcher {
	String selectHallId;
	private EditText edittext_reservation_name;// 预定姓名 喜事堂名称
	private RadioButton rb_Sex_Male;// 男
	private RadioButton rb_Sex_Woman;// 女
	private TextView edittext_reservation_phone;// 预定电话
	private TextView edittext_reservation_site;// 预定地址
	private EditText edittext_reservation_dinner;// 预定主餐
	private EditText edittext_reservation_meal;// 预定副餐
	private TextView ets_UserInfo_Sites;
	private ToDoItem toDoItem;
	private Button dateBtn; // 事件日期和时间
	private SimpleDateFormat sfDate;
	private Button reserva;
	int sex = 1;// 默认选择三星级
//	private Context context;
	private Button iv;
	private ACache mCache;
	private String UserTel = "", nickName;
	private String UserPass = "";
	private Spinner caixixuanze, xishitang_xuanze, zhucanshijian_xuanze;
	private List<String> ls, ll, ld;
	private AnLi anli;
	private List<AnLi> lal;
	private String series, feastTypy, date;
	private JSONObject tJson = null;
	private int caiID, yanhuiID, dateID;

	private void tryc(String result) {
		try {
			JSONObject json = new JSONObject(result.trim());
			JSONArray configlist = json.getJSONArray("Type");
			JSONArray configlists = json.getJSONArray("series");
			System.out.println(result);
			if (configlist.length() >= 1) {
				mCache.put("预定", result, 60 * 60 * 24);
			}
			if (configlist.length() <= configlists.length()) {

				configlist = json.getJSONArray("series");
				configlists = json.getJSONArray("Type");

			}
			System.out.println(configlist.length() + "RTYUJHG"
					+ configlists.length());

			for (int i = 0; i < configlist.length(); i++) {
				tJson = configlist.getJSONObject(i);
				anli = new AnLi();
				anli.setHallsName(tJson.getString("Name"));// 菜系ming
				System.out.println(i + "DFGH" + tJson.getString("Name"));
				anli.setPkCaseID(tJson.getInt("ID"));// caixiID
				ls.add(tJson.getString("Name"));
				lal.add(anli);
			}
			for (int j = 0; j < configlists.length(); j++) {
				tJson = configlists.getJSONObject(j);
				ll.add(tJson.getString("Name"));
				lal.get(j).setPkTbWareCgyID(tJson.getInt("pkFstCgyID"));// xiyanID
				lal.get(j).setName(tJson.getString("Name"));// xiyanName
			}

			ArrayAdapter<String> mDistrictAdapter = new ArrayAdapter<String>(
					this, R.layout.myspinner, ls);
			mDistrictAdapter
					.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			caixixuanze.setAdapter(mDistrictAdapter);

			ArrayAdapter<String> mDistrictAdapterss = new ArrayAdapter<String>(
					this, R.layout.myspinner, ll);
			mDistrictAdapterss
					.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			xishitang_xuanze.setAdapter(mDistrictAdapterss);

			ArrayAdapter<String> mDistrictAdapters = new ArrayAdapter<String>(
					this, R.layout.myspinner, ld);
			mDistrictAdapters
					.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			zhucanshijian_xuanze.setAdapter(mDistrictAdapters);
			caixixuanze.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					series = lal.get(arg2).getHallsName();
					caiID = arg2;
					System.out.println(caiID);
					System.out.println(series);
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub

				}
			});
			xishitang_xuanze
					.setOnItemSelectedListener(new OnItemSelectedListener() {

						@Override
						public void onItemSelected(AdapterView<?> arg0,
								View arg1, int arg2, long arg3) {
							// TODO Auto-generated method stub
							feastTypy = lal.get(arg2).getName();
							yanhuiID = arg2;
							System.out.println(feastTypy);
							System.out.println(yanhuiID);
						}

						@Override
						public void onNothingSelected(AdapterView<?> arg0) {
							// TODO Auto-generated method stub

						}
					});

			zhucanshijian_xuanze
					.setOnItemSelectedListener(new OnItemSelectedListener() {

						@Override
						public void onItemSelected(AdapterView<?> arg0,
								View arg1, int arg2, long arg3) {
							// TODO Auto-generated method stub

							date = ld.get(arg2);
							dateID = arg2;
							System.out.println(date);
						}

						@Override
						public void onNothingSelected(AdapterView<?> arg0) {
							// TODO Auto-generated method stub

						}
					});

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			// Toast.makeText(ReserveActivity.this, e.getMessage(),
			// Toast.LENGTH_SHORT).show();
		}
	}

	private void getMsg() {
		RequestParams p = new RequestParams();
		SmartFruitsRestClient.get("BookHandler.ashx?Action=bookPage", p,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
						// TODO Auto-generated method stub
						String result = new String(arg2);
						System.out.println(result);
						tryc(result);
					}

					@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
							Throwable arg3) {
						// TODO Auto-generated method stub
						System.out.println(arg3 + "asdfgdhgsa");
						if (ciShu >= 3) {
							isFalse = false;
							Toast.makeText(ReserveActivity.this,
									getString(R.string.conn_failed),
									Toast.LENGTH_SHORT).show();
						}
						;
						if (isFalse) {
							getMsg();
							ciShu++;
						}

					}
				});
	}

	private String zhuangTai = "0";

	private void getDingDanWei() {
		RequestParams p = new RequestParams();
		if (LocalStorage.get("UserTel").toString().length() != 11) {

		} else {
			System.out.println(LocalStorage.get("UserTel").toString()
					+ "_________");
			p.put("fkCusTel", LocalStorage.get("UserTel").toString());
			SmartFruitsRestClient.get("BookHandler.ashx?Action=bookAllInfo", p,
					new AsyncHttpResponseHandler() {

						@Override
						public void onSuccess(int arg0, Header[] arg1,
								byte[] arg2) {
							// TODO Auto-generated method stub
							String result = new String(arg2);
							System.out.println("RFY" + result);
							try {
								JSONObject json = new JSONObject(result.trim());
								JSONArray j = json.getJSONArray("预定信息列表");
								JSONObject tj;
								if (j.length() != 0) {
									for (int i = 0; i < j.length(); i++) {
										tj = j.getJSONObject(i);
										zhuangTai = tj.getString("OrdStatus");
									}

								}
								System.out.println(zhuangTai);
								if ("1".equals(zhuangTai)
										|| "2".equals(zhuangTai)
										|| "3".equals(zhuangTai)) {
									new Diolg(ReserveActivity.this, "确定",
											"null", "您还有未完成的订单", "提示", 16);
								}
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						}

						@Override
						public void onFailure(int arg0, Header[] arg1,
								byte[] arg2, Throwable arg3) {
							// TODO Auto-generated method stub
							System.out.println(arg3 + "asdfgdhgsa");
							if (ciShu >= 3) {
								isFalse = false;
							}
							;
							if (isFalse) {
								ciShu++;
							}

						}
					});
		}
	}

	private boolean isFalse = true;
	private int ciShu = 0;
	private Button xzxst;
	private String xst;
	private int jiaRu;
	private TextView tv;
	private float dy, uy, zy;
	private LinearLayout ll1, l;
	public static Activity ssthis;
	private ScrollView s;
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
		setContentView(R.layout.activity_reservations);
		ActivityCollector.addActivity(this);
		// if(!HomeActivity.quan){
		// rl = (RelativeLayout) findViewById(R.id.g_eng_d_uo);
		// android.widget.LinearLayout.LayoutParams l =
		// (android.widget.LinearLayout.LayoutParams) rl.getLayoutParams();
		// l.topMargin = (int) (HomeActivity.a12);
		// rl.setLayoutParams(l);
		// }
		s = (ScrollView) findViewById(R.id.awefasdfasdfasdfasdf);
		jiaRu = getIntent().getIntExtra("jiaRu", 0);
		ssthis = ReserveActivity.this;
		lal = new ArrayList<AnLi>();
		tv = (TextView) findViewById(R.id.tv_title);
		tv.setText("预定信息");
		mCache = ACache.get(this);

		LocalStorage.initContext(this);
		ll1 = (LinearLayout) findViewById(R.id.wo_de_yu_ding1);
		ll1.setOnTouchListener(new OnTouchListener() {

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
		UserTel = LocalStorage.get("UserTel").toString();
		UserPass = LocalStorage.get("UserPass").toString();
		nickName = LocalStorage.get("RealName").toString();
		// System.out.println("111111密码：  " + UserPass + "22222手机号  ：" +
		// UserTel);
		xzxst = (Button) findViewById(R.id.xzxst);// 选择喜事堂
		xzxst.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(ReserveActivity.this,
						WeddinghallActivity.class);
				intent.putExtra("yuding", 1);
				startActivityForResult(intent, 123);

			}
		});
		edittext_reservation_name = (EditText) findViewById(R.id.et_UserInfo_Name);
		edittext_reservation_name.setText(nickName);
		caixixuanze = (Spinner) findViewById(R.id.caixi_xuanze);
		ls = new ArrayList<String>();
		ll = new ArrayList<String>();
		ld = new ArrayList<String>();
		ld.add("中午");
		ld.add("晚上");
		zhucanshijian_xuanze = (Spinner) findViewById(R.id.zhucanshijian_xuanze);

		xishitang_xuanze = (Spinner) findViewById(R.id.xishitang_xuanze);
		caixixuanze.setAdapter(new ArrayAdapter<String>(ReserveActivity.this,
				android.R.layout.simple_list_item_2, ls));

		rb_Sex_Male = (RadioButton) findViewById(R.id.rb_Sex_Male);
		rb_Sex_Woman = (RadioButton) findViewById(R.id.rb_Sex_Woman);
		String se = (String) LocalStorage.get("Sex");
		if ("0".equals(se)) {
			rb_Sex_Woman.setChecked(true);
		} else {
			rb_Sex_Male.setChecked(true);
		}

		ets_UserInfo_Sites = (TextView) findViewById(R.id.ets_UserInfo_Sites);

		edittext_reservation_phone = (TextView) findViewById(R.id.et_UserInfo_Age);
		edittext_reservation_phone.setText(UserTel);

		edittext_reservation_site = (TextView) findViewById(R.id.et_UserInfo_Site);
		String reservation_site = (String) LocalStorage.get("HomeAddress");
		edittext_reservation_site.setText(reservation_site);

		edittext_reservation_dinner = (EditText) findViewById(R.id.et_UserInfo_Table);

		edittext_reservation_meal = (EditText) findViewById(R.id.et_UserInfo_FTable);
		edittext_reservation_dinner.addTextChangedListener(this);
		edittext_reservation_meal.addTextChangedListener(this);
		reserva = (Button) findViewById(R.id.reserva_tion);
		reserva.setOnClickListener(reservation);

		dateBtn = (Button) findViewById(R.id.dateBtn);
		Date now = new Date();
		sfDate = new SimpleDateFormat("yyyy/MM/dd", Locale.CHINA);
		Bundle extras = getIntent().getExtras();
		if (extras == null || extras.get("FastDateTime") == null) {
			// 创建task为空的ToDoItem对象
			toDoItem = new ToDoItem("");
			// 新建待办事项，将界面中事件时间设置为当前时间
			dateBtn.setText(sfDate.format(now));
			toDoItem.setTime(now); // 设置ToDoItem事件时间
		}
		// 处理事件日期点击事件
		dateBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				DialogFragment calendarFragment = new CalendarFragment(toDoItem
						.getTime(), null, null, 0, null) {

					@Override
					public void onDateSet(DatePicker view, int year,
							int monthOfYear, int dayOfMonth) {
						super.onDateSet(view, year, monthOfYear, dayOfMonth);
						toDoItem.setTime(year, monthOfYear, dayOfMonth);
						Date date = toDoItem.getTime();
						dateBtn.setText(sfDate.format(date));

					}

				};
				calendarFragment.show(getFragmentManager(), "calendarPcker");
				// Toast.makeText(getApplicationContext(),
				// toDoItem.getTime().toString(), Toast.LENGTH_LONG)
				// .show();
			}
		});

		rb_Sex_Male
				.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						// three_star.setChecked(true);
						if (isChecked) {
							sex = 1;
						}
					}
				});
		rb_Sex_Woman
				.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						if (isChecked) {
							sex = 0;
						}
					}
				});
		iv = (Button) findViewById(R.id.btn_back);
		iv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				HomeActivity.dianJiShiJian = System.currentTimeMillis();
				InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				inputMethodManager.hideSoftInputFromWindow(ReserveActivity.this
						.getCurrentFocus().getWindowToken(),
						InputMethodManager.HIDE_NOT_ALWAYS);

				// 结束当前界面
				ReserveActivity.this.finish();
				ActivityCollector.finishAll();
			}
		});

		edittext_reservation_name.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View arg0, int arg1, KeyEvent arg2) {
				String t1 = edittext_reservation_name.getText().toString();
				System.out.println(arg1);
				switch (arg1) {
				case 62:
					t1 = t1.trim();
					edittext_reservation_name.setText(t1);
					edittext_reservation_name.setSelection(t1.length());
					Toast.makeText(ReserveActivity.this, "姓名不允许包含空格，请重新输入！",
							Toast.LENGTH_LONG).show();
					break;

				default:
					break;
				}

				return false;
			}

		});
		if (null != mCache.getAsString("预定")) {
			String result = mCache.getAsString("预定");
			getDingDanWei();
			tryc(result);
		} else {
			System.out.println(147852);
			getDingDanWei();
			getMsg();
		}
		setMoreListener();
	}

	@SuppressWarnings("static-access")
	private Boolean checkLogin() {
		return new LoginCheckAlertDialogUtils(ReserveActivity.this,0)
				.showDialog();// 若登录返回false,反之true
	}

	private ImageView iv_more;

	private void setMoreListener() {
		iv_more = (ImageView) findViewById(R.id.iv_more);
		iv_more.setOnClickListener(new IvListener(iv_more,
				ReserveActivity.this, 0));
	}

	private String FeastDatetime;
	private Matcher m;
	private boolean flag = false;
	private int zhu;
	private int fuz;
	private OnClickListener reservation = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			if (checkLogin()) {
				System.out.println("没登录");

			} else {
				if (rb_Sex_Male.isChecked()) {
					sex = 1;
				} else {
					sex = 0;
				}
				String UserTel = (String) LocalStorage.get("UserTel");
				String RealName = edittext_reservation_name.getText()
						.toString().trim();
				String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
				Pattern p = Pattern.compile(regEx);
				m = p.matcher(RealName);
				final String sexa = sex + "";
				String Tel = edittext_reservation_phone.getText().toString();
				String DeliveryAddress = edittext_reservation_site.getText()
						.toString().trim();
				String MainSetQuantity = edittext_reservation_dinner.getText()
						.toString();
				if (!TextUtils.isEmpty(MainSetQuantity)) {
					zhu = Integer.parseInt(MainSetQuantity);
				}

				String SunSetQuantity = edittext_reservation_meal.getText()
						.toString();
				if (!TextUtils.isEmpty(SunSetQuantity)) {
					fuz = Integer.parseInt(SunSetQuantity);
				}
				Date today = new Date();
				SimpleDateFormat ss = new SimpleDateFormat("yyyy/MM/dd",
						Locale.CHINA);
				if (ss.format(today).equals(dateBtn.getText().toString())) {
					Toast.makeText(getApplicationContext(), "请选择一周之后的日期再进行预订！",
							Toast.LENGTH_SHORT).show();
					return;
				} else {
					FeastDatetime = dateBtn.getText().toString();
				}
				System.out.println(FeastDatetime + "!!!!!!!!!!!!!!#D$#C");

				String xstdz = ets_UserInfo_Sites.getText().toString();

				if (m.find()) {
					Toast.makeText(ReserveActivity.this, "姓名不允许输入特殊符号！",
							Toast.LENGTH_LONG).show();
					return;
				}
				if (RealName == null || "".equals(RealName)) {
					Toast.makeText(ReserveActivity.this, "请输入您的姓名",
							Toast.LENGTH_LONG).show();
					return;
				} else if (!Utils.isMobile(Tel)) {
					Toast.makeText(ReserveActivity.this, "输入手机号码有误，请重新输入",
							Toast.LENGTH_LONG).show();
					return;
				} else if (DeliveryAddress == null
						|| "".equals(DeliveryAddress)) {
					Toast.makeText(ReserveActivity.this, "请输入联系地址",
							Toast.LENGTH_LONG).show();
					return;

				} else if ((TextUtils.isEmpty(MainSetQuantity) && TextUtils
						.isEmpty(SunSetQuantity)) || (0 == zhu && 0 == fuz)) {
					Toast.makeText(ReserveActivity.this, "请输入主餐桌数",
							Toast.LENGTH_LONG).show();
					return;
				} else if ((TextUtils.isEmpty(SunSetQuantity) && TextUtils
						.isEmpty(MainSetQuantity)) || (0 == zhu && 0 == fuz)) {
					Toast.makeText(ReserveActivity.this, "请输入副餐桌数",
							Toast.LENGTH_LONG).show();
					return;
				} else if (DeliveryAddress.contains("<")
						|| DeliveryAddress.contains(">")) {
					Toast.makeText(ReserveActivity.this, "请不要输入‘<’ 或‘>’ ",
							Toast.LENGTH_LONG).show();
					return;
				} else if (TextUtils.isEmpty(feastTypy)) {
					Toast.makeText(ReserveActivity.this, "请选择喜宴类别",
							Toast.LENGTH_LONG).show();
					return;
				} else if ("".equals(xstdz)) {
					Toast.makeText(ReserveActivity.this, "请选择喜事堂",
							Toast.LENGTH_LONG).show();
					return;
				}

				Intent intent = new Intent();
				Bundle bundle = new Bundle();
				bundle.putString("Name", edittext_reservation_name.getText()
						.toString());
				bundle.putString("sex", sexa);
				System.out.println("====ReserveActivity====" + sexa);
				bundle.putString("cxName", series);
				bundle.putString("xyName", feastTypy);
				bundle.putString("xstAdd", ets_UserInfo_Sites.getText()
						.toString());
				bundle.putString("Tel", edittext_reservation_phone.getText()
						.toString());
				bundle.putString("DeliveryAdr", edittext_reservation_site
						.getText().toString());
				bundle.putString("MainCount", edittext_reservation_dinner
						.getText().toString());
				bundle.putString("SubCount", edittext_reservation_meal
						.getText().toString());
				bundle.putString("FastDateTime", dateBtn.getText().toString());
				bundle.putString("HallID", selectHallId);
				bundle.putInt("caiID", caiID);
				bundle.putInt("yanhuiID", yanhuiID);
				bundle.putString("date", date);
				bundle.putInt("dateID", dateID);
				bundle.putInt("jiaRu", 21);

				intent.putExtras(bundle);

				intent.setClass(ReserveActivity.this, ReserveWinActivity.class);
				startActivity(intent);
			}
		}

	};

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case 123:
			String result = data.getStringExtra("resultString");
			selectHallId = data.getStringExtra("selectHallId");
			ets_UserInfo_Sites.setText(result);
			break;
		default:
			break;
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (System.currentTimeMillis() - HomeActivity.dianJiShiJian > 500) {
				HomeActivity.dianJiShiJian = System.currentTimeMillis();
				ReserveActivity.this.finish();
				ActivityCollector.finishAll();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO Auto-generated method stub
		if (edittext_reservation_dinner.getText().toString().startsWith("0")) {
			edittext_reservation_dinner.setText("");
			Toast.makeText(getApplicationContext(), "主桌桌数首位不能为0，请重新输入！",
					Toast.LENGTH_SHORT).show();
		}
		if (edittext_reservation_meal.getText().toString().startsWith("0")) {
			edittext_reservation_meal.setText("");
			Toast.makeText(getApplicationContext(), "副桌桌数首位不能为0，请重新输入！",
					Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void afterTextChanged(Editable s) {
		// TODO Auto-generated method stub

	}
}
