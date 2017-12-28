package com.lipeng.mygithub.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 网络状态检查工具
 * @author lipeng
 * @date 2017/12/25
 */

public class NetworkUtils {
    private NetworkUtils(){
    }

    /**
     * 判断是否有网络连接
     * @param context 上下文环境
     * */
    public static boolean isNetworkConnected(Context context){
        if (context != null){
            //获取手机网络连接管理对象，包含wifi和数据连接
            ConnectivityManager manager = (ConnectivityManager) context.
                    getSystemService(Context.CONNECTIVITY_SERVICE);
            if (manager != null) {
                //获取network信息
                NetworkInfo networkInfo = manager.getActiveNetworkInfo();
                if (networkInfo != null) {
                    return networkInfo.isAvailable();
                }
            }
        }
        return false;
    }
}
