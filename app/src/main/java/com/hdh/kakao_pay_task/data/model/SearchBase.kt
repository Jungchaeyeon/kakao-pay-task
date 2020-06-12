package com.hdh.kakao_pay_task.data.model

open class SearchBase {
    val meta: Meta? = null

    data class Meta(
        val total_count: Int,
        val pageable_count: Int,
        val is_end: Boolean
    )
}