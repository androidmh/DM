package mengh.zy.base.ui.adapter

import android.view.ViewGroup

import java.util.ArrayList

/**
 * author : HJQ
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2018/10/18
 * desc   : FragmentPagerAdapter基类
 */
abstract class BaseFragmentPagerAdapter<T : androidx.fragment.app.Fragment> private constructor(private val fm: androidx.fragment.app.FragmentManager) : androidx.fragment.app.FragmentPagerAdapter(fm) {

    /**
     * 获取Fragment集合
     */
    private val allFragment: MutableList<T> = ArrayList() // Fragment集合

    /**
     * 获取当前的Fragment
     */
    private var currentFragment: Any? = null // 当前显示的Fragment

    /**
     * 在Activity中使用ViewPager适配器
     */
    constructor(activity: androidx.fragment.app.FragmentActivity) : this(activity.supportFragmentManager)

    init {
        setIt()
    }

    private fun setIt() {
        init(fm, allFragment)
    }

    //初始化Fragment
    protected abstract fun  init(fm: androidx.fragment.app.FragmentManager, list: MutableList<T>)

    override fun getItem(position: Int): T {
        return allFragment[position]
    }

    override fun getCount(): Int {
        return allFragment.size
    }

    override fun setPrimaryItem(container: ViewGroup, position: Int, `object`: Any) {
        if (currentFragment !== `object`) {
            // 记录当前的Fragment对象
            currentFragment = `object`
        }
        super.setPrimaryItem(container, position, `object`)
    }
}