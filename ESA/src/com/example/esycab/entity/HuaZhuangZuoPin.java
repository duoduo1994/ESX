package com.example.esycab.entity;

public class HuaZhuangZuoPin {
	private int pkShowID;
	private int fkWebSvcId;
	private String url;
	private int fkWenRoleID;

	public int getPkShowID() {
		return pkShowID;
	}

	public void setPkShowID(int pkShowID) {
		this.pkShowID = pkShowID;
	}

	public int getFkWebSvcId() {
		return fkWebSvcId;
	}

	public void setFkWebSvcId(int fkWebSvcId) {
		this.fkWebSvcId = fkWebSvcId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getFkWenRoleID() {
		return fkWenRoleID;
	}

	public void setFkWenRoleID(int fkWenRoleID) {
		this.fkWenRoleID = fkWenRoleID;
	}

	@Override
	public String toString() {
		return "HuaZhuangZuoPin [pkShowID=" + pkShowID + ", fkWebSvcId="
				+ fkWebSvcId + ", url=" + url + ", fkWenRoleID=" + fkWenRoleID
				+ "]";
	}

}
