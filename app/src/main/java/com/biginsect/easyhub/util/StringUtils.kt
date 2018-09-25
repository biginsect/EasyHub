package com.biginsect.easyhub.util

import android.content.Context
import com.biginsect.easyhub.R
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author big insect
 * @date 2018/8/31.
 */
object StringUtils {

    /**
     * 根据event的时间，设置展示的内容
     * 1.just now
     * 2.seconds ago
     * 3.minutes ago
     * 4.hours ago
     * 5.days ago
     * 6.间隔大于30天显示具体日期
     * */
    fun getTimeString(context: Context, date: Date):String{
        val timeInterval = System.currentTimeMillis() - date.time

        val milliseconds = 1000.0f
        val seconds = 60 * milliseconds
        val minutes = 60 * seconds
        val hours = 24 * minutes
        val days = 30 * hours

        return when(timeInterval){
            in 0f..milliseconds -> context.getString(R.string.just_now)
            in milliseconds..seconds -> context.getString(R.string.seconds_ago)
            in seconds..minutes -> context.getString(R.string.minutes_ago)
            in minutes..hours -> context.getString(R.string.hours_ago)
            in hours..days -> context.getString(R.string.days_ago)

            else ->{
                SimpleDateFormat("MMM d, yyyy", Locale.ENGLISH).format(date)
            }
        }
    }

    fun upCaseFirstChar(str: String?):String?{
        if (BlankUtils.isBlankString(str)){
            return null
        }

        return str!!.substring(0, 1).toUpperCase() + str.substring(1)
    }
}