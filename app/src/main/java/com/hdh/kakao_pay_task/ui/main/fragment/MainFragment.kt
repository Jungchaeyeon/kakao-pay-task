package com.hdh.kakao_pay_task.ui.main.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.recyclerview.widget.RecyclerView
import com.hdh.kakao_pay_task.R
import com.hdh.kakao_pay_task.data.model.SearchCulture
import com.hdh.kakao_pay_task.ui.base.mvp.MvpFragment
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
        recycler_search_result.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(1)){
                    //Log.e("최하단" , "로드 ")
                    mPresenter?.loadMoreItems()
                }
            }
        })

        edit_search.setOnEditorActionListener { v, actionId, event ->
            when(actionId){
                EditorInfo.IME_ACTION_SEARCH->button_search.performClick()
            }
            true
        }

        setOnClickListener()
    }

    private fun setOnClickListener() {
        button_search.setOnClickListener {
            mPresenter?.loadItems(edit_search.text.toString())
        }
    }

    override fun createPresenter(): MainFragmentPresenter = MainFragmentPresenter(this)

    override fun showListLoading() {
        lottie_loading.visibility =View.VISIBLE
        lottie_loading.playAnimation()
    }

    override fun hideListLoading() {
        lottie_loading.visibility =View.GONE
        lottie_loading.cancelAnimation()
    }

    override fun setList(model: SearchCulture) {
        searchListAdapter.items = model.response.body.items.itemList
        searchListAdapter.notifyDataSetChanged()
    }

    override fun addList(model: SearchCulture) {
        searchListAdapter.addItems(model.response.body.items.itemList)
    }
}
