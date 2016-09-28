package com.example.administrator.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.administrator.entity.AnLi;
import com.example.administrator.entity.CaiDan;
import com.example.administrator.list.CommonAdapter;
import com.example.administrator.list.ViewHolder;
import com.example.administrator.myapplication.R;
import com.example.administrator.net.XUtilsHelper;
import com.example.administrator.utils.ACache;
import com.example.administrator.utils.ActivityCollector;
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

public class TaoCan_LieBiaoActivity extends BaseActivity {

    @ViewInject(R.id.hunqing_taocan_liebiao)
    private ListView lieBiao;
    private ACache mCache;

    @Override
    protected int setContentView() {
        return R.layout.activity_tao_can__lie_biao;
    }

    @Override
    protected void initView() {
        ActivityCollector.addActivity(this);
        mCache = ACache.get(this);
        findViewById(R.id.taocan_liebiao_work_back).setOnClickListener(
                new OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        // TODO Auto-generated method stub
                        finishA();
                    }
                });
        lieBiao.setSelector(new ColorDrawable(Color.parseColor("#ffffff")));
        if (null != mCache.getAsString("婚庆套餐列表")) {
            String result = mCache.getAsString("婚庆套餐列表");
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
        iv_more.setOnClickListener(new IvListener(iv_more, TaoCan_LieBiaoActivity.this, 0));
    }

    private void getMsg() {

        XUtilsHelper xUtilsHelper1 = new XUtilsHelper(this, "weddingHandler.ashx?Action=GetSetMealList",1);
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
                    toast(TaoCan_LieBiaoActivity.this, getString(R.string.conn_failed));
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


//		RequestParams p = new RequestParams();
//		SmartFruitsRestClient.get("weddingHandler.ashx?Action=GetSetMealList",
//				p, new AsyncHttpResponseHandler() {
//
//					@Override
//					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
//						// TODO Auto-generated method stub
//						String result = new String(arg2);
//						System.out.println(result);
//						tryc(result);
//
//					}
//
//					@Override
//					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
//							Throwable arg3) {
//						// TODO Auto-generated method stub
//						System.out.println(arg3 + "asdfgdhgsa");
//						if (ciShu >= 3) {
//							isFalse = false;
//							Toast.makeText(TaoCan_LieBiaoActivity.this,
//									getString(R.string.conn_failed),
//									Toast.LENGTH_SHORT).show();
//						}
//						;
//						if (isFalse) {
//							getMsg();
//							ciShu++;
//						}
//
//					}
//				});
    }


    private void tryc(String result) {
        lcd = new ArrayList<CaiDan>();
        try {
            JSONArray json = new JSONArray(result.trim());
            System.out.println(json.length());
            if (json.length() >= 1) {
                mCache.put("婚庆套餐列表", result, 60 * 60 * 24);
            }
            //	System.out.println(json.getJSONObject(4));
            //	System.out.println(json.getJSONObject(3));
            for (int i = 0; i < json.length(); i++) {
                tJson = json.getJSONObject(i);
                System.out.println(i + "_____________" + tJson);
                cd = new CaiDan();
                cd.setName(tJson.getString("Name"));
                cd.setIsPass(tJson.getString("WeddingItemID"));
                cd.setPlace(tJson.getString("Price"));
                cd.setImageUrl(tJson.getString("Icon"));
                ls = new ArrayList<String>();
                for (int j = 0; j < tJson.getJSONArray("ImageUrl").length(); j++) {
                    System.out.println(tJson.getJSONArray("ImageUrl").get(j));
                    ls.add((String) tJson.getJSONArray("ImageUrl").get(j));
                }
                cd.setLs(ls);
                lcd.add(cd);
            }
            cacd = new CommonAdapter<CaiDan>(TaoCan_LieBiaoActivity.this, lcd,
                    R.layout.item_taocan) {


                @Override
                public void convert(ViewHolder helper, AnLi item) {

                }

                @Override
                public void convert(ViewHolder helper, CaiDan item) {
                    // TODO Auto-generated method stub
                    helper.setText(R.id.hunqing_taocan_item_mingzi,
                            item.getName());
                    helper.setText(R.id.hunqing_taocan_item_jiage,
                            "￥" + item.getPlace());
                    helper.loadImage(R.id.hunqing_taocan_item_tupian,
                            item.getImageUrl());

                }
            };
            lieBiao.setAdapter(cacd);
            lieBiao.setOnItemClickListener(new OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1,
                                        int arg2, long arg3) {
                    // TODO Auto-generated method stub
                    Intent mIntent = new Intent(TaoCan_LieBiaoActivity.this, HunQing_TaoCanXiangQingActivity.class);
                    Bundle mBundle = new Bundle();
                    mBundle.putSerializable("taocanxiangq", lcd.get(arg2));
                    mIntent.putExtras(mBundle);
                    startActivity(mIntent);
                }
            });
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            System.out.println("H" + e.getMessage());
        }
    }

    private JSONObject tJson;
    private List<CaiDan> lcd;
    private CaiDan cd;
    private List<String> ls;
    private CommonAdapter<CaiDan> cacd;
    private boolean isFalse = true;
    private int ciShu = 0;

}
