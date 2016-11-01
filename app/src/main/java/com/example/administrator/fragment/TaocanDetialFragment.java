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

import com.example.administrator.entity.PeicaiBean;
import com.example.administrator.entity.TaocanDetailNBean;
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
    View view;
    BaseRecyclerAdapter<TaocanDetailNBean.ContentBean> rcvadapter1;
    BaseRecyclerAdapter<PeicaiBean> rcvadapter2;
    private List<TaocanDetailNBean.ContentBean> list = new ArrayList<>();
    private List<PeicaiBean> pclist = new ArrayList<>();
    private List<PeicaiBean> peicaiID=new ArrayList<>();

    public void updateData(List<PeicaiBean> pclist){
        this.pclist = pclist;
         rcvadapter2 = new BaseRecyclerAdapter<PeicaiBean>(getActivity(), pclist, R.layout.taocan_item_2) {
            @Override
            public void convert(BaseRecyclerHolder holder, PeicaiBean item, int position, boolean isScrolling) {
//                holder.setImageResource(R.id.tv_dishname_taocandetail_2,R.mipmap.ic_launcher);
                holder.setText(R.id.tv_dishname_taocandetail_2, item.getName());
            }
        };
        rcvadapter2.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position) {
                Toast.makeText(getActivity(), position + "click", Toast.LENGTH_LONG).show();
                peicaiID.add(pclist.get(position));
            }
        });
        fud.setLayoutManager(new LinearLayoutManager(getActivity()));
        fud.setAdapter(rcvadapter2);
    }
    public void updateData1(List<TaocanDetailNBean.ContentBean> list){
        this.list = list;
        rcvadapter1 = new BaseRecyclerAdapter<TaocanDetailNBean.ContentBean>(getActivity(), list, R.layout.taocan_item_1) {
            @Override
            public void convert(BaseRecyclerHolder holder, TaocanDetailNBean.ContentBean item, int position, boolean isScrolling) {
                holder.setImageResource(R.id.iv_taocandetail_1, R.mipmap.ic_launcher);
                holder.setImageByUrl(R.id.iv_taocandetail_1,item.getImageUrl());
                holder.setText(R.id.tv_dishname_taocandetail_1, item.getName());
                holder.setText(R.id.tv_dishprice_taocandetail_1, item.getPrice());
            }
        };

        zhud.setLayoutManager(new LinearLayoutManager(getActivity()));
        zhud.setAdapter(rcvadapter1);

    }

    public TaocanDetialFragment() {
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_taocan_detail, null);

        super.onCreate(savedInstanceState);
        pei = (RadioButton) view.findViewById(R.id.rb_taocandetail_pei);
        zhu = (RadioButton) view.findViewById(R.id.rb_taocandetail_zhu);
        zhud = (RecyclerView) view.findViewById(R.id.rcv_zhu_taocan_detail);
        fud = (RecyclerView) view.findViewById(R.id.rcv_fu_taocan_detail);
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
public void getList(LCallback callback){
List<PeicaiBean> pl =peicaiID;
    callback.getList(pl);
}
    public interface LCallback{
        public void getList(List<PeicaiBean> plist);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
