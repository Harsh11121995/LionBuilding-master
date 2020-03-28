package com.dies.lionbuilding.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.dies.lionbuilding.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ContactDetail extends AppCompatActivity {
    @BindView(R.id.back_icon)
    ImageView back_icon;
    @BindView(R.id.toolbar_Title)
    TextView tv_toolbar_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_detail);
        ButterKnife.bind(this);

        tv_toolbar_title.setText("Contact");
        back_icon.setOnClickListener(view -> {
            finish();
        });
    }
}
