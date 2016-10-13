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

/**
 * 注册页
 * 
 * @author Administrator
 * 
 */
public class RegisterActivity extends Activity implements ProConst {

	private EditText edit_register_mobile; // 手机号码
	private EditText et_register_valid; // 验证码
	private EditText edit_register_password;// 密码
	private EditText edit_confirm_password;// 确认密码
	private Button btn_register_validcode;// 获取验证码按钮
	private Button btn_register_confirm;// 提交
	private Button leftBtn = null;
	private LoadingDialog dialog = null;// 加载
	// private Timer timer = null;
	private TimeCount time;
	private int zhuCe;
	private TextView tv;
	private LinearLayout ll;
	private float dy, uy;

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
		setContentView(R.layout.myregister);
		// if(!HomeActivity.quan){
		// rl = (RelativeLayout) findViewById(R.id.g_eng_d_uo);
		// android.widget.LinearLayout.LayoutParams l =
		// (android.widget.LinearLayout.LayoutParams) rl.getLayoutParams();
		// l.topMargin = (int) (HomeActivity.a12);
		// rl.setLayoutParams(l);
		// }
		tv = (TextView) findViewById(R.id.tv_title);
		tv.setText("用户注册");
		ActivityCollector.addActivity(this);
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
		LocalStorage.initContext(this);
		zhuCe = getIntent().getIntExtra("zhuCe", 0);
		edit_register_mobile = (EditText) findViewById(R.id.edit_register_mobile);
		et_register_valid = (EditText) findViewById(R.id.et_register_valid);
		edit_register_password = (EditText) findViewById(R.id.edit_register_password);
		edit_confirm_password = (EditText) findViewById(R.id.edit_confirm_password);
		btn_register_validcode = (Button) findViewById(R.id.btn_register_validcode);
		btn_register_confirm = (Button) findViewById(R.id.btn_register_confirm);
		leftBtn = (Button) findViewById(R.id.btn_back);
		time = new TimeCount(60000, 1000);
		leftBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				inputMethodManager.hideSoftInputFromWindow(
						RegisterActivity.this.getCurrentFocus()
								.getWindowToken(),
						InputMethodManager.HIDE_NOT_ALWAYS);
				RegisterActivity.this.finish();
			}
		});
		// 获取验证码
		btn_register_validcode.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String UserTel = edit_register_mobile.getText().toString();
				if (!Utils.isMobile(UserTel)) {
					Toast.makeText(v.getContext(),
							getString(R.string.register_mobile_format_error),
							Toast.LENGTH_LONG).show();
					return;
				} else if (!DataConvert.isMobile(UserTel)) {
					Toast.makeText(v.getContext(),
							getString(R.string.register_mobile_format_error),
							Toast.LENGTH_LONG).show();
					return;
				}
				dialog = new LoadingDialog(v.getContext(), "正在获取，请稍候...");
				dialog.showDialog();
				edit_register_mobile.setEnabled(false);
				RequestParams params = new RequestParams();
				params.add("UserTel", UserTel);
				SmartFruitsRestClient.post(
						"LoginCheckHandler.ashx?Action=Code", params,
						new AsyncHttpResponseHandler() {

							@Override
							public void onSuccess(int arg0, Header[] arg1,
									byte[] arg2) {
								// TODO Auto-generated method stub
								dialog.closeDialog();
								handler.sendEmptyMessage(2);
								String result = new String(arg2);
								System.out.println(result);
								int i = Integer.parseInt(result);
								try {
									switch (i) {
									case LOGIN_FIALED:
										setView(getString(R.string.conn_failed));
										break;
									default:
										break;
									}
								} catch (Exception e) {
									e.printStackTrace();
									// Toast.makeText(RegisterActivity.this,
									// e.getMessage(), Toast.LENGTH_SHORT)
									// .show();
								}
							}

							@Override
							public void onFailure(int arg0, Header[] arg1,
									byte[] arg2, Throwable arg3) {
								// TODO Auto-generated method stub
								System.out.println(arg3 + "asdfgdhgsa");
								handler.sendEmptyMessage(2);
								setView(getString(R.string.conn_failed));
								return;
							}
						});

				time.start();

			}
		});
		// 提交
		btn_register_confirm.setOnClickListener(confirmBtn);

		setMoreListener();
	}

	private ImageView iv_more;

	private void setMoreListener() {
		iv_more = (ImageView) findViewById(R.id.iv_more);
		iv_more.setOnClickListener(new IvListener(iv_more,
				RegisterActivity.this, 0));
	}

	private OnClickListener confirmBtn = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			final String UserTel = edit_register_mobile.getText().toString();
			String UserCode = et_register_valid.getText().toString();
			final String UserPass = edit_register_password.getText().toString()
					.trim();
			final String ConfirmPass = edit_confirm_password.getText()
					.toString().trim();

			if (!Utils.isMobile(UserTel)) {
				Toast.makeText(RegisterActivity.this, "输入手机号码有误",
						Toast.LENGTH_LONG).show();
				return;
			} else if (UserCode == null || "".equals(UserCode)) {
				Toast.makeText(RegisterActivity.this,
						getString(R.string.register_code_hind),
						Toast.LENGTH_LONG).show();
				return;
			} else if (UserPass == null || "".equals(UserPass)) {
				Toast.makeText(RegisterActivity.this,
						getString(R.string.login_password), Toast.LENGTH_LONG)
						.show();
				return;
			} else if (!UserPass.equals(ConfirmPass)) {
				Toast.makeText(RegisterActivity.this,
						getString(R.string.confirm_password), Toast.LENGTH_LONG)
						.show();
				return;
			}
			dialog = new LoadingDialog(v.getContext(), "正在获取，请稍候...");
			dialog.showDialog();
			RequestParams params = new RequestParams();
			params.add("UserTel", UserTel);
			params.add("UserCode", UserCode);
			params.add("UserPass", MD5Util.string2MD5(UserPass));
			SmartFruitsRestClient.post(
					"LoginCheckHandler.ashx?Action=register", params,
					new AsyncHttpResponseHandler() {

						@Override
						public void onSuccess(int arg0, Header[] arg1,
								byte[] arg2) {
							// TODO Auto-generated method stub

							handler.sendEmptyMessage(2);
							String result = new String(arg2);
							System.out.println(result
									+ "++++++++++++++++++++++");
							int i = Integer.parseInt(result);
							try {
								switch (i) {
								case SUCCESS:// 0
									StartActivity.dengRu();
									LocalStorage.set("UserTel", UserTel);
									LocalStorage.set("UserPass", UserPass);
									setView(getString(R.string.mobile_success));
									if (5 == MainActivity.a5) {
										Intent intent = new Intent();
										intent.setClass(RegisterActivity.this,
												HomeActivity.class);
										startActivity(intent);
										HomeActivity.homethis.finish();
										MainActivity.a.finish();
										RegisterActivity.this.finish();
									} else {
										MainActivity.a.finish();
										RegisterActivity.this.finish();
									}

									break;
								case MOBILE_IS_EXIST:// -5
									edit_register_password.setText("");
									edit_confirm_password.setText("");
									setView(getString(R.string.mobile_is_exist));
									dialog.closeDialog();
									break;
								case VALIDATION_INPUT_ERROR:// -3
									edit_register_password.setText("");
									edit_confirm_password.setText("");
									setView(getString(R.string.valid_input_error));
									dialog.closeDialog();
									break;
								case VALIDATION_TIME_OUT:// -2
									edit_register_password.setText("");
									edit_confirm_password.setText("");
									setView(getString(R.string.valid_input_error));
									dialog.closeDialog();
									break;
								case MOBILE_ISNOT_EXIST:// -4
									edit_register_password.setText("");
									edit_confirm_password.setText("");
									setView(getString(R.string.conn_faileda));
									dialog.closeDialog();
									break;
								case ILLEGAL_REQUEST:// -99
									edit_register_password.setText("");
									edit_confirm_password.setText("");
									setView(getString(R.string.valid_ILLEGAL_REQUEST));
									dialog.closeDialog();
									break;
								case LOGIN_FIALED:// -1
									edit_register_password.setText("");
									edit_confirm_password.setText("");
									setView(getString(R.string.conn_failed));
									dialog.closeDialog();
									break;
								default:
									break;
								}
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}

						@Override
						public void onFailure(int arg0, Header[] arg1,
								byte[] arg2, Throwable arg3) {
							// TODO Auto-generated method stub
							System.out.println(arg3 + "asdfgdhgsa");
							handler.sendEmptyMessage(2);
							dialog.closeDialog();
							setView(getString(R.string.conn_failed));

							return;
						}
					});
			//
		}
	};

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (System.currentTimeMillis() - HomeActivity.dianJiShiJian > 500) {
				HomeActivity.dianJiShiJian = System.currentTimeMillis();
				RegisterActivity.this.finish();
			}
		}
		return super.onKeyDown(keyCode, event);
	}

	private void setView(String toast) {
		Toast.makeText(RegisterActivity.this, toast, Toast.LENGTH_LONG).show();
		btn_register_validcode.setText(getString(R.string.get_valid_code));
		btn_register_validcode.setClickable(true);
		et_register_valid.setText("");
		edit_register_mobile.setEnabled(true);
		// if (timer!=null) {
		// timer.cancel();
		// }
	}

	// private void goback(View view) {
	// finish();
	// }

	// private void sendHandMsg(int counter) {
	// Message timerMsg = handler.obtainMessage();
	// timerMsg.obj = counter;
	// timerMsg.what = 1;
	// handler.sendMessage(timerMsg);
	// }

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				String valicodeStr = "";
				int timeBalance = (Integer) msg.obj;
				if (timeBalance <= 0) {
					// valicodeStr = getString(R.string.get_valid_code);
					btn_register_validcode.setClickable(true);
				} else {
					valicodeStr = timeBalance + "秒";
					btn_register_validcode.setClickable(false);
				}
				btn_register_validcode.setText(valicodeStr);
				break;
			case 2:
				// if (dialog!=null) {
				// dialog.closeDialog();
				// }
				break;
			default:
				break;
			}
		};
	};

	private class TimeCount extends CountDownTimer {

		public TimeCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
		}

		@Override
		public void onTick(long millisUntilFinished) {

			btn_register_validcode.setClickable(false);
			btn_register_validcode.setText("(" + millisUntilFinished / 1000
					+ ") 秒后可重新发送");
		}

		@Override
		public void onFinish() {
			btn_register_validcode.setText("重新获取验证码");
			btn_register_validcode.setClickable(true);

		}

	}

}
