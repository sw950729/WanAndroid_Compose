package com.silence.wanandroid.main.project.model

import com.silence.wanandroid.net.Empty

/**
 * @date:2021/3/27
 * @author:Silence
 * @describe:
 **/
@Empty
data class ProjectBean(
    var curPage: Int,
    var offset: Int,
    var over: Boolean,
    var pageCount: Int,
    var size: Int,
    var total: Int,
    var datas: MutableList<ProjectListItem>
)

@Empty
data class ProjectListItem(
    var apkLink: String,
    var audit: Int,
    var author: String,
    var canEdit: Boolean,
    var chapterId: Int,
    var chapterName: String,
    var collect: Boolean,
    var courseId: Int,
    var desc: String,
    var descMd: String,
    var envelopePic: String,
    var fresh: Boolean,
    var id: Int,
    var link: String,
    var niceDate: String,
    var niceShareDate: String,
    var origin: String,
    var prefix: String,
    var projectLink: String,
    var publishTime: Long,
    var selfVisible: Int,
    var shareDate: Long,
    var shareUser: String,
    var superChapterId: Int,
    var superChapterName: String,
    var tags: MutableList<TagBean>,
    var title: String,
    var type: Int,
    var userId: Int,
    var visible: Int,
    var zan: Int
)

@Empty
data class TagBean(
    var name: String,
    var url: String,
)