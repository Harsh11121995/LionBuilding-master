package com.dies.lionbuilding.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GiftModel {

    @SerializedName("statusCode")
    @Expose
    private Integer statusCode;
    @SerializedName("data")
    @Expose
    private List<GiftModel.Data> data = null;
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

        @SerializedName("gft_id")
        @Expose
        private String gft_id;
        @SerializedName("gft_name")
        @Expose
        private String gft_name;
        @SerializedName("gft_point")
        @Expose
        private String gft_point;
        @SerializedName("gft_status")
        @Expose
        private String gft_status;
        @SerializedName("offer_image")
        @Expose
        private String offer_image;
        @SerializedName("offer_discription")
        @Expose
        private String offer_discription;
        @SerializedName("offer_start")
        @Expose
        private String offer_start;
        @SerializedName("offer_end")
        @Expose
        private String offer_end;

        public String getGft_id() {
            return gft_id;
        }

        public void setGft_id(String gft_id) {
            this.gft_id = gft_id;
        }

        public String getGft_name() {
            return gft_name;
        }

        public void setGft_name(String gft_name) {
            this.gft_name = gft_name;
        }

        public String getGft_point() {
            return gft_point;
        }

        public void setGft_point(String gft_point) {
            this.gft_point = gft_point;
        }

        public String getGft_status() {
            return gft_status;
        }

        public void setGft_status(String gft_status) {
            this.gft_status = gft_status;
        }

        public String getOffer_image() {
            return offer_image;
        }

        public void setOffer_image(String offer_image) {
            this.offer_image = offer_image;
        }

        public String getOffer_discription() {
            return offer_discription;
        }

        public void setOffer_discription(String offer_discription) {
            this.offer_discription = offer_discription;
        }

        public String getOffer_start() {
            return offer_start;
        }

        public void setOffer_start(String offer_start) {
            this.offer_start = offer_start;
        }

        public String getOffer_end() {
            return offer_end;
        }

        public void setOffer_end(String offer_end) {
            this.offer_end = offer_end;
        }
    }
}
