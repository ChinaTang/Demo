package com.tangdi.demo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by tangdi on 10/12/17.
 */

public class CustomerProgess extends View {

    private int firstcolor;

    private int secondcolor;

    private int speed;

    private int circlewidth;

    private Paint paint;

    private int prosses;

    private String text;

    private Rect mTextBound;

    private int textsize;

    private boolean isOver;

    /**
     * 旋转结束回调
     */
    public interface overCallback{
        void over();
    }

    private overCallback callback;


    public CustomerProgess(Context context) {
        super(context);
    }

    public CustomerProgess(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomerProgess(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomerProgess, defStyleAttr, 0);
        int n = a.getIndexCount();
        for(int i = 0; i < n; i++){
            int attr = a.getIndex(i);
            switch(attr){
                case R.styleable.CustomerProgess_firstcolor:
                    firstcolor = a.getColor(attr, Color.GREEN);
                    break;
                case R.styleable.CustomerProgess_secondcolor:
                    secondcolor = a.getColor(attr, Color.BLUE);
                    break;
                case R.styleable.CustomerProgess_speed:
                    speed = a.getInt(attr, 0);
                    break;
                case R.styleable.CustomerProgess_circlewidth:
                    circlewidth =  a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_PX, 20, getResources().getDisplayMetrics()));
                    break;
                case R.styleable.CustomerProgess_circletext:
                    text = a.getString(attr);
                    break;
                case R.styleable.CustomerProgess_circletextsize:
                    textsize = a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_PX, 20, getResources().getDisplayMetrics()));
            }
        }

        paint = new Paint();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    prosses++;
                    if(prosses == 360){
                        if(callback != null){
                            callback.over();
                            return;
                        }
                    }
                    if(isOver){
                        return;
                    }
                    postInvalidate();
                    try {
                        Thread.sleep(speed);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        paint.setTextSize(textsize);
        mTextBound = new Rect();
        paint.getTextBounds(text, 0, text.length(), mTextBound);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public void onDraw(Canvas canvas){
        int centre = getWidth() / 2;
        int radius = centre - circlewidth;

        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawText(text, centre - mTextBound.width() / 2, centre, paint);

        paint.setStrokeWidth(circlewidth);
        paint.setAntiAlias(true); // 消除锯齿
        paint.setStyle(Paint.Style.STROKE); // 设置空心
        RectF oval = new RectF(centre - radius, centre - radius, centre + radius, centre + radius); // 用于定义的圆弧的形状和大小的界限

        paint.setColor(firstcolor); // 设置圆环的颜色
        canvas.drawCircle(centre, centre, radius, paint); // 画出圆环
        paint.setColor(secondcolor); // 设置圆环的颜色
        canvas.drawArc(oval, -90, prosses, false, paint); // 根据进度画圆弧

    }

    public void setOverLinstner(overCallback overCallback){
        this.callback = overCallback;
    }

    public void overThread(){
        isOver = true;
    }
}
