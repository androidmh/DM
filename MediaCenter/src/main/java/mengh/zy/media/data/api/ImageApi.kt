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
    fun getImage(@QueryMap req: Map<String, String>): Observable<BaseResp<ImageBean>>

    /**
     *  添加收藏图片
     */
    @FormUrlEncoded
    @POST("collect/image")
    fun addCollect(@Field("media_id") media_id: Int): Observable<BaseResp<Any>>

    /**
     *  删除收藏图片
     */
    @DELETE("collect/image")
    fun deleteCollect(@Query("media_id") media_id: Int): Observable<BaseResp<Any>>
}