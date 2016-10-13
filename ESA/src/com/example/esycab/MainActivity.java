package com.example.esycab;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.esycab.network.SmartFruitsRestClient;
import com.example.esycab.utils.ActivityCollector;
import com.example.esycab.utils.LoadingDialog;
import com.example.esycab.utils.LocalStorage;
import com.example.esycab.utils.MD5Util;
import com.example.esycab.utils.ProConst;
import com.example.esycab.utils.StringUtils;
import com.eyoucab.list.Utils;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * 登入
 * 
 * @author Administrator
 * 
 */
public class MainActivity extends Activity implements ProConst {

	private EditText edittext_login_account;
	private EditText edittext_login_password;
	private Button btn_register;// 注册按钮
	private Button btn_login_login;// 登陆按钮
	private TextView text_login_findpass;
	private ImageView checkBox1;// 显示密码
	private int flag = -1;
	private String UserPass;
	private String UserTel;
	private int dengRu;
	private LinearLayout ll;
	private float dy, uy;
	static Activity a;
	public static int a5;

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
		setContentView(R.layout.login);
		ActivityCollector.addActivity(this);
		LocalStorage.initContext(this);
		a = MainActivity.this;
		Intent intent = this.getIntent();
		flag = intent.getIntExtra("flag", -1);
		dengRu = intent.getIntExtra("dengRu", 0);
		ll = (LinearLayout) findViewById(R.id.deng_lu_scrollview);
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
		edittext_login_account = (EditText) findViewById(R.id.edittext_login_account);// ///
		String mobile = (String) LocalStorage.get("UserTel");
		edittext_login_account.setText(mobile);
		edittext_login_password = (EditText) findViewById(R.id.edittext_login_password);// //
//		String password = (String) LocalStorage.get("UserPass");
		// edittext_login_password.setText(password);

		btn_login_login = (Button) findViewById(R.id.btn_login_login);
		btn_register = (Button) findViewById(R.id.btn_register);
		text_login_findpass = (TextView) findViewById(R.id.text_login_findpass);

		edittext_login_account.addTextChangedListener(watcher);

		// 登陆
		btn_login_login.setOnClickListener(myLogin);
		// 注册用户
		btn_register.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (flag == 5) {
					a5 = 5;
					Intent intent = new Intent();
					intent.setClass(MainActivity.this, RegisterActivity.class);
					startActivity(intent);
				} else {
					a5 = 1;
					Intent intent = new Intent();
					intent.setClass(MainActivity.this, RegisterActivity.class);
					startActivity(intent);
					MainActivity.this.finish();
				}
			}
		});

		// 找回密码
		text_login_findpass.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				Bundle bundle = new Bundle();
				bundle.putString("UserTel", edittext_login_account.getText()
						.toString());
				intent.putExtras(bundle);
				intent.setClass(MainActivity.this, FindPassActivity.class);
				startActivity(intent);
			}
		});

		checkBox1 = (ImageView) findViewById(R.id.checkBox1);
		checkBox1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (isChecked) {
					// 如果选中，显示密码
					Toast.makeText(MainActivity.this, "密码最大15位数",
							Toast.LENGTH_SHORT).show();
					checkBox1.setImageResource(R.drawable.after_eye);
					isChecked = false;
					edittext_login_password
							.setTransformationMethod(HideReturnsTransformationMethod
									.getInstance());
				} else {
					// 否则隐藏密码
					checkBox1.setImageResource(R.drawable.before_eye);
					isChecked = true;
					edittext_login_password
							.setTransformationMethod(PasswordTransformationMethod
									.getInstance());
				}
			}
		});
	}

	private TextWatcher watcher = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {
			// TODO Auto-generated method stub
			edittext_login_password.setText("");
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

	private boolean isChecked = true;
	// 加载对话框
	private LoadingDialog dialog = null;
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				if (dialog != null) {
					dialog.closeDialog();
				}
				break;
			default:
				break;
			}
		}
	};
