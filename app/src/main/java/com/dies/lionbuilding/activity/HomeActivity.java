package com.dies.lionbuilding.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dies.lionbuilding.R;
import com.dies.lionbuilding.apiservice.ApiService;
import com.dies.lionbuilding.apiservice.ApiServiceCreator;
import com.dies.lionbuilding.application.SessionManager;
import com.dies.lionbuilding.fragment.DealerFragment;
import com.dies.lionbuilding.fragment.DistributorFragment;
import com.dies.lionbuilding.fragment.RmFragment;
import com.dies.lionbuilding.fragment.SalseExecutiveFragment;

import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView toolbar_Title;
    private DrawerLayout mDrawer;
    private NavigationView nvDrawer;
    ImageView branding_icon;

    ProgressDialog pDialog;
    ApiService apiservice;
    int statusCode;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        sessionManager = new SessionManager(this);
        apiservice = ApiServiceCreator.createService("latest");
        toolbar = findViewById(R.id.toolbar);
        toolbar_Title = toolbar.findViewById(R.id.toolbar_Title);
        toolbar_Title.setText("Dashboard");
        branding_icon = findViewById(R.id.branding_icon);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mDrawer = findViewById(R.id.drawer_layout);
        nvDrawer = findViewById(R.id.nvView);
        setupDrawerContent(nvDrawer);

        branding_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDrawer.isDrawerVisible(GravityCompat.START)) {
                    mDrawer.closeDrawer(GravityCompat.START);
                } else {
                    mDrawer.openDrawer(GravityCompat.START);
                }
            }
        });

        if (findViewById(R.id.contentContainer) != null) {
            if (savedInstanceState != null) {
                return;
            }
            if (sessionManager.getKeyRoll().equals("Dealer")) {
                DealerFragment dealerFragment = new DealerFragment();
                getSupportFragmentManager().beginTransaction().add(R.id.contentContainer, dealerFragment).commit();
            } else if (sessionManager.getKeyRoll().equals("Sales Executive")) {
                SalseExecutiveFragment salseExecutiveFragment = new SalseExecutiveFragment();
                getSupportFragmentManager().beginTransaction().add(R.id.contentContainer, salseExecutiveFragment).commit();
            } else if (sessionManager.getKeyRoll().equals("Distributor")) {
                DistributorFragment distributorFragment = new DistributorFragment();
                getSupportFragmentManager().beginTransaction().add(R.id.contentContainer,distributorFragment).commit();
            } else if (sessionManager.getKeyRoll().equals("RM")) {
                RmFragment rmFragment = new RmFragment();
                getSupportFragmentManager().beginTransaction().add(R.id.contentContainer,rmFragment).commit();

            } else {

            }
        }

    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    public void selectDrawerItem(MenuItem menuItem) {
        Intent i;

        switch (menuItem.getItemId()) {
            case R.id.nav_dashboard:
                i = new Intent(HomeActivity.this, HomeActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                break;
            case R.id.nav_add_customer:
                i = new Intent(HomeActivity.this, MyProfile.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                break;

            case R.id.nav_gift_managment:
                i = new Intent(HomeActivity.this, RewardActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                break;

            case R.id.nav_logout:
                sessionManager.logoutUser();
                i = new Intent(getApplicationContext(), LoginActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                break;
            default:
                //fragmentClass = FirstFragment.class;
        }

        try {
            //   fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        menuItem.setChecked(true);
        // Set action bar title
        setTitle(menuItem.getTitle());
        // Close the navigation drawer
        mDrawer.closeDrawers();
    }


//    private void getUserDetail() {
//
//        pDialog = new ProgressDialog(HomeActivity.this);
//        pDialog.setTitle("Checking Data");
//        pDialog.setMessage("Please Wait...");
//        pDialog.setIndeterminate(false);
//        pDialog.setCancelable(false);
//        pDialog.show();
//
//        Observable<UserDataResponse> responseObservable = apiservice.getUserDetails(sessionManager.getKeyEmail(), sessionManager.getKeyToken());
//        responseObservable.subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .onErrorResumeNext(throwable -> {
//                    if (throwable instanceof retrofit2.HttpException) {
//                        retrofit2.HttpException ex = (retrofit2.HttpException) throwable;
//                        statusCode = ex.code();
//                        Log.e("error", ex.getLocalizedMessage());
//                    } else if (throwable instanceof SocketTimeoutException) {
//                        statusCode = 1000;
//                    }
//                    return Observable.empty();
//                })
//                .subscribe(new Observer<UserDataResponse>() {
//                    @Override
//                    public void onCompleted() {
//                        pDialog.dismiss();
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.e("error", e.getLocalizedMessage());
//                    }
//
//                    @Override
//                    public void onNext(UserDataResponse userDataResponse) {
//                        statusCode = userDataResponse.getStatusCode();
//                        if (statusCode == 200) {
//                            sessionManager.setTotalPoint(String.valueOf(userDataResponse.getData().get(0).getTotal_point()));
//                            if (userDataResponse.getData().get(0).getUser_type().equalsIgnoreCase("Dealer")){
//                                lnr_shopandproduct.setVisibility(View.GONE);
//                            }else if (userDataResponse.getData().get(0).getUser_type().equalsIgnoreCase("Sales Executive")){
//                                lnr_shopandproduct.setVisibility(View.VISIBLE);
//                            }else if (userDataResponse.getData().get(0).getUser_type().equalsIgnoreCase("Carpenter")){
//                                lnr_shopandproduct.setVisibility(View.GONE);
//                            }
//
//                        }
//                    }
//                });
//    }
}
