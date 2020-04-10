package com.dies.lionbuilding.adapter.Order;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dies.lionbuilding.R;
import com.dies.lionbuilding.model.OrderConData;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderDeliveredAdapter extends RecyclerView.Adapter<OrderDeliveredAdapter.MyViewHolder> {

    Context context;
    List<OrderConData.Data> dataList;

    public OrderDeliveredAdapter(Context context, List<OrderConData.Data> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public OrderDeliveredAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ordrdelivered_details, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderDeliveredAdapter.MyViewHolder holder, int position) {

        holder.txt_dlrname.setText(dataList.get(position).getDelerName());
        holder.txt_status.setText(dataList.get(position).getOrderStatusName());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txt_dlrname)
        TextView txt_dlrname;

        @BindView(R.id.txt_status)
        TextView txt_status;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
