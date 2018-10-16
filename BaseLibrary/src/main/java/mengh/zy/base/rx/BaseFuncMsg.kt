package mengh.zy.base.rx

import mengh.zy.base.data.protocol.BaseResp
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.functions.Function
import mengh.zy.base.common.ResultCode

/**
 * @author by mengh
 * @email menghedianmo@163.com
 * @date on 2018/8/13$.
 * PS: Not easy to write code, please indicate.
 *
 *   Describe:
 */
class BaseFuncMsg<T> : Function<BaseResp<T>, ObservableSource<out String>> {
    override fun apply(t: BaseResp<T>): Observable<String>? {
        return Observable.just(t.msg)
    }
}