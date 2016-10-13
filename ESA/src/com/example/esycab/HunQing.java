package com.example.esycab;

import com.example.esycab.utils.ActivityCollector;
import com.example.esycab.utils.IvListener;

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

public class HunQing extends Activity implements OnClickListener {
	private ImageView hunqing_taocan, qingdian_liyi, siren_dingzhi, gewu_yanyi;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
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
		setContentView(R.layout.hunqing);
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
		hunqing_taocan = (ImageView) findViewById(R.id.hunqing_taocan);
		qingdian_liyi = (ImageView) findViewById(R.id.qingdian_liyi);
		siren_dingzhi = (ImageView) findViewById(R.id.siren_dingzhi);
		gewu_yanyi = (ImageView) findViewById(R.id.gewu_yanyi);
		hunqing_taocan.setOnClickListener(this);
		qingdian_liyi.setOnClickListener(this);
		siren_dingzhi.setOnClickListener(this);
		gewu_yanyi.setOnClickListener(this);
		setMoreListener();
	}

	private ImageView iv_more;

	private void setMoreListener() {
		iv_more = (ImageView) findViewById(R.id.iv_more);
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
