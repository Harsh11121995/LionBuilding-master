package com.dies.lionbuilding.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.dies.lionbuilding.R;
import com.dies.lionbuilding.adapter.ExpanseAdapter;
import com.dies.lionbuilding.adapter.LeaveAdapter;
import com.dies.lionbuilding.apiservice.ApiService;
import com.dies.lionbuilding.apiservice.ApiServiceCreator;
import com.dies.lionbuilding.application.SessionManager;
import com.dies.lionbuilding.model.ExpanseModel;
import com.dies.lionbuilding.model.LeaveModel;
import com.github.clans.fab.FloatingActionButton;

import java.net.SocketTimeoutException;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.schedulers.Schedulers;

public class ExpanseManagementActivity extends AppCompatActivity {

    @BindView(R.id.fab_add)
    FloatingActionButton fab_add;

    @BindView(R.id.rcv_expanse)
    RecyclerView rcv_expanse;

    @BindView(R.id.back_icon)
    ImageView back;

    SessionManager sessionManager;
    ApiService apiservice;
    ProgressDialog pDialog;
    RecyclerView.LayoutManager layoutManager;
    int statusCode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expanse_management);
        ButterKnife.bind(this);
        sessionManager = new SessionManager(this);
        apiservice = ApiServiceCreator.createService("latest");

        fab_add.setOnClickListener(view -> {
            Intent intent=new Intent(this,AddExpanseActivity.class);
            startActivity(intent);
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        getAllExpanse();
    }


    public void getAllExpanse(){
        rcv_expanse.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        rcv_expanse.setLayoutManager(layoutManager);
        pDialog = new ProgressDialog(this);
        pDialog.setTitle("Checking Data");
        pDialog.setMessage("Please Wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

        Observable<ExpanseModel> responseObservable = apiservice.getAllExpanse(sessionManager.getKeyId());

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
                .subscribe(new Observer<ExpanseModel>() {
                    @Override
                    public void onCompleted() {
                        pDialog.dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error", "" + e.getMessage());
                    }

                    @Override
                    public void onNext(ExpanseModel leaveModel) {
                        statusCode = leaveModel.getStatusCode();
                        if (statusCode == 200) {
                            ExpanseAdapter expanseAdapter=new ExpanseAdapter(ExpanseManagementActivity.this,leaveModel.getData()) ;
                            rcv_expanse.setAdapter(expanseAdapter);
                        }
                    }
                });
    }
}
