package com.dies.lionbuilding.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dies.lionbuilding.R;
import com.dies.lionbuilding.activity.OrderManagement.SelectOrdereTypeActivity;
import com.dies.lionbuilding.adapter.CartAdapter;
import com.dies.lionbuilding.apiservice.ApiService;
import com.dies.lionbuilding.apiservice.ApiServiceCreator;
import com.dies.lionbuilding.application.SessionManager;
import com.dies.lionbuilding.application.Utility;
import com.dies.lionbuilding.database.DatabaseHelper;
import com.dies.lionbuilding.model.OrderData;
import com.dies.lionbuilding.model.ProductModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CartActivity extends AppCompatActivity {


    @BindView(R.id.rcv_product)
    RecyclerView rcv_product;

    @BindView(R.id.toolbar_Title)
    TextView toolbar_Title;

    @BindView(R.id.txt_total_price)
    TextView txt_total_price;

    @BindView(R.id.btn_add)
    Button btn_add;

    @BindView(R.id.lnr_total)
    LinearLayout lnr_total;

//    @BindView(R.id.edt_user)
//    EditText edt_user;

    @BindView(R.id.back_icon)
    ImageView back_icon;



    public static final int REQUEST_CODE = 100;

    String data1 = null;

    CartAdapter adapter;




    DatabaseHelper myDb;
    List<ProductModel.Data> arrayList;
    RecyclerView.LayoutManager layoutManager;
    Cursor res;

    SessionManager sessionManager;
    ApiService apiservice;
    ProgressDialog pDialog;
    int statusCode;
    double total = 0.0;
    double product_point_total=0.0;
    String sord_prd_id,sord_qty,sord_price,sord_total,sord_point;

    ArrayList<OrderData> arrayListOrderData,arrayListOrderData2;


    ArrayList<String> productid,prdqty,prdprice,totalprice,productpoint;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        ButterKnife.bind(this);
        toolbar_Title.setText("Cart");
        myDb = new DatabaseHelper(this);
        arrayList = new ArrayList<>();
        sessionManager = new SessionManager(this);
        apiservice = ApiServiceCreator.createService("latest");

        back_icon.setOnClickListener(view -> {
            finish();
        });



//        edt_user.setOnClickListener(view -> {
//            Intent dateintent = new Intent(this, SelectDistributor.class);
//            startActivityForResult(dateintent, REQUEST_CODE);
//        });

        btn_add.setOnClickListener(view -> {
            sord_prd_id = TextUtils.join(",", productid);
            sord_qty = TextUtils.join(",", prdqty);
            sord_price = TextUtils.join(",", prdprice);
            sord_total = TextUtils.join(",", totalprice);
            sord_point = TextUtils.join(",", productpoint);

            if (sord_prd_id.equals("")){
                Toast.makeText(this, "data null", Toast.LENGTH_SHORT).show();
            }else if (sord_qty.equals("")){
                Toast.makeText(this, "data null", Toast.LENGTH_SHORT).show();
            }else if (sord_price.equals("")){
                Toast.makeText(this, "data null", Toast.LENGTH_SHORT).show();
            }else if (sord_total.equals("")){
                Toast.makeText(this, "data null", Toast.LENGTH_SHORT).show();
            }else {
                //addOrder();
                OrderData orderData=new OrderData();
                arrayListOrderData=new ArrayList<>();
                orderData.setDealer_id("0");
                orderData.setOrder_product_id(sord_prd_id);
                orderData.setOrder_price(sord_price);
                orderData.setOrder_product_qty(sord_qty);
                orderData.setTotal(String.valueOf(total));
                orderData.setProduct_point_total(String.valueOf(product_point_total));
                orderData.setsOrd_Point(sord_point);
                orderData.setOrder_totalprice(sord_total);

                arrayListOrderData.add(orderData);
                Utility.getAppcon().getSession().arrayListorder=new ArrayList<>();
                Utility.getAppcon().getSession().arrayListorder=arrayListOrderData;

                Intent intent=new Intent(this, SelectOrdereTypeActivity.class);
                startActivity(intent);


            }
        });

         getData();

    }

    public void getData(){
        res = myDb.getIdWiseData(sessionManager.getKeyId());
        if (res.getCount() > 0) {
            lnr_total.setVisibility(View.VISIBLE);
            Utility.displayToast(this, "data found");

            Log.e("Sqlite_Data", DatabaseUtils.dumpCursorToString(res));


            if (res.moveToFirst()) {
                do {
                    arrayList.add(new ProductModel().new Data(
                            res.getString(1), res.getString(2), res.getString(3)
                            , res.getString(4), "", "", res.getString(7),
                            "", res.getString(5), res.getString(6), res.getInt(0),res.getString(9)));

                } while (res.moveToNext());
                //res.getString(11)
                res.close();
                rcv_product.setHasFixedSize(true);
                layoutManager = new LinearLayoutManager(this);
                rcv_product.setLayoutManager(layoutManager);

                Subtottal();
                adapter = new CartAdapter(this, arrayList,"product");
                rcv_product.setAdapter(adapter);
            } else {
                Utility.displayToast(this, "No Data found");
            }
        }else {
            lnr_total.setVisibility(View.GONE);
        }
    }

    public void Subtottal() {
        productid =new ArrayList<>();
        prdqty=new ArrayList<>();
        prdprice=new ArrayList<>();
        totalprice=new ArrayList<>();
        productpoint=new ArrayList<>();


        for (int i = 0; i < arrayList.size(); i++) {
            double pp=Double.parseDouble(arrayList.get(i).getProduct_price());
            double qty=Double.parseDouble(arrayList.get(i).getProduct_qty());
            double tp=pp*qty;
            total = total + tp;
            txt_total_price.setText(String.valueOf(total));
            String pid=String.valueOf(arrayList.get(i).getPrcat_id());
            productid.add(pid);
            prdqty.add(arrayList.get(i).getProduct_qty());
            prdprice.add(arrayList.get(i).getProduct_price());
            double product_point=Double.parseDouble(arrayList.get(i).getProduct_point());
            Double totalPRoductPoint=Double.parseDouble(arrayList.get(i).getProduct_qty())*product_point;
            product_point_total=product_point_total+totalPRoductPoint;
            productpoint.add(String.valueOf(totalPRoductPoint));
            totalprice.add(String.valueOf(tp));
        }

//        String s = TextUtils.join(", ", productid);
//
//        String delarid=Utility.getAppcon().getSession().dealer_id;
//        String user_id=sessionManager.getKeyId();


    }


