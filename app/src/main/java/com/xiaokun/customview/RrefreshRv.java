package com.xiaokun.customview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.xiaokun.customview.refresh_layout.RefreshLayout;

/**
 * Created by xiaokun on 2019/4/28.
 *
 * @author xiaokun
 * @date 2019/4/28
 */
public class RrefreshRv extends RecyclerView implements RefreshLayout.PullCallBack {
    public RrefreshRv(Context context) {
        super(context);
    }

    public RrefreshRv(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RrefreshRv(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean canDownPull() {
//        LayoutManager layoutManager = getLayoutManager();
//        if (layoutManager instanceof LinearLayoutManager) {
//            layoutManager = (LinearLayoutManager) layoutManager;
//        }
//        int pos = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
//        return ((LinearLayoutManager) layoutManager).findViewByPosition(pos).getTop() == 0
//                && pos == 0;
        return !canScrollVertically(-1);
    }

    @Override
    public boolean canUpPull() {
//        LayoutManager layoutManager = getLayoutManager();
//        return ((LinearLayoutManager) layoutManager).findLastCompletelyVisibleItemPosition() == getAdapter().getItemCount() - 1;
        return !canScrollVertically(1);
    }
}
