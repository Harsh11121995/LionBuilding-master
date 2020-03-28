package com.dies.lionbuilding.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dies.lionbuilding.R;
import com.dies.lionbuilding.activity.ProductDetailActivity;
import com.dies.lionbuilding.activity.ProductOrderUsingBarcode;
import com.dies.lionbuilding.activity.ShowShopMap;
import com.dies.lionbuilding.application.Utility;
import com.dies.lionbuilding.model.ProductModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.support.constraint.Constraints.TAG;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    Context context;
    List<ProductModel.Data> listProduct;

    public ProductAdapter(Context context, List<ProductModel.Data> listProduct) {
        this.context = context;
        this.listProduct = listProduct;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.product_rowdta,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.tv_p_name.setText(listProduct.get(position).getProduct_descpriction());
        holder.tv_p_price.setText(listProduct.get(position).getProduct_price());
        holder.lnr_product.setOnClickListener(view -> {

            Utility.getAppcon().getSession().arrayListProduct=new ArrayList<>();
            Utility.getAppcon().getSession().arrayListProduct.add(listProduct.get(position));
            Intent intent=new Intent(context, ProductOrderUsingBarcode.class);
            context.startActivity(intent);

        });

    }

    @Override
    public int getItemCount() {
        return listProduct.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_p_name)
        TextView tv_p_name;

        @BindView(R.id.tv_p_price)
        TextView tv_p_price;

        @BindView(R.id.lnr_product)
        LinearLayout lnr_product;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
