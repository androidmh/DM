package mengh.zy.base.utils

import android.net.Uri
import com.bilibili.boxing.Boxing
import com.bilibili.boxing.model.config.BoxingConfig
import com.bilibili.boxing.model.config.BoxingCropOption
import com.bilibili.boxing.utils.BoxingFileHelper
import mengh.zy.base.R
import mengh.zy.base.ui.activity.BaseActivity
import mengh.zy.base.ui.fragment.BaseFragment
import mengh.zy.base.widgets.DMBoxingActivity
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
                .withAlbumPlaceHolderRes(R.mipmap.image)
                .withMediaPlaceHolderRes(R.mipmap.image)
                .needCamera(R.drawable.ic_boxing_camera_white)
        Boxing.of(singleCropImgConfig).withIntent(activity, DMBoxingActivity::class.java).start(activity, code)
    }

    fun selectSingleCutImg(fragment: BaseFragment, code: Int) {
        val cachePath = BoxingFileHelper.getCacheDir(fragment.activity!!)
        val destUri = Uri.Builder()
                .scheme("file")
                .appendPath(cachePath)
                .appendPath(String.format(Locale.US, "%s.png", System.currentTimeMillis()))
                .build()
        val singleCropImgConfig = BoxingConfig(BoxingConfig.Mode.SINGLE_IMG)
                .withCropOption(BoxingCropOption(destUri))
                .withMediaPlaceHolderRes(R.mipmap.image)
                .withAlbumPlaceHolderRes(R.mipmap.image)
                .needCamera(R.drawable.ic_boxing_camera_white)
        Boxing.of(singleCropImgConfig).withIntent(fragment.activity!!, DMBoxingActivity::class.java).start(fragment, code)
    }
}
