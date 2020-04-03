package com.dies.lionbuilding.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dies.lionbuilding.R;
import com.dies.lionbuilding.model.ProductModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderSummaryAdapter extends RecyclerView.Adapter<OrderSummaryAdapter.ViewHolder> {

    Context context;
    ArrayList<ProductModel.Data> arrayList;

    public OrderSummaryAdapter(Context context, ArrayList<ProductModel.Data> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_summary_items, parent, false);
        return new OrderSummaryAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderSummaryAdapter.ViewHolder holder, int position) {

        holder.txt_product_name.setText(arrayList.get(position).getProduct_name());
        holder.txt_product_qty.setText(arrayList.get(position).getProduct_quentity());
        holder.txt_product_price.setText(arrayList.get(position).getProduct_price());

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txt_pname)
        TextView txt_product_name;

        @BindView(R.id.txt_qty)
        TextView txt_product_qty;

        @BindView(R.id.txt_price)
        TextView txt_product_price;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
