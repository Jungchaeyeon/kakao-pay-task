package com.hdh.kakao_pay_task

import android.app.Application
import com.facebook.stetho.Stetho


class KakaoPayTaskApplication : Application() {


    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG){
            Stetho.initializeWithDefaults(this)
        }

    }
}