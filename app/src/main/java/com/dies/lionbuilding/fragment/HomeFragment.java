package com.dies.lionbuilding.fragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.dies.lionbuilding.R;
import com.dies.lionbuilding.activity.BankManagement.BankView;
import com.dies.lionbuilding.activity.ContactDetail;
import com.dies.lionbuilding.activity.ExpanseManagementActivity;
import com.dies.lionbuilding.activity.LeaveManagementActivity;
import com.dies.lionbuilding.activity.MyProfile;
import com.dies.lionbuilding.activity.ProductCategory;
import com.dies.lionbuilding.activity.RewardActivity;
import com.dies.lionbuilding.activity.RouteManagement.RouteViewActivity;
import com.dies.lionbuilding.activity.ScanBarcodeActivity;
import com.dies.lionbuilding.activity.SendLocation;
import com.dies.lionbuilding.apiservice.ApiService;
import com.dies.lionbuilding.apiservice.ApiServiceCreator;
import com.dies.lionbuilding.application.SessionManager;
import com.dies.lionbuilding.model.UserDataResponse;

import java.net.SocketTimeoutException;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    @BindView(R.id.card_add_profile)
    CardView card_add_profile;
    @BindView(R.id.card_my_profile)
    CardView card_my_profile;
    @BindView(R.id.card_contact)
    CardView card_contact;
    @BindView(R.id.card_add_bank)
    CardView card_add_bank;
    @BindView(R.id.card_shop)
    CardView card_shop;
    @BindView(R.id.card_logout)
    CardView card_logout;
    @BindView(R.id.card_product)
    CardView card_product;
    @BindView(R.id.card_scan_barcode)
    CardView card_scan_barcode;
    @BindView(R.id.card_scan_)
    CardView card_scan_;
    @BindView(R.id.card_reward)
    CardView card_reward;
    @BindView(R.id.card_route)
    CardView card_route;
    @BindView(R.id.card_Leave)
    CardView card_Leave;
    @BindView(R.id.card_expanse)
    CardView card_expanse;
    @BindView(R.id.lnr_shopandproduct)
    LinearLayout lnr_shopandproduct;
    ProgressDialog pDialog;
    ApiService apiservice;
    int statusCode;
    SessionManager sessionManager;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this,view);
        sessionManager=new SessionManager(getActivity());
        apiservice = ApiServiceCreator.createService("latest");
        getUserDetail();

        card_my_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), MyProfile.class));
            }
        });

        card_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ProductCategory.class));
            }
        });

        card_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ContactDetail.class));
            }
        });

        card_scan_barcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getActivity(), ScanBarcodeActivity.class);
                startActivity(i);
            }
        });

        card_scan_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), SendLocation.class);
                startActivity(intent);
            }
        });
        card_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sessionManager.logoutUser();
            }
        });

        card_add_bank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), BankView.class));
            }
        });

        card_reward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), RewardActivity.class));
            }
        });

        card_route.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), RouteViewActivity.class));
            }
        });

        card_Leave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), LeaveManagementActivity.class));
            }
        });
        card_expanse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ExpanseManagementActivity.class));
            }
        });




        return view;
    }

    private void getUserDetail() {

        pDialog = new ProgressDialog(getActivity());
        pDialog.setTitle("Checking Data");
        pDialog.setMessage("Please Wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

        Observable<UserDataResponse> responseObservable = apiservice.getUserDetails(sessionManager.getKeyEmail(), sessionManager.getKeyToken());
        responseObservable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
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
                .subscribe(new Observer<UserDataResponse>() {
                    @Override
                    public void onCompleted() {
                        pDialog.dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error", e.getLocalizedMessage());
                    }

                    @Override
                    public void onNext(UserDataResponse userDataResponse) {
                        statusCode = userDataResponse.getStatusCode();
                        if (statusCode == 200) {
                            sessionManager.setTotalPoint(String.valueOf(userDataResponse.getData().get(0).getTotal_point()));
                            if (userDataResponse.getData().get(0).getUser_type().equalsIgnoreCase("Dealer")){
                                lnr_shopandproduct.setVisibility(View.GONE);
                            }else if (userDataResponse.getData().get(0).getUser_type().equalsIgnoreCase("Sales Executive")){
                                lnr_shopandproduct.setVisibility(View.VISIBLE);
                            }else if (userDataResponse.getData().get(0).getUser_type().equalsIgnoreCase("Carpenter")){
                                lnr_shopandproduct.setVisibility(View.GONE);
                            }

                        }
                    }
                });
    }

}
