package com.dies.lionbuilding.activity.BankManagement;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.dies.lionbuilding.R;
import com.dies.lionbuilding.apiservice.ApiService;
import com.dies.lionbuilding.apiservice.ApiServiceCreator;
import com.dies.lionbuilding.application.SessionManager;
import com.dies.lionbuilding.model.AddBankModel;
import com.dies.lionbuilding.model.NewUserModel;

import java.net.SocketTimeoutException;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.schedulers.Schedulers;

public class BankDetail extends AppCompatActivity {

    @BindView(R.id.txt_ac_holder_name)
    TextView txt_ac_holder_name;
    @BindView(R.id.txt_ac_no)
    TextView txt_ac_no;
    @BindView(R.id.txt_bank_name)
    TextView txt_bank_name;
    @BindView(R.id.txt_branch_name)
    TextView txt_branch_name;
    @BindView(R.id.txt_ifsc)
    TextView txt_ifsc;
    @BindView(R.id.txt_pan_no)
    TextView txt_pan_no;

    @BindView(R.id.back_icon)
    ImageView back_icon;
    @BindView(R.id.toolbar_Title)
    TextView tv_toolbar_title;
    int statusCode;
    ProgressDialog pDialog;
    SessionManager sessionManager;
    ApiService apiservice;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_detail);

        getSupportActionBar().hide();


        ButterKnife.bind(this);
        sessionManager = new SessionManager(this);
        apiservice = ApiServiceCreator.createService("latest");
        tv_toolbar_title.setText("Bank Detail");

        getBankDetail();
        back_icon.setOnClickListener(view -> {
            finish();
        });


    }
    private void getBankDetail() {

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
                    public void onNext(AddBankModel bankModel) {//
                        statusCode = bankModel.getStatusCode();
                        if (statusCode == 200) {
                            txt_bank_name.setText(bankModel.getData().get(0).getBank_name());
                            txt_ac_no.setText(bankModel.getData().get(0).getBank_acnumber());
                            txt_ac_holder_name.setText(bankModel.getData().get(0).getAc_hlname());
                            Log.d("data", "onNext: "+txt_ac_holder_name);
                            txt_branch_name.setText(bankModel.getData().get(0).getBank_branchname());
                            txt_ifsc.setText(bankModel.getData().get(0).getBank_ifsc_code());
                            txt_pan_no.setText(bankModel.getData().get(0).getBank_pan_no());




                        }
                    }
                });

    }
}
