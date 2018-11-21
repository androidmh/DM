package mengh.zy.media.ui.activity

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import com.blankj.utilcode.util.KeyboardUtils
import kotlinx.android.synthetic.main.activity_search.*
import mengh.zy.base.common.BaseConstant.Companion.SEARCH_KEY
import mengh.zy.base.ext.getToString
import mengh.zy.base.ext.onClick
import mengh.zy.base.ext.setVisible
import mengh.zy.base.ui.activity.BaseActivity
import mengh.zy.base.utils.DMTimeUtils
import mengh.zy.base.utils.MaterialDialogUtils
import mengh.zy.base.utils.UserHawkUtils
import mengh.zy.base.widgets.DMLabelView
import mengh.zy.media.R
import org.jetbrains.anko.toast

class SearchActivity : BaseActivity() {
    override val layoutId: Int
        get() = R.layout.activity_search

    override fun onResume() {
        super.onResume()
        loadData()
    }

    override fun initView() {
        searchTv.onClick(this)
        deleteIv.onClick(this)
        val timer = DMTimeUtils.startTimeMethod({
            runOnUiThread {
                KeyboardUtils.showSoftInput(searchEt)
            }
        }, 500)

        mToolbar.setNavigationOnClickListener {
            KeyboardUtils.hideSoftInput(searchEt)
            timer.cancel()
            finishAnimation()
        }

        searchEt.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                searchTo(searchEt.getToString())
            }
            false
        }
        loadData()
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
            searchTv -> {
                searchTo(searchEt.getToString())
            }

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
            KeyboardUtils.hideSoftInput(searchEt)
            val bundle = Bundle()
            bundle.putString(SEARCH_KEY,search)
            startActivityAnimation(SearchListActivity::class.java,bundle)
        }
    }
}
