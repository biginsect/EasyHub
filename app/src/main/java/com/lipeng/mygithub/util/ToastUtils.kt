package com.lipeng.mygithub.util

import android.content.Context
import android.widget.Toast
import com.lipeng.mygithub.constant.ToastType
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
    private var oldMsg = ""
    private var lastTime = 0L
    private var current = 0L

    @JvmStatic
    private fun showToast(context: Context, msg: String?, type: Int, time: Int){

        if (null == msg || msg.trim().isEmpty()){
            Logger.d(TAG, "message is null")
            return
        }

        if (null == mToast) {
            lastTime = System.currentTimeMillis()

            mToast = when (type) {

                ToastType.SUCCESS -> Toasty.success(context, msg, time)
                ToastType.ERROR -> Toasty.error(context, msg, time)
                ToastType.WARNING -> Toasty.warning(context, msg, time)
                ToastType.NORMAL -> Toasty.normal(context, msg, time)
                ToastType.INFO -> Toasty.info(context, msg, time)
                else -> null
            }

            mToast?.show()
        }else{
            current = System.currentTimeMillis()

            if (oldMsg == msg){
                if (time < lastTime - current){
                    mToast?.show()
                }
            }else{
                oldMsg = msg
                /**msg改变，reset*/
                mToast = when (type) {

                    ToastType.SUCCESS -> Toasty.success(context, msg, time)
                    ToastType.ERROR -> Toasty.error(context, msg, time)
                    ToastType.WARNING -> Toasty.warning(context, msg, time)
                    ToastType.NORMAL -> Toasty.normal(context, msg, time)
                    ToastType.INFO -> Toasty.info(context, msg, time)
                    else -> null
                }

                mToast?.show()
            }
        }
    }

    @JvmStatic
    fun showLongToast(context: Context, msg: String?, type: Int){
        showToast(context, msg, type, Toast.LENGTH_LONG)
    }

    @JvmStatic
    fun showShortToast(context: Context, msg: String?, type: Int){
        showToast(context, msg, type, Toast.LENGTH_SHORT)
    }

    @JvmStatic
    fun cancel(){
        mToast?.cancel()
    }
}