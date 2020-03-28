package com.dies.lionbuilding.activity.OrderManagement;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.dies.lionbuilding.R;
import com.dies.lionbuilding.activity.SelectDealerActivity;
import com.dies.lionbuilding.activity.SelectDistributor;
import com.dies.lionbuilding.apiservice.ApiService;
import com.dies.lionbuilding.apiservice.ApiServiceCreator;
import com.dies.lionbuilding.application.SessionManager;
import com.dies.lionbuilding.application.Utility;
import com.dies.lionbuilding.database.DatabaseHelper;
import com.dies.lionbuilding.model.OrderData;
import com.dies.lionbuilding.model.UserDataResponse;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.schedulers.Schedulers;

public class SelectOrdereTypeActivity extends AppCompatActivity {


    public static final int REQUEST_CODE = 100;
    public static final int REQUEST_CODE_SECOND = 101;
    @BindView(R.id.spnr_order_type)
    Spinner spnr_order_type;
    @BindView(R.id.edt_dealer)
    EditText edt_dealer;
    @BindView(R.id.edt_distibutor)
    EditText edt_distibutor;
    SessionManager sessionManager;
    ApiService apiservice;
    ProgressDialog pDialog;
    int statusCode;
    DatabaseHelper myDb;
    String data1 = null;
    String order_type;
    String sord_prd_id, sord_qty, sord_price, sord_total, sord_point;
    double total = 0.0;
    double product_point_total = 0.0;
    ArrayList<OrderData> arrayList;
    String distributor_id,dealer_id;
    @BindView(R.id.btn_save)
    Button btn_save;

    String OrderType = "";

    ArrayList<String> arrayListotype = new ArrayList<>();
    List<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_ordere_type);
        ButterKnife.bind(this);
        sessionManager = new SessionManager(this);
        apiservice = ApiServiceCreator.createService("latest");
        myDb = new DatabaseHelper(this);
        arrayList = Utility.getAppcon().getSession().arrayListorder;

        btn_save.setOnClickListener(view -> {

            Log.i("MyAndroidClass", arrayList.get(0).getOrder_price());
            //Log.d("array_list", arrayList.toString());
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

                } else {
                    order_type = "secondary";
                    edt_dealer.setVisibility(View.VISIBLE);
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


        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayListotype);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnr_order_type.setAdapter(arrayAdapter);

    }


    public void addOrder() {
        pDialog = new ProgressDialog(this);
        pDialog.setTitle("Checking Data");
        pDialog.setMessage("Please Wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

        Observable<UserDataResponse> responseObservable = apiservice.CreateOrder(
                sessionManager.getKeyId(),
                dealer_id,
                arrayList.get(0).getOrder_product_id(),
                arrayList.get(0).getOrder_product_qty(),
                arrayList.get(0).getOrder_price(),
                arrayList.get(0).getTotal(),
                arrayList.get(0).getProduct_point_total(),
                arrayList.get(0).getsOrd_Point(),
                arrayList.get(0).getOrder_totalprice(),
                order_type,
                distributor_id,
                dealer_id);


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
                            Utility.displayToast(SelectOrdereTypeActivity.this, userDataResponse.getMessage());
                            int result = myDb.removeAll();
                            if (result > 0) {
//                                adapter.clear();
//                                adapter.notifyDataSetChanged();
                                //lnr_total.setVisibility(View.GONE);
                             //   Utility.displayToast(SelectOrdereTypeActivity.this, "remove alldata");
                            } else {
                                Utility.displayToast(SelectOrdereTypeActivity.this, "noooooooremove data");
                            }
                        } else {
                            Utility.displayToast(SelectOrdereTypeActivity.this, userDataResponse.getMessage());
                        }
                    }
                });
    }

//    public void getDistributor(){
//        pDialog = new ProgressDialog(this);
//        pDialog.setTitle("Checking Data");
//        pDialog.setMessage("Please Wait...");
//        pDialog.setIndeterminate(false);
//        pDialog.setCancelable(false);
//        pDialog.show();
//
//        Observable<UserDataResponse> responseObservable = apiservice.getDistributor();
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
//
//
//                        }
//                    }
//                });
//    }


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
