package com.example.esycab.utils;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class Card extends FrameLayout {
	private ImageView label;

	public Card(Context context) {
		super(context);

		label = new ImageView(getContext());

		LayoutParams lp = new LayoutParams(-1, -1);
		lp.setMargins(5, 5, 0, 0);
		addView(label, lp);

		setNum(0);
	}

	private int num = 0;

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
		label.setImageResource(num);
	}
}
