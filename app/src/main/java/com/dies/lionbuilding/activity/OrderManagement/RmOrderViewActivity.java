package com.dies.lionbuilding.activity.OrderManagement;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.dies.lionbuilding.R;
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

    @BindView(R.id.txt_disname)
    TextView txt_disname;

    @BindView(R.id.txt_pname)
    TextView txt_pname;

    @BindView(R.id.txt_pqty)
    TextView txt_pqty;

    @BindView(R.id.txt_pprice)
    TextView txt_pprice;

    @BindView(R.id.iv_product)
    ImageView iv_product;

    @BindView(R.id.back_icon)
    ImageView back_icon;

    @BindView(R.id.toolbar_Title)
    TextView toolbar_Title;

    SessionManager sessionManager;
    ApiService apiservice;
    ProgressDialog pDialog;
    int statusCode;
    List<RmOrderViewModel.Data> arrayListdata;
    String TAG = "TAG";
    Intent intent;
    String dlrname, status;
    String orderr_id;

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

                            txt_disname.setText(arrayListdata.get(0).getDistributorName());
                            txt_pname.setText(arrayListdata.get(0).getProductName());
                            txt_pprice.setText(arrayListdata.get(0).getProductPrice());
                            txt_pqty.setText(arrayListdata.get(0).getSordQty());

                            Picasso.with(getApplicationContext()).load(ApiConstants.IMAGE_URL + arrayListdata.get(0).getProductImg()).into(iv_product);

                            Utility.displayToast(RmOrderViewActivity.this, rmOrderViewModel.getMessage());

                        }
                    }
                });


    }
}
