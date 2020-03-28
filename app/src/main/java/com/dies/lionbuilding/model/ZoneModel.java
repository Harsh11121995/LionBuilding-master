package com.dies.lionbuilding.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ZoneModel {

    @SerializedName("statusCode")
    @Expose
    private Integer statusCode;
    @SerializedName("data")
    @Expose
    private List<ZoneModel.Data> data = null;
    @SerializedName("message")
    @Expose
    private String message;

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public List<ZoneModel.Data> getData() {
        return data;
    }

    public void setData(List<ZoneModel.Data> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public class Data {

        @SerializedName("zn_id")
        @Expose
        private String zn_id;

        @SerializedName("zn_name")
        @Expose
        private String zn_name;
        @SerializedName("zn_status")
        @Expose
        private String zn_status;
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


        @SerializedName("wrd_id")
        @Expose
        private String wrd_id;

        @SerializedName("wrd_name")
        @Expose
        private String wrd_name;

        @SerializedName("wrd_status")
        @Expose
        private String wrd_status;

        @SerializedName("city_name")
        @Expose
        private String city_name;



        public String getWrd_id() {
            return wrd_id;
        }

        public void setWrd_id(String wrd_id) {
            this.wrd_id = wrd_id;
        }

        public String getWrd_name() {
            return wrd_name;
        }

        public void setWrd_name(String wrd_name) {
            this.wrd_name = wrd_name;
        }

        public String getWrd_status() {
            return wrd_status;
        }

        public void setWrd_status(String wrd_status) {
            this.wrd_status = wrd_status;
        }

        public String getZn_id() {
            return zn_id;
        }

        public void setZn_id(String zn_id) {
            this.zn_id = zn_id;
        }

        public String getZn_name() {
            return zn_name;
        }

        public void setZn_name(String zn_name) {
            this.zn_name = zn_name;
        }

        public String getZn_status() {
            return zn_status;
        }

        public void setZn_status(String zn_status) {
            this.zn_status = zn_status;
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

        public String getCity_name() {
            return city_name;
        }

        public void setCity_name(String city_name) {
            this.city_name = city_name;
        }
    }
}
