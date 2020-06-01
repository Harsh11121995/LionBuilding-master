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
import com.dies.lionbuilding.model.RewardModel;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RewardHistoryAdapter extends RecyclerView.Adapter<RewardHistoryAdapter.MyViewHolder> {

    List<RewardModel.Data> arraylist;
    Context context;

    public RewardHistoryAdapter(Context context, List<RewardModel.Data> arraylist) {
        this.arraylist = arraylist;
        this.context = context;
    }

    @NonNull
    @Override
    public RewardHistoryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reward_details, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RewardHistoryAdapter.MyViewHolder holder, int position) {

        holder.txt_giftname.setText(arraylist.get(position).getGftName());
        holder.txt_username.setText(arraylist.get(position).getName());
        holder.txt_point.setText(arraylist.get(position).getGftPoint());
        holder.txt_status.setText(arraylist.get(position).getGiftStatus());
        holder.txt_date.setText(arraylist.get(position).getRgDate());

        Picasso.with(context).
                load(ApiConstants.IMAGE_URL + arraylist.get(position).getOfferImage())
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .into(holder.img_gift);
    }

    @Override
    public int getItemCount() {
        return arraylist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.txt_giftname)
        TextView txt_giftname;

        @BindView(R.id.img_gift)
        ImageView img_gift;

        @BindView(R.id.txt_username)
        TextView txt_username;

        @BindView(R.id.txt_point)
        TextView txt_point;

        @BindView(R.id.txt_date)
        TextView txt_date;

        @BindView(R.id.txt_status)
        TextView txt_status;


        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
