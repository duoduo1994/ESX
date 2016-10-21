package com.example.administrator.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.activity.AdressManageActivity;
import com.example.administrator.activity.LoginInActivity;
import com.example.administrator.activity.MyAssetActivity;
import com.example.administrator.activity.MyOrderListActivity;
import com.example.administrator.myapplication.R;
import com.example.administrator.utils.ElasticScrollView;
import com.example.administrator.utils.LocalStorage;

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
    @BindView(R.id.tv_username)
    TextView tvUsername;
    @BindView(R.id.tv_useraccount)
    TextView tvUseraccount;
    @BindView(R.id.cichan)
    TextView cichan;
    @BindView(R.id.ll_my_asset)
    LinearLayout llMyAsset;
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
        isLogin();
        cichan.setOnClickListener(v -> startActivity(new Intent(getActivity(), MyAssetActivity.class)));
        llMyAsset.setOnClickListener(v -> startActivity(new Intent(getActivity(), MyAssetActivity.class)));
        myAddress.setOnClickListener(v -> startActivity(new Intent(getActivity(), AdressManageActivity.class)));
//        llMylogin.setOnClickListener(v -> startActivity(new Intent(getActivity(), LoginInActivity.class)));
        order.setOnClickListener(v -> startActivity(new Intent(getActivity(), MyOrderListActivity.class)));


        return view;
    }

    private void isLogin() {
        String LoginStatus = LocalStorage.get(getActivity().getApplicationContext(), "LoginStatus");
        if (LoginStatus.equals("login")) {
            tvUsername.setText("亲,怎么称呼");
            tvUseraccount.setVisibility(View.VISIBLE);
            tvUseraccount.setText("账号：" + LocalStorage.get(getActivity().getApplicationContext(), "UserTel"));
            llMylogin.setOnClickListener(v -> new AlertDialog.Builder(getActivity()).setMessage("是否退出？").setNegativeButton("是", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    LocalStorage.set("LoginStatus", "out");
                    isLogin();
                }
            }).setPositiveButton("fou", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            }).show());
        } else {
            tvUsername.setText("点击登录");
            tvUseraccount.setVisibility(View.GONE);
            llMylogin.setOnClickListener(v -> startActivity(new Intent(getActivity(), LoginInActivity.class)));

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        isLogin();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            isLogin();
        }
    }
}
