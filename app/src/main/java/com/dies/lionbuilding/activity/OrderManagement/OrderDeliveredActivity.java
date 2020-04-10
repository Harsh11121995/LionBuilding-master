package com.dies.lionbuilding.activity.OrderManagement;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.dies.lionbuilding.R;
import com.dies.lionbuilding.apiservice.ApiService;
import com.dies.lionbuilding.apiservice.ApiServiceCreator;
import com.dies.lionbuilding.application.SessionManager;
import com.dies.lionbuilding.application.Utility;
import com.dies.lionbuilding.model.OrderConData;
import com.google.gson.Gson;

import java.net.SocketTimeoutException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.schedulers.Schedulers;

public class OrderDeliveredActivity extends AppCompatActivity {

    @BindView(R.id.txt_dlrname)
    TextView txt_dlrname;

    @BindView(R.id.txt_status)
    TextView txt_status;

    @BindView(R.id.btn_save)
    Button btn_save;

    @BindView(R.id.back_icon)
    ImageView back_icon;

    SessionManager sessionManager;
    ApiService apiservice;
    ProgressDialog pDialog;
    int statusCode;
    List<OrderConData.Data> arrayListdata;
    String TAG = "TAG";

    String orderr_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_delivered);
        ButterKnife.bind(this);
        sessionManager = new SessionManager(this);
        apiservice = ApiServiceCreator.createService("latest");
        orderr_id = getIntent().getStringExtra("ord_id");


        loadServerData();

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LoadSaveBtnApi();
            }
        });

        back_icon.setOnClickListener(view -> {
            finish();
        });
    }

    private void LoadSaveBtnApi() {

        pDialog = new ProgressDialog(OrderDeliveredActivity.this);
        pDialog.setTitle("Checking Data");
        pDialog.setMessage("Please Wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

        Observable<OrderConData> responseObservable = apiservice.getdistrbtr_odrconfirm(
                orderr_id, sessionManager.getKeyId());

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
                            Log.e(TAG, "arrayListdata: save btn-- " + new Gson().toJson(arrayListdata));

                            Utility.displayToast(OrderDeliveredActivity.this, orderConData.getMessage());
                            /*Intent intent = new Intent(OrderDeliveredActivity.this, DistributorFragment.class);
                            startActivity(intent);*/
                            finish();

                        }
                    }
                });
    }

    public void loadServerData() {
        pDialog = new ProgressDialog(OrderDeliveredActivity.this);
        pDialog.setTitle("Checking Data");
        pDialog.setMessage("Please Wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

        Observable<OrderConData> responseObservable = apiservice.getorderapproved(
                orderr_id);

//            Log.e(TAG, "ord_id: " + arrayListdata.get(0).getOrdId());

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

                            txt_dlrname.setText(arrayListdata.get(0).getDelerName());
                            txt_status.setText(arrayListdata.get(0).getOrderStatusName());

                            Utility.displayToast(OrderDeliveredActivity.this, orderConData.getMessage());

                        }
                    }
                });
    }


}
