package com.hdh.kakao_pay_task.ui.main.fragment

import com.hdh.kakao_pay_task.data.model.GallerySearchList
import com.hdh.kakao_pay_task.ui.base.BaseView

interface MainFragmentView : BaseView {
    fun showListLoading()
    fun hideListLoading()
    fun setList(model: ArrayList<GallerySearchList.Item>)
    fun savePrevSize()
    fun notifyList()
}