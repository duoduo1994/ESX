package com.example.administrator.activity;

import android.util.DisplayMetrics;
import android.view.Menu;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout.LayoutParams;

import com.example.administrator.ab.view.MyGridViewAdapter;
import com.example.administrator.myapplication.R;

/**
 * Created by User on 2016/10/19.
 */

public class DingDan_XiangQing_ysx extends BaseActivity {

    HorizontalScrollView horizontalScrollView_zhu;
    GridView gridView_zhu;
    DisplayMetrics dm;
    private int NUM = 4; // 每行显示个数
    private int hSpacing = 20;// 水平间距

    @Override
    protected int setContentView() {
        return R.layout.dingdan_xiangqing_ysx;
    }

    @Override
    protected void initView() {

        horizontalScrollView_zhu = (HorizontalScrollView) findViewById(R.id.scrollView_zhu);

        gridView_zhu = (GridView) findViewById(R.id.gridView_zhu);
        horizontalScrollView_zhu.setHorizontalScrollBarEnabled(false);// 隐藏滚动条
        getScreenDen();
        setValue(gridView_zhu);

    }
    private void setValue(GridView gridView) {
        MyGridViewAdapter adapter = new MyGridViewAdapter(this, 21);
        int count = adapter.getCount();
        int columns = (count % 2 == 0) ? count / 2 : count / 2 + 1;
        gridView.setAdapter(adapter);
        LayoutParams params = new LayoutParams(columns * dm.widthPixels / NUM,
                LayoutParams.WRAP_CONTENT);
        gridView.setLayoutParams(params);
        gridView.setColumnWidth(dm.widthPixels / NUM);
        // gridView.setHorizontalSpacing(hSpacing);
        gridView.setStretchMode(GridView.NO_STRETCH);
        if (count <= 3) {
            gridView.setNumColumns(count);
        } else {
            gridView.setNumColumns(columns);
        }
    }
    private void getScreenDen() {
        dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
