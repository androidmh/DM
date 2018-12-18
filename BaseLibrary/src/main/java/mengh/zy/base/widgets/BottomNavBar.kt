package mengh.zy.base.widgets

import android.content.Context
import android.util.AttributeSet
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.ashokvarma.bottomnavigation.BottomNavigationItem
import mengh.zy.base.R

/**
 * @author by mengh
 * @email menghedianmo@163.com
 * @date on 2018/8/17$.
 * PS: Not easy to write code, please indicate.
 *
 *   Describe:
 */
class BottomNavBar @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : BottomNavigationBar(context, attrs, defStyleAttr) {


    init {
        // 首页item
        val homeItem = BottomNavigationItem(R.mipmap.index, resources.getString(R.string.index))
                .setActiveColorResource(R.color.colorPrimaryDark)
                .setInActiveColorResource(R.color.text_dark)

        // 图片item
        val imgItem = BottomNavigationItem(R.mipmap.image, resources.getString(R.string.img))
                .setActiveColorResource(R.color.colorPrimaryDark)
                .setInActiveColorResource(R.color.text_dark)

        // 视频item(暂时放弃)
//        val videoItem = BottomNavigationItem(R.mipmap.video, resources.getString(R.string.video))
//                .setActiveColorResource(R.color.text_light_dark)
//                .setActiveColorResource(R.color.colorPrimaryDark)
//                .setInActiveColorResource(R.color.text_dark)

        // 我的item
        val userItem = BottomNavigationItem(R.mipmap.user_center, resources.getString(R.string.user_center))
                .setActiveColorResource(R.color.colorPrimaryDark)
                .setInActiveColorResource(R.color.text_dark)

        setMode(BottomNavigationBar.MODE_DEFAULT)
        setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC)
        setBarBackgroundColor(R.color.bottom_nav)

        addItem(homeItem)
                .addItem(imgItem)
//                .addItem(videoItem)
                .addItem(userItem)
                .setFirstSelectedPosition(0)
                .initialise()
    }
}