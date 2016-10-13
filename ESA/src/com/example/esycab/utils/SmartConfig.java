package com.example.esycab.utils;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import com.example.esycab.R;

import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.os.Environment;



/**
 * 
 * @author Administrator
 *	url.prpperty配置信息等
 */
public class SmartConfig {
	public static String FILE_UPLOAD_URL = "0.0.0.0"; // 文件服务器地址
	public static int FILE_UPLOAD_PORT = 8085; // 文件服务器端口
	public static String APP_SERVER_URL = "120.27.141.95"; // 玩具服务器地址
	public static String OSS_URL = "";
	public static String ROOT_CHAT_PATH = "";
	public static String ROOT_DB_PATH = "";
	public static String ROOT_IMG_PATH = "";
	Properties p = null;
	private static SmartConfig config=null;
	public static SmartConfig getInstance(){
		if(config==null){
			config=new SmartConfig();
		}
		return config;
	}

	
	/**
	 * 读取应用程序配置文件的数据
	 * @param ctx
	 * @param statusBarHeight
	 * @throws Exception
	 */
	public void propertyInit(Context ctx) {

		p = new Properties();
		try {
			p.load(ctx.getResources().openRawResource(R.raw.url));
			String fileUploadUrl = p.getProperty("FILE_UPLOAD_URL");
			if (StringUtils.isNotEmpty(fileUploadUrl)) {
				FILE_UPLOAD_URL = fileUploadUrl;
			}
			
			String fileUploadPort = p.getProperty("FILE_UPLOAD_PORT");
			if (StringUtils.isNotEmpty(fileUploadPort)) {
				FILE_UPLOAD_PORT = Integer.parseInt(fileUploadPort);
			}
			
			String appServUrl = p.getProperty("APP_SERVER_URL");
			if (StringUtils.isNotEmpty(appServUrl)) {
				APP_SERVER_URL= appServUrl;
			}
			String ossUrl=p.getProperty("OSS_URL");
			if (StringUtils.isNotEmpty(ossUrl)) {
				OSS_URL= ossUrl;
			}
			
				File file=ctx.getFilesDir();  
				String path=file.getAbsolutePath();  
				ROOT_CHAT_PATH = path+ "/smartFruit/cache/chat";
				ROOT_DB_PATH   = path + "/smartFruit/cache/db";
				ROOT_IMG_PATH  = Environment.getExternalStorageDirectory().toString() + "/smartFruit/cache/image";
//			}
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
