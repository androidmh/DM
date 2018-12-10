package mengh.zy.media.data.protocol

import okhttp3.MultipartBody

/**
 * Created by HDM on 2018/1/11.
 * E-mail menghedianmo@163.com
 * author HDM
 */

data class UploadImgReq(val img: MultipartBody.Part, val sort: String, val describe: String, val label: String = "1,2")
