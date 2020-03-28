package com.dies.lionbuilding.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.dies.lionbuilding.R;
import com.dies.lionbuilding.apiservice.ApiService;
import com.dies.lionbuilding.apiservice.ApiServiceCreator;
import com.dies.lionbuilding.application.SessionManager;
import com.dies.lionbuilding.application.Utility;
import com.dies.lionbuilding.model.CountryModel;
import com.dies.lionbuilding.model.LoginResponse;

import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.schedulers.Schedulers;

public class AddProfile extends AppCompatActivity {

    @BindView(R.id.back_icon)
    ImageView back_icon;
    @BindView(R.id.toolbar_Title)
    TextView tv_toolbar_title;


//    @BindView(R.id.edt_Dob)
//    EditText edt_Dob;
    @BindView(R.id.edtfirstname)
    EditText edt_firstname;

    @BindView(R.id.edt_gst)
    EditText edt_gst;


//    @BindView(R.id.edtlastname)
//    EditText edt_lastname;
//    @BindView(R.id.edt_email)
//    EditText edt_email;
    @BindView(R.id.edt_mNo)
    EditText edt_mobileNo;
    @BindView(R.id.edt_address1)
    EditText edt_address1;
//    @BindView(R.id.edt_address2)
//    EditText edt_address2;
    @BindView(R.id.edt_password)
    EditText edt_password;

    @BindView(R.id.spnr_contry)
    Spinner spnr_contry;
    @BindView(R.id.spnr_state)
    Spinner spnr_state;
    @BindView(R.id.spnr_city)
    Spinner spnr_city;
    @BindView(R.id.spnr_usertype)
    Spinner spnr_usertype;


    private ApiService apiservice;
    private SessionManager sessionManager = null;
    private ProgressDialog pDialog;
    int statusCode;
    String c_id, s_id, city_id, roll_id;

    List<CountryModel.Data> data, data1, data2, data3;

    String[] users = {"Dealer", "Carpenter", "Sales Executive"};
    String Contry, City, State, user_type;

    ArrayList<String> countryarray = new ArrayList<>();
    ArrayList<String> statearray = new ArrayList<>();
    ArrayList<String> cityarray = new ArrayList<>();
    ArrayList<String> rolearray = new ArrayList<>();

