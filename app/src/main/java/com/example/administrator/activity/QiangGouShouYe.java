package com.example.administrator.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.ab.view.CcommodityPresenter;
import com.example.administrator.ab.view.HorizontalListView;
import com.example.administrator.ab.view.ImageCycleView;
import com.example.administrator.ab.view.StarBar;
import com.example.administrator.entity.Bean;
import com.example.administrator.entity.PeiCai;
import com.example.administrator.entity.ToppicBean;
import com.example.administrator.myapplication.R;
import com.example.administrator.net.RetrofitUtil;
import com.example.administrator.utils.Load;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by User on 2016/10/12.
 */

public class QiangGouShouYe extends BaseActivity {
    private ImageCycleView icv;
    private RetrofitUtil<ToppicBean> TopPicUtil;
    private RetrofitUtil<Bean> beanRetrofitUtil;
    private RetrofitUtil<PeiCai> beanPeiCai;
    private List<String> l = new ArrayList<>();
    Bean b;
    private ImageView cai_xuanze, tuijiantaocan,qianggou_shangpingxiangqing;
    CcommodityPresenter mDialog;
    String ProductID;


    private TextView fresh_name, fresh_number, fresh_norm, fresh_pro;
    private TextView fresh_price, shihangjia, shichangjia, yishou, beizhu, quanbupingjia;
    private ListView fresh_pingjia;
    private HorizontalListView horizon_listview;
    StarBar starBar;


    @Override
    protected int setContentView() {
        return R.layout.qing_shou_ye;
    }

    @Override
    protected void initView() {

        Intent intent = this.getIntent();
        ProductID = intent.getStringExtra("ProID");//获取ID值
        System.out.println(ProductID + "hsdfksdbgbsdh");
        beanRetrofitUtil = new RetrofitUtil<>(this);
        beanPeiCai = new RetrofitUtil<>(this);

        fresh_name = (TextView) findViewById(R.id.fresh_name);//生鲜单品名称
        fresh_number = (TextView) findViewById(R.id.fresh_number);//生鲜规格数量
        fresh_norm = (TextView) findViewById(R.id.fresh_norm);//规格单位
        fresh_pro = (TextView) findViewById(R.id.fresh_pro);//产品单位/包
        beizhu = (TextView) findViewById(R.id.beizhu);//产品备注
        fresh_price = (TextView) findViewById(R.id.fresh_price);//产品抢购价格+产品单位/包
        shihangjia = (TextView) findViewById(R.id.shihangjia);//值同fresh_price
        shichangjia = (TextView) findViewById(R.id.shichangjia);//市场价
        shichangjia.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        yishou = (TextView) findViewById(R.id.yishou);//已售+产品销量
        horizon_listview = (HorizontalListView) findViewById(R.id.horizon_listview);//抢购列表
        fresh_pingjia = (ListView) findViewById(R.id.fresh_pingjia);//评价
        quanbupingjia = (TextView) findViewById(R.id.quanbupingjia);//评价人数
        qianggou_shangpingxiangqing= (ImageView) findViewById(R.id.qianggou_shangpingxiangqing);
        qianggou_shangpingxiangqing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(QiangGouShouYe.this, Shangping_xiangqing.class));
            }
        });
        getData();


        icv = (ImageCycleView) findViewById(R.id.costom);
        TopPicUtil = new RetrofitUtil<>(QiangGouShouYe.this);
        starBar = (StarBar) findViewById(R.id.starBar);

        // UtilDemo();
        //  zhucai();
        cai_xuanze = (ImageView) findViewById(R.id.cai_xuanze);
        mDialog = new CcommodityPresenter(QiangGouShouYe.this);
        cai_xuanze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(123456);
            String danjia=    fresh_price.getText().toString();
                mDialog.showDialog(danjia);
            }
        });
        tuijiantaocan = (ImageView) findViewById(R.id.tuijiantaocan);
        tuijiantaocan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(QiangGouShouYe.this, TuiJianCombo.class));
            }
        });


    }

    String shi;

    private void getData() {
        //http://120.27.141.95:8221/ashx/Shopping.ashx?Component=Product&ProModule=Freash&Function=HttpGetEntitysMainPage&ProID=1
        Map<String, String> map = new HashMap<>();
        map.put("Component", "Product");
        map.put("ProModule", "Freash");
        map.put("Function", "HttpGetEntitysMainPage");
        map.put("ProID", ProductID);
        beanRetrofitUtil.getBeanDataFromNet("Shopping", map, Bean.class, new RetrofitUtil.CallBack<Bean>() {
            @Override
            public void onLoadingDataComplete(Bean body) {
                b = body;
                fresh_name.setText(b.getBaseData().getName());
                fresh_number.setText(b.getBaseData().getNumber());
                fresh_norm.setText(b.getBaseData().getNormUnit());
                fresh_pro.setText(b.getBaseData().getProUnit());
                beizhu.setText(b.getBaseData().getRemark());
                BigDecimal qiang = new BigDecimal(b.getRobData());
                String qi = String.valueOf(qiang.setScale(1, BigDecimal.ROUND_HALF_UP));
                fresh_price.setText(qi + "/" + b.getBaseData().getProUnit());
                shihangjia.setText("￥"+qi + "/" + b.getBaseData().getProUnit());
                String shichang = b.getBaseData().getPrice();


                if ("".equals(shichang) || shichang == null) {
                    shi = "0.00";
                } else {

                    BigDecimal shic = new BigDecimal(b.getBaseData().getPrice());
                    shi = String.valueOf(shic.setScale(1, BigDecimal.ROUND_HALF_UP));
                }


                shichangjia.setText("￥"+shi + "/" + b.getBaseData().getProUnit());
                yishou.setText("已售" + b.getBaseData().getSales());
                setpic(b.getBaseData().getImageUrl());

                if ("".equals(b.getEvaluateOverView().getAvStar()) || b.getEvaluateOverView().getAvStar() == null) {
                    starBar.setStarMark(0);
                } else {

                    starBar.setStarMark(Float.parseFloat(b.getEvaluateOverView().getAvStar()));//设置星数
                }

                if ("".equals(b.getEvaluateOverView().getTotalCount()) || b.getEvaluateOverView().getTotalCount() == null) {
                    quanbupingjia.setText("0");
                } else {
                    quanbupingjia.setText(b.getEvaluateOverView().getTotalCount());//评价人数
                }


                myAdapter = new MyAdapter(b.getRecommendSetMeal());
                myAdapter_pinglun = new MyAdapter2(b.getEvaluateOverView().getDetails());
                horizon_listview.setAdapter(myAdapter);
                fresh_pingjia.setAdapter(myAdapter_pinglun);
            }


            @Override
            public void onLoadingDataFailed(Throwable t) {

            }
        });

        Map<String, String> m = new HashMap<>();
        m.put("Component", "Product");
        m.put("ProModule", "Freash");
        m.put("Function", "HttpQueryAllGarnishs");
        m.put("ProID", ProductID);
        beanPeiCai.getBeanDataFromNet("Shopping", map, PeiCai.class, new RetrofitUtil.CallBack<PeiCai>() {

                    @Override
                    public void onLoadingDataComplete(PeiCai body) {
                        mDialog.list();//传值过去
                        body.getPeicai_ID();
                    }


                    @Override
                    public void onLoadingDataFailed(Throwable t) {

                    }
                }


        );


    }


