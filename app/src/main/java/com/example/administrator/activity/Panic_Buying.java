package com.example.administrator.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.ab.view.MyProgress;
import com.example.administrator.entity.GetRobberyProducts;
import com.example.administrator.myapplication.R;
import com.example.administrator.net.RetrofitUtil;
import com.example.administrator.net.XUtilsHelper;
import com.example.administrator.utils.Load;
import com.lidroid.xutils.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;


/**
 * Created by User on 2016/10/11.
 */

public class Panic_Buying extends BaseActivity {


    Button btnBack;

    TextView tvTitle;
    private List<GetRobberyProducts> prg = new ArrayList<>();

    private RetrofitUtil<GetRobberyProducts> TopPicUtil;

    private MyAdapter adapter;
    private ListView list_push;

    @Override
    protected int setContentView() {
        return R.layout.panic_buying;
    }

    @Override
    protected void initView() {
        TopPicUtil = new RetrofitUtil<>(Panic_Buying.this);

        Load.getLoad(Panic_Buying.this);
        getmessage();
        list_push = (ListView) findViewById(R.id.list_push);
        btnBack= (Button) findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvTitle= (TextView) findViewById(R.id.tv_title);
        tvTitle.setText("秒抢购");
    }

    JSONObject tJson;
    GetRobberyProducts rob;

    private boolean isFalse = true;
    private int ciShu = 0;

    private void getmessage() {

        XUtilsHelper xUtilsHelper1 = new XUtilsHelper(this, "Promoting.ashx?Function=GetRobberyProducts", 2);
        RequestParams requestParams = new RequestParams();


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
                if (ciShu >= 3) {
                    isFalse = false;
                }
                ;
                if (isFalse) {
                    getmessage();
                    ciShu++;
                }
            }

            @Override
            public void onNext(String s) {
                try {
                    System.out.println(s.toString());
                    JSONArray js = new JSONArray(s.trim());
                    for (int i = 0; i < js.length(); i++) {
                        tJson = js.getJSONObject(i);
                        rob = new GetRobberyProducts();
                        rob.setRobberyID(tJson.getString("RobberyID"));
                        rob.setProductCgy(tJson.getString("ProductCgy"));
                        rob.setProductID(tJson.getString("ProductID"));
                        rob.setImageUrl(tJson.getString("ImageUrl"));
                        rob.setReMark(tJson.getString("ReMark"));
                        rob.setSales(tJson.getString("Sales"));
                        rob.setSurplusCount(tJson.getString("SurplusCount"));
                        rob.setOriginalPrice(tJson.getString("OriginalPrice"));
                        rob.setCurrentPrice(tJson.getString("CurrentPrice"));
                        rob.setNorm(tJson.getString("Norm"));
                        prg.add(rob);
                    }

                    adapter = new MyAdapter();
                    list_push.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }


    private class MyAdapter extends BaseAdapter {
        LayoutInflater ll;

        MyAdapter() {
            ll = LayoutInflater.from(Panic_Buying.this);
        }

        @Override
        public int getCount() {

            return prg.size();
        }

        @Override
        public Object getItem(int arg0) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public long getItemId(int arg0) {
            // TODO Auto-generated method stub
            return 0;
        }

        /*
         * 重写view
         */
        @Override
        public View getView(int arg0, View arg1, ViewGroup arg2) {
            ViewHolder v = null;
            if (arg1 == null) {
                arg1 = ll.inflate(R.layout.item_push, null);
                v = new ViewHolder();
                v.myProgress = (MyProgress) arg1.findViewById(R.id.pgsBar);
                v.push_picture = (ImageView) arg1.findViewById(R.id.push_picture);
                v.residue = (TextView) arg1.findViewById(R.id.residue);
                v.push_name = (TextView) arg1.findViewById(R.id.push_name);
                v.xianjia = (TextView) arg1.findViewById(R.id.xianjia);
                v.guige = (TextView) arg1.findViewById(R.id.guige);
                v.yuanjia = (TextView) arg1.findViewById(R.id.yuanjia);
                v.push_qiang = (Button) arg1.findViewById(R.id.push_qiang);

                arg1.setTag(v);

            } else {
                v = (ViewHolder) arg1.getTag();
            }

            v.push_name.setText(prg.get(arg0).getReMark());
            int sales = Integer.parseInt(prg.get(arg0).getSales());

            v.residue.setText("已抢购" + sales + "件");
            int SurplusCount = Integer.parseInt(prg.get(arg0).getSurplusCount());
            int percentage = sales / (sales + SurplusCount);
            v.myProgress.setProgress(percentage);
            if ("".equals(prg.get(arg0).getCurrentPrice()) || prg.get(arg0).getCurrentPrice() == null) {
                v.xianjia.setText("0/");
            } else {
                BigDecimal bd = new BigDecimal(prg.get(arg0).getCurrentPrice());
                bd = bd.setScale(1, BigDecimal.ROUND_HALF_UP);
                v.xianjia.setText(bd + "/");
            }
            if ("".equals(prg.get(arg0).getOriginalPrice()) || prg.get(arg0).getOriginalPrice() == null) {
                v.yuanjia.setText("￥0");
            } else {
                BigDecimal b = new BigDecimal(prg.get(arg0).getOriginalPrice());
                b = b.setScale(1, BigDecimal.ROUND_HALF_UP);
                v.yuanjia.setText("￥" + b);
            }

            v.guige.setText(prg.get(arg0).getNorm());

            v.yuanjia.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);

            String strUrl = prg.get(arg0).getImageUrl();
            Load.imageLoader
                    .displayImage((strUrl), v.push_picture, Load.options);


            v.push_qiang.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.putExtra("ProID", prg.get(arg0).getProductID());
                    intent.setClass(Panic_Buying.this, QiangGouShouYe.class);
                    startActivity(intent);
                }
            });


            return arg1;
        }

        private class ViewHolder {
            ImageView push_picture;
            MyProgress myProgress = null;
            TextView residue;
            Button push_qiang;
            TextView push_name;
            TextView xianjia;
            TextView guige;
            TextView yuanjia;

        }
    }
}
