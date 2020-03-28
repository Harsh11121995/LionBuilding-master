package com.dies.lionbuilding.util;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.dies.lionbuilding.apiservice.ApiService;
import com.dies.lionbuilding.apiservice.ApiServiceCreator;
import com.dies.lionbuilding.application.SessionManager;
import com.dies.lionbuilding.application.Utility;
import com.dies.lionbuilding.model.UserDataResponse;

import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;

import rx.Observable;
import rx.Observer;
import rx.schedulers.Schedulers;

public class AlarmReceiver extends BroadcastReceiver {

    GPSTracker gps;
    ProgressDialog pDialog;
    int statusCode;
    ApiService apiservice;
    SessionManager sessionManager;
    double latitude, longitude;
    String lat, lon;
    Runnable runnable;
    String imagename;
    //Intent i;
    String TAG = "TAG";

    public AlarmReceiver() {
        // TODO Auto-generated constructor stub
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        apiservice = ApiServiceCreator.createService("latest");
        sessionManager = new SessionManager(context);


        //registerReceiver(broadcastReceiver, new IntentFilter(GoogleService.str_receiver));

        // Toast.makeText(context, "Alarm Reciever", Toast.LENGTH_SHORT).show();
        //context.startService(new Intent(context, AndroidLocationServices.class));

        //context.startService(new Intent(context, GPSTracker.class));
        imagename = intent.getStringExtra("image");
        Log.e(TAG, "onReceive: "+imagename );

        gps = new GPSTracker(context);
        if (gps.canGetLocation()) {

            latitude = gps.getLatitude();
            longitude = gps.getLongitude();
            lat = String.valueOf(latitude);
            lon = String.valueOf(longitude);


            // \n is for new line
            Toast.makeText(context, "Your Location is - \nLat: "
                    + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();

            Log.e("latlong", "Your Location is - \nLat: "
                    + latitude + "\nLong: " + longitude);

//            Bundle extras = arg1.getExtras();
//            Intent i = new Intent("broadCastName");
//            // Data you need to pass to activity
//            i.putExtra("message", lat);
//            i.putExtra("message1", lon);
//
//            context.sendBroadcast(i);

            Map<String, String> postData = new HashMap<>();
            postData.put("userId", "36");
            postData.put("trck_latitude", lat);
            postData.put("trck_longitude", lon);

          /*  HttpPostAsyncTask httpPostAsyncTask=new HttpPostAsyncTask();
            httpPostAsyncTask.execute(ApiConstants.BASE_URL+"UserData/getcurrentlocation",postData.toString());*/


          //  String action = intent.getAction();




//            Handler handler = new Handler();
//             runnable = new Runnable() {
//                public void run() {
//
//                    handler.postDelayed(runnable, 50000);
//
//                }
//            };

        } else {
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }

      //  AddBankDetail(context);
       // Toast.makeText(context, "call api alaram receiver", Toast.LENGTH_SHORT).show();

    }

/*
    private void AddBankDetail(Context context) {
//        pDialog = new ProgressDialog(context);
//        pDialog.setTitle("Checking Data");
//        pDialog.setMessage("Please Wait...");
//        pDialog.setIndeterminate(false);
//        pDialog.setCancelable(false);
//        pDialog.show();

        Observable<UserDataResponse> responseObservable = apiservice.addLatlong(
                sessionManager.getUserData().get(0).getUserId(),
                lat,
                lon,
                imagename
        );

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
                .subscribe(new Observer<UserDataResponse>() {
                    @Override
                    public void onCompleted() {
//                        pDialog.dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {

                        Log.e("error", "" + e.getMessage());
                    }

                    @Override
                    public void onNext(UserDataResponse addBankModel) {
                        statusCode = addBankModel.getStatusCode();
                        if (statusCode == 200) {
                            Utility.displayToast(context, addBankModel.getMessage());

//                            startActivity(new Intent(AddBank.this, DashBoardActivity.class));


                        } else {
//                            Utility.displayToast(AddBank.this, addBankModel.getMessage());
                        }


                    }
                });

    }
*/


}


/*
    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            Double latitude = Double.valueOf(intent.getStringExtra("latutide"));
            Double longitude = Double.valueOf(intent.getStringExtra("longitude"));

            ///List<Address> addresses = null;

            try {
//					addresses = geocoder.getFromLocation(latitude, longitude, 1);
//					String cityName = addresses.get(0).getAddressLine(0);
//					String stateName = addresses.get(0).getAddressLine(1);
//					String countryName = addresses.get(0).getAddressLine(2);
//
//					tv_area.setText(addresses.get(0).getAdminArea());
//					tv_locality.setText(stateName);
//					tv_address.setText(countryName);


            } catch (Exception e1) {
                e1.printStackTrace();
            }


//				tv_latitude.setText(latitude+"");
//				tv_longitude.setText(longitude+"");
//				tv_address.getText();


        }
    };
*/
