package io.aneopsy.theis_p.beecome.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewTreeObserver;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import io.aneopsy.theis_p.beecome.R;
import io.aneopsy.theis_p.beecome.ui.adapter.ViewPagerAdapter2;
import io.aneopsy.theis_p.beecome.ui.view.DummyFragment2;
import io.aneopsy.theis_p.beecome.ui.view.RevealBackgroundView;


public class FrigoActivity extends BaseDrawerActivity implements RevealBackgroundView.OnStateChangeListener {

    public static final String ARG_REVEAL_START_LOCATION = "reveal_start_location";
    private RecyclerView recyclerView;

    private List<MyObject> cities = new ArrayList<>();

    @Bind(R.id.vRevealBackground)
    RevealBackgroundView vRevealBackground;

    int icons[] = {
            R.drawable.ent,
            R.drawable.pla,
            R.drawable.des,
            R.drawable.boi,
            R.drawable.con,
            R.drawable.sur
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frigo);

        setupUserProfileGrid();
    }

    private void setupUserProfileGrid() {
        ViewPager viewPager = (ViewPager) findViewById(R.id.tabanim_viewpager);
        setupViewPager(viewPager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabanim_tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(icons[0]);
        tabLayout.getTabAt(1).setIcon(icons[1]);
        tabLayout.getTabAt(2).setIcon(icons[2]);
        tabLayout.getTabAt(3).setIcon(icons[3]);
        tabLayout.getTabAt(4).setIcon(icons[4]);
        tabLayout.getTabAt(5).setIcon(icons[5]);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter2 adapter = new ViewPagerAdapter2(getSupportFragmentManager());
        adapter.addFrag(new DummyFragment2(getResources().getColor(R.color.primary), 0), "");
        adapter.addFrag(new DummyFragment2(getResources().getColor(R.color.primary), 1), "");
        adapter.addFrag(new DummyFragment2(getResources().getColor(R.color.primary), 2), "");
        adapter.addFrag(new DummyFragment2(getResources().getColor(R.color.primary), 3), "");
        adapter.addFrag(new DummyFragment2(getResources().getColor(R.color.primary), 4), "");
        adapter.addFrag(new DummyFragment2(getResources().getColor(R.color.primary), 5), "");
        viewPager.setAdapter(adapter);
    }

    private void setupRevealBackground(Bundle savedInstanceState) {
        vRevealBackground.setOnStateChangeListener(this);
        if (savedInstanceState == null) {
            final int[] startingLocation = getIntent().getIntArrayExtra(ARG_REVEAL_START_LOCATION);
            vRevealBackground.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    vRevealBackground.getViewTreeObserver().removeOnPreDrawListener(this);
                    vRevealBackground.startFromLocation(startingLocation);
                    return true;
                }
            });
        } else {
            vRevealBackground.setToFinishedFrame();
        }
    }

    public static void startUserProfileFromLocation(int[] startingLocation, Activity startingActivity) {
        Intent intent = new Intent(startingActivity, FrigoActivity.class);
        intent.putExtra(ARG_REVEAL_START_LOCATION, startingLocation);
        startingActivity.startActivity(intent);
    }

    @Override
    public void onStateChange(int state) {}
}