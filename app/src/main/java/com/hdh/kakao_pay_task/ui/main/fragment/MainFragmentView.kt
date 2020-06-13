package com.hdh.kakao_pay_task.ui.main.fragment

import com.hdh.kakao_pay_task.data.model.SearchCulture
import com.hdh.kakao_pay_task.ui.base.BaseView

interface MainFragmentView : BaseView {
    fun showListLoading()
    fun hideListLoading()
    fun setList(model: SearchCulture)
    fun addList(model: SearchCulture)
}