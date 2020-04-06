package com.dies.lionbuilding.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.dies.lionbuilding.R;
import com.dies.lionbuilding.apiservice.ApiService;
import com.dies.lionbuilding.apiservice.ApiServiceCreator;
import com.dies.lionbuilding.application.SessionManager;
import com.dies.lionbuilding.application.Utility;
import com.dies.lionbuilding.model.CountryModel;
import com.dies.lionbuilding.model.LoginResponse;
import com.dies.lionbuilding.model.WardModel;
import com.dies.lionbuilding.model.ZoneModel;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.schedulers.Schedulers;

public class RegisterActivity extends AppCompatActivity {
    public static final int REQUEST_CODE = 100;
    final Calendar myCalendar = Calendar.getInstance();
    //    @BindView(R.id.edt_Dob)
//    EditText edt_Dob;
    @BindView(R.id.edtfirstname)
    EditText edt_firstname;
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
    @BindView(R.id.edt_city)
    EditText edt_city;
    @BindView(R.id.edt_pincode)
    EditText edt_pincode;
    @BindView(R.id.edt_distibutor)
    EditText edt_distributor;
    @BindView(R.id.edt_gst)
    EditText edt_gst;
    String dataa = null;
    @BindView(R.id.rg1)
    RadioGroup rg1;
    @BindView(R.id.rd1)
    RadioButton rd1;
    @BindView(R.id.rd2)
    RadioButton rd2;
    @BindView(R.id.rd3)
    RadioButton rd3;
    @BindView(R.id.rd4)
    RadioButton rd4;
    @BindView(R.id.ll_gst)
    LinearLayout ll_gst;

    @BindView(R.id.ll_distributor)
    LinearLayout ll_distributor;

    @BindView(R.id.ll_zone)
    LinearLayout ll_zone;

    //    @BindView(R.id.spnr_city)
//    Spinner spnr_city;
//    @BindView(R.id.spnr_usertype)
//    Spinner spnr_usertype;
//    @BindView(R.id.spnr_userzone)
//    Spinner spnr_userzone;
//    @BindView(R.id.spnr_userdistrict)
//    Spinner spnr_userdistrict;
//    @BindView(R.id.lnr_spnrzone)
//    LinearLayout lnr_spnrzone;
//    @BindView(R.id.lnr_spnrdistrict)
//    LinearLayout lnr_spnrdistrict;

    //    @BindView(R.id.spnr_contry)
//    Spinner spnr_contry;
    @BindView(R.id.spnr_state)
    Spinner spnr_state;
    String contry = "India";
    String Contry, City, State, user_type;
    String[] users = {"Dealer", "Carpenter", "Sales Executive"};
    int statusCode;
    int count = 0;
    List<ZoneModel.Data> zoneList;
    List<WardModel.Data> wardList;
    String zone, wrd;
    String z_id, wrd_id;
    RadioButton rb1, rb2, rb3, rb4;
    String rbtn1, rbtn2, rbtn3;
    String c_id = "1", s_id, city_id, roll_id;
    String uid;
    List<CountryModel.Data> data, data1, data2, data3;
    ArrayList<String> countryarray = new ArrayList<>();
    ArrayList<String> statearray = new ArrayList<>();
    ArrayList<String> cityarray = new ArrayList<>();
    ArrayList<String> rolearray = new ArrayList<>();
    ArrayList<String> zoneArray = new ArrayList<>();
    ArrayList<String> wrdArray = new ArrayList<>();
    private ApiService apiservice;
    private SessionManager sessionManager = null;
    private ProgressDialog pDialog;
    @BindView(R.id.spnr_zone)
    Spinner spnr_zone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        apiservice = ApiServiceCreator.createService("latest");
        userTypeAdapter userTypeAdapter = new userTypeAdapter(RegisterActivity.this, android.R.layout.simple_list_item_1);
        userTypeAdapter.addAll(users);
        userTypeAdapter.add("Select User Type");
//        spnr_usertype.setAdapter(userTypeAdapter);
//        spnr_usertype.setSelection(userTypeAdapter.getCount());
        getState("1");
        // getUsertype();

        getZone();


