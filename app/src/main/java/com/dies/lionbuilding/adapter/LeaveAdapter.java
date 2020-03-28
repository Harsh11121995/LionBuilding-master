package com.dies.lionbuilding.adapter;

import android.content.Context;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dies.lionbuilding.R;
import com.dies.lionbuilding.model.LeaveModel;
import com.dies.lionbuilding.model.RouteModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LeaveAdapter extends RecyclerView.Adapter<LeaveAdapter.MyViewHolder> {

    Context context;
    List<LeaveModel.Data> arrayList;

    public LeaveAdapter(Context context, List<LeaveModel.Data> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public LeaveAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.leave_layout,parent,false);
        return new LeaveAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LeaveAdapter.MyViewHolder holder, int position) {

        holder.txt_lv_start.setText(arrayList.get(position).getLv_start_date());
        holder.txt_lv_end.setText(arrayList.get(position).getLv_end_date());
        holder.txt_lv_reason.setText(Html.fromHtml(arrayList.get(position).getLv_desc()));

        if (arrayList.get(position).getLv_approved().equals("0")){
            holder.image_status.setImageResource(R.drawable.disapprove);
        }else if (arrayList.get(position).getLv_approved().equals("1")){
            holder.image_status.setImageResource(R.drawable.approve);
        }else {
            holder.image_status.setImageResource(R.drawable.pending);
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_lv_start)
        TextView txt_lv_start;

        @BindView(R.id.txt_lv_end)
        TextView txt_lv_end;

        @BindView(R.id.txt_lv_reason)
        TextView txt_lv_reason;

        @BindView(R.id.image_status)
        ImageView image_status;




        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

        }
    }
}
