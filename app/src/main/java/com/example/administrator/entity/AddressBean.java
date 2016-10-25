package com.example.administrator.entity;

/**
 * Created by ${WuQiLian} on 2016/10/21.
 */

public class AddressBean {

    /**
     * RecvID : 收货地址ID
     * RecvTel : 收货人电话
     * RecvName : 收货人姓名
     * ProvinceName : 省级名称
     * DistrictName : 地级级名称
     * CountyName : 县级名称
     * Details : 详细收货地址
     * CountyID : 县级ID
     * isDefault : 是否为默认地址,是为true,不是为false
     */

    private String RecvID;
    private String RecvTel;
    private String RecvName;
    private String ProvinceName;
    private String DistrictName;
    private String CountyName;
    private String Details;
    private String CountyID;
    private String isDefault;

    public String getRecvID() {
        return RecvID;
    }

    public void setRecvID(String RecvID) {
        this.RecvID = RecvID;
    }

    public String getRecvTel() {
        return RecvTel;
    }

    public void setRecvTel(String RecvTel) {
        this.RecvTel = RecvTel;
    }

    public String getRecvName() {
        return RecvName;
    }

    public void setRecvName(String RecvName) {
        this.RecvName = RecvName;
    }

    public String getProvinceName() {
        return ProvinceName;
    }

    public void setProvinceName(String ProvinceName) {
        this.ProvinceName = ProvinceName;
    }

    public String getDistrictName() {
        return DistrictName;
    }

    public void setDistrictName(String DistrictName) {
        this.DistrictName = DistrictName;
    }

    public String getCountyName() {
        return CountyName;
    }

    public void setCountyName(String CountyName) {
        this.CountyName = CountyName;
    }

    public String getDetails() {
        return Details;
    }

    public void setDetails(String Details) {
        this.Details = Details;
    }

    public String getCountyID() {
        return CountyID;
    }

    public void setCountyID(String CountyID) {
        this.CountyID = CountyID;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }
}
