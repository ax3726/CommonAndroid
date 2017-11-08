package com.lm.rxtest.widget;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2017/11/7 0007.
 */

public class MyView extends View {
    public MyView(Context context) {
        super(context);
    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setBackgroundColor(Color.parseColor("#88ffff00"));
        Paint paint=new Paint();
        paint.setColor(Color.parseColor("#ff00ff"));
        //paint.setStyle(Paint.Style.FILL);//实心
        paint.setStyle(Paint.Style.STROKE);//实心
        paint.setAntiAlias(true);//消除锯齿
      paint.setStrokeWidth(10);
        canvas.drawCircle(300,200,50,paint);
        float [] ss=new float[]{30,34,56,77,88,99,100,22,200,449,44,300,440,500};

        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(20);
        paint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawPoints(ss,paint);
        paint.setStyle(Paint.Style.STROKE);//实心

        paint.setStrokeCap(Paint.Cap.SQUARE);
        canvas.drawCircle(400,200,50,paint);



        canvas.drawLine(470, 200, 570, 500, paint);


        float [] aa=new float[]{600,600,800,600,700,600,700,800,600,800,800,800};


        canvas.drawLines(aa, paint);



    }
}
