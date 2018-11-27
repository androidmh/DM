package mengh.zy.base.utils

import android.content.*
import android.util.Log
import android.widget.EditText
import okhttp3.ResponseBody
import java.io.*
import java.util.*
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import androidx.exifinterface.media.ExifInterface
import com.blankj.utilcode.util.FileUtils
import com.blankj.utilcode.util.TimeUtils
import java.text.SimpleDateFormat


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
            val futureStudioIconFile = File(getDir() + File.separator + UUID.randomUUID().toString() + ".jpg")

            var inputStream: InputStream? = null
            var outputStream: OutputStream? = null

            try {
                val fileReader = ByteArray(4096)

                val fileSize = body.contentLength()
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

                    Log.d("dmdm", "file download: $fileSizeDownloaded of $fileSize")
                }

                outputStream.flush()

                return true
            } catch (e: IOException) {
                return false
            } finally {
                inputStream?.close()

                outputStream?.close()
                val fileLastModified = (FileUtils.getFileLastModified(futureStudioIconFile) / 1000).toInt()
                val exifInterface = ExifInterface(futureStudioIconFile.absolutePath)
                val dateFormat = SimpleDateFormat("yyyy:MM:dd hh:mm:ss", Locale.getDefault())
                val format = dateFormat.format(TimeUtils.getNowMills())
                exifInterface.setAttribute(ExifInterface.TAG_DATETIME_ORIGINAL, format)
                exifInterface.saveAttributes()

                val values = ContentValues()
                values.put(MediaStore.Images.Media.DATA, futureStudioIconFile.absolutePath)
                values.put(MediaStore.Images.Media.DESCRIPTION, "来自dm的图片")
                values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
                val insert = context?.contentResolver?.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
                val intent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(futureStudioIconFile))
                context?.sendBroadcast(intent)
            }
        } catch (e: IOException) {
            return false
        }

    }

    private fun getDir(): String {
        val dir = Environment.getExternalStorageDirectory().absolutePath+"/mengh.zy.dm/Media/DMImage"
        val tmpFile = File(dir)
        if (!tmpFile.exists()) {
            tmpFile.mkdirs()
        }
        return dir
    }
}
