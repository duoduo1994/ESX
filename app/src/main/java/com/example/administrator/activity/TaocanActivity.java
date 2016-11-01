package com.example.administrator.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.entity.PeicaiBean;
import com.example.administrator.entity.TaocanDetailNBean;
import com.example.administrator.entity.TaocanDetialBean;
import com.example.administrator.fragment.TaocanCommentFragment;
import com.example.administrator.fragment.TaocanDetialFragment;
import com.example.administrator.list.ImageLoader;
import com.example.administrator.myapplication.R;
import com.example.administrator.net.RetrofitUtil;
import com.example.administrator.utils.Load;
import com.example.administrator.utils.LocalStorage;
import com.example.administrator.utils.LogUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.os.Build.ID;

public class TaocanActivity extends BaseActivity {
    @BindView(R.id.btn_buy_now)
    Button btnBuyNow;
    @BindView(R.id.btn_add_car)
    Button btnAddCar;
    @BindView(R.id.taocan_img)
    ImageView taocanImg;
    @BindView(R.id.tv_taocan_name)
    TextView tvTaocanName;
    @BindView(R.id.tv_taocan_price)
    TextView tvTaocanPrice;
    @BindView(R.id.tv_taocan_xiaoliang)
    TextView tvTaocanXiaoliang;
    @BindView(R.id.tv_taocan_commend)
    TextView tvTaocanCommend;
    private ViewPager vp;
    private TabLayout tab;
    private StringBuilder sb=new StringBuilder();
    private String taocanID, taocanImageUrl, taocanName, taocanPrice;
    ArrayList<String> arrayList = new ArrayList<String>();
    private RetrofitUtil<TaocanDetailNBean> taocanRetrofitUtil;
    private RetrofitUtil<PeicaiBean> peiRetrofitUtil;
    private String[] titles = new String[]{"内容", "评价"};
    private ArrayList<Fragment> list = new ArrayList<>();
    private List<TaocanDetialBean> tclist = new ArrayList<>();
    private List<PeicaiBean> pclist = new ArrayList<>();
    private TaocanDetailNBean detailNBean;
    TaocanDetialFragment taocanDetialFragment = new TaocanDetialFragment();


    @Override
    protected int setContentView() {

        return R.layout.activity_taocan;

    }


    @Override
    protected void initView() {
        ButterKnife.bind(this);
        taocanRetrofitUtil = new RetrofitUtil<>(TaocanActivity.this);
        peiRetrofitUtil = new RetrofitUtil<>(TaocanActivity.this);
        Toast.makeText(this, getIntent().getStringExtra("taocanid") + "123", Toast.LENGTH_LONG).show();
        taocanID = getIntent().getStringExtra("taocanid");
        taocanImageUrl = getIntent().getStringExtra("taocanImageUrl");
        taocanName = getIntent().getStringExtra("taocanName");
        taocanPrice = getIntent().getStringExtra("taocanPrice");
        initData();
//        ImageLoader.getInstance().loadImage(taocanImageUrl,taocanImg);
        Load.imageLoader.displayImage(taocanImageUrl,
                taocanImg, Load.options);
        tvTaocanName.setText(taocanName);
        tvTaocanPrice.setText(taocanPrice);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        vp = (ViewPager) findViewById(R.id.vp_taocan_fl);
        tab = (TabLayout) findViewById(R.id.tab);
        setSupportActionBar(toolbar);
        list.add(taocanDetialFragment);
        list.add(new TaocanCommentFragment());
        VpAdapter vd = new VpAdapter(getSupportFragmentManager());
        toolbar.setTitle("套餐详情");
        vp.setAdapter(vd);
        tab.setupWithViewPager(vp);
        btnBuyNow.setOnClickListener(v -> BuyNow());
        btnAddCar.setOnClickListener(v -> AddCar());
    }

