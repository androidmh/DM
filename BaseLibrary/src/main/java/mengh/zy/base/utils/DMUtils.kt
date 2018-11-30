package mengh.zy.base.utils

import android.content.*
import android.widget.EditText
import okhttp3.ResponseBody
import java.io.*
import java.util.*
import android.media.MediaScannerConnection
import android.os.Environment
import android.util.Log
import androidx.exifinterface.media.ExifInterface
import com.blankj.utilcode.util.TimeUtils
import java.text.SimpleDateFormat
import android.net.Uri
import androidx.core.content.FileProvider


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
            val futureStudioIconFile = File(Environment.getExternalStorageDirectory(), "${getDMDir()}$fileName")

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

                var sMediaScannerConnection: MediaScannerConnection? = null
                sMediaScannerConnection = MediaScannerConnection(context, object : MediaScannerConnection.MediaScannerConnectionClient {
                    override fun onMediaScannerConnected() {
                        Log.e("dmdm", "onMediaScannerConnected")
                        sMediaScannerConnection?.scanFile(futureStudioIconFile.toString(), "image/jpeg")
                    }

                    override fun onScanCompleted(path: String?, uri: Uri?) {
                        Log.e("dmdm", "onScanCompleted$path$uri")
                        context?.sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,uri))
                        sMediaScannerConnection?.disconnect()
                    }
                })
                sMediaScannerConnection.connect()
            }
        } catch (e: IOException) {
            return false
        }
    }

    private fun getDMDir():String{
        val tmpFile = File(Environment.getExternalStorageDirectory(),"HDM/Media/DMImage/")
        if (!tmpFile.exists()) {
            tmpFile.mkdirs()
        }
        return "HDM/Media/DMImage/"
    }
}
