package com.example.administrator.ab.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.administrator.activity.QueRen_DingDan;
import com.example.administrator.entity.Product;
import com.example.administrator.myapplication.R;

import java.io.Serializable;
import java.util.List;

/**
 * Created by User on 2016/10/13.
 */

public class CcommodityPresenter implements View.OnClickListener {

    /**
     * 弹窗
     */
    public MyDialog mBottomSheetDialog;
    private Activity mActivity;
    private View contentView;
    private TextView tvPrice;
    private TextView tvShopName;
    private ImageView ivShopPhoto;
    private TextView tvNum;
    private TextView none_peicai;
    private ListView peicai;
    private Button goInput, immediately, zhucai;
    private TextView zongjia;
    MyAdapter myAdapter;
    private ScrollView myscrollview;
    private ImageView iv_close;
    private List<Product> datas;
    String chanpingdanjia;
    String ImageUrl;

    /**
     * 增加、减少
     */
    private Button btnCut, btnAdd;

    public CcommodityPresenter(Activity mActivity) {
        this.mActivity = mActivity;

    }


    public void showDialog(String chanpingdanjia, List<Product> datas, String ImageUrl, String fresh_name, String ProductID) {

        this.chanpingdanjia = chanpingdanjia.substring(0, chanpingdanjia.length() - 1);
        this.datas = datas;
        this.ImageUrl=ImageUrl;

        //----------------弹出窗口
        mBottomSheetDialog = new MyDialog(mActivity, R.style.GoodDialog);
        //设置退出速度
        mBottomSheetDialog.outDuration(100);
        mBottomSheetDialog.inDuration(100);
        //设置铺满
        mBottomSheetDialog.heightParam(ViewGroup.LayoutParams.WRAP_CONTENT);
        //解析视图
        contentView = LayoutInflater.from(mActivity).inflate(R.layout.layout_by_shop, null);
        //设置视图
        mBottomSheetDialog.setContentView(contentView);

        myscrollview = (ScrollView) contentView.findViewById(R.id.myscrollview);
        myscrollview.smoothScrollTo(0, 20);//scrollview置顶
        tvPrice = (TextView) contentView.findViewById(R.id.tv_shop_price);//商品价格
        tvShopName = (TextView) contentView.findViewById(R.id.tv_shop_name);//商品内容
        ivShopPhoto = (ImageView) contentView.findViewById(R.id.iv_shop_photo);//照片

        tvNum = (TextView) contentView.findViewById(R.id.tv_shop_num);//数量

        zhucai = (Button) contentView.findViewById(R.id.zhucai);//主菜

        btnCut = (Button) contentView.findViewById(R.id.btn_shop_cut);//减
        btnAdd = (Button) contentView.findViewById(R.id.btn_shop_add);//加
        none_peicai = (TextView) contentView.findViewById(R.id.none_peicai);//不需要配菜
        none_peicai.setOnClickListener(this);

        peicai = (ListView) contentView.findViewById(R.id.peicai);//配菜

        goInput = (Button) contentView.findViewById(R.id.btn_buy_input_message);//加入购物车
        immediately = (Button) contentView.findViewById(R.id.immediately);//立即预定
        zongjia = (TextView) contentView.findViewById(R.id.zongjia);//总价
        zongjia.setText(String.valueOf(Double.parseDouble(this.chanpingdanjia) * (Double.parseDouble(tvNum.getText().toString()))));

        iv_close = (ImageView) contentView.findViewById(R.id.iv_close);//关闭
        iv_close.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
        btnCut.setOnClickListener(this);
//        Load.imageLoader
//                .displayImage((strUrl), ivShopPhoto, Load.options);
        immediately.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String jiage= chanpingdanjia.substring(0, chanpingdanjia.length() - 1);
                double p=Double.parseDouble(zongjia.getText().toString())-(Double.parseDouble(jiage) * (Double.parseDouble(tvNum.getText().toString())));

                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("list", (Serializable) datas);
                bundle.putString("danjia",jiage);
                bundle.putString("imageUrl",ImageUrl);
                bundle.putString("fresh_name",fresh_name);
                bundle.putString("num",tvNum.getText().toString());
                bundle.putString("zongjia",String.valueOf(p));
                bundle.putString("ProductID",ProductID);
                bundle.putInt("type",1);
                intent.putExtras(bundle);
                intent.setClass(mActivity, QueRen_DingDan.class);
                mActivity.startActivity(intent);



            }
        });



        myAdapter = new MyAdapter(datas);
        peicai.setAdapter(myAdapter);
        myAdapter.setOnAddNum(this);
        myAdapter.setOnSubNum(this);

        mBottomSheetDialog.show();

    }

    double price = 0;
    int flag = 1;

    @Override
    public void onClick(View v) {
        Object tag = v.getTag();
        switch (v.getId()) {
            case R.id.btn_add:

                if (tag != null && tag instanceof Integer) { //解决问题：如何知道你点击的按钮是哪一个列表项中的，通过Tag的position
                    int position = (Integer) tag;
                    //更改集合的数据
                    int num = datas.get(position).getNum();
                    double danjia = datas.get(position).getDanjia();
                    num++;
                    datas.get(position).setNum(num); //修改集合中商品数量
                    datas.get(position).setPrice(danjia * num); //修改集合中该商品总价 数量*单价
                    //解决问题：点击某个按钮的时候，如果列表项所需的数据改变了，如何更新UI
                    myAdapter.notifyDataSetChanged();
                }
                for (int i = 0; i < datas.size(); i++) {
                    price += datas.get(i).getPrice();

                }
                price = price + Double.parseDouble(chanpingdanjia) * (Double.parseDouble(tvNum.getText().toString()));

                zongjia.setText(String.format("%.2f", price));
                System.out.println(price + "总价" + Double.parseDouble(chanpingdanjia) * (Double.parseDouble(tvNum.getText().toString())));
                none_peicai.setBackgroundColor(Color.parseColor("#ffe2e7eb"));
                flag = 1;
                price = 0;
                break;
            case R.id.btn_cut:
                System.out.println(tag + "ggggggg");
                if (tag != null && tag instanceof Integer) {
                    int position = (Integer) tag;
                    //更改集合的数据
                    int num = datas.get(position).getNum();
                    double danjia = datas.get(position).getDanjia();
                    if (num > 0) {
                        num--;
                        datas.get(position).setNum(num); //修改集合中商品数量
                        datas.get(position).setPrice(danjia * num); //修改集合中该商品总价 数量*单价
                        myAdapter.notifyDataSetChanged();
                    }
                }
                for (int i = 0; i < datas.size(); i++) {
                    price += datas.get(i).getPrice();
                }
                for (int i = 0; i < datas.size(); i++) {
                    if (datas.get(i).getNum() > 0) {
                        break;
                    } else {
                        none_peicai.setBackgroundColor(Color.parseColor("#7bb118"));
                        flag = 0;
                    }
                }

                price = price + Double.parseDouble(chanpingdanjia) * (Double.parseDouble(tvNum.getText().toString()));
                zongjia.setText(String.format("%.2f", price));

                price = 0;
                break;
            case R.id.iv_close:
                mBottomSheetDialog.dismiss();
                break;
            case R.id.btn_shop_add:
                int bo = Integer.parseInt(tvNum.getText().toString());
                tvNum.setText(String.valueOf(bo + 1));

                zongjia.setText(String.valueOf(Double.parseDouble(zongjia.getText().toString()) + Double.parseDouble(chanpingdanjia)));
                break;
            case R.id.btn_shop_cut:
                int bi = Integer.parseInt(tvNum.getText().toString());
                tvNum.setText(String.valueOf(bi - 1));
                if (bi > 0) {
                    zongjia.setText(String.valueOf(Double.parseDouble(zongjia.getText().toString()) - Double.parseDouble(chanpingdanjia)));
                }

                break;
            //#ffe2e7eb
            case R.id.none_peicai:


                if (flag == 1) {
                    none_peicai.setBackgroundColor(Color.parseColor("#7bb118"));

                    for (int i = 0; i < datas.size(); i++) {
                        datas.get(i).setNum(0);
                        datas.get(i).setPrice(0);
                    }
                    price = Double.parseDouble(chanpingdanjia) * (Double.parseDouble(tvNum.getText().toString()));
                    zongjia.setText(String.valueOf(price));
                    myAdapter.notifyDataSetChanged();
                    price = 0;
                    flag = 0;
                } else {
                    System.out.println(123456);
                    none_peicai.setBackgroundColor(Color.parseColor("#ffe2e7eb"));
                    flag = 1;

                }
                break;


        }
    }


    private class MyAdapter extends BaseAdapter {
        LayoutInflater ll;
        private View.OnClickListener onAddNum;
        private View.OnClickListener onSubNum;
        private List<Product> products;

        MyAdapter(List<Product> products) {
            ll = LayoutInflater.from(mActivity);
            this.products = products;
        }

        public void setOnAddNum(View.OnClickListener onAddNum) {
            this.onAddNum = onAddNum;
        }

        public void setOnSubNum(View.OnClickListener onSubNum) {
            this.onSubNum = onSubNum;
        }

        @Override
        public int getCount() {
            int ret = 0;
            if (products != null) {
                ret = products.size();
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
                arg1 = ll.inflate(R.layout.item_peicai, null);
                v = new ViewHolder();

                v.add = (Button) arg1.findViewById(R.id.btn_add);
                v.add.setOnClickListener(onAddNum);
                v.cut = (Button) arg1.findViewById(R.id.btn_cut);
                v.cut.setOnClickListener(onSubNum);
                v.tv_peicai = (TextView) arg1.findViewById(R.id.tv_peicai);
                v.tv_num = (TextView) arg1.findViewById(R.id.tv_num);

                v.add.setTag(arg0);
                v.cut.setTag(arg0);


                System.out.println(products.get(arg0).getNum() + "kfdgjnjf");
                arg1.setTag(v);

            } else {
                v = (ViewHolder) arg1.getTag();
            }
            v.tv_peicai.setText(products.get(arg0).getName()+" "+products.get(arg0).getDanjia()+"元");
            v.tv_num.setText(products.get(arg0).getNum() + "");
            v.tv_peicai.setBackgroundColor(Color.parseColor("#ffe9eaeb"));
            if (products.get(arg0).getNum() > 0) {
                v.tv_peicai.setBackgroundColor(Color.parseColor("#7bb118"));
            } else {
                v.tv_peicai.setBackgroundColor(Color.parseColor("#ffe9eaeb"));
            }


            return arg1;
        }

        private class ViewHolder {
            Button add;
            Button cut;
            TextView tv_peicai;
            TextView tv_num;

        }

    }


}
