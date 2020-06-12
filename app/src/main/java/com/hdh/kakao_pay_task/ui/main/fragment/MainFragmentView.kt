package com.hdh.kakao_pay_task.ui.main.fragment

import com.hdh.kakao_pay_task.data.model.SearchVclip
import com.hdh.kakao_pay_task.ui.base.BaseView

interface MainFragmentView : BaseView {
    fun setList(model: SearchVclip)
}