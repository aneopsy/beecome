package io.aneopsy.theis_p.beecome.ui.adapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.aneopsy.theis_p.beecome.R;
import jp.wasabeef.picasso.transformations.MaskTransformation;

public class AboutAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private int itemsCount = 0;
    private int lastAnimatedPosition = -1;
    private int avatarSize;
    private boolean animationsLocked = false;
    private boolean delayEnterAnimation = true;
    public AboutAdapter(Context context) {
        this.context = context;
        avatarSize = context.getResources().getDimensionPixelSize(R.dimen.comment_avatar_size);
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(context).inflate(R.layout.item_about, parent, false);
        return new CommentViewHolder(view);
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        runEnterAnimation(viewHolder.itemView, position);
        CommentViewHolder holder = (CommentViewHolder) viewHolder;
        switch (position % 6) {
            case 0:
                holder.tvComment.setText("Diego CAYUELA [MCS]");
                break;
            case 1:
                holder.tvComment.setText("Melissa ELISSALDE [MCS]");
                break;
            case 2:
                holder.tvComment.setText("Maxime MARROQ [MCS]");
                break;
            case 3:
                holder.tvComment.setText("Bertille NADAUD [EART]");
                break;
            case 4:
                holder.tvComment.setText("Gauvin DAILLET [EPITECH]");
                break;
            case 5:
                holder.tvComment.setText("Paul THEIS [EPITECH]");
                break;
        }
        Picasso.with(context)


                .load(R.drawable.bonhomme)
                .centerCrop()
                .resize(avatarSize, avatarSize)
                .transform(new MaskTransformation(context, R.drawable.hexamask))
                .into(holder.ivUserAvatar);
    }
    private void runEnterAnimation(View view, int position) {
        if (animationsLocked) return;
        if (position > lastAnimatedPosition) {
            lastAnimatedPosition = position;
            view.setTranslationY(100);
            view.setAlpha(0.f);
            view.animate()
                    .translationY(0).alpha(1.f)
                    .setStartDelay(delayEnterAnimation ? 20 * (position) : 0)
                    .setInterpolator(new DecelerateInterpolator(2.f))
                    .setDuration(300)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            animationsLocked = true;
                        }
                    })
                    .start();
        }
    }
    @Override
    public int getItemCount() {
        return itemsCount;
    }
    public void updateItems() {
        itemsCount = 6;
        notifyDataSetChanged();
    }
    public void addItem() {
        itemsCount++;
        notifyItemInserted(itemsCount - 1);
    }
    public void setAnimationsLocked(boolean animationsLocked) {
        this.animationsLocked = animationsLocked;
    }
    public void setDelayEnterAnimation(boolean delayEnterAnimation) {
        this.delayEnterAnimation = delayEnterAnimation;
    }
    public static class CommentViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.ivUserAvatar)
        ImageView ivUserAvatar;
        @Bind(R.id.tvComment)
        TextView tvComment;
        public CommentViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}