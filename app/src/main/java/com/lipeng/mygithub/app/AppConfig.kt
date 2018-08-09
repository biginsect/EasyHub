package com.lipeng.mygithub.app

import com.lipeng.mygithub.BuildConfig

/**
 *相关配置，url
 * @author big insect
 * @date  2018/8/9.
 */
object AppConfig {
    const val DB_NAME  = "GitHub"

    const val GIT_HUB_BASE_URL = "https://github.com/"

    const val BASE_API_URL = "https://api.github.com/"

    const val CLIENT_ID = BuildConfig.CLIENT_ID

    const val CLIENT_SECRET = BuildConfig.CLIENT_SECRET

    const val APPLICATION_ID = BuildConfig.APPLICATION_ID

    /**gitHub鉴权成功之后，重定向到url*/
    const val REDIRECT_URL = ""
}