package com.hdh.kakao_pay_task.ui.main.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hdh.kakao_pay_task.R
import com.hdh.kakao_pay_task.data.model.*
import com.hdh.kakao_pay_task.ui.base.mvp.MvpFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : MvpFragment<MainFragmentPresenter>(), MainFragmentView {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = super.setContentView(inflater, R.layout.fragment_main)

    private val searchListAdapter by lazy {
        SearchListAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setStatusBarResID(R.color.colorAccent)
        recycler_search_result.adapter = searchListAdapter

        mPresenter?.let { mPresenter ->
            addSubscription(
                mPresenter.doSearch
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        //recycler_search_result.adapter = searchListAdapter
                    }
            )
        }
        setOnClickListener()
    }


    private fun setOnClickListener() {
        button_search.setOnClickListener {
            mPresenter?.loadItems(edit_search.text.toString())
        }
    }

    override fun createPresenter(): MainFragmentPresenter = MainFragmentPresenter(this)

    override fun setList(model: SearchCulture) {
        searchListAdapter.items = ArrayList()
        searchListAdapter.notifyDataSetChanged()
    }
}
