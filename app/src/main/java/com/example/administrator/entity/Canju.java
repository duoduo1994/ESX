package com.example.administrator.entity;

public class Canju {
	private  int pkTablesID;
	private String name;
	private String imageUrl;

	@Override
	public String toString() {
		return "Canju [pkTablesID=" + pkTablesID + ", name=" + name
				+ ", imageUrl=" + imageUrl + "]";
	}

	public int getPkTablesID() {
		return pkTablesID;
	}

	public void setPkTablesID(int pkTablesID) {
		this.pkTablesID = pkTablesID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

}
