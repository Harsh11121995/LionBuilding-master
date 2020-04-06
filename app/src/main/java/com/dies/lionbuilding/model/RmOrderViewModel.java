package com.dies.lionbuilding.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RmOrderViewModel {

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

        @SerializedName("sord_id")
        @Expose
        private String sordId;
        @SerializedName("ord_id")
        @Expose
        private String ordId;
        @SerializedName("sord_slm_id")
        @Expose
        private String sordSlmId;
        @SerializedName("sord_dstr_id")
        @Expose
        private String sordDstrId;
        @SerializedName("sord_dl_id")
        @Expose
        private String sordDlId;
        @SerializedName("sord_prd_id")
        @Expose
        private String sordPrdId;
        @SerializedName("sord_qty")
        @Expose
        private String sordQty;
        @SerializedName("sord_price")
        @Expose
        private String sordPrice;
        @SerializedName("sord_total")
        @Expose
        private String sordTotal;
        @SerializedName("sord_point")
        @Expose
        private String sordPoint;
        @SerializedName("sdlr_point")
        @Expose
        private String sdlrPoint;
        @SerializedName("sdstr_point")
        @Expose
        private String sdstrPoint;
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
        private Object updatedDtm;
        @SerializedName("sales_exe_name")
        @Expose
        private String salesExeName;
        @SerializedName("deler_name")
        @Expose
        private String delerName;
        @SerializedName("distributor_name")
        @Expose
        private String distributorName;
        @SerializedName("product_name")
        @Expose
        private String productName;
        @SerializedName("product_price")
        @Expose
        private String productPrice;
        @SerializedName("product_img")
        @Expose
        private String productImg;
        @SerializedName("product_point")
        @Expose
        private String productPoint;

        public String getSordId() {
            return sordId;
        }

        public void setSordId(String sordId) {
            this.sordId = sordId;
        }

        public String getOrdId() {
            return ordId;
        }

        public void setOrdId(String ordId) {
            this.ordId = ordId;
        }

        public String getSordSlmId() {
            return sordSlmId;
        }

        public void setSordSlmId(String sordSlmId) {
            this.sordSlmId = sordSlmId;
        }

        public String getSordDstrId() {
            return sordDstrId;
        }

        public void setSordDstrId(String sordDstrId) {
            this.sordDstrId = sordDstrId;
        }

        public String getSordDlId() {
            return sordDlId;
        }

        public void setSordDlId(String sordDlId) {
            this.sordDlId = sordDlId;
        }

        public String getSordPrdId() {
            return sordPrdId;
        }

        public void setSordPrdId(String sordPrdId) {
            this.sordPrdId = sordPrdId;
        }

        public String getSordQty() {
            return sordQty;
        }

        public void setSordQty(String sordQty) {
            this.sordQty = sordQty;
        }

        public String getSordPrice() {
            return sordPrice;
        }

        public void setSordPrice(String sordPrice) {
            this.sordPrice = sordPrice;
        }

        public String getSordTotal() {
            return sordTotal;
        }

        public void setSordTotal(String sordTotal) {
            this.sordTotal = sordTotal;
        }

        public String getSordPoint() {
            return sordPoint;
        }

        public void setSordPoint(String sordPoint) {
            this.sordPoint = sordPoint;
        }

        public String getSdlrPoint() {
            return sdlrPoint;
        }

        public void setSdlrPoint(String sdlrPoint) {
            this.sdlrPoint = sdlrPoint;
        }

        public String getSdstrPoint() {
            return sdstrPoint;
        }

        public void setSdstrPoint(String sdstrPoint) {
            this.sdstrPoint = sdstrPoint;
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

        public Object getUpdatedDtm() {
            return updatedDtm;
        }

        public void setUpdatedDtm(Object updatedDtm) {
            this.updatedDtm = updatedDtm;
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

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getProductPrice() {
            return productPrice;
        }

        public void setProductPrice(String productPrice) {
            this.productPrice = productPrice;
        }

        public String getProductImg() {
            return productImg;
        }

        public void setProductImg(String productImg) {
            this.productImg = productImg;
        }

        public String getProductPoint() {
            return productPoint;
        }

        public void setProductPoint(String productPoint) {
            this.productPoint = productPoint;
        }

    }
}
