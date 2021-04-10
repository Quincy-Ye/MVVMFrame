package com.yeqingqing.frame.network

import android.util.Log
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 *
 * @Author: QCoder
 * @CreateDate: 2021/3/29
 * @Description: 封装 Retrofit
 * @Email: 526416248@qq.com
 */
class RetrofitHelper {
    //获取 Retrofit 单例
    companion object {
        private const val CONNECT_TIMEOUT = 5L
        private const val READ_TIMEOUT = 5L
        val instance: RetrofitHelper by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            RetrofitHelper()
        }

    }

    private val retrofitBuilder: Retrofit.Builder
    private lateinit var retrofit: Retrofit

    init {
        val gson = Gson().newBuilder()
            .setLenient()
            .serializeNulls()
            .create()
        retrofitBuilder = Retrofit.Builder()
        //默认配置
        retrofitBuilder.addConverterFactory(GsonConverterFactory.create(gson))
            .client(initOkHttpClient())
    }

    fun setBaseUrl(baseUrl: String): RetrofitHelper {
        retrofitBuilder.baseUrl(baseUrl)
        return this
    }

    fun addConverterFactory(factory: Converter.Factory): RetrofitHelper {
        retrofitBuilder.addConverterFactory(factory)
        return this
    }

    fun setClient(client: OkHttpClient): RetrofitHelper {
        retrofitBuilder.client(client)
        return this
    }

    fun init():Retrofit {
        retrofit = retrofitBuilder.build()
        return retrofit
    }

    /**
     * 客户端初始化
     */
    private fun initOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(initLogInterceptor())
            .build()
    }

    /**
     * 日志拦截器
     */
    private fun initLogInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                Log.i("Retrofit", message)
            }
        })

        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    /**
     * 服务实例化
     * @param service
     */
    fun <T> getService(service: Class<T>): T {
        return retrofit.create(service)
    }
}