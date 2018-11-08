package mengh.zy.user.presenter

import mengh.zy.base.ext.execute
import mengh.zy.base.presenter.BasePresenter
import mengh.zy.base.rx.BaseSubscriber
import mengh.zy.user.presenter.view.UserView
import mengh.zy.user.service.UserService
import okhttp3.MultipartBody
import javax.inject.Inject

/**
 * @author by mengh
 * @email menghedianmo@163.com
 * @date on 2018/8/10$.
 * PS: Not easy to write code, please indicate.
 */
open class UserPresenter @Inject constructor() : BasePresenter<UserView>() {

    @Inject
    lateinit var userService: UserService

    fun updateBack(part: MultipartBody.Part) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        userService.updateBack(part)
                .execute(object : BaseSubscriber<String>(mView) {
                    override fun onNext(t: String) {
                        mView.onUpdateBackResult(t)
                    }
                }, lifecycleProvider)
    }
}