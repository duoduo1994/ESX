package com.example.administrator.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.ab.view.ImageCycleView;
import com.example.administrator.activity.HomeActivity;
import com.example.administrator.activity.HotTaocanActivity;
import com.example.administrator.activity.Panic_Buying;
import com.example.administrator.activity.TaocanActivity;
import com.example.administrator.entity.AllpicBean;
import com.example.administrator.entity.ShouyeListBean;
import com.example.administrator.entity.ToppicBean;
import com.example.administrator.myapplication.R;
import com.example.administrator.net.RetrofitUtil;
import com.example.administrator.net.XUtilsHelper;
import com.example.administrator.utils.BaseRecyclerAdapter;
import com.example.administrator.utils.BaseRecyclerHolder;
import com.example.administrator.utils.CountDown;
import com.example.administrator.utils.Load;
import com.example.administrator.utils.LogUtils;
import com.example.administrator.utils.ViewPagerAdapter;
import com.lidroid.xutils.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;



/**
 * Created by ${WuQiLian} on 2016/9/6.
 */
public class Tab1 extends Fragment implements View.OnClickListener {
    @BindView(R.id.textView6)
    TextView tv_qianggou_time;
    @BindView(R.id.iv_home_cooker)
    ImageView ivHomeCooker;
    @BindView(R.id.iv_home_fruit)
    ImageView ivHomeFruit;
    @BindView(R.id.iv_home_new)
    ImageView ivHomeNew;
    @BindView(R.id.iv_home_hot)
    ImageView ivHomeHot;
    private View view;
    private String tttt;
    private LinearLayout xiangCun, siren, niqing, tiexing;
    //    private ViewPager viewPager;
    private ImageCycleView icv;
    private RecyclerView rcv_showye;
    private RetrofitUtil<ToppicBean> TopPicUtil;
    private RetrofitUtil<ShouyeListBean> ListPicUtil;
    private RetrofitUtil TimeUtil;
    private List<String> l;
    private List<AllpicBean> allList;
    private List<ShouyeListBean> listBeanList;
    private ImageView iv_home_qianggou;
    private RelativeLayout rl_home_qianggou;
    BaseRecyclerAdapter<ShouyeListBean> baseRecyclerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        view = LayoutInflater.from(getActivity()).inflate(
                R.layout.item_1, null);
        ButterKnife.bind(this, view);
        TimeUtil=new RetrofitUtil(getActivity());
        QianggouCountDown();
        l = new ArrayList<>();
        listBeanList = new ArrayList<>();
        allList = new ArrayList<>();
        Load.getLoad(getActivity());
        TopPicUtil = new RetrofitUtil<>(getActivity());
        ListPicUtil = new RetrofitUtil<>(getActivity());
        UtilDemo();

//        viewPager = (ViewPager) view.findViewById(R.id.viewpager_shou);
        iv_home_qianggou = (ImageView) view.findViewById(R.id.iv_home_qianggou);
        rcv_showye = (RecyclerView) view.findViewById(R.id.rcv_showye);
        xiangCun = (LinearLayout) view.findViewById(R.id.xiangcun_xiyan);
        icv = (ImageCycleView) view.findViewById(R.id.icv_costom);
        siren = (LinearLayout) view.findViewById(R.id.siren_dingzhi);
        niqing = (LinearLayout) view.findViewById(R.id.niqing_woyuan);
        tiexing = (LinearLayout) view.findViewById(R.id.tiexin_daojia);
        rl_home_qianggou= (RelativeLayout) view.findViewById(R.id.rl_home_qianggou);
        xiangCun.setOnClickListener(this);
        siren.setOnClickListener(this);
        niqing.setOnClickListener(this);
        tiexing.setOnClickListener(this);
        ivHomeHot.setOnClickListener(this);
        rl_home_qianggou.setOnClickListener(this);
        rcv_showye.setLayoutManager(new LinearLayoutManager(getActivity()));
        baseRecyclerAdapter = new BaseRecyclerAdapter<ShouyeListBean>(getActivity(), listBeanList, R.layout.shouye_list_item) {
            @Override
            public void convert(BaseRecyclerHolder holder, ShouyeListBean item, int position, boolean isScrolling) {
                if(item.getImageUrl()==null){
// Load.imageLoader.displayImage(R.mipmap.nopic,(ImageView) holder.getView(R.id.iv_shouye_list), Load.options);
                }else{
                    Load.imageLoader.displayImage(item.getImageUrl(), (ImageView) holder.getView(R.id.iv_shouye_list), Load.options);
                }

            }
        };
        rcv_showye.setAdapter(baseRecyclerAdapter);
//        init();
        addImage();

