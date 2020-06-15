package com.hdh.kakao_pay_task.data.model

import com.google.gson.annotations.SerializedName
import java.text.DecimalFormat

data class GallerySearchList(
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

    class Item(
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
        val galViewCount: Int
    ){
        fun galPhotographer() : String {
            val tempList = galPhotographer.split(" ")
            return tempList[tempList.size - 1]
        }

        fun galSearchKeyword(maxLength : Int , quantity : Int) : String{
            var resultTag = ""
            val tagList = galSearchKeyword.replace(" ", "").split(",")
            var count = 0
            for(item in tagList){
                if (count >= quantity)
                    break
                else if (item.length > maxLength)
                    continue

                resultTag += "#${item} "
                count++
            }

            return if (resultTag.isNotEmpty()) {
                resultTag.substring(0, resultTag.length - 1)
            } else {
                ""
            }
        }

        fun galViewCount() : String {
            return toStringFrom1000(galViewCount)
        }

        private fun toStringFrom1000(num: Int): String {
            val df = DecimalFormat("#,###")
            return df.format(num.toLong())
        }
    }

    data class Header(
        val resultCode: Int,
        val resultMsg: String
    )
}