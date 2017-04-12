package com.daimajia.numberprogressbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;

import static com.daimajia.numberprogressbar.NumberProgressBar.ProgressAttrVisibility.Invisible;
import static com.daimajia.numberprogressbar.NumberProgressBar.ProgressAttrVisibility.Visible;

/**
 * Created by daimajia on 14-4-30.
 */
public class NumberProgressBar extends View {

    private int mMaxProgress = 100;

    /**
     * Current progress, can not exceed the max progress.
     * 当前进度值，不能超过最大进度值
     */
    private int mCurrentProgress = 0;

    /**
     * The progress area bar color.
     * 进度条颜色
     */
    private int mReachedBarColor;

    /**
     * The bar unreached area color.
     * 没有进度到达的区域的颜色
     */
    private int mUnreachedBarColor;

    /**
     * The progress text color.
     * 进度文字的颜色
     */
    private int mTextColor;

    /**
     * 圆角矩形颜色
     * 默认与进度条颜色一致，如果有指定则为指定颜色
     * gjgAdd
     */
    private int mRoundColor;
    /**
     * The progress text size.
     * 进度文字的大小
     */
    private float mTextSize;

    /**
     * The height of the reached area.
     * 进度条的高度
     */
    private float mReachedBarHeight;

    /**
     * The height of the unreached area.
     * 进度条无进度区域的高度
     */
    private float mUnreachedBarHeight;

    /**
     * 圆角角度
     * gjgAdd
     */
    private float mRoundRadius;
    /**
     * 圆角矩形框宽度
     * 如果指定了显示文字，且显示圆角矩形，这个宽度会根据文字宽度来计算，设置的属性将会失效
     * 如果不显示文字，只显示这个圆角矩形，则为设置的属性值
     * gjgAdd
     */
    private float mRoundWidth;
    /**
     * 圆角矩形框宽度
     * 如果指定了显示文字，且显示圆角矩形，这个宽度会根据文字高度来计算，设置的属性将会失效
     * 如果不显示文字，只显示这个圆角矩形，则为设置的属性值
     * gjgAdd
     */
    private float mRoundHeight;

    /**
     * The suffix of the number.
     * 数字后缀
     */
    private String mSuffix = "%";

    /**
     * The prefix.
     * 前缀
     */
    private String mPrefix = "";


    private final int default_text_color = Color.rgb(66, 145, 241);
    private final int default_reached_color = Color.rgb(66, 145, 241);
    private final int default_unreached_color = Color.rgb(204, 204, 204);
    private final float default_progress_text_offset;
    private final float default_text_size;
    private final float default_reached_bar_height;
    private final float default_unreached_bar_height;
    private final float default_round_color = default_reached_color;
    private final float default_round_radius;
    private final float default_round_width;
    private final float default_round_height;

    /**
     * For save and restore instance of progressbar.
     * 保存和恢复进度条实例对象的Key值
     */
    private static final String INSTANCE_STATE = "saved_instance";
    private static final String INSTANCE_TEXT_COLOR = "text_color";
    private static final String INSTANCE_TEXT_SIZE = "text_size";
    private static final String INSTANCE_REACHED_BAR_HEIGHT = "reached_bar_height";
    private static final String INSTANCE_REACHED_BAR_COLOR = "reached_bar_color";
    private static final String INSTANCE_UNREACHED_BAR_HEIGHT = "unreached_bar_height";
    private static final String INSTANCE_UNREACHED_BAR_COLOR = "unreached_bar_color";
    private static final String INSTANCE_MAX = "max";
    private static final String INSTANCE_PROGRESS = "progress";
    private static final String INSTANCE_SUFFIX = "suffix";
    private static final String INSTANCE_PREFIX = "prefix";
    private static final String INSTANCE_TEXT_VISIBILITY = "text_visibility";
    private static final String INSTANCE_ROUND_COLOR = "round_color";
    private static final String INSTANCE_ROUND_RADIUS = "round_radius";
    private static final String INSTANCE_ROUND_WIDTH = "round_width";
    private static final String INSTANCE_ROUND_HEIGHT = "round_height";
    private static final String INSTANCE_ROUND_VISIBLE = "round_visibility";

