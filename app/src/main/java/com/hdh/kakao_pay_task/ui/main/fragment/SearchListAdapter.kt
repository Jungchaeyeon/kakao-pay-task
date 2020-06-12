package com.hdh.kakao_pay_task.ui.main.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hdh.kakao_pay_task.R
import com.hdh.kakao_pay_task.data.model.*
import kotlinx.android.synthetic.main.item_search.view.*

class SearchListAdapter : RecyclerView.Adapter<SearchListAdapter.SearchListHolder>() {

    public var items: ArrayList<SearchCulture> = ArrayList()
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
        fun bind(item: SearchCulture) {
            with(itemView){
                //Glide.with(this).load(item.thumbnail).into(image_thumbnail)
                //text_title.text = item.addr
            }
//            when (item) {
//                is SearchVclip -> bind(item as SearchVclip.Document)
//                is SearchImage -> bind(item as SearchImage.Document)
//                is SearchBlog -> bind(item as SearchBlog.Document)
//                is SearchBook -> bind(item as SearchBook.Document)
//                is SearchCafe -> bind(item as SearchCafe.Document)
//            }
        }

//        private fun bind(item: SearchVclip.Document) {
//            with(itemView){
//                Glide.with(this).load(item.thumbnail).into(image_thumbnail)
//                text_title.text = item.title
//            }
//        }

        private fun bind(item: SearchImage.Document) {

        }

        private fun bind(item: SearchBlog.Document) {

        }

        private fun bind(item: SearchBook.Document) {

        }

        private fun bind(item: SearchCafe.Document) {

        }
    }
}
