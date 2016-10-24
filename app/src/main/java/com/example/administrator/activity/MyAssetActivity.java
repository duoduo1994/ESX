package com.example.administrator.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.myapplication.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyAssetActivity extends AppCompatActivity {

    @BindView(R.id.btn_back)
    Button btnBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_more)
    ImageView ivMore;
    @BindView(R.id.ll_my_asset)
    LinearLayout llMyAsset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_asset);
        ButterKnife.bind(this);
        btnBack.setOnClickListener(v -> MyAssetActivity.this.finish());
        tvTitle.setText("余额");
        llMyAsset.setOnClickListener(v -> startActivity(new Intent(MyAssetActivity.this,AssetDetailActivity.class)));
    }
}
