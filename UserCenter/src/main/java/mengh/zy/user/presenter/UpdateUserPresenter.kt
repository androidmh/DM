package mengh.zy.user.presenter

import mengh.zy.base.ext.execute
import mengh.zy.base.presenter.BasePresenter
import mengh.zy.base.rx.BaseSubscriber
import mengh.zy.user.data.protocol.GetTokenReq
import mengh.zy.user.data.protocol.LoginTokenBean
import mengh.zy.user.data.protocol.UpdateUserReq
import mengh.zy.user.presenter.view.UpdateUserView
import mengh.zy.user.service.UserService
import okhttp3.MultipartBody
import javax.inject.Inject

/**
 * @author by mengh
 * @email menghedianmo@163.com
 * @date on 2018/8/10$.
 * PS: Not easy to write code, please indicate.
 */
open class UpdateUserPresenter @Inject constructor() : BasePresenter<UpdateUserView>() {

    @Inject
    lateinit var userService: UserService

    fun updateUser(req: UpdateUserReq) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        userService.updateUser(req)
                .execute(object : BaseSubscriber<String>(mView) {
                    override fun onNext(t: String) {
                        mView.onUpdateUserResult(t)
                    }
                }, lifecycleProvider)
    }

    fun updateAvatar(part: MultipartBody.Part) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        userService.updateAvatar(part)
                .execute(object : BaseSubscriber<String>(mView) {
                    override fun onNext(t: String) {
                        mView.onUpdateAvatarResult(t)
                    }
                }, lifecycleProvider)
    }
}