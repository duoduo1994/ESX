package com.example.administrator.entity;

public class CommonEntity {
	private int id;
	private String txtInfo;
	private int imageView;
	private double kuan,gao;
	private double wKuan;
	
	public double getwKuan() {
		return wKuan;
	}

	public void setwKuan(double wKuan) {
		
		this.wKuan = wKuan;
	}

	public double getKuan() {
		return kuan;
	}

	public void setKuan(double kuan) {
		this.kuan = kuan;
	}

	public double getGao() {
		return gao;
	}

	public void setGao(double gao) {
		this.gao = gao;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTxtInfo() {
		return txtInfo;
	}

	public void setTxtInfo(String txtInfo) {
		this.txtInfo = txtInfo;
	}

	public int getImageView() {
		return imageView;
	}

	public void setImageView(int imageView) {
		this.imageView = imageView;
	}

}