    /**
     * 进度文字是可见表示值 0 可见 其它不可见
     */
    private static final int PROGRESS_TEXT_VISIBLE = 0;
    /**
     * 圆角矩形可见表示值 0 可见 其它不可见
     */
    private static final int PROGRESS_ROUND_VISIBLE = 1;


    /**
     * The width of the text that to be drawn.
     * 进度文字的宽度
     */
    private float mDrawTextWidth;

    /**
     * The drawn text start.
     * 进度文字的起始位置
     */
    private float mDrawTextStart;

    /**
     * The drawn text end.
     * 进度文字的结束位置
     */
    private float mDrawTextEnd;

    /**
     * The text that to be drawn in onDraw().
     * 进度文字
     */
    private String mCurrentDrawText;

    /**
     * The Paint of the reached area.
     * 进度条画笔
     */
    private Paint mReachedBarPaint;
    /**
     * The Paint of the unreached area.
     * 无进度的进度条画笔
     */
    private Paint mUnreachedBarPaint;
    /**
     * The Paint of the progress text.
     * 进度文字画笔
     */
    private Paint mTextPaint;
    /**
     * The Paint of the Round area.
     * 圆角矩形画笔
     * gjgAdd
     */
    private Paint mRoundPaint;

    /**
     * Unreached bar area to draw rect.
     * 无进度矩形区域区域
     */
    private RectF mUnreachedRectF = new RectF(0, 0, 0, 0);
    /**
     * Reached bar area rect.
     * 进度矩形区域
     */
    private RectF mReachedRectF = new RectF(0, 0, 0, 0);
    /**
     * Round area rect.
     * 圆角矩形区域
     * gjgAdd
     */
    private RectF mRoundRectF = new RectF(0, 0, 0, 0);


    /**
     * The progress text offset.
     * 进度文字偏移量
     */
    private float mOffset;

    /**
     * Determine if need to draw unreached area.
     * 是否需要绘制无进度区域
     */
    private boolean mDrawUnreachedBar = true;
    /**
     * Determine if need to draw unreached area.
     * 是否需要绘制进度区域
     */
    private boolean mDrawReachedBar = true;
    /**
     * Determine if need to draw unreached area.
     * 是否需要绘制文字区域
     */
    private boolean mIfDrawText = true;
    /**
     * 是否需要绘制圆角矩形
     * gjgAdd
     */
    private boolean mIfDrawRound = false;

    /**
     * Listener
     * 进度监听
     */
    private OnProgressBarListener mListener;

    public enum ProgressAttrVisibility {
        Visible, Invisible
    }

    public NumberProgressBar(Context context) {
        this(context, null);
    }

    public NumberProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NumberProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        default_reached_bar_height = dp2px(1.5f);
        default_unreached_bar_height = dp2px(1.0f);
        default_text_size = sp2px(10);
        default_progress_text_offset = dp2px(3.0f);
        default_round_radius = dp2px(2.0f);
        default_round_width = dp2px(40.0f);
        default_round_height = dp2px(15.0f);

        //load styled attributes.加载样式属性
        final TypedArray attributes = context.getTheme().obtainStyledAttributes(attrs, R.styleable.NumberProgressBar,
                defStyleAttr, 0);

        mReachedBarColor = attributes.getColor(R.styleable.NumberProgressBar_progress_reached_color, default_reached_color);
        mUnreachedBarColor = attributes.getColor(R.styleable.NumberProgressBar_progress_unreached_color, default_unreached_color);
        mTextColor = attributes.getColor(R.styleable.NumberProgressBar_progress_text_color, default_text_color);
        mRoundColor = attributes.getColor(R.styleable.NumberProgressBar_progress_round_color, mReachedBarColor);
        mTextSize = attributes.getDimension(R.styleable.NumberProgressBar_progress_text_size, default_text_size);

        mReachedBarHeight = attributes.getDimension(R.styleable.NumberProgressBar_progress_reached_bar_height, default_reached_bar_height);
        mUnreachedBarHeight = attributes.getDimension(R.styleable.NumberProgressBar_progress_unreached_bar_height, default_unreached_bar_height);
        mRoundRadius = attributes.getDimension(R.styleable.NumberProgressBar_progress_round_radius, default_round_radius);
        mRoundWidth = attributes.getDimension(R.styleable.NumberProgressBar_progress_round_width, default_round_width);
        mRoundHeight = attributes.getDimension(R.styleable.NumberProgressBar_progress_round_height, default_round_height);
        mOffset = attributes.getDimension(R.styleable.NumberProgressBar_progress_text_offset, default_progress_text_offset);

