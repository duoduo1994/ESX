package com.example.administrator.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.fragment.MyOrderFragment;
import com.example.administrator.myapplication.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;


public class MyOrderListActivity extends AppCompatActivity {
   @ViewInject(R.id.btn_back)
    Button btn_back;
    @ViewInject(R.id.tv_title)
    private TextView tv;
    @ViewInject(R.id.tab_myorder)
    TabLayout tab_myorder;
    @ViewInject(R.id.vp_myorder)
    ViewPager vp_myorder;
    String[] titles;
    FragmentPagerAdapter fpAdapter;
    List<Fragment> Flist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order_list);
        ViewUtils.inject(this);
        Flist=new ArrayList<>();
        titles=new String[]{"全部","待支付","代发货","待收货","待评价"};
        initFragment();
        setOnclick();
        initAdapter();
    }

    private void initFragment() {
        for (int i = 0; i < titles.length; i++) {
            MyOrderFragment f=new MyOrderFragment();
            Bundle b=new Bundle();
            b.putString("data","dataaaaa"+i);
            f.setArguments(b);
            Flist.add(f);
        }
    }

    private void initAdapter() {
        fpAdapter=new VpAdapter(getSupportFragmentManager(),Flist);
        vp_myorder.setAdapter(fpAdapter);
        vp_myorder.setOffscreenPageLimit(5);
        tab_myorder.setupWithViewPager(vp_myorder);
    }

    private void setOnclick() {
        tv.setText("我的订单");
        btn_back.setOnClickListener(v -> MyOrderListActivity.this.finish());
    }

    class VpAdapter extends FragmentPagerAdapter{

        List<Fragment> list;
        public VpAdapter(FragmentManager fm, List<Fragment> list) {
            super(fm);
            this.list=list;
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
