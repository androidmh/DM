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
            val fileName = TimeUtils.getNowMills().toString() + ".jpg"
            val futureStudioIconFile = File(getDir() , fileName)

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
                sMediaScannerConnection = MediaScannerConnection(context,object : MediaScannerConnection.MediaScannerConnectionClient{
                    override fun onMediaScannerConnected() {
                        Log.e("dmdm","onMediaScannerConnected")
                        sMediaScannerConnection?.scanFile(futureStudioIconFile.toString(),"image/jpeg")
                    }
                    override fun onScanCompleted(path: String?, uri: Uri?) {
                        Log.e("dmdm","onScanCompleted$path$uri")
                        sMediaScannerConnection?.disconnect()
                    }
                })
                sMediaScannerConnection.connect()
            }
        } catch (e: IOException) {
            return false
        }
    }

    private fun getDir(): String {
        val mainPath = Environment.getExternalStorageDirectory().path
        val dir = "$mainPath/HDM/Media/DMImage"
        val tmpFile = File(dir)
        if (!tmpFile.exists()) {
            tmpFile.mkdirs()
        }
        return dir
    }
}
