package com.lipeng.mygithub.util;

import java.util.List;

/**
 * List工具类
 * @author lipeng
 * @date 2018/3/16
 */

public final class ListUtils {

    private ListUtils(){

    }

    public static <T> int getSize(List<T> list){
        return null == list ? 0 : list.size();
    }

    public static <T> boolean isEmpty(List<T> list){
        return null == list || 0 == list.size() ;
    }
}
