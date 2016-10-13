package com.example.administrator.activity;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.example.administrator.city.CityPicker;
import com.example.administrator.myapplication.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class AddaddressActivity extends AppCompatActivity {

    @BindView(R.id.et_address_receiver)
    EditText etAddressReceiver;
    @BindView(R.id.et_address_phone)
    EditText etAddressPhone;
    @BindView(R.id.tv_address_detail)
    TextView tvAddressDetail;
    @BindView(R.id.iv_choose_area)
    ImageView ivChooseArea;
    @BindView(R.id.rl_address_area)
    RelativeLayout rlAddressArea;
    @BindView(R.id.iv_choose_street)
    ImageView ivChooseStreet;
    @BindView(R.id.tv_address_street)
    TextView tvAddressStreet;
    @BindView(R.id.rl_address_street)
    LinearLayout rlAddressStreet;
    @BindView(R.id.et_detail_address)
    EditText etDetailAddress;
    @BindView(R.id.swith_setdefault)
    Switch swithSetdefault;
    @BindView(R.id.btn_save_address)
    Button btnSaveAddress;
    @BindView(R.id.activity_addaddress)
    RelativeLayout activityAddaddress;
    PopupWindow pw;
    int height;
    String address;
    @BindView(R.id.tv_choose_plase)
    TextView tvChoosePlase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addaddress);
        height = getResources().getDisplayMetrics().heightPixels;
        ButterKnife.bind(this);
        ClickEvent();
    }

    private void ClickEvent() {
        rlAddressArea.setOnClickListener(v -> initPw());
    }

    private void initPw() {
        View v = LayoutInflater.from(this).inflate(R.layout.pw_city_choose, null);
        CityPicker cp = (CityPicker) v.findViewById(R.id.city_picker);
        Button btn = (Button) v.findViewById(R.id.btn_pick);
        pw = new PopupWindow(v, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        pw.setFocusable(true);
        pw.setOutsideTouchable(true);
        pw.setBackgroundDrawable(new BitmapDrawable());
        pw.showAsDropDown(tvAddressDetail);
        btn.setOnClickListener(v1 -> {
            address = cp.getCity_string();
            tvChoosePlase.setVisibility(View.INVISIBLE);
            ivChooseArea.setVisibility(View.INVISIBLE);
            pw.dismiss();
            tvAddressDetail.setText(address);
        });
    }
}
