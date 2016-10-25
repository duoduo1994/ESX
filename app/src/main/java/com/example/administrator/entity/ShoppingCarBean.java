package com.example.administrator.entity;

import java.util.List;

/**
 * Created by WangYueli on 2016/10/20.
 */

public class ShoppingCarBean {


    /**
     * ID : 生鲜ID
     * ProductCgy : 生鲜单品模块固定值为:Freash
     * Name : 生鲜名称
     * ImageUrl : 生鲜图片
     * TotalCount : 购买总数
     * TotalPrice : 购买总价
     * MinRecvHour : 最短预定时限
     * MaxRecvHour : 最长预定时限
     * Gamish : [{"ID":"配菜ID","Name":"配菜名称","TotalPrice":"总价"}]
     */

    private List<YSXFreashBean> YSXFreash;
    /**
     * ID : 喜宴套餐ID
     * ProductCgy : 喜宴套餐模块固定值为:FeastSetMeal
     * Name : 喜宴套餐名称
     * ImageUrl : 喜宴套餐图片
     * TotalCount : 购买总数
     * TotalPrice : 总价
     */

    private List<FeastBean> Feast;

    public List<YSXFreashBean> getYSXFreash() {
        return YSXFreash;
    }

    public void setYSXFreash(List<YSXFreashBean> YSXFreash) {
        this.YSXFreash = YSXFreash;
    }

    public List<FeastBean> getFeast() {
        return Feast;
    }

    public void setFeast(List<FeastBean> Feast) {
        this.Feast = Feast;
    }

    public static class YSXFreashBean {
        private String ID;
        private String ProductCgy;
        private String Name;
        private String ImageUrl;
        private String TotalCount;
        private String TotalPrice;
        private String MinRecvHour;
        private String MaxRecvHour;

        public YSXFreashBean() {
        }

        public YSXFreashBean(String ID, String productCgy, String name, String imageUrl, String totalCount, String totalPrice, String minRecvHour, String maxRecvHour, List<GamishBean> gamish) {
            this.ID = ID;
            ProductCgy = productCgy;
            Name = name;
            ImageUrl = imageUrl;
            TotalCount = totalCount;
            TotalPrice = totalPrice;
            MinRecvHour = minRecvHour;
            MaxRecvHour = maxRecvHour;
            Gamish = gamish;
        }

        /**
         * ID : 配菜ID
         * Name : 配菜名称
         * TotalPrice : 总价
         */


        private List<GamishBean> Gamish;

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public String getProductCgy() {
            return ProductCgy;
        }

        public void setProductCgy(String ProductCgy) {
            this.ProductCgy = ProductCgy;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public String getImageUrl() {
            return ImageUrl;
        }

        public void setImageUrl(String ImageUrl) {
            this.ImageUrl = ImageUrl;
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

        public String getMinRecvHour() {
            return MinRecvHour;
        }

        public void setMinRecvHour(String MinRecvHour) {
            this.MinRecvHour = MinRecvHour;
        }

        public String getMaxRecvHour() {
            return MaxRecvHour;
        }

        public void setMaxRecvHour(String MaxRecvHour) {
            this.MaxRecvHour = MaxRecvHour;
        }

        public List<GamishBean> getGamish() {
            return Gamish;
        }

        public void setGamish(List<GamishBean> Gamish) {
            this.Gamish = Gamish;
        }

        public static class GamishBean {
            private String ID;
            private String Name;
            private String TotalPrice;

            public GamishBean() {
            }

            public GamishBean(String ID, String totalPrice, String name) {
                this.ID = ID;
                TotalPrice = totalPrice;
                Name = name;
            }

            public String getID() {
                return ID;
            }

            public void setID(String ID) {
                this.ID = ID;
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
        }
    }

    public static class FeastBean {
        private String ID;
        private String ProductCgy;
        private String Name;
        private String ImageUrl;
        private String TotalCount;
        private String TotalPrice;

        public FeastBean() {
        }

        public FeastBean(String ID, String productCgy, String name, String imageUrl, String totalCount, String totalPrice) {
            this.ID = ID;
            ProductCgy = productCgy;
            Name = name;
            ImageUrl = imageUrl;
            TotalCount = totalCount;
            TotalPrice = totalPrice;
        }

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public String getProductCgy() {
            return ProductCgy;
        }

        public void setProductCgy(String ProductCgy) {
            this.ProductCgy = ProductCgy;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public String getImageUrl() {
            return ImageUrl;
        }

        public void setImageUrl(String ImageUrl) {
            this.ImageUrl = ImageUrl;
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
