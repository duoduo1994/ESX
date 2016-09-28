package com.example.administrator.activity;

import java.math.BigDecimal;
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

import android.content.Context;
import android.os.Bundle;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
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

public class ChuShi_TuanDuiXQActivity extends BaseActivity {
	private int ID;
	@ViewInject(R.id.chushi_tuandui_1)
	private LinearLayout tuanDui1;
	@ViewInject(R.id.chushi_yuyue)
	private LinearLayout yuYue1;
	@ViewInject(R.id.chushi_tuandui_2)
	private LinearLayout tuanDui2;

	@ViewInject(R.id.chushi_tdcl)
	private TextView chengLi;// ,baoJia;
	@ViewInject(R.id.chushi_tdrs)
	private TextView renShu;
	@ViewInject(R.id.chushi_tdfw)
	private TextView fuWurs;
	@ViewInject(R.id.chushi_tdzc)
	private TextView zhuChu;


	// 成立时间，厨师人数，服务人数，主厨，手机
	@ViewInject(R.id.chushi_zcnl)
	private TextView zhuChuFW;
	@ViewInject(R.id.chushi_zccl)
	private TextView zhuChuCL;
	@ViewInject(R.id.chushi_zcsc)
	private TextView zhuChuSC;
	@ViewInject(R.id.chushi_zczd)
	private TextView zhuChuJD;


	// 主厨 服务范围，厨龄，擅长，报价，接单能力，住址
	@ViewInject(R.id.unit_price)
	private TextView fuWuFei;
	private TextView xingMing;
	@ViewInject(R.id.woyaoyuyue_meiyong)
	private TextView meiyong;
	@ViewInject(R.id.tongyong_jiage)
	private TextView tongyong_jiage;
	@ViewInject(R.id.tv_title)
	private TextView tv_title;
	@ViewInject(R.id.chushi_tdfwxs)
	private TextView chushi_tdfwxs;



	private ImageView touXiang;
	@ViewInject(R.id.chushi_tdsixing)
	private ImageView siXing;
	@ViewInject(R.id.chushi_tdwuxing)
	private ImageView wuXing;
	@ViewInject(R.id.chushi_tdsixings)
	private ImageView hpsixing;
	@ViewInject(R.id.chushi_tdwuxings)
	private ImageView hpwuxing;
	@ViewInject(R.id.chushi_tdyixings)
	private ImageView hpyixing;
	@ViewInject(R.id.chushi_tderxings)
	private ImageView hperxing;
	@ViewInject(R.id.chushi_tdsanxings)
	private ImageView hpsanxing;


@ViewInject(R.id.jiarudingdan)
	private Button yuYue;
	private CookInfo cf;
	@ViewInject(R.id.ysxtj)
	private ImageView ysxtj;
	private String pkCookGrpID;
	@ViewInject(R.id.jiarudingdan)
	private Button b;
	@Override
	protected int setContentView() {
		return R.layout.chushi_xiangqing;
	}
	@Override
	protected void initView() {
		ActivityCollector.addActivity(this);
		// cf = (CookInfo) getIntent().getSerializableExtra("user");
		LocalStorage.initContext(this);

		b.setText("我要预约");

		tv_title.setText("厨师详情");
		findViewById(R.id.btn_back).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finishA();
			}
		});
		Load.getLoad(this);


		yuYue.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (!new LoginCheckAlertDialogUtils(
						ChuShi_TuanDuiXQActivity.this).showDialog()) {
					shangChuan();
				}
			}
		});


		meiyong.setVisibility(View.GONE);
		tongyong_jiage.setVisibility(View.GONE);
		fuWuFei.setVisibility(View.GONE);

		if (getIntent().getSerializableExtra("user") == null) {
			// fkCookID=getIntent().getIntExtra("fkCookID", 1);
			pkCookGrpID = getIntent().getStringExtra("fkCookGrpID");
			yuYue1.setVisibility(View.GONE);
			iv_more.setVisibility(View.GONE);
			cf = new CookInfo();
			getData(pkCookGrpID);
		} else {
			cf = (CookInfo) getIntent().getSerializableExtra("user");
			ID = cf.getPkCookID();
			initDatas();
		}
		setMoreListener();
	}




	@ViewInject(R.id.iv_more)
	private ImageView iv_more;

	private void setMoreListener() {

		iv_more.setOnClickListener(new IvListener(iv_more,
				ChuShi_TuanDuiXQActivity.this, 0));
	}

	private void getData(String pkCookGrpID2) {



		XUtilsHelper xUtilsHelper1 = new XUtilsHelper(this,"CooksHandler.ashx?Action=CooksGrp",1);
		RequestParams requestParams = new RequestParams();
		requestParams.addBodyParameter("CooksGrpID", pkCookGrpID2);
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
							JSONArray configlist = json.getJSONArray("厨师");
							JSONObject jObject = configlist.getJSONObject(0);
							cf.setRealName(jObject.getString("Name"));//
							cf.setCookName(jObject.getString("CookName"));//
							cf.setChuShiNum(jObject.getString("NumCook"));//
							cf.setGoodAt(jObject.getString("GoodAt"));//
							cf.setIsYsxRecommend(jObject
									.getString("IsYsxRecommend"));//
							cf.setFuWuNum(jObject.getString("ServiceNum"));//
							cf.setBornDateTime(jObject.getString("SetupDt"));//
							cf.setWorkingTime(jObject.getString("WorkingTime"));//
							cf.setMaximum(2 + "");
							cf.setMaximum(jObject.getString("Maximum"));//
							cf.setServiceArea(jObject.getString("ServiceArea"));//
							cf.setFkStarLevelID(Integer.parseInt(jObject
									.getString("StarLevel")));//
							cf.setScrol(jObject.getString("StarNum"));//
							cf.setImageUrl(jObject.getString("ImageUrl"));//
							initDatas();
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

			}
		});









		// http://120.27.141.95:8085/Ashx/CooksHandler.ashx?Action=CooksGrp&CooksGrpID=72
