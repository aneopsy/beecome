package io.aneopsy.theis_p.beecome.ui.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import butterknife.ButterKnife;
import io.aneopsy.theis_p.beecome.R;

public class LoadingDefisItemView extends FrameLayout {
    private OnLoadingFinishedListener onLoadingFinishedListener;
    public LoadingDefisItemView(Context context) {
        super(context);
        init();
    }
    public LoadingDefisItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    public LoadingDefisItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public LoadingDefisItemView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }
    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.item_feed_loader, this, true);
        ButterKnife.bind(this);
    }
    public void startLoading() {}
    public void setOnLoadingFinishedListener(OnLoadingFinishedListener onLoadingFinishedListener) {
        this.onLoadingFinishedListener = onLoadingFinishedListener;
    }
    public interface OnLoadingFinishedListener {void onLoadingFinished();
    }
}
