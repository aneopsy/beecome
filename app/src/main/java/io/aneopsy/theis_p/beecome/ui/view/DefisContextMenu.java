package io.aneopsy.theis_p.beecome.ui.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import butterknife.ButterKnife;
import butterknife.OnClick;
import io.aneopsy.theis_p.beecome.R;
import io.aneopsy.theis_p.beecome.Utils;

public class DefisContextMenu extends LinearLayout {
    private static final int CONTEXT_MENU_WIDTH = Utils.dpToPx(240);
    private int defisItem = -1;
    private OnDefisContextMenuItemClickListener onItemClickListener;
    public DefisContextMenu(Context context) {
        super(context);
        init();
    }
    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_context_menu, this, true);
        setBackgroundResource(R.drawable.bg_container_shadow);
        setOrientation(VERTICAL);
        setLayoutParams(new LayoutParams(CONTEXT_MENU_WIDTH, ViewGroup.LayoutParams.WRAP_CONTENT));
    }
    public void bindToItem(int DefisItem) {
        this.defisItem = defisItem;
    }
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        ButterKnife.bind(this);
    }
    public void dismiss() {
        ((ViewGroup) getParent()).removeView(DefisContextMenu.this);
    }
    @OnClick(R.id.btnReport)
    public void onReportClick() {
        if (onItemClickListener != null) {
            onItemClickListener.onReportClick(defisItem);
        }
    }
    @OnClick(R.id.btnSharePhoto)
    public void onSharePhotoClick() {
        if (onItemClickListener != null) {
            onItemClickListener.onSharePhotoClick(defisItem);
        }
    }
    @OnClick(R.id.btnCopyShareUrl)
    public void onCopyShareUrlClick() {
        if (onItemClickListener != null) {
            onItemClickListener.onCopyShareUrlClick(defisItem);
        }
    }
    @OnClick(R.id.btnCancel)
    public void onCancelClick() {
        if (onItemClickListener != null) {
            onItemClickListener.onCancelClick(defisItem);
        }
    }
    public void setOnDefisMenuItemClickListener(OnDefisContextMenuItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    public interface OnDefisContextMenuItemClickListener {
        public void onReportClick(int defisItem);
        public void onSharePhotoClick(int defisItem);
        public void onCopyShareUrlClick(int defisItem);
        public void onCancelClick(int defisItem);
    }
}