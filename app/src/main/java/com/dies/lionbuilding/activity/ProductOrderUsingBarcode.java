package com.dies.lionbuilding.activity;

import android.app.ProgressDialog;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dies.lionbuilding.R;
import com.dies.lionbuilding.apiservice.ApiConstants;
import com.dies.lionbuilding.apiservice.ApiService;
import com.dies.lionbuilding.apiservice.ApiServiceCreator;
import com.dies.lionbuilding.application.SessionManager;
import com.dies.lionbuilding.application.Utility;
import com.dies.lionbuilding.database.DatabaseHelper;
import com.dies.lionbuilding.model.ProductModel;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.schedulers.Schedulers;

public class ProductOrderUsingBarcode extends AppCompatActivity {


    SessionManager sessionManager;
    ApiService apiservice;
    ProgressDialog pDialog;
    RecyclerView.LayoutManager layoutManager;
    int statusCode;
    int product_id;
    DatabaseHelper myDb;

    String User_name;

    @BindView(R.id.tv_p_name)
    TextView tv_p_name;

    @BindView(R.id.tv_p_price)
    TextView tv_p_price;

    @BindView(R.id.txt_p_desc)
    TextView txt_p_desc;

//    @BindView(R.id.spin_user)
//    Spinner spin_user;

    @BindView(R.id.et_qty)
    EditText et_qty;

    @BindView(R.id.product_image)
    ImageView product_image;

    @BindView(R.id.btn_add)
    Button btn_add;
    List<ProductModel.Data> arrayListdata;


    List<ProductModel.Data> arrayList=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_order_using_barcode);
        sessionManager = new SessionManager(this);
        apiservice = ApiServiceCreator.createService("latest");
        ButterKnife.bind(this);
        myDb=new DatabaseHelper(this);
        arrayListdata = Utility.getAppcon().getSession().arrayListProduct;




        tv_p_name.setText(arrayListdata.get(0).getProduct_name());
        tv_p_price.setText(arrayListdata.get(0).getProduct_price());
        txt_p_desc.setText(arrayListdata.get(0).getProduct_descpriction());
        product_id=arrayListdata.get(0).getP_id();

        Picasso.with(ProductOrderUsingBarcode.this).load(ApiConstants.IMAGE_URL + arrayListdata.get(0).getProduct_img())
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .fit()
                .into(product_image);


//        tv_p_name.setText(arrayListdata.get(0).getProduct_name());
//        tv_p_price.setText(arrayListdata.get(0).getProduct_price());
//        txt_p_desc.setText(arrayListdata.get(0).getProduct_descpriction());

//        String barcodeid=getIntent().getStringExtra("barcode_id");
//        getBarcodeData(barcodeid);
//        Log.e("barcode",barcodeid);


        btn_add.setOnClickListener(view -> {

            if (et_qty.getText().toString().equals("")) {
                Toast.makeText(this, "Please add Quentity", Toast.LENGTH_SHORT).show();
            }else {
                addincart();
            }

        });

    }


    public void addincart(){
//        Cursor res=myDb.checkData(arrayList.get(0).getPrcat_id());
//            if (res.getCount()>0){
//                Toast.makeText(ProductOrderUsingBarcode.this,"Data already Inserted",Toast.LENGTH_LONG).show();
//            }else {
            boolean isInserted = myDb.insertData(arrayListdata.get(0).getPrcat_id(),arrayListdata.get(0).getProduct_catagory_id(),
                    arrayListdata.get(0).getProduct_name(),
                    arrayListdata.get(0).getProduct_price(),
                    arrayListdata.get(0).getProduct_descpriction(),
                    arrayListdata.get(0).getProduct_img(),
                    et_qty.getText().toString(),
                    Utility.getAppcon().getSession().dealer_id,
                    arrayListdata.get(0).getProduct_point(),
                    sessionManager.getKeyId()
            );

            if(isInserted == true)
                Toast.makeText(ProductOrderUsingBarcode.this,"Data Inserted",Toast.LENGTH_LONG).show();
                finish();

//            }
    }

    public void getBarcodeData(String barcode_id)
    {
        pDialog = new ProgressDialog(this);
        pDialog.setTitle("Checking Data");
        pDialog.setMessage("Please Wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

        Observable<ProductModel> responseObservable = apiservice.getBarcodedata(barcode_id);

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
                        Log.e("barcode_data", productModel.toString());

                            arrayList=productModel.getData();
                            tv_p_name.setText(productModel.getData().get(0).getProduct_name());
                            tv_p_price.setText(productModel.getData().get(0).getProduct_price());
                            txt_p_desc.setText(productModel.getData().get(0).getProduct_descpriction());
                            product_id=productModel.getData().get(0).getP_id();

                            Picasso.with(ProductOrderUsingBarcode.this).load(ApiConstants.IMAGE_URL + arrayList.get(0).getProduct_img())
                                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                                    .networkPolicy(NetworkPolicy.NO_CACHE)
                                    .fit()
                                    .into(product_image);

                        }
                    }
                });

    }

    public void OrderAdd(){
        pDialog = new ProgressDialog(this);
        pDialog.setTitle("Checking Data");
        pDialog.setMessage("Please Wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

        Observable<ProductModel> responseObservable = apiservice.AddOrder(product_id,User_name,et_qty.getText().toString());

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
                            Log.e("barcode_data", productModel.toString());
                            tv_p_name.setText(productModel.getData().get(0).getProduct_name());
                            tv_p_price.setText(productModel.getData().get(0).getProduct_price());
                            txt_p_desc.setText(productModel.getData().get(0).getProduct_descpriction());
                        }
                    }
                });

    }
}
