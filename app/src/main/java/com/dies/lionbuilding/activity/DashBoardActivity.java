package com.dies.lionbuilding.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.dies.lionbuilding.R;
import com.dies.lionbuilding.activity.BankManagement.BankView;
import com.dies.lionbuilding.activity.RouteManagement.RouteViewActivity;
import com.dies.lionbuilding.apiservice.ApiService;
import com.dies.lionbuilding.apiservice.ApiServiceCreator;
import com.dies.lionbuilding.application.GpsUtils;
import com.dies.lionbuilding.application.SessionManager;
import com.dies.lionbuilding.model.UserDataResponse;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.net.SocketTimeoutException;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class DashBoardActivity extends AppCompatActivity {

    @BindView(R.id.card_add_profile)
    CardView card_add_profile;
    @BindView(R.id.card_my_profile)
    CardView card_my_profile;
    @BindView(R.id.card_contact)
    CardView card_contact;
    @BindView(R.id.card_add_bank)
    CardView card_add_bank;
    @BindView(R.id.card_shop)
    CardView card_shop;
    @BindView(R.id.card_logout)
    CardView card_logout;
    @BindView(R.id.card_product)
    CardView card_product;
    @BindView(R.id.card_scan_barcode)
    CardView card_scan_barcode;
    @BindView(R.id.card_scan_)
    CardView card_scan_;
    @BindView(R.id.card_reward)
    CardView card_reward;
    @BindView(R.id.card_route)
    CardView card_route;
    @BindView(R.id.card_Leave)
    CardView card_Leave;
    @BindView(R.id.card_expanse)
    CardView card_expanse;
    @BindView(R.id.lnr_shopandproduct)
    LinearLayout lnr_shopandproduct;
    ProgressDialog pDialog;
    ApiService apiservice;
    int statusCode;
    SessionManager sessionManager;


    private FusedLocationProviderClient mFusedLocationClient;
    private double wayLatitude = 0.0, wayLongitude = 0.0;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    private boolean isContinue = false;
    private boolean isGPS = false;
    public static final int LOCATION_REQUEST = 1000;
    public static final int GPS_REQUEST = 1001;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
//        getSupportActionBar().hide();
        ButterKnife.bind(this);
        sessionManager=new SessionManager(this);
        apiservice = ApiServiceCreator.createService("latest");
        getUserDetail();
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10 * 1000); // 10 seconds
        locationRequest.setFastestInterval(5 * 1000); // 5 seconds
        card_add_profile.setOnClickListener(view ->  {
                startActivity(new Intent(DashBoardActivity.this,Users.class));
        });
        card_shop.setOnClickListener(view -> {
            startActivity(new Intent(this,ShopActivity.class));
            new GpsUtils(DashBoardActivity.this).turnGPSOn(new GpsUtils.onGpsListener() {
                @Override
                public void gpsStatus(boolean isGPSEnable) {
                    // turn on GPS
                    isGPS = isGPSEnable;
                }
            });
            if (!isGPS) {
                Toast.makeText(DashBoardActivity.this, "Please turn on GPS", Toast.LENGTH_SHORT).show();
                return;
            }
            isContinue = false;
            getLocation();
        });
        card_my_profile.setOnClickListener(view -> {
            startActivity(new Intent(this,MyProfile.class));
        });
        card_product.setOnClickListener(view -> {
            startActivity(new Intent(this,ProductCategory.class));
        });
        card_contact.setOnClickListener(view ->  {
            startActivity(new Intent(this,ContactDetail.class));
        });
        card_scan_barcode.setOnClickListener(view -> {
            Intent i=new Intent(this,ScanBarcodeActivity.class);
            startActivity(i);
        });
        card_scan_.setOnClickListener(view -> {
            Intent intent=new Intent(this,SendLocation.class);
            startActivity(intent);
        });
        card_logout.setOnClickListener(view -> {
            sessionManager.logoutUser();
        });
        card_add_bank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DashBoardActivity.this, BankView.class));
            }
        });
        card_reward.setOnClickListener(view -> {
            startActivity(new Intent(DashBoardActivity.this, RewardActivity.class));
        });

        card_route.setOnClickListener(view -> {
            startActivity(new Intent(DashBoardActivity.this, RouteViewActivity.class));
        });

        card_Leave.setOnClickListener(view -> {
            startActivity(new Intent(DashBoardActivity.this, LeaveManagementActivity.class));
        });

        card_expanse.setOnClickListener(view -> {
            startActivity(new Intent(DashBoardActivity.this, ExpanseManagementActivity.class));
        });


        new GpsUtils(this).turnGPSOn(new GpsUtils.onGpsListener() {
            @Override
            public void gpsStatus(boolean isGPSEnable) {
                // turn on GPS
                isGPS = isGPSEnable;
            }
        });

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    if (location != null) {
                        wayLatitude = location.getLatitude();
                        wayLongitude = location.getLongitude();
                        Toast.makeText(DashBoardActivity.this, "Lat"+wayLatitude, Toast.LENGTH_SHORT).show();

                        if (!isContinue && mFusedLocationClient != null) {
                            mFusedLocationClient.removeLocationUpdates(locationCallback);
                        }
                    }
                }
            }
        };
    }
    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(DashBoardActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(DashBoardActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(DashBoardActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    LOCATION_REQUEST);
        } else {
            if (isContinue) {
                mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
            } else {
                mFusedLocationClient.getLastLocation().addOnSuccessListener(DashBoardActivity.this, location -> {
                    if (location != null) {
                        wayLatitude = location.getLatitude();
                        wayLongitude = location.getLongitude();
                    } else {
                        mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
                    }
                });
            }
        }
    }


    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1000: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    if (isContinue) {
                        mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
                    } else {
                        mFusedLocationClient.getLastLocation().addOnSuccessListener(DashBoardActivity.this, location -> {
                            if (location != null) {
                                wayLatitude = location.getLatitude();
                                wayLongitude = location.getLongitude();
                               // locationTxt.setText(String.format(Locale.US, "%s - %s", wayLatitude, wayLongitude));
                            } else {
                                mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
                            }
                        });
                    }
                } else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == GPS_REQUEST) {
                isGPS = true; // flag maintain before get location
            }
        }
    }


    private void getUserDetail() {

        pDialog = new ProgressDialog(DashBoardActivity.this);
        pDialog.setTitle("Checking Data");
        pDialog.setMessage("Please Wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

        Observable<UserDataResponse> responseObservable = apiservice.getUserDetails(sessionManager.getKeyEmail(), sessionManager.getKeyToken());
        responseObservable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
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
                .subscribe(new Observer<UserDataResponse>() {
                    @Override
                    public void onCompleted() {
                        pDialog.dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error", e.getLocalizedMessage());
                    }

                    @Override
                    public void onNext(UserDataResponse userDataResponse) {
                        statusCode = userDataResponse.getStatusCode();
                        if (statusCode == 200) {
                            sessionManager.setTotalPoint(String.valueOf(userDataResponse.getData().get(0).getTotal_point()));
                            if (userDataResponse.getData().get(0).getUser_type().equalsIgnoreCase("Dealer")){
                                lnr_shopandproduct.setVisibility(View.GONE);
                            }else if (userDataResponse.getData().get(0).getUser_type().equalsIgnoreCase("Sales Executive")){
                                lnr_shopandproduct.setVisibility(View.VISIBLE);
                            }else if (userDataResponse.getData().get(0).getUser_type().equalsIgnoreCase("Carpenter")){
                                lnr_shopandproduct.setVisibility(View.GONE);
                            }

                        }
                    }
                });
    }
}
