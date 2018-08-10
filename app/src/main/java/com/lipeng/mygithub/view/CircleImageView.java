package com.lipeng.mygithub.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.TypedValue;

import com.lipeng.mygithub.R;

/**
 * 自定义圆形ImageView
 * @author biginsect
 * @date 2018/1/4
 */

public final class CircleImageView extends AppCompatImageView {

    /**圆形模式*/
    private static final int MODE_CIRCLE = 1;
    /**普通模式*/
    private static final int MODE_NORMAL = 0;
    /**圆角模式*/
    private static final int MODE_ROUND = 2;
    /**全局画笔*/
    private Paint mPaint;
    /**当前模式*/
    private int currentMode = 0;
    /**圆角半径*/
    private int currentRound = dpToPx(10);

    public CircleImageView(Context context){
        super(context);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
    }

    public CircleImageView(Context context, AttributeSet attrs){
        this(context, attrs, 0);
    }

    public CircleImageView(Context context, AttributeSet attrs, int defStyle){
        super(context, attrs, defStyle);
        obtainStyledAttrs(context, attrs, defStyle);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
    }

    private void obtainStyledAttrs(Context context, AttributeSet attrs, int defStyleAttrs){
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.CircleImageView, defStyleAttrs, 0);
        currentMode = a.hasValue(R.styleable.CircleImageView_type) ?
                a.getInt(R.styleable.CircleImageView_type, MODE_NORMAL) : MODE_NORMAL;
        currentRound = a.hasValue(R.styleable.CircleImageView_radius) ?
                a.getDimensionPixelSize(R.styleable.CircleImageView_radius, currentRound) : currentRound;
        a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        /**圆形模式，宽高一致*/
        if (currentMode == MODE_CIRCLE) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            int result = Math.min(getMeasuredHeight(), getMeasuredWidth());
            setMeasuredDimension(result, result);
        }else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Drawable drawable = getDrawable();
        Matrix matrix = getImageMatrix();
        /**无法识别URI*/
        if (null == drawable){
            return;
        }
        /**drawable没有内容*/
        if (drawable.getIntrinsicWidth() == 0 || drawable.getIntrinsicHeight() == 0){
            return;
        }

        if (null == matrix && 0 == getPaddingTop() && 0 == getPaddingLeft()){
            drawable.draw(canvas);
        }else {
            final int saveCount = canvas.getSaveCount();
            canvas.save();

            if (getCropToPadding()){
                final int scrollX = getScrollX();
                final int scrollY = getScrollY();
                canvas.clipRect(scrollX + getPaddingLeft(), scrollY + getPaddingTop(),
                        scrollX + getRight() - getRight() - getPaddingRight(),
                        scrollY + getBottom() - getTop() - getPaddingBottom());
            }

            canvas.translate(getPaddingLeft(), getPaddingTop());

            if (currentMode == MODE_CIRCLE){
                setBitmapShader(drawable);
                canvas.drawCircle(getWidth() / 2, getHeight() / 2, getWidth() / 2, mPaint);
            }else if (currentMode == MODE_ROUND) {
                setBitmapShader(drawable);
                canvas.drawRoundRect(getRectF(), currentRound, currentRound, mPaint);
            }else {
                if (null != matrix){
                    canvas.concat(matrix);
                }
                drawable.draw(canvas);
            }
            canvas.restoreToCount(saveCount);
        }
    }

    private RectF getRectF(){
        return new RectF(getPaddingLeft(), getPaddingTop(),
                getWidth() - getPaddingRight(),
                getHeight() - getPaddingBottom());
    }

    private void setBitmapShader(Drawable drawable){
        Bitmap bitmap = drawableToBitmap(drawable);
        mPaint.setShader(new BitmapShader(bitmap,
                Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
    }

    /**
     * drawable转换成bitmap
     * */
    private Bitmap drawableToBitmap(Drawable drawable){
        if (null == drawable){
            return null;
        }

        Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        /**根据scale type 获取matrix*/
        Matrix matrix = getImageMatrix();
        /**将matrix传递给bitmap*/
        if (null != matrix){
            canvas.concat(matrix);
        }
        drawable.draw(canvas);

        return bitmap;
    }

    /**
     * dp转换成px
     * @param value px的值
     * */
    public int dpToPx(float value){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                value, getResources().getDisplayMetrics());
    }
}
