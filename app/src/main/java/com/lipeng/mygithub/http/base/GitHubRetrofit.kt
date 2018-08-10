package com.lipeng.mygithub.http.base

import com.lipeng.mygithub.app.AppApplication
import com.lipeng.mygithub.app.AppConfig
import com.lipeng.mygithub.app.AppData
import com.lipeng.mygithub.util.BlankUtils
import com.lipeng.mygithub.util.FileUtils
import com.lipeng.mygithub.util.NetUtils
import okhttp3.*
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
                .addInterceptor(AppInterceptor())
                .addNetworkInterceptor(NetworkInterceptor())
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

    fun createRetrofit(baseUrl: String, token: String?, isJson: Boolean): Retrofit {
        GitHubRetrofit.token = token
        val key = "$baseUrl-$isJson"
        if (!mMap.containsKey(key)){
            createRetrofit(baseUrl, isJson)
        }

        return mMap[key]!!
    }

    fun createRetrofit(baseUrl: String, token: String?):Retrofit{
        return createRetrofit(baseUrl, token, isJson = true)
    }

    /**
     * 应用发出请求到okhttp核心之间的拦截器
     * */
    private class AppInterceptor :Interceptor{
        override fun intercept(chain: Interceptor.Chain): Response {
            var request = chain.request()

            /**添加一个唯一的loginId字符用于区分存储在缓存中的*/
            if (null != AppData.loggedUser &&
                    !AppConfig.isCommonPageUrl(request.url().toString())){
                val httpUrl = request.url().newBuilder()
                        .addQueryParameter("LoginId", AppData.loggedUser?.login)
                        .build()

                request = request.newBuilder()
                        .url(httpUrl)
                        .build()

            }

            /**添加访问token*/
            if (!BlankUtils.isBlankString(token)){
                val auth = if (token!!.startsWith("Basic")){
                    token as String
                }else{
                    "token $token"
                }

                request = request.newBuilder()
                        .addHeader("Authorization", auth)
                        .build()
            }

            /**强制使用网络请求数据*/
            val force = request.header("forceNetWork")
            if (!BlankUtils.isBlankString(force) && !NetUtils.isNetwrokAvailable()){//
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build()
            }else if ("true" == force){
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_NETWORK)
                        .build()
            }

            return chain.proceed(request)
        }
    }

    /**
     * okhttp核心到远程服务器之间的拦截器
     * */
    private class NetworkInterceptor :Interceptor{
        override fun intercept(chain: Interceptor.Chain): Response {
            val request = chain.request()

            return chain.proceed(request)
        }
    }
}