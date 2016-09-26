package com.example.administrator.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.activity.HomeActivity;
import com.example.administrator.myapplication.R;
import com.example.administrator.utils.ElasticScrollView;

/**
 * Created by ${WuQiLian} on 2016/9/6.
 */
public class Tab4 extends Fragment {
    private View view;
    ElasticScrollView elasticScrollView3;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        view = LayoutInflater.from(getActivity()).inflate(
                R.layout.item_4, null);
        elasticScrollView3= (ElasticScrollView) view.findViewById(R.id.elasticScrollView3);
        View ll = LayoutInflater.from(getActivity()).inflate(
                R.layout.item_geren_zhongxin, null);
        elasticScrollView3.addChild(ll, 1);



        return view;
    }
}
