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
    const val LANGUAGE = "language"
    const val START_PAGE = "startPage"
    const val FIRST_USE = "firstUse"

    val isCacheFirstAvailable = getDefaultSp(AppApplication.getInstance()).getBoolean(CACHE_FIRST_ENABLE, true)

    private val isDisableLoadingImage = getDefaultSp(AppApplication.getInstance()).getBoolean(DISABLE_LOADING_IMAGE, false)

    fun set(key: String?, value: Any?){
        if (BlankUtils.isBlankString(key) || null == value){
            throw NullPointerException(String.format("Key and value not be null key=%s, value=%s", key, value))
        }

        val edit = getDefaultSp().edit()
        when (value) {
            is String -> edit.putString(key, value)
            is Int -> edit.putInt(key, value)
            is Long -> edit.putLong(key, value)
            is Boolean -> edit.putBoolean(key, value)
            is Float -> edit.putFloat(key, value)
            is Set<*> -> edit.putStringSet(key, value as Set<String>)
            else -> throw IllegalStateException(String.format("Type of value unsupported key=%s, value=%s", key, value))
        }

        edit.apply()
    }

    fun getLanguage(): String{
        return getDefaultSp().getString(LANGUAGE, "en")
    }

    /**
     * wifi 网络下才加载图片
     * */
    fun isLoadImageAvailable(): Boolean{
        return NetUtils.netStatus == NetUtils.TYPE_WIFI || !isDisableLoadingImage
    }

    fun isFirstUse(): Boolean{
        return getDefaultSp().getBoolean(FIRST_USE, true)
    }

    fun getStartPage():String{
        return getDefaultSp(AppApplication.getInstance()).getString(START_PAGE, "Profile")
    }

    private fun getDefaultSp(): SharedPreferences{
        return PreferenceManager.getDefaultSharedPreferences(AppApplication.getInstance())
    }

    private fun getDefaultSp(context: Context): SharedPreferences{
        return PreferenceManager.getDefaultSharedPreferences(context)
    }
}