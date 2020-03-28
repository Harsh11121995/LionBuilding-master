package com.dies.lionbuilding.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ExpanseModel {
    @SerializedName("statusCode")
    @Expose
    private Integer statusCode;
    @SerializedName("data")
    @Expose
    private List<ExpanseModel.Data> data = null;
    @SerializedName("message")
    @Expose
    private String message;

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public List<ExpanseModel.Data> getData() {
        return data;
    }

    public void setData(List<ExpanseModel.Data> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public class Data {

        @SerializedName("expt_id")
        @Expose
        private String expt_id;

        @SerializedName("expt_name")
        @Expose
        private String expt_name;

        @SerializedName("exp_id")
        @Expose
        private String exp_id;

        @SerializedName("exp_user_id")
        @Expose
        private String exp_user_id;

        @SerializedName("exp_desc")
        @Expose
        private String exp_desc;

        @SerializedName("exp_amount")
        @Expose
        private String exp_amount;

        @SerializedName("exp_type")
        @Expose
        private String exp_type;

        @SerializedName("exp_images")
        @Expose
        private String exp_images;

        @SerializedName("exp_date")
        @Expose
        private String exp_date;

        public String getExpt_id() {
            return expt_id;
        }

        public void setExpt_id(String expt_id) {
            this.expt_id = expt_id;
        }

        public String getExpt_name() {
            return expt_name;
        }

        public void setExpt_name(String expt_name) {
            this.expt_name = expt_name;
        }

        public String getExp_id() {
            return exp_id;
        }

        public void setExp_id(String exp_id) {
            this.exp_id = exp_id;
        }

        public String getExp_user_id() {
            return exp_user_id;
        }

        public void setExp_user_id(String exp_user_id) {
            this.exp_user_id = exp_user_id;
        }

        public String getExp_desc() {
            return exp_desc;
        }

        public void setExp_desc(String exp_desc) {
            this.exp_desc = exp_desc;
        }

        public String getExp_amount() {
            return exp_amount;
        }

        public void setExp_amount(String exp_amount) {
            this.exp_amount = exp_amount;
        }

        public String getExp_type() {
            return exp_type;
        }

        public void setExp_type(String exp_type) {
            this.exp_type = exp_type;
        }

        public String getExp_images() {
            return exp_images;
        }

        public void setExp_images(String exp_images) {
            this.exp_images = exp_images;
        }

        public String getExp_date() {
            return exp_date;
        }

        public void setExp_date(String exp_date) {
            this.exp_date = exp_date;
        }
    }
}
