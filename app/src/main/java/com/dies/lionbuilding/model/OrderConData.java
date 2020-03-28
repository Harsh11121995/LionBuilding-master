package com.dies.lionbuilding.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderConData {

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

        @SerializedName("ord_id")
        @Expose
        private String ordId;
        @SerializedName("ord_slm_id")
        @Expose
        private String ordSlmId;
        @SerializedName("ord_dstr_id")
        @Expose
        private String ordDstrId;
        @SerializedName("ord_dl_id")
        @Expose
        private String ordDlId;
        @SerializedName("ord_qty")
        @Expose
        private String ordQty;
        @SerializedName("ord_discount")
        @Expose
        private Object ordDiscount;
        @SerializedName("ord_total")
        @Expose
        private String ordTotal;
        @SerializedName("ord_point")
        @Expose
        private String ordPoint;
        @SerializedName("ord_date")
        @Expose
        private String ordDate;
        @SerializedName("ord_status")
        @Expose
        private String ordStatus;
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
        private Object updatedBy;
        @SerializedName("updatedDtm")
        @Expose
        private String updatedDtm;
        @SerializedName("app_type")
        @Expose
        private String appType;
        @SerializedName("ord_type")
        @Expose
        private String ordType;
        @SerializedName("dlr_point")
        @Expose
        private String dlrPoint;
        @SerializedName("dstr_point")
        @Expose
        private String dstrPoint;
        @SerializedName("order_status_name")
        @Expose
        private String orderStatusName;
        @SerializedName("sales_exe_name")
        @Expose
        private String salesExeName;
        @SerializedName("deler_name")
        @Expose
        private String delerName;
        @SerializedName("distributor_name")
        @Expose
        private String distributorName;

        public String getOrdId() {
            return ordId;
        }

        public void setOrdId(String ordId) {
            this.ordId = ordId;
        }

        public String getOrdSlmId() {
            return ordSlmId;
        }

        public void setOrdSlmId(String ordSlmId) {
            this.ordSlmId = ordSlmId;
        }

        public String getOrdDstrId() {
            return ordDstrId;
        }

        public void setOrdDstrId(String ordDstrId) {
            this.ordDstrId = ordDstrId;
        }

        public String getOrdDlId() {
            return ordDlId;
        }

        public void setOrdDlId(String ordDlId) {
            this.ordDlId = ordDlId;
        }

        public String getOrdQty() {
            return ordQty;
        }

        public void setOrdQty(String ordQty) {
            this.ordQty = ordQty;
        }

        public Object getOrdDiscount() {
            return ordDiscount;
        }

        public void setOrdDiscount(Object ordDiscount) {
            this.ordDiscount = ordDiscount;
        }

        public String getOrdTotal() {
            return ordTotal;
        }

        public void setOrdTotal(String ordTotal) {
            this.ordTotal = ordTotal;
        }

        public String getOrdPoint() {
            return ordPoint;
        }

        public void setOrdPoint(String ordPoint) {
            this.ordPoint = ordPoint;
        }

        public String getOrdDate() {
            return ordDate;
        }

        public void setOrdDate(String ordDate) {
            this.ordDate = ordDate;
        }

        public String getOrdStatus() {
            return ordStatus;
        }

        public void setOrdStatus(String ordStatus) {
            this.ordStatus = ordStatus;
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

        public Object getUpdatedBy() {
            return updatedBy;
        }

        public void setUpdatedBy(Object updatedBy) {
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

        public String getOrdType() {
            return ordType;
        }

        public void setOrdType(String ordType) {
            this.ordType = ordType;
        }

        public String getDlrPoint() {
            return dlrPoint;
        }

        public void setDlrPoint(String dlrPoint) {
            this.dlrPoint = dlrPoint;
        }

        public String getDstrPoint() {
            return dstrPoint;
        }

        public void setDstrPoint(String dstrPoint) {
            this.dstrPoint = dstrPoint;
        }

        public String getOrderStatusName() {
            return orderStatusName;
        }

        public void setOrderStatusName(String orderStatusName) {
            this.orderStatusName = orderStatusName;
        }

        public String getSalesExeName() {
            return salesExeName;
        }

        public void setSalesExeName(String salesExeName) {
            this.salesExeName = salesExeName;
        }

        public String getDelerName() {
            return delerName;
        }

        public void setDelerName(String delerName) {
            this.delerName = delerName;
        }

        public String getDistributorName() {
            return distributorName;
        }

        public void setDistributorName(String distributorName) {
            this.distributorName = distributorName;
        }

    }
}
