package com.xiaokun.customview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by 肖坤 on 2018/9/24.
 * 一共3个子view,头布局,mainview，尾布局
 *
 * @author 肖坤
 * @date 2018/9/24
 */

public class RefreshAndLoadMoreView extends LinearLayout {

    private static final String TAG = "RefreshAndLoadMoreView";
    /**
     * 下拉刷新状态
     */
    public static final int PULL_DOWN_TO_REFRESH = 0;
    /**
     * 松开刷新状态
     */
    public static final int RELEASE_REFRESH = 1;
    /**
     * 正在刷新状态
     */
    public static final int REFRESHING = 2;
    /**
     * 刷新完成状态
     */
    public static final int REFRESH_COMPLETED = 3;
    private Context mContext;
    private LayoutInflater mInflater;
    private View mHeaderView;
    private TextView mRefreshHeaderTv;
    private int mLastYIntercept;
    private int mLastXIntercept;
    private int mLastX;
    private int mLastY;
    private ValueAnimator mAnimator;
    private int mHeaderHeight;

    private int mCurrentState;

    public RefreshAndLoadMoreView(Context context) {
        this(context, null);
    }

    public RefreshAndLoadMoreView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RefreshAndLoadMoreView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
        //设置竖直方向
        setOrientation(VERTICAL);
    }

    /**
     * 设置内容布局
     *
     * @param view
     */
    public void setContentView(View view) {
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.height = params.height + mHeaderHeight;
        view.setLayoutParams(params);
        addView(view, 1);
        //尾布局
        View footerView = mInflater.inflate(R.layout.footer_layout, this, false);
        addView(footerView, 2);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        Log.e(TAG, "onLayout(" + TAG + ".java:" + Thread.currentThread().getStackTrace()[2].getLineNumber() + ")" + changed);
        if (changed) {
            mHeaderHeight = getChildAt(0).getHeight();
            scrollTo(0, mHeaderHeight);
            View view = getChildAt(1);
            int measuredHeight = view.getMeasuredHeight();
            measuredHeight = measuredHeight + mHeaderHeight;
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            layoutParams.height = measuredHeight;
            view.setLayoutParams(layoutParams);
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        //头布局
        mHeaderView = mInflater.inflate(R.layout.header_layout, this, false);
        mRefreshHeaderTv = mHeaderView.findViewById(R.id.refresh_header_tv);
        addView(mHeaderView, 0);
    }

    //外部拦截法，参考自艺术探索
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercepted = false;
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                intercepted = false;
                if (mAnimator != null && mAnimator.isStarted()) {
                    intercepted = true;
                    mAnimator.cancel();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                y = (int) ev.getY();
                Log.e(TAG, "onInterceptTouchEvent(" + TAG + ".java:" + Thread.currentThread().getStackTrace()[2].getLineNumber() + ")" + (y - mLastYIntercept));
                if (y - mLastYIntercept > 0) {
                    //下拉
                    intercepted = true;
                    if (getChildAt(1).canScrollVertically(-1)) {
                        intercepted = false;
                    } else {
                        intercepted = true;
                    }
                } else {
                    //上拉
                    if (getChildAt(1).canScrollVertically(1)) {
                        intercepted = false;
                    } else {
                        intercepted = true;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                intercepted = false;
                break;
            default:
                break;
        }
        mLastXIntercept = x;
        mLastYIntercept = y;
        mLastX = x;
        mLastY = y;
        return intercepted;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (mAnimator != null && mAnimator.isStarted()) {
                    mAnimator.cancel();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaY = y - mLastY;
                scrollBy(0, -deltaY / 3);
                Log.e(TAG, "onTouchEvent(" + TAG + ".java:" + Thread.currentThread().getStackTrace()[2].getLineNumber() + ")" + getScrollY());
                if (getScrollY() < 0) {
                    setState(RELEASE_REFRESH);
                } else {
                    setState(PULL_DOWN_TO_REFRESH);
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                onActionUp();
                break;
            default:
                break;
        }
        mLastX = x;
        mLastY = y;
        return true;
    }

    /**
     * 设置状态
     *
     * @param state
     */
    private void setState(int state) {
        String headerTvStr = "";
        switch (state) {
            case PULL_DOWN_TO_REFRESH:
                mCurrentState = PULL_DOWN_TO_REFRESH;
                headerTvStr = "下拉刷新";
                break;
            case RELEASE_REFRESH:
                mCurrentState = RELEASE_REFRESH;
                headerTvStr = "松开刷新";
                break;
            case REFRESHING:
                mCurrentState = REFRESHING;
                headerTvStr = "正在刷新";
                break;
            case REFRESH_COMPLETED:
                mCurrentState = REFRESH_COMPLETED;
                headerTvStr = "刷新完成";
                break;
        }
        mRefreshHeaderTv.setText(headerTvStr);
    }

    /**
     * 手指抬起时的操作
     */
    private void onActionUp() {
        if (mCurrentState == RELEASE_REFRESH) {
            refresh();
        } else if (mCurrentState == PULL_DOWN_TO_REFRESH) {
            int scrollY = getScrollY();
            mAnimator = ValueAnimator.ofInt(scrollY, mHeaderHeight);
            mAnimator.addUpdateListener(mAnimatorUpdateListener);
            mAnimator.setDuration(200);
            mAnimator.start();
        }
    }

    private void refresh() {
        int scrollY = getScrollY();
        mAnimator = ValueAnimator.ofInt(scrollY, 0);
        mAnimator.addUpdateListener(mAnimatorUpdateListener);
        mAnimator.setDuration(200);
        mAnimator.start();

        final ValueAnimator valueAnimator = ValueAnimator.ofInt(0, mHeaderHeight);
        valueAnimator.setDuration(200);
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                setState(REFRESHING);
                valueAnimator.addUpdateListener(mAnimatorUpdateListener);
                valueAnimator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        setState(PULL_DOWN_TO_REFRESH);
                    }
                });
                postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        valueAnimator.start();
                        setState(REFRESH_COMPLETED);
                    }
                }, 1000);
            }
        });
    }

    ValueAnimator.AnimatorUpdateListener mAnimatorUpdateListener = new ValueAnimator.AnimatorUpdateListener() {

        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            int value = (int) animation.getAnimatedValue();
            scrollTo(0, value);
        }
    };
}
