package com.example.administrator.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.activity.DingDan_XiangQing_xcxy;
import com.example.administrator.activity.DingDan_XiangQing_ysx;
import com.example.administrator.myapplication.R;

import java.util.List;

/**
 * Created by @vitovalov on 30/9/15.
 */
@SuppressLint("ValidFragment")
public class TabFragment extends Fragment {

    private MyAdapter mAdapter;
    private String btName;


private List<String> list;

    public TabFragment(List<String> list, String btName){
    this.list=list;
        this.btName=btName;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment, container, false);

        ListView recyclerView = (ListView) view.findViewById(
                R.id.fragment_list_rv);

   //

        mAdapter = new MyAdapter();
        recyclerView.setAdapter(mAdapter);

        return view;
    }



    private class MyAdapter extends BaseAdapter {
        LayoutInflater ll;

        MyAdapter() {
            ll = LayoutInflater.from(getActivity());
        }

        @Override
        public int getCount() {

            return list.size();
        }

        @Override
        public Object getItem(int arg0) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public long getItemId(int arg0) {
            // TODO Auto-generated method stub
            return 0;
        }

        /*
         * 重写view
         */
        @Override
        public View getView(int arg0, View arg1, ViewGroup arg2) {
          ViewHolder v = null;
            if (arg1 == null) {
                arg1 = ll.inflate(R.layout.qiangdan_item, null);
                v = new ViewHolder();

                v.wode_dingdan_bianhao = (TextView) arg1
                        .findViewById(R.id.wode_dingdan_bianhao);
                v.chushi_qiangdan_jine = (TextView) arg1
                        .findViewById(R.id.chushi_qiangdan_jine);
                v.chushi_qiangdan_yudingriqi = (TextView) arg1
                        .findViewById(R.id.chushi_qiangdan_yudingriqi);
                v.chushi_qiangdan_xiyanriqi = (TextView) arg1
                        .findViewById(R.id.chushi_qiangdan_xiyanriqi);
                v.chushi_qiangdan_zhuoshu = (TextView) arg1
                        .findViewById(R.id.chushi_qiangdan_zhuoshu);
                v.chushi_qiangdan_leixing = (TextView) arg1
                        .findViewById(R.id.chushi_qiangdan_leixing);
                v.chushi_qiangdan_didian = (TextView) arg1
                        .findViewById(R.id.chushi_qiangdan_didian);
                v.qiangdan= (Button) arg1
                        .findViewById(R.id.qiangdan);
                v.chakan_xiangqing=(Button) arg1
                        .findViewById(R.id.chakan_xiangqing);
                arg1.setTag(v);

            } else {
                v = (ViewHolder) arg1.getTag();
            }


            v.wode_dingdan_bianhao.setText(list.get(arg0));
            if(btName.equals("立即抢单")){
                v.qiangdan.setText("立即抢单");

            }else if(btName.equals("执行签到")){
                v.qiangdan.setText("执行签到");
            }else{
                v.qiangdan.setVisibility(View.GONE);
            }
            v.qiangdan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
            v.chakan_xiangqing.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(btName.equals("立即抢单")){
                        startActivity(new Intent(getActivity(), DingDan_XiangQing_xcxy.class));
                    }else{
                        startActivity(new Intent(getActivity(), DingDan_XiangQing_ysx.class));
                    }

                }        ;
            });



            return arg1;
        }

        private class ViewHolder {
            TextView wode_dingdan_bianhao;
            TextView chushi_qiangdan_jine;
            TextView chushi_qiangdan_yudingriqi;
            TextView chushi_qiangdan_xiyanriqi;
            TextView chushi_qiangdan_zhuoshu;
            TextView chushi_qiangdan_leixing;
            TextView chushi_qiangdan_didian;
            Button qiangdan;

            Button chakan_xiangqing;
        }

    }
















}
