package mengh.zy.user.presenter

import mengh.zy.base.ext.execute
import mengh.zy.base.presenter.BasePresenter
import mengh.zy.base.rx.BaseSubscriber
import mengh.zy.user.data.protocol.GetTokenReq
import mengh.zy.user.data.protocol.LoginTokenBean
import mengh.zy.user.data.protocol.UserInfoBean
import mengh.zy.user.presenter.view.LoginView
import mengh.zy.user.service.UserService
import javax.inject.Inject

/**
 * @author by mengh
 * @email menghedianmo@163.com
 * @date on 2018/8/10$.
 * PS: Not easy to write code, please indicate.
 */
open class LoginPresenter @Inject constructor() : BasePresenter<LoginView>() {

    @Inject
    lateinit var userService: UserService

    fun getToken(req: GetTokenReq) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        userService.getToken(req)
                .execute(object : BaseSubscriber<LoginTokenBean>(mView) {
                    override fun onNext(t: LoginTokenBean) {
                        mView.onLoginResult(t.token)
                    }
                }, lifecycleProvider)
    }

    fun getUser() {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        userService.getUser()
                .execute(object : BaseSubscriber<UserInfoBean>(mView) {
                    override fun onNext(t: UserInfoBean) {
                        mView.onGetUserResult(t)
                    }
                }, lifecycleProvider)
    }

}