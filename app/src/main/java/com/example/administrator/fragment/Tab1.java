package com.example.administrator.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.administrator.activity.HomeActivity;
import com.example.administrator.myapplication.R;
import com.example.administrator.net.XUtilsHelper;
import com.example.administrator.utils.ViewPagerAdapter;
import com.lidroid.xutils.http.RequestParams;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by ${WuQiLian} on 2016/9/6.
 */
public class Tab1 extends Fragment implements View.OnClickListener{
    private View view;
    private LinearLayout xiangCun,siren,niqing,tiexing;
    private ViewPager viewPager;
    /** 首页轮播的界面的资源 */
    private int[] resId = {R.mipmap.shouye6, R.mipmap.shouye1,
            R.mipmap.shouye2, R.mipmap.shouye3, R.mipmap.shouye4,
            R.mipmap.shouye5, R.mipmap.shouye6, R.mipmap.shouye1 };
    private ImageView[] tips;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        view = LayoutInflater.from(getActivity()).inflate(
                R.layout.item_1, null);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager_shou);
        xiangCun = (LinearLayout) view.findViewById(R.id.xiangcun_xiyan);
        siren = (LinearLayout) view.findViewById(R.id.siren_dingzhi);
        niqing = (LinearLayout) view.findViewById(R.id.niqing_woyuan);
        tiexing = (LinearLayout) view.findViewById(R.id.tiexin_daojia);
        xiangCun.setOnClickListener(this);
        siren.setOnClickListener(this);
        niqing.setOnClickListener(this);
        tiexing.setOnClickListener(this);
        init();
        addImage();
        return view;
    }
    private List<ImageView> ali;
    private ImageView iv;
    ViewPagerAdapter vpa;
    private void init(){
        ViewGroup group = (ViewGroup) view.findViewById(R.id.viewGroup1);
        tips = new ImageView[6];
        for (int i = 0; i < tips.length; i++) {
            ImageView imageView = new ImageView(getActivity());
            imageView.setLayoutParams(new ViewGroup.LayoutParams(20, 20));
            if (i == 0) {
                imageView.setBackgroundResource(R.mipmap.red_point);
            } else {
                imageView.setBackgroundResource(R.mipmap.dot_unselected);
            }
            tips[i] = imageView;

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT));
            layoutParams.leftMargin = 10;// 设置点点点view的左边距
            layoutParams.rightMargin = 10;// 设置点点点view的右边距
            group.addView(imageView, layoutParams);
        }

        ali = new ArrayList();
        for (int i = 0; i < resId.length; i++) {
            iv = new ImageView(getActivity());
            iv.setImageResource(resId[i]);
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            ali.add(iv);
        }
        vpa = new ViewPagerAdapter(ali,
                getActivity());
        viewPager.setAdapter(vpa);
        viewPager.setCurrentItem(1);
        startTask();
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                pageIndex = arg0;
            }

            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {
            }

            /* state: 0空闲，1是滑行中，2加载完毕 */
            @Override
            public void onPageScrollStateChanged(int state) {
                // TODO Auto-generated method stub
                // System.out.println("state:" + state);
                if (state == 0 && !isTaskRun) {
                    setCurrentItem();
                    startTask();
                } else if (state == 1 && isTaskRun)
                    stopTask();
            }
        });
    }
    /**
     * 停止定时任务
     */
    private void stopTask() {
        // TODO Auto-generated method stub
        isTaskRun = false;
        mTimer.cancel();
    }
    /**
     * 处理Page的切换逻辑
     */
    private void setCurrentItem() {
        if (pageIndex == 0) {
            pageIndex = 6;
            viewPager.setCurrentItem(pageIndex, false);
        } else if (pageIndex == 7) {
            pageIndex = 1;
            viewPager.setCurrentItem(pageIndex, false);
        } else {

            viewPager.setCurrentItem(pageIndex, true);// 取消动画
        }
        setImageBackground(pageIndex - 1);
    }

    private void setImageBackground(int selectItems) {
        for (int i = 0; i < tips.length; i++) {
            if (i == selectItems) {
                tips[i].setBackgroundResource(R.mipmap.red_point);
            } else {
                tips[i].setBackgroundResource(R.mipmap.dot_unselected);
            }
        }
    }

    private Timer mTimer;
    private TimerTask mTask;
    private int pageIndex = 1;
    private boolean isTaskRun;
    // 处理EmptyMessage(0)
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            setCurrentItem();
        }
    };
    /**
     * 开启定时任务
     */
    private void startTask() {
        // TODO Auto-generated method stub
        isTaskRun = true;
        mTimer = new Timer();
        mTask = new TimerTask() {
            @Override
            public void run() {
                pageIndex++;
                mHandler.sendEmptyMessage(0);
            }
        };
        mTimer.schedule(mTask, 2 * 1000, 2 * 1000);// 这里设置自动切换的时间，单位是毫秒，2*1000表示2秒
    }
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.xiangcun_xiyan:
                startActivity(new Intent(getActivity(), HomeActivity.class));
                break;
            case R.id.siren_dingzhi:
                Toast.makeText(getActivity(), "私人定制", Toast.LENGTH_LONG).show();
                break;
            case R.id.niqing_woyuan:
                Toast.makeText(getActivity(), "你请我援", Toast.LENGTH_LONG).show();
                break;
            case R.id.tiexin_daojia:
                Toast.makeText(getActivity(), "贴心到家", Toast.LENGTH_LONG).show();
                break;
        }

    }



    private void addImage(){
        XUtilsHelper xUtilsHelper1 = new XUtilsHelper(getActivity(),"");
        RequestParams requestParams = new RequestParams();

        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                xUtilsHelper1.sendPost(requestParams,subscriber);
            }
        }).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {

            }
        });





    }







}
