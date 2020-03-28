package com.dies.lionbuilding.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AddBankModel {
    @SerializedName("statusCode")
    @Expose
    private Integer statusCode;
    @SerializedName("data")
    @Expose
    private List<AddBankModel.Data> data = null;
    @SerializedName("message")
    @Expose
    private String message;


    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public List<AddBankModel.Data> getData() {
        return data;
    }

    public void setData(List<AddBankModel.Data> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public class Data {

        @SerializedName("bank_id")
        @Expose
        private String bank_id;

        @SerializedName("userId")
        @Expose
        private String userId;

        @SerializedName("bank_acnumber")
        @Expose
        private String bank_acnumber;

        @SerializedName("bank_name")
        @Expose
        private String bank_name;

        @SerializedName("bank_branchname")
        @Expose
        private String bank_branchname;

        @SerializedName("bank_ifsc_code")
        @Expose
        private String bank_ifsc_code;

        @SerializedName("bank_pan_no")
        @Expose
        private String bank_pan_no;

        @SerializedName("bank_pan_img")
        @Expose
        private String bank_pan_img;

        @SerializedName("bank_checqu")
        @Expose
        private String bank_checqu;

        @SerializedName("bank_adhar_card")
        @Expose
        private String bank_adhar_card;

        @SerializedName("bank_gst_number")
        @Expose
        private String bank_gst_number;
        @SerializedName("status")
        @Expose
        private String status;

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

        @SerializedName("username")
        @Expose
        private String username;

        @SerializedName("ac_hlname")
        @Expose
        private String ac_hlname;

        public String getBank_id() {
            return bank_id;
        }

        public void setBank_id(String bank_id) {
            this.bank_id = bank_id;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getBank_acnumber() {
            return bank_acnumber;
        }

        public void setBank_acnumber(String bank_acnumber) {
            this.bank_acnumber = bank_acnumber;
        }

        public String getBank_name() {
            return bank_name;
        }

        public void setBank_name(String bank_name) {
            this.bank_name = bank_name;
        }

        public String getBank_branchname() {
            return bank_branchname;
        }

        public void setBank_branchname(String bank_branchname) {
            this.bank_branchname = bank_branchname;
        }

        public String getBank_ifsc_code() {
            return bank_ifsc_code;
        }

        public void setBank_ifsc_code(String bank_ifsc_code) {
            this.bank_ifsc_code = bank_ifsc_code;
        }

        public String getBank_pan_no() {
            return bank_pan_no;
        }

        public void setBank_pan_no(String bank_pan_no) {
            this.bank_pan_no = bank_pan_no;
        }

        public String getBank_pan_img() {
            return bank_pan_img;
        }

        public void setBank_pan_img(String bank_pan_img) {
            this.bank_pan_img = bank_pan_img;
        }

        public String getBank_checqu() {
            return bank_checqu;
        }

        public void setBank_checqu(String bank_checqu) {
            this.bank_checqu = bank_checqu;
        }

        public String getBank_adhar_card() {
            return bank_adhar_card;
        }

        public void setBank_adhar_card(String bank_adhar_card) {
            this.bank_adhar_card = bank_adhar_card;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
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

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getAc_hlname() {
            return ac_hlname;
        }

        public void setAc_hlname(String ac_hlname) {
            this.ac_hlname = ac_hlname;
        }

        public String getBank_gst_number() {
            return bank_gst_number;
        }

        public void setBank_gst_number(String bank_gst_number) {
            this.bank_gst_number = bank_gst_number;
        }
    }
}
