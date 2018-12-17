package mengh.zy.dm.ui.fragment

import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import kotlinx.android.synthetic.main.fragment_index.*
import kotlinx.android.synthetic.main.fragment_index.view.*
import mengh.zy.base.common.BaseConstant.Companion.IMG_TAB
import mengh.zy.base.ext.empty
import mengh.zy.base.ext.error
import mengh.zy.base.ui.fragment.BaseMvpFragment
import mengh.zy.dm.R
import mengh.zy.dm.data.protocol.IndexBean
import mengh.zy.dm.injection.component.DaggerIndexComponent
import mengh.zy.dm.injection.module.IndexModule
import mengh.zy.dm.presenter.IndexPresenter
import mengh.zy.dm.presenter.view.IndexView
import mengh.zy.dm.ui.adapter.IndexAdapter
import mengh.zy.dm.ui.item.IndexItem
import org.jetbrains.anko.support.v4.find
import org.jetbrains.anko.support.v4.toast

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

    private lateinit var adapter: IndexAdapter

    private var page = 0

    override fun initView(v: View) {
        adapter = IndexAdapter(mutableListOf())
        adapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN)
        val layoutManager = androidx.recyclerview.widget.LinearLayoutManager(mActivity)
        v.indexRv.layoutManager = layoutManager
        v.indexRv.adapter = adapter
    }

    override fun initData() {
        initToolbar(find(R.id.dmToolbar), "首页")
        loadData()
        mProgressLayout.showLoading()
        indexSl.setOnRefreshListener {
            loadData()
        }
        indexSl.setOnLoadMoreListener {
            mPresenter.getNextPage(page)
        }
    }

    private fun loadData() {
        mPresenter.getIndex(0)
    }

    override fun widgetClick(v: View) {
    }

    override fun injectComponent() {
        DaggerIndexComponent.builder().activityComponent(activityComponent).indexModule(IndexModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onGetIndexResult(result: IndexBean) {
        indexSl.finishRefresh()
        IMG_TAB = result.tabs
        if (result.banners.isNullOrEmpty() && result.indexes.isNullOrEmpty()) {
            mProgressLayout.empty()
        } else {
            mProgressLayout.showContent()
            setRecycle(result)
            page = result.next_page
        }
    }

    override fun onLoadMoreResult(result: IndexBean) {
        if (!result.indexes.isNullOrEmpty()){
            page = result.next_page
            for (index in result.indexes) {
                adapter.addData(IndexItem(IndexItem.LAYOUT,index))
            }
        }else{
            toast("没有更多数据了")
        }
        indexSl.finishLoadMore()
    }

    override fun onError(text: String) {
        super.onError(text)
        mProgressLayout.error(title = text, listener = View.OnClickListener {
            loadData()
            mProgressLayout.showLoading()
        })
        indexSl.finishRefresh(false)
        indexSl.finishLoadMore(false)
    }

    private fun setRecycle(result: IndexBean) {
        val data: MutableList<IndexItem> = ArrayList()
        val banners: MutableList<IndexBean.BannersBean> = ArrayList()
        for (banner in result.banners) {
            banners.add(banner)
        }
        data.add(IndexItem(IndexItem.IMG, data2 = banners))
        for (index in result.indexes) {
            data.add(IndexItem(IndexItem.LAYOUT, index))
        }
        adapter.addData(data)
    }
}
