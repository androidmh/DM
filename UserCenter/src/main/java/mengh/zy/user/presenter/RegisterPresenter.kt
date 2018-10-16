package mengh.zy.user.presenter

import mengh.zy.base.ext.execute
import mengh.zy.base.presenter.BasePresenter
import mengh.zy.base.rx.BaseSubscriber
import mengh.zy.user.data.protocol.RegisterReq
import mengh.zy.user.presenter.view.RegisterView
import mengh.zy.user.service.UserService
import javax.inject.Inject

/**
 * @author by mengh
 * @email menghedianmo@163.com
 * @date on 2018/8/10$.
 * PS: Not easy to write code, please indicate.
 */
open class RegisterPresenter @Inject constructor() : BasePresenter<RegisterView>() {

    @Inject
    lateinit var userService: UserService

    fun register(req: RegisterReq) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        userService.register(req)
                .execute(object : BaseSubscriber<String>(mView) {
                    override fun onNext(t: String) {
                        mView.onRegisterResult(t)
                    }
                }, lifecycleProvider)
    }

}