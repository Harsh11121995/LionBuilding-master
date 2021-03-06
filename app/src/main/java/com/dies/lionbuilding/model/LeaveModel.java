package com.dies.lionbuilding.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LeaveModel {

    @SerializedName("statusCode")
    @Expose
    private Integer statusCode;
    @SerializedName("data")
    @Expose
    private List<LeaveModel.Data> data = null;
    @SerializedName("message")
    @Expose
    private String message;

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public List<LeaveModel.Data> getData() {
        return data;
    }

    public void setData(List<LeaveModel.Data> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public class Data {

        @SerializedName("lv_id")
        @Expose
        private String lv_id;
        @SerializedName("lv_user_id")
        @Expose
        private String lv_user_id;
        @SerializedName("lv_desc")
        @Expose
        private String lv_desc;
        @SerializedName("lv_start_date")
        @Expose
        private String lv_start_date;
        @SerializedName("lv_end_date")
        @Expose
        private String lv_end_date;
        @SerializedName("lv_approved")
        @Expose
        private String lv_approved;

        @SerializedName("sales_exe_name")
        @Expose
        private String sales_exe_name;

        @SerializedName("rm_leave_status")
        @Expose
        private String rm_leave_status;

        @SerializedName("hr_leave_status")
        @Expose
        private String hr_leave_status;

        @SerializedName("lv_rm_approved")
        @Expose
        private String lv_rm_approved;

        @SerializedName("last_name")
        @Expose
        private String last_name;

        public String getLv_rm_approved() {
            return lv_rm_approved;
        }

        public void setLv_rm_approved(String lv_rm_approved) {
            this.lv_rm_approved = lv_rm_approved;
        }

        public String getLv_user_id() {
            return lv_user_id;
        }

        public void setLv_user_id(String lv_user_id) {
            this.lv_user_id = lv_user_id;
        }

        public String getRm_leave_status() {
            return rm_leave_status;
        }

        public void setRm_leave_status(String rm_leave_status) {
            this.rm_leave_status = rm_leave_status;
        }

        public String getHr_leave_status() {
            return hr_leave_status;
        }

        public void setHr_leave_status(String hr_leave_status) {
            this.hr_leave_status = hr_leave_status;
        }

        public String getLv_id() {
            return lv_id;
        }

        public void setLv_id(String lv_id) {
            this.lv_id = lv_id;
        }

        public String getLv_desc() {
            return lv_desc;
        }

        public void setLv_desc(String lv_desc) {
            this.lv_desc = lv_desc;
        }

        public String getLv_start_date() {
            return lv_start_date;
        }

        public void setLv_start_date(String lv_start_date) {
            this.lv_start_date = lv_start_date;
        }

        public String getLv_end_date() {
            return lv_end_date;
        }

        public void setLv_end_date(String lv_end_date) {
            this.lv_end_date = lv_end_date;
        }

        public String getLv_approved() {
            return lv_approved;
        }

        public void setLv_approved(String lv_approved) {
            this.lv_approved = lv_approved;
        }

        public String getSales_exe_name() {
            return sales_exe_name;
        }

        public void setSales_exe_name(String sales_exe_name) {
            this.sales_exe_name = sales_exe_name;
        }

        public String getLast_name() {
            return last_name;
        }

        public void setLast_name(String last_name) {
            this.last_name = last_name;
        }
    }
}
