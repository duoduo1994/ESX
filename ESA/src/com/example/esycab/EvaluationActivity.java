package com.example.esycab;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.esycab.network.SmartFruitsRestClient;
import com.example.esycab.utils.LocalStorage;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.view.KeyEvent;
import android.view.LayoutInflater;
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
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 评价页
 * 
 * @author Administrator
 * 
 */
public class EvaluationActivity extends Activity implements
		OnRatingBarChangeListener {
	private Button iv;
	private Button submission;
	private String name;
	private TextView orderNum, realName, tv;
	private String bianHao, riQi;
	private EditText et;
	private LinearLayout ll;
	private float dy, uy;
	private RatingBar fuWu, caiXi, chuShi, canJu, hunChe, hunQing, zongPing;

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
		setContentView(R.layout.evaluation);
		tv = (TextView) findViewById(R.id.tv_title);
		tv.setText("我的评价");
		LocalStorage.initContext(this);
		ll = (LinearLayout) findViewById(R.id.pingjia_scrollview);
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

		SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");// 设置日期格式
		riQi = df.format(new Date());// new Date()为获取当前系统时间

		bianHao = getIntent().getStringExtra("ding_danbianhao");
		name = getIntent().getStringExtra("姓名");
		orderNum = (TextView) findViewById(R.id.order_number);
		realName = (TextView) findViewById(R.id.order_name);
		orderNum.setText(bianHao);
		et = (EditText) findViewById(R.id.evaluation_overall);
		name = (String) LocalStorage.get("RealName");
		realName.setText(name);
		fuWu = (RatingBar) findViewById(R.id.evaluation_of_service);
		caiXi = (RatingBar) findViewById(R.id.evaluation_of_cuisine);
		chuShi = (RatingBar) findViewById(R.id.evaluation_of_chef);
		canJu = (RatingBar) findViewById(R.id.evaluation_of_tableware);
		hunChe = (RatingBar) findViewById(R.id.evaluation_of_desk);
		hunQing = (RatingBar) findViewById(R.id.evaluation_of_deskss);
		zongPing = (RatingBar) findViewById(R.id.evaluation_of_overall);
		iv = (Button) findViewById(R.id.btn_back);
		fuWu.setOnRatingBarChangeListener(this);
		caiXi.setOnRatingBarChangeListener(this);
		chuShi.setOnRatingBarChangeListener(this);
		canJu.setOnRatingBarChangeListener(this);
		hunChe.setOnRatingBarChangeListener(this);
		hunQing.setOnRatingBarChangeListener(this);
		zongPing.setOnRatingBarChangeListener(this);
		iv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				// 结束当前界面
				if (System.currentTimeMillis() - HomeActivity.dianJiShiJian > 500) {

					HomeActivity.dianJiShiJian = System.currentTimeMillis();
					Intent data = new Intent();
					data.putExtra("qud", 0);
					EvaluationActivity.this.setResult(15, data);
					finish();
				}
			}
		});
		submission = (Button) findViewById(R.id.evaluation_submission);
		submission.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				pingJia();

			}

		});
		setMoreListener();
	}

	private ImageView iv_more;

	private void setMoreListener() {
		iv_more = (ImageView) findViewById(R.id.iv_more);
		iv_more.setVisibility(View.GONE);
	}

	private void pingJia() {
		RequestParams p = new RequestParams();
		p.put("fkOrderID", bianHao);
		p.put("EvaluationDt", riQi);
		p.put("ServiceMannerStarNum", 1);
		p.put("CuisineStarNum", caixi);// 喜宴
		p.put("CookStarNum", chushi);// 厨师
		p.put("WareStarNum", canju);// 餐具
		p.put("WeddingStarNum", 1);
		p.put("SetMealStarNum", 1);
		p.put("WeddingcarStarNum", 1);
		p.put("OverallAppraisalStarNum", 1);
		p.put("Remark", et.getText().toString());
		p.put("ValuatorTel", LocalStorage.get("UserTel").toString());

		SmartFruitsRestClient.get("EvaluateHandler.ashx?Action=EvaluateOnce",
				p, new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
						// TODO Auto-generated method stub
						String result = new String(arg2);
						System.out.println(result);
						JSONObject json;
						try {
							json = new JSONObject(result.trim());
							res = json.getString("提示");
							if ("不知道你想评价什么".equals(res)) {
								res = "您的订单还未加入过内容";
							}
							if (EvaluationActivity.this.isFinishing()) {
							} else {
								d = new Dialog(EvaluationActivity.this,
										R.style.loading_dialog);
								v = LayoutInflater
										.from(EvaluationActivity.this).inflate(
												R.layout.dialog, null);// 窗口布局
								d.setContentView(v);// 把设定好的窗口布局放到dialog中
								d.setCanceledOnTouchOutside(true);// 设定点击窗口空白处取消会话
								p1 = (Button) v.findViewById(R.id.p);
								n = (Button) v.findViewById(R.id.n);
								juTiXinXi = (TextView) v
										.findViewById(R.id.banben_xinxi);
								tiShi = (TextView) v
										.findViewById(R.id.banben_gengxin);
								juTiXinXi.setText(res);
								tiShi.setText("提示");
								p1.setText("确定");
								n.setText("返回");
								n.setVisibility(View.GONE);
								p1.setOnClickListener(new OnClickListener() {

									@Override
									public void onClick(View arg0) {
										// TODO Auto-generated method stub
										if ("评价成功".equals(res)) {
											Intent data = new Intent();
											data.putExtra("qud", 1);
											EvaluationActivity.this.setResult(
													15, data);
											finish();
											d.dismiss();
										}

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
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}

					@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
							Throwable arg3) {
						// TODO Auto-generated method stub
						System.out.println(arg3 + "asdfgdhgsa");
						if (ciShu >= 3) {
							isFalse = false;
							Toast.makeText(EvaluationActivity.this,
									getString(R.string.conn_failed),
									Toast.LENGTH_SHORT).show();
						}
						;

						if (isFalse) {
							pingJia();
							ciShu++;
						}

					}
				});
	}

	private String res;
	private TextView juTiXinXi, tiShi;
	private Dialog d;
	private Button p1;
	private Button n;
	private View v;
	private boolean isFalse = true;
	private int ciShu = 0;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (System.currentTimeMillis() - HomeActivity.dianJiShiJian > 500) {

				HomeActivity.dianJiShiJian = System.currentTimeMillis();
				Intent data = new Intent();
				data.putExtra("qud", 0);
				EvaluationActivity.this.setResult(15, data);
				finish();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	private float caixi = 3, chushi = 3, canju = 3;

	@Override
	public void onRatingChanged(RatingBar arg0, float arg1, boolean arg2) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
//		case R.id.evaluation_of_service:
//			System.out.println("fuwu" + arg1);
//			fuwu = arg1;
//			break;
		case R.id.evaluation_of_cuisine:
			System.out.println("caixi" + arg1);
			caixi = arg1;
			break;
		case R.id.evaluation_of_chef:
			System.out.println("chushi" + arg1);
			chushi = arg1;
			break;
		case R.id.evaluation_of_tableware:
			System.out.println("canju" + arg1);
			canju = arg1;
			break;
//		case R.id.evaluation_of_desk:
//			System.out.println("hunche" + arg1);
//			hunche = arg1;
//			break;
//		case R.id.evaluation_of_deskss:
//			System.out.println("hunqing" + arg1);
//			hunqing = arg1;
//			break;
//		case R.id.evaluation_of_overall:
//			System.out.println("zongping" + arg1);
//			zongping = arg1;
//			break;
		default:
			break;
		}
	}
}
