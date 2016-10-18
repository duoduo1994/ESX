package com.example.administrator.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.activity.HomeActivity;
import com.example.administrator.myapplication.R;
import com.example.administrator.utils.ElasticScrollView;

public class FragmentNotify extends Fragment implements OnClickListener {
//	private List<GoodsEntity> lst = new ArrayList<GoodsEntity>();
	private LinearLayout wallet, xishitang, cookschedule, rentcar, order,
			allevaluate, cooperation, about, changemyinfomation;
	private TextView logon;
	private ImageView imageView1;
	private TextView textView5, usertelaa;
	private String userTel, userPass, nickName;
	private RelativeLayout rl;
	ElasticScrollView elasticScrollView;
private boolean isLog = false;
	// Boolean isSDCard;
//	@Override
//	public void onHiddenChanged(boolean hidden) {
//		// TODO Auto-generated method stub
//		super.onHiddenChanged(hidden);
//		if (hidden) {
//		} else {
//			LocalStorage.initContext(HomeActivity._instance);
//			System.out.println(((String) LocalStorage.get("LoginStatus"))
//					 + "%%%");
//			if (((String) LocalStorage.get("LoginStatus")).equals("tuichu")) {
//			} else {
//				System.out.println((String) LocalStorage.get("LoginStatus")+"$$$$$$$$$$$$");
//				if(isLog){}else{
//					isLog = true;
//				GetPersonInfo getPersonInfo = new GetPersonInfo();
//				getPersonInfo.getInfo(HomeActivity._instance,
//						LocalStorage.get("UserTel").toString());
//				getPersonInfo.setCallBackListener(new CallBackInfo() {
//
//					@Override
//					public void getSuccese() {
//						// TODO Auto-generated method stub
//						initData();
//					}
//
//					@Override
//					public void getFailure() {
//						// TODO Auto-generated method stub
//						initData();
//					}
//				});
//			}}
//		}
//	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = LayoutInflater.from(getActivity()).inflate(
				R.layout.geren_zhongxin, null);
//		Load.getLoad(getActivity());
		elasticScrollView = (ElasticScrollView) view
				.findViewById(R.id.elasticScrollView2);
		View ll = LayoutInflater.from(HomeActivity._instance).inflate(
				R.layout.item_geren_zhongxin, null);
		elasticScrollView.addChild(ll, 1);
//		userTel = LocalStorage.get("UserTel").toString();
//		userPass = LocalStorage.get("UserPass").toString();
//		nickName = LocalStorage.get("NickName").toString();
		usertelaa = (TextView) view.findViewById(R.id.usertel);
//		if (((String) LocalStorage.get("LoginStatus")).equals("tuichu")) {
//		} else {
//			usertelaa.setText("账号：" + LocalStorage.get("UserTel").toString());
//		}
		if (!HomeActivity.quan) {
			rl = (RelativeLayout) view.findViewById(R.id.geren_zhongxin_tou);
			LinearLayout.LayoutParams l = (LinearLayout.LayoutParams) rl
					.getLayoutParams();
			rl.setBackgroundColor(Color.parseColor("#eeeeee"));
			// l.topMargin = (int) (HomeActivity.a12);
			l.height = HomeActivity.a12;
			rl.setLayoutParams(l);
		}
//		LocalStorage.initContext(HomeActivity._instance);
		wallet = (LinearLayout) ll.findViewById(R.id.my_qingtie);// 钱包
		xishitang = (LinearLayout) ll.findViewById(R.id.my_xishitang);// 我的喜事堂
		cookschedule = (LinearLayout) ll.findViewById(R.id.cookschedule);// 厨师档期
		rentcar = (LinearLayout) ll.findViewById(R.id.my_address);// 婚车租赁
		order = (LinearLayout) ll.findViewById(R.id.order);// 我的订单
		allevaluate = (LinearLayout) ll.findViewById(R.id.my_coupon);// 我的评价
		// collection = (LinearLayout) view.findViewById(R.id.collection);//
		// 我的收藏
		cooperation = (LinearLayout) ll.findViewById(R.id.my_cooperation);// 我要合作
		about = (LinearLayout) ll.findViewById(R.id.about);// 联系客服
		logon = (TextView) view.findViewById(R.id.logon);// 登录
		imageView1 = (ImageView) view.findViewById(R.id.imageView1);// 我的头像
		textView5 = (TextView) view.findViewById(R.id.textView5);
		changemyinfomation = (LinearLayout) view
				.findViewById(R.id.changemyinfomation);

		if (checkLogin()) {
			logon.setText("登录");
			logon.setVisibility(View.VISIBLE);
		} else {
			logon.setText("退出");
			logon.setVisibility(View.GONE);
//			System.out.println("&&&&&&&&&&&" + userTel + "***"
//					+ LocalStorage.get("UserTel").toString());
//			GetPersonInfo getPersonInfo = new GetPersonInfo();
//			getPersonInfo.getInfo(getActivity(), LocalStorage.get("UserTel")
//					.toString());
//			getPersonInfo.setCallBackListener(new CallBackInfo() {
//
//				@Override
//				public void getSuccese() {
//					// TODO Auto-generated method stub
//					initData();
//				}
//
//				@Override
//				public void getFailure() {
//					// TODO Auto-generated method stub
//					initData();
//				}
//			});
		}

		view.setOnClickListener(this);

		// myInfo.setOnClickListener(this);
		wallet.setOnClickListener(this);
		xishitang.setOnClickListener(this);
		cookschedule.setOnClickListener(this);
		rentcar.setOnClickListener(this);
		order.setOnClickListener(this);
		allevaluate.setOnClickListener(this);
		// collection.setOnClickListener(this);
		cooperation.setOnClickListener(this);
		about.setOnClickListener(this);
		logon.setOnClickListener(this);
		// imageView1.setOnClickListener(this);
		changemyinfomation.setOnClickListener(this);
