package mengh.zy.media.ui.fragment

import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.hwangjr.rxbus.annotation.Subscribe
import com.sackcentury.shinebuttonlib.ShineButton
import kotlinx.android.synthetic.main.fragment_img_list.*
import kotlinx.android.synthetic.main.fragment_img_list.view.*
import mengh.zy.base.common.BaseConstant
import mengh.zy.base.common.BaseConstant.Companion.IMAGE_URL_KEY
import mengh.zy.base.event.LoginEvent
import mengh.zy.base.ext.empty
import mengh.zy.base.ext.error
import mengh.zy.base.rx.DMBus
import mengh.zy.base.ui.activity.DMWebActivity
import mengh.zy.base.ui.activity.PhotoViewActivity
import mengh.zy.base.ui.fragment.BaseMvpFragment
import mengh.zy.media.R
import mengh.zy.media.data.protocol.ImageBean
import mengh.zy.media.injection.component.DaggerMediaComponent
import mengh.zy.media.injection.module.MediaModule
import mengh.zy.media.presenter.ImgListPresenter
import mengh.zy.media.presenter.view.ImgListView
import mengh.zy.media.ui.adapter.ImageListAdapter
import mengh.zy.provider.common.afterLogin
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast

class ImgListFragment : BaseMvpFragment<ImgListPresenter>(), ImgListView {

    override val layoutId: Int
        get() = R.layout.fragment_img_list

    private lateinit var adapter: ImageListAdapter

    private var page = 0

    override fun initView(v: View) {
        adapter = ImageListAdapter(R.layout.item_img_list, mutableListOf())
        adapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN)
        val layoutManager = GridLayoutManager(mActivity, 2)
        v.imgRv.layoutManager = layoutManager
        v.imgRv.adapter = adapter
    }

    override fun initData() {
        DMBus.mBus.register(this)
        mProgressLayout.showLoading()
        imgSl.setOnRefreshListener {
            loadData()
        }
        imgSl.setOnLoadMoreListener {
            mPresenter.getNextPage(mapOf(Pair("sort", arguments?.getString(BaseConstant.TAB_KEY)!!), Pair("page", "$page")))
        }
        loadData()
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
        if (result.images.isNullOrEmpty()) {
            mProgressLayout.empty()
        } else {
            page = result.next_page
            mProgressLayout.showContent()
            setRecycle(result)
        }
        imgSl.finishRefresh()
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

    override fun onCollectResult(result: String) {
//        toast(result)
    }

    private fun setRecycle(result: ImageBean) {
        adapter.addData(result.images)
        adapter.setOnItemClickListener { _, _, position ->
            afterLogin {
                startActivity<PhotoViewActivity>(IMAGE_URL_KEY to result.images[position].url)
            }
        }
        adapter.setOnItemChildClickListener { _, view, position ->
            when (view.id) {
                R.id.collectBtn -> {
                    val collectBtn = adapter.getViewByPosition(imgRv, position, R.id.collectBtn) as ShineButton
                    val isCollect = collectBtn.isChecked
                    afterLogin {
                        mPresenter.setCollect(result.images[position].id, isCollect)
                    }
                }
                R.id.authorTv -> {
                    val author = result.images[position].author
                    if (author.id < 0) {
                        startActivity<DMWebActivity>(BaseConstant.WEB_KEY to author.sign)
                    } else {
                    }
                }
            }
        }
    }

    @Subscribe
    fun login(loginEvent: LoginEvent) {
        if (loginEvent.isLogin) {
            loadData()
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
