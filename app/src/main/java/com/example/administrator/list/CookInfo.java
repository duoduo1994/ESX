package com.example.administrator.list;

import java.io.Serializable;
import java.util.List;

/**
 * {"厨师":[{"pkCookID":1, "fkStarLevelID":3, "RealName":"宁小江", "NickName":"食神",
 * "Tel":"15958295246", "Pwd":null, "Sex":"1",
 * "BornDateTime":"\/Date(599625487000)\/", "WorkingTime":10,
 * "HomeAddress":"宁波", "NowAddress":"宁波", "ImageUrl":null, "price":null}]}
 * 
 * @author Administrator
 * 
 */
public class CookInfo implements Serializable{
	int pkCookID;
	int fkStarLevelID;
	double price;
	String RealName;
	String NickName;
	String Tel;
	String Pwd;
	String Sex;
	String BornDateTime;
	String WorkingTime;
	String HomeAddress;
	String NowAddress;
	String ImageUrl;
	String maximum;
	String chuShiNum;
	String fuWuNum;
	int score;
	String goodAt;
	String nengli;
	String scrol;
	String IsYsxRecommend;
	String ServiceArea;
	String StarLevel;
	String CookName;
	String fuwu;
	String chul;
	String ServiceNumGrp;
	
	public String getFuwu() {
		return fuwu;
	}

	public void setFuwu(String fuwu) {
		this.fuwu = fuwu;
	}

	public String getChul() {
		return chul;
	}

	public void setChul(String chul) {
		this.chul = chul;
	}

	public String getFuWuNum() {
		return fuWuNum;
	}

	public void setFuWuNum(String fuWuNum) {
		this.fuWuNum = fuWuNum;
	}

	public String getChuShiNum() {
		return chuShiNum;
	}

	public void setChuShiNum(String chuShiNum) {
		this.chuShiNum = chuShiNum;
	}

	public String getNengli() {
		return nengli;
	}

	public void setNengli(String nengli) {
		this.nengli = nengli;
	}

	public String getScrol() {
		return scrol;
	}

	public void setScrol(String scrol) {
		this.scrol = scrol;
	}

	public String getGoodAt() {
		return goodAt;
	}

	public void setGoodAt(String goodAt) {
		this.goodAt = goodAt;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}




	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getMaximum() {
		return maximum;
	}

	public void setMaximum(String maximum) {
		this.maximum = maximum;
	}

	private List<son> sonList;

	public List<son> getSonList() {
		return sonList;
	}

	public void setSonList(List<son> sonList) {
		this.sonList = sonList;
	}

	public int getPkCookID() {
		return pkCookID;
	}

	public void setPkCookID(int pkCookID) {
		this.pkCookID = pkCookID;
	}

	public int getFkStarLevelID() {
		return fkStarLevelID;
	}

	public void setFkStarLevelID(int fkStarLevelID) {
		this.fkStarLevelID = fkStarLevelID;
	}

	public String getRealName() {
		return RealName;
	}

	public void setRealName(String realName) {
		RealName = realName;
	}

	public String getNickName() {
		return NickName;
	}

	public void setNickName(String nickName) {
		NickName = nickName;
	}

	public String getTel() {
		return Tel;
	}

	public void setTel(String tel) {
		Tel = tel;
	}

	public String getPwd() {
		return Pwd;
	}

	public void setPwd(String pwd) {
		Pwd = pwd;
	}

	public String getSex() {
		return Sex;
	}

	public void setSex(String sex) {
		Sex = sex;
	}

	public String getBornDateTime() {
		return BornDateTime;
	}

	public void setBornDateTime(String bornDateTime) {
		BornDateTime = bornDateTime;
	}

	public String getWorkingTime() {
		return WorkingTime;
	}

	public void setWorkingTime(String workingTime) {
		WorkingTime = workingTime;
	}

	public String getHomeAddress() {
		return HomeAddress;
	}

	public void setHomeAddress(String homeAddress) {
		HomeAddress = homeAddress;
	}

	public String getNowAddress() {
		return NowAddress;
	}

	public void setNowAddress(String nowAddress) {
		NowAddress = nowAddress;
	}

	public String getImageUrl() {
		return ImageUrl;
	}

	public void setImageUrl(String imageUrl) {
		ImageUrl = imageUrl;
	}

	public String getIsYsxRecommend() {
		return IsYsxRecommend;
	}

	public void setIsYsxRecommend(String isYsxRecommend) {
		IsYsxRecommend = isYsxRecommend;
	}

	public String getServiceArea() {
		return ServiceArea;
	}

	public void setServiceArea(String serviceArea) {
		ServiceArea = serviceArea;
	}

	public String getStarLevel() {
		return StarLevel;
	}

	public void setStarLevel(String starLevel) {
		StarLevel = starLevel;
	}

	public String getCookName() {
		return CookName;
	}

	public void setCookName(String cookName) {
		CookName = cookName;
	}

	public String getServiceNumGrp() {
		return ServiceNumGrp;
	}

	public void setServiceNumGrp(String serviceNumGrp) {
		ServiceNumGrp = serviceNumGrp;
	}

	

}
