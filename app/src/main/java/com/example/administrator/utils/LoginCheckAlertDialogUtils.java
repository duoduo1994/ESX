package com.example.administrator.utils;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.activity.LoginInActivity;
import com.example.administrator.activity.MainActivity;
import com.example.administrator.myapplication.R;

public class LoginCheckAlertDialogUtils {
	String loginStatus;
	private Context context;

	public LoginCheckAlertDialogUtils(Context context) {
		super();
		// TODO Auto-generated constructor stub
		this.context = context;
	}

	private TextView juTiXinXi, tiShi, fuZhi;
	private Dialog d;
	private Button p1;
	private Button n;
	private View v;

	// 定义一个显示消息的对话框
	public boolean showDialog() {

		if (((Activity) context).isFinishing()) {
		} else {
			LocalStorage.initContext(context);
			loginStatus = LocalStorage.get("LoginStatus").toString();
			System.out.println(LocalStorage.get("LoginStatus").toString().equals("login") + "&&&"
					+ loginStatus);
			System.out.println("YGU" + loginStatus);
			if (!loginStatus.equals("login")) {
				d = new Dialog(context, R.style.loading_dialog);
				v = LayoutInflater.from(context).inflate(R.layout.dialog, null);// 窗口布局
				d.setContentView(v);// 把设定好的窗口布局放到dialog中
				d.setCanceledOnTouchOutside(true);// 设定点击窗口空白处取消会话
				p1 = (Button) v.findViewById(R.id.p);
				n = (Button) v.findViewById(R.id.n);
				juTiXinXi = (TextView) v.findViewById(R.id.banben_xinxi);
				tiShi = (TextView) v.findViewById(R.id.banben_gengxin);
				fuZhi = (TextView) v.findViewById(R.id.fuzhi_jiantieban);
				juTiXinXi.setText("亲，还没登录呢，是否前去登录？");
				tiShi.setText("登录提示");
				p1.setText("确定");
				n.setText("返回");
				p1.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						Intent intent = new Intent(context, LoginInActivity.class);
						intent.putExtra("dengRu", 45);
						context.startActivity(intent);
						d.dismiss();
					}
				});
				n.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						// System.out.println(a);
//						if(context == HomeActivity._instance){
//							d.dismiss();
//						}else{
//						Intent i = new Intent(context, HomeActivity.class);
//						context.startActivity(i);
//						ActivityCollector.finishAll();
//						HomeActivity._instance.finish();
//						((Activity) context).finish();
						d.dismiss();
	//				}
					}
				});
				d.show();
				return true;
			} else {
				System.out.println("denglule");
				return false;
			}
		}
		return false;

	}

}
