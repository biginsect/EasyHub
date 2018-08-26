package com.biginsect.easyhub.util

import android.content.Context
import java.io.File

/**
 * 操作文件的工具类
 * @author big insect
 */
object FileUtils {
    private const val DIR_NAME_CACHE = "http_response"

    private fun getCacheDir(context: Context, dirName :String): File{
        val cacheFile  = File(context.externalCacheDir, dirName)
        if (!cacheFile.exists()){
            cacheFile.mkdir()
        }
        return cacheFile
    }

    fun getHttpCache(context: Context):File{
        return getCacheDir(context, DIR_NAME_CACHE)
    }
}