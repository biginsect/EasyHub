package com.lipeng.mygithub.util;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

/**
 * 提示语的工具类，防止重复显示
 * @author lipeng
 * @date 2017/12/25
 */

public class ToastUtils {
    private final static String TAG = "ToastUtils";
    private static Toast mToast = null;
    private static String oldMessage = "";
    /**最后一次弹提示的时间*/
    private static long lastTime = 0;
    private static long currentTime = 0;

    private ToastUtils(){
    }

    /**
     * 长提示
     * @param context 上下文
     * @param msg 提示信息
     * */
    private static void showToast(Context context, String msg, int type){
        /*提示信息不能为空*/
        if (TextUtils.isEmpty(msg)){
            Log.d(TAG, "Toast message is null");
            return;
        }
        if (mToast == null){
            mToast = Toast.makeText(context, msg, type);
            mToast.show();
            lastTime = System.currentTimeMillis();
        }else {
            currentTime = System.currentTimeMillis();
            if (msg.equals(oldMessage)){
                if (currentTime - lastTime > type){
                    mToast.show();
                }
            }else {
                oldMessage = msg;
                mToast.setText(msg);
                mToast.show();
            }
        }

        /*重置记录的时间*/
        currentTime = lastTime;
    }

    /**
     * 短提示
     * @param context 上下文环境
     * @param msg 提示信息
     * */
    public static void showShortToast(Context context, String msg){
        showToast(context, msg, Toast.LENGTH_SHORT);
    }

    /**
     * 长提示
     * @param context 上下文环境
     * @param msg 提示信息
     */
    public static void showLongToast(Context context, String msg){
        showToast(context, msg, Toast.LENGTH_LONG);
    }
}
