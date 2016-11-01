package com.example.administrator.entity;

import java.util.List;

/**
 * Created by WangYueli on 2016/10/31.
 */

public class TaocanDetailNBean {

    /**
     * Feature :
     * ImageUrl : Http://120.27.141.95:8221/UploadFile/DishImage/10.jpg
     * IsOnLine : 1
     * MaxRecvHour :
     * MinRecvHour :
     * Name : 参考菜单1
     * Price : 0.0000
     * Remark :
     * Sales :
     */

    private BaseDataBean BaseData;
    /**
     * ImageUrl :
     * Name : 太子蟹
     * Norm :
     * Price :
     */

    private List<ContentBean> Content;

    public BaseDataBean getBaseData() {
        return BaseData;
    }

    public void setBaseData(BaseDataBean BaseData) {
        this.BaseData = BaseData;
    }

    public List<ContentBean> getContent() {
        return Content;
    }

    public void setContent(List<ContentBean> Content) {
        this.Content = Content;
    }

    public static class BaseDataBean {
        private String Feature;
        private String ImageUrl;
        private String IsOnLine;
        private String MaxRecvHour;
        private String MinRecvHour;
        private String Name;
        private String Price;
        private String Remark;
        private String Sales;

        @Override
        public String toString() {
            return "Feature"+Feature+"ImageUrl"+ImageUrl+"IsOnLine"+IsOnLine;
        }

        public String getFeature() {
            return Feature;
        }

        public void setFeature(String Feature) {
            this.Feature = Feature;
        }

        public String getImageUrl() {
            return ImageUrl;
        }

        public void setImageUrl(String ImageUrl) {
            this.ImageUrl = ImageUrl;
        }

        public String getIsOnLine() {
            return IsOnLine;
        }

        public void setIsOnLine(String IsOnLine) {
            this.IsOnLine = IsOnLine;
        }

        public String getMaxRecvHour() {
            return MaxRecvHour;
        }

        public void setMaxRecvHour(String MaxRecvHour) {
            this.MaxRecvHour = MaxRecvHour;
        }

        public String getMinRecvHour() {
            return MinRecvHour;
        }

        public void setMinRecvHour(String MinRecvHour) {
            this.MinRecvHour = MinRecvHour;
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

        public String getRemark() {
            return Remark;
        }

        public void setRemark(String Remark) {
            this.Remark = Remark;
        }

        public String getSales() {
            return Sales;
        }

        public void setSales(String Sales) {
            this.Sales = Sales;
        }
    }

    public static class ContentBean {
        private String ImageUrl;
        private String Name;
        private String Norm;
        private String Price;

        @Override
        public String toString() {
            return "ImageUrl"+ImageUrl+"Name"+Name+"Norm"+Norm+"Price"+Price;
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

        public String getNorm() {
            return Norm;
        }

        public void setNorm(String Norm) {
            this.Norm = Norm;
        }

        public String getPrice() {
            return Price;
        }

        public void setPrice(String Price) {
            this.Price = Price;
        }
    }
}
