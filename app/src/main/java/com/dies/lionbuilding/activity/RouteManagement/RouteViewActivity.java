package com.dies.lionbuilding.activity.RouteManagement;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.dies.lionbuilding.R;
import com.dies.lionbuilding.adapter.RouteAdapter;
import com.dies.lionbuilding.apiservice.ApiService;
import com.dies.lionbuilding.apiservice.ApiServiceCreator;
import com.dies.lionbuilding.application.SessionManager;
import com.dies.lionbuilding.model.RouteModel;

import java.net.SocketTimeoutException;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.schedulers.Schedulers;

public class RouteViewActivity extends AppCompatActivity {

    @BindView(R.id.rcv_route)
    RecyclerView rcv_route;
    @BindView(R.id.toolbar_Title)
    TextView toolbar_Title;
    @BindView(R.id.back_icon)
    ImageView back_icon;


    SessionManager sessionManager;
    ApiService apiservice;
    ProgressDialog pDialog;
    RecyclerView.LayoutManager layoutManager;
    int statusCode;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_view);
        ButterKnife.bind(this);
        sessionManager = new SessionManager(this);
        apiservice = ApiServiceCreator.createService("latest");
        toolbar_Title.setText("Route");
        back_icon.setOnClickListener(view -> {
            finish();
        });
        getRoute();

    }


    public void getRoute(){
        rcv_route.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        rcv_route.setLayoutManager(layoutManager);
        pDialog = new ProgressDialog(this);
        pDialog.setTitle("Checking Data");
        pDialog.setMessage("Please Wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

        Observable<RouteModel> responseObservable = apiservice.getAllRoute(sessionManager.getKeyId());

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
                .subscribe(new Observer<RouteModel>() {
                    @Override
                    public void onCompleted() {
                        pDialog.dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error", "" + e.getMessage());
                    }

                    @Override
                    public void onNext(RouteModel routeModel) {
                        statusCode = routeModel.getStatusCode();
                        if (statusCode == 200) {
                            RouteAdapter routeAdapter=new RouteAdapter(RouteViewActivity.this,routeModel.getData());
                            rcv_route.setAdapter(routeAdapter);
                        }
                    }
                });

    }
}
