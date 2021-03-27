package com.silence.wanandroid.main.project.viewmodel

import androidx.lifecycle.MutableLiveData
import com.silence.wanandroid.base.BaseViewModel
import com.silence.wanandroid.main.project.model.ProjectBean
import com.silence.wanandroid.net.RxNetWork
import kotlinx.coroutines.launch

/**
 * @date:2021/3/27
 * @author:Silence
 * @describe:
 **/
class ProjectViewModel : BaseViewModel() {

    var projectData = MutableLiveData<ProjectBean>()

    fun getProjectArticleList(page: Int) {
        mCoroutine.launch {
            with(RxNetWork.getObserverHttps().getProjectArticle(page)) {
                if (page == 0) {
                    projectData.postValue(null)
                    projectData.postValue(data)
                } else {
                    projectData.postValue(data)
                }
            }
        }
    }
}