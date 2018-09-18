package com.xiaokun.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

/**
 * <pre>
 *      作者  ：肖坤
 *      时间  ：2018/08/16
 *      描述  ：自定义ImageView
 *      版本  ：1.0
 * </pre>
 */
public class SubscriptImageView extends android.support.v7.widget.AppCompatImageView
{
    private static final String TAG = "SubscriptImageView";
    private Paint mPaint = new Paint();

    private int mSubBarWidth;
    private int mSubWidth;
    private int mSubBgColor;
    private int mSubTextColor;
    private int mSubTextSize;
    private String mSubText;
    private int DEFAULT_SUB_BAR_WIDTH = 30;
    private int DEFALUT_SUB_WIDTH = 80;
    private int DEFALUT_SUB_TEXT_SIZE = 18;

    public SubscriptImageView(Context context)
    {
        this(context, null);
    }

    public SubscriptImageView(Context context, @Nullable AttributeSet attrs)
    {
        this(context, attrs, 0);
    }

    public SubscriptImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.SubscriptImageView, defStyleAttr, 0);
        mSubBarWidth = (int) typedArray.getDimension(R.styleable.SubscriptImageView_subBarWidth, DEFAULT_SUB_BAR_WIDTH);
        mSubWidth = (int) typedArray.getDimension(R.styleable.SubscriptImageView_subWidth, DEFALUT_SUB_WIDTH);
        mSubBgColor = typedArray.getColor(R.styleable.SubscriptImageView_subBgColor, Color.parseColor("#60C8FA"));
        mSubTextColor = typedArray.getColor(R.styleable.SubscriptImageView_subTextColor, Color.parseColor("#ffffff"));
        mSubText = typedArray.getString(R.styleable.SubscriptImageView_subText);
        mSubTextSize = (int) typedArray.getDimension(R.styleable.SubscriptImageView_subTextSize, DEFALUT_SUB_TEXT_SIZE);
        typedArray.recycle();

        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeCap(Paint.Cap.SQUARE);
        mPaint.setTextSize(mSubTextSize);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight();

        mPaint.setStrokeWidth(mSubBarWidth);
        mPaint.setColor(mSubBgColor);
        canvas.drawLine(width - mSubWidth + mSubBarWidth / 2 * 1.414f, 0, width, mSubWidth - mSubBarWidth / 2 * 1.414f, mPaint);

        mPaint.setStrokeWidth(1f);
        //文字高度
        Rect bounds = new Rect();
        mPaint.getTextBounds(mSubText, 0, mSubText.length(), bounds);
        float mTextHeight = bounds.height();

        //文字宽度
        float textWidth = mPaint.measureText(mSubText);
        float v = (mSubWidth - mSubBarWidth / 2 * 1.414f + mTextHeight / 2) * 1.414f;
        //70*✔2 = 99  沿路径的距离添加到文本的起始位置
        float hOffset = (v - textWidth) / 2;

        Path path = new Path();
        path.moveTo(width - mSubWidth + mSubBarWidth / 2 * 1.414f - mTextHeight / 2, 0);
        path.lineTo(width, mSubWidth - mSubBarWidth / 2 * 1.414f + mTextHeight / 2);

        mPaint.setColor(mSubTextColor);
        canvas.drawTextOnPath(mSubText, path, hOffset, 0, mPaint);
    }
}
