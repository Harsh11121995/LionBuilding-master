package com.dies.lionbuilding.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductModel {
    @SerializedName("statusCode")
    @Expose
    private Integer statusCode;
    @SerializedName("data")
    @Expose
    private List<ProductModel.Data> data = null;
    @SerializedName("message")
    @Expose
    private String message;

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public List<ProductModel.Data> getData() {
        return data;
    }

    public void setData(List<ProductModel.Data> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public class Data {

        @SerializedName("product_id")
        @Expose
        private String prcat_id;

        @SerializedName("product_catagory_id")
        @Expose
        private String product_catagory_id;

        @SerializedName("product_name")
        @Expose
        private String product_name;


        @SerializedName("product_price")
        @Expose
        private String product_price;
        @SerializedName("product_ratting")
        @Expose
        private String product_ratting;
        @SerializedName("product_weight")
        @Expose
        private String product_weight;
        @SerializedName("product_qty")
        @Expose
        private String product_qty;
        @SerializedName("product_barcode")
        @Expose
        private String product_barcode;

        @SerializedName("product_descpriction")
        @Expose
        private String product_descpriction;

        @SerializedName("product_img")
        @Expose
        private String product_img;

        @SerializedName("product_point")
        @Expose
        private String product_point;

        private int p_id;
        private String product_total;
        private String product_total_qty;




        public Data(String prcat_id, String product_catagory_id, String product_name, String product_price, String product_ratting, String product_weight, String product_qty, String product_barcode, String product_descpriction, String product_img, int p_id,String product_point) {
            this.prcat_id = prcat_id;
            this.product_catagory_id = product_catagory_id;
            this.product_name = product_name;
            this.product_price = product_price;
            this.product_ratting = product_ratting;
            this.product_weight = product_weight;
            this.product_qty = product_qty;
            this.product_barcode = product_barcode;
            this.product_descpriction = product_descpriction;
            this.product_img = product_img;
            this.p_id = p_id;
            this.product_point=product_point;
        }

        public String getPrcat_id() {
            return prcat_id;
        }

        public void setPrcat_id(String prcat_id) {
            this.prcat_id = prcat_id;
        }

        public String getProduct_catagory_id() {
            return product_catagory_id;
        }

        public void setProduct_catagory_id(String product_catagory_id) {
            this.product_catagory_id = product_catagory_id;
        }

        public String getProduct_name() {
            return product_name;
        }

        public void setProduct_name(String product_name) {
            this.product_name = product_name;
        }

        public String getProduct_price() {
            return product_price;
        }

        public void setProduct_price(String product_price) {
            this.product_price = product_price;
        }

        public String getProduct_ratting() {
            return product_ratting;
        }

        public void setProduct_ratting(String product_ratting) {
            this.product_ratting = product_ratting;
        }

        public String getProduct_weight() {
            return product_weight;
        }

        public void setProduct_weight(String product_weight) {
            this.product_weight = product_weight;
        }

        public String getProduct_qty() {
            return product_qty;
        }

        public void setProduct_qty(String product_qty) {
            this.product_qty = product_qty;
        }

        public String getProduct_barcode() {
            return product_barcode;
        }

        public void setProduct_barcode(String product_barcode) {
            this.product_barcode = product_barcode;
        }

        public String getProduct_descpriction() {
            return product_descpriction;
        }

        public void setProduct_descpriction(String product_descpriction) {
            this.product_descpriction = product_descpriction;
        }

        public String getProduct_img() {
            return product_img;
        }

        public void setProduct_img(String product_img) {
            this.product_img = product_img;
        }

        public int getP_id() {
            return p_id;
        }

        public void setP_id(int p_id) {
            this.p_id = p_id;
        }

        public String getProduct_point() {
            return product_point;
        }

        public void setProduct_point(String product_point) {
            this.product_point = product_point;
        }

        public String getProduct_total() {
            return product_total;
        }

        public void setProduct_total(String product_total) {
            this.product_total = product_total;
        }

        public String getProduct_total_qty() {
            return product_total_qty;
        }

        public void setProduct_total_qty(String product_total_qty) {
            this.product_total_qty = product_total_qty;
        }
    }
    }
