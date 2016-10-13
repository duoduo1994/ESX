package com.example.esycab;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import com.example.esycab.utils.ActivityCollector;
import com.example.esycab.utils.CarLoad;
import com.example.esycab.utils.Diolg;
import com.example.esycab.utils.IvListener;

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

public class HunCheZhanShi extends Activity {
	private String img = "", carname = "";
	private List<String> imageUrl ;
	private MyAdapter adapter;
	private ListView hun_che_zhanshi;
	private TextView tv, carName;
	private Button woyaoyuyue;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
			// 透明状态栏
			getWindow().addFlags(
					WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON);
			// 透明导航栏
			// getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
		} else {
			this.requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏
			this.getWindow().setFlags(
					WindowManager.LayoutParams.FLAG_FULLSCREEN,
					WindowManager.LayoutParams.FLAG_FULLSCREEN);// 去掉信息栏
		}
		setContentView(R.layout.tuijiancheduizhanshi);
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
		tv = (TextView) findViewById(R.id.tv_title);
		tv.setText("图片展示");
		woyaoyuyue = (Button) findViewById(R.id.woyaoyuyue);

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
		carName = (TextView) findViewById(R.id.carName);
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

		hun_che_zhanshi = (ListView) findViewById(R.id.hun_che_zhanshi);
		adapter = new MyAdapter();
		hun_che_zhanshi.setAdapter(adapter);

		findViewById(R.id.btn_back).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (System.currentTimeMillis() - HomeActivity.dianJiShiJian > 500) {

					HomeActivity.dianJiShiJian = System.currentTimeMillis();
					HunCheZhanShi.this.finish();
				}
			}
		});

		setMoreListener();

	}

	private ImageView iv_more;

	private void setMoreListener() {
		iv_more = (ImageView) findViewById(R.id.iv_more);
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

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (System.currentTimeMillis() - HomeActivity.dianJiShiJian > 500) {

				HomeActivity.dianJiShiJian = System.currentTimeMillis();
				finish();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
