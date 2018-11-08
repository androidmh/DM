package mengh.zy.user.service.impl

import io.reactivex.Observable
import mengh.zy.base.ext.convert
import mengh.zy.user.data.repository.UserRepository
import mengh.zy.user.service.UserService
import mengh.zy.base.ext.convertMsg
import mengh.zy.user.data.protocol.*
import okhttp3.MultipartBody
import javax.inject.Inject

/**
 * @author by mengh
 * @email menghedianmo@163.com
 * @date on 2018/8/10.
 * PS: Not easy to write code, please indicate.
 *
 *   Describe:请求服务类，调起请求并返回值的类
 */

class UserServiceImpl @Inject constructor() : UserService {
    @Inject
    lateinit var repository: UserRepository

    override fun register(req: RegisterReq): Observable<String> {
        return repository.register(req)
                .convertMsg()
    }

    override fun getToken(req: GetTokenReq): Observable<LoginTokenBean> {
        return repository.getToken(req)
                .convert()
    }

    override fun getUser(): Observable<UserInfoBean> {
        return repository.getUser()
                .convert()
    }

    override fun updateUser(req: UpdateUserReq): Observable<String> {
        return repository.updateUser(req)
                .convertMsg()
    }

    override fun updateAvatar(part: MultipartBody.Part): Observable<String> {
        return repository.updateAvatar(part)
                .convertMsg()
    }

    override fun updateBack(part: MultipartBody.Part): Observable<String> {
        return repository.updateBack(part)
                .convertMsg()
    }
}
