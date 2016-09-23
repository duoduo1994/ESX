package com.example.administrator.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.LinearLayout;

import com.example.administrator.myapplication.R;
import com.example.administrator.net.XUtilsHelper;
import com.example.administrator.utils.LocalStorage;
import com.example.administrator.utils.MD5Util;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.view.annotation.ViewInject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by ${WuQiLian} on 2016/9/2.
 */
public class StartActivity extends BaseActivity {

    @ViewInject(R.id.start_tu)
    LinearLayout linearLayout;

    @Override
    protected int setContentView() {
        return R.layout.startactivity;
    }
static Activity t;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void initView() {
        t = StartActivity.this;
        linearLayout.setBackgroundResource(R.mipmap.start_page);
        Observable.timer(2, TimeUnit.SECONDS, AndroidSchedulers.mainThread()).map(l -> {
            preferences = getSharedPreferences("phone", MODE_PRIVATE);
            if (preferences.getBoolean("firstStart", true)) {
                editor = preferences.edit();
                // 将登录标志位设置为false，下次登录时不在显示首次登录界面
                editor.putBoolean("firstStart", false);
                editor.commit();
                startActivity(new Intent(StartActivity.this, GuideActivity.class));
                StartActivity.this.finish();
            } else {
                startActivity(new Intent(StartActivity.this, MainActivity.class));
                StartActivity.this.finish();
            }
            return null;
        }).subscribe();
    }
    static boolean isFalse = true;
    static int ciShu = 0;
    public static void dengRu(){
        String tel = (String) LocalStorage.get("UserTel").toString();
        String passEnt = MD5Util.string2MD5((String) LocalStorage.get("UserPass").toString());

        XUtilsHelper xUtilsHelper1 = new XUtilsHelper(t,"LoginCheckHandler.ashx?Action=UserLogin");
        RequestParams p = new RequestParams();
        p.addBodyParameter("UserPass",passEnt );
        p.addBodyParameter("UserTel", tel);
        p.addBodyParameter("CustPhyAddr", HomeActivity.strUniqueId);
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                xUtilsHelper1.sendPost(p,subscriber);
            }
        }).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if (ciShu >= 3) {
                    isFalse = false;
                }
                ;

                if (isFalse) {
                    tuiChu();
                    ciShu++;
                }
            }

            @Override
            public void onNext(String s) {


                System.out.println("RFY"+s);
                LocalStorage.set("LoginStatus", "login");
            }
        });

    }
    public static void tuiChu(){
        String tel = (String) LocalStorage.get("UserTel").toString();




        XUtilsHelper xUtilsHelper1 = new XUtilsHelper(t,"LoginCheckHandler.ashx?Action=UserExit");
        RequestParams p = new RequestParams();
        p.addBodyParameter("UserTel", tel);
        p.addBodyParameter("CustPhyAddr", HomeActivity.strUniqueId);
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                xUtilsHelper1.sendPost(p,subscriber);
            }
        }).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if (ciShu >= 3) {
                    isFalse = false;
                }
                ;

                if (isFalse) {
                    tuiChu();
                    ciShu++;
                }
            }

            @Override
            public void onNext(String s) {

                String ut=LocalStorage.get("UserTel").toString();
//						String up=LocalStorage.get("UserPass").toString();
                LocalStorage.clear();
                LocalStorage.set("LoginStatus", "tuichu");
//						LocalStorage.set("UserPass",up );
                LocalStorage.set("UserTel", ut);

                System.out.println("RFY"+s);
                try {
                    JSONObject json = new JSONObject(s.trim());
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        });












	}

}
