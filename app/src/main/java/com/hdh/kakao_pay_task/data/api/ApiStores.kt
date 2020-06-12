package com.hdh.kakao_pay_task.data.api

import com.hdh.kakao_pay_task.data.model.SearchBook
import com.hdh.kakao_pay_task.data.model.SearchCafe
import com.hdh.kakao_pay_task.data.model.SearchImage
import com.hdh.kakao_pay_task.data.model.SearchVclip
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
}