package cool.lht.customprogressbar;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.Shader;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by lht on 2016/9/20.
 */
public class LhtProgressBar extends View {


    private int textColor;    //进度条文字颜色
    private int backgroundColor;    //进度条背景颜色
    private float maxProgress;  //进度条最大值
    private int textSize;  //文字大小
    private float progress;  //进度条当前值
    private int borderWidth;   //边框宽度
    private String progressText = "";   //进度条文字

    private Context mContext;
    private Paint mPaint;
    private Rect textBouds;
    private float progressWidth;
    private int sliderPosition;
    private PorterDuffXfermode xfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP);

    public LhtProgressBar(Context context) {
        super(context);
        mContext = context;
        initialization();
    }

    public LhtProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        try {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.LhtProgressBar);
            borderWidth = ta.getInt(R.styleable.LhtProgressBar_borderWidth, 5);
            maxProgress = ta.getFloat(R.styleable.LhtProgressBar_maxProgress, 100);
            progress = ta.getFloat(R.styleable.LhtProgressBar_progress, 0);
            textSize = ta.getInt(R.styleable.LhtProgressBar_textSize, 20);
            backgroundColor = ta.getColor(R.styleable.LhtProgressBar_backgroundColor, Color.parseColor("#40C0F8"));
            textColor = ta.getColor(R.styleable.LhtProgressBar_textColor, Color.WHITE);
            progressText = ta.getString(R.styleable.LhtProgressBar_progressText);
            ta.recycle();
        } catch (Exception e) {
            initialization();
        }
    }

    private void initialization() {
        borderWidth = 5;
        maxProgress = 100;
        progress = 0;
        textSize = 20;
        backgroundColor = Color.parseColor("#40C0F8");
        textColor = Color.WHITE;
        progressText = "";
    }

    public LhtProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public LhtProgressBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mContext = context;
    }

    @Override
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);
        drawBorder(canvas);
        drawProgress(canvas);
        drawText(canvas);
        playAnimation(canvas);
        drawColorProgressText(canvas);
        mPaint = new Paint();
        postInvalidateDelayed(50);
    }

    private void drawBorder(Canvas canvas) {
        mPaint.setAntiAlias(true);
        mPaint.setColor(backgroundColor);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(dp2px(borderWidth));
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint);
    }

    private void drawProgress(Canvas canvas) {
        mPaint.setColor(backgroundColor);
        mPaint.setStyle(Paint.Style.FILL);
        progressWidth = (float) (getMeasuredWidth() * (progress / maxProgress));
        canvas.drawRect(0, 0, progressWidth, getMeasuredHeight(), mPaint);
    }

    private void drawText(Canvas canvas) {
        textBouds = new Rect();
        mPaint.setColor(backgroundColor);
        mPaint.setTextSize(dp2px(textSize));
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.getTextBounds(progressText, 0, progressText.length(), textBouds);
        canvas.drawText(progressText, getMeasuredWidth() / 2, getMeasuredHeight() / 2 + dp2px(borderWidth), mPaint);
    }

    private void drawColorProgressText(Canvas canvas) {
        mPaint.setColor(textColor);
        int tWidth = textBouds.width();
        float xCoordinate = (getMeasuredWidth() - tWidth) / 2;
        float progressWidth = (progress / maxProgress) * getMeasuredWidth();
        if (progressWidth > xCoordinate) {
            canvas.save(Canvas.CLIP_SAVE_FLAG);
            float right = Math.min(progressWidth, xCoordinate + tWidth);
            canvas.clipRect(xCoordinate, 0, right, getMeasuredHeight());
            canvas.drawText(progressText, getMeasuredWidth() / 2, getMeasuredHeight() / 2 + dp2px(borderWidth), mPaint);
            canvas.restore();
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        if (heightSpecMode == MeasureSpec.AT_MOST) {
            heightSpecSize = (int) (widthSpecSize * 0.15 + 0.5f);
        }
        setMeasuredDimension(widthMeasureSpec, heightSpecSize);
        init();
    }

    private void playAnimation(Canvas canvas) {
        Shader mShader = new RadialGradient(sliderPosition, (getMeasuredHeight() - borderWidth) / 2, getMeasuredHeight(), Color.parseColor("#AAFFFFFF"), Color.parseColor("#00FFFFFF"), Shader.TileMode.REPEAT);
        mPaint.setXfermode(xfermode);
        mPaint.setShader(mShader);
        if (sliderPosition > getMeasuredWidth())
            sliderPosition = 0;
        if (sliderPosition < progressWidth)
            canvas.drawCircle(sliderPosition, (getMeasuredHeight() - borderWidth) / 2, getMeasuredHeight(), mPaint);
        mPaint.setXfermode(null);
        mPaint.setShader(null);
        sliderPosition += getMeasuredWidth() / 30;
    }

    private void init() {
        mPaint = new Paint();
        textBouds = new Rect();
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    @Override
    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public int getTextSize() {
        return textSize;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public float getProgress() {
        return progress;
    }

    public void setProgress(float progress) {
        this.progress = progress;
//        this.invalidate();
    }

    private int px2dp(float pxValue) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    private int dp2px(float dipValue) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public void setProgressText(String progressText) {
        this.progressText = progressText;
    }

    public void setMaxProgress(float maxProgress) {
        this.maxProgress = maxProgress;
    }

}
