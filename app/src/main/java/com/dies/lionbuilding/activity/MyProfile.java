package com.dies.lionbuilding.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dies.lionbuilding.R;
import com.dies.lionbuilding.apiservice.ApiService;
import com.dies.lionbuilding.apiservice.ApiServiceCreator;
import com.dies.lionbuilding.application.SessionManager;
import com.dies.lionbuilding.application.Utility;
import com.dies.lionbuilding.model.UserDataResponse;
import com.google.gson.Gson;

import java.net.SocketTimeoutException;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MyProfile extends AppCompatActivity {

    SessionManager sessionManager;
    ApiService apiservice;
    ProgressDialog pDialog;
    RecyclerView.LayoutManager layoutManager;
    int statusCode;

    @BindView(R.id.txt_name)
    TextView txt_name;
    @BindView(R.id.txt_usertype)
    TextView txt_usertype;
    @BindView(R.id.txt_email)
    TextView txt_email;
    @BindView(R.id.txt_address)
    TextView txt_address;
    @BindView(R.id.txt_gst)
    TextView txt_gst;
    @BindView(R.id.txt_mobile)
    TextView txt_mobile;
    @BindView(R.id.txt_state)
    TextView txt_state;
    @BindView(R.id.txt_city)
    TextView txt_city;

    @BindView(R.id.back_icon)
    ImageView back;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        ButterKnife.bind(this);
        apiservice = ApiServiceCreator.createService("latest");
        sessionManager = new SessionManager(MyProfile.this);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        getUserDetail();

    }


    private void getUserDetail() {

        pDialog = new ProgressDialog(MyProfile.this);
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
                            txt_name.setText(userDataResponse.getData().get(0).getFirstName());
                            txt_usertype.setText(userDataResponse.getData().get(0).getUser_type());
                            txt_email.setText(userDataResponse.getData().get(0).getEmail());
                            txt_gst.setText(userDataResponse.getData().get(0).getGst());
                            txt_state.setText(userDataResponse.getData().get(0).getState_name());
                            String Address=userDataResponse.getData().get(0).getAddress_line1()+userDataResponse.getData().get(0).getAddress_line2();
                            txt_address.setText(Address);
                            txt_mobile.setText(userDataResponse.getData().get(0).getMobile());
                            txt_city.setText(userDataResponse.getData().get(0).getCity_name());
                            txt_state.setText(userDataResponse.getData().get(0).getState_name());

                        }
                    }
                });
    }
}