//	private SharedPreferences preferences;
//	private Editor editor;
	// 登陆
	private OnClickListener myLogin = new OnClickListener() {

		@Override
		public void onClick(View v) {

			login();
		}
	};

	private void login() {

		UserTel = edittext_login_account.getText().toString();
		UserPass = edittext_login_password.getText().toString();

		if (!Utils.isMobile(UserTel) || StringUtils.isEmpty(UserPass)) {
			Toast.makeText(MainActivity.this, "用户名或密码不能为空或有误",
					Toast.LENGTH_SHORT).show();
			return;
		}
		dialog = new LoadingDialog(MainActivity.this, "正在登陆，请稍候...");
		dialog.showDialog();
		// TODO Auto-generated method stub
		RequestParams params = new RequestParams();
		String passEnt = MD5Util.string2MD5(UserPass);
		params.add("UserTel", UserTel);
		params.add("CustPhyAddr", HomeActivity.strUniqueId);
		params.add("UserPass", passEnt);
		// 保存登陆用户
		SmartFruitsRestClient.post("LoginCheckHandler.ashx?Action=UserLogin",
				params, new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
						// TODO Auto-generated method stub
						try {
							System.out.println("！！！！！！！登录");
							handler.sendEmptyMessage(1);
							String result = new String(arg2, "utf-8");
							System.out.println("logsin" + result);
							if (!result.equals("-1")
									&& !TextUtils.isEmpty(result)) {
								Toast.makeText(MainActivity.this,
										getString(R.string.login_btn_login),
										Toast.LENGTH_SHORT).show();
								JSONObject jsonObject = new JSONObject(result
										.trim());
								System.out.println("jsonObject=" + jsonObject);
								JSONArray jsArray = jsonObject
										.getJSONArray("用户信息");
								JSONObject json = jsArray.getJSONObject(0);
								LocalStorage.set("UserTel", UserTel);
								// Toast.makeText(MainActivity.this,
								// LocalStorage.get("UserTel").toString(),
								// Toast.LENGTH_LONG).show();
								System.out.println("FTYUIK"
										+ LocalStorage.get("UserPass")
												.toString());

								LocalStorage.set("UserPass", UserPass);
								System.out.println("FTYUIK"
										+ LocalStorage.get("UserPass")
												.toString());
								LocalStorage.set("LoginStatus", "login");
								System.out.println(LocalStorage
										.get("LoginStatus"));
								LocalStorage.set("Sex", json.getString("Sex"));
								if (!TextUtils.isEmpty(json
										.getString("BornDateTime"))) {
									SimpleDateFormat sim1 = new SimpleDateFormat(
											"yyyy/MM/dd HH:mm:ss");
									SimpleDateFormat sim2 = new SimpleDateFormat(
											"yyyy-MM-dd");
									String s = json.getString("BornDateTime")
											.replace("\\", "");
									Date d = sim1.parse(s);
									System.out.println("jsArray="
											+ sim2.format(d));
									LocalStorage.set("BornDateTime",
											sim2.format(d));
								}
								if (!TextUtils.isEmpty(json
										.getString("ImageUrl"))) {
									String ss = json.getString("ImageUrl")
											.replace("\\", "").trim();
									System.out.println("ss=" + ss);
									LocalStorage.set("ImageUrl", ss);
								}
								authority = json.getString("Authority");
								System.out.println("=============" + authority);
								LocalStorage.set("UpdateDT",
										json.getString("UpdateDT"));
								LocalStorage.set("Authority",
										json.getString("Authority"));
								LocalStorage.set("HomeAddress",
										json.getString("HomeAddress"));
								LocalStorage.set("NowAddress",
										json.getString("NowAddress"));
								LocalStorage.set("RealName",
										json.getString("RealName"));
								LocalStorage.set("NickName",
										json.getString("NickName"));
								if (flag == 1) {
									Intent resultIntent = new Intent();
									MainActivity.this.setResult(RESULT_OK,
											resultIntent);
									finish();
								} else if (flag == 5) {
									Intent data = new Intent();
									System.out.println(1);
									System.out.println(json
											.getString("NickName") + "!!!!!");

									data.putExtra("Authority", authority);
									data.putExtra("resultString",
											json.getString("NickName"));
									MainActivity.this.setResult(123, data);
									finish();
								}  else if (dengRu == 45) {
									System.out.println("denglu5");
									MainActivity.this.finish();
								}else {
									startActivity(new Intent(MainActivity.this,
											HomeActivity.class));
									MainActivity.this.finish();
								}
							} else if (result.equals("-1")) {
								Toast.makeText(MainActivity.this,
										getString(R.string.login_failed),
										Toast.LENGTH_SHORT).show();
							}
						} catch (Exception e) {
							handler.sendEmptyMessage(1);
							System.out.println(123456789 + "," + e);
							e.printStackTrace();
							// Toast.makeText(MainActivity.this,
							// e.getMessage(), Toast.LENGTH_SHORT)
							// .show();
						}
					}

					@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
							Throwable arg3) {
						if (dialog != null) {
							dialog.closeDialog();
						}
						handler.sendEmptyMessage(1);
						// TODO Auto-generated method stub
						System.out.println(arg3 + "asdfgdhgsa");
						Toast.makeText(MainActivity.this,
								getString(R.string.conn_failed),
								Toast.LENGTH_SHORT).show();
						return;
					}

				});

	}

//	private boolean isFalse = true;
//	private int ciShu = 0;

	private String authority = "null";

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			System.out.println("HJJJJJ" + flag);

			if (flag == 5) {
				Intent data = new Intent();
				data.putExtra("Authority", authority);
				data.putExtra("resultString", "亲，该咋称呼您~");
				MainActivity.this.setResult(123, data);
			}
				finish();
		}
		return super.onKeyDown(keyCode, event);
	}

}