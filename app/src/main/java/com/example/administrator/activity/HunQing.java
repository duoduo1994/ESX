package com.example.administrator.activity;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.example.administrator.myapplication.R;
import com.example.administrator.utils.ActivityCollector;
import com.example.administrator.utils.IvListener;
import com.lidroid.xutils.view.annotation.ViewInject;

public class HunQing extends BaseActivity implements OnClickListener {
	@ViewInject(R.id.hunqing_taocan)
	private ImageView hunqing_taocan;
	@ViewInject(R.id.qingdian_liyi)
	private ImageView qingdian_liyi;
	@ViewInject(R.id.siren_dingzhi)
	private ImageView siren_dingzhi;
	@ViewInject(R.id.gewu_yanyi)
	private ImageView gewu_yanyi;


	@Override
	protected int setContentView() {
		return R.layout.hunqing;
	}
	@Override
	protected void initView() {
		ActivityCollector.addActivity(this);
		findViewById(R.id.hunqing_work_back).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						if (System.currentTimeMillis()
								- HomeActivity.dianJiShiJian > 500) {

							HomeActivity.dianJiShiJian = System
									.currentTimeMillis();
							// 结束当前界面
							HunQing.this.finish();
							ActivityCollector.finishAll();
						}
					}
				});




		hunqing_taocan.setOnClickListener(this);
		qingdian_liyi.setOnClickListener(this);
		siren_dingzhi.setOnClickListener(this);
		gewu_yanyi.setOnClickListener(this);
		setMoreListener();
	}


	@ViewInject(R.id.iv_more)
	private ImageView iv_more;

	private void setMoreListener() {

		iv_more.setOnClickListener(new IvListener(iv_more, HunQing.this, 0));
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (System.currentTimeMillis() - HomeActivity.dianJiShiJian > 500) {

				HomeActivity.dianJiShiJian = System.currentTimeMillis();
				HunQing.this.finish();
				ActivityCollector.finishAll();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}


	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.hunqing_taocan:
			startActivity(new Intent(HunQing.this, TaoCan_LieBiaoActivity.class));
			break;
		case R.id.qingdian_liyi:
			startActivity(new Intent(HunQing.this, QingDian_LiYiActivity.class));
			break;
		case R.id.siren_dingzhi:
			startActivity(new Intent(HunQing.this, ShiRen_DingZhiActivity.class));
			break;
		case R.id.gewu_yanyi:
			startActivity(new Intent(HunQing.this, GeWu_YanYiActivity.class));
			break;

		}
	}
}
