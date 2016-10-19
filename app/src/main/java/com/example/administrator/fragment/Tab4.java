package com.example.administrator.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.administrator.activity.AdressManageActivity;
import com.example.administrator.activity.LoginInActivity;
import com.example.administrator.activity.MyOrderListActivity;
import com.example.administrator.myapplication.R;
import com.example.administrator.utils.ElasticScrollView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ${WuQiLian} on 2016/9/6.
 */
public class Tab4 extends Fragment {
    @BindView(R.id.my_qingtie)
    LinearLayout myQingtie;
    @BindView(R.id.my_xishitang)
    LinearLayout myXishitang;
    @BindView(R.id.my_address)
    LinearLayout myAddress;
    @BindView(R.id.my_coupon)
    LinearLayout myCoupon;
    @BindView(R.id.my_package)
    LinearLayout myPackage;
    @BindView(R.id.my_collect)
    LinearLayout myCollect;
    @BindView(R.id.my_cooperation)
    LinearLayout myCooperation;
    @BindView(R.id.my_identification)
    LinearLayout myIdentification;
    @BindView(R.id.about)
    LinearLayout about;
    @BindView(R.id.ll_mylogin)
    LinearLayout llMylogin;
    @BindView(R.id.order)
    LinearLayout order;
    private View view;
    ElasticScrollView elasticScrollView3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        view = LayoutInflater.from(getActivity()).inflate(
                R.layout.item_4, null);
        elasticScrollView3 = (ElasticScrollView) view.findViewById(R.id.elasticScrollView3);
        View ll = LayoutInflater.from(getActivity()).inflate(
                R.layout.item_geren_zhongxin, null);
        elasticScrollView3.addChild(ll, 1);


        ButterKnife.bind(this, view);
        myAddress.setOnClickListener(v -> startActivity(new Intent(getActivity(), AdressManageActivity.class)));
        llMylogin.setOnClickListener(v -> startActivity(new Intent(getActivity(), LoginInActivity.class)));
        order.setOnClickListener(v -> startActivity(new Intent(getActivity(), MyOrderListActivity.class)));
        return view;
    }
}
