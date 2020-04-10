package com.dies.lionbuilding.apiservice;


import com.dies.lionbuilding.model.AddBankModel;
import com.dies.lionbuilding.model.AddShopModel;
import com.dies.lionbuilding.model.CountryModel;
import com.dies.lionbuilding.model.ExpanseModel;
import com.dies.lionbuilding.model.GiftModel;
import com.dies.lionbuilding.model.LeaveModel;
import com.dies.lionbuilding.model.LoginResponse;
import com.dies.lionbuilding.model.NewUserModel;
import com.dies.lionbuilding.model.OrderConData;
import com.dies.lionbuilding.model.PastRouteModel;
import com.dies.lionbuilding.model.ProductCategoryModel;
import com.dies.lionbuilding.model.ProductModel;
import com.dies.lionbuilding.model.RmOrderViewModel;
import com.dies.lionbuilding.model.RouteModel;
import com.dies.lionbuilding.model.ShopViewModel;
import com.dies.lionbuilding.model.UserAllData;
import com.dies.lionbuilding.model.UserDataResponse;
import com.dies.lionbuilding.model.WardModel;
import com.dies.lionbuilding.model.ZoneModel;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import rx.Observable;

/**
 * Created by Kunal
 * on 5/5/2017.
 */

public interface ApiService {

    @FormUrlEncoded
    @POST("UserData/getUserData")
    public Observable<UserDataResponse> getUserDetails(@Field("mobile") String email,
                                                       @Header("Authorization") String header);

    @FormUrlEncoded
    @POST("UserData/registeruser")
    public Observable<LoginResponse> registerUser(@Field("fname") String first_name,
                                                  @Field("mobile") String mobile,
                                                  @Field("gst_no") String gst,
                                                  @Field("password") String password,
                                                  @Field("address_line1") String address_line1,
                                                  @Field("state") String state,
                                                  @Field("city") String city,
                                                  @Field("pincode") String pincode,
                                                  @Field("user_type") String user_type,
                                                  @Field("distributor_id") String d_id,
                                                  @Field("zone") String zone);


    @FormUrlEncoded
    @POST("UserData/addnewprofile")
    public Observable<LoginResponse> addnewprofile(@Field("puserId") String puserId,
                                                   @Field("fname") String first_name,
                                                   @Field("mobile") String mobile,
                                                   @Field("gst_no") String gst,
                                                   @Field("password") String password,
                                                   @Field("address_line1") String address_line1,
                                                   @Field("state") String state,
                                                   @Field("city") String city,
                                                   @Field("pincode") String pincode,
                                                   @Field("user_type") String user_type);

    @FormUrlEncoded
    @POST("UserData/login")
    public Observable<LoginResponse> loginUser(@Field("mobile") String email,
                                               @Field("password") String password,
                                               @Field("fcm_key") String fcm_key,
                                               @Field("app_type") String app_type);


    @POST("UserData/getCounty")
    public Observable<CountryModel> getCountry();

    @FormUrlEncoded
    @POST("UserData/getSate")
    public Observable<CountryModel> getState(@Field("country_id") String country_id);

    @FormUrlEncoded
    @POST("UserData/getCity")
    public Observable<CountryModel> getCity(@Field("state_id") String country_id);


    @POST("UserData/getUserType")
    public Observable<CountryModel> getUsertype();

    @FormUrlEncoded
    @POST("UserData/getuserwisetype")
    public Observable<CountryModel> getUservisetype(@Field("userId") String userId);

    @FormUrlEncoded
    @POST("UserData/addShopLocation")
    public Observable<AddShopModel> AddShopData(@Field("userId") String userId,
                                                @Field("shop_name") String shop_name,
                                                @Field("sl_address_line1") String sl_address_line1,
                                                @Field("sl_address_line2") String sl_address_line2,
                                                @Field("sl_pincode") String sl_pincode,
                                                @Field("sl_latitude") String sl_latitude,
                                                @Field("sl_longitude") String sl_longitude,
                                                @Field("sl_zone") String sl_zone,
                                                @Field("sl_wrd_id") String sl_wrd_id);

    @POST("UserData/getZone")
    public Observable<ZoneModel> getZone();

    @FormUrlEncoded
    @POST("UserData/getZoneWard")
    public Observable<ZoneModel> getZoneWard(@Field("zn_id") String zn_id);

    @FormUrlEncoded
    @POST("UserData/getZoneWard")
    public Observable<WardModel> getZoneWardd(@Field("zn_id") String zn_id);

    @FormUrlEncoded
    @POST("UserData/getShoplocation")
    public Observable<ShopViewModel> getShop(@Field("userId") String userId);


    @POST("Product/getProductCatagory")
    public Observable<ProductCategoryModel> getProductCategory();

    @FormUrlEncoded
    @POST("Product/getProductData")
    public Observable<ProductModel> getProduct(@Field("prcat_id") String prdc_id);


    @POST("UserData/get_route")
    public Observable<RouteModel> getRouteData();

