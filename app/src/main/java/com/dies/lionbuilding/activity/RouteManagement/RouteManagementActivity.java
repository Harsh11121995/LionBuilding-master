package com.dies.lionbuilding.activity.RouteManagement;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dies.lionbuilding.R;
import com.dies.lionbuilding.ShopTimelineViewActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RouteManagementActivity extends AppCompatActivity {


    @BindView(R.id.lnr_today_route)
    LinearLayout lnr_today_route;

    @BindView(R.id.lnr_c_route)
    LinearLayout lnr_c_route;

    @BindView(R.id.lnr_past_route)
    LinearLayout lnr_past_route;

    @BindView(R.id.lnr_future_route)
    LinearLayout lnr_future_route;

    @BindView(R.id.back_icon)
    ImageView back;

    @BindView(R.id.toolbar_Title)
    TextView toolbar_Title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_management);
        ButterKnife.bind(this);

        toolbar_Title.setText("Route Management");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        lnr_today_route.setOnClickListener(view -> {
            Intent intent = new Intent(this, ShopTimelineViewActivity.class);
            startActivity(intent);
        });

        lnr_c_route.setOnClickListener(view -> {
            Intent intent = new Intent(this, CreateRouteActivity.class);
            startActivity(intent);
        });

        lnr_past_route.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RouteManagementActivity.this, PastRouteActivity.class);
                startActivity(intent);
            }
        });

        lnr_future_route.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RouteManagementActivity.this, FutureRouteActivity.class);
                startActivity(intent);
            }
        });
    }
}
