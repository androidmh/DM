package mengh.zy.base.widgets

import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import com.blankj.utilcode.util.PermissionUtils
import mengh.zy.base.R
import mengh.zy.base.ext.onClick
import mengh.zy.base.utils.MaterialDialogUtils
import com.blankj.utilcode.constant.PermissionConstants
import kotlinx.android.synthetic.main.dialog_img.*
import mengh.zy.base.ext.loadUrl
import mengh.zy.base.ext.setVisible
import mengh.zy.base.utils.DMUtils
import mengh.zy.base.widgets.presenter.DownloadImgPresenter
import mengh.zy.base.widgets.presenter.view.DownloadImgView
import okhttp3.ResponseBody
import org.jetbrains.anko.support.v4.toast


/**
 * Created by HMH on 2017/8/17.
 */

class ImgDialogFragment : DialogFragment(),DownloadImgView{

    private lateinit var mPresenter:DownloadImgPresenter

    private lateinit var url: String

    fun putUrl(url: String){
        this.url = url
    }

    override fun onStart() {
        super.onStart()
        if (dialog == null) {
            return
        }
        dialog.window!!.setWindowAnimations(
                R.style.DialogAnimation)
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.AppTheme)
    }

    override fun onResume() {
        super.onResume()
        dialog.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        dialog.window!!.requestFeature(Window.FEATURE_NO_TITLE)
        mPresenter = DownloadImgPresenter()
        mPresenter.mView = this
        mPresenter.context = activity!!
        return inflater.inflate(R.layout.dialog_img, ll_iv_dialog)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imgIv.loadUrl(url)
        imgIv.onClick {
            this.dismiss()
        }
        downloadIv.onClick {
            downloadIv.setVisible(false)
            mProgressBar.setVisible(true)
            checkStoragePermission()
        }
    }

    private fun checkStoragePermission() {
        val granted = PermissionUtils.isGranted(WRITE_EXTERNAL_STORAGE)
        if (granted) {
            mPresenter.getImg(url,mProgressBar)
        } else {
            PermissionUtils.permission(PermissionConstants.STORAGE).callback(object : PermissionUtils.FullCallback {
                override fun onGranted(permissionsGranted: MutableList<String>?) {
                    mPresenter.getImg(url,mProgressBar)
                }

                override fun onDenied(permissionsDeniedForever: MutableList<String>, permissionsDenied: MutableList<String>?) {
                    if (permissionsDeniedForever.size != 0) {
                        MaterialDialogUtils
                                .getConfirmDialog(activity!!, "提示", "请在设置-应用-DM-权限管理中开启以下权限:\n-写入外部存储权限\n-读取外部存储空间\n\n否则无法正常使用该功能")
                                .positiveButton {
                                    PermissionUtils.launchAppDetailsSettings()
                                }
                                .show()
                    }
                }
            }).request()
        }
    }

    override fun onError(text: String) {
        toast(text)
    }

    override fun onGetImgSuccess(result: ResponseBody) {
        val writeResponseBodyToDisk = DMUtils.writeResponseBodyToDisk(result, activity!!)
        if (writeResponseBodyToDisk){
            toast("下载成功")
        }else{
            toast("下载失败")
            downloadIv.setVisible(true)
            mProgressBar.setVisible(false)
        }
    }
}
