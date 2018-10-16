package mengh.zy.base.rx

import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.functions.Function
import mengh.zy.base.common.ResultCode
import mengh.zy.base.data.protocol.BaseResp

/**
 * @author by mengh
 * @email menghedianmo@163.com
 * @date on 2018/8/13$.
 * PS: Not easy to write code, please indicate.
 *
 *   Describe:
 */
class BaseFunc<T> : Function<BaseResp<T>, ObservableSource<out T>> {
    override fun apply(t: BaseResp<T>): ObservableSource<out T> {
        return if (t.error_code != ResultCode.SUCCESS) {
            Observable.error(BaseException(t.error_code, t.msg))
        } else {
            Observable.just(t.data)
        }
    }
}