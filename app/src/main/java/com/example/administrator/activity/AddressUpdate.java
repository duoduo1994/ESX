package com.example.administrator.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.example.administrator.myapplication.R;
import com.example.administrator.net.RetrofitUtil;
import com.example.administrator.utils.LocalStorage;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.administrator.myapplication.R.id.et_address_phone;
import static com.example.administrator.myapplication.R.id.p;

public class AddressUpdate extends AppCompatActivity {


    @BindView(R.id.et_address_receiver)
    EditText etAddressReceiver;

    @BindView(R.id.et_address_phone)
    EditText etAddressPhone;

    @BindView(R.id.et_detail_address)
    EditText etDetailAddress;

    @BindView(R.id.btn_save_address)
    Button btnSaveAddress;
    @BindView(R.id.tv_title)
    TextView tvTitle;

//    @BindView(R.id.swith_setdefault)
//    Switch swithSetdefault;

    private String s;
    private int num;
    private String ischeck ="false";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addaddress);
        ButterKnife.bind(this);

        tvTitle.setText("修改地址");

        Intent intent=getIntent();
        s=intent.getStringExtra("RecvID");
        Spinner spinner = (Spinner) findViewById(R.id.address_spinner);
        String[] a = {"海曙区", "江东区", "江北区", "北仑区", "镇海区", "鄞州区", "余姚市","慈溪市","奉化市","象山县","宁海县"};
        ArrayAdapter<String> ada = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, a);
        ada.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(ada);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                num = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        Switch swithSetdefault = (Switch)findViewById(R.id.swith_setdefault);
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
        btnSaveAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tel = etAddressPhone.getText().toString();
                String name = etAddressReceiver.getText().toString();
                String num2 = String.valueOf(num+1);
                String detail = etDetailAddress.getText().toString();
                RetrofitUtil retrofitUtil = new RetrofitUtil(AddressUpdate.this);
                Map<String, String> map = new HashMap<>();
                map.put("Function","HttpUpdateRecvAddr");
                map.put("UserTel", LocalStorage.get("UserTel").toString());
                map.put("UserPhyAdd",MainActivity.strUniqueId);
                map.put("RecvID",s);
                map.put("RecvTel",tel);
                map.put("RecvName",name);
                map.put("CountyID",num2);
                map.put("Details",detail);
                map.put("IsDefault",ischeck);
                retrofitUtil.getStringDataFromNet("User", map, new RetrofitUtil.CallBack<String>(){

                    @Override
                    public void onLoadingDataComplete(String body) {
                        Intent intent1 = new Intent(AddressUpdate.this,AdressManageActivity.class);
                        startActivity(intent1);
                        finish();

                    }

                    @Override
                    public void onLoadingDataFailed(Throwable t) {

                    }
                });

            }
        });

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(AddressUpdate.this, AdressManageActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }



}
