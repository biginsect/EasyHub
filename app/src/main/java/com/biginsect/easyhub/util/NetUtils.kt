package com.biginsect.easyhub.util

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.util.Log

@SuppressLint("StaticFieldLeak")
/**
 * @author big insect
 */
object NetUtils {
    private const val TYPE_DISCONNECTED = 0
    const val TYPE_WIFI = 1
    private const val TYPE_MOBILE = 2

    var netStatus:Int = -1
    private set

    private lateinit var context:Context

    fun init(context: Context){
        this.context = context
        checkStatus()
    }

    fun isNetworkConnected(context: Context?):Boolean{
        val manager: ConnectivityManager? = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = manager?.activeNetworkInfo
        if (networkInfo != null) {
            return networkInfo.isAvailable
        }

        return false
    }

    fun checkStatus(){
        try {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val info = connectivityManager.activeNetworkInfo
            if (null != info && info.isAvailable){
                if (info.state == NetworkInfo.State.CONNECTED){
                    if (info.type == ConnectivityManager.TYPE_WIFI){
                        netStatus = TYPE_WIFI
                    }
                    if (info.type == ConnectivityManager.TYPE_MOBILE){
                        netStatus = TYPE_MOBILE
                    }
                }
            }else{
                netStatus = TYPE_DISCONNECTED
            }
        }catch (e: Exception){
            Log.d("----error----", e.toString())
            e.printStackTrace()
            netStatus = TYPE_DISCONNECTED
        }
    }

    fun isNetworkAvailable(): Boolean{
        return netStatus == TYPE_MOBILE || netStatus == TYPE_WIFI
    }
}