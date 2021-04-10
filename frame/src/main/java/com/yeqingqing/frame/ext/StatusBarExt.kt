package com.yeqingqing.frame.ext

import android.app.Activity
import android.content.Context
import android.os.Build
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.annotation.ColorInt

/**
 *
 * @Author: QCoder
 * @CreateDate: 2021/4/1
 * @Description: Status 状态栏的扩展方法
 * @param
 * @Email: 526416248@qq.com
 */
private const val COLOR_TRANSPARENT = 0

/**
 * 沉浸式状态栏
 * 包括隐藏，设置字体颜色，设置状态栏颜色等功能
 * @param txtBlack 字体是否设置为黑色 。默认为黑色
 * @param titleBar 标题栏，是否有标题栏；如果有，需要模拟有状态栏的情况。默认为没有标题栏
 * @param color 状态栏颜色，默认为透明
 */
fun Activity.immersive(
    txtBlack: Boolean = true,
    titleBar: View? = null,
    @ColorInt color: Int = COLOR_TRANSPARENT
) {
    //如果设置了颜色，就不需要模拟有状态栏的情况
    var flag = true
    when {
        Build.VERSION.SDK_INT > 21 -> {
            when (color) {
                COLOR_TRANSPARENT -> {
                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                    var systemUiVisibility = window.decorView.systemUiVisibility
                    systemUiVisibility = systemUiVisibility or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    systemUiVisibility = systemUiVisibility or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    window.decorView.systemUiVisibility = systemUiVisibility
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                    window.statusBarColor = color
                }
                else -> {
                    flag = false
                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                    var systemUiVisibility = window.decorView.systemUiVisibility
                    systemUiVisibility =
                        systemUiVisibility and View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    systemUiVisibility = systemUiVisibility and View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    window.decorView.systemUiVisibility = systemUiVisibility
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                    window.statusBarColor = color
                }
            }
        }
//        //Build.VERSION.SDK_INT in 19..29
//        Build.VERSION.SDK_INT > 19 -> {
//            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
//            if (color != COLOR_TRANSPARENT) {
//                setTranslucentView(window.decorView as ViewGroup, color)
//            }
//        }
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.R -> {
            if (color != COLOR_TRANSPARENT) {
                window.statusBarColor = getColor(android.R.color.transparent)
            }
            window.setDecorFitsSystemWindows(false)
        }
    }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        var systemUiVisibility = window.decorView.systemUiVisibility
        systemUiVisibility = if (txtBlack) {
            systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        } else {
            systemUiVisibility and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
        }
        Log.i(
            "StatusBar",
            "Activity.darkMode @darkMode=$txtBlack | systemUiVisibility:$systemUiVisibility"
        )
        window.decorView.systemUiVisibility = systemUiVisibility
    }

    if (titleBar != null &&flag) {
        val params: ViewGroup.LayoutParams = titleBar.layoutParams
        val topMargin: Int = getStatusBarH(titleBar.context)
        params.height = params.height + topMargin
        titleBar.setPadding(
            titleBar.paddingLeft,
            titleBar.paddingTop + topMargin,
            titleBar.paddingRight,
            titleBar.paddingBottom
        )
        titleBar.layoutParams = params
    }

}

private fun getStatusBarH(context: Context): Int {
    val resourceId =
        context.resources.getIdentifier("status_bar_height", "dimen", "android")
    return context.resources.getDimensionPixelSize(resourceId)
}
