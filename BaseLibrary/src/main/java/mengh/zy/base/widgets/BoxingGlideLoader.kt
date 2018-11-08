package mengh.zy.base.widgets

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bilibili.boxing.loader.IBoxingCallback
import com.bilibili.boxing.loader.IBoxingMediaLoader
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import mengh.zy.base.R
import mengh.zy.base.common.GlideApp

/**
 * @author by mengh
 * @email menghedianmo@163.com
 * @date on 2018/9/3$.
 * PS: Not easy to write code, please indicate.
 *
 *   Describe:loader
 */
class BoxingGlideLoader : IBoxingMediaLoader {

    override fun displayThumbnail(img: ImageView, absPath: String, width: Int, height: Int) {
        val path = "file://$absPath"
        try {
            // https://github.com/bumptech/glide/issues/1531
//            Glide.with(img.context).load(path).placeholder(R.drawable.ic_boxing_default_image).crossFade().centerCrop().override(width, height).into(img)
            val options: RequestOptions = RequestOptions()
                    .placeholder(R.mipmap.image)
                    .centerCrop()
                    .override(width, height)
            GlideApp.with(img.context)
                    .load(path)
                    .apply(options)
                    .into(img)
        } catch (ignore: IllegalArgumentException) {
        }

    }

    @SuppressLint("CheckResult")
    override fun displayRaw(img: ImageView, absPath: String, width: Int, height: Int, callback: IBoxingCallback?) {
        val path = "file://$absPath"
        val options: RequestOptions = RequestOptions()
                .override(width, height)

        val request = Glide.with(img.context)
                .load(path)
        if (width > 0 && height > 0) {
            request.apply(options)
        }
        request.listener(object : RequestListener<Drawable> {
            override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                if (resource != null && callback != null) {
                    img.setImageDrawable(resource)
                    callback.onSuccess()
                    return true
                }
                return false
            }

            override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                if (callback != null) {
                    callback.onFail(e)
                    return true
                }
                return false
            }
        }).into(img)

    }

}
