package mengh.zy.dm.data.api

import io.reactivex.Observable
import mengh.zy.base.data.protocol.BaseResp
import mengh.zy.dm.data.protocol.IndexBean
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
    fun getIndex(@Query("page") page: Int,
                 @Query("count") count: Int): Observable<BaseResp<IndexBean>>
}