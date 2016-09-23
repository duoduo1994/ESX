package com.example.administrator.utils;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.example.administrator.activity.HomeActivity;
import com.example.administrator.myapplication.R;

public class IvListener implements OnClickListener {
	private ImageView iv;
	private Context context;
	private PopupWindow pw;
	private int a;

	public IvListener(ImageView iv, Context context, int a) {
		super();
		this.iv = iv;
		this.a = a;
		this.context = context;
	}

	@Override
	public void onClick(View v) {
		initPop();
		pw.showAsDropDown(iv);

	}

	private void initPop() {
		View v = LayoutInflater.from(context).inflate(R.layout.item_more_pop,
				null);
		pw = new PopupWindow(v, LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		pw.setBackgroundDrawable(new BitmapDrawable());

		pw.setFocusable(true);
		// 设置允许在外点击消失
		pw.setOutsideTouchable(true);
		v.findViewById(R.id.btn_more_shou).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						Intent i = new Intent(context, HomeActivity.class);
						context.startActivity(i);
//						ActivityCollector.finishAll();
						HomeActivity._instance.finish();
						((Activity) context).finish();
						pw.dismiss();

					}
				});
		v.findViewById(R.id.btn_more_order).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View arg0) {
//						if (!new LoginCheckAlertDialogUtils(context)
//								.showDialog()) {
//							if (a == 1) {
//
//							} else {
//								Intent i = new Intent(context,
//										WoDeDingDanActivity.class);
//								context.startActivity(i);
//							}
//							pw.dismiss();
//						}

					}
				});

	}

}