        int textVisible = attributes.getInt(R.styleable.NumberProgressBar_progress_text_visibility, PROGRESS_TEXT_VISIBLE);
        if (textVisible != PROGRESS_TEXT_VISIBLE) {
            mIfDrawText = false;
        }else{
            mIfDrawText = true;
        }
        int roundVisible = attributes.getInt(R.styleable.NumberProgressBar_progress_round_visibility, PROGRESS_ROUND_VISIBLE);
        if (roundVisible != PROGRESS_ROUND_VISIBLE) {
            mIfDrawRound = true;
        }else{
            mIfDrawRound = false;
        }
        setProgress(attributes.getInt(R.styleable.NumberProgressBar_progress_current, 0));
        setMax(attributes.getInt(R.styleable.NumberProgressBar_progress_max, 100));

        attributes.recycle();
        initializePainters();
    }

    @Override
    protected int getSuggestedMinimumWidth() {
        return (int) mTextSize;
    }

    @Override
    protected int getSuggestedMinimumHeight() {
        return Math.max((int) mTextSize, Math.max((int) mReachedBarHeight, (int) mUnreachedBarHeight));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measure(widthMeasureSpec, true), measure(heightMeasureSpec, false));
    }

    private int measure(int measureSpec, boolean isWidth) {
        int result;
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);
        int padding = isWidth ? getPaddingLeft() + getPaddingRight() : getPaddingTop() + getPaddingBottom();
        if (mode == MeasureSpec.EXACTLY) {
            result = size;
        } else {
            result = isWidth ? getSuggestedMinimumWidth() : getSuggestedMinimumHeight();
            result += padding;
            if(mIfDrawRound){
                result += (2*mOffset);
            }
            if (mode == MeasureSpec.AT_MOST) {
                if (isWidth) {
                    result = Math.max(result, size);
                } else {
                    result = Math.min(result, size);
                }
            }
        }
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(mIfDrawRound && mIfDrawText){
            calculateTextAndRound();
        }else if(mIfDrawText){
            calculateText();
        }else if(mIfDrawRound){
            calculateRound();
        }else{
            calculateWithout();
        }
        if (mDrawReachedBar) {
            canvas.drawRect(mReachedRectF, mReachedBarPaint);
        }

        if (mDrawUnreachedBar) {
            canvas.drawRect(mUnreachedRectF, mUnreachedBarPaint);
        }
        if (mIfDrawRound) {
            canvas.drawRoundRect(mRoundRectF, mRoundRadius,mRoundRadius,mRoundPaint);
        }
        if (mIfDrawText)
            canvas.drawText(mCurrentDrawText, mDrawTextStart, mDrawTextEnd, mTextPaint);
    }

    private void calculateWithout() {
        //计算Round活动区域的真实宽度
        float actionWtidth = getWidth() - getPaddingLeft() - getPaddingRight();
        int rate = getProgress() <= getMax() ? getProgress() : getMax();
        mReachedRectF.left = getPaddingLeft();
        mReachedRectF.top = getHeight() / 2.0f - mReachedBarHeight / 2.0f;
        mReachedRectF.bottom = getHeight() / 2.0f + mReachedBarHeight / 2.0f;
        mReachedRectF.right = mReachedRectF.left + actionWtidth / (getMax() * 1.0f) * rate;

        mUnreachedRectF.left = mReachedRectF.right;
        mUnreachedRectF.right = getWidth() - getPaddingRight();
        mUnreachedRectF.top = getHeight() / 2.0f + -mUnreachedBarHeight / 2.0f;
        mUnreachedRectF.bottom = getHeight() / 2.0f + mUnreachedBarHeight / 2.0f;
    }

    private void calculateRound() {
        mRoundHeight = 30 + mOffset*2;
        mRoundWidth = 60 + mRoundHeight;
        mRoundRadius = mRoundHeight/2;

        //计算Round活动区域的真实宽度
        float actionWtidth = getWidth() - getPaddingLeft() - getPaddingRight() - mRoundWidth;

        //计算Round的活动区域
        mRoundRectF.top = getHeight() / 2.0f - mRoundHeight / 2.0f;
        mRoundRectF.bottom = getHeight() / 2.0f + mRoundHeight / 2.0f;
        int rate = getProgress() <= getMax() ? getProgress() : getMax();
        mRoundRectF.left = getPaddingLeft() + actionWtidth / (getMax() * 1.0f) * rate;
        mRoundRectF.right = mRoundRectF.left+mRoundWidth;

        //根据Round区域确定进度区域
        mReachedRectF.left = getPaddingLeft();
        mReachedRectF.top = getHeight() / 2.0f - mReachedBarHeight / 2.0f;
        mReachedRectF.bottom = getHeight() / 2.0f + mReachedBarHeight / 2.0f;
        mReachedRectF.right = mRoundRectF.left;

        //根据Round区域确定非进度区域
        mUnreachedRectF.left = mRoundRectF.right;
        mUnreachedRectF.right = getWidth() - getPaddingRight();
        mUnreachedRectF.top = getHeight() / 2.0f + -mUnreachedBarHeight / 2.0f;
        mUnreachedRectF.bottom = getHeight() / 2.0f + mUnreachedBarHeight / 2.0f;
    }

    private void calculateText() {
        //计算Text的Width
        mCurrentDrawText = String.format("%d", getProgress() * 100 / getMax());
        mCurrentDrawText = mPrefix + mCurrentDrawText + mSuffix;
        mDrawTextWidth = mTextPaint.measureText(mCurrentDrawText);

        //计算Text活动区域的真实宽度
        float actionWtidth = getWidth() - getPaddingLeft() - getPaddingRight() - mDrawTextWidth;
        int rate = getProgress() <= getMax() ? getProgress() : getMax();
        mDrawTextStart = getPaddingLeft() + actionWtidth / (getMax() * 1.0f) * rate;
        mDrawTextEnd = (int) ((getHeight() / 2.0f) - ((mTextPaint.descent() + mTextPaint.ascent()) / 2.0f));
        float textRight = mDrawTextStart + mDrawTextWidth;

        //计算进度区域
        mReachedRectF.left = getPaddingLeft();
        mReachedRectF.top = getHeight() / 2.0f - mReachedBarHeight / 2.0f;
        mReachedRectF.bottom = getHeight() / 2.0f + mReachedBarHeight / 2.0f;
        mReachedRectF.right = mDrawTextStart;

        //计算非进度区域
        mUnreachedRectF.left = textRight;
        mUnreachedRectF.right = getWidth() - getPaddingRight();
        mUnreachedRectF.top = getHeight() / 2.0f + -mUnreachedBarHeight / 2.0f;
        mUnreachedRectF.bottom = getHeight() / 2.0f + mUnreachedBarHeight / 2.0f;

    }

    private void calculateTextAndRound() {
        //计算Text的Width
        mCurrentDrawText = String.format("%d", getProgress() * 100 / getMax());
        mCurrentDrawText = mPrefix + mCurrentDrawText + mSuffix;
        mDrawTextWidth = mTextPaint.measureText(mCurrentDrawText);

        //根据Text的Width计算Round的大小
        float des = mTextPaint.descent();
        float asc = mTextPaint.ascent();
        mRoundHeight = -(des + asc) + mOffset*2;
        mRoundWidth = mDrawTextWidth + mRoundHeight;
        mRoundRadius = mRoundHeight/2;

        //计算Round活动区域的真实宽度
        float actionWtidth = getWidth() - getPaddingLeft() - getPaddingRight() - mRoundWidth;

        //计算Round的活动区域
        mRoundRectF.top = getHeight() / 2.0f - mRoundHeight / 2.0f;
        mRoundRectF.bottom = getHeight() / 2.0f + mRoundHeight / 2.0f;
        int rate = getProgress() <= getMax() ? getProgress() : getMax();
        mRoundRectF.left = getPaddingLeft() + actionWtidth / (getMax() * 1.0f) * rate;
        mRoundRectF.right = mRoundRectF.left+mRoundWidth;

        //根据Round区域确定文字的位置
        mDrawTextStart = (mRoundRectF.left)+ mRoundRadius;
        mDrawTextEnd = (int) ((getHeight() / 2.0f) - ((mTextPaint.descent() + mTextPaint.ascent()) / 2.0f));

        //根据Round区域确定进度区域
        mReachedRectF.left = getPaddingLeft();
        mReachedRectF.top = getHeight() / 2.0f - mReachedBarHeight / 2.0f;
        mReachedRectF.bottom = getHeight() / 2.0f + mReachedBarHeight / 2.0f;
        mReachedRectF.right = mRoundRectF.left;

        //根据Round区域确定非进度区域
        mUnreachedRectF.left = mRoundRectF.right;
        mUnreachedRectF.right = getWidth() - getPaddingRight();
        mUnreachedRectF.top = getHeight() / 2.0f + -mUnreachedBarHeight / 2.0f;
        mUnreachedRectF.bottom = getHeight() / 2.0f + mUnreachedBarHeight / 2.0f;
    }

    /**
     * 初始化画笔
     */
    private void initializePainters() {
        mReachedBarPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mReachedBarPaint.setColor(mReachedBarColor);

        mUnreachedBarPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mUnreachedBarPaint.setColor(mUnreachedBarColor);

        mRoundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mRoundPaint.setColor(mRoundColor);

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(mTextColor);
        mTextPaint.setTextSize(mTextSize);
    }

    /**
     * Get progress text color.
     *
     * @return progress text color.
     */
    public int getTextColor() {
        return mTextColor;
    }

    /**
     * Get progress text size.
     *
     * @return progress text size.
     */
    public float getProgressTextSize() {
        return mTextSize;
    }

    public int getUnreachedBarColor() {
        return mUnreachedBarColor;
    }

    public int getReachedBarColor() {
        return mReachedBarColor;
    }

    public int getProgress() {
        return mCurrentProgress;
    }

    public int getMax() {
        return mMaxProgress;
    }

    public float getReachedBarHeight() {
        return mReachedBarHeight;
    }

    public float getUnreachedBarHeight() {
        return mUnreachedBarHeight;
    }

    public void setProgressTextSize(float textSize) {
        this.mTextSize = textSize;
        mTextPaint.setTextSize(mTextSize);
        invalidate();
    }

    public void setProgressTextColor(int textColor) {
        this.mTextColor = textColor;
        mTextPaint.setColor(mTextColor);
        invalidate();
    }

    public void setUnreachedBarColor(int barColor) {
        this.mUnreachedBarColor = barColor;
        mUnreachedBarPaint.setColor(mUnreachedBarColor);
        invalidate();
    }

    public void setReachedBarColor(int progressColor) {
        this.mReachedBarColor = progressColor;
        mReachedBarPaint.setColor(mReachedBarColor);
        invalidate();
    }

    public void setReachedBarHeight(float height) {
        mReachedBarHeight = height;
    }

    public void setUnreachedBarHeight(float height) {
        mUnreachedBarHeight = height;
    }

    public void setMax(int maxProgress) {
        if (maxProgress > 0) {
            this.mMaxProgress = maxProgress;
            invalidate();
        }
    }

    public void setSuffix(String suffix) {
        if (suffix == null) {
            mSuffix = "";
        } else {
            mSuffix = suffix;
        }
    }

    public String getSuffix() {
        return mSuffix;
    }

    public void setPrefix(String prefix) {
        if (prefix == null)
            mPrefix = "";
        else {
            mPrefix = prefix;
        }
    }

    public String getPrefix() {
        return mPrefix;
    }

    public void incrementProgressBy(int by) {
        if (by > 0) {
            setProgress(getProgress() + by);
        }

        if(mListener != null){
            mListener.onProgressChange(getProgress(), getMax());
        }
    }

    public void setProgress(int progress) {
        if (progress >= 0) {
            this.mCurrentProgress = progress;
            invalidate();
        }
    }

    public int getmRoundColor() {
        return mRoundColor;
    }

    public void setmRoundColor(int mRoundColor) {
        this.mRoundColor = mRoundColor;
        mRoundPaint.setColor(mRoundColor);
        invalidate();
    }

    public float getmRoundRadius() {
        return mRoundRadius;
    }

    public void setmRoundRadius(float mRoundRadius) {
        this.mRoundRadius = mRoundRadius;
        invalidate();
    }

    public float getmRoundWidth() {
        return mRoundWidth;
    }

    public void setmRoundWidth(float mRoundWidth) {
        this.mRoundWidth = mRoundWidth;
        invalidate();
    }

    public float getmRoundHeight() {
        return mRoundHeight;
    }

    public void setmRoundHeight(float mRoundHeight) {
        this.mRoundHeight = mRoundHeight;
        invalidate();
    }

    public boolean isRoundVisibility() {
        return mIfDrawRound;
    }

    public void setRoundVisibility(ProgressAttrVisibility visibility) {
        mIfDrawText = visibility == Visible;
        invalidate();
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        final Bundle bundle = new Bundle();
        bundle.putParcelable(INSTANCE_STATE, super.onSaveInstanceState());
        bundle.putInt(INSTANCE_TEXT_COLOR, getTextColor());
        bundle.putFloat(INSTANCE_TEXT_SIZE, getProgressTextSize());
        bundle.putFloat(INSTANCE_REACHED_BAR_HEIGHT, getReachedBarHeight());
        bundle.putFloat(INSTANCE_UNREACHED_BAR_HEIGHT, getUnreachedBarHeight());
        bundle.putInt(INSTANCE_REACHED_BAR_COLOR, getReachedBarColor());
        bundle.putInt(INSTANCE_UNREACHED_BAR_COLOR, getUnreachedBarColor());
        bundle.putInt(INSTANCE_MAX, getMax());
        bundle.putInt(INSTANCE_PROGRESS, getProgress());
        bundle.putString(INSTANCE_SUFFIX, getSuffix());
        bundle.putString(INSTANCE_PREFIX, getPrefix());
        bundle.putBoolean(INSTANCE_TEXT_VISIBILITY, getProgressTextVisibility());
        bundle.putFloat(INSTANCE_ROUND_COLOR, getmRoundColor());
        bundle.putFloat(INSTANCE_ROUND_RADIUS, getmRoundRadius());
        bundle.putFloat(INSTANCE_ROUND_WIDTH, getmRoundWidth());
        bundle.putFloat(INSTANCE_ROUND_HEIGHT, getmRoundHeight());
        bundle.putBoolean(INSTANCE_ROUND_VISIBLE, isRoundVisibility());
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            final Bundle bundle = (Bundle) state;
            mTextColor = bundle.getInt(INSTANCE_TEXT_COLOR);
            mTextSize = bundle.getFloat(INSTANCE_TEXT_SIZE);
            mReachedBarHeight = bundle.getFloat(INSTANCE_REACHED_BAR_HEIGHT);
            mUnreachedBarHeight = bundle.getFloat(INSTANCE_UNREACHED_BAR_HEIGHT);
            mReachedBarColor = bundle.getInt(INSTANCE_REACHED_BAR_COLOR);
            mUnreachedBarColor = bundle.getInt(INSTANCE_UNREACHED_BAR_COLOR);

            mRoundColor = bundle.getInt(INSTANCE_ROUND_COLOR);
            mRoundRadius = bundle.getFloat(INSTANCE_ROUND_RADIUS);
            mRoundWidth = bundle.getFloat(INSTANCE_ROUND_WIDTH);
            mRoundHeight = bundle.getFloat(INSTANCE_ROUND_HEIGHT);

            initializePainters();
            setMax(bundle.getInt(INSTANCE_MAX));
            setProgress(bundle.getInt(INSTANCE_PROGRESS));
            setPrefix(bundle.getString(INSTANCE_PREFIX));
            setSuffix(bundle.getString(INSTANCE_SUFFIX));
            setProgressTextVisibility(bundle.getBoolean(INSTANCE_TEXT_VISIBILITY) ? Visible : Invisible);
            setRoundVisibility(bundle.getBoolean(INSTANCE_ROUND_VISIBLE) ? Visible : Invisible);
            super.onRestoreInstanceState(bundle.getParcelable(INSTANCE_STATE));
            return;
        }
        super.onRestoreInstanceState(state);
    }

    public float dp2px(float dp) {
        final float scale = getResources().getDisplayMetrics().density;
        return dp * scale + 0.5f;
    }

    public float sp2px(float sp) {
        final float scale = getResources().getDisplayMetrics().scaledDensity;
        return sp * scale;
    }

    public void setProgressTextVisibility(ProgressAttrVisibility visibility) {
        mIfDrawText = visibility == Visible;
        invalidate();
    }

    public boolean getProgressTextVisibility() {
        return mIfDrawText;
    }

    public void setOnProgressBarListener(OnProgressBarListener listener){
        mListener = listener;
    }
}
