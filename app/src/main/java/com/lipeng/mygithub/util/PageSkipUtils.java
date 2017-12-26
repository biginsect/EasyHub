package com.lipeng.mygithub.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

/**
 * 页面跳转工具
 * @author lipeng
 * @date 2017/12/25
 */

public class PageSkipUtils {

    /**
     * 不允许该工具实例化与被继承
     * */
    private PageSkipUtils(){
    }

    /**
     * 页面跳转,不携带任何数据
     * @param context 当前页面
     * @param target 目标页面
     * */
    public static void skipWithNoData(Activity context, Class target){
        Intent intent = new Intent(context, target);
        context.startActivity(intent);
    }
}
