package com.example.administrator.activity;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.entity.AnLi;
import com.example.administrator.entity.PeiCai;
import com.example.administrator.list.CommonAdapter;
import com.example.administrator.list.ViewHolder;
import com.example.administrator.myapplication.R;
import com.example.administrator.utils.Load;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 2016/10/13.
 */

public class TuiJianCombo  extends BaseActivity {

ListView lv_name,lv_can;
    private CommonAdapter<PeiCai> cf;
    private List<PeiCai> fatlist = new ArrayList<PeiCai>();
    private  ImageView  tao_picture;
    private CheckBox check;
    private TextView  peicai_name,peicai_price,peicai_xiangqing;
    @Override
    protected int setContentView() {
        return R.layout.tuijian_taocan;
    }

    @Override
    protected void initView() {
        mains();

        tao_picture= (ImageView) findViewById(R.id.tao_picture);
        peicai_name= (TextView) findViewById(R.id.peicai_name);
        peicai_price= (TextView) findViewById(R.id.peicai_price);
        peicai_xiangqing= (TextView) findViewById(R.id.peicai_xiangqing);
        check= (CheckBox) findViewById(R.id.check);


    }

    private int index = 0;
public void mains() {

    lv_name= (ListView) findViewById(R.id.lv_name);//左边
    lv_can= (ListView) findViewById(R.id.lv_can);//右边
    cf = new CommonAdapter<PeiCai>(TuiJianCombo.this, fatlist,
            R.layout.item_zitis) {
        @Override
        public void convert(ViewHolder helper, AnLi item) {

        }

        @Override
        public void convert(ViewHolder helper, PeiCai item) {
            // TODO Auto-generated method stub
            helper.setText(R.id.anliss, item.getPeicai_name());
            helper.setTextColor(R.id.anliss, Color.BLACK,
                    Color.parseColor("#adadad"), index);
            helper.setBack(index, R.id.anliss);
        }
    };
    lv_name.setAdapter(cf);
    my = new MyAdapter();
    lv_can.setAdapter(my);
    lv_name.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            // initview();//图片显示
            cf.notifyDataSetChanged();
            System.out.println(12);
            index = position;
          //  newList.clear();
//            for (int i = 0; i < lists.size(); i++) {
//                if (fatlist.get(position).getId()
//                        .equals(lists.get(i).getId())) {
//                    newList.add(lists.get(i));
//                    // System.out.println(lists.get(i).isiCho());
//                }
//            }
            my.notifyDataSetChanged();
            lv_can.setSelection(0);
        }
    });
}

    private MyAdapter my;
    private class MyAdapter extends BaseAdapter {
        LayoutInflater ll;

        MyAdapter() {
            ll = LayoutInflater.from(TuiJianCombo.this);
        }

        @Override
        public int getCount() {

            return 0;
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
                arg1 = ll.inflate(R.layout.item_caixixiangqing, null);
                v = new ViewHolder();
                v.tuijaincai_tupian= (ImageView) arg1.findViewById(R.id.tuijaincai_tupian);
                v.tuijiancai_tihuan= (TextView) arg1.findViewById(R.id.tuijiancai_tihuan);
                v.tuijiancai_mingzi= (TextView) arg1.findViewById(R.id.tuijiancai_mingzi);

                arg1.setTag(v);

            } else {
                v = (ViewHolder) arg1.getTag();
            }


        String strUrl="";//图片路径
            Load.imageLoader
                    .displayImage((strUrl), v.tuijaincai_tupian, Load.options);





            return arg1;
        }

        private class ViewHolder {
            ImageView tuijaincai_tupian;//图片
            TextView tuijiancai_tihuan;//替换
            TextView tuijiancai_mingzi;//名字


        }
    }










}
