package com.example.administrator.entity;

/**
 * Created by WangYueli on 2016/9/30.
 */

public class ShouyeListBean {

    /**
     * PublicityID : 20160922170800000000
     * ProductID : 1
     * ProductCgy : 1
     * ImageUrl : Http://120.27.141.95:8221/UploadFile/Promoting/100000000.jpg
     */

    private String PublicityID;
    private String ProductID;
    private String ProductCgy;
    private String ImageUrl;

    public ShouyeListBean() {
    }

    public ShouyeListBean(String productCgy, String publicityID, String productID, String imageUrl) {
        ProductCgy = productCgy;
        PublicityID = publicityID;
        ProductID = productID;
        ImageUrl = imageUrl;
    }

    public String getPublicityID() {
        return PublicityID;
    }

    public void setPublicityID(String PublicityID) {
        this.PublicityID = PublicityID;
    }

    public String getProductID() {
        return ProductID;
    }

    public void setProductID(String ProductID) {
        this.ProductID = ProductID;
    }

    public String getProductCgy() {
        return ProductCgy;
    }

    public void setProductCgy(String ProductCgy) {
        this.ProductCgy = ProductCgy;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String ImageUrl) {
        this.ImageUrl = ImageUrl;
    }
}
