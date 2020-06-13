package com.hdh.kakao_pay_task.data.model

import com.google.gson.annotations.SerializedName

data class SearchCulture(
    val response: Response
) {
    data class Response(
        val body: Body,
        val header: Header
    )

    data class Body(
        val numOfRows: String,
        val pageNo: String,
        val totalCount: String,
        val items: Items
    )

    data class Items(
        @SerializedName("item")
        val itemList: ArrayList<Item>
    )

    data class Item(
        val galContentId: String,
        val galContentTypeId: String,
        val galTitle: String,
        val galWebImageUrl: String,
        @SerializedName("galCreatedtime")
        val galCreatedTime: String,
        @SerializedName("galModifiedtime")
        val galModifiedTime: String,
        val galPhotographyMonth: String,
        val galPhotographyLocation: String,
        val galPhotographer: String,
        val galSearchKeyword: String,
        val galViewCount: String
    )

    data class Header(
        val resultCode: Int,
        val resultMsg: String
    )
}