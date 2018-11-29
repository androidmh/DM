package mengh.zy.base.presenter

import android.content.Context
import com.trello.rxlifecycle2.LifecycleProvider
import mengh.zy.base.presenter.view.BaseDownloadView
import mengh.zy.base.presenter.view.BaseView
import mengh.zy.base.utils.NetWorkUtils
import javax.inject.Inject

/**
 * @author by mengh
 * @email menghedianmo@163.com
 * @date on 2018/8/10.
 * PS: Not easy to write code, please indicate.
 */

open class BaseDownloadPresenter<T : BaseDownloadView> {
    lateinit var mView: T

    lateinit var context: Context

    fun checkNetWork():Boolean{
        return if (NetWorkUtils.isNetWorkAvailable(context)){
            true
        }else{
            mView.onError("网络不可用")
            false
        }
    }
}