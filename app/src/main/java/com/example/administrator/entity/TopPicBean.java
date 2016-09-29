package com.example.administrator.entity;

import java.util.List;

/**
 * Created by WangYueli on 2016/9/29.
 */

public class ToppicBean {

    private List<String> Top;

    public List<String> getTop() {
        return Top;
    }

    public void setTop(List<String> Top) {
        this.Top = Top;
    }

    @Override
    public String toString() {
        String s=Top.toString();
        return s;
    }
}
