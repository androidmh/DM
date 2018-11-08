package mengh.zy.base.ui.activity

import android.os.Bundle
import com.alibaba.android.arouter.launcher.ARouter
import mengh.zy.base.injection.component.ActivityComponent
import mengh.zy.base.injection.module.ActivityModule
import mengh.zy.base.injection.module.LifecycleProviderModule
import mengh.zy.base.common.BaseApplication
import mengh.zy.base.presenter.BasePresenter
import mengh.zy.base.presenter.view.BaseView
import mengh.zy.base.widgets.ProgressLoading
import mengh.zy.base.injection.component.DaggerActivityComponent
import org.jetbrains.anko.toast
import javax.inject.Inject

/**
 * @author by mengh
 * @email menghedianmo@163.com
 * @date on 2018/8/10.
 * PS: Not easy to write code, please indicate.
 *
 *   Describe:动态MVP Activity的基类
 */


abstract class BaseMvpActivity<T : BasePresenter<*>> : BaseActivity(), BaseView {

    @Inject
    lateinit var mPresenter: T

    lateinit var activityComponent: ActivityComponent

    private lateinit var mLoadingDialog: ProgressLoading

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //加载布局


        initActivityInjection()

        injectComponent()

        mLoadingDialog = ProgressLoading.create(this)
        ARouter.getInstance().inject(this)
    }


    /**
     * Dagger注册
     */
    abstract fun injectComponent()

    private fun initActivityInjection() {
        activityComponent = DaggerActivityComponent.builder().appComponent((application as BaseApplication).appComponent)
                .activityModule(ActivityModule(this))
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
}