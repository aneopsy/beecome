package io.aneopsy.theis_p.beecome.ui.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.OvershootInterpolator;

import io.aneopsy.theis_p.beecome.Utils;

public class DefisContextMenuManager extends RecyclerView.OnScrollListener implements View.OnAttachStateChangeListener {

    private static DefisContextMenuManager instance;

    private DefisContextMenu contextMenuView;

    private boolean isContextMenuDismissing;
    private boolean isContextMenuShowing;

    public static DefisContextMenuManager getInstance() {
        if (instance == null) {
            instance = new DefisContextMenuManager();
        }
        return instance;
    }

    private DefisContextMenuManager() {

    }

public void toggleContextMenuFromView(View openingView, int defisItem, DefisContextMenu.OnDefisContextMenuItemClickListener listener) {
    if (contextMenuView == null) {
        showContextMenuFromView(openingView, defisItem, listener);
    } else {
        hideContextMenu();
    }
}

    private void showContextMenuFromView(final View openingView, int defisItem, DefisContextMenu.OnDefisContextMenuItemClickListener listener) {
        if (!isContextMenuShowing) {
            isContextMenuShowing = true;
            contextMenuView = new DefisContextMenu(openingView.getContext());
            contextMenuView.bindToItem(defisItem);
            contextMenuView.addOnAttachStateChangeListener(this);
            contextMenuView.setOnDefisMenuItemClickListener(listener);

            ((ViewGroup) openingView.getRootView().findViewById(android.R.id.content)).addView(contextMenuView);

            contextMenuView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    contextMenuView.getViewTreeObserver().removeOnPreDrawListener(this);
                    setupContextMenuInitialPosition(openingView);
                    performShowAnimation();
                    return false;
                }
            });
        }
    }

    private void setupContextMenuInitialPosition(View openingView) {
        final int[] openingViewLocation = new int[2];
        openingView.getLocationOnScreen(openingViewLocation);
        int additionalBottomMargin = Utils.dpToPx(16);
        contextMenuView.setTranslationX(openingViewLocation[0] - contextMenuView.getWidth() / 3);
        contextMenuView.setTranslationY(openingViewLocation[1] - contextMenuView.getHeight() - additionalBottomMargin);
    }

    private void performShowAnimation() {
        contextMenuView.setPivotX(contextMenuView.getWidth() / 2);
        contextMenuView.setPivotY(contextMenuView.getHeight());
        contextMenuView.setScaleX(0.1f);
        contextMenuView.setScaleY(0.1f);
        contextMenuView.animate()
                .scaleX(1f).scaleY(1f)
                .setDuration(150)
                .setInterpolator(new OvershootInterpolator())
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        isContextMenuShowing = false;
                    }
                });
    }

    public void hideContextMenu() {
        if (!isContextMenuDismissing) {
            isContextMenuDismissing = true;
            performDismissAnimation();
        }
    }

    private void performDismissAnimation() {
        contextMenuView.setPivotX(contextMenuView.getWidth() / 2);
        contextMenuView.setPivotY(contextMenuView.getHeight());
        contextMenuView.animate()
                .scaleX(0.1f).scaleY(0.1f)
                .setDuration(150)
                .setInterpolator(new AccelerateInterpolator())
                .setStartDelay(100)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        if (contextMenuView != null) {
                            contextMenuView.dismiss();
                        }
                        isContextMenuDismissing = false;
                    }
                });
    }

    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        if (contextMenuView != null) {
            hideContextMenu();
            contextMenuView.setTranslationY(contextMenuView.getTranslationY() - dy);
        }
    }

    @Override
    public void onViewAttachedToWindow(View v) {

    }

    @Override
    public void onViewDetachedFromWindow(View v) {
        contextMenuView = null;
    }
}
