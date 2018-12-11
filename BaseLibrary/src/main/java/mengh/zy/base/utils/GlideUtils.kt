package mengh.zy.base.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomViewTarget
import com.bumptech.glide.request.transition.Transition
import mengh.zy.base.R
import mengh.zy.base.common.BaseApplication
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
    private var options: RequestOptions = RequestOptions()
            .placeholder(R.drawable.icon_default_user)
            .centerCrop()
            .circleCrop()

    private var options2: RequestOptions = RequestOptions()
            .placeholder(R.drawable.loading)

    private var diskOptions: RequestOptions = RequestOptions()
            .placeholder(mengh.zy.base.R.drawable.loading)
            .diskCacheStrategy(DiskCacheStrategy.ALL)

    /**
     * @param context 上下文
     * @param url 加载地址
     * @param imageView 加载imageView
     * 普通加载图片
     */
    fun loadCircleImg(context: Context, url: String, imageView: ImageView) {
        GlideApp.with(context)
                .load(url)
                .apply(options)
                .transition(DrawableTransitionOptions().crossFade(500))
                .into(imageView)
    }

    fun loadImg(context: Context, url: String, imageView: ImageView, isCenter: Boolean) {
        if (isCenter) {
            GlideApp.with(context)
                    .load(url)
                    .apply(options2)
                    .centerCrop()
                    .transition(DrawableTransitionOptions().crossFade(500))
                    .into(imageView)
        } else {
            GlideApp.with(context)
                    .load(url)
                    .apply(options2)
                    .transition(DrawableTransitionOptions().crossFade(500))
                    .into(imageView)
        }
    }

    fun loadDiskCacheImg(context: Context, url: String, imageView: ImageView) {
        Glide.with(context)
                .load(url)
                .apply(diskOptions)
                .into(imageView)
    }

    fun downloadDisk(url: String): Boolean {
        val file = Glide.with(BaseApplication.context)
                .download(url)
                .submit()
                .get()
        return DMUtils.writeResponseBodyToDisk(file, BaseApplication.context)
    }

    fun loadViewImg(context: Context, url: String, view: View) {
        GlideApp.with(context)
                .load(url)
                .apply(options2)
                .transition(DrawableTransitionOptions().crossFade(500))
                .into<CustomViewTarget<View, Drawable>>(object : CustomViewTarget<View, Drawable>(view) {
                    override fun onLoadFailed(errorDrawable: Drawable?) {
                    }

                    override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                        view.background = resource
                    }

                    override fun onResourceCleared(placeholder: Drawable?) {
                    }
                })
    }

}
