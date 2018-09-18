package com.xiaokun.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * <pre>
 *      作者  ：肖坤
 *      时间  ：2018/08/15
 *      描述  ：水平进度
 *      版本  ：1.0
 * </pre>
 */
public class HorizontalprogressView extends View
{
    private static final String TAG = "CustomView2";
    private Paint mPaint = new Paint();

    public static final float DEFAULT_PROGRESS = 0;

    private float mProgress = DEFAULT_PROGRESS;
    private Drawable mDrawable;
    private int mIntrinsicHeight;
    private int mIntrinsicWidth;

    //圆柱总长度
    private int mBartotalLength = 380;
    //圆柱宽度
    private int mBarWidth = 8;
    //水平文字距离圆柱长度
    private int mHTextOffset = 22;
    //图片距离圆柱长度
    private int mDrawableOffset = 4;
    private int mTextSize = 24;
    //最大值
    private int mMaxValue = 500;
    //竖直方向上文字距离圆柱长度
    private int mVTextOffset = 22;

    private int mVTextColor = Color.WHITE;
    private int mHTextColor = Color.BLUE;
    private int mUnReachBarColor = DEFAULT_UNREACH_COLOR;
    private int mReachBarColor = DEFAULT_REACH_COLOR;

    public static final int DEFAULT_REACH_COLOR = Color.parseColor("#7EC3E6");
    public static final int DEFAULT_UNREACH_COLOR = Color.parseColor("#2B2E33");
    private float mAbs;

    public HorizontalprogressView(Context context)
    {
        this(context, null);
    }

    public HorizontalprogressView(Context context, @Nullable AttributeSet attrs)
    {
        this(context, attrs, 0);
    }

    public HorizontalprogressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.HorizontalprogressView, defStyleAttr, 0);
        mBartotalLength = (int) typedArray.getDimension(R.styleable.HorizontalprogressView_totalBarLength, 380);
        mBarWidth = (int) typedArray.getDimension(R.styleable.HorizontalprogressView_barWidth, 8);
        mHTextOffset = (int) typedArray.getDimension(R.styleable.HorizontalprogressView_hTextOffset, 22);
        mDrawableOffset = (int) typedArray.getDimension(R.styleable.HorizontalprogressView_drawableOffset, 4);
        mTextSize = (int) typedArray.getDimension(R.styleable.HorizontalprogressView_textSize, 24);
        mVTextOffset = (int) typedArray.getDimension(R.styleable.HorizontalprogressView_vTextOffset, 22);
        mMaxValue = typedArray.getInteger(R.styleable.HorizontalprogressView_maxValue, 500);
        mVTextColor = typedArray.getColor(R.styleable.HorizontalprogressView_vTextColor, Color.WHITE);
        mHTextColor = typedArray.getColor(R.styleable.HorizontalprogressView_hTextColor, Color.BLUE);
        mUnReachBarColor = typedArray.getColor(R.styleable.HorizontalprogressView_unReachBarColor, DEFAULT_UNREACH_COLOR);
        mReachBarColor = typedArray.getColor(R.styleable.HorizontalprogressView_reachBarColor, DEFAULT_REACH_COLOR);
        mDrawable = typedArray.getDrawable(R.styleable.HorizontalprogressView_drawable);
        mProgress = typedArray.getFloat(R.styleable.HorizontalprogressView_horizontalProgress, DEFAULT_PROGRESS);
        typedArray.recycle();
        initPaint();
//        mDrawable = context.getResources().getDrawable(R.mipmap.personal_center_ic_integral);
        mIntrinsicHeight = mDrawable.getIntrinsicHeight();
        mIntrinsicWidth = mDrawable.getIntrinsicWidth();
    }

    private void initPaint()
    {
        mPaint.setColor(Color.GRAY);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(8f);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setTextSize(mTextSize);
        float descent = mPaint.descent();
        float ascent = mPaint.ascent();
        mAbs = Math.abs(descent + ascent);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int height = 0;
        if (mAbs > mBarWidth)
        {
            height = (int) (mBarWidth + mDrawableOffset + mIntrinsicHeight + (mAbs - mBarWidth) / 2);
        } else
        {
            height = mBarWidth + mDrawableOffset + mIntrinsicHeight;
        }

        Log.e(TAG, "(" + TAG + ".java:" + Thread.currentThread().getStackTrace()[2].getLineNumber() + ")" + height);
        int width = (int) (mIntrinsicWidth / 2 + mBartotalLength + mHTextOffset + mPaint.measureText(mMaxValue + ""));
        setMeasuredDimension(width, height);
    }

    //设置水平进度
    public void setProgress(float progress)
    {
        mProgress = progress;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        //当前值
        int currentValue = (int) (mProgress * mMaxValue);
        canvas.save();
        if (mAbs > mBarWidth)
        {
            canvas.translate(mIntrinsicWidth / 2, getHeight() - mAbs / 2);
        } else
        {
            canvas.translate(mIntrinsicWidth / 2, getHeight() - mBarWidth / 2);
        }

        mPaint.setStrokeWidth(8f);
        //绘制unReachBar
        mPaint.setColor(mUnReachBarColor);
        canvas.drawLine(0, 0, mBartotalLength, 0, mPaint);

        //绘制reachBar
        mPaint.setColor(mReachBarColor);
        mPaint.setTextSize(24);
        canvas.drawLine(0, 0, mProgress * mBartotalLength, 0, mPaint);

        //绘制水平方向文字
        mPaint.setColor(mHTextColor);
        mPaint.setStrokeWidth(1f);
        float textWidth = mPaint.measureText(currentValue + "");
        canvas.drawText(mMaxValue + "", mBartotalLength + mHTextOffset, mAbs / 2, mPaint);

        //绘制竖直方向文字的背景图片
        mDrawable.setBounds((int) (mProgress * mBartotalLength - mIntrinsicWidth / 2), -mBarWidth / 2 - mDrawableOffset - mIntrinsicHeight,
                (int) (mProgress * mBartotalLength + mIntrinsicWidth / 2), -mBarWidth / 2 - mDrawableOffset);
        mDrawable.draw(canvas);

        //绘制竖直方向文字
        mPaint.setColor(mVTextColor);
        canvas.drawText(currentValue + "", mProgress * mBartotalLength - textWidth / 2, -mBarWidth / 2 - mVTextOffset, mPaint);
        canvas.restore();
    }
}
