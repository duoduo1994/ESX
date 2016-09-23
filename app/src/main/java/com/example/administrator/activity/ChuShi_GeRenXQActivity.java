package com.example.administrator.activity;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.administrator.list.CookInfo;
import com.example.administrator.myapplication.R;
import com.example.administrator.net.XUtilsHelper;
import com.example.administrator.utils.ActivityCollector;
import com.example.administrator.utils.Diolg;
import com.example.administrator.utils.IvListener;
import com.example.administrator.utils.Load;
import com.example.administrator.utils.LocalStorage;
import com.example.administrator.utils.LoginCheckAlertDialogUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.view.annotation.ViewInject;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import rx.Observable;
import rx.Subscriber;

public class ChuShi_GeRenXQActivity extends BaseActivity {

	@ViewInject(R.id.chushi_renshixq)
	private LinearLayout geRen;
	@ViewInject(R.id.chushi_tuanduixq)
	private LinearLayout tuanDui;
@ViewInject(R.id.chushi_geren_xq)
	private LinearLayout geRen1;
	@ViewInject(R.id.chushi_yuyue)
	private LinearLayout  yuYue1;
@ViewInject(R.id.unit_price)
	private TextView fuWuFei;
	@ViewInject(R.id.chushi_xingming_xq)
	private TextView xingMing;
	@ViewInject(R.id.tv_title)
	private TextView tv_title;
	@ViewInject(R.id.chushi_touxiang_xq)
	private ImageView touXiang;
	@ViewInject(R.id.chushi_grsixing)
	private ImageView siXing;
	@ViewInject(R.id.chushi_grwuxing)
	private ImageView wuXing;
	@ViewInject(R.id.chushi_grsixings_gr)
	private ImageView hpsixing;
	@ViewInject(R.id.chushi_grwuxings_gr)
	private ImageView hpwuxing;
	@ViewInject(R.id.chushi_gryixings_gr)
	private ImageView hpyixing;
	@ViewInject(R.id.chushi_grerxings_gr)
	private ImageView hperxing;
	@ViewInject(R.id.chushi_grsanxings_gr)
	private ImageView hpsanxing;


@ViewInject(R.id.jiarudingdan)
	private Button yuYue;
	@ViewInject(R.id.chushi_grxb)
	private TextView xingBie ;// baoJia,

	// 性别，年龄，厨龄，手机，擅长，报价，接单，住址，服务
	@ViewInject(R.id.chushi_grnl)
	private TextView nianLi;
	@ViewInject(R.id.chushi_grcl)
	private TextView chuLing;
	@ViewInject(R.id.chushi_grsc)
	private TextView shanChang;
	@ViewInject(R.id.chushi_grzd)
	private TextView nengLi;
	@ViewInject(R.id.chushi_grfw)
	private TextView fuWu;
	@ViewInject(R.id.woyaoyuyue_meiyong)
	private TextView meiyong;
	@ViewInject(R.id.tongyong_jiage)
	private TextView tongyong_jiage;
	@ViewInject(R.id.chushi_grfwshu_mu)
	private TextView fuWushumu;

	private CookInfo ci;
	private String fkCookID;
	@ViewInject(R.id.jiarudingdan)
	private Button b;
	@Override
	protected int setContentView() {
		return R.layout.chushi_xiangqing;
	}
	@Override
	protected void initView() {

		b.setText("我要预约");
		ActivityCollector.addActivity(this);
		// ci = (CookInfo) getIntent().getSerializableExtra("chushigeren");


		geRen.setVisibility(View.VISIBLE);
		tuanDui.setVisibility(View.GONE);
		findViewById(R.id.btn_back).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finishA();
			}
		});
		Load.getLoad(this);
		LocalStorage.initContext(this);





		tv_title.setText("厨师详情");




		meiyong.setVisibility(View.GONE);
		tongyong_jiage.setVisibility(View.GONE);
		fuWuFei.setVisibility(View.GONE);
		yuYue.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				// BookHandler.ashx?Action=AddIntoOrder&Type=Type_Cook
				System.out.println(123654);
				if (!new LoginCheckAlertDialogUtils(ChuShi_GeRenXQActivity.this)
						.showDialog()) {
					shangChuan();
				}
			}
		});







		// shouJi = (TextView) geRen1.findViewById(R.id.chushi_grsj);

		// baoJia = (TextView) geRen1.findViewById(R.id.chushi_grbj);

		// zhuZhi = (TextView) geRen1.findViewById(R.id.chushi_grdz);











		if (getIntent().getSerializableExtra("chushigeren") == null) {
			// fkCookID=getIntent().getIntExtra("fkCookID", 1);
			fkCookID = getIntent().getStringExtra("fkCookID");
			iv_more.setVisibility(View.GONE);
			yuYue1.setVisibility(View.GONE);
			ci = new CookInfo();
			getData(fkCookID);
		} else {
			ci = (CookInfo) getIntent().getSerializableExtra("chushigeren");
			initDatas();
		}
		setMoreListener();
	}


