package mengh.zy.base.utils

import android.net.Uri
import com.bilibili.boxing.Boxing
import com.bilibili.boxing.model.config.BoxingConfig
import com.bilibili.boxing.model.config.BoxingCropOption
import com.bilibili.boxing.utils.BoxingFileHelper
import com.bilibili.boxing_impl.ui.BoxingActivity
import mengh.zy.base.R
import mengh.zy.base.ui.activity.BaseActivity
import java.util.*

/**
 * @author by mengh
 * @email menghedianmo@163.com
 * @date on 2018/10/18$.
 * PS: Not easy to write code, please indicate.
 *
 *   Describe:
 */
object BoxingUtils {
    fun selectSingleCutImg(activity: BaseActivity, code: Int) {
        val cachePath = BoxingFileHelper.getCacheDir(activity)
        val destUri = Uri.Builder()
                .scheme("file")
                .appendPath(cachePath)
                .appendPath(String.format(Locale.US, "%s.png", System.currentTimeMillis()))
                .build()
        val singleCropImgConfig = BoxingConfig(BoxingConfig.Mode.SINGLE_IMG)
                .withCropOption(BoxingCropOption(destUri))
                .withMediaPlaceHolderRes(R.mipmap.image)
                .needCamera(R.drawable.ic_boxing_camera_white)
        Boxing.of(singleCropImgConfig).withIntent(activity, BoxingActivity::class.java).start(activity, code)
    }

    fun selectSingleImg(activity: BaseActivity, code: Int) {
        val singleImgConfig = BoxingConfig(BoxingConfig.Mode.SINGLE_IMG).withMediaPlaceHolderRes(R.mipmap.image)
        Boxing.of(singleImgConfig).withIntent(activity, BoxingActivity::class.java).start(activity, code)
    }
}
