package com.biginsect.easygithub.util

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.biginsect.easygithub.app.AppApplication

/**
 * @author big insect
 * @date 2018/8/24.
 */
object PreUtils {
    private const val CACHE_FIRST_ENABLE = "cacheFirstEnable"

    val isCacheFirstAvailable = getDefaultSp(AppApplication.getInstance()).getBoolean(CACHE_FIRST_ENABLE, true)

    fun getDefaultSp(context: Context): SharedPreferences{
        return PreferenceManager.getDefaultSharedPreferences(context)
    }
}