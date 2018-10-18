package mengh.zy.base.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import mengh.zy.base.R
import mengh.zy.base.common.GlideApp


/**
 * @author by mengh
 * @email menghedianmo@163.com
 * @date on 2018/9/3$.
 * PS: Not easy to write code, please indicate.
 *
 *   Describe:Glide工具类
 */
object GlideUtils {
    //glide相关设置
    var options: RequestOptions = RequestOptions()
            .placeholder(R.drawable.icon_default_user)
            .centerCrop()
            .circleCrop()


    /**
     * @param activity 上下文
     * @param url 加载地址
     * @param imageView 加载imageview
     * 普通加载图片
     */
    fun loadImg(context: Context, url: String, imageView: ImageView) {
        GlideApp.with(context)
                .load(url)
                .apply(options)
                .transition(DrawableTransitionOptions().crossFade(500))
                .into(imageView)
    }

}
