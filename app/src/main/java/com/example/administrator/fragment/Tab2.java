package com.example.administrator.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.administrator.entity.ProBean;
import com.example.administrator.myapplication.R;
import com.example.administrator.net.RetrofitUtil;
import com.example.administrator.utils.LogUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.R.id.list;

/**
 * Created by ${WuQiLian} on 2016/9/6.
 */
public class Tab2 extends Fragment{
    private View view;
    RetrofitUtil<ProBean> pu;
    List<ProBean> l;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        pu=new RetrofitUtil<>(getActivity());
        l=new ArrayList<>();
        view = LayoutInflater.from(getActivity()).inflate(
                R.layout.item_2, null);
        retroListDemo();

        return view;
    }

    private void retroListDemo() {

        //http://120.27.141.95:8221/ashx/Promoting.ashx?Function=GetRobberyProducts
        Map<String, String> map = new HashMap<>();
        map.put("Function", "GetRobberyProducts");
        pu.getListDataFromNet("Promoting", map, ProBean.class, new RetrofitUtil.CallBack2<ProBean>() {
            @Override
            public void onLoadListDataComplete(List<ProBean> list) {
                Toast.makeText(getActivity(),list.get(0).toString()+"123",Toast.LENGTH_LONG).show();
                LogUtils.i("111222",list.toString()+"123");
            }

            @Override
            public void onLoadingDataFailed(Throwable t) {
                LogUtils.i("111222","errorr123");
            }
        });
//        pu.getListDataFromNet("Promoting", map,  new RetrofitUtil.CallBack<List<ProBean>>() {
//            @Override
//            public void onLoadingDataComplete(List<ProBean> body) {
//
//            }
//
//            @Override
//            public void onLoadListDataComplete(List list) {
//                Toast.makeText(getActivity(),list.get(0).toString()+"123",Toast.LENGTH_LONG).show();
//                LogUtils.i("111222",list.get(0).toString()+"123");
//            }
//
//            @Override
//            public void onLoadingDataFailed(Throwable t) {
//                Toast.makeText(getActivity(),"error123",Toast.LENGTH_LONG).show();
//            }
//        });
//        pu.getBeanDataFromNet("Promoting", map, l.getClass(), new RetrofitUtil.CallBack<List<ProBean>>() {
//            @Override
//            public void onLoadingDataComplete(List<ProBean> body) {
//
//            }
//
//            @Override
//            public void onLoadListDataComplete(List list) {
//
//            }
//
//            @Override
//            public void onLoadingDataFailed(Throwable t) {
//
//            }
//        });
    }
}
