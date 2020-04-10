package com.dies.lionbuilding.adapter.Order;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.dies.lionbuilding.R;
import com.dies.lionbuilding.RmSalesMapActivity;
import com.dies.lionbuilding.activity.OrderManagement.RmOrderViewActivity;
import com.dies.lionbuilding.model.OrderConData;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SalesExeOrderAdapter extends RecyclerView.Adapter<SalesExeOrderAdapter.MyViewHolder> {

    Context context;
    List<OrderConData.Data> dataList;

    public SalesExeOrderAdapter(Context context, List<OrderConData.Data> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public SalesExeOrderAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rm_salesexeorder_details, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SalesExeOrderAdapter.MyViewHolder holder, int position) {

        holder.txt_salesExename.setText(dataList.get(position).getSalesExeName());
        holder.txt_dlrname.setText(dataList.get(position).getDelerName());
        holder.txt_distriname.setText(dataList.get(position).getDistributorName());
        holder.txt_totalqty.setText(dataList.get(position).getOrdQty());
        holder.txt_totalprice.setText(dataList.get(position).getOrdTotal());
        holder.txt_status.setText(dataList.get(position).getOrderStatusName());
        holder.txt_date.setText(dataList.get(position).getOrdDate());

        holder.btn_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, RmOrderViewActivity.class);
                intent.putExtra("ord_id", dataList.get(position).getOrdId());
                context.startActivity(intent);

            }
        });

        holder.btn_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, RmSalesMapActivity.class);
                intent.putExtra("ord_slm_id", dataList.get(position).getOrdSlmId());
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txt_salesExename)
        TextView txt_salesExename;

        @BindView(R.id.txt_distriname)
        TextView txt_distriname;

        @BindView(R.id.txt_dlrname)
        TextView txt_dlrname;

        @BindView(R.id.txt_totalqty)
        TextView txt_totalqty;

        @BindView(R.id.txt_totalprice)
        TextView txt_totalprice;

        @BindView(R.id.txt_status)
        TextView txt_status;

        @BindView(R.id.txt_date)
        TextView txt_date;

        @BindView(R.id.btn_view)
        Button btn_view;

        @BindView(R.id.btn_location)
        Button btn_location;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
