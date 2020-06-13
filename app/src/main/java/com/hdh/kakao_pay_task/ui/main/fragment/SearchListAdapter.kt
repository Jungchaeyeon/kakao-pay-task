package com.hdh.kakao_pay_task.ui.main.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.signature.ObjectKey
import com.hdh.kakao_pay_task.R
import com.hdh.kakao_pay_task.data.model.SearchCulture
import kotlinx.android.synthetic.main.item_search.view.*

class SearchListAdapter : RecyclerView.Adapter<SearchListAdapter.SearchListHolder>() {

    var items: ArrayList<SearchCulture.Item> = ArrayList()
        set(value) {
            field = value
            notifyDataSetChanged()
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
                    .override(500)
                    .signature(ObjectKey(item.galWebImageUrl)).into(image_thumbnail)
                text_title.text = item.galTitle
            }
        }
    }
}
