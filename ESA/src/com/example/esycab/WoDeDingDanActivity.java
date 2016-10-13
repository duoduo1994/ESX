package com.example.esycab;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.esycab.entity.AnLi;
import com.example.esycab.network.SmartFruitsRestClient;
import com.example.esycab.utils.ActivityCollector;
import com.example.esycab.utils.Diolg;
import com.example.esycab.utils.LocalStorage;
import com.example.esycab.utils.XListView;
import com.example.esycab.utils.XListView.IXListViewListener;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.util.Log;
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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class WoDeDingDanActivity extends Activity implements IXListViewListener {
	private XListView lv;
	private LinearLayout ll, jiBen;
	private String tel;
	private TextView tv;
	private String[] type = { "婚宴", "生日宴", "满月宴", "乔迁之喜", "金榜题名谢师宴", "公司年会宴" };
	private String[] ord = { "预定中", "沟通中", "订单已确定", "订单已支付", "订单已配送", "完成" };
//	private String[] judged = { "我要评价", "已评价" };
	public static Activity a;
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
		setContentView(R.layout.activity_wo_de_ding_dan);
		ActivityCollector.addActivity(this);
		a = WoDeDingDanActivity.this;
		LocalStorage.initContext(this);
		lv = (XListView) findViewById(R.id.wode_dingdan_libiao);
		jiBen = (LinearLayout) findViewById(R.id.wo_de_dingdan_jiben_xingxi);
		jiBen.setVisibility(View.GONE);
		wu = (ImageView) findViewById(R.id.zanwudingdan);
		tel = (String) LocalStorage.get("UserTel");
		ll = (LinearLayout) findViewById(R.id.wode_dingdan_woyao_yuyue);
		ll.setVisibility(View.GONE);
		tv = (TextView) findViewById(R.id.tv_title);
		tv.setText("订单列表");
		lv.setPullLoadEnable(true);
		lv.setPullRefreshEnable(true);
		// 设置监听
		lv.setXListViewListener(this);
		// address=getAdress();
		findViewById(R.id.btn_back).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if(System.currentTimeMillis()-HomeActivity.dianJiShiJian>500){
					HomeActivity.dianJiShiJian = System.currentTimeMillis();
				WoDeDingDanActivity.this.finish();
				}
			}
		});
		getMsg();
		setMoreListener();
	}
	private	ImageView iv_more,wu;
	private void setMoreListener() {
		iv_more = (ImageView) findViewById(R.id.iv_more);
		iv_more.setVisibility(View.GONE);
//		 iv_more.setOnClickListener(new IvListener(iv_more,
//		 WoDeDingDanActivity.this,1));
	}

	private void getMsg() {

		RequestParams p = new RequestParams();
		p.put("fkCusTel", tel);
		SmartFruitsRestClient.get("OrderHandler.ashx?Action=OrderAll", p,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
						String result = new String(arg2);
						System.out.println(result);
						tryc(result);
					}

					@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
							Throwable arg3) {
						System.out.println(arg3 + "asdfgdhgsa");
						if (ciShu >= 3) {
							isFalse = false;
							Toast.makeText(WoDeDingDanActivity.this,
									getString(R.string.conn_failed),
									Toast.LENGTH_SHORT).show();
						}
						;

						if (isFalse) {
							getMsg();
							ciShu++;
						}

					}
				});
	}

	private AnLi anli;
	private JSONObject tJson;
	static List<AnLi> lal;
