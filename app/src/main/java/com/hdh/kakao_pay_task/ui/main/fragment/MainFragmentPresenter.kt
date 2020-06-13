package com.hdh.kakao_pay_task.ui.main.fragment

import com.hdh.kakao_pay_task.data.model.SearchCulture
import com.hdh.kakao_pay_task.utils.ApiCallback
import com.hdh.kakao_pay_task.ui.base.BasePresenter
import io.reactivex.subjects.PublishSubject


class MainFragmentPresenter(): BasePresenter<MainFragmentView>(){

    constructor(view : MainFragmentView) : this(){
        onAttach(view)
    }

    fun loadItems(query : String){
        if (query.isEmpty()){
            mView?.showToast("검색어를 입력하세요.")
        }

        addSubscription(apiStores?.tourRequest(title=query),
            object : ApiCallback<SearchCulture>() {
                override fun onSuccess(model: SearchCulture) {
                    mView?.setList(model)
                }

                override fun onFailure(msg: String?) {

                }
            })
    }
}