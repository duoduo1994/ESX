package com.example.administrator.activity;

import com.example.administrator.myapplication.R;
import com.example.administrator.utils.ActivityCollector;
import com.example.administrator.utils.IvListener;
import com.lidroid.xutils.view.annotation.ViewInject;


import android.os.Bundle;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.app.Activity;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class HunCheActivity extends BaseActivity implements OnClickListener {
	@ViewInject(R.id.btn_back)
	private Button back;
	@ViewInject(R.id.hunche_gongsi_jianjie)
	private Button jianJie;
	@ViewInject(R.id.hunche_tuijian_tuandui)
	private Button cheDui;
	@ViewInject(R.id.hunche_hunche_zhuangshi)
	private Button zhuangShi;
	@ViewInject(R.id.tv_title)
	private TextView tv;
	@Override
	protected int setContentView() {
		return R.layout.activity_hun_che;
	}
	@Override
	protected void initView() {


		ActivityCollector.addActivity(this);
//		if(!HomeActivity.quan){
//			rl = (RelativeLayout) findViewById(R.id.g_eng_d_uo);
//			android.widget.LinearLayout.LayoutParams l = (android.widget.LinearLayout.LayoutParams) rl.getLayoutParams();
//			l.topMargin = (int) (HomeActivity.a12);
//			rl.setLayoutParams(l);
//		}


		tv.setText("婚车");



		back.setOnClickListener(this);
		jianJie.setOnClickListener(this);
		cheDui.setOnClickListener(this);
		zhuangShi.setOnClickListener(this);
		setMoreListener();
	}

	@ViewInject(R.id.iv_more)
	private ImageView iv_more;
	private void setMoreListener() {

		iv_more.setOnClickListener(new IvListener(iv_more, HunCheActivity.this,0));
	}

	
	

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.btn_back:
			if(System.currentTimeMillis()-HomeActivity.dianJiShiJian>500){

				HomeActivity.dianJiShiJian = System.currentTimeMillis();
			HunCheActivity.this.finish();
			ActivityCollector.finishAll();
			}
			break;
//		case R.id.hunche_gongsi_jianjie:
//			startActivity(new Intent(HunCheActivity.this, HunChe_GongSiJianJieActivity.class));
//			break;
		case R.id.hunche_tuijian_tuandui:
			startActivity(new Intent(HunCheActivity.this, HunChe_TuiJianCheDuiActivity.class));
			break;
		case R.id.hunche_hunche_zhuangshi:
			startActivity(new Intent(HunCheActivity.this, HunChe_HunCheZhuangShiActivity.class));
			break;
		default:
			break;
		}
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if(System.currentTimeMillis()-HomeActivity.dianJiShiJian>500){

				HomeActivity.dianJiShiJian = System.currentTimeMillis();
			HunCheActivity.this.finish();
			ActivityCollector.finishAll();
		}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}


}