//    private void UtilDemo() {
//        Map<String, String> map = new HashMap<>();
//        map.put("Function", "GetTopPicture");
//        TopPicUtil.getBeanDataFromNet("Other", map, ToppicBean.class, new RetrofitUtil.CallBack<ToppicBean>() {
//            @Override
//            public void onLoadingDataComplete(ToppicBean body) {
//                l = body.getTop();
//                Log.i("onLoadingDataComplete: ", "o" + l.toString());
//                setpic();
//            }
//
//            @Override
//            public void onLoadingDataFailed(Throwable t) {
//            }
//        });
//    }

    private void setpic(String url) {
        ArrayList<String> a = new ArrayList();
//        for (int i = 0; i <l.size() ; i++) {
//            a.add(l.get(i));
//        }
        a.add(url);
        Log.i("onLoadingDataComplete: ", "o" + a.toString());
        icv.setImageResources(a, mAdCycleViewListener);
    }


    private ImageCycleView.ImageCycleViewListener mAdCycleViewListener = new ImageCycleView.ImageCycleViewListener() {

        @Override
        public void onImageClick(int position, View imageView) {
            // TODO 单击图片处理事件
            Toast.makeText(QiangGouShouYe.this, "position->" + position, Toast.LENGTH_SHORT).show();
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


    MyAdapter myAdapter;

    private class MyAdapter extends BaseAdapter {
        LayoutInflater ll;
        List<?> list;

        MyAdapter(List<?> list) {
            ll = LayoutInflater.from(QiangGouShouYe.this);
            this.list = list;
        }

        @Override
        public int getCount() {

            return list.size();
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
                arg1 = ll.inflate(R.layout.item_fresh, null);
                v = new ViewHolder();
                v.tao_name = (TextView) arg1.findViewById(R.id.tao_name);
                v.tao_neirong = (TextView) arg1.findViewById(R.id.tao_neirong);
                v.tao_price = (TextView) arg1.findViewById(R.id.tao_price);
                arg1.setTag(v);

            } else {
                v = (ViewHolder) arg1.getTag();
            }
            return arg1;
        }

        private class ViewHolder {
            TextView tao_name;
            TextView tao_neirong;
            TextView tao_price;

        }
    }


    MyAdapter2 myAdapter_pinglun;

    private class MyAdapter2 extends BaseAdapter {
        LayoutInflater ll;
        List<?> list;

        MyAdapter2(List<?> list) {
            ll = LayoutInflater.from(QiangGouShouYe.this);
            this.list = list;
        }

        @Override
        public int getCount() {

            return list.size();
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
                arg1 = ll.inflate(R.layout.item_pingjia, null);
                v = new ViewHolder();
                v.star = (StarBar) arg1.findViewById(R.id.star);
                v.evaluateTel = (TextView) arg1.findViewById(R.id.evaluateTel);
                v.evaluateDt = (TextView) arg1.findViewById(R.id.evaluateDt);
                v.evaluateDt = (TextView) arg1.findViewById(R.id.evaluateDt);
                v.details = (TextView) arg1.findViewById(R.id.details);
                arg1.setTag(v);

            } else {
                v = (ViewHolder) arg1.getTag();
            }

//            v.star.setStarMark(list.get(arg0).getAvStar());


            return arg1;
        }

        private class ViewHolder {
            StarBar star;
            TextView evaluateTel;
            TextView evaluateDt;
            TextView details;
        }
    }


}
