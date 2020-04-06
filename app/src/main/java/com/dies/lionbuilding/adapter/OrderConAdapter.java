package com.dies.lionbuilding.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.dies.lionbuilding.R;
import com.dies.lionbuilding.activity.OrderManagement.OrderDeliveredActivity;
import com.dies.lionbuilding.activity.OrderManagement.RmOrderViewActivity;
import com.dies.lionbuilding.apiservice.ApiConstants;
import com.dies.lionbuilding.apiservice.ApiService;
import com.dies.lionbuilding.apiservice.ApiServiceCreator;
import com.dies.lionbuilding.application.SessionManager;
import com.dies.lionbuilding.application.Utility;
import com.dies.lionbuilding.model.OrderConData;
import com.dies.lionbuilding.model.PastRouteModel;
import com.google.gson.Gson;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.net.SocketTimeoutException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.schedulers.Schedulers;

public class OrderConAdapter extends RecyclerView.Adapter<OrderConAdapter.MyViewHolder> {

    Context context;
    List<OrderConData.Data> dataList;
    SessionManager sessionManager;


    public OrderConAdapter(Context context, List<OrderConData.Data> dataList) {
        this.context = context;
        this.dataList = dataList;
        sessionManager = new SessionManager(context);
    }

    @NonNull
    @Override
    public OrderConAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ordercon_details, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderConAdapter.MyViewHolder holder, int position) {

        holder.txt_dlrname.setText(dataList.get(position).getDelerName());
        holder.txt_distriname.setText(dataList.get(position).getDistributorName());
        holder.txt_totalqty.setText(dataList.get(position).getOrdQty());
        holder.txt_totalprice.setText(dataList.get(position).getOrdTotal());
        holder.txt_status.setText(dataList.get(position).getOrderStatusName());
        holder.txt_date.setText(dataList.get(position).getOrdDate());

        holder.btn_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (sessionManager.getKeyRoll().equals("Distributor")) {
                    Intent intent = new Intent(context, OrderDeliveredActivity.class);
                    intent.putExtra("ord_id", dataList.get(position).getOrdId());
                    context.startActivity(intent);
                } else if (sessionManager.getKeyRoll().equals("RM")) {
                    Intent intent = new Intent(context, RmOrderViewActivity.class);
                    intent.putExtra("ord_id", dataList.get(position).getOrdId());
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

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

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
