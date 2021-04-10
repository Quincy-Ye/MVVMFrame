package com.yeqingqing.mvvmframe.demo

import android.util.Log
import com.yeqingqing.frame.base.repository.BaseRepositoryBoth
import com.yeqingqing.frame.base.repository.ILocalDataSource
import com.yeqingqing.frame.base.repository.IRemoteDataSource
import com.yeqingqing.frame.network.ApiResponse
import com.yeqingqing.frame.network.RetrofitHelper
import com.yeqingqing.frame.utils.ContextProvider
import com.yeqingqing.mvvmframe.demo.MainRepository.Companion.mainDao
import com.yeqingqing.mvvmframe.demo.bd.MainDataBean
import com.yeqingqing.mvvmframe.demo.bd.MainDatabase
import javax.inject.Inject

/**
 *
 * @Author: QCoder
 * @CreateDate: 2021/3/26
 * @Description: 仓库汇总，主要同时负责本地和远程的数据操作
 * @Email: 526416248@qq.com
 */
class MainRepository @Inject constructor(
    localDataSource: MainLocalDataSource,
    remoteDataSource: MainRemoteDataSource
) : BaseRepositoryBoth<MainLocalDataSource, MainRemoteDataSource>(
    localDataSource,
    remoteDataSource
) {
    companion object{
        const val TAG ="MainRepository"
        val mainDao = MainDatabase.getDatabase(ContextProvider.context).mainDao()
    }
    suspend fun getLastData(): ApiResponse<List<MainDataBean>> {
       val result = remoteDataSource.getData4Remote()
        localDataSource.saveData2Local(result.data)
        return result
    }
}

/**
 * 本地数据管理
 */
class MainLocalDataSource @Inject constructor() : ILocalDataSource {
    companion object{
        const val TAG ="MainLocalDataSource"
    }
   suspend fun saveData2Local(results:List<MainDataBean>){
       for (mainDataBean in results) {
           val result=mainDao.insert(mainDataBean)
           Log.i(TAG, "saveData2Local: $result")
       }
    }
}
/**
 * 远程数据管理
 */
class MainRemoteDataSource @Inject constructor() : IRemoteDataSource {
    companion object{
        const val TAG ="MainRemoteDataSource"
    }
    suspend fun getData4Remote():ApiResponse<List<MainDataBean>>{
        return RetrofitHelper.instance.getService(ApiService::class.java).getTopArticle()
    }
}