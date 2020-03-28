package com.dies.lionbuilding.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PastRouteModel {

    @SerializedName("statusCode")
    @Expose
    private Integer statusCode;
    @SerializedName("data")
    @Expose
    private List<Data> data = null;
    @SerializedName("message")
    @Expose
    private String message;

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public class Data {

        @SerializedName("tv_id")
        @Expose
        private String tvId;
        @SerializedName("tv_slid")
        @Expose
        private String tvSlid;
        @SerializedName("tv_dlid")
        @Expose
        private String tvDlid;
        @SerializedName("tv_comment")
        @Expose
        private String tvComment;
        @SerializedName("tv_lat")
        @Expose
        private String tvLat;
        @SerializedName("tv_long")
        @Expose
        private String tvLong;
        @SerializedName("tv_image")
        @Expose
        private String tvImage;
        @SerializedName("tv_day")
        @Expose
        private String tvDay;
        @SerializedName("tv_month")
        @Expose
        private String tvMonth;
        @SerializedName("tv_year")
        @Expose
        private String tvYear;
        @SerializedName("isDeleted")
        @Expose
        private String isDeleted;
        @SerializedName("createdBy")
        @Expose
        private String createdBy;
        @SerializedName("createdDtm")
        @Expose
        private String createdDtm;
        @SerializedName("updatedBy")
        @Expose
        private String updatedBy;
        @SerializedName("updatedDtm")
        @Expose
        private String updatedDtm;
        @SerializedName("app_type")
        @Expose
        private String appType;
        @SerializedName("tv_date")
        @Expose
        private String tvDate;
        @SerializedName("salexe_name")
        @Expose
        private String salexeName;
        @SerializedName("sl_id")
        @Expose
        private String slId;
        @SerializedName("userId")
        @Expose
        private String userId;
        @SerializedName("shop_name")
        @Expose
        private String shopName;
        @SerializedName("sl_address_line1")
        @Expose
        private String slAddressLine1;
        @SerializedName("sl_address_line2")
        @Expose
        private String slAddressLine2;
        @SerializedName("sl_pincode")
        @Expose
        private String slPincode;
        @SerializedName("dealer_name")
        @Expose
        private String dealerName;
        @SerializedName("last_name")
        @Expose
        private String lastName;
        @SerializedName("zone_name")
        @Expose
        private String zoneName;
        @SerializedName("city_name")
        @Expose
        private String cityName;
        @SerializedName("visited_status")
        @Expose
        private Integer visitedStatus;

        public String getTvId() {
            return tvId;
        }

        public void setTvId(String tvId) {
            this.tvId = tvId;
        }

        public String getTvSlid() {
            return tvSlid;
        }

        public void setTvSlid(String tvSlid) {
            this.tvSlid = tvSlid;
        }

        public String getTvDlid() {
            return tvDlid;
        }

        public void setTvDlid(String tvDlid) {
            this.tvDlid = tvDlid;
        }

        public String getTvComment() {
            return tvComment;
        }

        public void setTvComment(String tvComment) {
            this.tvComment = tvComment;
        }

        public String getTvLat() {
            return tvLat;
        }

        public void setTvLat(String tvLat) {
            this.tvLat = tvLat;
        }

        public String getTvLong() {
            return tvLong;
        }

        public void setTvLong(String tvLong) {
            this.tvLong = tvLong;
        }

        public String getTvImage() {
            return tvImage;
        }

        public void setTvImage(String tvImage) {
            this.tvImage = tvImage;
        }

        public String getTvDay() {
            return tvDay;
        }

        public void setTvDay(String tvDay) {
            this.tvDay = tvDay;
        }

        public String getTvMonth() {
            return tvMonth;
        }

        public void setTvMonth(String tvMonth) {
            this.tvMonth = tvMonth;
        }

        public String getTvYear() {
            return tvYear;
        }

        public void setTvYear(String tvYear) {
            this.tvYear = tvYear;
        }

        public String getIsDeleted() {
            return isDeleted;
        }

        public void setIsDeleted(String isDeleted) {
            this.isDeleted = isDeleted;
        }

        public String getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(String createdBy) {
            this.createdBy = createdBy;
        }

        public String getCreatedDtm() {
            return createdDtm;
        }

        public void setCreatedDtm(String createdDtm) {
            this.createdDtm = createdDtm;
        }

        public String getUpdatedBy() {
            return updatedBy;
        }

        public void setUpdatedBy(String updatedBy) {
            this.updatedBy = updatedBy;
        }

        public String getUpdatedDtm() {
            return updatedDtm;
        }

        public void setUpdatedDtm(String updatedDtm) {
            this.updatedDtm = updatedDtm;
        }

        public String getAppType() {
            return appType;
        }

        public void setAppType(String appType) {
            this.appType = appType;
        }

        public String getTvDate() {
            return tvDate;
        }

        public void setTvDate(String tvDate) {
            this.tvDate = tvDate;
        }

        public String getSalexeName() {
            return salexeName;
        }

        public void setSalexeName(String salexeName) {
            this.salexeName = salexeName;
        }

        public String getSlId() {
            return slId;
        }

        public void setSlId(String slId) {
            this.slId = slId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

        public String getSlAddressLine1() {
            return slAddressLine1;
        }

        public void setSlAddressLine1(String slAddressLine1) {
            this.slAddressLine1 = slAddressLine1;
        }

        public String getSlAddressLine2() {
            return slAddressLine2;
        }

        public void setSlAddressLine2(String slAddressLine2) {
            this.slAddressLine2 = slAddressLine2;
        }

        public String getSlPincode() {
            return slPincode;
        }

        public void setSlPincode(String slPincode) {
            this.slPincode = slPincode;
        }

        public String getDealerName() {
            return dealerName;
        }

        public void setDealerName(String dealerName) {
            this.dealerName = dealerName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getZoneName() {
            return zoneName;
        }

        public void setZoneName(String zoneName) {
            this.zoneName = zoneName;
        }

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }

        public Integer getVisitedStatus() {
            return visitedStatus;
        }

        public void setVisitedStatus(Integer visitedStatus) {
            this.visitedStatus = visitedStatus;
        }

    }

}
