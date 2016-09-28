package com.example.administrator.activity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.entity.AnLi;
import com.example.administrator.list.CommonAdapter;
import com.example.administrator.list.CookInfo;
import com.example.administrator.list.ViewHolder;
import com.example.administrator.myapplication.R;
import com.example.administrator.net.XUtilsHelper;
import com.example.administrator.utils.ACache;
import com.example.administrator.utils.ActivityCollector;
import com.example.administrator.utils.IvListener;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.view.annotation.ViewInject;

import rx.Observable;
import rx.Subscriber;

/**
 * 厨师选择
 * 
 */
public class CookActivity extends BaseActivity {
	private List<CookInfo> list3TuanDui, list4TuanDui, list5TuanDui;
	private List<CookInfo> list3geRen, list4geRen, list5geRen;
	@ViewInject(R.id.chushi_xingxi)
	private ListView lv1;
	private CommonAdapter<CookInfo> tuan, geR;// 适配器
	@ViewInject(R.id.btn_back)
	private Button iv;
	@ViewInject(R.id.chushi_chushijiage)
	private Button CSJiaGe;
	private int index = 0;
	private String xingJi[] = { "三星级", "四星级", "五星级" };
	private String leiBie[] = { "团队", "个人" };
	@ViewInject(R.id.chushi_xingjis)
	private Spinner xingJiS;
	@ViewInject(R.id.chushi_leibies)
	private Spinner leiBieS;
	private ACache mCache;
	private String XJ, LB;
	@ViewInject(R.id.tv_title)
	private TextView tv;
	@Override
	protected int setContentView() {
		return R.layout.chushi;
	}
	@Override
	protected void initView() {


		ActivityCollector.addActivity(this);
		mCache = ACache.get(this);

		tv.setText("厨师");

		lv1.setSelector(new ColorDrawable(Color.parseColor("#c5c4c3")));



		CSJiaGe.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startActivity(new Intent(CookActivity.this,
						ChuShiJiaGeBiaoActivity.class));
			}
		});
