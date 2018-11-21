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

    fun getLocale(language: String): Locale{
        val array = language.split(Regex("-"))

        return if (array.size > 1){
            val country = array[1].replaceFirst("r", "")
            Locale(array[0], country)
        }else{
            Locale(language)
        }
    }

    fun getDateStr(date: Date?): String{
        val locale = getLocale(PreUtils.getLanguage())
        val regex = "MMM d, yyyy"
        val format = SimpleDateFormat(regex, locale)

        return format.format(date)
    }

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

        val milliseconds = 1000L
        val seconds = 60 * milliseconds
        val minutes = 60 * seconds
        val hours = 24 * minutes
        val days = 30 * hours

        return when(timeInterval){
            /**until 取半开区间（包含前者不包含后者）*/
            in 0 until milliseconds -> context.getString(R.string.just_now)
            in milliseconds until seconds -> context.getString(R.string.seconds_ago)
            in seconds until minutes -> context.getString(R.string.minutes_ago)
            in minutes until hours -> context.getString(R.string.hours_ago)
            in hours until days -> context.getString(R.string.days_ago)

            else ->{
                SimpleDateFormat("MMM d, yyyy", Locale.ENGLISH).format(date)
            }
        }
    }

    /**
     * 首个字母大写
     * */
    fun upCaseFirstChar(str: String?):String?{
        if (BlankUtils.isBlankString(str)){
            return null
        }

        return str!!.substring(0, 1).toUpperCase() + str.substring(1)
    }
}