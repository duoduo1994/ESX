package com.example.administrator.amap.navi;


import com.example.administrator.utils.LocalStorage;


import cn.jpush.android.api.JPushInterface;
import cn.sharesdk.framework.ShareSDK;


import android.app.Application;
import android.content.Context;
import android.os.Vibrator;
import android.support.multidex.MultiDex;

/**
 * 文件名：主Application
 * 说明：初始化
 * 作者： 赵百旗
 * 公司：宁波驿邮
 *
 */
public class LocationApplication extends Application {
    public Vibrator mVibrator;//手机振动
    @Override
    public void onCreate() {
    	
        super.onCreate();
        /***
         * 初始化定位sdk，建议在Application中创建
         */
        mVibrator =(Vibrator)getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
        JPushInterface.setDebugMode(true);
     	JPushInterface.init(this);
     	LocalStorage.initContext(getApplicationContext());
     	ShareSDK.initSDK(this);
    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
                 MultiDex.install(this) ;
    }
}
