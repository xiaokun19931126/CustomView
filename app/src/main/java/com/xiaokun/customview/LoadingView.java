package com.xiaokun.customview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 肖坤 on 2018/9/13.
 *
 * @author 肖坤
 * @date 2018/9/13
 */

public class LoadingView extends View {
    private static final String TAG = "LoadingView";
    private Paint mPaint;
    private Context mContext;


    public LoadingView(Context context) {
        this(context, null);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initPaint();
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setColor(Color.GRAY);
        mPaint.setStrokeWidth(1);
        mPaint.setStyle(Paint.Style.FILL);
    }

    int r1 = 0;
    int r2 = 0;
    int r3 = 0;

    private int r = 20;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(0, 20);

        canvas.drawCircle(20, 0, r1, mPaint);
        canvas.drawCircle(65, 0, r2, mPaint);
        canvas.drawCircle(110, 0, r3, mPaint);

    }

    private List<ValueAnimator> animatorList = new ArrayList<>();
    private boolean lastBeginFlag;

    public void start() {
        animatorList.clear();
        lastBeginFlag = false;

        ValueAnimator animator = ValueAnimator.ofInt(0, r, 0);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                r1 = (int) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                lastBeginFlag = true;
            }
        });
        configurateAnim(animator);
        animator.start();
        animatorList.add(animator);

        ValueAnimator animator1 = ValueAnimator.ofInt(0, r, 0);
        animator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                r2 = (int) animation.getAnimatedValue();
            }
        });
        configurateAnim(animator1);
        animator1.setStartDelay(250);
        animator1.start();
        animatorList.add(animator1);

        ValueAnimator animator2 = ValueAnimator.ofInt(0, r, 0);
        animator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                r3 = (int) animation.getAnimatedValue();
                if (lastBeginFlag) {
                    postInvalidate();
                }
            }
        });

        configurateAnim(animator2);
        animator2.setStartDelay(500);
        animator2.start();
        animator2.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                start();
            }
        });
        animatorList.add(animator2);
    }

    private ValueAnimator createAnim(final int index) {
        ValueAnimator animator = ValueAnimator.ofInt(0, 30, 0);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int animatedValue = (int) animation.getAnimatedValue();
                switch (index) {
                    case 0:
                        r1 = animatedValue;
                        break;
                    case 1:
                        r2 = animatedValue;
                        break;
                    case 2:
                        r3 = animatedValue;
                        break;
                }
                if (index == 0) {
                    r1 = animatedValue;
                }
                invalidate();
            }
        });
        configurateAnim(animator);

        animator.setStartDelay(index * 250);
        animator.start();
        return animator;
    }

    private void configurateAnim(ValueAnimator animator) {
        animator.setDuration(1000);
        animator.setInterpolator(new LinearInterpolator());
//        animator.setRepeatMode(ValueAnimator.REVERSE);
//        animator.setRepeatCount(ValueAnimator.INFINITE);
    }

    public void release() {
        for (ValueAnimator valueAnimator : animatorList) {
            //先执行移除监听操作
            valueAnimator.removeAllListeners();
            valueAnimator.removeAllUpdateListeners();
            valueAnimator.end();
            valueAnimator.cancel();
        }
    }

    private float dpToPx(float dp) {
        if (mContext != null) {
            return dp * mContext.getResources().getDisplayMetrics().density;
        }
        return 0;
    }

    private int dpToPxInt(float dp) {
        if (mContext != null) {
            return (int) (dpToPx(dp) + 0.5f);
        }
        return 0;
    }
}
