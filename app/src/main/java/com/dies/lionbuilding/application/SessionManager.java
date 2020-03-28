package com.dies.lionbuilding.application;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;

import com.dies.lionbuilding.activity.LoginActivity;
import com.dies.lionbuilding.model.GiftModel;
import com.dies.lionbuilding.model.NewUserModel;
import com.dies.lionbuilding.model.OrderData;
import com.dies.lionbuilding.model.ProductModel;
import com.dies.lionbuilding.model.RouteModel;
import com.dies.lionbuilding.model.ShopModel;
import com.dies.lionbuilding.model.UserDataResponse;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;

public class SessionManager {

    private SharedPreferences pref,pref2;
    private Editor editor,editor2;
    private static final String PREF_NAME = "skzlogin";
    private static final String IS_LOGIN = "IsLoggedIn";
    private static final String IS_NEW = "IsNew";
    private static String KEY_EMAIL = "id";
    private static String KEY_ID="user_id";
    private static String CART_COUNT_ID="cart_count_id";
    private static String KEY_TOKEN = "token";
    private static String KEY_ROLL = "roll";
    private static String STATUS = "status";
    private static String KEY_PASSWORD = "password";
    private static String TOTAL_POINT = "total_point";
    private static String KEY_USER_DETAILS = "userdetails";
    private static String KEY_IMAGE = "image_list";
    private static String LOGIN_TYPE = "login_type";
    private static String FCM_ID = "fcm_id";
    private static final String PREF_DEVICE = "device";

    private static Boolean KEY_STATUS ;

    public ArrayList<ProductModel.Data> arrayListProduct = new ArrayList<>();
    public ArrayList<RouteModel.Data> arrayListRoute = new ArrayList<>();
    public ArrayList<GiftModel.Data> arrayListGift = new ArrayList<>();
    public ArrayList<NewUserModel.Data> arrayListUser = new ArrayList<>();
    public ArrayList<OrderData> arrayListorder= new ArrayList<>();
    public ArrayList<ProductModel.Data> arrayListOrderData= new ArrayList<>();


    public String screen_name = "";
    public String group_page_type = "";
    public String post_operation = "";
    public String sel_group_id = "";
    public String sel_pet_page = "";
    public String Post_type = "";
    public String dealer_id  = "";
    public String screen = "";
    public String cartscreen = "";
    public String product_id = "";
    public String address = "";
    public String product_qty = "";
    public String totalamount = "";
    public String taxtotal = "";
    public String subtotal = "";
    public String discount = "";

    public boolean status_value ;

    public String notification_count = "";
    private Context _context;


    public SessionManager(Context context) {
        int PRIVATE_MODE = 0;
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
        pref2 = context.getSharedPreferences(PREF_DEVICE, PRIVATE_MODE);
        editor2 = pref2.edit();
    }


    public String getFcmId() {
        return pref2.getString(FCM_ID, null);
    }

    public  void setFcmId(String fcmId) {
        editor2 = pref2.edit();
        editor2.putString(FCM_ID, fcmId);
        editor2.apply();
    }


    public boolean getStatus() {
        return pref.getBoolean(STATUS, true);
    }

    public void setStatus(boolean status) {
        editor.putBoolean(STATUS, status);
        editor.commit();
    }

    public String getKeyRoll() {
        return pref.getString(KEY_ROLL, null);
    }

    public void setKeyRoll(String roll) {
        editor.putString(KEY_ROLL, roll);
        editor.commit();
    }



    public String getKeyToken() {
        return pref.getString(KEY_TOKEN, null);
    }

    public void setKeyToken(String token) {
        editor.putString(KEY_TOKEN, token);
        editor.commit();
    }

    public String getKeyPassword() {
        return pref.getString(KEY_PASSWORD, null);
    }

    public void setKeyPassword(String keyPassword) {
        editor.putString(KEY_PASSWORD, keyPassword);
        editor.commit();
    }

    public void setTotalPoint(String point) {
        editor.putString(TOTAL_POINT, point);
        editor.commit();
    }

    public String getTotalPoint() {
        return pref.getString(TOTAL_POINT, "");
    }

    public String getKeyMobile() {
        return pref.getString(KEY_EMAIL, "");
    }

    public void setKeyMobile(String mobile) {
        editor.putString(KEY_EMAIL, mobile);
        editor.commit();
    }

    public String getKeyEmail() {
        return pref.getString(KEY_EMAIL, "");
    }

    public void setKeyEmail(String email) {
        editor.putString(KEY_EMAIL, email);
        editor.commit();
    }

    public String getKeyId() {
        return pref.getString(KEY_ID, null);
    }



    public void setKeyId(String keyId) {
        editor.putString(KEY_ID, keyId);
        editor.apply();
    }


    public void setCartCount(String keyId) {
        editor.putString(CART_COUNT_ID, keyId);
        editor.apply();
    }
    public String getCartCount() {
        return pref.getString(CART_COUNT_ID, null);
    }

    public String getLoginType() {
        return pref.getString(LOGIN_TYPE, "");
    }

    public void setLoginType(String loginType) {
        editor.putString(LOGIN_TYPE, loginType);
        editor.commit();
    }

    public void createLoginSession() {
        editor.putBoolean(IS_LOGIN, true);
        editor.commit();
    }
    public void checkInstall() {
        editor.putBoolean(IS_NEW, true);
        editor.commit();
    }
    public boolean isInstalled() {
        return pref.getBoolean(IS_NEW, false);
    }

    public void setKeyUserDetails(String userData) {
        editor.putString(KEY_USER_DETAILS, userData);
        editor.commit();
    }

    public List<UserDataResponse.Data> getUserData() {
        Type type = new TypeToken<List<UserDataResponse.Data>>() {
        }.getType();
        return new Gson().fromJson(pref.getString(KEY_USER_DETAILS, null), type);
    }

    public void setKeyImage(String keyImage) {
        editor.putString(KEY_IMAGE, keyImage);
        editor.commit();
    }
    public String getKeyImage() {
        return pref.getString(KEY_IMAGE, "");
    }

//    public List<ImagePathResponse.Data> getImageData() {
//        Type type = new TypeToken<List<ImagePathResponse.Data>>() {
//        }.getType();
//        return new Gson().fromJson(pref.getString(KEY_IMAGE, null), type);
//    }

    public void logoutUser() {
        editor.clear();
        editor.commit();

        Intent i = new Intent(_context, LoginActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
        _context.startActivity(i);
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }
}