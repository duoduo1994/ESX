package com.example.esycab;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.example.esycab.alipay.PayResult;
import com.example.esycab.alipay.SignUtils;
import com.example.esycab.entity.AnLi;
import com.example.esycab.network.SmartFruitsRestClient;
import com.example.esycab.utils.Diolg;
import com.example.esycab.utils.LocalStorage;
import com.example.esycab.utils.XListView;
import com.eyoucab.list.CommonAdapter;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class WoDeDingDanXQ extends Activity {
	private ListView lv;
	private LinearLayout jiBen;
	private String tel, ding_dan_bianhao;
	private LinearLayout ll;
	private TextView t1, t2, biaoTi;
	private Button btn;
	static AnLi al;
	boolean isZhiFu = false;
	// 商户PID
	private final String PARTNER = "2088121862938985";
	// 商户收款账号
	private final String SELLER = "bill@nbeykj.com";
	// 商户私钥，pkcs8格式
	private final String RSA_PRIVATE = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAM05RB9EN+ipyb+eRKKMzg2W9Frdwk2DK47Rh95uqQWEPI5kGwV90w1B2QYi9Shl3aDyjyCsG+zuNJvPQQrrPGOPE4KvW5gE6KjkKifTrZp0+vUcOiRoIeodynfaBJzPARaRLgZ0sW3x/17fzsuSPPFQr4fgft+vjevRN1QJXCI1AgMBAAECgYEAsZLCihdaFRyM8Bu/IbOaO5IJn3JQxQchsnNFEEdKQva3+zFNXEQSUV9PrQqkfK8fxTr8c3XYt/spVERR8GO5HJOiTSVqZRREjMBeqfbtNKidWcWBz1330vYhuvWiEyKaoWXAR/wlRbGywZC007qF1YCgqaMP6oxlF5d+QNiXcIECQQDpAu2KunuVb+agjWmVLgBYS/EIZ/plIWkVngsOcCNt1/+0fcjteD49+HdSCkeStF6yDwpuMwlp8FB6Hd939r/hAkEA4XiDzxZgMtgz8msWXiOsBGC+MKuKB7ybdanahXv1EUPecYa45+BSB/dg0Fu4WJGyQAx81XCSnNqscVoATc381QJAJI90DB9kgrcTHb/ygBi+rxwDTslZzYJnhZ/Npk9MD9EjawONgk0qnvicaD/6qPcqIJAhl9bkND4jsnV7ecw6oQJBAIa9259dUt3vwJOFlZdqn/j5y48QZGudNhZKDFIrMtg/g+a6STQhJUSnfE7oETG02B5YmB2BqcxdDiKrU3UrvYUCQEjVvTWSO2vUbgMeVq/lCbpb8Eu52eya30afD/aCW1ujlLd3oyjGg2Hfwk8lAzfMs4oqh/SmWs7OqYDAxdmliGA=";

	// 支付宝公钥
	private final String RSA_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDI6d306Q8fIfCOaTXyiUeJHkrIvYISRcc73s3vF1ZT7XN8RNPwJxo8pWaJMmvyTn9N4HQ632qJBVHf8sxHi/fEsraprwCtzvzQETrNRwVxLO5jVmRGi60j8Ue1efIlzPXV9je9mkjzOmdssymZkh2QhUrCmZYI/FCEa3/cNMW0QIDAQAB";
	private final int SDK_PAY_FLAG = 1;

	private static final int SDK_CHECK_FLAG = 2;

	private EditText mUserId;
	private Button mLogon;
	private Button zf;

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
		LocalStorage.initContext(this);
		// RelativeLayout rl;
		// if(!HomeActivity.quan){
		// rl = (RelativeLayout) findViewById(R.id.g_eng_d_uo);
		// android.widget.LinearLayout.LayoutParams l =
		// (android.widget.LinearLayout.LayoutParams) rl.getLayoutParams();
		// l.topMargin = (int) (HomeActivity.a12);
		// rl.setLayoutParams(l);
		// }
		zf = (Button) findViewById(R.id.jiarudingdan);
		lv = (ListView) findViewById(R.id.wode_dingdan_libiao);
		al = (AnLi) getIntent().getSerializableExtra("dingdan");
		ding_dan_bianhao = getIntent().getStringExtra("ding_dan_bianhao");
		jiBen = (LinearLayout) findViewById(R.id.wo_de_dingdan_jiben_xingxi);
		jiBen.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				startActivity(new Intent(WoDeDingDanXQ.this,
						JiBenXingXiActivity.class));
			}
		});
		lv.setDividerHeight(1);
		((XListView) lv).setPullLoadEnable(false);
		((XListView) lv).setPullRefreshEnable(false);
		setListener();
		biaoTi = (TextView) findViewById(R.id.tv_title);
		biaoTi.setText("订单");
		ll = (LinearLayout) findViewById(R.id.wode_dingdan_woyao_yuyue);
		t1 = (TextView) ll.findViewById(R.id.tongyong_jiage);
		t2 = (TextView) ll.findViewById(R.id.unit_price);
		btn = (Button) ll.findViewById(R.id.jiarudingdan);
		if ("完成".equals(al.getDetail()) || "订单已配送".equals(al.getDetail())) {
			ll.setVisibility(View.GONE);
		} else if ("订单已支付".equals(al.getDetail())) {
			ll.setVisibility(View.GONE);
		} else {
			t1.setText("应付定金");
			btn.setText("立即支付");
			btn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					if (!isZhiFu) {
						if (TextUtils.isEmpty(PARTNER) || TextUtils.isEmpty(RSA_PRIVATE) || TextUtils.isEmpty(SELLER)) {
							new Diolg(WoDeDingDanXQ.this, "确定", "null", "需要配置PARTNER | RSA_PRIVATE| SELLER", "警告", 0);
							return;
						}
						// 订单
						String orderInfo = getOrderInfo("甬尚鲜定金", "该测试商品的详细描述",
								df.format(d3)+ "");

						/**
						 * 特别注意，这里的签名逻辑需要放在服务端，切勿将私钥泄露在代码中！
						 */
						String sign = sign(orderInfo);
						try {
							/**
							 * 仅需对sign 做URL编码
							 */
							sign = URLEncoder.encode(sign, "UTF-8");
						} catch (UnsupportedEncodingException e) {
							e.printStackTrace();
						}

						/**
						 * 完整的符合支付宝参数规范的订单信息
						 */
						final String payInfo = orderInfo + "&sign=\"" + sign + "\"&" + getSignType();
						
						Runnable payRunnable = new Runnable() {

							@Override
							public void run() {
								// 构造PayTask 对象
								PayTask alipay = new PayTask(WoDeDingDanXQ.this);
								// 调用支付接口，获取支付结果
								String result = alipay.pay(payInfo, true);

								Message msg = new Message();
								msg.what = SDK_PAY_FLAG;
								msg.obj = result;
								mHandler.sendMessage(msg);
							}
						};

						// 必须异步调用
						Thread payThread = new Thread(payRunnable);
						payThread.start();

					}

				}
			});
		}
		tel = getIntent().getStringExtra("ding_dan_bianhao");

		findViewById(R.id.btn_back).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (System.currentTimeMillis() - HomeActivity.dianJiShiJian > 500) {
					if (isZhiFu) {
						Intent i = new Intent(WoDeDingDanXQ.this,
								WoDeDingDanActivity.class);
						startActivity(i);
						WoDeDingDanActivity.a.finish();
						WoDeDingDanXQ.this.finish();
					} else {
						WoDeDingDanXQ.this.finish();
					}
					HomeActivity.dianJiShiJian = System.currentTimeMillis();
				}
			}
		});
		getDJ();
		getMsg();

		setMoreListener();
	}
	DecimalFormat    df  ;
	double d3;
	private void getDJ() {
		System.out.println("getdj");
		RequestParams p = new RequestParams();
		SmartFruitsRestClient.get("PayHisHandler.ashx?Action=GetPayConfig", p,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
						// TODO Auto-generated method stub
						String result = new String(arg2);
						System.out.println(result);
						try {
							JSONArray j = new JSONArray(result.trim());
							JSONObject dj = j.getJSONObject(0);
							String zfdj = dj.getString("FeastEarnestMoney");
							System.out.println(zfdj + "&&&&&&&&&&&&");
						df = new DecimalFormat("######0.00");   

							d3	 =(double) (Double.parseDouble(zfdj) ) ;
							 
							t2.setText(df.format(d3)+ "");
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							System.out.println(e.getMessage() + "************");
						}
					}

					@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
							Throwable arg3) {
						// TODO Auto-generated method stub
						System.out.println(arg3 + "asdfgdhgsa");
						if (ciShu >= 3) {
							isFalse = false;
							Toast.makeText(WoDeDingDanXQ.this,
									getString(R.string.conn_failed),
									Toast.LENGTH_SHORT).show();
						}
						;

						if (isFalse) {
							getDJ();
							ciShu++;
						}

					}
				});

	}

	private ImageView iv_more;

	private void setMoreListener() {
		iv_more = (ImageView) findViewById(R.id.iv_more);
		iv_more.setVisibility(View.GONE);
	}


	/**
	 * get the out_trade_no for an order. 生成商户订单号，该值在商户端应保持唯一（可自定义格式规范）
	 * 
	 */
	private String getOutTradeNo() {
		SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss", Locale.getDefault());
		Date date = new Date();
		String key = format.format(date);

		Random r = new Random();
		key = key + r.nextInt();
		key = key.substring(0, 15);
		return key;
	}

	/**
	 * sign the order info. 对订单信息进行签名
	 * 
	 * @param content
	 *            待签名订单信息
	 */
	private String sign(String content) {
		return SignUtils.sign(content, RSA_PRIVATE);
	}

	/**
	 * get the sign type we use. 获取签名方式
	 * 
	 */
	private String getSignType() {
		return "sign_type=\"RSA\"";
	}
	/**
	 * create the order info. 创建订单信息
	 * 
	 */
	private String getOrderInfo(String subject, String body, String price) {

		// 签约合作者身份ID
		String orderInfo = "partner=" + "\"" + PARTNER + "\"";

		// 签约卖家支付宝账号
		orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

		// 商户网站唯一订单号
		orderInfo += "&out_trade_no=" + "\"" + getOutTradeNo() + "\"";

		// 商品名称
		orderInfo += "&subject=" + "\"" + subject + "\"";

		// 商品详情
		orderInfo += "&body=" + "\"" + body + "\"";

		// 商品金额
		orderInfo += "&total_fee=" + "\"" + price + "\"";

		// 服务器异步通知页面路径
				orderInfo += "&notify_url=" + "\""
						+ "http://120.27.141.95:8085/Ashx/notify_url.ashx" + "\"";

		// 服务接口名称， 固定值
		orderInfo += "&service=\"mobile.securitypay.pay\"";

		// 支付类型， 固定值
		orderInfo += "&payment_type=\"1\"";

		// 参数编码， 固定值
		orderInfo += "&_input_charset=\"utf-8\"";

		// 设置未付款交易的超时时间
		// 默认30分钟，一旦超时，该笔交易就会自动被关闭。
		// 取值范围：1m～15d。
		// m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
		// 该参数数值不接受小数点，如1.5h，可转换为90m。
		orderInfo += "&it_b_pay=\"30m\"";

		// extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
		// orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

		// 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
		orderInfo += "&return_url=\"m.alipay.com\"";

		return orderInfo;
	}

	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SDK_PAY_FLAG: {
				PayResult payResult = new PayResult((String) msg.obj);

				// 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
				String resultInfo = payResult.getResult();
				System.out.println(resultInfo + "______________________");
				String resultStatus = payResult.getResultStatus();
				System.out.println(resultStatus + "+++++++++++++++++");
				// 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
				if (TextUtils.equals(resultStatus, "9000")) {
					isZhiFu = true;
					Toast.makeText(WoDeDingDanXQ.this, "支付成功",
							Toast.LENGTH_SHORT).show();
					if (isZhiFu) {
						Intent i = new Intent(WoDeDingDanXQ.this,
								WoDeDingDanActivity.class);
						startActivity(i);
						WoDeDingDanActivity.a.finish();
						WoDeDingDanXQ.this.finish();
					} else {
						WoDeDingDanXQ.this.finish();
					}
				} else {
					// 判断resultStatus 为非“9000”则代表可能支付失败
					// “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
					if (TextUtils.equals(resultStatus, "8000")) {
						Toast.makeText(WoDeDingDanXQ.this, "支付结果确认中",
								Toast.LENGTH_SHORT).show();

					} else {
						// 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
						Toast.makeText(WoDeDingDanXQ.this, "支付失败",
								Toast.LENGTH_SHORT).show();

					}
				}
				break;
			}
			case SDK_CHECK_FLAG: {
				// Toast.makeText(WoDeDingDanXQ.this, "检查结果为：" + msg.obj,
				// Toast.LENGTH_SHORT).show();
				break;
			}
			default:
				break;
			}
		};
	};
	/**
	 * get the sdk version. 获取SDK版本号
	 * 
	 */
	public void getSDKVersion() {
		PayTask payTask = new PayTask(this);
		String version = payTask.getVersion();
		Toast.makeText(this, version, Toast.LENGTH_SHORT).show();
	}
	private void getMsg() {
		RequestParams p = new RequestParams();
		p.put("OrderID", tel);
		SmartFruitsRestClient.get("OrderHandler.ashx?Action=OrderDetail", p,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
						// TODO Auto-generated method stub
						String result = new String(arg2);
						System.out.println(result);
						tryc(result);
					}

					@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
							Throwable arg3) {
						// TODO Auto-generated method stub
						System.out.println(arg3 + "asdfgdhgsa");
						if (ciShu >= 3) {
							isFalse = false;
							Toast.makeText(WoDeDingDanXQ.this,
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
	private List<AnLi> lal;
//	private CommonAdapter<AnLi> caal;

	private void setListener() {
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String type = lal.get(position - 1).getHallsName();
				System.out.println(lal.get(position - 1).getHallsName()
						+ "+++++++++++++++++++");
				// Toast.makeText(WoDeDingDanXQ.this,
				// lal.get(position).getHallsName(), 1).show();
				if (type.equals("厨师")) {
					Intent intent = new Intent(WoDeDingDanXQ.this,
							ChuShi_GeRenXQActivity.class);
					intent.putExtra("fkCookID", lal.get(position - 1).getRiQX());
					startActivity(intent);
				} else if (type.equals("厨师团队")) {
					Intent intent = new Intent(WoDeDingDanXQ.this,
							ChuShi_TuanDuiXQActivity.class);
					intent.putExtra("fkCookGrpID", lal.get(position - 1)
							.getRiQX());
					startActivity(intent);
				} else if (type.equals("餐具租赁")) {
					Intent intent = new Intent(WoDeDingDanXQ.this,
							CanjuItenActivity.class);
					String t;
					// System.out.println(position+"_"+lal.get(position)+"+"+lal.get(position).getFuzhuo()+"!!!!!!!!!!!!!");
					if (lal.get(position - 1).getFuzhuo().equals("120")) {
						t = 5 + "";
					} else if (lal.get(position - 1).getFuzhuo().equals("80")) {
						t = 4 + "";
					} else {
						t = 3 + "";
					}
					intent.putExtra("sl", t);
					startActivity(intent);
				} else {
					Intent intent = new Intent(WoDeDingDanXQ.this,
							FeastSetTaocanActivity.class);
					intent.putExtra("name", lal.get(position - 1)
							.getHallsName());
					intent.putExtra("price", lal.get(position - 1)
							.getLianxidizhi());
					intent.putExtra("SetMealID", lal.get(position - 1)
							.getFeastOrd());
					startActivity(intent);
				}
			}
		});
	}

	private void tryc(String result) {
		if (lal == null) {
			lal = new ArrayList<AnLi>();
		} else {
			lal.clear();
		}
		try {
			JSONObject json = new JSONObject(result.trim());
			JSONArray configlist = json.getJSONArray("详情");
			if (null != configlist) {
				for (int i = 0; i < configlist.length(); i++) {
					tJson = configlist.getJSONObject(i);
					anli = new AnLi();

					String Title = tJson.getString("Title");

					if (Title.equals("套餐")) {
						String SetMealID = tJson.getString("SetMealID").trim();
						anli.setFeastOrd(SetMealID);
						anli.setFeastPrice(Double.parseDouble(tJson
								.getString("TotalPrice")));
						anli.setLianxidizhi(tJson.getString("TotalPrice"));
						String name = tJson.getString("Name");
						anli.setHallsName(name);
						anli.setDetail(Title);
					} else if (Title.equals("餐具租赁")) {
						anli.setDetail(Title);
						anli.setHallsName(Title);
						anli.setFuzhuo(tJson.getString("Tprice"));
						anli.setLianxidizhi(tJson.getString("Tprice"));
					} else {
						if (Title.equals("厨师")) {
							// anli.setPkCaseID(tJson.getInt("fkCookID"));
							anli.setRiQX(tJson.getString("fkCookID"));
						} else {
							anli.setRiQX(tJson.getString("fkCookGrpID"));
							// anli.setPkCaseID(tJson.getInt("fkCookGrpID"));
						}
						anli.setFeastOrd("");
						anli.setHallsName(Title);
						anli.setDetail(Title);
					}
					anli.setFeastAdr(Title);

					anli.setA(false);
					lal.add(anli);
				}

			} else {
				btn.setClickable(false);
			}

			if (null == my) {
				System.out.println("my==null");
				my = new MyAdapter();
				lv.setAdapter(my);
			} else {
				System.out.println("mu!=null");
				my.notifyDataSetChanged();
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			System.out.println("G" + e.getMessage());
			Toast.makeText(WoDeDingDanXQ.this, e.getMessage(),
					Toast.LENGTH_SHORT).show();
		}
	}

	private MyAdapter my;

	private class MyAdapter extends BaseAdapter {
		LayoutInflater li;
//		private View view;
		List<Integer> lsi;

		MyAdapter() {
			li = LayoutInflater.from(WoDeDingDanXQ.this);
			lsi = new ArrayList<Integer>();
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return lal.size();
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

//		public void setGX(View v1, int arg0) {
//			v = (ViewHolder) v1.getTag();
//		}

		@Override
		public View getView(final int arg0, View arg1, ViewGroup arg2) {
			if (!lsi.contains(arg0)) {
				arg1 = li.inflate(R.layout.item_wode_dingdan, null);
				v = new ViewHolder();
				v.leixing = (TextView) arg1
						.findViewById(R.id.wode_dingdan_xinlei);// mingzi
				v.name = (TextView) arg1.findViewById(R.id.wode_dingdan_jia_ge);// jiage
				v.quxiao = (TextView) arg1
						.findViewById(R.id.wode_ding_dan_shanchu);// shanche
				// v.image = (ImageView)
				// arg1.findViewById(R.id.wode_dingdan_zhifu);//gouxuan
				arg1.setTag(v);
				lsi.add(arg0);
			} else {
				v = (ViewHolder) arg1.getTag();
			}
			System.out.println(lal.get(arg0).getDetail());
			if (lal.get(arg0).getDetail().equals("餐具租赁")
					|| lal.get(arg0).getDetail().equals("套餐")) {
				if (lal.get(arg0).getLianxidizhi().contains(".")) {
					int a = lal.get(arg0).getLianxidizhi().lastIndexOf(".");
					lal.get(arg0).setLianxidizhi(
							lal.get(arg0).getLianxidizhi().substring(0, a));
				}
				v.name.setText(lal.get(arg0).getLianxidizhi() + "元/桌");
				v.leixing.setText(lal.get(arg0).getHallsName());
			} else {
				v.name.setText("另算");
				v.leixing.setText(lal.get(arg0).getDetail());
			}

			click(v, arg1, arg0);
			return arg1;
		}

		public void click(final ViewHolder v2, final View arg1, final int arg0) {

			v2.quxiao.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg1) {
					// TODO Auto-generated method stub
					if ("完成".equals(al.getDetail())
							|| "订单已配送".equals(al.getDetail())
							|| "订单已确定".equals(al.getDetail())
							|| "订单已支付".equals(al.getDetail())) {
						new Diolg(WoDeDingDanXQ.this, "确定", "返回", "订单已确定，不可删除",
								"提示", 0);
					} else {
						if (WoDeDingDanXQ.this.isFinishing()) {
						} else {
									d = new Dialog(WoDeDingDanXQ.this,
											R.style.loading_dialog);
									v11 = LayoutInflater.from(
											WoDeDingDanXQ.this).inflate(
											R.layout.dialog, null);// 窗口布局
									d.setContentView(v11);// 把设定好的窗口布局放到dialog中
									d.setCanceledOnTouchOutside(true);// 设定点击窗口空白处取消会话
									p1 = (Button) v11.findViewById(R.id.p);
									n = (Button) v11.findViewById(R.id.n);
									juTiXinXi = (TextView) v11
											.findViewById(R.id.banben_xinxi);
									tiShi = (TextView) v11
											.findViewById(R.id.banben_gengxin);
//									fuZhi = (TextView) v11
//											.findViewById(R.id.fuzhi_jiantieban);
									juTiXinXi.setText("确定删除吗？");
									tiShi.setText("提示");
									p1.setText("是");
									n.setText("否");
									p1.setOnClickListener(new OnClickListener() {

										@Override
										public void onClick(View arg) {
											// TODO Auto-generated method stub
											shanChu(lal.get(arg0).getFeastAdr(),
													lal.get(arg0).getFeastOrd());
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
				}
			});
		}
	}

	private TextView juTiXinXi, tiShi;
	private Dialog d;
	private Button p1;
	private Button n;
	private View v11;
	private ViewHolder v = null;
//	private int jiaGe = 0;

	private class ViewHolder {
//		ImageView image;
		TextView name, leixing, quxiao;
	}

	private void shanChu(final String lei, final String SetMealID) {
		String tels = (String) LocalStorage.get("UserTel");
		System.out.println(tels + "HJK" + HomeActivity.strUniqueId);

		RequestParams p = new RequestParams();
		p.put("fkCusTel", tels);
		p.put("CustPhyAddr", HomeActivity.strUniqueId);
		if (lei.contains("套餐")) {
			p.put("SetMealID", SetMealID);
			SmartFruitsRestClient.get(
					"BookHandler.ashx?Action=DeleteSetMealByID", p,
					new AsyncHttpResponseHandler() {

						@Override
						public void onSuccess(int arg0, Header[] arg1,
								byte[] arg2) {
							// TODO Auto-generated method stub
							String result = new String(arg2);
							try {
								JSONObject j = new JSONObject(result.trim());
								new Diolg(WoDeDingDanXQ.this, "确定", "null", j
										.getString("提示"), "提示", 0);
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							System.out.println(result);
							// Toast.makeText(WoDeDingDanXQ.this, result,
							// Toast.LENGTH_LONG).show();
							getMsg();

						}

						@Override
						public void onFailure(int arg0, Header[] arg1,
								byte[] arg2, Throwable arg3) {
							// TODO Auto-generated method stub
							System.out.println(arg3 + "asdfgdhgsa");
							if (ciShu >= 3) {
								isFalse = false;
								Toast.makeText(WoDeDingDanXQ.this,
										getString(R.string.conn_failed),
										Toast.LENGTH_SHORT).show();
							}
							;

							if (isFalse) {
								shanChu(lei, SetMealID);
								ciShu++;
							}

						}
					});

			return;
		} else if (lei.equals("厨师")) {
			p.put("Type", "Type_Cook_Single");
		} else if (lei.equals("厨师团队")) {
			p.put("Type", "Type_Cook_Grp");
		} else if (lei.equals("婚庆定制")) {
			p.put("Type", "Type_WiddingItem");
		} else if (lei.equals("餐具租赁")) {
			p.put("Type", "Type_TableWare");
		} else if (lei.equals("婚车租赁")) {
			p.put("Type", "Type_WiddingCar");
		} else if (lei.equals("喜事堂")) {
			p.put("Type", "Type_Hall");
		}
		System.out.println(tels + "," + HomeActivity.strUniqueId + ","
				+ SetMealID);
		SmartFruitsRestClient.get("BookHandler.ashx?Action=RemoveOutOrder", p,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
						// TODO Auto-generated method stub
						String result = new String(arg2);
						System.out.println(result);
						try {
							JSONObject j = new JSONObject(result.trim());
							new Diolg(WoDeDingDanXQ.this, "确定", "null", j
									.getString("提示"), "提示", 0);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						// Toast.makeText(WoDeDingDanXQ.this, result,
						// Toast.LENGTH_LONG).show();
						getMsg();
					}

					@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
							Throwable arg3) {
						// TODO Auto-generated method stub
						System.out.println(arg3 + "asdfgdhgsa");
						if (ciShu >= 3) {
							isFalse = false;
							Toast.makeText(WoDeDingDanXQ.this,
									getString(R.string.conn_failed),
									Toast.LENGTH_SHORT).show();
						}
						;

						if (isFalse) {
							shanChu(lei, SetMealID);
							ciShu++;
						}

					}
				});
	}

	private boolean isFalse = true;
	private int ciShu = 0;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (System.currentTimeMillis() - HomeActivity.dianJiShiJian > 500) {
				if (isZhiFu) {
					Intent i = new Intent(WoDeDingDanXQ.this,
							WoDeDingDanActivity.class);
					startActivity(i);
					WoDeDingDanActivity.a.finish();
					WoDeDingDanXQ.this.finish();
				} else {
					WoDeDingDanXQ.this.finish();
				}
				HomeActivity.dianJiShiJian = System.currentTimeMillis();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
