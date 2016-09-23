package com.example.administrator.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.administrator.fragment.Tab1;
import com.example.administrator.fragment.Tab2;
import com.example.administrator.fragment.Tab3;
import com.example.administrator.fragment.Tab4;
import com.example.administrator.fragment.Tab5;
import com.example.administrator.myapplication.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.event.OnRadioGroupCheckedChange;

public class MainActivity extends FragmentActivity {
    private long exitTime;
    private Tab1 tab1;
    private Tab2 tab2;
    private Tab3 tab3;
    private Tab4 tab4;
    private Tab5 tab5;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewUtils.inject(this);
        initView();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
            return true;
        } else {
            MainActivity.this.finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    @OnRadioGroupCheckedChange(R.id.radio_group)
    public void onClick(RadioGroup radioGroup, int i) {
        switch (i) {
            case R.id.radio_1:
                if (!tab1.isAdded()) {
                    getSupportFragmentManager().beginTransaction().hide(tab2).hide(tab3).hide(tab4).hide(tab5).add(R.id.fragment_main, tab1).commit();
                } else {
                    getSupportFragmentManager().beginTransaction().show(tab1).hide(tab2).hide(tab3).hide(tab4).hide(tab5).commit();
                }
                break;
            case R.id.radio_2:
                if (!tab2.isAdded()) {
                    getSupportFragmentManager().beginTransaction().hide(tab1).hide(tab3).hide(tab4).hide(tab5).add(R.id.fragment_main, tab2).commit();
                } else {
                    getSupportFragmentManager().beginTransaction().show(tab2).hide(tab1).hide(tab3).hide(tab4).hide(tab5).commit();
                }
                break;
            case R.id.radio_3:
                if (!tab3.isAdded()) {
                    getSupportFragmentManager().beginTransaction().hide(tab1).hide(tab2).hide(tab4).hide(tab5).add(R.id.fragment_main, tab3).commit();
                } else {
                    getSupportFragmentManager().beginTransaction().show(tab3).hide(tab1).hide(tab2).hide(tab4).hide(tab5).commit();
                }
                break;
            case R.id.radio_4:
                if (!tab4.isAdded()) {
                    getSupportFragmentManager().beginTransaction().hide(tab1).hide(tab2).hide(tab3).hide(tab5).add(R.id.fragment_main, tab4).commit();
                } else {
                    getSupportFragmentManager().beginTransaction().show(tab4).hide(tab1).hide(tab2).hide(tab3).hide(tab5).commit();
                }
                break;
            case R.id.radio_5:
                if (!tab5.isAdded()) {
                    getSupportFragmentManager().beginTransaction().hide(tab1).hide(tab2).hide(tab3).hide(tab4).add(R.id.fragment_main, tab5).commit();
                } else {
                    getSupportFragmentManager().beginTransaction().show(tab5).hide(tab1).hide(tab2).hide(tab3).hide(tab4).commit();
                }
                break;
        }
    }

    private void initView() {// p
        tab1 = new Tab1();
        tab2 = new Tab2();
        tab3 = new Tab3();
        tab4 = new Tab4();
        tab5 = new Tab5();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_main, tab1).add(R.id.fragment_main, tab2).add(R.id.fragment_main, tab3).add(R.id.fragment_main, tab4).add(R.id.fragment_main, tab5).hide(tab2).hide(tab3).hide(tab5).hide(tab4).commit();
//        butt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Observable.create(new Observable.OnSubscribe<Object>() {
//                    @Override
//                    public void call(Subscriber<? super Object> subscriber) {
//                        String path = "http://192.168.0.222/ashx/JoinHandler.ashx?Action=CookJoin&CookOrGrp=Cook&ApplyTime=2016/1/7&PrincipalName=负责人&PrincipalTel=13332224445&GoodAt=5H9&Maximum=5&ServiceArea=鄞州区&IDNum=621055190101025589";
//                        RequestParams params = new RequestParams();
//                        params.addBodyParameter("IDNumPosImg",new File(Environment.getExternalStorageDirectory(),"name.png"));
//                        params.addBodyParameter("IDNumBackImg",new File(Environment.getExternalStorageDirectory(),"name.png"));
//                        params.addBodyParameter("HealthImg",new File(Environment.getExternalStorageDirectory(),"name.png"));
//                        params.addBodyParameter("CookImg",new File(Environment.getExternalStorageDirectory(),"name.png"));
//                        params.addBodyParameter("LevelImg",new File(Environment.getExternalStorageDirectory(),"name.png"));
//                        new Net(path, params, subscriber);
//                    }
//                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<Object>() {
//                    @Override
//                    public void onCompleted() {
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        System.out.println(e.getMessage()+"*******&^");
//                    }
//
//                    @Override
//                    public void onNext(Object o) {
//                        t.setText(o.toString());
//                    }
//                });
    }
}
