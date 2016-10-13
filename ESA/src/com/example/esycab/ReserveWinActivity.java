package com.example.esycab;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.Header;
import org.json.JSONObject;

import com.example.esycab.Fragment.CalendarFragment;
import com.example.esycab.ab.view.ToDoItem;
import com.example.esycab.entity.AnLi;
import com.example.esycab.network.SmartFruitsRestClient;
import com.example.esycab.utils.ACache;
import com.example.esycab.utils.ActivityCollector;
import com.example.esycab.utils.IvListener;
import com.example.esycab.utils.LocalStorage;
import com.example.esycab.utils.ProConst;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 预定成功页
 * 
 * @author Administrator
 * 
 */
public class ReserveWinActivity extends Activity implements ProConst {
	private String selectHallId;// 喜事堂ID
	// private TextView order_reference;// 訂單
	private TextView edittext_reservation_name;// 预定姓名
	private EditText tv_reservation_name;// 预定姓名
	private TextView edittext_reservation_phone;// 预定电话
	private EditText tv_reservation_phone;// 预定电话
	private TextView edittext_reservation_site;// 预定地址
	private EditText tv_reservation_site;// 预定地址
	private TextView edittext_reservation_dinner;// 预定主餐
	private EditText tv_reservation_dinner;// 预定主餐
	private TextView edittext_reservation_meal;// 预定副餐
	private EditText tv_reservation_meal;// 预定副餐
	private TextView edittext_reservation_dateBtna;// 预定时间
	private TextView av_UserInfos_zhucanshijian;// 主餐时间
	private TextView edittext_reservation_sex;// 显示性别=================================
//	private RadioGroup rg_UserInfo_Sex;// 性别
	private Button btn_Edit, xgxst;
	private ToDoItem toDoItema;
	private Button dateBtn; // 事件日期和时间
	private SimpleDateFormat sfDate;
	// private TableLayout alertTableLayout;
	private Button reserva;
//	private Context context;
	private int Feast = 1;
	private String Name = "";
	int jiaRu;
	private String Tel = "";
	private String DeliveryAdr = "";
	private String MainCount = "";
	private String SubCount = "";
	private String FastDateTime = "";
	private String Sex = "";
	private String caixiName = "";
	private String xyName = "";
	private String xstAdd = "";
	private int a;
	private String OderNo;
	// 定义改变后的值
	private String names;
	private String tel;
	private String address;
	private String zhucan;
	private String fucan;
	private String times = "";
	private int sexa;// 默认选择男
	private String t1;
	private int caiID, yanhuiID, dateID;
	private String strUniqueId = HomeActivity.strUniqueId;
	private float dy, uy, zy;

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
		setContentView(R.layout.order_form_a);
		ActivityCollector.addActivity(this);
		// RelativeLayout rl;
		// if(!HomeActivity.quan){
		// rl = (RelativeLayout) findViewById(R.id.g_eng_d_uo);
		// android.widget.LinearLayout.LayoutParams l =
		// (android.widget.LinearLayout.LayoutParams) rl.getLayoutParams();
		// l.topMargin = (int) (HomeActivity.a12);
		// rl.setLayoutParams(l);
		// }
		LocalStorage.initContext(this);
		OderNo = LocalStorage.get("OderNo").toString();
		Intent intent = this.getIntent();
		Bundle bundle = intent.getExtras();
		if (bundle != null) {
			Name = bundle.getString("Name");
			Tel = bundle.getString("Tel");
			DeliveryAdr = bundle.getString("DeliveryAdr");
			MainCount = bundle.getString("MainCount");
			SubCount = bundle.getString("SubCount");
			FastDateTime = bundle.getString("FastDateTime");

			caixiName = bundle.getString("cxName");
			xyName = bundle.getString("xyName");
			jiaRu = bundle.getInt("jiaRu");
			xstAdd = bundle.getString("xstAdd");
			selectHallId = bundle.getString("HallID");
			caiID = bundle.getInt("caiID");
			yanhuiID = bundle.getInt("yanhuiID");
			date = bundle.getString("date");
			dateID = bundle.getInt("dateID");
			System.out.println(caixiName + "UJ" + xyName + "TYUIK" + xstAdd);
			// ==============================================================================================//
			Sex = bundle.getString("sex");

			edittext_reservation_sex = (TextView) findViewById(R.id.tv_UserInfo_Sex);
			if (Sex.equals("0")) {
				edittext_reservation_sex.setText("女");
				RadioButton woman = (RadioButton) findViewById(R.id.ra_Sex_Woman);
				woman.setChecked(true);
				sexa = 0;
			} else {
				edittext_reservation_sex.setText("男");
				RadioButton man = (RadioButton) findViewById(R.id.ra_Sex_Male);
				man.setChecked(true);

				sexa = 1;
			}

			System.out.println("====ReserveWinActivity=====" + Sex
					+ "*************************");

			// ==============================================================================================//
		}
//		lll = (LinearLayout) findViewById(R.id.yuding_chenggong_scrollview);
		edittext_reservation_name = (TextView) findViewById(R.id.av_UserInfo_Name);
		tv_reservation_name = (EditText) findViewById(R.id.tv_UserInfo_Name);
		edittext_reservation_phone = (TextView) findViewById(R.id.av_UserInfo_Age);
		tv_reservation_phone = (EditText) findViewById(R.id.tv_UserInfo_Age);
		edittext_reservation_site = (TextView) findViewById(R.id.av_UserInfo_Site);
		tv_reservation_site = (EditText) findViewById(R.id.tv_UserInfo_Site);
		edittext_reservation_dinner = (TextView) findViewById(R.id.av_UserInfo_Table);
		tv_reservation_dinner = (EditText) findViewById(R.id.tv_UserInfo_Table);
		edittext_reservation_meal = (TextView) findViewById(R.id.av_UserInfo_FTable);
		tv_reservation_meal = (EditText) findViewById(R.id.tv_UserInfo_FTable);
		edittext_reservation_dateBtna = (TextView) findViewById(R.id.av_dateBtnaa);
//		rg_UserInfo_Sex = (RadioGroup) findViewById(R.id.rg_UserInfo_Sexa);
		avs_UserInfo_Sites = (TextView) findViewById(R.id.avs_UserInfo_Sites);
		avs_UserInfo_Site = (TextView) findViewById(R.id.avs_UserInfo_Site);
		av_UserInfos_Site = (TextView) findViewById(R.id.av_UserInfos_Site);
		tvs_UserInfo_Sites = (EditText) findViewById(R.id.tvs_UserInfo_Sites);
		av_UserInfos_zhucanshijian = (TextView) findViewById(R.id.av_UserInfos_zhucanshijian);
																								// shijian

