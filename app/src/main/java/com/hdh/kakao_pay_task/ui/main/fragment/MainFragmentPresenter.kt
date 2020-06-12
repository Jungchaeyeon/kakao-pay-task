package com.hdh.kakao_pay_task.ui.main.fragment

import com.hdh.kakao_pay_task.utils.ApiCallback
import com.hdh.kakao_pay_task.data.model.SearchVclip
import com.hdh.kakao_pay_task.ui.base.BasePresenter
import io.reactivex.subjects.PublishSubject


class MainFragmentPresenter(): BasePresenter<MainFragmentView>(){

    val doSearch = PublishSubject.create<MainFragmentPresenter.SearchType>()

    enum class SearchType {
        VCLIP,
        IMAGE,
        BLOG,
        BOOK,
        CAFE
    }

    constructor(view : MainFragmentView) : this(){
        onAttach(view)
        doSearch.onNext(SearchType.VCLIP)
    }

    fun loadItems(query : String){
        if (query.isEmpty()){
            mView?.showToast("검색어를 입력하세요.")
        }

        addSubscription(apiStores?.vclipRequest(query=query, page=1),
            object : ApiCallback<SearchVclip>() {
                override fun onSuccess(model: SearchVclip) {
                    mView?.setList(model)
                }

                override fun onFailure(msg: String?) {

                }
            })
    }
}