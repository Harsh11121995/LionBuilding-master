package com.dies.lionbuilding.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dies.lionbuilding.R;
import com.dies.lionbuilding.apiservice.ApiService;
import com.dies.lionbuilding.apiservice.ApiServiceCreator;
import com.dies.lionbuilding.application.SessionManager;
import com.dies.lionbuilding.fragment.ContractorFragment;
import com.dies.lionbuilding.fragment.DealerFragment;
import com.dies.lionbuilding.fragment.DistributorFragment;
import com.dies.lionbuilding.fragment.RmFragment;
import com.dies.lionbuilding.fragment.SalseExecutiveFragment;
import com.dies.lionbuilding.model.UserDataResponse;

import java.net.SocketTimeoutException;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class WelcomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Toolbar toolbar;
    SessionManager sessionManager;
    DrawerLayout drawer;
    TextView toolbar_title1, navUsername, navUsertype;
    ApiService apiservice;
    int statusCode;
    NavigationView navigationView;
    int row_index = 0;
    int posofItem = 0;
    private String[] title;
    private Drawable[] icon;
    RecyclerView.Adapter adapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        apiservice = ApiServiceCreator.createService("latest");
        sessionManager = new SessionManager(WelcomeActivity.this);
        toolbar = (Toolbar) findViewById(R.id.toolbar1);
        toolbar_title1 = toolbar.findViewById(R.id.toolbar_Title1);
        toolbar_title1.setText("Dashboard");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //recyclerView = findViewById(R.id.rv_draweritems);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        navigationView = (NavigationView) findViewById(R.id.nvView);
        View headerView = navigationView.getHeaderView(0);
        navUsername = headerView.findViewById(R.id.txt_username);
        navUsertype = headerView.findViewById(R.id.txt_usertype);

        /*icon = loadScreenIcons();
        title = loadScreenTitles();*/

       /* recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerViewAdapter(this, title, icon, posofItem);
        recyclerView.setAdapter(adapter);*/

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
                getSupportFragmentManager().beginTransaction().add(R.id.contentContainer, distributorFragment).commit();
            } else if (sessionManager.getKeyRoll().equals("RM")) {
                RmFragment rmFragment = new RmFragment();
                getSupportFragmentManager().beginTransaction().add(R.id.contentContainer, rmFragment).commit();

            } else if (sessionManager.getKeyRoll().equals("Contractor")) {
                ContractorFragment confgmnt = new ContractorFragment();
                getSupportFragmentManager().beginTransaction().add(R.id.contentContainer, confgmnt).commit();

            } else if (sessionManager.getKeyRoll().equals("Architect")) {
                ContractorFragment confgmnt = new ContractorFragment();
                getSupportFragmentManager().beginTransaction().add(R.id.contentContainer, confgmnt).commit();

            } else {

            }
        }

        getUserDetail();

        final LinearLayout holder = findViewById(R.id.holder);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

                //this code is the real player behind this beautiful ui
                // basically, it's a mathemetical calculation which handles the shrinking of
                // our content view

                float scaleFactor = 10f;
                float slideX = drawerView.getWidth() * slideOffset;

                holder.setTranslationX(slideX);
                holder.setScaleX(1 - (slideOffset / scaleFactor));
                holder.setScaleY(1 - (slideOffset / scaleFactor));

                super.onDrawerSlide(drawerView, slideOffset);
            }
        };
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);// will remove all possible our aactivity's window bounds
        }

        drawer.addDrawerListener(toggle);

        drawer.setScrimColor(Color.TRANSPARENT);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        Intent i;

        if (id == R.id.nav_dashboard) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (id == R.id.nav_add_customer) {
            i = new Intent(WelcomeActivity.this, MyProfile.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
        } else if (id == R.id.nav_gift_managment) {
            i = new Intent(WelcomeActivity.this, RewardActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
        } else if (id == R.id.nav_logout) {
            sessionManager.logoutUser();
            i = new Intent(getApplicationContext(), LoginActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void getUserDetail() {

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

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error", e.getLocalizedMessage());
                    }

                    @Override
                    public void onNext(UserDataResponse userDataResponse) {
                        statusCode = userDataResponse.getStatusCode();
                        if (statusCode == 200) {
                            navUsername.setText(userDataResponse.getData().get(0).getFirstName());
                            navUsertype.setText(userDataResponse.getData().get(0).getUser_type());


                        }
                    }
                });
    }

/*
    private String[] loadScreenTitles() {

        return getResources().getStringArray(R.array.ld_drawerTitles);
    }
*/

/*
    private Drawable[] loadScreenIcons() {

        TypedArray ta = getResources().obtainTypedArray(R.array.ld_drawerIcons);
        Drawable[] icons = new Drawable[ta.length()];
        for (int i = 0; i < ta.length(); i++) {

            int id = ta.getResourceId(i, 0);
            if (id != 0) {
                icons[i] = ContextCompat.getDrawable(this, id);
            }
        }
        ta.recycle();
        return icons;
    }
*/

/*
    public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

        private String[] screenTitles;
        private Drawable[] screenIcons;
        Context context1;

        public RecyclerViewAdapter(Context context2, String[] screenTitles, Drawable[] screenIcons, int posofItem) {

            this.screenTitles = screenTitles;
            this.screenIcons = screenIcons;
            context1 = context2;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            public ImageView icon;
            public TextView title, txtCoin;
            public LinearLayout layoutItem;

            public ViewHolder(View v) {
                super(v);
                icon = (ImageView) v.findViewById(R.id.icon);
                title = (TextView) v.findViewById(R.id.txt_title);
                layoutItem = (LinearLayout) v.findViewById(R.id.layoutItem);
            }
        }

        @Override
        public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view1 = LayoutInflater.from(context1).inflate(R.layout.item_option, parent, false);
            ViewHolder viewHolder1 = new ViewHolder(view1);
            return viewHolder1;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {

            holder.icon.setImageDrawable(screenIcons[position]);

            holder.title.setText(screenTitles[position]);

            holder.layoutItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    row_index = position;
                    Intent i;
                    notifyDataSetChanged();

                    if (position == 0) {

                        holder.title.setTextColor(Color.parseColor("#e63131"));
                        drawer.closeDrawer(GravityCompat.START);
                    } else if (position == 1) {

                        i = new Intent(WelcomeActivity.this, MyProfile.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);

                    } else if (position == 2) {
                        i = new Intent(WelcomeActivity.this, RewardActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);

                    } else if (position == 3) {


                    } else if (position == 4) {

                    } else if (position == 5) {

                        sessionManager.logoutUser();
                        i = new Intent(getApplicationContext(), LoginActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);

                    }
                    drawer.closeDrawer(GravityCompat.START);

                }
            });

        }

        @Override
        public int getItemCount() {

            return screenTitles.length;
        }
    }
*/

}
