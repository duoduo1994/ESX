package com.example.esycab;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.example.esycab.entity.ModeBean;
import com.example.esycab.network.SmartFruitsRestClient;
import com.example.esycab.utils.CustomDialog;
import com.example.esycab.utils.LocalStorage;
import com.eyoucab.list.CommonAdapter;
import com.eyoucab.list.ViewHolder;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import android.os.Bundle;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class QingTieXinXiActivity extends Activity {
	private TextView biaoTi, tv_mode_choosed;//tv_preview, ;
	private Button btn_preview, btn_send;
	private EditText et_name, et_tel, et_mname;
//	, et_brideroommname,et_bridemname, et_called, et_babyname, et_farthername,et_mothername,
//	et_shouxinname,et_leadername,et_shizhangname,et_juzhangname,et_banzhurenname,et_daoshiname,et_laoshiname;// et_locate,et_time,
	private ImageView iv_mode;
	private TextView  et_extime_qingtiexinxi,et_address_qingtiexinxi;
	private String name, tel, mname, orderID, time, chenghu="亲友";//
	static String locate, loId;
	private LinearLayout ll_preall;//rl_normal, rl_wedding1, rl_babyfeast,,rl_shouyan,ll_nianhui,ll_xieshi;
	private String s, leixing, type, extime;
	private PopupWindow pw;
	private ImageView iv_more;
	private ListView lv;
	private int itemid,choosed;
//	private Spinner spinner1;
	private Boolean anto = false;
	private int flag = 0,flag2=0;
	private Boolean sflag = false;
	CommonAdapter ca;
	CommonAdapter spa;
	List<String> splist;
	float dy, uy, zy;
	private List<ModeBean> list = new ArrayList<ModeBean>();

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
		setContentView(R.layout.activity_qingtie_xinxi);
		
		// 动态注册广播
		iv_more = (ImageView) findViewById(R.id.iv_more);
		iv_more.setVisibility(View.GONE);
		// iv_more.setOnClickListener(new IvListener(iv_more,
		// QingTieXinXiActivity.this));
		// if(!HomeActivity.quan){
		// rl = (RelativeLayout) findViewById(R.id.g_eng_d_uo); 
		// android.widget.LinearLayout.LayoutParams l =
		// (android.widget.LinearLayout.LayoutParams) rl.getLayoutParams();
		// l.topMargin = (int) (HomeActivity.a12);
		// rl.setLayoutParams(l);
		// }
		orderID = getIntent().getStringExtra("orderID");
		locate = getIntent().getStringExtra("DeliveryAddr");
		time = getIntent().getStringExtra("FeastDt");
		leixing = getIntent().getStringExtra("leixing");
		type = getIntent().getStringExtra("FeastOrd");
		biaoTi = (TextView) findViewById(R.id.tv_title);
		biaoTi.setText("请帖信息");
		initView();
		setEtListener();
		isWedding();
		et_address_qingtiexinxi.setText(locate);
//		initSpinnerData();
		initPPW();
		setOnclick();
		findViewById(R.id.btn_back).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				inputMethodManager.hideSoftInputFromWindow(
						QingTieXinXiActivity.this.getCurrentFocus()
								.getWindowToken(),
						InputMethodManager.HIDE_NOT_ALWAYS);
				QingTieXinXiActivity.this.finish();
			}
		});
	}

	private void setEtListener() {
		et_name.addTextChangedListener(new EtListener(et_name));
		et_extime_qingtiexinxi.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				CustomDialog customDialog=new CustomDialog(QingTieXinXiActivity.this, extime, new CustomDialog.OnCustomDialogListener() {
					
					@Override
					public void back(String name) {
						extime=name;
						et_extime_qingtiexinxi.setText(extime);
					}
				});
				customDialog.show();
			}
		});
	}

	LinearLayout ll;

	private void isWedding() {
		ll = (LinearLayout) findViewById(R.id.qingtie_fasong);
		if (leixing.equals("婚宴")) {
			ll.setWeightSum(0.26f);
			flag = 1;
		} else if (leixing.equals("满月宴")) {
			flag = 3;
		}else if(leixing.equals("生日宴")){
			flag = 6;
		}else if(leixing.equals("金榜题名谢师宴")){
			flag = 7;
		}else if(leixing.equals("乔迁之喜")){
			flag = 5;
			
		}
		else if(leixing.equals("公司年会宴")){
			flag = 4;
		}else {
			ll.setWeightSum(0.06f);
		}
		itemid=flag;
			
		}

	

	boolean isFirst = true;