        ll_zone.setVisibility(View.GONE);

        spnr_zone.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                TextView textView = (TextView)adapterView.getChildAt(0);
//                textView.setTextColor(getResources().getColor(R.color.colorblack));
                if (spnr_zone.getSelectedItem() == "Select Zone") {
                    zone = "";
                    //Utility.displayToast(RegisterActivity.this, "not data selected");
                    //Do nothing.
                } else {

                    Utility.displayToast(RegisterActivity.this, "selected");
                    zone = spnr_zone.getSelectedItem().toString();
                    ZoneModel.Data datalist = zoneList.get(i - 1);
                    z_id = datalist.getZn_id();
                    Log.e("zone_id", z_id);
                    getWrdId(z_id);

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
                    // getCity(s_id);
                    //Toast.makeText(MainActivity.this, spinner.getSelectedItem().toString(), Toast.LENGTH_LONG).show();
                }

            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        edt_distributor.setOnClickListener(view -> {
            Intent dateintent = new Intent(this, SelectDistributor.class);
            startActivityForResult(dateintent, REQUEST_CODE);
        });


        rg1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int id = rg1.getCheckedRadioButtonId();
                switch (id) {
                    case R.id.rd1:
                        ll_distributor.setVisibility(View.VISIBLE);
                        ll_zone.setVisibility(View.GONE);
                        break;
                    case R.id.rd2:
                        ll_distributor.setVisibility(View.GONE);
                        ll_zone.setVisibility(View.GONE);
                        break;
                    case R.id.rd3:
                        ll_distributor.setVisibility(View.GONE);
                        ll_zone.setVisibility(View.GONE);
                        break;
                    case R.id.rd4:
                        ll_distributor.setVisibility(View.GONE);
                        ll_zone.setVisibility(View.VISIBLE);

                        break;
                    default:
                        break;
                }
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        dataa = data.getStringExtra("data");
        uid = data.getStringExtra("user_id");
        Utility.getAppcon().getSession().dealer_id = uid;
//        Log.e("usr_id",uid);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && dataa != null) {
            edt_distributor.setText(dataa);
        }
    }

    @OnClick(R.id.btn_login)
    public void btn_login() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn_register)
    public void btn_register() {
        if (edt_firstname.getText().toString().equals("")) {
            Utility.displayToast(RegisterActivity.this, "Please enter First Name");
        } else if (edt_address1.getText().toString().equals("")) {
            Utility.displayToast(RegisterActivity.this, "Please enter Address");
        } else if (edt_mobileNo.equals("")) {
            Utility.displayToast(RegisterActivity.this, "Please enter Mobile Number");
        } else if (edt_password.getText().toString().equals("")) {
            Utility.displayToast(RegisterActivity.this, "Please enter Password");
        } else {

            if (rg1.getCheckedRadioButtonId() == -1) {
                // no radio buttons are checked
                Utility.displayToast(this, "Please Select Your Roll");
            } else {
                int selectedId = rg1.getCheckedRadioButtonId();
                rb1 = (RadioButton) findViewById(selectedId);
                rb2 = (RadioButton) findViewById(selectedId);
                rb3 = (RadioButton) findViewById(selectedId);
                rb4 = (RadioButton) findViewById(selectedId);
                if (rb1.getText().toString().equals("Dealer")) {
                    ll_distributor.setVisibility(View.VISIBLE);
                    roll_id = "2";
                } else if (rb2.getText().toString().equals("Contractor")) {
                    ll_distributor.setVisibility(View.GONE);
                    roll_id = "3";
                } else if (rb3.getText().toString().equals("Distributor")) {
                    ll_distributor.setVisibility(View.GONE);
                    roll_id = "4";
                } else if (rb4.getText().toString().equals("RM")) {
                    ll_distributor.setVisibility(View.GONE);
                    roll_id = "6";
                } else {
                    ll_distributor.setVisibility(View.GONE);
                    roll_id = "5";
                }
                // one of the radio buttons is checked
                RegisterUser();
            }

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

    private void RegisterUser() {
        pDialog = new ProgressDialog(this);
        pDialog.setTitle("Checking Data");
        pDialog.setMessage("Please Wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

        Observable<LoginResponse> responseObservable = apiservice.registerUser(
                edt_firstname.getText().toString(),
                edt_mobileNo.getText().toString(),
                edt_gst.getText().toString(),
                edt_password.getText().toString(),
                edt_address1.getText().toString(),
                s_id,
                edt_city.getText().toString(),
                edt_pincode.getText().toString(),
                roll_id, uid, z_id
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
                            Utility.displayToast(RegisterActivity.this, loginResponse.getMessage());
                            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                        } else {
                            Utility.displayToast(RegisterActivity.this, loginResponse.getMessage());
                        }
                    }
                });


    }

