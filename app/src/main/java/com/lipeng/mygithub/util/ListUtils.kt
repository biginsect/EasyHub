package com.lipeng.mygithub.util

/**
 * @author big insect
 */
object ListUtils {
    fun <T> getSize(list: List<T>?):Int{
        return list?.size ?: 0
    }

    fun <T> isEmpty(list: List<T>?):Boolean{
        return list == null || list.isEmpty()
    }
}