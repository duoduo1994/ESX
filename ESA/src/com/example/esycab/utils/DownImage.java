package com.example.esycab.utils;

import java.io.InputStream;  
import java.net.URL;  

import android.graphics.Bitmap;  
import android.graphics.BitmapFactory;  
import android.os.AsyncTask;  
import android.widget.ImageView; 

public class DownImage extends AsyncTask<String, Void, Bitmap> {

	private ImageView imageView;
	
	 public DownImage(ImageView imageView) {
		 this.imageView = imageView;
	 }
	 
	 protected Bitmap doInBackground(String... params) {
		 String url = params[0];
		 Bitmap bitmap = null;
		 try {
			 InputStream is = new URL(url).openStream();
			 bitmap = BitmapFactory.decodeStream(is);
			 is.close();
		 } catch (Exception e) {
			 e.printStackTrace();
		 }
		 return bitmap;
	 }
	protected void onPostExecute(Bitmap result) {
		super.onPostExecute(result);
		imageView.setImageBitmap(result);
	 } 

}
