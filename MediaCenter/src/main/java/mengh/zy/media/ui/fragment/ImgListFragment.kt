package mengh.zy.media.ui.fragment

import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import kotlinx.android.synthetic.main.fragment_img_list.*
import mengh.zy.base.common.BaseConstant
import mengh.zy.base.ext.empty
import mengh.zy.base.ext.error
import mengh.zy.base.ui.fragment.BaseMvpFragment
import mengh.zy.media.R
import mengh.zy.media.data.protocol.ImageBean
import mengh.zy.media.injection.component.DaggerMediaComponent
import mengh.zy.media.injection.module.MediaModule
import mengh.zy.media.presenter.ImgListPresenter
import mengh.zy.media.presenter.view.ImgListView
import mengh.zy.media.ui.adapter.ImageListAdapter
import mengh.zy.base.widgets.ImgDialogFragment
import org.jetbrains.anko.support.v4.toast

class ImgListFragment : BaseMvpFragment<ImgListPresenter>(), ImgListView {
    override val layoutId: Int
        get() = R.layout.fragment_img_list

    private lateinit var adapter: ImageListAdapter

    private var page = 0

    override fun initView() {
        loadData()
        mProgressLayout.showLoading()
        imgSl.setOnRefreshListener {
            loadData()
        }
        imgSl.setOnLoadMoreListener {
            mPresenter.getNextPage(mapOf(Pair("sort", arguments?.getString(BaseConstant.TAB_KEY)!!), Pair("page", "$page")))
        }
    }

    private fun loadData() {
        mPresenter.getImage(mapOf(Pair("sort", arguments?.getString(BaseConstant.TAB_KEY)!!), Pair("page", "0")))
    }

    override fun widgetClick(v: View) {
    }

    override fun injectComponent() {
        DaggerMediaComponent.builder().activityComponent(activityComponent).mediaModule(MediaModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onGetImgResult(result: ImageBean) {
        page = result.next_page
        imgSl.finishRefresh()
        if (result.images.isNullOrEmpty()) {
            mProgressLayout.empty()
        } else {
            mProgressLayout.showContent()
            setRecycle(result)
        }
    }

    override fun onLoadMoreResult(result: ImageBean) {
        if (!result.images.isNullOrEmpty()) {
            page = result.next_page
            adapter.addData(result.images)
        } else {
            toast("没有更多数据了")
        }
        imgSl.finishLoadMore()
    }

    private fun setRecycle(result: ImageBean) {
        adapter = ImageListAdapter(R.layout.item_img_list, result.images)
        adapter.openLoadAnimation(BaseQuickAdapter.SCALEIN)
        val layoutManager = GridLayoutManager(mActivity, 2)
        imgRv.layoutManager = layoutManager
        imgRv.adapter = adapter
        adapter.onItemClickListener = BaseQuickAdapter.OnItemClickListener { _, _, position ->
            ImgDialogFragment(result.images[position].url!!).show(fragmentManager, "img_dialog")
        }
    }

    override fun onError(text: String) {
        super.onError(text)
        mProgressLayout.error(title = text, listener = View.OnClickListener {
            loadData()
            mProgressLayout.showLoading()
        })
        imgSl.finishRefresh(false)
        imgSl.finishLoadMore(false)
    }

}
