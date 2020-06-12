package com.hdh.kakao_pay_task.data.model

class SearchBook : SearchBase() {

    val documents: ArrayList<Document>? = null

    data class Document(
        val title: String,
        val contents: String,
        val url: String,
        val isbn: String,
        val datetime: String,
        val authors: String,
        val publisher: String,
        val translators: String,
        val price: String,
        val sale_price: String,
        val thumbnail: String,
        val status: String
    )
}