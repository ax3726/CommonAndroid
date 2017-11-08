package com.lm.rxtest.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ProgressBar;

/**
 * Created by Administrator on 2017/11/7 0007.
 */

public class MyProgress extends ProgressBar {
    public MyProgress(Context context) {
        super(context);
    }

    public MyProgress(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setColor(Color.parseColor("#ffffff"));

        canvas.drawText("立即投资", getWidth() / 2, getHeight() / 2, paint);
    }
}