//    public void addOrder(){
//        pDialog = new ProgressDialog(this);
//        pDialog.setTitle("Checking Data");
//        pDialog.setMessage("Please Wait...");
//        pDialog.setIndeterminate(false);
//        pDialog.setCancelable(false);
//        pDialog.show();
//
//        Observable<UserDataResponse> responseObservable = apiservice.CreateOrder(sessionManager.getKeyId(),
//                Utility.getAppcon().getSession().dealer_id ,
//                sord_prd_id,
//                sord_qty,
//                sord_price,
//                String.valueOf(total),
//                String.valueOf(product_point_total),
//                sord_point,
//                sord_total);
//
//        responseObservable.subscribeOn(Schedulers.newThread())
//                .observeOn(rx.android.schedulers.AndroidSchedulers.mainThread())
//                .onErrorResumeNext(throwable -> {
//                    if (throwable instanceof retrofit2.HttpException) {
//                        retrofit2.HttpException ex = (retrofit2.HttpException) throwable;
//                        statusCode = ex.code();
//                        Log.e("error", ex.getLocalizedMessage());
//                    } else if (throwable instanceof SocketTimeoutException) {
//                        statusCode = 1000;
//                    }
//                    return Observable.empty();
//                })
//                .subscribe(new Observer<UserDataResponse>() {
//                    @Override
//                    public void onCompleted() {
//                        pDialog.dismiss();
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.e("error", "" + e.getMessage());
//                    }
//
//                    @Override
//                    public void onNext(UserDataResponse userDataResponse) {
//                        statusCode = userDataResponse.getStatusCode();
//                        if (statusCode == 200) {
//                            Utility.displayToast(CartActivity.this,userDataResponse.getMessage());
//                            int result=myDb.removeAll();
//                            if (result>0){
//                                adapter.clear();
//                                adapter.notifyDataSetChanged();
//                                lnr_total.setVisibility(View.GONE);
//                                Utility.displayToast(CartActivity.this,"remove alldata");
//                            }else {
//                                Utility.displayToast(CartActivity.this,"noooooooremove data");
//                            }
//                        }else {
//                            Utility.displayToast(CartActivity.this,userDataResponse.getMessage());
//                        }
//                    }
//                });
//    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        data1 = data.getStringExtra("data");
        String uid=data.getStringExtra("user_id");
        Utility.getAppcon().getSession().dealer_id=uid;
//        Log.e("usr_id",uid);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data1 != null) {
          //  edt_user.setText(data1);
        }
    }
}