    @FormUrlEncoded
    @POST("UserData/get_route_zone")
    public Observable<RouteModel> getRouteZoneData(@Field("rt_id") String rt_id);

    @FormUrlEncoded
    @POST("UserData/get_route_zone_wisecity")
    public Observable<RouteModel> getRouteZoneCityData(@Field("zn_id") String zn_id);

    @FormUrlEncoded
    @POST("UserData/get_route_citydealer")
    public Observable<RouteModel> getRouteZoneCityDealerData(@Field("city_id") String city_id,
                                                             @Field("zn_id") String zn_id);

    @FormUrlEncoded
    @POST("UserData/add_subroute_data")
    public Observable<RouteModel> AddData(@Field("sroute_id") String city_id,
                                          @Field("suserId") String zn_id,
                                          @Field("scity") String scity,
                                          @Field("sdlid") String sdlid,
                                          @Field("szone") String szone,
                                          @Field("sday") String sday,
                                          @Field("smonth") String smonth,
                                          @Field("syear") String syear);

    @FormUrlEncoded
    @POST("UserData/rm_add_subroute_data")
    public Observable<RouteModel> AddRmData(@Field("sroute_id") String city_id,
                                            @Field("suserId") String zn_id,
                                            @Field("scity") String scity,
                                            @Field("sdlid") String sdlid,
                                            @Field("szone") String szone,
                                            @Field("sday") String sday,
                                            @Field("smonth") String smonth,
                                            @Field("syear") String syear);

    //addtrackrecord

    @FormUrlEncoded
    @POST("UserData/getprofiledata")
    public Observable<NewUserModel> getUserDetail(@Field("userId") String userId);

    @FormUrlEncoded
    @POST("UserData/getbankdetail")
    public Observable<AddBankModel> getBankDeatil(@Field("userId") String userId);

    @FormUrlEncoded
    @POST("Product/getBarcodeData")
    public Observable<ProductModel> getBarcodedata(@Field("barcode_id") String userId);

    @FormUrlEncoded
    @POST("UserData/adduserpoints")
    public Observable<ProductModel> AddPoints(@Field("pt_user_id") String userId,
                                              @Field("pt_point_value") String product_Points,
                                              @Field("pt_product_id") String product_id,
                                              @Field("pt_qty") String pt_qty);

    @FormUrlEncoded
    @POST("Product/getBarcodeData")
    public Observable<ProductModel> AddOrder(@Field("product_id") int p_id,
                                             @Field("product_id") String uname,
                                             @Field("product_id") String qty);

    /////////////////////// ordr apis ///////////////////////////////////////////////

    @FormUrlEncoded
    @POST("UserData/create_order")
    public Observable<UserDataResponse> CreateOrder(@Field("ord_slm_id") String ord_slm_id,
                                                    @Field("ord_dl_id") String dealer_id,
                                                    @Field("sord_prd_id") String sord_prd_id,
                                                    @Field("sord_qty") String sord_qty,
                                                    @Field("sord_price") String sord_price,
                                                    @Field("ord_total") String ord_total,
                                                    @Field("ord_point") String ord_point,
                                                    @Field("sord_point") String sord_point,
                                                    @Field("sord_total") String sord_total,
                                                    @Field("ord_type") String ord_type,
                                                    @Field("ord_dstr_id") String ord_dstr_id,
                                                    @Field("ord_dl_id") String ord_dl_id);

    @FormUrlEncoded
    @POST("UserData/getorder")
    public Observable<OrderConData> getAllDisOrder(@Field("userId") String user_id);

    @FormUrlEncoded
    @POST("UserData/getrmorder")
    public Observable<OrderConData> getAllRmOrder(@Field("userId") String user_id);

    @FormUrlEncoded
    @POST("UserData/getrm_salesexeorder")
    public Observable<OrderConData> getAllRm_salesExeOrder(@Field("userId") String user_id);

    @FormUrlEncoded
    @POST("UserData/getrm_salesexe_currentlocation")
    public Observable<UserAllData> getRmSalesExe_currentLocation(@Field("ord_slm_id") String user_id);

    @FormUrlEncoded
    @POST("UserData/orderapproved")
    public Observable<OrderConData> getorderapproved(@Field("ord_id") String user_id);

    @FormUrlEncoded
    @POST("UserData/getorderdetail")
    public Observable<RmOrderViewModel> getRmOrderDetail(@Field("ord_id") String user_id);

    @FormUrlEncoded
    @POST("UserData/distributor_orderconfirm")
    public Observable<OrderConData> getdistrbtr_odrconfirm(@Field("ord_id") String ord_id,
                                                           @Field("userId") String user_id);

    @FormUrlEncoded
    @POST("UserData/rm_distributor_orderconfirm")
    public Observable<OrderConData> get_rm_distrbtr_odrconfirm(@Field("ord_id") String ord_id,
                                                               @Field("userId") String user_id);

