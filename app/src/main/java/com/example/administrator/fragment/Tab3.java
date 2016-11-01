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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.entity.ShoppingCarBean;
import com.example.administrator.myapplication.R;
import com.example.administrator.net.RetrofitUtil;
import com.example.administrator.utils.BaseRecyclerAdapter;
import com.example.administrator.utils.BaseRecyclerHolder;
import com.example.administrator.utils.LocalStorage;
import com.example.administrator.utils.LogUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.administrator.myapplication.R.id.btn_delete;
import static com.example.administrator.myapplication.R.id.cb_select_all;


/**
 * Created by ${WuQiLian} on 2016/9/6.
 */
public class Tab3 extends Fragment implements View.OnClickListener {
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
    @BindView(R.id.cb_select_all)
    CheckBox cbSelectAll;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.btn_jiesuan)
    Button btnJiesuan;
    @BindView(R.id.addsch)
    ImageView addsch;
    @BindView(R.id.ch_select_del)
    CheckBox chSelectDel;
    @BindView(R.id.btn_delete)
    Button btnDelete;
    @BindView(R.id.rl_del)
    RelativeLayout rlDel;
    @BindView(R.id.rl_jiesuan)
    RelativeLayout rlJiesuan;
    @BindView(R.id.ll_ysx)
    LinearLayout llYsx;
    @BindView(R.id.ll_xcxy)
    LinearLayout llXcxy;
    private View view;
    List<CheckBox> lcb;
    ShoppingCarBean scBean=new ShoppingCarBean();
    private double zongjia = 0;
    private RetrofitUtil<ShoppingCarBean> CarRetrofit;
    private Map<Integer, Double> priceMap;
    private Map<Integer, Boolean> isCheckedMap;
    private Map<Integer, Boolean> isCheckedMap2;
    private Map<Integer, Boolean> isCheckedMapAll;
    BaseRecyclerAdapter<ShoppingCarBean.FeastBean.ContentBean> b1;
    BaseRecyclerAdapter<ShoppingCarBean.YSXFreashBean.ContentBean> b2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        view = LayoutInflater.from(getActivity()).inflate(
                R.layout.item_3, null);
        ButterKnife.bind(this, view);
        tvTitle.setText("购物车");
        CarRetrofit = new RetrofitUtil<>(getActivity());
        priceMap = new HashMap<>();
        isCheckedMap = new HashMap<>();
        isCheckedMap2 = new HashMap<>();
        isCheckedMapAll = new HashMap<>();
        lcb = new ArrayList<>();
        lcb.add(cbYsx);
        lcb.add(cbXcxy);
        lcb.add(cbService);
        initData();
//        initAdapter();
        rcvYsx.setLayoutManager(new LinearLayoutManager(getActivity()));
        rcvXcxy.setLayoutManager(new LinearLayoutManager(getActivity()));
