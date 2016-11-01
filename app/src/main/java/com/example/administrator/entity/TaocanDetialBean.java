package com.example.administrator.entity;

import android.text.TextUtils;

import java.util.List;

/**
 * Created by WangYueli on 2016/9/26.
 */

public class TaocanDetialBean {

    /**
     * DishSortNo : 1
     * DishID : 81
     * DishName : 红膏咸蟹
     * Material : 蟹
     * Norm : 300
     * NormUnit : g
     * ImageUrl : http://120.27.141.95:8085/UploadFile/Data/_0100_Lengcai/_010010_Honggaoxianxie1.jpg
     * DishCgy : 32
     * UnitPrice : 88.0000
     * NetPrice :
     * PretaxPrice :
     * AftertaxPrice :
     * SupplierID :
     * Place : 宁波
     * IsDection : 1
     * IsPass : 1
     * IsReplace : 0
     * ReplaceDishID :
     * Remark :
     * UpdateTm :
     * UpdateModule :
     * UpdateID :
     * UpdateCode :
     * UpdTime :
     * BigImageUrl : UploadFile/Data/_0100_BLengcai/_010010_Honggaoxianxie1.jpg
     * ReserveNumber2 :
     * ReserveNumber3 :
     * ReserveNumber4 :
     * ReserveNumber5 :
     * ReserveString1 :
     * ReserveString2 :
     * ReserveString3 :
     * ReserveString4 : 0
     * ReserveString5 : 0
     * UpdCount : 0
     * NormPf : 约
     * ReplaceID :
     * ReplaceName :
     * ReplaceImageUrl : http://120.27.141.95:8085/
     */

    private List<CaidanBean> Caidan;
    /**
     * pkDishCgyID : 32
     * Name : 冷菜
     */

    private List<CaixiBean> Caixi;

    public List<CaidanBean> getCaidan() {
        return Caidan;
    }

//    @Override
//    public String toString() {
//        return "[ Caidan"+Caidan.toArray().toString()+",Caixi"+Caixi.toArray().toString()+"]";
//    }
    public void setCaidan(List<CaidanBean> Caidan) {
        this.Caidan = Caidan;
    }

    public List<CaixiBean> getCaixi() {
        return Caixi;
    }

    public void setCaixi(List<CaixiBean> Caixi) {
        this.Caixi = Caixi;
    }
    public static class CaidanBean {
        public CaidanBean(String dishName, String place, String normPf) {
            DishName = dishName;
            Place = place;
            NormPf = normPf;
        }
        private String DishSortNo;
        private String DishID;
        private String DishName;
        private String Material;
        private String Norm;
        private String NormUnit;
        private String ImageUrl;
        private String DishCgy;
        private String UnitPrice;
        private String NetPrice;
        private String PretaxPrice;
        private String AftertaxPrice;
        private String SupplierID;
        private String Place;
        private String IsDection;
        private String IsPass;
        private String IsReplace;
        private String ReplaceDishID;
        private String Remark;
        private String UpdateTm;
        private String UpdateModule;
        private String UpdateID;
        private String UpdateCode;
        private String UpdTime;
        private String BigImageUrl;
        private String ReserveNumber2;
        private String ReserveNumber3;
        private String ReserveNumber4;
        private String ReserveNumber5;
        private String ReserveString1;
        private String ReserveString2;
        private String ReserveString3;
        private String ReserveString4;
        private String ReserveString5;
        private String UpdCount;
        private String NormPf;
        private String ReplaceID;
        private String ReplaceName;
        private String ReplaceImageUrl;

        @Override
        public String toString() {
            return   "["+DishSortNo+DishID +DishName+ Material +Norm+ NormUnit +ImageUrl+ DishCgy + UnitPrice+
            NetPrice + PretaxPrice+ AftertaxPrice +SupplierID+ Place + IsDection+ IsPass + IsReplace+ ReplaceDishID +Remark+
            UpdateTm +UpdateModule+ UpdateID +UpdateCode+ UpdTime + BigImageUrl+ ReserveNumber2 + ReserveNumber3+ ReserveNumber4
                   + ReserveNumber5+ ReserveString1 + ReserveString2+ ReserveString3 +ReserveString4+ ReserveString5 + UpdCount+ NormPf
                   + ReplaceID+ ReplaceName +ReplaceImageUrl+"]";
        }

        public String getDishSortNo() {
            return DishSortNo;
        }

        public void setDishSortNo(String DishSortNo) {
            this.DishSortNo = DishSortNo;
        }

        public String getDishID() {
            return DishID;
        }

        public void setDishID(String DishID) {
            this.DishID = DishID;
        }

        public String getDishName() {
            return DishName;
        }

        public CaidanBean setDishName(String dishName) {
            if (!TextUtils.isEmpty(dishName)) {
                DishName = dishName;
            }
            return this;
        }

        public String getMaterial() {
            return Material;
        }

        public void setMaterial(String Material) {
            this.Material = Material;
        }

        public String getNorm() {
            return Norm;
        }

        public void setNorm(String Norm) {
            this.Norm = Norm;
        }

        public String getNormUnit() {
            return NormUnit;
        }

        public void setNormUnit(String NormUnit) {
            this.NormUnit = NormUnit;
        }

        public String getImageUrl() {
            return ImageUrl;
        }

        public void setImageUrl(String ImageUrl) {
            this.ImageUrl = ImageUrl;
        }

        public String getDishCgy() {
            return DishCgy;
        }

        public void setDishCgy(String DishCgy) {
            this.DishCgy = DishCgy;
        }

        public String getUnitPrice() {
            return UnitPrice;
        }

        public void setUnitPrice(String UnitPrice) {
            this.UnitPrice = UnitPrice;
        }

