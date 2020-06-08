package com.dies.lionbuilding.fragment;


import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dies.lionbuilding.R;
import com.dies.lionbuilding.activity.Product;
import com.dies.lionbuilding.adapter.ProductAdapter;
import com.dies.lionbuilding.adapter.ProductListAdapter;
import com.dies.lionbuilding.apiservice.ApiService;
import com.dies.lionbuilding.apiservice.ApiServiceCreator;
import com.dies.lionbuilding.application.SessionManager;
import com.dies.lionbuilding.model.ProductModel;


import java.net.SocketTimeoutException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observable;
import rx.Observer;
import rx.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class  DynamicFragment extends Fragment {

    View view;

    SharedPreferences sharedPreferences;
    public static final String MyPREFERENCES = "login";

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    //ShoesAdapter adapter;

    SessionManager sessionManager;
    ApiService apiservice;
    ProgressDialog pDialog;
    int statusCode;

    public static DynamicFragment newInstance(int val,int position) {
        DynamicFragment fragment = new DynamicFragment();
        Bundle args = new Bundle();
        args.putInt("pos", position);
        args.putInt("someInt", val);
        fragment.setArguments(args);
        return fragment;
    }

    int val,position;
    String dat;
    TextView c;


    String frmname[]={"shoes_and_bag","ironing","dray_clean","roll_polish","wash_and_fold","wash_and_iron"};
    public DynamicFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view=inflater.inflate(R.layout.fragment_add_invoice_step2, container, false);
        sessionManager = new SessionManager(getActivity());
        apiservice = ApiServiceCreator.createService("latest");

        val = getArguments().getInt("someInt", 0);
        position = getArguments().getInt("pos", 0);
        dat= String.valueOf(val);
        //c = view.findViewById(R.id.c);
//        c.setText("" + val);

      //  getActivity().overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        Product();
        return view;
    }




//    private void getShoesList() {
//        ManageCustomerInterface restInterface = RetrofitConfig.getRetrofit().create(ManageCustomerInterface.class);
//        Call<ArrayList<InvoiceModel>> call = restInterface.getProduct(dat);
//        call.enqueue(new Callback<ArrayList<InvoiceModel>>() {
//            @Override
//            public void onResponse(@NonNull Call<ArrayList<InvoiceModel>> call, @NonNull Response<ArrayList<InvoiceModel>> response) {
//                if (response.isSuccessful()) {
//                    if (response.body() != null) {
//
//                        String pos= String.valueOf(position);
//                        adapter = new ShoesAdapter(response.body(), getActivity(),pos);
//                        recyclerView.setAdapter(adapter);
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<ArrayList<InvoiceModel>> call, @NonNull Throwable t) {
//
//            }
//        });
//    }


    private void Product() {
//        rv_product.setHasFixedSize(true);
//        layoutManager = new GridLayoutManager(this, 2);
//        rv_product.setLayoutManager(layoutManager);
        pDialog = new ProgressDialog(getActivity());
        pDialog.setTitle("Checking Data");
        pDialog.setMessage("Please Wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();


        Observable<ProductModel> responseObservable = apiservice.getProduct(dat);

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
                            String pos=String.valueOf(position);
                            ProductListAdapter productAdapter=new ProductListAdapter(productModel.getData(),getActivity(),pos);
                            recyclerView.setAdapter(productAdapter);

//                            productAdapter = new ProductAdapter(Product.this, productModel.getData());
//                            rv_product.setAdapter(productAdapter);
//                            productAdapter.notifyDataSetChanged();

                        }
                    }
                });
    }

}
