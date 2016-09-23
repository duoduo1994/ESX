package com.example.administrator.fragment;

import java.util.List;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.administrator.entity.AnLi;

import com.example.administrator.list.CommonAdapter;
import com.example.administrator.list.ViewHolder;
import com.example.administrator.myapplication.R;
@SuppressLint("ValidFragment")
public class CanJuFragment extends Fragment {
	private List<AnLi> la;

	public CanJuFragment(List<AnLi> la) {
		this.la = la;
	}
	public CanJuFragment() {
	}




	private ListView lv1;
	private View v;
	private CommonAdapter<AnLi> caxm;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// System.out.println(tab+"---onCreate");
		v = LayoutInflater.from(getActivity()).inflate(R.layout.tab1, null);
		lv1 = (ListView) v.findViewById(R.id.l1sss);
		super.onCreate(savedInstanceState);
		System.out.println("ASD"+la);
		if(la.size()!=0){
		caxm = new CommonAdapter<AnLi>(getActivity(), la, R.layout.item_hunqinggongsi) {
			@Override
			public void convert(ViewHolder helper, AnLi item) {
				helper.setText(R.id.hunqing_gongsi, item.getName());
				//helper.setTextColor(R.id.anliss, Color.BLACK, Color.parseColor("#696969"), index);
			//	helper.setBackG(R.id.item_ziti_bg, Color.WHITE, Color.parseColor("#f5f5f5"), index);
				helper.setVis(R.id.beijing);
				helper.setVis(R.id.tiao_zhuan);
				helper.loadImage(R.id.shipei_tupian, item.getImgUrl());
			}
		};
		
		lv1.setAdapter(caxm);}
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// System.out.println(tab+"---onCreateView");
		ViewGroup vg = (ViewGroup) v.getParent();
		if (vg != null) {
			vg.removeAllViewsInLayout();
		}
		return v;
	}
	@Override
	public void onDestroy() {
		// System.out.println(tab+"---onDestroy");
		super.onDestroy();
	}

	@Override
	public void onDestroyView() {
		// System.out.println(tab+"---onDestroyView");
		super.onDestroyView();
	}

	@Override
	public void onPause() {
		// System.out.println(tab+"---onPause");
		super.onPause();
	}

	@Override
	public void onResume() {
		// System.out.println(tab+"---onResume");
		super.onResume();
	}

	@Override
	public void onStart() {
		// System.out.println(tab+"---onStart");
		super.onStart();
	}

	@Override
	public void onStop() {
		// System.out.println(tab+"---onStop");
		super.onStop();
	}
}
