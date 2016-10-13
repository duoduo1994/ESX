package com.example.esycab;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.esycab.network.SmartFruitsRestClient;
import com.example.esycab.utils.ActivityCollector;
import com.example.esycab.utils.Diolg;
import com.example.esycab.utils.IvListener;
import com.example.esycab.utils.LocalStorage;
import com.example.esycab.utils.LoginCheckAlertDialogUtils;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.os.Bundle;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class YiJianFanKuiActivity extends Activity {
	private TextView tv;
	private EditText et;
	private LinearLayout ll;
	private float dy, uy;

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
		setContentView(R.layout.activity_yi_jian_fan_kui);
		ActivityCollector.addActivity(this);
		LocalStorage.initContext(this);
		ll = (LinearLayout) findViewById(R.id.geng_d_yi_jian);
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
		tv = (TextView) findViewById(R.id.tv_title);
		et = (EditText) findViewById(R.id.yijian_fankui_et);
		tv.setText("意见反馈");
		findViewById(R.id.btn_back).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (System.currentTimeMillis() - HomeActivity.dianJiShiJian > 500) {
					HomeActivity.dianJiShiJian = System.currentTimeMillis();
					InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					inputMethodManager.hideSoftInputFromWindow(
							YiJianFanKuiActivity.this.getCurrentFocus()
									.getWindowToken(),
							InputMethodManager.HIDE_NOT_ALWAYS);
					YiJianFanKuiActivity.this.finish();
				}
			}
		});
		findViewById(R.id.yijian_fan_kui_tijiao).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						// tiJiao();
						if (!new LoginCheckAlertDialogUtils(
								YiJianFanKuiActivity.this,0).showDialog()) {
							if (!TextUtils.isEmpty(et.getText().toString())) {
								tiJiao();
							} else {
								new Diolg(YiJianFanKuiActivity.this, "确定",
										"null", "输入内容为空", "提示", 0);
							}
						}
					}
				});
		setMoreListener();
	}

	private ImageView iv_more;

	private void setMoreListener() {
		iv_more = (ImageView) findViewById(R.id.iv_more);
		iv_more.setOnClickListener(new IvListener(iv_more,
				YiJianFanKuiActivity.this, 0));
	}

	private void tiJiao() {
		String tel = (String) LocalStorage.get("UserTel").toString();
		RequestParams p = new RequestParams();
		p.put("UserTel", tel);
		p.put("FeedbackContent", et.getText().toString());
		SmartFruitsRestClient.get("FeedBack.ashx?Action=Submit", p,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
						// TODO Auto-generated method stub
						String result = new String(arg2);
						System.out.println(result);
						try {
							JSONObject json = new JSONObject(result.trim());
							if (json.getString("提示").equals("提交成功")) {
								Toast.makeText(YiJianFanKuiActivity.this,
										"感谢评价", Toast.LENGTH_LONG).show();
								YiJianFanKuiActivity.this.finish();
							} else {
								Toast.makeText(YiJianFanKuiActivity.this,
										"内容提交错误，请重试", Toast.LENGTH_LONG).show();
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}

					@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
							Throwable arg3) {
						// TODO Auto-generated method stub
						System.out.println(arg3);
						if (ciShu >= 3) {
							isFalse = false;
							Toast.makeText(YiJianFanKuiActivity.this,
									getString(R.string.conn_failed) + arg3,
									Toast.LENGTH_SHORT).show();
						}
						;

						if (isFalse) {
							tiJiao();
							ciShu++;
						}

					}
				});
	}

	private boolean isFalse = true;
	private int ciShu = 0;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (System.currentTimeMillis() - HomeActivity.dianJiShiJian > 500) {
				HomeActivity.dianJiShiJian = System.currentTimeMillis();
				YiJianFanKuiActivity.this.finish();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
