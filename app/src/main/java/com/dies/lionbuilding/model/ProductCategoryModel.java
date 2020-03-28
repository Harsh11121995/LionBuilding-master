package com.dies.lionbuilding.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductCategoryModel {

    @SerializedName("statusCode")
    @Expose
    private Integer statusCode;
    @SerializedName("data")
    @Expose
    private List<ProductCategoryModel.Data> data = null;
    @SerializedName("message")
    @Expose
    private String message;

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public List<ProductCategoryModel.Data> getData() {
        return data;
    }

    public void setData(List<ProductCategoryModel.Data> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public class Data {

        @SerializedName("prcat_id")
        @Expose
        private String prcat_id;

        @SerializedName("prcat_name")
        @Expose
        private String prcat_name;

        @SerializedName("prcat_status")
        @Expose
        private String prcat_status;

        @SerializedName("isDeleted")
        @Expose
        private String isDeleted;

        @SerializedName("createdBy")
        @Expose
        private String createdBy;

        @SerializedName("modifiedBy")
        @Expose
        private String modifiedBy;

        @SerializedName("createdDtm")
        @Expose
        private String createdDtm;
        @SerializedName("updatedDtm")
        @Expose
        private String updatedDtm;

        public String getPrcat_id() {
            return prcat_id;
        }

        public void setPrcat_id(String prcat_id) {
            this.prcat_id = prcat_id;
        }

        public String getPrcat_name() {
            return prcat_name;
        }

        public void setPrcat_name(String prcat_name) {
            this.prcat_name = prcat_name;
        }

        public String getPrcat_status() {
            return prcat_status;
        }

        public void setPrcat_status(String prcat_status) {
            this.prcat_status = prcat_status;
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

        public String getModifiedBy() {
            return modifiedBy;
        }

        public void setModifiedBy(String modifiedBy) {
            this.modifiedBy = modifiedBy;
        }

        public String getCreatedDtm() {
            return createdDtm;
        }

        public void setCreatedDtm(String createdDtm) {
            this.createdDtm = createdDtm;
        }

        public String getUpdatedDtm() {
            return updatedDtm;
        }

        public void setUpdatedDtm(String updatedDtm) {
            this.updatedDtm = updatedDtm;
        }
    }
}
