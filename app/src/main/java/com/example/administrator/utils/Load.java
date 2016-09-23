package com.example.administrator.utils;

import java.io.File;

import android.content.Context;


import com.example.administrator.myapplication.R;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.utils.StorageUtils;


public class Load {

	public static ImageLoader imageLoader;

	public static DisplayImageOptions options;

	public static void getLoad(Context context) {
		if (imageLoader == null) {
			File cacheDir = StorageUtils.getOwnCacheDirectory(context, "/Android/data/com.example.esycab/cache/ACache");
			ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
					context).threadPriority(Thread.NORM_PRIORITY - 2)
			// �����̵߳����ȼ�
					
					.threadPoolSize(5)
					.threadPriority(Thread.MIN_PRIORITY + 3)
					.denyCacheImageMultipleSizesInMemory()
					.discCacheFileNameGenerator(new Md5FileNameGenerator())
					.memoryCache(new UsingFreqLimitedMemoryCache(2000000))
					// 你可以传入自己的内存缓存
					.discCache(new UnlimitedDiskCache(cacheDir))
					// 你可以传入自己的磁盘缓存
					.tasksProcessingOrder(QueueProcessingType.LIFO)
					.defaultDisplayImageOptions(
							DisplayImageOptions.createSimple())
					// ����ͼƬ���غ���ʾ�Ĺ�����������
					.build();

			imageLoader = ImageLoader.getInstance();
			imageLoader.init(config);

			options = new DisplayImageOptions.Builder()
					.showStubImage(R.mipmap.nuli)
					// ����ͼƬ�������ڼ���ʾ��ͼƬ
					.showImageForEmptyUri(R.mipmap.nopic)
					// ����ͼƬUriΪ�ջ��Ǵ����ʱ����ʾ��ͼƬ
					.showImageOnFail(R.mipmap.nopic)
					// ����ͼƬ����/�������д���ʱ����ʾ��ͼƬ
					.cacheInMemory(true)// �Ƿ񾏵��ȴ���
					.cacheOnDisc(true)// �Ƿ񾏴浽sd����
					.build();
		}
	}
}
