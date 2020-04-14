package com.dies.lionbuilding.activity.RouteManagement;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.dies.lionbuilding.R;
import com.dies.lionbuilding.adapter.PastRouteAdapter;
import com.dies.lionbuilding.apiservice.ApiService;
import com.dies.lionbuilding.apiservice.ApiServiceCreator;
import com.dies.lionbuilding.application.SessionManager;
import com.dies.lionbuilding.model.PastRouteModel;
import com.google.gson.Gson;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.schedulers.Schedulers;

public class PastRouteActivity extends AppCompatActivity {

    @BindView(R.id.rv_pastroute_list)
    RecyclerView rv_pastroute_list;

    @BindView(R.id.back_icon)
    ImageView back_icon;

    @BindView(R.id.toolbar_Title)
    TextView toolbar_Title;
    SessionManager sessionManager;
    ApiService apiservice;
    ProgressDialog pDialog;
    String TAG = "TAG";
    List<PastRouteModel.Data> arrayListdata;
    PastRouteAdapter myAdapter;
    RecyclerView.LayoutManager layoutManager;
    int statusCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_route);
        ButterKnife.bind(this);
        arrayListdata = new ArrayList<>();
        sessionManager = new SessionManager(this);
        apiservice = ApiServiceCreator.createService("latest");

        toolbar_Title.setText("Past Route");
        back_icon.setOnClickListener(view -> {
            finish();
        });

        rv_pastroute_list.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(PastRouteActivity.this);
        rv_pastroute_list.setLayoutManager(layoutManager);


        if (sessionManager.getKeyRoll().equals("RM")) {
            getRMPastRouteApi();
        } else {
            getSalesPastRouteApi();
        }

    }

    private void getSalesPastRouteApi() {

        pDialog = new ProgressDialog(PastRouteActivity.this);
        pDialog.setTitle("Checking Data");
        pDialog.setMessage("Please Wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

        Observable<PastRouteModel> responseObservable = apiservice.getAllPastRoute(
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
                .subscribe(new Observer<PastRouteModel>() {
                    @Override
                    public void onCompleted() {
                        pDialog.dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error", "" + e.getMessage());
                    }

                    @Override
                    public void onNext(PastRouteModel pastRouteModel) {
                        statusCode = pastRouteModel.getStatusCode();
                        if (statusCode == 200) {

                            arrayListdata = pastRouteModel.getData();
                            Log.e(TAG, "arrayListdata: " + new Gson().toJson(arrayListdata));
                            myAdapter = new PastRouteAdapter(PastRouteActivity.this, arrayListdata);
                            rv_pastroute_list.setAdapter(myAdapter);

                        }
                    }
                });
    }

    private void getRMPastRouteApi() {

        pDialog = new ProgressDialog(PastRouteActivity.this);
        pDialog.setTitle("Checking Data");
        pDialog.setMessage("Please Wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

        Observable<PastRouteModel> responseObservable = apiservice.getAllRMPastRoute(
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
                .subscribe(new Observer<PastRouteModel>() {
                    @Override
                    public void onCompleted() {
                        pDialog.dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error", "" + e.getMessage());
                    }

                    @Override
                    public void onNext(PastRouteModel pastRouteModel) {
                        statusCode = pastRouteModel.getStatusCode();
                        if (statusCode == 200) {

                            arrayListdata = pastRouteModel.getData();
                            Log.e(TAG, "arrayListdata: " + new Gson().toJson(arrayListdata));
                            myAdapter = new PastRouteAdapter(PastRouteActivity.this, arrayListdata);
                            rv_pastroute_list.setAdapter(myAdapter);

                        }
                    }
                });
    }

}
