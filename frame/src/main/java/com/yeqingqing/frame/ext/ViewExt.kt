package com.yeqingqing.frame.ext

import android.util.Log
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomnavigation.BottomNavigationView

/**
 *
 * @Author: QCoder
 * @CreateDate: 2021/3/30
 * @Description: TODO
 * @Email: 526416248@qq.com
 */
fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}
fun BottomNavigationView.cancelLongClick() {
    val itemCount = this.childCount
    Log.i("TAG", "cancelLongClick:$itemCount ")
    val bottomNavigationMenuView: ViewGroup = (this.getChildAt(0) as ViewGroup)
    for (position in 0 until 3) {
        bottomNavigationMenuView.getChildAt(position).setOnLongClickListener { true }
    }
}