        public String getNetPrice() {
            return NetPrice;
        }

        public void setNetPrice(String NetPrice) {
            this.NetPrice = NetPrice;
        }

        public String getPretaxPrice() {
            return PretaxPrice;
        }

        public void setPretaxPrice(String PretaxPrice) {
            this.PretaxPrice = PretaxPrice;
        }

        public String getAftertaxPrice() {
            return AftertaxPrice;
        }

        public void setAftertaxPrice(String AftertaxPrice) {
            this.AftertaxPrice = AftertaxPrice;
        }

        public String getSupplierID() {
            return SupplierID;
        }

        public void setSupplierID(String SupplierID) {
            this.SupplierID = SupplierID;
        }

        public String getPlace() {
            return Place;
        }

        public void setPlace(String Place) {
            this.Place = Place;
        }

        public String getIsDection() {
            return IsDection;
        }

        public void setIsDection(String IsDection) {
            this.IsDection = IsDection;
        }

        public String getIsPass() {
            return IsPass;
        }

        public CaidanBean setIsPass(String isPass) {
            if (!TextUtils.isEmpty(isPass)) {
                IsPass = isPass;
            }
            return this;
        }

        public String getIsReplace() {
            return IsReplace;
        }

        public CaidanBean setIsReplace(String isReplace) {
            if (!TextUtils.isEmpty(isReplace)) {
                IsReplace = isReplace;
            }
            return this;
        }

        public String getReplaceDishID() {
            return ReplaceDishID;
        }

        public void setReplaceDishID(String ReplaceDishID) {
            this.ReplaceDishID = ReplaceDishID;
        }

        public String getRemark() {
            return Remark;
        }

        public void setRemark(String Remark) {
            this.Remark = Remark;
        }

        public String getUpdateTm() {
            return UpdateTm;
        }

        public void setUpdateTm(String UpdateTm) {
            this.UpdateTm = UpdateTm;
        }

        public String getUpdateModule() {
            return UpdateModule;
        }

        public void setUpdateModule(String UpdateModule) {
            this.UpdateModule = UpdateModule;
        }

        public String getUpdateID() {
            return UpdateID;
        }

        public void setUpdateID(String UpdateID) {
            this.UpdateID = UpdateID;
        }

        public String getUpdateCode() {
            return UpdateCode;
        }

        public void setUpdateCode(String UpdateCode) {
            this.UpdateCode = UpdateCode;
        }

        public String getUpdTime() {
            return UpdTime;
        }

        public void setUpdTime(String UpdTime) {
            this.UpdTime = UpdTime;
        }

        public String getBigImageUrl() {
            return BigImageUrl;
        }

        public void setBigImageUrl(String BigImageUrl) {
            this.BigImageUrl = BigImageUrl;
        }

        public String getReserveNumber2() {
            return ReserveNumber2;
        }

        public void setReserveNumber2(String ReserveNumber2) {
            this.ReserveNumber2 = ReserveNumber2;
        }

        public String getReserveNumber3() {
            return ReserveNumber3;
        }

        public void setReserveNumber3(String ReserveNumber3) {
            this.ReserveNumber3 = ReserveNumber3;
        }

        public String getReserveNumber4() {
            return ReserveNumber4;
        }

        public void setReserveNumber4(String ReserveNumber4) {
            this.ReserveNumber4 = ReserveNumber4;
        }

        public String getReserveNumber5() {
            return ReserveNumber5;
        }

        public void setReserveNumber5(String ReserveNumber5) {
            this.ReserveNumber5 = ReserveNumber5;
        }

        public String getReserveString1() {
            return ReserveString1;
        }

        public void setReserveString1(String ReserveString1) {
            this.ReserveString1 = ReserveString1;
        }

        public String getReserveString2() {
            return ReserveString2;
        }

        public void setReserveString2(String ReserveString2) {
            this.ReserveString2 = ReserveString2;
        }

        public String getReserveString3() {
            return ReserveString3;
        }

        public void setReserveString3(String ReserveString3) {
            this.ReserveString3 = ReserveString3;
        }

        public String getReserveString4() {
            return ReserveString4;
        }

        public void setReserveString4(String ReserveString4) {
            this.ReserveString4 = ReserveString4;
        }

        public String getReserveString5() {
            return ReserveString5;
        }

        public void setReserveString5(String ReserveString5) {
            this.ReserveString5 = ReserveString5;
        }

        public String getUpdCount() {
            return UpdCount;
        }

        public void setUpdCount(String UpdCount) {
            this.UpdCount = UpdCount;
        }

        public String getNormPf() {
            return NormPf;
        }

        public void setNormPf(String NormPf) {
            this.NormPf = NormPf;
        }

        public String getReplaceID() {
            return ReplaceID;
        }

        public void setReplaceID(String ReplaceID) {
            this.ReplaceID = ReplaceID;
        }

        public String getReplaceName() {
            return ReplaceName;
        }

        public void setReplaceName(String ReplaceName) {
            this.ReplaceName = ReplaceName;
        }

        public String getReplaceImageUrl() {
            return ReplaceImageUrl;
        }

        public void setReplaceImageUrl(String ReplaceImageUrl) {
            this.ReplaceImageUrl = ReplaceImageUrl;
        }
    }

    public static class CaixiBean {
        private String pkDishCgyID;
        private String Name;

        @Override
        public String toString() {
            return "["+pkDishCgyID+Name+"]";
        }

        public String getPkDishCgyID() {
            return pkDishCgyID;
        }

        public void setPkDishCgyID(String pkDishCgyID) {
            this.pkDishCgyID = pkDishCgyID;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }
    }
}
