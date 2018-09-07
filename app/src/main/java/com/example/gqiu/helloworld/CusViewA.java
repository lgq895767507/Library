package com.example.gqiu.helloworld;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Display;
import android.view.View;

/**
 * Created by gq.liu on 18-8-13.
 */
public class CusViewA extends View {
    public CusViewA(Context context) {
        super(context);
    }

    public CusViewA(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CusViewA(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //测量
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        getMeasuredHeight();
        getMeasuredWidth();


        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    //布局
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        getTop();
        // ...
      //  MeasureSpec.AT_MOST, MeasureSpec.EXACTLY, MeasureSpec.UNSPECIFIED;
        super.onLayout(changed, left, top, right, bottom);
    }

    //绘制
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
       // canvas.drawLine();
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
    }



}
