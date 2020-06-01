package com.dies.lionbuilding.adapter.Order;

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
import android.widget.TextView;

import com.dies.lionbuilding.R;
import com.dies.lionbuilding.activity.OrderManagement.OrderConfirmActivity;
import com.dies.lionbuilding.activity.OrderManagement.OrderDeliveredActivity;
import com.dies.lionbuilding.activity.OrderManagement.RmOrderViewActivity;
import com.dies.lionbuilding.apiservice.ApiService;
import com.dies.lionbuilding.apiservice.ApiServiceCreator;
import com.dies.lionbuilding.application.SessionManager;
import com.dies.lionbuilding.application.Utility;
import com.dies.lionbuilding.model.OrderConData;
import com.google.gson.Gson;

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
    ApiService apiservice;
    ProgressDialog pDialog;
    int statusCode;
    private String TAG = "TAG";

    public OrderConAdapter(Context context, List<OrderConData.Data> dataList) {
        this.context = context;
        this.dataList = dataList;
        sessionManager = new SessionManager(context);
        apiservice = ApiServiceCreator.createService("latest");
    }

    @NonNull
    @Override
    public OrderConAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ordercon_details, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderConAdapter.MyViewHolder holder, int position) {

        if (dataList.get(position).getOrderStatusName().equals("Receive")) {
            holder.btn_confrm.setVisibility(View.VISIBLE);
        } else {
            holder.btn_confrm.setVisibility(View.GONE);
        }

        holder.txt_dlrname.setText(dataList.get(position).getDelerName());
        holder.txt_distriname.setText(dataList.get(position).getDistributorName());
        holder.txt_totalqty.setText(dataList.get(position).getOrdQty());
        holder.txt_totalprice.setText(dataList.get(position).getOrdTotal());
        holder.txt_status.setText(dataList.get(position).getOrderStatusName());
        holder.txt_date.setText(dataList.get(position).getOrdDate());

        holder.btn_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Intent intent = new Intent(context, OrderDeliveredActivity.class);

                Intent intent = new Intent(context, RmOrderViewActivity.class);
                intent.putExtra("ord_id", dataList.get(position).getOrdId());
                context.startActivity(intent);

                /*if (sessionManager.getKeyRoll().equals("Distributor")) {
                    Intent intent = new Intent(context, OrderDeliveredActivity.class);
                    intent.putExtra("ord_id", dataList.get(position).getOrdId());
                    context.startActivity(intent);
                } else if (sessionManager.getKeyRoll().equals("RM")) {
                    Intent intent = new Intent(context, RmOrderViewActivity.class);
                    intent.putExtra("ord_id", dataList.get(position).getOrdId());
                    context.startActivity(intent);
                }*/
            }
        });

        holder.btn_confrm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pDialog = new ProgressDialog(context);
                pDialog.setTitle("Checking Data");
                pDialog.setMessage("Please Wait...");
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(false);
                pDialog.show();

                Observable<OrderConData> responseObservable = apiservice.getdistrbtr_odrconfirm(
                        dataList.get(position).getOrdId(), sessionManager.getKeyId());

                Log.e(TAG, "ord_id: " + dataList.get(position).getOrdId());

                responseObservable.subscribeOn(Schedulers.newThread())
                        .observeOn(rx.android.schedulers.AndroidSchedulers.mainThread())
                        .onErrorResumeNext(throwable -> {
                            if (throwable instanceof retrofit2.HttpException) {
                                retrofit2.HttpException ex = (retrofit2.HttpException) throwable;
                                statusCode = ex.code();
                                Log.e("error", ex.getLocalizedMessage());
                            } else if (throwable instanceof SocketTimeoutException) {
                                statusCode = 1000;
                            }
                            return Observable.empty();
                        })
                        .subscribe(new Observer<OrderConData>() {
                            @Override
                            public void onCompleted() {
                                pDialog.dismiss();
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e("error", "" + e.getMessage());
                            }

                            @Override
                            public void onNext(OrderConData orderConData) {
                                statusCode = orderConData.getStatusCode();
                                if (statusCode == 200) {

                                    dataList = orderConData.getData();
                                    Log.e(TAG, "arrayListdata: save btn-- " + new Gson().toJson(dataList));

                                    Utility.displayToast(context, orderConData.getMessage());

                                    ((OrderConfirmActivity) context).getAllDisOrderApi();
                                    //notifyItemChanged(position);
                                    //holder.btn_confrm.setVisibility(View.GONE);
                                    // updateData(dataList);
                                }
                            }
                        });
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

        @BindView(R.id.btn_confrm)
        Button btn_confrm;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
