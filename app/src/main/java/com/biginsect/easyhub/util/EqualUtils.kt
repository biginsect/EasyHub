package com.biginsect.easyhub.util

import android.text.TextUtils

/**
 * 相等比较工具 防止NPE
 * @author big insect
 */
object EqualUtils {

    /**
     * 两个字符串内容是否相同
     * */
    fun twoStringIsEquals(str1 :String?, str2 :String?):Boolean{
        if(TextUtils.isEmpty(str1)){
            return TextUtils.isEmpty(str2)
        }
        return str1 == str2
    }

    /**
     * 两个list内容是否相同
     * */
    fun <T> twoListIsEquals(list1: List<T>?, list2 :List<T>?): Boolean{
        if (null == list1 || list1.isEmpty()){
            return null == list2 || list2.isEmpty()
        }
        return list1 == list2
    }
}