package com.example.esycab;

import java.util.Timer;

import org.apache.http.Header;
import com.example.esycab.network.SmartFruitsRestClient;
import com.example.esycab.utils.ActivityCollector;
import com.example.esycab.utils.DataConvert;
import com.example.esycab.utils.IvListener;
import com.example.esycab.utils.LoadingDialog;
import com.example.esycab.utils.LocalStorage;
import com.example.esycab.utils.MD5Util;
import com.example.esycab.utils.ProConst;

import com.eyoucab.list.Utils;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 密码找回
 * 
 * @author Administrator
 * 
 */
public class FindPassActivity extends Activity implements ProConst {
	private LoadingDialog dialog = null;// 加载
	private EditText edit_find_mobile; // 手机号码
	private EditText et_find_valid; // 验证码
	private EditText edit_find_password;// 密码
	private EditText edit_findConfirm_password;// 确认密码
	private Button btn_find_validcode;// 获取验证码按钮
	private Button btn_find_confirm;// 提交
	private String UserTel = "";
	private String UserNewPass = "";
	private String UserCode = "";
	private String ConfirmPassword = "";
	private Timer timer = null;
	private TimeCount time;
	private TextView biaoTi;
	private LinearLayout ll;
	private float dy, uy;
	private int flag = -1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
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
		setContentView(R.layout.myregister);
		ActivityCollector.addActivity(this);
		LocalStorage.initContext(this);
		// RelativeLayout rl;
		// if(!HomeActivity.quan){
		// rl = (RelativeLayout) findViewById(R.id.g_eng_d_uo);
		// android.widget.LinearLayout.LayoutParams l =
		// (android.widget.LinearLayout.LayoutParams) rl.getLayoutParams();
		// l.topMargin = (int) (HomeActivity.a12);
		// rl.setLayoutParams(l);
		// }
		biaoTi = (TextView) findViewById(R.id.tv_title);
		biaoTi.setText("找回密码");
		timer = new Timer();
		ll = (LinearLayout) findViewById(R.id.zhaohui_mima_zhuce);
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
		edit_find_mobile = (EditText) findViewById(R.id.edit_register_mobile);
		Intent intent = this.getIntent();
		Bundle bundle = intent.getExtras();
		if (bundle != null) {
			UserTel = bundle.getString("UserTel");
		}
		flag = getIntent().getIntExtra("flag", -1);
		if (flag == 6) {
			biaoTi.setText("修改密码");
			UserTel = LocalStorage.get("UserTel").toString();
			edit_find_mobile.setEnabled(false);
		}
		findViewById(R.id.btn_back).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (System.currentTimeMillis() - HomeActivity.dianJiShiJian > 500) {

					HomeActivity.dianJiShiJian = System.currentTimeMillis();
					InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					inputMethodManager.hideSoftInputFromWindow(
							FindPassActivity.this.getCurrentFocus()
									.getWindowToken(),
							InputMethodManager.HIDE_NOT_ALWAYS);
					FindPassActivity.this.finish();
				}
			}
		});

		et_find_valid = (EditText) findViewById(R.id.et_register_valid);
		edit_find_password = (EditText) findViewById(R.id.edit_register_password);
		edit_findConfirm_password = (EditText) findViewById(R.id.edit_confirm_password);
		btn_find_validcode = (Button) findViewById(R.id.btn_register_validcode);
		btn_find_confirm = (Button) findViewById(R.id.btn_register_confirm);
		btn_find_confirm.setText("确定修改并登录");
		edit_find_mobile.setText(UserTel);
		time = new TimeCount(60000, 1000);
		// 获取验证码
		btn_find_validcode.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				System.out.println("HIUUG*OH");
				String mobile = edit_find_mobile.getText().toString();
				if (!Utils.isMobile(mobile)) {
					Toast.makeText(v.getContext(),
							getString(R.string.register_mobile_format_error),
							Toast.LENGTH_LONG).show();
					return;
				} else if (!DataConvert.isMobile(mobile)) {
					Toast.makeText(v.getContext(),
							getString(R.string.register_mobile_format_error),
							Toast.LENGTH_LONG).show();
					return;
				}
				edit_find_mobile.setEnabled(false);
				RequestParams params = new RequestParams();
				params.add("UserTel", mobile);

				SmartFruitsRestClient.post(
						"LoginCheckHandler.ashx?Action=Code", params,
						new AsyncHttpResponseHandler() {

							@Override
							public void onSuccess(int arg0, Header[] arg1,
									byte[] arg2) {

								// TODO Auto-generated method stub
								handler.sendEmptyMessage(2);
								String result = new String(arg2);
								System.out.println(result);
								int i = Integer.parseInt(result);
								try {
									switch (i) {
									case LOGIN_FIALED:
										setView(getString(R.string.conn_faileda));
										break;
									default:
										break;
									}
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
									// Toast.makeText(FindPassActivity.this,
									// e.getMessage(),
									// Toast.LENGTH_SHORT).show();
								}
							}

							@Override
							public void onFailure(int arg0, Header[] arg1,
									byte[] arg2, Throwable arg3) {
								// TODO Auto-generated method stub
								System.out.println(arg3 + "asdfgdhgsa");

								setView(getString(R.string.conn_failed));

							}
						});

				time.start();
			}

		});

		// 提交
		btn_find_confirm.setOnClickListener(confirmBtn);
		setMoreListener();
	}

	private ImageView iv_more;

	private void setMoreListener() {
		iv_more = (ImageView) findViewById(R.id.iv_more);
//		iv_more.setVisibility(View.GONE);
		iv_more.setOnClickListener(new IvListener(iv_more,
				FindPassActivity.this, 0));
	}

	private OnClickListener confirmBtn = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			UserTel = edit_find_mobile.getText().toString();
			UserCode = et_find_valid.getText().toString();
			UserNewPass = edit_find_password.getText().toString();
			ConfirmPassword = edit_findConfirm_password.getText().toString();
			if (!Utils.isMobile(UserTel)) {
				Toast.makeText(FindPassActivity.this, "输入手机号码有误",
						Toast.LENGTH_LONG).show();
				return;
			} else if (UserCode == null || "".equals(UserCode)) {
				Toast.makeText(FindPassActivity.this,
						getString(R.string.register_code_hind),
						Toast.LENGTH_LONG).show();
				return;
			} else if (UserNewPass == null || "".equals(UserNewPass)) {
				Toast.makeText(FindPassActivity.this,
						getString(R.string.login_password), Toast.LENGTH_LONG)
						.show();
				return;
			} else if (!ConfirmPassword.equals(UserNewPass)) {
				Toast.makeText(FindPassActivity.this,
						getString(R.string.confirm_password), Toast.LENGTH_LONG)
						.show();
				return;

			}
			dialog = new LoadingDialog(v.getContext(), "正在获取，请稍候...");
			dialog.showDialog();
			RequestParams params = new RequestParams();
			params.add("UserTel", UserTel);
			params.add("UserCode", UserCode);
			params.add("UserNewPass", MD5Util.string2MD5(UserNewPass));
			SmartFruitsRestClient.post(
					"LoginCheckHandler.ashx?Action=ForgetPwd", params,
					new AsyncHttpResponseHandler() {

						@Override
						public void onSuccess(int arg0, Header[] arg1,
								byte[] arg2) {
							// TODO Auto-generated method stub
							handler.sendEmptyMessage(2);
							String result = new String(arg2);
							int i = Integer.parseInt(result);
							System.out.println(i + "+++++++++++++++++++++++++");
							try {

								switch (i) {
								case NOT_EXIST:// 4
									setView(getString(R.string.password_success));

									if (flag == 6) {
										LocalStorage.set("UserPass",
												UserNewPass);
										Intent data = new Intent();
										System.out.println(6);
										FindPassActivity.this.setResult(66,
												data);
										finish();
									}
									LocalStorage.set("UserTel", UserTel);
									LocalStorage.set("UserPass", UserNewPass);
									System.out
											.println("1111111111111111111111");
									StartActivity.dengRu();
									Intent intent = new Intent();
									intent.setClass(FindPassActivity.this,
											HomeActivity.class);
									startActivity(intent);
									MainActivity.a.finish();
									HomeActivity.homethis.finish();
									FindPassActivity.this.finish();
									break;
								case VALIDATION_TIME_OUT:// -2
									edit_find_mobile.setText("");
									setView(getString(R.string.conn_faileda));
									et_find_valid.setText("");
									edit_find_password.setText("");
									edit_findConfirm_password.setText("");
									dialog.closeDialog();
									break;
								case VALIDATION_INPUT_ERROR:// -3
									setView(getString(R.string.valid_input_error));
									et_find_valid.setText("");
									edit_find_password.setText("");
									edit_findConfirm_password.setText("");
									dialog.closeDialog();
									break;
								case MOBILE_ISNOT_EXIST:// -4
									setView(getString(R.string.conn_faileda));
									et_find_valid.setText("");
									edit_find_password.setText("");
									edit_findConfirm_password.setText("");
									dialog.closeDialog();
									break;
								case ILLEGAL_REQUEST:// -99
									setView(getString(R.string.valid_ILLEGAL_REQUEST));
									et_find_valid.setText("");
									edit_find_password.setText("");
									edit_findConfirm_password.setText("");
									dialog.closeDialog();
									break;
								case LOGIN_FIALED:// -1
									setView("该账户不存在");
									et_find_valid.setText("");
									edit_find_password.setText("");
									edit_findConfirm_password.setText("");
									dialog.closeDialog();
									break;
								default:
									break;
								}
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								// Toast.makeText(FindPassActivity.this,
								// e.getMessage(),
								// Toast.LENGTH_SHORT).show();
							}
						}

						@Override
						public void onFailure(int arg0, Header[] arg1,
								byte[] arg2, Throwable arg3) {
							dialog.closeDialog();
							// TODO Auto-generated method stub
							System.out.println(arg3 + "asdfgdhgsa");

							setView(getString(R.string.conn_failed));
						}
					});
		}
	};

	private void setView(String toast) {
		Toast.makeText(this, toast, Toast.LENGTH_LONG).show();
		dialog.closeDialog();
		btn_find_validcode.setText(getString(R.string.get_valid_code));
		btn_find_validcode.setClickable(true);
		et_find_valid.setText("");
		edit_find_mobile.setEnabled(true);
		timer.cancel();
	}

	//
	// private void sendHandMsg(int counter) {
	// Message timerMsg = handler.obtainMessage();
	// timerMsg.obj = counter;
	// timerMsg.what = 1;
	// handler.sendMessage(timerMsg);
	// }

//	private void goback(View view) {
//		finish();
//	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (System.currentTimeMillis() - HomeActivity.dianJiShiJian > 500) {

				HomeActivity.dianJiShiJian = System.currentTimeMillis();

				FindPassActivity.this.finish();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 1) {
				String valicodeStr = "";
				int timeBalance = (Integer) msg.obj;
				if (timeBalance <= 0) {
					// valicodeStr = getString(R.string.get_valid_code);
					btn_find_validcode.setClickable(true);
				} else {
					valicodeStr = timeBalance + "秒";
					btn_find_validcode.setClickable(false);
				}
				btn_find_validcode.setText(valicodeStr);
			}
		};
	};

	private class TimeCount extends CountDownTimer {

		public TimeCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
		}

		@Override
		public void onTick(long millisUntilFinished) {

			btn_find_validcode.setClickable(false);
			btn_find_validcode.setText("(" + millisUntilFinished / 1000
					+ ") 秒后可重新发送");
		}

		@Override
		public void onFinish() {
			btn_find_validcode.setText("重新获取验证码");
			btn_find_validcode.setClickable(true);

		}

	}

}
