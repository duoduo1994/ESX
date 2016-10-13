package com.example.esycab;

import com.example.esycab.entity.AnLi;
import com.example.esycab.utils.ActivityCollector;

import android.os.Bundle;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.app.Activity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class JiBenXingXiActivity extends Activity {

	private AnLi li;
	private TextView tv_name_jiben, biaoTi, tv_gender_jiben, tv_tel_jiben,
			tv_address_jiben, tv_xishitang_jiben, tv_ordertime_jiben,
			tv_feastdate_jiben, tv_feasttype_jiben, tv_start_jiben,
			tv_over_jiben, tv_maintime_jiben, tv_zhunum_jiben, tv_fumun_jiben;

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
		setContentView(R.layout.activity_ji_ben_xing_xi);
		// RelativeLayout rl;
		ActivityCollector.addActivity(this);
		// if(!HomeActivity.quan){
		// rl = (RelativeLayout) findViewById(R.id.g_eng_d_uo);
		// android.widget.LinearLayout.LayoutParams l =
		// (android.widget.LinearLayout.LayoutParams) rl.getLayoutParams();
		// l.topMargin = (int) (HomeActivity.a12);
		// rl.setLayoutParams(l);
		// }
		li = WoDeDingDanXQ.al;
		initView();
		setData();
		setMoreListener();
	}

	private ImageView iv_more;

	private void setMoreListener() {
		iv_more = (ImageView) findViewById(R.id.iv_more);
		iv_more.setVisibility(View.GONE);
		// iv_more.setOnClickListener(new IvListener(iv_more,
		// JiBenXingXiActivity.this));
	}

	private void setData() {
		tv_name_jiben.setText(li.getName().trim());
		tv_gender_jiben.setText(li.getSex().trim());
		tv_feastdate_jiben.setText(li.getHallsName().trim());
		tv_ordertime_jiben.setText(li.getRiQX().trim());
		tv_feasttype_jiben.setText(li.getFeastOrd().trim());
		tv_address_jiben.setText(li.getLianxidizhi().trim());
		tv_fumun_jiben.setText(li.getFuzhuo().trim());
		tv_zhunum_jiben.setText(li.getZhuzhuo().trim());
		tv_tel_jiben.setText(li.getTel().trim());
		tv_maintime_jiben.setText(li.getZhucanshijian().trim());
		tv_xishitang_jiben.setText(li.getImgUrl());
	}

	private void initView() {
		tv_xishitang_jiben = (TextView) findViewById(R.id.tv_xishitang_jiben);// 喜事堂地址
		tv_name_jiben = (TextView) findViewById(R.id.tv_name_jiben);// 姓名w
		tv_gender_jiben = (TextView) findViewById(R.id.tv_gender_jiben);// 性别w
		tv_tel_jiben = (TextView) findViewById(R.id.tv_tel_jiben);// 联系电话w
		tv_address_jiben = (TextView) findViewById(R.id.tv_address_jiben);// 联系地址w
		tv_ordertime_jiben = (TextView) findViewById(R.id.tv_ordertime_jiben);// 下单时间w
		tv_feastdate_jiben = (TextView) findViewById(R.id.tv_feastdate_jiben);// 宴请日期w
		tv_feasttype_jiben = (TextView) findViewById(R.id.tv_feasttype_jiben);// 宴请类型w
		tv_start_jiben = (TextView) findViewById(R.id.tv_start_jiben);// 宴请开始
		tv_over_jiben = (TextView) findViewById(R.id.tv_over_jiben);// 宴请结束
		tv_maintime_jiben = (TextView) findViewById(R.id.tv_maintime_jiben);// 主餐时间（中午晚上）
		tv_zhunum_jiben = (TextView) findViewById(R.id.tv_zhunum_jiben);// 主餐桌数w
		tv_fumun_jiben = (TextView) findViewById(R.id.tv_fumun_jiben);// 副餐桌数w
		biaoTi = (TextView) findViewById(R.id.tv_title);
		biaoTi.setText("订单基本信息");
		findViewById(R.id.btn_back).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				JiBenXingXiActivity.this.finish();
			}
		});

	}

}