String[] models={"婚宴模板一","婚宴模板二","宝宝宴模板","年会模板","乔迁宴模板","寿宴模板","谢师宴模板"};
	private void initPPW() {
		if(flag == 1){
			list.add(new ModeBean("婚宴模板一", BitmapFactory.decodeResource(
					getResources(), R.drawable.m1)));
			list.add(new ModeBean("婚宴模板二", BitmapFactory.decodeResource(
					getResources(), R.drawable.m2)));
		}else if(flag==4){
			list.add(new ModeBean("年会模板", BitmapFactory.decodeResource(
					getResources(), R.drawable.m5)));
		}else if(flag==5){
			list.add(new ModeBean("乔迁宴模板", BitmapFactory.decodeResource(
					getResources(), R.drawable.m4)));
		}else if(flag==6){
			list.add(new ModeBean("寿宴模板", BitmapFactory.decodeResource(
					getResources(), R.drawable.m6)));
		}else if(flag==7){
			list.add(new ModeBean("谢师宴模板", BitmapFactory.decodeResource(
					getResources(), R.drawable.m7)));
		}else if(flag==3){
			list.add(new ModeBean("宝宝宴模板", BitmapFactory.decodeResource(
					getResources(), R.drawable.m3)));
		}else{
			
		}
		ca = new CommonAdapter<ModeBean>(this, list, R.layout.item_lv) {

			@Override
			public void convert(ViewHolder helper, ModeBean item) {
				helper.setText(R.id.tv1, item.getName());
				helper.setImageBitmap(R.id.iv1, item.getBitmap());
			}
		};
		View v = LayoutInflater.from(this).inflate(R.layout.popupwindow, null);
		lv = (ListView) v.findViewById(R.id.lv);
		lv.setAdapter(ca);
		pw = new PopupWindow(v, LayoutParams.WRAP_CONTENT,
			LayoutParams.WRAP_CONTENT);
		pw.setBackgroundDrawable(new BitmapDrawable());

		pw.setFocusable(true);
		// 设置允许在外点击消失
		pw.setOutsideTouchable(true);
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				anto=true;
				pw.dismiss();
				flag2=arg2;
				itemid = flag+flag2;
				iv_mode.setVisibility(View.INVISIBLE);
				tv_mode_choosed.setText("已选择"+models[flag-1+flag2]);
				tv_mode_choosed.setVisibility(View.VISIBLE);
			}
		});
	}

	private void setOnclick() {
		ll_preall.setOnTouchListener(new OnTouchListener() {

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
		btn_preview.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				getEtData(); // xinliang
				boolean g=anto;
				boolean f=extime.equals("请选择时间");
//				System.out.println(a + "+++++++++++++" + b);
				if ((TextUtils.isEmpty(et_name.getText().toString()) || TextUtils.isEmpty(et_tel.getText().toString()))) {
					Toast.makeText(QingTieXinXiActivity.this, "请输入完整的请帖信息", 1).show();
				}else if(!g){
					Toast.makeText(QingTieXinXiActivity.this, "请选择模板", 1).show();
				}else if(f){
					Toast.makeText(QingTieXinXiActivity.this, "请选择喜宴时间", 1).show();
				}else {
					s = name + "#" + tel + "#" + mname + "#" + locate + "#"
							+ time + "#" + itemid + "#" + orderID + "#"
							+ chenghu + "#" + type;
					// TODO Auto-generated method stub
					Intent intent = new Intent(QingTieXinXiActivity.this,
							QingTiePreviewActivity.class);
					intent.putExtra("data", s);
					intent.putExtra("extime", extime);
					intent.putExtra("isWedding", flag);
					startActivity(intent);
				}
			}
		});
		// 发送键的监听
		btn_send.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				getEtData();
				boolean f=extime.equals("请选择时间");
//				System.out.println(a + "+++++++++++++" + b);
				if ((TextUtils.isEmpty(et_name.getText().toString()) || TextUtils
						.isEmpty(et_tel.getText().toString()))) {
					Toast.makeText(QingTieXinXiActivity.this, "请输入完整的请帖信息", 1)
							.show();
				}else if(!anto){
					Toast.makeText(QingTieXinXiActivity.this, "请选择喜宴模板", 1).show();
				}else if(f){
					Toast.makeText(QingTieXinXiActivity.this, "请选择喜宴时间", 1).show();
				} else {
					s = name + "#" + tel + "#" + mname + "#" + locate + "#"
							 + time+" "+extime + "#" + itemid + "#" + orderID + "#"
							+ chenghu + "#" + type;
					Log.i("==================", s);
					getEtData();
					sendSMS(tel);
					if (sflag) {// 判断是否发送成功
						// postEtData();
						Intent intent = new Intent(QingTieXinXiActivity.this,
								QingTiePreviewShortActivity.class);
						intent.putExtra("data", s);
						intent.putExtra("extime", extime);
						intent.putExtra("isWedding", flag);
						et_name.setText("");
						et_tel.setText("");
						startActivity(intent);
					}
				}

			}
		});
