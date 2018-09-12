package com.xiaokun.customview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import static android.animation.ValueAnimator.INFINITE;
import static android.animation.ValueAnimator.REVERSE;

/**
 * Created by 肖坤 on 2018/9/12.
 *
 * @author 肖坤
 * @date 2018/9/12
 */

public class MusicPlayView extends View
{
    private static final String TAG = "MusicPlayView";
    private Paint mPaint;

    public MusicPlayView(Context context)
    {
        this(context, null);
    }

    public MusicPlayView(Context context, @Nullable AttributeSet attrs)
    {
        this(context, attrs, 0);
    }

    public MusicPlayView(Context context, @Nullable AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    private void initPaint()
    {
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(20);
        mPaint.setStyle(Paint.Style.FILL);
    }

    int y1 = 0;
    int y2 = 0;
    int y3 = 0;
    int y4 = 0;
    int y5 = 0;

    //36 72 108 144 180
    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        canvas.drawLine(10, y1, 10, 180, mPaint);
        canvas.drawLine(50, y2, 50, 180, mPaint);
        canvas.drawLine(90, y3, 90, 180, mPaint);
        canvas.drawLine(130, y4, 130, 180, mPaint);
        canvas.drawLine(170, y5, 170, 180, mPaint);
    }

    public void start()
    {
//        AnimatorSet set = new AnimatorSet();
        ValueAnimator animator = ValueAnimator.ofInt(0, 100);
        configurationAnim(animator);
        animator.setDuration(350);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
        {
            @Override
            public void onAnimationUpdate(ValueAnimator animation)
            {
                int value = (int) animation.getAnimatedValue();
                y3 = value;
                invalidate();
            }
        });
        animator.start();

        ValueAnimator animator1 = ValueAnimator.ofInt(36, 90);
        configurationAnim(animator1);
        animator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
        {
            @Override
            public void onAnimationUpdate(ValueAnimator animation)
            {
                int value = (int) animation.getAnimatedValue();
                y5 = value;
                invalidate();
            }
        });
        animator1.setDuration(300);
        animator1.start();

        ValueAnimator animator2 = ValueAnimator.ofInt(40, 70);
        configurationAnim(animator2);
        animator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
        {
            @Override
            public void onAnimationUpdate(ValueAnimator animation)
            {
                int value = (int) animation.getAnimatedValue();
                y1 = value;
                invalidate();
            }
        });
        animator2.setDuration(300);
        animator2.start();

        ValueAnimator animator3 = ValueAnimator.ofInt(50, 110);
        configurationAnim(animator3);
        animator3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
        {
            @Override
            public void onAnimationUpdate(ValueAnimator animation)
            {
                int value = (int) animation.getAnimatedValue();
                y2 = value;
                invalidate();
            }
        });
        animator3.setDuration(300);
        animator3.start();

        ValueAnimator animator4 = ValueAnimator.ofInt(70, 130);
        configurationAnim(animator4);
        animator4.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
        {
            @Override
            public void onAnimationUpdate(ValueAnimator animation)
            {
                int value = (int) animation.getAnimatedValue();
                y4 = value;
                invalidate();
            }
        });
        animator4.setDuration(500);
        animator4.start();
//        set.playTogether(animator, animator1, animator2, animator3, animator4);
//        set.setDuration(500);
//        set.start();
    }

    private void configurationAnim(ValueAnimator animator)
    {
//        animator.setDuration(500);
        animator.setRepeatCount(INFINITE);
        animator.setRepeatMode(REVERSE);
    }

}
