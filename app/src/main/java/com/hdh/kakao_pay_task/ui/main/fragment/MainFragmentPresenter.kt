package com.hdh.kakao_pay_task.ui.main.fragment

import com.hdh.kakao_pay_task.data.model.SearchCulture
import com.hdh.kakao_pay_task.utils.ApiCallback
import com.hdh.kakao_pay_task.ui.base.BasePresenter
import com.hdh.kakao_pay_task.utils.Delegate
import io.reactivex.subjects.PublishSubject


class MainFragmentPresenter() : BasePresenter<MainFragmentView>() {

    private var currentKeyword : String = ""
    private var currentPage : Int = 0
    private var isEnd : Boolean = false

    constructor(view: MainFragmentView) : this() {
        onAttach(view)
    }

    fun loadItems(keyword: String) {
        if (keyword.isEmpty()) {
            mView?.showToast("검색어를 입력하세요.")
        }
        currentKeyword = keyword
        currentPage = 1

        addSubscription(apiStores?.tourRequest(keyword = currentKeyword),
            object : ApiCallback<SearchCulture>() {
                override fun onSuccess(model: SearchCulture) {
                    mView?.setList(model)
                    mView?.hideKeyboard()
                    currentPage++
                    isEnd = false
                }

                override fun onFailure(msg: String?) {
                    mView?.showToast(msg)
                }
            })
    }

    fun loadMoreItems() {
        if (isEnd)
            return

        mView?.showListLoading()
        addSubscription(apiStores?.tourRequest(pageNo = currentPage, keyword = currentKeyword),
            object : ApiCallback<SearchCulture>() {
                override fun onSuccess(model: SearchCulture) {
                    mView?.addList(model)
                    mView?.hideKeyboard()
                    currentPage++
                    mView?.hideListLoading()
                }

                override fun onFailure(msg: String?) {
                    mView?.showToast("마지막 페이지 입니다.")
                    isEnd = true
                    mView?.hideListLoading()
                }
            })
    }
}