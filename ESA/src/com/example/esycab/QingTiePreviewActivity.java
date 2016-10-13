package com.example.esycab;



import android.os.Bundle;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class QingTiePreviewActivity extends Activity {
	private String s;
	private String extime;
	private String[] ss;
	private int isWedding; 
	private LinearLayout rl_qingtie,ll_pre_kong,ll_below;
	private TextView tv_date_pre,tv_time_pre,tv_locate_pre,tv_bingke_pre,textViewjy;
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
		setContentView(R.layout.qingtie_p);
//		if(!HomeActivity.quan){
//			rl = (RelativeLayout) findViewById(R.id.g_eng_d_uo);
//			android.widget.LinearLayout.LayoutParams l = (android.widget.LinearLayout.LayoutParams) rl.getLayoutParams();
//			l.topMargin = (int) (HomeActivity.a12);
//			rl.setLayoutParams(l);
//		}
		isWedding=getIntent().getIntExtra("isWedding", 0);
		s=getIntent().getStringExtra("data");
		extime=getIntent().getStringExtra("extime");
		ss=s.split("#");
		initView();
		if(ss[5].equals("1")){
			rl_qingtie.setBackgroundResource(R.drawable.model1);
			ll_below.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 0, (float) 0.28));
			ll_pre_kong.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 0, (float) 0.45));
			chooseColor();
		}else if(ss[5].equals("3")){
			rl_qingtie.setBackgroundResource(R.drawable.model_baby);
			ll_below.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 0, (float) 0.44));
			ll_pre_kong.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 0, (float) 0.25));
		}else if(ss[5].equals("2")){
			rl_qingtie.setBackgroundResource(R.drawable.model2);
			ll_below.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 0, (float) 0.2));
			ll_pre_kong.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 0, (float) 0.45));
		}else if(ss[5].equals("4")){
			rl_qingtie.setBackgroundResource(R.drawable.moldel_nianhui);
			ll_below.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 0, (float) 0.3));
			ll_pre_kong.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 0, (float) 0.2));
			
		}else if(ss[5].equals("5")){
			rl_qingtie.setBackgroundResource(R.drawable.model_move);
			ll_below.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 0, (float) 0.27));
			ll_pre_kong.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 0, (float) 0.38));
			chooseColor();
		}else if(ss[5].equals("6")){
			rl_qingtie.setBackgroundResource(R.drawable.model_shou);
			chooseColor();
			ll_below.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 0, (float) 0.34));
			ll_pre_kong.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 0, (float) 0.35));
		}else if(ss[5].equals("7")){
			rl_qingtie.setBackgroundResource(R.drawable.model_xieshi);
			ll_below.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 0, (float) 0.33));
			ll_pre_kong.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 0, (float) 0.31));
		}
//		isWedding();
		
		for (String string : ss) {
			Log.i("datas",string);
		}
		//getScreenshot(rl_qingtie);
		setData();
	}
	private void setData() {
		tv_bingke_pre.setText(ss[0]);
		tv_time_pre.setText(extime);
		tv_date_pre.setText("瑾定于"+"   "+ss[4].substring(0, 9));
		
		tv_locate_pre.setText("席设"+"   "+ss[3]);
		
		
	}
	private void initView() {
		textViewjy=(TextView) findViewById(R.id.textViewjy);
		ll_below=(LinearLayout) findViewById(R.id.ll_below);
		rl_qingtie=(LinearLayout) findViewById(R.id.rl_qingtie);
		ll_pre_kong=(LinearLayout) findViewById(R.id.ll_pre_kong);
//		ll_pre_word=(LinearLayout) findViewById(R.id.ll_pre_word);
		tv_date_pre=(TextView) findViewById(R.id.tv_date_pre);
		tv_time_pre=(TextView) findViewById(R.id.tv_time_pre);
		tv_locate_pre=(TextView) findViewById(R.id.tv_locate_pre);
		tv_bingke_pre=(TextView) findViewById(R.id.tv_bingke_pre);

	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			QingTiePreviewActivity.this.finish();
		}
		return super.onKeyDown(keyCode, event);
	}
	public void chooseColor(){
		tv_bingke_pre.setTextColor(Color.parseColor("#ffffff"));
		tv_locate_pre.setTextColor(Color.parseColor("#ffffff"));
		tv_time_pre.setTextColor(Color.parseColor("#ffffff"));
		tv_date_pre.setTextColor(Color.parseColor("#ffffff"));
		textViewjy.setTextColor(Color.parseColor("#ffffff"));
	}
}
