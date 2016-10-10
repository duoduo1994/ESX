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
import android.widget.Toast;

import com.example.administrator.ab.view.ImageCycleView;
import com.example.administrator.activity.HomeActivity;
import com.example.administrator.activity.HunQing_TaoCan_XiangQingActivity;
import com.example.administrator.activity.TaocanActivity;
import com.example.administrator.entity.AnLi;
import com.example.administrator.entity.ShouyeListBean;
import com.example.administrator.entity.ToppicBean;
import com.example.administrator.list.CommonAdapter;
import com.example.administrator.list.ViewHolder;
import com.example.administrator.myapplication.R;
import com.example.administrator.net.RetrofitUtil;
import com.example.administrator.net.XUtilsHelper;
import com.example.administrator.utils.BaseRecyclerAdapter;
import com.example.administrator.utils.BaseRecyclerHolder;
import com.example.administrator.utils.Load;
import com.example.administrator.utils.LogUtils;
import com.example.administrator.utils.ViewPagerAdapter;
import com.lidroid.xutils.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;

import static android.R.id.list;
import static android.os.Build.ID;
import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;
import static com.example.administrator.myapplication.R.id.anli;
import static com.example.administrator.myapplication.R.id.lv;
import static com.example.administrator.myapplication.R.id.rcv_showye;
import static com.example.administrator.myapplication.R.layout.item;


/**
 * Created by ${WuQiLian} on 2016/9/6.
 */
public class Tab1 extends Fragment implements View.OnClickListener {
    private View view;
    private LinearLayout xiangCun, siren, niqing, tiexing;
//    private ViewPager viewPager;
private ImageCycleView icv;
    private RecyclerView rcv_showye;
    private RetrofitUtil<ToppicBean> TopPicUtil;
    private RetrofitUtil<ShouyeListBean> ListPicUtil;
    private List<String> l;
    private List<ShouyeListBean> listBeanList;
    BaseRecyclerAdapter<ShouyeListBean> baseRecyclerAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        l=new ArrayList<>();
        listBeanList=new ArrayList<>();
        Load.getLoad(getActivity());
        TopPicUtil=new RetrofitUtil<>(getActivity());
        ListPicUtil=new RetrofitUtil<>(getActivity());
        UtilDemo();
        view = LayoutInflater.from(getActivity()).inflate(
                R.layout.item_1, null);
//        viewPager = (ViewPager) view.findViewById(R.id.viewpager_shou);
        rcv_showye= (RecyclerView) view.findViewById(R.id.rcv_showye);
        xiangCun = (LinearLayout) view.findViewById(R.id.xiangcun_xiyan);
        icv= (ImageCycleView) view.findViewById(R.id.icv_costom);
        siren = (LinearLayout) view.findViewById(R.id.siren_dingzhi);
        niqing = (LinearLayout) view.findViewById(R.id.niqing_woyuan);
        tiexing = (LinearLayout) view.findViewById(R.id.tiexin_daojia);
        xiangCun.setOnClickListener(this);
        siren.setOnClickListener(this);
        niqing.setOnClickListener(this);
        tiexing.setOnClickListener(this);

        rcv_showye.setLayoutManager(new LinearLayoutManager(getActivity()));
        baseRecyclerAdapter=new BaseRecyclerAdapter<ShouyeListBean>(getActivity(),listBeanList,R.layout.shouye_list_item) {
            @Override
            public void convert(BaseRecyclerHolder holder, ShouyeListBean item, int position, boolean isScrolling) {
                Load.imageLoader.displayImage(item.getImageUrl(), (ImageView) holder.getView(R.id.iv_shouye_list),Load.options);
            }
        };
        rcv_showye.setAdapter(baseRecyclerAdapter);
//        init();
        addImage();
        return view;
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
        XUtilsHelper xh1=new XUtilsHelper(getActivity(),"http://120.27.141.95:8221/ashx/Promoting.ashx?Function=GetAllPromote",2);
        RequestParams requestParams = new RequestParams();
        requestParams.addBodyParameter("Function", "GetAllPromote");
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                xh1.sendPost(null,subscriber);
            }
        }).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {

                baseRecyclerAdapter.notifyDataSetChanged();
                Log.i("onCompleted", "convert: "+listBeanList.toString());
                Toast.makeText(getActivity(),"convert: "+listBeanList.toString(),Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onError(Throwable e) {
                Toast.makeText(getActivity(),"convergggggggggggggt: "+e,Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNext(String s) {
                Log.i("onLoadingDataComplete: ", "o" + s);
                try {
                    JSONArray jsonArray= new JSONArray(s.trim());
//                    JSONArray jsonArray = ja1.getJSONArray(0);
                    JSONObject jo;
                    for (int i = 0; i < jsonArray.length(); i++) {
                        jo=jsonArray.getJSONObject(i);
                        ShouyeListBean slbean;
//                            /**
//                             * PublicityID : 20160922170800000000
//                             * ProductID : 1
//                             * ProductCgy : 1
//                             * ImageUrl : Http://120.27.141.95:8221/UploadFile/Promoting/100000000.jpg
//                             */
                            slbean=new ShouyeListBean(jo.getString("PublicityID"),jo.getString("ProductID"),jo.getString("ProductCgy"),jo.getString("ImageUrl"));
                        Log.i("ddddddddddddd", "osssssssssssssssssssnNext: "+listBeanList.toString());
                        listBeanList.add(slbean);
                    }
                    baseRecyclerAdapter.notifyDataSetChanged();
                    Log.i("ddddddddddddd", "osssssssssssssssssssnNext: "+listBeanList.toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
        }
    });
    }

    private void setpic() {
        ArrayList<String> a=new ArrayList();
        for (int i = 0; i <l.size() ; i++) {
            a.add(l.get(i));
        }
        Log.i( "onLoadingDataComplete: ", "o"+a.toString());
        icv.setImageResources(a,mAdCycleViewListener);
    }
    private ImageCycleView.ImageCycleViewListener mAdCycleViewListener = new ImageCycleView.ImageCycleViewListener() {

        @Override
        public void onImageClick(int position, View imageView) {
            // TODO 单击图片处理事件
            Toast.makeText(getActivity(), "position->"+position, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void displayImage(String imageURL, ImageView imageView) {
//            Glide.with(getActivity()).load(imageURL).into(imageView);
//            ImageLoader.getInstance().loadImage(imageURL,imageView);
            Load.imageLoader.displayImage(imageURL,
                    imageView, Load.options);
//            ImageLoader.getInstance().displayImage(imageURL, imageView);// 此处本人使用了ImageLoader对图片进行加装！
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
        }

    }


    private void addImage() {
        XUtilsHelper xUtilsHelper1 = new XUtilsHelper(getActivity(), "",1);
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
