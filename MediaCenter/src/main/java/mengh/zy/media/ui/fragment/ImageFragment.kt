package mengh.zy.media.ui.fragment

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.widget.Toolbar
import kotlinx.android.synthetic.main.fragment_image.*
import mengh.zy.base.common.BaseConstant.Companion.IMG_TAB
import mengh.zy.base.common.BaseConstant.Companion.TAB_KEY
import mengh.zy.base.ui.fragment.BaseFragment
import mengh.zy.base.ui.fragment.BaseLazyFragment
import mengh.zy.media.R
import mengh.zy.media.ui.activity.SearchActivity
import mengh.zy.media.ui.adapter.ImageAdapter
import org.jetbrains.anko.support.v4.find
import org.jetbrains.anko.support.v4.toast
import java.util.ArrayList

/**
 * @author by mengh
 * @email menghedianmo@163.com
 * @date on 2018/9/28$.
 * PS: Not easy to write code, please indicate.
 *
 *   Describe:
 */
class ImageFragment : BaseLazyFragment() {
    override val layoutId: Int
        get() = R.layout.fragment_image

    override fun initView() {
        val toolbar = find<Toolbar>(R.id.dmToolbar)
        initToolbar(toolbar, "图片")
        setHasOptionsMenu(true)
        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.search_item -> {
                    startActivityAnimation(SearchActivity::class.java)
                }
                R.id.upload_item->{
                    toast("上传")
                }
            }
            true
        }

        val fragments = ArrayList<BaseFragment>()
        val list = ArrayList<String>()
        for (tab in IMG_TAB) {
            val bundle = Bundle()
            bundle.putString(TAB_KEY, tab)
            val imgListFragment = ImgListFragment()
            imgListFragment.arguments = bundle
            fragments.add(imgListFragment)
            list.add(tab)
        }
        val adapter = ImageAdapter(childFragmentManager, fragments, list)
        imageVp.adapter = adapter
        imageVp.offscreenPageLimit = adapter.count
        imageTab.setupWithViewPager(imageVp)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_image, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun widgetClick(v: View) {}
}