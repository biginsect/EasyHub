package com.lipeng.mygithub.util

/**
 * 检查是否为null 或者 ""
 * @author big insect
 * @date 2018/8/9.
 */
object BlankUtils {

    fun isBlankString(str: String?): Boolean{
        return null == str || str.trim() == ""
    }
}