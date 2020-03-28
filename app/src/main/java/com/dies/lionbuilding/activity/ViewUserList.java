package com.dies.lionbuilding.activity;

import android.app.ProgressDialog;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;

import com.dies.lionbuilding.R;
import com.dies.lionbuilding.adapter.UsersListAdapter;
import com.dies.lionbuilding.apiservice.ApiService;
import com.dies.lionbuilding.apiservice.ApiServiceCreator;
import com.dies.lionbuilding.application.SessionManager;
import com.dies.lionbuilding.model.NewUserModel;

import java.net.SocketTimeoutException;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.schedulers.Schedulers;

public class ViewUserList extends AppCompatActivity {

    @BindView(R.id.rcv_userlist)
    RecyclerView rcv_userlist;
    @BindView(R.id.back_icon)
    ImageView back_icon;



    UsersListAdapter usersListAdapter;
    int statusCode;
    ProgressDialog pDialog;
    SessionManager sessionManager;
    ApiService apiservice;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user_list);
        ButterKnife.bind(this);
        sessionManager=new SessionManager(this);
        apiservice = ApiServiceCreator.createService("latest");

        back_icon.setOnClickListener(view -> {
            finish();
        });

        getUsersList();


    }

    private void getUsersList() {

        rcv_userlist.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        rcv_userlist.setLayoutManager(layoutManager);
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
                            usersListAdapter = new UsersListAdapter(ViewUserList.this,newUserModel.getData(),"scan");
                            rcv_userlist.setAdapter(usersListAdapter);
                            usersListAdapter.notifyDataSetChanged();
                        }
                    }
                });

    }
}
