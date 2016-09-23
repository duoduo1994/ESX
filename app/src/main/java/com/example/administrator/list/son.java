package com.example.administrator.list;

import java.io.Serializable;
import java.sql.Date;

public class son implements Serializable {
	private int pid;
	private int categoryId;
	private static final long serialVersionUID = 1L;
	private String secondId;
	private String lessonpId;
	private String secondTitle;
	private String albumTitle;
	private String cover_url_small;
	private int image;
	private long updated_at;
	private long created_at;
	private Date addtime;
	private String UnitPrice;
	private boolean iCho;
	String tiName;
	String tiTuPian;
	String tiID;
	boolean isTi;
	boolean isTiHuan;
	String jiage;
	String danwei;
	String fenliang;
	String tifengliang;
	String tidanwei;

	public String getFenliang() {
		return fenliang;
	}

	public void setFenliang(String fenliang) {
		this.fenliang = fenliang;
	}

	public String getTifengliang() {
		return tifengliang;
	}

	public void setTifengliang(String tifengliang) {
		this.tifengliang = tifengliang;
	}

	public String getTidanwei() {
		return tidanwei;
	}

	public void setTidanwei(String tidanwei) {
		this.tidanwei = tidanwei;
	}

	public String getJiage() {
		return jiage;
	}

	public void setJiage(String jiage) {
		this.jiage = jiage;
	}

	public String getDanwei() {
		return danwei;
	}

	public void setDanwei(String danwei) {
		this.danwei = danwei;
	}

	public boolean isTiHuan() {
		return isTiHuan;
	}

	public void setTiHuan(boolean isTiHuan) {
		this.isTiHuan = isTiHuan;
	}

	public String getSecondId() {
		return secondId;
	}

	public void setSecondId(String secondId) {
		this.secondId = secondId;
	}

	public String getLessonpId() {
		return lessonpId;
	}

	public void setLessonpId(String lessonpId) {
		this.lessonpId = lessonpId;
	}

	public String getSecondTitle() {
		return secondTitle;
	}

	public void setSecondTitle(String secondTitle) {
		this.secondTitle = secondTitle;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public boolean isTi() {
		return isTi;
	}

	public void setTi(boolean isTi) {
		this.isTi = isTi;
	}

	public String getTiID() {
		return tiID;
	}

	public void setTiID(String tiID) {
		this.tiID = tiID;
	}

	public String getTiName() {
		return tiName;
	}

	public void setTiName(String tiName) {
		this.tiName = tiName;
	}

	public String getTiTuPian() {
		return tiTuPian;
	}

	public void setTiTuPian(String tiTuPian) {
		this.tiTuPian = tiTuPian;
	}

	public boolean isiCho() {
		return iCho;
	}

	public void setiCho(boolean iCho) {
		this.iCho = iCho;
	}

	public String getUnitPrice() {
		return UnitPrice;
	}

	public void setUnitPrice(String UnitPrice) {
		this.UnitPrice = UnitPrice;
	}

	public String getId() {
		return secondId;
	}

	public void setId(String secondId) {
		this.secondId = secondId;
	}

	public String getName() {
		return lessonpId;
	}

	public void setName(String lessonpId) {
		this.lessonpId = lessonpId;
	}

	public String getImageUrl() {
		return secondTitle;
	}

	public void setImageUrl(String secondTitle) {
		this.secondTitle = secondTitle;
	}

	public int getImage() {
		return image;
	}

	public void setImage(int image) {
		this.image = image;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public String getAlbumTitle() {
		return albumTitle;
	}

	public void setAlbumTitle(String albumTitle) {
		this.albumTitle = albumTitle;
	}

	public String getCover_url_small() {
		return cover_url_small;
	}

	public void setCover_url_small(String cover_url_small) {
		this.cover_url_small = cover_url_small;
	}

	public long getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(long updated_at) {
		this.updated_at = updated_at;
	}

	public long getCreated_at() {
		return created_at;
	}

	public void setCreated_at(long created_at) {
		this.created_at = created_at;
	}

	public Date getAddtime() {
		return addtime;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}

	@Override
	public String toString() {
		return "son [pid=" + pid + ", categoryId=" + categoryId + ", secondId="
				+ secondId + ", lessonpId=" + lessonpId + ", secondTitle="
				+ secondTitle + ", albumTitle=" + albumTitle
				+ ", cover_url_small=" + cover_url_small + ", image=" + image
				+ ", updated_at=" + updated_at + ", created_at=" + created_at
				+ ", addtime=" + addtime + ", UnitPrice=" + UnitPrice
				+ ", iCho=" + iCho + ", tiName=" + tiName + ", tiTuPian="
				+ tiTuPian + ", tiID=" + tiID + ", isTi=" + isTi + "]";
	}

}
