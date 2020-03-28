package com.dies.lionbuilding.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dies.lionbuilding.R;

import com.dies.lionbuilding.activity.BankManagement.BankDetail;
import com.dies.lionbuilding.apiservice.ApiConstants;
import com.dies.lionbuilding.model.AddBankModel;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BankListAdapter extends RecyclerView.Adapter<BankListAdapter.ViewHolder>  {

    Context context;
    List<AddBankModel.Data> banklist;
    String ischecked = "false";
    int pos;



    public BankListAdapter(Context context, List<AddBankModel.Data> banklist) {
        this.context = context;
        this.banklist = banklist;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_bank_list, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        holder.tv_name.setText(banklist.get(position).getAc_hlname());
        holder.txt_ac_number.setText(banklist.get(position).getBank_acnumber());
        holder.txt_ifsc_number.setText(banklist.get(position).getBank_ifsc_code());
        holder.txt_pan_number.setText(banklist.get(position).getBank_pan_no());
        holder.txt_gst_number.setText(banklist.get(position).getBank_gst_number());
        holder.tv_text.setText("IFSC Code:");

        Picasso.with(context).load(ApiConstants.IMAGE_URL + banklist.get(position).getBank_adhar_card())
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .fit()
                .into(holder.img_adhar);

        Picasso.with(context).load(ApiConstants.IMAGE_URL + banklist.get(position).getBank_pan_img())
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .fit()
                .into(holder.img_pan);

        Picasso.with(context).load(ApiConstants.IMAGE_URL + banklist.get(position).getBank_checqu())
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .fit()
                .into(holder.img_cheque);
//        holder.card_view_detail.setOnClickListener(view -> {
//            context.startActivity(new Intent(context, BankDetail.class));
//        });

    }

    @Override
    public int getItemCount() {
        return banklist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_name)
        TextView tv_name;

        @BindView(R.id.txt_ac_number)
        TextView txt_ac_number;

        @BindView(R.id.tv_text)
        TextView tv_text;

        @BindView(R.id.txt_bank_name)
        TextView txt_bank_name;

        @BindView(R.id.txt_branch_name)
        TextView txt_branch_name;

        @BindView(R.id.txt_ifsc_number)
        TextView txt_ifsc_number;

        @BindView(R.id.txt_pan_number)
        TextView txt_pan_number;

        @BindView(R.id.txt_gst_number)
        TextView txt_gst_number;

        @BindView(R.id.img_adhar)
        ImageView img_adhar;

        @BindView(R.id.img_pan)
        ImageView img_pan;

        @BindView(R.id.img_cheque)
        ImageView img_cheque;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
