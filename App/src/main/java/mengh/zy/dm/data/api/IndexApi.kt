package mengh.zy.dm.data.api

import io.reactivex.Observable
import mengh.zy.base.data.protocol.BaseResp
import mengh.zy.dm.data.protocol.IndexBean
import mengh.zy.user.data.protocol.*
import okhttp3.MultipartBody
import retrofit2.http.*

/**
 * @author by mengh
 * @email menghedianmo@163.com
 * @date on 2018/8/10.
 * PS: Not easy to write code, please indicate.
 *
 *   Describe:UserCenter网络请求
 */

interface IndexApi {
    /**
     * 获取userInfo
     */
    @GET("index")
    fun getIndex(): Observable<BaseResp<IndexBean>>
}