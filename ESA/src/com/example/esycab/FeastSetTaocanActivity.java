package com.example.esycab;

import java.io.File;
import java.util.ArrayList;

import java.util.List;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.esycab.network.SmartFruitsRestClient;

import com.example.esycab.utils.ACache;
import com.example.esycab.utils.ActivityCollector;
import com.example.esycab.utils.Load;
import com.example.esycab.utils.ProConst;
import com.eyoucab.list.CommonAdapter;

import com.eyoucab.list.father;
import com.eyoucab.list.son;
import com.eyoucab.list.taocan;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;

import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import android.view.View;

import android.view.View.OnClickListener;

import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import android.widget.ListView;
import android.widget.LinearLayout;

import android.widget.AdapterView.OnItemClickListener;

/**
 * 喜宴套餐☑
 * 
 * @author Administrator
 * 
 */
public class FeastSetTaocanActivity extends Activity implements ProConst {
	private ListView lv1, lv2;
	Button iv;
	LinearLayout moreView;
	double TotalPrice = 0.0;
	String tagName = null;
	List<son> lists = new ArrayList<son>();
	List<son> l;
	List<son> newList = new ArrayList<son>();
	List<son> newList1 = new ArrayList<son>();
	List<father> fatlist = new ArrayList<father>();
	LinearLayout yuYue;
	CommonAdapter<father> cf;
	private ACache mCache;
	private TextView tv, tv_food_price;
	List<String> imageUrls = new ArrayList<String>();
	taocan t;
	TextView jiaGe;
	int isZiXuan;
	 String a;// 主副
	 String b;// 套餐ID
	 String c;
	 String name;
	private String title;
	private String SetMealID, starLevel = 3 + "", price;
	private ImageView star1, star2;
	private LinearLayout ll_starlevel, ll_price;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wedding_party);// 加载这个界面
		ActivityCollector.addActivity(this);
		ll_starlevel = (LinearLayout) findViewById(R.id.ll_starlevel);
		ll_price = (LinearLayout) findViewById(R.id.ll_price);
		tv_food_price = (TextView) findViewById(R.id.tv_food_price);
		ll_starlevel.setVisibility(View.VISIBLE);
		ll_price.setVisibility(View.VISIBLE);
		title = getIntent().getStringExtra("name");
		price = getIntent().getStringExtra("price");
		tv_food_price.setText(price);
		name = title;
		if (name.contains("自选")) {
			ll_starlevel.setVisibility(View.GONE);
		}
		star1 = (ImageView) findViewById(R.id.iv_star_level4);
		star2 = (ImageView) findViewById(R.id.iv_star_level5);
		SetMealID = getIntent().getStringExtra("SetMealID");
		getStarLevel(SetMealID);
		tv = (TextView) findViewById(R.id.tv_title);
		tv.setText(title);
		l = new ArrayList<son>();
		t = (taocan) getIntent().getSerializableExtra("an");
		SendClick(SetMealID);

		// System.out.println(selectIndex + "GH");
		// System.out.println(TotalPrice);

		yuYue = (LinearLayout) findViewById(R.id.xiyan_taocan_jiage);
		yuYue.setVisibility(View.GONE);
		// jiaGe = (TextView) yuYue.findViewById(R.id.unit_price);
		// jiaGe.setText((int) TotalPrice + "");
		mCache = ACache.get(this);

		iv = (Button) findViewById(R.id.btn_back);
		iv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(System.currentTimeMillis()-HomeActivity.dianJiShiJian>500){

					HomeActivity.dianJiShiJian = System.currentTimeMillis();
				File f = new File(
						"/data/data/com.example.esycab/cache/ACache/1297131408");
				f.delete();

				// 结束当前界面
				FeastSetTaocanActivity.this.finish();
				}
			}
		});

		// button1 = (Button) findViewById(R.id.jiarudingdan);// 加入订单按钮

		setMoreListener();
		}
		
	private	ImageView iv_more;
		private void setMoreListener() {
			iv_more = (ImageView) findViewById(R.id.iv_more);
			iv_more.setVisibility(View.GONE);
//			iv_more.setOnClickListener(new IvListener(iv_more, FeastSetTaocanActivity.this,0));
		}

	private void getStarLevel(String setMealID2) {
		RequestParams params = new RequestParams();
		params.add("SetMealID", setMealID2);
		SmartFruitsRestClient.get(
				"MenuHandler.ashx?Action=getSetMealStarLevelByID", params,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
						// TODO Auto-generated method stub
						String result = new String(arg2);
						JSONObject json;
						try {
							json = new JSONObject(result.trim());
							JSONArray configlist = json.getJSONArray("星级");
							starLevel = configlist.getJSONObject(0).getString(
									"StarLevel");
							Log.i("==================", configlist
									.getJSONObject(0).getString("StarLevel"));
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						if (starLevel.equals("4")) {
							star1.setVisibility(View.VISIBLE);
						} else if (starLevel.equals("5")) {
							star1.setVisibility(View.VISIBLE);
							star2.setVisibility(View.VISIBLE);
						}
						;

					}

					@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
							Throwable arg3) {
						// TODO Auto-generated method stub
						starLevel = 3 + "";
					}
				});

	}


	public void SendClick(final String ID) {
		RequestParams reqParams = new RequestParams();
		reqParams.put("SetMealID", ID);

		// 传输数据过去
		SmartFruitsRestClient.post("MenuHandler.ashx?Action=getMenuByID",
				reqParams, new AsyncHttpResponseHandler() {
					// http请求成功返回数据
					@Override
					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
						// TODO Auto-generated method stub
						son news;

						father newa;
						String result = new String(arg2);
						System.out.println("F" + result);
						try {
							JSONObject json = new JSONObject(result.trim());
							JSONArray configlist = json.getJSONArray("菜单");
							JSONArray cuisinelist = json.getJSONArray("菜系");

							if (configlist.length() > 0) {
								mCache.put("喜宴套餐详情", result, 60 * 60 * 24);
							}

							if (configlist != null) {
								int size = configlist.length();
								JSONObject tJson = null;
								lists.clear();
								for (int i = 0; i < size; i++) {
									news = new son();
									tJson = configlist.getJSONObject(i);
									String secondId = tJson
											.getString("DishCgy");
									news.setId(secondId);
									String lessonpId = tJson
											.getString("DishName");
									news.setName(lessonpId);
									news.setCover_url_small(tJson
											.getString("DishID"));
									String ImageUrl = tJson
											.getString("ImageUrl");
									String price = tJson.getString("UnitPrice");
									if ("".equals(price) || price == null) {
										price = "0.0000";
									}
									int a = price.lastIndexOf(".");
									price = price.substring(0, a);
									// double p = Double.parseDouble(price)*1.1;
									// String p2 = df.format(price)+"";
									// System.out.println(p2+"++++++++++++++");
									news.setUnitPrice(price);
									news.setImageUrl(ImageUrl);
									news.setJiage(tJson.getString("Norm"));
									news.setDanwei(tJson.getString("NormUnit"));
									if ("".equals(tJson.getString("ReplaceID"))) {
										news.setTi(false);
									} else {
										news.setTiID(tJson
												.getString("ReplaceID"));
										news.setTiName(tJson
												.getString("ReplaceName"));
										news.setTiTuPian(tJson
												.getString("ReplaceImageUrl"));
										news.setTi(true);
										news.setTiHuan(false);
									}
									;
									lists.add(news);

								}
							}
							if (cuisinelist != null) {
								int sizes = cuisinelist.length();
								JSONObject tJsona = null;
								fatlist.clear();
								for (int i = 0; i < sizes; i++) {
									newa = new father();
									tJsona = cuisinelist.getJSONObject(i);
									String seconId = tJsona
											.getString("pkDishCgyID");

									newa.setId(seconId);
									String lessonId = tJsona.getString("Name");
									newa.setName(lessonId);
									fatlist.add(newa);

								}

							}
							for (int i = 0; i < lists.size(); i++) {
								if (fatlist.get(0).getId()
										.equals(lists.get(i).getId())) {
									newList1.add(lists.get(i));
								}
							}

							mains();
						} catch (JSONException e) {
							e.printStackTrace();
							// Toast.makeText(FeastSetActivity.this,
							// e.getMessage(), Toast.LENGTH_SHORT).show();
						}
						return;
					}

					@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
							Throwable arg3) {
						// TODO Auto-generated method stub 失败的回调函数
						System.out.println(arg3 + "asdfgdhgsa");
						if (ciShu >= 3) {
							isFalse = false;
							Toast.makeText(FeastSetTaocanActivity.this,
									getString(R.string.conn_failed),
									Toast.LENGTH_SHORT).show();
						}
						;
						if (isFalse) {
							SendClick(ID);
							ciShu++;
						}

					}
				});
	}

	boolean isFalse = true;
	int ciShu = 0;
	CommonAdapter<son> cs;
	int index = 0;

	public void mains() {
		lv1 = (ListView) findViewById(R.id.lv1s);
		lv2 = (ListView) findViewById(R.id.lv2);
		System.out.println(lv2);
		cf = new CommonAdapter<father>(FeastSetTaocanActivity.this, fatlist,
				R.layout.item_zitis) {

			@Override
			public void convert(com.eyoucab.list.ViewHolder helper, father item) {
				// TODO Auto-generated method stub
				helper.setText(R.id.anliss, item.getName());
				helper.setTextColor(R.id.anliss, Color.BLACK,
						Color.parseColor("#adadad"), index);
				helper.setBack(index, R.id.anliss);
			}
		};
		lv1.setAdapter(cf);
		for (int i = 0; i < newList1.size(); i++) {
			System.out.println(newList1.get(i).isTi());
		}
		cas = new CommonAdapter<son>(FeastSetTaocanActivity.this, newList1,
				R.layout.item_caixixiangqing_shipei) {

			@Override
			public void convert(com.eyoucab.list.ViewHolder helper, son item) {
				// TODO Auto-generated method stub
				helper.setText(R.id.cai_mingzi, item.getName());
				helper.loadImage(R.id.cai_tupian, item.getImageUrl());
				// v.jiaGess = (TextView) arg1.findViewById(R.id.cai_jiage);
				// v.goux = (ImageView) arg1.findViewById(R.id.cai_gouxuan);
				// v.tih = (TextView) arg1.findViewById(R.id.xiyan_tihuan);
				if (name.contains("单餐")) {
//					helper.setText(R.id.cai_jiage, item.getDanwei());
					helper.setText(R.id.cai_jiage, "");
				} else if (!TextUtils.isEmpty(item.getJiage())) {
					if ("g".equals(item.getDanwei())) {
						helper.setText(R.id.cai_jiage,"(约" + item.getJiage()
								+ item.getDanwei() + ")");
					} else {
						helper.setText(R.id.cai_jiage,"(" + item.getJiage()
								+ item.getDanwei() + ")");
					}
					//
				} else {
					helper.setText(R.id.cai_jiage, "");
				}
			}
		};
//		m1 = new MyAdapter1();
		lv2.setAdapter(cas);
		lv2.setVisibility(View.VISIBLE);
		// listview的监听
		lv1.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// initview();//图片显示
				cf.notifyDataSetChanged();
				System.out.println(12);
				index = position;
				newList1.clear();
				for (int i = 0; i < lists.size(); i++) {
					if (fatlist.get(position).getId()
							.equals(lists.get(i).getId())) {
						newList1.add(lists.get(i));
						// System.out.println(lists.get(i).isiCho());
					}
				}
				cas.notifyDataSetChanged();
				lv2.setSelection(0);
			}
		});

	}

	CommonAdapter<son> cas;
	MyAdapter1 m1;

	class MyAdapter1 extends BaseAdapter {
		LayoutInflater li;
		View view;
		List<Integer> lsi1;

		MyAdapter1() {
			li = LayoutInflater.from(FeastSetTaocanActivity.this);
			lsi1 = new ArrayList<Integer>();
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return newList1.size();
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

		public void setGX(View v1, int arg0) {
			v = (ViewHolder) v1.getTag();
		}

		@Override
		public View getView(final int arg0, View arg1, ViewGroup arg2) {
			if (!lsi1.contains(arg0)) {
				System.out.println("+++++++");
				arg1 = li.inflate(R.layout.item_caixixiangqing_shipei, null);
				v = new ViewHolder();
				v.image = (ImageView) arg1.findViewById(R.id.cai_tupian);
				v.imgName = (TextView) arg1.findViewById(R.id.cai_mingzi);
				v.jiaGess = (TextView) arg1.findViewById(R.id.cai_jiage);
				//v.goux = (ImageView) arg1.findViewById(R.id.cai_gouxuan);
				v.tih = (TextView) arg1.findViewById(R.id.xiyan_tihuan);
				arg1.setTag(v);
				lsi1.add(arg0);
			} else {
				v = (ViewHolder) arg1.getTag();
				System.out.println(arg0);
			}
			v.imgName.setText(newList1.get(arg0).getName());
			Load.imageLoader.displayImage(newList1.get(arg0).getImageUrl(),
					v.image, Load.options);

			if (name.contains("单餐")) {
				v.jiaGess.setText(newList1.get(arg0).getJiage()
						+ newList1.get(arg0).getDanwei());
			} else if (!TextUtils.isEmpty(newList1.get(arg0).getJiage())) {
				if ("g".equals(newList1.get(arg0).getDanwei())) {
					v.jiaGess.setText("(约" + newList1.get(arg0).getJiage()
							+ newList1.get(arg0).getDanwei() + ")");
				} else {
					v.jiaGess.setText("(" + newList1.get(arg0).getJiage()
							+ newList1.get(arg0).getDanwei() + ")");
				}

			} else {
				v.jiaGess.setText("");
			}
			if (newList1.get(arg0).isTi()) {
				System.out.println(arg0 + "+++++++++++");
				v.tih.setVisibility(View.VISIBLE);
			} else {
				v.tih.setVisibility(View.GONE);
			}
			return arg1;
		}

	}

	private ViewHolder v = null;

	private class ViewHolder {
		ImageView image;
		TextView imgName, jiaGess, tih;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if(System.currentTimeMillis()-HomeActivity.dianJiShiJian>500){

				HomeActivity.dianJiShiJian = System.currentTimeMillis();
			File f = new File(
					"/data/data/com.example.esycab/cache/ACache/1297131408");
			f.delete();

			FeastSetTaocanActivity.this.finish();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