@ViewInject(R.id.iv_more)
	private ImageView iv_more;

	private void setMoreListener() {

		iv_more.setOnClickListener(new IvListener(iv_more,
				ChuShi_GeRenXQActivity.this, 0));
	}

	private void getData(String msg) {





		XUtilsHelper xUtilsHelper1 = new XUtilsHelper(this,"CooksHandler.ashx?Action=GetDetail");
		RequestParams requestParams = new RequestParams();
		requestParams.addBodyParameter("PkCookID", msg);
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

			}

			@Override
			public void onNext(String s) {
				try {
							JSONObject json = new JSONObject(s.trim());
							JSONArray configlist = json.getJSONArray("详情");
							JSONObject jObject = configlist.getJSONObject(0);
							ci.setRealName(jObject.getString("RealName"));
							ci.setSex(jObject.getString("Sex"));
							ci.setIsYsxRecommend(jObject
									.getString("IsYsxRecommend"));
							ci.setFuWuNum(0 + "");
							ci.setImageUrl(jObject.getString("ImageUrl"));// 头像
							System.out.println(ci.getImageUrl()
									+ "%%%%%%%%%%%%%%%%%%");
							// ci.setFuWuNum(jObject.getString("ServiceNum"));
							ci.setBornDateTime(jObject
									.getString("BornDateTime"));
							ci.setGoodAt(jObject.getString("GoodAt"));
							ci.setMaximum(jObject.getString("Maximum"));
							ci.setServiceArea(jObject.getString("ServiceArea"));
							ci.setStarLevel(jObject.getString("StarLevel"));
							ci.setScrol(jObject.getString("StarNum"));
							ci.setWorkingTime(jObject.getString("WorkingTime"));// 厨玲
							initDatas();
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}


			}
		});




















		// http://120.27.141.95:8085/Ashx/CooksHandler.ashx?Action=GetDetail&CooksGrpID=72
