package com.dies.lionbuilding.activity.RouteManagement;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.dies.lionbuilding.R;
import com.dies.lionbuilding.TimeLine.TimelineRow;
import com.dies.lionbuilding.adapter.FutureRouteAdapter;
import com.dies.lionbuilding.apiservice.ApiService;
import com.dies.lionbuilding.apiservice.ApiServiceCreator;
import com.dies.lionbuilding.application.SessionManager;
import com.dies.lionbuilding.model.RouteModel;
import com.google.gson.Gson;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.schedulers.Schedulers;

public class FutureRouteActivity extends AppCompatActivity {


    @BindView(R.id.txt_selectdate)
    TextView txt_selectdate;

    @BindView(R.id.btn_submit)
    Button btn_submit;

    @BindView(R.id.rv_future_route_list)
    RecyclerView rv_future_route_list;

    @BindView(R.id.back_icon)
    ImageView back_icon;
    @BindView(R.id.toolbar_Title)
    TextView toolbar_Title;
    SessionManager sessionManager;
    ApiService apiservice;
    ProgressDialog pDialog;
    String TAG = "TAG";
    int statusCode;
    List<RouteModel.RouteData> arrayListdata = new ArrayList<>();
    FutureRouteAdapter myAdapter;
    RecyclerView.LayoutManager layoutManager;
    private Integer mYear, mMonth, mDay;
    String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_future_route);
        ButterKnife.bind(this);
        //arrayListdata = new ArrayList<>();
        sessionManager = new SessionManager(this);
        apiservice = ApiServiceCreator.createService("latest");

        toolbar_Title.setText("Future Route");
        initViews();
    }

    private void initViews() {

        back_icon.setOnClickListener(view -> {
            finish();
        });

        rv_future_route_list.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(FutureRouteActivity.this);
        rv_future_route_list.setLayoutManager(layoutManager);

        txt_selectdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar mcurrentDate = Calendar.getInstance();
                mYear = mcurrentDate.get(Calendar.YEAR);
                mMonth = mcurrentDate.get(Calendar.MONTH);
                mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker = new DatePickerDialog(FutureRouteActivity.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        // TODO Auto-generated method stub
                        /*      Your code   to get date and time    */
                        selectedmonth++;
                        if (selectedmonth < 10) {
                            txt_selectdate.setText(selectedday + "-" + "0" + selectedmonth + "-" + selectedyear);
                            date = selectedday + "-" + "0" + selectedmonth + "-" + selectedyear;
                            Log.e(TAG, "date: " + date);
                        } else {
                            txt_selectdate.setText(selectedday + "-" + selectedmonth + "-" + selectedyear);
                            date = selectedday + "-" + selectedmonth + "-" + selectedyear;
                            Log.e(TAG, "date: " + date);
                        }
                        // txt_pickup_date.setText(selectedday+ "/" + selectedmonth + "/" + selectedyear);
                        txt_selectdate.setTextColor(getResources().getColor(R.color.colorblack));
                    }
                }, mYear, mMonth, mDay);
                mDatePicker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                mDatePicker.show();


            }
        });

        String id = sessionManager.getKeyId();
        Log.e(TAG, "id: " + id);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (sessionManager.getKeyRoll().equals("RM")) {
                    getRMFutureRouteApi();
                } else {
                    getSalesFutureRouteApi();

                }

            }
        });
    }

    private void getSalesFutureRouteApi() {

        pDialog = new ProgressDialog(FutureRouteActivity.this);
        pDialog.setTitle("Checking Data");
        pDialog.setMessage("Please Wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

        Observable<RouteModel> responseObservable = apiservice.getAllFutureRoute(
                sessionManager.getKeyId(),
                date);

        Log.e(TAG, "getKeyId: " + sessionManager.getKeyId());
        Log.e(TAG, "txt_selectdate: " + date);

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
                .subscribe(new Observer<RouteModel>() {
                    @Override
                    public void onCompleted() {
                        pDialog.dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error", "" + e.getMessage());
                    }

                    @Override
                    public void onNext(RouteModel routeModel) {
                        statusCode = routeModel.getStatusCode();
                        if (statusCode == 200) {

                            arrayListdata = routeModel.getData().get(0).getRoute_data();
                            Log.e(TAG, "arrayListdata: " + new Gson().toJson(arrayListdata));
                            myAdapter = new FutureRouteAdapter(FutureRouteActivity.this, arrayListdata);
                            rv_future_route_list.setAdapter(myAdapter);

                        }
                    }
                });
    }

    private void getRMFutureRouteApi() {

        pDialog = new ProgressDialog(FutureRouteActivity.this);
        pDialog.setTitle("Checking Data");
        pDialog.setMessage("Please Wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

        Observable<RouteModel> responseObservable = apiservice.getAllRMFutureRoute(
                sessionManager.getKeyId(),
                date);

        Log.e(TAG, "getKeyId: " + sessionManager.getKeyId());
        Log.e(TAG, "txt_selectdate: " + date);

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
                .subscribe(new Observer<RouteModel>() {
                    @Override
                    public void onCompleted() {
                        pDialog.dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error", "" + e.getMessage());
                    }

                    @Override
                    public void onNext(RouteModel routeModel) {
                        statusCode = routeModel.getStatusCode();
                        if (statusCode == 200) {

                            arrayListdata = routeModel.getData().get(0).getRoute_data();
                            Log.e(TAG, "arrayListdata: " + new Gson().toJson(arrayListdata));
                            myAdapter = new FutureRouteAdapter(FutureRouteActivity.this, arrayListdata);
                            rv_future_route_list.setAdapter(myAdapter);

                        }
                    }
                });

    }
}
