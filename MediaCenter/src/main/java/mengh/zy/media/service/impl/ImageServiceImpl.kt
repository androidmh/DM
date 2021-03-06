package mengh.zy.media.service.impl

import io.reactivex.Observable
import mengh.zy.base.ext.convert
import mengh.zy.base.ext.convertMsg
import mengh.zy.media.data.protocol.ImageBean
import mengh.zy.media.data.repository.ImageRepository
import mengh.zy.media.service.ImageService
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

/**
 * @author by mengh
 * @email menghedianmo@163.com
 * @date on 2018/8/10.
 * PS: Not easy to write code, please indicate.
 *
 *   Describe:请求服务类，调起请求并返回值的类
 */

class ImageServiceImpl @Inject constructor() : ImageService {
    @Inject
    lateinit var repository: ImageRepository

    override fun getImage(req: Map<String, String>): Observable<ImageBean> {
        return repository.getImage(req).convert()
    }

    override fun addCollect(media_id: Int): Observable<String> {
        return repository.addCollect(media_id).convertMsg()
    }

    override fun deleteCollect(media_id: Int): Observable<String> {
        return repository.deleteCollect(media_id).convertMsg()
    }

    override fun uploadImage(img: MultipartBody.Part, map: MutableMap<String, RequestBody>): Observable<String> {
        return repository.uploadImage(img, map).convertMsg()
    }

}
