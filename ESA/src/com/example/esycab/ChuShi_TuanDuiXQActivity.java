package com.example.esycab;

import java.math.BigDecimal;
import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.example.esycab.network.SmartFruitsRestClient;
import com.example.esycab.utils.ActivityCollector;
import com.example.esycab.utils.Diolg;
import com.example.esycab.utils.IvListener;
import com.example.esycab.utils.Load;
import com.example.esycab.utils.LocalStorage;
import com.example.esycab.utils.LoginCheckAlertDialogUtils;
import com.eyoucab.list.CookInfo;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
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

public class ChuShi_TuanDuiXQActivity extends Activity {
	private int ID;
	private LinearLayout tuanDui1, yuYue1, tuanDui2;
	private TextView chengLi, renShu, fuWurs, zhuChu;// ,baoJia;
	// 成立时间，厨师人数，服务人数，主厨，手机
	private TextView zhuChuFW, zhuChuCL, zhuChuSC, zhuChuJD;
	// 主厨 服务范围，厨龄，擅长，报价，接单能力，住址
	private TextView fuWuFei, xingMing, meiyong, tongyong_jiage, tv_title,
			chushi_tdfwxs;
	private ImageView touXiang, siXing, wuXing, hpsixing, hpwuxing, hpyixing,
			hperxing, hpsanxing;
	private Button yuYue;
	private CookInfo cf;
	private ImageView ysxtj;
	private String pkCookGrpID;
	private Button b;

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
		setContentView(R.layout.chushi_xiangqing);
		ActivityCollector.addActivity(this);
		// cf = (CookInfo) getIntent().getSerializableExtra("user");
		LocalStorage.initContext(this);
		b = (Button) findViewById(R.id.jiarudingdan);
		b.setText("我要预约");
		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_title.setText("厨师详情");
		findViewById(R.id.btn_back).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (System.currentTimeMillis() - HomeActivity.dianJiShiJian > 500) {
					HomeActivity.dianJiShiJian = System.currentTimeMillis();
					ChuShi_TuanDuiXQActivity.this.finish();
				}
			}
		});
		Load.getLoad(this);

		tuanDui1 = (LinearLayout) findViewById(R.id.chushi_tuandui_1);
		yuYue1 = (LinearLayout) findViewById(R.id.chushi_yuyue);
		chengLi = (TextView) tuanDui1.findViewById(R.id.chushi_tdcl);
		renShu = (TextView) tuanDui1.findViewById(R.id.chushi_tdrs);
		fuWurs = (TextView) tuanDui1.findViewById(R.id.chushi_tdfw);
		zhuChu = (TextView) tuanDui1.findViewById(R.id.chushi_tdzc);
		chushi_tdfwxs = (TextView) tuanDui1.findViewById(R.id.chushi_tdfwxs);

		tuanDui2 = (LinearLayout) findViewById(R.id.chushi_tuandui_2);
		zhuChuFW = (TextView) tuanDui2.findViewById(R.id.chushi_zcnl);
		zhuChuCL = (TextView) tuanDui2.findViewById(R.id.chushi_zccl);
		zhuChuSC = (TextView) tuanDui2.findViewById(R.id.chushi_zcsc);
		zhuChuJD = (TextView) tuanDui2.findViewById(R.id.chushi_zczd);

		siXing = (ImageView) tuanDui1.findViewById(R.id.chushi_tdsixing);
		wuXing = (ImageView) tuanDui1.findViewById(R.id.chushi_tdwuxing);
		hpsixing = (ImageView) tuanDui1.findViewById(R.id.chushi_tdsixings);
		hpwuxing = (ImageView) tuanDui1.findViewById(R.id.chushi_tdwuxings);
		hpyixing = (ImageView) tuanDui1.findViewById(R.id.chushi_tdyixings);
		hperxing = (ImageView) tuanDui1.findViewById(R.id.chushi_tderxings);
		hpsanxing = (ImageView) tuanDui1.findViewById(R.id.chushi_tdsanxings);

		ysxtj = (ImageView) findViewById(R.id.ysxtj);

		fuWuFei = (TextView) yuYue1.findViewById(R.id.unit_price);
		yuYue = (Button) yuYue1.findViewById(R.id.jiarudingdan);
		yuYue.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (!new LoginCheckAlertDialogUtils(
						ChuShi_TuanDuiXQActivity.this,0).showDialog()) {
					shangChuan();
				}
			}
		});
		tongyong_jiage = (TextView) yuYue1.findViewById(R.id.tongyong_jiage);
		meiyong = (TextView) yuYue1.findViewById(R.id.woyaoyuyue_meiyong);
		meiyong.setVisibility(View.GONE);
		tongyong_jiage.setVisibility(View.GONE);
		fuWuFei.setVisibility(View.GONE);
		iv_more = (ImageView) findViewById(R.id.iv_more);
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

	private ImageView iv_more;

	private void setMoreListener() {

		iv_more.setOnClickListener(new IvListener(iv_more,
				ChuShi_TuanDuiXQActivity.this, 0));
	}

	private void getData(String pkCookGrpID2) {
		// http://120.27.141.95:8085/Ashx/CooksHandler.ashx?Action=CooksGrp&CooksGrpID=72
		RequestParams p = new RequestParams();
		p.put("CooksGrpID", pkCookGrpID2);
		SmartFruitsRestClient.get("CooksHandler.ashx?Action=CooksGrp", p,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
						String result = new String(arg2);
						try {
							JSONObject json = new JSONObject(result.trim());
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

					@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
							Throwable arg3) {
						// TODO Auto-generated method stub
						Toast.makeText(ChuShi_TuanDuiXQActivity.this,
								arg3.toString(), 1).show();
					}
				});

	}

	private void shangChuan() {
		String tel = (String) LocalStorage.get("UserTel").toString();
		System.out.println("!!!!!!!!!!!!!!" + tel);
		RequestParams p = new RequestParams();
		p.put("CookGrpID", ID);
		p.put("fkCusTel", tel);
		p.put("CustPhyAddr", HomeActivity.strUniqueId);
		SmartFruitsRestClient.get(
				"BookHandler.ashx?Action=AddIntoOrder&Type=Type_Cook", p,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
						// TODO Auto-generated method stub
						String result = new String(arg2);
						System.out.println(result);
						try {
							JSONObject json = new JSONObject(result.trim());
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

					@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
							Throwable arg3) {
						// TODO Auto-generated method stub
						System.out.println(arg3 + "asdfgdhgsa");
						if (ciShu >= 3) {
							isFalse = false;
							Toast.makeText(ChuShi_TuanDuiXQActivity.this,
									getString(R.string.conn_failed),
									Toast.LENGTH_SHORT).show();
						}
						;

						if (isFalse) {
							shangChuan();
							ciShu++;
						}

					}
				});
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

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (System.currentTimeMillis() - HomeActivity.dianJiShiJian > 500) {
				HomeActivity.dianJiShiJian = System.currentTimeMillis();
				ChuShi_TuanDuiXQActivity.this.finish();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