//		RequestParams p = new RequestParams();
//		p.put("PkCookID", msg);
//		Log.i("RequestParams111111111111111", msg);
//		SmartFruitsRestClient.get("CooksHandler.ashx?Action=GetDetail", p,
//				new AsyncHttpResponseHandler() {
//
//					@Override
//					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
//						String result = new String(arg2);
//						System.out.println(result);
//						try {
//							JSONObject json = new JSONObject(result.trim());
//							JSONArray configlist = json.getJSONArray("详情");
//							JSONObject jObject = configlist.getJSONObject(0);
//							ci.setRealName(jObject.getString("RealName"));
//							ci.setSex(jObject.getString("Sex"));
//							ci.setIsYsxRecommend(jObject
//									.getString("IsYsxRecommend"));
//							ci.setFuWuNum(0 + "");
//							ci.setImageUrl(jObject.getString("ImageUrl"));// 头像
//							System.out.println(ci.getImageUrl()
//									+ "%%%%%%%%%%%%%%%%%%");
//							// ci.setFuWuNum(jObject.getString("ServiceNum"));
//							ci.setBornDateTime(jObject
//									.getString("BornDateTime"));
//							ci.setGoodAt(jObject.getString("GoodAt"));
//							ci.setMaximum(jObject.getString("Maximum"));
//							ci.setServiceArea(jObject.getString("ServiceArea"));
//							ci.setStarLevel(jObject.getString("StarLevel"));
//							ci.setScrol(jObject.getString("StarNum"));
//							ci.setWorkingTime(jObject.getString("WorkingTime"));// 厨玲
//							initDatas();
//						} catch (JSONException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//
//					}
//
//					@Override
//					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
//							Throwable arg3) {
//						// TODO Auto-generated method stub
//						Toast.makeText(ChuShi_GeRenXQActivity.this,
//								arg3.toString(), 1).show();
//					}
//				});

	}


	protected void toast(Context context , String info){
		Toast.makeText(context,info,Toast.LENGTH_SHORT).show();
	}

	private void shangChuan() {
		String tel = (String) LocalStorage.get("UserTel").toString();
		System.out.println("!!!!!!!!!!!!!!" + tel);
		XUtilsHelper xUtilsHelper1 = new XUtilsHelper(this,"BookHandler.ashx?Action=AddIntoOrder&Type=Type_Cook");
		RequestParams requestParams = new RequestParams();
		requestParams.addBodyParameter("CookID", String.valueOf(ci.getPkCookID()));
		requestParams.addBodyParameter("fkCusTel", tel);
		requestParams.addBodyParameter("CustPhyAddr", HomeActivity.strUniqueId);
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
toast(ChuShi_GeRenXQActivity.this,
		getString(R.string.conn_failed));

						}
						;

						if (isFalse) {
							shangChuan();
							ciShu++;
						}

			}

			@Override
			public void onNext(String s) {
				try {
							JSONObject json = new JSONObject(s.trim());
							// Toast.makeText(ChuShi_GeRenXQActivity.this,
							// result, Toast.LENGTH_LONG).show();
							if ("你还没有订单".equals(json.getString("提示"))) {
								Intent i = new Intent(
										ChuShi_GeRenXQActivity.this,
										ReserveActivity.class);
								i.putExtra("jiaRu", 21);
								startActivity(i);
							} else if ("添加成功".equals(json.getString("提示"))) {
								new Diolg(ChuShi_GeRenXQActivity.this, "确定",
										"null", "添加成功", "提示", 10);
							} else if ("添加失败".equals(json.getString("提示"))) {
								new Diolg(ChuShi_GeRenXQActivity.this, "确定",
										"null", "添加失败", "提示", 0);
							} else if ("意外错误".equals(json.getString("提示"))) {
								new Diolg(ChuShi_GeRenXQActivity.this, "确定",
										"null", "意外错误", "提示", 0);
							} else if ("用户不能为空".equals(json.getString("提示"))) {
								new Diolg(ChuShi_GeRenXQActivity.this, "确定",
										"null", "用户不能为空", "提示", 0);
							} else if ("你有一笔要付钱的订单".equals(json.getString("提示"))) {
								new Diolg(ChuShi_GeRenXQActivity.this, "确定",
										"null", "你有一笔要付钱的订单", "提示", 0);
							} else if ("".equals(json.getString("提示"))) {
								new Diolg(ChuShi_GeRenXQActivity.this, "确定",
										"null", "服务器异常", "提示", 0);
							} else if ("服务器忙碌".equals(json.getString("提示"))) {
								new Diolg(ChuShi_GeRenXQActivity.this, "确定",
										"null", "服务器忙碌", "提示", 0);
							} else if ("你的账户已在另一台手机登录".equals(json
									.getString("提示"))) {
								new Diolg(ChuShi_GeRenXQActivity.this, "确定",
										"返回", "你的账户已在另一台手机登录", "提示", 0);
							} else if ("替换成功".equals(json.getString("提示"))) {
								new Diolg(ChuShi_GeRenXQActivity.this, "确定",
										"返回", "替换成功", "提示", 10);
							} else if ("替换失败".equals(json.getString("提示"))) {
								new Diolg(ChuShi_GeRenXQActivity.this, "确定",
										"返回", "替换失败", "提示", 0);
							} else if ("绝不可能出现在这里".equals(json.getString("提示"))) {
								new Diolg(ChuShi_GeRenXQActivity.this, "确定",
										"返回", "绝不可能出现在这里", "提示", 0);
							} else if ("你已经加过这个厨师了".equals(json.getString("提示"))) {
								new Diolg(ChuShi_GeRenXQActivity.this, "确定",
										"返回", "你已经加过这个厨师了", "提示", 0);
							} else {
								Intent in = new Intent(
										ChuShi_GeRenXQActivity.this,
										MainActivity.class);
								in.putExtra("dengRu", 45);
								startActivity(in);
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
			}
		});













