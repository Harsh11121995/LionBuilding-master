package com.dies.lionbuilding.activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.dies.lionbuilding.R;
import com.dies.lionbuilding.activity.RouteManagement.FutureRouteActivity;
import com.dies.lionbuilding.apiservice.ApiService;
import com.dies.lionbuilding.apiservice.ApiServiceCreator;
import com.dies.lionbuilding.application.SessionManager;
import com.dies.lionbuilding.application.Utility;
import com.dies.lionbuilding.model.RouteModel;

import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.schedulers.Schedulers;

public class AddLeave extends AppCompatActivity {


    @BindView(R.id.btn_add)
    Button btn_add;
    @BindView(R.id.et_startdate)
    EditText et_startdate;
    @BindView(R.id.et_enddate)
    EditText et_enddate;
    @BindView(R.id.et_detail)
    EditText et_detail;
    @BindView(R.id.toolbar_Title)
    TextView toolbar_Title;
    @BindView(R.id.back_icon)
    ImageView back_icon;


    final Calendar myCalendar = Calendar.getInstance();

    SessionManager sessionManager;
    ApiService apiservice;
    ProgressDialog pDialog;
    RecyclerView.LayoutManager layoutManager;
    int statusCode;
    private Integer mYear, mMonth, mDay;
    String sdate, edate;
    private String TAG = "TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_leave);
        ButterKnife.bind(this);
        sessionManager = new SessionManager(this);
        apiservice = ApiServiceCreator.createService("latest");
        back_icon.setOnClickListener(view -> {
            finish();

        });


        toolbar_Title.setText("Add Leave");
        final Calendar myCalendar = Calendar.getInstance();

        btn_add.setOnClickListener(view -> {
            if (et_startdate.getText().toString().equals("")) {
                Utility.displayToast(this, "Please select start date");
            } else if (et_enddate.getText().toString().equals("")) {
                Utility.displayToast(this, "Please select end date");
            } else if (et_detail.getText().toString().equals("")) {
                Utility.displayToast(this, "Please enter Details");
            } else {
                AddLeave();
            }

        });

        et_startdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar mcurrentDate = Calendar.getInstance();
                mYear = mcurrentDate.get(Calendar.YEAR);
                mMonth = mcurrentDate.get(Calendar.MONTH);
                mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker = new DatePickerDialog(AddLeave.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        // TODO Auto-generated method stub
                        /*      Your code   to get date and time    */
                        selectedmonth++;
                        if (selectedmonth < 10) {
                            et_startdate.setText(selectedday + "-" + "0" + selectedmonth + "-" + selectedyear);
                            sdate = selectedday + "-" + "0" + selectedmonth + "-" + selectedyear;
                            Log.e(TAG, "date: " + sdate);
                        } else {
                            et_startdate.setText(selectedday + "-" + selectedmonth + "-" + selectedyear);
                            sdate = selectedday + "-" + selectedmonth + "-" + selectedyear;
                            Log.e(TAG, "date: " + sdate);
                        }
                        // txt_pickup_date.setText(selectedday+ "/" + selectedmonth + "/" + selectedyear);
                        et_startdate.setTextColor(getResources().getColor(R.color.colorblack));
                    }
                }, mYear, mMonth, mDay);
                mDatePicker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                mDatePicker.show();


            }
        });

        et_enddate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar mcurrentDate = Calendar.getInstance();
                mYear = mcurrentDate.get(Calendar.YEAR);
                mMonth = mcurrentDate.get(Calendar.MONTH);
                mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker = new DatePickerDialog(AddLeave.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        // TODO Auto-generated method stub
                        /*      Your code   to get date and time    */
                        selectedmonth++;
                        if (selectedmonth < 10) {
                            et_enddate.setText(selectedday + "-" + "0" + selectedmonth + "-" + selectedyear);
                            edate = selectedday + "-" + "0" + selectedmonth + "-" + selectedyear;
                            Log.e(TAG, "date: " + edate);
                        } else {
                            et_enddate.setText(selectedday + "-" + selectedmonth + "-" + selectedyear);
                            edate = selectedday + "-" + selectedmonth + "-" + selectedyear;
                            Log.e(TAG, "date: " + edate);
                        }
                        // txt_pickup_date.setText(selectedday+ "/" + selectedmonth + "/" + selectedyear);
                        et_enddate.setTextColor(getResources().getColor(R.color.colorblack));
                    }
                }, mYear, mMonth, mDay);
                mDatePicker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                mDatePicker.show();


            }
        });

    }

    public void AddLeave() {

        layoutManager = new LinearLayoutManager(this);

        pDialog = new ProgressDialog(this);
        pDialog.setTitle("Checking Data");
        pDialog.setMessage("Please Wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

        Observable<RouteModel> responseObservable = apiservice.AddLeave(
                sessionManager.getKeyId(),
                sdate,
                edate,
                et_detail.getText().toString());

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
                            Utility.displayToast(AddLeave.this, routeModel.getMessage());
                            finish();
                        } else {
                            Utility.displayToast(AddLeave.this, routeModel.getMessage());
                        }
                    }
                });

    }

}

 /* DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };

        DatePickerDialog.OnDateSetListener date1 = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel2();
            }
        };

        et_startdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(AddLeave.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        et_enddate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(AddLeave.this, date1, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });*/

/*       private void updateLabel() {
            String myFormat = "dd-MM-yyyy"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            et_startdate.setText(sdf.format(myCalendar.getTime()));
        }

    private void updateLabel2() {
        String myFormat = "dd-MM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        et_enddate.setText(sdf.format(myCalendar.getTime()));
    }
*/