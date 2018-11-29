package mengh.zy.base.widgets.presenter.view

import mengh.zy.base.presenter.view.BaseDownloadView
import okhttp3.ResponseBody

/**
 * @author by mengh
 * @email menghedianmo@163.com
 * @date on 2018/11/27$.
 * PS: Not easy to write code, please indicate.
 *
 *   Describe:
 */
interface DownloadImgView: BaseDownloadView {
    fun onGetImgSuccess(result:ResponseBody)
}