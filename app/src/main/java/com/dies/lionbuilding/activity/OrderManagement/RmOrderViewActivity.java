package com.dies.lionbuilding.activity.OrderManagement;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.dies.lionbuilding.R;
import com.dies.lionbuilding.adapter.Order.ViewOrderAdapter;
import com.dies.lionbuilding.apiservice.ApiConstants;
import com.dies.lionbuilding.apiservice.ApiService;
import com.dies.lionbuilding.apiservice.ApiServiceCreator;
import com.dies.lionbuilding.application.SessionManager;
import com.dies.lionbuilding.application.Utility;
import com.dies.lionbuilding.model.OrderConData;
import com.dies.lionbuilding.model.RmOrderViewModel;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.net.SocketTimeoutException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.schedulers.Schedulers;

public class RmOrderViewActivity extends AppCompatActivity {


    @BindView(R.id.back_icon)
    ImageView back_icon;

    @BindView(R.id.toolbar_Title)
    TextView toolbar_Title;

    @BindView(R.id.rv_viewodrdetails)
    RecyclerView rv_viewodrdetails;
    SessionManager sessionManager;
    ApiService apiservice;
    ProgressDialog pDialog;
    int statusCode;
    List<RmOrderViewModel.Data> arrayListdata;
    String TAG = "TAG";
    Intent intent;
    String dlrname, status;
    String orderr_id;
    ViewOrderAdapter viewOrderAdapter;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rm_order_view);

        ButterKnife.bind(this);
        sessionManager = new SessionManager(this);
        apiservice = ApiServiceCreator.createService("latest");
        orderr_id = getIntent().getStringExtra("ord_id");

        toolbar_Title.setText("View Order");
        back_icon.setOnClickListener(view -> {
            finish();
        });
        rv_viewodrdetails.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(RmOrderViewActivity.this);
        rv_viewodrdetails.setLayoutManager(layoutManager);
        loadServerData();
    }

    private void loadServerData() {
        pDialog = new ProgressDialog(RmOrderViewActivity.this);
        pDialog.setTitle("Checking Data");
        pDialog.setMessage("Please Wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

        Observable<RmOrderViewModel> responseObservable = apiservice.getRmOrderDetail(
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
                .subscribe(new Observer<RmOrderViewModel>() {
                    @Override
                    public void onCompleted() {
                        pDialog.dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error", "" + e.getMessage());
                    }

                    @Override
                    public void onNext(RmOrderViewModel rmOrderViewModel) {
                        statusCode = rmOrderViewModel.getStatusCode();
                        if (statusCode == 200) {

                            arrayListdata = rmOrderViewModel.getData();
                            Log.e(TAG, "arrayListdata: " + new Gson().toJson(arrayListdata));

                            viewOrderAdapter = new ViewOrderAdapter(RmOrderViewActivity.this, arrayListdata);
                            rv_viewodrdetails.setAdapter(viewOrderAdapter);
                            Utility.displayToast(RmOrderViewActivity.this, rmOrderViewModel.getMessage());

                        }
                    }
                });


    }
}