//		tv_preview.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				Intent intent = new Intent(QingTieXinXiActivity.this,
//						WoDeQingTieActivity.class);
//				intent.putExtra("OrderID", orderID);
//				intent.putExtra("Tel",
//						LocalStorage.get(QingTieXinXiActivity.this, "UserTel"));
//				startActivity(intent);
//			}
//		});
		// 选择模板
		iv_mode.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				pw.showAsDropDown(iv_mode);
				
				
			}
		});
		tv_mode_choosed.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				pw.showAsDropDown(iv_mode);
			}
		});

	}

	private void initView() {
		et_address_qingtiexinxi = (TextView) findViewById(R.id.et_address_qingtiexinxi);
		et_extime_qingtiexinxi = (TextView) findViewById(R.id.et_extime_qingtiexinxi);
		ll_preall = (LinearLayout) findViewById(R.id.ll_all_pre);
//		tv_preview = (TextView) findViewById(R.id.tv_preview);
		btn_preview = (Button) findViewById(R.id.btn_preview_qingtiexinxi);
		btn_send = (Button) findViewById(R.id.btn_send_qingtiexinxi);
		et_name = (EditText) findViewById(R.id.et_name_qingtiexinxi);
		et_tel = (EditText) findViewById(R.id.et_tel_qingtiexinxi);
		iv_mode = (ImageView) findViewById(R.id.et_qingtiemode_qingtiexinxi);
		tv_mode_choosed = (TextView) findViewById(R.id.tv_mode_choosed);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			QingTieXinXiActivity.this.finish();
		}
		return super.onKeyDown(keyCode, event);
	}

	public void getEtData() {
		extime = et_extime_qingtiexinxi.getText().toString();
		name = et_name.getText().toString();
		tel = et_tel.getText().toString();
		s = name + "#" + tel + "#" + mname + "#" + locate + "#" + time + "#"
				+ itemid + "#" + orderID + "#" + chenghu + "#" + type;
	}


	public void sendSMS(String phonenumber) {// 发送短信的类
		SmsManager smsManager = SmsManager.getDefault();
		Pattern pattern = Pattern.compile("^[1][3-8][0-9]{9}$");
		Matcher number = pattern.matcher(phonenumber);
//		String datas = orderID + "#" + tel + "#" + time+" "+extime + "#" + name + "#"
//				+ "mname" + "#"+ LocalStorage.get(QingTieXinXiActivity.this, "UserTel") + "#"
//				+ itemid;
//		Log.i("dadadadadadadadad", datas);
//		s = name + "#" + tel + "#" + mname + "#" + locate + "#" + time + "#"
//				+ itemid + "#" + orderID + "#" + chenghu + "#" + type;
//		String massage = "各位亲友，我将于" + time +" "+ extime+"举办" + type + "啦！欢迎您携家人，在"
//				+ locate + "参加我的喜宴！";
		if (!number.matches()) {
			sflag = false;
			Toast.makeText(QingTieXinXiActivity.this, "号码输入不合法，请重新输入", 1)
					.show();
			et_tel.setText("");
		} else {
			sflag = true;
//			List<String> contentsList = smsManager.divideMessage(massage);
//			Intent itSend = new Intent(SENT_SMS_ACTION);
//			itSend.putExtra("post_data", datas);
//			PendingIntent PI = PendingIntent.getBroadcast(
//					getApplicationContext(), 1, itSend,
//					PendingIntent.FLAG_ONE_SHOT);
//			for (String string : contentsList) {
//				smsManager.sendTextMessage(phonenumber, null, string, PI, null);
//			}
			// showWeixinDialog();
		}
	}

		// Intent intent = new Intent(Intent.ACTION_SEND);
		// intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		// intent.putExtra(Intent.EXTRA_STREAM,
		// Uri.parse(Environment.getExternalStorageDirectory()+tel+name+".jpg"));//
		// uri为你的附件的uri
		// intent.putExtra("subject", "我们的请帖"); //彩信的主题
		// intent.putExtra("address", "18326895601"); //彩信发送目的号码
		// intent.putExtra("sms_body", "欢迎来到我的婚礼！！！！"); //彩信中文字内容
		// intent.putExtra(Intent.EXTRA_TEXT, "it's EXTRA_TEXT");
		// intent.setType("image/*");// 彩信附件类型
		// intent.setClassName("com.android.mms","com.android.mms.ui.ComposeMessageActivity");
		// startActivity(intent);



	public static String StringFilter(String str) throws PatternSyntaxException {
		String regEx = "[?><;:'|_~`!@#$%^&*,.，。、= ]"; // 要过滤掉的字符
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.replaceAll("").trim();
	}

	class EtListener implements TextWatcher {

		private EditText et;

		public EtListener(EditText editText) {
			this.et = editText;
		}

		@Override
		public void afterTextChanged(Editable arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {
			String t = et.getText().toString();
			String editable = et.getText().toString();
			String str = StringFilter(editable.toString());
			if (!editable.equals(str)) {
				et.setText(str);
				et.setSelection(str.length()); // 光标置后

			}

		}

	}
	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		et_name.requestFocus();
		super.onRestart();
	} 
}