//		RequestParams p = new RequestParams();
//		p.put("CooksGrpID", pkCookGrpID2);
//		SmartFruitsRestClient.get("CooksHandler.ashx?Action=CooksGrp", p,
//				new AsyncHttpResponseHandler() {
//
//					@Override
//					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
//						String result = new String(arg2);
//						try {
//							JSONObject json = new JSONObject(result.trim());
//							JSONArray configlist = json.getJSONArray("厨师");
//							JSONObject jObject = configlist.getJSONObject(0);
//							cf.setRealName(jObject.getString("Name"));//
//							cf.setCookName(jObject.getString("CookName"));//
//							cf.setChuShiNum(jObject.getString("NumCook"));//
//							cf.setGoodAt(jObject.getString("GoodAt"));//
//							cf.setIsYsxRecommend(jObject
//									.getString("IsYsxRecommend"));//
//							cf.setFuWuNum(jObject.getString("ServiceNum"));//
//							cf.setBornDateTime(jObject.getString("SetupDt"));//
//							cf.setWorkingTime(jObject.getString("WorkingTime"));//
//							cf.setMaximum(2 + "");
//							cf.setMaximum(jObject.getString("Maximum"));//
//							cf.setServiceArea(jObject.getString("ServiceArea"));//
//							cf.setFkStarLevelID(Integer.parseInt(jObject
//									.getString("StarLevel")));//
//							cf.setScrol(jObject.getString("StarNum"));//
//							cf.setImageUrl(jObject.getString("ImageUrl"));//
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
//						Toast.makeText(ChuShi_TuanDuiXQActivity.this,
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






		XUtilsHelper xUtilsHelper1 = new XUtilsHelper(this,"weddingHandler.ashx?Action=GetCase",1);
		RequestParams p = new RequestParams();
		p.addBodyParameter("CookGrpID", String.valueOf(ID));
		p.addBodyParameter("fkCusTel", tel);
		p.addBodyParameter("CustPhyAddr", HomeActivity.strUniqueId);

		Observable.create(new Observable.OnSubscribe<String>() {
			@Override
			public void call(Subscriber<? super String> subscriber) {
				xUtilsHelper1.sendPost(p,subscriber);
			}
		}).subscribe(new Subscriber<String>() {
			@Override
			public void onCompleted() {

			}

			@Override
			public void onError(Throwable e) {
				if (ciShu >= 3) {
							isFalse = false;
					toast(ChuShi_TuanDuiXQActivity.this,
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
							// Toast.makeText(ChuShi_TuanDuiXQActivity.this,
							// result, Toast.LENGTH_LONG).show();
							if ("你还没有订单".equals(json.getString("提示"))) {
								Intent i = new Intent(
										ChuShi_TuanDuiXQActivity.this,
										ReserveActivity.class);
								i.putExtra("jiaRu", 21);
								startActivity(i);
							} else if ("添加成功".equals(json.getString("提示"))) {
								new Diolg(ChuShi_TuanDuiXQActivity.this, "确定",
										"null", "添加成功", "提示", 10);
							} else if ("添加失败".equals(json.getString("提示"))) {
								new Diolg(ChuShi_TuanDuiXQActivity.this, "确定",
										"null", "添加失败", "提示", 0);
							} else if ("意外错误".equals(json.getString("提示"))) {
								new Diolg(ChuShi_TuanDuiXQActivity.this, "确定",
										"null", "意外错误", "提示", 0);
							} else if ("你有一笔要付钱的订单".equals(json.getString("提示"))) {
								new Diolg(ChuShi_TuanDuiXQActivity.this, "确定",
										"null", "你有一笔要付钱的订单", "提示", 0);
							} else if ("".equals(json.getString("提示"))) {
								new Diolg(ChuShi_TuanDuiXQActivity.this, "确定",
										"null", "服务器异常", "提示", 0);
							} else if ("用户不能为空".equals(json.getString("提示"))) {
								new Diolg(ChuShi_TuanDuiXQActivity.this, "确定",
										"null", "用户不能为空", "提示", 0);
							} else if ("服务器忙碌".equals(json.getString("提示"))) {
								new Diolg(ChuShi_TuanDuiXQActivity.this, "确定",
										"null", "服务器忙碌", "提示", 0);
							} else if ("你的账户已在另一台手机登录".equals(json
									.getString("提示"))) {
								new Diolg(ChuShi_TuanDuiXQActivity.this, "确定",
										"返回", "你的账户已在另一台手机登录", "提示", 0);
							} else if ("替换成功".equals(json.getString("提示"))) {
								new Diolg(ChuShi_TuanDuiXQActivity.this, "确定",
										"返回", "替换成功", "提示", 10);
							} else if ("替换失败".equals(json.getString("提示"))) {
								new Diolg(ChuShi_TuanDuiXQActivity.this, "确定",
										"返回", "替换失败", "提示", 0);
							} else if ("绝不可能出现在这里".equals(json.getString("提示"))) {
								new Diolg(ChuShi_TuanDuiXQActivity.this, "确定",
										"返回", "绝不可能出现在这里", "提示", 0);
							} else if ("你已经加过这个团队了".equals(json.getString("提示"))) {
								new Diolg(ChuShi_TuanDuiXQActivity.this, "确定",
										"返回", "你已经加过这个团队了", "提示", 0);
							} else {
								Intent in = new Intent(
										ChuShi_TuanDuiXQActivity.this,
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
//		p.put("CookGrpID", ID);
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
//							// Toast.makeText(ChuShi_TuanDuiXQActivity.this,
//							// result, Toast.LENGTH_LONG).show();
//							if ("你还没有订单".equals(json.getString("提示"))) {
//								Intent i = new Intent(
//										ChuShi_TuanDuiXQActivity.this,
//										ReserveActivity.class);
//								i.putExtra("jiaRu", 21);
//								startActivity(i);
//							} else if ("添加成功".equals(json.getString("提示"))) {
//								new Diolg(ChuShi_TuanDuiXQActivity.this, "确定",
//										"null", "添加成功", "提示", 10);
//							} else if ("添加失败".equals(json.getString("提示"))) {
//								new Diolg(ChuShi_TuanDuiXQActivity.this, "确定",
//										"null", "添加失败", "提示", 0);
//							} else if ("意外错误".equals(json.getString("提示"))) {
//								new Diolg(ChuShi_TuanDuiXQActivity.this, "确定",
//										"null", "意外错误", "提示", 0);
//							} else if ("你有一笔要付钱的订单".equals(json.getString("提示"))) {
//								new Diolg(ChuShi_TuanDuiXQActivity.this, "确定",
//										"null", "你有一笔要付钱的订单", "提示", 0);
//							} else if ("".equals(json.getString("提示"))) {
//								new Diolg(ChuShi_TuanDuiXQActivity.this, "确定",
//										"null", "服务器异常", "提示", 0);
//							} else if ("用户不能为空".equals(json.getString("提示"))) {
//								new Diolg(ChuShi_TuanDuiXQActivity.this, "确定",
//										"null", "用户不能为空", "提示", 0);
//							} else if ("服务器忙碌".equals(json.getString("提示"))) {
//								new Diolg(ChuShi_TuanDuiXQActivity.this, "确定",
//										"null", "服务器忙碌", "提示", 0);
//							} else if ("你的账户已在另一台手机登录".equals(json
//									.getString("提示"))) {
//								new Diolg(ChuShi_TuanDuiXQActivity.this, "确定",
//										"返回", "你的账户已在另一台手机登录", "提示", 0);
//							} else if ("替换成功".equals(json.getString("提示"))) {
//								new Diolg(ChuShi_TuanDuiXQActivity.this, "确定",
//										"返回", "替换成功", "提示", 10);
//							} else if ("替换失败".equals(json.getString("提示"))) {
//								new Diolg(ChuShi_TuanDuiXQActivity.this, "确定",
//										"返回", "替换失败", "提示", 0);
//							} else if ("绝不可能出现在这里".equals(json.getString("提示"))) {
//								new Diolg(ChuShi_TuanDuiXQActivity.this, "确定",
//										"返回", "绝不可能出现在这里", "提示", 0);
//							} else if ("你已经加过这个团队了".equals(json.getString("提示"))) {
//								new Diolg(ChuShi_TuanDuiXQActivity.this, "确定",
//										"返回", "你已经加过这个团队了", "提示", 0);
//							} else {
//								Intent in = new Intent(
//										ChuShi_TuanDuiXQActivity.this,
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
//							Toast.makeText(ChuShi_TuanDuiXQActivity.this,
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
	private String c;

	private void initDatas() {
		touXiang = (ImageView) findViewById(R.id.chushi_touxiang_xq);// 头像
		Load.imageLoader.displayImage(cf.getImageUrl(), touXiang, Load.options);

		xingMing = (TextView) findViewById(R.id.chushi_xingming_xq);// 姓名
		xingMing.setText(cf.getRealName());
		System.out.println(cf.getBornDateTime() + "asdjajfbbhfa");

		if ("".equals(cf.getBornDateTime()) || cf.getBornDateTime() == null) {
			chengLi.setText("");
		} else {
			c = cf.getBornDateTime().substring(0,
					cf.getBornDateTime().lastIndexOf("/") + 3);
			chengLi.setText(c);// 团队成立时间
		}

		renShu.setText(cf.getChuShiNum() + "");// 厨师人数

		fuWurs.setText(cf.getFuWuNum() + "");// 服务人数

		zhuChu.setText(cf.getCookName());// 主厨姓名

		zhuChuCL.setText(cf.getWorkingTime()+"年");// 主厨厨龄

		chushi_tdfwxs.setText(cf.getServiceNumGrp());// 服务数目

		String good = cf.getGoodAt();// 擅长菜系
		String[] g = good.split("#");
		for (int i = 0; i < g.length; i++) {
			if (Integer.parseInt(g[i]) > 10) {
				zhuChuSC.setText(zhuChuSC.getText() + "");
			} else {
				zhuChuSC.setText(zhuChuSC.getText()
						+ cai[Integer.parseInt(g[i]) - 1] + "  ");
			}
		}

		String dajdnl = cf.getMaximum();// 最大接单能力
		if ("".equals(dajdnl) || dajdnl == null || Integer.parseInt(dajdnl) > 8) {
			zhuChuJD.setText(dajdnl);
		} else {
			zhuChuJD.setText(zdjdnl[Integer.parseInt(dajdnl) - 1]);
		}

		if (!TextUtils.isEmpty(cf.getServiceArea())) {// 服务范围
			String fuf = cf.getServiceArea();
			String[] f = fuf.split("#");
			for (int i = 0; i < f.length; i++) {
				if (Integer.parseInt(f[i]) > 3) {
					zhuChuFW.setText(zhuChuFW.getText() + "");
				} else {
					zhuChuFW.setText(zhuChuFW.getText()
							+ fu[Integer.parseInt(f[i]) - 1] + " ");
				}
			}
		}

		if (cf.getIsYsxRecommend().equals("1")) {// 是否是永尚鲜推荐
			ysxtj.setVisibility(View.VISIBLE);
		}

		if (cf.getFkStarLevelID() == 3) {// 星级水平
			siXing.setVisibility(View.GONE);
			wuXing.setVisibility(View.GONE);
		} else if (cf.getFkStarLevelID() == 4) {
			wuXing.setVisibility(View.GONE);
		}

		BigDecimal a = new BigDecimal(cf.getScrol()).setScale(0,
				BigDecimal.ROUND_HALF_UP);
		int xing = a.intValue();// 好评星级

		System.out.println(xing + "sahfvbabhjajsj");
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

	}

	private boolean isFalse = true;
	private int ciShu = 0;




}
