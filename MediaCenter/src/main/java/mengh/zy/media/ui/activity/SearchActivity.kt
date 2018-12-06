package mengh.zy.media.ui.activity

import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import kotlinx.android.synthetic.main.activity_search.*
import mengh.zy.base.common.BaseConstant.Companion.SEARCH_KEY
import mengh.zy.base.ext.onClick
import mengh.zy.base.ext.setVisible
import mengh.zy.base.ui.activity.BaseActivity
import mengh.zy.base.utils.MaterialDialogUtils
import mengh.zy.base.utils.UserHawkUtils
import mengh.zy.base.widgets.DMLabelView
import mengh.zy.media.R
import org.jetbrains.anko.find
import org.jetbrains.anko.toast

class SearchActivity : BaseActivity() {
    override val layoutId: Int
        get() = R.layout.activity_search

    override fun onResume() {
        super.onResume()
        loadData()
    }

    override fun initView() {
        deleteIv.onClick(this)
        val toolbar = find<Toolbar>(R.id.dmToolbar)
        initToolbar(toolbar, "", true){
            finishAnimation()
        }
        loadData()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        val searchItem = menu?.findItem(R.id.search_view)
        val searchView = searchItem?.actionView as SearchView
        searchView.isIconified = false
        searchView.onActionViewExpanded()
        searchView.isSubmitButtonEnabled = true
        searchView.queryHint = getString(R.string.search_hint)
        val src = searchView.findViewById<AppCompatImageView>(R.id.search_go_btn)
        src.setImageResource(R.mipmap.search)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String): Boolean {
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

    private fun loadData() {
        val list = UserHawkUtils.getSearchHistory()
        deleteIv.setVisible(true)
        if (list.isNullOrEmpty()){
            deleteIv.setVisible(false)
        }
        historyFl.removeAllViews()
        for (s in list) {
            val tv = DMLabelView(this)
            tv.text = s
            historyFl.addView(tv)
            tv.onClick {
                searchTo(s)
            }
        }
    }

    override fun widgetClick(v: View) {
        when (v) {
            deleteIv ->{
                MaterialDialogUtils.getConfirmDialog(this,"是否清空搜索历史？","")
                        .positiveButton {
                            UserHawkUtils.deleteSearchHistory()
                            loadData()
                        }
                        .show()
            }
        }
    }

    private fun searchTo(search: String) {
        if (search.isEmpty()){
            toast("请输入你要搜索的内容")
        }else{
            UserHawkUtils.putSearchHistory(search)
            val bundle = Bundle()
            bundle.putString(SEARCH_KEY,search)
            startActivityAnimation(SearchListActivity::class.java,bundle)
        }
    }
}
