package com.yeqingqing.frame.base.vm

import java.io.Serializable

/**
 *
 * @Author: QCoder
 * @CreateDate: 2021/3/25
 * @Description: 基类 ViewModel
 * @Email: 526416248@qq.com
 */
abstract class BaseViewModel: ScopeViewModel(), Serializable {

    interface LoadingEvent:Serializable {
        fun showLoading(message: String = "")
        fun showMessage(message: String= "", delay: Int = 2)
        fun dismissLoading(delay: Int = 0)
    }

    private  var loadingEvent: LoadingEvent? = null

    open fun onLifeStart() {

    }

    open fun onLifeCreate() {

    }

    open fun onLifeResume() {

    }

    open fun onLifePause() {

    }

    open fun onLifeStop() {

    }

    open fun onLifeDestroy() {

    }

    fun bindLoadingEvent(loadingEvent: LoadingEvent) {
        this.loadingEvent = loadingEvent
    }

    protected fun showLoading(message: String = "") {
        loadingEvent?.showLoading(message)
    }

    protected fun showMessage(message: String,delay: Int=2) {
        loadingEvent?.showMessage(message,delay)
    }

    fun dismissLoading(delay: Int = 0) {
        loadingEvent?.dismissLoading(delay)
    }


}