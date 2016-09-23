package com.example.administrator.activity;

import com.example.administrator.myapplication.R;
import com.example.administrator.utils.ActivityCollector;
import com.example.administrator.utils.IvListener;
import com.lidroid.xutils.view.annotation.ViewInject;


import android.os.Bundle;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.app.Activity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class HunChe_GongSiJianJieActivity extends BaseActivity {
	@ViewInject(R.id.tv_title)
	private TextView tv;
	@Override
	protected int setContentView() {
		return R.layout.activity_hun_che__gong_si_jian_jie;
	}
	@Override
	protected void initView() {


		ActivityCollector.addActivity(this);
		// RelativeLayout rl;
		// if(!HomeActivity.quan){
		// rl = (RelativeLayout) findViewById(R.id.g_eng_d_uo);
		// android.widget.LinearLayout.LayoutParams l =
		// (android.widget.LinearLayout.LayoutParams) rl.getLayoutParams();
		// l.topMargin = (int) (HomeActivity.a12);
		// rl.setLayoutParams(l);
		// }

		tv.setText("公司简介");
		findViewById(R.id.btn_back).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finishA();
			}
		});
		setMoreListener();
	}

	@ViewInject(R.id.iv_more)
	private ImageView iv_more;

	private void setMoreListener() {

		iv_more.setOnClickListener(new IvListener(iv_more,
				HunChe_GongSiJianJieActivity.this, 0));
	}




}
