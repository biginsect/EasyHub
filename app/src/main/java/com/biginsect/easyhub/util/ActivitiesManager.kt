package com.biginsect.easyhub.util

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import java.lang.ref.SoftReference
import java.util.*

/**
 * @author big insect
 */
object ActivitiesManager {
    private val mActivityList: LinkedList<SoftReference<Activity>> = LinkedList()

    fun addActivity(softActivity: SoftReference<Activity>) {
        mActivityList.add(softActivity)
    }

    fun finishActivity(softActivity: SoftReference<Activity>?) {
        mActivityList.remove(softActivity)
    }

    private fun finishAll() {
        var i = mActivityList.size - 1
        while (i >= 0) {
            val softActivity = mActivityList[i]
            softActivity.get()?.finish()
            i--
        }

        mActivityList.clear()
    }

    fun appExit(context: Context) {
        try {
            finishAll()
            val manager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            manager.killBackgroundProcesses(context.packageName)
            System.exit(0)
        } catch (e: Exception) {

        }
    }
}