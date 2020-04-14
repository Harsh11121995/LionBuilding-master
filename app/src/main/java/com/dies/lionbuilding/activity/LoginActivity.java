package com.dies.lionbuilding.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.dies.lionbuilding.R;
import com.dies.lionbuilding.apiservice.ApiService;
import com.dies.lionbuilding.apiservice.ApiServiceCreator;
import com.dies.lionbuilding.application.SessionManager;
import com.dies.lionbuilding.application.Utility;
import com.dies.lionbuilding.model.LoginResponse;
import com.dies.lionbuilding.model.UserDataResponse;
import com.google.gson.Gson;

import java.net.SocketTimeoutException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.edt_mno)
    EditText edt_mno;

    @BindView(R.id.edt_password)
    EditText edt_password;

    ProgressDialog pDialog;
    ApiService apiservice;
    int statusCode;
    SessionManager sessionManager;
    private String Token;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        apiservice = ApiServiceCreator.createService("latest");
        sessionManager = new SessionManager(LoginActivity.this);

        if (sessionManager.isLoggedIn()) {
            Intent intent = new Intent(this, WelcomeActivity.class);
            startActivity(intent);
            finish();
        }

    }
    @OnClick(R.id.btn_login)
    public void btn_login(){
        if (edt_mno.getText().toString().equals("")) {
            Utility.displayToast(this, "Please enter Mobileno");
        } else if (edt_password.getText().toString().equals("")) {
            Utility.displayToast(this, "Please enter Password");
        }else {
            checkLogin(edt_mno.getText().toString(),edt_password.getText().toString());
        }
    }
    @OnClick(R.id.btn_register)
    public void btn_register(){
        Intent intent=new Intent(this,RegisterActivity.class);
        startActivity(intent);
    }

    private void checkLogin(String mobileno, String pwd) {

        pDialog = new ProgressDialog(LoginActivity.this);
        pDialog.setTitle("Checking Data");
        pDialog.setMessage("Please Wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

        Observable<LoginResponse> responseObservable = apiservice.loginUser(
                mobileno,
                pwd,
                "",
                "mobile"
        );
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
                .subscribe(new Observer<LoginResponse>() {
                    @Override
                    public void onCompleted() {
                        pDialog.dismiss();
//                        if (statusCode == 401) {
//                            Utility.displayToast(getApplicationContext(), "");
//                        } else if (statusCode == 403) {
//                            Utility.displayToast(getApplicationContext(), getString(R.string.str_user_not_authorized));
//                        } else if (statusCode == 404) {
//                            Utility.displayToast(getApplicationContext(), getString(R.string.str_user_not_found));
//                        } else if (statusCode == 426) {
//                            Utility.displayToast(getApplicationContext(), getString(R.string.str_access_denied));
//                        } else if (statusCode == 1000) {
//                            Utility.displayToast(getApplicationContext(), getString(R.string.str_no_data));
//                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error", "" + e.getMessage());
                    }

                    @Override
                    public void onNext(LoginResponse loginResponse) {
                        statusCode = loginResponse.getStatusCode();
                        if (statusCode == 200) {
                            pDialog.dismiss();
                            Token = loginResponse.getData().get(0).getToken();
                            sessionManager.setKeyMobile(mobileno);
                            sessionManager.setLoginType("normal");

                            Log.e("token---->", "onNext: "+Token );
                            getUserDetail();
                        }
                    }
                });
    }


    private void getUserDetail() {

        pDialog = new ProgressDialog(LoginActivity.this);
        pDialog.setTitle("Checking Data");
        pDialog.setMessage("Please Wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

        Observable<UserDataResponse> responseObservable = apiservice.getUserDetails(sessionManager.getKeyMobile(), Token);
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
                            sessionManager.setKeyUserDetails(new Gson().toJson(userDataResponse.getData()));
                            sessionManager.setKeyToken(Token);
                            Log.e("user_type",userDataResponse.getData().get(0).getUser_type());
                            sessionManager.setKeyRoll(userDataResponse.getData().get(0).getUser_type());
                            sessionManager.setKeyId(userDataResponse.getData().get(0).getUserId());
                            sessionManager.setKeyPassword(edt_password.getText().toString());
                            sessionManager.createLoginSession();
                            sessionManager.setTotalPoint(String.valueOf(userDataResponse.getData().get(0).getTotal_point()));
                            Intent intent = new Intent(LoginActivity.this, WelcomeActivity.class);
                            startActivity(intent);
                        }
                    }
                });
    }
}
