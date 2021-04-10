package com.yeqingqing.mvvmframe.demo

import com.yeqingqing.frame.base.BaseApplication
import com.yeqingqing.frame.network.RetrofitHelper
import dagger.hilt.android.HiltAndroidApp

/**
 *
 * @Author: QCoder
 * @CreateDate: 2021/4/2
 * @Description: TODO
 * @Email: 526416248@qq.com
 */
@HiltAndroidApp
class DemoApp:BaseApplication() {
    //初始化 Retrofit
    init {
        RetrofitHelper.instance.setBaseUrl("https://www.wanandroid.com/").init()
    }
}