package com.dies.lionbuilding.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
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
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.schedulers.Schedulers;

public class ProductDetailActivity extends AppCompatActivity {

    @BindView(R.id.tv_p_name)
    TextView tv_p_name;

    @BindView(R.id.tv_p_price)
    TextView tv_p_price;

    @BindView(R.id.txt_p_desc)
    TextView txt_p_desc;

    @BindView(R.id.tv_p_point)
    TextView tv_p_point;

    @BindView(R.id.txt_qty)
    TextView txt_qty;



    @BindView(R.id.back_icon)
    ImageView back_icon;
    @BindView(R.id.btn_cart)
    Button btn_cart;

    @BindView(R.id.product_image)
    ImageView product_image;


    List<ProductModel.Data> arrayList;
    DatabaseHelper myDb;

    SessionManager sessionManager;
    ApiService apiservice;
    ProgressDialog pDialog;
    int statusCode;
    String product_id, product_points;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        ButterKnife.bind(this);
      //  arrayList = Utility.getAppcon().getSession().arrayListProduct;
        myDb = new DatabaseHelper(this);
        sessionManager = new SessionManager(this);
        apiservice = ApiServiceCreator.createService("latest");


            back_icon.setOnClickListener(view -> {
                finish();
            });

        String barcodeid = getIntent().getStringExtra("barcode_id");
        getBarcodeData(barcodeid);
        Log.e("barcode", barcodeid);
        btn_cart.setOnClickListener(view -> {
            if (product_points.equals("")){
                Utility.displayToast(ProductDetailActivity.this, "no product points");
            }else if (txt_qty.getText().toString().equals("")){
                Utility.displayToast(ProductDetailActivity.this, "Please add quentity");
            }else {
                adddata();
            }

        });

    }


    public void adddata(){
        boolean isInserted = myDb.insertBarcodeData(
                arrayList.get(0).getPrcat_id(),
                arrayList.get(0).getProduct_catagory_id(),
                arrayList.get(0).getProduct_name(),
                arrayList.get(0).getProduct_price(),
                arrayList.get(0).getProduct_descpriction(),
                arrayList.get(0).getProduct_img(),
                txt_qty.getText().toString(),
                "",
                product_points
        );
        if(isInserted == true) {
            Toast.makeText(ProductDetailActivity.this, "Data Inserted", Toast.LENGTH_LONG).show();
            finish();
        }else {
            Toast.makeText(ProductDetailActivity.this, "Data Not Inserted", Toast.LENGTH_LONG).show();
        }

    }


    public void getBarcodeData(String barcode_id) {
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
                          //  Log.e("barcode_data", productModel.toString());
                            //  arrayList=productModel.getData();
                            arrayList=productModel.getData();
                            tv_p_name.setText(productModel.getData().get(0).getProduct_name());
                            tv_p_price.setText(productModel.getData().get(0).getProduct_price());
                            txt_p_desc.setText(productModel.getData().get(0).getProduct_descpriction());
                            product_id = String.valueOf(productModel.getData().get(0).getP_id());
                            tv_p_point.setText(productModel.getData().get(0).getProduct_point());
                            product_points = productModel.getData().get(0).getProduct_point();
                            Picasso.with(ProductDetailActivity.this).load(ApiConstants.IMAGE_URL + productModel.getData().get(0).getProduct_img())
                                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                                    .networkPolicy(NetworkPolicy.NO_CACHE)
                                    .into(product_image);

                            Log.d("listdata", arrayList.get(0).getPrcat_id());
                        }
                    }
                });

    }





}
