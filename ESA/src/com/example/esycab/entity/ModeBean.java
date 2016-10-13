package com.example.esycab.entity;

import android.graphics.Bitmap;

public class ModeBean {
	private String name;
	private Bitmap bitmap;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Bitmap getBitmap() {
		return bitmap;
	}
	public void setDrawable(Bitmap bitmap) {
		this.bitmap = bitmap;
	}
	public ModeBean(String name, Bitmap bitmap) {
		super();
		this.name = name;
		this.bitmap = bitmap;
	}
	public ModeBean() {
		super();
	}
	@Override
	public String toString() {
		return "ModeBean [name=" + name + ", drawable=" + bitmap + "]";
	}
	

}
