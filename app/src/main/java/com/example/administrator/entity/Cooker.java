package com.example.administrator.entity;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Cooker implements Serializable{
	String SolarDtFrom;
	String SolarDtTo;
	String OccupyStatus;
	String HallName;
	String ROrderName;
	String ROrderNameTel;
	public String getSolarDtFrom() {
		return SolarDtFrom;
	}
	public void setSolarDtFrom(String solarDtFrom) {
		SolarDtFrom = solarDtFrom;
	}
	public String getSolarDtTo() {
		return SolarDtTo;
	}
	public void setSolarDtTo(String solarDtTo) {
		SolarDtTo = solarDtTo;
	}
	public String getOccupyStatus() {
		return OccupyStatus;
	}
	public void setOccupyStatus(String occupyStatus) {
		OccupyStatus = occupyStatus;
	}
	public String getHallName() {
		return HallName;
	}
	public void setHallName(String hallName) {
		HallName = hallName;
	}
	public String getROrderName() {
		return ROrderName;
	}
	public void setROrderName(String rOrderName) {
		ROrderName = rOrderName;
	}
	public String getROrderNameTel() {
		return ROrderNameTel;
	}
	public void setROrderNameTel(String rOrderNameTel) {
		ROrderNameTel = rOrderNameTel;
	}
	
}
