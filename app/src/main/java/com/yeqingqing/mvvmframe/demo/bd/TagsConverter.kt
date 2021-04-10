package com.yeqingqing.mvvmframe.demo.bd

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


/**
 *
 * @Author: QCoder
 * @CreateDate: 2021/4/9
 * @Description: 转换类型
 * @Email: 526416248@qq.com
 */

class TagsConverter {
    @TypeConverter
    fun revert(value: String): List<TagsBean> {
        val type = object : TypeToken<TagsBean>(){

        }.type
        return Gson().fromJson(value,type)
    }

    @TypeConverter
    fun converter(tags: List<TagsBean>): String {
        // 使用Gson方法把List转成json格式的string，便于我们用的解析
        return Gson().toJson(tags)
    }
}
