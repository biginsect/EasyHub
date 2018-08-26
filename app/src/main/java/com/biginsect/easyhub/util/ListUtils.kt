package com.biginsect.easyhub.util

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

    /**
     * separator is ","
     * */
    fun listToString(list: List<String>): String{
        val stringBuilder = StringBuilder("")
        if (getSize(list) == 0){
            return stringBuilder.toString()
        }
        val listSize = getSize(list)
        var i = 0
        while (i < listSize){
            stringBuilder.append(list[i])
            if (i != listSize - 1){
                stringBuilder.append(",")
            }

            i++
        }

        return stringBuilder.toString()
    }
}