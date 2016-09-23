package com.example.administrator.activity;

import com.example.administrator.entity.AnLi;
import com.example.administrator.entity.CaiDan;
import com.example.administrator.list.CommonAdapter;
import com.example.administrator.list.ViewHolder;
import com.example.administrator.myapplication.R;
import com.example.administrator.utils.ActivityCollector;
import com.example.administrator.utils.Diolg;
import com.example.administrator.utils.IvListener;
import com.lidroid.xutils.view.annotation.ViewInject;


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

public class HunQing_TaoCanXiangQingActivity extends BaseActivity {
	private CaiDan cd;
	@ViewInject(R.id.tv_hunqing_title)
	private TextView tv;
	@ViewInject(R.id.tongyong_jiage)
	private TextView jiaGe;
	@ViewInject(R.id.unit_price)
	private TextView jiage;
	@ViewInject(R.id.woyaoyuyue_meiyong)
	private TextView meiyong;
	@ViewInject(R.id.jiarudingdan)
	private Button yuDing;
	@ViewInject(R.id.taocan_xiangqing)
	private LinearLayout ll;
	@ViewInject(R.id.listview_bizhong)
	private LinearLayout llv;
	@ViewInject(R.id.hunqing_taocanxiangqing_tup)
	private ListView lv;
	private CommonAdapter<String> cas;
	@Override
	protected int setContentView() {
		return R.layout.activity_hun_qing__tao_can_xiang_qing;
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
		cd = (CaiDan) getIntent().getSerializableExtra("taocanxiangq");
		System.out.println(cd);




		
		System.out.println(jiaGe);
		jiaGe.setText("价格：");

		yuDing.setText("我要预定");

		
		yuDing.setBackgroundColor(Color.parseColor("#e27386"));
		yuDing.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				new Diolg(HunQing_TaoCanXiangQingActivity.this, "立即拨打", "返回", "联系电话18058516999", "提示", 7);
			}
		});


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

			@Override
			public void convert(ViewHolder helper, AnLi item) {

			}
		};
		lv.setAdapter(cas);
		findViewById(R.id.btn_hunqing_back).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						finishA();
					}
				});
		setMoreListener();
		}


@ViewInject(R.id.iv_more)
	private	ImageView iv_more;
		private void setMoreListener() {
			iv_more.setOnClickListener(new IvListener(iv_more, HunQing_TaoCanXiangQingActivity.this,0));
		}




}
