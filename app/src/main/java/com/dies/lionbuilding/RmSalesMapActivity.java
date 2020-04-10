package com.dies.lionbuilding;

import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.dies.lionbuilding.activity.OrderManagement.OrderDeliveredActivity;
import com.dies.lionbuilding.apiservice.ApiService;
import com.dies.lionbuilding.apiservice.ApiServiceCreator;
import com.dies.lionbuilding.application.SessionManager;
import com.dies.lionbuilding.application.Utility;
import com.dies.lionbuilding.model.OrderConData;
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
import java.util.List;

import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.schedulers.Schedulers;

public class RmSalesMapActivity extends FragmentActivity implements OnMapReadyCallback {

    String odrSlm_id;
    SessionManager sessionManager;
    ApiService apiservice;
    int statusCode;
    List<UserAllData.Data> arrayListdata;
    String TAG = "TAG";
    double lat = 0.0;
    double logi = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rm_sales_map);
        ButterKnife.bind(this);
        sessionManager = new SessionManager(this);
        apiservice = ApiServiceCreator.createService("latest");
        odrSlm_id = getIntent().getStringExtra("ord_slm_id");

        getMapData();

    }

    private void getMapData() {

        Observable<UserAllData> responseObservable = apiservice.getRmSalesExe_currentLocation(
                odrSlm_id);

        Log.e(TAG, "odrSlm_id: " + odrSlm_id);

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

                            arrayListdata = userAllData.getData();
                            Log.e(TAG, "arrayListdata: " + new Gson().toJson(arrayListdata));
                            Utility.displayToast(RmSalesMapActivity.this, userAllData.getMessage());

                            viewMap();
                        }
                    }
                });
    }

    private void viewMap() {


        lat = Double.parseDouble(arrayListdata.get(0).getLatitude());
        logi = Double.parseDouble(arrayListdata.get(0).getLongitude());

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);
    }

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
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(position));

        // Applying zoom to the marker position
       // googleMap.animateCamera(updateZoom);

    }
}
