package com.dies.lionbuilding.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dies.lionbuilding.R;
import com.dies.lionbuilding.model.RouteModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FutureRouteAdapter extends RecyclerView.Adapter<FutureRouteAdapter.MyHolder> {

    Context context;
    List<RouteModel.RouteData> arrayList;

    public FutureRouteAdapter(Context context, List<RouteModel.RouteData> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public FutureRouteAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.futureroute_details, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FutureRouteAdapter.MyHolder holder, int position) {

        holder.txt_dealrname.setText(arrayList.get(position).getDealer_name());
        holder.txt_shopname.setText(arrayList.get(position).getShop_name());
        holder.txt_pincode.setText(arrayList.get(position).getSl_pincode());
        holder.txt_add1.setText(arrayList.get(position).getSl_address_line1());
        holder.txt_add2.setText(arrayList.get(position).getSl_address_line2());

 /*       holder.txt_dealrname.setText(arrayList.get(position).getRoute_data().get(position).getDealer_name());
        holder.txt_shopname.setText(arrayList.get(position).getRoute_data().get(position).getShop_name());
        holder.txt_pincode.setText(arrayList.get(position).getRoute_data().get(position).getSl_pincode());
        holder.txt_add1.setText(arrayList.get(position).getRoute_data().get(position).getSl_address_line1());
        holder.txt_add2.setText(arrayList.get(position).getRoute_data().get(position).getSl_address_line2());
*/
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txt_dealrname)
        TextView txt_dealrname;

        @BindView(R.id.txt_shopname)
        TextView txt_shopname;

        @BindView(R.id.txt_pincode)
        TextView txt_pincode;

        @BindView(R.id.txt_add1)
        TextView txt_add1;

        @BindView(R.id.txt_add2)
        TextView txt_add2;

        public MyHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }


}
