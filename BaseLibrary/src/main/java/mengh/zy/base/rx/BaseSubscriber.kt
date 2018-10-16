package mengh.zy.base.rx

import com.google.gson.Gson
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import mengh.zy.base.data.protocol.BaseResp
import mengh.zy.base.presenter.view.BaseView
import retrofit2.HttpException

/**
 * @author by mengh
 * @email menghedianmo@163.com
 * @date on 2018/8/10.
 * PS: Not easy to write code, please indicate.
 *
 *   Describe:RX订阅流程封装类
 */

open class BaseSubscriber<T>(private val baseView: BaseView, private val isHide: Boolean = true) : Observer<T> {

    override fun onSubscribe(d: Disposable) {
    }

    override fun onNext(t: T) {
    }

    override fun onComplete() {
        if (isHide) {
            baseView.hideLoading()
        }
    }

    override fun onError(e: Throwable) {
        baseView.hideLoading()
        try {
            val body = (e as HttpException).response().errorBody()
            val fromJson = Gson().fromJson(body!!.string(), BaseResp::class.java)
            baseView.onError(fromJson.msg)
        }
        catch (e:Exception){
            baseView.onError("未知异常")
        }
    }

}