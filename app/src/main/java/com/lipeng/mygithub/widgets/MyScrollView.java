package com.lipeng.mygithub.widgets;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * 自定义ScrollView
 * @author biginsect
 * @date 2018/1/5
 */

public class MyScrollView extends ScrollView{
    private ScrollListener mScrollListener;

    public MyScrollView(Context context){
        this(context, null);
    }

    public MyScrollView(Context context, AttributeSet attrs){
        this(context, attrs, 0);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyle){
        super(context, attrs, defStyle);
    }

    public void setOnScrollListener(ScrollListener listener){
        this.mScrollListener = listener;
    }

    /**解决ScrollView嵌套RecyclerView自动滚动到底部的问题*/
    @Override
    protected int computeScrollDeltaToGetChildRectOnScreen(Rect rect) {
        return 0;
    }

    /**
     * 对自定义ScrollView的监听
     * */
    public interface ScrollListener{
        /**
         * 当前ScrollView状态更改之后进行回调处理
         * @param scrollView 当前对象
         *
         * @param x 当前水平滚动原点
         * @param y 当前垂直滚动点
         * @param oldX 原水平滚动点.
         * @param oldY 原垂直滚动点
         * */
        void onScrollChanged(MyScrollView scrollView, int x, int y,
                             int oldX, int oldY);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldL, int oldT) {
        super.onScrollChanged(l, t, oldL, oldT);
        if (null != mScrollListener){
            mScrollListener.onScrollChanged(this, l, t,
                    oldL, oldT );
        }
    }
}
