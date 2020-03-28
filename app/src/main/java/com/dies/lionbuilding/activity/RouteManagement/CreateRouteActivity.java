package com.dies.lionbuilding.activity.RouteManagement;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.dies.lionbuilding.R;
import com.dies.lionbuilding.apiservice.ApiService;
import com.dies.lionbuilding.apiservice.ApiServiceCreator;
import com.dies.lionbuilding.application.SessionManager;
import com.dies.lionbuilding.application.Utility;
import com.dies.lionbuilding.model.CountryModel;
import com.dies.lionbuilding.model.RouteModel;
import com.thomashaertel.widget.MultiSpinner;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.schedulers.Schedulers;

public class CreateRouteActivity extends AppCompatActivity {

    @BindView(R.id.btn_save)
    Button btn_save;

//    @BindView(R.id.spnr_zone)
//    Spinner spnr_zone;

    @BindView(R.id.spnr_route)
    Spinner spnr_route;

    @BindView(R.id.spin_route_list)
    MultiSpinner spin_route_list;

    @BindView(R.id.spin_dealer_list)
    MultiSpinner spin_dealer_list;

    @BindView(R.id.spin_city_list)
    MultiSpinner spin_city_list;

    @BindView(R.id.spnr_year)
    Spinner spnr_year;

    @BindView(R.id.spnr_days)
    Spinner spnr_days;

    @BindView(R.id.spnr_month)
    Spinner spnr_month;

    @BindView(R.id.txt_zone_name)
    TextView txt_zone_name;

    @BindView(R.id.txt_city_name)
    TextView txt_city_name;

    @BindView(R.id.txt_dealer_name)
    TextView txt_dealer_name;

    @BindView(R.id.ll_zonedata)
    LinearLayout ll_zonedata;

    @BindView(R.id.back_icon)
    ImageView back;

    SessionManager sessionManager;
    ApiService apiservice;
    ProgressDialog pDialog;
    RecyclerView.LayoutManager layoutManager;
    int statusCode;
    private ArrayAdapter<String> adapter_friend;
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayList<String> arrayListyear = new ArrayList<>();
    ArrayList<String> arrayListday = new ArrayList<>();
    String selected_route = "", selected_zone = "";
    private String[] friend_id = new String[]{};
    private String[] city_id = new String[]{};
    private String[] dealer_id = new String[]{};
    private String[] sel_route_id = new String[]{};
    private String[] sel_route_name = new String[]{};
    private String[] friend_name = new String[]{};
    private String[] city_name = new String[]{};
    private String[] dealer_name = new String[]{};
    boolean[] selectedItemsFriend;

    String[] All_Route_Array, sel_Route_id;
    String c_id = "1", r_id, roll_id;
    String Contry, City, State, user_type;
    ArrayList<String> arrayListdata;
    ArrayAdapter<String> dataAdapter;

    List<RouteModel.Data> data, data1;

    String month, year, day;


    String route_id_str = "", city_id_str = "", dealer_id_str = "", friend_name_str, city_name_String, dealer_name_String;
    String zone_id_str = "", zone_name_str;

