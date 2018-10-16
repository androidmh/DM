package mengh.zy.base.ui.fragment

import android.os.Bundle
import mengh.zy.base.injection.component.ActivityComponent
import mengh.zy.base.injection.module.ActivityModule
import mengh.zy.base.injection.module.LifecycleProviderModule
import mengh.zy.base.common.BaseApplication
import mengh.zy.base.injection.component.DaggerActivityComponent
import mengh.zy.base.presenter.BasePresenter
import mengh.zy.base.presenter.view.BaseView
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initActivityInjection()
        injectComponent()
    }

    abstract fun injectComponent()

    private fun initActivityInjection() {
        activityComponent = DaggerActivityComponent.builder().appComponent((activity!!.application as BaseApplication).appComponent)
                .activityModule(ActivityModule(this.activity!!))
                .lifecycleProviderModule(LifecycleProviderModule(this))
                .build()
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun onError(text:String) {
        toast(text)
    }
}