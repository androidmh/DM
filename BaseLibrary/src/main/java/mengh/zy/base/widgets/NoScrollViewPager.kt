package mengh.zy.base.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent

/**
 * author : HJQ
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2018/10/18
 * desc   : 禁用水平滑动的ViewPager（一般用于APP主页的ViewPager + Fragment）
 */
class NoScrollViewPager : androidx.viewpager.widget.ViewPager {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    // 不拦截这个事件
    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        return false
    }

    // 不处理这个事件
    override fun onTouchEvent(ev: MotionEvent): Boolean {
        performClick()
        return false
    }

    override fun performClick(): Boolean {
        super.performClick()
        return true
    }
}