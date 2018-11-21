package mengh.zy.media.service

import io.reactivex.Observable
import mengh.zy.media.data.protocol.ImageBean


/**
 * @author by mengh
 * @email menghedianmo@163.com
 * @date on 2018/8/10$.
 * PS: Not easy to write code, please indicate.
 */
interface ImageService {
    fun getImage(req: Map<String,String>): Observable<ImageBean>

    fun addCollect(media_id: Int): Observable<String>

    fun deleteCollect(media_id: Int): Observable<String>
}