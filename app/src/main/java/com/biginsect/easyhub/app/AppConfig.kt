package com.biginsect.easyhub.app

import com.biginsect.easyhub.BuildConfig
import com.biginsect.easyhub.util.StringUtils

/**
 * 相关配置，url
 * @author big insect
 * @date  2018/8/9.
 */
object AppConfig {
    const val DB_NAME  = "GitHub"

    const val GIT_HUB_BASE_URL = "https://github.com/"

    const val BASE_API_URL = "https://api.github.com/"

    const val OAUTH_URL = GIT_HUB_BASE_URL + "login/oauth/authorize"

    const val OAUTH2_SCOPE = "user,repo,gist,notifications"

    const val CLIENT_ID = BuildConfig.CLIENT_ID

    const val CLIENT_SECRET = BuildConfig.CLIENT_SECRET

    const val APPLICATION_ID = BuildConfig.APPLICATION_ID

    /**gitHub鉴权成功之后，重定向到url*/
    const val REDIRECT_URL = ""

    const val HTTP_CACHE_MAX_AGE = 32 * 1024 * 1024
    /**缓存最长存放时间*/
    const val CACHE_MAX_AGE = 4 * 7 * 24 * 60 *60

    const val FORCE_NETWORK = "forceNetwork"

    private val COMMON_PAGE_URL_LIST: List<String> = listOf("https://github.com/trending")

    fun isCommonPageUrl(url: String?): Boolean{
        if (StringUtils.isBlankString(url)){
            return false
        }

        for (commonUrl in COMMON_PAGE_URL_LIST){
            if (url!!.contains(commonUrl)){
                return true
            }
        }
        
        return false
    }
}