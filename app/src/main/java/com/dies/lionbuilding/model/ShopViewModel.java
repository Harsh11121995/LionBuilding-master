package com.dies.lionbuilding.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ShopViewModel {
    @SerializedName("statusCode")
    @Expose
    private Integer statusCode;
    @SerializedName("data")
    @Expose
    private List<ShopViewModel.Data> data = null;
    @SerializedName("message")
    @Expose
    private String message;

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public List<ShopViewModel.Data> getData() {
        return data;
    }

    public void setData(List<ShopViewModel.Data> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public class Data {

        @SerializedName("sl_id")
        @Expose
        private String sl_id;

        @SerializedName("userId")
        @Expose
        private String userId;
        @SerializedName("shop_name")
        @Expose
        private String shop_name;

        @SerializedName("sl_address_line1")
        @Expose
        private String sl_address_line1;

        @SerializedName("sl_address_line2")
        @Expose
        private String sl_address_line2;

        @SerializedName("sl_pincode")
        @Expose
        private String sl_pincode;
        @SerializedName("sl_latitude")
        @Expose
        private String sl_latitude;
        @SerializedName("sl_longitude")
        @Expose
        private String sl_longitude;
        @SerializedName("sl_zone")
        @Expose
        private String sl_zone;
        @SerializedName("sl_wrd_id")
        @Expose
        private String sl_wrd_id;
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
        private String app_type;
        @SerializedName("zone_name")
        @Expose
        private String zone_name;
        @SerializedName("ward_name")
        @Expose
        private String ward_name;

        public String getSl_id() {
            return sl_id;
        }

        public void setSl_id(String sl_id) {
            this.sl_id = sl_id;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getShop_name() {
            return shop_name;
        }

        public void setShop_name(String shop_name) {
            this.shop_name = shop_name;
        }

        public String getSl_address_line1() {
            return sl_address_line1;
        }

        public void setSl_address_line1(String sl_address_line1) {
            this.sl_address_line1 = sl_address_line1;
        }

        public String getSl_address_line2() {
            return sl_address_line2;
        }

        public void setSl_address_line2(String sl_address_line2) {
            this.sl_address_line2 = sl_address_line2;
        }

        public String getSl_pincode() {
            return sl_pincode;
        }

        public void setSl_pincode(String sl_pincode) {
            this.sl_pincode = sl_pincode;
        }

        public String getSl_latitude() {
            return sl_latitude;
        }

        public void setSl_latitude(String sl_latitude) {
            this.sl_latitude = sl_latitude;
        }

        public String getSl_longitude() {
            return sl_longitude;
        }

        public void setSl_longitude(String sl_longitude) {
            this.sl_longitude = sl_longitude;
        }

        public String getSl_zone() {
            return sl_zone;
        }

        public void setSl_zone(String sl_zone) {
            this.sl_zone = sl_zone;
        }

        public String getSl_wrd_id() {
            return sl_wrd_id;
        }

        public void setSl_wrd_id(String sl_wrd_id) {
            this.sl_wrd_id = sl_wrd_id;
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

        public String getApp_type() {
            return app_type;
        }

        public void setApp_type(String app_type) {
            this.app_type = app_type;
        }

        public String getZone_name() {
            return zone_name;
        }

        public void setZone_name(String zone_name) {
            this.zone_name = zone_name;
        }

        public String getWard_name() {
            return ward_name;
        }

        public void setWard_name(String ward_name) {
            this.ward_name = ward_name;
        }
    }
}
