package com.dies.lionbuilding.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NewUserModel {

    @SerializedName("statusCode")
    @Expose
    private Integer statusCode;
    @SerializedName("data")
    @Expose
    private List<NewUserModel.Data> data = null;
    @SerializedName("message")
    @Expose
    private String message;

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public List<NewUserModel.Data> getData() {
        return data;
    }

    public void setData(List<NewUserModel.Data> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public class Data {

        @SerializedName("userId")
        @Expose
        private String userId;
        @SerializedName("puserId")
        @Expose
        private String puserId;

        @SerializedName("name")
        @Expose
        private String firstName;

        @SerializedName("lname")
        @Expose
        private String lastName;

        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("password")
        @Expose
        private String password;

        @SerializedName("country")
        @Expose
        private String country;

        @SerializedName("login_status")
        @Expose
        private String login_status;

        @SerializedName("roleId")
        @Expose
        private String roleId;

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

        @SerializedName("dob")
        @Expose
        private String dob;

        @SerializedName("isDeleted")
        @Expose
        private String isDeleted;
        @SerializedName("usertype")
        @Expose
        private String usertype;
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
        @SerializedName("device_id")
        @Expose
        private String device_id;
        @SerializedName("token")
        @Expose
        private String token;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getPuserId() {
            return puserId;
        }

        public void setPuserId(String puserId) {
            this.puserId = puserId;
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

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getLogin_status() {
            return login_status;
        }

        public void setLogin_status(String login_status) {
            this.login_status = login_status;
        }

        public String getRoleId() {
            return roleId;
        }

        public void setRoleId(String roleId) {
            this.roleId = roleId;
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

        public String getDob() {
            return dob;
        }

        public void setDob(String dob) {
            this.dob = dob;
        }

        public String getIsDeleted() {
            return isDeleted;
        }

        public void setIsDeleted(String isDeleted) {
            this.isDeleted = isDeleted;
        }

        public String getUsertype() {
            return usertype;
        }

        public void setUsertype(String usertype) {
            this.usertype = usertype;
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

        public String getDevice_id() {
            return device_id;
        }

        public void setDevice_id(String device_id) {
            this.device_id = device_id;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
