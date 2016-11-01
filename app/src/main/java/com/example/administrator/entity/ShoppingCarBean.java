package com.example.administrator.entity;

import java.util.List;

/**
 * Created by WangYueli on 2016/10/20.
 */

public class ShoppingCarBean {

    private YSXFreashBean YSXFreash;
    private FeastBean Feast;


    public YSXFreashBean getYSXFreash() {
        return YSXFreash;
    }

    public void setYSXFreash(YSXFreashBean YSXFreash) {
        this.YSXFreash = YSXFreash;
    }

    public FeastBean getFeast() {
        return Feast;
    }

    public void setFeast(FeastBean Feast) {
        this.Feast = Feast;
    }

    public static class YSXFreashBean {
        /**
         * ID : 生鲜ID
         * ProductCgy : 生鲜单品模块固定值为:Freash
         * Name : 生鲜名称
         * ImageUrl : 生鲜图片
         * TotalCount : 购买总数
         * TotalPrice : 购买总价
         * MinRecvHour : 最短预定时限
         * MaxRecvHour : 最长预定时限
         * GamishTotalPrice : 配菜总价
         * Gamish : [{"ID":"配菜ID","Name":"配菜名称"}]
         */

        private List<ContentBean> Content;

        public List<ContentBean> getContent() {
            return Content;
        }

        public void setContent(List<ContentBean> Content) {
            this.Content = Content;
        }

        public static class ContentBean {
            private String ID;
            private String ProductCgy;
            private String Name;
            private String ImageUrl;
            private String TotalCount;
            private String TotalPrice;
            private String MinRecvHour;
            private String MaxRecvHour;
            private String GamishTotalPrice;

            @Override
            public String toString() {
                return "ID"+ID+ProductCgy+Name+ImageUrl+TotalCount+GamishTotalPrice;
            }

            /**
             * ID : 配菜ID
             * Name : 配菜名称
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

            public String getGamishTotalPrice() {
                return GamishTotalPrice;
            }

            public void setGamishTotalPrice(String GamishTotalPrice) {
                this.GamishTotalPrice = GamishTotalPrice;
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
            }
        }
    }

    public static class FeastBean {
        /**
         * ID : 喜宴套餐ID
         * ProductCgy : 喜宴套餐模块固定值为:FeastSetMeal
         * Name : 喜宴套餐名称
         * ImageUrl : 喜宴套餐图片
         * TotalCount : 购买总数
         * TotalPrice : 总价
         */

        private List<ContentBean> Content;

        public List<ContentBean> getContent() {
            return Content;
        }

        public void setContent(List<ContentBean> Content) {
            this.Content = Content;
        }

        public static class ContentBean {
            private String ID;
            private String ProductCgy;
            private String Name;
            private String ImageUrl;
            private String TotalCount;
            private String TotalPrice;

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
}
