package com.eyoucab.list;

import java.io.Serializable;

/**
 * @author Administrator
 * 
 */
@SuppressWarnings("serial")
public class taocan implements Serializable {

	private String pkSetID;
	private String Name;
	private String fkSetCtID;
	private String TotalPrice;
	private String flg;
	private String img;
	private String lei;

	public String getLei() {
		return lei;
	}

	public void setLei(String lei) {
		this.lei = lei;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getPkSetID() {
		return pkSetID;
	}

	public void setPkSetID(String pkSetID) {
		this.pkSetID = pkSetID;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getFkSetCtID() {
		return fkSetCtID;
	}

	public void setFkSetCtID(String fkSetCtID) {
		this.fkSetCtID = fkSetCtID;
	}

	public String getTotalPrice() {
		return TotalPrice;
	}

	public void setTotalPrice(String totalPrice) {
		TotalPrice = totalPrice;
	}

	public String getFlg() {
		return flg;
	}

	public void setFlg(String flg) {
		this.flg = flg;
	}

}
