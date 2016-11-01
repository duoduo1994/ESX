package com.example.administrator.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.entity.HotTancanAllBean;
import com.example.administrator.myapplication.R;
import com.example.administrator.net.RetrofitUtil;
import com.example.administrator.utils.BaseRecyclerAdapter;
import com.example.administrator.utils.BaseRecyclerHolder;
import com.example.administrator.utils.LogUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.R.attr.id;

public class HotTaocanActivity extends AppCompatActivity {

    @BindView(R.id.rcv_hot_taocan)
    RecyclerView rcvHotTaocan;
    RetrofitUtil<HotTancanAllBean> retrofitUtil;
    List<HotTancanAllBean> listt;
    BaseRecyclerAdapter<HotTancanAllBean> adapter;
    @BindView(R.id.btn_back)
    Button btnBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_more)
    ImageView ivMore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hot_taocan);
        retrofitUtil = new RetrofitUtil<>(this);
        listt = new ArrayList<>();
        ButterKnife.bind(this);
        tvTitle.setText("人气套餐");
        btnBack.setOnClickListener(v -> this.finish());
        getData();

    }

    private void getData() {
        //http://120.27.141.95:8221/ashx/Shopping.ashx?Component=Product&ProModule=FreashSetMeal&Function=HttpQueryAllEntitys
        Map<String, String> map = new HashMap<>();
        map.put("Component", "Product");
        map.put("ProModule", "FreashSetMeal");
        map.put("Function", "HttpQueryAllEntitys");
        retrofitUtil.getListDataFromNet("Shopping", map, HotTancanAllBean.class, new RetrofitUtil.CallBack2<HotTancanAllBean>() {

            @Override
            public void onLoadListDataComplete(List<HotTancanAllBean> list) {
                listt = list;
                LogUtils.i("renqitaocan", list.get(0).toString() + "fff");
                setAdapter(list);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onLoadingDataFailed(Throwable t) {
                LogUtils.i("renqitaocanThrowable", t.toString() + "999");
            }
        });
    }
    private void setAdapter(List<HotTancanAllBean> listt) {
        rcvHotTaocan.setLayoutManager(new LinearLayoutManager(HotTaocanActivity.this, LinearLayoutManager.VERTICAL, false));
        adapter = new BaseRecyclerAdapter<HotTancanAllBean>(this, listt, R.layout.taocan_item) {
            @Override
            public void convert(BaseRecyclerHolder holder, HotTancanAllBean item, int position, boolean isScrolling) {
                holder.setText(R.id.tv_taocan_name, item.getBaseDate().getName());
                holder.setText(R.id.tv_taocan_price, item.getBaseDate().getPrice());
                holder.setText(R.id.tv_taocan_content, item.getContent());
                holder.setText(R.id.tv_taocan_num, item.getBaseDate().getSales());
                if (item.getBaseDate().getImageUrl().trim() == null) {
                    holder.setImageResource(R.id.iv_taocan_pic, R.mipmap.nopic);
                } else {
                    holder.setImageByUrl(R.id.iv_taocan_pic, item.getBaseDate().getImageUrl());
                }
            }
        };

        rcvHotTaocan.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position) {
                String ID = listt.get(position).getBaseDate().getID();
                String ImageUrl=listt.get(position).getBaseDate().getImageUrl();
                String Name=listt.get(position).getBaseDate().getName();
                String Price=listt.get(position).getBaseDate().getPrice();
                String Sales=listt.get(position).getBaseDate().getSales();
                //private String ID;
               // private String ImageUrl;
               // private String Name;Price
//                private String Sales;
                Intent i = new Intent(HotTaocanActivity.this, TaocanActivity.class);
                i.putExtra("taocanid", ID);
                i.putExtra("taocanImageUrl", ImageUrl);
                i.putExtra("taocanName", Name);
                i.putExtra("taocanPrice", Price);
                i.putExtra("taocanSales", Sales);
                startActivity(i);
            }
        });
    }
}