//    private void updateLabel() {
//        String myFormat = "MM/dd/yyyy"; //In which you need put here
//        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
//        edt_Dob.setText(sdf.format(myCalendar.getTime()));
//    }

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
                            Utility.displayToast(RegisterActivity.this, countryModel.getMessage());
                        }
                    }

                });
//        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, countryarray);
//        dataAdapter.add("Select Contry");
//        dataAdapter.setDropDownViewResource(R.layout.spinner_item);
//        spnr_contry.setAdapter(dataAdapter);

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
                            Utility.displayToast(RegisterActivity.this, countryModel.getMessage());
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

    public void getZone() {
      /*  pDialog = new ProgressDialog(this);
        pDialog.setTitle("Checking Data");
        pDialog.setMessage("Please Wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();*/


        Observable<ZoneModel> responseObservable = apiservice.getZone();
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
                .subscribe(new Observer<ZoneModel>() {
                    @Override
                    public void onCompleted() {
                        pDialog.dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error", "" + e.getMessage());
                    }

                    @Override
                    public void onNext(ZoneModel zoneModel) {
                        statusCode = zoneModel.getStatusCode();
                        if (statusCode == 200) {
                            zoneList = zoneModel.getData();

                            for (int i = 0; i < zoneModel.getData().size(); i++) {
                                zoneArray.add(zoneModel.getData().get(i).getZn_name());
                            }

                        } else {
                            Utility.displayToast(RegisterActivity.this, zoneModel.getMessage());
                        }
                    }

                });
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, zoneArray);
        dataAdapter.add("Select Zone");
        dataAdapter.setDropDownViewResource(R.layout.spinner_item);
        spnr_zone.setAdapter(dataAdapter);

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
                            Utility.displayToast(RegisterActivity.this, countryModel.getMessage());
                        }
                    }
                });
        cityarray.clear();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, cityarray);
        dataAdapter.add("Select City");
        dataAdapter.setDropDownViewResource(R.layout.spinner_item);
        //  spnr_city.setAdapter(dataAdapter);

    }

    public void getUsertype() {

//        pDialog = new ProgressDialog(this);
//        pDialog.setTitle("Checking Data");
//        pDialog.setMessage("Please Wait...");
//        pDialog.setIndeterminate(false);
//        pDialog.setCancelable(false);
//        pDialog.show();


        Observable<CountryModel> responseObservable = apiservice.getUsertype();
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
                            Utility.displayToast(RegisterActivity.this, countryModel.getMessage());
                        }
                    }
                });
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, rolearray);
        dataAdapter.add("Select UserType");
        dataAdapter.setDropDownViewResource(R.layout.spinner_item);
        //  spnr_usertype.setAdapter(dataAdapter);
        dataAdapter.notifyDataSetChanged();
    }


    public void getWrdId(String z_id) {

        pDialog = new ProgressDialog(this);
        pDialog.setTitle("Checking Data");
        pDialog.setMessage("Please Wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();


        Observable<WardModel> responseObservable = apiservice.getZoneWardd(z_id);
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
                .subscribe(new Observer<WardModel>() {
                    @Override
                    public void onCompleted() {
                        pDialog.dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error", "" + e.getMessage());
                    }

                    @Override
                    public void onNext(WardModel zoneModel) {
                        statusCode = zoneModel.getStatusCode();
                        if (statusCode == 200) {
                            wardList = zoneModel.getData();
                            Log.d("getWrdId", "onNext: " + zoneList);

                            for (int i = 0; i < zoneModel.getData().size(); i++) {
                                wrdArray.add(zoneModel.getData().get(i).getWrd_name());
                            }

                        } else {
                            Utility.displayToast(RegisterActivity.this, zoneModel.getMessage());
                        }
                    }

                });
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, wrdArray);
        dataAdapter.add("Select ward");
        dataAdapter.setDropDownViewResource(R.layout.spinner_item);
        //spnr_userdistrict.setAdapter(dataAdapter);

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


}

