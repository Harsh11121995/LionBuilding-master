package com.dies.lionbuilding.activity;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.dies.lionbuilding.R;
import com.dies.lionbuilding.activity.RouteManagement.PastRouteActivity;
import com.dies.lionbuilding.adapter.PastRouteAdapter;
import com.dies.lionbuilding.adapter.RewardHistoryAdapter;
import com.dies.lionbuilding.apiservice.ApiService;
import com.dies.lionbuilding.apiservice.ApiServiceCreator;
import com.dies.lionbuilding.application.SessionManager;
import com.dies.lionbuilding.model.PastRouteModel;
import com.dies.lionbuilding.model.RewardModel;
import com.google.gson.Gson;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.schedulers.Schedulers;

public class RewardHistoryActivity extends AppCompatActivity {

    @BindView(R.id.back_icon)
    ImageView back_icon;
    @BindView(R.id.toolbar_Title)
    TextView tv_toolbar_title;
    @BindView(R.id.rv_rewardlist)
    RecyclerView rv_rewardlist;
    SessionManager sessionManager;
    ApiService apiservice;
    ProgressDialog pDialog;
    RecyclerView.LayoutManager layoutManager;
    int statusCode;
    List<RewardModel.Data> arrayListdata;
    RewardHistoryAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reward_history);
        ButterKnife.bind(this);
        arrayListdata = new ArrayList<>();
        sessionManager = new SessionManager(this);
        apiservice = ApiServiceCreator.createService("latest");
        tv_toolbar_title.setText("Reward History");
        back_icon.setOnClickListener(view -> {
            finish();
        });
        rv_rewardlist.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(RewardHistoryActivity.this);
        rv_rewardlist.setLayoutManager(layoutManager);
        loadServerData();
    }

    private void loadServerData() {

        pDialog = new ProgressDialog(RewardHistoryActivity.this);
        pDialog.setTitle("Checking Data");
        pDialog.setMessage("Please Wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

        Observable<RewardModel> responseObservable = apiservice.getUserGiftStatus(
                sessionManager.getKeyId());

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
                .subscribe(new Observer<RewardModel>() {
                    @Override
                    public void onCompleted() {
                        pDialog.dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error", "" + e.getMessage());
                    }

                    @Override
                    public void onNext(RewardModel rewardModel) {
                        statusCode = rewardModel.getStatusCode();
                        if (statusCode == 200) {

                            arrayListdata = rewardModel.getData();
                            myAdapter = new RewardHistoryAdapter(RewardHistoryActivity.this, arrayListdata);
                            rv_rewardlist.setAdapter(myAdapter);

                        }
                    }
                });
    }
}
