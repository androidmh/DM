package mengh.zy.base.ui.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar

abstract class BaseLazyFragment : BaseFragment() {

    private var isLazyLoad = false

    private var isVisibleToUser = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (isVisibleToUser && !isLazyLoad) {
            isLazyLoad = true
//            initView()
        }
    }


    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        this.isVisibleToUser = isVisibleToUser
        if (isVisibleToUser && !isLazyLoad && view != null) {
            isLazyLoad = true
//            initView()
        }
        super.setUserVisibleHint(isVisibleToUser)
    }

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