package com.example.administrator.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;


import com.example.administrator.entity.AddressBean;
import com.example.administrator.myapplication.R;
import com.example.administrator.net.RetrofitUtil;
import com.example.administrator.utils.LocalStorage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.administrator.myapplication.R.id.check;
import static com.example.administrator.myapplication.R.id.map;


public class AdressManageActivity extends AppCompatActivity {

    @BindView(R.id.rcv_addresslist)
    //rcv_addresslist
            RecyclerView rcvAddresslist;
    @BindView(R.id.btn_add_address)
    Button btnAddAddress;
    @BindView(R.id.btn_back)
    Button btnBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    private RetrofitUtil<AddressBean> addressben;
    private List<AddressBean> lists = new ArrayList<AddressBean>();
    private  Myadapter myadapter;
    private String ischeck="false";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adress_manage);
        ButterKnife.bind(this);
        //System.out.println(LocalStorage.get("Usertel").toString()+"============================");
       // System.out.println(MainActivity.strUniqueId  +"===========================");
        tvTitle.setText("管理地址");
        btnBack.setOnClickListener(v -> AdressManageActivity.this.finish());
       // btnAddAddress.setOnClickListener(v -> startActivity(new Intent(AdressManageActivity.this, AddaddressActivity.class)));
        btnAddAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdressManageActivity.this, AddaddressActivity.class);
                startActivity(intent);
                finish();

            }
        });
        addressben = new RetrofitUtil<>(this);
        isLogin(AdressManageActivity.this);

         myadapter = new Myadapter();
        rcvAddresslist.setLayoutManager(new LinearLayoutManager(this));

    }


    class Myadapter extends RecyclerView.Adapter<Myadapter.MyViewHolder>{

        private int max_count=30;
    @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(AdressManageActivity.this).inflate(R.layout.item_addressall,parent,false));

            return holder;
       }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position){


            holder.txtpn.setText(lists.get(position).getRecvName());
            System.out.println(lists.get(position).getRecvName()+"==================");
            holder.txtphoto.setText(lists.get(position).getRecvTel());
            holder.txtaddress.setText(lists.get(position).getProvinceName()+lists.get(position).getDistrictName()+
                    lists.get(position).getCountyName()+lists.get(position).getDetails());
            System.out.println(lists.get(position).getIsDefault()+"++++++++++++++++++++++++");
            if(lists.get(position).getIsDefault().equals("true")){
                  holder.rbismoren.setChecked(true);
            }
            else if(lists.get(position).getIsDefault().equals("false")){
                holder.rbismoren.setChecked(false);
            }

            //设置默认值
            holder.rbismoren.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(holder.rbismoren.isChecked()){
                        ischeck = "true";
                        for(int j=0;j<lists.size();j++){
                            if(lists.get(j).getIsDefault().equals("true") &&j!=position){
                                lists.get(j).setIsDefault("false");
                            }
                        }

                    }
                    else {
                        ischeck="false";
                    }
                    Setmoren("选择默认值",position,ischeck);


                }
            });
            holder.rbupdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.putExtra("RecvID",lists.get(position).getRecvID());
                    intent.setClass(AdressManageActivity.this,AddressUpdate.class);
                    startActivity(intent);
                    finish();
                }
            });
            holder.rbdelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                   //     System.out.println(lists.get(position).getRecvID()+"=======================");
                        System.out.println(position+"------------------------");
                        Delete(lists.get(position).getRecvID());
                      lists.remove(position);
                      notifyItemRemoved(position);
                    if(position != lists.size()){ // 如果移除的是最后一个，忽略
                        notifyItemRangeChanged(position, lists.size() - position);
                        Setmoren("第一次浏览",1,"diao");
                    }
                }
            });
        }

        @Override
        public int getItemCount(){
        if (lists.size() < max_count) {
            return lists.size();
        }
        return max_count;
        }

        class MyViewHolder extends ViewHolder{
            public LinearLayout llViewHolder;
            TextView txtpn,txtphoto,txtaddress;
            CheckBox rbismoren,rbupdate,rbdelete;
            public MyViewHolder(View itemView) {
                super(itemView);
                    txtpn = (TextView) itemView.findViewById(R.id.address_personname);
                    txtphoto = (TextView) itemView.findViewById(R.id.address_phone);
                    txtaddress = (TextView) itemView.findViewById(R.id.address_name);
                    rbismoren = (CheckBox) itemView.findViewById(R.id.address_moren);
                    rbupdate = (CheckBox) itemView.findViewById(R.id.address_bianji);
                    rbdelete = (CheckBox) itemView.findViewById(R.id.address_shanchu);
                    llViewHolder =(LinearLayout) itemView;

            }
        }
    }

  List<AddressBean> list = new ArrayList<>();
    public  void init(){
//http://120.27.141.95:8221/ashx/User.ashx?Function=HttpQueryAllRecvAddr
        Map<String, String> map = new HashMap<>();
        map.put("Function","HttpQueryAllRecvAddr");
      //  map.put("UserTel", LocalStorage.get("Usertel").toString());
       // map.put("UserPhyAdd",LocalStorage.get("strUniqueId").toString());
        map.put("UserTel",LocalStorage.get("UserTel").toString());
        map.put("UserPhyAdd",MainActivity.strUniqueId);

        addressben.getListDataFromNet("User", map, AddressBean.class, new RetrofitUtil.CallBack2<AddressBean>(){
            @Override
            public void onLoadListDataComplete(List<AddressBean> list) {
                for(int i=0;i<list.size();i++)
                {
                    AddressBean add = new AddressBean();
                    add.setRecvID(list.get(i).getRecvID());
                    add.setRecvTel(list.get(i).getRecvTel());
                    add.setRecvName(list.get(i).getRecvName());
                    add.setProvinceName(list.get(i).getProvinceName());
                    add.setDistrictName(list.get(i).getDistrictName());
                    add.setCountyName(list.get(i).getCountyName());
                    add.setDetails(list.get(i).getDetails());
                    add.setIsDefault(list.get(i).getIsDefault());
                //    System.out.println(list.get(i).getRecvName()+"========================");
                    lists.add(add);
                }
                Setmoren("第一次浏览",1,"diao");
                rcvAddresslist.setAdapter(myadapter);
            }

            @Override
            public void onLoadingDataFailed(Throwable t) {

            }
        });

    }
    public void Delete(String s){
        //http://120.27.141.95:8221/ashx/User.ashx?Function=HttpDeleteRecvAddr&UserTel=123&UserPhyAdd=123&RecvAddrID=123
        RetrofitUtil retrofitUtil = new RetrofitUtil(AdressManageActivity.this);
        Map<String, String> m = new HashMap<>();
        m.put("Function","HttpDeleteRecvAddr");
        m.put("UserTel",LocalStorage.get("UserTel").toString());
        m.put("UserPhyAdd",MainActivity.strUniqueId);
        m.put("RecvAddrID",s);
        retrofitUtil.getStringDataFromNet("User", m, new RetrofitUtil.CallBack<String>(){

            @Override
            public void onLoadingDataComplete(String body) {
                myadapter.notifyDataSetChanged();

            }

            @Override
            public void onLoadingDataFailed(Throwable t) {

            }
        });

    }

    public  void isLogin(Context context){
        if (LocalStorage.get("LoginStatus").toString().equals("login")) {
            init();

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
                    Intent intent = new Intent(AdressManageActivity.this,
                            LoginInActivity.class);
                    intent.putExtra("flag", 5);
//                    startActivityForResult(intent, 123);
                    startActivity(intent);
                    finish();

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

    public void check(int i,String ischeck){
        RetrofitUtil retrofitUtil = new RetrofitUtil(AdressManageActivity.this);
        String num = String.valueOf(i+1);
        Map<String, String> map = new HashMap<>();
        map.put("Function","HttpUpdateRecvAddr");
        map.put("UserTel",LocalStorage.get("UserTel").toString());
        map.put("UserPhyAdd",MainActivity.strUniqueId);
        map.put("RecvID",lists.get(i).getRecvID());
        map.put("RecvTel",lists.get(i).getRecvTel());
        map.put("RecvName",lists.get(i).getRecvName());
        map.put("CountyID",num);
        map.put("Details",lists.get(i).getDetails());
        map.put("IsDefault",ischeck);
        System.out.println(ischeck+"000000000000");
        retrofitUtil.getStringDataFromNet("User", map, new RetrofitUtil.CallBack<String>(){

            @Override
            public void onLoadingDataComplete(String body) {
                String b = body.substring(7,11);
                System.out.println(b+"----------------------------");
                if(b.equals("修改成功")){
                lists.get(i).setIsDefault(ischeck);
                myadapter.notifyItemRangeChanged(0, lists.size());
                }
                else {
                    if(ischeck.equals("true")){
                        lists.get(i).setIsDefault("false");
                    }
                    else {lists.get(i).setIsDefault("true");}
                    myadapter.notifyItemRangeChanged(0, lists.size());
                    Toast.makeText(AdressManageActivity.this, "选择默认地址失败请重新选择", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onLoadingDataFailed(Throwable t) {
                System.out.println("++++++++++++++++++++++++");

            }
        });
    }


    public void Setmoren(String s,int position,String ischeck){
        int number=-1,i;
        //Http://120.27.141.95:8221/ashx/User.ashx?Function=HttpSetDefaultRecvAddr&UserTel=15558793823&UserPhyAdd=123456&RecvID= 1
        for( i=0;i<lists.size();i++){
            if(lists.get(i).getIsDefault().equals("true")){
                number=i;
            }
        }
        RetrofitUtil retrofitUtil = new RetrofitUtil(AdressManageActivity.this);
        Map<String, String> map = new HashMap<>();
        map.put("Function","HttpSetDefaultRecvAddr");
        map.put("UserTel",LocalStorage.get("UserTel").toString());
        map.put("UserPhyAdd",MainActivity.strUniqueId);
        System.out.println(number+"++++++++++默认值++++++++");
        if(number==-1) {
            map.put("RecvID",  "");
        }
        else {
            map.put("RecvID", lists.get(number).getRecvID());
        }
        retrofitUtil.getStringDataFromNet("User", map, new RetrofitUtil.CallBack<String>(){
            @Override
            public void onLoadingDataComplete(String body) {
                String b = body.substring(7,11);
                System.out.println(b+"========================");
                if(b.equals("设置成功") && s.equals("第一次浏览")){
                    System.out.println("++");
                }
                else if(b.equals("设置失败") && s.equals("第一次浏览")){
                    for(int i=0;i<lists.size();i++){
                        list.get(i).setIsDefault("false");
                    }
                    Toast.makeText(AdressManageActivity.this, "默认地址设置失败请重新设置", Toast.LENGTH_SHORT).show();
                    System.out.println("=-=-=-");
                }
                else if(b.equals("设置失败") && s.equals("选择默认值")){
                    Toast.makeText(AdressManageActivity.this, "选择默认地址失败请重新选择", Toast.LENGTH_SHORT).show();
                }
                else if (b.equals("设置成功") && s.equals("选择默认值")){
                    check(position,ischeck);
                }


            }

            @Override
            public void onLoadingDataFailed(Throwable t) {

            }
        });

    }


}
