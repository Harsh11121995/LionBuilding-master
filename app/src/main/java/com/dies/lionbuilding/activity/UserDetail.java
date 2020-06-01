package com.dies.lionbuilding.activity;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.dies.lionbuilding.R;
import com.dies.lionbuilding.adapter.UsersListAdapter;
import com.dies.lionbuilding.apiservice.ApiService;
import com.dies.lionbuilding.apiservice.ApiServiceCreator;
import com.dies.lionbuilding.application.SessionManager;
import com.dies.lionbuilding.application.Utility;
import com.dies.lionbuilding.model.NewUserModel;

import java.net.SocketTimeoutException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.schedulers.Schedulers;

public class UserDetail extends AppCompatActivity {

    int statusCode;
    ProgressDialog pDialog;
    SessionManager sessionManager;
    ApiService apiservice;
    RecyclerView.LayoutManager layoutManager;
    List<NewUserModel.Data> arrayList;

    @BindView(R.id.txt_usertype)
    TextView txt_usertype;
    @BindView(R.id.txt_email)
    TextView txt_email;
    @BindView(R.id.txt_address)
    TextView txt_address;
    @BindView(R.id.txt_dob)
    TextView txt_dob;
    @BindView(R.id.txt_mobile)
    TextView txt_mobile;
    @BindView(R.id.txt_state)
    TextView txt_state;
    @BindView(R.id.txt_city)
    TextView txt_city;
    @BindView(R.id.txt_name)
    TextView txt_name;
    @BindView(R.id.back_icon)
    ImageView back_icon;
    @BindView(R.id.toolbar_Title)
    TextView tv_toolbar_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);

//        getSupportActionBar().hide();

        ButterKnife.bind(this);
        sessionManager = new SessionManager(this);
        apiservice = ApiServiceCreator.createService("latest");

        tv_toolbar_title.setText("Users");
        arrayList = Utility.getAppcon().getSession().arrayListUser;

        back_icon.setOnClickListener(view -> {
            finish();
        });

        getUsersDetail();

    }

    private void getUsersDetail() {

        pDialog = new ProgressDialog(this);
        pDialog.setTitle("Checking Data");
        pDialog.setMessage("Please Wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();


        Log.e("TAG", "userid: " + arrayList.get(0).getUserId());
        Observable<NewUserModel> responseObservable = apiservice.getUserDetail(arrayList.get(0).getUserId());

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
                .subscribe(new Observer<NewUserModel>() {
                    @Override
                    public void onCompleted() {
                        pDialog.dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error", "" + e.getMessage());
                    }

                    @Override
                    public void onNext(NewUserModel newUserModel) {//
                        statusCode = newUserModel.getStatusCode();
                        if (statusCode == 200) {
                            txt_name.setText(newUserModel.getData().get(0).getFirstName());
                            txt_usertype.setText(newUserModel.getData().get(0).getUsertype());
                            txt_email.setText(newUserModel.getData().get(0).getEmail());
                            txt_address.setText(newUserModel.getData().get(0).getAddress_line1());
                            txt_city.setText(newUserModel.getData().get(0).getCity_name());
                            txt_dob.setText(newUserModel.getData().get(0).getDob());
                            txt_mobile.setText(newUserModel.getData().get(0).getMobile());
                            txt_state.setText(newUserModel.getData().get(0).getState_name());
                            //tv_toolbar_title.setText(newUserModel.getData().get(0).getFirstName());

                            //txt_name.setText(newUserModel.getData().get(0).getFirstName());
                        }
                    }
                });

    }
}
