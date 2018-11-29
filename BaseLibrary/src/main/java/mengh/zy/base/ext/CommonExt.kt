package mengh.zy.base.ext

import android.content.Context
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import com.bilibili.boxing.model.entity.impl.ImageMedia
import com.bilibili.boxing.utils.ImageCompressor
import mengh.zy.base.data.protocol.BaseResp
import mengh.zy.base.rx.BaseFuncMsg
import com.trello.rxlifecycle2.LifecycleProvider
import com.vlonjatg.progressactivity.ProgressRelativeLayout
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.jessyan.progressmanager.ProgressListener
import me.jessyan.progressmanager.ProgressManager
import me.jessyan.progressmanager.body.ProgressInfo
import mengh.zy.base.rx.BaseFunc
import mengh.zy.base.rx.BaseSubscriber
import mengh.zy.base.utils.GlideUtils
import mengh.zy.base.widgets.DefaultTextWatcher
import mengh.zy.base.R
import mengh.zy.base.R.id.mProgressBar
import mengh.zy.base.rx.BaseDownloadSubscriber
import java.io.File
import java.lang.Exception

/**
 * @author by mengh
 * @email menghedianmo@163.com
 * @date on 2018/8/10$.
 * PS: Not easy to write code, please indicate.
 */
fun <T> Observable<T>.execute(subscriber: BaseSubscriber<T>, lifecycleProvider: LifecycleProvider<*>) {
    this.observeOn(AndroidSchedulers.mainThread())
            .compose(lifecycleProvider.bindToLifecycle())
            .subscribeOn(Schedulers.io())
            .subscribe(subscriber)
}

fun <T> Observable<T>.executeDown(subscriber: BaseDownloadSubscriber<T>) {
    this.observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(subscriber)
}

fun <T> Observable<BaseResp<T>>.convert(): Observable<T> {
    return this.flatMap(BaseFunc())
}

fun <T> Observable<BaseResp<T>>.convertMsg(): Observable<String> {
    return this.flatMap(BaseFuncMsg())
}

/*
    扩展点击事件
 */
fun View.onClick(listener: View.OnClickListener): View {
    setOnClickListener(listener)
    return this
}

/*
    扩展点击事件，参数为方法
 */
fun View.onClick(method: () -> Unit): View {
    setOnClickListener { method() }
    return this
}

/*
    扩展长按事件，参数为方法
 */
fun View.onLongClick(method: () -> Unit): View {
    setOnLongClickListener {
        method()
        false
    }
    return this
}

fun ImageMedia.getCompressFile(context: Context): File {
    var file = File(this.thumbnailPath)
    this.setSize(file.length().toString())
    if (this.compress(ImageCompressor(context))) {
        this.removeExif()
        file = File(this.compressPath)
    }
    return file
}

fun Button.enable(method: () -> Boolean, vararg et: EditText) {
    val btn = this
    for (editText in et) {
        editText.addTextChangedListener(object : DefaultTextWatcher() {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                btn.isEnabled = method()
            }
        })
    }
}

/*
    扩展edit的获取内容方法
 */
fun EditText.getToString(): String {
    return text.toString()
}

fun ProgressRelativeLayout.empty(img: Int = R.mipmap.empty, title: String = "暂无数据", des: String = "") {
    this.showEmpty(img, title, des)
}

fun ProgressRelativeLayout.error(img: Int = R.mipmap.error, title: String = "暂无数据", des: String = "", btnText: String = "重试", listener: View.OnClickListener) {
    this.showError(img, title, des, btnText, listener)
}

/*
    ImageView加载网络图片(圆形)
 */
fun ImageView.loadCircleUrl(url: String) {
    GlideUtils.loadCircleImg(context, url, this)
}

/*
    ImageView加载网络图片
 */
fun ImageView.loadUrl(url: String) {
    GlideUtils.loadImg(context, url, this)
}

/*
    View加载网络图片
 */
fun View.loadUrlTarget(url: String) {
    GlideUtils.loadViewImg(context, url, this)
}

/*
    扩展视图可见性
 */
fun View.setVisible(visible: Boolean) {
    this.visibility = if (visible) View.VISIBLE else View.GONE
}

/*
    动态加载进度条
 */
fun ProgressBar.setProgress(url: String) {
    ProgressManager.getInstance().addResponseListener(url, object : ProgressListener {
        override fun onProgress(progressInfo: ProgressInfo) {
            this@setProgress.progress = progressInfo.percent
        }

        override fun onError(id: Long, e: Exception?) {
        }
    })
}