package com.lipeng.mygithub.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

import java.util.LinkedList;
import java.util.List;

/**
 * activity管理类，用于添加和管理activity
 * @author lipeng
 * @date 2018/3/16
 */

public final class ActivitiesManager {
    /**存储activity的容器*/
    private List<Activity> mActivityList = new LinkedList<>();
    private static volatile ActivitiesManager instance = null;

    private ActivitiesManager(){

    }

    public static ActivitiesManager getInstance(){
        if (null == instance){
            synchronized (ActivitiesManager.class){
                if (null == instance){
                    instance = new ActivitiesManager();
                }
            }
        }

        return instance;
    }

    /**
     * 添加activity到堆栈
     * @param activity target
     * */
    public void addActivity(Activity activity){
        mActivityList.add(activity);
    }

    /**
     * 结束指定activity
     * @param activity target
     * */
    public void finishActivity(Activity activity){
        if (null != activity){
            mActivityList.remove(activity);
            activity.finish();
        }
    }

    /**
     * 结束全部activity
     * */
    public void finishAllActivities(){
        while(mActivityList.size() > 0){
            Activity activity = mActivityList.get(mActivityList.size() - 1);
            mActivityList.remove(mActivityList.size() - 1);
            activity.finish();
        }
    }

    /**
     * 关闭应用程序
     * @param context target
     * */
    public void appExit(Context context){
        try{
            finishAllActivities();
            if (null != context){
                ActivityManager manager = (ActivityManager) context
                        .getSystemService(Context.ACTIVITY_SERVICE);
                if (null != manager){
                    manager.killBackgroundProcesses(context.getPackageName());
                }
                System.exit(0);
            }
        }catch (Exception e){
        }
    }
}
