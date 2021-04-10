package com.yeqingqing.frame.base.repository

/**
 *
 * @Author: QCoder
 * @CreateDate: 2021/3/26
 * @Description: 参考 MVVM-Architecture
 * @Email: 526416248@qq.com
 */
open class BaseRepositoryBoth< L : ILocalDataSource,R : IRemoteDataSource>(
    val localDataSource: L,
    val remoteDataSource: R

) : IRepository

open class BaseRepositoryLocal<L : ILocalDataSource>(
    val localDataSource: L
) : IRepository

open class BaseRepositoryRemote<R : IRemoteDataSource>(
    val remoteDataSource: R
) : IRepository

open class BaseRepositoryNothing() : IRepository