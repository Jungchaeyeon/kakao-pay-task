package com.hdh.kakao_pay_task.ui.main.fragment

import com.hdh.kakao_pay_task.data.model.GallerySearchList
import com.hdh.kakao_pay_task.utils.ApiCallback
import com.hdh.kakao_pay_task.ui.base.BasePresenter


class MainFragmentPresenter() : BasePresenter<MainFragmentView>() {

    private var currentKeyword: String = ""
    private var currentPage: Int = 0
    private var isEnd: Boolean = false
    private var gallerySearchList: ArrayList<GallerySearchList.Item> = ArrayList()

    constructor(view: MainFragmentView) : this() {
        onAttach(view)
    }

    fun loadItems(keyword: String) {
        currentKeyword = if (keyword.isEmpty()) {
            " "
        } else {
            keyword
        }

        addSubscription(apiStores?.tourRequest(keyword = currentKeyword),
            object : ApiCallback<GallerySearchList>() {
                override fun onSuccess(model: GallerySearchList) {
                    gallerySearchList = model.response.body.items.itemList
                    mView?.setList(gallerySearchList)
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
            object : ApiCallback<GallerySearchList>() {
                override fun onSuccess(model: GallerySearchList) {
                    mView?.savePrevSize()
                    gallerySearchList.addAll(model.response.body.items.itemList)
                    mView?.notifyList()
                    mView?.hideKeyboard()
                    currentPage++
                    mView?.hideListLoading()
                }

                override fun onFailure(msg: String?) {
                    isEnd = true
                    mView?.hideListLoading()
                }
            })
    }
}