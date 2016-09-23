package com.example.administrator.list;

import java.io.Serializable;
import java.util.List;

public class father implements Serializable {
	private static final long serialVersionUID = 1L;
	private String seconId;
	private String lessonId;
	private int image;
	String ImageUrl;
	private List<son> sonList;

	public String getId() {
		return seconId;
	}

	public void setId(String seconId) {
		this.seconId = seconId;
	}

	public String getName() {
		return lessonId;
	}

	public void setName(String name) {
		this.lessonId = name;
	}

	public List<son> getSonList() {
		return sonList;
	}

	public void setSonList(List<son> sonList) {
		this.sonList = sonList;
	}

	public int getImage() {
		return image;
	}

	public void setImage(int image) {
		this.image = image;
	}

	public String getImageUrl() {
		return ImageUrl;
	}

	public void setImageUrl(String imageUrl) {
		ImageUrl = imageUrl;
	}
}
