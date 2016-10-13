package com.example.esycab.utils;

import java.io.File;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

public class UploadImageThread extends Thread {
	
	public String IpAddress=new SmartConfig().APP_SERVER_URL;
	private File mImageFile;
	UploadImageListener mUploadImageListener;
	Context context;

	public UploadImageThread(Context context,File imageFile) {
		this.context=context;
		this.mImageFile = imageFile;
	}

	public void setUploadImageListener(UploadImageListener uploadImageListener) {
		mUploadImageListener = uploadImageListener;
	}

	public interface UploadImageListener {
		public void uploadImageFail();

		public void uploadImageSuccess();
	}

	@Override
	public void run() {

		HttpClient httpclient = new DefaultHttpClient();
		// 设置通信协议版本
		httpclient.getParams().setParameter(
				CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);

		HttpPost httppost = new HttpPost(IpAddress+
				"LoginCheckHandler.ashx?Action=UpUser&UserTel="
						+ LocalStorage.get("UserTel").toString()
						+ "&UserPass="
						+ MD5Util.string2MD5(LocalStorage.get("UserPass")
								.toString()));

		MultipartEntity mpEntity = new MultipartEntity(); // 文件传输
		ContentBody cbFile = new FileBody(mImageFile);
		mpEntity.addPart("userfile", cbFile); // <input type="file"
												// name="userfile" /> 对应的

		httppost.setEntity(mpEntity);
		System.out.println("executing request " + httppost.getRequestLine());
		try {
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity resEntity = response.getEntity();

			System.out.println(response.getStatusLine());// 通信Ok
			String json = "";
			String path = "";
			if (resEntity != null) {
				// System.out.println(EntityUtils.toString(resEntity,"utf-8"));
				json = EntityUtils.toString(resEntity, "utf-8");
				JSONObject p = null;
				try {
					p = new JSONObject(json);
					path = p.getString("结果");
					if(path.equals("失败")){
						Toast.makeText(context, "头像上传失败，请重试！", Toast.LENGTH_SHORT).show();
					}
					else{
						File file = new File(Environment
								.getExternalStorageDirectory().toString()
								+ "/EFHM/cache/photo/myHead.jpg");
						if (file.exists()) {
							file.delete();
							System.out.println("delete succ");
						}
						LocalStorage.set("ImageUrl",path);
					}
					
					System.out.println("path"+path);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (resEntity != null) {
				resEntity.consumeContent();
			}

		} catch (Exception e) {
			// TODO: handle exception
		}

		httpclient.getConnectionManager().shutdown();
	}
}
