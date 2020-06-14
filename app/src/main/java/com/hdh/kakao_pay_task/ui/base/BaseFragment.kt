package com.hdh.kakao_pay_task.ui.base

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.hdh.kakao_pay_task.utils.ApiClient
import com.hdh.kakao_pay_task.data.api.ApiStores
import com.hdh.kakao_pay_task.utils.ClickUtil
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.util.*

abstract class BaseFragment : Fragment(), BaseView {
    private var mView: View? = null
    override var mContext: Context? = null
    override var mActivity: BaseActivity? = null
    private val compositeDisposable = CompositeDisposable()
    private var statusBarHexColor = 0
    private var statusBarResID = 0
    private var isCloseType: Boolean = false
    val click by lazy { ClickUtil(this.lifecycle) }

    fun getBaseActivity(): BaseActivity {
        return activity as BaseActivity
    }

    fun onBackPressed(): Boolean {
        return true
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = getBaseActivity()

        val fragmentList: ArrayList<BaseFragment> = getBaseActivity().fragmentList
        if (fragmentList.size > 0) {
            fragmentList[fragmentList.size - 1].mView?.isClickable = false
        }
        getBaseActivity().fragmentList.add(this)
    }

    fun setContentView(inflater: LayoutInflater, resId: Int): View {
        val decor = activity?.window?.decorView
        mView = inflater.inflate(resId, null)
        mContext = mView?.context

        decor?.systemUiVisibility = 0

        hideKeyboard()

        return inflater.inflate(resId, null)
    }

    override fun pushFragment(fragment: BaseFragment) {
        this.mView?.isClickable = false
        getBaseActivity().pushFragment(fragment)
    }

    override fun pushUpFragment(fragment: BaseFragment) {
        this.mView?.isClickable = false
        fragment.isCloseType = true
        getBaseActivity().pushUpFragment(fragment)
    }

    override fun pushMainFragment(fragment: BaseFragment) {
        this.mView?.isClickable = false
        getBaseActivity().pushMainFragment(fragment)
    }

    override fun popFragment() {
        if (isCloseType) {
            getBaseActivity().popBackStackClose(this, true)
            return
        }
        getBaseActivity().popBackStack(this, true)
    }


    override fun popFragment(fragment: BaseFragment) {
        // no animation
        if (isCloseType) {
            getBaseActivity().popBackStackClose(this, false)
            return
        }
        getBaseActivity().popBackStack(fragment, false)
    }

    open fun apiStores(): ApiStores? {
        return ApiClient().retrofit()?.create(ApiStores::class.java)
    }

    override fun onReturn() {
        this.mView?.isClickable = true

        if (statusBarHexColor != 0) {
            setStatusBarNoAnimation(statusBarHexColor)
            return
        }
        setStatusBarResID(statusBarResID)
    }

    override fun setStatusBarNoAnimation(hex: Int) {
        statusBarHexColor = hex

        getBaseActivity().window?.statusBarColor = statusBarHexColor
    }

    override fun setStatusBarResID(id: Int) {
        statusBarResID = id
        getBaseActivity().setFragmentStatusBarColor(id)
    }

    open fun getMotherView(): View? {
        return mView
    }

    override fun showToast(message: String?) {
        message?.let {
            if (it.isNotEmpty())
                Toast.makeText(getBaseActivity(), message, Toast.LENGTH_LONG).show()
        }
    }

    override fun showLoading() {
        getBaseActivity().loadingState.onNext(true)
    }

    override fun hideLoading() {
        getBaseActivity().loadingState.onNext(false)
    }

    override fun hideKeyboard() {
        getBaseActivity().hideKeyboard()
    }

    override fun onDestroy() {
        super.onDestroy()

        getBaseActivity().fragmentList.remove(this)
        getBaseActivity().window?.clearFlags(WindowManager.LayoutParams.FLAG_SECURE)
        onUnsubscribe()
    }

    open fun onUnsubscribe() {
        compositeDisposable.dispose()
    }

    open fun addSubscription(observer: Disposable) {
        compositeDisposable.add(observer)
    }
}