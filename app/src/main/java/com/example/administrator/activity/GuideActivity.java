package com.example.administrator.activity;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.administrator.myapplication.R;
import com.example.administrator.utils.ViewPagerActivity;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${WuQiLian} on 2016/9/5.
 */
public class GuideActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    @ViewInject(R.id.guide_pager)
    ViewPager viewPager;
    @ViewInject(R.id.guide_start)
    Button button;

    @OnClick(R.id.guide_start)
    public void onClick(View view) {
        startActivity(new Intent(GuideActivity.this, MainActivity.class));
        GuideActivity.this.finish();
    }

    private int[] pics = {R.mipmap.start_page1, R.mipmap.start_page2, R.mipmap.start_page3, R.mipmap.start_page4};
    private ViewPagerActivity viewPagerActivity;
    private List<View> views;

    @Override
    protected int setContentView() {
        return R.layout.guideactivity;
    }

    @Override
    protected void initView() {
        button.setVisibility(View.GONE);
        views = new ArrayList<>();
        LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                DrawerLayout.LayoutParams.WRAP_CONTENT);

        // 初始化引导图片列
        for (int i = 0; i < pics.length; i++) {
            ImageView iv = new ImageView(this);
            iv.setLayoutParams(mParams);
            iv.setImageResource(pics[i]);
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            views.add(iv);
        }
        // 初始化Adapter
        viewPagerActivity = new ViewPagerActivity(views);
        viewPager.setAdapter(viewPagerActivity);
        // 绑定回调
        viewPager.setOnPageChangeListener(GuideActivity.this);

    }

    // 当滑动状态改变时调用
    @Override
    public void onPageScrollStateChanged(int arg0) {
        // TODO Auto-generated method stub

    }

    // 当当前页面被滑动时调
    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
        // TODO Auto-generated method stub

    }

    // 当新的页面被选中时调
    @Override
    public void onPageSelected(int arg0) {
        if (arg0 == 3) {
            button.setVisibility(View.VISIBLE);

        } else {
            button.setVisibility(View.GONE);
        }
    }

}
