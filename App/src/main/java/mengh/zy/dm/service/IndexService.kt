package mengh.zy.dm.service

import io.reactivex.Observable
import mengh.zy.dm.data.protocol.IndexBean

/**
 * @author by mengh
 * @email menghedianmo@163.com
 * @date on 2018/8/10$.
 * PS: Not easy to write code, please indicate.
 */
interface IndexService {
    fun getIndex(page: Int,count: Int): Observable<IndexBean>
}