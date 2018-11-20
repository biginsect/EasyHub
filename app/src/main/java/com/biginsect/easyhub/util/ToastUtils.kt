package com.biginsect.easyhub.util

import android.content.Context
import android.widget.Toast
import com.biginsect.easyhub.constant.ToastType
import com.orhanobut.logger.Logger
import es.dmoral.toasty.Toasty

/**
 * 封装toasty，多次点击toast只显示一次
 * @author big insect
 * @date 2018/8/10.
 */
object ToastUtils {
    private const val  TAG = "ToastUtils"
    private var mToast: Toast? = null

    private fun showToast(context: Context, msg: String?, type: ToastType, time: Int){

        if (null == msg || msg.trim().isEmpty()){
            Logger.d(TAG, "message is null")
            return
        }

        if(mToast == null){
            mToast = setToast(context, msg, type, time)
        }else{
            mToast?.setText(msg)
        }

        mToast?.show()
    }

    fun showLongToast(context: Context, msg: String?, type: ToastType){
        showToast(context, msg, type, Toast.LENGTH_LONG)
    }

    fun showShortToast(context: Context, msg: String?, type: ToastType){
        showToast(context, msg, type, Toast.LENGTH_SHORT)
    }

    fun cancel(){
        mToast?.cancel()
    }

    private fun setToast(context: Context, msg: String, type: ToastType, time: Int): Toast{
        return when(type){
            ToastType.ERROR -> Toasty.error(context, msg, time)
            ToastType.SUCCESS -> Toasty.success(context, msg, time)
            ToastType.WARNING -> Toasty.warning(context, msg, time)
            ToastType.NORMAL -> Toasty.normal(context, msg, time)
            ToastType.INFO -> Toasty.info(context, msg, time)
        }
    }
}