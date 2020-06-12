package com.hdh.kakao_pay_task.utils

import com.facebook.stetho.common.LogUtil
import com.hdh.kakao_pay_task.ui.base.BaseActivity
import io.reactivex.observers.DisposableObserver
import retrofit2.HttpException

abstract class ApiCallback<M>() :
    DisposableObserver<M>() {

    private var activity: BaseActivity? = null
    private var isShowLoading: Boolean = false

    constructor(activity: BaseActivity?, isShowLoading: Boolean) : this() {
        this.activity = activity
        this.isShowLoading = isShowLoading
    }

    abstract fun onSuccess(model: M)
    abstract fun onFailure(msg: String?)

    override fun onStart() {
        super.onStart()
        if (isShowLoading) {
            activity?.loadingState?.onNext(true)
        }
    }

    override fun onError(e: Throwable) {
        e.printStackTrace()
        if (e is HttpException) {
            //httpException.response().errorBody().string()
            val code = e.code()
            var msg = e.message
            LogUtil.d("code=$code")
            if (code == 502 || code == 404 || code == 504) {
                msg = "통신 상태가 원활하지 않습니다 잠시 후 다시 시도해 주세요."
            }
            onFailure(msg)
        } else {
            onFailure(e.message)
        }
    }

    override fun onNext(model: M) {
        onSuccess(model)
    }

    override fun onComplete() {
        activity?.loadingState?.onNext(false)
    }
}