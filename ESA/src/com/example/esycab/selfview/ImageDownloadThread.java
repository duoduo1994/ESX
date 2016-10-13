package com.example.esycab.selfview;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.example.esycab.StartActivity;
import com.example.esycab.utils.ProConst;
import com.example.esycab.utils.SmartConfig;

public class ImageDownloadThread implements Runnable, ProConst {

	private String uri;
	private String fileName;
	FileTransferListener transferListern;

	public ImageDownloadThread(String uri, String fileName) {
		this.uri = uri;
		this.fileName = fileName;
	}

	public void addDownloadTransferListener(FileTransferListener listener) {
		this.transferListern = listener;
		StartActivity.exec.execute(this);
	}

	@Override
	public void run() {
		// 从网络上获取图片
		URL url;
		transferListern.onFileTransferBegin();
		try {
			if (uri == null) {
				return;
			}
			url = new URL(uri);
			File file = new File(SmartConfig.ROOT_IMG_PATH);
			if (!file.exists()) {
				file.mkdirs();
			}
			File newFile = new File(SmartConfig.ROOT_IMG_PATH + "/" + fileName);
			if (!newFile.exists()) {
				newFile.createNewFile();
			}
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5000);
			conn.setRequestMethod("GET");
			conn.setDoInput(true);
			if (conn.getResponseCode() == 200) {
				InputStream is = conn.getInputStream();
				FileOutputStream fos = new FileOutputStream(newFile);
				byte[] buffer = new byte[128];
				int len = 0;
				while ((len = is.read(buffer)) != -1) {
					fos.write(buffer, 0, len);
				}
				is.close();
				fos.close();
				transferListern.onFileTransferEnd(fileName);
			}
			this.uri = null;
			this.fileName = null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			transferListern.onFileTransfailed();
		}
	}
}
