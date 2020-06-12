package com.hdh.kakao_pay_task.ui.base.mvp

import android.os.Bundle
import android.view.View
import com.hdh.kakao_pay_task.ui.base.BaseFragment
import com.hdh.kakao_pay_task.ui.base.BasePresenter

abstract class MvpFragment<P : BasePresenter<*>> : BaseFragment() {

    protected var mPresenter: P? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mPresenter = createPresenter()
    }

    protected abstract fun createPresenter(): P

    override fun onDestroyView() {
        super.onDestroyView()
        mPresenter?.onDetach()
    }
}
