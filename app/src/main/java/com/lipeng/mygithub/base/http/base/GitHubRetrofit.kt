package com.lipeng.mygithub.base.http.base

import com.lipeng.mygithub.app.AppApplication
import com.lipeng.mygithub.util.FileUtils
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import java.util.concurrent.TimeUnit

/**
 * @author big insect
 */
object  GitHubRetrofit {
    private val TAG = "GitHubRetrofit"
    private var mMap = HashMap<String, Retrofit>()
    private var token:String? = null
    private const val TIME_OUT = 32*1000L

    private fun createRetrofit(baseUrl :String, isJson: Boolean){
        val cache = Cache(FileUtils.getHttpCache(AppApplication.getInstance()),
                TIME_OUT)

        val okHttpClient = OkHttpClient.Builder()
                .connectTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
                .cache(cache)
                .build()

        val builder  = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient)

        if (isJson){
            builder.addConverterFactory(GsonConverterFactory.create())
        }else{
            builder.addConverterFactory(SimpleXmlConverterFactory.create())
        }

        mMap["$baseUrl-$isJson"] = builder.build()
    }

    fun createRetrofit(baseUrl: String, token: String, isJson: Boolean): Retrofit {
        GitHubRetrofit.token = token
        val key = "$baseUrl-$isJson"
        if (!mMap.containsKey(key)){
            createRetrofit(baseUrl, isJson)
        }

        return mMap[key]!!
    }

    fun createRetrofit(baseUrl: String, token: String):Retrofit{
        return createRetrofit(baseUrl, token, isJson = true)
    }
}