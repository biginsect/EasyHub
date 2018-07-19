package com.lipeng.mygithub.util

import android.content.Context
import android.net.ConnectivityManager

/**
 * @author big insect
 */
object NetUtils {
    fun isNetworkConnected(context: Context?):Boolean{
        val manager: ConnectivityManager? = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = manager?.activeNetworkInfo
        if (networkInfo != null) {
            return networkInfo.isAvailable
        }

        return false
    }

}