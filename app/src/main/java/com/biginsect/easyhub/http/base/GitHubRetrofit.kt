package com.biginsect.easyhub.http.base

import com.biginsect.easyhub.app.AppApplication
import com.biginsect.easyhub.app.AppConfig
import com.biginsect.easyhub.app.AppData
import com.biginsect.easyhub.util.BlankUtils
import com.biginsect.easyhub.util.FileUtils
import com.biginsect.easyhub.util.NetUtils
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
    private const val CACHE_MAX_SIZE = 32 * 1024 * 1024L
    private const val TIME_OUT = 32*1000L

    private fun createRetrofit(baseUrl :String, isJson: Boolean){
        val cache = Cache(FileUtils.getHttpCache(AppApplication.getInstance()),
                CACHE_MAX_SIZE)

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
            builder.addConverterFactory(SimpleXmlConverterFactory.createNonStrict())
        }

        mMap["$baseUrl-$isJson"] = builder.build()
    }

    fun createRetrofit(baseUrl: String, token: String?, isJson: Boolean): Retrofit {
        this.token = token
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

                /**需要将token放入请求头中才能获取到信息*/
                request = request.newBuilder()
                        .addHeader("Authorization", auth)
                        .build()
            }

            /**强制使用网络请求数据*/
            val force = request.header(AppConfig.FORCE_NETWORK)
            if (!BlankUtils.isBlankString(force) && !NetUtils.isNetworkAvailable()){//
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
     * ？github服务器不支持缓存，自行设置
     * */
    private class NetworkInterceptor :Interceptor{
        override fun intercept(chain: Interceptor.Chain): Response {
            val request = chain.request()
            val response = chain.proceed(request)

            var requestCacheControl = request.cacheControl().toString()

            val forceNetwork = request.header(AppConfig.FORCE_NETWORK)
            if (!BlankUtils.isBlankString(forceNetwork)){
                requestCacheControl = getCacheInfo()
            }



            return  if (BlankUtils.isBlankString(requestCacheControl)) {
                response
            }else{//设置缓存
                response.newBuilder()
                        .header("Cache-Control", requestCacheControl)
                        .removeHeader("Pragma")
                        .build()
            }
        }
    }

    private fun getCacheInfo(): String {
        return "public, max-age=${AppConfig.CACHE_MAX_AGE}"
    }
}