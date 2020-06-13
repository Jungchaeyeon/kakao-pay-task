package com.hdh.kakao_pay_task.ui.main.activity

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Base64
import android.util.Log
import com.hdh.kakao_pay_task.R
import com.hdh.kakao_pay_task.ui.base.BaseActivity
import com.hdh.kakao_pay_task.ui.main.fragment.MainFragment
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        pushFragment(MainFragment())
    }
}
