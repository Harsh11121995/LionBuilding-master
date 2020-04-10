package com.dies.lionbuilding.activity;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;

import com.dies.lionbuilding.R;
import com.dies.lionbuilding.activity.OrderManagement.SalesExecOrderActivity;
import com.dies.lionbuilding.adapter.Order.SalesExeOrderAdapter;
import com.dies.lionbuilding.adapter.RmLeaveAdapter;
import com.dies.lionbuilding.apiservice.ApiService;
import com.dies.lionbuilding.apiservice.ApiServiceCreator;
import com.dies.lionbuilding.application.SessionManager;
import com.dies.lionbuilding.model.LeaveModel;
import com.dies.lionbuilding.model.OrderConData;
import com.google.gson.Gson;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.schedulers.Schedulers;

public class RmGetAllLeaveActivty extends AppCompatActivity {

    @BindView(R.id.rv_rm_getLeaveList)
    RecyclerView rv_rm_getLeaveList;

    @BindView(R.id.back_icon)
    ImageView back_icon;

    SessionManager sessionManager;
    ApiService apiservice;
    ProgressDialog pDialog;
    String TAG = "TAG";
    List<LeaveModel.Data> arrayListdata;
    RecyclerView.LayoutManager layoutManager;
    RmLeaveAdapter myAdapter;
    int statusCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rm_get_all_leave_activty);
        ButterKnife.bind(this);
        arrayListdata = new ArrayList<>();
        sessionManager = new SessionManager(this);
        apiservice = ApiServiceCreator.createService("latest");


        back_icon.setOnClickListener(view -> {
            finish();
        });

        rv_rm_getLeaveList.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(RmGetAllLeaveActivty.this);
        rv_rm_getLeaveList.setLayoutManager(layoutManager);

        getRmLeaveDataApi();

    }

    public void getRmLeaveDataApi() {

        pDialog = new ProgressDialog(RmGetAllLeaveActivty.this);
        pDialog.setTitle("Checking Data");
        pDialog.setMessage("Please Wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

        Observable<LeaveModel> responseObservable = apiservice.rmGetAllLeave(
                sessionManager.getKeyId());

        Log.e(TAG, "getKeyId: " + sessionManager.getKeyId());

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
                .subscribe(new Observer<LeaveModel>() {
                    @Override
                    public void onCompleted() {
                        pDialog.dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error", "" + e.getMessage());
                    }

                    @Override
                    public void onNext(LeaveModel leaveModel) {
                        statusCode = leaveModel.getStatusCode();
                        if (statusCode == 200) {
                            arrayListdata = leaveModel.getData();
                            Log.e(TAG, "arrayListdata: " + new Gson().toJson(arrayListdata));
                            myAdapter = new RmLeaveAdapter(RmGetAllLeaveActivty.this, arrayListdata);
                            rv_rm_getLeaveList.setAdapter(myAdapter);
                        }
                    }
                });
    }
}
