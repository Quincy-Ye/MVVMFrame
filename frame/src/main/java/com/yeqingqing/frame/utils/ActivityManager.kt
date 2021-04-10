package com.yeqingqing.frame.utils

import android.app.Activity
import java.util.*
import kotlin.system.exitProcess

/**
 *
 * @Author: QCoder
 * @CreateDate: 2021/3/25
 * @Description: 负责Activity的管理，添加，删除，退出App等
 * @Email: 526416248@qq.com
 */
class ActivityManager private constructor() {
    //双重检查单例模式
    companion object {
        val instance: ActivityManager by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            ActivityManager()
        }
    }

    private val activities = Stack<Activity>()

    fun addActivity(activity: Activity) {
        activities.add(activity)
    }

    fun getTopActivity() = activities.lastElement()

    fun finishActivity(cls: Class<Any>) {
        val iterator = activities.iterator()
        while (iterator.hasNext()) {
            val activity = iterator.next()
            if (activity.javaClass == cls) {
                iterator.remove()
                activity.finish()
            }
        }

    }
    fun finishActivity(activity: Activity){
        activities.remove(activity)
        activity.finish()
    }


    fun finishAllActivities() {
        var i = 0
        val size = activities.size
        while (i < size) {
            if (null != activities[i]) {
                activities[i].finish()
            }
            i++
        }

        activities.clear()
    }

    fun exitApp() {
        finishAllActivities()
        exitProcess(0)
    }
}