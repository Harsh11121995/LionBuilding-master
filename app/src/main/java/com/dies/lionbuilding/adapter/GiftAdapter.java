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
import com.dies.lionbuilding.activity.GiftDetailActivity;
import com.dies.lionbuilding.activity.ProductOrderUsingBarcode;
import com.dies.lionbuilding.apiservice.ApiConstants;
import com.dies.lionbuilding.application.Utility;
import com.dies.lionbuilding.model.GiftModel;
import com.dies.lionbuilding.model.LeaveModel;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GiftAdapter extends RecyclerView.Adapter<GiftAdapter.MyViewHolder> {

    Context context;
    List<GiftModel.Data> arrayList;

    public GiftAdapter(Context context, List<GiftModel.Data> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public GiftAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.gift_layout, parent, false);
        return new GiftAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GiftAdapter.MyViewHolder holder, int position) {

        holder.txt_title.setText(arrayList.get(position).getGft_name());
        holder.txt_point.setText(arrayList.get(position).getGft_point()+" pts");
        holder.txt_vilid.setText("Valid Up to: "+arrayList.get(position).getOffer_start());

        holder.card_gift.setOnClickListener(view -> {
            Utility.getAppcon().getSession().arrayListGift=new ArrayList<>();
            Utility.getAppcon().getSession().arrayListGift.add(arrayList.get(position));
            Intent intent=new Intent(context, GiftDetailActivity.class);
            context.startActivity(intent);
        });

        Picasso.with(context).load(ApiConstants.IMAGE_URL + arrayList.get(position).getOffer_image())
                .fit()
                .into(holder.img_gift);

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_title)
        TextView txt_title;

        @BindView(R.id.txt_point)
        TextView txt_point;

        @BindView(R.id.txt_vilid)
        TextView txt_vilid;

        @BindView(R.id.img_gift)
        ImageView img_gift;

        @BindView(R.id.card_gift)
        CardView card_gift;




        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
