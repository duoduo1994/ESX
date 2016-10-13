package com.example.esycab;

import com.example.esycab.utils.ActivityCollector;
import com.example.esycab.utils.IvListener;

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

public class HunCheActivity extends Activity implements OnClickListener {
	private Button back, jianJie, cheDui, zhuangShi;
	private TextView tv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON);
            //透明导航栏
          //  getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
		}else{
			this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
			this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏
		}
		setContentView(R.layout.activity_hun_che);
		ActivityCollector.addActivity(this);
//		if(!HomeActivity.quan){
//			rl = (RelativeLayout) findViewById(R.id.g_eng_d_uo);
//			android.widget.LinearLayout.LayoutParams l = (android.widget.LinearLayout.LayoutParams) rl.getLayoutParams();
//			l.topMargin = (int) (HomeActivity.a12);
//			rl.setLayoutParams(l);
//		}
		back = (Button) findViewById(R.id.btn_back);
		tv = (TextView) findViewById(R.id.tv_title);
		tv.setText("婚车");
		jianJie = (Button) findViewById(R.id.hunche_gongsi_jianjie);
		cheDui = (Button) findViewById(R.id.hunche_tuijian_tuandui);
		zhuangShi = (Button) findViewById(R.id.hunche_hunche_zhuangshi);
		back.setOnClickListener(this);
		jianJie.setOnClickListener(this);
		cheDui.setOnClickListener(this);
		zhuangShi.setOnClickListener(this);
		setMoreListener();
	}
	
	private ImageView iv_more;
	private void setMoreListener() {
		iv_more = (ImageView) findViewById(R.id.iv_more);
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