    private void AddCar() {
        LogUtils.isLogin(TaocanActivity.this);
        //添加到购物车
        taocanDetialFragment.getList(new TaocanDetialFragment.LCallback() {
            @Override
            public void getList(List<PeicaiBean> plist) {
                sb.append("[");
                for (int i = 0; i < plist.size(); i++) {
                    sb.append("{\"ID\":\""+plist.get(i).getID()+"\",\"Count\":\"1\"}]");
                }
            }
        });
        RetrofitUtil addRetrofit = new RetrofitUtil(TaocanActivity.this);
        Map<String, String> map = new HashMap<>();
        map.put("Component", "Product");
        map.put("ProModule", "FreashSetMeal");
        map.put("Function", "HttpMoveToShoppingCart");
        //http://120.27.141.95:8221/ashx/Shopping.ashx?Component=Product&ProModule=FreashSetMeal&Function=HttpMoveToShoppingCart&UserTel=18727823918&UserPhyAdd=333333&ProID=3&GamishInfo=
        map.put("UserTel", LocalStorage.get("UserTel").toString());
        map.put("UserPhyAdd", LocalStorage.get("strUniqueId").toString());
//        toast(TaocanActivity.this,LocalStorage.get("UserTel").toString()+LocalStorage.get("strUniqueId").toString());
        map.put("ProID", taocanID);
        map.put("GamishInfo",sb.toString());
        LogUtils.i("添加到购物车", LocalStorage.get("UserTel").toString() + "str" + LocalStorage.get("strUniqueId").toString() + tclist.toArray().toString());
        addRetrofit.getStringDataFromNet("Shopping", map, new RetrofitUtil.CallBack<String>() {
            @Override
            public void onLoadingDataComplete(String body) {
                String result = "请重试";
                try {
                    JSONObject jo = new JSONObject(body.trim());
                    result = jo.getString("提示");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Toast.makeText(TaocanActivity.this, result, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onLoadingDataFailed(Throwable t) {
                Toast.makeText(TaocanActivity.this, "网络异常，请重新提交。", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void BuyNow() {
        LogUtils.isLogin(TaocanActivity.this);
        Intent i = new Intent(TaocanActivity.this, QueRen_DingDan.class);
        i.putExtra("type", 2);
        i.putExtra("taocanid", ID);
        i.putExtra("taocanImageUrl", taocanImageUrl);
        i.putExtra("taocanName", taocanName);
        i.putExtra("taocanPrice", taocanPrice);
        startActivity(i);

    }

    private void initData() {
        //Http://120.27.141.95:8221/ashx/Shopping.ashx?Component=Product&ProModule=FreashSetMeal&Function=HttpGetEntitysMainPage&ProID=1
        Map<String, String> map = new HashMap<>();
        map.put("Component", "Product");
        map.put("ProModule", "FreashSetMeal");
        map.put("Function", "HttpGetEntitysMainPage");
        map.put("ProID", taocanID);
        taocanRetrofitUtil.getBeanDataFromNet("Shopping", map, TaocanDetailNBean.class, new RetrofitUtil.CallBack<TaocanDetailNBean>() {
            @Override
            public void onLoadingDataComplete(TaocanDetailNBean body) {
                detailNBean = body;
                taocanDetialFragment.updateData1(detailNBean.getContent());
            }

            @Override
            public void onLoadingDataFailed(Throwable t) {
                toast(TaocanActivity.this, "yichang" + t.toString());
            }
        });
        //Http://120.27.141.95:8221/ashx/Shopping.ashx?Component=Product&ProModule=FreashSetMeal&Function=HttpQueryAllGamishs&ProID=1
        Map<String, String> map2 = new HashMap<>();
        map2.put("Component", "Product");
        map2.put("ProModule", "FreashSetMeal");
        map2.put("Function", "HttpQueryAllGamishs");
        map2.put("ProID", taocanID);
        peiRetrofitUtil.getListDataFromNet("Shopping", map2, PeicaiBean.class, new RetrofitUtil.CallBack2<PeicaiBean>() {
            @Override
            public void onLoadListDataComplete(List<PeicaiBean> list) {
                pclist.addAll(list);
                taocanDetialFragment.updateData(list);
            }

            @Override
            public void onLoadingDataFailed(Throwable t) {
                toast(TaocanActivity.this, "yichang" + t.toString());
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    class VpAdapter extends FragmentPagerAdapter {

        public VpAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return list.get(position);
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }
}
