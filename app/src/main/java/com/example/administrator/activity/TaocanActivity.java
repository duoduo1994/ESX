package com.example.administrator.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.example.administrator.entity.TaocanDetialBean;
import com.example.administrator.fragment.TaocanCommentFragment;
import com.example.administrator.fragment.TaocanDetialFragment;
import com.example.administrator.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class TaocanActivity extends BaseActivity {
    private ViewPager vp;
    private TabLayout tab;
    private String[] titles= new String[]{"内容","评价"};
    private ArrayList<Fragment> list=new ArrayList<>();
    private List<TaocanDetialBean> tclist;


    @Override
    protected int setContentView() {

        return R.layout.activity_taocan;

    }

    @Override
    protected void initView() {
        initData();
        list.add(new TaocanDetialFragment(tclist));
        list.add(new TaocanCommentFragment());
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        vp= (ViewPager) findViewById(R.id.vp_taocan_fl);
        tab= (TabLayout) findViewById(R.id.tab);
        setSupportActionBar(toolbar);
        VpAdapter vd=new VpAdapter(getSupportFragmentManager());
        vp.setAdapter(vd);
        tab.setupWithViewPager(vp);
    }

    private void initData() {
        tclist=new ArrayList<>();
        for (int i = 0; i <20 ; i++) {
            TaocanDetialBean tdb=new TaocanDetialBean();
            List<TaocanDetialBean.CaidanBean> li=new ArrayList<>();
            li.add(new TaocanDetialBean.CaidanBean("红烧鱼","123","eeee"));
           tdb.setCaidan(li);
            tclist.add(tdb);
        }

    }


    class VpAdapter extends FragmentPagerAdapter{

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
