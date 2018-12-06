package mengh.zy.media.ui.activity

import android.view.Menu
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.hwangjr.rxbus.annotation.Subscribe
import com.sackcentury.shinebuttonlib.ShineButton
import kotlinx.android.synthetic.main.activity_search_list.*
import mengh.zy.base.common.BaseConstant
import mengh.zy.base.event.LoginEvent
import mengh.zy.base.ext.empty
import mengh.zy.base.ext.error
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
import org.jetbrains.anko.find
import org.jetbrains.anko.toast

class SearchListActivity : BaseMvpActivity<SearchListPresenter>(), SearchListView {
    override val layoutId: Int
        get() = R.layout.activity_search_list

    private var page = 0

    private lateinit var adapter: ImageListAdapter

    private lateinit var searchString: String

    override fun initView() {
        DMBus.mBus.register(this)
        val toolbar = find<Toolbar>(R.id.dmToolbar)
        initToolbar(toolbar, "", true){
            finishAnimation()
        }
        searchString = intent.getStringExtra(BaseConstant.SEARCH_KEY)
        mProgressLayout.showLoading()
        imgSl.setOnRefreshListener {
            initData()
        }

        imgSl.setOnLoadMoreListener {
            mPresenter.getNextPage(mapOf(Pair("search", searchString), Pair("page", "$page")))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        val searchItem = menu?.findItem(R.id.search_view)
        val searchView = searchItem?.actionView as SearchView
        //保持展开状态
        searchView.isIconified = false
        //无内容无取消按钮
        searchView.onActionViewExpanded()
        //提交按钮
        searchView.isSubmitButtonEnabled = true
        searchView.queryHint = getString(R.string.search_hint)
        searchView.clearFocus()

        val go = searchView.findViewById<AppCompatImageView>(R.id.search_go_btn)
        go.setImageResource(R.mipmap.search)

        val input = searchView.findViewById<SearchView.SearchAutoComplete>(R.id.search_src_text)
        input.setText(searchString)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String): Boolean {
                searchString = query
                searchTo(query)
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return true
            }
        })
        return true
    }

    override fun initData() {
        mPresenter.getImage(mapOf(Pair("search", searchString), Pair("page", "0")))
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

    private fun setRecycle(result: ImageBean) {
        adapter = ImageListAdapter(R.layout.item_img_list, result.images)
        adapter.openLoadAnimation(BaseQuickAdapter.SCALEIN)
        val layoutManager = GridLayoutManager(this, 2)
        imgRv.layoutManager = layoutManager
        imgRv.adapter = adapter
        adapter.setOnItemClickListener { _, _, position ->
            afterLogin {
                val imgDialogFragment = ImgDialogFragment()
                imgDialogFragment.putUrl(result.images[position].url)
                imgDialogFragment.show(supportFragmentManager, "img_dialog")
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
            mPresenter.getImage(mapOf(Pair("search", search), Pair("page", "0")))
        }
    }

}
