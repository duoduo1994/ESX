package com.example.administrator.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.administrator.entity.ShoppingCarBean;
import com.example.administrator.myapplication.R;
import com.example.administrator.net.RetrofitUtil;
import com.example.administrator.utils.BaseRecyclerAdapter;
import com.example.administrator.utils.BaseRecyclerHolder;
import com.example.administrator.utils.LocalStorage;
import com.example.administrator.utils.LogUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.R.id.list;
import static com.example.administrator.myapplication.R.id.cb_select_all;
import static com.example.administrator.myapplication.R.id.map;

/**
 * Created by ${WuQiLian} on 2016/9/6.
 */
public class Tab3 extends Fragment implements View.OnClickListener{
    @BindView(R.id.btn_back)
    Button btnBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_more)
    ImageView ivMore;
    @BindView(R.id.cb_ysx_car)
    CheckBox cbYsx;
    @BindView(R.id.rcv_ysx_car)
    RecyclerView rcvYsx;
    @BindView(R.id.cb_select_xcxy)
    CheckBox cbXcxy;
    @BindView(R.id.rcv_xcxy_car)
    RecyclerView rcvXcxy;
    @BindView(R.id.cb_select_service)
    CheckBox cbService;
    @BindView(R.id.sc_cae_all)
    ScrollView scAll;
    @BindView(cb_select_all)
    CheckBox cbSelectAll;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.btn_jiesuan)
    Button btnJiesuan;
    private View view;
    List<CheckBox> lcb;
    ShoppingCarBean scBean;
    private double zongjia=0;
    private RetrofitUtil<ShoppingCarBean> CarRetrofit;
    private Map<Integer,Double> priceMap;
    private Map<Integer, Boolean> isCheckedMap;
    private Map<Integer, Boolean> isCheckedMap2;
    private Map<Integer, Boolean> isCheckedMapAll;
    BaseRecyclerAdapter<ShoppingCarBean.FeastBean> b1;
    BaseRecyclerAdapter<ShoppingCarBean.YSXFreashBean> b2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        view = LayoutInflater.from(getActivity()).inflate(
                R.layout.item_3, null);
        ButterKnife.bind(this, view);
        tvTitle.setText("购物车");
        CarRetrofit=new RetrofitUtil<>(getActivity());
        priceMap=new HashMap<>();
        isCheckedMap = new HashMap<>();
        isCheckedMap2= new HashMap<>();
        isCheckedMapAll= new HashMap<>();
        lcb=new ArrayList<>();
        lcb.add(cbYsx);lcb.add(cbXcxy);lcb.add(cbService);
        rcvYsx.setLayoutManager(new LinearLayoutManager(getActivity()));
        rcvXcxy.setLayoutManager(new LinearLayoutManager(getActivity()));
        cbYsx.setChecked(true);

        cbXcxy.setChecked(true);
        cbYsx.setOnClickListener(this);
        cbXcxy.setOnClickListener(this);
        cbService.setOnClickListener(this);
        cbSelectAll.setOnClickListener(this);
        initData();
        initAdapter();
        return view;
    }
    private void initAdapter() {
        b1=new BaseRecyclerAdapter<ShoppingCarBean.FeastBean>(getActivity(),scBean.getFeast(),R.layout.item_shopping_car) {
            @Override
            public void convert(BaseRecyclerHolder holder, ShoppingCarBean.FeastBean item, int position, boolean isScrolling) {
                ((CheckBox)holder.getView(R.id.cb_item_scar)).setChecked(isCheckedMap.get(position));
                priceMap.put(position,10.0);
                ((CheckBox)holder.getView(R.id.cb_item_scar)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        isCheckedMap.put(position,isChecked);
                        if(isChecked){
                            zongjia =zongjia+10;
                        }else{
                            zongjia =zongjia-10;
                        }
                        tvPrice.setText(zongjia +" ");
                        btnJiesuan.setText("结算（"+zongjia+")");
//                        isCheckedMap2.put(position,isChecked);
//                        isa(cbYsx,isCheckedMap);
                        isa(cbXcxy,isCheckedMap);
                    }
                });
                holder.setText(R.id.tv_goods_name_scar,"套餐1");
                holder.setText(R.id.tv_goods_price_scar,"10");
                holder.setText(R.id.tv_count_scar,"*12");
                holder.setImageResource(R.id.iv_item_scar,R.mipmap.nopic);
            }
        };
        b2=new BaseRecyclerAdapter<ShoppingCarBean.YSXFreashBean>(getActivity(),scBean.getYSXFreash(),R.layout.item_shopping_car) {
            @Override
            public void convert(BaseRecyclerHolder holder, ShoppingCarBean.YSXFreashBean item, int position, boolean isScrolling) {
                ((CheckBox)holder.getView(R.id.cb_item_scar)).setChecked(isCheckedMap2.get(position));
                priceMap.put(position,10.0);
                ((CheckBox)holder.getView(R.id.cb_item_scar)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                        isCheckedMap.put(position,isChecked);
                        isCheckedMap2.put(position,isChecked);
                        if(isChecked){
                            zongjia =zongjia+10;
                        }else{
                            zongjia =zongjia-10;
                        }
                        tvPrice.setText(zongjia +" ");
                        btnJiesuan.setText("结算（"+zongjia+")");

                        isa(cbYsx,isCheckedMap2);
//                        isa(cbXcxy,isCheckedMap2);
                    }
                });
                holder.setText(R.id.tv_goods_name_scar,"套餐1");
                holder.setText(R.id.tv_goods_price_scar,"10");
                holder.setText(R.id.tv_count_scar,"*12");
                holder.setImageResource(R.id.iv_item_scar,R.mipmap.nopic);
            }
        };
        rcvXcxy.setAdapter(b1);
        rcvYsx.setAdapter(b2);
    }

    private void initData() {
        LogUtils.isLogin(getActivity());
        Map<String,String> map=new HashMap<>();
        map.put("Component","ShoppingCart");
        map.put("Function","HttpGetEntitysMainPage");
        map.put("UserTel", LocalStorage.get(getContext().getApplicationContext(),"UserTel"));
        map.put("UserPhyAdd",LocalStorage.get(getContext().getApplicationContext(),"strUniqueId"));
        CarRetrofit.getBeanDataFromNet("Shopping", map, ShoppingCarBean.class, new RetrofitUtil.CallBack<ShoppingCarBean>() {
            @Override
            public void onLoadingDataComplete(ShoppingCarBean body) {
                scBean=body;
            }

            @Override
            public void onLoadingDataFailed(Throwable t) {

            }
        });
        //http://120.27.141.95:8221/ashx/Shopping.ashx?Component=ShoppingCart&Function=HttpGetEntitysMainPage&UserTel=18727823918&UserPhyAdd=333333
        scBean=new ShoppingCarBean();
        List<ShoppingCarBean.FeastBean> l=new ArrayList<>();
        List<ShoppingCarBean.YSXFreashBean> l2=new ArrayList<>();
        for (int i = 0; i < 3; i++) {
           l.add(new ShoppingCarBean.FeastBean());
            l2.add(new ShoppingCarBean.YSXFreashBean());
            isCheckedMap.put(i,true);
            isCheckedMap2.put(i,true);
            zongjia =60.00;
        }
        scBean.setFeast(l);
        scBean.setYSXFreash(l2);
        for (int i = 0; i < priceMap.size(); i++) {
                zongjia=zongjia+priceMap.get(i);
        }
        tvPrice.setText(zongjia +"");
        btnJiesuan.setText("结算（"+zongjia+")");
    }
    private void isa(CheckBox cb, Map<Integer,Boolean> map){
                            if(map.containsValue(false)){
                                cb.setChecked(false);
                            }else {
                                cb.setChecked(true);
                            }
//        for (int i = 0; i < priceMap.size(); i++) {
//            if(map.get(i)){
//                zongjia=zongjia+priceMap.get(i);
//            }
//        }
//        tvPrice.setText(zongjia +" ");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cb_ysx_car:
                if(cbYsx.isChecked()){
                    isCheckedMapAll.put(0,true);
                    for (int i = 0; i <b2.getItemCount() ; i++) {
                        isCheckedMap2.put(i,true);
                    }

                }else {
                    isCheckedMapAll.put(0, false);
                    for (int i = 0; i < b2.getItemCount(); i++) {
                        isCheckedMap2.put(i, false);
                    }
                }
                isa(cbSelectAll,isCheckedMapAll);
                b2.notifyDataSetChanged();
//                YsxCb();
                break;
            case R.id.cb_select_xcxy:
                if(cbXcxy.isChecked()){
                    isCheckedMapAll.put(1,true);
                    for (int i = 0; i <b1.getItemCount() ; i++) {
                        isCheckedMap.put(i,true);
                    }
                }else {
                    isCheckedMapAll.put(1, false);
                    for (int i = 0; i < b1.getItemCount(); i++) {
                        isCheckedMap.put(i, false);
                    }
                }
                isa(cbSelectAll,isCheckedMapAll);
                b1.notifyDataSetChanged();
//                YsxCb();
                break;
            case cb_select_all:
                if(cbSelectAll.isChecked()){
                    cbYsx.setChecked(true);
                    cbXcxy.setChecked(true);
                    cbService.setChecked(true);
                }else {
                    cbXcxy.setChecked(false);
                    cbYsx.setChecked(false);
                    cbService.setChecked(false);
                }
                YsxCb();
                XcxyCb();
                break;
            case R.id.cb_select_service:
                if(cbService.isChecked()){
                    cbService.setChecked(true);
                    isCheckedMapAll.put(2,true);
                }else{
                    cbService.setChecked(false);
                    isCheckedMapAll.put(2,false);
                }
                isa(cbSelectAll,isCheckedMapAll);
                break;
        }

    }

    private void YsxCb() {
        if(cbYsx.isChecked()){
            isCheckedMapAll.put(0,true);
            for (int i = 0; i <b1.getItemCount() ; i++) {
                isCheckedMap.put(i,true);
            }

        }else {
            isCheckedMapAll.put(0,false);
            for (int i = 0; i <b1.getItemCount() ; i++) {
                isCheckedMap.put(i,false);
            }
        }
        isa(cbSelectAll,isCheckedMapAll);
        b1.notifyDataSetChanged();
    }
    private void XcxyCb() {
        if(cbXcxy.isChecked()){
            isCheckedMapAll.put(0,true);
            for (int i = 0; i <b2.getItemCount() ; i++) {
                isCheckedMap2.put(i,true);
            }

        }else {
            isCheckedMapAll.put(0,false);
            for (int i = 0; i <b2.getItemCount() ; i++) {
                isCheckedMap2.put(i,false);
            }
        }
        isa(cbSelectAll,isCheckedMapAll);
        b2.notifyDataSetChanged();
    }

}
