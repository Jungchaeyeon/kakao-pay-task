package com.hdh.kakao_pay_task.data.model

class SearchVclip : SearchBase() {

    val documents: ArrayList<Document> = ArrayList()

    data class Document(
        val title: String,
        val url: String,
        val datetime: String,
        val play_time: Int,
        val thumbnail: String,
        val author: String
    )
}