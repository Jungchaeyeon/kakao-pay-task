package com.hdh.kakao_pay_task.data.model

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.target.ViewTarget
import com.google.gson.annotations.SerializedName
import com.hdh.kakao_pay_task.utils.AnimationUtil
import com.hdh.kakao_pay_task.utils.DPIUtil
import kotlinx.android.synthetic.main.item_top.view.*
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
        val galViewCount: Int
    ){
        fun galPhotographer() : String {
            val textLength = galPhotographer.length
            return if (textLength > 3) {
                galPhotographer.substring(textLength-3 , textLength)
            } else {
                galPhotographer
            }
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

        fun getImage(imageView : AppCompatImageView) : ViewTarget<ImageView , Drawable>{
            return Glide.with(imageView.context).load(galWebImageUrl)
                .override(256, 256)
                .apply(RequestOptions.bitmapTransform(RoundedCorners(DPIUtil.dp2px(8f).toInt())))
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean = false
                    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                        AnimationUtil.setAlphaAnimation(imageView)
                        return false
                    }
                })
                .into(imageView)
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