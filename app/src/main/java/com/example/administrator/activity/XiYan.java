package com.example.administrator.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.list.taocan;
import com.example.administrator.myapplication.R;
import com.example.administrator.net.XUtilsHelper;
import com.example.administrator.utils.ACache;
import com.example.administrator.utils.ActivityCollector;
import com.example.administrator.utils.IvListener;
import com.example.administrator.utils.Load;
import com.example.administrator.utils.LoadingDialog;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.view.annotation.ViewInject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;

public class XiYan extends BaseActivity implements OnClickListener {
    @ViewInject(R.id.sanll)
    private LinearLayout sanll;
    @ViewInject(R.id.sill)
    private LinearLayout sill;
    @ViewInject(R.id.wull)
    private LinearLayout wull;
    @ViewInject(R.id.zill)
    private LinearLayout zill;
    @ViewInject(R.id.xiyan_zixuan_taocan)
    private LinearLayout ziXuan;
    @ViewInject(R.id.sanxingjis)
    private TextView sanxingji;
    @ViewInject(R.id.sixingjis)
    private TextView sixingji;
    @ViewInject(R.id.wuxingjis)
    private TextView wuxingji;
    @ViewInject(R.id.zixuan)
    private TextView zixuan;
    @ViewInject(R.id.taocailist)
    private ListView lv;
    private int ID = 3;
    @ViewInject(R.id.tv_title)
    private TextView tv;
    private ACache mCache;
    public static Activity xt;

    @Override
    protected int setContentView() {
        return R.layout.xiyan;
    }


