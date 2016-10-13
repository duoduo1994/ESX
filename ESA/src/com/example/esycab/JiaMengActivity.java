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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class JiaMengActivity extends Activity {
	private	LinearLayout gongSi,chuShi;
	
	private	TextView tv;
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
		setContentView(R.layout.activity_jia_meng);
		ActivityCollector.addActivity(this);
//		if(!HomeActivity.quan){
//			rl = (RelativeLayout) findViewById(R.id.g_eng_d_uo);
//			android.widget.LinearLayout.LayoutParams l = (android.widget.LinearLayout.LayoutParams) rl.getLayoutParams();
//			l.topMargin = (int) (HomeActivity.a12);
//			rl.setLayoutParams(l);
//		}
		gongSi = (LinearLayout) findViewById(R.id.jiameng_gongsi);
		chuShi = (LinearLayout) findViewById(R.id.jiameng_chushi);
		tv = (TextView) findViewById(R.id.tv_title);
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
	
	
	
	private ImageView iv_more;
	private void setMoreListener() {
		iv_more = (ImageView) findViewById(R.id.iv_more);
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
