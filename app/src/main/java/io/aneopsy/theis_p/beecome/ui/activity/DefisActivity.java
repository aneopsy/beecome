package io.aneopsy.theis_p.beecome.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.OvershootInterpolator;

import butterknife.Bind;
import butterknife.OnClick;
import io.aneopsy.theis_p.beecome.R;
import io.aneopsy.theis_p.beecome.Utils;
import io.aneopsy.theis_p.beecome.ui.adapter.DefisAdapter;
import io.aneopsy.theis_p.beecome.ui.adapter.DefisItemAnimator;
import io.aneopsy.theis_p.beecome.ui.view.DefisContextMenu;
import io.aneopsy.theis_p.beecome.ui.view.DefisContextMenuManager;
import io.aneopsy.theis_p.beecome.ui.view.RevealBackgroundView;

public class DefisActivity extends BaseDrawerActivity implements RevealBackgroundView.OnStateChangeListener, DefisAdapter.OnDefisItemClickListener,
        DefisContextMenu.OnDefisContextMenuItemClickListener {
    public static final String ACTION_SHOW_LOADING_ITEM = "action_show_loading_item";
    public static final String ARG_REVEAL_START_LOCATION = "reveal_start_location";
    private static final int ANIM_DURATION_TOOLBAR = 300;
    private static final int ANIM_DURATION_FAB = 400;
    @Bind(R.id.rvDefis)
    RecyclerView rvDefis;
    @Bind(R.id.btnCreate)
    FloatingActionButton fabCreate;
    @Bind(R.id.vRevealBackground)
    RevealBackgroundView vRevealBackground;
    private DefisAdapter defisAdapter;
    private boolean pendingIntroAnimation;
    public static void startUserProfileFromLocation(int[] startingLocation, Activity startingActivity) {
        Intent intent = new Intent(startingActivity, DefisActivity.class);
        intent.putExtra(ARG_REVEAL_START_LOCATION, startingLocation);
        startingActivity.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_defis);
        setupdefis();
        if (savedInstanceState == null) {
            pendingIntroAnimation = true;
        } else {
            defisAdapter.updateItems(false);
        }
        setupRevealBackground(savedInstanceState);
    }
    private void setupdefis() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this) {
            @Override
            protected int getExtraLayoutSpace(RecyclerView.State state) {
                return 300;
            }
        };
        rvDefis.setLayoutManager(linearLayoutManager);
        defisAdapter = new DefisAdapter(this);
        defisAdapter.setOnDefisItemClickListener(this);
        rvDefis.setAdapter(defisAdapter);
        rvDefis.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                DefisContextMenuManager.getInstance().onScrolled(recyclerView, dx, dy);
            }
        });
        rvDefis.setItemAnimator(new DefisItemAnimator());
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
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (ACTION_SHOW_LOADING_ITEM.equals(intent.getAction())) {
            showdefisLoadingItemDelayed();
        }
    }
    private void showdefisLoadingItemDelayed() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                rvDefis.smoothScrollToPosition(0);
                defisAdapter.showLoadingView();
            }
        }, 500);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        if (pendingIntroAnimation) {
            pendingIntroAnimation = false;
            startIntroAnimation();
        }
        return true;
    }
    private void startIntroAnimation() {
        fabCreate.setTranslationY(2 * getResources().getDimensionPixelOffset(R.dimen.btn_fab_size));
        int actionbarSize = Utils.dpToPx(56);
        getToolbar().setTranslationY(-actionbarSize);
        getIvLogo().setTranslationY(-actionbarSize);
        getInboxMenuItem().getActionView().setTranslationY(-actionbarSize);

        getToolbar().animate()
                .translationY(0)
                .setDuration(ANIM_DURATION_TOOLBAR)
                .setStartDelay(300);
        getIvLogo().animate()
                .translationY(0)
                .setDuration(ANIM_DURATION_TOOLBAR)
                .setStartDelay(400);
        getInboxMenuItem().getActionView().animate()
                .translationY(0)
                .setDuration(ANIM_DURATION_TOOLBAR)
                .setStartDelay(500)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        startContentAnimation();
                    }
                })
                .start();
    }
    private void startContentAnimation() {
        fabCreate.animate()
                .translationY(0)
                .setInterpolator(new OvershootInterpolator(1.f))
                .setStartDelay(300)
                .setDuration(ANIM_DURATION_FAB)
                .start();
        defisAdapter.updateItems(true);
    }
    @Override
    public void onCommentsClick(View v, int position) {
        final Intent intent = new Intent(this, CommentsActivity.class);
        int[] startingLocation = new int[2];
        v.getLocationOnScreen(startingLocation);
        intent.putExtra(CommentsActivity.ARG_DRAWING_START_LOCATION, startingLocation[1]);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }
    @Override
    public void onMoreClick(View v, int itemPosition) {
        DefisContextMenuManager.getInstance().toggleContextMenuFromView(v, itemPosition, this);
    }
    @Override
    public void onProfileClick(View v) {
        int[] startingLocation = new int[2];
        v.getLocationOnScreen(startingLocation);
        startingLocation[0] += v.getWidth() / 2;
        UserProfileActivity.startUserProfileFromLocation(startingLocation, this);
        overridePendingTransition(0, 0);
    }
    @Override
    public void onReportClick(int defisItem) {DefisContextMenuManager.getInstance().hideContextMenu();}
    @Override
    public void onSharePhotoClick(int defisItem) {DefisContextMenuManager.getInstance().hideContextMenu();}
    @Override
    public void onCopyShareUrlClick(int defisItem) {DefisContextMenuManager.getInstance().hideContextMenu();}
    @Override
    public void onCancelClick(int defisItem) {DefisContextMenuManager.getInstance().hideContextMenu();}
    @Override
    public void onStateChange(int state) {}
    @OnClick(R.id.btnCreate)
    public void onTakePhotoClick() {
        int[] startingLocation = new int[2];
        fabCreate.getLocationOnScreen(startingLocation);
        startingLocation[0] += fabCreate.getWidth() / 2;
        TakePhotoActivity.startCameraFromLocation(startingLocation, this);
        overridePendingTransition(0, 0);
    }
}