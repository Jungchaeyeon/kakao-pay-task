package com.hdh.kakao_pay_task.ui.base

import android.animation.ValueAnimator
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.PersistableBundle
import android.view.View
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.facebook.stetho.common.LogUtil
import com.hdh.kakao_pay_task.R
import com.hdh.kakao_pay_task.utils.ApiClient
import com.hdh.kakao_pay_task.data.api.ApiStores
import com.hdh.kakao_pay_task.utils.ColorUtil
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import retrofit2.Call
import kotlin.collections.ArrayList

abstract class BaseActivity : AppCompatActivity() {

    protected val compositeDisposable = CompositeDisposable()
    public var fragmentList: ArrayList<BaseFragment> = ArrayList()
    private var calls: ArrayList<Call<*>> = ArrayList()
    private var doubleBackToExitPressedOnce = false
    private var isBacking = false
    private var statusBarHexColor = 0
    val loadingState = PublishSubject.create<Boolean>()

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }

    fun setFragmentStatusBarColor(colorID: Int) {
        if (colorID == 0) return

        statusBarAnimation(ContextCompat.getColor(application, colorID))
    }

    fun statusBarAnimation(statusBarColor: Int) {
        val vaStatusBar = ValueAnimator.ofFloat(0f, 1.0f)
        vaStatusBar.addUpdateListener { valueAnimator ->
            val ratio = valueAnimator.animatedValue as Float
            val color: Int = ColorUtil.fadeColor(window.statusBarColor, statusBarColor, ratio)
            window.statusBarColor = color
        }
        vaStatusBar.duration = 400
        vaStatusBar.start()
    }

    override fun onBackPressed() {
        if (fragmentList.size > 0 && fragmentList.size != 1) {
            if (!fragmentList[fragmentList.size - 1].onBackPressed()) {
                return
            }
            if (isBacking) {
                return
            }
            fragmentList[fragmentList.size - 1].popFragment()
            if (!isBacking) {
                isBacking = true
                Handler().postDelayed({ isBacking = false }, 500)
            }
            return
        }

        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }

        this.doubleBackToExitPressedOnce = true

        Toast.makeText(this, "뒤로가기를 한번 더 누르면 앱이 종료됩니다.", Toast.LENGTH_SHORT).show()
        Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 1500)
    }

    fun getTopFragment(): BaseFragment? {
        return if (fragmentList.size <= 0) null else fragmentList[fragmentList.size - 1]
    }

    fun removeBackAllFragment() {
        Handler().postDelayed({
            while (fragmentList.size > 1) {
                popBackStack(false, fragmentList[0], false)
                fragmentList.removeAt(0)
            }
        }, 600)
    }

    open fun removeAllFragment() {
        val currentFragment = fragmentList.size - 1
        while (fragmentList.size > 0) {
            if (currentFragment == fragmentList.size - 1) {
                popBackStack(false, fragmentList[fragmentList.size - 1], true)
            } else {
                popBackStack(false, fragmentList[fragmentList.size - 1], false)
            }
            fragmentList.removeAt(fragmentList.size - 1)
        }
    }

    fun onReturn() {
        hideKeyboard()
        if (statusBarHexColor != 0) {
            setStatusBarHexColor(statusBarHexColor)
            return
        }
    }

    fun setStatusBarHexColor(hex: Int) {
        statusBarHexColor = hex
        statusBarAnimation(hex)
    }

    fun setStatusBarColor(colorID: Int) {
        if (colorID == 0) return
        statusBarHexColor = ContextCompat.getColor(application, colorID)
        statusBarAnimation(statusBarHexColor)
    }

    fun popBackStack(isReturn: Boolean, fragment: BaseFragment, isAnim: Boolean) {
        if (isReturn) {
            if (fragmentList.size > 1) {
                fragmentList[fragmentList.size - 2].onReturn()
            } else {
                onReturn()
            }
        }
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        if (isAnim) {
            transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
        }
        transaction.remove(fragment)
        transaction.commitAllowingStateLoss()
        if (isAnim) {
            if (fragmentList.size > 1) {
                val xmlAnimation = AnimationUtils.loadAnimation(
                    baseContext,
                    R.anim.fade_in
                )
                fragmentList[fragmentList.size - 2].getMotherView()?.startAnimation(xmlAnimation)
            }
        }
    }

    fun popBackStack(fragment: BaseFragment, isAnim: Boolean) {
        popBackStack(true, fragment, isAnim)
    }

    fun popBackStackClose(fragment: BaseFragment, isAnim: Boolean) {
        if (fragmentList.size > 1) {
            fragmentList[fragmentList.size - 2].onReturn()
        } else {
            onReturn()
        }
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        if (isAnim) transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
        transaction.remove(fragment)
        transaction.commitAllowingStateLoss()
    }

    fun popBackStackFadeOut(fragment: BaseFragment, isAnim: Boolean) {
        if (fragmentList.size > 1) {
            fragmentList[fragmentList.size - 2].onReturn()
        } else {
            onReturn()
        }
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        if (isAnim) transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
        transaction.remove(fragment)
        transaction.commitAllowingStateLoss()
    }

    fun pushFragment(fragment: BaseFragment) {

        if (null != findViewById(R.id.fragment_container)) {
            findViewById<View>(R.id.fragment_container).bringToFront()
        }
        val transaction =
            supportFragmentManager.beginTransaction()
        //transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left)
        transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
        transaction.add(R.id.fragment_container, fragment, fragment.javaClass.name)
        transaction.commitAllowingStateLoss()
        if (getTopFragment() != null) {
            val xmlAnimation = AnimationUtils.loadAnimation(baseContext, R.anim.fade_out)
            getTopFragment()?.getMotherView()?.startAnimation(xmlAnimation)
        }
    }

    fun pushUpFragment(fragment: BaseFragment) {
        if (null != findViewById(R.id.fragment_container)) {
            findViewById<View>(R.id.fragment_container).bringToFront()
        }
        val transaction = supportFragmentManager.beginTransaction()
        transaction.setCustomAnimations(R.anim.slide_up_in, R.anim.none)
        transaction.add(R.id.fragment_container, fragment, fragment.javaClass.name)
        transaction.commitAllowingStateLoss()
    }

    fun pushMainFragment(fragment: BaseFragment) {
        pushUpFragment(fragment)
        removeBackAllFragment()
    }

    fun hideKeyboard() {
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
    }

    open fun apiStores(): ApiStores? {
        return ApiClient().retrofit()?.create(ApiStores::class.java)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }

    fun <T> addSubscription(observable: Observable<T>, observer: DisposableObserver<T>) {
        compositeDisposable.add(observer)
        observable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(observer)
    }

    fun addSubscription(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    fun onUnsubscribe() {
        LogUtil.d("onUnSubscribe")
        compositeDisposable.dispose()
    }

}