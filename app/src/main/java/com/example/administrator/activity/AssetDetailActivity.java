package com.example.administrator.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.entity.PayDetailbean;
import com.example.administrator.myapplication.R;
import com.example.administrator.net.RetrofitUtil;
import com.example.administrator.utils.BaseRecyclerAdapter;
import com.example.administrator.utils.BaseRecyclerHolder;
import com.example.administrator.utils.LocalStorage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.administrator.activity.StartActivity.t;
import static com.example.administrator.myapplication.R.id.map;
import static com.example.administrator.myapplication.R.id.parallax;

public class AssetDetailActivity extends AppCompatActivity {

    @BindView(R.id.btn_back)
    Button btnBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_more)
    ImageView ivMore;
    @BindView(R.id.rcv_asset_detail)
    RecyclerView rcvAssetDetail;
    List<PayDetailbean> pList;
    BaseRecyclerAdapter<PayDetailbean> pAdapter;
    RetrofitUtil<PayDetailbean> pRetrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asset_detail);
        ButterKnife.bind(this);
        pList=new ArrayList<>();
        pRetrofit=new RetrofitUtil<>(this);
        tvTitle.setText("收支明细");
        btnBack.setOnClickListener(v -> AssetDetailActivity.this.finish());
        initData();
    }

    private void initAdapter(List<PayDetailbean> dlist) {
        pAdapter=new BaseRecyclerAdapter<PayDetailbean>(AssetDetailActivity.this,dlist,R.layout.item_pay_detail) {
            @Override
            public void convert(BaseRecyclerHolder holder, PayDetailbean item, int position, boolean isScrolling) {
                holder.setText(R.id.tv_pay_remain,"余额："+item.getResualtValue());
                holder.setText(R.id.tv_pay_date,item.getDate());
                holder.setText(R.id.tv_pay_remain,"余额："+item.getResualtValue());
            }
        };
        rcvAssetDetail.setLayoutManager(new LinearLayoutManager(AssetDetailActivity.this));
        rcvAssetDetail.setAdapter(pAdapter);
    }

    private void initData() {
        for (int i = 0; i <3 ; i++) {
            pList.add(new PayDetailbean("2015-6-8","-34.98","4576.90"));
        }
        if(!LocalStorage.get("LoginStatus").equals("login")){
            Toast.makeText(AssetDetailActivity.this,"您还没有登录哦",Toast.LENGTH_LONG).show();
        }else {
        Map<String,String> map=new HashMap<>();
        map.put("Function","HttpQueryPayment");
        map.put("UserTel", LocalStorage.get("Usertel").toString());
        map.put("UserPhyAdd",LocalStorage.get("strUniqueId").toString());
        pRetrofit.getListDataFromNet("User", map, PayDetailbean.class, new RetrofitUtil.CallBack2<PayDetailbean>() {
            @Override
            public void onLoadListDataComplete(List<PayDetailbean> list) {
                pList.addAll(list);
                initAdapter(pList);
                pAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLoadingDataFailed(Throwable t) {
                Toast.makeText(AssetDetailActivity.this,"网络异常"+t.toString(),Toast.LENGTH_LONG).show();
            }
        });
    }
    }
}