//	private CommonAdapter<AnLi> caal;

	public void tryc(String result) {
		if (lal == null) {
			lal = new ArrayList<AnLi>();
		} else {
			lal.clear();
		}
		try {
			JSONObject json = new JSONObject(result.trim());
			JSONArray configlist = json.getJSONArray("订单列表");
			if (0 != configlist.length()) {
				// System.out.println(configlist.length());
				for (int i = 0; i < configlist.length(); i++) {
					tJson = configlist.getJSONObject(i);
					anli = new AnLi();
					// "ReserveID": "375",
					anli.setName(tJson.getString("Name"));
					if (tJson.getString("Sex").equals("0")) {
						anli.setSex("女");
					} else {
						anli.setSex("男");
					}
					anli.setHasJudge(tJson.getString("EvaluateOrNot"));
					anli.setLianxidizhi(tJson.getString("DeliveryAddr"));// 联系地址
					anli.setZhuzhuo(tJson.getString("MainCount") + "桌");// 主餐桌数
					anli.setFuzhuo(tJson.getString("SubCount") + "桌");// 副餐桌数
					if (tJson.getString("fkDinnerTimeID").equals("1")) {
						anli.setZhucanshijian("午餐");
					} else {
						anli.setZhucanshijian("晚餐");
					}

					anli.setTel(tJson.getString("Tel"));// 联系电话
					anli.setFeastAdr(tJson.getString("OrderID"));// 订单编号
					anli.setFeastOrd(tJson.getString("FeastCgy"));// xiyan
					anli.setHallsName(tJson.getString("FeastDt"));// xiyan riqi
					anli.setRiQX(tJson.getString("BookDt"));// xiadan riqi
					anli.setImgUrl(tJson.getString("HallName"));// 具体地址对应的id
					anli.setDetail(tJson.getString("OrdStatus"));
					if ("1".equals(tJson.getString("FeastCgy"))) {
						anli.setFeastOrd(type[0]);
					} else if ("2".equals(tJson.getString("FeastCgy"))) {
						anli.setFeastOrd(type[1]);
					} else if ("3".equals(tJson.getString("FeastCgy"))) {
						anli.setFeastOrd(type[2]);
					} else if ("4".equals(tJson.getString("FeastCgy"))) {
						anli.setFeastOrd(type[3]);
					} else if ("5".equals(tJson.getString("FeastCgy"))) {
						anli.setFeastOrd(type[4]);
					} else if ("6".equals(tJson.getString("FeastCgy"))) {
						anli.setFeastOrd(type[5]);
					}
					if ("1".equals(tJson.getString("OrdStatus"))) {
						anli.setDetail(ord[0]);
					} else if ("2".equals(tJson.getString("OrdStatus"))) {
						anli.setDetail(ord[1]);
					} else if ("3".equals(tJson.getString("OrdStatus"))) {
						anli.setDetail(ord[2]);
					} else if ("4".equals(tJson.getString("OrdStatus"))) {
						anli.setDetail(ord[3]);
					} else if ("5".equals(tJson.getString("OrdStatus"))) {
						anli.setDetail(ord[4]);
					} else if ("6".equals(tJson.getString("OrdStatus"))) {
						anli.setDetail(ord[5]);
					}
					System.out.println(anli.getHallsName());
					int a = anli.getHallsName().lastIndexOf("/");
					int b = anli.getRiQX().lastIndexOf("/");
					String a1 = anli.getHallsName().substring(0, a + 3);
					String b1 = anli.getRiQX().substring(0, b + 3);
					anli.setHallsName(a1);
					anli.setRiQX(b1);
					lal.add(anli);
				}
				if (my == null) {
					my = new MyAdapter();
					lv.setAdapter(my);
				} else {
					my.notifyDataSetChanged();
				}
			}else{
				lv.setVisibility(View.GONE);
				wu.setVisibility(View.VISIBLE);
			}


		} catch (JSONException e) {
			System.out.println("G" + e.getMessage());
		}
	}

	private MyAdapter my;

	private	class MyAdapter extends BaseAdapter {
		LayoutInflater li;
//		View view;
		List<Integer> lsi;

		MyAdapter() {
			li = LayoutInflater.from(WoDeDingDanActivity.this);
			lsi = new ArrayList<Integer>();
		}

		@Override
		public int getCount() {
			return lal.size();
		}

		@Override
		public Object getItem(int arg0) {
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			return 0;
		}

//		public void setGX(View v1, int arg0) {
//			v = (ViewHolder) v1.getTag();
//		}

		@Override
		public View getView(final int arg0, View arg1, ViewGroup arg2) {
			if (arg0 < lal.size()) {
				arg1 = li.inflate(R.layout.item_order, null);
				v = new ViewHolder();
				v.bianhao = (TextView) arg1
						.findViewById(R.id.wode_dingdan_bianhao);
				v.rv = (LinearLayout) arg1.findViewById(R.id.wode_dingdan_tiaozhuan_xq);
				v.leixing = (TextView) arg1
						.findViewById(R.id.wode_dingdan_xiyanleixing);
//				v.xiadan = (TextView) arg1
//						.findViewById(R.id.wo_de_ding_dang_xiadan);
				v.name = (TextView) arg1
						.findViewById(R.id.wode_dingdan_zhuangtaisss);
				v.riqi = (TextView) arg1
						.findViewById(R.id.wode_dingdan_yanqing_riqi);
				v.quxiao = (TextView) arg1
						.findViewById(R.id.wode_dingdan_zhuangtai);
				v.bt = (Button) arg1
						.findViewById(R.id.wo_de_ding_dan_fa_qingtie);
				v.image = (LinearLayout) arg1
						.findViewById(R.id.wode_dingdan_tiaozhuan_xq);
				v.pingjia = (Button) arg1
						.findViewById(R.id.wo_de_ding_dan_wo_pingjia);
				arg1.setTag(v);
				lsi.add(arg0);
			} else {
				v = (ViewHolder) arg1.getTag();
			}
			v.bianhao.setText(lal.get(arg0).getFeastAdr());
			v.name.setText(lal.get(arg0).getDetail());
			v.riqi.setText(lal.get(arg0).getHallsName());
//			v.xiadan.setText(lal.get(arg0).getRiQX());
			if (!"完成".equals(lal.get(arg0).getDetail())) {
				v.pingjia.setVisibility(View.INVISIBLE);
			}//"订单已配送", "完成"
			if("订单已支付".equals(lal.get(arg0).getDetail())||"完成".equals(lal.get(arg0).getDetail())||"订单已配送".equals(lal.get(arg0).getDetail())){
				v.rv.setBackgroundColor(Color.parseColor("#f0f0f0"));
			}else{
				v.rv.setBackgroundColor(Color.WHITE);
			}
			if (!lal.get(arg0).getHasJudge().equals("未评价")) {
				v.pingjia.setText("已评价");
				v.pingjia.setClickable(false);
			} else {
				v.pingjia.setText("我要评价");
				v.pingjia.setClickable(true);
			}
			v.leixing.setText(lal.get(arg0).getFeastOrd());// 喜宴类型，为一的时候时喜宴
			click(v, arg1, arg0);
			return arg1;
		}

		public void click(ViewHolder v2, final View arg1, final int arg0) {
			v2.image.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg1) {
					// TODO Auto-generated method stub

					Intent intent = new Intent(WoDeDingDanActivity.this,
							WoDeDingDanXQ.class);
					intent.putExtra("ding_dan_bianhao", lal.get(arg0)
							.getFeastAdr());
					Bundle bundle = new Bundle();
					bundle.putSerializable("dingdan", lal.get(arg0));
					intent.putExtras(bundle);
					WoDeDingDanActivity.this.startActivity(intent);
				}
			});
			v2.pingjia.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg1) {
					if (!lal.get(arg0).getHasJudge().equals("未评价")) {

					} else {
						Intent intent = new Intent(WoDeDingDanActivity.this,
								EvaluationActivity.class);
						intent.putExtra("ding_danbianhao", lal.get(arg0)
								.getFeastAdr());
						WoDeDingDanActivity.this.startActivityForResult(intent,
								15);
					}
				}
			});
			v2.bt.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg5) {
					Log.i("订单类型的编号", lal.get(arg0).getFeastOrd() + "");
					// getAdress();
					// TODO Auto-generated method stub"订单已支付", "订单已配送"
					if ("完成".equals(lal.get(arg0).getDetail())) {
						new Diolg(WoDeDingDanActivity.this, "确定", "返回",
								"订单已完成", "提示", 0);
					} else {
						Intent intent = new Intent(WoDeDingDanActivity.this,
								QingTieXinXiActivity.class);
						intent.putExtra("leixing", lal.get(arg0).getFeastOrd());
						intent.putExtra("orderID", lal.get(arg0).getFeastAdr());
						intent.putExtra("leixing", lal.get(arg0).getFeastOrd());
						Log.i("==============================", lal.get(arg0)
								.getFeastOrd());
						intent.putExtra("DeliveryAddr", lal.get(arg0)
								.getImgUrl());
						intent.putExtra("FeastDt", lal.get(arg0).getHallsName());
						intent.putExtra("FeastOrd", lal.get(arg0).getFeastOrd());
						WoDeDingDanActivity.this.startActivity(intent);
					}
				}

			});
			v2.quxiao.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg1) {
					if ("完成".equals(lal.get(arg0).getDetail())||"订单已支付".equals(lal.get(arg0).getDetail())||"订单已配送".equals(lal.get(arg0).getDetail())) {
						new Diolg(WoDeDingDanActivity.this, "确定", "返回",
								"该订单状态不可取消", "提示", 0);
					} else {
						if(WoDeDingDanActivity.this.isFinishing()){}else{
						d = new Dialog(WoDeDingDanActivity.this,
								R.style.loading_dialog);
						v1 = LayoutInflater.from(WoDeDingDanActivity.this)
								.inflate(R.layout.dialog, null);// 窗口布局
						d.setContentView(v1);// 把设定好的窗口布局放到dialog中
						d.setCanceledOnTouchOutside(true);// 设定点击窗口空白处取消会话
						p1 = (Button) v1.findViewById(R.id.p);
						n = (Button) v1.findViewById(R.id.n);
						juTiXinXi = (TextView) v1
								.findViewById(R.id.banben_xinxi);
						tiShi = (TextView) v1.findViewById(R.id.banben_gengxin);
//						fuZhi = (TextView) v1
//								.findViewById(R.id.fuzhi_jiantieban);
						juTiXinXi.setText("确认取消订单吗");
						tiShi.setText("取消确认");
						p1.setText("确认");
						n.setText("返回");
						p1.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View arg) {
								// TODO Auto-generated method stub
								quXiaoDingDan(lal.get(arg0).getFeastAdr());
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
						d.show();}
					}
				}
			});
		}
	}

	private TextView juTiXinXi, tiShi;
	private Dialog d;
	private Button p1;
	private Button n;
	private View v1;

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case 15:
			if (data.getIntExtra("qud", 0) == 1) {
				getMsg();
			}
			break;

		default:
			break;
		}
	}

	private void quXiaoDingDan(final String ID) {
		System.out.println("++++++++++++++++++++++++");
		RequestParams p = new RequestParams();
		p.put("OrderID", ID);
		String tel = (String) LocalStorage.get("UserTel");
		p.put("fkCusTel", tel);
		p.put("CustPhyAddr", HomeActivity.strUniqueId);
		SmartFruitsRestClient.get("BookHandler.ashx?Action=bookDelete", p,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
						String result = new String(arg2);
						isFalse = true;
						ciShu = 0;
						System.out.println(result);
						try {
							JSONObject j = new JSONObject(result.trim());
							result = j.getString("提示");
							if (j.getString("提示").equals("success")) {
								result = "成功";
							} else if (j.getString("提示").equals("非法的操作")) {
								result = "订单已确定，不宜删除";
							}
							new Diolg(WoDeDingDanActivity.this, "确定", "null",
									result, "提示", 0);
							// Toast.makeText(WoDeDingDanActivity.this,
							// j.getString("提示"), Toast.LENGTH_LONG).show();
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						// Toast.makeText(WoDeDingDanActivity.this, result,
						// Toast.LENGTH_LONG).show();
						getMsg();
					}

					@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
							Throwable arg3) {
						System.out.println(arg3 + "asdfgdhgsa");
						if (ciShu >= 3) {
							isFalse = false;
							Toast.makeText(WoDeDingDanActivity.this,
									"网络连接失败，请重新连接", Toast.LENGTH_SHORT).show();
						}
						;

						if (isFalse) {
							quXiaoDingDan(ID);
							ciShu++;
						}

					}
				});
	}

	private ViewHolder v = null;

	private class ViewHolder {
		LinearLayout image,rv;
		Button bt, pingjia;
		TextView name, leixing, riqi, bianhao, quxiao, xiadan;
	}

	private Handler handler = new Handler();
	private boolean isFalse = true;
	private int ciShu = 0;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if(System.currentTimeMillis()-HomeActivity.dianJiShiJian>500){
				HomeActivity.dianJiShiJian = System.currentTimeMillis();
			WoDeDingDanActivity.this.finish();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onRefresh() {
		handler.postDelayed(new Thread() {
			public void run() {

				// page = 1;
				// tngous.removeAll(tngous);
				getMsg();
				lv.stopRefresh();

			}
		}, 1000);
	}

	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub

	}

}
