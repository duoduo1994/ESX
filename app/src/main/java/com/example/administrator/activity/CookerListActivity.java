package com.example.administrator.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.entity.CookerBean;
import com.example.administrator.myapplication.R;
import com.example.administrator.utils.BaseRecyclerAdapter;
import com.example.administrator.utils.BaseRecyclerHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class CookerListActivity extends AppCompatActivity {

    @BindView(R.id.btn_back)
    Button btnBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_more)
    ImageView ivMore;
    @BindView(R.id.rcv_cooker_list)
    RecyclerView rcvCookerList;
    List<CookerBean> cList;
    BaseRecyclerAdapter<CookerBean> cAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cooker_list);
        ButterKnife.bind(this);
        btnBack.setOnClickListener(v -> CookerListActivity.this.finish());
        tvTitle.setText("厨师列表");
        cList=new ArrayList<>();
        initData();
        initAdapter();
    }

    private void initData() {
        //http://120.27.141.95:8221/ashx/CookRob.ashx?Function=HttpQueryAllEntitys
        for (int i = 0; i < 6; i++) {
            //public CookerBean(String goodAt, String ID, String imageUrl, String name, String serviceNum)
            cList.add(new CookerBean("江浙菜，川菜，徽菜，宁波特色菜","23","","月月酱","34"));
        }
    }

    private void initAdapter() {
        cAdapter=new BaseRecyclerAdapter<CookerBean>(this,cList,R.layout.item_cooker) {
            @Override
            public void convert(BaseRecyclerHolder holder, CookerBean item, int position, boolean isScrolling) {
                holder.setText(R.id.tv_cooker_name,item.getName());
                holder.setText(R.id.tv_cooker_goodat,item.getGoodAt());
                holder.setText(R.id.tv_cooker_num,"已接单"+item.getServiceNum());
                holder.setImageByUrl(R.id.iv_cooker_pic,item.getImageUrl());
            }
        };
        rcvCookerList.setLayoutManager(new LinearLayoutManager(this));
        rcvCookerList.setAdapter(cAdapter);
    }
}
