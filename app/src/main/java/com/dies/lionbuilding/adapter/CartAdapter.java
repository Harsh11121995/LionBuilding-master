package com.dies.lionbuilding.adapter;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dies.lionbuilding.R;
import com.dies.lionbuilding.activity.CartActivity;
import com.dies.lionbuilding.activity.ProductDetailActivity;
import com.dies.lionbuilding.activity.ProductOrderUsingBarcode;
import com.dies.lionbuilding.apiservice.ApiConstants;
import com.dies.lionbuilding.application.Utility;
import com.dies.lionbuilding.database.DatabaseHelper;
import com.dies.lionbuilding.model.ProductModel;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    Context context;
    List<ProductModel.Data> listProduct;
    DatabaseHelper myDb;
    String name;

    DecimalFormat format = new DecimalFormat("0.00");

    int minteger = 0;

    public CartAdapter(Context context, List<ProductModel.Data> listProduct,String name) {
        this.context = context;
        this.listProduct = listProduct;
        this.name=name;
        myDb=new DatabaseHelper(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cart_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        minteger=Integer.parseInt(listProduct.get(position).getProduct_qty());
        holder.txt_product_name.setText(listProduct.get(position).getProduct_descpriction());
        double product_price=Double.parseDouble(listProduct.get(position).getProduct_price());
        double qty=Double.parseDouble(listProduct.get(position).getProduct_qty());
        double total_price=product_price*qty;

        if (name.equals("product")){
            holder.txt_price.setText("₹"+format.format(total_price));
        }else {
            int product_point=Integer.parseInt(listProduct.get(position).getProduct_point());
            int qtyy=Integer.parseInt(listProduct.get(position).getProduct_qty());
            String total_point=String.valueOf(qtyy*product_point);
            holder.txt_price.setText(total_point+" Points");
        }


        holder.card_product.setOnClickListener(view -> {
            Utility.getAppcon().getSession().arrayListProduct = new ArrayList<>();
            Utility.getAppcon().getSession().arrayListProduct.add(listProduct.get(position));
            Intent intent = new Intent(context, ProductOrderUsingBarcode.class);
            intent.putExtra("cartdata","cartdata");
            context.startActivity(intent);
        });

        holder.txt_qty.setText("Quentity : "+String.valueOf(minteger));




        Picasso.with(context).load(ApiConstants.IMAGE_URL + listProduct.get(position).getProduct_img())
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .into(holder.img_product);


        holder.integer_number.setText(String.valueOf(minteger));
        holder.image_plus.setOnClickListener(view -> {
            minteger=Integer.parseInt(listProduct.get(position).getProduct_qty());
            minteger = minteger + 1;
            holder.integer_number.setText(String.valueOf(minteger));

        });

        holder.image_minus.setOnClickListener(view -> {
            minteger=Integer.parseInt(listProduct.get(position).getProduct_qty());
            minteger = minteger - 1;
            holder.integer_number.setText(String.valueOf(minteger));
        });

        holder.delete_product.setOnClickListener(view -> {
            Integer deletedRows =   myDb.deleteData(String.valueOf(listProduct.get(position).getP_id()));
            if(deletedRows > 0) {
                listProduct.remove(position);
                notifyDataSetChanged();
                if (context instanceof CartActivity) {
                    ((CartActivity)context).getData();
                }
                Toast.makeText(context, "Data Deleted", Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(context, "Data not Deleted", Toast.LENGTH_LONG).show();
            }

        });

    }

    @Override
    public int getItemCount() {
        return listProduct.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txt_product_name)
        TextView txt_product_name;

        @BindView(R.id.txt_price)
        TextView txt_price;

        @BindView(R.id.card_product)
        CardView card_product;

        @BindView(R.id.delete_product)
        CardView delete_product;

        @BindView(R.id.integer_number)
        TextView integer_number;

        @BindView(R.id.image_plus)
        ImageView image_plus;

        @BindView(R.id.image_minus)
        ImageView image_minus;

        @BindView(R.id.txt_qty)
        TextView txt_qty;

        @BindView(R.id.img_product)
        ImageView img_product;




        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

         void display(int number) {
        }
    }

    public void clear() {
        int size = listProduct.size();
        listProduct.clear();
        notifyItemRangeRemoved(0, size);
    }


    public void increaseInteger(View view) {


    }public void decreaseInteger(View view) {

    }



}
