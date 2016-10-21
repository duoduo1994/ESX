package com.example.administrator.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.myapplication.R;
import com.example.administrator.utils.GetPersonInfo;
import com.example.administrator.utils.LoadingDialog;
import com.example.administrator.utils.LocalStorage;

public class MyInformationActivity extends AppCompatActivity {
    private LinearLayout nickName, pass,  sex, Erweima, changeheadimage;
    private TextView nickText, nickEdit, sexText, sexEdit;
    private String editTel, editPass, editSex, editNickName, myHeadUrl;
    private TextView tv;
    private LoadingDialog dialog = null;
    private Button lonout;
    private ImageView myHead;
    private int H, W;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_information);
        DisplayMetrics mDisplayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(mDisplayMetrics);
        H = mDisplayMetrics.heightPixels;
        W = mDisplayMetrics.widthPixels;
        tv = (TextView) findViewById(R.id.tv_title);
        tv.setText("个人资料");
        LocalStorage.initContext(this);
        findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent data = new Intent();
                data.putExtra("resultStringName", editNickName);
                MyInformationActivity.this.setResult(13, data);
                finish();
            }
        });
        myHead = (ImageView) findViewById(R.id.myHead);
        lonout = (Button) findViewById(R.id.lonout);
        Erweima = (LinearLayout) findViewById(R.id.Erweima);
        changeheadimage = (LinearLayout) findViewById(R.id.changeheadimage);

        nickName = (LinearLayout) findViewById(R.id.NickName);
        nickText = (TextView) findViewById(R.id.NickText);
        nickEdit = (TextView) findViewById(R.id.NickEdit);

        pass = (LinearLayout) findViewById(R.id.Pass);

        sex = (LinearLayout) findViewById(R.id.Sex);
        sexText = (TextView) findViewById(R.id.SexText);
        sexEdit = (TextView) findViewById(R.id.SexEdit);

        editTel = (String) LocalStorage.get("UserTel");
        GetPersonInfo getPersonInfo = new GetPersonInfo();
        getPersonInfo.getInfo(MyInformationActivity.this, editTel);
    }
}
