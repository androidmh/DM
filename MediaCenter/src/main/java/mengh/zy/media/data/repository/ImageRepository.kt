package mengh.zy.media.data.repository

import io.reactivex.Observable
import mengh.zy.base.data.net.RetrofitFactory
import mengh.zy.base.data.protocol.BaseResp
import mengh.zy.media.data.api.ImageApi
import mengh.zy.media.data.protocol.ImageBean
import mengh.zy.media.data.protocol.UploadImgReq
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Part
import retrofit2.http.PartMap
import javax.inject.Inject

/**
 * @author by mengh
 * @email menghedianmo@163.com
 * @date on 2018/11/9$.
 * PS: Not easy to write code, please indicate.
 *
 *   Describe:
 */
class ImageRepository @Inject constructor() {
    fun getImage(req: Map<String, String>): Observable<BaseResp<ImageBean>> {
        return RetrofitFactory.instance.create(ImageApi::class.java)
                .getImage(req)
    }

    fun addCollect(media_id: Int): Observable<BaseResp<Any>> {
        return RetrofitFactory.instance.create(ImageApi::class.java)
                .addCollect(media_id)
    }

    fun deleteCollect(media_id: Int): Observable<BaseResp<Any>> {
        return RetrofitFactory.instance.create(ImageApi::class.java)
                .deleteCollect(media_id)
    }

    fun uploadImage(img: MultipartBody.Part, map: MutableMap<String, RequestBody>): Observable<BaseResp<Any>> {
        return RetrofitFactory.instance.create(ImageApi::class.java)
                .uploadImage(img,map)
    }
}