    @Override
    protected void initView() {
        ActivityCollector.addActivity(XiYan.this);
        xt = XiYan.this;
        mCache = ACache.get(this);
        Load.getLoad(this);
        ld = new LoadingDialog(XiYan.this, "正在加载，请稍后...");

        tv.setText("喜宴套餐");
        zhuTc = new ArrayList<taocan>();
        lv.setSelector(new ColorDrawable(Color.parseColor("#c0c0c0")));
        sanll.setOnClickListener(this);
        sill.setOnClickListener(this);
        wull.setOnClickListener(this);
        zill.setOnClickListener(this);
        findViewById(R.id.btn_back).setOnClickListener(
                new OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        // TODO Auto-generated method stub
                        if (System.currentTimeMillis() - HomeActivity.dianJiShiJian > 500) {
                            HomeActivity.dianJiShiJian = System.currentTimeMillis();
                            XiYan.this.finish();
                            ActivityCollector.finishAll();
                        }
                    }
                });
        ziXuan.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                startActivity(new Intent(XiYan.this,
                        FeastSetActivity.class));
            }
        });
        if (null != mCache.getAsString("喜宴套餐" + ID)) {
            String result = mCache.getAsString("喜宴套餐" + ID);
            tryc(result);
        } else {
            System.out.println(147852);
            getMsg(ID);
        }
        setMoreListener();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - HomeActivity.dianJiShiJian > 500) {
                HomeActivity.dianJiShiJian = System.currentTimeMillis();
                XiYan.this.finish();
                ActivityCollector.finishAll();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // TODO Auto-generated method stub
        super.onSaveInstanceState(outState);
        outState.putInt("ID", ID);

    }

    @ViewInject(R.id.iv_more)
    private ImageView iv_more;

    private void setMoreListener() {
        iv_more.setOnClickListener(new IvListener(iv_more, XiYan.this, 0));
    }


    @Override
    public void onClick(View arg0) {
        // TODO Auto-generated method stub
        switch (arg0.getId()) {
            case R.id.sanll:
                ziXuan.setVisibility(View.GONE);
                lv.setVisibility(View.VISIBLE);
                sanxingji.setTextColor(Color.BLACK);
                sanll.setBackgroundColor(Color.WHITE);
                sixingji.setTextColor(Color.parseColor("#adadad"));
                sill.setBackgroundColor(Color.parseColor("#e5e5e5"));
                wuxingji.setTextColor(Color.parseColor("#adadad"));
                wull.setBackgroundColor(Color.parseColor("#e5e5e5"));
                zixuan.setTextColor(Color.parseColor("#adadad"));
                zill.setBackgroundColor(Color.parseColor("#e5e5e5"));
                index = 0;
                ID = 3;
                if (null != mCache.getAsString("喜宴套餐" + ID)) {
                    String result = mCache.getAsString("喜宴套餐" + ID);
                    tryc(result);
                } else {
                    System.out.println(147852);
                    getMsg(ID);
                }
                isFirsts = true;
                break;
            case R.id.sill:
                ziXuan.setVisibility(View.GONE);
                lv.setVisibility(View.VISIBLE);
                sanxingji.setTextColor(Color.parseColor("#adadad"));
                sanll.setBackgroundColor(Color.parseColor("#e5e5e5"));
                sixingji.setTextColor(Color.BLACK);
                sill.setBackgroundColor(Color.WHITE);
                wuxingji.setTextColor(Color.parseColor("#adadad"));
                wull.setBackgroundColor(Color.parseColor("#e5e5e5"));
                zixuan.setTextColor(Color.parseColor("#adadad"));
                zill.setBackgroundColor(Color.parseColor("#e5e5e5"));
                index = 0;
                ID = 4;
                isFirsts = true;
                if (null != mCache.getAsString("喜宴套餐" + ID)) {
                    String result = mCache.getAsString("喜宴套餐" + ID);
                    tryc(result);
                } else {
                    System.out.println(147852);
                    getMsg(ID);
                }
                break;
            case R.id.wull:
                ziXuan.setVisibility(View.GONE);
                lv.setVisibility(View.VISIBLE);
                sanxingji.setTextColor(Color.parseColor("#adadad"));
                sanll.setBackgroundColor(Color.parseColor("#e5e5e5"));
                sixingji.setTextColor(Color.parseColor("#adadad"));
                sill.setBackgroundColor(Color.parseColor("#e5e5e5"));
                wuxingji.setTextColor(Color.BLACK);
                wull.setBackgroundColor(Color.WHITE);
                zixuan.setTextColor(Color.parseColor("#adadad"));
                zill.setBackgroundColor(Color.parseColor("#e5e5e5"));
                index = 0;
                ID = 5;
                isFirsts = true;
                if (null != mCache.getAsString("喜宴套餐" + ID)) {
                    String result = mCache.getAsString("喜宴套餐" + ID);
                    tryc(result);
                } else {
                    System.out.println(147852);
                    getMsg(ID);
                }
                break;
            case R.id.zill:
                lv.setVisibility(View.GONE);
                ziXuan.setVisibility(View.VISIBLE);
                sanxingji.setTextColor(Color.parseColor("#adadad"));
                sanll.setBackgroundColor(Color.parseColor("#e5e5e5"));
                sixingji.setTextColor(Color.parseColor("#adadad"));
                sill.setBackgroundColor(Color.parseColor("#e5e5e5"));
                wuxingji.setTextColor(Color.parseColor("#adadad"));
                wull.setBackgroundColor(Color.parseColor("#e5e5e5"));
                zixuan.setTextColor(Color.BLACK);
                zill.setBackgroundColor(Color.WHITE);
                break;
            default:
                break;
        }
    }

    private List<taocan> list = new ArrayList<taocan>();
    private List<taocan> fuList = new ArrayList<taocan>();

    //	private double a[];
    private taocan news;
    private boolean isFalse = true;
    private int ciShu = 0;
    private LoadingDialog ld;

    private void getMsg(final int selectIndex) {
        ld.showDialog();

        System.out.println(1 + "FGHJKL");


        XUtilsHelper xUtilsHelper1 = new XUtilsHelper(this, "MenuHandler.ashx?Action=Meals",1);
        RequestParams requestParams = new RequestParams();
        String in = Integer.toString(selectIndex);
        requestParams.addBodyParameter("Starlevel", in);
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
                    toast(XiYan.this, getString(R.string.conn_failed));
                }

                if (isFalse) {
                    getMsg(selectIndex);
                    ciShu++;
                }
            }

            @Override
            public void onNext(String s) {
                ld.closeDialog();

                System.out.println("返回的json数据：" + s);
                tryc(s);
            }
        });


