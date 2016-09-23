package com.example.administrator.utils;



import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.myapplication.R;


public class LoadingDialog {
	
	Dialog dialog;
	private Context mContext;
	private String  msg;
	boolean cancelable = true;
	public LoadingDialog(Context context, String msg) {
		this.mContext = context;
		this.msg =msg;
	}
	
	/***
	 * 
	 * @param context
	 * @param msg
	 * @param cancelable 是否可以点击返回
	 */
	public LoadingDialog(Context context, String msg,boolean cancelable) {
		this.cancelable = cancelable;
		this.mContext = context;
		this.msg =msg;
	}
	
	
	public void showDialog(){
		createLoadingDialog(mContext,msg);
	}
	
	public void closeDialog(){
		if (loadingDialog!=null) {
			loadingDialog.dismiss();
		}
	}
	
	
	
	/**
	 * 得到自定义的progressDialog
	 * @param context
	 * @param msg
	 * @return
	 */
	public void createLoadingDialog(Context context, String msg) {
		if(((Activity)context).isFinishing()){}else{
		LayoutInflater inflater = LayoutInflater.from(context);
		View v = inflater.inflate(R.layout.loading_dialog, null);// 得到加载view
		LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view);// 加载布局
		// main.xml中的ImageView
		ImageView spaceshipImage = (ImageView) v.findViewById(R.id.img);
		TextView tipTextView = (TextView) v.findViewById(R.id.tipTextView);// 提示文字
		// 加载动画
		Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(
				context, R.anim.load_animation);
		// 使用ImageView显示动画
		spaceshipImage.startAnimation(hyperspaceJumpAnimation);
		tipTextView.setText(msg);// 设置加载信息

		 loadingDialog = new Dialog(context, R.style.loading_dialog);// 创建自定义样式dialog
		loadingDialog.setCanceledOnTouchOutside(false);
		loadingDialog.setCancelable(cancelable);// 不可以用“返回键”取消
		loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT));// 设置布局
		loadingDialog.show();}

	}
	Dialog loadingDialog;
}
