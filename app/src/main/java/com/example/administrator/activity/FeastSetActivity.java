package com.example.administrator.activity;

import java.io.File;
import java.util.ArrayList;

import java.util.List;



import com.example.administrator.entity.AnLi;
import com.example.administrator.list.CommonAdapter;
import com.example.administrator.list.ViewHolder;
import com.example.administrator.list.father;
import com.example.administrator.list.son;
import com.example.administrator.list.taocan;
import com.example.administrator.myapplication.R;
import com.example.administrator.net.XUtilsHelper;
import com.example.administrator.utils.ACache;
import com.example.administrator.utils.ActivityCollector;
import com.example.administrator.utils.Diolg;
import com.example.administrator.utils.IvListener;
import com.example.administrator.utils.Load;
import com.example.administrator.utils.LoadingDialog;
import com.example.administrator.utils.LocalStorage;
import com.example.administrator.utils.LoginCheckAlertDialogUtils;
import com.example.administrator.utils.ProConst;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;

import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import android.view.View;

import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;

import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import android.widget.ListView;
import android.widget.LinearLayout;

import android.widget.AdapterView.OnItemClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import rx.Observable;
import rx.Subscriber;

/**
 * 喜宴套餐☑
 * 
 * @author Administrator
 * 
 */
public class FeastSetActivity extends BaseActivity implements ProConst {
	@ViewInject(R.id.lv1s)
	private ListView lv1;
	@ViewInject(R.id.lv2)
	private ListView lv2;
	@ViewInject(R.id.btn_back)
	private Button iv;
	private String selectIndex;
	private int TotalPrice = 0;
	static List<son> lists = new ArrayList<son>();
	private List<son> l;
	private List<son> newList = new ArrayList<son>();
	private List<son> newList1 = new ArrayList<son>();
	private List<father> fatlist = new ArrayList<father>();
	@ViewInject(R.id.xiyan_taocan_jiage)
	private LinearLayout yuYue;
	private CommonAdapter<father> cf;
	@ViewInject(R.id.jiarudingdan)
	private Button button1;
	private ACache mCache;
	@ViewInject(R.id.tv_title)
	private TextView tv;
	@ViewInject(R.id.xiyan_caidan)
	private TextView caidan;
	private taocan t;
	@ViewInject(R.id.unit_price)
	private TextView jiaGe;
	private int isZiXuan;
	// private String a1;// 主副
	private String b;// 套餐ID
	private String c;
	private String name;
	@ViewInject(R.id.image_iamge)
	private ImageView image_iamge;
	private boolean quanp = false;
	private String Tprice;
	private StringBuilder caiID;
	private String IDcai;
	private double price;
	private LoadingDialog ld;
	@Override
	protected int setContentView() {
		return R.layout.wedding_party;
	}
	@Override
	protected void initView() {
		ActivityCollector.addActivity(FeastSetActivity.this);
		LocalStorage.initContext(this);

		tv.setText("套餐详情");
		ld = new LoadingDialog(FeastSetActivity.this, "正在加载，请稍后...");



		caidan.setVisibility(View.VISIBLE);
		l = new ArrayList<son>();
		t = (taocan) getIntent().getSerializableExtra("an");
		if (null != t) {
			isZiXuan = 1;
			selectIndex = t.getPkSetID(); // 获取套餐ID
			System.out.println(selectIndex + "******dsfgserfg******");
			name = t.getName();
			TotalPrice = (int) Double.parseDouble(t.getTotalPrice());
			Tprice = TotalPrice + "";
			// a1 = t.getFkSetCtID();
			b = t.getPkSetID();
			c = t.getLei();
			SendClick(selectIndex);
		} else {
			isZiXuan = 3;
			ZiXuan();
		}



		jiaGe.setText((int) TotalPrice + "");
		mCache = ACache.get(this);


		iv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				System.out.println(quanp + ",");
				if (isZiXuan == 3) {// zixuan
					System.out.println(LocalStorage.get("LoginStatus")
							+ "^^^^^^^^");
					caiID = new StringBuilder();
					price = 0.0;
					if (!new LoginCheckAlertDialogUtils(FeastSetActivity.this)
							.showDialog()) {
						for (int i = 0; i < FeastSetActivity.lists.size(); i++) {
							if (FeastSetActivity.lists.get(i).isiCho()) {
								caiID.append("H"
										+ FeastSetActivity.lists.get(i)
												.getAlbumTitle());
								System.out.println(caiID);
								price = price
										+ Double.parseDouble(FeastSetActivity.lists
												.get(i).getUnitPrice());
							}
						}
						if (caiID.length() > 0) {
							for (int i = 0; i < FeastSetActivity.lists.size(); i++) {
								if (FeastSetActivity.lists.get(i).isiCho()) {
									caiID.append("H"
											+ FeastSetActivity.lists.get(i)
													.getAlbumTitle());
									System.out.println(caiID);
									price = price
											+ Double.parseDouble(FeastSetActivity.lists
													.get(i).getUnitPrice());
								}
							}
							System.out.println("+++++++++++++++" + caiID);
							int end = caiID.length();
							IDcai = caiID.substring(1, end);
							System.out.println("QW" + IDcai);
							show();
						}else{
							HomeActivity.dianJiShiJian = System.currentTimeMillis();
							System.out.println(2);
							File f = new File(
									"/data/data/com.example.esycab/cache/ACache/1297131408");
							if (f.exists()) {
								f.delete();
							}
							// 结束当前界面
							FeastSetActivity.this.finish();
						}
					}

				}else
				if (System.currentTimeMillis() - HomeActivity.dianJiShiJian > 500) {
					HomeActivity.dianJiShiJian = System.currentTimeMillis();
					System.out.println(2);
					File f = new File(
							"/data/data/com.example.esycab/cache/ACache/1297131408");
					if (f.exists()) {
						f.delete();
					}
					// 结束当前界面
					FeastSetActivity.this.finish();

				}
			}
		});
		caidan.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (null != t) {
					for (int i = 0; i < lists.size(); i++) {
						if (lists.get(i).isTiHuan()) {
							isZiXuan = 2;// zixuan 3 guding 1 tihuan 2
							break;
						} else {
							isZiXuan = 1;
						}
					}
				}

				if (l.size() == 0 && isZiXuan == 3) {
					toast(FeastSetActivity.this, "您还没选择菜");

				} else {
					Intent intent = new Intent(FeastSetActivity.this,
							ViewPagerDemo.class);
					intent.putExtra("iszixuan", isZiXuan);
					startActivity(intent);
				}
			}
		});

		button1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (null != t) {
					for (int i = 0; i < lists.size(); i++) {
						if (lists.get(i).isTiHuan()) {
							isZiXuan = 2;// zixuan 3 guding 1 tihuan 2
							break;
						} else {
							isZiXuan = 1;
						}
					}
				}

				if (l.size() == 0 && isZiXuan == 3) {
					toast(FeastSetActivity.this, "您还没选择菜");

				} else {
					if (isZiXuan == 3) {// zixuan
						System.out.println(LocalStorage.get("LoginStatus")
								+ "^^^^^^^^");
						caiID = new StringBuilder();
						price = 0.0;
						if (!new LoginCheckAlertDialogUtils(
								FeastSetActivity.this).showDialog()) {
							for (int i = 0; i < FeastSetActivity.lists.size(); i++) {
								if (FeastSetActivity.lists.get(i).isiCho()) {
									caiID.append("H"
											+ FeastSetActivity.lists.get(i)
													.getAlbumTitle());
									System.out.println(caiID);
									price = price
											+ Double.parseDouble(FeastSetActivity.lists
													.get(i).getUnitPrice());
								}
							}
							System.out.println("+++++++++++++++" + caiID);
							int end = caiID.length();
							IDcai = caiID.substring(1, end);
							System.out.println("QW" + IDcai);
							shangChuanZiXuan();
						}

					} else if (isZiXuan == 2) {// tihuan
						caiID = new StringBuilder();
						System.out.println(LocalStorage.get("LoginStatus")
								+ "^^^^^^^^");
						if (!new LoginCheckAlertDialogUtils(
								FeastSetActivity.this).showDialog()) {
							for (int i = 0; i < FeastSetActivity.lists.size(); i++) {
								if (FeastSetActivity.lists.get(i).isTiHuan()) {
									caiID.append("H"
											+ FeastSetActivity.lists.get(i)
													.getCover_url_small());
								}
							}
							int end = caiID.length();
							IDcai = caiID.substring(1, end);
							System.out.println("QW" + IDcai);
							shangChuanTiHuan();
						}
					} else if (isZiXuan == 1) {
						System.out.println(LocalStorage.get("LoginStatus")
								+ "^^^^^^^^");
						if (!new LoginCheckAlertDialogUtils(
								FeastSetActivity.this).showDialog()) {
							shangChuan();
						}
					}

				}
			}

		});
		setMoreListener();
	}

	protected void toast(Context context , String info){
		Toast.makeText(context,info,Toast.LENGTH_SHORT).show();
	}

	public void show() {
		if (FeastSetActivity.this.isFinishing()) {
		} else {
			d = new Dialog(FeastSetActivity.this, R.style.loading_dialog);
			v1 = LayoutInflater.from(FeastSetActivity.this).inflate(
					R.layout.dialog, null);// 窗口布局
			d.setContentView(v1);// 把设定好的窗口布局放到dialog中
			d.setCanceledOnTouchOutside(true);// 设定点击窗口空白处取消会话
			p1 = (Button) v1.findViewById(R.id.p);
			n = (Button) v1.findViewById(R.id.n);
			juTiXinXi = (TextView) v1.findViewById(R.id.banben_xinxi);
			tiShi = (TextView) v1.findViewById(R.id.banben_gengxin);
			fuZhi = (TextView) v1.findViewById(R.id.fuzhi_jiantieban);
			juTiXinXi.setText("是否加入订单");
			tiShi.setText("提示");
			p1.setText("确定");
			n.setText("不用");
			p1.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					shangChuanZiXuan();
					d.dismiss();
				}
			});
			n.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					// System.out.println(a);
					d.dismiss();
				}
			});
			d.show();
		}
	}

	TextView juTiXinXi, tiShi, fuZhi;
	Dialog d;
	Button p1;
	Button n;
	View v1;

	private void shangChuanTiHuan() {// ti huan
		tel = (String) LocalStorage.get("UserTel").toString();






		XUtilsHelper xUtilsHelper1 = new XUtilsHelper(this,"BookHandler.ashx?Action=ReplaceSetMeal");
		RequestParams p = new RequestParams();
		p.addBodyParameter("fkCusTel", tel);
		p.addBodyParameter("DishNameIDs", IDcai);
		p.addBodyParameter("Price", Tprice);
		p.addBodyParameter("SetMealCgy", String.valueOf(Integer.parseInt(c) + 11));
		p.addBodyParameter("SetMealIDtoReplace", b);
		p.addBodyParameter("SetMealName", name + ("替换"));
		System.out.println(tel + "+" + IDcai + "+" + price + b);
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
					toast(FeastSetActivity.this,
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
				System.out.println(s);
						// Toast.makeText(ViewPagerDemo.this, tel+"&&&&"+result,
						// Toast.LENGTH_LONG).show();
						isFalse = true;
						ciShu = 0;
						try {
							JSONObject json = new JSONObject(s.trim());

							if ("你还没有订单".equals(json.getString("提示"))) {
								Intent i = new Intent(FeastSetActivity.this,
										ReserveActivity.class);
								i.putExtra("jiaRu", 21);
								startActivity(i);
							} else if ("添加成功".equals(json.getString("提示"))) {
								new Diolg(FeastSetActivity.this, "确定", "null",
										"添加成功", "提示", 9);
							} else if ("添加失败".equals(json.getString("提示"))) {
								new Diolg(FeastSetActivity.this, "确定", "null",
										"添加失败", "提示", 0);
							} else if ("意外错误".equals(json.getString("提示"))) {
								new Diolg(FeastSetActivity.this, "确定", "null",
										"意外错误", "提示", 0);
							} else if ("服务器忙碌".equals(json.getString("提示"))) {
								new Diolg(FeastSetActivity.this, "确定", "null",
										"服务器忙碌", "提示", 0);
							} else if ("你的账户已在另一台手机登录".equals(json
									.getString("提示"))) {
								new Diolg(FeastSetActivity.this, "确定", "返回",
										"你的账户已在另一台手机登录", "提示", 0);
							} else if ("你套餐数量已达上限,可联系客服添加".equals(json
									.getString("提示"))) {
								new Diolg(FeastSetActivity.this, "确定", "返回",
										"你套餐数量已达上限,可联系客服添加", "提示", 9);
							} else if ("这个套餐你已经加过了".equals(json.getString("提示"))) {
								new Diolg(FeastSetActivity.this, "确定", "返回",
										"这个套餐你已经加过了", "提示", 9);
							} else if ("你有一笔要付钱的订单".equals(json.getString("提示"))) {
								new Diolg(FeastSetActivity.this, "确定", "null",
										"你有一笔要付钱的订单", "提示", 9);
							} else if ("替换成功".equals(json.getString("提示"))) {
								new Diolg(FeastSetActivity.this, "确定", "返回",
										"替换成功", "提示", 9);
							} else if ("替换失败".equals(json.getString("提示"))) {
								new Diolg(FeastSetActivity.this, "确定", "返回",
										"替换失败", "提示", 0);
							} else if ("绝不可能出现在这里".equals(json.getString("提示"))) {
								new Diolg(FeastSetActivity.this, "确定", "返回",
										"绝不可能出现在这里", "提示", 0);
							} else if ("失败了,因为你所选的套餐类型和实际类型不一致".equals(json
									.getString("提示"))) {
								new Diolg(FeastSetActivity.this, "确定", "返回",
										"失败了,因为你所选的套餐类型和实际类型不一致", "提示", 0);
							} else if ("不知道你要添加什么".equals(json.getString("提示"))) {
								new Diolg(FeastSetActivity.this, "确定", "返回",
										"不知道你要添加什么", "提示", 0);
							} else {
								Intent in = new Intent(FeastSetActivity.this,
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
//		p.put("fkCusTel", tel);
//		p.put("DishNameIDs", IDcai);
//		p.put("Price", Tprice);
//		p.put("SetMealCgy", (Integer.parseInt(c) + 11));
//		p.put("SetMealIDtoReplace", b);
//		p.put("SetMealName", name + ("替换"));
//		System.out.println(tel + "+" + IDcai + "+" + price + b);
//
//		SmartFruitsRestClient.get("BookHandler.ashx?Action=ReplaceSetMeal", p,
//				new AsyncHttpResponseHandler() {
//
//					@Override
//					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
//						// TODO Auto-generated method stub
//						String result = new String(arg2);
//						System.out.println(result);
//						// Toast.makeText(ViewPagerDemo.this, tel+"&&&&"+result,
//						// Toast.LENGTH_LONG).show();
//						isFalse = true;
//						ciShu = 0;
//						try {
//							JSONObject json = new JSONObject(result.trim());
//
//							if ("你还没有订单".equals(json.getString("提示"))) {
//								Intent i = new Intent(FeastSetActivity.this,
//										ReserveActivity.class);
//								i.putExtra("jiaRu", 21);
//								startActivity(i);
//							} else if ("添加成功".equals(json.getString("提示"))) {
//								new Diolg(FeastSetActivity.this, "确定", "null",
//										"添加成功", "提示", 9);
//							} else if ("添加失败".equals(json.getString("提示"))) {
//								new Diolg(FeastSetActivity.this, "确定", "null",
//										"添加失败", "提示", 0);
//							} else if ("意外错误".equals(json.getString("提示"))) {
//								new Diolg(FeastSetActivity.this, "确定", "null",
//										"意外错误", "提示", 0);
//							} else if ("服务器忙碌".equals(json.getString("提示"))) {
//								new Diolg(FeastSetActivity.this, "确定", "null",
//										"服务器忙碌", "提示", 0);
//							} else if ("你的账户已在另一台手机登录".equals(json
//									.getString("提示"))) {
//								new Diolg(FeastSetActivity.this, "确定", "返回",
//										"你的账户已在另一台手机登录", "提示", 0);
//							} else if ("你套餐数量已达上限,可联系客服添加".equals(json
//									.getString("提示"))) {
//								new Diolg(FeastSetActivity.this, "确定", "返回",
//										"你套餐数量已达上限,可联系客服添加", "提示", 9);
//							} else if ("这个套餐你已经加过了".equals(json.getString("提示"))) {
//								new Diolg(FeastSetActivity.this, "确定", "返回",
//										"这个套餐你已经加过了", "提示", 9);
//							} else if ("你有一笔要付钱的订单".equals(json.getString("提示"))) {
//								new Diolg(FeastSetActivity.this, "确定", "null",
//										"你有一笔要付钱的订单", "提示", 9);
//							} else if ("替换成功".equals(json.getString("提示"))) {
//								new Diolg(FeastSetActivity.this, "确定", "返回",
//										"替换成功", "提示", 9);
//							} else if ("替换失败".equals(json.getString("提示"))) {
//								new Diolg(FeastSetActivity.this, "确定", "返回",
//										"替换失败", "提示", 0);
//							} else if ("绝不可能出现在这里".equals(json.getString("提示"))) {
//								new Diolg(FeastSetActivity.this, "确定", "返回",
//										"绝不可能出现在这里", "提示", 0);
//							} else if ("失败了,因为你所选的套餐类型和实际类型不一致".equals(json
//									.getString("提示"))) {
//								new Diolg(FeastSetActivity.this, "确定", "返回",
//										"失败了,因为你所选的套餐类型和实际类型不一致", "提示", 0);
//							} else if ("不知道你要添加什么".equals(json.getString("提示"))) {
//								new Diolg(FeastSetActivity.this, "确定", "返回",
//										"不知道你要添加什么", "提示", 0);
//							} else {
//								Intent in = new Intent(FeastSetActivity.this,
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
//							Toast.makeText(FeastSetActivity.this,
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

	private void shangChuan() { // guding
		tel = (String) LocalStorage.get("UserTel").toString();



		XUtilsHelper xUtilsHelper1 = new XUtilsHelper(this,"BookHandler.ashx?Action=AddIntoOrder&Type=Type_SetMeal");
		RequestParams p = new RequestParams();

		p.addBodyParameter("fkCusTel", tel);
		// p.put("CustPhyAddr", StartActivity.strUniqueId);
		// Toast.makeText(ViewPagerDemo.this, tel, Toast.LENGTH_LONG).show();
		System.out.println(tel + "%%%%%%%%%%%%%%%%%%%%%%");
		p.addBodyParameter("SetMealID", b);
		p.addBodyParameter("SetMealCgy", c);


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
					toast(FeastSetActivity.this,
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

						System.out.println(s);
						// Toast.makeText(ViewPagerDemo.this, tel+"&&&&"+result,
						// Toast.LENGTH_LONG).show();
						isFalse = true;
						ciShu = 0;
						try {
							JSONObject json = new JSONObject(s.trim());
							// Toast.makeText(ViewPagerDemo.this, result,
							// Toast.LENGTH_LONG).show();
							if ("你还没有订单".equals(json.getString("提示"))) {
								Intent i = new Intent(FeastSetActivity.this,
										ReserveActivity.class);
								i.putExtra("jiaRu", 21);
								startActivity(i);
							} else if ("添加成功".equals(json.getString("提示"))) {
								new Diolg(FeastSetActivity.this, "确定", "null",
										"添加成功", "提示", 9);
							} else if ("添加失败".equals(json.getString("提示"))) {
								new Diolg(FeastSetActivity.this, "确定", "null",
										"添加失败", "提示", 0);
							} else if ("你有一笔要付钱的订单".equals(json.getString("提示"))) {
								new Diolg(FeastSetActivity.this, "确定", "null",
										"你有一笔要付钱的订单", "提示", 9);
							} else if ("意外错误".equals(json.getString("提示"))) {
								new Diolg(FeastSetActivity.this, "确定", "null",
										"意外错误", "提示", 0);
							} else if ("服务器忙碌".equals(json.getString("提示"))) {
								new Diolg(FeastSetActivity.this, "确定", "null",
										"服务器忙碌", "提示", 0);
							} else if ("你的账户已在另一台手机登录".equals(json
									.getString("提示"))) {
								new Diolg(FeastSetActivity.this, "确定", "返回",
										"你的账户已在另一台手机登录", "提示", 0);
							} else if ("你套餐数量已达上限,可联系客服添加".equals(json
									.getString("提示"))) {
								new Diolg(FeastSetActivity.this, "确定", "返回",
										"你套餐数量已达上限,可联系客服添加", "提示", 9);
							} else if ("这个套餐你已经加过了".equals(json.getString("提示"))) {
								new Diolg(FeastSetActivity.this, "确定", "返回",
										"这个套餐你已经加过了", "提示", 9);
							} else if ("替换成功".equals(json.getString("提示"))) {
								new Diolg(FeastSetActivity.this, "确定", "返回",
										"替换成功", "提示", 9);
							} else if ("替换失败".equals(json.getString("提示"))) {
								new Diolg(FeastSetActivity.this, "确定", "返回",
										"替换失败", "提示", 0);
							} else if ("绝不可能出现在这里".equals(json.getString("提示"))) {
								new Diolg(FeastSetActivity.this, "确定", "返回",
										"绝不可能出现在这里", "提示", 0);
							} else if ("失败了,因为你所选的套餐类型和实际类型不一致".equals(json
									.getString("提示"))) {
								new Diolg(FeastSetActivity.this, "确定", "返回",
										"失败了,因为你所选的套餐类型和实际类型不一致", "提示", 0);
							} else if ("不知道你要添加什么".equals(json.getString("提示"))) {
								new Diolg(FeastSetActivity.this, "确定", "返回",
										"不知道你要添加什么", "提示", 0);
							} else {
								Intent in = new Intent(FeastSetActivity.this,
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
//		p.put("fkCusTel", tel);
//		// p.put("CustPhyAddr", StartActivity.strUniqueId);
//		// Toast.makeText(ViewPagerDemo.this, tel, Toast.LENGTH_LONG).show();
//		System.out.println(tel + "%%%%%%%%%%%%%%%%%%%%%%");
//		p.put("SetMealID", b);
//		p.put("SetMealCgy", c);
//		SmartFruitsRestClient.get(
//				"BookHandler.ashx?Action=AddIntoOrder&Type=Type_SetMeal", p,
//				new AsyncHttpResponseHandler() {
//
//					@Override
//					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
//						// TODO Auto-generated method stub
//						String result = new String(arg2);
//						System.out.println(result);
//						// Toast.makeText(ViewPagerDemo.this, tel+"&&&&"+result,
//						// Toast.LENGTH_LONG).show();
//						isFalse = true;
//						ciShu = 0;
//						try {
//							JSONObject json = new JSONObject(result.trim());
//							// Toast.makeText(ViewPagerDemo.this, result,
//							// Toast.LENGTH_LONG).show();
//							if ("你还没有订单".equals(json.getString("提示"))) {
//								Intent i = new Intent(FeastSetActivity.this,
//										ReserveActivity.class);
//								i.putExtra("jiaRu", 21);
//								startActivity(i);
//							} else if ("添加成功".equals(json.getString("提示"))) {
//								new Diolg(FeastSetActivity.this, "确定", "null",
//										"添加成功", "提示", 9);
//							} else if ("添加失败".equals(json.getString("提示"))) {
//								new Diolg(FeastSetActivity.this, "确定", "null",
//										"添加失败", "提示", 0);
//							} else if ("你有一笔要付钱的订单".equals(json.getString("提示"))) {
//								new Diolg(FeastSetActivity.this, "确定", "null",
//										"你有一笔要付钱的订单", "提示", 9);
//							} else if ("意外错误".equals(json.getString("提示"))) {
//								new Diolg(FeastSetActivity.this, "确定", "null",
//										"意外错误", "提示", 0);
//							} else if ("服务器忙碌".equals(json.getString("提示"))) {
//								new Diolg(FeastSetActivity.this, "确定", "null",
//										"服务器忙碌", "提示", 0);
//							} else if ("你的账户已在另一台手机登录".equals(json
//									.getString("提示"))) {
//								new Diolg(FeastSetActivity.this, "确定", "返回",
//										"你的账户已在另一台手机登录", "提示", 0);
//							} else if ("你套餐数量已达上限,可联系客服添加".equals(json
//									.getString("提示"))) {
//								new Diolg(FeastSetActivity.this, "确定", "返回",
//										"你套餐数量已达上限,可联系客服添加", "提示", 9);
//							} else if ("这个套餐你已经加过了".equals(json.getString("提示"))) {
//								new Diolg(FeastSetActivity.this, "确定", "返回",
//										"这个套餐你已经加过了", "提示", 9);
//							} else if ("替换成功".equals(json.getString("提示"))) {
//								new Diolg(FeastSetActivity.this, "确定", "返回",
//										"替换成功", "提示", 9);
//							} else if ("替换失败".equals(json.getString("提示"))) {
//								new Diolg(FeastSetActivity.this, "确定", "返回",
//										"替换失败", "提示", 0);
//							} else if ("绝不可能出现在这里".equals(json.getString("提示"))) {
//								new Diolg(FeastSetActivity.this, "确定", "返回",
//										"绝不可能出现在这里", "提示", 0);
//							} else if ("失败了,因为你所选的套餐类型和实际类型不一致".equals(json
//									.getString("提示"))) {
//								new Diolg(FeastSetActivity.this, "确定", "返回",
//										"失败了,因为你所选的套餐类型和实际类型不一致", "提示", 0);
//							} else if ("不知道你要添加什么".equals(json.getString("提示"))) {
//								new Diolg(FeastSetActivity.this, "确定", "返回",
//										"不知道你要添加什么", "提示", 0);
//							} else {
//								Intent in = new Intent(FeastSetActivity.this,
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
//							Toast.makeText(FeastSetActivity.this,
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

	private void shangChuanZiXuan() {// zixuan
		tel = (String) LocalStorage.get("UserTel").toString();


		XUtilsHelper xUtilsHelper1 = new XUtilsHelper(this,"BookHandler.ashx?Action=AddIntoOrder&Type=Type_CustomSetMeal");
		RequestParams p = new RequestParams();
		p.addBodyParameter("fkCusTel", tel);
		p.addBodyParameter("DishNameIDs", IDcai);
		// p.put("CustPhyAddr", StartActivity.strUniqueId);
		p.addBodyParameter("Price",String.valueOf(price));
		p.addBodyParameter("SetMealCgy", "2");
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
					toast(FeastSetActivity.this,
							getString(R.string.conn_failed));

						}
						;

						if (isFalse) {
							shangChuanZiXuan();
							ciShu++;
						}
			}

			@Override
			public void onNext(String s) {

						System.out.println(s);
						// Toast.makeText(ViewPagerDemo.this, tel+"&&&&"+result,
						// Toast.LENGTH_LONG).show();
						isFalse = true;
						ciShu = 0;
						try {
							JSONObject json = new JSONObject(s.trim());

							if ("你还没有订单".equals(json.getString("提示"))) {
								Intent i = new Intent(FeastSetActivity.this,
										ReserveActivity.class);
								i.putExtra("jiaRu", 21);
								startActivity(i);
							} else if ("添加成功".equals(json.getString("提示"))) {
								new Diolg(FeastSetActivity.this, "确定", "null",
										"添加成功", "提示", 9);
							} else if ("添加失败".equals(json.getString("提示"))) {
								new Diolg(FeastSetActivity.this, "确定", "null",
										"添加失败", "提示", 0);
							} else if ("意外错误".equals(json.getString("提示"))) {
								new Diolg(FeastSetActivity.this, "确定", "null",
										"意外错误", "提示", 0);
							} else if ("服务器忙碌".equals(json.getString("提示"))) {
								new Diolg(FeastSetActivity.this, "确定", "null",
										"服务器忙碌", "提示", 0);
							} else if ("你的账户已在另一台手机登录".equals(json
									.getString("提示"))) {
								new Diolg(FeastSetActivity.this, "确定", "返回",
										"你的账户已在另一台手机登录", "提示", 9);
							} else if ("你套餐数量已达上限,可联系客服添加".equals(json
									.getString("提示"))) {
								new Diolg(FeastSetActivity.this, "确定", "返回",
										"你套餐数量已达上限,可联系客服添加", "提示", 9);
							} else if ("这个套餐你已经加过了".equals(json.getString("提示"))) {
								new Diolg(FeastSetActivity.this, "确定", "返回",
										"这个套餐你已经加过了", "提示", 0);
							} else if ("替换成功".equals(json.getString("提示"))) {
								new Diolg(FeastSetActivity.this, "确定", "返回",
										"替换成功", "提示", 9);
							} else if ("替换失败".equals(json.getString("提示"))) {
								new Diolg(FeastSetActivity.this, "确定", "返回",
										"替换失败", "提示", 0);
							} else if ("你有一笔要付钱的订单".equals(json.getString("提示"))) {
								new Diolg(FeastSetActivity.this, "确定", "null",
										"你有一笔要付钱的订单", "提示", 9);
							} else if ("绝不可能出现在这里".equals(json.getString("提示"))) {
								new Diolg(FeastSetActivity.this, "确定", "返回",
										"绝不可能出现在这里", "提示", 0);
							} else if ("失败了,因为你所选的套餐类型和实际类型不一致".equals(json
									.getString("提示"))) {
								new Diolg(FeastSetActivity.this, "确定", "返回",
										"失败了,因为你所选的套餐类型和实际类型不一致", "提示", 0);
							} else if ("不知道你要添加什么".equals(json.getString("提示"))) {
								new Diolg(FeastSetActivity.this, "确定", "返回",
										"不知道你要添加什么", "提示", 0);
							} else {
								Intent in = new Intent(FeastSetActivity.this,
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
//		p.put("fkCusTel", tel);
//		p.put("DishNameIDs", IDcai);
//		// p.put("CustPhyAddr", StartActivity.strUniqueId);
//		p.put("Price", price);
//		p.put("SetMealCgy", 2);
//		System.out.println(tel + "+" + IDcai + "+" + price + "+" + 6);
//		SmartFruitsRestClient.get(
//				"BookHandler.ashx?Action=AddIntoOrder&Type=Type_CustomSetMeal",
//				p, new AsyncHttpResponseHandler() {
//
//					@Override
//					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
//						// TODO Auto-generated method stub
//						String result = new String(arg2);
//						System.out.println(result);
//						// Toast.makeText(ViewPagerDemo.this, tel+"&&&&"+result,
//						// Toast.LENGTH_LONG).show();
//						isFalse = true;
//						ciShu = 0;
//						try {
//							JSONObject json = new JSONObject(result.trim());
//
//							if ("你还没有订单".equals(json.getString("提示"))) {
//								Intent i = new Intent(FeastSetActivity.this,
//										ReserveActivity.class);
//								i.putExtra("jiaRu", 21);
//								startActivity(i);
//							} else if ("添加成功".equals(json.getString("提示"))) {
//								new Diolg(FeastSetActivity.this, "确定", "null",
//										"添加成功", "提示", 9);
//							} else if ("添加失败".equals(json.getString("提示"))) {
//								new Diolg(FeastSetActivity.this, "确定", "null",
//										"添加失败", "提示", 0);
//							} else if ("意外错误".equals(json.getString("提示"))) {
//								new Diolg(FeastSetActivity.this, "确定", "null",
//										"意外错误", "提示", 0);
//							} else if ("服务器忙碌".equals(json.getString("提示"))) {
//								new Diolg(FeastSetActivity.this, "确定", "null",
//										"服务器忙碌", "提示", 0);
//							} else if ("你的账户已在另一台手机登录".equals(json
//									.getString("提示"))) {
//								new Diolg(FeastSetActivity.this, "确定", "返回",
//										"你的账户已在另一台手机登录", "提示", 9);
//							} else if ("你套餐数量已达上限,可联系客服添加".equals(json
//									.getString("提示"))) {
//								new Diolg(FeastSetActivity.this, "确定", "返回",
//										"你套餐数量已达上限,可联系客服添加", "提示", 9);
//							} else if ("这个套餐你已经加过了".equals(json.getString("提示"))) {
//								new Diolg(FeastSetActivity.this, "确定", "返回",
//										"这个套餐你已经加过了", "提示", 0);
//							} else if ("替换成功".equals(json.getString("提示"))) {
//								new Diolg(FeastSetActivity.this, "确定", "返回",
//										"替换成功", "提示", 9);
//							} else if ("替换失败".equals(json.getString("提示"))) {
//								new Diolg(FeastSetActivity.this, "确定", "返回",
//										"替换失败", "提示", 0);
//							} else if ("你有一笔要付钱的订单".equals(json.getString("提示"))) {
//								new Diolg(FeastSetActivity.this, "确定", "null",
//										"你有一笔要付钱的订单", "提示", 9);
//							} else if ("绝不可能出现在这里".equals(json.getString("提示"))) {
//								new Diolg(FeastSetActivity.this, "确定", "返回",
//										"绝不可能出现在这里", "提示", 0);
//							} else if ("失败了,因为你所选的套餐类型和实际类型不一致".equals(json
//									.getString("提示"))) {
//								new Diolg(FeastSetActivity.this, "确定", "返回",
//										"失败了,因为你所选的套餐类型和实际类型不一致", "提示", 0);
//							} else if ("不知道你要添加什么".equals(json.getString("提示"))) {
//								new Diolg(FeastSetActivity.this, "确定", "返回",
//										"不知道你要添加什么", "提示", 0);
//							} else {
//								Intent in = new Intent(FeastSetActivity.this,
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
//							Toast.makeText(FeastSetActivity.this,
//									getString(R.string.conn_failed),
//									Toast.LENGTH_SHORT).show();
//						}
//						;
//
//						if (isFalse) {
//							shangChuanZiXuan();
//							ciShu++;
//						}
//
//					}
//				});
	}

	private String tel;
	@ViewInject(R.id.iv_more)
	private ImageView iv_more;

	private void setMoreListener() {

		iv_more.setOnClickListener(new IvListener(iv_more,
				FeastSetActivity.this, 0));
	}

	private void ZiXuan() {
		ld.showDialog();




		XUtilsHelper xUtilsHelper1 = new XUtilsHelper(this,"MenuHandler.ashx?Action=GetAllDish");
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
							ld.closeDialog();
					toast(FeastSetActivity.this,
							getString(R.string.conn_failed) + e);

						}
						;
						if (isFalse) {
							ZiXuan();
							ciShu++;
						}
			}

			@Override
			public void onNext(String s) {
				ld.closeDialog();
						son news;
						father newa;

						System.out.println("F" + s);
						try {
							JSONObject json = new JSONObject(s.trim());
							JSONArray configlist = json.getJSONArray("所有菜");
							JSONArray cuisinelist = json.getJSONArray("菜系");

							if (configlist.length() > 0) {
								mCache.put("喜宴套餐详情", s, 60 * 10);
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
									String ImageUrl = tJson
											.getString("ImageUrl");
									news.setAlbumTitle(tJson
											.getString("DishID"));
									news.setJiage(tJson.getString("Norm"));
									news.setDanwei(tJson.getString("NormUnit"));
									String price = tJson.getString("UnitPrice");
									if ("".equals(price) || price == null) {
										price = "0.0000";
									}
									int a = price.lastIndexOf(".");
									price = price.substring(0, a);
									news.setUnitPrice(price);
									news.setImageUrl(ImageUrl);
									news.setiCho(false);
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
									newa.setImage(0);
									fatlist.add(newa);

								}

							}
							main();
							for (int i = 0; i < lists.size(); i++) {
								if (fatlist.get(0).getId()
										.equals(lists.get(i).getId())) {
									newList.add(lists.get(i));
								}
							}

						} catch (JSONException e) {
							e.printStackTrace();
							toast(FeastSetActivity.this,
									e.getMessage());

						}

			}
		});









//		RequestParams reqParams = new RequestParams();
//		SmartFruitsRestClient.get("MenuHandler.ashx?Action=GetAllDish",
//				reqParams, new AsyncHttpResponseHandler() {
//					// http请求成功返回数据
//					@Override
//					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
//						// TODO Auto-generated method stub
//						ld.closeDialog();
//						son news;
//						father newa;
//						String result = new String(arg2);
//						System.out.println("F" + result);
//						try {
//							JSONObject json = new JSONObject(result.trim());
//							JSONArray configlist = json.getJSONArray("所有菜");
//							JSONArray cuisinelist = json.getJSONArray("菜系");
//
//							if (configlist.length() > 0) {
//								mCache.put("喜宴套餐详情", result, 60 * 10);
//							}
//
//							if (configlist != null) {
//								int size = configlist.length();
//								JSONObject tJson = null;
//								lists.clear();
//								for (int i = 0; i < size; i++) {
//									news = new son();
//									tJson = configlist.getJSONObject(i);
//									String secondId = tJson
//											.getString("DishCgy");
//									news.setId(secondId);
//									String lessonpId = tJson
//											.getString("DishName");
//									news.setName(lessonpId);
//									String ImageUrl = tJson
//											.getString("ImageUrl");
//									news.setAlbumTitle(tJson
//											.getString("DishID"));
//									news.setJiage(tJson.getString("Norm"));
//									news.setDanwei(tJson.getString("NormUnit"));
//									String price = tJson.getString("UnitPrice");
//									if ("".equals(price) || price == null) {
//										price = "0.0000";
//									}
//									int a = price.lastIndexOf(".");
//									price = price.substring(0, a);
//									news.setUnitPrice(price);
//									news.setImageUrl(ImageUrl);
//									news.setiCho(false);
//									lists.add(news);
//
//								}
//							}
//							if (cuisinelist != null) {
//								int sizes = cuisinelist.length();
//								JSONObject tJsona = null;
//								fatlist.clear();
//								for (int i = 0; i < sizes; i++) {
//									newa = new father();
//									tJsona = cuisinelist.getJSONObject(i);
//									String seconId = tJsona
//											.getString("pkDishCgyID");
//
//									newa.setId(seconId);
//									String lessonId = tJsona.getString("Name");
//									newa.setName(lessonId);
//									newa.setImage(0);
//									fatlist.add(newa);
//
//								}
//
//							}
//							main();
//							for (int i = 0; i < lists.size(); i++) {
//								if (fatlist.get(0).getId()
//										.equals(lists.get(i).getId())) {
//									newList.add(lists.get(i));
//								}
//							}
//
//						} catch (JSONException e) {
//							e.printStackTrace();
//							Toast.makeText(FeastSetActivity.this,
//									e.getMessage(), Toast.LENGTH_SHORT).show();
//						}
//
//						return;
//					}
//
//					@Override
//					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
//							Throwable arg3) {
//						// TODO Auto-generated method stub 失败的回调函数
//						System.out.println(arg3 + "asdfgdhgsa");
//						if (ciShu >= 3) {
//							isFalse = false;
//							ld.closeDialog();
//							Toast.makeText(FeastSetActivity.this,
//									getString(R.string.conn_failed) + arg3,
//									Toast.LENGTH_SHORT).show();
//						}
//						;
//						if (isFalse) {
//							ZiXuan();
//							ciShu++;
//						}
//
//					}
//				});
	}

	private void SendClick(final String ID) {
		ld.showDialog();


		XUtilsHelper xUtilsHelper1 = new XUtilsHelper(this,"MenuHandler.ashx?Action=getMenuByID");
		RequestParams requestParams = new RequestParams();
		requestParams.addBodyParameter("SetMealID", ID);
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
				ld.closeDialog();

						if (ciShu >= 3) {
							isFalse = false;
toast(FeastSetActivity.this,
		getString(R.string.conn_failed) + e);

						}
						;
						if (isFalse) {
							SendClick(ID);
							ciShu++;
						}
			}

			@Override
			public void onNext(String s) {
				ld.closeDialog();
						son news;

						father newa;

						System.out.println("F" + s);
						try {
							JSONObject json = new JSONObject(s.trim());
							JSONArray configlist = json.getJSONArray("菜单");
							JSONArray cuisinelist = json.getJSONArray("菜系");

							if (configlist.length() > 0) {
								mCache.put("喜宴套餐详情", s, 60 * 60 * 24);
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
									news.setTifengliang(tJson
											.getString("ReplaceNorm"));
									news.setTidanwei(tJson
											.getString("ReplaceNormUnit"));
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
			}
		});














//		RequestParams reqParams = new RequestParams();
//		reqParams.put("SetMealID", ID);
//
//		// 传输数据过去
//		SmartFruitsRestClient.post("MenuHandler.ashx?Action=getMenuByID",
//				reqParams, new AsyncHttpResponseHandler() {
//					// http请求成功返回数据
//					@Override
//					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
//						// TODO Auto-generated method stub
//						ld.closeDialog();
//						son news;
//
//						father newa;
//						String result = new String(arg2);
//						System.out.println("F" + result);
//						try {
//							JSONObject json = new JSONObject(result.trim());
//							JSONArray configlist = json.getJSONArray("菜单");
//							JSONArray cuisinelist = json.getJSONArray("菜系");
//
//							if (configlist.length() > 0) {
//								mCache.put("喜宴套餐详情", result, 60 * 60 * 24);
//							}
//
//							if (configlist != null) {
//								int size = configlist.length();
//								JSONObject tJson = null;
//								lists.clear();
//								for (int i = 0; i < size; i++) {
//									news = new son();
//									tJson = configlist.getJSONObject(i);
//									String secondId = tJson
//											.getString("DishCgy");
//									news.setId(secondId);
//									String lessonpId = tJson
//											.getString("DishName");
//									news.setName(lessonpId);
//									news.setCover_url_small(tJson
//											.getString("DishID"));
//									String ImageUrl = tJson
//											.getString("ImageUrl");
//									String price = tJson.getString("UnitPrice");
//									if ("".equals(price) || price == null) {
//										price = "0.0000";
//									}
//									int a = price.lastIndexOf(".");
//									price = price.substring(0, a);
//									// double p = Double.parseDouble(price)*1.1;
//									// String p2 = df.format(price)+"";
//									// System.out.println(p2+"++++++++++++++");
//									news.setUnitPrice(price);
//									news.setImageUrl(ImageUrl);
//									news.setTifengliang(tJson
//											.getString("ReplaceNorm"));
//									news.setTidanwei(tJson
//											.getString("ReplaceNormUnit"));
//									news.setJiage(tJson.getString("Norm"));
//									news.setDanwei(tJson.getString("NormUnit"));
//									if ("".equals(tJson.getString("ReplaceID"))) {
//										news.setTi(false);
//									} else {
//										news.setTiID(tJson
//												.getString("ReplaceID"));
//										news.setTiName(tJson
//												.getString("ReplaceName"));
//										news.setTiTuPian(tJson
//												.getString("ReplaceImageUrl"));
//										news.setTi(true);
//										news.setTiHuan(false);
//									}
//									;
//									lists.add(news);
//
//								}
//							}
//							if (cuisinelist != null) {
//								int sizes = cuisinelist.length();
//								JSONObject tJsona = null;
//								fatlist.clear();
//								for (int i = 0; i < sizes; i++) {
//									newa = new father();
//									tJsona = cuisinelist.getJSONObject(i);
//									String seconId = tJsona
//											.getString("pkDishCgyID");
//
//									newa.setId(seconId);
//									String lessonId = tJsona.getString("Name");
//									newa.setName(lessonId);
//									fatlist.add(newa);
//
//								}
//
//							}
//							for (int i = 0; i < lists.size(); i++) {
//								if (fatlist.get(0).getId()
//										.equals(lists.get(i).getId())) {
//									newList1.add(lists.get(i));
//								}
//							}
//
//							mains();
//						} catch (JSONException e) {
//							e.printStackTrace();
//							// Toast.makeText(FeastSetActivity.this,
//							// e.getMessage(), Toast.LENGTH_SHORT).show();
//						}
//
//						return;
//					}
//
//					@Override
//					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
//							Throwable arg3) {
//						// TODO Auto-generated method stub 失败的回调函数
//						ld.closeDialog();
//						System.out.println(arg3 + "asdfgdhgsa");
//						if (ciShu >= 3) {
//							isFalse = false;
//
//							Toast.makeText(FeastSetActivity.this,
//									getString(R.string.conn_failed) + arg3,
//									Toast.LENGTH_SHORT).show();
//						}
//						;
//						if (isFalse) {
//							SendClick(ID);
//							ciShu++;
//						}
//
//					}
//				});
	}

	private boolean isFalse = true;
	private int ciShu = 0;
	private int index = 0;

	private void mains() {


		System.out.println(lv2);
		cf = new CommonAdapter<father>(FeastSetActivity.this, fatlist,
				R.layout.item_zitis) {



			@Override
			public void convert(com.example.administrator.list.ViewHolder helper, AnLi item) {

			}

			@Override
			public void convert(com.example.administrator.list.ViewHolder helper, father item) {
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
		m1 = new MyAdapter1();
		lv2.setAdapter(m1);
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
				m1.notifyDataSetChanged();
				lv2.setSelection(0);
			}
		});

		// lv2.setOnItemClickListener(new OnItemClickListener() {
		//
		// @Override
		// public void onItemClick(AdapterView<?> parent, View view,
		// int position, long id) {
		//
		//
		// }
		// });
		image_iamge.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				quanp = false;
				image_iamge.setVisibility(View.GONE);
				lv2.setVisibility(View.VISIBLE);
			}
		});

	}

	// 将显示的显示到这里
	private void main() {
		lv1 = (ListView) findViewById(R.id.lv1s);
		lv2 = (ListView) findViewById(R.id.lv2);
		System.out.println(lv2);
		cf = new CommonAdapter<father>(FeastSetActivity.this, fatlist,
				R.layout.item_zij) {



			@Override
			public void convert(com.example.administrator.list.ViewHolder helper, father item) {
				// TODO Auto-generated method stub
				helper.setText(R.id.zi_xuan_zi, item.getName());
				helper.setTextColor(R.id.zi_xuan_zi, Color.BLACK,
						Color.parseColor("#adadad"), index);
				helper.setBack(index, R.id.zi_xuan_zi);
				helper.setText(R.id.zi_xuan_shu, item.getImage() + "");
				helper.setZiXuan(R.id.zi_xuan_shu, index);
			}

			@Override
			public void convert(com.example.administrator.list.ViewHolder helper, AnLi item) {

			}
		};
		lv1.setAdapter(cf);
		my = new MyAdapter();
		lv2.setAdapter(my);
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
				newList.clear();
				for (int i = 0; i < lists.size(); i++) {
					if (fatlist.get(position).getId()
							.equals(lists.get(i).getId())) {
						newList.add(lists.get(i));
						// System.out.println(lists.get(i).isiCho());
					}
				}
				my.notifyDataSetChanged();
				lv2.setSelection(0);
			}
		});

		// lv2.setOnItemClickListener(new OnItemClickListener() {
		//
		// @Override
		// public void onItemClick(AdapterView<?> parent, View view,
		// int position, long id) {
		//
		// quanp = true;
		// image_iamge.setVisibility(View.VISIBLE);
		// lv2.setVisibility(View.GONE);
		// Load.imageLoader.displayImage(newList.get(position)
		// .getImageUrl(), image_iamge, Load.options);
		// }
		// });
		image_iamge.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				quanp = false;
				image_iamge.setVisibility(View.GONE);
				lv2.setVisibility(View.VISIBLE);
			}
		});

	}

	private MyAdapter my;

	private class MyAdapter extends BaseAdapter {
		LayoutInflater li;

		MyAdapter() {
			li = LayoutInflater.from(FeastSetActivity.this);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return newList.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		public void setGX(View v1, int arg0) {
			v = (ViewHolde) v1.getTag();
			if (newList.get(arg0).isiCho()) {
				if (fatlist.get(0).getId().equals(newList.get(arg0).getId())) {
					fatlist.get(0).setImage((fatlist.get(0).getImage() - 1));
				} else {
					fatlist.get(1).setImage((fatlist.get(1).getImage() - 1));
				}
				v.goux.setImageResource(R.mipmap.checkbox_normal);
				newList.get(arg0).setiCho(false);
				TotalPrice = TotalPrice
						- Integer.parseInt(newList.get(arg0).getUnitPrice());
				jiaGe.setText("" + TotalPrice);
				cf.notifyDataSetChanged();
				l.remove(newList.get(arg0));
			} else {
				if (fatlist.get(0).getId().equals(newList.get(arg0).getId())) {
					fatlist.get(0).setImage((fatlist.get(0).getImage() + 1));
				} else {
					fatlist.get(1).setImage((fatlist.get(1).getImage() + 1));
				}
				v.goux.setImageResource(R.mipmap.checkbox_pressed);
				newList.get(arg0).setiCho(true);
				System.out.println("ASD"
						+ Double.parseDouble(newList.get(arg0).getUnitPrice()));
				TotalPrice = TotalPrice
						+ Integer.parseInt(newList.get(arg0).getUnitPrice());
				jiaGe.setText("" + TotalPrice);
				cf.notifyDataSetChanged();
				l.add(newList.get(arg0));
			}
		}

		@Override
		public View getView(final int arg0, View arg1, ViewGroup arg2) {
			if (arg1 == null) {
				arg1 = li.inflate(R.layout.item_caixixiangqing_shipei, null);
				v = new ViewHolde();
				v.image = (ImageView) arg1.findViewById(R.id.cai_tupian);
				v.imgName = (TextView) arg1.findViewById(R.id.cai_mingzi);
				v.jiaGess = (TextView) arg1.findViewById(R.id.cai_jiage);
				v.goux = (ImageView) arg1.findViewById(R.id.cai_gouxuan);
				arg1.setTag(v);
			} else {
				v = (ViewHolde) arg1.getTag();
			}
			if (newList.get(arg0).isiCho()) {
				v.goux.setImageResource(R.mipmap.checkbox_pressed);
			} else {
				v.goux.setImageResource(R.mipmap.checkbox_normal);
			}

			if (!TextUtils.isEmpty(newList.get(arg0).getJiage())) {
				if ("g".equals(newList.get(arg0).getDanwei())) {
					v.jiaGess.setText("￥" + newList.get(arg0).getUnitPrice()
							+ "/约" + newList.get(arg0).getJiage()
							+ newList.get(arg0).getDanwei());
				} else {
					v.jiaGess.setText("￥" + newList.get(arg0).getUnitPrice()
							+ "/" + newList.get(arg0).getJiage()
							+ newList.get(arg0).getDanwei());
				}

			} else {
				v.jiaGess.setText("￥" + newList.get(arg0).getUnitPrice());
			}

			v.imgName.setText(newList.get(arg0).getName());
			Load.imageLoader.displayImage(newList.get(arg0).getImageUrl(),
					v.image, Load.options);
			click(v, arg1, arg0);

			v.image.setOnClickListener(new OnClickListener() {// 点击放大图片

				@Override
				public void onClick(View v) {
					quanp = true;
					image_iamge.setVisibility(View.VISIBLE);
					lv2.setVisibility(View.GONE);

					Load.imageLoader.displayImage(newList.get(arg0)
							.getImageUrl(), image_iamge, Load.options);

				}
			});

			return arg1;
		}

		public void click(ViewHolde v2, final View arg1, final int arg0) {
			v2.goux.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg) {
					// TODO Auto-generated method stub
					// View view = lv2.getChildAt(arg0);
					// System.out.println(lv2);
					// System.out.println(view+"GFH"+arg0);
					setGX(arg1, arg0);
				}
			});
			v2.imgName.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg) {
					// TODO Auto-generated method stub
					setGX(arg1, arg0);
				}
			});
			v2.jiaGess.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg) {
					// TODO Auto-generated method stub
					setGX(arg1, arg0);
				}
			});
		}

	}

	private MyAdapter1 m1;

	private class MyAdapter1 extends BaseAdapter {
		LayoutInflater li;

		MyAdapter1() {
			li = LayoutInflater.from(FeastSetActivity.this);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return newList1.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		@Override
		public View getView(final int arg0, View arg1, ViewGroup arg2) {
			if (arg1 == null) {
				System.out.println("+++++++");
				arg1 = li.inflate(R.layout.item_caixixiangqing_shipei, null);
				v = new ViewHolde();
				v.image = (ImageView) arg1.findViewById(R.id.cai_tupian);
				v.imgName = (TextView) arg1.findViewById(R.id.cai_mingzi);
				v.jiaGess = (TextView) arg1.findViewById(R.id.cai_jiage);
				v.goux = (ImageView) arg1.findViewById(R.id.cai_gouxuan);
				v.tih = (TextView) arg1.findViewById(R.id.xiyan_tihuan);
				arg1.setTag(v);
			} else {
				v = (ViewHolde) arg1.getTag();
				System.out.println(arg0);
			}
			v.imgName.setText(newList1.get(arg0).getName());
			Load.imageLoader.displayImage(newList1.get(arg0).getImageUrl(),
					v.image, Load.options);

			v.image.setOnClickListener(new OnClickListener() {// 点击放大图片

				@Override
				public void onClick(View v) {
					final Dialog dialog = new Dialog(FeastSetActivity.this,
							android.R.style.Theme_Black_NoTitleBar_Fullscreen);
					ImageView imgView = getView(arg0);
					dialog.setContentView(imgView);
					dialog.show();

					// 点击图片消失
					imgView.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							dialog.dismiss();
						}
					});

				}
			});

			if (name.contains("单餐")) {
				if (!TextUtils.isEmpty(newList1.get(arg0).getJiage())) {
					v.jiaGess.setText("约" + newList1.get(arg0).getJiage()
							+ newList1.get(arg0).getDanwei());
				} else {
					v.jiaGess.setText("");
				}

			} else if (!TextUtils.isEmpty(newList1.get(arg0).getJiage())) {
				if ("g".equals(newList1.get(arg0).getDanwei())) {
					v.jiaGess.setText("约" + newList1.get(arg0).getJiage()
							+ newList1.get(arg0).getDanwei());
				} else {
					v.jiaGess.setText(newList1.get(arg0).getJiage()
							+ newList1.get(arg0).getDanwei());
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
			click(v, arg1, arg0);
			return arg1;
		}

		private ImageView getView(int arg0) {
			ImageView imgView = new ImageView(FeastSetActivity.this);
			imgView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.MATCH_PARENT));
			Load.imageLoader.displayImage(newList1.get(arg0).getImageUrl(),
					imgView, Load.options);
			return imgView;
		}

		public void click(final ViewHolde v2, final View arg1, final int arg0) {
			v2.tih.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg) {
					// TODO Auto-generated method stub
					if (newList1.get(arg0).isTiHuan()) {
						newList1.get(arg0).setTiHuan(false);
					} else {
						newList1.get(arg0).setTiHuan(true);
					}
					String id;
					String name;
					String tuPian;
					String fenliang;
					String danwei;
					id = newList1.get(arg0).getCover_url_small();
					name = newList1.get(arg0).getName();
					tuPian = newList1.get(arg0).getImageUrl();
					fenliang = newList1.get(arg0).getJiage();
					danwei = newList1.get(arg0).getDanwei();
					newList1.get(arg0).setJiage(
							newList1.get(arg0).getTifengliang());
					newList1.get(arg0).setDanwei(
							newList1.get(arg0).getTidanwei());
					newList1.get(arg0).setCover_url_small(
							newList1.get(arg0).getTiID());
					newList1.get(arg0).setName(newList1.get(arg0).getTiName());
					newList1.get(arg0).setImageUrl(
							newList1.get(arg0).getTiTuPian());
					newList1.get(arg0).setTifengliang(fenliang);
					newList1.get(arg0).setTidanwei(danwei);
					newList1.get(arg0).setTiID(id);
					newList1.get(arg0).setTiName(name);
					newList1.get(arg0).setTiTuPian(tuPian);
					System.out.println(newList1.get(arg0).getJiage()
							+ newList1.get(arg0).getTifengliang()
							+ "###########");
					if (name.contains("单餐")) {
						if (!TextUtils.isEmpty(newList1.get(arg0).getJiage())) {
							v2.jiaGess.setText("约"
									+ newList1.get(arg0).getJiage()
									+ newList1.get(arg0).getDanwei());
						} else {
							v2.jiaGess.setText("");
						}

					} else if (!TextUtils
							.isEmpty(newList1.get(arg0).getJiage())) {
						if ("g".equals(newList1.get(arg0).getDanwei())) {
							v2.jiaGess.setText("约"
									+ newList1.get(arg0).getJiage()
									+ newList1.get(arg0).getDanwei());
						} else {
							v2.jiaGess.setText(newList1.get(arg0).getJiage()
									+ newList1.get(arg0).getDanwei());
						}

					} else {
						v2.jiaGess.setText("");
					}

					v2.imgName.setText(newList1.get(arg0).getName());
					Load.imageLoader.displayImage(newList1.get(arg0)
							.getImageUrl(), v2.image, Load.options);
				}
			});
		}
	}

	private ViewHolde v = null;

	private class ViewHolde {
		ImageView image, goux;
		TextView imgName, jiaGess, tih;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (isZiXuan == 3) {// zixuan
				caiID = new StringBuilder();
				price = 0.0;
				if (!new LoginCheckAlertDialogUtils(FeastSetActivity.this)
						.showDialog()) {
					for (int i = 0; i < FeastSetActivity.lists.size(); i++) {
						if (FeastSetActivity.lists.get(i).isiCho()) {
							caiID.append("H"
									+ FeastSetActivity.lists.get(i)
											.getAlbumTitle());
							System.out.println(caiID);
							price = price
									+ Double.parseDouble(FeastSetActivity.lists
											.get(i).getUnitPrice());
						}
					}
					System.out.println("+++++++++++++++" + caiID);
					if (caiID.length() > 0) {
						for (int i = 0; i < FeastSetActivity.lists.size(); i++) {
							if (FeastSetActivity.lists.get(i).isiCho()) {
								caiID.append("H"
										+ FeastSetActivity.lists.get(i)
												.getAlbumTitle());
								System.out.println(caiID);
								price = price
										+ Double.parseDouble(FeastSetActivity.lists
												.get(i).getUnitPrice());
							}
						}
						System.out.println("+++++++++++++++" + caiID);
						int end = caiID.length();
						IDcai = caiID.substring(1, end);
						System.out.println("QW" + IDcai);
						show();
					} else {
						HomeActivity.dianJiShiJian = System.currentTimeMillis();
						File f = new File(
								"/data/data/com.example.esycab/cache/ACache/1297131408");
						if (f.exists()) {
							f.delete();
						}
						// 结束当前界面
						FeastSetActivity.this.finish();
						return true;
					}
				}

			}else
			if (System.currentTimeMillis() - HomeActivity.dianJiShiJian > 500) {

				HomeActivity.dianJiShiJian = System.currentTimeMillis();
				File f = new File(
						"/data/data/com.example.esycab/cache/ACache/1297131408");
				if (f.exists()) {
					f.delete();
				}
				// 结束当前界面
				FeastSetActivity.this.finish();

			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}


}
