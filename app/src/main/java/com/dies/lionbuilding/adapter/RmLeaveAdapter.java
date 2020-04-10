package com.dies.lionbuilding.adapter;

import android.app.ProgressDialog;
import android.content.Context;
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
import com.dies.lionbuilding.activity.RmGetAllLeaveActivty;
import com.dies.lionbuilding.adapter.Order.SalesExeOrderAdapter;
import com.dies.lionbuilding.apiservice.ApiService;
import com.dies.lionbuilding.apiservice.ApiServiceCreator;
import com.dies.lionbuilding.application.Utility;
import com.dies.lionbuilding.model.LeaveModel;
import com.dies.lionbuilding.model.OrderConData;
import com.google.gson.Gson;

import java.net.SocketTimeoutException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.schedulers.Schedulers;

public class RmLeaveAdapter extends RecyclerView.Adapter<RmLeaveAdapter.MyViewHolder> {

    Context context;
    List<LeaveModel.Data> dataList;
    ApiService apiservice;
    ProgressDialog pDialog;
    int statusCode;
    private String TAG = "TAG";

    public RmLeaveAdapter(Context context, List<LeaveModel.Data> dataList) {
        this.context = context;
        this.dataList = dataList;
        apiservice = ApiServiceCreator.createService("latest");

    }

    @NonNull
    @Override
    public RmLeaveAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rm_leave_details, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RmLeaveAdapter.MyViewHolder holder, int position) {

        holder.txt_salesExename.setText(dataList.get(position).getSales_exe_name());
        holder.txt_edate.setText(dataList.get(position).getLv_end_date());
        holder.txt_sdate.setText(dataList.get(position).getLv_start_date());
        holder.txt_lv_desc.setText(dataList.get(position).getLv_desc());
        holder.txt_hr_status.setText(dataList.get(position).getHr_leave_status());
        holder.txt_rm_status.setText(dataList.get(position).getRm_leave_status());

        if (dataList.get(position).getRm_leave_status().equals("Approve")) {
            holder.btn_aprv_dsaprv.setText("Disapprrove");
        } else {
            holder.btn_aprv_dsaprv.setText("Approve");
        }

        holder.btn_aprv_dsaprv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (dataList.get(position).getRm_leave_status().equals("Approve")) {

                    pDialog = new ProgressDialog(context);
                    pDialog.setTitle("Checking Data");
                    pDialog.setMessage("Please Wait...");
                    pDialog.setIndeterminate(false);
                    pDialog.setCancelable(false);
                    pDialog.show();

                    Observable<LeaveModel> responseObservable = apiservice.rm_getLeaveApproved(

                            dataList.get(position).getLv_id(),
                            dataList.get(position).getLv_user_id(),
                            "0"
                    );

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
                            .subscribe(new Observer<LeaveModel>() {
                                @Override
                                public void onCompleted() {
                                    pDialog.dismiss();
                                }

                                @Override
                                public void onError(Throwable e) {
                                    Log.e("error", "" + e.getMessage());
                                }

                                @Override
                                public void onNext(LeaveModel leaveModel) {
                                    statusCode = leaveModel.getStatusCode();
                                    if (statusCode == 200) {

                                        dataList = leaveModel.getData();
                                        Log.e(TAG, "arrayListdata: save btn-- " + new Gson().toJson(dataList));

                                        Utility.displayToast(context, leaveModel.getMessage());

                                        ((RmGetAllLeaveActivty) context).getRmLeaveDataApi();
                                    }
                                }
                            });
                } else {

                    pDialog = new ProgressDialog(context);
                    pDialog.setTitle("Checking Data");
                    pDialog.setMessage("Please Wait...");
                    pDialog.setIndeterminate(false);
                    pDialog.setCancelable(false);
                    pDialog.show();

                    Observable<LeaveModel> responseObservable = apiservice.rm_getLeaveApproved(

                            dataList.get(position).getLv_id(),
                            dataList.get(position).getLv_user_id(),
                            "1"
                    );

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
                            .subscribe(new Observer<LeaveModel>() {
                                @Override
                                public void onCompleted() {
                                    pDialog.dismiss();
                                }

                                @Override
                                public void onError(Throwable e) {
                                    Log.e("error", "" + e.getMessage());
                                }

                                @Override
                                public void onNext(LeaveModel leaveModel) {
                                    statusCode = leaveModel.getStatusCode();
                                    if (statusCode == 200) {

                                        dataList = leaveModel.getData();
                                        Log.e(TAG, "arrayListdata: save btn-- " + new Gson().toJson(dataList));

                                        Utility.displayToast(context, leaveModel.getMessage());

                                        ((RmGetAllLeaveActivty) context).getRmLeaveDataApi();
                                    }
                                }
                            });
                }


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

        @BindView(R.id.txt_sdate)
        TextView txt_sdate;

        @BindView(R.id.txt_edate)
        TextView txt_edate;

        @BindView(R.id.txt_lv_desc)
        TextView txt_lv_desc;

        @BindView(R.id.txt_hr_status)
        TextView txt_hr_status;

        @BindView(R.id.txt_rm_status)
        TextView txt_rm_status;

        @BindView(R.id.btn_aprv_dsaprv)
        Button btn_aprv_dsaprv;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
