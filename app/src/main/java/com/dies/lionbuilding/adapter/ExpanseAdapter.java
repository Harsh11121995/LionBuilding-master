package com.dies.lionbuilding.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dies.lionbuilding.R;
import com.dies.lionbuilding.apiservice.ApiConstants;
import com.dies.lionbuilding.model.ExpanseModel;
import com.dies.lionbuilding.model.LeaveModel;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.support.constraint.Constraints.TAG;

public class ExpanseAdapter extends RecyclerView.Adapter<ExpanseAdapter.MyViewHolder> {

    Context context;
    List<ExpanseModel.Data> arrayList;

    public ExpanseAdapter(Context context, List<ExpanseModel.Data> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ExpanseAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.expanse_layout, parent, false);
        return new ExpanseAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpanseAdapter.MyViewHolder holder, int position) {

        holder.txt_date.setText(arrayList.get(position).getExp_date());
        holder.txt_amount.setText(arrayList.get(position).getExp_amount());
        holder.txt_desc.setText(Html.fromHtml(arrayList.get(position).getExp_desc()));

       /* if (!arrayList.get(position).getExp_images().equals("")){
            holder.card_image.setVisibility(View.VISIBLE);
        }else {
            holder.card_image.setVisibility(View.GONE);
        }*/

        if (arrayList.get(position).getExp_images() == null) {
            holder.card_expimage.setVisibility(View.GONE);

        } else {
            holder.card_expimage.setVisibility(View.VISIBLE);

        }

        Picasso.with(context).load(ApiConstants.IMAGE_URL + arrayList.get(position).getExp_images())
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .into(holder.img_data);


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_date)
        TextView txt_date;

        @BindView(R.id.txt_amount)
        TextView txt_amount;

        @BindView(R.id.txt_desc)
        TextView txt_desc;

        @BindView(R.id.img_data)
        ImageView img_data;

        @BindView(R.id.card_image)
        CardView card_image;

        @BindView(R.id.card_expimage)
        CardView card_expimage;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
