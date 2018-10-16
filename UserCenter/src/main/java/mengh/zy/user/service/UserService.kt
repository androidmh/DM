package mengh.zy.user.service

import io.reactivex.Observable
import mengh.zy.user.data.protocol.GetTokenReq
import mengh.zy.user.data.protocol.LoginTokenBean
import mengh.zy.user.data.protocol.RegisterReq
import mengh.zy.user.data.protocol.UserInfoBean

/**
 * @author by mengh
 * @email menghedianmo@163.com
 * @date on 2018/8/10$.
 * PS: Not easy to write code, please indicate.
 */
interface UserService {

    fun register(req: RegisterReq): Observable<String>

    fun getToken(req: GetTokenReq): Observable<LoginTokenBean>

    fun getUser(): Observable<UserInfoBean>
}