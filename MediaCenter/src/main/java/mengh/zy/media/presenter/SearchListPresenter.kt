package mengh.zy.media.presenter

import mengh.zy.base.ext.execute
import mengh.zy.base.presenter.BasePresenter
import mengh.zy.base.rx.BaseSubscriber
import mengh.zy.media.data.protocol.ImageBean
import mengh.zy.media.presenter.view.SearchListView
import mengh.zy.media.service.ImageService
import javax.inject.Inject

/**
 * @author by mengh
 * @email menghedianmo@163.com
 * @date on 2018/11/9$.
 * PS: Not easy to write code, please indicate.
 *
 *   Describe:
 */
open class SearchListPresenter @Inject constructor() : BasePresenter<SearchListView>() {
    @Inject
    lateinit var indexService: ImageService

    fun getImage(req: Map<String,String>){
        if (!checkNetWork()) {
            return
        }
        indexService.getImage(req)
                .execute(object : BaseSubscriber<ImageBean>(mView) {
                    override fun onNext(t: ImageBean) {
                        mView.onGetImgResult(t)
                    }
                }, lifecycleProvider)
    }

    fun getNextPage(req: Map<String,String>){
        if (!checkNetWork()) {
            return
        }
        indexService.getImage(req)
                .execute(object : BaseSubscriber<ImageBean>(mView) {
                    override fun onNext(t: ImageBean) {
                        mView.onLoadMoreResult(t)
                    }
                }, lifecycleProvider)
    }

    fun setCollect(media_id: Int,isCollect:Boolean){
        if (!checkNetWork()) {
            return
        }
        if (isCollect){
            indexService.addCollect(media_id)
                    .execute(object : BaseSubscriber<String>(mView) {
                        override fun onNext(t: String) {
                            mView.onCollectResult(t)
                        }
                    }, lifecycleProvider)
        }
        else{
            indexService.deleteCollect(media_id)
                    .execute(object : BaseSubscriber<String>(mView) {
                        override fun onNext(t: String) {
                            mView.onCollectResult(t)
                        }
                    }, lifecycleProvider)
        }
    }
}