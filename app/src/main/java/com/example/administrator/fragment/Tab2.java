package com.example.administrator.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.administrator.entity.ProBean;
import com.example.administrator.myapplication.R;
import com.example.administrator.net.RetrofitUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ${WuQiLian} on 2016/9/6.
 */
public class Tab2 extends Fragment {
    @BindView(R.id.rb_allfresh_1)
    RadioButton rbAllfresh1;
    @BindView(R.id.rb_allfresh_2)
    RadioButton rbAllfresh2;
    @BindView(R.id.rb_allfresh_3)
    RadioButton rbAllfresh3;
    @BindView(R.id.rb_allfresh_4)
    RadioButton rbAllfresh4;
    @BindView(R.id.rb_allfresh_5)
    RadioButton rbAllfresh5;
    @BindView(R.id.rb_allfresh_6)
    RadioButton rbAllfresh6;
    @BindView(R.id.rb_allfresh_7)
    RadioButton rbAllfresh7;
    @BindView(R.id.frame_all_fresh)
    FrameLayout frameAllFresh;
    @BindView(R.id.rg_allfresh)
    RadioGroup rgAllfresh;
    private View view;
    List<Fragment> flist;
    RetrofitUtil<ProBean> pu;
    List<ProBean> l;
    private FragmentManager fm;
    String[] s={"123","234","456","678","890","111","222"};
    private int index = 0;  //点击的RadioButtonTab标记
    private int currIndex = 0;  //当前的RadioButtonTab标记

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        fm=getChildFragmentManager();
        flist=new ArrayList<>();
        pu = new RetrofitUtil<>(getActivity());
        l = new ArrayList<>();
        view = LayoutInflater.from(getActivity()).inflate(
                R.layout.item_2, null);
        ButterKnife.bind(this, view);
        initFrData();
        setListener();
        return view;
    }

    private void initFrData() {
        for (int i = 0; i <s.length ; i++) {
            flist.add(new AllFreshFragment().newInstance(s[i]));
        }
        fm.beginTransaction().add(R.id.frame_all_fresh, flist.get(0)).show(flist.get(0)).commit();

    }

    private void setListener() {
        rgAllfresh.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb_allfresh_1:
                        index=0;
                    break;
                    case R.id.rb_allfresh_2:
                        index=1;
                        break;
                    case R.id.rb_allfresh_3:
                        index=2;
                        break;
                    case R.id.rb_allfresh_4:
                        index=3;
                        break;
                    case R.id.rb_allfresh_5:
                        index=4;
                        break;
                    case R.id.rb_allfresh_6:
                        index=5;
                        break;
                    case R.id.rb_allfresh_7:
                        index=6;
                        break;
                }
                //当前页不等于点击的页面时
                if (currIndex != index) {
                    //隐藏掉当前页
                    FragmentTransaction transaction = fm.beginTransaction().hide(flist.get(currIndex));
                    if (!flist.get(index).isAdded()) {
                        transaction.add(R.id.frame_all_fresh, flist.get(index));
                    }
                    //显示点击的那页
                    transaction.show(flist.get(index)).commit();
                }
                //更新当前页标记
                currIndex = index;
            }
        });
    }

}
