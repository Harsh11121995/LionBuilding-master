package com.dies.lionbuilding.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.dies.lionbuilding.R;
import com.dies.lionbuilding.activity.ShowShopMap;
import com.dies.lionbuilding.model.AddShopModel;
import com.dies.lionbuilding.model.ShopViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.support.constraint.Constraints.TAG;

public class ShopViewAdapter extends RecyclerView.Adapter<ShopViewAdapter.ViewHolder> {

    Context context;
    List<ShopViewModel.Data> shopModels;
    String ischecked = "false";
    int pos;


    public ShopViewAdapter(Context context, List<ShopViewModel.Data> shopModels) {
        this.context = context;
        this.shopModels = shopModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_shop_view, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tv_shop_name.setText(shopModels.get(position).getShop_name());
        holder.tv_shop_address.setText(shopModels.get(position).getSl_address_line1());

        holder.card_view_map.setOnClickListener(view -> {
            Log.e(TAG, "onBindViewHolder: "+shopModels.get(position).getSl_latitude() );
            Log.e(TAG, "onBindViewHolder: "+shopModels.get(position).getSl_longitude() );
            Intent intent = new Intent(context, ShowShopMap.class);
            intent.putExtra("sl_latitude", ""+shopModels.get(position).getSl_latitude());
            intent.putExtra("sl_longitude", ""+shopModels.get(position).getSl_longitude());
            context.startActivity(intent);
        });
        holder.card_shop_detail.setOnClickListener(view -> {
            int pos = getItemViewType(position);
            holder.card_view_map.setVisibility(View.GONE);
            if (ischecked.equals("true")) {
                ischecked = "false";
                holder.card_view_map.setVisibility(View.GONE);
            } else if (ischecked.equals("false")) {
                ischecked = "true";
                holder.card_view_map.setVisibility(View.VISIBLE);

            }
            notifyDataSetChanged();


        });


    }


    @Override
    public int getItemCount() {
        return shopModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_shop_name)
        TextView tv_shop_name;
        @BindView(R.id.tv_shop_address)
        TextView tv_shop_address;
        @BindView(R.id.card_shop_detail)
        CardView card_shop_detail;
        @BindView(R.id.card_view_map)
        CardView card_view_map;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            pos = getAdapterPosition();

        }
    }
}
