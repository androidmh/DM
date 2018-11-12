package mengh.zy.media.ui.adapter


import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import mengh.zy.base.ui.fragment.BaseFragment
import java.util.ArrayList

/**
 * Created by HDM on 2017/12/20.
 * E-mail menghedianmo@163.com
 * author HDM
 */

class ImageAdapter(fm: FragmentManager, private val fragments: ArrayList<BaseFragment>, private val tabTileList: List<String>) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): BaseFragment {
        return fragments[position]
    }

    override fun getCount(): Int {
        return fragments.size
    }

    //设置tabLayout标题
    override fun getPageTitle(position: Int): CharSequence? {
        return tabTileList[position]

    }
}