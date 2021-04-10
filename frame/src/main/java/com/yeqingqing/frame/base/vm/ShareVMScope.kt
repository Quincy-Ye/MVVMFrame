package com.yeqingqing.frame.base.vm

/**
 *
 * @Author: QCoder
 * @CreateDate: 2021/3/25
 * @Description: 用于标记 VM 的作用域,实现 ViewModel 的共享
 * @Email: 526416248@qq.com
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD, AnnotationTarget.FUNCTION)
annotation class ShareVMScope(val scopeName:String) {
}