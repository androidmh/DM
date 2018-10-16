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
                .setActiveColorResource(R.color.text_light_dark)
                .setInActiveColorResource(R.color.white)

        // 图片item
        val imgItem = BottomNavigationItem(R.mipmap.image, resources.getString(R.string.img))
                .setActiveColorResource(R.color.text_light_dark)
                .setInActiveColorResource(R.color.white)

        // 视频item
        val videoItem = BottomNavigationItem(R.mipmap.video, resources.getString(R.string.video))
                .setActiveColorResource(R.color.text_light_dark)
                .setInActiveColorResource(R.color.white)

        // 我的item
        val userItem = BottomNavigationItem(R.mipmap.user_center, resources.getString(R.string.user_center))
                .setActiveColorResource(R.color.text_light_dark)
                .setInActiveColorResource(R.color.white)

        setMode(BottomNavigationBar.MODE_FIXED)
        setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC)
        setBarBackgroundColor(R.color.colorPrimary)

        addItem(homeItem)
                .addItem(imgItem)
                .addItem(videoItem)
                .addItem(userItem)
                .setFirstSelectedPosition(0)
                .initialise()
    }
}