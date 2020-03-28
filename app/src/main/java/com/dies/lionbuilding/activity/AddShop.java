package com.dies.lionbuilding.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dies.lionbuilding.R;
import com.dies.lionbuilding.apiservice.ApiService;
import com.dies.lionbuilding.apiservice.ApiServiceCreator;
import com.dies.lionbuilding.application.SessionManager;
import com.dies.lionbuilding.application.Utility;
import com.dies.lionbuilding.model.AddShopModel;
import com.dies.lionbuilding.model.CountryModel;
import com.dies.lionbuilding.model.LoginResponse;
import com.dies.lionbuilding.model.UserDataResponse;
import com.dies.lionbuilding.model.ZoneModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.schedulers.Schedulers;

public class AddShop extends AppCompatActivity {

    @BindView(R.id.edt_add1)
    EditText edt_add1;
    @BindView(R.id.edt_add2)
    EditText edt_add2;
    @BindView(R.id.edt_pincode)
    EditText edt_pincode;
    @BindView(R.id.edt_shop_name)
    EditText edt_shop_name;
    @BindView(R.id.spnr_zone)
    Spinner spnr_zone;
    @BindView(R.id.spnr_wrd_id)
    Spinner spnr_wrd;
    @BindView(R.id.back_icon)
    ImageView back_icon;
    @BindView(R.id.toolbar_Title)
    TextView tv_toolbar_title;


    ApiService apiservice;
    ProgressDialog pDialog;
    SessionManager sessionManager;
    int statusCode;
    private FusedLocationProviderClient mFusedLocationClient;

    private double wayLatitude = 0.0, wayLongitude = 0.0;
    private LocationRequest locationRequest;
    LocationManager locationManager;


    String zone, wrd;
    List<ZoneModel.Data> zoneList;
    String z_id, wrd_id;
    Location location;

    ArrayList<String> zoneArray = new ArrayList<>();
    ArrayList<String> wrdArray = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shop);
        ButterKnife.bind(this);
        apiservice = ApiServiceCreator.createService("latest");
        sessionManager = new SessionManager(this);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        getZone();


        spnr_zone.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                TextView textView = (TextView)adapterView.getChildAt(0);
//                textView.setTextColor(getResources().getColor(R.color.colorblack));
                if (spnr_zone.getSelectedItem() == "Select Zone") {
                    zone = "";
                    Utility.displayToast(AddShop.this,"not data selected");
                    //Do nothing.
                } else {

                    Utility.displayToast(AddShop.this,"selected");
                    zone = spnr_zone.getSelectedItem().toString();
                    ZoneModel.Data datalist = zoneList.get(i - 1);
                    z_id = datalist.getZn_id();
                    Log.e("state_id", z_id);
                    getWrdId(z_id);

                    //Toast.makeText(MainActivity.this, spinner.getSelectedItem().toString(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spnr_wrd.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (spnr_wrd.getSelectedItem() == "Select ward") {
                    wrd = "";
                    //Do nothing.
                } else {
                    wrd = spnr_wrd.getSelectedItem().toString();
                    ZoneModel.Data datalist = zoneList.get(i - 1);
                    wrd_id = datalist.getWrd_name();

                    //Toast.makeText(MainActivity.this, spinner.getSelectedItem().toString(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });






        tv_toolbar_title.setText("Add Shop");
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10 * 1000); // 10 seconds
        locationRequest.setFastestInterval(5 * 1000); // 5 seconds

        locationManager = (LocationManager) getSystemService(Service.LOCATION_SERVICE);
        if (locationManager != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (location != null) {
                wayLatitude = location.getLatitude();
                wayLongitude = location.getLongitude();

                Utility.displayToast(this,String.valueOf(wayLatitude)+"\n"+String.valueOf(wayLongitude));

            }
        }



        back_icon.setOnClickListener(view -> {
            finish();
        });


    }
