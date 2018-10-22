package mengh.zy.base.ext

import android.graphics.drawable.AnimationDrawable
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.kennyc.view.MultiStateView
import mengh.zy.base.data.protocol.BaseResp
import mengh.zy.base.rx.BaseFuncMsg
import com.trello.rxlifecycle2.LifecycleProvider
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import mengh.zy.base.rx.BaseFunc
import mengh.zy.base.rx.BaseSubscriber
import mengh.zy.base.utils.GlideUtils
import mengh.zy.base.widgets.DefaultTextWatcher
import mengh.zy.base.R
import org.jetbrains.anko.find

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
    多状态视图开始加载
 */
fun MultiStateView.startLoading() {
    viewState = MultiStateView.VIEW_STATE_LOADING
    val loadingView = getView(MultiStateView.VIEW_STATE_LOADING)
    val animBackground = loadingView!!.find<View>(R.id.loading_anim_view).background
    (animBackground as AnimationDrawable).start()
}

/*
    扩展edit的获取内容方法
 */
fun EditText.getToString(): String {
    return text.toString()
}

/*
    ImageView加载网络图片
 */
fun ImageView.loadUrl(url: String) {
    GlideUtils.loadImg(context, url, this)
}

/*
    扩展视图可见性
 */
fun View.setVisible(visible: Boolean) {
    this.visibility = if (visible) View.VISIBLE else View.GONE
}