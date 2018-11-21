package mengh.zy.media.presenter.view

import mengh.zy.base.presenter.view.BaseView
import mengh.zy.media.data.protocol.ImageBean

/**
 * @author by mengh
 * @email menghedianmo@163.com
 * @date on 2018/11/9$.
 * PS: Not easy to write code, please indicate.
 *
 *   Describe:
 */
interface SearchListView : BaseView {
    fun onGetImgResult(result: ImageBean)
    fun onLoadMoreResult(result: ImageBean)
    fun onCollectResult(result: String)
}