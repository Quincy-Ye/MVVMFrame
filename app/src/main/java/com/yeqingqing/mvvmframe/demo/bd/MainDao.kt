package com.yeqingqing.mvvmframe.demo.bd

import androidx.room.*


@Dao
interface MainDao {

    //Insert 可以返回插入数据的id值
    //这里冲突策略采用忽视，根据需求也可换成 REPLACE
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(MainDataBean: MainDataBean):Long

    @Delete
    suspend fun delete(MainDataBean: MainDataBean): Int

    @Update
    suspend fun update(MainDataBean: MainDataBean): Int

    @Query("select * from main_data order by id asc")
    suspend fun queryAllRecord(): List<MainDataBean>

}