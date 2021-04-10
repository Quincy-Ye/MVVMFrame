package com.yeqingqing.frame.base

import android.app.Application
import com.yeqingqing.frame.utils.ContextProvider

/**
 *
 * @Author: QCoder
 * @CreateDate: 2021/3/26
 * @Description: 基类 Application
 * @Email: 526416248@qq.com
 */

open class BaseApplication:Application() {
    companion object {
        lateinit var INSTANCE: BaseApplication
    }
    override fun onCreate() {
        super.onCreate()
        ContextProvider.initial(this)
        INSTANCE = this
    }

}