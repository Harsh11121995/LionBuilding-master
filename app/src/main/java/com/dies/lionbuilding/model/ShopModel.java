package com.dies.lionbuilding.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ShopModel {

    public class RouteData {

        @SerializedName("sl_id")
        @Expose
        private String sl_id;

        @SerializedName("shop_name")
        @Expose
        private String shop_name;

        @SerializedName("sl_address_line1")
        @Expose
        private String sl_address_line1;

        @SerializedName("sl_address_line2")
        @Expose
        private String sl_address_line2;
        @SerializedName("sl_pincode")
        @Expose
        private String sl_pincode;
        @SerializedName("sl_latitude")
        @Expose
        private String sl_latitude;
        @SerializedName("sl_longitude")
        @Expose
        private String sl_longitude;

        @SerializedName("dealer_name")
        @Expose
        private String dealer_name;

        public String getSl_id() {
            return sl_id;
        }

        public void setSl_id(String sl_id) {
            this.sl_id = sl_id;
        }

        public String getShop_name() {
            return shop_name;
        }

        public void setShop_name(String shop_name) {
            this.shop_name = shop_name;
        }

        public String getSl_address_line1() {
            return sl_address_line1;
        }

        public void setSl_address_line1(String sl_address_line1) {
            this.sl_address_line1 = sl_address_line1;
        }

        public String getSl_address_line2() {
            return sl_address_line2;
        }

        public void setSl_address_line2(String sl_address_line2) {
            this.sl_address_line2 = sl_address_line2;
        }

        public String getSl_pincode() {
            return sl_pincode;
        }

        public void setSl_pincode(String sl_pincode) {
            this.sl_pincode = sl_pincode;
        }

        public String getSl_latitude() {
            return sl_latitude;
        }

        public void setSl_latitude(String sl_latitude) {
            this.sl_latitude = sl_latitude;
        }

        public String getSl_longitude() {
            return sl_longitude;
        }

        public void setSl_longitude(String sl_longitude) {
            this.sl_longitude = sl_longitude;
        }

        public String getDealer_name() {
            return dealer_name;
        }

        public void setDealer_name(String dealer_name) {
            this.dealer_name = dealer_name;
        }
    }
}
