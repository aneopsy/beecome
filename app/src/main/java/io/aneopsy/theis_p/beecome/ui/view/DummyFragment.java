package io.aneopsy.theis_p.beecome.ui.view;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

import io.aneopsy.theis_p.beecome.R;
import io.aneopsy.theis_p.beecome.ui.utils.VersionModel;
import io.aneopsy.theis_p.beecome.ui.adapter.SimpleRecyclerAdapter;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

public class DummyFragment extends Fragment {
    int color;
    int img;
    SimpleRecyclerAdapter adapter;
    public DummyFragment() {
    }
    @SuppressLint("ValidFragment")
    public DummyFragment(int color, int img) {
        this.color = color;
        this.img = img;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dummy_fragment, container, false);
        final FrameLayout frameLayout = (FrameLayout) view.findViewById(R.id.dummyfrag_bg);
        frameLayout.setBackgroundColor(color);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.dummyfrag_scrollableview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getBaseContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new SlideInUpAnimator(new OvershootInterpolator(1f)));
        List<Integer> list = new ArrayList<Integer>();
        /*for (int i = 0; i < VersionModel.data.length; i++) {
            list.add(VersionModel.icon[i]);
        }*/
        list.add(VersionModel.icon[img]);
        adapter = new SimpleRecyclerAdapter(list);
        recyclerView.setAdapter(adapter);
        return view;
    }
}