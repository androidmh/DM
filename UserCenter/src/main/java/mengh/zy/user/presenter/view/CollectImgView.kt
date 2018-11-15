package mengh.zy.user.presenter.view

import mengh.zy.base.presenter.view.BaseView
import mengh.zy.user.data.protocol.CollectImgBean

/**
 * @author by mengh
 * @email menghedianmo@163.com
 * @date on 2018/8/10$.
 * PS: Not easy to write code, please indicate.
 */
interface CollectImgView : BaseView {
    fun onGetCollectImgResult(result: CollectImgBean)
    fun onLoadMoreCollectImgResult(result: CollectImgBean)
    fun onCollectResult(result: String)
}