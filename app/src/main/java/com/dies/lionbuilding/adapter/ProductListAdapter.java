package com.dies.lionbuilding.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.dies.lionbuilding.R;
import com.dies.lionbuilding.activity.OrderManagement.OrderActivity;
import com.dies.lionbuilding.apiservice.ApiConstants;
import com.dies.lionbuilding.model.ProductModel;
import com.google.gson.Gson;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.MyViewHolder> {

List<ProductModel.Data> arrayList;
Context context;
    Integer qty = 0;
    Double single_product_price = 0.0;
    String fragment_type;

    public MyCustomEditTextListener myCustomEditTextListener;

    public ProductListAdapter(List<ProductModel.Data> arrayList, Context context,String fragment_type) {
        this.arrayList = arrayList;
        this.context = context;
        this.fragment_type = fragment_type;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list_layout, parent, false);
        return new ProductListAdapter.MyViewHolder(view, new MyCustomEditTextListener());
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.myCustomEditTextListener.updatePosition(holder.getAdapterPosition(), holder);

        holder.txt_product_name.setText(arrayList.get(position).getProduct_name());

        holder.txt_product_price.setText(arrayList.get(position).getProduct_price());

        Picasso.with(context).load(ApiConstants.IMAGE_URL + arrayList.get(position).getProduct_img())
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .fit()
                .into(holder.img_product);

        arrayList.get(position).setProduct_quentity("");


        holder.img_plus.setOnClickListener(view -> {
            qty = Integer.parseInt(holder.edt_qty.getText().toString());
            Integer main_qty = ((OrderActivity) context).getText();
            Double order_total = ((OrderActivity) context).getTotal();

            qty = qty + 1;
            main_qty += 1;

            order_total += 1 * Double.valueOf(arrayList.get(holder.getAdapterPosition()).getProduct_price());
            single_product_price = qty * Double.valueOf(arrayList.get(holder.getAdapterPosition()).getProduct_price());

            holder.edt_qty.setText(String.valueOf(qty));
            arrayList.get(holder.getAdapterPosition()).setProduct_quentity(holder.edt_qty.getText().toString());
            arrayList.get(holder.getAdapterPosition()).setProduct_total(String.valueOf(single_product_price));
            ((OrderActivity) context).setText(main_qty.toString());
            ((OrderActivity) context).setTotal(order_total.toString());


            int pos=Integer.parseInt(fragment_type);
            ((OrderActivity) context).setArray(arrayList,pos);

//            int pos=Integer.parseInt(fragment_type);
//            ((OrderActivity) context).setArray(arrayList,pos);
        });


        holder.img_minus.setOnClickListener(view -> {

            qty = Integer.parseInt(holder.edt_qty.getText().toString());
            Integer main_qty = ((OrderActivity) context).getText();
            Double order_total = ((OrderActivity) context).getTotal();
            if (qty > 0) {
                qty = qty - 1;
                main_qty -= 1;

                order_total -= 1 * Double.valueOf(arrayList.get(holder.getAdapterPosition()).getProduct_price());
                single_product_price = qty * Double.valueOf(arrayList.get(holder.getAdapterPosition()).getProduct_price());

                holder.edt_qty.setText(String.valueOf(qty));
                arrayList.get(holder.getAdapterPosition()).setProduct_quentity(holder.edt_qty.getText().toString());
                arrayList.get(holder.getAdapterPosition()).setProduct_total(String.valueOf(single_product_price));
                ((OrderActivity) context).setText(main_qty.toString());
                ((OrderActivity) context).setTotal(order_total.toString());

                int pos=Integer.parseInt(fragment_type);
                ((OrderActivity) context).setArray(arrayList,pos);
            }
        });


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public MyCustomEditTextListener myCustomEditTextListener;


        @BindView(R.id.txt_product_name)
        TextView txt_product_name;

        @BindView(R.id.txt_product_price)
        TextView txt_product_price;

        @BindView(R.id.img_minus)
        ImageView img_minus;

        @BindView(R.id.img_plus)
        ImageView img_plus;

        @BindView(R.id.edt_qty)
        public EditText edt_qty;

        @BindView(R.id.img_product)
        ImageView img_product;


        public MyViewHolder(View itemView,MyCustomEditTextListener myCustomEditTextListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.myCustomEditTextListener = myCustomEditTextListener;
            this.edt_qty.addTextChangedListener(this.myCustomEditTextListener);
        }
    }

    private class MyCustomEditTextListener implements TextWatcher {
        private int position;
        Integer before_val = 0;
        MyViewHolder holder;

        public void updatePosition(int position, MyViewHolder holder) {

            this.position = position;
            this.holder = holder;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            if (charSequence.length() != 0) {
                before_val = Integer.valueOf(charSequence.toString());
            }
        }

        @Override
        public void onTextChanged(CharSequence s, int i, int i2, int i3) {
            if (s.length() != 0) {
                arrayList.get(position).setProduct_qty(s.toString());

                Integer main_qty = ((OrderActivity) context).getText();
                Double order_total = ((OrderActivity) context).getTotal();

                main_qty -= before_val;
                order_total -= before_val * Double.valueOf(arrayList.get(position).getProduct_price());
                main_qty += Integer.valueOf(s.toString());



//                if (usertype=="DAIICT"){
//                    if (arrayList.get(position).getDaiict()!=null && !arrayList.get(position).getDaiict().equals("") && !arrayList.get(position).getDaiict().equals("0") ){
//                        order_total += Integer.valueOf(s.toString()) * Double.valueOf(arrayList.get(position).getDaiict());
//                        single_product_price = Integer.valueOf(s.toString()) * Double.valueOf(arrayList.get(position).getDaiict());
//                    }else{
//                        order_total += Integer.valueOf(s.toString()) * Double.valueOf(arrayList.get(position).getProduct_price());
//                        single_product_price = Integer.valueOf(s.toString()) * Double.valueOf(arrayList.get(position).getProduct_price());
//                    }
//                }else if (usertype=="GFSU"){
//                    if (arrayList.get(position).getGfsu()!=null && !arrayList.get(position).getGfsu().equals("") && !arrayList.get(position).getGfsu().equals("0") ){
//                        order_total += Integer.valueOf(s.toString()) * Double.valueOf(arrayList.get(position).getGfsu());
//                        single_product_price = Integer.valueOf(s.toString()) * Double.valueOf(arrayList.get(position).getGfsu());
//                    }else{
//                        order_total += Integer.valueOf(s.toString()) * Double.valueOf(arrayList.get(position).getProduct_price());
//                        single_product_price = Integer.valueOf(s.toString()) * Double.valueOf(arrayList.get(position).getProduct_price());
//                    }
//                }else if (usertype=="HOSTEL"){
//                    if (arrayList.get(position).getHostel()!=null && !arrayList.get(position).getHostel().equals("") && !arrayList.get(position).getHostel().equals("0")){
//                        order_total += Integer.valueOf(s.toString()) * Double.valueOf(arrayList.get(position).getHostel());
//                        single_product_price = Integer.valueOf(s.toString()) * Double.valueOf(arrayList.get(position).getHostel());
//                    }else{
//                        order_total += Integer.valueOf(s.toString()) * Double.valueOf(arrayList.get(position).getProduct_price());
//                        single_product_price = Integer.valueOf(s.toString()) * Double.valueOf(arrayList.get(position).getProduct_price());
//                    }
//                }else if (usertype=="REGULAR"){
//                    if (arrayList.get(position).getRegular()!=null && !arrayList.get(position).getRegular().equals("") && !arrayList.get(position).getRegular().equals("0")){
//                        order_total += Integer.valueOf(s.toString()) * Double.valueOf(arrayList.get(position).getRegular());
//                        single_product_price = Integer.valueOf(s.toString()) * Double.valueOf(arrayList.get(position).getRegular());
//                    }else{
//                        order_total += Integer.valueOf(s.toString()) * Double.valueOf(arrayList.get(position).getProduct_price());
//                        single_product_price = Integer.valueOf(s.toString()) * Double.valueOf(arrayList.get(position).getProduct_price());
//                    }
//                }
//
//
                order_total += Integer.valueOf(s.toString()) * Double.valueOf(arrayList.get(position).getProduct_price());
                single_product_price = Integer.valueOf(s.toString()) * Double.valueOf(arrayList.get(position).getProduct_price());


                ((OrderActivity) context).setText(main_qty.toString());
                ((OrderActivity) context).setTotal(order_total.toString());
                arrayList.get(position).setProduct_total(String.valueOf(single_product_price));
                Log.d("data1", new Gson().toJson(arrayList));


                int pos=Integer.parseInt(fragment_type);
                ((OrderActivity) context).setArray(arrayList,pos);

            } else {
                holder.edt_qty.setText("0");
            }
        }

        @Override
        public void afterTextChanged(Editable s) {


        }
    }
}
