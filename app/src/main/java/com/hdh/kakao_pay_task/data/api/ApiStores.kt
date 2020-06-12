package com.hdh.kakao_pay_task.data.api

import com.hdh.kakao_pay_task.data.model.*
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.*

interface ApiStores {

    @GET("v2/search/vclip")
    fun vclipRequest(
        @Query("query") query: String,
        @Query("sort") sort: String = "accuracy",
        @Query("page") page: Int,
        @Query("size") size: Int = 15
    ): Observable<SearchVclip>

    @GET("v2/search/image")
    fun imageRequest(
        @Query("query") query: String,
        @Query("sort") sort: String = "accuracy",
        @Query("page") page: Int,
        @Query("size") size: Int = 15
    ): Observable<SearchImage>

    @GET("v2/search/blog")
    fun blogRequest(
        @Query("query") query: String,
        @Query("sort") sort: String = "accuracy",
        @Query("page") page: Int,
        @Query("size") size: Int = 15
    ): Observable<SearchImage>

    @GET("v2/search/book")
    fun bookRequest(
        @Query("query") query: String,
        @Query("sort") sort: String = "accuracy",
        @Query("page") page: Int,
        @Query("size") size: Int = 15
    ): Observable<SearchBook>

    @GET("v2/search/cafe")
    fun cafeRequest(
        @Query("query") query: String,
        @Query("sort") sort: String = "accuracy",
        @Query("page") page: Int,
        @Query("size") size: Int = 15
    ): Observable<SearchCafe>


    @GET("storesByAddr/json")
    fun maskRequest(
        @Query("address") query: String
    ): Observable<SearchMask>

    @GET("openapi/service/rest/PhotoGalleryService/galleryDetailList")
    fun tourRequest(
        @Query("MobileOS") MobileOS: String = "AND",
        @Query("MobileApp") MobileApp: String = "hdh",
        @Query("ServiceKey", encoded = true) ServiceKey: String = "LfK5Z3og8LzQNxddM%2FbBwctUVcpLk1qTBvzPsJK2SNuqPrPxz%2Fy9lrdhnsCO4t2KRpYX1RZQ1ZthK9NEPYOQGg%3D%3D",
        @Query("title") title: String,
        @Query("_type") _type: String = "json"

    ): Observable<SearchCulture>
}