//        cbYsx.setChecked(true);
//        cbService.setChecked(true);
//        cbXcxy.setChecked(true);
//        cbYsx.setOnClickListener(this);
//        cbXcxy.setOnClickListener(this);
//        cbService.setOnClickListener(this);
//        cbSelectAll.setOnClickListener(this);
//        ivMore.setOnClickListener(this);
//        btnDelete.setOnClickListener(this);

        return view;
    }

    private void initAdapter() {
        b1 = new BaseRecyclerAdapter<ShoppingCarBean.FeastBean.ContentBean>(getActivity(), scBean.getFeast().getContent(), R.layout.item_shopping_car) {
            @Override
            public void convert(BaseRecyclerHolder holder, ShoppingCarBean.FeastBean.ContentBean item, int position, boolean isScrolling) {
                ((CheckBox) holder.getView(R.id.cb_item_scar)).setChecked(isCheckedMap.get(position));
                priceMap.put(position, Double.parseDouble(item.getTotalPrice()));
                ((CheckBox) holder.getView(R.id.cb_item_scar)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        isCheckedMap.put(position, isChecked);
                        if (isChecked) {
                            zongjia = zongjia +Double.parseDouble(item.getTotalPrice());
                        } else {
                            zongjia = zongjia -Double.parseDouble(item.getTotalPrice());
                        }
                        tvPrice.setText(zongjia + " ");
                        btnJiesuan.setText("结算（" + zongjia + ")");
                        isall(cbXcxy, isCheckedMap, 1);
                    }
                });
                holder.setText(R.id.tv_goods_name_scar, item.getName());
                holder.setText(R.id.tv_goods_price_scar, item.getTotalPrice());
                holder.setText(R.id.tv_count_scar, item.getTotalCount());
                holder.setImageResource(R.id.iv_item_scar, R.mipmap.nopic);
            }
        };
        rcvXcxy.setAdapter(b1);

    }

    private void initAdapter2() {
        b2 = new BaseRecyclerAdapter<ShoppingCarBean.YSXFreashBean.ContentBean>(getActivity(), scBean.getYSXFreash().getContent(), R.layout.item_shopping_car) {
            @Override
            public void convert(BaseRecyclerHolder holder, ShoppingCarBean.YSXFreashBean.ContentBean item, int position, boolean isScrolling) {
                ((CheckBox) holder.getView(R.id.cb_item_scar)).setChecked(isCheckedMap2.get(position));
                priceMap.put(position, Double.parseDouble(item.getTotalPrice()));
                ((CheckBox) holder.getView(R.id.cb_item_scar)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        isCheckedMap2.put(position, isChecked);
                        if (isChecked) {
                            zongjia = zongjia +Double.parseDouble(item.getTotalPrice());
                        } else {
                            zongjia = zongjia - Double.parseDouble(item.getTotalPrice());
                        }
                        tvPrice.setText(zongjia + " ");
                        btnJiesuan.setText("结算（" + zongjia + ")");

                        isall(cbYsx, isCheckedMap2, 0);
                    }
                });
//                holder.setText(R.id.tv_goods_name_scar, "套餐1");
//                holder.setText(R.id.tv_goods_price_scar, "10");
//                holder.setText(R.id.tv_count_scar, "*12");
//                holder.setImageResource(R.id.iv_item_scar, R.mipmap.nopic);
                holder.setText(R.id.tv_goods_name_scar, item.getName());
                holder.setText(R.id.tv_goods_price_scar, item.getTotalPrice());
                holder.setText(R.id.tv_count_scar, item.getTotalCount());
                holder.setImageResource(R.id.iv_item_scar, R.mipmap.nopic);
            }
        };
        rcvYsx.setAdapter(b2);
    }

    private void initData() {
        //http://120.27.141.95:8221/ashx/Shopping.ashx?Component=ShoppingCart&Function=HttpGetEntitysMainPage&UserTel=18727823918&UserPhyAdd=333333
        LogUtils.isLogin(getActivity());
        if(LocalStorage.get("LoginStatus").equals("login")) {
            Map<String, String> map = new HashMap<>();
            map.put("Component", "ShoppingCart");
            map.put("Function", "HttpGetEntitysMainPage");
            map.put("UserTel", LocalStorage.get(getContext().getApplicationContext(), "UserTel"));
            map.put("UserPhyAdd", LocalStorage.get(getContext().getApplicationContext(), "strUniqueId"));
            CarRetrofit.getStringDataFromNet("Shopping", map, new RetrofitUtil.CallBack<String>() {
                @Override
                public void onLoadingDataComplete(String body) {
                    LogUtils.i("22222222",body+"222");
                    Toast.makeText(getActivity(), body, Toast.LENGTH_LONG).show();
                    if (body.equals("{\"提示\":\"你还没有登录\"}")){
                        initData();
                    }else {
                        Gson gson = new Gson();
                        scBean = gson.fromJson(body, ShoppingCarBean.class);
                        for (int i = 0; i < scBean.getYSXFreash().getContent().size(); i++) {
                    isCheckedMap2.put(i,true);
                    zongjia=zongjia+Double.parseDouble(scBean.getYSXFreash().getContent().get(i).getTotalPrice());
                }
                for (int i = 0; i < scBean.getFeast().getContent().size(); i++) {
                    isCheckedMap.put(i,true);
                    zongjia=zongjia+Double.parseDouble(scBean.getFeast().getContent().get(i).getTotalPrice());
                }
                if (isCheckedMap.containsValue(true)) {
                    llXcxy.setVisibility(View.VISIBLE);
                    initAdapter();
                }
                if(isCheckedMap2.containsValue(true)){
                    llYsx.setVisibility(View.VISIBLE);
                    initAdapter2();
                }
                for (int i = 0; i < priceMap.size(); i++) {
                    zongjia = zongjia + priceMap.get(i);
                }
                tvPrice.setText(zongjia + "");
                btnJiesuan.setText("结算（" + zongjia + ")");
                    }
                }

                @Override
                public void onLoadingDataFailed(Throwable t) {

                }
            });
        }
//        CarRetrofit.getBeanDataFromNet("Shopping", map, ShoppingCarBean.class, new RetrofitUtil.CallBack<ShoppingCarBean>() {
//            @Override
//            public void onLoadingDataComplete(ShoppingCarBean body) {
//                scBean=body;
//                Toast.makeText(getActivity(),"111"+scBean.toString(),Toast.LENGTH_SHORT).show();
//                for (int i = 0; i < body.getYSXFreash().getContent().size(); i++) {
//                    isCheckedMap2.put(i,true);
//                    zongjia=zongjia+Double.parseDouble(scBean.getYSXFreash().getContent().get(i).getTotalPrice());
//                }
////                for (int i = 0; i < scBean.getFeast().getContent().size(); i++) {
////                    isCheckedMap.put(i,true);
////                    zongjia=zongjia+Double.parseDouble(scBean.getFeast().getContent().get(i).getTotalPrice());
////                }
//                if (isCheckedMap.containsValue(true)) {
//                    llXcxy.setVisibility(View.VISIBLE);
//                    initAdapter();
//                }
//                if(isCheckedMap2.containsValue(true)){
//                    llYsx.setVisibility(View.VISIBLE);
//                    initAdapter2();
//                }
//                for (int i = 0; i < priceMap.size(); i++) {
//                    zongjia = zongjia + priceMap.get(i);
//                }
//                tvPrice.setText(zongjia + "");
//                btnJiesuan.setText("结算（" + zongjia + ")");
//                Toast.makeText(getActivity(),body.toString(),Toast.LENGTH_LONG).show();
//            }
//            @Override
//            public void onLoadingDataFailed(Throwable t) {
//            }
//        });


    }

    private void isae(CheckBox cb, Map<Integer, Boolean> map) {
        if (map.containsValue(false)) {
            cb.setChecked(false);
        } else {
            cb.setChecked(true);
        }
    }

    private void isall(CheckBox cb, Map<Integer, Boolean> map, int i) {
        if (map.containsValue(false)) {
            cb.setChecked(false);
            isCheckedMapAll.put(i, false);
        } else {
            cb.setChecked(true);
            isCheckedMapAll.put(i, true);
        }
        isae(cbSelectAll, isCheckedMapAll);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_more:
                rlDel.setVisibility(View.VISIBLE);
                rlJiesuan.setVisibility(View.INVISIBLE);
                break;
            case btn_delete:
                rlDel.setVisibility(View.INVISIBLE);
                rlJiesuan.setVisibility(View.VISIBLE);
//                for (int i = 0; i < isCheckedMap2.size(); i++) {
//                    if (isCheckedMap2.get(i)) {
//                            scBean.getYSXFreash().remove(i);
//                            isCheckedMap2.remove(i);
//                        i--;
//                    }
//                }
                b2.notifyDataSetChanged();
//                b1.notifyDataSetChanged();
                break;
            case R.id.cb_ysx_car:
                if (cbYsx.isChecked()) {
                    isCheckedMapAll.put(0, true);
                    for (int i = 0; i < b2.getItemCount(); i++) {
                        isCheckedMap2.put(i, true);
                    }
                } else {
                    isCheckedMapAll.put(0, false);
                    for (int i = 0; i < b2.getItemCount(); i++) {
                        isCheckedMap2.put(i, false);
                    }
                }
                isae(cbSelectAll, isCheckedMapAll);
                b2.notifyDataSetChanged();
//                YsxCb();
                break;
            case R.id.cb_select_xcxy:
                if (cbXcxy.isChecked()) {
                    isCheckedMapAll.put(1, true);
                    for (int i = 0; i < b1.getItemCount(); i++) {
                        isCheckedMap.put(i, true);
                    }
                } else {
                    isCheckedMapAll.put(1, false);
                    for (int i = 0; i < b1.getItemCount(); i++) {
                        isCheckedMap.put(i, false);
                    }
                }
                isae(cbSelectAll, isCheckedMapAll);
                b1.notifyDataSetChanged();
//                YsxCb();
                break;
            case cb_select_all:
                if (cbSelectAll.isChecked()) {
                    cbYsx.setChecked(true);
                    cbXcxy.setChecked(true);
                    cbService.setChecked(true);
                } else {
                    cbXcxy.setChecked(false);
                    cbYsx.setChecked(false);
                    cbService.setChecked(false);
                }
                if (isCheckedMap.containsValue(true)) {
                    llXcxy.setVisibility(View.VISIBLE);
                    XcxyCb();
                }
                if(isCheckedMap2.containsValue(true)){
                    llYsx.setVisibility(View.VISIBLE);
                    YsxCb();
                }


                break;
            case R.id.cb_select_service:
                if (cbService.isChecked()) {
                    cbService.setChecked(true);
                    isCheckedMapAll.put(2, true);
                } else {
                    cbService.setChecked(false);
                    isCheckedMapAll.put(2, false);
                }
                isae(cbSelectAll, isCheckedMapAll);
                break;
        }
    }

    private void YsxCb() {
        if (cbYsx.isChecked()) {
            isCheckedMapAll.put(0, true);
            for (int i = 0; i < b1.getItemCount(); i++) {
                isCheckedMap.put(i, true);
            }

        } else {
            isCheckedMapAll.put(0, false);
            for (int i = 0; i < b1.getItemCount(); i++) {
                isCheckedMap.put(i, false);
            }
        }
        isae(cbSelectAll, isCheckedMapAll);
        b1.notifyDataSetChanged();
    }

    private void XcxyCb() {
        if (cbXcxy.isChecked()) {
            isCheckedMapAll.put(0, true);
            for (int i = 0; i < b2.getItemCount(); i++) {
                isCheckedMap2.put(i, true);
            }

        } else {
            isCheckedMapAll.put(0, false);
            for (int i = 0; i < b2.getItemCount(); i++) {
                isCheckedMap2.put(i, false);
            }
        }
        isae(cbSelectAll, isCheckedMapAll);
        b2.notifyDataSetChanged();
    }
}
