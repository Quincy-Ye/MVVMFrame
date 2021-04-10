package com.yeqingqing.mvvmframe.demo


import com.yeqingqing.frame.network.ApiResponse
import com.yeqingqing.mvvmframe.demo.bd.MainDataBean
import retrofit2.http.GET

/**
 *
 * @Author: QCoder
 * @CreateDate: 2021/3/29
 * @Description: TODO
 * @Email: 526416248@qq.com
 */
interface ApiService {
    //使用协程进行网络请求
    @GET("article/top/json/")
    suspend fun getTopArticle(): ApiResponse<List<MainDataBean>>
}