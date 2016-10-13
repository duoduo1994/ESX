package com.example.esycab.utils.encrypter;

import java.util.List;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class ViewPagerAdapter extends PagerAdapter {

	// �����б�
	private List<ImageView> views;
	private Context context;

	public ViewPagerAdapter(List<ImageView> views, Context context) {
		super();
		this.views = views;
		this.context = context;
	}

	@Override
	public void destroyItem(View arg0, int arg1, Object arg2) {
		((ViewPager) arg0).removeView(views.get(arg1));
	}


	@Override
	public void finishUpdate(View arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getCount() {
		if (views != null) {
			return views.size();
		}

		return 0;
	}

	@Override
	public Object instantiateItem(View arg0, int arg1) {
		final int a = arg1;
		  View view = views.get(arg1);
		  view.setOnClickListener(new OnClickListener() {
		   
		   @Override
		   public void onClick(View v) {
		    // TODO Auto-generated method stub
//			   Toast.makeText(context, "您点击了第"+a+"图", Toast.LENGTH_SHORT).show();
		    switch (a) {
			case 0:
				
				break;
			case 1:
				break;
			default:
				break;
			}
		   }
		  });
		  ViewPager viewPager = (ViewPager) arg0;
		  viewPager.addView(view);
		  return views.get(arg1);
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return (arg0 == arg1);
	}

	@Override
	public void restoreState(Parcelable arg0, ClassLoader arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public Parcelable saveState() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void startUpdate(View arg0) {
		// TODO Auto-generated method stub

	}

}