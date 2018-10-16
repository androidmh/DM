package mengh.zy.base.widgets

import android.app.Activity
import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import kotlinx.android.synthetic.main.layout_header_bar.view.*
import mengh.zy.base.R

/**
 * @author by mengh
 * @email menghedianmo@163.com
 * @date on 2018/8/30$.
 * PS: Not easy to write code, please indicate.
 *
 *   Describe:
 */
class HeaderToolBar(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {
    private var isShowBack = true
    private var titleText: String? = null
    private var rightText: String? = null

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.HeaderToolBar)
        isShowBack = typedArray.getBoolean(R.styleable.HeaderToolBar_isShowBack, true)
        titleText = typedArray.getString(R.styleable.HeaderToolBar_titleText)
        rightText = typedArray.getString(R.styleable.HeaderToolBar_rightText)

        initView()
        typedArray.recycle()
    }

    private fun initView() {
        View.inflate(context, R.layout.layout_header_bar, this)
        if (isShowBack) {
            if (isInEditMode) {
                return
            }
            headerBar.setNavigationIcon(R.mipmap.arrow_back)
            headerBar.setNavigationOnClickListener {
                if (context is Activity) {
                    (context as Activity).finish()
                }
            }
        }
        titleText?.let {
            tvTitle.text = it

        }
        rightText?.let {
            tvRight.text = it
            tvRight.visibility = View.VISIBLE
        }
    }

    fun getRightView(): TextView {
        return tvRight
    }
}