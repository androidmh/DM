package mengh.zy.media.ui.activity

import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.recyclerview.widget.GridLayoutManager
import com.blankj.utilcode.util.KeyboardUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.hwangjr.rxbus.annotation.Subscribe
import com.sackcentury.shinebuttonlib.ShineButton
import kotlinx.android.synthetic.main.activity_search_list.*
import mengh.zy.base.common.BaseConstant
import mengh.zy.base.event.LoginEvent
import mengh.zy.base.ext.empty
import mengh.zy.base.ext.error
import mengh.zy.base.ext.getToString
import mengh.zy.base.ext.onClick
import mengh.zy.base.rx.DMBus
import mengh.zy.base.ui.activity.BaseMvpActivity
import mengh.zy.base.utils.UserHawkUtils
import mengh.zy.base.widgets.ImgDialogFragment
import mengh.zy.media.R
import mengh.zy.media.data.protocol.ImageBean
import mengh.zy.media.injection.component.DaggerMediaComponent
import mengh.zy.media.injection.module.MediaModule
import mengh.zy.media.presenter.SearchListPresenter
import mengh.zy.media.presenter.view.SearchListView
import mengh.zy.media.ui.adapter.ImageListAdapter
import mengh.zy.provider.common.afterLogin
import org.jetbrains.anko.toast

class SearchListActivity : BaseMvpActivity<SearchListPresenter>(), SearchListView {
    override val layoutId: Int
        get() = R.layout.activity_search_list

    private var page = 0

    private lateinit var adapter: ImageListAdapter

    private lateinit var searchString: String

    override fun initView() {
        DMBus.mBus.register(this)
        searchTv.onClick(this)
        searchString = intent.getStringExtra(BaseConstant.SEARCH_KEY)
        searchEt.setText(searchString)
        mProgressLayout.showLoading()
        imgSl.setOnRefreshListener {
            initData()
        }

        imgSl.setOnLoadMoreListener {
            mPresenter.getNextPage(mapOf(Pair("search", searchString), Pair("page", "$page")))
        }

        mToolbar.setNavigationOnClickListener {
            finishAnimation()
        }

        searchEt.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                searchTo(searchEt.getToString())
            }
            false
        }
    }


    override fun initData() {
        mPresenter.getImage(mapOf(Pair("search", searchString), Pair("page", "0")))
    }

    override fun widgetClick(v: View) {
        when (v) {
            searchTv -> {
                searchTo(searchEt.getToString())
            }
        }
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

    private fun setRecycle(result: ImageBean) {
        adapter = ImageListAdapter(R.layout.item_img_list, result.images)
        adapter.openLoadAnimation(BaseQuickAdapter.SCALEIN)
        val layoutManager = GridLayoutManager(this, 2)
        imgRv.layoutManager = layoutManager
        imgRv.adapter = adapter
        adapter.setOnItemClickListener { _, _, position ->
            afterLogin {
                ImgDialogFragment(result.images[position].url).show(supportFragmentManager, "img_dialog")
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
            }
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

    override fun onError(text: String) {
        super.onError(text)
        mProgressLayout.error(title = text, listener = View.OnClickListener {
            initData()
            mProgressLayout.showLoading()
        })
        imgSl.finishRefresh(false)
        imgSl.finishLoadMore(false)
    }

    override fun onCollectResult(result: String) {
    }

    @Subscribe
    fun login(loginEvent: LoginEvent) {
        if (loginEvent.isLogin) {
            initData()
        }
    }

    private fun searchTo(search: String) {
        if (search.isEmpty()) {
            toast("请输入你要搜索的内容")
        } else {
            UserHawkUtils.putSearchHistory(search)
            KeyboardUtils.hideSoftInput(searchEt)
            mPresenter.getImage(mapOf(Pair("search", search), Pair("page", "0")))
        }
    }

}
