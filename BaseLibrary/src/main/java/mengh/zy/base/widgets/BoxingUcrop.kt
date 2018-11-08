package mengh.zy.base.widgets

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import com.bilibili.boxing.loader.IBoxingCrop
import com.bilibili.boxing.model.config.BoxingCropOption
import com.yalantis.ucrop.UCrop

/**
 * use Ucrop(https://github.com/Yalantis/uCrop) as the implement for [IBoxingCrop]
 *
 * @author ChenSL
 */
class BoxingUcrop : IBoxingCrop {

    override fun onStartCrop(context: Context, fragment: androidx.fragment.app.Fragment, cropConfig: BoxingCropOption,
                             path: String, requestCode: Int) {
        val uri = Uri.Builder()
                .scheme("file")
                .appendPath(path)
                .build()
        val crop = UCrop.Options()
        // do not copy exif information to crop pictures
        // because png do not have exif and png is not Distinguishable
        crop.setCompressionFormat(Bitmap.CompressFormat.PNG)
        crop.withMaxResultSize(cropConfig.maxWidth, cropConfig.maxHeight)
        crop.withAspectRatio(cropConfig.aspectRatioX, cropConfig.aspectRatioY)

        UCrop.of(uri, cropConfig.destination)
                .withOptions(crop)
                .start(context, fragment, requestCode)
    }

    override fun onCropFinish(resultCode: Int, data: Intent?): Uri? {
        if (data == null) {
            return null
        }
        val throwable = UCrop.getError(data)
        return if (throwable != null) {
            null
        } else UCrop.getOutput(data)
    }
}
