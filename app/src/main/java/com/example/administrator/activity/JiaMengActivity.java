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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class JiaMengActivity extends BaseActivity {
	@ViewInject(R.id.jiameng_gongsi)
	private	 LinearLayout gongSi;
	@ViewInject(R.id.jiameng_chushi)
	private	 LinearLayout chuShi;
	@ViewInject(R.id.tv_title)
	private	TextView tv;
	@Override
	protected int setContentView() {
		return R.layout.activity_jia_meng;
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


		tv.setText("加盟");
		findViewById(R.id.btn_back).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(System.currentTimeMillis()-HomeActivity.dianJiShiJian>500){

					HomeActivity.dianJiShiJian = System.currentTimeMillis();
				JiaMengActivity.this.finish();
				ActivityCollector.finishAll();
				}
			}
		});
		
		gongSi.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent1 = new Intent(JiaMengActivity.this, JoinInGongSi.class);
				
				startActivity(intent1);
			}
		});
		chuShi.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent1 = new Intent(JiaMengActivity.this, JoinInActivity.class);
				startActivity(intent1);
			}
		});
		setMoreListener();
	}



@ViewInject(R.id.iv_more)
	private ImageView iv_more;
	private void setMoreListener() {

		iv_more.setOnClickListener(new IvListener(iv_more, JiaMengActivity.this,0));
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if(System.currentTimeMillis()-HomeActivity.dianJiShiJian>500){

				HomeActivity.dianJiShiJian = System.currentTimeMillis();
			JiaMengActivity.this.finish();
			ActivityCollector.finishAll();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}




}
