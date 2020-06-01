package com.dies.lionbuilding.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.dies.lionbuilding.RmSalesMapActivity;
import com.dies.lionbuilding.activity.OrderManagement.SalesExecOrderActivity;
import com.dies.lionbuilding.activity.RouteManagement.CreateRouteActivity;
import com.dies.lionbuilding.apiservice.ApiService;
import com.dies.lionbuilding.apiservice.ApiServiceCreator;
import com.dies.lionbuilding.application.SessionManager;
import com.dies.lionbuilding.application.Utility;
import com.dies.lionbuilding.model.RouteModel;
import com.dies.lionbuilding.model.UserAllData;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.schedulers.Schedulers;

public class SalesExeLocationActivity extends AppCompatActivity {


    SessionManager sessionManager;
    ApiService apiservice;
    int statusCode;
    List<UserAllData.Data> data1;
    String TAG = "TAG";
    @BindView(R.id.spn_salesExe)
    Spinner spn_salesExe;
    @BindView(R.id.btn_map)
    Button btn_map;
    @BindView(R.id.back_icon)
    ImageView back_icon;
    @BindView(R.id.toolbar_Title)
    TextView toolbar_Title;
    /*@BindView(R.id.ll_map)
    LinearLayout ll_map;*/
    private String name = "";
    private String r_id = "";
    ProgressDialog pDialog;
    double lat = 0.0;
    double logi = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_exe_location);
        ButterKnife.bind(this);
        sessionManager = new SessionManager(this);
        apiservice = ApiServiceCreator.createService("latest");

        toolbar_Title.setText("SalesExecutive Location");
        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        // ll_map.setVisibility(View.GONE);
        getSalesExe();
        spn_salesExe.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                //((TextView) spnr_route.getSelectedView()).setTextColor(Color.WHITE);

                // ((TextView) view).setTextColor(getResources().getColor(R.color.colorblack));
                if (spn_salesExe.getSelectedItem() == "Select SalesExecutive") {
                    // ll_map.setVisibility(View.GONE);
                    name = "";
                } else {
                    // ll_map.setVisibility(View.VISIBLE);
                    name = spn_salesExe.getSelectedItem().toString();
                    UserAllData.Data datalist = data1.get(i - 1);
                    r_id = datalist.getUserId();
                    Log.e("user_id", r_id);

                    // showMap(r_id);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btn_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (r_id.equals("")) {
                    Utility.displayToast(getApplicationContext(), "First Select SalesExecutive");
                } else {
                    Intent intent = new Intent(SalesExeLocationActivity.this, RmSalesMapActivity.class);
                    intent.putExtra("id", r_id);
                    startActivity(intent);
                }

            }
        });
    }

/*
    private void showMap(String r_id) {

        Observable<UserAllData> responseObservable = apiservice.getRmSalesExe_currentLocation(
                r_id);

        Log.e(TAG, "odrSlm_id: " + r_id);

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
                .subscribe(new Observer<UserAllData>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error", "" + e.getMessage());
                    }

                    @Override
                    public void onNext(UserAllData userAllData) {
                        statusCode = userAllData.getStatusCode();
                        if (statusCode == 200) {

                            data1 = userAllData.getData();
                            Log.e(TAG, "arrayListdata: " + new Gson().toJson(data1));
                            Utility.displayToast(SalesExeLocationActivity.this, userAllData.getMessage());

                            viewMap();
                        }
                    }
                });
    }
*/

/*
    private void viewMap() {


        lat = Double.parseDouble(data1.get(0).getLatitude());
        logi = Double.parseDouble(data1.get(0).getLongitude());

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.frm_map);

        mapFragment.getMapAsync(SalesExeLocationActivity.this);
    }
*/

/*
    @Override
    public void onMapReady(GoogleMap googleMap) {

        LatLng position = new LatLng(lat, logi);

        // Instantiating MarkerOptions class
        MarkerOptions options = new MarkerOptions();

        // Setting position for the MarkerOptions
        options.position(position);

        // Setting title for the MarkerOptions
        options.title("Position");

        // Setting snippet for the MarkerOptions
        options.snippet("Latitude:" + lat + ",Longitude:" + logi);

        // Adding Marker on the Google Map
        googleMap.addMarker(options);

        // Creating CameraUpdate object for position
        CameraUpdate updatePosition = CameraUpdateFactory.newLatLng(position);

        // Creating CameraUpdate object for zoom
        // CameraUpdate updateZoom = CameraUpdateFactory.zoomBy(4);

        // Updating the camera position to the user input latitude and longitude
        //googleMap.moveCamera(CameraUpdateFactory.newLatLng(position));

        // Applying zoom to the marker position
        // googleMap.animateCamera(updateZoom);

        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, logi), 12.0f));


    }
*/

    private void getSalesExe() {

        pDialog = new ProgressDialog(this);
        pDialog.setTitle("Checking Data");
        pDialog.setMessage("Please Wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

        Observable<UserAllData> responseObservable = apiservice.getRM_SalesExe(sessionManager.getKeyId());

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
                .subscribe(new Observer<UserAllData>() {
                    @Override
                    public void onCompleted() {
                        pDialog.dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error", "" + e.getMessage());
                    }

                    @Override
                    public void onNext(UserAllData userAllData) {
                        statusCode = userAllData.getStatusCode();
                        if (statusCode == 200) {
                            ArrayList<String> arrayListdata = new ArrayList<>();
                            data1 = userAllData.getData();
                            for (int i = 0; i < userAllData.getData().size(); i++) {
                                arrayListdata.add(userAllData.getData().get(i).getName());
                            }

                            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(SalesExeLocationActivity.this, R.layout.spinner_item, arrayListdata);
                            //dataAdapter.add("Select Route");
                            dataAdapter.insert("Select SalesExecutive", 0);
                            dataAdapter.setDropDownViewResource(R.layout.spinner_item);
                            spn_salesExe.setAdapter(dataAdapter);
                            dataAdapter.notifyDataSetChanged();
                        } else {
                            Utility.displayToast(SalesExeLocationActivity.this, userAllData.getMessage());
                        }
                    }
                });
    }
}
