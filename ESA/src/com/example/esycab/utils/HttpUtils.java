package com.example.esycab.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;

public class HttpUtils {
	
	public static String post(String url,List <NameValuePair> params){
		try {
			HttpPost httpRequest = new HttpPost(url);   //建立HTTP POST联机
			httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			httpRequest.setHeaders(httpRequest.getAllHeaders());
			HttpResponse httpResponse;
			httpResponse = new DefaultHttpClient().execute(httpRequest);
			//取得http响应
			switch (httpResponse.getStatusLine().getStatusCode()) {
			case 200:
				HttpEntity entity = httpResponse.getEntity();
				InputStream is = entity.getContent();
				 // 读取请求内容
		        BufferedReader br = new BufferedReader(new InputStreamReader(is));
		        String line = null;
		        StringBuilder sb = new StringBuilder();
		        while((line = br.readLine())!=null){
		            sb.append(line);
		        }
				return sb.toString();
			default:
				return "";
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		return "";
	}
	
}
