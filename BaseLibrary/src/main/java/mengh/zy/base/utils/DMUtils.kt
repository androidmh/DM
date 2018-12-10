package mengh.zy.base.utils

import android.content.*
import android.widget.EditText
import okhttp3.ResponseBody
import java.io.*
import java.util.*
import android.os.Environment
import androidx.exifinterface.media.ExifInterface
import com.blankj.utilcode.util.TimeUtils
import java.text.SimpleDateFormat
import android.net.Uri
import android.os.Environment.DIRECTORY_PICTURES
import okhttp3.MediaType
import okhttp3.RequestBody


/**
 * Created by HMH on 2017/7/31.
 */

object DMUtils {

    fun isBtnEnableOfEt(vararg editTexts: EditText): Boolean {
        for (editText in editTexts) {
            if (editText.text.isNullOrEmpty()) {
                return false
            }
        }
        return true
    }

    /**
     * 将文件写入本地
     */
    fun writeResponseBodyToDisk(body: ResponseBody, context: Context?): Boolean {
        try {
            val nowMills1 = TimeUtils.getNowMills()
            val fileName = nowMills1.toString() + ".jpg"
            val futureStudioIconFile = File(getDMDir(), fileName)

            var inputStream: InputStream? = null
            var outputStream: FileOutputStream? = null

            try {
                val fileReader = ByteArray(4096)

                var fileSizeDownloaded: Long = 0

                inputStream = body.byteStream()
                outputStream = FileOutputStream(futureStudioIconFile)

                while (true) {
                    val read = inputStream!!.read(fileReader)

                    if (read == -1) {
                        break
                    }

                    outputStream.write(fileReader, 0, read)

                    fileSizeDownloaded += read.toLong()
                }

                outputStream.flush()

                return true
            } catch (e: IOException) {
                return false
            } finally {
                inputStream?.close()

                outputStream?.close()
                //更改文件时间
                val exifInterface = ExifInterface(futureStudioIconFile.absolutePath)
                val dateFormat = SimpleDateFormat("yyyy:MM:dd HH:mm:ss", Locale.getDefault())
                val nowMills = TimeUtils.getNowMills()
                val format = dateFormat.format(nowMills)
                exifInterface.setAttribute(ExifInterface.TAG_DATETIME_ORIGINAL, format)
                exifInterface.saveAttributes()

                context?.sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,Uri.fromFile(futureStudioIconFile)))
//                var sMediaScannerConnection: MediaScannerConnection? = null
//                sMediaScannerConnection = MediaScannerConnection(context, object : MediaScannerConnection.MediaScannerConnectionClient {
//                    override fun onMediaScannerConnected() {
//                        sMediaScannerConnection?.scanFile(futureStudioIconFile.toString(), "image/jpeg")
//                    }
//
//                    override fun onScanCompleted(path: String?, uri: Uri?) {
//                        context?.sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,uri))
//                        sMediaScannerConnection?.disconnect()
//                    }
//                })
//                sMediaScannerConnection.connect()
            }
        } catch (e: IOException) {
            return false
        }
    }

    private fun getDMDir():String{
        val tmpFile = File(Environment.getExternalStoragePublicDirectory(DIRECTORY_PICTURES),"HDM/dmDownload")
        if (!tmpFile.exists()) {
            tmpFile.mkdirs()
        }
        return tmpFile.absolutePath
    }

    /**
     * string转RequestBody解决@part带双引号
     */
    fun stringToRequestBody(string: String):RequestBody {
       return RequestBody.create(MediaType.parse("text/plain"), string)
   }
}
