package com.dies.lionbuilding.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.dies.lionbuilding.R;
import com.dies.lionbuilding.apiservice.ApiService;
import com.dies.lionbuilding.apiservice.ApiServiceCreator;
import com.dies.lionbuilding.application.SessionManager;
import com.dies.lionbuilding.model.NewUserModel;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.schedulers.Schedulers;

public class SelectDealerActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{

    ListView listView;
    SessionManager sessionManager;
    ApiService apiservice;
    ProgressDialog pDialog;
    int statusCode;
    String[] array;
    ArrayList<String> arrayList_user = new ArrayList<>();
    ArrayAdapter<String> adapter;
    String animal_id;



    @BindView(R.id.toolbar_Title)
    TextView toolbar_Title;
    @BindView(R.id.notify_icon)
    ImageView notify_icon;
    //    @BindView(R.id.cart_icon)
//    ImageView cart_icon;
    @BindView(R.id.back_icon)
    ImageView backIcon;

    List<NewUserModel.Data> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_dealer);

        ButterKnife.bind(this);
        listView=findViewById(R.id.list_breed);
        sessionManager = new SessionManager(this);
        apiservice = ApiServiceCreator.createService("latest");
        SearchView simpleSearchView = (SearchView) findViewById(R.id.simpleSearchView);
        toolbar_Title.setText("Breed");
        animal_id=getIntent().getStringExtra("animal_id");
        notify_icon.setVisibility(View.GONE);
        backIcon.setVisibility(View.VISIBLE);
        backIcon.setOnClickListener(view -> {
            Intent returnIntent = new Intent();
            setResult(Activity.RESULT_CANCELED, returnIntent);
            finish();
        });

        getUsersList();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String str=(String)adapterView.getItemAtPosition(i);
                NewUserModel.Data datalist = data.get(i);
                String u_id=datalist.getUserId();
                Intent intent = new Intent();
                intent.putExtra("data",str);
                intent.putExtra("user_id",u_id);
                setResult(RESULT_OK,intent);
                finish();
            }
        });

        simpleSearchView.setOnQueryTextListener(this);

    }


    private void getUsersList() {

        pDialog = new ProgressDialog(this);
        pDialog.setTitle("Checking Data");
        pDialog.setMessage("Please Wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();


        Observable<NewUserModel> responseObservable = apiservice.getDealer();

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
                            List<NewUserModel.Data> res = newUserModel.getData();
                            data = newUserModel.getData();
                            for (int i = 0; i < res.size(); i++) {
                                arrayList_user.add(res.get(i).getFirstName());
                            }
                            array = arrayList_user.toArray(new String[0]);
                            adapter = new ArrayAdapter<String>(SelectDealerActivity.this, android.R.layout.simple_list_item_1, array);
                            //  country.setDropDownViewResource(R.layout.spinner_item);
                            listView.setAdapter(adapter);
                        }
                    }
                });

    }


    @Override
    public void onBackPressed() {
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_CANCELED, returnIntent);
        finish();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String text=newText;
        adapter.getFilter().filter(newText);
        return false;
    }

}
