package mengh.zy.base.widgets

import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.fragment.app.DialogFragment
import com.blankj.utilcode.util.PermissionUtils
import mengh.zy.base.R
import mengh.zy.base.ext.loadUrl
import mengh.zy.base.ext.onClick
import mengh.zy.base.utils.MaterialDialogUtils
import com.blankj.utilcode.constant.PermissionConstants
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.dialog_img.*
import me.jessyan.progressmanager.ProgressListener
import me.jessyan.progressmanager.ProgressManager
import me.jessyan.progressmanager.body.ProgressInfo
import mengh.zy.base.data.api.DownloadApi
import mengh.zy.base.data.net.RetrofitFactory
import org.jetbrains.anko.*
import mengh.zy.base.utils.DMUtils
import okhttp3.ResponseBody
import org.jetbrains.anko.support.v4.toast
import java.lang.Exception


@SuppressLint("ValidFragment")
/**
 * Created by HMH on 2017/8/17.
 */

class ImgDialogFragment(private var url: String) : DialogFragment() {
    lateinit var mProgressBar:ProgressBar
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
        val v = inflater.inflate(R.layout.dialog_img, ll_iv_dialog)
        val img = v.find<ImageView>(R.id.imgIv)
        val downloadIv = v.find<ImageView>(R.id.downloadIv)
        mProgressBar = v.find(R.id.mProgressBar)
        img.loadUrl(url)
        img.onClick {
            this.dismiss()
        }
        downloadIv.onClick {
            val granted = PermissionUtils.isGranted(WRITE_EXTERNAL_STORAGE)
            if (granted) {
                getImg()
            } else {
                PermissionUtils.permission(PermissionConstants.STORAGE).callback(object : PermissionUtils.FullCallback {
                    override fun onGranted(permissionsGranted: MutableList<String>?) {
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
        return v
    }

    private fun getImg() {
        ProgressManager.getInstance().addResponseListener(url, object : ProgressListener{
            override fun onProgress(progressInfo: ProgressInfo) {
                mProgressBar.progress = progressInfo.percent
                Log.e("dmdm","${progressInfo.percent}")
            }

            override fun onError(id: Long, e: Exception?) {
            }
        })
        RetrofitFactory.instance
                .create(DownloadApi::class.java,true)
                .downloadImage(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<ResponseBody> {
                    override fun onComplete() {
                    }

                    override fun onSubscribe(d: Disposable) {
                    }
                    override fun onNext(t: ResponseBody) {
                        toast("下载成功")
                        DMUtils.writeResponseBodyToDisk(t,activity)
                    }

                    override fun onError(e: Throwable) {
                        toast("下载失败")
                    }
                })
    }
}
