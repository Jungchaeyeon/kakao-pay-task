package com.hdh.kakao_pay_task.ui.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hdh.kakao_pay_task.R
import com.hdh.kakao_pay_task.data.model.GallerySearchList
import com.hdh.kakao_pay_task.ui.base.BaseFragment
import com.hdh.kakao_pay_task.utils.ImageUtil
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.android.synthetic.main.item_search_linear.*

class DetailFragment(private val item: GallerySearchList.Item) : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = super.setContentView(inflater, R.layout.fragment_detail)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ImageUtil.getImage(imageView=image_thumbnail , imageUrl=item.galWebImageUrl)
        text_main_title.text = item.galTitle
        text_title.text = item.galTitle
        text_tag.text = item.galSearchKeyword(4, 4)
        text_photographer.text = item.galPhotographer()
        text_view_count.text = item.galViewCount()

        setOnClickListener()
    }

    private fun setOnClickListener() {
        image_close.setOnClickListener {
            click.run {
                popFragment()
            }
        }
    }
}