//		final Handler handler = new Handler() {
//			public void handleMessage(Message message) {
//				GetPersonInfo refreshPersonInfo = new GetPersonInfo();
//				refreshPersonInfo.getInfo(getActivity(),
//						LocalStorage.get("UserTel").toString());
//				refreshPersonInfo.setCallBackListener(new CallBackInfo() {
//
//					@Override
//					public void getSuccese() {
//						// TODO Auto-generated method stub
//						OnReceiveData();
//						initData();
//					}
//
//					@Override
//					public void getFailure() {
//						// TODO Auto-generated method stub
//						OnReceiveData();
//						initData();
//					}
//				});
//			}
//		};
//		elasticScrollView.setonRefreshListener(new OnRefreshListener() {
//
//			@Override
//			public void onRefresh() {
//				Thread thread = new Thread(new Runnable() {
//
//					@Override
//					public void run() {
//						try {
//							Thread.sleep(2000);
//						} catch (InterruptedException e) {
//							e.printStackTrace();
//						}
//						Message message = handler.obtainMessage(0, "new Text");
//						handler.sendMessage(message);
//					}
//				});
//				thread.start();
//			}
//		});
		return view;

	};

//	protected void OnReceiveData() {
//		elasticScrollView.onRefreshComplete();// guanbi shuaxin
//	}

	private Boolean checkLogin() {
//		if (LocalStorage.get("LoginStatus").toString().equals("login")) {
//			return false;
//		} else {
//			System.out.println("mei deng lu ");
//			d = new Dialog(getActivity(), R.style.loading_dialog);
//			v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog,
//					null);// 窗口布局
//			d.setContentView(v);// 把设定好的窗口布局放到dialog中
//			d.setCanceledOnTouchOutside(true);// 设定点击窗口空白处取消会话
//			p1 = (Button) v.findViewById(R.id.p);
//			n = (Button) v.findViewById(R.id.n);
//			juTiXinXi = (TextView) v.findViewById(R.id.banben_xinxi);
//			tiShi = (TextView) v.findViewById(R.id.banben_gengxin);
//			fuZhi = (TextView) v.findViewById(R.id.fuzhi_jiantieban);
//			juTiXinXi.setText("亲，还没登录呢，是否前去登录？");
//			tiShi.setText("登录提示");
//			p1.setText("确定");
//			n.setText("返回");
//			p1.setOnClickListener(new OnClickListener() {
//
//				@Override
//				public void onClick(View arg0) {
//					// TODO Auto-generated method stub
//					Intent intent = new Intent(getActivity(),
//							MainActivity.class);
//					intent.putExtra("flag", 5);
//					startActivityForResult(intent, 123);
//					d.dismiss();
//				}
//			});
//			n.setOnClickListener(new OnClickListener() {
//
//				@Override
//				public void onClick(View arg0) {
//					// TODO Auto-generated method stub
//					// System.out.println(a);
//					d.dismiss();
//				}
//			});
//			d.show();
			return true;
		}



	private TextView juTiXinXi, tiShi, fuZhi;
	private Dialog d;
	private Button p1;
	private Button n;
	private View v;

	private void initData() {

//		if (!TextUtils.isEmpty(LocalStorage.get("ImageUrl").toString())) {
//			Load.imageLoader.displayImage(LocalStorage.get("ImageUrl")
//					.toString(), imageView1, Load.options);
//		}
//		if (!TextUtils.isEmpty(LocalStorage.get("NickName").toString())) {
//			textView5.setText(LocalStorage.get("NickName").toString());
//			textView5.setTextSize(20);
//		}
//		if (!TextUtils.isEmpty(LocalStorage.get("UserTel").toString())) {
//			usertelaa.setText("账号：" + LocalStorage.get("UserTel").toString());
//		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		switch (requestCode) {

		case 123:

			String result = data.getStringExtra("resultString");
			String dengji = data.getStringExtra("Authority");
			if (!result.equals("亲，该咋称呼您~")) {
				System.out.println(54321);
				logon.setText("退出");
				logon.setVisibility(View.GONE);
//				userTel = LocalStorage.get("UserTel").toString();
				System.out.println(userTel + "$$$$$$$$$$$$");
//				usertelaa.setText("账号：" + userTel);
//				Load.imageLoader.displayImage(LocalStorage.get("ImageUrl")
//						.toString(), imageView1, Load.options);
			} else {
				System.out.println(12345);
				logon.setText("登录");
				imageView1.setImageResource(R.mipmap.myphoto);
			}
			textView5.setText(result);
//			if (!TextUtils.isEmpty(LocalStorage.get("UserTel").toString())) {
//				usertelaa.setText("账号："
//						+ LocalStorage.get("UserTel").toString());
//			}
			break;
		case 13:
			String result1 = data.getStringExtra("resultStringName");
			if (result1.equals("tuichu")) {
				System.out.println("tuichu!!!!!!!!!!!!!!!!!!!");
				logon.setText("登录");
				imageView1.setImageResource(R.mipmap.myphoto);
				textView5.setText("亲，该咋称呼您~");
				return;
			}
//			Load.imageLoader.displayImage(LocalStorage.get("ImageUrl")
//					.toString(), imageView1, Load.options);
			textView5.setText(result1);
//			if (!TextUtils.isEmpty(LocalStorage.get("UserTel").toString())) {
//				usertelaa.setText("账号："
//						+ LocalStorage.get("UserTel").toString());
//			}
			break;
		default:
			break;
		}
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		Intent intent = null;
		switch (arg0.getId()) {

		case R.id.logon:
//			if (logon.getText().toString().equals("登录")) {
//				Intent intent1 = new Intent(getActivity(), MainActivity.class);
//				intent1.putExtra("flag", 5);
//				startActivityForResult(intent1, 123);
//			} else {
//				new AlertDialog.Builder(getActivity())
//						.setTitle("退出提示")
//						// 设置对话框标题
//
//						.setMessage("亲，您真的准备退出吗？")
//						// 设置显示的内容
//
//						.setPositiveButton("确定",
//								new DialogInterface.OnClickListener() {// 添加确定按钮
//
//									@Override
//									public void onClick(DialogInterface dialog,
//											int which) {// 确定按钮的响应事件
//
//										// TODO Auto-generated method stub
//										logon.setText("登录");
//										LocalStorage.clear();
//										LocalStorage.set("LoginStatus",
//												"tuichu");
//										LocalStorage.set("UserPass", userPass);
//										LocalStorage.set("UserTel", userTel);
//
//										StartActivity.tuiChu();
//
//										Intent intent = new Intent(
//												getActivity(),
//												HomeActivity.class);
//										startActivity(intent);
//										getActivity().finish();
//
//									}
//
//								})
//						.setNegativeButton("返回",
//								new DialogInterface.OnClickListener() {// 添加返回按钮
//
//									@Override
//									public void onClick(DialogInterface dialog,
//											int which) {
//									}
//
//								}).show();
//			}
//			break;
//		case R.id.changemyinfomation:
//			if (!checkLogin()) {
//				intent = new Intent(getActivity(), MyInfomationActivity.class);
//				startActivityForResult(intent, 13);
//
//			}
//			break;
//		case R.id.wo_de_wallet:
//			if (!checkLogin()) {
//				intent = new Intent(getActivity(), WoDeQingTieAllActivity.class);
//				startActivity(intent);
//			}
//			break;
//		case R.id.wo_de_xishitang:
//			if (!checkLogin()) {
//				if (!LocalStorage.get("Authority").equals("4")) {
//					Toast.makeText(getActivity(), "您暂未拥有此权限！",
//							Toast.LENGTH_LONG).show();
//					return;
//				}
//				intent = new Intent(getActivity(), MyHallSchList.class);
//				startActivity(intent);
//			}
//			break;
//		case R.id.cookschedule:
//			if (!checkLogin()) {
//				if (!LocalStorage.get("Authority").equals("5")) {
//					Toast.makeText(getActivity(), "您暂未拥有此权限！",
//							Toast.LENGTH_LONG).show();
//					return;
//				}
//				intent = new Intent(getActivity(), MyCookSchList.class);
//				startActivity(intent);
//			}
//			break;
//		case R.id.rentcar:
//			if (!checkLogin()) {
//				intent = new Intent(getActivity(), WoDeZhiFuActivity.class);
//				startActivity(intent);
//			}
//			break;
//		case R.id.order:
//			if (!checkLogin()) {
//				intent = new Intent(getActivity(), WoDeDingDanActivity.class);
//				startActivity(intent);
//			}
//			break;
//
//		case R.id.about:
//			new Diolg(getActivity(), "拨打电话", "返回", "客服电话400-8261-707", "联系客服",
//					3);
//			break;
		default:
			break;
		}

	}

}
