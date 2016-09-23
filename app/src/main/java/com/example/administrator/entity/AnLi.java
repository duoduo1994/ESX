package com.example.administrator.entity;

import java.io.Serializable;

@SuppressWarnings("serial")
public class AnLi implements Serializable {
	private int pkCaseID;
	private String feastOrd;
	private String feastAdr;
	private double feastPrice;
	private String imgUrl;
	private String hallsName;
	private String detail;
	private int pkTbWareCgyID;
	private String name;
	boolean a;
	private String riQ;
	private String riQX;
	private String sex;
	private String lianxidizhi;
	private String zhucanshijian;
	private String zhuzhuo;
	private String fuzhuo;
	private String hasJudge;
	private String tel;
	private String xiShiTangDZ;

	@Override
	public String toString() {
		return "AnLi [pkCaseID=" + pkCaseID + ", feastOrd=" + feastOrd
				+ ", feastAdr=" + feastAdr + ", feastPrice=" + feastPrice
				+ ", imgUrl=" + imgUrl + ", hallsName=" + hallsName
				+ ", detail=" + detail + ", pkTbWareCgyID=" + pkTbWareCgyID
				+ ", name=" + name + ", a=" + a + ", riQ=" + riQ + ", riQX="
				+ riQX + ", sex=" + sex + ", lianxidizhi=" + lianxidizhi
				+ ", zhucanshijian=" + zhucanshijian + ", zhuzhuo=" + zhuzhuo
				+ ", fuzhuo=" + fuzhuo + "]";
	}

	public String getXiShiTangDZ() {
		return xiShiTangDZ;
	}

	public void setXiShiTangDZ(String xiShiTangDZ) {
		this.xiShiTangDZ = xiShiTangDZ;
	}

	public String getHasJudge() {
		return hasJudge;
	}

	public void setHasJudge(String hasJudge) {
		this.hasJudge = hasJudge;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getLianxidizhi() {
		return lianxidizhi;
	}

	public void setLianxidizhi(String lianxidizhi) {
		this.lianxidizhi = lianxidizhi;
	}

	public String getZhucanshijian() {
		return zhucanshijian;
	}

	public void setZhucanshijian(String zhucanshijian) {
		this.zhucanshijian = zhucanshijian;
	}

	public String getZhuzhuo() {
		return zhuzhuo;
	}

	public void setZhuzhuo(String zhuzhuo) {
		this.zhuzhuo = zhuzhuo;
	}

	public String getFuzhuo() {
		return fuzhuo;
	}

	public void setFuzhuo(String fuzhuo) {
		this.fuzhuo = fuzhuo;
	}

	public String getRiQ() {
		return riQ;
	}

	public void setRiQ(String riQ) {
		this.riQ = riQ;
	}

	public String getRiQX() {
		return riQX;
	}

	public void setRiQX(String riQX) {
		this.riQX = riQX;
	}

	public boolean isA() {
		return a;
	}

	public void setA(boolean a) {
		this.a = a;
	}

	public int getPkTbWareCgyID() {
		return pkTbWareCgyID;
	}

	public void setPkTbWareCgyID(int pkTbWareCgyID) {
		this.pkTbWareCgyID = pkTbWareCgyID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPkCaseID() {
		return pkCaseID;
	}

	public void setPkCaseID(int pkCaseID) {
		this.pkCaseID = pkCaseID;
	}

	public String getFeastOrd() {
		return feastOrd;
	}

	public void setFeastOrd(String feastOrd) {
		this.feastOrd = feastOrd;
	}

	public String getFeastAdr() {
		return feastAdr;
	}

	public void setFeastAdr(String feastAdr) {
		this.feastAdr = feastAdr;
	}

	public double getFeastPrice() {
		return feastPrice;
	}

	public void setFeastPrice(double feastPrice) {
		this.feastPrice = feastPrice;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getHallsName() {
		return hallsName;
	}

	public void setHallsName(String hallsName) {
		this.hallsName = hallsName;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

}
