package com.example.administrator.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.myapplication.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AssetDetailActivity extends AppCompatActivity {

    @BindView(R.id.btn_back)
    Button btnBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_more)
    ImageView ivMore;
    @BindView(R.id.rcv_asset_detail)
    RecyclerView rcvAssetDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asset_detail);
        ButterKnife.bind(this);
        tvTitle.setText("收支明细");
        btnBack.setOnClickListener(v -> AssetDetailActivity.this.finish());
    }
}
