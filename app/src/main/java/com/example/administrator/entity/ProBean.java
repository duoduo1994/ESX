package com.example.administrator.entity;

/**
 * Created by WangYueli on 2016/10/13.
 */

public class ProBean {

    /**
     * RobberyID : 16100815558793823113
     * ProductCgy : 1
     * ProductID : 1
     * ImageUrl :
     * ReMark : 大青龙虾,对折疯抢中
     * Sales : 0
     * SurplusCount : 100
     * OriginalPrice : 100.0000
     * CurrentPrice : 50.0000
     * Norm : 500g
     */

    private String RobberyID;
    private String ProductCgy;
    private String ProductID;
    private String ImageUrl;
    private String ReMark;
    private String Sales;
    private String SurplusCount;
    private String OriginalPrice;
    private String CurrentPrice;
    private String Norm;

    @Override
    public String toString() {
        return getCurrentPrice()+"ppp"+getProductCgy()+"ddd";
    }

    public String getRobberyID() {
        return RobberyID;
    }

    public void setRobberyID(String RobberyID) {
        this.RobberyID = RobberyID;
    }

    public String getProductCgy() {
        return ProductCgy;
    }

    public void setProductCgy(String ProductCgy) {
        this.ProductCgy = ProductCgy;
    }

    public String getProductID() {
        return ProductID;
    }

    public void setProductID(String ProductID) {
        this.ProductID = ProductID;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String ImageUrl) {
        this.ImageUrl = ImageUrl;
    }

    public String getReMark() {
        return ReMark;
    }

    public void setReMark(String ReMark) {
        this.ReMark = ReMark;
    }

    public String getSales() {
        return Sales;
    }

    public void setSales(String Sales) {
        this.Sales = Sales;
    }

    public String getSurplusCount() {
        return SurplusCount;
    }

    public void setSurplusCount(String SurplusCount) {
        this.SurplusCount = SurplusCount;
    }

    public String getOriginalPrice() {
        return OriginalPrice;
    }

    public void setOriginalPrice(String OriginalPrice) {
        this.OriginalPrice = OriginalPrice;
    }

    public String getCurrentPrice() {
        return CurrentPrice;
    }

    public void setCurrentPrice(String CurrentPrice) {
        this.CurrentPrice = CurrentPrice;
    }

    public String getNorm() {
        return Norm;
    }

    public void setNorm(String Norm) {
        this.Norm = Norm;
    }

}
