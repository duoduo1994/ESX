package com.example.administrator.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.ab.view.Connect;
import com.example.administrator.activity.AdressManageActivity;
import com.example.administrator.activity.LoginInActivity;
import com.example.administrator.activity.MyAssetActivity;
import com.example.administrator.activity.MyHallSchList;
import com.example.administrator.activity.MyOrderListActivity;
import com.example.administrator.activity.QiangDanActivity;
import com.example.administrator.activity.WoDeQingTieAllActivity;
import com.example.administrator.myapplication.R;
import com.example.administrator.utils.Diolg;
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
    @BindView(R.id.btn_back)
    Button btnBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_more)
    ImageView ivMore;
    private View view;
    @BindView(R.id.cooktext)
    TextView cooktext;
    ElasticScrollView elasticScrollView3;
    Connect connect=LoginInActivity.connect;
    Handler myhandler=Connect.handler;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        connect=new Connect();
        view = LayoutInflater.from(getActivity()).inflate(
                R.layout.item_4, null);
        elasticScrollView3 = (ElasticScrollView) view.findViewById(R.id.elasticScrollView3);
        View ll = LayoutInflater.from(getActivity()).inflate(
                R.layout.item_geren_zhongxin, null);
        elasticScrollView3.addChild(ll, 1);


        ButterKnife.bind(this, view);
        tvTitle.setText("个人中心");
        isLogin();
        cichan.setOnClickListener(v -> startActivity(new Intent(getActivity(), MyAssetActivity.class)));
        llMyAsset.setOnClickListener(v -> startActivity(new Intent(getActivity(), MyAssetActivity.class)));
        myAddress.setOnClickListener(v -> startActivity(new Intent(getActivity(), AdressManageActivity.class)));
//        llMylogin.setOnClickListener(v -> startActivity(new Intent(getActivity(), LoginInActivity.class)));
        order.setOnClickListener(v -> startActivity(new Intent(getActivity(), MyOrderListActivity.class)));
        myQingtie.setOnClickListener(v -> startActivity(new Intent(getActivity(), WoDeQingTieAllActivity.class)));
        myXishitang.setOnClickListener(v -> startActivity(new Intent(getActivity(), MyHallSchList.class)));
        cooktext.setOnClickListener(v -> startActivity(new Intent(getActivity(), QiangDanActivity.class)));
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Diolg(getActivity(), "拨打电话", "返回", "客服电话400-8261-707", "联系客服",
                        3);
            }
        });

        LocalStorage.set("LoginStatus","out");
        JieShou();
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
                    connect.tuichu(myhandler);
                }
            }).setPositiveButton("否", new DialogInterface.OnClickListener() {
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



    public void JieShou() {
        myhandler = new Handler() {
            public void handleMessage(Message msg) {
                if (msg.what == 0x456) {
                    String s = msg.obj.toString();
                    if(s.equals("退出成功")){
                        System.out.println(123456);
                        LocalStorage.set("LoginStatus","out");
                        isLogin();
                    }
                    Toast.makeText(getActivity(),s,Toast.LENGTH_LONG).show();
                }
            }
        };
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
