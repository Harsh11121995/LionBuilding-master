package com.dies.lionbuilding.adapter.Order;

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
import com.dies.lionbuilding.model.RmOrderViewModel;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewOrderAdapter extends RecyclerView.Adapter<ViewOrderAdapter.MyViewHolder> {

    Context context;
    List<RmOrderViewModel.Data> arrayListdata;

    public ViewOrderAdapter(Context context, List<RmOrderViewModel.Data> arrayListdata) {
        this.context = context;
        this.arrayListdata = arrayListdata;
    }

    @NonNull
    @Override
    public ViewOrderAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vieworder_details, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewOrderAdapter.MyViewHolder holder, int position) {


        holder.txt_disname.setText(arrayListdata.get(position).getDistributorName());
        holder.txt_pname.setText(arrayListdata.get(position).getProductName());
        holder.txt_pprice.setText(arrayListdata.get(position).getSordTotal());
        holder.txt_pqty.setText(arrayListdata.get(position).getSordQty());

        Picasso.with(context).load(ApiConstants.IMAGE_URL + arrayListdata.get(position).getProductImg()).into(holder.iv_product);
    }

    @Override
    public int getItemCount() {
        return arrayListdata.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txt_disname)
        TextView txt_disname;

        @BindView(R.id.txt_pname)
        TextView txt_pname;

        @BindView(R.id.txt_pqty)
        TextView txt_pqty;

        @BindView(R.id.txt_pprice)
        TextView txt_pprice;

        @BindView(R.id.iv_product)
        ImageView iv_product;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
