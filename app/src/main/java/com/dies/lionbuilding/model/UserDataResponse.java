package com.dies.lionbuilding.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserDataResponse {

    @SerializedName("statusCode")
    @Expose
    private Integer statusCode;
    @SerializedName("total_friend")
    @Expose
    private Integer total_friend;
    @SerializedName("data")
    @Expose
    private List<Data> data = null;
    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("order_id")
    @Expose
    private String order_id;

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

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

    public Integer getTotal_friend() {
        return total_friend;
    }

    public void setTotal_friend(Integer total_friend) {
        this.total_friend = total_friend;
    }

    public class Data {

        @SerializedName("userId")
        @Expose
        private String userId;

        @SerializedName("name")
        @Expose
        private String firstName;

        @SerializedName("lname")
        @Expose
        private String lastName;

        @SerializedName("email")
        @Expose
        private String email;

        @SerializedName("user_name")
        @Expose
        private String userName;

        @SerializedName("mobile")
        @Expose
        private String mobile;
        @SerializedName("state_name")
        @Expose
        private String state_name;

        @SerializedName("city_name")
        @Expose
        private String city_name;

        @SerializedName("address_line1")
        @Expose
        private String address_line1;
        @SerializedName("address_line2")
        @Expose
        private String address_line2;

        @SerializedName("gst_no")
        @Expose
        private String gst;

        @SerializedName("login_status")
        @Expose
        private String login_status;
        @SerializedName("profile_image")
        @Expose
        private String profile_image;

        @SerializedName("typename")
        @Expose
        private String user_type;

        @SerializedName("total_point")
        @Expose
        private Integer total_point;
        @SerializedName("status_btn")
        @Expose
        private String status_btn;

        public String getStatus_btn() {
            return status_btn;
        }

        public void setStatus_btn(String status_btn) {
            this.status_btn = status_btn;
        }

        public String getProfile_image() {
            return profile_image;
        }

        public void setProfile_image(String profile_image) {
            this.profile_image = profile_image;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getState_name() {
            return state_name;
        }

        public void setState_name(String state_name) {
            this.state_name = state_name;
        }

        public String getCity_name() {
            return city_name;
        }

        public void setCity_name(String city_name) {
            this.city_name = city_name;
        }

        public String getAddress_line1() {
            return address_line1;
        }

        public void setAddress_line1(String address_line1) {
            this.address_line1 = address_line1;
        }

        public String getAddress_line2() {
            return address_line2;
        }

        public void setAddress_line2(String address_line2) {
            this.address_line2 = address_line2;
        }

        public String getGst() {
            return gst;
        }

        public void setGst(String gst) {
            this.gst = gst;
        }

        public String getLogin_status() {
            return login_status;
        }

        public void setLogin_status(String login_status) {
            this.login_status = login_status;
        }

        public String getUser_type() {
            return user_type;
        }

        public void setUser_type(String user_type) {
            this.user_type = user_type;
        }

        public Integer getTotal_point() {
            return total_point;
        }

        public void setTotal_point(Integer total_point) {
            this.total_point = total_point;
        }
    }
}
