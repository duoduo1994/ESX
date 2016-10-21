package com.example.administrator.entity;

import java.util.List;

/**
 * Created by User on 2016/10/14.
 */

public class Bean {

    /**
     * Name : 大鲳鱼
     * ImageUrl :
     * Number :
     * NormUnit :
     * ProUnit :
     * Remark :
     * Price :
     * Sales :
     */

    private BaseDataBean BaseData;
    /**
     * BaseData : {"Name":"大鲳鱼","ImageUrl":"","Number":"","NormUnit":"","ProUnit":"","Remark":"","Price":"","Sales":""}
     * RobData : 50.0000
     * RecommendSetMeal : []
     * EvaluateOverView : {"TotalCount":"0","AvStar":"","Details":[]}
     */

    private String RobData;
    /**
     * TotalCount : 0
     * AvStar :
     * Details : []
     */

    private EvaluateOverViewBean EvaluateOverView;
    private List<?> RecommendSetMeal;

    public BaseDataBean getBaseData() {
        return BaseData;
    }

    public void setBaseData(BaseDataBean BaseData) {
        this.BaseData = BaseData;
    }

    public String getRobData() {
        return RobData;
    }

    public void setRobData(String RobData) {
        this.RobData = RobData;
    }

    public EvaluateOverViewBean getEvaluateOverView() {
        return EvaluateOverView;
    }

    public void setEvaluateOverView(EvaluateOverViewBean EvaluateOverView) {
        this.EvaluateOverView = EvaluateOverView;
    }

    public List<?> getRecommendSetMeal() {
        return RecommendSetMeal;
    }

    public void setRecommendSetMeal(List<?> RecommendSetMeal) {
        this.RecommendSetMeal = RecommendSetMeal;
    }

    public static class BaseDataBean {
        private String Name;
        private String ImageUrl;
        private String Number;
        private String NormUnit;
        private String ProUnit;
        private String Remark;
        private String Price;
        private String Sales;

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

        public String getNumber() {
            return Number;
        }

        public void setNumber(String Number) {
            this.Number = Number;
        }

        public String getNormUnit() {
            return NormUnit;
        }

        public void setNormUnit(String NormUnit) {
            this.NormUnit = NormUnit;
        }

        public String getProUnit() {
            return ProUnit;
        }

        public void setProUnit(String ProUnit) {
            this.ProUnit = ProUnit;
        }

        public String getRemark() {
            return Remark;
        }

        public void setRemark(String Remark) {
            this.Remark = Remark;
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

    public static class EvaluateOverViewBean {
        private String TotalCount;
        private String AvStar;
        private List<?> Details;

        public String getTotalCount() {
            return TotalCount;
        }

        public void setTotalCount(String TotalCount) {
            this.TotalCount = TotalCount;
        }

        public String getAvStar() {
            return AvStar;
        }

        public void setAvStar(String AvStar) {
            this.AvStar = AvStar;
        }

        public List<?> getDetails() {
            return Details;
        }

        public void setDetails(List<?> Details) {
            this.Details = Details;
        }
    }
}
