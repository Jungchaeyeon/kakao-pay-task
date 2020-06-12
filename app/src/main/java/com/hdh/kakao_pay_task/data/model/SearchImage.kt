package com.hdh.kakao_pay_task.data.model

class SearchImage : SearchBase() {

    val documents: ArrayList<Document>? = null

    data class Document(
        val collection: String,
        val thumbnail_url: String,
        val image_url: String,
        val width: Int,
        val height: Int,
        val display_sitename: String,
        val doc_url: String,
        val datetime: String
    )
}