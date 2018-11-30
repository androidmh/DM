package mengh.zy.base.ui.activity

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.transition.TransitionInflater
import android.util.Pair
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import com.blankj.utilcode.util.ScreenUtils
import com.gyf.barlibrary.ImmersionBar
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import mengh.zy.base.R
import mengh.zy.base.common.AppManger
import mengh.zy.base.ext.judgeSdk21

/**
 * @author by mengh
 * @email menghedianmo@163.com
 * @date on 2018/8/10.
 * PS: Not easy to write code, please indicate.
 *
 *   Describe:静态Activity的基类
 */

abstract class BaseActivity : RxAppCompatActivity(), View.OnClickListener {

    private lateinit var mImmersionBar: ImmersionBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        judgeSdk21({
            val transition = TransitionInflater.from(this).inflateTransition(R.transition.fade)
            window.enterTransition = transition
            window.exitTransition = transition
            window.reenterTransition = transition
        })

        val mContextView = LayoutInflater.from(this).inflate(layoutId, null)
        setContentView(mContextView)
        AppManger.instance.addActivity(this)
        initView()
        mImmersionBar = ImmersionBar.with(this)
        mImmersionBar
                .keyboardMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
                .init()
        if (ScreenUtils.isPortrait()) {
            ScreenUtils.adaptScreen4VerticalSlide(this, 360)
        } else {
            ScreenUtils.adaptScreen4HorizontalSlide(this, 360)
        }
    }

    /**
     * 绑定布局
     */
    protected abstract val layoutId: Int

    abstract fun initView()

    override fun onDestroy() {
        super.onDestroy()
        mImmersionBar.destroy()
        AppManger.instance.finishActivity(this)
    }

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
     * 带动画跳转
     */
    fun startActivityAnimation(cls: Class<out BaseActivity>, bundle: Bundle? = null, vararg sharedElements: Pair<View, String>) {
        val intent = Intent(this, cls)
        if (bundle != null) {
            intent.putExtras(bundle)
        }
        judgeSdk21({
            val toBundle = ActivityOptions.makeSceneTransitionAnimation(this, *sharedElements).toBundle()
            startActivity(intent, toBundle)
        }, {
            startActivity(intent)
        })
    }

    /**
     * 带动画finish
     */
    fun finishAnimation() {
        judgeSdk21({
            finishAfterTransition()
        }, {
            finish()
        })
    }

    /**
     * View点击
     */
    protected abstract fun widgetClick(v: View)


}