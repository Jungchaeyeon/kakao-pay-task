package com.hdh.kakao_pay_task.ui.main.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hdh.kakao_pay_task.R
import com.hdh.kakao_pay_task.data.model.GallerySearchList
import com.hdh.kakao_pay_task.utils.Delegate
import com.hdh.kakao_pay_task.utils.ImageUtil
import kotlinx.android.synthetic.main.item_search_linear.view.*

class SearchLinearAdapter(
    private val callback : Delegate.Callback<GallerySearchList.Item>) : RecyclerView.Adapter<SearchLinearAdapter.SearchListHolder>() {

    private var prevSize = 0

    var items: ArrayList<GallerySearchList.Item> = ArrayList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    fun savePrevSize() {
        prevSize = this.items.size - 1
    }

    fun notifyList(){
        notifyItemRangeChanged(prevSize, items.size + 1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchListHolder =
        SearchListHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_search_linear, parent, false)
        )

    override fun onBindViewHolder(holder: SearchListHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class SearchListHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(item: GallerySearchList.Item) {
            with(itemView) {
                ImageUtil.getImage(imageView=image_thumbnail , imageUrl=item.galWebImageUrl)
                text_title.text = item.galTitle
                text_tag.text = item.galSearchKeyword(4,4)
                text_photographer.text = item.galPhotographer()
                text_view_count.text = item.galViewCount()
                view_void.visibility = getVisibility(bindingAdapterPosition)

                this.setOnClickListener {
                    callback.run(item)
                }
            }
        }

        private fun getVisibility(position : Int) : Int{
            return if (position == items.size - 1) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }
    }
}
