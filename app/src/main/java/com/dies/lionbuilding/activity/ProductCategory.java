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
import com.dies.lionbuilding.adapter.ProductCatAdapter;
import com.dies.lionbuilding.adapter.ShopViewAdapter;
import com.dies.lionbuilding.apiservice.ApiService;
import com.dies.lionbuilding.apiservice.ApiServiceCreator;
import com.dies.lionbuilding.application.SessionManager;
import com.dies.lionbuilding.model.ProductCategoryModel;
import com.dies.lionbuilding.model.ShopViewModel;
import com.github.clans.fab.FloatingActionButton;

import java.net.SocketTimeoutException;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.schedulers.Schedulers;

public class ProductCategory extends AppCompatActivity {
    @BindView(R.id.rv_product_cat)
    RecyclerView rv_product_cat;
    @BindView(R.id.back_icon)
    ImageView back_icon;

    @BindView(R.id.toolbar_Title)
    TextView toolbar_Title;

    @BindView(R.id.cart_icon)
    ImageView cart_icon;



    ProductCatAdapter productCatAdapter;
    int statusCode;
    ProgressDialog pDialog;
    SessionManager sessionManager;
    ApiService apiservice;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_category);
        ButterKnife.bind(this);
        sessionManager = new SessionManager(this);
        apiservice = ApiServiceCreator.createService("latest");
        ProductCat();
        toolbar_Title.setText("Product Category");
        back_icon.setOnClickListener(view -> {
            finish();
        });
        cart_icon.setOnClickListener(view -> {
            Intent intent=new Intent(this,CartActivity.class);
            startActivity(intent);
        });
    }
    private void ProductCat() {
        rv_product_cat.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        rv_product_cat.setLayoutManager(layoutManager);
        pDialog = new ProgressDialog(this);
        pDialog.setTitle("Checking Data");
        pDialog.setMessage("Please Wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();


        Observable<ProductCategoryModel> responseObservable = apiservice.getProductCategory();

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
                .subscribe(new Observer<ProductCategoryModel>() {
                    @Override
                    public void onCompleted() {
                        pDialog.dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error", "" + e.getMessage());
                    }

                    @Override
                    public void onNext(ProductCategoryModel categoryModel) {
                        statusCode = categoryModel.getStatusCode();
                        if (statusCode == 200) {
                            productCatAdapter = new ProductCatAdapter(ProductCategory.this, categoryModel.getData());
                            rv_product_cat.setAdapter(productCatAdapter);
                            productCatAdapter.notifyDataSetChanged();

                        }
                    }
                });


    }
}
