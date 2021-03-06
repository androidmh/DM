package mengh.zy.user.data.api

import io.reactivex.Observable
import mengh.zy.base.data.protocol.BaseResp
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

interface UserApi {
    /**
     * 注册
     */
    @POST("client/register")
    fun register(@Body req: RegisterReq): Observable<BaseResp<Any>>

    /**
     * 获取token
     */
    @POST("token")
    fun getToken(@Body req: GetTokenReq): Observable<BaseResp<LoginTokenBean>>

    /**
     * 获取userInfo
     */
    @GET("user")
    fun getUser(): Observable<BaseResp<UserInfoBean>>

    /**
     * 更新userInfo
     */
    @POST("user")
    fun updateUser(@Body req: UpdateUserReq): Observable<BaseResp<Any>>

    /**
     * 更新头像
     */
    @Multipart
    @POST("avatar")
    fun updateAvatar(@Part avatar: MultipartBody.Part): Observable<BaseResp<Any>>

    /**
     * 更新背景图
     */
    @Multipart
    @POST("avatar/back")
    fun updateBack(@Part back: MultipartBody.Part): Observable<BaseResp<Any>>

    /**
     * 获取收藏图片列表
     */
    @GET("collect/image")
    fun getCollectImage(@Query("page")page:Int): Observable<BaseResp<CollectImgBean>>

    /**
     *  删除收藏图片
     */
    @DELETE("collect/image")
    fun deleteCollect(@Query("media_id") media_id: Int): Observable<BaseResp<Any>>
}