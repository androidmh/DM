package mengh.zy.user.ui.activity

import android.view.View
import kotlinx.android.synthetic.main.activity_collect.*
import mengh.zy.base.ui.activity.BaseActivity
import mengh.zy.base.ui.fragment.BaseFragment
import mengh.zy.user.R
import mengh.zy.user.ui.adapter.CollectAdapter
import mengh.zy.user.ui.fragment.CollectImgFragment
import java.util.ArrayList

class CollectActivity : BaseActivity() {
    override val layoutId: Int
        get() = R.layout.activity_collect

    override fun initView() {
        val fragments = ArrayList<BaseFragment>()
        val list = ArrayList<String>()
        fragments.add(CollectImgFragment())
        list.add("图片")
        fragments.add(CollectImgFragment())
        list.add("文章")
        fragments.add(CollectImgFragment())
        list.add("视频")
        val adapter = CollectAdapter(supportFragmentManager, fragments, list)
        collectVp.adapter = adapter
        collectVp.offscreenPageLimit = adapter.count
        collectTab.setupWithViewPager(collectVp)
    }

    override fun widgetClick(v: View) {

    }

}
