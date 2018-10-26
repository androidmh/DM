package mengh.zy.base.ui.fragment

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.blankj.utilcode.util.ScreenUtils
import com.trello.rxlifecycle2.components.support.RxFragment
import mengh.zy.base.R
import mengh.zy.base.common.AppManger
import mengh.zy.base.ui.activity.BaseActivity


/**
 * @author by mengh
 * @email menghedianmo@163.com
 * @date on 2018/8/10.
 * PS: Not easy to write code, please indicate.
 *
 *   Describe:静态Activity的基类
 */

abstract class BaseFragment : RxFragment(), View.OnClickListener {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mActivity = (activity as BaseActivity?)!!
        if (ScreenUtils.isPortrait()) {
            ScreenUtils.adaptScreen4VerticalSlide(mActivity, 360)
        } else {
            ScreenUtils.adaptScreen4HorizontalSlide(mActivity, 360)
        }
        return inflater.inflate(layoutId,null)
    }


    /**
     * 绑定布局
     */
    protected abstract val layoutId: Int

    protected lateinit var mActivity:BaseActivity

    /**
     * 点击事件
     */
    override fun onClick(v: View) {
        if (!isFastClick(1000)) {
            widgetClick(v)
        } else {
            return
        }
    }

    /**
     * 防止快速点击
     */
    private var lastClickTime: Long = 0

    private fun isFastClick(millisecond: Int): Boolean {
        val curClickTime = System.currentTimeMillis()
        val interval = curClickTime - lastClickTime

        if (interval in 1..(millisecond - 1)) {
            // 超过点击间隔后再将lastClickTime重置为当前点击时间
            return true
        }
        lastClickTime = curClickTime
        return false
    }

    /**
     * View点击
     */
    protected abstract fun widgetClick(v: View)

}