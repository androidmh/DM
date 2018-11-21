package mengh.zy.media.ui.fragment

import android.os.Bundle
import android.util.Pair
import android.view.View
import kotlinx.android.synthetic.main.fragment_image.*
import mengh.zy.base.common.BaseConstant.Companion.IMG_TAB
import mengh.zy.base.common.BaseConstant.Companion.TAB_KEY
import mengh.zy.base.ext.onClick
import mengh.zy.base.ui.fragment.BaseFragment
import mengh.zy.base.ui.fragment.BaseLazyFragment
import mengh.zy.media.R
import mengh.zy.media.ui.activity.SearchActivity
import mengh.zy.media.ui.adapter.ImageAdapter
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
        searchTv.onClick(this)
        uploadTv.onClick(this)
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


    override fun widgetClick(v: View) {
        when (v) {
            searchTv -> {
                startActivityAnimation(SearchActivity::class.java)
            }
            uploadTv -> {
            }
        }
    }
}