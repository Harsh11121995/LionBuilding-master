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
import com.dies.lionbuilding.activity.OrderManagement.OrderActivity;
import com.dies.lionbuilding.activity.OrderManagement.OrderConfirmActivity;
import com.dies.lionbuilding.activity.OrderManagement.SalesExecOrderActivity;
import com.dies.lionbuilding.activity.RewardHistoryActivity;
import com.dies.lionbuilding.activity.RmGetAllLeaveActivty;
import com.dies.lionbuilding.activity.RouteManagement.RouteManagementActivity;
import com.dies.lionbuilding.activity.SendLocation;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ContractorFragment extends Fragment {

    @BindView(R.id.card_order)
    CardView card_order;
    @BindView(R.id.card_reward)
    CardView card_reward;


    public ContractorFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contractor, container, false);
        ButterKnife.bind(this, view);

        //checkAndRequestPermissions();

        card_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), OrderActivity.class));
            }
        });
        card_reward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), RewardHistoryActivity.class));
            }
        });

        return view;
    }


/*
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
*/
}
