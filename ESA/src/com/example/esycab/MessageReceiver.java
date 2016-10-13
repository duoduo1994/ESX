package com.example.esycab;

import org.apache.http.Header;

import com.example.esycab.network.SmartFruitsRestClient;
import com.example.esycab.utils.LocalStorage;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

public class MessageReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context arg0, Intent arg1) {
		// TODO Auto-generated method stub
		String data=arg1.getStringExtra("post_data");
		String [] datas=data.split("#");
		 switch(getResultCode())  
         {  
             case Activity.RESULT_OK:  
            	 Toast.makeText(arg0, "短信发送成功！", 1).show();
//            	 RequestParams p=new RequestParams();
//         		//p.put("OrderID", 1+"");订单号码，可以不传
//         		p.put("OrderID", datas[0]);
//         		p.put("RecvTel", datas[1]);
//         		p.put("CallFormCgy", datas[2]);
//         		p.put("FeastDt",  datas[2]);
//         		p.put("RecvName", datas[3]);
//         		p.put("HostName",datas[4]);
//         		p.put("Tel", datas[5]);
//         		p.put("Model", "模板"+datas[6]);
//         		SmartFruitsRestClient.post("InvitationCard.ashx?Action=InvitationCard", p, new AsyncHttpResponseHandler() {
//         			
//         			@Override
//         			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
//         				// TODO Auto-generated method stub
//         				String result=new String(arg2);
//         				Log.i("短信发送成功！","短信发送成功！");
//         			}
//         			
//         			@Override
//         			public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
//         				// TODO Auto-generated method stub
//         			}
//         		});
            	 
            	 break;  
             case SmsManager.RESULT_ERROR_GENERIC_FAILURE:  
             case SmsManager.RESULT_ERROR_RADIO_OFF:  
             case SmsManager.RESULT_ERROR_NULL_PDU:  
             default:  
                 //System.err.println("Send Message to "+phoneNum+" fail!");
            	 Toast.makeText(arg0, "短信发送失败！", 1).show();
                 break;  
         } 
	}

}
