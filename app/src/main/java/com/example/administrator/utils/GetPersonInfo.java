package com.example.administrator.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.text.TextUtils;


public class GetPersonInfo {
	
	//声明接口
	CallBackInfo callBackInfo;
	
	//接口
	public interface CallBackInfo{
		public void getSuccese();
		public void getFailure();
	}
	
	//被调用，执行接口实现
	public void callListenSuccese(){
		callBackInfo.getSuccese();
		callBackInfo.getFailure();
	}
	//被调用，执行接口实现
	public void callListenFailure(){
		callBackInfo.getFailure();
	}
	
	//外部进行接口实现
    public void setCallBackListener(CallBackInfo callBackInfo) {
        this.callBackInfo = callBackInfo;
    }
	
	public GetPersonInfo(){};
	
	LoadingDialog dialog=null;
	
	public void getInfo(Context context,String userTel){
		dialog=new LoadingDialog(context, "数据正在加载中...");
		dialog.showDialog();
		System.out.println("^^^^^^^^^^^^^"+userTel);
//		RequestParams params=new RequestParams();
//		params.add("UserTel", userTel);
//		SmartFruitsRestClient.post("LoginCheckHandler.ashx?Action=userInfo", params, new AsyncHttpResponseHandler() {
//
//			@Override
//			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
//				// TODO Auto-generated method stub
//				dialog.closeDialog();
//				String result=new String(arg2);
//				System.out.println("#################"+result);
//				try {
//					if(TextUtils.isEmpty(result)){
//						return;
//					}
//					JSONObject jsonObject = new JSONObject(result.trim());
//					JSONArray jsArray = jsonObject.getJSONArray("用户信息");
//					JSONObject json = jsArray.getJSONObject(0);
//
//					LocalStorage.set("Sex",json.getString("Sex"));
//
//					if (!TextUtils.isEmpty(json.getString("BornDateTime"))) {
//						SimpleDateFormat sim1 = new SimpleDateFormat(
//								"yyyy/MM/dd HH:mm:ss");
//						SimpleDateFormat sim2 = new SimpleDateFormat(
//								"yyyy-MM-dd");
//						String s = json.getString("BornDateTime").replace("\\","");
//						Date d = sim1.parse(s);
//						LocalStorage.set("BornDateTime",sim2.format(d));
//					}
//					if (!TextUtils.isEmpty(json.getString("ImageUrl"))) {
//						String ss = json.getString("ImageUrl").replace("\\", "").trim();
//						LocalStorage.set("ImageUrl", ss);
//					}
//					LocalStorage.set("UpdateDT", json.getString("UpdateDT"));
//					LocalStorage.set("Authority",json.getString("Authority"));
//					LocalStorage.set("HomeAddress",json.getString("HomeAddress"));
//					LocalStorage.set("NowAddress",json.getString("NowAddress"));
//					LocalStorage.set("RealName",json.getString("RealName"));
//					LocalStorage.set("NickName",json.getString("NickName"));
//
//					callListenSuccese();
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//
//			}
//
//			@Override
//			public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
//				// TODO Auto-generated method stub
//				dialog.closeDialog();
//				callListenFailure();
//			}
//		});
		
	}
	
}
