package com.dies.lionbuilding.activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.dies.lionbuilding.R;
import com.dies.lionbuilding.adapter.LeaveAdapter;
import com.dies.lionbuilding.apiservice.ApiService;
import com.dies.lionbuilding.apiservice.ApiServiceCreator;
import com.dies.lionbuilding.application.SessionManager;
import com.dies.lionbuilding.application.Utility;
import com.dies.lionbuilding.model.CountryModel;
import com.dies.lionbuilding.model.ExpanseModel;
import com.dies.lionbuilding.model.LeaveModel;

import java.io.File;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observable;
import rx.Observer;
import rx.schedulers.Schedulers;

public class AddExpanseActivity extends AppCompatActivity {


    SessionManager sessionManager;
    ApiService apiservice;
    ProgressDialog pDialog;
    RecyclerView.LayoutManager layoutManager;
    int statusCode;

    @BindView(R.id.et_date)
    EditText et_date;
    @BindView(R.id.et_expanse_amount)
    EditText et_expanse_amount;
    @BindView(R.id.et_expanse_detail)
    EditText et_expanse_detail;
    @BindView(R.id.toolbar_Title)
    TextView toolbar_Title;
    // Calendar date;
    final Calendar myCalendar = Calendar.getInstance();

    String mediaPath;

    @BindView(R.id.spin_type)
    Spinner spin_type;

    @BindView(R.id.img_doc)
    ImageView img_doc;

    @BindView(R.id.btn_submit)
    Button btn_submit;

    @BindView(R.id.back_icon)
    ImageView back;

    ArrayList<String> expansearray = new ArrayList<>();
    List<ExpanseModel.Data> data;
    String Expanse, e_id;

    private AwesomeValidation awesomeValidation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expanse);
        ButterKnife.bind(this);
        sessionManager = new SessionManager(this);
        apiservice = ApiServiceCreator.createService("latest");

        toolbar_Title.setText("Add Expense");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        img_doc.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, 1);
        });


        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

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
        et_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(AddExpanseActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        awesomeValidation.addValidation(this, R.id.et_date, "^(?:(?:31(\\\\/|-|\\\\.)(?:0?[13578]|1[02]))\\\\1|(?:(?:29|30)(\\\\/|-|\\\\.)(?:0?[1,3-9]|1[0-2])\\\\2))(?:(?:1[6-9]|[2-9]\\\\d)?\\\\d{2})$|^(?:29(\\\\/|-|\\\\.)0?2\\\\3(?:(?:(?:1[6-9]|[2-9]\\\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\\\d|2[0-8])(\\\\/|-|\\\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\\\4(?:(?:1[6-9]|[2-9]\\\\d)?\\\\d{2})$", R.string.dateerror);

        getExpansivetype();

        btn_submit.setOnClickListener(view -> {

            if (et_date.getText().toString().equals("")) {
                et_date.setError("please Enter date");
            } else if (et_expanse_amount.getText().toString().equals("")) {
                et_expanse_amount.setError("Please Enter Expanse");
            } else if (et_expanse_detail.getText().toString().equals("")) {
                et_expanse_detail.setError("Please Enter Expanse Detail");
            } else {
                AddExpanse();

            }


        });


        spin_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (spin_type.getSelectedItem() == "Select type") {
                    Expanse = "";
                    //Do nothing.
                } else {
                    Expanse = spin_type.getSelectedItem().toString();
                    ExpanseModel.Data datalist = data.get(i - 1);
                    e_id = datalist.getExpt_id();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void updateLabel() {
        String myFormat = "dd-MM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        et_date.setText(sdf.format(myCalendar.getTime()));
    }


    public void getExpansivetype() {

        pDialog = new ProgressDialog(this);
        pDialog.setTitle("Checking Data");
        pDialog.setMessage("Please Wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

        Observable<ExpanseModel> responseObservable = apiservice.getExpanseType();

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
                .subscribe(new Observer<ExpanseModel>() {
                    @Override
                    public void onCompleted() {
                        pDialog.dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error", "" + e.getMessage());
                    }

                    @Override
                    public void onNext(ExpanseModel expanseModel) {
                        statusCode = expanseModel.getStatusCode();
                        if (statusCode == 200) {
                            data = expanseModel.getData();
                            for (int i = 0; i < expanseModel.getData().size(); i++) {
                                expansearray.add(expanseModel.getData().get(i).getExpt_name());
                            }
                        }
                    }
                });

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, expansearray);
        dataAdapter.add("Select type");
        dataAdapter.setDropDownViewResource(R.layout.spinner_item);
        spin_type.setAdapter(dataAdapter);
    }


    public void AddExpanse() {

        if (mediaPath == null) {
            mediaPath = "";
        }

        Map<String, RequestBody> map = new HashMap<>();
        Compressor compressedImageFile = new Compressor(this);
        compressedImageFile.setQuality(60);
        if (!mediaPath.equals("")) {
            File file = new File(mediaPath);
            File compressfile = null;
            try {
                compressfile = compressedImageFile.compressToFile(file);
                RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), compressfile);
                map.put("exp_images\"; filename=\"" + file.getName() + "\"", requestBody);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {

            RequestBody ex_date = RequestBody.create(MediaType.parse("text/plain"), et_date.getText().toString());
            RequestBody ex_detail = RequestBody.create(MediaType.parse("text/plain"), et_expanse_detail.getText().toString());
            RequestBody ex_amount = RequestBody.create(MediaType.parse("text/plain"), et_expanse_amount.getText().toString());
            RequestBody ex_id = RequestBody.create(MediaType.parse("text/plain"), e_id);
            RequestBody ex_user_id = RequestBody.create(MediaType.parse("text/plain"), sessionManager.getKeyId());


            map.put("exp_date", ex_date);
            map.put("exp_desc", ex_detail);
            map.put("exp_amount", ex_amount);
            map.put("exp_type", ex_id);
            map.put("exp_user_id", ex_user_id);


            pDialog = new ProgressDialog(this);
            pDialog.setTitle("Checking Data");
            pDialog.setMessage("Please Wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();

            Observable<ExpanseModel> responseObservable = apiservice.addExpanse(map);

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
                    .subscribe(new Observer<ExpanseModel>() {
                        @Override
                        public void onCompleted() {
                            pDialog.dismiss();
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e("error", "" + e.getMessage());
                        }

                        @Override
                        public void onNext(ExpanseModel expanseModel) {
                            statusCode = expanseModel.getStatusCode();
                            if (statusCode == 200) {
                                Utility.displayToast(AddExpanseActivity.this, expanseModel.getMessage());
                                finish();
                            } else {
                                Utility.displayToast(AddExpanseActivity.this, expanseModel.getMessage());
                            }
                        }
                    });

        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && null != data) {
            //selectedImage = data.getData();
//            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = null;
            if (selectedImage != null) {
                cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            }
            assert cursor != null;
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            //filePath1 = cursor.getString(column_index);
            mediaPath = cursor.getString(columnIndex);
            // mediaPath=compressImage(mediaPath1);
            img_doc.setImageBitmap(BitmapFactory.decodeFile(mediaPath));
            cursor.close();
        }
    }


}