		xgxst = (Button) findViewById(R.id.xgxst);
		xgxst.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(ReserveWinActivity.this,
						WeddinghallActivity.class);
				intent.putExtra("yuding", 1);
				startActivityForResult(intent, 123);

			}
		});

//		tvs_UserInfo_Site = (Spinner) findViewById(R.id.tvs_UserInfo_Site);// xiyan
//																			// leibie
//		tv_UserInfos_Site = (Spinner) findViewById(R.id.tv_UserInfos_Site);// caixi
		// ==============================================================================================//
		edittext_reservation_sex = (TextView) findViewById(R.id.tv_UserInfo_Sex);
		if (Sex.equals("0")) {
			edittext_reservation_sex.setText("女");
			sexa = 0;
		} else {
			edittext_reservation_sex.setText("男");
			sexa = 1;
		}
		// ==============================================================================================//

		reserva = (Button) findViewById(R.id.ensure);
		reserva.setOnClickListener(servatn);// 监听servatn
		dateBtn = (Button) findViewById(R.id.dateBtnaa);
		btn_Edit = (Button) findViewById(R.id.reserva_amend);
		btn_Edit.setOnClickListener(reservatn);
		avs_UserInfo_Sites.setText(xstAdd);
		avs_UserInfo_Site.setText(xyName);
		av_UserInfos_Site.setText(caixiName);
		tvs_UserInfo_Sites.setText(xstAdd);
		edittext_reservation_name.setText(Name);
		tv_reservation_name.setText(Name);
		edittext_reservation_phone.setText(Tel);
		tv_reservation_phone.setText(Tel);
		edittext_reservation_site.setText(DeliveryAdr);
		tv_reservation_site.setText(DeliveryAdr);
		edittext_reservation_dinner.setText(MainCount);
		tv_reservation_dinner.setText(MainCount);
		edittext_reservation_meal.setText(SubCount);
		tv_reservation_meal.setText(SubCount);
		edittext_reservation_dateBtna.setText(FastDateTime);
		av_UserInfos_zhucanshijian.setText(date);
		dateBtn = (Button) findViewById(R.id.dateBtnaa);
		sfDate = new SimpleDateFormat("yyyy/MM/dd", Locale.CHINA);
		Date date;
		try {
			date = sfDate.parse(FastDateTime);
			Bundle extras = getIntent().getExtras();
			if (extras == null || extras.get("") == null) {
				// 创建task为空的ToDoItem对象
				toDoItema = new ToDoItem("");
				// 新建待办事项，将界面中事件时间设置为当前时间
				dateBtn.setText(FastDateTime);

				toDoItema.setTime(date); // 设置ToDoItem事件时间
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 处理事件日期点击事件
		dateBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				DialogFragment calendarFragment = new CalendarFragment(
						toDoItema.getTime(), null, null, 0, null) {

					@Override
					public void onDateSet(DatePicker view, int year,
							int monthOfYear, int dayOfMonth) {
						super.onDateSet(view, year, monthOfYear, dayOfMonth);
						toDoItema.setTime(year, monthOfYear, dayOfMonth);
						Date date = toDoItema.getTime();
						dateBtn.setText(sfDate.format(date));

					}

				};
				calendarFragment.show(getFragmentManager(), "calendarPcker");
				// Toast.makeText(getApplicationContext(),
				// toDoItema.getTime().toString(), Toast.LENGTH_LONG)
				// .show();
			}
		});