        return view;
    }

    private void QianggouCountDown() {
        String EndTime ;
        String CurrentTime;
        //http://120.27.141.95:8221/ashx/Promoting.ashx?Function=GetRobbeyTime
        Map<String,String> map=new HashMap<>();
        map.put("Function","GetRobbeyTime");
        TimeUtil.getStringDataFromNet("Promoting", map, new RetrofitUtil.CallBack<String>() {
            @Override
            public void onLoadingDataComplete(String body) {
                tttt=body;
                try {
                    JSONObject jo=new JSONObject(tttt.trim());
                    String EndTime =jo.getString("EndTime");
                    String CurrentTime=jo.getString("CurrentTime");
                    String dt1=EndTime.substring(0,19);
                    String dt2=CurrentTime.substring(0,19);
                    Calendar c=Calendar.getInstance();
                    c.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dt1));
                    long et=c.getTimeInMillis();
                    c.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dt2));
                    long ct=c.getTimeInMillis();
                    long count=et-ct;
                    int counttime=(int)count;
                    CountDown.countdown(counttime)
                            .subscribe(new Subscriber<Integer>() {
                                @Override
                                public void onCompleted() {
                                    tv_qianggou_time.setText("抢购结束");
                                }

                                @Override
                                public void onError(Throwable e) {
                                    tv_qianggou_time.setVisibility(View.INVISIBLE);
                                }

                                @Override
                                public void onNext(Integer integer) {
                                    tv_qianggou_time.setText(integer/3600+": "+(integer-(integer/3600)*3600)/60+": "+(integer-(integer/60)*60));
                                }
                            });

                } catch (JSONException|ParseException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onLoadingDataFailed(Throwable t) {

            }
        });


    }

    private void UtilDemo() {
        Map<String, String> map = new HashMap<>();
        map.put("Function", "GetTopPicture");
        TopPicUtil.getBeanDataFromNet("Other", map, ToppicBean.class, new RetrofitUtil.CallBack<ToppicBean>() {
            @Override
            public void onLoadingDataComplete(ToppicBean body) {
                l = body.getTop();
                Log.i("onLoadingDataComplete: ", "o" + l.toString());
                setpic();
            }
            @Override
            public void onLoadingDataFailed(Throwable t) {
            }
        });
//http://120.27.141.95:8221/ashx/Promoting.ashx?Function=GetAllPromote
        XUtilsHelper xh1 = new XUtilsHelper(getActivity(), "Promoting.ashx?Function=GetAllPromote", 2);
//        RequestParams requestParams = new RequestParams();
//        requestParams.addBodyParameter("Function", "GetAllPromote");
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                xh1.sendPost(null, subscriber);
            }
        }).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {

                baseRecyclerAdapter.notifyDataSetChanged();
                Log.i("onCompleted", "convert: " + listBeanList.toString());
                Toast.makeText(getActivity(), "convert: " + listBeanList.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(getActivity(), "convergggggggggggggt: " + e, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(String s) {
                Log.i("onLoadingDataComplete: ", "o" + s);
                try {
                    JSONArray jsonArray = new JSONArray(s.trim());
                    JSONObject jo;
                    for (int i = 0; i < jsonArray.length(); i++) {
                        jo = jsonArray.getJSONObject(i);
                        ShouyeListBean slbean;
                        slbean = new ShouyeListBean(jo.getString("PublicityID"), jo.getString("ProductID"), jo.getString("ProductCgy"), jo.getString("ImageUrl"));
                        listBeanList.add(slbean);
                    }
                    baseRecyclerAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        XUtilsHelper xhqg = new XUtilsHelper(getActivity(), "Other.ashx?Function=GetPicture", 2);
//        RequestParams requestParams1 = new RequestParams();
//        requestParams.addBodyParameter("Function", "GetAllPromote");
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                xhqg.sendPost(null, subscriber);
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
                    JSONArray jo2 = new JSONArray(s.trim());
                    for (int i = 0; i < jo2.length(); i++) {
                        JSONArray jo3 = jo2.getJSONArray(i);
                        for (int j = 0; j < jo3.length(); j++) {
                            JSONObject jobj = jo3.getJSONObject(j);
                            /**
                             * Image : Http://120.27.141.95:8221/UploadFile/mediaImage/HomePage0007.jpg
                             * Type : 0
                             */
                            allList.add(new AllpicBean(jobj.getString("Image"), jobj.getString("Type")));
                        }
                        setAllpic(allList);
                    }
                    LogUtils.i("我的测试onCompletedwaimian", allList.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                Gson gson=new Gson();
//                    TypeToken<ArrayList<AllpicBean>> token=new TypeToken<ArrayList<AllpicBean>>(){
//                    };
//                    ArrayList<AllpicBean> aList=gson.fromJson(s,token.getType());
            }
        });


    }

    private void setAllpic(List<AllpicBean> allList) {
        for (int i = 0; i < allList.size(); i++) {
            if (allList.get(i).getType().equals("Panicbuy")) {
                Load.imageLoader.displayImage(allList.get(i).getImage(),
                        iv_home_qianggou, Load.options);
            } else if (allList.get(i).getType().equals("CookCgy")) {
                Load.imageLoader.displayImage(allList.get(i).getImage(),
                        ivHomeCooker, Load.options);
            }
            if (allList.get(i).getType().equals("Fresh")) {
                Load.imageLoader.displayImage(allList.get(i).getImage(),
                        ivHomeFruit, Load.options);
            }
            if (allList.get(i).getType().equals("NewGoods")) {
                Load.imageLoader.displayImage(allList.get(i).getImage(),
                        ivHomeNew, Load.options);
            }
            if (allList.get(i).getType().equals("PopularitySetMeal")) {
                Load.imageLoader.displayImage(allList.get(i).getImage(),
                        ivHomeHot, Load.options);
            }

        }
    }

    private void setpic() {
        ArrayList<String> a = new ArrayList();
        for (int i = 0; i < l.size(); i++) {
            a.add(l.get(i));
        }
        Log.i("onLoadingDataComplete: ", "o" + a.toString());
        icv.setImageResources(a, mAdCycleViewListener);
    }

    private ImageCycleView.ImageCycleViewListener mAdCycleViewListener = new ImageCycleView.ImageCycleViewListener() {

        @Override
        public void onImageClick(int position, View imageView) {
            // TODO 单击图片处理事件
            Toast.makeText(getActivity(), "position->" + position, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void displayImage(String imageURL, ImageView imageView) {
//            Glide.with(getActivity()).load(imageURL).into(imageView);
//            ImageLoader.getInstance().loadImage(imageURL,imageView);
            Load.imageLoader.displayImage(imageURL,
                    imageView, Load.options);
        }
    };

    private List<ImageView> ali;
    private List<String> ivUrl;
    private ImageView iv;
    ViewPagerAdapter vpa;

    private void init() {

        XUtilsHelper xUtilsHelper1 = new XUtilsHelper(getActivity(), "CooksHandler.ashx?Action=GroupAndSingleInfo", 1);
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
            }

            @Override
            public void onNext(String s) {

            }
        });
    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.xiangcun_xiyan:
                startActivity(new Intent(getActivity(), HomeActivity.class));
                break;
            case R.id.siren_dingzhi:
                startActivity(new Intent(getActivity(), TaocanActivity.class));
                break;
            case R.id.niqing_woyuan:
                LogUtils.isLogin(getActivity());
                Toast.makeText(getActivity(), "你请我援正在建设，敬请期待！", Toast.LENGTH_LONG).show();
                break;
            case R.id.tiexin_daojia:
                Toast.makeText(getActivity(), "贴心到家正在建设，敬请期待！", Toast.LENGTH_LONG).show();
                break;
            case R.id.iv_home_hot:
                startActivity(new Intent(getActivity(), HotTaocanActivity.class));
                break;

            case R.id.rl_home_qianggou:
                startActivity(new Intent(getActivity(), Panic_Buying.class));
                break;
        }

    }


    private void addImage() {
        XUtilsHelper xUtilsHelper1 = new XUtilsHelper(getActivity(), "", 1);
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
            }

            @Override
            public void onNext(String s) {
            }
        });
    }
}
