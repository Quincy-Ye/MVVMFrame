package com.yeqingqing.mvvmframe.demo.bd

import androidx.room.*


@Entity(tableName = "main_data")
@TypeConverters(TagsConverter::class)
data class MainDataBean(
    @PrimaryKey(autoGenerate = true) var id: Int,
    @ColumnInfo(name = "apkLink") var apkLink: String,
    @ColumnInfo(name = "audit") var audit: Int,
    @ColumnInfo(name = "author") var author: String,
    @ColumnInfo(name = "canEdit") var canEdit: Boolean,
    @ColumnInfo(name = "chapterId") var chapterId: Int,
    @ColumnInfo(name = "chapterName") var chapterName: String,
    @ColumnInfo(name = "collect") var collect: Boolean,
    @ColumnInfo(name = "courseId") var courseId: Int,
    @ColumnInfo(name = "desc") var desc: String,
    @ColumnInfo(name = "descMd") var descMd: String,
    @ColumnInfo(name = "envelopePic") var envelopePic: String,
    @ColumnInfo(name = "fresh") var fresh: Boolean,
    @ColumnInfo(name = "link") var link: String,
    @ColumnInfo(name = "niceDate") var niceDate: String,
    @ColumnInfo(name = "niceShareDate") var niceShareDate: String,
    @ColumnInfo(name = "origin") var origin: String,
    @ColumnInfo(name = "prefix") var prefix: String,
    @ColumnInfo(name = "projectLink") var projectLink: String,
    @ColumnInfo(name = "publishTime") var publishTime: Long,
    @ColumnInfo(name = "selfVisible") var selfVisible: Int,
    @ColumnInfo(name = "shareDate") var shareDate: Long,
    @ColumnInfo(name = "shareUser") var shareUser: String,
    @ColumnInfo(name = "superChapterId") var superChapterId: Int,
    @ColumnInfo(name = "superChapterName") var superChapterName: String,
    @ColumnInfo(name = "tags") var tags: List<TagsBean>,
    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "type") var type: Int,
    @ColumnInfo(name = "userId") var userId: Int,
    @ColumnInfo(name = "visible") var visible: Int,
    @ColumnInfo(name = "zan") var zan: Int
)
data class TagsBean(val name:String,val url:String)
