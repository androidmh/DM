package mengh.zy.media.data.api

import io.reactivex.Observable
import mengh.zy.base.data.protocol.BaseResp
import mengh.zy.media.data.protocol.ImageBean
import retrofit2.http.*

/**
 * @author by mengh
 * @email menghedianmo@163.com
 * @date on 2018/8/10.
 * PS: Not easy to write code, please indicate.
 *
 *   Describe:UserCenter网络请求
 */

interface ImageApi {
    /**
     * 获取userInfo
     */
    @GET("image")
    fun getImage(@QueryMap req: Map<String,String>): Observable<BaseResp<ImageBean>>
}