    final Calendar myCalendar = Calendar.getInstance();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_profile);
        ButterKnife.bind(this);
        tv_toolbar_title.setText("Add Profile");
        back_icon.setOnClickListener(view -> {
            finish();
        });

        sessionManager=new SessionManager(this);
        apiservice = ApiServiceCreator.createService("latest");
        userTypeAdapter userTypeAdapter = new userTypeAdapter(AddProfile.this, android.R.layout.simple_list_item_1);
        userTypeAdapter.addAll(users);
        userTypeAdapter.add("Select User Type");
        spnr_usertype.setAdapter(userTypeAdapter);
        spnr_usertype.setSelection(userTypeAdapter.getCount());
       // getCountry();
        getState("1");
        getUsertype();

        spnr_contry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (spnr_contry.getSelectedItem() == "Select Contry") {
                    Contry = "";
                    //Do nothing.
                } else {
                    Contry = spnr_contry.getSelectedItem().toString();
                    CountryModel.Data datalist = data.get(i - 1);
                    c_id = datalist.getCountry_id();

                    Log.e("state_id", c_id);
                    getState(c_id);

                    //Toast.makeText(MainActivity.this, spinner.getSelectedItem().toString(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        spnr_state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (spnr_state.getSelectedItem() == "Select State") {
                    State = "";
                    //Do nothing.
                } else {
                    State = spnr_state.getSelectedItem().toString();
                    CountryModel.Data datalist = data1.get(i - 1);
                    s_id = datalist.getState_id();
                    Log.e("state_id", s_id);
                    getCity(s_id);
                    //Toast.makeText(MainActivity.this, spinner.getSelectedItem().toString(), Toast.LENGTH_LONG).show();
                }

            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        spnr_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (spnr_city.getSelectedItem() == "Select City") {
                    City = "";
                    //Do nothing.
                } else {
                    City = spnr_city.getSelectedItem().toString();
                    CountryModel.Data datalist = data2.get(i - 1);
                    city_id = datalist.getCity_id();
                    //Toast.makeText(MainActivity.this, spinner.getSelectedItem().toString(), Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        spnr_usertype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (spnr_usertype.getSelectedItem() == "Select UserType") {
                    user_type = "";

                } else {
                    user_type = spnr_usertype.getSelectedItem().toString();
                    CountryModel.Data datalist = data3.get(i - 1);

                    roll_id = datalist.getRoleId();
                    Log.d("dataList", "onItemSelected: " + roll_id);

                    //Toast.makeText(RegisterActivity.this, spnr_usertype.getSelectedItem().toString(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }




    @OnClick(R.id.btn_register)
    public void btn_register() {
        if (edt_firstname.getText().toString().equals("")) {
            Utility.displayToast(AddProfile.this, "Please enter First Name");
        } else if (edt_address1.getText().toString().equals("")) {
            Utility.displayToast(AddProfile.this, "Please enter Address");
        } else if (edt_mobileNo.equals("")) {
            Utility.displayToast(AddProfile.this, "Please enter Mobile Number");
        } else if (edt_password.getText().toString().equals("")) {
            Utility.displayToast(AddProfile.this, "Please enter Password");
        } else {
            AddUser();
        }

    }

    private boolean isValidEmailId(String email) {

        return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(email).matches();
    }


    private void updateLabel() {
        String myFormat = "MM/dd/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        //edt_Dob.setText(sdf.format(myCalendar.getTime()));
    }


    public class userTypeAdapter extends ArrayAdapter<String> {

        public userTypeAdapter(Context context, int textViewResourceId) {
            super(context, textViewResourceId);
            // TODO Auto-generated constructor stub

        }

        @Override
        public int getCount() {

            // TODO Auto-generated method stub
            int count = super.getCount();

            return count > 0 ? count - 1 : count;
        }
    }


    private void AddUser() {
        pDialog = new ProgressDialog(this);
        pDialog.setTitle("Checking Data");
        pDialog.setMessage("Please Wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();


        Observable<LoginResponse> responseObservable = apiservice.addnewprofile(
                sessionManager.getKeyId(),
                edt_firstname.getText().toString(),
                edt_mobileNo.getText().toString(),
                edt_gst.getText().toString(),
                edt_password.getText().toString(),
                edt_address1.getText().toString(),
                s_id,
                city_id,
                "",
                roll_id

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
                .subscribe(new Observer<LoginResponse>() {
                    @Override
                    public void onCompleted() {
                        pDialog.dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error", "" + e.getMessage());
                    }

                    @Override
                    public void onNext(LoginResponse loginResponse) {
                        statusCode = loginResponse.getStatusCode();
                        if (statusCode == 201) {
                            Log.d("data", "onNext: " + loginResponse);
                            Utility.displayToast(AddProfile.this, loginResponse.getMessage());
                            startActivity(new Intent(AddProfile.this, LoginActivity.class));
                        } else {
                            Utility.displayToast(AddProfile.this, loginResponse.getMessage());
                        }
                    }
                });


    }


    public void getCountry() {

        pDialog = new ProgressDialog(this);
        pDialog.setTitle("Checking Data");
        pDialog.setMessage("Please Wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();


        Observable<CountryModel> responseObservable = apiservice.getCountry();
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
                .subscribe(new Observer<CountryModel>() {
                    @Override
                    public void onCompleted() {
                        pDialog.dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error", "" + e.getMessage());
                    }

                    @Override
                    public void onNext(CountryModel countryModel) {
                        statusCode = countryModel.getStatusCode();
                        if (statusCode == 200) {
                            data = countryModel.getData();
                            for (int i = 0; i < countryModel.getData().size(); i++) {
                                countryarray.add(countryModel.getData().get(i).getCountry_name());
                            }

                        } else {
                            Utility.displayToast(AddProfile.this, countryModel.getMessage());
                        }
                    }

                });
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, countryarray);
        dataAdapter.add("Select Contry");
        dataAdapter.setDropDownViewResource(R.layout.spinner_item);
        spnr_contry.setAdapter(dataAdapter);

    }


    public void getState(String c_id) {

        pDialog = new ProgressDialog(this);
        pDialog.setTitle("Checking Data");
        pDialog.setMessage("Please Wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();


        Observable<CountryModel> responseObservable = apiservice.getState(c_id);
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
                .subscribe(new Observer<CountryModel>() {
                    @Override
                    public void onCompleted() {
                        pDialog.dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error", "" + e.getMessage());
                    }

                    @Override
                    public void onNext(CountryModel countryModel) {
                        data1 = countryModel.getData();
                        statusCode = countryModel.getStatusCode();

                        if (statusCode == 200) {
                            for (int i = 0; i < countryModel.getData().size(); i++) {
                                statearray.add(countryModel.getData().get(i).getState_name());
                            }

                        } else {
                            Utility.displayToast(AddProfile.this, countryModel.getMessage());
                        }
                    }
                });
        statearray.clear();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, statearray);
        dataAdapter.add("Select State");
        dataAdapter.setDropDownViewResource(R.layout.spinner_item);
        spnr_state.setAdapter(dataAdapter);
        dataAdapter.notifyDataSetChanged();

    }

    public void getCity(String s_id) {

        pDialog = new ProgressDialog(this);
        pDialog.setTitle("Checking Data");
        pDialog.setMessage("Please Wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();


        Observable<CountryModel> responseObservable = apiservice.getCity(s_id);
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
                .subscribe(new Observer<CountryModel>() {
                    @Override
                    public void onCompleted() {
                        pDialog.dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error", "" + e.getMessage());
                    }

                    @Override
                    public void onNext(CountryModel countryModel) {
                        data2 = countryModel.getData();
                        statusCode = countryModel.getStatusCode();
                        if (statusCode == 200) {
                            for (int i = 0; i < countryModel.getData().size(); i++) {
                                cityarray.add(countryModel.getData().get(i).getCity_name());
                            }

                        } else {
                            Utility.displayToast(AddProfile.this, countryModel.getMessage());
                        }
                    }
                });
        cityarray.clear();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, cityarray);
        dataAdapter.add("Select City");
        dataAdapter.setDropDownViewResource(R.layout.spinner_item);
        spnr_city.setAdapter(dataAdapter);

    }

    public void getUsertype() {

//        pDialog = new ProgressDialog(this);
//        pDialog.setTitle("Checking Data");
//        pDialog.setMessage("Please Wait...");
//        pDialog.setIndeterminate(false);
//        pDialog.setCancelable(false);
//        pDialog.show();


        Observable<CountryModel> responseObservable = apiservice.getUservisetype(sessionManager.getKeyId());
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
                .subscribe(new Observer<CountryModel>() {
                    @Override
                    public void onCompleted() {
//                        pDialog.dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error", "" + e.getMessage());
                    }

                    @Override
                    public void onNext(CountryModel countryModel) {
                        Log.d("data", "onNext: " + countryModel);
                        data3 = countryModel.getData();
                        statusCode = countryModel.getStatusCode();
                        if (statusCode == 200) {
                            for (int i = 0; i < countryModel.getData().size(); i++) {

                                rolearray.add(countryModel.getData().get(i).getRole());
                            }

                        } else {
                            Utility.displayToast(AddProfile.this, countryModel.getMessage());
                        }
                    }
                });
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, rolearray);
        dataAdapter.add("Select UserType");
        dataAdapter.setDropDownViewResource(R.layout.spinner_item);
        spnr_usertype.setAdapter(dataAdapter);
        dataAdapter.notifyDataSetChanged();
    }



}
