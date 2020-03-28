package com.dies.lionbuilding.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dies.lionbuilding.R;
import com.dies.lionbuilding.activity.AddProfile;
import com.dies.lionbuilding.activity.OrderManagement.OrderActivity;
import com.dies.lionbuilding.activity.OrderManagement.OrderConfirmActivity;
import com.dies.lionbuilding.activity.OrderManagement.OrderDeliveredActivity;
import com.dies.lionbuilding.activity.OrderManagement.OrderDeliveredSummary;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class DistributorFragment extends Fragment {


    @BindView(R.id.card_add_user)
    CardView card_add_user;

    @BindView(R.id.card_order)
    CardView card_order;

    @BindView(R.id.card_reward)
    CardView card_reward;

    @BindView(R.id.card_shop_location)
    CardView card_shop_location;

    @BindView(R.id.card_oderCon)
    CardView card_oderCon;

    @BindView(R.id.card_oderdilivered)
    CardView card_oderdilivered;

    public DistributorFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_distributor, container, false);
        ButterKnife.bind(this, view);

        card_add_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), AddProfile.class));
            }
        });

        card_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), OrderActivity.class));
            }
        });

        card_reward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), AddProfile.class));
            }
        });

        card_shop_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), AddProfile.class));
            }
        });

        card_oderCon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), OrderConfirmActivity.class));
            }
        });

        card_oderdilivered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), OrderDeliveredSummary.class));
            }
        });
        return view;
    }

}
