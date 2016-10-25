package com.example.administrator.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.administrator.entity.MyOrderBean;
import com.example.administrator.myapplication.R;
import com.example.administrator.utils.BaseRecyclerAdapter;
import com.example.administrator.utils.BaseRecyclerHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WangYueli on 2016/9/30.
 */

public class MyOrderFragment extends Fragment {
   private RecyclerView rcv;
    private BaseRecyclerAdapter<MyOrderBean> bAdapter;
    private BaseRecyclerAdapter<MyOrderBean.GamishBean> iAdapter;
    List<MyOrderBean> mList;
    List<MyOrderBean.GamishBean> iList;
    String d;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=LayoutInflater.from(getActivity()).inflate(R.layout.fragment_my_order,null);
        rcv= (RecyclerView) v.findViewById(R.id.rcv_frag_my_order);
        return v;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        d=bundle.getString("data");
        Toast.makeText(getActivity(),d,Toast.LENGTH_LONG).show();
        mList=new ArrayList<>();
        iList=new ArrayList<>();
        initData(d);
        super.onCreate(savedInstanceState);
    }
    private void initData(String d) {
        for (int i = 0; i < 5; i++) {
            iList.add(new MyOrderBean.GamishBean(d+"GBname",d+"23",d+"100.00"));
            mList.add(new MyOrderBean(d+"goods",d+"12",d+"344.66",iList));
        }
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initAdapter();
    }
    private void initAdapter() {
        bAdapter=new BaseRecyclerAdapter<MyOrderBean>(getActivity(),mList,R.layout.item_myorder) {
            @Override
            public void convert(BaseRecyclerHolder holder, MyOrderBean item, int position, boolean isScrolling) {
                holder.setText(R.id.tv_myorder_No,item.getName());
                holder.setText(R.id.tv_price_myorder,"合计：￥ "+item.getTotalPrice()+"（含运费 0.00）");
                iAdapter=new BaseRecyclerAdapter<MyOrderBean.GamishBean>(getActivity(),item.getGamish(),R.layout.item_myorder_detral) {
                    @Override
                    public void convert(BaseRecyclerHolder holder, MyOrderBean.GamishBean item2, int position, boolean isScrolling) {
                        holder.setText(R.id.tv_count_order,"*"+item2.getTotalCount());
                        holder.setText(R.id.tv_goods_name_order,"商品名称："+item2.getTotalCount());
                        holder.setText(R.id.tv_pei_price_order,"合计：￥ "+item2.getTotalPrice()+"（含运费 0.00）");
                    }
                };
                RecyclerView ircv = holder.getView(R.id.rcv_order_content);
                ircv.setLayoutManager(new LinearLayoutManager(getActivity()));
                ircv.setAdapter(iAdapter);
            }
        };
        rcv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rcv.setAdapter(bAdapter);
    }
}
