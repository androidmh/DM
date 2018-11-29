package mengh.zy.base.widgets.presenter

import android.widget.ProgressBar
import mengh.zy.base.data.api.DownloadApi
import mengh.zy.base.data.net.RetrofitFactory
import mengh.zy.base.ext.executeDown
import mengh.zy.base.ext.setProgress
import mengh.zy.base.presenter.BaseDownloadPresenter
import mengh.zy.base.presenter.BasePresenter
import mengh.zy.base.rx.BaseDownloadSubscriber
import mengh.zy.base.widgets.presenter.view.DownloadImgView
import okhttp3.ResponseBody

/**
 * @author by mengh
 * @email menghedianmo@163.com
 * @date on 2018/11/27$.
 * PS: Not easy to write code, please indicate.
 *
 *   Describe:
 */
class DownloadImgPresenter : BaseDownloadPresenter<DownloadImgView>() {
    fun getImg(url:String,progressBar: ProgressBar){
        if (!checkNetWork()) {
            return
        }
        progressBar.setProgress(url)
        RetrofitFactory.instance
                .create(DownloadApi::class.java,true)
                .downloadImage(url)
                .executeDown(object : BaseDownloadSubscriber<ResponseBody>(mView) {
                    override fun onNext(t: ResponseBody) {
                        mView.onGetImgSuccess(t)
                    }
                })
    }
}