package com.biginsect.easyhub.util

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.biginsect.easyhub.app.AppApplication

/**
 * @author big insect
 * @date 2018/8/24.
 */
object PreUtils {
    private const val CACHE_FIRST_ENABLE = "cacheFirstEnable"
    private const val DISABLE_LOADING_IMAGE = "disableLoadingImage"

    val isCacheFirstAvailable = getDefaultSp(AppApplication.getInstance()).getBoolean(CACHE_FIRST_ENABLE, true)

    private val isDisableLoadingImage = getDefaultSp(AppApplication.getInstance()).getBoolean(DISABLE_LOADING_IMAGE, false)

    private fun getDefaultSp(context: Context): SharedPreferences{
        return PreferenceManager.getDefaultSharedPreferences(context)
    }

    /**
     * wifi 网络下才加载图片
     * */
    fun isLoadImageAvailable(): Boolean{
        return NetUtils.netStatus == NetUtils.TYPE_WIFI || !isDisableLoadingImage
    }
}