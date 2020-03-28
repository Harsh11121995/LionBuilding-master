package com.dies.lionbuilding.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dies.lionbuilding.R;
import com.dies.lionbuilding.adapter.BankListAdapter;
import com.dies.lionbuilding.adapter.UsersListAdapter;
import com.dies.lionbuilding.apiservice.ApiService;
import com.dies.lionbuilding.apiservice.ApiServiceCreator;
import com.dies.lionbuilding.application.SessionManager;
import com.dies.lionbuilding.model.AddBankModel;
import com.dies.lionbuilding.model.NewUserModel;
import com.github.clans.fab.FloatingActionButton;

import java.net.SocketTimeoutException;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.schedulers.Schedulers;

public class Users extends AppCompatActivity {

    @BindView(R.id.rv_users_list)
    RecyclerView rv_users_list;
    @BindView(R.id.fab_add_user)
    FloatingActionButton fab_add_user;
    @BindView(R.id.back_icon)
    ImageView img_back_press;

    @BindView(R.id.toolbar_Title)
    TextView tv_toolbar_title;

    UsersListAdapter usersListAdapter;
    int statusCode;
    ProgressDialog pDialog;
    SessionManager sessionManager;
    ApiService apiservice;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

//        getSupportActionBar().hide();

        ButterKnife.bind(this);
        sessionManager = new SessionManager(this);
        apiservice = ApiServiceCreator.createService("latest");

        tv_toolbar_title.setText("User");

        getUsersList();
        img_back_press.setOnClickListener(view -> {
            finish();
        });
        fab_add_user.setOnClickListener(view -> {
            startActivity(new Intent(Users.this, AddProfile.class));
        });
    }


    private void getUsersList() {

        rv_users_list.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        rv_users_list.setLayoutManager(layoutManager);
        pDialog = new ProgressDialog(this);
        pDialog.setTitle("Checking Data");
        pDialog.setMessage("Please Wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();


        Observable<NewUserModel> responseObservable = apiservice.getUserDetail(sessionManager.getUserData().get(0).getUserId());

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
                            usersListAdapter = new UsersListAdapter(Users.this,newUserModel.getData(),"detail");
                            rv_users_list.setAdapter(usersListAdapter);
                            usersListAdapter.notifyDataSetChanged();



                        }
                    }
                });

    }
}
