package mengh.zy.base.ui.activity

import android.view.View
import kotlinx.android.synthetic.main.activity_photo_view.*
import mengh.zy.base.R
import mengh.zy.base.common.BaseConstant.Companion.IMAGE_URL_KEY
import mengh.zy.base.ext.loadDiskUrl
import mengh.zy.base.ext.onClick
import mengh.zy.base.utils.GlideUtils
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast

class PhotoViewActivity : BaseActivity() {
    override val layoutId: Int
        get() = R.layout.activity_photo_view

    private lateinit var url: String

    override fun initView() {
        url = intent.getStringExtra(IMAGE_URL_KEY)
        photoView.loadDiskUrl(url)
        photoView.onClick(this)
        saveIv.onClick(this)
    }

    override fun widgetClick(v: View) {
        when (v) {
            photoView -> {
                finish()
            }
            saveIv -> {
                doAsync {
                    val downloadDisk = GlideUtils.downloadDisk(url)
                    runOnUiThread {
                        if (downloadDisk) {
                            toast("图片已保存至/storage/emulated/0/Pictures/HDM/dmDownload")
                        } else {
                            toast("保存失败")
                        }
                    }
                }
            }
        }
    }

}
