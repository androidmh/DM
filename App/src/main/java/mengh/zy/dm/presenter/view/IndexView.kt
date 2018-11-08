package mengh.zy.dm.presenter.view

import mengh.zy.base.presenter.view.BaseView
import mengh.zy.dm.data.protocol.IndexBean

/**
 * @author by mengh
 * @email menghedianmo@163.com
 * @date on 2018/8/10$.
 * PS: Not easy to write code, please indicate.
 */
interface IndexView : BaseView {
    fun onGetIndexResult(result: IndexBean)
    fun onLoadMoreResult(result: IndexBean)
}