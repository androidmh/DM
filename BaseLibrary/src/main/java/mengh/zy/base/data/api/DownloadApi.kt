package mengh.zy.base.data.api

import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Streaming
import retrofit2.http.Url

/**
 * @author by mengh
 * @email menghedianmo@163.com
 * @date on 2018/11/21$.
 * PS: Not easy to write code, please indicate.
 *
 *   Describe:
 */
interface DownloadApi {
    @GET
    @Streaming
    fun downloadImage(@Url url:String): Observable<ResponseBody>
}