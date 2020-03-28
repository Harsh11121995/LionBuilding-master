package com.dies.lionbuilding.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dies.lionbuilding.R;
import com.dies.lionbuilding.adapter.ProductAdapter;
import com.dies.lionbuilding.adapter.ProductCatAdapter;
import com.dies.lionbuilding.apiservice.ApiService;
import com.dies.lionbuilding.apiservice.ApiServiceCreator;
import com.dies.lionbuilding.application.SessionManager;
import com.dies.lionbuilding.model.ProductCategoryModel;
import com.dies.lionbuilding.model.ProductModel;

import java.net.SocketTimeoutException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.schedulers.Schedulers;

public class Product extends AppCompatActivity {
    @BindView(R.id.rv_product)
    RecyclerView rv_product;
    @BindView(R.id.toolbar_Title)
    TextView toolbar_Title;
    @BindView(R.id.back_icon)
    ImageView back_icon;
    @BindView(R.id.img_cart)
    ImageView cart;
    @BindView(R.id.notify_icon)
    ImageView bell;

    ProductAdapter productAdapter;

    int statusCode;
    ProgressDialog pDialog;
    SessionManager sessionManager;
    ApiService apiservice;
    RecyclerView.LayoutManager layoutManager;
    String p_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        ButterKnife.bind(this);
        p_id = getIntent().getStringExtra("prcat_id");
        sessionManager = new SessionManager(this);
        apiservice = ApiServiceCreator.createService("latest");
        Product();

        toolbar_Title.setText("Product");
        bell.setVisibility(View.GONE);
        cart.setVisibility(View.VISIBLE);
        back_icon.setOnClickListener(view -> {
            finish();
        });

    }

    private void Product() {
        rv_product.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(this, 2);
        rv_product.setLayoutManager(layoutManager);
        pDialog = new ProgressDialog(this);
        pDialog.setTitle("Checking Data");
        pDialog.setMessage("Please Wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();


        Observable<ProductModel> responseObservable = apiservice.getProduct(p_id);

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
                .subscribe(new Observer<ProductModel>() {
                    @Override
                    public void onCompleted() {
                        pDialog.dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error", "" + e.getMessage());
                    }

                    @Override
                    public void onNext(ProductModel productModel) {
                        statusCode = productModel.getStatusCode();
                        if (statusCode == 200) {
                            productAdapter = new ProductAdapter(Product.this, productModel.getData());
                            rv_product.setAdapter(productAdapter);
                            productAdapter.notifyDataSetChanged();

                        }
                    }
                });
    }
}
