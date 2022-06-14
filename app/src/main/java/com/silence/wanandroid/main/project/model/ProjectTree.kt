package com.silence.wanandroid.main.project.model

import com.silence.wanandroid.net.Empty

@Empty
data class ChildrenBean(
    var courseId: Int,
    var id: Int,
    var name: String,
    var order: Int,
    var parentChapterId: Int,
    var userControlSetTop: Boolean,
    var visible: Int
)