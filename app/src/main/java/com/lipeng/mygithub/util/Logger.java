package com.lipeng.mygithub.util;

import android.util.Log;

import com.lipeng.mygithub.BuildConfig;

/**
 * 开发调试日志类
 * @author lipeng
 * @date 2018/1/17
 */

public final class Logger {
    private static final short VERBOSE = Log.VERBOSE;
    private static final short DEBUG = Log.DEBUG;
    private static final short INFO = Log.INFO;
    private static final short WARN = Log.WARN;
    private static final short ERROR = Log.ERROR;

    /**最低打印等级*/
    public static short LOG_LEVEL = VERBOSE;
    private Logger(){
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static void v(String tag, String msg){
        if (BuildConfig.DEBUG && LOG_LEVEL <= VERBOSE){
            Log.v(tag, msg);
        }
    }

    public static void v(String tag, String msg, Throwable t){
        if (BuildConfig.DEBUG && LOG_LEVEL <= VERBOSE){
            Log.v(tag, msg, t);
        }
    }

    public static void d(String tag, String msg){
        if (BuildConfig.DEBUG && LOG_LEVEL <= DEBUG){
            Log.d(tag, msg);
        }
    }

    public static void d(String tag, String msg, Throwable t){
        if (BuildConfig.DEBUG && LOG_LEVEL <= DEBUG){
            Log.d(tag, msg, t);
        }
    }

    public static void i(String tag, String msg){
        if (BuildConfig.DEBUG && LOG_LEVEL <= INFO){
            Log.i(tag, msg);
        }
    }

    public static void i(String tag, String msg, Throwable t){
        if (BuildConfig.DEBUG && LOG_LEVEL <= INFO){
            Log.i(tag, msg, t);
        }
    }

    public static void w(String tag, String msg){
        if (BuildConfig.DEBUG && LOG_LEVEL <= WARN){
            Log.w(tag, msg);
        }
    }

    public static void w(String tag, String msg, Throwable t){
        if (BuildConfig.DEBUG && LOG_LEVEL <= WARN){
            Log.w(tag, msg, t);
        }
    }

    public static void e(String tag, String msg){
        if (BuildConfig.DEBUG && LOG_LEVEL <= ERROR){
            Log.e(tag, msg);
        }
    }

    public static void e(String tag, String msg, Throwable t){
        if (BuildConfig.DEBUG && LOG_LEVEL <= ERROR){
            Log.e(tag, msg, t );
        }
    }
}
