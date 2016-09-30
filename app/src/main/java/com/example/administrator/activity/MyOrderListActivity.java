package com.example.administrator.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.myapplication.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;


public class MyOrderListActivity extends AppCompatActivity {
   @ViewInject(R.id.btn_back)
    Button btn_back;
    @ViewInject(R.id.tv_title)
    private TextView tv;
    @ViewInject(R.id.tab_myorder)
    TabLayout tab_myorder;
    @ViewInject(R.id.vp_myorder)
    ViewPager vp_myorder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order_list);
        ViewUtils.inject(this);
        String[] titles={"全部","待支付","代发货","待收货","待评价"};

        setOnclick();
    }

    private void setOnclick() {
        tv.setText("我的订单");
        btn_back.setOnClickListener(v -> MyOrderListActivity.this.finish());
    }

    class VpAdapter extends FragmentPagerAdapter{

        public VpAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return null;
        }

        @Override
        public int getCount() {
            return 0;
        }
    }
}
