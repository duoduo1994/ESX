package com.example.administrator.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.administrator.entity.TaocanDetialBean;
import com.example.administrator.fragment.TaocanCommentFragment;
import com.example.administrator.fragment.TaocanDetialFragment;
import com.example.administrator.myapplication.R;
import com.example.administrator.net.RetrofitUtil;
import com.example.administrator.utils.LocalStorage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TaocanActivity extends BaseActivity {
    @BindView(R.id.btn_buy_now)
    Button btnBuyNow;
    @BindView(R.id.btn_add_car)
    Button btnAddCar;
    private ViewPager vp;
    private TabLayout tab;
    private String[] titles = new String[]{"内容", "评价"};
    private ArrayList<Fragment> list = new ArrayList<>();
    private List<TaocanDetialBean> tclist;


    @Override
    protected int setContentView() {

        return R.layout.activity_taocan;

    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        initData();
        list.add(new TaocanDetialFragment(tclist));
        list.add(new TaocanCommentFragment());
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        vp = (ViewPager) findViewById(R.id.vp_taocan_fl);
        tab = (TabLayout) findViewById(R.id.tab);
        setSupportActionBar(toolbar);
        VpAdapter vd = new VpAdapter(getSupportFragmentManager());
        vp.setAdapter(vd);
        tab.setupWithViewPager(vp);
        btnBuyNow.setOnClickListener(v -> BuyNow());
        btnAddCar.setOnClickListener(v -> AddCar());
    }

    private void AddCar() {
        RetrofitUtil addRetrofit=new RetrofitUtil(TaocanActivity.this);
        Map<String,String> map=new HashMap<>();
        map.put("Component","ShoppingCar ");
        map.put("Function","HttpsSubmit");
        map.put("UserTel", LocalStorage.get("Usertel").toString());
        map.put("UserPhyAdd",LocalStorage.get("strUniqueId").toString());
        map.put("RecvTime","2016-12-5");
        map.put("ProArray",tclist.toArray().toString());
        addRetrofit.getStringDataFromNet("Shopping", map, new RetrofitUtil.CallBack<String>() {
            @Override
            public void onLoadingDataComplete(String body) {
                String result="请重试";
                try {
                    JSONObject jo=new JSONObject(body.trim());
                    result=jo.getString("提示");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Toast.makeText(TaocanActivity.this,result,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onLoadingDataFailed(Throwable t) {
                Toast.makeText(TaocanActivity.this,"网络异常，请重新提交。",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void BuyNow() {
    }

    private void initData() {
        tclist = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            TaocanDetialBean tdb = new TaocanDetialBean();
            List<TaocanDetialBean.CaidanBean> li = new ArrayList<>();
            li.add(new TaocanDetialBean.CaidanBean("红烧鱼", "123", "eeee"));
            tdb.setCaidan(li);
            tclist.add(tdb);
        }

    }




    class VpAdapter extends FragmentPagerAdapter {

        public VpAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return list.get(position);
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }
}
