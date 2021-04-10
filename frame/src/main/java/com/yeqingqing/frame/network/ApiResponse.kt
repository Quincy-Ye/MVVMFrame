package com.yeqingqing.frame.network

import java.lang.Exception

/**
 *
 * @Author: QCoder
 * @CreateDate: 2021/3/29
 * @Description: Api返回的通用数据模型
 * @param T 具体数据模型
 * @Email: 526416248@qq.com
 */
data class ApiResponse<T> (
    var errorCode:Int =0,
    var errorMsg:String = "",
    var data:T
)
fun <T> ApiResponse<T>.dataConvert():T{
    if (errorCode == 0){
        return data
    }else{
        throw Exception()
    }
}