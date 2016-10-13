package com.eyoucab.list;

/**
 * 物品实体对象
 * 
 * @author Ly
 * 
 */
public class GoodsEntity {
	private String ID;
	private String Name;
	private String Sex;
	private String Tel;
	private String DeliveryAdr;
	private String MainCount;
	private String SubCount;
	private String BookDateTime;
	private String UpDateTime;
	private String fkCusTel;
	private String OderNo;
	private String FastDateTime;
	private String flg;

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getSex() {
		return Sex;
	}

	public void setSex(String sex) {
		Sex = sex;
	}

	public String getTel() {
		return Tel;
	}

	public void setTel(String tel) {
		Tel = tel;
	}

	public String getDeliveryAdr() {
		return DeliveryAdr;
	}

	public void setDeliveryAdr(String deliveryAdr) {
		DeliveryAdr = deliveryAdr;
	}

	public String getMainCount() {
		return MainCount;
	}

	public void setMainCount(String mainCount) {
		MainCount = mainCount;
	}

	public String getSubCount() {
		return SubCount;
	}

	public void setSubCount(String subCount) {
		SubCount = subCount;
	}

	public String getBookDateTime() {
		return BookDateTime;
	}

	public void setBookDateTime(String bookDateTime) {
		BookDateTime = bookDateTime;
	}

	public String getUpDateTime() {
		return UpDateTime;
	}

	public void setUpDateTime(String upDateTime) {
		UpDateTime = upDateTime;
	}

	public String getFastDateTime() {
		return FastDateTime;
	}

	public void setFastDateTime(String fastDateTime) {
		FastDateTime = fastDateTime;
	}

	public String getflg() {
		return flg;
	}

	public void setflg(String Flg) {
		flg = Flg;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getfkCusTel() {
		return fkCusTel;
	}

	public void setfkCusTel(String ordID) {
		fkCusTel = ordID;
	}

	public String getOderNo() {
		return OderNo;
	}

	public void setOderNo(String iD) {
		OderNo = iD;
	}

}
