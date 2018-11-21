package mengh.zy.user.data.repository

import io.reactivex.Observable
import mengh.zy.user.data.api.UserApi
import mengh.zy.base.data.net.RetrofitFactory
import mengh.zy.base.data.protocol.BaseResp
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

class UserRepository @Inject constructor() {

    fun register(req: RegisterReq): Observable<BaseResp<Any>> {
        return RetrofitFactory.instance.create(UserApi::class.java)
                .register(req)
    }

    fun getToken(req: GetTokenReq): Observable<BaseResp<LoginTokenBean>> {
        return RetrofitFactory.instance.create(UserApi::class.java)
                .getToken(req)
    }

    fun getUser(): Observable<BaseResp<UserInfoBean>> {
        return RetrofitFactory.instance.create(UserApi::class.java)
                .getUser()
    }

    fun updateUser(req: UpdateUserReq): Observable<BaseResp<Any>> {
        return RetrofitFactory.instance.create(UserApi::class.java)
                .updateUser(req)
    }

    fun updateAvatar(part: MultipartBody.Part): Observable<BaseResp<Any>>{
        return RetrofitFactory.instance.create(UserApi::class.java)
                .updateAvatar(part)
    }

    fun updateBack(part: MultipartBody.Part): Observable<BaseResp<Any>>{
        return RetrofitFactory.instance.create(UserApi::class.java)
                .updateBack(part)
    }

    fun getCollectImage(page:Int): Observable<BaseResp<CollectImgBean>>{
        return RetrofitFactory.instance.create(UserApi::class.java)
                .getCollectImage(page)
    }

    fun deleteCollect(media_id: Int): Observable<BaseResp<Any>>{
        return RetrofitFactory.instance.create(UserApi::class.java)
                .deleteCollect(media_id)
    }
}