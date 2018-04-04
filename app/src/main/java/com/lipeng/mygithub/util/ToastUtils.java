package com.lipeng.mygithub.util;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.lipeng.mygithub.constant.ToastType;
import com.orhanobut.logger.Logger;

import es.dmoral.toasty.Toasty;

/**
 * 提示语的工具类，封装第三方工具类Toasty，防止重复显示
 * @author lipeng
 * @date 2017/12/25
 */

public final class ToastUtils {
    private final static String TAG = "ToastUtils";
    private static Toast mToast = null;
    private static String oldMessage = "";
    /**最后一次弹提示的时间*/
    private static long lastTime = 0;
    /**当前记录的时间*/
    private static long currentTime = 0;

    private ToastUtils(){
        throw new UnsupportedOperationException(TAG + "  " + "Cannot be initialized");
    }

    /**
     * 长提示
     * @param context 上下文
     * @param msg 提示信息
     * @param time 提示时长类型
     * @param type 提示的类型
     * */
    private static void showToast(Context context, String msg, int time, ToastType type){
        /*提示信息不能为空*/
        if (TextUtils.isEmpty(msg)){
            Logger.d(TAG, "Toast message is null");
            return;
        }
        if (mToast == null){
            /**记录最后一次弹toast的时间*/
            lastTime = System.currentTimeMillis();
            setToastType(context, msg, time, type);
            mToast.show();
        }else {
            /**记录当前toast的时间*/
            currentTime = System.currentTimeMillis();
            if (msg.equals(oldMessage)){
                /**相同的toast内容，时间间隔大于toast的时长，直接显示*/
                if (currentTime - lastTime > time){
                    mToast.show();
                }
            }else {
                /**否则记录当前的提示内容，用于与下次toast内容做比较*/
                oldMessage = msg;
                /**内容改变，需重新设置toast*/
                setToastType(context, msg, time, type);
                mToast.show();
            }
        }

        /*重置记录的时间*/
        currentTime = lastTime;
    }

    /**
     * 设置提示信息的类型
     * */
    private static void setToastType(Context context, String  msg, int time, ToastType type){
        switch (type){
            case INFO:
                mToast = Toasty.info(context, msg, time);
                break;
            case NORMAL:
                mToast = Toasty.normal(context, msg, time);
                break;
            case WARNING:
                mToast = Toasty.warning(context, msg, time);
                break;
            case ERROR:
                mToast = Toasty.error(context, msg, time);
                break;
            case SUCCESS:
                mToast = Toasty.success(context, msg, time);
                break;
            default:
                break;
        }
    }

    /**
     * 短提示
     * @param context 上下文环境
     * @param msg 提示信息
     * */
    public static void showShortToast(Context context, String msg, ToastType type){
        showToast(context, msg, Toast.LENGTH_SHORT, type);
    }

    /**
     * 长提示
     * @param context 上下文环境
     * @param msg 提示信息
     */
    public static void showLongToast(Context context, String msg, ToastType type){
        showToast(context, msg, Toast.LENGTH_LONG, type);
    }

    /**
     * 取消toast提示
     * */
    public static void cancelToast(){
        mToast.cancel();
    }
}
