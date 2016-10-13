package com.example.esycab;

import com.example.esycab.entity.CaiDan;
import com.example.esycab.utils.ActivityCollector;
import com.example.esycab.utils.Diolg;
import com.example.esycab.utils.IvListener;
import com.eyoucab.list.CommonAdapter;
import com.eyoucab.list.ViewHolder;

import android.os.Bundle;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class HunQing_TaoCanXiangQingActivity extends Activity {
	private CaiDan cd;
	private TextView tv,jiaGe,jiage,meiyong;
	private Button yuDing;
	private LinearLayout ll,llv;
	private ListView lv;
	private CommonAdapter<String> cas;
	
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
		setContentView(R.layout.activity_hun_qing__tao_can_xiang_qing);
		ActivityCollector.addActivity(this);
//		if(!HomeActivity.quan){
//			rl = (RelativeLayout) findViewById(R.id.g_eng_d_uo);
//			android.widget.LinearLayout.LayoutParams l = (android.widget.LinearLayout.LayoutParams) rl.getLayoutParams();
//			l.topMargin = (int) (HomeActivity.a12);
//			rl.setLayoutParams(l);
//		}
		cd = (CaiDan) getIntent().getSerializableExtra("taocanxiangq");
		System.out.println(cd);
		tv = (TextView) findViewById(R.id.tv_hunqing_title);
		llv = (LinearLayout) findViewById(R.id.listview_bizhong);
		jiaGe = (TextView) findViewById(R.id.tongyong_jiage);
		jiage = (TextView) findViewById(R.id.unit_price);
		
		System.out.println(jiaGe);
		jiaGe.setText("价格：");
		yuDing = (Button) findViewById(R.id.jiarudingdan);
		yuDing.setText("我要预定");
		meiyong = (TextView) findViewById(R.id.woyaoyuyue_meiyong);
		
		yuDing.setBackgroundColor(Color.parseColor("#e27386"));
		yuDing.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				new Diolg(HunQing_TaoCanXiangQingActivity.this, "立即拨打", "返回", "联系电话18058516999", "提示", 7);
			}
		});
		lv = (ListView) findViewById(R.id.hunqing_taocanxiangqing_tup);
		ll = (LinearLayout) findViewById(R.id.taocan_xiangqing);
		if (null != cd) {
			
			
			ll.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Intent i = new Intent(HunQing_TaoCanXiangQingActivity.this,
							HunQing_TaoCan_XiangQingActivity.class);
					i.putExtra("taocanID", cd.getIsPass());
					startActivity(i);
				}
			});
		}else{
			cd = (CaiDan) getIntent().getSerializableExtra("qingdianliyixq");
			ll.setVisibility(View.GONE);
			llv.setWeightSum(0.73f);
			
		}
		jiage.setText(cd.getPlace()+"");
		tv.setText(cd.getName());
		if(cd.getName().contains("套餐")){
			
		}else{
			jiaGe.setVisibility(View.GONE);
			meiyong.setVisibility(View.GONE);
			jiage.setVisibility(View.GONE);
		}
		cas = new CommonAdapter<String>(
				HunQing_TaoCanXiangQingActivity.this, cd.getLs(),
				R.layout.item_hunq_zuop) {

			@Override
			public void convert(ViewHolder helper, String item) {
				// TODO Auto-generated method stub
				helper.loadImage(R.id.hunqing_zuop, item);
			}
		};
		lv.setAdapter(cas);
		findViewById(R.id.btn_hunqing_back).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						if(System.currentTimeMillis()-HomeActivity.dianJiShiJian>500){

							HomeActivity.dianJiShiJian = System.currentTimeMillis();
						HunQing_TaoCanXiangQingActivity.this.finish();
						}
					}
				});
		setMoreListener();
		}
	private	ImageView iv_more;
		private void setMoreListener() {
			iv_more = (ImageView) findViewById(R.id.iv_more);
			iv_more.setOnClickListener(new IvListener(iv_more, HunQing_TaoCanXiangQingActivity.this,0));
		}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if(System.currentTimeMillis()-HomeActivity.dianJiShiJian>500){

				HomeActivity.dianJiShiJian = System.currentTimeMillis();
			HunQing_TaoCanXiangQingActivity.this.finish();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
