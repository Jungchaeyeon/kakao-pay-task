package com.hdh.kakao_pay_task.ui.main.fragment

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.signature.ObjectKey
import com.hdh.kakao_pay_task.R
import com.hdh.kakao_pay_task.data.model.SearchCulture
import com.hdh.kakao_pay_task.utils.AnimationUtil
import com.hdh.kakao_pay_task.utils.DPIUtil
import kotlinx.android.synthetic.main.item_search.view.*

class SearchListAdapter : RecyclerView.Adapter<SearchListAdapter.SearchListHolder>() {

    private var prevSize = 0

    var items: ArrayList<SearchCulture.Item> = ArrayList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    fun addItems(items: ArrayList<SearchCulture.Item>) {
        prevSize = this.items.size - 2
        this.items.addAll(items)
        notifyItemRangeChanged(prevSize, items.size + 2)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchListHolder =
        SearchListHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_search, parent, false)
        )

    override fun onBindViewHolder(holder: SearchListHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class SearchListHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(item: SearchCulture.Item) {
            with(itemView) {

                Glide.with(this).load(item.galWebImageUrl)
                    .override(256, 256)
                    .apply(RequestOptions.bitmapTransform(RoundedCorners(DPIUtil.dp2px(8f).toInt())))
                    .listener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean = false
                        override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                            AnimationUtil.setAlphaAnimation(image_thumbnail)
                            return false
                        }
                    })
                    .into(image_thumbnail)
                text_title.text = item.galTitle

                if (bindingAdapterPosition == items.size - 1) {
                    layout_view.setPadding(0, 0, 0, DPIUtil.dp2px(72f).toInt())
                } else {
                    layout_view.setPadding(0, 0, 0, 0)
                }
            }
        }
    }
}
