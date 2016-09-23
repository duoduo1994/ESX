package com.example.administrator.utils;

import com.example.administrator.myapplication.R;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Diolg {
	public Context context, context1, context2;
	public String btn1, btn2, xinXi, biaoTi;
	int a;

	public Diolg(Context context, String btn1, String btn2, String xinXi,
			String biaoTi, int a) {
		super();
		// TODO Auto-generated constructor stub
		this.context = context;
		this.a = a;
		this.btn1 = btn1;
		this.btn2 = btn2;
		this.xinXi = xinXi;
		this.biaoTi = biaoTi;
		show();
	}

	public void show() {
		if (((Activity) context).isFinishing()) {
		} else {
			d = new Dialog(context, R.style.loading_dialog);
			v = LayoutInflater.from(context).inflate(R.layout.dialog, null);// 窗口布局
			d.setContentView(v);// 把设定好的窗口布局放到dialog中
			d.setCanceledOnTouchOutside(true);// 设定点击窗口空白处取消会话
			p1 = (Button) v.findViewById(R.id.p);
			n = (Button) v.findViewById(R.id.n);
			juTiXinXi = (TextView) v.findViewById(R.id.banben_xinxi);
			tiShi = (TextView) v.findViewById(R.id.banben_gengxin);
			fuZhi = (TextView) v.findViewById(R.id.fuzhi_jiantieban);
			juTiXinXi.setText(xinXi);
			tiShi.setText(biaoTi);
			p1.setText(btn1);
			n.setText(btn2);
			if (btn2.equals("null")) {
				n.setVisibility(View.GONE);
			}
			if (a == 5) {
				fuZhi.setVisibility(View.VISIBLE);
				juTiXinXi.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						Clipboard.copy("23564718@136.com", context);
						fuZhi.setTextColor(Color.parseColor("#696969"));
					}
				});
			}
			p1.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					if (a == 7) {
						Intent phoneIntent = new Intent(
								"android.intent.action.CALL", Uri.parse("tel:"
										+ "18058516999"));
						context.startActivity(phoneIntent);
					} else if (a == 2) {
						Intent phoneIntent = new Intent(
								"android.intent.action.CALL", Uri.parse("tel:"
										+ "18058516999"));
						context.startActivity(phoneIntent);
					} else if (a == 10) {
						((Activity) context).finish();
					} else if (a == 3) {
						Intent phoneIntent = new Intent(
								"android.intent.action.CALL", Uri.parse("tel:"
										+ "400-8261-707"));
						context.startActivity(phoneIntent);
					}
//					else if (a == 16) {
//						Intent i = new Intent(context,
//								WoDeDingDanActivity.class);
//						context.startActivity(i);
//						ReserveActivity.ssthis.finish();
//					} else if (a == 9) {
//						Intent i = new Intent(context, XiYan.class);
//						context.startActivity(i);
//						XiYan.xt.finish();
//					}
					d.dismiss();
				}
			});
			n.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					// System.out.println(a);
					if (a == 10) {
					}
					d.dismiss();
				}
			});
			d.show();
		}
	}

	TextView juTiXinXi, tiShi, fuZhi;
	Dialog d;
	Button p1;
	Button n;
	View v;
}
