package mengh.zy.base.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import mengh.zy.base.R
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatTextView


/**
 * @author by mengh
 * @email menghedianmo@163.com
 * @date on 2018/8/30$.
 * PS: Not easy to write code, please indicate.
 *
 *   Describe:
 */
class DMLabelView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    init {
        this.background = ContextCompat.getDrawable(context, R.drawable.label_bg)
        this.setPadding(15, 15, 15, 15)
        val params = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT)
        params.setMargins(10,10,10,10)
        this.layoutParams = params
        this.textSize = 12f
    }
}