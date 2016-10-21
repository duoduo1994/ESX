package com.example.administrator.entity;

import java.util.List;

/**
 * Created by WangYueli on 2016/10/20.
 */

public class ShoppingCarBean {

    private List<XcxyBean> Xcxy;
    private List<YsxBean> Ysx;

    public ShoppingCarBean() {
    }


    public List<XcxyBean> getXcxy() {
        return Xcxy;
    }

    public void setXcxy(List<XcxyBean> Xcxy) {
        this.Xcxy = Xcxy;
    }

    public List<YsxBean> getYsx() {
        return Ysx;
    }

    public void setYsx(List<YsxBean> Ysx) {
        this.Ysx = Ysx;
    }

    public static class XcxyBean {
        /**
         * ID : 餐具ID
         * ImageUrl : 餐具图片
         * Name : 餐具名称
         * TotalCount : 桌数
         * TotalPrice : 总价
         */

        private List<CoverBorrowBean> CoverBorrow;
        /**
         * ID : 喜宴服务ID
         * ImageUrl : 图片
         * Name : 服务名称
         */

        private List<FeastServerBean> FeastServer;


        /**
         * ID : 喜宴套餐ID
         * ImageUrl : 喜宴套餐图片
         * Name : 喜宴套餐名称
         * TotalCount : 购买总数
         * TotalPrice : 总价
         */


        private List<FeastSetMealBean> FeastSetMeal;

        public List<CoverBorrowBean> getCoverBorrow() {
            return CoverBorrow;
        }

        public void setCoverBorrow(List<CoverBorrowBean> CoverBorrow) {
            this.CoverBorrow = CoverBorrow;
        }

        public List<FeastServerBean> getFeastServer() {
            return FeastServer;
        }

        public void setFeastServer(List<FeastServerBean> FeastServer) {
            this.FeastServer = FeastServer;
        }

        public List<FeastSetMealBean> getFeastSetMeal() {
            return FeastSetMeal;
        }

        public void setFeastSetMeal(List<FeastSetMealBean> FeastSetMeal) {
            this.FeastSetMeal = FeastSetMeal;
        }

        public static class CoverBorrowBean {
            private String ID;
            private String ImageUrl;
            private String Name;
            private String TotalCount;
            private String TotalPrice;


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

        public static class FeastServerBean {
            private String ID;
            private String ImageUrl;
            private String Name;


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
        }

        public static class FeastSetMealBean {
            private String ID;
            private String ImageUrl;
            private String Name;
            private String TotalCount;
            private String TotalPrice;


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

    public static class YsxBean {

        /**
         * Gamish : [{"ID":"配菜ID","Name":"配菜名称","TotalPrice":"总价"}]
         * ID : 生鲜ID
         * ImageUrl : 生鲜图片
         * Name : 生鲜名称
         * TotalCount : 购买总数
         * TotalPrice : 购买总价
         */

        private List<FreashBean> Freash;
        /**
         * Gamish : [{"ID":"配菜ID","Name":"配菜名称","TotalPrice":"总价"}]
         * ID : 生鲜套餐ID
         * ImageUrl : 生鲜图片
         * Name : 生鲜名称
         * TotalCount : 购买总数
         * TotalPrice : 购买总价
         */

        private List<FreashSetMealBean> FreashSetMeal;

        public List<FreashBean> getFreash() {
            return Freash;
        }

        public void setFreash(List<FreashBean> Freash) {
            this.Freash = Freash;
        }

        public List<FreashSetMealBean> getFreashSetMeal() {
            return FreashSetMeal;
        }

        public void setFreashSetMeal(List<FreashSetMealBean> FreashSetMeal) {
            this.FreashSetMeal = FreashSetMeal;
        }

        public static class FreashBean {
            private String ID;
            private String ImageUrl;
            private String Name;
            private String TotalCount;
            private String TotalPrice;


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

        public static class FreashSetMealBean {
            private String ID;
            private String ImageUrl;
            private String Name;
            private String TotalCount;
            private String TotalPrice;
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
    }
}
