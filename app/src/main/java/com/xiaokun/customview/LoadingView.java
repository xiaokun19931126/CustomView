package com.xiaokun.customview;

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

/**
 * Created by 肖坤 on 2018/9/13.
 *
 * @author 肖坤
 * @date 2018/9/13
 */

public class LoadingView extends View
{
    private static final String TAG = "LoadingView";
    private Paint mPaint;

    public LoadingView(Context context)
    {
        this(context, null);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs)
    {
        this(context, attrs, 0);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    private void initPaint()
    {
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(1);
        mPaint.setStyle(Paint.Style.FILL);
    }

    int r1 = 0;
    int r2 = 0;
    int r3 = 0;


    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        canvas.translate(0, 60);

        canvas.drawCircle(45, 0, r1, mPaint);
        canvas.drawCircle(135, 0, r2, mPaint);
        canvas.drawCircle(225, 0, r3, mPaint);

    }

    Handler mHandler = new Handler(Looper.getMainLooper());

    public void start()
    {
        ValueAnimator animator = ValueAnimator.ofInt(15, 30);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
        {
            @Override
            public void onAnimationUpdate(ValueAnimator animation)
            {
                r1 = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        configurateAnim(animator);

        mHandler.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                ValueAnimator animator1 = ValueAnimator.ofInt(15, 30);
                animator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
                {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation)
                    {
                        r2 = (int) animation.getAnimatedValue();
                        invalidate();
                    }
                });
                configurateAnim(animator1);
            }
        }, 250);

        mHandler.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                ValueAnimator animator2 = ValueAnimator.ofInt(15, 30);
                animator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
                {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation)
                    {
                        r3 = (int) animation.getAnimatedValue();
                        invalidate();
                    }
                });
                configurateAnim(animator2);
            }
        }, 500);
    }

    private void configurateAnim(ValueAnimator animator)
    {
        animator.setDuration(500);
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.start();
    }

}
