package com.hdh.kakao_pay_task.utils

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.hdh.kakao_pay_task.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {
    private lateinit var mRetrofit: Retrofit
    private val baseUrl = "https://dapi.kakao.com/"

    fun retrofit(baseUrl: String = this.baseUrl): Retrofit? {
        val builder = OkHttpClient.Builder()

        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

            builder.addInterceptor(loggingInterceptor)
            builder.addNetworkInterceptor(StethoInterceptor())
        }

        mRetrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(
                builder
                    .addInterceptor { chain ->
                        val requestBuilder = chain.request().newBuilder()
                        requestBuilder.addHeader(
                            "Authorization",
                            "KakaoAK 2335c059e76b35744a56e47d9c299db2"
                        )
                        chain.proceed(requestBuilder.build())
                    }
                    .build())
            .build()

        return mRetrofit
    }
}