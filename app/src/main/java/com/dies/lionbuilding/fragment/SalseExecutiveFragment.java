package com.dies.lionbuilding.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dies.lionbuilding.R;
import com.dies.lionbuilding.activity.ExpanseManagementActivity;
import com.dies.lionbuilding.activity.LeaveManagementActivity;
import com.dies.lionbuilding.activity.OrderManagement.OrderActivity;
import com.dies.lionbuilding.activity.ProductCategory;
import com.dies.lionbuilding.activity.RouteManagement.RouteManagementActivity;
import com.dies.lionbuilding.activity.SendLocation;
import com.dies.lionbuilding.activity.Users;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class SalseExecutiveFragment extends Fragment {


    @BindView(R.id.card_add_user)
    CardView card_add_user;
    @BindView(R.id.card_start_day)
    CardView card_start_day;
    @BindView(R.id.card_order)
    CardView card_order;
    @BindView(R.id.card_route)
    CardView card_route;
    @BindView(R.id.card_Leave)
    CardView card_Leave;
    @BindView(R.id.card_expanse)
    CardView card_expanse;



    public SalseExecutiveFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_salse_executive, container, false);
        ButterKnife.bind(this,view);

         checkAndRequestPermissions();
        card_add_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), Users.class));
            }
        });

        card_start_day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), SendLocation.class));
            }
        });

        card_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(getActivity(), ProductCategory.class));
                startActivity(new Intent(getActivity(), OrderActivity.class));
            }
        });

        card_route.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), RouteManagementActivity.class));
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


    private boolean checkAndRequestPermissions() {

        int camera = ContextCompat.checkSelfPermission(getActivity(), "android.permission.CAMERA");
        int writeStorage = ContextCompat.checkSelfPermission(getActivity(), "android.permission.WRITE_EXTERNAL_STORAGE");
        int readStorage = ContextCompat.checkSelfPermission(getActivity(), "android.permission.READ_EXTERNAL_STORAGE");
        int location = ContextCompat.checkSelfPermission(getActivity(), "android.permission.ACCESS_FINE_LOCATION");
        int location1 = ContextCompat.checkSelfPermission(getActivity(), "android.permission.ACCESS_COARSE_LOCATION");

        List<String> listPermissionNeeded = new ArrayList<>();

        if (writeStorage != 0) {
            listPermissionNeeded.add("android.permission.WRITE_EXTERNAL_STORAGE");
        }
        if (readStorage != 0) {
            listPermissionNeeded.add("android.permission.READ_EXTERNAL_STORAGE");
        }
        if (camera != 0) {

            listPermissionNeeded.add("android.permission.CAMERA");
        }
        if (location != 0) {

            listPermissionNeeded.add("android.permission.ACCESS_FINE_LOCATION");
        }
        if (location != 0) {

            listPermissionNeeded.add("android.permission.ACCESS_COARSE_LOCATION");
        }
        if (listPermissionNeeded.isEmpty()) {
            return true;
        }
        ActivityCompat.requestPermissions(getActivity(), listPermissionNeeded.toArray(new String[listPermissionNeeded.size()]), 100);
        return false;
    }
}