//        spnr_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//
//                if (spnr_city.getSelectedItem() == "Select City") {
//                    City = "";
//                    //Do nothing.
//                } else {
//                    City = spnr_city.getSelectedItem().toString();
//                    CountryModel.Data datalist = data2.get(i - 1);
//                    city_id = datalist.getCity_id();
//                    //Toast.makeText(MainActivity.this, spinner.getSelectedItem().toString(), Toast.LENGTH_LONG).show();
//                }
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });


//        spnr_usertype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                if (spnr_usertype.getSelectedItem() == "Select UserType") {
//                    user_type = "";
//
//                } else {
//                    user_type = spnr_usertype.getSelectedItem().toString();
//                    CountryModel.Data datalist = data3.get(i - 1);
//                    if (user_type.equals("Sales Executive")){
//                        getZone();
//                        lnr_spnrzone.setVisibility(View.VISIBLE);
//                        lnr_spnrdistrict.setVisibility(View.VISIBLE);
//
//                    }else {
//                        lnr_spnrzone.setVisibility(View.GONE);
//                        lnr_spnrdistrict.setVisibility(View.GONE);
//                    }
//
//                    roll_id = datalist.getRoleId();
//                   // Log.d("dataList", "onItemSelected: " + roll_id);
//
//                    //Toast.makeText(RegisterActivity.this, spnr_usertype.getSelectedItem().toString(), Toast.LENGTH_LONG).show();
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });


//        spnr_userzone.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                TextView textView = (TextView)adapterView.getChildAt(0);
//                textView.setTextColor(getResources().getColor(R.color.colorblack));
//                if (spnr_userzone.getSelectedItem() == "Select Zone") {
//                    zone = "";
//                    //Do nothing.
//                } else {
//                    zone = spnr_userzone.getSelectedItem().toString();
//                    ZoneModel.Data datalist = zoneList.get(i - 1);
//                    z_id = datalist.getZn_id();
//                    Log.e("state_id", z_id);
//                    getWrdId(z_id);
//
//                    //Toast.makeText(MainActivity.this, spinner.getSelectedItem().toString(), Toast.LENGTH_LONG).show();
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });
//        spnr_userdistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                if (spnr_userdistrict.getSelectedItem() == "Select ward") {
//                    wrd = "";
//                    //Do nothing.
//                } else {
//                    wrd = spnr_userdistrict.getSelectedItem().toString();
//                    WardModel.Data datalist = wardList.get(i - 1);
//                    wrd_id = datalist.getWrd_id();
//
//                    //Toast.makeText(MainActivity.this, spinner.getSelectedItem().toString(), Toast.LENGTH_LONG).show();
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });

//        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
//
//            @Override
//            public void onDateSet(DatePicker view, int year, int monthOfYear,
//                                  int dayOfMonth) {
//                // TODO Auto-generated method stub
//                myCalendar.set(Calendar.YEAR, year);
//                myCalendar.set(Calendar.MONTH, monthOfYear);
//                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
////                updateLabel();
//            }
//
//        };
//        edt_Dob.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//                new DatePickerDialog(RegisterActivity.this, date, myCalendar
//                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
//                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
//            }
//        });
//        spnr_contry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                if (spnr_contry.getSelectedItem() == "Select Contry") {
//                    Contry = "";
//                    //Do nothing.
//                } else {
//                    Contry = spnr_contry.getSelectedItem().toString();
//                    CountryModel.Data datalist = data.get(i - 1);
//                    c_id = datalist.getCountry_id();
//
//                    Log.e("state_id", c_id);
//                    getState(c_id);
//
//                    //Toast.makeText(MainActivity.this, spinner.getSelectedItem().toString(), Toast.LENGTH_LONG).show();
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });