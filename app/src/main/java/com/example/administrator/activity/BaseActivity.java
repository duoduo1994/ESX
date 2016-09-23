package com.example.administrator.activity;


import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.administrator.myapplication.R;
import com.example.administrator.utils.ActivityCollector;
import com.lidroid.xutils.ViewUtils;

import rx.Subscriber;

/**
 * Created by ${WuQiLian} on 2016/8/31.
 */
public abstract class BaseActivity extends AppCompatActivity {
    private Context context;
    private int anInt = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(setContentView());
        ViewUtils.inject(this);

        if (isConnect() == false) {
            toast(context, "设备无连接，请连接网络");
        }
        initView();
    }

    protected void toast(Context context, String info) {
        Toast.makeText(context, info, Toast.LENGTH_SHORT).show();
    }


    protected abstract int setContentView();

    protected void finishA() {
        if (System.currentTimeMillis() - dianJiShiJian > 500) {
            dianJiShiJian = System.currentTimeMillis();
            ((Activity) context).finish();
            overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
        }
    }

    protected static long dianJiShiJian = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - dianJiShiJian > 500) {
                dianJiShiJian = System.currentTimeMillis();
                ((Activity) context).finish();
                overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);

            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /*
     * 判断网络连接是否已开 true 已打开 false 未打开
     */
    private boolean isConnect() {
        ConnectivityManager conManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = conManager.getActiveNetworkInfo();
        if (networkInfo != null) { // 注意，这个判断一定要的哦，要不然会出错
            return networkInfo.isAvailable();
        }
        return false;

    }

    protected abstract void initView();

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
