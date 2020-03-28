package com.dies.lionbuilding.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RouteModel {
    @SerializedName("statusCode")
    @Expose
    private Integer statusCode;
    @SerializedName("data")
    @Expose
    private List<RouteModel.Data> data = null;

    @SerializedName("zone_data")
    @Expose
    private List<RouteModel.Zone> zone = null;

    @SerializedName("city_data")
    @Expose
    private List<RouteModel.City> city = null;

    @SerializedName("dealer_data")
    @Expose
    private List<RouteModel.Dealer> dealer = null;


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

    public List<Zone> getZone() {
        return zone;
    }

    public void setZone(List<Zone> zone) {
        this.zone = zone;
    }

    public List<City> getCity() {
        return city;
    }

    public void setCity(List<City> city) {
        this.city = city;
    }

    public List<Dealer> getDealer() {
        return dealer;
    }

    public void setDealer(List<Dealer> dealer) {
        this.dealer = dealer;
    }

    public class Data {

        @SerializedName("rt_id")
        @Expose
        private String rt_id;

        @SerializedName("rt_name")
        @Expose
        private String rt_name;
        @SerializedName("dl_id")
        @Expose
        private String dl_id;
        @SerializedName("rt_date")
        @Expose
        private String rt_date;
        @SerializedName("rt_zone")
        @Expose
        private String rt_zone;
        @SerializedName("rt_wrd_id")
        @Expose
        private String rt_wrd_id;

        @SerializedName("sales_exe_name")
        @Expose
        private String sales_exe_name;

        @SerializedName("district_name")
        @Expose
        private String district_name;

        @SerializedName("zn_id")
        @Expose
        private String zn_id;

        @SerializedName("zn_name")
        @Expose
        private String zn_name;

        @SerializedName("city_id")
        @Expose
        private String city_id;

        @SerializedName("city_name")
        @Expose
        private String city_name;

        @SerializedName("sl_id")
        @Expose
        private String sl_id;

        @SerializedName("shop_name")
        @Expose
        private String shop_name;

        @SerializedName("sday")
        @Expose
        private String sday;

        @SerializedName("route_name")
        @Expose
        private String route_name;


        @SerializedName("route_data")
        @Expose
        private List<RouteModel.RouteData> route_data = null;

        public String getRt_id() {
            return rt_id;
        }

        public void setRt_id(String rt_id) {
            this.rt_id = rt_id;
        }

        public String getDl_id() {
            return dl_id;
        }

        public void setDl_id(String dl_id) {
            this.dl_id = dl_id;
        }

        public String getRt_date() {
            return rt_date;
        }

        public void setRt_date(String rt_date) {
            this.rt_date = rt_date;
        }

        public String getRt_zone() {
            return rt_zone;
        }

        public void setRt_zone(String rt_zone) {
            this.rt_zone = rt_zone;
        }

        public String getRt_wrd_id() {
            return rt_wrd_id;
        }

        public void setRt_wrd_id(String rt_wrd_id) {
            this.rt_wrd_id = rt_wrd_id;
        }

        public String getSales_exe_name() {
            return sales_exe_name;
        }

        public void setSales_exe_name(String sales_exe_name) {
            this.sales_exe_name = sales_exe_name;
        }


        public List<RouteData> getRoute_data() {
            return route_data;
        }

        public void setRoute_data(List<RouteData> route_data) {
            this.route_data = route_data;
        }

        public String getDistrict_name() {
            return district_name;
        }

        public void setDistrict_name(String district_name) {
            this.district_name = district_name;
        }

        public String getRt_name() {
            return rt_name;
        }

        public void setRt_name(String rt_name) {
            this.rt_name = rt_name;
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

        public String getCity_id() {
            return city_id;
        }

        public void setCity_id(String city_id) {
            this.city_id = city_id;
        }

        public String getCity_name() {
            return city_name;
        }

        public void setCity_name(String city_name) {
            this.city_name = city_name;
        }

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

        public String getSday() {
            return sday;
        }

        public void setSday(String sday) {
            this.sday = sday;
        }

        public String getRoute_name() {
            return route_name;
        }

        public void setRoute_name(String route_name) {
            this.route_name = route_name;
        }
    }

    public class RouteData {

        @SerializedName("sl_id")
        @Expose
        private String sl_id;

        @SerializedName("userId")
        @Expose
        private String userId;

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

        @SerializedName("visited_status")
        @Expose
        private String visited_status;

        @SerializedName("tv_image")
        @Expose
        private String tv_image;

        public String getTv_image() {
            return tv_image;
        }

        public void setTv_image(String tv_image) {
            this.tv_image = tv_image;
        }

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

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getVisited_status() {
            return visited_status;
        }

        public void setVisited_status(String visited_status) {
            this.visited_status = visited_status;
        }
    }

    public class Zone {
        @SerializedName("zn_id")
        @Expose
        private String zn_id;

        @SerializedName("zn_name")
        @Expose
        private String zn_name;

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
    }

    public class City {
        @SerializedName("city_id")
        @Expose
        private String city_id;

        @SerializedName("city_name")
        @Expose
        private String city_name;

        public String getCity_id() {
            return city_id;
        }

        public void setCity_id(String city_id) {
            this.city_id = city_id;
        }

        public String getCity_name() {
            return city_name;
        }

        public void setCity_name(String city_name) {
            this.city_name = city_name;
        }
    }

    public class Dealer {
        @SerializedName("sl_id")
        @Expose
        private String sl_id;

        @SerializedName("shop_name")
        @Expose
        private String shop_name;

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
    }

}
