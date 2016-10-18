package com.example.administrator.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.myapplication.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

/**
 * Created by WangYueli on 2016/10/14.
 */

public class AllFreshFragment extends Fragment {
    List<String> list;
    String url;
    @BindView(R.id.rcv_all_fresh)
    RecyclerView rcvAllFresh;
    @BindView(R.id.tv_all_fresh)
    TextView tvAllFresh;

    public static AllFreshFragment newInstance(String s) {
        Log.v(TAG, "newInstance(int imageResourceId)");

        AllFreshFragment AFragment = new AllFreshFragment();

        // Get arguments passed in, if any
//        Bundle args = imageRotatorFragment.getArguments();
        Bundle b = new Bundle(2);
        b.putString("V", s);
//        args.putInt(KEY_ARG_IMAGE_RES_ID, imageResourceId);
        AFragment.setArguments(b);

        return AFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        list = new ArrayList<>();
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.frag_all_fresh, null);
        ButterKnife.bind(this, v);
        tvAllFresh.setText(url);
        rcvAllFresh.setBackgroundColor(Color.BLUE);
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        url = getArguments().getString("V");
        Toast.makeText(getActivity(), "urlll" + url, Toast.LENGTH_LONG).show();
    }
}
