package com.lipeng.mygithub.app

import android.app.Application
import com.lipeng.mygithub.BuildConfig
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy

/**
 * @author big insect
 */
open class GitApplication :Application() {
    private val tag :String = GitApplication::class.java.simpleName
    companion object {
        @JvmStatic lateinit var instance : GitApplication
        private set
    }


    override fun onCreate() {
        super.onCreate()
        instance = this
        val startTime = System.currentTimeMillis()
        initLogger()
        Logger.t(tag).i("App start time :", startTime)
    }

    private fun initLogger(){
        val strategy : PrettyFormatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)
                .methodCount(0)
                .methodOffset(0)
                .tag("MyGitHub_Logger")
                .build()

        Logger.addLogAdapter(object :AndroidLogAdapter(strategy){
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return BuildConfig.DEBUG
            }
        })
    }
}