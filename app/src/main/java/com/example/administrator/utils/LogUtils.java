package com.example.administrator.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.activity.MainActivity;
import com.example.administrator.myapplication.R;


/**
 * Description : 日子工具类
 * Author : lauren
 * Email  : lauren.liuling@gmail.com
 * Blog   : http://www.liuling123.com
 * Date   : 15/12/14
 */
public class LogUtils {
    public static final boolean DEBUG = true;

    public static void isLogin(Context context){
        if (LocalStorage.get("LoginStatus").toString().equals("login")) {
        } else {
            Dialog d = new Dialog(context, R.style.loading_dialog);
           View v = LayoutInflater.from(context).inflate(R.layout.dialog,
                    null);// 窗口布局
            d.setContentView(v);// 把设定好的窗口布局放到dialog中
            d.setCanceledOnTouchOutside(true);// 设定点击窗口空白处取消会话
            Button p1 = (Button) v.findViewById(R.id.p);
            Button n = (Button) v.findViewById(R.id.n);
            TextView juTiXinXi = (TextView) v.findViewById(R.id.banben_xinxi);
            TextView tiShi = (TextView) v.findViewById(R.id.banben_gengxin);
            TextView fuZhi = (TextView) v.findViewById(R.id.fuzhi_jiantieban);
            juTiXinXi.setText("亲，还没登录呢，是否前去登录？");
            tiShi.setText("登录提示");
            p1.setText("确定");
            n.setText("返回");
            p1.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    // TODO Auto-generated method stub
                    Intent intent = new Intent(context,
                            MainActivity.class);
                    intent.putExtra("flag", 5);
//                    startActivityForResult(intent, 123);
                    context.startActivity(intent);
                    d.dismiss();
                }
            });
            n.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    d.dismiss();
                }
            });
            d.show();
        }
    }
    public static void v(String tag, String message) {
        if(DEBUG) {
            Log.v(tag, message);
        }
    }

    public static void d(String tag, String message) {
        if(DEBUG) {
            Log.d(tag, message);
        }
    }

    public static void i(String tag, String message) {
        if(DEBUG) {
            Log.i(tag, message);
        }
    }

    public static void w(String tag, String message) {
        if(DEBUG) {
            Log.w(tag, message);
        }
    }

    public static void e(String tag, String message) {
        if(DEBUG) {
            Log.e(tag, message);
        }
    }

    public static void e(String tag, String message, Exception e) {
        if(DEBUG) {
            Log.e(tag, message, e);
        }
    }

}
