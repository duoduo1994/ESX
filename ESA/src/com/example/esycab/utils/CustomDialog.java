package com.example.esycab.utils;

import com.example.esycab.R;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

public class CustomDialog extends Dialog implements
		android.view.View.OnClickListener {

	public interface OnCustomDialogListener {
		public void back(String name);
	}

	private String time;
	private OnCustomDialogListener customDialogListener;
	private TimePicker tp;

	public CustomDialog(Context context, String time,
			OnCustomDialogListener customDialogListener) {
		super(context);
		this.time = time;
		this.customDialogListener = customDialogListener;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.time_dialog);
		setTitle("请选择时间");
		tp = (TimePicker) findViewById(R.id.tp_diolog);
			time = tp.getCurrentHour() + ":" + tp.getCurrentMinute();
		Button btn = (Button) findViewById(R.id.btn_time);
		btn.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		tp.clearFocus();
		//time = tp.getCurrentHour() + ":" + tp.getCurrentMinute();
		if (tp.getCurrentMinute() < 10) {
			time = tp.getCurrentHour() + ":0" + tp.getCurrentMinute();
		} else {
			time = tp.getCurrentHour() + ":" + tp.getCurrentMinute();
		}
		customDialogListener.back(String.valueOf(time));
		CustomDialog.this.dismiss();

	}

}