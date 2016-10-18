package com.example.administrator.entity;

/**
 * Created by WangYueli on 2016/10/13.
 */

public class HotTancanAllBean {

    /**
     * ID : 1
     * ImageUrl :
     * Name : 参考菜单1
     * Price :
     * Sales :
     */

    private BaseDateBean BaseDate;
    /**
     * BaseDate : {"ID":"1","ImageUrl":"","Name":"参考菜单1","Price":"","Sales":""}
     * Content : 红膏炝蟹,特级海蜇,油煎带鱼,话梅花生,白切目鱼
     */

    private String Content;

    @Override
    public String toString() {
        return super.toString();
    }

    public BaseDateBean getBaseDate() {
        return BaseDate;
    }

    public void setBaseDate(BaseDateBean BaseDate) {
        this.BaseDate = BaseDate;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String Content) {
        this.Content = Content;
    }

    public static class BaseDateBean {
        private String ID;
        private String ImageUrl;
        private String Name;
        private String Price;
        private String Sales;

        @Override
        public String toString() {
            return super.toString();
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

        public String getPrice() {
            return Price;
        }

        public void setPrice(String Price) {
            this.Price = Price;
        }

        public String getSales() {
            return Sales;
        }

        public void setSales(String Sales) {
            this.Sales = Sales;
        }
    }
}
