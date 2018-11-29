package mengh.zy.base.rx

import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import mengh.zy.base.presenter.view.BaseDownloadView
import mengh.zy.base.utils.DMLog
import retrofit2.HttpException

/**
 * @author by mengh
 * @email menghedianmo@163.com
 * @date on 2018/8/10.
 * PS: Not easy to write code, please indicate.
 *
 *   Describe:RX订阅流程封装类
 */

open class BaseDownloadSubscriber<T>(private val baseView: BaseDownloadView) : Observer<T> {

    override fun onSubscribe(d: Disposable) {
    }

    override fun onNext(t: T) {
    }

    override fun onComplete() {
    }

    override fun onError(e: Throwable) {
        val body = (e as HttpException).response().errorBody()
        DMLog.LogV("error",body!!.string())
        baseView.onError("网络异常，下载失败")
    }

}