    private ArrayAdapter<String> spin_route_adapter, spin_zone_adapter;


//    private MultiSpinner.MultiSpinnerListener onSelected_route = new MultiSpinner.MultiSpinnerListener() {
//        public void onItemsSelected(boolean[] selected) {
//            StringBuilder builder = new StringBuilder();
//            route_id_str = "";
//            friend_name_str = "";
//            for (int i = 0; i < selected.length; i++) {
//                if (selected[i]) {
//                    builder.append(friend_id[(int) spin_route_adapter.getItemId(i)]).append(",");
//                    route_id_str += friend_id[(int) spin_route_adapter.getItemId(i)] + ",";
//                    friend_name_str += spin_route_adapter.getItem(i) + ",";
//                    spin_route_list.setSelected(selected);
//                }
//            }
//            if (!route_id_str.equals("")) {
//                route_id_str = route_id_str.substring(0, route_id_str.length() - 1);
//            }
//            if (!friend_name_str.equals("")) {
//                friend_name_str = friend_name_str.substring(0, friend_name_str.length() - 1);
//                spin_route_list.setText(friend_name_str);
//            } else {
//                spin_route_list.setText("Select Route");
//            }
//            spin_route_list.setTextColor(getResources().getColor(R.color.colorblack));
//            Log.e("friend_list", route_id_str);
//
//            //CityData(route_id_str);
//            //ZoneData(route_id_str);
//        }
//    };

//    private MultiSpinner.MultiSpinnerListener onSelected_city = new MultiSpinner.MultiSpinnerListener() {
//        public void onItemsSelected(boolean[] selected) {
//            StringBuilder builder = new StringBuilder();
//            zone_id_str = "";
//            zone_name_str = "";
//            for (int i = 0; i < selected.length; i++) {
//                if (selected[i]) {
//                    builder.append(city_id[(int) spin_route_adapter.getItemId(i)]).append(",");
//                    city_id_str  += city_id[(int) spin_route_adapter.getItemId(i)] + ",";
//                    city_name_String += spin_route_adapter.getItem(i) + ",";
//                    spin_city_list.setSelected(selected);
//                }
//            }
//            if (!city_id_str .equals("")) {
//                city_id_str  = city_id_str .substring(0, city_id_str .length() - 1);
//            }
//            if (!city_name_String.equals("")) {
//                city_name_String = city_name_String.substring(0, city_name_String.length() - 1);
//                spin_city_list.setText(city_name_String);
//            } else {
//                spin_city_list.setText("Select City");
//            }
//            spin_city_list.setTextColor(getResources().getColor(R.color.colorblack));
//            Log.e("friend_list", city_id_str );
//            DealerData(city_id_str,route_id_str);
//            //ZoneData(route_id_str);
//        }
//    };

//    private MultiSpinner.MultiSpinnerListener onSelected_dealer = new MultiSpinner.MultiSpinnerListener() {
//        public void onItemsSelected(boolean[] selected) {
//            StringBuilder builder = new StringBuilder();
//            zone_id_str = "";
//            zone_name_str = "";
//            for (int i = 0; i < selected.length; i++) {
//                if (selected[i]) {
//                    builder.append(dealer_id[(int) spin_route_adapter.getItemId(i)]).append(",");
//                    dealer_id_str  += dealer_id[(int) spin_route_adapter.getItemId(i)] + ",";
//                    dealer_name_String += spin_route_adapter.getItem(i) + ",";
//                    spin_dealer_list.setSelected(selected);
//                }
//            }
//            if (!dealer_id_str .equals("")) {
//                dealer_id_str  = dealer_id_str .substring(0, dealer_id_str .length() - 1);
//            }
//            if (!dealer_name_String.equals("")) {
//                dealer_name_String = dealer_name_String.substring(0, dealer_name_String.length() - 1);
//                spin_dealer_list.setText(dealer_name_String);
//            } else {
//                spin_dealer_list.setText("Select Dealer");
//            }
//            spin_dealer_list.setTextColor(getResources().getColor(R.color.colorblack));
//            Log.e("friend_list", dealer_id_str );
//
//            //ZoneData(route_id_str);
//        }
//    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_route);
        ButterKnife.bind(this);

        sessionManager = new SessionManager(this);
        apiservice = ApiServiceCreator.createService("latest");

        arrayList.add("Select Month");
        arrayList.add("January");
        arrayList.add("February");
        arrayList.add("March");
        arrayList.add("April");
        arrayList.add("May");
        arrayList.add("July");
        arrayList.add("Auguest");
        arrayList.add("Septemebr");
        arrayList.add("Octomber");
        arrayList.add("November");
        arrayList.add("December");

