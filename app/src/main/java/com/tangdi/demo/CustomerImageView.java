package com.tangdi.demo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by tangdi on 10/12/17.
 */

public class CustomerImageView extends View {

    private Bitmap imageView;

    private String text;

    private int textSize;

    private int imageScale;

    private int textColor;

    private int width;

    private int height;

    private Rect mTextBound, rect;

    private Paint paint;

    public CustomerImageView(Context context) {
        super(context);
    }

    public CustomerImageView(Context context, @Nullable AttributeSet attrs) {
        //super(context, attrs);
        this(context, attrs, 0);
    }

    public CustomerImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomerImageView, defStyleAttr, 0);
        int n = a.getIndexCount();
        for (int i = 0; i < n; i++){
            int attr = a.getIndex(i);
            switch (attr){
                case R.styleable.CustomerImageView_image:
                    imageView = BitmapFactory.decodeResource(getResources(), a.getResourceId(attr, 0));
                    break;
                case R.styleable.CustomerImageView_imageScaleType:
                    imageScale = a.getInt(attr, 0);
                    break;
                case R.styleable.CustomerImageView_text:
                    text = a.getString(attr);
                    break;
                case R.styleable.CustomerImageView_textcolor:
                    textColor = a.getColor(attr, 0);
                    break;
                case R.styleable.CustomerImageView_textSize:
                    textSize = a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                            16, getResources().getDisplayMetrics()));
                    break;
            }
        }
        a.recycle();
        paint = new Paint();
        mTextBound = new Rect();
        rect = new Rect();
        paint.setTextSize(textSize);
        paint.getTextBounds(text, 0, text.length(), mTextBound);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specSize = MeasureSpec.getSize(widthMeasureSpec);
        if(specMode == MeasureSpec.EXACTLY){
            width = specSize;
        }else{
            int desireByImg = getPaddingLeft() + getPaddingRight() + imageView.getWidth();
            int desireByTitle = getPaddingLeft() + getPaddingRight() + mTextBound.width();
            if(specMode == MeasureSpec.AT_MOST){
                int desr = Math.max(desireByImg, desireByTitle);
                width = Math.min(desr, specSize);
            }
        }

        specMode = MeasureSpec.getMode(heightMeasureSpec);
        specSize = MeasureSpec.getSize(heightMeasureSpec);
        if(specMode == MeasureSpec.EXACTLY){
            height = specSize;
        }else{
            int desireByImg = getPaddingLeft() + getPaddingRight() + imageView.getWidth();
            int desireByTitle = getPaddingLeft() + getPaddingRight() + mTextBound.width();
            if(specMode == MeasureSpec.AT_MOST){
                int desr = Math.max(desireByImg, desireByTitle);
                height = Math.min(desr, specSize);
            }
        }

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas){
        paint.setStrokeWidth(4);
        paint.setColor(Color.CYAN);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), paint);

        rect.left = getPaddingLeft();
        rect.right = width - getPaddingRight();
        rect.top = getPaddingTop();
        rect.bottom = height - getPaddingBottom();

        paint.setColor(textColor);
        paint.setStyle(Paint.Style.FILL);

        if(mTextBound.width() > width){
            TextPaint textPaint = new TextPaint(paint);
            String Message = TextUtils.ellipsize(text, textPaint, (float) width - getPaddingLeft() - getPaddingRight(),
                    TextUtils.TruncateAt.END).toString();
            canvas.drawText(Message,getPaddingLeft(), height - getPaddingBottom(), paint);
        }else{
            canvas.drawText(text, width / 2 - mTextBound.width() * 1.0f / 2, height - getPaddingBottom(), paint);
        }

        rect.bottom -= mTextBound.height();

        //计算居中的矩形范围
        rect.left = width / 2 - imageView.getWidth() / 2;
        rect.right = width / 2 + imageView.getWidth() / 2;
        rect.top = (height - mTextBound.height()) / 2 - imageView.getHeight() / 2;
        rect.bottom = (height - mTextBound.height()) / 2 + imageView.getHeight() / 2;

        canvas.drawBitmap(imageView, null, rect, paint);
    }


}
