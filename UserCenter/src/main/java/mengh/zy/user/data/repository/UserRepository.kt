package mengh.zy.user.data.repository

import io.reactivex.Observable
import mengh.zy.user.data.api.UserApi
import mengh.zy.base.data.net.RetrofitFactory
import mengh.zy.base.data.protocol.BaseResp
import mengh.zy.user.data.protocol.GetTokenReq
import mengh.zy.user.data.protocol.LoginTokenBean
import mengh.zy.user.data.protocol.RegisterReq
import mengh.zy.user.data.protocol.UserInfoBean
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
}