@OnClick(R.id.btn_add_shop)
public void btn_add_shop(){
    if (edt_add1.getText().toString().equals("")) {
        Utility.displayToast(this, "Please enter AddressLine 1");
    } else if (edt_shop_name.getText().toString().equals("")) {
        Utility.displayToast(this, "Please  enter Shop Name");
    } else if (edt_pincode.getText().toString().equals("")) {
        Utility.displayToast(this, "Please enter PinCode Number");
    } else {
        AddShopData();
    }

}


    private void AddShopData() {
        pDialog = new ProgressDialog(this);
        pDialog.setTitle("Checking Data");
        pDialog.setMessage("Please Wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();


        Observable<AddShopModel> responseObservable = apiservice.AddShopData(sessionManager.getUserData().get(0).getUserId(),
                edt_shop_name.getText().toString(),
                edt_add1.getText().toString(),
                edt_add2.getText().toString(),
                edt_pincode.getText().toString(),
                String.valueOf(wayLatitude), String.valueOf(wayLongitude),
                z_id, wrd_id);

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
                .subscribe(new Observer<AddShopModel>() {
                    @Override
                    public void onCompleted() {
                        pDialog.dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {

                        Log.e("error", "" + e.getMessage());
                    }

                    @Override
                    public void onNext(AddShopModel addShopModel) {
                        statusCode = addShopModel.getStatusCode();
                        if (statusCode == 200) {
                            Utility.displayToast(AddShop.this, addShopModel.getMessage());
                            finish();
                            startActivity(new Intent(AddShop.this, ShopActivity.class));


                        } else {
                            Utility.displayToast(AddShop.this, addShopModel.getMessage());
                        }

                    }
                });

    }

    public void getZone() {
        pDialog = new ProgressDialog(this);
        pDialog.setTitle("Checking Data");
        pDialog.setMessage("Please Wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();


        Observable<ZoneModel> responseObservable = apiservice.getZone();
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
                .subscribe(new Observer<ZoneModel>() {
                    @Override
                    public void onCompleted() {
                        pDialog.dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error", "" + e.getMessage());
                    }

                    @Override
                    public void onNext(ZoneModel zoneModel) {
                        statusCode = zoneModel.getStatusCode();
                        if (statusCode == 200) {
                            zoneList = zoneModel.getData();

                            for (int i = 0; i < zoneModel.getData().size(); i++) {
                                zoneArray.add(zoneModel.getData().get(i).getZn_name());
                            }

                        } else {
                            Utility.displayToast(AddShop.this, zoneModel.getMessage());
                        }
                    }

                });
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, zoneArray);
        dataAdapter.add("Select Zone");
        dataAdapter.setDropDownViewResource(R.layout.spinner_item);
        spnr_zone.setAdapter(dataAdapter);

    }

    public void getWrdId(String z_id) {

        pDialog = new ProgressDialog(this);
        pDialog.setTitle("Checking Data");
        pDialog.setMessage("Please Wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();


        Observable<ZoneModel> responseObservable = apiservice.getZoneWard(z_id);
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
                .subscribe(new Observer<ZoneModel>() {
                    @Override
                    public void onCompleted() {
                        pDialog.dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error", "" + e.getMessage());
                    }

                    @Override
                    public void onNext(ZoneModel zoneModel) {
                        statusCode = zoneModel.getStatusCode();
                        if (statusCode == 200) {
                            zoneList = zoneModel.getData();
                            Log.d("getWrdId", "onNext: " + zoneList.toString());

                            for (int i = 0; i < zoneModel.getData().size(); i++) {
                                wrdArray.add(zoneModel.getData().get(i).getCity_name());
                            }

                        } else {
                            Utility.displayToast(AddShop.this, zoneModel.getMessage());
                        }
                    }

                });
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, wrdArray);
        dataAdapter.add("Select ward");
        dataAdapter.setDropDownViewResource(R.layout.spinner_item);
        spnr_wrd.setAdapter(dataAdapter);

    }
}
