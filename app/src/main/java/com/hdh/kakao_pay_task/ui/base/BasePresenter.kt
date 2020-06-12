package com.hdh.kakao_pay_task.ui.base

import android.annotation.SuppressLint
import com.hdh.kakao_pay_task.utils.ApiClient
import com.hdh.kakao_pay_task.data.api.ApiStores
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

open class BasePresenter<V> {

    protected var mView: V? = null
    protected var apiStores: ApiStores? = null

    val compositeDisposable: CompositeDisposable by lazy {
        CompositeDisposable()
    }

    open fun onAttach(view: V) {
        mView = view
        apiStores = ApiClient().retrofit()?.create(ApiStores::class.java)
    }

    open fun onDetach() {
        onUnSubscribe()
    }

    open fun onUnSubscribe() {
        compositeDisposable.dispose()
    }

    @SuppressLint("CheckResult")
    open fun addSubscription(observable: Observable<*>?, observer: DisposableObserver<*>) {
        compositeDisposable.add(observer)

        observable
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribeWith(observer as DisposableObserver<Any>)
    }

    open fun addSubscription(observer: Disposable) {
        compositeDisposable.add(observer)
    }
}