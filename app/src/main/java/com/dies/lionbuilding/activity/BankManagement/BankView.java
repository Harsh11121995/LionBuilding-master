package com.dies.lionbuilding.activity.BankManagement;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.dies.lionbuilding.R;
import com.dies.lionbuilding.activity.AddBank;
import com.dies.lionbuilding.adapter.BankListAdapter;
import com.dies.lionbuilding.apiservice.ApiService;
import com.dies.lionbuilding.apiservice.ApiServiceCreator;
import com.dies.lionbuilding.application.SessionManager;
import com.dies.lionbuilding.model.AddBankModel;
import com.github.clans.fab.FloatingActionButton;

import java.net.SocketTimeoutException;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.schedulers.Schedulers;

public class BankView extends AppCompatActivity {

    @BindView(R.id.rv_bank_list)
    RecyclerView rv_bank_list;
    @BindView(R.id.fab_add_bank)
    FloatingActionButton fab_add_bank;
    @BindView(R.id.back_icon)
    ImageView img_back_press;

    @BindView(R.id.toolbar_Title)
    TextView tv_toolbar_title;


    BankListAdapter bankListAdapter;
    int statusCode;
    ProgressDialog pDialog;
    SessionManager sessionManager;
    ApiService apiservice;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_view);
       // getSupportActionBar().hide();

        ButterKnife.bind(this);
        sessionManager = new SessionManager(this);
        apiservice = ApiServiceCreator.createService("latest");

        tv_toolbar_title.setText("Bank");
        bankViewList();

        img_back_press.setOnClickListener(view -> {
            finish();
        });
        fab_add_bank.setOnClickListener(view -> {
            startActivity(new Intent(BankView.this, AddBank.class));
        });
    }

    private void bankViewList() {

        rv_bank_list.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        rv_bank_list.setLayoutManager(layoutManager);
        pDialog = new ProgressDialog(this);
        pDialog.setTitle("Checking Data");
        pDialog.setMessage("Please Wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();


        Observable<AddBankModel> responseObservable = apiservice.getBankDeatil(sessionManager.getKeyId());

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
                .subscribe(new Observer<AddBankModel>() {
                    @Override
                    public void onCompleted() {
                        pDialog.dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error", "" + e.getMessage());
                    }

                    @Override
                    public void onNext(AddBankModel addBankModel) {//
                        statusCode = addBankModel.getStatusCode();
                        if (statusCode == 200) {
                            bankListAdapter = new BankListAdapter(BankView.this,addBankModel.getData());
                            rv_bank_list.setAdapter(bankListAdapter);
                            bankListAdapter.notifyDataSetChanged();

                        }
                    }
                });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
