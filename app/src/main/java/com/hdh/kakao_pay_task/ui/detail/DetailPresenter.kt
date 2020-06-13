package com.hdh.kakao_pay_task.ui.detail

import com.hdh.kakao_pay_task.ui.base.BasePresenter


class DetailPresenter() : BasePresenter<DetailView>() {

    constructor(view: DetailView) : this() {
        onAttach(view)
    }

}