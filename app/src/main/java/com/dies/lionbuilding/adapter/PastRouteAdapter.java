package com.dies.lionbuilding.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dies.lionbuilding.R;
import com.dies.lionbuilding.apiservice.ApiConstants;
import com.dies.lionbuilding.model.PastRouteModel;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PastRouteAdapter extends RecyclerView.Adapter<PastRouteAdapter.MyViewHolder> {

    Context context;
    List<PastRouteModel.Data> dataList;

    public PastRouteAdapter(Context context, List<PastRouteModel.Data> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public PastRouteAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pastroute_details, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PastRouteAdapter.MyViewHolder holder, int position) {

        holder.txt_dealrname.setText(dataList.get(position).getDealerName());

        Picasso.with(context)
                .load(ApiConstants.IMAGE_URL + dataList.get(position).getTvImage())
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .into(holder.iv_location);

        holder.txt_shopname.setText(dataList.get(position).getShopName());
        holder.txt_pincode.setText(dataList.get(position).getSlPincode());
        holder.txt_add1.setText(dataList.get(position).getSlAddressLine1());
        holder.txt_add2.setText(dataList.get(position).getSlAddressLine2());

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.txt_dealrname)
        TextView txt_dealrname;

        @BindView(R.id.iv_location)
        ImageView iv_location;

        @BindView(R.id.txt_shopname)
        TextView txt_shopname;

        @BindView(R.id.txt_pincode)
        TextView txt_pincode;

        @BindView(R.id.txt_add1)
        TextView txt_add1;

        @BindView(R.id.txt_add2)
        TextView txt_add2;


        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
