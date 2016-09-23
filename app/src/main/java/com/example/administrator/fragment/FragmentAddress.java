package com.example.administrator.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;


import com.example.administrator.activity.HomeActivity;
import com.example.administrator.activity.YiJianFanKuiActivity;
import com.example.administrator.myapplication.R;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class FragmentAddress extends Fragment {
	private LinearLayout yiJian, zhiFu, guanYu,gengduo_fenxiang;
	private RelativeLayout rl;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		View view = LayoutInflater.from(getActivity()).inflate(
				R.layout.gengduo, null);
		if(!HomeActivity.quan){
			rl = (RelativeLayout) view.findViewById(R.id.gengduo_tou);
			LinearLayout.LayoutParams l = (LinearLayout.LayoutParams) rl.getLayoutParams();
			rl.setBackgroundColor(Color.parseColor("#eeeeee"));
			l.height = HomeActivity.a12;
			rl.setLayoutParams(l);
		}
		yiJian = (LinearLayout) view.findViewById(R.id.gengduo_yijian_fankui);
		zhiFu = (LinearLayout) view.findViewById(R.id.gengduo_zhifu_bangzhu);
		guanYu = (LinearLayout) view.findViewById(R.id.gengduo_guanyu_women);
		gengduo_fenxiang= (LinearLayout) view.findViewById(R.id.gengduo_fenxiang);
		yiJian.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startActivity(new Intent(getActivity(),
						YiJianFanKuiActivity.class));
			}
		});
//		gengduo_fenxiang.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View arg0) {
//				// TODO Auto-generated method stub
//				getScreenshot();
//				shareToWechat();
//			}
//
//
//		});
//		zhiFu.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View arg0) {
//				// TODO Auto-generated method stub
//				startActivity(new Intent(getActivity(),
//						ZhiFuBangZhuActivity.class));
//			}
//		});
//		guanYu.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View arg0) {
//				// TODO Auto-generated method stub
//				startActivity(new Intent(getActivity(),
//						GuanYuWoMenActivity.class));
//			}
//		});

		return view;
	}
	private void shareToWechat() {
//		OnekeyShare oks = new OnekeyShare();
//		//关闭sso授权
//		oks.disableSSOWhenAuthorize();
//		// title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间等使用
//		oks.setTitle("甬尚鲜之乡村喜宴已经上线欢迎下载");
//		// titleUrl是标题的网络链接，QQ和QQ空间等使用
//		oks.setTitleUrl("http://ysx001.nbeysx.com");
//		// text是分享文本，所有平台都需要这个字段
//		oks.setText("乡村喜事不用愁，甬尚鲜为你提供一站式的生鲜配送服务。");
//		// imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//		oks.setImagePath(getSdcardDir()+"/ScreenImage/logo"+"xcxy.jpg");//确保SDcard下面存在此张图片
//		// url仅在微信（包括好友和朋友圈）中使用
//		oks.setUrl("http://ysx001.nbeysx.com");
//		// comment是我对这条分享的评论，仅在人人网和QQ空间使S用
//		oks.setComment("乡村喜事不用愁，甬尚鲜为你提供一站式的生鲜配送服务。");
//		// site是分享此内容的网站名称，仅在QQ空间使用
//		oks.setSite("乡村喜事不用愁，甬尚鲜为你提供一站式的生鲜配送服务。");
//		// siteUrl是分享此内容的网站地址，仅在QQ空间使用
//		oks.setSiteUrl("http://ysx001.nbeysx.com");
////Toast.makeText(HomeActivity._instance, "liming33333333333333333333333333", Toast.LENGTH_LONG).show();
//		// 启动分享GUI
////		oks.setCallback(new MySharedListener());
//		oks.show(getActivity());
		
}
	private static String getSdcardDir() {
		if (Environment.getExternalStorageState().equalsIgnoreCase(
				Environment.MEDIA_MOUNTED)) {
			return Environment.getExternalStorageDirectory().toString()
					+ "/xiangcunxiyan/cache/photos";
		} else {
			return "/data/data/xiangcunxiyan/cache/photos";
		}

	}
	private void getScreenshot() {
		Bitmap bitmap=BitmapFactory.decodeResource(getResources(), R.mipmap.logo1);
		if(bitmap!=null){
			try {
            	String path = getSdcardDir()+"/ScreenImage/logo";
            	File p=new File(path);
            	String fp=path+"xcxy.jpg";
            	File file=new File(fp);
            	if(!p.exists()){ 
                    p.mkdirs(); 
                } 
                if(!file.exists()) { 
                    file.createNewFile(); 
                }
                    BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                    bos.flush();
                    bos.close();
            } catch (IOException e) {
                    e.printStackTrace();
            }
		}
		else {
		}
		}
}
