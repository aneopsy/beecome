package io.aneopsy.theis_p.beecome.ui.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextSwitcher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.aneopsy.theis_p.beecome.R;
import io.aneopsy.theis_p.beecome.ui.activity.MainActivity;
import io.aneopsy.theis_p.beecome.ui.view.LoadingDefisItemView;

public class DefisAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final String ACTION_LIKE_BUTTON_CLICKED = "action_like_button_button";
    public static final String ACTION_LIKE_IMAGE_CLICKED = "action_like_image_button";
    public static final int VIEW_TYPE_DEFAULT = 1;
    public static final int VIEW_TYPE_LOADER = 2;
    private final List<DefisItem> defisItems = new ArrayList<>();
    private Context context;
    private OnDefisItemClickListener onDefisItemClickListener;
    private boolean showLoadingView = false;
    public DefisAdapter(Context context) {
        this.context = context;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_DEFAULT) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_defis, parent, false);
            CellDefisViewHolder cellDefisViewHolder = new CellDefisViewHolder(view);
            setupClickableViews(view, cellDefisViewHolder);
            return cellDefisViewHolder;
        } else if (viewType == VIEW_TYPE_LOADER) {
            LoadingDefisItemView view = new LoadingDefisItemView(context);
            view.setLayoutParams(new LinearLayoutCompat.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT)
            );
            return new LoadingCellDefisViewHolder(view);
        }
        return null;
    }
    private void setupClickableViews(final View view, final CellDefisViewHolder cellDefisViewHolder) {
        cellDefisViewHolder.btnComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDefisItemClickListener.onCommentsClick(view, cellDefisViewHolder.getAdapterPosition());
            }
        });
        cellDefisViewHolder.btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDefisItemClickListener.onMoreClick(v, cellDefisViewHolder.getAdapterPosition());
            }
        });
        cellDefisViewHolder.ivDefisCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = cellDefisViewHolder.getAdapterPosition();
                defisItems.get(adapterPosition).likesCount++;
                notifyItemChanged(adapterPosition, ACTION_LIKE_IMAGE_CLICKED);
                if (context instanceof MainActivity) {
                    ((MainActivity) context).showLikedSnackbar();
                }
            }
        });
        cellDefisViewHolder.btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = cellDefisViewHolder.getAdapterPosition();
                defisItems.get(adapterPosition).likesCount++;
                notifyItemChanged(adapterPosition, ACTION_LIKE_BUTTON_CLICKED);
                if (context instanceof MainActivity) {
                    ((MainActivity) context).showLikedSnackbar();
                }
            }
        });
        cellDefisViewHolder.ivUserProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDefisItemClickListener.onProfileClick(view);
            }
        });
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        ((CellDefisViewHolder) viewHolder).bindView(defisItems.get(position));
        if (getItemViewType(position) == VIEW_TYPE_LOADER) {
            bindLoadingDefisItem((LoadingCellDefisViewHolder) viewHolder);
        }
    }
    private void bindLoadingDefisItem(final LoadingCellDefisViewHolder holder) {
        holder.loadingDefisItemView.setOnLoadingFinishedListener(new LoadingDefisItemView.OnLoadingFinishedListener() {
            @Override
            public void onLoadingFinished() {
                showLoadingView = false;
                notifyItemChanged(0);
            }
        });
        holder.loadingDefisItemView.startLoading();
    }
    @Override
    public int getItemViewType(int position) {
        if (showLoadingView && position == 0) {
            return VIEW_TYPE_LOADER;
        } else {
            return VIEW_TYPE_DEFAULT;
        }
    }
    @Override
    public int getItemCount() {
        return defisItems.size();
    }
    public void updateItems(boolean animated) {
        defisItems.clear();
        defisItems.addAll(Arrays.asList(
                new DefisItem(33, false),
                new DefisItem(1, false),
                new DefisItem(223, false),
                new DefisItem(2, false),
                new DefisItem(6, false),
                new DefisItem(8, false),
                new DefisItem(99, false)
        ));
        if (animated) {
            notifyItemRangeInserted(0, defisItems.size());
        } else {
            notifyDataSetChanged();
        }
    }
    public void setOnDefisItemClickListener(OnDefisItemClickListener onDefisItemClickListener) {
        this.onDefisItemClickListener = onDefisItemClickListener;
    }
    public void showLoadingView() {
        showLoadingView = true;
        notifyItemChanged(0);
    }
    public static class CellDefisViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.ivDefisCenter)
        ImageView ivDefisCenter;
        @Bind(R.id.ivDefisBottom)
        ImageView ivDefisBottom;
        @Bind(R.id.btnComments)
        ImageButton btnComments;
        @Bind(R.id.btnLike)
        ImageButton btnLike;
        @Bind(R.id.btnMore)
        ImageButton btnMore;
        @Bind(R.id.vBgLike)
        View vBgLike;
        @Bind(R.id.ivLike)
        ImageView ivLike;
        @Bind(R.id.tsLikesCounter)
        TextSwitcher tsLikesCounter;
        @Bind(R.id.ivUserProfile)
        ImageView ivUserProfile;
        @Bind(R.id.vImageRoot)
        FrameLayout vImageRoot;
        DefisItem defisItem;
        public CellDefisViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
        public void bindView(DefisItem defisItem) {
            this.defisItem = defisItem;
            int adapterPosition = getAdapterPosition();
            ivDefisCenter.setImageResource(adapterPosition % 2 == 0 ? R.drawable.img_feed_center_1 : R.drawable.img_feed_center_2);
            ivDefisBottom.setImageResource(adapterPosition % 2 == 0 ? R.drawable.img_feed_bottom_1 : R.drawable.img_feed_bottom_2);
            btnLike.setImageResource(defisItem.isLiked ? R.drawable.ic_heart_red : R.drawable.ic_heart_outline_grey);
            tsLikesCounter.setCurrentText(vImageRoot.getResources().getQuantityString(
                    R.plurals.likes_count, defisItem.likesCount, defisItem.likesCount
            ));
        }
        public DefisItem getDefisItem() {
            return defisItem;
        }
    }
    public static class LoadingCellDefisViewHolder extends CellDefisViewHolder {
        LoadingDefisItemView loadingDefisItemView;
        public LoadingCellDefisViewHolder(LoadingDefisItemView view) {
            super(view);
            this.loadingDefisItemView = view;
        }
        @Override
        public void bindView(DefisItem defisItem) {
            super.bindView(defisItem);
        }
    }
    public static class DefisItem {
        public int likesCount;
        public boolean isLiked;
        public DefisItem(int likesCount, boolean isLiked) {
            this.likesCount = likesCount;
            this.isLiked = isLiked;
        }
    }
    public interface OnDefisItemClickListener {
        void onCommentsClick(View v, int position);
        void onMoreClick(View v, int position);
        void onProfileClick(View v);
    }
}