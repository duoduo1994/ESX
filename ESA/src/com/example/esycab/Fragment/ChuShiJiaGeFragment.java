package com.example.esycab.Fragment;


import com.example.esycab.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class ChuShiJiaGeFragment extends Fragment {

	public ChuShiJiaGeFragment(int index) {
		this.index = index;
	}

	private ImageView iv;
	private View v;
	private int index;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// System.out.println(tab+"---onCreate");
		v = LayoutInflater.from(getActivity()).inflate(R.layout.tab2, null);
		iv = (ImageView) v.findViewById(R.id.chushi_jia_ge_biao);
		if (index == 3) {
			iv.setImageResource(R.drawable.sanxingji_chushijiage);
		} else if (index == 4) {
			iv.setImageResource(R.drawable.sixingji_chushijiage);
		} else {
			iv.setImageResource(R.drawable.wuxingji_chushijiage);
		}
		super.onCreate(savedInstanceState);

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
