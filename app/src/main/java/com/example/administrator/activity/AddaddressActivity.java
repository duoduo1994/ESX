package com.example.administrator.activity;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.myapplication.R;
import com.example.administrator.net.RetrofitUtil;
import com.example.administrator.utils.LocalStorage;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.administrator.activity.StartActivity.t;
import static com.example.administrator.myapplication.R.id.et_address_phone;


public class AddaddressActivity extends AppCompatActivity {

    @BindView(R.id.et_address_receiver)
    EditText etAddressReceiver;

    @BindView(et_address_phone)
    EditText etAddressPhone;

    @BindView(R.id.tv_address_detail)
    TextView tvAddressDetail;
    //    @BindView(R.id.iv_choose_area)
//    ImageView ivChooseArea;
//    @BindView(R.id.rl_address_area)
//    RelativeLayout rlAddressArea;
//    @BindView(R.id.tv_choose_plase)
//    TextView tvChoosePlase;
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

    @BindView(R.id.btn_back)
    Button btnBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;


    //    @BindView(R.id.address_spinner)
//    Spinner spinner;
    private ArrayAdapter<String> arr_adapter;
    private String str;
    private int num;
    private String ischeck="false";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addaddress);
        height = getResources().getDisplayMetrics().heightPixels;
        ButterKnife.bind(this);
        tvTitle.setText("添加新地址");
        ClickEvent();

        swithSetdefault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(swithSetdefault.isChecked()){
                    ischeck="true";
                    System.out.println("true"+"================");
                }
                else if(!swithSetdefault.isChecked()){
                    ischeck="false";
                    System.out.println("false"+"-----------------");
                }

            }
        });



        Spinner spinner = (Spinner) findViewById(R.id.address_spinner);
        String[] a = {"海曙区", "江东区", "江北区", "北仑区", "镇海区", "鄞州区", "余姚市","慈溪市","奉化市","象山县","宁海县"};
        ArrayAdapter<String> ada = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, a);
        ada.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(ada);
        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                num = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    private void ClickEvent() {
    //    rlAddressArea.setOnClickListener(v -> initPw());
          btnSaveAddress.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  Pattern pattern = Pattern.compile("^[1][3-8][0-9]{9}$");
                  Matcher number2 = pattern.matcher(etAddressPhone.getText().toString());
                  String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
                  Pattern p = Pattern.compile(regEx);
                  Matcher m = p.matcher(etAddressReceiver.getText().toString());
                  if(m.find() || (etAddressReceiver.getText().toString()).equals("")|| (etAddressReceiver.getText().toString()).equals(" "))
                  {
                      Toast.makeText(AddaddressActivity.this, "姓名中包含特殊符号或为空", Toast.LENGTH_SHORT).show();
                  }
                  else if (!number2.matches()) {
                      // sflag = false;
                      Toast.makeText(AddaddressActivity.this, "号码输入不合法，请重新输入", Toast.LENGTH_SHORT).show();
                  }
                  else if((etDetailAddress.getText().toString()).equals("")){
                      Toast.makeText(AddaddressActivity.this, "地址不能为空", Toast.LENGTH_SHORT).show();
                  }
                  else {
                      SaveAddress();
                  }

              }
          });
        btnBack.setOnClickListener(v -> AddaddressActivity.this.finish());
    }
//

    private void SaveAddress() {
        /**
         * 1.UserTel;用户账号(电话 )
         2.UserPhyAdd; 用户地址 ( 唯一标识 )
         3.RecvTel;收货人电话
         4.CountyID;县级地址ID
         5.Details;详细地址
         6.IsDefault;是否设为默认,是则为ture
         */
        String tel = etAddressPhone.getText().toString();
        String name = etAddressReceiver.getText().toString();
        String num2 = String.valueOf(num+1);
        String s = etDetailAddress.getText().toString();

        System.out.println(num + "==================");
        System.out.println(MainActivity.strUniqueId+"66666666666666666666666666");
        System.out.println(ischeck+"---------------------");
        RetrofitUtil retrofitUtil = new RetrofitUtil(AddaddressActivity.this);
        Map<String, String> map = new HashMap<>();
        map.put("Function", "HttpNewRecvAddr");
        map.put("UserTel", LocalStorage.get("UserTel").toString());
        map.put("UserPhyAdd", MainActivity.strUniqueId);
        map.put("RecvTel", tel);
        map.put("RecvName", name);
        map.put("CountyID", num2);
        map.put("Details", s);
        map.put("IsDefault", ischeck);

        retrofitUtil.getStringDataFromNet("User", map, new RetrofitUtil.CallBack<String>() {
            @Override
            public void onLoadingDataComplete(String body) {
                String b = body.substring(7,11);
                if(b.equals("新建成功")){
                    Intent intent1 = new Intent(AddaddressActivity.this,AdressManageActivity.class);
                    startActivity(intent1);
                    finish();
                }
                else if(b.equals("新建失败")){
                    Toast.makeText(AddaddressActivity.this,"修改失败请重新编辑",Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(AddaddressActivity.this,"尚未登录",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onLoadingDataFailed(Throwable t) {

            }
        });
       // AddaddressActivity.this.finish();
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(AddaddressActivity.this, AdressManageActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}

//    private void initPw() {
//        View v = LayoutInflater.from(this).inflate(R.layout.pw_city_choose, null);
//        CityPicker cp = (CityPicker) v.findViewById(R.id.city_picker);
//        Button btn = (Button) v.findViewById(R.id.btn_pick);
//        pw = new PopupWindow(v, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
//        pw.setFocusable(true);
//        pw.setOutsideTouchable(true);
//        pw.setBackgroundDrawable(new BitmapDrawable());
//        pw.showAsDropDown(tvAddressDetail);
//        btn.setOnClickListener(v1 -> {
//            address = cp.getCity_string();
//            tvChoosePlase.setVisibility(View.INVISIBLE);
//            ivChooseArea.setVisibility(View.INVISIBLE);
//            pw.dismiss();
//            tvAddressDetail.setText(address);
//        });
//    }
