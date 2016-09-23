package com.example.administrator.net;


import android.content.Context;
import android.view.View;

/**
 * Created by Administrator on 2016/8/31.
 */
public class OnClick implements View.OnClickListener {
    private Context context;
    private View view;

    public OnClick(Context context, View view) {
        this.context = context;
        this.view = view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

        }
    }
}
