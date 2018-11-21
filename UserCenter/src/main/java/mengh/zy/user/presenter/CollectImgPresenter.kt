package mengh.zy.user.presenter

import mengh.zy.base.ext.execute
import mengh.zy.base.presenter.BasePresenter
import mengh.zy.base.rx.BaseSubscriber
import mengh.zy.user.data.protocol.CollectImgBean
import mengh.zy.user.presenter.view.CollectImgView
import mengh.zy.user.service.UserService
import javax.inject.Inject

/**
 * @author by mengh
 * @email menghedianmo@163.com
 * @date on 2018/8/10$.
 * PS: Not easy to write code, please indicate.
 */
open class CollectImgPresenter @Inject constructor() : BasePresenter<CollectImgView>() {

    @Inject
    lateinit var userService: UserService

    fun getCollectImage(page: Int) {
        if (!checkNetWork()) {
            return
        }
        userService.getCollectImage(page)
                .execute(object : BaseSubscriber<CollectImgBean>(mView) {
                    override fun onNext(t: CollectImgBean) {
                        if (page > 0) {
                            mView.onLoadMoreCollectImgResult(t)
                        } else {
                            mView.onGetCollectImgResult(t)
                        }
                    }
                }, lifecycleProvider)
    }

    fun deleteCollect(media_id: Int) {
        if (!checkNetWork()) {
            return
        }
        userService.deleteCollect(media_id)
                .execute(object : BaseSubscriber<String>(mView) {
                    override fun onNext(t: String) {
                        mView.onCollectResult(t)
                    }
                }, lifecycleProvider)
    }
}