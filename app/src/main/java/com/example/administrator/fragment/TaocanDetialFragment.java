package com.example.administrator.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.administrator.entity.TaocanDetialBean;
import com.example.administrator.myapplication.R;
import com.example.administrator.utils.BaseRecyclerAdapter;
import com.example.administrator.utils.BaseRecyclerHolder;

import java.util.ArrayList;
import java.util.List;

import static com.example.administrator.myapplication.R.layout.item;

/**
 * Created by WangYueli on 2016/9/27.
 */

public class TaocanDetialFragment extends Fragment {
    private RadioButton pei;
    private RadioButton zhu;
    private RecyclerView zhud;
    private RecyclerView fud;
    private List<TaocanDetialBean> list = new ArrayList<>();

    public TaocanDetialFragment() {
    }

    @SuppressLint("ValidFragment")
    public TaocanDetialFragment(List<TaocanDetialBean> list) {
        this.list = list;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_taocan_detail, null);

        super.onCreate(savedInstanceState);
        pei = (RadioButton) view.findViewById(R.id.rb_taocandetail_pei);
        zhu = (RadioButton) view.findViewById(R.id.rb_taocandetail_zhu);
        zhud = (RecyclerView) view.findViewById(R.id.rcv_zhu_taocan_detail);
        fud = (RecyclerView) view.findViewById(R.id.rcv_fu_taocan_detail);
        BaseRecyclerAdapter<TaocanDetialBean> rcvadapter1 = new BaseRecyclerAdapter<TaocanDetialBean>(getActivity(), list, R.layout.taocan_item_1) {
            @Override
            public void convert(BaseRecyclerHolder holder, TaocanDetialBean item, int position, boolean isScrolling) {
                holder.setImageResource(R.id.iv_taocandetail_1, R.mipmap.ic_launcher);
                holder.setText(R.id.tv_dishname_taocandetail_1, item.getCaidan().get(0).getDishName());
                holder.setText(R.id.tv_dishprice_taocandetail_1, item.getCaidan().get(0).getPlace());
            }
        };
        BaseRecyclerAdapter<TaocanDetialBean> rcvadapter2 = new BaseRecyclerAdapter<TaocanDetialBean>(getActivity(), list, R.layout.taocan_item_2) {
            @Override
            public void convert(BaseRecyclerHolder holder, TaocanDetialBean item, int position, boolean isScrolling) {
//                holder.setImageResource(R.id.tv_dishname_taocandetail_2,R.mipmap.ic_launcher);
                holder.setText(R.id.tv_dishname_taocandetail_2, item.getCaidan().get(0).getDishName());
                holder.setText(R.id.tv_storage_taocandetail_2, "kakakakakaka");
                holder.setText(R.id.tv_dishweight_taocandetail_2, "kakakakakaka");
            }
        };
        rcvadapter2.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position) {
                Toast.makeText(getActivity(), position + "click", Toast.LENGTH_LONG).show();
            }
        });
        zhud.setLayoutManager(new LinearLayoutManager(getActivity()));
        fud.setLayoutManager(new LinearLayoutManager(getActivity()));
        zhud.setAdapter(rcvadapter1);
        fud.setAdapter(rcvadapter2);
        zhu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "sssss", Toast.LENGTH_SHORT).show();
                fud.setVisibility(View.GONE);
                zhud.setVisibility(View.VISIBLE);
            }
        });
        pei.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zhud.setVisibility(View.GONE);
                fud.setVisibility(View.VISIBLE);
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
