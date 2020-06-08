package com.dies.lionbuilding.activity.OrderManagement;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.dies.lionbuilding.R;
import com.dies.lionbuilding.adapter.PlansPagerAdapter;
import com.dies.lionbuilding.adapter.ProductCatAdapter;
import com.dies.lionbuilding.adapter.TabingFragmentAdapter;
import com.dies.lionbuilding.apiservice.ApiService;
import com.dies.lionbuilding.apiservice.ApiServiceCreator;
import com.dies.lionbuilding.application.SessionManager;
import com.dies.lionbuilding.application.Utility;
import com.dies.lionbuilding.model.ProductCategoryModel;
import com.dies.lionbuilding.model.ProductModel;
import com.google.gson.Gson;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.schedulers.Schedulers;

public class OrderActivity extends AppCompatActivity {


    TabLayout tab;
    ViewPager viewPager;
    TabingFragmentAdapter tabingFragmentAdapter;

    ProductCatAdapter productCatAdapter;
    int statusCode;
    ProgressDialog pDialog;
    SessionManager sessionManager;
    ApiService apiservice;
    RecyclerView.LayoutManager layoutManager;
    String category[];
    Integer id[];
    Integer no_of_product = 0;

    List<ProductCategoryModel.Data> arrayList;
    ArrayList<String> arrayList_catagory;
    ArrayList<Integer> arrayList_id;

    @BindView(R.id.txt_total_qty)
    TextView txt_total_qty;

    @BindView(R.id.txt_order_total)
    TextView txt_order_total;

    HashMap<String, List<ProductModel.Data>> maps = new HashMap<String, List<ProductModel.Data>>();
    ArrayList<Integer> list = new ArrayList<>();
    HashSet<Integer> hashSet = new HashSet<Integer>();

    ArrayList<String> stringArrayList = new ArrayList<>();
    String total_price, total_qty;
    private String TAG = "TAG";

    @BindView(R.id.back_icon)
    ImageView back_icon;
    @BindView(R.id.toolbar_Title)
    TextView tv_toolbar_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        ButterKnife.bind(this);
        sessionManager = new SessionManager(this);
        apiservice = ApiServiceCreator.createService("latest");

        back_icon.setOnClickListener(view -> {
            finish();
        });
        tv_toolbar_title.setText("Create Order");

        ProductCat();

        tab = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.frameLayout);


        tab.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }


    public Integer getText() {
        return Integer.valueOf(txt_total_qty.getText().toString());
    }

    public void setText(String text) {
        txt_total_qty.setText(text);
    }

    public Double getTotal() {
        return Double.valueOf(txt_order_total.getText().toString());
    }

    public void setTotal(String text) {
        txt_order_total.setText(text);
    }

    public void setArray(List<ProductModel.Data> array, int i) {
        maps.put("Group" + String.valueOf(i), array);
        list.add(i);
    }

    private void ProductCat() {
//        rv_product_cat.setHasFixedSize(true);
//        layoutManager = new LinearLayoutManager(this);
//        rv_product_cat.setLayoutManager(layoutManager);
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
                            arrayList = new ArrayList<>();
                            arrayList = categoryModel.getData();
                            arrayList_catagory = new ArrayList<>();
                            arrayList_id = new ArrayList<>();

                            for (int i = 0; i < arrayList.size(); i++) {
                                arrayList_catagory.add(arrayList.get(i).getPrcat_name());
                                arrayList_id.add(Integer.parseInt(arrayList.get(i).getPrcat_id()));
                            }

                            category = new String[arrayList_catagory.size()];
                            category = arrayList_catagory.toArray(category);
                            id = new Integer[arrayList_id.size()];
                            id = arrayList_id.toArray(id);

                            for (int k = 0; k < category.length; k++) {
                                tab.addTab(tab.newTab().setText("" + category[k]));
                            }
                            PlansPagerAdapter adapter = new PlansPagerAdapter
                                    (getSupportFragmentManager(), tab.getTabCount(), id);
                            viewPager.setAdapter(adapter);
                            viewPager.setOffscreenPageLimit(6);
                            viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tab));

                        }
                    }
                });
    }

    @OnClick(R.id.btn_submit)
    public void btn_submit() {

        hashSet.addAll(list);
        list.clear();
        list.addAll(hashSet);
        ArrayList<ProductModel.Data> dt = new ArrayList<>();
        for (int i = 0; i < maps.size(); i++) {
            dt.addAll(maps.get("Group" + list.get(i)));
        }
        Utility.getAppcon().getSession().arrayListOrderData = dt;


        Log.e("arrayList_new_data", new Gson().toJson(Utility.getAppcon().getSession().arrayListOrderData));
        ArrayList<ProductModel.Data> arrayList = new ArrayList<ProductModel.Data>();

        if (Utility.getAppcon().getSession().arrayListOrderData.size() > 0) {
            for (int i = 0; i < Utility.getAppcon().getSession().arrayListOrderData.size(); i++) {
                if (!Utility.getAppcon().getSession().arrayListOrderData.get(i).getProduct_quentity().equals("0") && !Utility.getAppcon().getSession().arrayListOrderData.get(i).getProduct_quentity().equals("")) {
                    arrayList.add(Utility.getAppcon().getSession().arrayListOrderData.get(i));
                }
            }
        }

        Log.e("arrayList-step-1", new Gson().toJson(Utility.getAppcon().getSession().arrayListOrderData));

        total_qty = txt_total_qty.getText().toString();
        total_price = txt_order_total.getText().toString();

        if (dt.size() == 0) {   //check arraylist empty or not
            Utility.displayToast(this, "Please Select Product");
        } else {
            Utility.getAppcon().getSession().arrayListOrderData.get(0).setProduct_total(total_price);
            Utility.getAppcon().getSession().arrayListOrderData.get(0).setProduct_total_qty(total_qty);

            Intent intent = new Intent(OrderActivity.this, OrderSummaryActivty.class);
            Utility.getAppcon().getSession().arrayListOrderDatanew = arrayList;
            startActivity(intent);
        }
    }

  /* if(Utility.getAppcon().getSession().arrayListOrderData.equals(""))
        {

        }
        else
        {

        }*/

}
