package com.dies.lionbuilding.activity;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dies.lionbuilding.R;
import com.dies.lionbuilding.apiservice.ApiConstants;
import com.dies.lionbuilding.apiservice.ApiService;
import com.dies.lionbuilding.apiservice.ApiServiceCreator;
import com.dies.lionbuilding.application.SessionManager;
import com.dies.lionbuilding.application.Utility;
import com.dies.lionbuilding.model.GiftModel;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GiftDetailActivity extends AppCompatActivity {


    @BindView(R.id.tv_p_point)
    TextView tv_p_point;

    @BindView(R.id.product_image)
    ImageView product_image;

    @BindView(R.id.txt_p_desc)
    TextView txt_p_desc;

    @BindView(R.id.btn_redeem)
    TextView btn_redeem;

    SessionManager sessionManager;
    ApiService apiservice;
    ArrayList<GiftModel.Data> arrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gift_detail);
        ButterKnife.bind(this);

        sessionManager = new SessionManager(this);
        apiservice = ApiServiceCreator.createService("latest");

        arrayList=Utility.getAppcon().getSession().arrayListGift;


        Picasso.with(this).load(ApiConstants.IMAGE_URL + arrayList.get(0).getOffer_image())
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .fit()
                .into(product_image);
        tv_p_point.setText(arrayList.get(0).getGft_point());
        txt_p_desc.setText(Html.fromHtml(arrayList.get(0).getOffer_discription()));

        btn_redeem.setOnClickListener(view -> {
            int tp=Integer.parseInt(sessionManager.getTotalPoint());
            int point=Integer.parseInt(arrayList.get(0).getGft_point());
            if (tp<point){
                Toast.makeText(this,"Not Enough Point",Toast.LENGTH_SHORT).show();
            }else {

            }
        });
    }
}
