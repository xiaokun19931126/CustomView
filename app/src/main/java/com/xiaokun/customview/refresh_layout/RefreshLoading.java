package com.xiaokun.customview.refresh_layout;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.xiaokun.customview.LoadingView;

/**
 * Created by xiaokun on 2019/4/28.
 *
 * @author xiaokun
 * @date 2019/4/28
 */
public class RefreshLoading extends LinearLayout implements RefreshLayout.HeadAndFootCallBack {

    private LoadingView loadingView;

    public RefreshLoading(@NonNull Context context) {
        super(context);
        initView();
    }

    public RefreshLoading(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
//        loadingView = new LoadingView(getContext());
//        addView(loadingView);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        //loadingView = ((LoadingView) getChildAt(0));

    }


    @Override
    public void setAttribute() {

    }

    @Override
    public void startPull() {
        LoadingView loadingView = new LoadingView(getContext());
        addView(loadingView, 0);
        loadingView.start();
    }

    @Override
    public void stopPull() {
        removeViewAt(0);
    }
}
