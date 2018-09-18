package com.xiaokun.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;


/**
 * <pre>
 *      作者  ：肖坤
 *      时间  ：2018/08/15
 *      描述  ：圆形进度
 *      版本  ：1.0
 * </pre>
 */
public class RoundprogressView extends View {
    private static final String TAG = "CustomView";

    private float mAngle = 0;

    //默认设置圆环宽度8px
    private float mRingWidth = 8;

    private float mInnerRingWidth = 10;

    private float mRingSpacing = 6;

    private float mStartAngle = -225;

    private float mSweepAngle = 270;
    private int mUnReachColor;
    private int mReachColor;

    public static final int DEFAULT_REACH_COLOR = Color.parseColor("#7EC3E6");
    public static final int DEFAULT_UNREACH_COLOR = Color.parseColor("#2B2E33");
    public static final float DEFAULT_ANGLE = 0;

    private Paint mPaint = new Paint();

    public RoundprogressView(Context context) {
        this(context, null);
    }

    public RoundprogressView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundprogressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.RoundprogressView, defStyleAttr, 0);
        mRingWidth = typedArray.getDimension(R.styleable.RoundprogressView_ringWidth, 8);
        mStartAngle = typedArray.getFloat(R.styleable.RoundprogressView_startAngle, -225);
        mSweepAngle = typedArray.getFloat(R.styleable.RoundprogressView_sweepAngle, 270);
        mReachColor = typedArray.getColor(R.styleable.RoundprogressView_reachColor, DEFAULT_REACH_COLOR);
        mUnReachColor = typedArray.getColor(R.styleable.RoundprogressView_unReachColor, DEFAULT_UNREACH_COLOR);
        mAngle = typedArray.getFloat(R.styleable.RoundprogressView_roundProgress, DEFAULT_ANGLE);
        typedArray.recycle();

        initPaint();
    }

    private void initPaint() {
        mPaint.setColor(Color.GRAY);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(8f);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        float v = mRingWidth / 2;

        //绘制内部的那个黑色圆环
        mPaint.setColor(Color.parseColor("#141414"));
        mPaint.setStrokeWidth(10f);
        //200-8 - 6 -10 = 186
        canvas.drawCircle(width / 2, getHeight() / 2, (width - mRingWidth - 2 * mRingSpacing - mInnerRingWidth) / 2, mPaint);

        mPaint.setStrokeWidth(8f);
        mPaint.setColor(mUnReachColor);
        RectF rectF = new RectF(v, v, width - v, width - v);
        canvas.drawArc(rectF, mStartAngle, mSweepAngle, false, mPaint);

        mPaint.setColor(mReachColor);
        canvas.drawArc(rectF, mStartAngle, mAngle, false, mPaint);
    }

    //设置扫描的进度
    public void setAngle(float progress) {
        mAngle = progress * mSweepAngle;
        invalidate();
    }

}
