package com.dies.lionbuilding.activity.OrderManagement;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.dies.lionbuilding.R;
import com.dies.lionbuilding.activity.SelectDealerActivity;
import com.dies.lionbuilding.activity.SelectDistributor;
import com.dies.lionbuilding.adapter.OrderSummaryAdapter;
import com.dies.lionbuilding.apiservice.ApiService;
import com.dies.lionbuilding.apiservice.ApiServiceCreator;
import com.dies.lionbuilding.application.SessionManager;
import com.dies.lionbuilding.application.Utility;
import com.dies.lionbuilding.model.ProductModel;
import com.dies.lionbuilding.model.UserDataResponse;
import com.google.gson.Gson;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.schedulers.Schedulers;

public class OrderSummaryActivty extends AppCompatActivity {


    public static final int REQUEST_CODE = 100;

    /* @BindView(R.id.txt_price)
     TextView totalprice;

     @BindView(R.id.txt_qty)
     TextView totalqty;*/
    public static final int REQUEST_CODE_SECOND = 101;
    @BindView(R.id.recycler_view)
    RecyclerView rv;
    @BindView(R.id.subtotal_value)
    TextView subtotal_value;
    @BindView(R.id.edt_distibutor)
    EditText edt_distibutor;
    @BindView(R.id.edt_dealer)
    EditText edt_dealer;
    @BindView(R.id.spnr_order_type)
    Spinner spnr_order_type;
    @BindView(R.id.back_icon)
    ImageView back_icon;
    @BindView(R.id.notify_icon)
    ImageView notify_icon;
    @BindView(R.id.toolbar_Title)
    TextView toolbar_Title;
    @BindView(R.id.btn_add)
    Button btn_add;
    @BindView(R.id.lnr_user)
    LinearLayout lnr_user;
    @BindView(R.id.relative1)
    RelativeLayout relative;
    @BindView(R.id.relative2)
    RelativeLayout relative2;
    @BindView(R.id.tv_ordrid)
    TextView tv_ordrid;

    ArrayList<ProductModel.Data> arrayList;
    RecyclerView.LayoutManager layoutManager;
    OrderSummaryAdapter adapter;
    List<String> list_pid, pqty, order_price, sord_point, sord_price;
    double sorderpoint;
    double sorderprice;
    SessionManager sessionManager;
    ApiService apiservice;
    ProgressDialog pDialog;
    int statusCode;
    String data1 = null;
    String distributor_id, dealer_id;
    ArrayList<String> arrayListotype = new ArrayList<>();
    String OrderType = "";
    String order_type;
    String res, res1, res2, res3, res4;
    double total_point = 0.0;
    private String TAG = "TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_summary_activty);
        ButterKnife.bind(this);
        sessionManager = new SessionManager(this);
        apiservice = ApiServiceCreator.createService("latest");

        back_icon.setOnClickListener(view -> {
            finish();
        });

        if (sessionManager.getKeyRoll().equals("Sales Executive")) {
            lnr_user.setVisibility(View.VISIBLE);
        } else if (sessionManager.getKeyRoll().equals("Dealer")) {
            lnr_user.setVisibility(View.GONE);
        } else if (sessionManager.getKeyRoll().equals("Distributor")) {
            lnr_user.setVisibility(View.GONE);
        } else {
            lnr_user.setVisibility(View.GONE);
        }


        toolbar_Title.setText("Order Summary");

        notify_icon.setVisibility(View.GONE);

        btn_add.setOnClickListener(view -> {

            /*if (sessionManager.getKeyRoll().equals("Dealer") || sessionManager.getKeyRoll().equals("Distributor")) {
                callApi();
            } else {

                if (dealer_id == null && distributor_id == null) {
                    Utility.displayToast(OrderSummaryActivty.this, "Please select distributor or dealer");
                } else {

                    callApi();

                }
                // addOrder();
            }*/

            addOrder();
        });

