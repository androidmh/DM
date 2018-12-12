package mengh.zy.base.ui.activity

import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.content.Intent
import android.view.View
import com.blankj.utilcode.constant.PermissionConstants
import com.blankj.utilcode.util.PermissionUtils
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_photo_view.*
import mengh.zy.base.R
import mengh.zy.base.common.BaseApplication.Companion.context
import mengh.zy.base.common.BaseConstant.Companion.IMAGE_URL_KEY
import mengh.zy.base.ext.loadDiskUrl
import mengh.zy.base.ext.onClick
import mengh.zy.base.utils.DMUtils
import mengh.zy.base.utils.GlideUtils
import mengh.zy.base.utils.MaterialDialogUtils
import org.jetbrains.anko.doAsync

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
                checkStoragePermission()
            }
        }
    }

    private fun checkStoragePermission() {
        val granted = PermissionUtils.isGranted(WRITE_EXTERNAL_STORAGE)
        if (granted) {
            savePhoto()
        } else {
            PermissionUtils.permission(PermissionConstants.STORAGE).callback(object : PermissionUtils.FullCallback {
                override fun onGranted(permissionsGranted: MutableList<String>?) {
                    savePhoto()
                }
                override fun onDenied(permissionsDeniedForever: MutableList<String>, permissionsDenied: MutableList<String>?) {
                    if (permissionsDeniedForever.size != 0) {
                        MaterialDialogUtils
                                .getConfirmDialog(context, "提示", "请在设置-应用-DM-权限管理中开启以下权限:\n-写入外部存储权限\n-读取外部存储空间\n\n否则无法正常使用该功能")
                                .positiveButton {
                                    PermissionUtils.launchAppDetailsSettings()
                                }
                                .show()
                    }
                }
            }).request()
        }
    }

    private fun savePhoto() {
        doAsync {
            val downloadDisk = GlideUtils.downloadDisk(url)
            runOnUiThread {
                if (downloadDisk) {
                    Snackbar.make(photoRl, "图片已保存", Snackbar.LENGTH_LONG)
                            .setAction("打开") {
                                val intent = Intent(Intent.ACTION_VIEW)
                                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                                intent.setDataAndType(DMUtils.uri, "image/*")
                                startActivity(intent)
                            }
                            .show()
                } else {
                    Snackbar.make(photoRl, "保存失败", Snackbar.LENGTH_SHORT)
                            .show()
                }
            }
        }
    }

}
