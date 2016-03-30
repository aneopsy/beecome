package io.aneopsy.theis_p.beecome.ui.adapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.aneopsy.theis_p.beecome.R;
import io.aneopsy.theis_p.beecome.Utils;

public class DefisItemAnimator extends DefaultItemAnimator {
    private static final AccelerateInterpolator ACCELERATE_INTERPOLATOR = new AccelerateInterpolator();
    private static final OvershootInterpolator OVERSHOOT_INTERPOLATOR = new OvershootInterpolator(4);
    Map<RecyclerView.ViewHolder, AnimatorSet> likeAnimationsMap = new HashMap<>();
    Map<RecyclerView.ViewHolder, AnimatorSet> heartAnimationsMap = new HashMap<>();
    private int lastAddAnimatedItem = -2;
    @Override
    public boolean canReuseUpdatedViewHolder(RecyclerView.ViewHolder viewHolder) {
        return true;
    }
    @NonNull
    @Override
    public ItemHolderInfo recordPreLayoutInformation(@NonNull RecyclerView.State state,
                                                     @NonNull RecyclerView.ViewHolder viewHolder,
                                                     int changeFlags, @NonNull List<Object> payloads) {
        if (changeFlags == FLAG_CHANGED) {
            for (Object payload : payloads) {
                if (payload instanceof String) {
                    return new DefisItemHolderInfo((String) payload);
                }
            }
        }
        return super.recordPreLayoutInformation(state, viewHolder, changeFlags, payloads);
    }
    @Override
    public boolean animateAdd(RecyclerView.ViewHolder viewHolder) {
        if (viewHolder.getItemViewType() == DefisAdapter.VIEW_TYPE_DEFAULT) {
            if (viewHolder.getLayoutPosition() > lastAddAnimatedItem) {
                lastAddAnimatedItem++;
                runEnterAnimation((DefisAdapter.CellDefisViewHolder) viewHolder);
                return false;
            }
        }
        dispatchAddFinished(viewHolder);
        return false;
    }
    private void runEnterAnimation(final DefisAdapter.CellDefisViewHolder holder) {
        final int screenHeight = Utils.getScreenHeight(holder.itemView.getContext());
        holder.itemView.setTranslationY(screenHeight);
        holder.itemView.animate()
                .translationY(0)
                .setInterpolator(new DecelerateInterpolator(3.f))
                .setDuration(700)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        dispatchAddFinished(holder);
                    }
                })
                .start();
    }
    @Override
    public boolean animateChange(@NonNull RecyclerView.ViewHolder oldHolder,
                                 @NonNull RecyclerView.ViewHolder newHolder,
                                 @NonNull ItemHolderInfo preInfo,
                                 @NonNull ItemHolderInfo postInfo) {
        cancelCurrentAnimationIfExists(newHolder);
        if (preInfo instanceof DefisItemHolderInfo) {
            DefisItemHolderInfo defisItemHolderInfo = (DefisItemHolderInfo) preInfo;
            DefisAdapter.CellDefisViewHolder holder = (DefisAdapter.CellDefisViewHolder) newHolder;
            animateHeartButton(holder);
            updateLikesCounter(holder, holder.getDefisItem().likesCount);
            if (DefisAdapter.ACTION_LIKE_IMAGE_CLICKED.equals(defisItemHolderInfo.updateAction)) {
                animatePhotoLike(holder);
            }
        }
        return false;
    }
    private void cancelCurrentAnimationIfExists(RecyclerView.ViewHolder item) {
        if (likeAnimationsMap.containsKey(item)) {
            likeAnimationsMap.get(item).cancel();
        }
        if (heartAnimationsMap.containsKey(item)) {
            heartAnimationsMap.get(item).cancel();
        }
    }
    private void animateHeartButton(final DefisAdapter.CellDefisViewHolder holder) {
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator rotationAnim = ObjectAnimator.ofFloat(holder.btnLike, "rotation", 0f, 360f);
        rotationAnim.setDuration(300);
        rotationAnim.setInterpolator(ACCELERATE_INTERPOLATOR);
        ObjectAnimator bounceAnimX = ObjectAnimator.ofFloat(holder.btnLike, "scaleX", 0.2f, 1f);
        bounceAnimX.setDuration(300);
        bounceAnimX.setInterpolator(OVERSHOOT_INTERPOLATOR);
        ObjectAnimator bounceAnimY = ObjectAnimator.ofFloat(holder.btnLike, "scaleY", 0.2f, 1f);
        bounceAnimY.setDuration(300);
        bounceAnimY.setInterpolator(OVERSHOOT_INTERPOLATOR);
        bounceAnimY.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                holder.btnLike.setImageResource(R.drawable.ic_heart_red);
            }
            @Override
            public void onAnimationEnd(Animator animation) {
                heartAnimationsMap.remove(holder);
                dispatchChangeFinishedIfAllAnimationsEnded(holder);
            }
        });
        animatorSet.play(bounceAnimX).with(bounceAnimY).after(rotationAnim);
        animatorSet.start();
        heartAnimationsMap.put(holder, animatorSet);
    }
    private void updateLikesCounter(DefisAdapter.CellDefisViewHolder holder, int toValue) {
        String likesCountTextFrom = holder.tsLikesCounter.getResources().getQuantityString(
                R.plurals.likes_count, toValue - 1, toValue - 1
        );
        holder.tsLikesCounter.setCurrentText(likesCountTextFrom);
        String likesCountTextTo = holder.tsLikesCounter.getResources().getQuantityString(
                R.plurals.likes_count, toValue, toValue
        );
        holder.tsLikesCounter.setText(likesCountTextTo);
    }
    private void animatePhotoLike(final DefisAdapter.CellDefisViewHolder holder) {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                likeAnimationsMap.remove(holder);
                resetLikeAnimationState(holder);
                dispatchChangeFinishedIfAllAnimationsEnded(holder);
            }
        });
        animatorSet.start();
        likeAnimationsMap.put(holder, animatorSet);
    }
    private void dispatchChangeFinishedIfAllAnimationsEnded(DefisAdapter.CellDefisViewHolder holder) {
        if (likeAnimationsMap.containsKey(holder) || heartAnimationsMap.containsKey(holder)) {
            return;
        }
        dispatchAnimationFinished(holder);
    }
    private void resetLikeAnimationState(DefisAdapter.CellDefisViewHolder holder) {
    }
    @Override
    public void endAnimation(RecyclerView.ViewHolder item) {
        super.endAnimation(item);
        cancelCurrentAnimationIfExists(item);
    }
    @Override
    public void endAnimations() {
        super.endAnimations();
        for (AnimatorSet animatorSet : likeAnimationsMap.values()) {
            animatorSet.cancel();
        }
    }
    public static class DefisItemHolderInfo extends ItemHolderInfo {
        public String updateAction;
        public DefisItemHolderInfo(String updateAction) {
            this.updateAction = updateAction;
        }
    }
}