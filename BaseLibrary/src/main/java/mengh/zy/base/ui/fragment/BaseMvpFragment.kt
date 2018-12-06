package mengh.zy.base.ui.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import mengh.zy.base.injection.component.ActivityComponent
import mengh.zy.base.injection.module.ActivityModule
import mengh.zy.base.injection.module.LifecycleProviderModule
import mengh.zy.base.common.BaseApplication
import mengh.zy.base.injection.component.DaggerActivityComponent
import mengh.zy.base.presenter.BasePresenter
import mengh.zy.base.presenter.view.BaseView
import mengh.zy.base.widgets.ProgressLoading
import org.jetbrains.anko.support.v4.toast
import javax.inject.Inject

/**
 * @author by mengh
 * @email menghedianmo@163.com
 * @date on 2018/8/10.
 * PS: Not easy to write code, please indicate.
 *
 *   Describe:动态MVP Activity的基类
 */


abstract class BaseMvpFragment<T : BasePresenter<*>> : BaseFragment(), BaseView {

    @Inject
    lateinit var mPresenter: T

    lateinit var activityComponent: ActivityComponent

    private lateinit var mLoadingDialog: ProgressLoading

    private var isLazyLoad = false

    private var isVisibleToUser = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initActivityInjection()
        injectComponent()
        mLoadingDialog = ProgressLoading.create(mActivity)
        if (isVisibleToUser && !isLazyLoad) {
            isLazyLoad = true
            initView()
        }
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        this.isVisibleToUser = isVisibleToUser
        if (isVisibleToUser && !isLazyLoad && view != null) {
            isLazyLoad = true
            initView()
        }
        super.setUserVisibleHint(isVisibleToUser)
    }

    abstract fun initView()

    abstract fun injectComponent()

    fun initToolbar(toolbar: Toolbar?, title: String, isBack: Boolean = false) {
        toolbar?.title = title
        toolbar?.setTitleTextColor(Color.WHITE)
        mImmersionBar.titleBar(toolbar)
        mActivity.setSupportActionBar(toolbar)
        if (isBack) {
            mActivity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
            toolbar?.setNavigationOnClickListener { mActivity.finish() }
        }
    }

    private fun initActivityInjection() {
        activityComponent = DaggerActivityComponent.builder().appComponent((activity!!.application as BaseApplication).appComponent)
                .activityModule(ActivityModule(this.activity!!))
                .lifecycleProviderModule(LifecycleProviderModule(this))
                .build()
    }

    override fun showLoading() {
        mLoadingDialog.showLoading()
    }

    override fun hideLoading() {
        mLoadingDialog.hideLoading()
    }

    override fun onError(text: String) {
        toast(text)
    }

    override fun onDetach() {
        super.onDetach()
        //解决java.lang.IllegalStateException: Activity has been destroyed 的错误
        try {
            val childFragmentManager = androidx.fragment.app.Fragment::class.java.getDeclaredField("mChildFragmentManager")
            childFragmentManager.isAccessible = true
            childFragmentManager.set(this, null)
        } catch (e: NoSuchFieldException) {
            throw RuntimeException(e)
        } catch (e: IllegalAccessException) {
            throw RuntimeException(e)
        }

    }
}