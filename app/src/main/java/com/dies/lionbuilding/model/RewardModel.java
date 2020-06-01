package com.dies.lionbuilding.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RewardModel {


    @SerializedName("statusCode")
    @Expose
    private Integer statusCode;
    @SerializedName("data")
    @Expose
    private List<Data> data = null;
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

        @SerializedName("rg_id")
        @Expose
        private String rgId;
        @SerializedName("rg_user")
        @Expose
        private String rgUser;
        @SerializedName("rg_point")
        @Expose
        private String rgPoint;
        @SerializedName("rg_status")
        @Expose
        private String rgStatus;
        @SerializedName("rg_gft_id")
        @Expose
        private String rgGftId;
        @SerializedName("rg_date")
        @Expose
        private String rgDate;
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
        @SerializedName("gft_id")
        @Expose
        private String gftId;
        @SerializedName("gft_name")
        @Expose
        private String gftName;
        @SerializedName("gft_point")
        @Expose
        private String gftPoint;
        @SerializedName("gft_status")
        @Expose
        private String gftStatus;
        @SerializedName("offer_image")
        @Expose
        private String offerImage;
        @SerializedName("offer_discription")
        @Expose
        private String offerDiscription;
        @SerializedName("offer_start")
        @Expose
        private String offerStart;
        @SerializedName("offer_end")
        @Expose
        private String offerEnd;
        @SerializedName("userId")
        @Expose
        private String userId;
        @SerializedName("puserId")
        @Expose
        private String puserId;
        @SerializedName("nrm_id")
        @Expose
        private Object nrmId;
        @SerializedName("distributor_id")
        @Expose
        private Object distributorId;
        @SerializedName("dealer_id")
        @Expose
        private Object dealerId;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("password")
        @Expose
        private String password;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("lname")
        @Expose
        private String lname;
        @SerializedName("mobile")
        @Expose
        private String mobile;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("country")
        @Expose
        private String country;
        @SerializedName("city")
        @Expose
        private String city;
        @SerializedName("state")
        @Expose
        private String state;
        @SerializedName("zone")
        @Expose
        private String zone;
        @SerializedName("ward")
        @Expose
        private String ward;
        @SerializedName("address_line1")
        @Expose
        private String addressLine1;
        @SerializedName("address_line2")
        @Expose
        private String addressLine2;
        @SerializedName("pincode")
        @Expose
        private String pincode;
        @SerializedName("dob")
        @Expose
        private String dob;
        @SerializedName("login_status")
        @Expose
        private String loginStatus;
        @SerializedName("roleId")
        @Expose
        private String roleId;
        @SerializedName("fcm_key")
        @Expose
        private String fcmKey;
        @SerializedName("app_type")
        @Expose
        private String appType;
        @SerializedName("device_id")
        @Expose
        private String deviceId;
        @SerializedName("token")
        @Expose
        private String token;
        @SerializedName("gst_no")
        @Expose
        private String gstNo;
        @SerializedName("prev_location")
        @Expose
        private String prevLocation;
        @SerializedName("crnt_location")
        @Expose
        private String crntLocation;
        @SerializedName("upd_location")
        @Expose
        private String updLocation;
        @SerializedName("status_btn")
        @Expose
        private String statusBtn;
        @SerializedName("gle_address")
        @Expose
        private String gleAddress;
        @SerializedName("role")
        @Expose
        private String role;
        @SerializedName("gift_status")
        @Expose
        private String giftStatus;

        public String getRgId() {
            return rgId;
        }

        public void setRgId(String rgId) {
            this.rgId = rgId;
        }

        public String getRgUser() {
            return rgUser;
        }

        public void setRgUser(String rgUser) {
            this.rgUser = rgUser;
        }

        public String getRgPoint() {
            return rgPoint;
        }

        public void setRgPoint(String rgPoint) {
            this.rgPoint = rgPoint;
        }

        public String getRgStatus() {
            return rgStatus;
        }

        public void setRgStatus(String rgStatus) {
            this.rgStatus = rgStatus;
        }

        public String getRgGftId() {
            return rgGftId;
        }

        public void setRgGftId(String rgGftId) {
            this.rgGftId = rgGftId;
        }

        public String getRgDate() {
            return rgDate;
        }

        public void setRgDate(String rgDate) {
            this.rgDate = rgDate;
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

        public String getGftId() {
            return gftId;
        }

        public void setGftId(String gftId) {
            this.gftId = gftId;
        }

        public String getGftName() {
            return gftName;
        }

        public void setGftName(String gftName) {
            this.gftName = gftName;
        }

        public String getGftPoint() {
            return gftPoint;
        }

        public void setGftPoint(String gftPoint) {
            this.gftPoint = gftPoint;
        }

        public String getGftStatus() {
            return gftStatus;
        }

        public void setGftStatus(String gftStatus) {
            this.gftStatus = gftStatus;
        }

        public String getOfferImage() {
            return offerImage;
        }

        public void setOfferImage(String offerImage) {
            this.offerImage = offerImage;
        }

        public String getOfferDiscription() {
            return offerDiscription;
        }

        public void setOfferDiscription(String offerDiscription) {
            this.offerDiscription = offerDiscription;
        }

        public String getOfferStart() {
            return offerStart;
        }

        public void setOfferStart(String offerStart) {
            this.offerStart = offerStart;
        }

        public String getOfferEnd() {
            return offerEnd;
        }

        public void setOfferEnd(String offerEnd) {
            this.offerEnd = offerEnd;
        }

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

        public Object getNrmId() {
            return nrmId;
        }

        public void setNrmId(Object nrmId) {
            this.nrmId = nrmId;
        }

        public Object getDistributorId() {
            return distributorId;
        }

        public void setDistributorId(Object distributorId) {
            this.distributorId = distributorId;
        }

        public Object getDealerId() {
            return dealerId;
        }

        public void setDealerId(Object dealerId) {
            this.dealerId = dealerId;
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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLname() {
            return lname;
        }

        public void setLname(String lname) {
            this.lname = lname;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getZone() {
            return zone;
        }

        public void setZone(String zone) {
            this.zone = zone;
        }

        public String getWard() {
            return ward;
        }

        public void setWard(String ward) {
            this.ward = ward;
        }

        public String getAddressLine1() {
            return addressLine1;
        }

        public void setAddressLine1(String addressLine1) {
            this.addressLine1 = addressLine1;
        }

        public String getAddressLine2() {
            return addressLine2;
        }

        public void setAddressLine2(String addressLine2) {
            this.addressLine2 = addressLine2;
        }

        public String getPincode() {
            return pincode;
        }

        public void setPincode(String pincode) {
            this.pincode = pincode;
        }

        public String getDob() {
            return dob;
        }

        public void setDob(String dob) {
            this.dob = dob;
        }

        public String getLoginStatus() {
            return loginStatus;
        }

        public void setLoginStatus(String loginStatus) {
            this.loginStatus = loginStatus;
        }

        public String getRoleId() {
            return roleId;
        }

        public void setRoleId(String roleId) {
            this.roleId = roleId;
        }

        public String getFcmKey() {
            return fcmKey;
        }

        public void setFcmKey(String fcmKey) {
            this.fcmKey = fcmKey;
        }

        public String getAppType() {
            return appType;
        }

        public void setAppType(String appType) {
            this.appType = appType;
        }

        public String getDeviceId() {
            return deviceId;
        }

        public void setDeviceId(String deviceId) {
            this.deviceId = deviceId;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getGstNo() {
            return gstNo;
        }

        public void setGstNo(String gstNo) {
            this.gstNo = gstNo;
        }

        public String getPrevLocation() {
            return prevLocation;
        }

        public void setPrevLocation(String prevLocation) {
            this.prevLocation = prevLocation;
        }

        public String getCrntLocation() {
            return crntLocation;
        }

        public void setCrntLocation(String crntLocation) {
            this.crntLocation = crntLocation;
        }

        public String getUpdLocation() {
            return updLocation;
        }

        public void setUpdLocation(String updLocation) {
            this.updLocation = updLocation;
        }

        public String getStatusBtn() {
            return statusBtn;
        }

        public void setStatusBtn(String statusBtn) {
            this.statusBtn = statusBtn;
        }

        public String getGleAddress() {
            return gleAddress;
        }

        public void setGleAddress(String gleAddress) {
            this.gleAddress = gleAddress;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getGiftStatus() {
            return giftStatus;
        }

        public void setGiftStatus(String giftStatus) {
            this.giftStatus = giftStatus;
        }
    }
}
