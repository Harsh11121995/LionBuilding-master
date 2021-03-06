package com.dies.lionbuilding.activity.OrderManagement;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;

import com.dies.lionbuilding.R;
import com.dies.lionbuilding.adapter.Order.OrderDeliveredAdapter;
import com.dies.lionbuilding.apiservice.ApiService;
import com.dies.lionbuilding.apiservice.ApiServiceCreator;
import com.dies.lionbuilding.application.SessionManager;
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

public class OrderDeliveredSummary extends AppCompatActivity {

    @BindView(R.id.deliveredOdr_List)
    RecyclerView deliveredOdr_List;

    @BindView(R.id.back_icon)
    ImageView back_icon;

    SessionManager sessionManager;
    ApiService apiservice;
    ProgressDialog pDialog;
    String TAG = "TAG";
    List<OrderConData.Data> arrayListdata;
    OrderDeliveredAdapter myAdapter;
    RecyclerView.LayoutManager layoutManager;
    int statusCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_delivered_summary);

        ButterKnife.bind(this);
        arrayListdata = new ArrayList<>();
        sessionManager = new SessionManager(this);
        apiservice = ApiServiceCreator.createService("latest");

        back_icon.setOnClickListener(view -> {
            finish();
        });

        loadServerData();
    }

    private void loadServerData() {

        deliveredOdr_List.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(OrderDeliveredSummary.this);
        deliveredOdr_List.setLayoutManager(layoutManager);
        pDialog = new ProgressDialog(OrderDeliveredSummary.this);
        pDialog.setTitle("Checking Data");
        pDialog.setMessage("Please Wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

        Observable<OrderConData> responseObservable = apiservice.getDeliveredOrder(
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
                .subscribe(new Observer<OrderConData>() {
                    @Override
                    public void onCompleted() {
                        pDialog.dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error", "" + e.getMessage());
                    }

                    @Override
                    public void onNext(OrderConData orderConData) {
                        statusCode = orderConData.getStatusCode();
                        if (statusCode == 200) {

                            arrayListdata = orderConData.getData();
                            Log.e(TAG, "arrayListdata: " + new Gson().toJson(arrayListdata));
                            myAdapter = new OrderDeliveredAdapter(OrderDeliveredSummary.this, arrayListdata);
                            deliveredOdr_List.setAdapter(myAdapter);

                        }
                    }
                });
    }
}
