package com.dies.lionbuilding.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dies.lionbuilding.R;
import com.dies.lionbuilding.apiservice.ApiService;
import com.dies.lionbuilding.apiservice.ApiServiceCreator;
import com.dies.lionbuilding.application.SessionManager;
import com.dies.lionbuilding.application.Utility;
import com.dies.lionbuilding.model.AddBankModel;
import com.dies.lionbuilding.model.LoginResponse;
import com.dies.lionbuilding.model.NewUserModel;
import com.dies.lionbuilding.model.ProductModel;
import com.dies.lionbuilding.model.UserDataResponse;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.schedulers.Schedulers;

public class ViewUser extends AppCompatActivity {
    @BindView(R.id.tv_email)
    TextView tv_email;
    @BindView(R.id.tv_bank_name)
    TextView tv_bank_name;
    @BindView(R.id.lnr_bankDetail)
    LinearLayout lnr_bankDetail;
    @BindView(R.id.btn_view_bank_detail)
    Button btn_view_bank_detail;

    List<NewUserModel.Data> listUserData;
    List<AddBankModel.Data> listBankData;

    private ApiService apiservice;
    private SessionManager sessionManager = null;
    private ProgressDialog pDialog;
    int statusCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user);
        ButterKnife.bind(this);
        //tv_toolbar_title.setText("Add Profile");
        apiservice = ApiServiceCreator.createService("latest");
        sessionManager=new SessionManager(this);
        listUserData=new ArrayList<>();
        //listBankData=new ArrayList<>();
       // Utility.getAppcon().getSession().arrayListUserDetail
       // listBankData = Utility.getAppcon().getSession().arrayListBankDetail;


       /* back_icon.setOnClickListener(view -> {
            finish();
        });*/


        viewUserDetail();


        btn_view_bank_detail.setOnClickListener(view -> {
            lnr_bankDetail.setVisibility(View.VISIBLE);
           viewBankDetail();
        });

    }

    private void viewUserDetail() {
      /*  pDialog = new ProgressDialog(this);
        pDialog.setTitle("Checking Data");
        pDialog.setMessage("Please Wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
*/

        Observable<NewUserModel> responseObservable = apiservice.getUserDetail(sessionManager.getUserData().get(0).getUserId());
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
                .subscribe(new Observer<NewUserModel>() {
                    @Override
                    public void onCompleted() {

                        //pDialog.dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error", "" + e.getMessage());
                    }

                    @Override
                    public void onNext(NewUserModel userDataResponse) {
                        statusCode = userDataResponse.getStatusCode();
                        if (statusCode == 200) {
                            Log.d("data", "onNext: " + userDataResponse);

                            Log.d("email======>", "onNext: "+tv_email);
                            Utility.displayToast(ViewUser.this, userDataResponse.getMessage());

                        } else {
                            Utility.displayToast(ViewUser.this, userDataResponse.getMessage());
                        }
                    }
                });


    }

    private void viewBankDetail() {
        pDialog = new ProgressDialog(this);
        pDialog.setTitle("Checking Data");
        pDialog.setMessage("Please Wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();


        Observable<AddBankModel> responseObservable = apiservice.getBankDeatil(sessionManager.getUserData().get(0).getUserId());
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
                .subscribe(new Observer<AddBankModel>() {
                    @Override
                    public void onCompleted() {
                        pDialog.dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error", "" + e.getMessage());
                    }

                    @Override
                    public void onNext(AddBankModel addBankModel) {
                        statusCode = addBankModel.getStatusCode();
                        if (statusCode == 200) {
                            Log.d("data", "onNext: " + addBankModel);
                            Utility.displayToast(ViewUser.this, addBankModel.getMessage());
                            tv_bank_name.setText(listBankData.get(0).getBank_name());
                        } else {
                            Utility.displayToast(ViewUser.this, addBankModel.getMessage());
                        }
                    }
                });


    }
}
