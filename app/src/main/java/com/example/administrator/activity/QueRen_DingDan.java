package com.example.administrator.activity;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.ab.view.ToDoItem;
import com.example.administrator.entity.Product;
import com.example.administrator.fragment.CalendarFragment;
import com.example.administrator.myapplication.R;
import com.example.administrator.net.RetrofitUtil;
import com.example.administrator.net.XUtilsHelper;
import com.example.administrator.utils.Load;
import com.example.administrator.utils.LocalStorage;
import com.lidroid.xutils.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;

import static android.os.Build.ID;

/**
 * Created by User on 2016/10/21.
 */


public class QueRen_DingDan extends BaseActivity {
    @BindView(R.id.btn_back)
    Button btnBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.addsch)
    ImageView addsch;
    @BindView(R.id.iv_more)
    ImageView ivMore;
    @BindView(R.id.xiyan_caidan)
    TextView xiyanCaidan;
    @BindView(R.id.shoujianren)
    TextView shoujianren;
    @BindView(R.id.tel)
    TextView tel;
    @BindView(R.id.shouhuodizhi)
    TextView shouhuodizhi;
    @BindView(R.id.dateBtn)
    Button dateBtn;
    @BindView(R.id.dingdan_queren)
    ListView dingdanQueren;
    @BindView(R.id.tijiao_price)
    TextView tijiaoPrice;
    @BindView(R.id.tijiap)
    Button tijiap;
    @BindView(R.id.xuyao)
    CheckBox xuyao;
    @BindView(R.id.buxuyao)
    CheckBox buxuyao;
    @BindView(R.id.spinner_caixi)
    Spinner spinnerCaixi;
    List<Product> list_peicai=null;
    String danjia="";
    String danping_name="";
    String danping_image="";
    String danping_num="";
    String zongjia="";
    private String taocanID,taocanImageUrl,taocanName,taocanPrice;
    String ProductID="";
    int type;
    List<String> alist=new ArrayList<>();
    MyAdapter myAdapter;
    @BindView(R.id.caixi)
    TextView caixi;
    private ToDoItem toDoItem;
    private SimpleDateFormat sfDate;
    @Override
    protected int setContentView() {
        return R.layout.queren_dingdan;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        dizhi();//获取地址
        Bundle bundle = this.getIntent().getExtras();
        type=bundle.getInt("type");
        if(type==1){
            list_peicai = (List<Product>) bundle.getSerializable("list");
            danjia = bundle.getString("danjia");
            danping_name = bundle.getString("fresh_name");
            danping_image = bundle.getString("ImageUrl");
            danping_num = bundle.getString("num");
            zongjia = bundle.getString("zongjia");
            ProductID=bundle.getString("ProductID");
//            System.out.println(danjia + "," + danping_name + "," + danping_image + "," + danping_num + "," + zongjia + "," + list_peicai);
            myAdapter = new MyAdapter(list_peicai);
            dingdanQueren.setAdapter(myAdapter);
        }else if(type==2){
//            taocanID=getIntent().getStringExtra("taocanid");
//            taocanImageUrl=getIntent().getStringExtra("taocanImageUrl");
//            taocanName=getIntent().getStringExtra("taocanName");
//            taocanPrice=getIntent().getStringExtra("taocanPrice");
//            myAdapter = new MyAdapter(null);
//            danjia=taocanPrice;
//            danping_name=taocanName;
//            ProductID=taocanID;
//            danping_image=taocanImageUrl;
//            dingdanQueren.setAdapter(myAdapter);
        }


        xuyao.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    buxuyao.setChecked(false);
                } else {
                    buxuyao.setChecked(true);
                }
            }
        });
        buxuyao.setChecked(true);
        buxuyao.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    xuyao.setChecked(false);
                } else {
                    xuyao.setChecked(true);
                }
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvTitle.setText("确认订单");
        Date now = new Date();
        sfDate = new SimpleDateFormat("yyyy/MM/dd", Locale.CHINA);
        Bundle extras = getIntent().getExtras();
        if (extras == null || extras.get("FastDateTime") == null) {
            // 创建task为空的ToDoItem对象
            toDoItem = new ToDoItem("");
            // 新建待办事项，将界面中事件时间设置为当前时间
            dateBtn.setText(sfDate.format(now));
            toDoItem.setTime(now); // 设置ToDoItem事件时间
        }
        dateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment calendarFragment = new CalendarFragment(toDoItem
                        .getTime(), null, null, 3, null) {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        super.onDateSet(view, year, monthOfYear, dayOfMonth);
                        toDoItem.setTime(year, monthOfYear, dayOfMonth);
                        Date date = toDoItem.getTime();
                        dateBtn.setText(sfDate.format(date));
                    }

                };
                calendarFragment.show(getFragmentManager(), "calendarPcker");
            }
        });

         tijiap.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 if (type == 1) {
                     qianggou();
                 }else if (type==2){
                     BuyTC();
                 }
             }
         });


    }

    private void BuyTC() {
        RetrofitUtil RR=new RetrofitUtil(QueRen_DingDan.this);
        Map<String, String> map = new HashMap<>();
        map.put("Component", "ShoppingCar");
        map.put("Function", "HttpsSubmit");
        map.put("UserTel", LocalStorage.get("UserTel").toString());
        map.put("UserPhyAdd", MainActivity.strUniqueId);
        map.put("RecvTime", dateBtn.getText().toString());
        map.put("IsNeedCook", " "+xuyao.isChecked());
        map.put("Cuisine","江浙菜");
        map.put("ProArray", "[{\"ProductCgy\":\"FreashSetMeal\",\"ProID\",\""+alist.get(0)+"\"}]");
        RR.getStringDataFromNet("Shopping", map, new RetrofitUtil.CallBack<String>() {
            @Override
            public void onLoadingDataComplete(String body) {
                String result=" ";
                try {
                    JSONObject jo=new JSONObject(body);
                    result=jo.getString("提示");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                toast(QueRen_DingDan.this,result);
            }

            @Override
            public void onLoadingDataFailed(Throwable t) {
                toast(QueRen_DingDan.this,"网络异常，请重试");
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

public void qianggou(){
    String json=changeArrayDateToJson(list_peicai);
    System.out.println(json+"画家的话");
    RetrofitUtil r=new RetrofitUtil(QueRen_DingDan.this);
    Map<String, String> map = new HashMap<>();
    map.put("Component", "Product");
    map.put("ProModule", "Freash");
    map.put("Function", "HttpToBeRobbery");
    map.put("UserTel", LocalStorage.get("UserTel").toString());
    map.put("UserPhyAdd", MainActivity.strUniqueId);
    map.put("GamishInfo",json);
    map.put("ProID", ProductID);
    r.getStringDataFromNet("Shopping", map, new RetrofitUtil.CallBack<String>() {
        @Override
        public void onLoadingDataComplete(String body) {
            Toast.makeText(QueRen_DingDan.this,body,Toast.LENGTH_LONG).show();
        }

        @Override
        public void onLoadingDataFailed(Throwable t) {

        }
    });
}

    private JSONArray jsonArray;//JSONObject对象，处理一个一个集合或者数组
    private String jsonString;  //保存带集合的json字符串
    private JSONObject object;  //JSONObject对象，处理一个一个的对象
    private JSONObject object2;
    private String changeArrayDateToJson(List<Product> list_peicai) {  //把一个集合转换成json格式的字符串
        jsonArray=null;
        object=null;
        jsonArray = new JSONArray();
        object=new JSONObject();
        for (int i = 0; i < list_peicai.size(); i++) {  //遍历上面初始化的集合数据，把数据加入JSONObject里面
            object2 = new JSONObject();//一个user对象，使用一个JSONObject对象来装
            try {
                object2.put("ID", list_peicai.get(i).getID());  //从集合取出数据，放入JSONObject里面 JSONObject对象和map差不多用法,以键和值形式存储数据
                object2.put("Count", list_peicai.get(i).getNum());
                jsonArray.put(object2); //把JSONObject对象装入jsonArray数组里面
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

//        try {
//            object.put("userDate", jsonArray); //再把JSONArray数据加入JSONObject对象里面(数组也是对象)
//            //object.put("time", "2013-11-14"); //这里还可以加入数据，这样json型字符串，就既有集合，又有普通数据
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        jsonString=null;
        jsonString = jsonArray.toString(); //把JSONObject转换成json格式的字符串

        return jsonString;
    }


    public void dizhi() {
        XUtilsHelper xUtilsHelper1 = new XUtilsHelper(this, "User.ashx?Function=HttpQueryDefaultRecvAddr", 2);
        RequestParams requestParams = new RequestParams();
        requestParams.addBodyParameter("UserTel", LocalStorage.get("UserTel").toString());
        requestParams.addBodyParameter("UserPhyAdd", MainActivity.strUniqueId);
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                xUtilsHelper1.sendPost(requestParams, subscriber);
            }
        }).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {

                try {
                    JSONArray tjson = new JSONArray(s.trim());
                    if (s.contains("提示")) {
                        Toast.makeText(QueRen_DingDan.this, "你还没登入", Toast.LENGTH_LONG).show();
                    } else {
                        JSONObject json = tjson.getJSONObject(0);
                        String RecvID = json.getString("RecvID");
                        String RecvTel = json.getString("RecvTel");
                        String RecvName = json.getString("RecvName");
                        String ProvinceName = json.getString("ProvinceName");
                        String DistrictName = json.getString("DistrictName");
                        String CountyName = json.getString("CountyName");
                        String Details = json.getString("Details");
                        shoujianren.setText("收件人:" + RecvName);
                        tel.setText(RecvTel);
                        shouhuodizhi.setText("收货地址:" + ProvinceName + DistrictName + CountyName + Details);
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    System.out.println(2);
                }


            }
        });
    }


    private class MyAdapter extends BaseAdapter {
        LayoutInflater ll;
        private List<Product> products;
        String peicai_name = "";

        MyAdapter(List<Product> products) {
            ll = LayoutInflater.from(QueRen_DingDan.this);
            this.products = products;
        }

        @Override
        public int getCount() {
            int ret = 1;
            if (products.size() != 0 || products != null) {
                return ret=1;
            }
            return ret;
        }
        @Override
        public Object getItem(int arg0) {
            // TODO Auto-generated method stub
            return products.get(arg0);
        }

        @Override
        public long getItemId(int arg0) {
            // TODO Auto-generated method stub
            return arg0;
        }

        /*
         * 重写view
         */
        @Override
        public View getView(int arg0, View arg1, ViewGroup arg2) {
            ViewHolder v = null;

            if (arg1 == null) {
                arg1 = ll.inflate(R.layout.list_queren, null);
                v = new ViewHolder();
                v.zhu_num = (TextView) arg1.findViewById(R.id.zhu_num);
                v.zhu_price = (TextView) arg1.findViewById(R.id.zhu_price);
                v.zhu_name = (TextView) arg1.findViewById(R.id.zhu_name);
                v.two_price = (TextView) arg1.findViewById(R.id.two_price);
                v.two_name = (TextView) arg1.findViewById(R.id.two_name);
                v.queren_image = (ImageView) arg1.findViewById(R.id.queren_image);
                arg1.setTag(v);

            } else {
                v = (ViewHolder) arg1.getTag();
            }

            if (products.size() != 0 || products != null) {
                v.zhu_num.setText("x" + danping_num);
                v.zhu_price.setText("￥" + danjia);
                v.zhu_name.setText(danping_name);
                Load.imageLoader
                        .displayImage((danping_image), v.queren_image, Load.options);


                if (products.size() != 0) {
                    for (int i = 0; i < products.size(); i++) {
                        peicai_name += products.get(i).getName() + "、";
                    }
                    v.two_name.setText(peicai_name);
                    v.two_price.setText("￥" + zongjia);
                }
            }
            return arg1;
        }

        private class ViewHolder {
            TextView zhu_num;
            TextView zhu_price;
            TextView zhu_name;
            TextView two_price;
            TextView two_name;
            ImageView queren_image;

        }

    }




}
