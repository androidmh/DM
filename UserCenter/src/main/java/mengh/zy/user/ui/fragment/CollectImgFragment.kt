package mengh.zy.user.ui.fragment

import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import kotlinx.android.synthetic.main.fragment_collect_img.*
import kotlinx.android.synthetic.main.fragment_collect_img.view.*
import mengh.zy.base.common.BaseConstant
import mengh.zy.base.ext.empty
import mengh.zy.base.ext.error
import mengh.zy.base.ui.activity.PhotoViewActivity
import mengh.zy.base.ui.fragment.BaseMvpFragment
import mengh.zy.base.utils.MaterialDialogUtils
import mengh.zy.user.ui.adapter.CollectImgAdapter
import mengh.zy.user.R
import mengh.zy.user.data.protocol.CollectImgBean
import mengh.zy.user.injection.component.DaggerUserComponent
import mengh.zy.user.injection.module.UserModule
import mengh.zy.user.presenter.CollectImgPresenter
import mengh.zy.user.presenter.view.CollectImgView
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast

class CollectImgFragment : BaseMvpFragment<CollectImgPresenter>(), CollectImgView {

    override val layoutId: Int
        get() = R.layout.fragment_collect_img

    private var page = 0

    private lateinit var adapter: CollectImgAdapter

    override fun initView(v: View) {
        adapter = CollectImgAdapter(R.layout.item_collect_img_list, mutableListOf())
        adapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN)
        val layoutManager = GridLayoutManager(mActivity, 2)
        v.imgRv.layoutManager = layoutManager
        v.imgRv.adapter = adapter
    }

    override fun initData() {
        loadData()
        mProgressLayout.showLoading()
        imgSl.setOnRefreshListener {
            loadData()
        }
        imgSl.setOnLoadMoreListener {
            mPresenter.getCollectImage(page)
        }
    }

    private fun loadData() {
        mPresenter.getCollectImage(0)
    }

    override fun widgetClick(v: View) {
    }

    override fun injectComponent() {
        DaggerUserComponent.builder().activityComponent(activityComponent).userModule(UserModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onGetCollectImgResult(result: CollectImgBean) {
        page = result.next_page
        imgSl.finishRefresh()
        if (result.images.isNullOrEmpty()) {
            mProgressLayout.empty()
        } else {
            mProgressLayout.showContent()
            setRecycle(result)
        }
    }

    override fun onCollectResult(result: String) {
        toast(result)
        loadData()
    }

    override fun onLoadMoreCollectImgResult(result: CollectImgBean) {
        if (!result.images.isNullOrEmpty()) {
            page = result.next_page
            adapter.addData(result.images)
        } else {
            toast("没有更多数据了")
        }
        imgSl.finishLoadMore()
    }

    private fun setRecycle(result: CollectImgBean) {
        adapter.addData(result.images)
        adapter.setOnItemClickListener { _, _, position ->
            startActivity<PhotoViewActivity>(BaseConstant.IMAGE_URL_KEY to result.images[position].url)
        }
        adapter.setOnItemLongClickListener { _, _, position ->
            MaterialDialogUtils.getConfirmDialog(mActivity, "是否删除本条收藏？", "")
                    .positiveButton {
                        mPresenter.deleteCollect(result.images[position].id)
                    }
                    .show()
            false
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
