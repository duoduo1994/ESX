package com.example.esycab.entity;

import java.io.Serializable;

public class NTH_Halls implements Serializable{

	private String pkHallID;//ID
	private String name;//名称
	private String contact;//负责人
	private String contactTel;
	private String fkAuctionCdrID;//喜宴日期
	private String detailAdr;//详细地址
	private String longitude;//经度
	private String latitude;//纬度
	private String wgsLongitude;//wgs经度
	private String wgsLatitude;//wgs纬度
	private String imageUrl;//喜事堂图片URL
	private String districtID;//区Id
	
	public String getWgsLongitude() {
		return wgsLongitude;
	}
	public void setWgsLongitude(String wgsLongitude) {
		this.wgsLongitude = wgsLongitude;
	}
	public String getWgsLatitude() {
		return wgsLatitude;
	}
	public void setWgsLatitude(String wgsLatitude) {
		this.wgsLatitude = wgsLatitude;
	}
	
	public String getDistrictID() {
		return districtID;
	}
	public void setDistrictID(String districtID) {
		this.districtID = districtID;
	}
	public String getPkHallID() {
		return pkHallID;
	}
	public void setPkHallID(String pkHallID) {
		this.pkHallID = pkHallID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getContactTel() {
		return contactTel;
	}
	public void setContactTel(String contactTel) {
		this.contactTel = contactTel;
	}
	public String getFkAuctionCdrID() {
		return fkAuctionCdrID;
	}
	public void setFkAuctionCdrID(String fkAuctionCdrID) {
		this.fkAuctionCdrID = fkAuctionCdrID;
	}
	public String getDetailAdr() {
		return detailAdr;
	}
	public void setDetailAdr(String detailAdr) {
		this.detailAdr = detailAdr;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	
	
}
