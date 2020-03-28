package com.dies.lionbuilding.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WardModel {

    @SerializedName("statusCode")
    @Expose
    private Integer statusCode;
    @SerializedName("data")
    @Expose
    private List<WardModel.Data> data = null;
    @SerializedName("message")
    @Expose
    private String message;

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public List<WardModel.Data> getData() {
        return data;
    }

    public void setData(List<WardModel.Data> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public class Data {

        @SerializedName("wrd_id")
        @Expose
        private String wrd_id;

        @SerializedName("wrd_name")
        @Expose
        private String wrd_name;

        @SerializedName("wrd_status")
        @Expose
        private String wrd_status;

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


    }
}
