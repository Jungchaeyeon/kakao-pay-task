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
        //Log.e("Hash" , getHashKey(this).toString())

        pushFragment(MainFragment())
    }

    fun getHashKey(context: Context): String? {
        try {
            if (Build.VERSION.SDK_INT >= 28) {

                val packageInfo = packageManager.getPackageInfo(packageName , PackageManager.GET_SIGNING_CERTIFICATES)
                val signatures = packageInfo.signingInfo.apkContentsSigners
                val md = MessageDigest.getInstance("SHA")
                for (signature in signatures) {
                    md.update(signature.toByteArray())
                    return String(Base64.encode(md.digest(), Base64.NO_WRAP))
                }
            } else {
                val packageInfo = packageManager.getPackageInfo(packageName , PackageManager.GET_SIGNATURES) ?: return null

                for (signature in packageInfo.signatures) {
                    try {
                        val md = MessageDigest.getInstance("SHA")
                        md.update(signature.toByteArray())
                        return Base64.encodeToString(md.digest(), Base64.NO_WRAP)
                    } catch (e: NoSuchAlgorithmException) {
                        // ERROR LOG
                    }
                }
            }
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }

        return null
    }
}
