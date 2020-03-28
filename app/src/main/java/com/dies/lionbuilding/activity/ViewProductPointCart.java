    package com.dies.lionbuilding.activity;

import android.app.ProgressDialog;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.dies.lionbuilding.adapter.CartAdapter;
import com.dies.lionbuilding.apiservice.ApiService;
import com.dies.lionbuilding.apiservice.ApiServiceCreator;
import com.dies.lionbuilding.application.SessionManager;
import com.dies.lionbuilding.application.Utility;
import com.dies.lionbuilding.database.DatabaseHelper;
import com.dies.lionbuilding.model.ProductModel;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.schedulers.Schedulers;

    public class ViewProductPointCart extends AppCompatActivity {

        @BindView(R.id.rcv_product)
        RecyclerView rcv_product;

        @BindView(R.id.lnr_total)
        LinearLayout lnr_total;

        @BindView(R.id.txt_total_point)
        TextView txt_total_point;

        @BindView(R.id.btn_add)
        Button btn_add;

        @BindView(R.id.back_icon)
        ImageView back_icon;
        CartAdapter adapter;




        SessionManager sessionManager;
        ApiService apiservice;
        ProgressDialog pDialog;
        int statusCode;
        Cursor res;
        int tpq;

        DatabaseHelper myDb;
        List<ProductModel.Data> arrayList;
        RecyclerView.LayoutManager layoutManager;

        ArrayList<String> productid,prdqty,prdprice,totalprice,productpoint;
        String sord_prd_id,sord_qty,sord_price,sord_total,sord_point;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_product_point_cart);
        ButterKnife.bind(this);
        sessionManager = new SessionManager(this);
        apiservice = ApiServiceCreator.createService("latest");
        myDb = new DatabaseHelper(this);
        arrayList = new ArrayList<>();
        back_icon.setOnClickListener(view -> {
            finish();
        });
        getData();

        btn_add.setOnClickListener(view -> {

            sord_prd_id = TextUtils.join(", ", productid);
            sord_qty = TextUtils.join(", ", prdqty);
            sord_point = TextUtils.join(", ", productpoint);

            if (sord_prd_id.equals("")){
                Toast.makeText(this, "data null", Toast.LENGTH_SHORT).show();
            }else if (sord_qty.equals("")){
                Toast.makeText(this, "data null", Toast.LENGTH_SHORT).show();
            }else if (productpoint.equals("")){
                Toast.makeText(this, "data null", Toast.LENGTH_SHORT).show();
            }else {
                AddPoints();
            }
        });


    }

        public void getData(){
            res = myDb.getAllBarcodeData();
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
                    adapter = new CartAdapter(this, arrayList,"point");
                    rcv_product.setAdapter(adapter);
                } else {
                    Utility.displayToast(this, "No Data found");
                }
            }else {
                lnr_total.setVisibility(View.GONE);
            }
        }


        public void Subtottal(){
            productid =new ArrayList<>();
            prdqty=new ArrayList<>();
            productpoint=new ArrayList<>();

            ArrayList<Integer> tpp=new ArrayList<Integer>();

            for (int i = 0; i < arrayList.size(); i++) {
                String pid=String.valueOf(arrayList.get(i).getPrcat_id());
                productid.add(pid);
                prdqty.add(arrayList.get(i).getProduct_qty());
                productpoint.add(arrayList.get(i).getProduct_point());
                tpq=Integer.parseInt(arrayList.get(i).getProduct_qty())*Integer.parseInt(arrayList.get(i).getProduct_point());
                tpp.add(tpq);
            }
            int total_point=0;
            for (int i=0;i<tpp.size();i++){
                total_point=total_point+tpp.get(i);
            }
            txt_total_point.setText(String.valueOf(total_point));
        }


        public void AddPoints() {
            pDialog = new ProgressDialog(this);
            pDialog.setTitle("Checking Data");
            pDialog.setMessage("Please Wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();

            Observable<ProductModel> responseObservable = apiservice.AddPoints(sessionManager.getKeyId(), sord_point, sord_prd_id,sord_qty);

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
                                Utility.displayToast(ViewProductPointCart.this, productModel.getMessage());
                                int result=myDb.removeAllBarcode();
                                if (result>0){
                                    adapter.clear();
                                    adapter.notifyDataSetChanged();
                                    lnr_total.setVisibility(View.GONE);
                                    Utility.displayToast(ViewProductPointCart.this,"remove alldata");
                                }else {
                                    Utility.displayToast(ViewProductPointCart.this,"Error");
                                }
                                finish();
                            } else {
                                Utility.displayToast(ViewProductPointCart.this, productModel.getMessage());
                            }
                        }
                    });
        }
}
