package com.example.administrator.entity;

/**
 * Created by WangYueli on 2016/10/22.
 */

public class CookerBean {

    /**
     * GoodAt : 擅长菜系
     * ID : 厨师团队ID
     * ImageUrl : 厨师团队图片
     * Name : 厨师名团队字
     * ServiceNum : 服务范围
     */

    private String GoodAt;
    private String ID;
    private String ImageUrl;
    private String Name;
    private String ServiceNum;

    public CookerBean() {
    }

    public CookerBean(String goodAt, String ID, String imageUrl, String name, String serviceNum) {
        GoodAt = goodAt;
        this.ID = ID;
        ImageUrl = imageUrl;
        Name = name;
        ServiceNum = serviceNum;
    }

    public String getGoodAt() {
        return GoodAt;
    }

    public void setGoodAt(String GoodAt) {
        this.GoodAt = GoodAt;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String ImageUrl) {
        this.ImageUrl = ImageUrl;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getServiceNum() {
        return ServiceNum;
    }

    public void setServiceNum(String ServiceNum) {
        this.ServiceNum = ServiceNum;
    }
}