//		RequestParams p = new RequestParams();
//		String in = Integer.toString(selectIndex);
//		p.put("Starlevel", in);
//		SmartFruitsRestClient.post("MenuHandler.ashx?Action=Meals", p,
//				new AsyncHttpResponseHandler() {
//
//					@Override
//					// 成功返回数据
//					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
//						ld.closeDialog();
//						String result = new String(arg2);
//						System.out.println("返回的json数据：" + result);
//						tryc(result);
//
//					}
//
//					@Override
//					// 失败返回数据
//					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
//							Throwable arg3) {
//						// TODO Auto-generated method stub
//						ld.closeDialog();
//						System.out.println(arg3 + "asdfgdhgsa");
//						if (ciShu >= 3) {
//							isFalse = false;
//
//							Toast.makeText(XiYan.this,
//									getString(R.string.conn_failed),
//									Toast.LENGTH_SHORT).show();
//						}
//						;
//						if (isFalse) {
//							getMsg(selectIndex);
//							ciShu++;
//						}
//
//					}
//				});
    }

    private int index = 0;
    private boolean isFirsts = true;
    private List<taocan> zhuTc;

    private void tryc(String result) {
        // 用适配器进行解析
        try {
            JSONObject js = new JSONObject(result.trim());
            JSONArray arry = js.getJSONArray("套餐");
            if (arry != null) {
                mCache.put("喜宴套餐" + ID, result, 60 * 10);
                int size = arry.length();
//				a = new double[size];
                JSONObject tJson = null;
                list.clear();
                fuList.clear();
                zhuTc.clear();
                for (int i = 0; i < size; i++) {
                    news = new taocan();
                    tJson = arry.getJSONObject(i);
                    String pkSetID = tJson.getString("SetMealID");// id
                    String Name = tJson.getString("Name");// 套餐名

                    // String fkSetCtID = tJson
                    // .getString("fkSetCtID");
                    String TotalPrice = tJson.getString("TotalPrice");
                    news.setFkSetCtID(tJson.getString("SetMealCgy"));
                    // String flg = tJson.getString("flg");
                    news.setPkSetID(pkSetID);
                    news.setName(Name);
                    news.setLei(tJson.getString("SetMealCgy"));
                    // news.setFkSetCtID(fkSetCtID);
                    news.setTotalPrice(TotalPrice);
                    // news.setFlg(flg);
                    news.setImg(tJson.getString("ImageUrl"));
                    zhuTc.add(news);
                }
//
//				catc = new CommonAdapter<taocan>(XiYan.this, zhuTc,
//						R.layout.item_hunqinggongsi) {
//
//					@Override
//					public void convert(com.eyoucab.list.ViewHolder helper,
//							taocan item) {
//						// TODO Auto-generated method stub
//						if(isFirsts){
//							helper.setText(R.id.hunqing_gongsi, item.getName());
//							helper.setVis(R.id.beijing);
//							helper.loadImage(R.id.shipei_tupian, item.getImg());}
//							helper.setTextColor(R.id.hunqing_gongsi,
//									Color.BLACK, Color.parseColor("#adadad"),
//									index);
//					}
//				};
//				lv.setAdapter(catc);
//				m = new MyAdapter1();
//				lv.setAdapter(m);
                m1 = new My();
                lv.setAdapter(m1);
                lv.setOnItemClickListener(new OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1,
                                            int arg2, long arg3) {
                        // TODO Auto-generated method stub
                        isFirsts = false;
                        index = arg2;
                        System.out.println(arg2);
                        m1.notifyDataSetChanged();
                        Intent i = new Intent(XiYan.this,
                                FeastSetActivity.class);
                        Bundle bundle = new Bundle();
                        // System.out.println(lal.get(arg2));
                        bundle.putSerializable("an", zhuTc.get(arg2));
                        i.putExtras(bundle);
                        startActivity(i);
                    }
                });
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
//			Toast.makeText(XiYan.this, e.getMessage(), Toast.LENGTH_SHORT)
//					.show();
        }
    }

    private My m1;

    private class My extends BaseAdapter {
        LayoutInflater li;

        My() {
            li = LayoutInflater.from(XiYan.this);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return zhuTc.size();
        }

        @Override
        public Object getItem(int arg0) {
            // TODO Auto-generated method stub
            return arg0;
        }

        @Override
        public long getItemId(int arg0) {
            // TODO Auto-generated method stub
            return arg0;
        }

        @Override
        public View getView(int arg0, View arg1, ViewGroup arg2) {
            myTag mt = null;
            System.out.println(arg0 + "*************************");
            if (arg1 == null) {
                System.out.println(zhuTc.size() + "!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                arg1 = li.inflate(R.layout.item_hunqinggongsi, null);
                mt = new myTag();
                mt.day = (ImageView) arg1.findViewById(R.id.shipei_tupian);
                mt.tv = (TextView) arg1.findViewById(R.id.hunqing_gongsi);
                mt.bj = (ImageView) arg1.findViewById(R.id.beijing);
                mt.day.setScaleType(ScaleType.FIT_XY);
                arg1.setTag(mt);
                System.out.println(arg0 + "______________________");
            } else {
                System.out.println(arg0 + "++++++++++++++++");
                mt = (myTag) arg1.getTag();
            }
            if (arg0 == index) {
                mt.tv.setTextColor(Color.BLACK);
            } else {
                mt.tv.setTextColor(Color.parseColor("#696969"));
            }
            mt.tv.setText(zhuTc.get(arg0).getName());
            Load.imageLoader.displayImage(zhuTc.get(arg0).getImg(), mt.day, Load.options);
            mt.bj.setVisibility(View.GONE);
            return arg1;
        }

    }

    private class myTag {
        TextView tv;
        ImageView day, bj;
    }
}
