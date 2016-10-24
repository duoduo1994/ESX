package com.example.administrator.entity;

/**
 * Created by WangYueli on 2016/10/22.
 */

public class PayDetailbean {

    /**
     * Date : 变更时间
     * Value : 变更金额
     * ResualtValue : 变更后余额
     */

    private String Date;
    private String Value;
    private String ResualtValue;

    public PayDetailbean() {
    }

    public PayDetailbean(String date, String value, String resualtValue) {
        Date = date;
        Value = value;
        ResualtValue = resualtValue;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String Date) {
        this.Date = Date;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String Value) {
        this.Value = Value;
    }

    public String getResualtValue() {
        return ResualtValue;
    }

    public void setResualtValue(String ResualtValue) {
        this.ResualtValue = ResualtValue;
    }
}
