package com.example.administrator.entity;

import java.util.List;

/**
 * Created by WangYueli on 2016/10/21.
 */

public class MyOrderBean {

    /**
     * ImageUrl :
     * Name :
     * TotalPrice :
     * Gamish : [{"Name":"","TotalCount":"","TotalPrice":""}]
     */

    private String ImageUrl;
    private String Name;
    private String TotalPrice;

    public MyOrderBean(String imageUrl, String name, String totalPrice, List<GamishBean> gamish) {
        ImageUrl = imageUrl;
        Name = name;
        TotalPrice = totalPrice;
        Gamish = gamish;
    }

    public MyOrderBean() {
    }

    /**
     * Name :
     * TotalCount :
     * TotalPrice :
     */

    private List<GamishBean> Gamish;

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

    public String getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(String TotalPrice) {
        this.TotalPrice = TotalPrice;
    }

    public List<GamishBean> getGamish() {
        return Gamish;
    }

    public void setGamish(List<GamishBean> Gamish) {
        this.Gamish = Gamish;
    }

    public static class GamishBean {
        private String Name;
        private String TotalCount;
        private String TotalPrice;

        public GamishBean(String name, String totalCount, String totalPrice) {
            Name = name;
            TotalCount = totalCount;
            TotalPrice = totalPrice;
        }

        public GamishBean() {
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public String getTotalCount() {
            return TotalCount;
        }

        public void setTotalCount(String TotalCount) {
            this.TotalCount = TotalCount;
        }

        public String getTotalPrice() {
            return TotalPrice;
        }

        public void setTotalPrice(String TotalPrice) {
            this.TotalPrice = TotalPrice;
        }
    }
}
