package com.example.administrator.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.myapplication.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdressManageActivity extends AppCompatActivity {

    @BindView(R.id.rcv_addresslist)
    RecyclerView rcvAddresslist;
    @BindView(R.id.btn_add_address)
    Button btnAddAddress;
    @BindView(R.id.btn_back)
    Button btnBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adress_manage);
        ButterKnife.bind(this);
        tvTitle.setText("管理地址");
        btnBack.setOnClickListener(v -> AdressManageActivity.this.finish());
        btnAddAddress.setOnClickListener(v -> startActivity(new Intent(AdressManageActivity.this, AddaddressActivity.class)));
    }
}
