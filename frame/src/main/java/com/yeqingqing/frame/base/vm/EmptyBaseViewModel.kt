package com.yeqingqing.frame.base.vm


/**
 *
 * @Author: QCoder
 * @CreateDate: 2021/3/25
 * @Description: 为不需要 ViewModel 的 View 提供一个空的 ViewModel
 * @Email: 526416248@qq.com
 */
class EmptyBaseViewModel:
    BaseViewModel() {
    override fun onLifeStart() {

    }

    override fun onLifeResume() {

    }

    override fun onLifePause() {

    }

    override fun onLifeStop() {

    }

    override fun onLifeDestroy() {

    }

}