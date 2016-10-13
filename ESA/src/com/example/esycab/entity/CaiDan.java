package com.example.esycab.entity;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class CaiDan implements Serializable{
	private int pkDishNameID;
	private String name;
	private String material;
	private String ssDection;
	private String isPass;
	private int weight;
	private String place;
	private String imageUrl;
	private int fkDishCgyID;
	private double unitPrice;
	private int pkTablesID;
	private List<Canju> lcj;
	private List<String > ls;
	
	public List<String> getLs() {
		return ls;
	}

	public void setLs(List<String> ls) {
		this.ls = ls;
	}

	public List<Canju> getLcj() {
		return lcj;
	}

	public void setLcj(List<Canju> lcj) {
		this.lcj = lcj;
	}

	public int getPkTablesID() {
		return pkTablesID;
	}

	public void setPkTablesID(int pkTablesID) {
		this.pkTablesID = pkTablesID;
	}

	public int getPkDishNameID() {
		return pkDishNameID;
	}

	public void setPkDishNameID(int pkDishNameID) {
		this.pkDishNameID = pkDishNameID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public String getSsDection() {
		return ssDection;
	}

	public void setSsDection(String ssDection) {
		this.ssDection = ssDection;
	}

	public String getIsPass() {
		return isPass;
	}

	public void setIsPass(String isPass) {
		this.isPass = isPass;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public int getFkDishCgyID() {
		return fkDishCgyID;
	}

	public void setFkDishCgyID(int fkDishCgyID) {
		this.fkDishCgyID = fkDishCgyID;
	}

	public double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}

	@Override
	public String toString() {
		return "CaiDan [pkDishNameID=" + pkDishNameID + ", name=" + name
				+ ", material=" + material + ", ssDection=" + ssDection
				+ ", isPass=" + isPass + ", weight=" + weight + ", place="
				+ place + ", imageUrl=" + imageUrl + ", fkDishCgyID="
				+ fkDishCgyID + ", unitPrice=" + unitPrice + "]";
	}

}
