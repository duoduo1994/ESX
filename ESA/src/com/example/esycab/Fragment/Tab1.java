package com.example.esycab.Fragment;

import java.util.List;

import com.example.esycab.R;
import com.example.esycab.utils.Load;
import com.eyoucab.list.son;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class Tab1 extends Fragment {
	private View v;
	private GridView gv;
	private TextView t1, leng, re;
	private String ls;
	private List<son> list;
	private MyAdapter m;
	private Context context;

	public Tab1(String ls, List<son> list, Context context) {
		this.ls = ls;
		this.list = list;
		this.context = context;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		v = LayoutInflater.from(getActivity()).inflate(R.layout.layout1, null);
		Load.getLoad(getActivity());
		gv = (GridView) v.findViewById(R.id.gridview1);

		m = new MyAdapter(list);
		gv.setAdapter(m);

		t1 = (TextView) v.findViewById(R.id.textView1);
		leng = (TextView) v.findViewById(R.id.dancai_lengcai);
		re = (TextView) v.findViewById(R.id.dancai_recai);
		t1.setText(ls);
		if ("冷菜".equals(ls)) {
			leng.setVisibility(View.GONE);
		} else {
			re.setVisibility(View.GONE);
		}
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		ViewGroup vg = (ViewGroup) v.getParent();
		if (vg != null) {
			vg.removeAllViewsInLayout();
		}
		return v;
	}

	private class MyAdapter extends BaseAdapter {
		LayoutInflater li;
		List<son> list;

		MyAdapter(List<son> list) {
			li = LayoutInflater.from(getActivity());
			this.list = list;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(final int arg0, View arg1, ViewGroup arg2) {
			ViewHolder v = null;
			if (arg1 == null) {
				arg1 = li.inflate(R.layout.text, null);
				v = new ViewHolder();
				// int a = getWindowManager().getDefaultDisplay().getWidth();
				// System.out.println(a);
				// v.image.setLayoutParams(new LayoutParams(a/4, a/4));
				v.image = (ImageView) arg1.findViewById(R.id.imgViewText);
				v.imgName = (TextView) arg1.findViewById(R.id.imgNameText);
				arg1.setTag(v);
			} else {
				v = (ViewHolder) arg1.getTag();
			}
			v.imgName.setText(list.get(arg0).getName());
			Load.imageLoader.displayImage(list.get(arg0).getImageUrl(),
					v.image, Load.options);
			v.image.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					 final Dialog dialog = new Dialog(context, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
					 ImageView imgView = getView(arg0);
					 dialog.setContentView(imgView);
					 dialog.show();

					// 点击图片消失
					imgView.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							dialog.dismiss();
						}
					});
				}
			});
			return arg1;
		}
		private ImageView getView(int arg0) {
			ImageView imgView = new ImageView(context);
			imgView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
			Load.imageLoader.displayImage(list.get(arg0).getImageUrl(),
					imgView, Load.options);

			return imgView;
		}
	}

	private class ViewHolder {
		ImageView image;
		TextView imgName;
	}

}
