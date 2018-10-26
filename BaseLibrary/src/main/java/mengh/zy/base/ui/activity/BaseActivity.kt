package mengh.zy.base.ui.activity

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import com.blankj.utilcode.util.ScreenUtils
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import mengh.zy.base.R
import mengh.zy.base.common.AppManger
import org.jetbrains.anko.find

/**
 * @author by mengh
 * @email menghedianmo@163.com
 * @date on 2018/8/10.
 * PS: Not easy to write code, please indicate.
 *
 *   Describe:静态Activity的基类
 */

abstract class BaseActivity : RxAppCompatActivity(), View.OnClickListener {

    protected var titleTB = ""

    protected var isBack = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mContextView = LayoutInflater.from(this).inflate(layoutId, null)
        setContentView(mContextView)
        initView()
        if (ScreenUtils.isPortrait()) {
            ScreenUtils.adaptScreen4VerticalSlide(this, 360)
        } else {
            ScreenUtils.adaptScreen4HorizontalSlide(this, 360)
        }
    }

    private fun fullScreen() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                //5.x开始需要把颜色设置透明，否则导航栏会呈现系统默认的浅灰色
                val window = this.window
                val decorView = window.decorView
                //两个 flag 要结合使用，表示让应用的主体内容占用系统状态栏的空间
                val option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                decorView.systemUiVisibility = option
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                window.statusBarColor = Color.TRANSPARENT
                //导航栏颜色也可以正常设置
//                window.navigationBarColor = Color.TRANSPARENT
            } else {
                val window = this.window
                val attributes = window.attributes
                val flagTranslucentStatus = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                val flagTranslucentNavigation = WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION
                attributes.flags = attributes.flags or flagTranslucentStatus
                //                attributes.flags |= flagTranslucentNavigation;
                window.attributes = attributes
            }
        }
    }

    /**
     * 绑定布局
     */
    protected abstract val layoutId: Int

    abstract fun initView()

    override fun onDestroy() {
        super.onDestroy()
        AppManger.instance.finishActivity(this)
        ScreenUtils.cancelAdaptScreen(this)
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
     * View点击
     */
    protected abstract fun widgetClick(v: View)
}