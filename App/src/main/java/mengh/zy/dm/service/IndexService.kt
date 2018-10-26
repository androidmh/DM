package mengh.zy.dm.service

import io.reactivex.Observable
import mengh.zy.base.data.protocol.BaseResp
import mengh.zy.dm.data.protocol.IndexBean
import mengh.zy.user.data.protocol.*
import okhttp3.MultipartBody

/**
 * @author by mengh
 * @email menghedianmo@163.com
 * @date on 2018/8/10$.
 * PS: Not easy to write code, please indicate.
 */
interface IndexService {
    fun getIndex(): Observable<IndexBean>
}