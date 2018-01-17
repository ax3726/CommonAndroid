package com.lm.base.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/***
 * 自定义进度条
 */
public class SpringProgressHomeView extends View {

    /**
     * 分段颜色
     */
    private static final int[] SECTION_COLORS = {Color.parseColor("#feb230")};
    /**
     * 进度条最大值
     */
    private float maxCount;
    /**
     * 进度条当前值
     */
    private float currentCount;
    /**
     * 画笔
     */
    private Paint mPaint;
    private int mWidth, mHeight;

    public SpringProgressHomeView(Context context, AttributeSet attrs,
                                  int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    public SpringProgressHomeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public SpringProgressHomeView(Context context) {
        super(context);
        initView(context);
    }

    private void initView(Context context) {
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        int round = mHeight / 2;
        System.out.println("max=" + maxCount + "  current=" + currentCount);
//		mPaint.setColor(Color.rgb(71, 76, 80));
        int sc = canvas.saveLayer(0, 0, mWidth, mHeight, null, Canvas.ALL_SAVE_FLAG);//将绘制操作保存到新的图层


        mPaint.setColor(Color.parseColor("#d6d6d6d6"));
        RectF rectBg = new RectF(0, 0, mWidth, mHeight);
        canvas.drawRoundRect(rectBg, round, round, mPaint);
        mPaint.setColor(Color.parseColor("#e5dede"));

        RectF rectBlackBg = new RectF(0, 0, mWidth, mHeight);

        canvas.drawRoundRect(rectBlackBg, round, round, mPaint);

        float section = currentCount / maxCount;
        RectF rectProgressBg = new RectF(0, 0, (mWidth) * section, mHeight);
//		if(section <= 1.0f/3.0f){
        if (section != 0.0f) {
            mPaint.setColor(SECTION_COLORS[0]);
        } else {
            mPaint.setColor(Color.TRANSPARENT);
        }
//		}else{
//			int count = (section <= 1.0f/3.0f*2 ) ? 2 : 3;
//			int[] colors = new int[count];
////			System.arraycopy(SECTION_COLORS, 0, colors, 0, count);
//			float[] positions = new float[count];
//			if(count == 2){
//				positions[0] = 0.0f;
//				positions[1] = 1.0f-positions[0];
//			}else{
//				positions[0] = 0.0f;
//				positions[1] = (maxCount/3)/currentCount;
//				positions[2] = 1.0f-positions[0]*2;
//			}
//			positions[positions.length-1] = 1.0f;
//			LinearGradient shader = new LinearGradient(3, 3, (mWidth-3)*section, mHeight-3, colors,null, Shader.TileMode.MIRROR);
//			mPaint.setShader(shader);
//		}

        // 设置混合模式
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN)); // 叠加处绘制源图
        canvas.drawRoundRect(rectProgressBg, round, 0, mPaint);

        Rect targetRect = new Rect(0, 0, mWidth, mHeight);
        Paint paint = new Paint();
        paint.setTextSize(dipToPx(16));

        String testString = "立即投资";

        paint.setColor(Color.TRANSPARENT);
        canvas.drawRect(targetRect, paint);

        paint.setColor(Color.parseColor("#ffffff"));
        Paint.FontMetricsInt fontMetrics = paint.getFontMetricsInt();

        int baseline = (targetRect.bottom + targetRect.top - fontMetrics.bottom - fontMetrics.top) / 2;
        // 下面这行是实现水平居中，drawText对应改为传入targetRect.centerX()
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(testString, targetRect.centerX(), baseline, paint);


        paint.setXfermode(null);
        canvas.restoreToCount(sc);

    }

    private int dipToPx(int dip) {
        float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f * (dip >= 0 ? 1 : -1));
    }

    /***
     * 设置最大的进度值
     * @param maxCount
     */
    public void setMaxCount(float maxCount) {
        this.maxCount = maxCount;
    }

    /***
     * 设置当前的进度值
     * @param currentCount
     */
    public void setCurrentCount(float currentCount) {
        this.currentCount = currentCount > maxCount ? maxCount : currentCount;
       // anCount = 0;
       // mHandler.sendEmptyMessage(0);
        invalidate();
    }

    private float maxAnCount = 0;
    private float anCount = 0;


   /* private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (anCount < maxAnCount) {
                anCount++;
                currentCount = anCount;
                invalidate();
                postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        sendEmptyMessage(0);
                    }
                }, 10);
            }

        }
    };*/


    public float getMaxCount() {
        return maxCount;
    }

    public float getCurrentCount() {
        return currentCount;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        if (widthSpecMode == MeasureSpec.EXACTLY || widthSpecMode == MeasureSpec.AT_MOST) {
            mWidth = widthSpecSize;
        } else {
            mWidth = 0;
        }
        if (heightSpecMode == MeasureSpec.AT_MOST || heightSpecMode == MeasureSpec.UNSPECIFIED) {
            mHeight = dipToPx(15);
        } else {
            mHeight = heightSpecSize;
        }
        setMeasuredDimension(mWidth, mHeight);
    }


}
