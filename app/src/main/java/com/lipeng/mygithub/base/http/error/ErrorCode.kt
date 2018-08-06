package com.lipeng.mygithub.base.http.error

import android.support.annotation.StringRes
import com.lipeng.mygithub.R
import com.lipeng.mygithub.app.AppApplication

/**
 * @author big insect
 */
object ErrorCode {
    /**缓存和网络不可用*/
    const val CACHE_AND_NETWORK_UNAVAILABLE = 1
    /**没有找到该页面*/
    const val PAGE_NOT_FOUND = 2
    /**未授权*/
    const val UNAUTHORIZED = 3
    private val MAP_MSG_ERROR = HashMap<Int, String>()

    init {
        MAP_MSG_ERROR[CACHE_AND_NETWORK_UNAVAILABLE] = getString(R.string.cache_and_network_unavailable)
        MAP_MSG_ERROR[PAGE_NOT_FOUND] = getString(R.string.page_not_found)
        MAP_MSG_ERROR[UNAUTHORIZED] = getString(R.string.unauthorized)
    }

    private fun getString(@StringRes resId: Int): String{
        return AppApplication.getInstance().resources.getString(resId)
    }

    fun getErrorMsg(code: Int): String{
        return MAP_MSG_ERROR[code]!!
    }
}