//		RequestParams p = new RequestParams();
//		p.put("CookID", ci.getPkCookID());
//		p.put("fkCusTel", tel);
//		p.put("CustPhyAddr", HomeActivity.strUniqueId);
//		SmartFruitsRestClient.get(
//				"BookHandler.ashx?Action=AddIntoOrder&Type=Type_Cook", p,
//				new AsyncHttpResponseHandler() {
//
//					@Override
//					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
//						// TODO Auto-generated method stub
//						String result = new String(arg2);
//						System.out.println(result);
//						try {
//							JSONObject json = new JSONObject(result.trim());
//							// Toast.makeText(ChuShi_GeRenXQActivity.this,
//							// result, Toast.LENGTH_LONG).show();
//							if ("你还没有订单".equals(json.getString("提示"))) {
//								Intent i = new Intent(
//										ChuShi_GeRenXQActivity.this,
//										ReserveActivity.class);
//								i.putExtra("jiaRu", 21);
//								startActivity(i);
//							} else if ("添加成功".equals(json.getString("提示"))) {
//								new Diolg(ChuShi_GeRenXQActivity.this, "确定",
//										"null", "添加成功", "提示", 10);
//							} else if ("添加失败".equals(json.getString("提示"))) {
//								new Diolg(ChuShi_GeRenXQActivity.this, "确定",
//										"null", "添加失败", "提示", 0);
//							} else if ("意外错误".equals(json.getString("提示"))) {
//								new Diolg(ChuShi_GeRenXQActivity.this, "确定",
//										"null", "意外错误", "提示", 0);
//							} else if ("用户不能为空".equals(json.getString("提示"))) {
//								new Diolg(ChuShi_GeRenXQActivity.this, "确定",
//										"null", "用户不能为空", "提示", 0);
//							} else if ("你有一笔要付钱的订单".equals(json.getString("提示"))) {
//								new Diolg(ChuShi_GeRenXQActivity.this, "确定",
//										"null", "你有一笔要付钱的订单", "提示", 0);
//							} else if ("".equals(json.getString("提示"))) {
//								new Diolg(ChuShi_GeRenXQActivity.this, "确定",
//										"null", "服务器异常", "提示", 0);
//							} else if ("服务器忙碌".equals(json.getString("提示"))) {
//								new Diolg(ChuShi_GeRenXQActivity.this, "确定",
//										"null", "服务器忙碌", "提示", 0);
//							} else if ("你的账户已在另一台手机登录".equals(json
//									.getString("提示"))) {
//								new Diolg(ChuShi_GeRenXQActivity.this, "确定",
//										"返回", "你的账户已在另一台手机登录", "提示", 0);
//							} else if ("替换成功".equals(json.getString("提示"))) {
//								new Diolg(ChuShi_GeRenXQActivity.this, "确定",
//										"返回", "替换成功", "提示", 10);
//							} else if ("替换失败".equals(json.getString("提示"))) {
//								new Diolg(ChuShi_GeRenXQActivity.this, "确定",
//										"返回", "替换失败", "提示", 0);
//							} else if ("绝不可能出现在这里".equals(json.getString("提示"))) {
//								new Diolg(ChuShi_GeRenXQActivity.this, "确定",
//										"返回", "绝不可能出现在这里", "提示", 0);
//							} else if ("你已经加过这个厨师了".equals(json.getString("提示"))) {
//								new Diolg(ChuShi_GeRenXQActivity.this, "确定",
//										"返回", "你已经加过这个厨师了", "提示", 0);
//							} else {
//								Intent in = new Intent(
//										ChuShi_GeRenXQActivity.this,
//										MainActivity.class);
//								in.putExtra("dengRu", 45);
//								startActivity(in);
//							}
//						} catch (JSONException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//					}
//
//					@Override
//					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
//							Throwable arg3) {
//						// TODO Auto-generated method stub
//						System.out.println(arg3 + "asdfgdhgsa");
//						if (ciShu >= 3) {
//							isFalse = false;
//							Toast.makeText(ChuShi_GeRenXQActivity.this,
//									getString(R.string.conn_failed),
//									Toast.LENGTH_SHORT).show();
//						}
//						;
//
//						if (isFalse) {
//							shangChuan();
//							ciShu++;
//						}
//
//					}
//				});
	}

	private String cai[] = { "宁波本帮菜", "鲁菜", "川菜", "粤菜", "淮扬菜", "闽菜", "浙菜",
			"湘菜", "徽菜", "西餐" };
	private String[] zdjdnl = { "5桌", "10桌", "15桌", "20桌", "30桌", "40桌",
			"50桌以上" };
	private String fu[] = { "鄞州区", "北仑区", "奉化区" };
	@ViewInject(R.id.ysxtj)
	private ImageView ysxtj;

	private void initDatas() {
		xingMing.setText(ci.getRealName());// 姓名

		Load.imageLoader.displayImage(ci.getImageUrl(), touXiang, Load.options);
		if (ci.getSex() == null || "".equals(ci.getSex())) {
			xingBie.setText("");
		} else {

			if (ci.getSex().equals("1")) {
				xingBie.setText("男");
			} else {
				xingBie.setText("女");
			}

		}

		if (ci.getIsYsxRecommend().equals("1")) { // 是否是永尚鲜推荐
			ysxtj.setVisibility(View.VISIBLE);
		}

		fuWushumu.setText(ci.getFuWuNum());// 服务数目

		SimpleDateFormat df = new SimpleDateFormat("yyyy");// 设置日期格式
		String nian = df.format(new Date());
		String date = ci.getBornDateTime();
		System.out.println(date + "asdjaf");
		if (date == null || "".equals(date)) {
			nianLi.setText("");
		} else {
			date = date.substring(0, 4);
			nianLi.setText(Integer.parseInt(nian) - Integer.parseInt(date)
					+ "岁");// 厨师年龄
		}

		chuLing.setText(ci.getWorkingTime() + "年");// 厨师厨龄

		String good = ci.getGoodAt();// 擅长菜系

		String[] g = good.split("#");
		for (int i = 0; i < g.length; i++) {
			if (Integer.parseInt(g[i]) > 10) {
				shanChang.setText(shanChang.getText() + "");
			} else {
				shanChang.setText(shanChang.getText()
						+ cai[Integer.parseInt(g[i]) - 1] + "  ");
			}
		}

		String dajdnl = ci.getMaximum(); // 最大接单能力
		System.out.println(dajdnl + "最大接单能力");
		if ("".equals(dajdnl) || dajdnl == null || Integer.parseInt(dajdnl) > 8) {
			nengLi.setText(dajdnl);
		} else {
			nengLi.setText(zdjdnl[Integer.parseInt(dajdnl) - 1]);
		}

		String fuf = ci.getServiceArea();// 服务范围（区）
		String[] f = fuf.split("#");
		for (int i = 0; i < f.length; i++) {
			if (Integer.parseInt(f[i]) > 3) {
				fuWu.setText(fuWu.getText() + "");
			} else {
				fuWu.setText(fuWu.getText() + fu[Integer.parseInt(f[i]) - 1]
						+ "  ");
			}
		}

		if (ci.getStarLevel().equals("3")) {// 星级水平
			siXing.setVisibility(View.GONE);
			wuXing.setVisibility(View.GONE);
		} else if (ci.getStarLevel().equals("4")) {
			wuXing.setVisibility(View.GONE);
		}
		System.out.println(ci.getScrol()
				+ "%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
		BigDecimal a = new BigDecimal(ci.getScrol()).setScale(0,
				BigDecimal.ROUND_HALF_UP);
		int xing = a.intValue(); // 好评星级
		System.out.println(xing + "%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
		if (xing == 0) {
			hpyixing.setVisibility(View.GONE);
			hperxing.setVisibility(View.GONE);
			hpsanxing.setVisibility(View.GONE);
			hpsixing.setVisibility(View.GONE);
			hpwuxing.setVisibility(View.GONE);
		} else if (xing == 1) {
			hperxing.setVisibility(View.GONE);
			hpsanxing.setVisibility(View.GONE);
			hpsixing.setVisibility(View.GONE);
			hpwuxing.setVisibility(View.GONE);
		} else if (xing == 2) {
			hpsanxing.setVisibility(View.GONE);
			hpsixing.setVisibility(View.GONE);
			hpwuxing.setVisibility(View.GONE);
		} else if (xing == 3) {
			hpsixing.setVisibility(View.GONE);
			hpwuxing.setVisibility(View.GONE);
		} else if (xing == 4) {
			hpwuxing.setVisibility(View.GONE);
		}

		// fuWuFei.setText((int) ci.getPrice() + "");

	}

	private boolean isFalse = true;
	private int ciShu = 0;




}
