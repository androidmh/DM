package mengh.zy.dm.data.repository

import io.reactivex.Observable
import mengh.zy.user.data.api.UserApi
import mengh.zy.base.data.net.RetrofitFactory
import mengh.zy.base.data.protocol.BaseResp
import mengh.zy.dm.data.api.IndexApi
import mengh.zy.dm.data.protocol.IndexBean
import mengh.zy.user.data.protocol.*
import okhttp3.MultipartBody
import javax.inject.Inject

/**
 * @author by mengh
 * @email menghedianmo@163.com
 * @date on 2018/8/10.
 * PS: Not easy to write code, please indicate.
 *
 *   Describe:数据网络请求的封装(整个注册流程的封装)
 */

class IndexRepository @Inject constructor() {

    fun getIndex(): Observable<BaseResp<IndexBean>> {
        return RetrofitFactory.instance.create(IndexApi::class.java)
                .getIndex()
    }
}