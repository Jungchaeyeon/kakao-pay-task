package com.hdh.kakao_pay_task.ui.base.mvp

import android.os.Bundle
import com.hdh.kakao_pay_task.ui.base.BaseActivity
import com.hdh.kakao_pay_task.ui.base.BasePresenter

abstract class MvpActivity<P : BasePresenter<*>> : BaseActivity() {

    protected var mvpPresenter: P? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        mvpPresenter = createPresenter()
        super.onCreate(savedInstanceState)
    }

    protected abstract fun createPresenter(): P

    override fun onDestroy() {
        super.onDestroy()
        mvpPresenter?.onDetach()
    }
}