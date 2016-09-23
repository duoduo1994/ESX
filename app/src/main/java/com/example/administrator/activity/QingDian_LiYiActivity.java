package com.example.administrator.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
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

public class QingDian_LiYiActivity extends BaseActivity {
    @ViewInject(R.id.tv_hunqing_title)
    private TextView tv;
    @ViewInject(R.id.qing_dian_li_yi_liebiao)
    private ListView lv;

    @Override
    protected int setContentView() {
        return R.layout.activity_qing_dian__li_yi;
    }

    @Override
    protected void initView() {
        ActivityCollector.addActivity(this);
        mCache = ACache.get(this);
        tv.setText("庆典礼仪");

        findViewById(R.id.btn_hunqing_back).setOnClickListener(
                new OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        // TODO Auto-generated method stub
                        finishA();
                    }
                });
        lal = new ArrayList<CaiDan>();
        if (null != mCache.getAsString("庆典礼仪")) {
            String result = mCache.getAsString("庆典礼仪");
            tryc(result);
        } else {
            System.out.println(147852);
            getMsg();
        }

        setMoreListener();
    }


    private void tryc(String result) {
        try {
            JSONArray json = new JSONArray(result.trim());
            JSONObject tJson;
            if (null != json) {
                // System.out.println(configlist.length());
                if (json.length() >= 1) {
                    mCache.put("庆典礼仪", result, 60 * 60 * 24);
                }
                for (int i = 0; i < json.length(); i++) {
                    tJson = json.getJSONObject(i);
                    anli = new CaiDan();
                    ls = new ArrayList<String>();
                    anli.setIsPass("WeddingItemID");
                    anli.setImageUrl(tJson.getString("Icon"));
                    anli.setName(tJson.getString("Name"));
                    anli.setPlace(tJson.getString("Price"));
                    for (int j = 0; j < tJson.getJSONArray("ImageUrl").length(); j++) {
                        ls.add((String) tJson.getJSONArray("ImageUrl").get(j));
                    }
                    anli.setLs(ls);
                    lal.add(anli);
                }

            }
            caal = new CommonAdapter<CaiDan>(QingDian_LiYiActivity.this, lal, R.layout.item_hunqinggongsi) {

                @Override
                public void convert(ViewHolder helper, CaiDan item) {
                    // TODO Auto-generated method stub
                    helper.setVis(R.id.beijing);
                    helper.setText(R.id.hunqing_gongsi, item.getName());
                    helper.loadImage(R.id.shipei_tupian, item.getImageUrl());
                }

                @Override
                public void convert(ViewHolder helper, AnLi item) {

                }
            };
            lv.setAdapter(caal);
            lv.setOnItemClickListener(new OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> arg0,
                                        View arg1, int arg2, long arg3) {
                    // TODO Auto-generated method stub
                    Intent mIntent = new Intent(QingDian_LiYiActivity.this, HunQing_TaoCanXiangQingActivity.class);
                    Bundle mBundle = new Bundle();
                    mBundle.putSerializable("qingdianliyixq", lal.get(arg2));
                    mIntent.putExtras(mBundle);

                    startActivity(mIntent);
                }
            });
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            System.out.println("G" + e.getMessage());
//			Toast.makeText(QingDian_LiYiActivity.this,
//					e.getMessage(),
//					Toast.LENGTH_SHORT).show();
        }
    }

    private ACache mCache;
    @ViewInject(R.id.iv_more)
    private ImageView iv_more;

    private void setMoreListener() {
        iv_more.setOnClickListener(new IvListener(iv_more, QingDian_LiYiActivity.this, 0));
    }

    private CaiDan anli;
    private CommonAdapter<CaiDan> caal;
    private List<CaiDan> lal;
    private List<String> ls;

    private void getMsg() {


        XUtilsHelper xUtilsHelper1 = new XUtilsHelper(this, "weddingHandler.ashx?Action=Ceremony");
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
                    toast(QingDian_LiYiActivity.this,
                            getString(R.string.conn_failed));
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
//		SmartFruitsRestClient.post("weddingHandler.ashx?Action=Ceremony", p,
//				new AsyncHttpResponseHandler() {
//
//					@Override
//					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
//						// TODO Auto-generated method stub
//						String result = new String(arg2);
//						System.out.println(result);
//						tryc(result);
//					}
//
//					@Override
//					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
//							Throwable arg3) {
//						// TODO Auto-generated method stub
//						System.out.println(arg3+"asdfgdhgsa");
//						if(ciShu>=3){
//							isFalse= false;
//							Toast.makeText(QingDian_LiYiActivity.this,
//									getString(R.string.conn_failed),
//									Toast.LENGTH_SHORT).show();
//						};
//
//						if(isFalse){
//							getMsg();
//							ciShu++;
//						}
//
//
//
//					}
//				});
    }

    private boolean isFalse = true;
    private int ciShu = 0;
}
