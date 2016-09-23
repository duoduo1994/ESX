package com.example.administrator.entity;

public class XiangQing_ZhuTaoCan {
	private int ID;
	private double count;
	private String title;
	private double price;
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	double tPrice;

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	@Override
	public String toString() {
		return "XiangQing_ZhuTaoCan [ID=" + ID + ", count=" + count
				+ ", title=" + title + ", price=" + price + ", name=" + name
				+ ", tPrice=" + tPrice + "]";
	}

	public double getCount() {
		return count;
	}

	public void setCount(double count) {
		this.count = count;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double gettPrice() {
		return tPrice;
	}

	public void settPrice(double tPrice) {
		this.tPrice = tPrice;
	}

}
