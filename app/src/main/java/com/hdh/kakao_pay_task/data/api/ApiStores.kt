package com.hdh.kakao_pay_task.data.api

import com.hdh.kakao_pay_task.data.model.*
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.*

interface ApiStores {

    @GET("PhotoGalleryService/galleryDetailList")
    fun tourRequest(
        @Query("MobileOS") MobileOS: String = "AND",
        @Query("MobileApp") MobileApp: String = "hdh",
        @Query("ServiceKey", encoded = true) ServiceKey: String = "LfK5Z3og8LzQNxddM%2FbBwctUVcpLk1qTBvzPsJK2SNuqPrPxz%2Fy9lrdhnsCO4t2KRpYX1RZQ1ZthK9NEPYOQGg%3D%3D",
        @Query("title") title: String,
        @Query("_type") _type: String = "json"

    ): Observable<SearchCulture>
}