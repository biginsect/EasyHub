package com.lipeng.mygithub.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by lipeng-ds3 on 2018/1/5.
 */

public class MyScrollView extends ScrollView{

    public MyScrollView(Context context){
        this(context, null);
    }

    public MyScrollView(Context context, AttributeSet attrs){
        this(context, attrs, 0);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyle){
        super(context, attrs, defStyle);
    }

    public void setOnScrollListener(){

    }
}