//		ra_Sex_Male
//				.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//					@Override
//					public void onCheckedChanged(CompoundButton buttonView,
//							boolean isChecked) {
//						// three_star.setChecked(true);
//						if (isChecked) {
//							sexa = 1;
//						}
//					}
//				});
//		ra_Sex_Woman
//				.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//					@Override
//					public void onCheckedChanged(CompoundButton buttonView,
//							boolean isChecked) {
//						if (isChecked) {
//							sexa = 0;
//						}
//					}
//				});
		// ==============================================================================================//
		t1 = tv_reservation_name.getText().toString();// 使光标位于名字最后面
		tv_reservation_name.setText(t1);
		tv_reservation_name.setSelection(t1.length());

		tv_reservation_name.setOnKeyListener(new OnKeyListener() {// 使姓名不能有空格

					@Override
					public boolean onKey(View arg0, int arg1, KeyEvent arg2) {

						switch (arg1) {
						case 62:
							t1 = t1.trim();
							tv_reservation_name.setText(t1);
							tv_reservation_name.setSelection(t1.length());
							Toast.makeText(ReserveWinActivity.this,
									"姓名不允许包含空格，请重新输入！", Toast.LENGTH_LONG)
									.show();
							break;

						default:
							break;
						}

						return false;
					}

				});
		// ==============================================================================================//

		setMoreListener();
	}

	private ImageView iv_more;

	private void setMoreListener() {
		iv_more = (ImageView) findViewById(R.id.iv_more);
		iv_more.setOnClickListener(new IvListener(iv_more,
				ReserveWinActivity.this, 0));
	}

	private TextView avs_UserInfo_Sites, avs_UserInfo_Site, av_UserInfos_Site;
	private EditText tvs_UserInfo_Sites;
	private OnClickListener reservatn = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			ReserveWinActivity.this.finish();
			// if (isSave)// 当前处于保存模式
			// {
			// // 点击之后 修改当前模式
			// isSave = false;
			// // tryc();
			// // 修改按钮显示
			// // btn_Edit.setText("保存");
			// // 控制TextView和EditText的显示
			// avs_UserInfo_Sites.setVisibility(View.GONE);// 不可见且不占用空间
			// tvs_UserInfo_Sites.setVisibility(View.VISIBLE);// 可见
			// xgxst.setVisibility(View.VISIBLE);
			//
			// avs_UserInfo_Site.setVisibility(View.GONE);// 不可见且不占用空间
			// tvs_UserInfo_Site.setVisibility(View.VISIBLE);// 可见
			//
			// av_UserInfos_Site.setVisibility(View.GONE);// 不可见且不占用空间
			// tv_UserInfos_Site.setVisibility(View.VISIBLE);// 可见
			//
			// av_UserInfos_zhucanshijian.setVisibility(View.GONE);
			// tv_UserInfos_zhucanshijian.setVisibility(View.VISIBLE);
			//
			// tv_reservation_name.setVisibility(View.VISIBLE);// 不可见且不占用空间
			// edittext_reservation_name.setVisibility(View.GONE);// 可见
			// tv_reservation_phone.setVisibility(View.VISIBLE);// 不可见且不占用空间
			// edittext_reservation_phone.setVisibility(View.GONE);// 可见
			// tv_reservation_site.setVisibility(View.VISIBLE);// 不可见且不占用空间
			// edittext_reservation_site.setVisibility(View.GONE);// 可见
			// tv_reservation_dinner.setVisibility(View.VISIBLE);// 不可见且不占用空间
			// edittext_reservation_dinner.setVisibility(View.GONE);// 可见
			// tv_reservation_meal.setVisibility(View.VISIBLE);// 不可见且不占用空间
			// edittext_reservation_meal.setVisibility(View.GONE);// 可见
			// dateBtn.setVisibility(View.VISIBLE);// 不可见且不占用空间
			// edittext_reservation_dateBtna.setVisibility(View.GONE);// 可见
			// rg_UserInfo_Sex.setVisibility(View.VISIBLE);
			// edittext_reservation_sex.setVisibility(View.GONE);
			//
			// }

		}

	};

	private OnClickListener servatn = new OnClickListener() {

		@Override
		public void onClick(View arg0) {

			// 发送报文过去
			String sexaa = sexa + "";
			String FeastDatetime = dateBtn.getText().toString();
			// 如果不相同就说明修改了 ，相同就说明没有修改

			if (!edittext_reservation_name.getText().equals(
					tv_reservation_name.getText())) {
				a = 1;
				names = tv_reservation_name.getText().toString();
			} else {
				names = edittext_reservation_name.getText().toString();
			}
			String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
			Pattern p = Pattern.compile(regEx);
			Matcher m = p.matcher(names);
			if (!edittext_reservation_phone.getText().equals(
					tv_reservation_phone.getText())) {

				tel = tv_reservation_phone.getText().toString();
				a = 1;
			} else {
				tel = edittext_reservation_phone.getText().toString();
			}
			if (!edittext_reservation_site.getText().equals(
					tv_reservation_site.getText())) {
				address = tv_reservation_site.getText().toString();
				a = 1;
			} else {
				address = edittext_reservation_site.getText().toString();
			}
			if (!edittext_reservation_dinner.getText().equals(
					tv_reservation_dinner.getText())) {
				zhucan = tv_reservation_dinner.getText().toString();
				a = 1;
			} else {
				zhucan = edittext_reservation_dinner.getText().toString();

			}
			if (!edittext_reservation_meal.getText().equals(
					tv_reservation_meal.getText())) {
				fucan = tv_reservation_meal.getText().toString();
				a = 1;
			} else {
				fucan = edittext_reservation_meal.getText().toString();
			}

			// bianhao = order_reference.getText().toString();

			// 发送数据
			/**
			 * http://192.168.0.203:8081/ashx/BookHandler.ashx?
			 * Action=bookEdit&Name=000&sex=0（性别0：女 1男）
			 * &Tel=15557250731&DeliveryAdr=地址1&MainCount=1(主桌数)
			 * &SubCount=1(副桌数)&FastDateTime=2016/02/02(宴席时间)
			 * &OderNo=16042200000084630995(订单号)T
			 */

			String UserTel = (String) LocalStorage.get("UserTel");
			String feastCgy = String.valueOf(yanhuiID + 1);
			String fkDinnerTimeID = String.valueOf(dateID + 1);
			String Cuisine = String.valueOf(caiID + 1);

			RequestParams params = new RequestParams();
			params.add("fkCusTel", UserTel);
			params.add("Name", names);
			if (!"".equals(zhucan)) {
				params.add("MainCount", zhucan);
			}
			if (!"".equals(fucan)) {
				params.add("SubCount", fucan);
			}

			params.add("DeliveryAddr", address);
			params.add("Tel", tel);
			params.add("Cuisine", Cuisine);
			params.add("FeastCgy", feastCgy);
			params.add("HallID", selectHallId);
			params.add("fkDinnerTimeID", fkDinnerTimeID);
			params.add("FeastDateTime", FeastDatetime);
			params.add("Sex", sexaa);
			params.add("CustPhyAddr", strUniqueId);

			System.out.println(UserTel + "," + names + "," + Cuisine + ","
					+ feastCgy + "," + selectHallId + "," + sexa + "," + OderNo
					+ "," + Tel + address + "," + zhucan + "," + fucan + ","
					+ FeastDatetime + "," + fkDinnerTimeID + "," + strUniqueId);

			// 三个值，性别int 时间 编号
			SmartFruitsRestClient.post("BookHandler.ashx?Action=book", params,
					new AsyncHttpResponseHandler() {

						@Override
						public void onSuccess(int arg0, Header[] arg1,
								byte[] arg2) {
							// TODO Auto-generated method stub
							String result;
							try {
								result = new String(arg2, "utf-8");
								System.out.println("ReserveWinActivity,"
										+ result);
								JSONObject json = new JSONObject(result.trim());

								String str = json.getString("提示");
								if (str != null) {
									if (ReserveWinActivity.this.isFinishing()) {
									} else {
										d = new Dialog(ReserveWinActivity.this,
												R.style.loading_dialog);
										v = LayoutInflater.from(
												ReserveWinActivity.this)
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
										juTiXinXi.setText(str);
										tiShi.setText("提示");
										p1.setText("确定");
										n.setText("返回");
										p1.setOnClickListener(new OnClickListener() {

											@Override
											public void onClick(View arg0) {
												// TODO Auto-generated method
												// stub
												fin();
												d.dismiss();
											}
										});
										n.setOnClickListener(new OnClickListener() {

											@Override
											public void onClick(View arg0) {
												// TODO Auto-generated method
												// stub
												// System.out.println(a);
												d.dismiss();
											}
										});
										d.show();
									}

								}
							} catch (Exception e) {
								// Toast.makeText(ReserveWinActivity.this,
								// e.getMessage(), Toast.LENGTH_SHORT)
								// .show();
							}

						}

						@Override
						public void onFailure(int arg0, Header[] arg1,
								byte[] arg2, Throwable arg3) {
							// TODO Auto-generated method stub
							System.out.println(arg3 + "asdfgdhgsa");
							Toast.makeText(ReserveWinActivity.this,
									getString(R.string.conn_failed),
									Toast.LENGTH_SHORT).show();
						}
					});

		}

	};
	private TextView juTiXinXi, tiShi, fuZhi;
	private Dialog d;
	private Button p1;
	private Button n;
	private View v;

	public void fin() {
		ReserveWinActivity.this.finish();
		ReserveActivity.ssthis.finish();
	}

	private void goback(View view) {
		Intent intent = new Intent(ReserveWinActivity.this, HomeActivity.class);
		startActivity(intent);
		finish();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case 123:
			String result = data.getStringExtra("resultString");
			selectHallId = data.getStringExtra("selectHallId");
			tvs_UserInfo_Sites.setText(result);
			break;
		default:
			break;

		}

	}

	private AnLi anli;
	private JSONObject tJson = null;
	private List<AnLi> lal = new ArrayList<AnLi>();
	private List<String> ls = new ArrayList<String>(),
			ll = new ArrayList<String>(), ld = new ArrayList<String>();
	private String series, feastTypy, date;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (System.currentTimeMillis() - HomeActivity.dianJiShiJian > 500) {
				HomeActivity.dianJiShiJian = System.currentTimeMillis();
				ReserveWinActivity.this.finish();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
