package com.yeqingqing.frame.utils

import android.content.Context

/**
 *
 * @Author: QCoder
 * @CreateDate: 2021/3/30
 * @Description: TODO
 * @Email: 526416248@qq.com
 */
object ContextProvider {
    lateinit var context: Context
    fun initial(context: Context) {
        ContextProvider.context = context
    }
}