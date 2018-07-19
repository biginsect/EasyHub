package com.lipeng.mygithub.util

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import java.util.*

/**
 * @author big insect
 */
object ActivitiesManager {
    private var mActivityList:LinkedList<Activity> = LinkedList()

    fun addActivity(activity: Activity){
        mActivityList.add(activity)
    }

    fun finishActivity(activity: Activity){
        mActivityList.remove(activity)
        activity.finish()
    }

    private fun finishAll(){
        while (mActivityList.size > 0){
            val activity :Activity = mActivityList.get(mActivityList.size - 1)
            mActivityList.removeAt(mActivityList.size - 1)
            activity.finish()
        }
    }

    fun appExit(context: Context){
        try {
            finishAll()
            val manager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            manager.killBackgroundProcesses(context.packageName)
            System.exit(0)
        }catch (e:Exception){

        }
    }
}