//		xingJiS.setAdapter(new ArrayAdapter<String>(this,
//				android.R.layout.simple_dropdown_item_1line, xingJi));
//		leiBieS.setAdapter(new ArrayAdapter<String>(this,
//				android.R.layout.simple_dropdown_item_1line, leiBie));
		ArrayAdapter<String> mDistrictAdapter = new ArrayAdapter<String>(
				this, R.layout.myspinner1, xingJi);
		mDistrictAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		xingJiS.setAdapter(mDistrictAdapter);
		ArrayAdapter<String> mDistrictAdapter1 = new ArrayAdapter<String>(
				this, R.layout.myspinner1, leiBie);
		mDistrictAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		leiBieS.setAdapter(mDistrictAdapter1);
		XJ = "三星级";
		LB = "团队";

		iv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (System.currentTimeMillis() - HomeActivity.dianJiShiJian > 500) {

					HomeActivity.dianJiShiJian = System.currentTimeMillis();
					ActivityCollector.finishAll();
					// 结束当前界面
					CookActivity.this.finish();
				}
			}
		});
		if (null != mCache.getAsString("厨师")) {
			String result = mCache.getAsString("厨师");
			tryc(result);
		} else {
			System.out.println(147852);
			getMsg();
		}
		setMoreListener();
	}

	protected void toast(Context context , String info){
		Toast.makeText(context,info,Toast.LENGTH_SHORT).show();
	}

	@ViewInject(R.id.iv_more)
	private ImageView iv_more;

	private void setMoreListener() {

		iv_more.setOnClickListener(new IvListener(iv_more, CookActivity.this, 0));
	}

	private void getMsg() {



		XUtilsHelper xUtilsHelper1 = new XUtilsHelper(this,"CooksHandler.ashx?Action=GroupAndSingleInfo",1);
		RequestParams requestParams = new RequestParams();

		Observable.create(new Observable.OnSubscribe<String>() {
			@Override
			public void call(Subscriber<? super String> subscriber) {
				xUtilsHelper1.sendPost(requestParams,subscriber);
			}
		}).subscribe(new Subscriber<String>() {
			@Override
			public void onCompleted() {
			}

			@Override
			public void onError(Throwable e) {
				if (ciShu >= 3) {
							isFalse = false;
					toast(CookActivity.this,
							getString(R.string.conn_failed));

						}

						if (isFalse) {
							getMsg();
							ciShu++;
						}
			}

			@Override
			public void onNext(String s) {
				tryc(s);

			}
		});













//		RequestParams reqParams = new RequestParams();
//		SmartFruitsRestClient.post(
//				"CooksHandler.ashx?Action=GroupAndSingleInfo", reqParams,
//				new AsyncHttpResponseHandler() {
//
//					@Override
//					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
//							Throwable arg3) {
//						// TODO Auto-generated method stub
//						System.out.println(arg3 + "asdfgdhgsa");
//						if (ciShu >= 3) {
//							isFalse = false;
//							Toast.makeText(CookActivity.this,
//									getString(R.string.conn_failed),
//									Toast.LENGTH_SHORT).show();
//						}
//						;
//						if (isFalse) {
//							getMsg();
//							ciShu++;
//						}
//					}
//
//					@Override
//					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
//						// TODO Auto-generated method stub
//						String result = new String(arg2);
//						System.out.println("!#*@()DJ#()J" + result);
//
//						tryc(result);
//						return;
//
//					}
//				});
	}

	private int tuandui1[];
	private int tuandui2[];
	private int tuandui3[];
	private int geren1[];
	private int geren2[];
	private int geren3[];
	private List<CookInfo> lci3;
	private List<CookInfo> lci4;

	private void pai3(int a[]) {
		lci3 = new ArrayList<CookInfo>();
		for (int i = a.length - 1; i >= 0; i--) {
			for (int j = 0; j < list3TuanDui.size(); j++) {

				if (Integer.parseInt(list3TuanDui.get(j).getScrol()) == a[i]) {
					System.out.println(list3TuanDui.get(j));
					lci3.add(list3TuanDui.get(j));
					list3TuanDui.remove(j);
				}
			}
		}
	}

	private void pai4(int a[]) {
		lci4 = new ArrayList<CookInfo>();
		for (int i = a.length - 1; i >= 0; i--) {
			for (int j = 0; j < list4TuanDui.size(); j++) {

				if (Integer.parseInt(list4TuanDui.get(j).getScrol()) == a[i]) {
					System.out.println(list4TuanDui.get(j));
					lci4.add(list4TuanDui.get(j));
					list4TuanDui.remove(j);
				}
			}
		}
	}

	private List<CookInfo> lci5;
	private List<CookInfo> lci6;

	private void pai5(int a[]) {
		lci5 = new ArrayList<CookInfo>();
		for (int i = a.length - 1; i >= 0; i--) {
			for (int j = 0; j < list5TuanDui.size(); j++) {
				if (null == list5TuanDui.get(j).getScrol()) {
					list5TuanDui.get(j).setScrol("0");
				}
				if (Integer.parseInt(list5TuanDui.get(j).getScrol()) == a[i]) {
					System.out.println(list5TuanDui.get(j));
					lci5.add(list5TuanDui.get(j));
					list5TuanDui.remove(j);
				}
			}
		}
	}

	private void pai6(int a[]) {
		lci6 = new ArrayList<CookInfo>();
		for (int i = a.length - 1; i >= 0; i--) {
			for (int j = 0; j < list3geRen.size(); j++) {
				if (null == list3geRen.get(j).getScrol()) {
					list3geRen.get(j).setScrol("0");
				}
				if (Integer.parseInt(list3geRen.get(j).getScrol()) == a[i]) {
					System.out.println(list3geRen.get(j));
					lci6.add(list3geRen.get(j));
					list3geRen.remove(j);
				}
			}
		}
	}

	private List<CookInfo> lci7;
	private List<CookInfo> lci8;

	private void pai7(int a[]) {
		lci7 = new ArrayList<CookInfo>();
		for (int i = a.length - 1; i >= 0; i--) {
			for (int j = 0; j < list4geRen.size(); j++) {
				if (null == list4geRen.get(j).getScrol()) {
					list4geRen.get(j).setScrol("0");
				}
				if (Integer.parseInt(list4geRen.get(j).getScrol()) == a[i]) {
					System.out.println(list4geRen.get(j));
					lci7.add(list4geRen.get(j));
					list4geRen.remove(j);
				}
			}
		}
	}

	private void pai8(int a[]) {
		lci8 = new ArrayList<CookInfo>();
		for (int i = a.length - 1; i >= 0; i--) {
			for (int j = 0; j < list5geRen.size(); j++) {
				if (null == list5geRen.get(j).getScrol()) {
					list5geRen.get(j).setScrol("0");
				}
				if (Integer.parseInt(list5geRen.get(j).getScrol()) == a[i]) {
					System.out.println(list5geRen.get(j));
					lci8.add(list5geRen.get(j));
					list5geRen.remove(j);
				}
			}
		}
	}

	private void tryc(String result) {
		if (list3geRen == null) {
			list3geRen = new ArrayList<CookInfo>();
		} else {
			list3geRen.clear();
		}
		if (list4geRen == null) {
			list4geRen = new ArrayList<CookInfo>();
		} else {
			list4geRen.clear();
		}
		if (list5geRen == null) {
			list5geRen = new ArrayList<CookInfo>();
		} else {
			list5geRen.clear();
		}
		if (null == list3TuanDui) {
			list3TuanDui = new ArrayList<CookInfo>();
		} else {
			list3TuanDui.clear();
		}
		if (null == list4TuanDui) {
			list4TuanDui = new ArrayList<CookInfo>();
		} else {
			list4TuanDui.clear();
		}
		if (null == list5TuanDui) {
			list5TuanDui = new ArrayList<CookInfo>();
		} else {
			list5TuanDui.clear();
		}
		try {
			JSONObject json = new JSONObject(result.trim());
			JSONObject cuilist = json.getJSONObject("团队");
			JSONObject culist = json.getJSONObject("个人");
			System.out.println(cuilist.length() + "F");
			if (culist.length() > 0) {
				mCache.put("厨师", result, 60 * 10);
			}
			JSONObject tJson = null;
			JSONArray sanXinJiT = (JSONArray) cuilist.get("三星级");
			JSONArray siXinJiT = (JSONArray) cuilist.get("四星级");
			JSONArray wuXinJiT = (JSONArray) cuilist.get("五星级");
			tuandui1 = new int[sanXinJiT.length()];
			tuandui2 = new int[siXinJiT.length()];
			tuandui3 = new int[wuXinJiT.length()];
			for (int i = 0; i < sanXinJiT.length(); i++) {
				tJson = sanXinJiT.getJSONObject(i);
				CookInfo ckInfo = new CookInfo();
				if (!TextUtils.isEmpty(tJson.getString("SetupDtGrp"))) {
					ckInfo.setBornDateTime(tJson.getString("SetupDtGrp"));// 创建日期
				} else {
					ckInfo.setBornDateTime("");
				}
				if (!TextUtils.isEmpty(tJson.getString("StarLevelGrp"))) {
					ckInfo.setFkStarLevelID(tJson.getInt("StarLevelGrp"));// 星级
				} else {
					ckInfo.setFkStarLevelID(0);
				}
				if (!TextUtils.isEmpty(tJson.getString("CookGrpID"))) {
					ckInfo.setPkCookID(tJson.getInt("CookGrpID"));// 团队ID
				} else {
					ckInfo.setPkCookID(0);// 团队ID
				}
				if (!TextUtils.isEmpty(tJson.getString("NameGrp"))) {
					ckInfo.setRealName(tJson.getString("NameGrp"));// 名字
				} else {
					ckInfo.setRealName("");// 名字
				}
				if (!TextUtils.isEmpty(tJson.getString("TelGrp"))) {
					ckInfo.setTel(tJson.getString("TelGrp"));// 电话
				} else {
					ckInfo.setTel("");
				}
				if (!TextUtils.isEmpty(tJson.getString("NumCookGrp"))) {
					ckInfo.setChuShiNum(tJson.getString("NumCookGrp"));// 厨师人数
				} else {
					ckInfo.setChuShiNum("");
				}
				if (!TextUtils.isEmpty(tJson.getString("NumServiceGrp"))) {
					ckInfo.setFuWuNum(tJson.getString("NumServiceGrp"));// 服务人数
				} else {
					ckInfo.setFuWuNum("");// 服务人数
				}
				if (!TextUtils.isEmpty(tJson.getString("ServiceNumGrp"))) {
					ckInfo.setServiceNumGrp(tJson.getString("ServiceNumGrp"));// 服务数目
				} else {
					ckInfo.setServiceNumGrp("0");// 服务数目
				}
				if (TextUtils.isEmpty(tJson.getString("StarNumGrp"))
						|| "0".equals(tJson.getString("StarNumGrp"))) {

					ckInfo.setScrol("1");// 评分ID\
				} else {

					ckInfo.setScrol(tJson.getString("StarNumGrp"));// 评分ID\
				}
				if (!TextUtils.isEmpty(tJson.getString("ImageUrlGrp"))) {
					ckInfo.setImageUrl(tJson.getString("ImageUrlGrp"));
				} else {
					ckInfo.setImageUrl("");
				}
				if (!TextUtils.isEmpty(tJson.getString("GoodAtGrp"))) {
					ckInfo.setGoodAt(tJson.getString("GoodAtGrp"));// 服务范围
				} else {
					ckInfo.setGoodAt("1");// 服务范围
				}
				if (!TextUtils.isEmpty(tJson.getString("sYsxRecommendGrp"))) {
					ckInfo.setIsYsxRecommend(tJson
							.getString("sYsxRecommendGrp"));// 是否为永尚鲜推荐
				} else {
					ckInfo.setIsYsxRecommend("0");// 是否为永尚鲜推荐
				}
				if (!TextUtils.isEmpty(tJson.getString("MaximumGrp"))) {
					ckInfo.setMaximum(tJson.getString("MaximumGrp"));// 最大接单能力
				} else {
					ckInfo.setMaximum("");// 最大接单能力
				}
				if (!TextUtils.isEmpty(tJson.getString("CookNameGrp"))) {
					ckInfo.setCookName(tJson.getString("CookNameGrp"));// 厨师名字
				} else {
					ckInfo.setCookName("");// 厨师名字
				}
				if (!TextUtils.isEmpty(tJson.getString("WorkingTimeGrp"))) {
					ckInfo.setWorkingTime(tJson.getString("WorkingTimeGrp"));
				} else {
					ckInfo.setWorkingTime("0");
				}
				if (!TextUtils.isEmpty(tJson.getString("ServiceAreaGrp"))) {
					ckInfo.setServiceArea(tJson.getString("ServiceAreaGrp"));
				} else {
					ckInfo.setServiceArea("1");
				}

				tuandui1[i] = Integer.parseInt(ckInfo.getScrol());
				list3TuanDui.add(ckInfo);

			}
			Arrays.sort(tuandui1);
			pai3(tuandui1);
			for (int i = 0; i < siXinJiT.length(); i++) {
				tJson = siXinJiT.getJSONObject(i);
				CookInfo ckInfo = new CookInfo();
				if (!TextUtils.isEmpty(tJson.getString("SetupDtGrp"))) {
					ckInfo.setBornDateTime(tJson.getString("SetupDtGrp"));// 创建日期
				} else {
					ckInfo.setBornDateTime("");
				}
				if (!TextUtils.isEmpty(tJson.getString("StarLevelGrp"))) {
					ckInfo.setFkStarLevelID(tJson.getInt("StarLevelGrp"));// 星级
				} else {
					ckInfo.setFkStarLevelID(0);
				}
				if (!TextUtils.isEmpty(tJson.getString("CookGrpID"))) {
					ckInfo.setPkCookID(tJson.getInt("CookGrpID"));// 团队ID
				} else {
					ckInfo.setPkCookID(0);// 团队ID
				}
				if (!TextUtils.isEmpty(tJson.getString("NameGrp"))) {
					ckInfo.setRealName(tJson.getString("NameGrp"));// 名字
				} else {
					ckInfo.setRealName("");// 名字
				}
				if (!TextUtils.isEmpty(tJson.getString("TelGrp"))) {
					ckInfo.setTel(tJson.getString("TelGrp"));// 电话
				} else {
					ckInfo.setTel("");
				}
				if (!TextUtils.isEmpty(tJson.getString("NumCookGrp"))) {
					ckInfo.setChuShiNum(tJson.getString("NumCookGrp"));// 厨师人数
				} else {
					ckInfo.setChuShiNum("");
				}
				if (!TextUtils.isEmpty(tJson.getString("NumServiceGrp"))) {
					ckInfo.setFuWuNum(tJson.getString("NumServiceGrp"));// 服务人数
				} else {
					ckInfo.setFuWuNum("");// 服务人数
				}
				if (!TextUtils.isEmpty(tJson.getString("ServiceNumGrp"))) {
					ckInfo.setServiceNumGrp(tJson.getString("ServiceNumGrp"));// 服务数目
				} else {
					ckInfo.setServiceNumGrp("0");// 服务数目
				}
				if (TextUtils.isEmpty(tJson.getString("StarNumGrp"))
						|| "0".equals(tJson.getString("StarNumGrp"))) {
					ckInfo.setScrol("1");// 评分ID\
				} else {

					ckInfo.setScrol(tJson.getString("StarNumGrp"));// 评分ID\
				}
				if (!TextUtils.isEmpty(tJson.getString("ImageUrlGrp"))) {
					ckInfo.setImageUrl(tJson.getString("ImageUrlGrp"));
				} else {
					ckInfo.setImageUrl("");
				}

				if (!TextUtils.isEmpty(tJson.getString("GoodAtGrp"))) {
					ckInfo.setGoodAt(tJson.getString("GoodAtGrp"));// 服务范围
				} else {
					ckInfo.setGoodAt("1");// 服务范围
				}
				if (!TextUtils.isEmpty(tJson.getString("sYsxRecommendGrp"))) {
					ckInfo.setIsYsxRecommend(tJson
							.getString("sYsxRecommendGrp"));// 是否为永尚鲜推荐
				} else {
					ckInfo.setIsYsxRecommend("0");// 是否为永尚鲜推荐
				}
				if (!TextUtils.isEmpty(tJson.getString("MaximumGrp"))) {
					ckInfo.setMaximum(tJson.getString("MaximumGrp"));// 最大接单能力
				} else {
					ckInfo.setMaximum("");// 最大接单能力
				}
				if (!TextUtils.isEmpty(tJson.getString("CookNameGrp"))) {
					ckInfo.setCookName(tJson.getString("CookNameGrp"));// 厨师名字
				} else {
					ckInfo.setCookName("");// 厨师名字
				}
				if (!TextUtils.isEmpty(tJson.getString("WorkingTimeGrp"))) {
					ckInfo.setWorkingTime(tJson.getString("WorkingTimeGrp"));
				} else {
					ckInfo.setWorkingTime("0");
				}
				if (!TextUtils.isEmpty(tJson.getString("ServiceAreaGrp"))) {
					ckInfo.setServiceArea(tJson.getString("ServiceAreaGrp"));
				} else {
					ckInfo.setServiceArea("1");
				}

				// System.out.println(ckInfo.getScore());
				// System.out.println(Integer.parseInt(ckInfo.getScrol())+"$$$$$$");
				// System.out.println(tuandui2.toString());
				tuandui2[i] = Integer.parseInt(ckInfo.getScrol());
				list4TuanDui.add(ckInfo);
			}
			Arrays.sort(tuandui2);
			pai4(tuandui2);
			for (int i = 0; i < wuXinJiT.length(); i++) {
				tJson = wuXinJiT.getJSONObject(i);
				CookInfo ckInfo = new CookInfo();
				if (!TextUtils.isEmpty(tJson.getString("SetupDtGrp"))) {
					ckInfo.setBornDateTime(tJson.getString("SetupDtGrp"));// 创建日期
				} else {
					ckInfo.setBornDateTime("");
				}
				if (!TextUtils.isEmpty(tJson.getString("StarLevelGrp"))) {
					ckInfo.setFkStarLevelID(tJson.getInt("StarLevelGrp"));// 星级
				} else {
					ckInfo.setFkStarLevelID(0);
				}
				if (!TextUtils.isEmpty(tJson.getString("CookGrpID"))) {
					ckInfo.setPkCookID(tJson.getInt("CookGrpID"));// 团队ID
				} else {
					ckInfo.setPkCookID(0);// 团队ID
				}
				if (!TextUtils.isEmpty(tJson.getString("NameGrp"))) {
					ckInfo.setRealName(tJson.getString("NameGrp"));// 名字
				} else {
					ckInfo.setRealName("");// 名字
				}
				if (!TextUtils.isEmpty(tJson.getString("TelGrp"))) {
					ckInfo.setTel(tJson.getString("TelGrp"));// 电话
				} else {
					ckInfo.setTel("");
				}
				if (!TextUtils.isEmpty(tJson.getString("NumCookGrp"))) {
					ckInfo.setChuShiNum(tJson.getString("NumCookGrp"));// 厨师人数
				} else {
					ckInfo.setChuShiNum("");
				}
				if (!TextUtils.isEmpty(tJson.getString("NumServiceGrp"))) {
					ckInfo.setFuWuNum(tJson.getString("NumServiceGrp"));// 服务人数
				} else {
					ckInfo.setFuWuNum("");// 服务人数
				}
				if (!TextUtils.isEmpty(tJson.getString("ServiceNumGrp"))) {
					ckInfo.setServiceNumGrp(tJson.getString("ServiceNumGrp"));// 服务数目
				} else {
					ckInfo.setServiceNumGrp("0");// 服务数目
				}
				if (TextUtils.isEmpty(tJson.getString("StarNumGrp"))
						|| "0".equals(tJson.getString("StarNumGrp"))) {

					ckInfo.setScrol("1");// 评分ID\
				} else {
					ckInfo.setScrol(tJson.getString("StarNumGrp"));// 评分ID\

				}
				if (!TextUtils.isEmpty(tJson.getString("ImageUrlGrp"))) {
					ckInfo.setImageUrl(tJson.getString("ImageUrlGrp"));
				} else {
					ckInfo.setImageUrl("");
				}

				if (!TextUtils.isEmpty(tJson.getString("GoodAtGrp"))) {
					ckInfo.setGoodAt(tJson.getString("GoodAtGrp"));// 服务范围
				} else {
					ckInfo.setGoodAt("1");// 服务范围
				}
				if (!TextUtils.isEmpty(tJson.getString("sYsxRecommendGrp"))) {
					ckInfo.setIsYsxRecommend(tJson
							.getString("sYsxRecommendGrp"));// 是否为永尚鲜推荐
				} else {
					ckInfo.setIsYsxRecommend("0");// 是否为永尚鲜推荐
				}
				if (!TextUtils.isEmpty(tJson.getString("MaximumGrp"))) {
					ckInfo.setMaximum(tJson.getString("MaximumGrp"));// 最大接单能力
				} else {
					ckInfo.setMaximum("");// 最大接单能力
				}
				if (!TextUtils.isEmpty(tJson.getString("CookNameGrp"))) {
					ckInfo.setCookName(tJson.getString("CookNameGrp"));// 厨师名字
				} else {
					ckInfo.setCookName("");// 厨师名字
				}
				if (!TextUtils.isEmpty(tJson.getString("WorkingTimeGrp"))) {
					ckInfo.setWorkingTime(tJson.getString("WorkingTimeGrp"));
				} else {
					ckInfo.setWorkingTime("0");
				}
				if (!TextUtils.isEmpty(tJson.getString("ServiceAreaGrp"))) {
					ckInfo.setServiceArea(tJson.getString("ServiceAreaGrp"));
				} else {
					ckInfo.setServiceArea("1");
				}

				tuandui3[i] = Integer.parseInt(ckInfo.getScrol());
				list5TuanDui.add(ckInfo);

			}
			Arrays.sort(tuandui3);
			pai5(tuandui3);
			JSONArray sanXinJi = (JSONArray) culist.get("三星级");
			JSONArray siXinJi = (JSONArray) culist.get("四星级");
			JSONArray wuXinJi = (JSONArray) culist.get("五星级");
			System.out.println(sanXinJi.length() + "FH");
			geren1 = new int[sanXinJi.length()];
			geren2 = new int[siXinJi.length()];
			geren3 = new int[wuXinJi.length()];
			for (int i = 0; i < sanXinJi.length(); i++) {
				tJson = sanXinJi.getJSONObject(i);
				CookInfo ckInfo = new CookInfo();
				if (!TextUtils.isEmpty(tJson.getString("BornDateTime"))) {
					ckInfo.setBornDateTime(tJson.getString("BornDateTime"));// 创建日期
				} else {
					ckInfo.setBornDateTime("");
				}
				if (!TextUtils.isEmpty(tJson.getString("pkCookID"))) {
					ckInfo.setPkCookID(tJson.getInt("pkCookID"));// 厨师ID
				} else {
					ckInfo.setPkCookID(0);
				}
				// ckInfo.setNickName(tJson.getString("NickName"));// 假名
				if (!TextUtils.isEmpty(tJson.getString("RealName"))) {
					ckInfo.setRealName(tJson.getString("RealName"));// 真实名字
				} else {
					ckInfo.setRealName("");
				}
				if (!TextUtils.isEmpty(tJson.getString("ImageUrl"))) {
					ckInfo.setImageUrl(tJson.getString("ImageUrl"));
				} else {
					ckInfo.setImageUrl("");
				}
				if (!TextUtils.isEmpty(tJson.getString("StarLevel"))) {
					ckInfo.setStarLevel(tJson.getString("StarLevel"));
				} else {
					ckInfo.setStarLevel("3");
				}
				if (!TextUtils.isEmpty(tJson.getString("Sex"))) {
					ckInfo.setSex(tJson.getString("Sex"));
				} else {
					ckInfo.setSex("");
				}
				if (!TextUtils.isEmpty(tJson.getString("WorkingTime"))) {
					ckInfo.setWorkingTime(tJson.getString("WorkingTime"));
				} else {
					ckInfo.setWorkingTime("");
				}
				if (!TextUtils.isEmpty(tJson.getString("ServiceNum"))) {
					ckInfo.setFuWuNum(tJson.getString("ServiceNum"));
				} else {
					ckInfo.setFuWuNum("0");
				}
				if (TextUtils.isEmpty(tJson.getString("StarNum"))
						|| "0".equals(tJson.getString("StarNum"))) {
					System.out.println(tJson.getString("StarNum") + "1234566");
					System.out.println(!TextUtils.isEmpty(tJson
							.getString("StarNum"))
							+ ","
							+ !"0".equals(tJson.getString("StarNum")));
					ckInfo.setScrol("1");
				} else {
					ckInfo.setScrol(tJson.getString("StarNum"));// 评分ID\
				}

				if (!TextUtils.isEmpty(tJson.getString("GoodAt"))) {
					ckInfo.setGoodAt(tJson.getString("GoodAt"));// 服务范围
				} else {
					ckInfo.setGoodAt("1");
				}
				if (!TextUtils.isEmpty(tJson.getString("IsYsxRecommend"))) {
					ckInfo.setIsYsxRecommend(tJson.getString("IsYsxRecommend"));// 是否为永尚鲜推荐
				} else {
					ckInfo.setIsYsxRecommend("0");
				}
				if (!TextUtils.isEmpty(tJson.getString("Maximum"))) {
					ckInfo.setMaximum(tJson.getString("Maximum"));// 最大接单能力
				} else {
					ckInfo.setMaximum("");
				}
				if (!TextUtils.isEmpty(tJson.getString("ServiceArea"))) {
					ckInfo.setServiceArea(tJson.getString("ServiceArea"));// 服务范围
				} else {
					ckInfo.setServiceArea("1");
				}

				geren1[i] = Integer.parseInt(ckInfo.getScrol());
				list3geRen.add(ckInfo);
			}
			Arrays.sort(geren1);
			pai6(geren1);
			for (int i = 0; i < siXinJi.length(); i++) {
				tJson = siXinJi.getJSONObject(i);
				CookInfo ckInfo = new CookInfo();
				if (!TextUtils.isEmpty(tJson.getString("BornDateTime"))) {
					ckInfo.setBornDateTime(tJson.getString("BornDateTime"));// 创建日期
				} else {
					ckInfo.setBornDateTime("");
				}
				if (!TextUtils.isEmpty(tJson.getString("pkCookID"))) {
					ckInfo.setPkCookID(tJson.getInt("pkCookID"));// 厨师ID
				} else {
					ckInfo.setPkCookID(0);
				}
				// ckInfo.setNickName(tJson.getString("NickName"));// 假名
				if (!TextUtils.isEmpty(tJson.getString("RealName"))) {
					ckInfo.setRealName(tJson.getString("RealName"));// 真实名字
				} else {
					ckInfo.setRealName("");
				}
				if (!TextUtils.isEmpty(tJson.getString("ImageUrl"))) {
					ckInfo.setImageUrl(tJson.getString("ImageUrl"));
				} else {
					ckInfo.setImageUrl("");
				}
				if (!TextUtils.isEmpty(tJson.getString("StarLevel"))) {
					ckInfo.setStarLevel(tJson.getString("StarLevel"));
				} else {
					ckInfo.setStarLevel("3");
				}
				if (!TextUtils.isEmpty(tJson.getString("Sex"))) {
					ckInfo.setSex(tJson.getString("Sex"));
				} else {
					ckInfo.setSex("");
				}
				if (!TextUtils.isEmpty(tJson.getString("WorkingTime"))) {
					ckInfo.setWorkingTime(tJson.getString("WorkingTime"));
				} else {
					ckInfo.setWorkingTime("");
				}
				if (!TextUtils.isEmpty(tJson.getString("ServiceNum"))) {
					ckInfo.setFuWuNum(tJson.getString("ServiceNum"));
				} else {
					ckInfo.setFuWuNum("0");
				}
				if (TextUtils.isEmpty(tJson.getString("StarNum"))
						|| "0".equals(tJson.getString("StarNum"))) {
					ckInfo.setScrol("1");
				} else {

					ckInfo.setScrol(tJson.getString("StarNum"));// 评分ID\
				}

				if (!TextUtils.isEmpty(tJson.getString("GoodAt"))) {
					ckInfo.setGoodAt(tJson.getString("GoodAt"));// 服务范围
				} else {
					ckInfo.setGoodAt("1");
				}
				if (!TextUtils.isEmpty(tJson.getString("IsYsxRecommend"))) {
					ckInfo.setIsYsxRecommend(tJson.getString("IsYsxRecommend"));// 是否为永尚鲜推荐
				} else {
					ckInfo.setIsYsxRecommend("0");
				}
				if (!TextUtils.isEmpty(tJson.getString("Maximum"))) {
					ckInfo.setMaximum(tJson.getString("Maximum"));// 最大接单能力
				} else {
					ckInfo.setMaximum("");
				}
				if (!TextUtils.isEmpty(tJson.getString("ServiceArea"))) {
					ckInfo.setServiceArea(tJson.getString("ServiceArea"));// 服务范围
				} else {
					ckInfo.setServiceArea("1");
				}

				geren2[i] = Integer.parseInt(ckInfo.getScrol());
				list4geRen.add(ckInfo);

			}
			Arrays.sort(geren2);
			pai7(geren2);
			for (int i = 0; i < wuXinJi.length(); i++) {
				tJson = wuXinJi.getJSONObject(i);
				CookInfo ckInfo = new CookInfo();
				if (!TextUtils.isEmpty(tJson.getString("BornDateTime"))) {
					ckInfo.setBornDateTime(tJson.getString("BornDateTime"));// 创建日期
				} else {
					ckInfo.setBornDateTime("");
				}
				if (!TextUtils.isEmpty(tJson.getString("pkCookID"))) {
					ckInfo.setPkCookID(tJson.getInt("pkCookID"));// 厨师ID
				} else {
					ckInfo.setPkCookID(0);
				}
				// ckInfo.setNickName(tJson.getString("NickName"));// 假名
				if (!TextUtils.isEmpty(tJson.getString("RealName"))) {
					ckInfo.setRealName(tJson.getString("RealName"));// 真实名字
				} else {
					ckInfo.setRealName("");
				}
				if (!TextUtils.isEmpty(tJson.getString("ImageUrl"))) {
					ckInfo.setImageUrl(tJson.getString("ImageUrl"));
				} else {
					ckInfo.setImageUrl("");
				}
				if (!TextUtils.isEmpty(tJson.getString("StarLevel"))) {
					ckInfo.setStarLevel(tJson.getString("StarLevel"));
				} else {
					ckInfo.setStarLevel("3");
				}
				if (!TextUtils.isEmpty(tJson.getString("Sex"))) {
					ckInfo.setSex(tJson.getString("Sex"));
				} else {
					ckInfo.setSex("");
				}
				if (!TextUtils.isEmpty(tJson.getString("WorkingTime"))) {
					ckInfo.setWorkingTime(tJson.getString("WorkingTime"));
				} else {
					ckInfo.setWorkingTime("");
				}
				if (!TextUtils.isEmpty(tJson.getString("ServiceNum"))) {
					ckInfo.setFuWuNum(tJson.getString("ServiceNum"));
				} else {
					ckInfo.setFuWuNum("0");
				}
				if (TextUtils.isEmpty(tJson.getString("StarNum"))
						|| "0".equals(tJson.getString("StarNum"))) {
					ckInfo.setScrol("1");
				} else {

					ckInfo.setScrol(tJson.getString("StarNum"));// 评分ID\
				}

				if (!TextUtils.isEmpty(tJson.getString("GoodAt"))) {
					ckInfo.setGoodAt(tJson.getString("GoodAt"));// 服务范围
				} else {
					ckInfo.setGoodAt("1");
				}
				if (!TextUtils.isEmpty(tJson.getString("IsYsxRecommend"))) {
					ckInfo.setIsYsxRecommend(tJson.getString("IsYsxRecommend"));// 是否为永尚鲜推荐
				} else {
					ckInfo.setIsYsxRecommend("0");
				}
				if (!TextUtils.isEmpty(tJson.getString("Maximum"))) {
					ckInfo.setMaximum(tJson.getString("Maximum"));// 最大接单能力
				} else {
					ckInfo.setMaximum("");
				}
				if (!TextUtils.isEmpty(tJson.getString("ServiceArea"))) {
					ckInfo.setServiceArea(tJson.getString("ServiceArea"));// 服务范围
				} else {
					ckInfo.setServiceArea("1");
				}

				geren3[i] = Integer.parseInt(ckInfo.getScrol());
				list5geRen.add(ckInfo);

			}
			Arrays.sort(geren3);
			pai8(geren3);
			tuan = new CommonAdapter<CookInfo>(CookActivity.this, lci3,
					R.layout.item_hunqinggongsi) {

				@Override
				public void convert(ViewHolder helper, CookInfo item) {
					// TODO Auto-generated method stub
					helper.setText(R.id.hunqing_gongsi, item.getRealName());
					helper.setVis(R.id.beijing);
					helper.setTextColor(R.id.hunqing_gongsi, Color.BLACK,
							Color.parseColor("#adadad"), index);
					helper.loadImage(R.id.shipei_tupian, item.getImageUrl());
				}

				@Override
				public void convert(ViewHolder helper, AnLi item) {

				}

			};
			lv1.setAdapter(tuan);
			lv1.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					index = arg2;
					Intent in = new Intent(CookActivity.this,
							ChuShi_TuanDuiXQActivity.class);
					Bundle bundle = new Bundle();
					bundle.putSerializable("user", lci3.get(arg2));
					in.putExtras(bundle);
					startActivity(in);

				}
			});
			xingJiS.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					XJ = xingJi[arg2];
					index = 0;
					ifs();
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub

				}
			});
			leiBieS.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					LB = leiBie[arg2];
					index = 0;
					ifs();
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub

				}

			});
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
	}

	private boolean isFalse = true;
	private int ciShu = 0;

	private void ifs() {
		System.out.println(XJ + "+++" + LB);
		if (XJ.equals("三星级") && LB.equals("团队")) {
			System.out.println("san tuandui" + lci3.size());
			tuan = new CommonAdapter<CookInfo>(CookActivity.this, lci3,
					R.layout.item_hunqinggongsi) {

				@Override
				public void convert(ViewHolder helper, CookInfo item) {
					// TODO Auto-generated method stub
					helper.setText(R.id.hunqing_gongsi, item.getRealName());
					helper.setVis(R.id.beijing);
					helper.setTextColor(R.id.hunqing_gongsi, Color.BLACK,
							Color.parseColor("#adadad"), index);
					helper.loadImage(R.id.shipei_tupian, item.getImageUrl());
				}

				@Override
				public void convert(ViewHolder helper, AnLi item) {

				}

			};
			System.out.println(lv1 + "L" + tuan);
			lv1.setAdapter(tuan);
			lv1.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					index = arg2;
					tuan.notifyDataSetChanged();
					Intent in = new Intent(CookActivity.this,
							ChuShi_TuanDuiXQActivity.class);
					Bundle bundle = new Bundle();
					bundle.putSerializable("user", lci3.get(arg2));
					in.putExtras(bundle);
					startActivity(in);
				}
			});
		} else if (XJ.equals("三星级") && LB.equals("个人")) {
			geR = new CommonAdapter<CookInfo>(CookActivity.this, lci6,
					R.layout.item_hunqinggongsi) {

				@Override
				public void convert(ViewHolder helper, CookInfo item) {
					// TODO Auto-generated method stub

					helper.setText(R.id.hunqing_gongsi, item.getRealName());
					helper.setVis(R.id.beijing);
					helper.loadImage(R.id.shipei_tupian, item.getImageUrl());
					helper.setTextColor(R.id.hunqing_gongsi, Color.BLACK,
							Color.parseColor("#adadad"), index);
					helper.loadImage(R.id.shipei_tupian, item.getImageUrl());
				}

				@Override
				public void convert(ViewHolder helper, AnLi item) {

				}
			};
			lv1.setAdapter(geR);
			lv1.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					index = arg2;
					geR.notifyDataSetChanged();
					Intent in = new Intent(CookActivity.this,
							ChuShi_GeRenXQActivity.class);
					Bundle bundle = new Bundle();
					bundle.putSerializable("chushigeren", lci6.get(arg2));
					in.putExtras(bundle);
					startActivity(in);
				}
			});
		} else if (XJ.equals("四星级") && LB.equals("团队")) {
			tuan = new CommonAdapter<CookInfo>(CookActivity.this, lci4,
					R.layout.item_hunqinggongsi) {

				@Override
				public void convert(ViewHolder helper, CookInfo item) {
					// TODO Auto-generated method stub
					helper.setText(R.id.hunqing_gongsi, item.getRealName());
					helper.setVis(R.id.beijing);
					helper.setTextColor(R.id.hunqing_gongsi, Color.BLACK,
							Color.parseColor("#adadad"), index);
					helper.loadImage(R.id.shipei_tupian, item.getImageUrl());
				}

				@Override
				public void convert(ViewHolder helper, AnLi item) {

				}

			};
			lv1.setAdapter(tuan);
			lv1.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					index = arg2;
					tuan.notifyDataSetChanged();
					Intent in = new Intent(CookActivity.this,
							ChuShi_TuanDuiXQActivity.class);
					Bundle bundle = new Bundle();
					bundle.putSerializable("user", lci4.get(arg2));
					in.putExtras(bundle);
					startActivity(in);
				}
			});
		} else if (XJ.equals("四星级") && LB.equals("个人")) {
			geR = new CommonAdapter<CookInfo>(CookActivity.this, lci7,
					R.layout.item_hunqinggongsi) {

				@Override
				public void convert(ViewHolder helper, CookInfo item) {
					// TODO Auto-generated method stub

					helper.setText(R.id.hunqing_gongsi, item.getRealName());
					helper.setVis(R.id.beijing);
					helper.loadImage(R.id.shipei_tupian, item.getImageUrl());
					helper.setTextColor(R.id.hunqing_gongsi, Color.BLACK,
							Color.parseColor("#adadad"), index);
					helper.loadImage(R.id.shipei_tupian, item.getImageUrl());
				}

				@Override
				public void convert(ViewHolder helper, AnLi item) {

				}
			};
			lv1.setAdapter(geR);
			lv1.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					index = arg2;
					geR.notifyDataSetChanged();
					Intent in = new Intent(CookActivity.this,
							ChuShi_GeRenXQActivity.class);
					Bundle bundle = new Bundle();
					bundle.putSerializable("chushigeren", lci7.get(arg2));
					in.putExtras(bundle);
					startActivity(in);
				}
			});
		} else if (XJ.equals("五星级") && LB.equals("团队")) {
			tuan = new CommonAdapter<CookInfo>(CookActivity.this, lci5,
					R.layout.item_hunqinggongsi) {

				@Override
				public void convert(ViewHolder helper, CookInfo item) {
					// TODO Auto-generated method stub
					helper.setText(R.id.hunqing_gongsi, item.getRealName());
					helper.setVis(R.id.beijing);
					helper.setTextColor(R.id.hunqing_gongsi, Color.BLACK,
							Color.parseColor("#adadad"), index);
					helper.loadImage(R.id.shipei_tupian, item.getImageUrl());
				}

				@Override
				public void convert(ViewHolder helper, AnLi item) {

				}

			};
			lv1.setAdapter(tuan);
			lv1.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					index = arg2;
					tuan.notifyDataSetChanged();
					Intent in = new Intent(CookActivity.this,
							ChuShi_TuanDuiXQActivity.class);
					Bundle bundle = new Bundle();
					bundle.putSerializable("user", lci5.get(arg2));
					in.putExtras(bundle);
					startActivity(in);
				}
			});
		} else if (XJ.equals("五星级") && LB.equals("个人")) {
			geR = new CommonAdapter<CookInfo>(CookActivity.this, lci8,
					R.layout.item_hunqinggongsi) {

				@Override
				public void convert(ViewHolder helper, CookInfo item) {
					// TODO Auto-generated method stub

					helper.setText(R.id.hunqing_gongsi, item.getRealName());
					helper.setVis(R.id.beijing);
					helper.loadImage(R.id.shipei_tupian, item.getImageUrl());
					helper.setTextColor(R.id.hunqing_gongsi, Color.BLACK,
							Color.parseColor("#adadad"), index);
					helper.loadImage(R.id.shipei_tupian, item.getImageUrl());
				}

				@Override
				public void convert(ViewHolder helper, AnLi item) {

				}
			};
			lv1.setAdapter(geR);
			lv1.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					index = arg2;
					geR.notifyDataSetChanged();
					Intent in = new Intent(CookActivity.this,
							ChuShi_GeRenXQActivity.class);
					Bundle bundle = new Bundle();
					bundle.putSerializable("chushigeren", lci8.get(arg2));
					in.putExtras(bundle);
					startActivity(in);
				}
			});
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (System.currentTimeMillis() - HomeActivity.dianJiShiJian > 500) {

				HomeActivity.dianJiShiJian = System.currentTimeMillis();
				CookActivity.this.finish();
				ActivityCollector.finishAll();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}


}
