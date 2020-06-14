package com.hdh.kakao_pay_task.ui.main.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hdh.kakao_pay_task.R
import com.hdh.kakao_pay_task.data.model.GallerySearchList
import com.hdh.kakao_pay_task.ui.base.mvp.MvpFragment
import com.hdh.kakao_pay_task.ui.detail.DetailFragment
import com.hdh.kakao_pay_task.utils.Delegate
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : MvpFragment<MainFragmentPresenter>(), MainFragmentView {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = super.setContentView(inflater, R.layout.fragment_main)

    private val searchGridAdapter by lazy {
        SearchGridAdapter(callback)
    }

    private val searchLinearAdapter by lazy {
        SearchLinearAdapter(callback)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setStatusBarResID(R.color.colorStatusBar)

        recycler_search_result.adapter = searchGridAdapter

        recycler_search_result.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(1)) {
                    mPresenter?.loadMoreItems()
                }
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                when (newState) {
                    RecyclerView.SCROLL_STATE_DRAGGING -> hideKeyboard()
                }
            }
        })

        mPresenter?.loadItems("")

        edit_search.setOnEditorActionListener { v, actionId, event ->
            when (actionId) {
                EditorInfo.IME_ACTION_SEARCH -> button_search.performClick()
            }
            true
        }

        setOnClickListener()
    }

    private fun setOnClickListener() {
        button_search.setOnClickListener {
            click.run {
                mPresenter?.loadItems(edit_search.text.toString())
            }
        }

        image_list.setOnClickListener {
            if (recycler_search_result.adapter is SearchGridAdapter) {
                recycler_search_result.layoutManager = LinearLayoutManager(context)
                recycler_search_result.adapter = searchLinearAdapter
                image_grid.setColorFilter(Color.parseColor("#222222"))
                image_list.setColorFilter(Color.parseColor("#ffcb03"))
            }
        }


        image_grid.setOnClickListener {
            if (recycler_search_result.adapter is SearchLinearAdapter) {
                recycler_search_result.layoutManager = GridLayoutManager(context, 2)
                recycler_search_result.adapter = searchGridAdapter
                image_grid.setColorFilter(Color.parseColor("#ffcb03"))
                image_list.setColorFilter(Color.parseColor("#222222"))
            }
        }
    }

    override fun createPresenter(): MainFragmentPresenter = MainFragmentPresenter(this)

    override fun showListLoading() {
        lottie_loading.visibility = View.VISIBLE
        lottie_loading.playAnimation()
    }

    override fun hideListLoading() {
        lottie_loading.visibility = View.GONE
        lottie_loading.cancelAnimation()
    }

    override fun setList(model: ArrayList<GallerySearchList.Item>) {
        searchGridAdapter.items = model
        searchLinearAdapter.items = model
    }

    override fun savePrevSize() {
        searchGridAdapter.savePrevSize()
        searchLinearAdapter.savePrevSize()
    }

    override fun notifyList() {
        searchGridAdapter.notifyList()
        searchLinearAdapter.notifyList()
    }

    private val callback = object : Delegate.Callback<GallerySearchList.Item> {
        override fun run(item: GallerySearchList.Item) {
            click.run {
                pushUpFragment(DetailFragment(item))
            }
        }
    }
}