    @FormUrlEncoded
    @POST("UserData/delivered_order")
    public Observable<OrderConData> getDeliveredOrder(@Field("userId") String user_id);


    /////////////////// route apis ////////////////////////////////////////////////
    @FormUrlEncoded
    @POST("UserData/get_user_subroute")
    public Observable<RouteModel> getAllRoute(@Field("userId") String user_id);

    @FormUrlEncoded
    @POST("UserData/rm_get_user_subroute")
    public Observable<RouteModel> getRmAllRoute(@Field("userId") String user_id);

    @FormUrlEncoded
    @POST("UserData/get_user_nextday_route")
    public Observable<RouteModel> getAllFutureRoute(@Field("userId") String user_id,
                                                    @Field("route_date") String route_date);

    @FormUrlEncoded
    @POST("UserData/rm_get_user_nextday_route")
    public Observable<RouteModel> getAllRMFutureRoute(@Field("userId") String user_id,
                                                      @Field("route_date") String route_date);

    @FormUrlEncoded
    @POST("UserData/get_user_visited_route")
    public Observable<PastRouteModel> getAllPastRoute(@Field("userId") String user_id);

    @FormUrlEncoded
    @POST("UserData/rm_get_user_visited_route")
    public Observable<PastRouteModel> getAllRMPastRoute(@Field("userId") String user_id);

    @FormUrlEncoded
    @POST("UserData/adduserleave")
    public Observable<RouteModel> AddLeave(@Field("lv_user_id") String user_id,
                                           @Field("lv_start_date") String startdate,
                                           @Field("lv_end_date") String enddate,
                                           @Field("lv_desc") String detail);

    @FormUrlEncoded
    @POST("UserData/get_all_leave")
    public Observable<LeaveModel> getAllLeave(@Field("lv_user_id") String user_id);

    @FormUrlEncoded
    @POST("UserData/rm_get_all_leave")
    public Observable<LeaveModel> rmGetAllLeave(@Field("userId") String user_id);

    @FormUrlEncoded
    @POST("UserData/rm_leave_approved")
    public Observable<LeaveModel> rm_getLeaveApproved(@Field("lv_id") String lv_id,
                                                      @Field("lv_user_id") String lv_user_id,
                                                      @Field("status") String status);

    @FormUrlEncoded
    @POST("UserData/get_all_gifts")
    public Observable<GiftModel> getAllGift(@Field("lv_user_id") String user_id);


    @POST("UserData/get_all_expensive_type")
    public Observable<ExpanseModel> getExpanseType();


    @Multipart
    @POST("UserData/add_user_expensive")
    public Observable<ExpanseModel> addExpanse(@PartMap Map<String, RequestBody> map);


    @FormUrlEncoded
    @POST("UserData/get_all_expensive")
    public Observable<ExpanseModel> getAllExpanse(@Field("exp_user_id") String user_id);

    @FormUrlEncoded
    @POST("UserData/user_view_points")
    public Observable<GiftModel> getAllTransaction(@Field("exp_user_id") String user_id);


    @POST("UserData/getdistributerdata")
    public Observable<NewUserModel> getDistributor();

    @POST("UserData/getdealerdata")
    public Observable<NewUserModel> getDealer();

    @FormUrlEncoded
    @POST("UserData/add_visited_data")
    public Observable<UserDataResponse> AddComment(@Field("tv_slid") String tv_slid,
                                                   @Field("tv_dlid") String tv_dlid,
                                                   @Field("tv_comment") String tv_comment,
                                                   @Field("tv_lat") String tv_lat,
                                                   @Field("tv_long") String tv_long);

    @Multipart
    @POST("UserData/add_visited_data")
    public Observable<UserDataResponse> AddComment(@PartMap Map<String, RequestBody> map);

    @Multipart
    @POST("UserData/rm_add_visited_data")
    public Observable<UserDataResponse> AddRMComment(@PartMap Map<String, RequestBody> map);

    @Multipart
    @POST("UserData/getcurrentlocation")
    public Observable<UserDataResponse> addLatlong(@PartMap Map<String, RequestBody> map);

    @Multipart
    @POST("UserData/userendofdaylocation")
    public Observable<UserDataResponse> endofdayLatlong(@PartMap Map<String, RequestBody> map);

    /*@FormUrlEncoded
    @POST("UserData/getcurrentlocation")
    public Observable<UserDataResponse> addLatlong(@Field("userId") String userId,
                                                   @Field("trck_latitude") String trck_latitude,
                                                   @Field("trck_longitude") String trck_longitude,
                                                   @Field("track_simage") String track_simage);*/

  /*  @FormUrlEncoded
    @POST("UserData/userendofdaylocation")
    public Observable<UserDataResponse> endofdayLatlong(@Field("userId") String userId,
                                                        @Field("trck_latitude") String trck_latitude,
                                                        @Field("trck_longitude") String trck_longitude,
                                                        @Field("track_eimage") String track_eimage);*/
}
