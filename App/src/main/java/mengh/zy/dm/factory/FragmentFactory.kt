package mengh.zy.dm.factory

import android.util.SparseArray
import mengh.zy.base.ui.fragment.BaseFragment
import mengh.zy.dm.ui.fragment.IndexFragment
import mengh.zy.media.ui.fragment.ImageFragment
import mengh.zy.media.ui.fragment.VideoFragment
import mengh.zy.user.ui.fragment.UserFragment

/**
 * @author by mengh
 * @email menghedianmo@163.com
 * @date on 2018/9/6$.
 * PS: Not easy to write code, please indicate.
 *
 *   Describe:
 */
object FragmentFactory {
    private var mFragments = SparseArray<BaseFragment>()
    fun createFragment(position: Int): BaseFragment {
        var fragment: BaseFragment? = null
        try {
            fragment = mFragments.get(position)
        } catch (e: Exception) {

        }

        if (fragment == null) {
            when (position) {
                0 -> fragment = IndexFragment()

                1 -> fragment = ImageFragment()

                2 -> fragment = VideoFragment()

                3 -> fragment = UserFragment()
            }
            mFragments.put(position, fragment)
        }

        return fragment!!
    }
}