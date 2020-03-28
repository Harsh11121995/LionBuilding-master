package com.dies.lionbuilding.activity;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.dies.lionbuilding.R;
import com.dies.lionbuilding.adapter.GiftAdapter;
import com.dies.lionbuilding.adapter.LeaveAdapter;
import com.dies.lionbuilding.apiservice.ApiService;
import com.dies.lionbuilding.apiservice.ApiServiceCreator;
import com.dies.lionbuilding.application.SessionManager;
import com.dies.lionbuilding.model.GiftModel;
import com.dies.lionbuilding.model.LeaveModel;

import java.net.SocketTimeoutException;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.schedulers.Schedulers;

public class RewardActivity extends AppCompatActivity {

    @BindView(R.id.rcv_reward)
    RecyclerView rcv_reward;

    @BindView(R.id.txt_point)
    TextView txt_point;





    SessionManager sessionManager;
    ApiService apiservice;
    ProgressDialog pDialog;
    RecyclerView.LayoutManager layoutManager;
    int statusCode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reward);
        ButterKnife.bind(this);
        sessionManager = new SessionManager(this);
        apiservice = ApiServiceCreator.createService("latest");
        txt_point.setText(sessionManager.getTotalPoint()+" points");
        ButterKnife.bind(this);
        getAllGift();

    }


    public void getAllGift(){
        rcv_reward.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        rcv_reward.setLayoutManager(layoutManager);
        pDialog = new ProgressDialog(this);
        pDialog.setTitle("Checking Data");
        pDialog.setMessage("Please Wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

        Observable<GiftModel> responseObservable = apiservice.getAllGift(sessionManager.getKeyId());

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
                .subscribe(new Observer<GiftModel>() {
                    @Override
                    public void onCompleted() {
                        pDialog.dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error", "" + e.getMessage());
                    }

                    @Override
                    public void onNext(GiftModel giftModel) {
                        statusCode = giftModel.getStatusCode();
                        if (statusCode == 200) {
                            GiftAdapter giftAdapter=new GiftAdapter(RewardActivity.this,giftModel.getData()) ;
                            rcv_reward.setAdapter(giftAdapter);
                        }
                    }
                });
    }



}
