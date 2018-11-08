package mengh.zy.dm.presenter

import mengh.zy.base.ext.execute
import mengh.zy.base.presenter.BasePresenter
import mengh.zy.base.rx.BaseSubscriber
import mengh.zy.dm.data.protocol.IndexBean
import mengh.zy.dm.presenter.view.IndexView
import mengh.zy.dm.service.IndexService
import javax.inject.Inject

/**
 * @author by mengh
 * @email menghedianmo@163.com
 * @date on 2018/8/10$.
 * PS: Not easy to write code, please indicate.
 */
open class IndexPresenter @Inject constructor() : BasePresenter<IndexView>() {

    @Inject
    lateinit var indexService: IndexService

    fun getIndex(page: Int, count: Int = 8) {
        if (!checkNetWork()) {
            return
        }
        indexService.getIndex(page, count)
                .execute(object : BaseSubscriber<IndexBean>(mView) {
                    override fun onNext(t: IndexBean) {
                        mView.onGetIndexResult(t)
                    }
                }, lifecycleProvider)
    }

    fun getNextPage(page: Int, count: Int = 8) {
        if (!checkNetWork()) {
            return
        }
        indexService.getIndex(page, count)
                .execute(object : BaseSubscriber<IndexBean>(mView) {
                    override fun onNext(t: IndexBean) {
                        mView.onLoadMoreResult(t)
                    }
                }, lifecycleProvider)
    }

}