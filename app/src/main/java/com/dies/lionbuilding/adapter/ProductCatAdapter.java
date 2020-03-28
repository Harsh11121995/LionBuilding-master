package com.dies.lionbuilding.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dies.lionbuilding.R;
import com.dies.lionbuilding.activity.ContactDetail;
import com.dies.lionbuilding.activity.Product;
import com.dies.lionbuilding.activity.ShowShopMap;
import com.dies.lionbuilding.model.ProductCategoryModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductCatAdapter extends RecyclerView.Adapter<ProductCatAdapter.ViewHolder> {
    Context context;
    List<ProductCategoryModel.Data> listProductCat;

    public ProductCatAdapter(Context context, List<ProductCategoryModel.Data> listProductCat) {
        this.context = context;
        this.listProductCat = listProductCat;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.product_cat_rowdata,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.tv_cat_product.setText(listProductCat.get(position).getPrcat_name());
        holder.card_product_cat.setOnClickListener(view -> {

            Intent intent = new Intent(context, Product.class);
            intent.putExtra("prcat_id", ""+listProductCat.get(position).getPrcat_id());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return listProductCat.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_cat_product)
        TextView tv_cat_product;

        @BindView(R.id.card_product_cat)
        CardView card_product_cat;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

        }
    }
}
