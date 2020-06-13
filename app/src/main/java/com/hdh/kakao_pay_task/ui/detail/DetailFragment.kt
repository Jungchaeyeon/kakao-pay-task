package com.hdh.kakao_pay_task.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hdh.kakao_pay_task.R
import com.hdh.kakao_pay_task.data.model.GallerySearchList
import com.hdh.kakao_pay_task.ui.base.mvp.MvpFragment
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.android.synthetic.main.item_search_linear.*
import kotlinx.android.synthetic.main.item_search_linear.text_title

class DetailFragment(private val item : GallerySearchList.Item) : MvpFragment<DetailPresenter>() , DetailView{

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = super.setContentView(inflater, R.layout.fragment_detail)

    override fun createPresenter(): DetailPresenter = DetailPresenter(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        item.getImage(image_thumbnail)
        text_main_title.text = item.galTitle
        text_title.text = item.galTitle
        text_tag.text = item.galSearchKeyword(4,4)
        text_photographer.text = item.galPhotographer()
        text_view_count.text = item.galViewCount()

        mPresenter?.let { mPresenter ->

        }
        setOnClickListener()
    }

    private fun setOnClickListener() {
        mPresenter?.let { mPresenter ->
            image_close.setOnClickListener {
                popFragment()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter?.onDetach()
    }
}