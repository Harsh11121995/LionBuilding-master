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
import com.dies.lionbuilding.adapter.LeaveAdapter;
import com.dies.lionbuilding.apiservice.ApiService;
import com.dies.lionbuilding.apiservice.ApiServiceCreator;
import com.dies.lionbuilding.application.SessionManager;
import com.dies.lionbuilding.model.LeaveModel;
import com.github.clans.fab.FloatingActionButton;
import java.net.SocketTimeoutException;
import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.schedulers.Schedulers;

public class LeaveManagementActivity extends AppCompatActivity {


    SessionManager sessionManager;
    ApiService apiservice;
    ProgressDialog pDialog;
    RecyclerView.LayoutManager layoutManager;
    int statusCode;

    @BindView(R.id.fab_add_leave)
    FloatingActionButton fab_add_leave;
    @BindView(R.id.rcv_leave)
    RecyclerView rcv_leave;

    @BindView(R.id.back_icon)
    ImageView back;

    @BindView(R.id.toolbar_Title)
    TextView toolbar_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_management);
        ButterKnife.bind(this);
        sessionManager = new SessionManager(this);
        apiservice = ApiServiceCreator.createService("latest");
        toolbar_title.setText("Leave Details");
        fab_add_leave.setOnClickListener(view -> {
            Intent intent=new Intent(LeaveManagementActivity.this,AddLeave.class);
            startActivity(intent);
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        getAllLeave();
    }



    public void getAllLeave(){
        rcv_leave.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        rcv_leave.setLayoutManager(layoutManager);
        pDialog = new ProgressDialog(this);
        pDialog.setTitle("Checking Data");
        pDialog.setMessage("Please Wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

        Observable<LeaveModel> responseObservable = apiservice.getAllLeave(sessionManager.getKeyId());

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
                .subscribe(new Observer<LeaveModel>() {
                    @Override
                    public void onCompleted() {
                        pDialog.dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error", "" + e.getMessage());
                    }

                    @Override
                    public void onNext(LeaveModel leaveModel) {
                        statusCode = leaveModel.getStatusCode();
                        if (statusCode == 200) {
                            LeaveAdapter leaveAdapter=new LeaveAdapter(LeaveManagementActivity.this,leaveModel.getData()) ;
                            rcv_leave.setAdapter(leaveAdapter);
                        }
                    }
                });
    }


//    @Override
//    protected void onResume() {
//        super.onResume();
//        getAllLeave();
//    }
}
