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
import com.dies.lionbuilding.activity.OrderManagement.OrderConfirmActivity;
import com.dies.lionbuilding.activity.OrderManagement.SalesExecOrderActivity;
import com.dies.lionbuilding.activity.RmGetAllLeaveActivty;
import com.dies.lionbuilding.activity.RouteManagement.RouteManagementActivity;
import com.dies.lionbuilding.activity.SendLocation;
import com.dies.lionbuilding.activity.Users;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RmFragment extends Fragment {

    @BindView(R.id.card_start_day)
    CardView card_start_day;
    @BindView(R.id.card_oderCon)
    CardView card_oderCon;
    @BindView(R.id.card_selsorder)
    CardView card_selsorder;
    @BindView(R.id.card_route_management)
    CardView card_route_management;
    @BindView(R.id.card_leave_management)
    CardView card_leave_management;
    @BindView(R.id.card_expense_management)
    CardView card_expense_management;

    public RmFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_rm, container, false);
        ButterKnife.bind(this, view);

        checkAndRequestPermissions();

        card_start_day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), SendLocation.class));
            }
        });
        card_oderCon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), OrderConfirmActivity.class));
            }
        });
        card_selsorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), SalesExecOrderActivity.class));
            }
        });
        card_route_management.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), RouteManagementActivity.class));
            }
        });
        card_leave_management.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), RmGetAllLeaveActivty.class));
            }
        });
        card_expense_management.setOnClickListener(new View.OnClickListener() {
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
        if (location1 != 0) {

            listPermissionNeeded.add("android.permission.ACCESS_COARSE_LOCATION");
        }
        if (listPermissionNeeded.isEmpty()) {
            return true;
        }
        ActivityCompat.requestPermissions(getActivity(), listPermissionNeeded.toArray(new String[listPermissionNeeded.size()]), 100);
        return false;
    }
}
