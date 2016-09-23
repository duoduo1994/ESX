/**
 * 文件名：图片加载
 * 说明：用于加载图片
 * 公司名：宁波驿邮
 * 作成者：励明
 * 作成日：2016/6/1
 */

package com.example.administrator.utils;



/**
 * 图片加载类
 * @author 励明
 */
import android.content.Context;


import com.example.administrator.myapplication.R;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

public class CarLoad {

	public static ImageLoader imageLoader;
	
	public static DisplayImageOptions options;

    public	static void getLoad(Context context) {
		if(imageLoader==null){
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context).threadPriority(Thread.NORM_PRIORITY - 2)
				// 设置线程的优先级
				.denyCacheImageMultipleSizesInMemory()
				// 当同一个Uri获取不同大小的图片，缓存到内存时，只缓存一个。
				//默认会缓存多个不同的大小的相同图片
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				// 设置缓存文件的名字
				.discCacheFileCount(60)
				// 缓存文件的最大个数
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				// 设置图片下载和显示的工作队列排序
				 .imageDownloader(new BaseImageDownloader(context,5 * 1000, 30 * 1000)) 
				.build();

		imageLoader = ImageLoader.getInstance();
		imageLoader.init(config);

		options = new DisplayImageOptions.Builder()
				.showStubImage(R.mipmap.nuli)
				// 设置图片在下载期间显示的图片
				.showImageForEmptyUri(R.mipmap.nopic)
				// 设置图片Uri为空或是错误的时候显示的图片
				.showImageOnFail(R.mipmap.nopic)
				// 设置图片加载/解码过程中错误时候显示的图片
				.cacheInMemory(true)// 是否緩到內存中
				.cacheOnDisc(true)// 是否緩存到sd卡上
				.build();
		}
	}
}
