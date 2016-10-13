package com.example.esycab;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

import org.apache.http.Header;

import com.example.esycab.network.SmartFruitsRestClient;
import com.example.esycab.utils.LocalStorage;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.onekeyshare.OnekeyShare;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.telephony.PhoneNumberUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

public class QingTiePreviewShortActivity extends Activity {
	private String s;
	private View view;
	private String extime;
	private String[] ss;
	public static final String SENT_SMS_ACTION = "SENT_SMS_ACTION";
	private MessageReceiver mSMSReceiver = new MessageReceiver();
	private IntentFilter mSMSResultFilter = new IntentFilter();
	private boolean hasSend=false;
	private LinearLayout rl_qingtie,ll_pre_kong,ll_below;
	private TextView tv_date_pre,tv_time_pre,tv_locate_pre,tv_bingke_pre,textViewjy;
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
		setContentView(R.layout.qingtie_p);
		LocalStorage.initContext(this);
		mSMSResultFilter.addAction(SENT_SMS_ACTION);
		registerReceiver(mSMSReceiver, mSMSResultFilter);
//		if(!HomeActivity.quan){
//			rl = (RelativeLayout) findViewById(R.id.g_eng_d_uo);
//			android.widget.LinearLayout.LayoutParams l = (android.widget.LinearLayout.LayoutParams) rl.getLayoutParams();
//			l.topMargin = (int) (HomeActivity.a12);
//			rl.setLayoutParams(l);
//		}
		s=getIntent().getStringExtra("data");
		extime=getIntent().getStringExtra("extime");
		ss=s.split("#");
		initView();
		ll_pre_kong = (LinearLayout) findViewById(R.id.ll_pre_kong);// kong bai
		if(ss[5].equals("1")){
			rl_qingtie.setBackgroundResource(R.drawable.model1);
			ll_below.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 0, (float) 0.28));
			ll_pre_kong.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 0, (float) 0.45));
			chooseColor();
		}else if(ss[5].equals("3")){
			rl_qingtie.setBackgroundResource(R.drawable.model_baby);
			ll_below.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 0, (float) 0.44));
			ll_pre_kong.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 0, (float) 0.25));
		}else if(ss[5].equals("2")){
			rl_qingtie.setBackgroundResource(R.drawable.model2);
			ll_below.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 0, (float) 0.2));
			ll_pre_kong.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 0, (float) 0.45));
		}else if(ss[5].equals("4")){
			rl_qingtie.setBackgroundResource(R.drawable.moldel_nianhui);
			ll_below.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 0, (float) 0.3));
			ll_pre_kong.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 0, (float) 0.2));
		}else if(ss[5].equals("5")){
			rl_qingtie.setBackgroundResource(R.drawable.model_move);
			ll_below.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 0, (float) 0.27));
			ll_pre_kong.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 0, (float) 0.38));
			chooseColor();
		}else if(ss[5].equals("6")){
			rl_qingtie.setBackgroundResource(R.drawable.model_shou);
			chooseColor();
			ll_below.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 0, (float) 0.34));
			ll_pre_kong.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 0, (float) 0.35));
		}else if(ss[5].equals("7")){
			rl_qingtie.setBackgroundResource(R.drawable.model_xieshi);
			ll_below.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 0, (float) 0.33));
			ll_pre_kong.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 0, (float) 0.31));
		}
		
		for (String string : ss) {
			Log.i("datas",string);
		}
		
		setData();
		final Handler handler=new Handler();
		handler.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				getScreenshot(rl_qingtie);
				
				runOnUiThread( new Runnable() {
					public void run() {
							showShare();
						
					}
				});
			}
		}, 200);
	}
	
	private void setData() {
		tv_bingke_pre.setText(ss[0]);
		tv_time_pre.setText(extime);
		tv_date_pre.setText("瑾定于"+"   "+ss[4].substring(0, 9));
		tv_locate_pre.setText("席设"+"   "+ss[3]);
		
	}
	private void initView() {
		textViewjy=(TextView) findViewById(R.id.textViewjy);
		ll_below=(LinearLayout) findViewById(R.id.ll_below);
		rl_qingtie=(LinearLayout) findViewById(R.id.rl_qingtie);
		ll_pre_kong=(LinearLayout) findViewById(R.id.ll_pre_kong);
		tv_date_pre=(TextView) findViewById(R.id.tv_date_pre);
		tv_time_pre=(TextView) findViewById(R.id.tv_time_pre);
		tv_locate_pre=(TextView) findViewById(R.id.tv_locate_pre);
		tv_bingke_pre=(TextView) findViewById(R.id.tv_bingke_pre);

	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			QingTiePreviewShortActivity.this.finish();
		}
		return super.onKeyDown(keyCode, event);
	}
		

       
    
	private void showWeixinDialog() {
		new AlertDialog.Builder(QingTiePreviewShortActivity.this).setTitle("提示").setMessage("是否同时将请帖通过短信发送？")
		.setPositiveButton("发送", new AlertDialog.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				sendSMS(ss[1]);
				QingTiePreviewShortActivity.this.finish();
			}
		}).setNegativeButton("取消", new AlertDialog.OnClickListener() {
			                 
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				QingTiePreviewShortActivity.this.finish();
			}

			
		}).show();
		hasSend=true;
		
		
	}
	private void showShare() {
//		ShareSDK.initSDK(this);
		OnekeyShare oks = new OnekeyShare();
		//关闭sso授权
		oks.disableSSOWhenAuthorize(); 

		if(ss[5].equals("3")){
			ss[5]="_Baby";
		}else if(ss[5].equals("1")){
			ss[5]="_H";
		}
		// title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间等使用
		oks.setTitle("快来参加我的"+ss[8]+"吧！");
		// titleUrl是标题的网络链接，QQ和QQ空间等使用
		oks.setTitleUrl("http://120.27.141.95:8086/InvitationCard/Model"+ss[5]+".aspx?id="+ss[6]+"&&Name="+ss[0]);
		// text是分享文本，所有平台都需要这个字段
		oks.setText("尊敬的"+ss[0]+"，我将于"+ss[4]+" "+extime+"举办"+ss[8]+"啦！欢迎您携家人，在"+ss[3]+"参加吧！");
		// imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
		oks.setImagePath(getSdcardDir()+"/ScreenImage/请帖"+"qt.jpg");//确保SDcard下面存在此张图片
		// url仅在微信（包括好友和朋友圈）中使用
		oks.setUrl("http://120.27.141.95:8086/InvitationCard/Model"+ss[5]+".aspx?id="+ss[6]+"&&Name="+ss[0]);
		// comment是我对这条分享的评论，仅在人人网和QQ空间使S用
		oks.setComment("快来参加我的"+ss[8]+"吧！");
		// site是分享此内容的网站名称，仅在QQ空间使用
		oks.setSite("我的请帖");
		// siteUrl是分享此内容的网站地址，仅在QQ空间使用
		oks.setSiteUrl("http://120.27.141.95:8086/InvitationCard/Model"+ss[5]+".aspx?id="+ss[6]+"&&Name="+ss[0]);

		// 启动分享GUI
		oks.setCallback(new MySharedListener());
		oks.show(this);
		
		}
	private static String getSdcardDir() {
		if (Environment.getExternalStorageState().equalsIgnoreCase(
				Environment.MEDIA_MOUNTED)) {
			return Environment.getExternalStorageDirectory().toString()
					+ "/xiangcunxiyan/cache/photos";
		} else {
			return "/data/data/xiangcunxiyan/cache/photos";
		}

	}
	private void getScreenshot(LinearLayout rl_qingtie2) {
		view=rl_qingtie2;
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache();
		Bitmap bitmap=view.getDrawingCache();
		if(bitmap!=null){
			try {
            	String path = getSdcardDir()+"/ScreenImage/请帖";
            	File p=new File(path);
            	String fp=path+"qt.jpg";
            	File file=new File(fp);
            	if(!p.exists()){ 
                    p.mkdirs(); 
                } 
                if(!file.exists()) { 
                    file.createNewFile(); 
                }
                    BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                    bos.flush();
                    bos.close();
            } catch (IOException e) {
                    e.printStackTrace();
            }
		}
		else {
		}
		}
	public void chooseColor(){
		tv_bingke_pre.setTextColor(Color.parseColor("#ffffff"));
		tv_locate_pre.setTextColor(Color.parseColor("#ffffff"));
		tv_time_pre.setTextColor(Color.parseColor("#ffffff"));
		tv_date_pre.setTextColor(Color.parseColor("#ffffff"));
		textViewjy.setTextColor(Color.parseColor("#ffffff"));
		
	}
	public class MySharedListener implements PlatformActionListener{  
        @Override  
        public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {  
                                Log.i("完成了","完成了");
                                putData();
                                showWeixinDialog();
                                
                                
                                
        }  
  
        @Override  
        public void onError(Platform platform, int i, Throwable throwable) {  
                                Log.i("出错了","完成了");  
  
        }  
  
        @Override  
        public void onCancel(Platform platform, int i) {  
                                Log.i("取消了","完成了");  
  
        }  
    } 
	public void sendSMS(String phonenumber) {// 发送短信的类
//		SmsManager smsManager = SmsManager.getDefault();
//		Pattern pattern = Pattern.compile("^[1][3-8][0-9]{9}$");
//		Matcher number = pattern.matcher(phonenumber);
//		s = name + "#" + tel + "#" + mname + "#" + locate + "#"
//				+ time + "#" + itemid + "#" + orderID + "#"
//				+ chenghu + "#" + type;
//		String datas = orderID + "#" + tel + "#" + time+" "+extime + "#" + name + "#"
//				+ "mname" + "#"+ LocalStorage.get(QingTieXinXiActivity.this, "UserTel") + "#"
//				+ itemid;
//		Log.i("dadadadadadadadad", datas);
//		s = name + "#" + tel + "#" + mname + "#" + locate + "#" + time + "#"
//				+ itemid + "#" + orderID + "#" + chenghu + "#" + type;
		String massage = "尊敬的"+ss[0]+"，我将于" + ss[4] +" "+ extime+"举办" + ss[8] + "啦！欢迎您携家人，在"
				+ ss[3] + "参加我的喜宴！";
		
//			List<String> contentsList = smsManager.divideMessage(massage);
//			Intent itSend = new Intent(SENT_SMS_ACTION);
//			itSend.putExtra("post_data", s);
//			PendingIntent PI = PendingIntent.getBroadcast(
//					getApplicationContext(), 1, itSend,
//					PendingIntent.FLAG_ONE_SHOT);
//			for (String string : contentsList) {
//				smsManager.sendTextMessage(phonenumber, null, string, PI, null);
//			}
			/** 
		     * 调起系统发短信功能 
		     * @param phoneNumber 
		     * @param message 
		     */  
		        if(PhoneNumberUtils.isGlobalPhoneNumber(phonenumber)){  
		            Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:"+phonenumber));            
		            intent.putExtra("sms_body", massage);            
		            startActivity(intent);  
		        }  
	}
	public void putData(){
//		s = name + "#" + tel + "#" + mname + "#" + locate + "#"
//		+ time + "#" + itemid + "#" + orderID + "#"
//		+ chenghu + "#" + type;
		RequestParams p=new RequestParams();
 		//p.put("OrderID", 1+"");订单号码，可以不传
 		p.put("OrderID", ss[6]);
 		p.put("RecvTel", ss[1]);
 		p.put("CallFormCgy", ss[7]);
 		p.put("FeastDt",  ss[4]+" "+extime);
 		p.put("RecvName", ss[0]);
 		p.put("HostName",ss[2]);
 		p.put("Tel", LocalStorage.get("UserTel").toString());
 		p.put("Model", "模板"+ss[5]);
 		SmartFruitsRestClient.post("InvitationCard.ashx?Action=InvitationCard", p, new AsyncHttpResponseHandler() {
 			
 			@Override
 			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
 				// TODO Auto-generated method stub
// 				String result=new String(arg2);
 			}
 			
 			@Override
 			public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
 				// TODO Auto-generated method stub
 			}
 		});
	}
//	@Override
//	protected void onResume() {
//		// TODO Auto-generated method stub
//		putData();
//        sendSMS(ss[1]);
//		super.onResume();
//	}
	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		if(!hasSend){
			putData();
			showWeixinDialog();
		}
		super.onRestart();
	}
}
