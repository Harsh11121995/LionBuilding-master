package com.dies.lionbuilding.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dies.lionbuilding.R;
import com.dies.lionbuilding.ShopTimelineViewActivity;
import com.dies.lionbuilding.application.Utility;
import com.dies.lionbuilding.model.RouteModel;
import com.dies.lionbuilding.model.ShopModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RouteAdapter extends RecyclerView.Adapter<RouteAdapter.MyViewHolder> {

    Context context;
    List<RouteModel.Data> arrayList;

    public RouteAdapter(Context context, List<RouteModel.Data> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public RouteAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.route_layout,parent,false);
        return new RouteAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RouteAdapter.MyViewHolder holder, int position) {

        holder.txt_zone.setText(arrayList.get(position).getRt_zone());
        holder.txt_district.setText(arrayList.get(position).getDistrict_name());
        holder.txt_date.setText(arrayList.get(position).getRt_date());
        holder.card_rout.setOnClickListener(view -> {
            Utility.getAppcon().getSession().arrayListRoute= new ArrayList<>();
            Utility.getAppcon().getSession().arrayListRoute.add(arrayList.get(position));
            Intent intent=new Intent(context, ShopTimelineViewActivity.class);
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.card_rout)
        CardView card_rout;

        @BindView(R.id.txt_zone)
        TextView txt_zone;

        @BindView(R.id.txt_district)
        TextView txt_district;

        @BindView(R.id.txt_date)
        TextView txt_date;



        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
