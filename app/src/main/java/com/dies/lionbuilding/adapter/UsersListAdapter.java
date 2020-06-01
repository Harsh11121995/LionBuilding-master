package com.dies.lionbuilding.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dies.lionbuilding.R;
import com.dies.lionbuilding.activity.ScanBarcodeActivity;
import com.dies.lionbuilding.activity.UserDetail;
import com.dies.lionbuilding.application.Utility;
import com.dies.lionbuilding.model.AddBankModel;
import com.dies.lionbuilding.model.NewUserModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UsersListAdapter extends RecyclerView.Adapter<UsersListAdapter.ViewHolder>  {

    Context context;
    List<NewUserModel.Data> dataList;

    String name;
    public UsersListAdapter(Context context, List<NewUserModel.Data> dataList,String name) {
        this.context = context;
        this.dataList = dataList;
        this.name=name;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_list, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.tv_name.setText(dataList.get(position).getFirstName());
//        holder.tv_ifsc.setText(dataList.get(position).getEmail());
//        holder.tv_text.setText("Email: ");

        holder.txt_mobile_no.setText(dataList.get(position).getMobile());

        holder.txt_type.setText(dataList.get(position).getUsertype());
       // holder.txt_type.setText("Type: ");




        holder.card_view_detail.setOnClickListener(view -> {
            if (name.equalsIgnoreCase("scan")){
                Utility.getAppcon().getSession().dealer_id =dataList.get(position).getUserId();
                Intent intent=new Intent(context, ScanBarcodeActivity.class);
                context.startActivity(intent);
            }else {
                Utility.getAppcon().getSession().arrayListUser= new ArrayList<>();
                Utility.getAppcon().getSession().arrayListUser.add(dataList.get(position));
                Intent intent=new Intent(context, UserDetail.class);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_name)
        TextView tv_name;
        @BindView(R.id.txt_mobile_no)
        TextView txt_mobile_no;

        @BindView(R.id.txt_type)
        TextView txt_type;

        @BindView(R.id.card_view_detail)
        CardView card_view_detail;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
//            lnr_userType.setVisibility(View.VISIBLE);
        }
    }
}
