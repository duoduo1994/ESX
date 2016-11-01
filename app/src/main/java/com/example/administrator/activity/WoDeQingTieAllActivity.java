package com.example.administrator.activity;

import android.app.Activity;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.entity.AnLi;
import com.example.administrator.entity.QingTieSended;
import com.example.administrator.list.CommonAdapter;
import com.example.administrator.list.ViewHolder;
import com.example.administrator.myapplication.R;
import com.example.administrator.net.XUtilsHelper;
import com.example.administrator.utils.ActivityCollector;
import com.example.administrator.utils.IvListener;
import com.example.administrator.utils.LocalStorage;
import com.lidroid.xutils.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;

public class WoDeQingTieAllActivity extends Activity {
	private TextView biaoTi;
	private ListView lv;
	private List<QingTieSended> list=new ArrayList<QingTieSended>();
	private CommonAdapter<QingTieSended> caq;
	private String orderID;

	private QingTieSended qingTieSended;
	private JSONObject tJson = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON);
            //透明导航栏
          //  getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
		}else{
			this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
			this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏
		}
		setContentView(R.layout.activity_wo_de_qing_tie);
		ActivityCollector.addActivity(this);
		LocalStorage.initContext(this);
//		if(!HomeActivity.quan){
//			rl = (RelativeLayout) findViewById(R.id.g_eng_d_uo);
//			android.widget.LinearLayout.LayoutParams l = (android.widget.LinearLayout.LayoutParams) rl.getLayoutParams();
//			l.topMargin = (int) (HomeActivity.a12);
//			rl.setLayoutParams(l);
//		}
		biaoTi = (TextView) findViewById(R.id.tv_title);
		biaoTi.setText("已发送列表");
		lv = (ListView) findViewById(R.id.wode_qingtie_liebiao);
		orderID=getIntent().getStringExtra("OrderID");
		initDatas();
		setAdapter();
		findViewById(R.id.btn_back).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(System.currentTimeMillis()- HomeActivity.dianJiShiJian>500){
					HomeActivity.dianJiShiJian = System.currentTimeMillis();
				WoDeQingTieAllActivity.this.finish();
				}
			}
		});
		setMoreListener();
		}
		
	private ImageView iv_more;
		private void setMoreListener() {
			iv_more = (ImageView) findViewById(R.id.iv_more);
			iv_more.setOnClickListener(new IvListener(iv_more, WoDeQingTieAllActivity.this,0));
		}
	
	
	private void setAdapter() {
		caq=new CommonAdapter<QingTieSended>(this,list, R.layout.item_qintie_list) {
			
			@Override
			public void convert(ViewHolder helper, QingTieSended item) {
				// TODO Auto-generated method stub
				helper.setText(R.id.tv_name_qingtie_list, item.getRecvName());
				helper.setText(R.id.tv_time_qingtie_list, item.getSendDt());
			}

			@Override
			public void convert(ViewHolder helper, AnLi item) {

			}
		};
		lv.setAdapter(caq);
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if(System.currentTimeMillis()- HomeActivity.dianJiShiJian>500){
				HomeActivity.dianJiShiJian = System.currentTimeMillis();
			WoDeQingTieAllActivity.this.finish();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	private void initDatas() {


		XUtilsHelper xUtilsHelper1 = new XUtilsHelper(this,"InvitationCard.ashx?Action=GetICardsTel",1);
		RequestParams requestParams = new RequestParams();
		requestParams.addBodyParameter("Tel", LocalStorage.get("UserTel").toString());
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
				Toast.makeText(WoDeQingTieAllActivity.this,
									getString(R.string.conn_failed),
									Toast.LENGTH_SHORT).show();
							caq.notifyDataSetChanged();
			}

			@Override
			public void onNext(String s) {
				String result = new String(s);
						System.out.println(result);
						tryc(result);

			}
		});

//		RequestParams p = new RequestParams();
//		p.put("Tel", LocalStorage.get("UserTel").toString());
//		//InvitationCard.ashx?Action=GetICardsOrderID&OrderID=
//		//120.27.141.95:8085/ashx/InvitationCard.ashx?Action=GetICardsTel&Tel=13333333333
//		SmartFruitsRestClient.get("InvitationCard.ashx?Action=GetICardsTel", p,
//						String result = new String(arg2);
//						System.out.println(result);
//				new AsyncHttpResponseHandler() {
//
//					@Override
//					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
//						// TODO Auto-generated method stub
//						tryc(result);
//					}
//
//					@Override
//					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
//							Throwable arg3) {
//						// TODO Auto-generated method stub
//
//							Toast.makeText(WoDeQingTieAllActivity.this,
//									getString(R.string.conn_failed),
//									Toast.LENGTH_SHORT).show();
//							caq.notifyDataSetChanged();
//
//
//
//					}
//				});
	}
	
	private void tryc(String result){
		try {
			caq.notifyDataSetChanged();
			JSONObject json = new JSONObject(result.trim());
			JSONArray configlist = json.getJSONArray("请帖信息列表");
			if (null != configlist) {
				// System.out.println(configlist.length());
				//mCache.put("请帖信息列表", result,60*60*24);
				for (int i = 0; i < configlist.length(); i++) {
					tJson = configlist.getJSONObject(i);
					qingTieSended = new QingTieSended();
//					qingTieSended.setCallFormCgy(tJson
//							.getString("CallFormCgy"));
					qingTieSended.setPkICardID(tJson
							.getString("PkICardID"));
					qingTieSended.setRecvName(tJson
							.getString("RecvName"));
					qingTieSended.setSendDt(tJson
							.getString("SendDt"));
					list.add(qingTieSended);
					//System.out.println(lal.get(0));
				}
				caq.notifyDataSetChanged();

			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			System.out.println("G"+e.getMessage());
//			Toast.makeText(WoDeQingTieAllActivity.this,
//					e.getMessage(),
//					Toast.LENGTH_SHORT).show();
		}
	}
}
