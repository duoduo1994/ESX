package com.example.administrator.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.entity.TaocanDetialBean;
import com.example.administrator.myapplication.R;
import com.example.administrator.utils.BaseRecyclerAdapter;
import com.example.administrator.utils.BaseRecyclerHolder;

import static android.R.id.list;

/**
 * Created by WangYueli on 2016/9/28.
 */

public class TaocanCommentFragment extends Fragment {
    private RecyclerView rcv;
    private TextView tv_comment_num;

    View v;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v=LayoutInflater.from(getActivity()).inflate(R.layout.fragment_taocan_comment,null);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    private void initView(View v) {
        rcv= (RecyclerView) v.findViewById(R.id.rcv_taocan_comment);
        tv_comment_num= (TextView) v.findViewById(R.id.tv_taocancomment_num);
//        BaseRecyclerAdapter<TaocanDetialBean> rcvadapter2=new BaseRecyclerAdapter<TaocanDetialBean>(getActivity(),list,R.layout.taocan_item_2) {
//            @Override
//            public void convert(BaseRecyclerHolder holder, TaocanDetialBean item, int position, boolean isScrolling) {
////                holder.setImageResource(R.id.tv_dishname_taocandetail_2,R.mipmap.ic_launcher);
//                holder.setText(R.id.tv_dishname_taocandetail_2,item.getCaidan().get(0).getDishName());
//                holder.setText(R.id.tv_storage_taocandetail_2,"kakakakakaka");
//                holder.setText(R.id.tv_dishweight_taocandetail_2,"kakakakakaka");
//            }
//        };
    }
}