        arrayListotype.add("Primary Order");
        arrayListotype.add("Secondary Order");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayListotype);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnr_order_type.setAdapter(dataAdapter);


        spnr_order_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                OrderType = spnr_order_type.getSelectedItem().toString();
                if (OrderType.equals("Primary Order")) {
                    order_type = "primary";
                    edt_dealer.setVisibility(View.GONE);
                    edt_distibutor.setVisibility(View.VISIBLE);

                } else {
                    order_type = "secondary";
                    edt_dealer.setVisibility(View.VISIBLE);
                    edt_distibutor.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        edt_distibutor.setOnClickListener(view -> {
            Intent dateintent = new Intent(this, SelectDistributor.class);
            startActivityForResult(dateintent, REQUEST_CODE);
        });

        edt_dealer.setOnClickListener(view -> {
            Intent dateintent = new Intent(this, SelectDealerActivity.class);
            startActivityForResult(dateintent, REQUEST_CODE_SECOND);
        });


        arrayList = Utility.getAppcon().getSession().arrayListOrderData;

        Log.e(TAG, "onCreate: arralist--" + new Gson().toJson(arrayList));

        /*totalqty.setText(arrayList.get(0).getProduct_total_qty());
        totalprice.setText(arrayList.get(0).getProduct_total());*/

        subtotal_value.setText(arrayList.get(0).getProduct_total());

        Log.e(TAG, "arraylist qty---: " + arrayList.get(0).getProduct_total_qty());
        Log.e(TAG, "arraylist total---: " + arrayList.get(0).getProduct_total());


        getTotalData();

        layoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(layoutManager);
        adapter = new OrderSummaryAdapter(this, arrayList);
        rv.setAdapter(adapter);

        //android.text.TextUtils.join(",", ids);
    }


    private void getTotalData() {

        list_pid = new ArrayList<>();
        pqty = new ArrayList<>();
        order_price = new ArrayList<>();
        sord_point = new ArrayList<>();
        sord_price = new ArrayList<>();


        for (int i = 0; i < arrayList.size(); i++) {

            list_pid.add(String.valueOf(arrayList.get(i).getPrcat_id()));
            pqty.add(arrayList.get(i).getProduct_qty());
            order_price.add(arrayList.get(i).getProduct_price());


            sorderprice = Double.parseDouble(arrayList.get(i).getProduct_price()) * Double.parseDouble(arrayList.get(i).getProduct_qty());
            sorderpoint = Double.parseDouble(arrayList.get(i).getProduct_point()) * Double.parseDouble(arrayList.get(i).getProduct_qty());

            total_point = total_point + sorderpoint;

            sord_point.add(String.valueOf(sorderpoint));
            sord_price.add(String.valueOf(sorderprice));


        }
        Log.e(TAG, "getTotalData: " + "sorderprice" + sorderprice + "---" + "sorderpoint" + sorderpoint);

        res = android.text.TextUtils.join(",", list_pid);
        res1 = android.text.TextUtils.join(",", pqty);
        res2 = android.text.TextUtils.join(",", order_price);
        res3 = android.text.TextUtils.join(",", sord_point);
        res4 = android.text.TextUtils.join(",", sord_price);

        Log.e(TAG, "res---" + res);
        Log.e(TAG, "res1---" + res1);
        Log.e(TAG, "res2---" + res2);
        Log.e(TAG, "res3---" + res3);
        Log.e(TAG, "res4---" + res4);


    }

    public void addOrder() {

        if (dealer_id == null && distributor_id == null) {
            Utility.displayToast(OrderSummaryActivty.this, "Please select distributor or dealer");
        } else {

            callApi();

        }

    }

    private void callApi() {


        pDialog = new ProgressDialog(this);
        pDialog.setTitle("Checking Data");
        pDialog.setMessage("Please Wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

        Observable<UserDataResponse> responseObservable = apiservice.CreateOrder(
                sessionManager.getKeyId(),
                dealer_id,
                res,
                res1,
                res2,
                arrayList.get(0).getProduct_total(),
                String.valueOf(total_point),
                res3,
                res4,
                order_type,
                distributor_id,
                dealer_id
        );


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
                .subscribe(new Observer<UserDataResponse>() {
                    @Override
                    public void onCompleted() {
                        pDialog.dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error", "" + e.getMessage());
                    }

                    @Override
                    public void onNext(UserDataResponse userDataResponse) {
                        statusCode = userDataResponse.getStatusCode();
                        if (statusCode == 200) {


                            Utility.displayToast(OrderSummaryActivty.this, userDataResponse.getMessage());

                            relative.setVisibility(View.GONE);
                            relative2.setVisibility(View.VISIBLE);

                            tv_ordrid.setText(userDataResponse.getOrder_id());
                            // finish();


                        } else {
                            // Utility.displayToast(OrderSummaryActivty.this, userDataResponse.getMessage());
                        }
                    }
                });

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            data1 = data.getStringExtra("data");
            distributor_id = data.getStringExtra("user_id");
            //  Utility.getAppcon().getSession().dealer_id = uid;
            edt_distibutor.setText(data1);
        } else if (requestCode == REQUEST_CODE_SECOND && resultCode == RESULT_OK) {
            data1 = data.getStringExtra("data");
            dealer_id = data.getStringExtra("user_id");
            // Utility.getAppcon().getSession().dealer_id = uid;
            edt_dealer.setText(data1);
        }
    }

}
