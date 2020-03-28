package com.dies.lionbuilding.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.dies.lionbuilding.R;
import com.dies.lionbuilding.adapter.ShopViewAdapter;
import com.dies.lionbuilding.apiservice.ApiService;
import com.dies.lionbuilding.apiservice.ApiServiceCreator;
import com.dies.lionbuilding.application.SessionManager;
import com.dies.lionbuilding.application.Utility;
import com.dies.lionbuilding.model.AddShopModel;
import com.dies.lionbuilding.model.ShopViewModel;
import com.github.clans.fab.FloatingActionButton;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.schedulers.Schedulers;

public class ShopActivity extends AppCompatActivity {

    @BindView(R.id.rv_shop_list)
    RecyclerView rv_shop_list;
    @BindView(R.id.fab_add_shop)
    FloatingActionButton fab_add_shop;
    @BindView(R.id.back_icon)
    ImageView img_back_press;
    @BindView(R.id.toolbar_Title)
    TextView tv_toolbar_title;


    ShopViewAdapter shopViewAdapter;
    int statusCode;
    ProgressDialog pDialog;
    SessionManager sessionManager;
    ApiService apiservice;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        ButterKnife.bind(this);
        sessionManager = new SessionManager(this);
        apiservice = ApiServiceCreator.createService("latest");
        ShopviewList();
        tv_toolbar_title.setText("Shop");
        img_back_press.setOnClickListener(view -> {
            finish();
        });
        fab_add_shop.setOnClickListener(view -> {
            startActivity(new Intent(ShopActivity.this, AddShop.class));
        });
    }

    private void ShopviewList() {
        rv_shop_list.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        rv_shop_list.setLayoutManager(layoutManager);
        pDialog = new ProgressDialog(this);
        pDialog.setTitle("Checking Data");
        pDialog.setMessage("Please Wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();


        Observable<ShopViewModel> responseObservable = apiservice.getShop(sessionManager.getUserData().get(0).getUserId());

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
                .subscribe(new Observer<ShopViewModel>() {
                    @Override
                    public void onCompleted() {
                        pDialog.dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error", "" + e.getMessage());
                    }

                    @Override
                    public void onNext(ShopViewModel shopViewModel) {
                        statusCode = shopViewModel.getStatusCode();
                        if (statusCode == 200) {
                            shopViewAdapter = new ShopViewAdapter(ShopActivity.this, shopViewModel.getData());
                            rv_shop_list.setAdapter(shopViewAdapter);
                            shopViewAdapter.notifyDataSetChanged();

                        }
                    }
                });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
