package mengh.zy.dm.ui.fragment

import android.support.v7.widget.LinearLayoutManager
import android.view.View
import kotlinx.android.synthetic.main.fragment_index.*
import mengh.zy.base.ui.fragment.BaseMvpFragment
import mengh.zy.dm.R
import mengh.zy.dm.data.protocol.IndexBean
import mengh.zy.dm.injection.component.DaggerIndexComponent
import mengh.zy.dm.injection.module.IndexModule
import mengh.zy.dm.presenter.IndexPresenter
import mengh.zy.dm.presenter.view.IndexView
import mengh.zy.dm.ui.adapter.IndexAdapter
import mengh.zy.dm.ui.item.IndexItem

/**
 * @author by mengh
 * @email menghedianmo@163.com
 * @date on 2018/9/28$.
 * PS: Not easy to write code, please indicate.
 *
 *   Describe:
 */
class IndexFragment : BaseMvpFragment<IndexPresenter>(), IndexView {

    override val layoutId: Int
        get() = R.layout.fragment_index

    override fun initView() {
        mPresenter.getIndex()
    }

    override fun widgetClick(v: View) {
    }

    override fun injectComponent() {
        DaggerIndexComponent.builder().activityComponent(activityComponent).indexModule(IndexModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onGetIndexResult(result: IndexBean) {
        setRecycle(result)
    }

    private fun setRecycle(result: IndexBean) {
        val data: MutableList<IndexItem> = ArrayList()
        val banners: MutableList<IndexBean.BannersBean> = ArrayList()
        for (banner in result.banners) {
            banners.add(banner)
        }
        data.add(IndexItem(IndexItem.IMG, data2 =banners))
        for (index in result.indexes) {
            data.add(IndexItem(IndexItem.LAYOUT,index))
        }

        val adapter = IndexAdapter(data)
        val layoutManager = LinearLayoutManager(mActivity)
        indexRv.layoutManager = layoutManager
        indexRv.adapter = adapter
    }
}
