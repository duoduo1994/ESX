package com.example.administrator.activity;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.entity.AnLi;
import com.example.administrator.entity.CaiDan;
import com.example.administrator.list.CommonAdapter;
import com.example.administrator.list.ViewHolder;
import com.example.administrator.myapplication.R;
import com.example.administrator.net.XUtilsHelper;
import com.example.administrator.utils.ACache;
import com.example.administrator.utils.ActivityCollector;
import com.example.administrator.utils.Diolg;
import com.example.administrator.utils.IvListener;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.view.annotation.ViewInject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;

public class ShiRen_DingZhiActivity extends BaseActivity {
    @ViewInject(R.id.taocan_xiangqing)
    private LinearLayout yuYue;
    @ViewInject(R.id.tongyong_jiage)
    private TextView t1;
    @ViewInject(R.id.unit_price)
    private TextView t2;
    @ViewInject(R.id.tv_hunqing_title)
    private TextView biaoTi;
    @ViewInject(R.id.hunqing_taocan_xiangq)
    private TextView t;
    @ViewInject(R.id.woyaoyuyue_meiyong)
    private TextView meiyong;
    @ViewInject(R.id.kepianzhanshi)
    private TextView kp;
    @ViewInject(R.id.jiarudingdan)
    private Button b1;
    @ViewInject(R.id.hunqing_taocanxiangqing_tup)
    private ListView lv;
    private ACache mCache;

    @Override
    protected int setContentView() {
        return R.layout.activity_hun_qing__tao_can_xiang_qing;
    }

    @Override
    protected void initView() {
        ActivityCollector.addActivity(this);
        mCache = ACache.get(this);
        biaoTi.setText("私人定制");
        kp.setText("客片展示");
        t.setText("项目价格表");
        t1.setText("订制价格");
        meiyong.setVisibility(View.GONE);
        t1.setVisibility(View.GONE);
        t2.setText("");
        t2.setVisibility(View.GONE);
        b1.setText("我要预定");
        b1.setBackgroundColor(Color.parseColor("#e27386"));
        b1.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                new Diolg(ShiRen_DingZhiActivity.this, "立即拨打", "返回",
                        "联系电话18058516999", "提示", 7);
            }
        });
        findViewById(R.id.btn_hunqing_back).setOnClickListener(
                new OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        // TODO Auto-generated method stub
                       finishA();
                    }
                });
        if (null != mCache.getAsString("私人定制")) {
            String result = mCache.getAsString("私人定制");
            tryc(result);
        } else {
            System.out.println(147852);
            getMsg();
        }
        setMoreListener();
    }


    @ViewInject(R.id.iv_more)
    private ImageView iv_more;

    private void setMoreListener() {
        iv_more.setOnClickListener(new IvListener(iv_more,
                ShiRen_DingZhiActivity.this, 0));
    }

    private boolean isFalse = true;
    private int ciShu = 0;

    private void getMsg() {
        XUtilsHelper xUtilsHelper1 = new XUtilsHelper(this, "weddingHandler.ashx?Action=PersonalTailor",1);
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
                    toast(ShiRen_DingZhiActivity.this, getString(R.string.conn_failed));
                }
                if (isFalse) {
                    getMsg();
                    ciShu++;
                }

            }

            @Override
            public void onNext(String s) {
                tryc(s);
            }
        });

    }

    private List<CaiDan> lcd;
    private CaiDan cd;
    private List<String> lsu;

    private void tryc(String result) {
        lcd = new ArrayList<CaiDan>();
        try {
            JSONArray json = new JSONArray(result.trim());
            for (int i = 0; i < json.length(); i++) {
                JSONObject t = json.getJSONObject(i);
                mCache.put("私人定制", result, 60 * 60 * 24);
                cd = new CaiDan();
                cd.setName(t.getString("name"));
                cd.setMaterial(t.getString("Cont"));
                JSONArray tuPian = t.getJSONArray("Img");
                lsu = new ArrayList<String>();
                for (int j = 0; j < tuPian.length(); j++) {
                    lsu.add((String) tuPian.get(j));
                }
                cd.setLs(lsu);
                lcd.add(cd);
            }
            cas = new CommonAdapter<String>(ShiRen_DingZhiActivity.this, lsu,
                    R.layout.item_hunq_zuop) {

                @Override
                public void convert(ViewHolder helper, String item) {
                    // TODO Auto-generated method stub
                    helper.loadImage(R.id.hunqing_zuop, item);
                }

                @Override
                public void convert(ViewHolder helper, AnLi item) {

                }
            };
            lv.setAdapter(cas);
            yuYue.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    // TODO Auto-generated method stub
                    Intent i = new Intent(ShiRen_DingZhiActivity.this,
                            HunQing_TaoCan_XiangQingActivity.class);
                    i.putExtra("taocanXX", lcd.get(0).getMaterial());
                    startActivity(i);
                }
            });
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            System.out.println(e.getMessage());
        }
    }

    private CommonAdapter<String> cas;
}
