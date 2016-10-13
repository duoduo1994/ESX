package com.eyoucab.list;

import java.io.Serializable;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.esycab.R;
import com.example.esycab.WoDeDingDanXQ;
import com.example.esycab.entity.AnLi;
import com.example.esycab.utils.Load;
import com.eyoucab.list.ImageLoader.Type;

/**
 * 通用的ViewHolder
 * 
 * @author Administrator
 * 
 */
public class ViewHolder implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3834055413473537830L;
	private final SparseArray<View> mViews;
	private int mPosition;
	private View mConvertView;
	private Context context;

	private ViewHolder(Context context, ViewGroup parent, int layoutId,
			int position) {
		this.context = context;
		this.mPosition = position;
		this.mViews = new SparseArray<View>();
		mConvertView = LayoutInflater.from(context).inflate(layoutId, parent,
				false);
		Load.getLoad(context);
		// setTag
		mConvertView.setTag(this);
	}

	/**
	 * 拿到一个ViewHolder对象
	 * 
	 * @param context
	 * @param convertView
	 * @param parent
	 * @param layoutId
	 * @param position
	 * @return
	 */
	public static ViewHolder get(Context context, View convertView,
			ViewGroup parent, int layoutId, int position) {
		if (convertView == null) {
			return new ViewHolder(context, parent, layoutId, position);
		}
		return (ViewHolder) convertView.getTag();
	}

	public View getConvertView() {
		return mConvertView;
	}

	/**
	 * 通过控件的Id获取对于的控件，如果没有则加入views
	 * 
	 * @param viewId
	 * @return
	 */
	public <T extends View> T getView(int viewId) {
		View view = mViews.get(viewId);
		if (view == null) {
			view = mConvertView.findViewById(viewId);
			mViews.put(viewId, view);
		}
		return (T) view;
	}

	/**
	 * 为TextView设置字符串
	 * 
	 * @param viewId
	 * @param text
	 * @return
	 */
	public ViewHolder setText(int viewId, String text) {
		TextView view = getView(viewId);
		view.setText(text);
		return this;
	}

	public ViewHolder setTextE(int viewId, String text) {
		EditText view = getView(viewId);
		view.setText(text);
		return this;
	}

	public ViewHolder setTextR(int viewId, float text) {
		RatingBar view = getView(viewId);
		view.setRating(text);
		view.setClickable(false);
		return this;
	}

	public ViewHolder setVis(int viewId) {
		View view = getView(viewId);
		view.setVisibility(view.GONE);
		return this;
	}

	public void click(int viewID, int ming, final int tu,
			final Context context, int a, final AnLi anli, final TextView vID,
			final son s) {

		if (a == 1) {
			TextView view = getView(viewID);
			final TextView v = getView(ming);
			ImageView t = getView(tu);
			view.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					String id;
					String name;
					String tuPian;
					id = s.getCover_url_small();
					name = s.getName();
					tuPian = s.getImageUrl();
					s.setCover_url_small(s.getTiID());
					s.setName(s.getTiName());
					s.setImageUrl(s.getTiTuPian());
					s.setTiID(id);
					s.setTiName(name);
					s.setTiTuPian(tuPian);
					v.setText(s.getName());
					loadImage(tu, s.getImageUrl());

				}
			});
		} else if (6 == a) {
			final ImageView view = getView(viewID);

			view.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub

					if (anli.isA()) {
						view.setImageResource(R.drawable.checkbox_normal);
						jiaGe = jiaGe - anli.getPkCaseID();
						anli.setA(false);
					} else {
						view.setImageResource(R.drawable.checkbox_pressed);
						jiaGe = jiaGe + anli.getPkCaseID();
						anli.setA(true);
					}
					vID.setText(jiaGe + "");
				}
			});

		}

	}

	int jiaGe = 0;

	public ViewHolder loadImage(int viewId, String url) {
		ImageView view = getView(viewId);
		Load.imageLoader.displayImage(url, view, Load.options);
		return this;

	}

	public ViewHolder setTC(int viewId, int color) {
		TextView view = getView(viewId);
		view.setTextColor(color);
		return this;

	}

	public void setBack(int position, int viewID) {
		TextView view = getView(viewID);
		if (mPosition == position) {
			view.setBackgroundColor(Color.WHITE);
			view.setTextColor(Color.BLACK);
		} else {
			view.setBackgroundColor(Color.parseColor("#F5F5F5"));
			view.setTextColor(Color.parseColor("#adadad"));
		}
	}

	public void setBackG(int viewID, int color, int otherColor, int position) {
		LinearLayout view = getView(viewID);
		if (mPosition == position) {
			view.setBackgroundColor(color);
		} else {
			view.setBackgroundColor(otherColor);
		}
	}

	public void setZiXuan(int viewID, int position) {
		TextView view = getView(viewID);
		if (mPosition == position) {
			view.setBackgroundResource(R.drawable.zixuan_l);
		} else {
			view.setBackgroundResource(R.drawable.zixuan_s);
		}
	}

	public void setVistable(int viewID) {
		View view = getView(viewID);
		view.setVisibility(view.VISIBLE);
	}

	public ViewHolder setTextColor(int viewId, int color, int otherColor,
			int index) {
		TextView view = getView(viewId);
		if (mPosition == index) {
			view.setTextColor(color);
		} else {
			view.setTextColor(otherColor);
		}
		return this;
	}

	/**
	 * 为ImageView设置图片
	 * 
	 * @param viewId
	 * @param drawableId
	 * @return
	 */
	public ViewHolder setImageResource(int viewId, int drawableId) {
		ImageView view = getView(viewId);
		view.setImageResource(drawableId);

		return this;
	}

	/**
	 * 为ImageView设置图片
	 * 
	 * @param viewId
	 * @param drawableId
	 * @return
	 */
	public ViewHolder setImageBitmap(int viewId, Bitmap bm) {
		ImageView view = getView(viewId);
		view.setImageBitmap(bm);
		return this;
	}

	/**
	 * 为ImageView设置图片
	 * 
	 * @param viewId
	 * @param drawableId
	 * @return
	 */
	public ViewHolder setImageByUrl(int viewId, String url) {
		ImageLoader.getInstance(3, Type.LIFO).loadImage(url,
				(ImageView) getView(viewId));
		return this;
	}

	public int getPosition() {
		return mPosition;
	}

}
