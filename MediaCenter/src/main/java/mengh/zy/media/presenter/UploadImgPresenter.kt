package mengh.zy.media.presenter

import android.widget.ProgressBar
import mengh.zy.base.common.BaseConstant.Companion.SERVER_ADDRESS
import mengh.zy.base.ext.execute
import mengh.zy.base.ext.setUpProgress
import mengh.zy.base.presenter.BasePresenter
import mengh.zy.base.rx.BaseSubscriber
import mengh.zy.media.presenter.view.UploadImgView
import mengh.zy.media.service.ImageService
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

/**
 * @author by mengh
 * @email menghedianmo@163.com
 * @date on 2018/11/9$.
 * PS: Not easy to write code, please indicate.
 *
 *   Describe:
 */
open class UploadImgPresenter @Inject constructor() : BasePresenter<UploadImgView>() {
    @Inject
    lateinit var imageService: ImageService

    fun uploadImage(img: MultipartBody.Part, map: MutableMap<String, RequestBody>, upProgressBar: ProgressBar?) {
        if (!checkNetWork()) {
            return
        }
        upProgressBar?.setUpProgress(SERVER_ADDRESS+"image")
        imageService.uploadImage(img, map)
                .execute(object : BaseSubscriber<String>(mView) {
                    override fun onNext(t: String) {
                        mView.onUploadResult(t)
                    }
                }, lifecycleProvider)
    }
}