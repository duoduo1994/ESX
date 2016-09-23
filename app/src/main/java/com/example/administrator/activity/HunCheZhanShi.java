package com.example.administrator.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.myapplication.R;
import com.example.administrator.utils.ActivityCollector;
import com.example.administrator.utils.CarLoad;
import com.example.administrator.utils.Diolg;
import com.example.administrator.utils.IvListener;
import com.lidroid.xutils.view.annotation.ViewInject;

public class HunCheZhanShi extends BaseActivity {
	private String img = "", carname = "";
	private List<String> imageUrl ;
	private MyAdapter adapter;
	@ViewInject(R.id.hun_che_zhanshi)
	private ListView hun_che_zhanshi;
	@ViewInject(R.id.tv_title)
	private TextView tv;
	@ViewInject(R.id.carName)
	private TextView carName;
	@ViewInject(R.id.woyaoyuyue)
	private Button woyaoyuyue;
	@Override
	protected int setContentView() {
		return R.layout.tuijiancheduizhanshi;
	}
	@Override
	protected void initView() {


		ActivityCollector.addActivity(this);
		// RelativeLayout rl;
		// if(!HomeActivity.quan){
		// rl = (RelativeLayout) findViewById(R.id.g_eng_d_uo);
		// android.widget.LinearLayout.LayoutParams l =
		// (android.widget.LinearLayout.LayoutParams) rl.getLayoutParams();
		// l.topMargin = (int) (HomeActivity.a12);
		// rl.setLayoutParams(l);
		// }
		CarLoad.getLoad(HunCheZhanShi.this);

		tv.setText("图片展示");


		woyaoyuyue.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				System.out.println(123456);
				// TODO Auto-generated method stub
				new Diolg(HunCheZhanShi.this, "立即拨打", "返回", "联系电话400-8261-707",
						"提示", 3);
			}
		});
		imageUrl	= new ArrayList<String>();
		imageUrl.clear();
		Intent intent = this.getIntent();
		if (intent != null) {
			img = intent.getStringExtra("img");
			carname = intent.getStringExtra("carname");

		}

		carName.setText(carname);

		try {
			JSONArray im = new JSONArray(img.trim());
			for (int i = 0; i < im.length(); i++) {
				imageUrl.add(im.getString(i));
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		adapter = new MyAdapter();
		hun_che_zhanshi.setAdapter(adapter);

		findViewById(R.id.btn_back).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finishA();
			}
		});

		setMoreListener();

	}


	@ViewInject(R.id.iv_more)
	private ImageView iv_more;

	private void setMoreListener() {

		iv_more.setOnClickListener(new IvListener(iv_more, HunCheZhanShi.this,
				0));
	}

	private class MyAdapter extends BaseAdapter {
		LayoutInflater ll;

		MyAdapter() {
			ll = LayoutInflater.from(HunCheZhanShi.this);
		}

		@Override
		public int getCount() {

			return imageUrl.size();
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
				arg1 = ll.inflate(R.layout.chedui_zhanshi_item, null);
				v = new ViewHolder();

				v.image = (ImageView) arg1.findViewById(R.id.carImage);
				arg1.setTag(v);

			} else {
				v = (ViewHolder) arg1.getTag();
			}

			String strUrl = imageUrl.get(arg0);
			CarLoad.imageLoader
					.displayImage((strUrl), v.image, CarLoad.options);

			return arg1;
		}

		private class ViewHolder {
			ImageView image;

		}

	}




}
