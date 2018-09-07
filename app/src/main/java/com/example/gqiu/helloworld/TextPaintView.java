package com.example.gqiu.helloworld;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by gq.liu on 18-9-6.
 */
public class TextPaintView extends View {

    private TextPaint textPaint;
    private StaticLayout staticLayout;

    public TextPaintView(Context context) {
        super(context);
        initView();
    }

    public TextPaintView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public TextPaintView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView(){
        textPaint = new TextPaint();
        textPaint.setColor(Color.RED);
        textPaint.setTextSize(20);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (staticLayout == null){
            staticLayout = new StaticLayout("sadasdasddddsssssssssssss",textPaint, 100, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, true);
            staticLayout.draw(canvas);
        }
    }
}
