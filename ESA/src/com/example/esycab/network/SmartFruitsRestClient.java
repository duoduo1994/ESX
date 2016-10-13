package com.example.esycab.network;



import com.example.esycab.utils.SmartConfig;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.BinaryHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class SmartFruitsRestClient {
	
	private static AsyncHttpClient client =new AsyncHttpClient();
	
	 static{
	     client.setTimeout(5*1000);   //设置链接超时，如果不设置，默认为30s
	     client.setConnectTimeout(3*1000);
	 }
	
	public static void get(String url, RequestParams params, AsyncHttpResponseHandler handler)
	{
		client.get(getAbsoluteUrl(url), params, handler);
	}
	
	
	public static void get(String url,BinaryHttpResponseHandler handler){
		client.get(url, handler);
	}
	
	public static void postOSS(String url, RequestParams params, AsyncHttpResponseHandler handler)
	{
		client.post(url, params, handler);
	}
	
	public static void post(String url, RequestParams params, AsyncHttpResponseHandler handler)
	{
		client.post(getAbsoluteUrl(url), params, handler);
	}
	
	public static void postNO(String url, AsyncHttpResponseHandler handler)
	{
		client.post(getAbsoluteUrl(url), handler);
	}
	
	private static String getAbsoluteUrl(String relativeUrl) {
		// TODO Auto-generated method stub
		return SmartConfig.APP_SERVER_URL + relativeUrl;
	}
}