        arrayListyear.add("Select Year");
        arrayListyear.add("2020");
        arrayListyear.add("2021");

        arrayListday.add("Select Day");
        arrayListday.add("Monday");
        arrayListday.add("Tuesday");
        arrayListday.add("Wednesday");
        arrayListday.add("Thursday");
        arrayListday.add("Friday");
        arrayListday.add("Saturday");
        arrayListday.add("Sunday");

        RouteData();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        spnr_route.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                //((TextView) spnr_route.getSelectedView()).setTextColor(Color.WHITE);

               // ((TextView) view).setTextColor(getResources().getColor(R.color.colorblack));
                if (spnr_route.getSelectedItem() == "Select Route") {
                    ll_zonedata.setVisibility(View.GONE);
                    State = "";
                } else {
                    ll_zonedata.setVisibility(View.VISIBLE);
                    State = spnr_route.getSelectedItem().toString();
                    RouteModel.Data datalist = data1.get(i - 1);
                    r_id = datalist.getRt_id();
                    Log.e("route_id", r_id);
                    ZoneData(r_id);

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spnr_days.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (spnr_days.getSelectedItem() == "Select Day") {
                    day = "";
                } else {
                    day = spnr_days.getSelectedItem().toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spnr_month.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (spnr_month.getSelectedItem() == "Select Month") {
                    month = "";
                } else {
                    month = spnr_month.getSelectedItem().toString();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        spnr_year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (spnr_month.getSelectedItem() == "Select Year") {
                    year = "";
                } else {
                    year = spnr_year.getSelectedItem().toString();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        spnr_month.setAdapter(arrayAdapter);

        ArrayAdapter<String> arrayAdapteryera = new ArrayAdapter<String>(this, R.layout.spinner_item, arrayListyear);
        arrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        spnr_year.setAdapter(arrayAdapteryera);

        ArrayAdapter<String> arrayAdapterday = new ArrayAdapter<String>(this, R.layout.spinner_item, arrayListday);
        arrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        spnr_days.setAdapter(arrayAdapterday);


        btn_save.setOnClickListener(view -> {
            if (year.equals("")) {
                Utility.displayToast(this, "year not found");
            } else if (day.equals("")) {
                Utility.displayToast(this, "day not found");
            } else if (month.equals("")) {
                Utility.displayToast(this, "month not found");
            } else if (city_id_str.equals("")) {
                Utility.displayToast(this, "city  not found");
            } else if (zone_id_str.equals("")) {
                Utility.displayToast(this, "zone not found");
            } else if (dealer_id_str.equals("")) {
                Utility.displayToast(this, "dealer not found");
            } else {
                AddData();
            }
        });

    }


    public void RouteData() {

        pDialog = new ProgressDialog(this);
        pDialog.setTitle("Checking Data");
        pDialog.setMessage("Please Wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

        Observable<RouteModel> responseObservable = apiservice.getRouteData();

        responseObservable.subscribeOn(Schedulers.newThread())
                .observeOn(rx.android.schedulers.AndroidSchedulers.mainThread())
                .onErrorResumeNext(throwable -> {
                    if (throwable instanceof retrofit2.HttpException) {
                        retrofit2.HttpException ex = (retrofit2.HttpException) throwable;
                        statusCode = ex.code();
                        Log.e("error", ex.getLocalizedMessage());
                    } else if (throwable instanceof SocketTimeoutException) {
                        statusCode = 1000;
                    }
                    return Observable.empty();
                })
                .subscribe(new Observer<RouteModel>() {
                    @Override
                    public void onCompleted() {
                        pDialog.dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error", "" + e.getMessage());
                    }

                    @Override
                    public void onNext(RouteModel routeModel) {
                        statusCode = routeModel.getStatusCode();
                        if (statusCode == 200) {
                            ArrayList<String> arrayListdata = new ArrayList<>();
                            data1 = routeModel.getData();
                            for (int i = 0; i < routeModel.getData().size(); i++) {
                                arrayListdata.add(routeModel.getData().get(i).getRt_name());
                            }

                            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(CreateRouteActivity.this, R.layout.spinner_item, arrayListdata);
                            //dataAdapter.add("Select Route");
                            dataAdapter.insert("Select Route", 0);
                            dataAdapter.setDropDownViewResource(R.layout.spinner_item);
                            spnr_route.setAdapter(dataAdapter);
                            dataAdapter.notifyDataSetChanged();
                        } else {
                            Utility.displayToast(CreateRouteActivity.this, routeModel.getMessage());
                        }
                    }
                });

    }

    public void ZoneData(String r_id) {

        pDialog = new ProgressDialog(this);
        pDialog.setTitle("Checking Data");
        pDialog.setMessage("Please Wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

        Observable<RouteModel> responseObservable = apiservice.getRouteZoneData(r_id);

        responseObservable.subscribeOn(Schedulers.newThread())
                .observeOn(rx.android.schedulers.AndroidSchedulers.mainThread())
                .onErrorResumeNext(throwable -> {
                    if (throwable instanceof retrofit2.HttpException) {
                        retrofit2.HttpException ex = (retrofit2.HttpException) throwable;
                        statusCode = ex.code();
                        Log.e("error", ex.getLocalizedMessage());
                    } else if (throwable instanceof SocketTimeoutException) {
                        statusCode = 1000;
                    }
                    return Observable.empty();
                })
                .subscribe(new Observer<RouteModel>() {
                    @Override
                    public void onCompleted() {
                        pDialog.dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error", "" + e.getMessage());
                    }

                    @Override
                    public void onNext(RouteModel routeModel) {
                        statusCode = routeModel.getStatusCode();
                        if (statusCode == 200) {

                            List<String> zoneid = new ArrayList<>();
                            List<String> zonename = new ArrayList<>();
                            List<String> cityid = new ArrayList<>();
                            List<String> cityname = new ArrayList<>();
                            List<String> dealerid = new ArrayList<>();
                            List<String> dealername = new ArrayList<>();

                            for (int i = 0; i < routeModel.getZone().size(); i++) {
                                zoneid.add(routeModel.getZone().get(i).getZn_id());
                                zonename.add(routeModel.getZone().get(i).getZn_name());
                            }

                            for (int i = 0; i < routeModel.getCity().size(); i++) {
                                cityid.add(routeModel.getCity().get(i).getCity_id());
                                cityname.add(routeModel.getCity().get(i).getCity_name());
                            }

                            for (int i = 0; i < routeModel.getDealer().size(); i++) {
                                dealerid.add(routeModel.getDealer().get(i).getSl_id());
                                dealername.add(routeModel.getDealer().get(i).getShop_name());
                            }


                            zone_id_str = TextUtils.join(",", zoneid);
                            String zonenamee = TextUtils.join(", ", zonename);
                            txt_zone_name.setText(zonenamee);

                            city_id_str = TextUtils.join(",", cityid);
                            String citynamee = TextUtils.join(", ", cityname);
                            txt_city_name.setText(citynamee);

                            dealer_id_str = TextUtils.join(",", dealerid);
                            String dealernamee = TextUtils.join(", ", dealername);
                            txt_dealer_name.setText(dealernamee);

//                            ArrayList<String> arrayListdata = new ArrayList<>();
//                            friend_id = new String[routeModel.getData().size()];
//                            friend_name = new String[routeModel.getData().size()];
                            //Integer array_length = routeModel.getData().size();

//                            if (routeModel.getData().size() > 0) {
//                                for (int i = 0; i < routeModel.getData().size(); i++) {
//                                    friend_id[i] = routeModel.getData().get(i).getZn_id();
//                                    friend_name[i] = routeModel.getData().get(i).getZn_name();
//                                    arrayListdata.add(routeModel.getData().get(i).getZn_name());
//                                }
//                                All_Route_Array = new String[arrayListdata.size()];
//                                sel_Route_id = new String[arrayListdata.size()];
//                                All_Route_Array = arrayListdata.toArray(All_Route_Array);
//                                spin_route_adapter = new ArrayAdapter<String>(CreateRouteActivity.this, android.R.layout.simple_spinner_item_spin, All_Route_Array);
//                                spin_route_list.setHint("Select Zone");
//                                spin_route_list.setHintTextColor(getResources().getColor(R.color.colorblack));
//                                spin_route_list.setAdapter(spin_route_adapter, false, onSelected_route);
//                                selectedItemsFriend = new boolean[friend_id.length];
//                            }
                        }
                    }
                });

    }


//    public void CityData(String r_id){
//
//        pDialog = new ProgressDialog(this);
//        pDialog.setTitle("Checking Data");
//        pDialog.setMessage("Please Wait...");
//        pDialog.setIndeterminate(false);
//        pDialog.setCancelable(false);
//        pDialog.show();
//
//        Observable<RouteModel> responseObservable = apiservice.getRouteZoneCityData(r_id);
//
//        responseObservable.subscribeOn(Schedulers.newThread())
//                .observeOn(rx.android.schedulers.AndroidSchedulers.mainThread())
//                .onErrorResumeNext(throwable -> {
//                    if (throwable instanceof retrofit2.HttpException) {
//                        retrofit2.HttpException ex = (retrofit2.HttpException) throwable;
//                        statusCode = ex.code();
//                        Log.e("error", ex.getLocalizedMessage());
//                    } else if (throwable instanceof SocketTimeoutException) {
//                        statusCode = 1000;
//                    }
//                    return Observable.empty();
//                })
//                .subscribe(new Observer<RouteModel>() {
//                    @Override
//                    public void onCompleted() {
//                        pDialog.dismiss();
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.e("error", "" + e.getMessage());
//                    }
//
//                    @Override
//                    public void onNext(RouteModel routeModel) {
//                        statusCode = routeModel.getStatusCode();
//                        if (statusCode == 200) {
//                            ArrayList<String> arrayListdata = new ArrayList<>();
//                            city_id= new String[routeModel.getData().size()];
//                            city_name = new String[routeModel.getData().size()];
//                            //Integer array_length = routeModel.getData().size();
//
//                            if (routeModel.getData().size() > 0) {
//                                for (int i = 0; i < routeModel.getData().size(); i++) {
//                                    city_id[i] = routeModel.getData().get(i).getCity_id();
//                                    city_name[i] = routeModel.getData().get(i).getCity_name();
//                                    arrayListdata.add(routeModel.getData().get(i).getCity_name());
//                                }
//                                All_Route_Array = new String[arrayListdata.size()];
//                                sel_Route_id = new String[arrayListdata.size()];
//                                All_Route_Array = arrayListdata.toArray(All_Route_Array);
//                                spin_route_adapter = new ArrayAdapter<String>(CreateRouteActivity.this, android.R.layout.simple_spinner_item, All_Route_Array);
//                                spin_city_list.setHint("Select City");
//                                spin_city_list.setHintTextColor(getResources().getColor(R.color.colorblack));
//                                spin_city_list.setAdapter(spin_route_adapter, false, onSelected_city);
//                                selectedItemsFriend = new boolean[friend_id.length];
//                            }
//                        }
//                    }
//                });
//
//    }

//    public void DealerData(String citty_id,String dealler_id){
//
//        pDialog = new ProgressDialog(this);
//        pDialog.setTitle("Checking Data");
//        pDialog.setMessage("Please Wait...");
//        pDialog.setIndeterminate(false);
//        pDialog.setCancelable(false);
//        pDialog.show();
//
//        Observable<RouteModel> responseObservable = apiservice.getRouteZoneCityDealerData(citty_id,dealler_id);
//
//        responseObservable.subscribeOn(Schedulers.newThread())
//                .observeOn(rx.android.schedulers.AndroidSchedulers.mainThread())
//                .onErrorResumeNext(throwable -> {
//                    if (throwable instanceof retrofit2.HttpException) {
//                        retrofit2.HttpException ex = (retrofit2.HttpException) throwable;
//                        statusCode = ex.code();
//                        Log.e("error", ex.getLocalizedMessage());
//                    } else if (throwable instanceof SocketTimeoutException) {
//                        statusCode = 1000;
//                    }
//                    return Observable.empty();
//                })
//                .subscribe(new Observer<RouteModel>() {
//                    @Override
//                    public void onCompleted() {
//                        pDialog.dismiss();
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.e("error", "" + e.getMessage());
//                    }
//
//                    @Override
//                    public void onNext(RouteModel routeModel) {
//                        statusCode = routeModel.getStatusCode();
//                        if (statusCode == 200) {
//                            ArrayList<String> arrayListdata = new ArrayList<>();
//                            dealer_id= new String[routeModel.getData().size()];
//                            dealer_name= new String[routeModel.getData().size()];
//                            //Integer array_length = routeModel.getData().size();
//
//                            if (routeModel.getData().size() > 0) {
//                                for (int i = 0; i < routeModel.getData().size(); i++) {
//                                    dealer_id[i] = routeModel.getData().get(i).getSl_id();
//                                    dealer_name[i] = routeModel.getData().get(i).getShop_name();
//                                    arrayListdata.add(routeModel.getData().get(i).getShop_name());
//                                }
//                                All_Route_Array = new String[arrayListdata.size()];
//                                sel_Route_id = new String[arrayListdata.size()];
//                                All_Route_Array = arrayListdata.toArray(All_Route_Array);
//                                spin_route_adapter = new ArrayAdapter<String>(CreateRouteActivity.this, android.R.layout.simple_spinner_item, All_Route_Array);
//                                spin_dealer_list.setHint("Select Dealer");
//                                spin_dealer_list.setHintTextColor(getResources().getColor(R.color.colorblack));
//                                spin_dealer_list.setAdapter(spin_route_adapter, false, onSelected_dealer);
//                                selectedItemsFriend = new boolean[friend_id.length];
//                            }
//                        }
//                    }
//                });
//
//    }


    public void AddData() {

        pDialog = new ProgressDialog(this);
        pDialog.setTitle("Checking Data");
        pDialog.setMessage("Please Wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

        Observable<RouteModel> responseObservable = apiservice.AddData(
                r_id,
                sessionManager.getKeyId(),
                city_id_str,
                dealer_id_str,
                zone_id_str,
                day,
                month,
                year);

        responseObservable.subscribeOn(Schedulers.newThread())
                .observeOn(rx.android.schedulers.AndroidSchedulers.mainThread())
                .onErrorResumeNext(throwable -> {
                    if (throwable instanceof retrofit2.HttpException) {
                        retrofit2.HttpException ex = (retrofit2.HttpException) throwable;
                        statusCode = ex.code();
                        Log.e("error", ex.getLocalizedMessage());
                    } else if (throwable instanceof SocketTimeoutException) {
                        statusCode = 1000;
                    }
                    return Observable.empty();
                })
                .subscribe(new Observer<RouteModel>() {
                    @Override
                    public void onCompleted() {
                        pDialog.dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error", "" + e.getMessage());
                    }

                    @Override
                    public void onNext(RouteModel routeModel) {
                        statusCode = routeModel.getStatusCode();
                        if (statusCode == 200) {
                            Utility.displayToast(CreateRouteActivity.this, routeModel.getMessage());
                            finish();
                        } else {
                            Utility.displayToast(CreateRouteActivity.this, routeModel.getMessage());
                        }
                    }
                });

    }


}
