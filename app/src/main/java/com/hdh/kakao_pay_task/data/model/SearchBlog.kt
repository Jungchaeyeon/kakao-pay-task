package com.hdh.kakao_pay_task.data.model

class SearchBlog : SearchBase() {

    val documents: ArrayList<Document>? = null

    data class Document(
        val title: String,
        val contents: String,
        val url: String,
        val blogname: String,
        val thumbnail: String,
        val datetime: String
    )
}