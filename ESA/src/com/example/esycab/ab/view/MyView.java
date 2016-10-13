package com.example.esycab.ab.view;

import com.example.esycab.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.media.ThumbnailUtils;
import android.util.AttributeSet;
import android.view.View;

public class MyView extends View {

	float left;
	int bmWidth;
	
	
	public void setLeft(float left) {
		this.left = left;
	}
	public void setBmWidth(int bmWidth) {
		this.bmWidth = bmWidth;
	}
	public MyView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	@Override
	protected void onDraw(Canvas canvas) {
		
		Bitmap bitmap=BitmapFactory.decodeResource(getResources(), R.drawable.gundongtiao);
		bitmap=ThumbnailUtils.extractThumbnail(bitmap, bmWidth, 5,ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
		canvas.drawBitmap(bitmap, left, 0, null);
		super.onDraw(canvas);
	}
}
